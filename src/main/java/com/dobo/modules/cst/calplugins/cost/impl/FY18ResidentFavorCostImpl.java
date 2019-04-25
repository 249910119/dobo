package com.dobo.modules.cst.calplugins.cost.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.base.CstDictPara;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.google.common.collect.Lists;

/**
 * 硬件驻场折减成本模型
 *
 */
public class FY18ResidentFavorCostImpl extends CalCostService {
	
	// 折减对应的资源计划 
	private String[] favorLevel = new String[]{"MANCSTHZ1","MANCSTHZ2","MANCSTHZ3","MANCSTHZ4"};

	private ResidentFavorSplit residentFavorSplit;
	
	/**
	 * 计算产品成本-产品清单维度
	 * @param module		模型
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	@Override
    public void cost(CstModelProdModuleRel module, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		paraMap.clear();
		// 根据选择的驻场人员对应的 城市和资源计划分类，遍历故障和巡检中的一线人工计划
		splitDetailsByCityAndResType(module.getProdId(), detailInfoMap, proddciMap);
		
		// 加载计算参数
		super.getCostCalculatePara(module, null);
		
		//System.out.println(residentFavorSplit.getResidentDetailResMap().size());
		// 根据选择的驻场人员对应的 城市和资源计划分类，聚合计算折减情况
		for(String cityAndRestype : residentFavorSplit.getResidentDetailResMap().keySet()) {
			// 故障和巡检服务成本清单中无 对应的折减资源
			if(residentFavorSplit.getProdDetailResMap().get(cityAndRestype) == null) continue;

			// 返回成本明细集合 <detailId, costInfo>
			DetailCostInfo detailCostInfo = new DetailCostInfo();
			// 获取同城市同资源计划分类维度的折减资源计划
			Map<String, String> favorMap = new HashMap<String, String>();
			// 初始折减资源计划
			for(String favor : favorLevel) {
				favorMap.put(favor, "0.0");
			}
			detailCostInfo.setCostInfoMap(favorMap);
			
			Map<String, Double> residentResMap = residentFavorSplit.getResidentDetailResMap().get(cityAndRestype);
			Map<String, Double> prodResMap = residentFavorSplit.getProdDetailResMap().get(cityAndRestype);
			// 折减顺序：初级(一线2级)→中级(一线3级)→高级(一线4级)
			String[] residentLevel = new String[]{"MANCSTH12","MANCSTH13","MANCSTH14"};
			// 折减故障和巡检服务中的当前级别
			int level = 0;
			// 故障和巡检服务中对应的资源计划的measureId
			String measureId = "";
			// 折减对应的资源计划的measureId
			String favorMeasureId = "";
			for(String rl : residentLevel) {
				if(residentResMap.get(rl) == null) continue;
				level = Integer.valueOf(rl.substring(rl.length()-1));
				
				for(int i=level;i>0;i--) {
					measureId = "MANCSTH1" + i;
					favorMeasureId = "MANCSTHZ" + i;
					if(prodResMap.get(measureId) == null) continue;
					if(residentResMap.get(rl) <= 0) break;
					/**
					 * 当[n级别∑故障、巡检一线人工资源计划]>=[n级别∑驻场（一线人工资源计划*驻场复用比例）]时,一线人工资源计划折减值n=-n级别∑驻场一线人工资源计划*驻场复用比例
					 */
					if(prodResMap.get(measureId) >= residentResMap.get(rl)) {
						favorMap.put(favorMeasureId, Double.valueOf(favorMap.get(favorMeasureId))-residentResMap.get(rl) +"");
						prodResMap.put(measureId, prodResMap.get(measureId)-residentResMap.get(rl));
						residentResMap.put(rl, 0.0);
					}
					/**
					 * 当[n级别∑故障、巡检一线人工资源计划]<[n级别∑驻场（一线人工资源计划*驻场复用比例）]，且当[（n-1）级别∑故障、巡检一线人工资源计划]>=[n级别∑驻场（一线人工资源计划*驻场复用比例）-n级别∑故障、巡检一线人工资源计划]时，
					 * 一线人工资源计划折减值n=-n级别∑故障、巡检一线人工资源计划               
					 * 一线人工资源计划折减值（n-1）=-（n级别∑驻场（一线人工资源计划*驻场复用比例）-n级别∑故障、巡检一线人工资源计划）
					 * 就近级别降级逐级折减原则，最终折减至故障/巡检最低级别的最高值
					 */
					else {
						favorMap.put(favorMeasureId, Double.valueOf(favorMap.get(favorMeasureId))-prodResMap.get(measureId) +"");
						residentResMap.put(rl, residentResMap.get(rl)-prodResMap.get(measureId));
						prodResMap.put(measureId, 0.0);
					}
				}
			}
			
			// 初始折减成本计算需要的资源计划
			paraMap.putAll(favorMap);
			
			// 计算同城市同资源计划分类维度的驻场清单，返回折减成本
			this.calculateFormulaForCost(module, detailCostInfo);
			
			// 获取折减对应驻场的服务周期
			Map<String, Date> serviceCycleMap = residentFavorSplit.getResidentServiceMap().get(cityAndRestype);
			detailCostInfo.setServiceBegin(serviceCycleMap.get("serviceBegin"));
			detailCostInfo.setServiceEnd(serviceCycleMap.get("serviceEnd"));
			
			//proddciMap.get(module.getProdId()).put(cityAndRestype, detailCostInfo);
			String detailId = residentFavorSplit.getFavorTypeMap().get(cityAndRestype)+"_"+cityAndRestype;
			proddciMap.get(module.getProdId()).put(detailId, detailCostInfo);
		}
	}
	
	/**
	 * 遍历模型公式计算产品成本
	 * @param module		模型
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void calculateFormulaForCost(CstModelProdModuleRel module, DetailCostInfo dcis) {
		Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
		for(CstModelCalculateFormula cmcf : pmrMap.values()) {
			if("MANCSTHZ1,MANCSTHZ2,MANCSTHZ3,MANCSTHZ4".contains(cmcf.getMeasureId())) continue;
			
			if(dcis.getCostInfoMap() == null) {
				Map<String, String> costInfoMap = new HashMap<String, String>();
				dcis.setCostInfoMap(costInfoMap);
			}
			
			String calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
			dcis.getCostInfoMap().put(cmcf.getMeasureId(), calValue);
			
			// 参数放在参数集合
			if("A0".equals(cmcf.getIsModelPara())) { //是否成本参数(A0:是/A1:否)
				paraMap.put(cmcf.getMeasureId(), calValue);
			}
		}
	}
	
	/**
	 * 根据选择的驻场人员对应的 城市和资源计划分类，遍历故障和巡检中的一线人工计划
	 * @param prodId    驻场服务编码
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	public void splitDetailsByCityAndResType(String prodId, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		residentFavorSplit = new ResidentFavorSplit();
		String cityId ;
		String resstattyeId ;
		String residentLevel ;
		// 获取字典缓存，用于获取驻场复用系数
		Map<String, CstDictPara> cstDictParaMap = CacheDataUtils.getCstDictParaMap();
		// 1.根据驻场人员遍历清单的同城市同资源计划分类下的清单
		for(ProdDetailInfo pdi : detailInfoMap.get(prodId).values()) {
			// 驻场服务的成本明细
			DetailCostInfo dci = proddciMap.get(prodId).get(pdi.getDetailId());
			
			Map<String, String> inParaMap = pdi.getInParaMap();
			// 取字典的驻场复用系数
			CstDictPara cstDictPara = cstDictParaMap.get(inParaMap.get("WORKKIND"));
			if(cstDictPara.getParaValue() > 0) {
				cityId = inParaMap.get("SECTION");
				
				CstResourceBaseInfo cstResourceBaseInfo = CacheDataUtils.getCstResourceBaseInfoMap().get(inParaMap.get("resourceId"));
				// 驻场中的资源计划分类
				resstattyeId = cstResourceBaseInfo.getResstattypeId();
				// 驻场的级别
				residentLevel = CacheDataUtils.getCstManLevelRoleRelMap().get(cstResourceBaseInfo.getLaborLevel()).getMeasureId();
				if(residentFavorSplit.getResidentDetailResMap().get(cityId+resstattyeId) == null) {
					residentFavorSplit.getResidentDetailResMap().put(cityId+resstattyeId, new HashMap<String, Double>());
				} 
				if(residentFavorSplit.getResidentDetailResMap().get(cityId+resstattyeId).get(residentLevel) == null) {
					residentFavorSplit.getResidentDetailResMap().get(cityId+resstattyeId).put(residentLevel, 0.0);
				}
				residentFavorSplit.getResidentDetailResMap().get(cityId+resstattyeId).put(residentLevel, Double.valueOf(dci.getCostInfoMap().get(residentLevel))*cstDictPara.getParaValue() +
						residentFavorSplit.getResidentDetailResMap().get(cityId+resstattyeId).get(residentLevel));
				
				if(residentFavorSplit.getResidentDetailMap().get(cityId+resstattyeId) == null) {
					residentFavorSplit.getResidentDetailMap().put(cityId+resstattyeId, new HashMap<String, List<DetailCostInfo>>());
				} 
				if(residentFavorSplit.getResidentDetailMap().get(cityId+resstattyeId).get(residentLevel) == null) {
					residentFavorSplit.getResidentDetailMap().get(cityId+resstattyeId).put(residentLevel, Lists.newArrayList(dci));
				} else {
					residentFavorSplit.getResidentDetailMap().get(cityId+resstattyeId).get(residentLevel).add(dci);
				}
				
				// 同城市同资源计划分类下驻场服务的服务周期
				if(residentFavorSplit.getResidentServiceMap().get(cityId+resstattyeId) == null) {
					residentFavorSplit.getResidentServiceMap().put(cityId+resstattyeId, new HashMap<String, Date>());
				}
				Map<String, Date> serviceCycleMap = residentFavorSplit.getResidentServiceMap().get(cityId+resstattyeId);
				if(serviceCycleMap.get("serviceBegin") == null || pdi.getServiceBegin().before(serviceCycleMap.get("serviceBegin"))) {
					serviceCycleMap.put("serviceBegin", pdi.getServiceBegin());
				}
				if(serviceCycleMap.get("serviceEnd") == null || pdi.getServiceEnd().after(serviceCycleMap.get("serviceEnd"))) {
					serviceCycleMap.put("serviceEnd", pdi.getServiceEnd());
				}
				
				// 设置折减pd_id为对应驻场其中一条的Pd_id
				residentFavorSplit.getFavorTypeMap().put(cityId+resstattyeId, pdi.getDetailId());
				
			}
		}
		
		// 2.根据驻场清单中人员的复用情况遍历故障和巡检服务
		if(residentFavorSplit.getResidentDetailResMap().size() > 0) {
			//MANCSTH11	一线1级人工资源计划    MANCSTH12	一线2级人工资源计划    MANCSTH13	一线3级人工资源计划   MANCSTH14	一线4级人工资源计划   MANCSTH15	一线5级人工资源计划
			String[] line1LevelMeasure = new String[]{"MANCSTH11","MANCSTH12","MANCSTH13","MANCSTH14","MANCSTH15"};

			Map<String, String> inParaMap ;
			for(String prod : detailInfoMap.keySet()) {
				// 剔除不参与驻场服务折减的服务产品
				if(!"RXSA-03-01-01,RXSA-03-01-03,RXSA-03-02-02".contains(prod)) continue; 
				for(ProdDetailInfo pdi : detailInfoMap.get(prod).values()) {
					// 清单的输入参数
					inParaMap = pdi.getInParaMap();
					cityId = inParaMap.get("SECTION");
					CstResourceBaseInfo cstResourceBaseInfo = CacheDataUtils.getCstResourceBaseInfoMap().get(inParaMap.get("resourceId"));
					// 故障和巡检服务中的资源计划
					resstattyeId = cstResourceBaseInfo.getResstattypeId();
					// 如果清单故障和巡检服务中同城市同资源计划分类存在驻场服务中，计算一线人工资源
					if(residentFavorSplit.getResidentDetailResMap().get(cityId+resstattyeId) != null) {
						
						if(residentFavorSplit.getProdDetailResMap().get(cityId+resstattyeId) == null) {
							residentFavorSplit.getProdDetailResMap().put(cityId+resstattyeId, new HashMap<String, Double>());
						}
						// 故障和巡检服务的成本明细
						DetailCostInfo dci = proddciMap.get(prod).get(pdi.getDetailId());
						// 遍历一线的不同角色
						for(String line1Level : line1LevelMeasure) {
							if(residentFavorSplit.getProdDetailResMap().get(cityId+resstattyeId).get(line1Level) == null) {
								residentFavorSplit.getProdDetailResMap().get(cityId+resstattyeId).put(line1Level, 0.0);
							}
							// 故障和巡检服务的成本明细中对应的一线级别是否存在
							if(dci.getCostInfoMap().get(line1Level) != null) {
								residentFavorSplit.getProdDetailResMap().get(cityId+resstattyeId).put(line1Level, Double.valueOf(dci.getCostInfoMap().get(line1Level)) + 
										residentFavorSplit.getProdDetailResMap().get(cityId+resstattyeId).get(line1Level));
							}
						}
						
						/*// 添加同城市同资源计划分类 对应的名称
						if(residentFavorSplit.getFavorTypeMap().get(cityId+resstattyeId) == null) {
							residentFavorSplit.getFavorTypeMap().put(cityId+resstattyeId, CacheDataUtils.getCstManCityParaMap().get(cityId).getCityName()
									+"|"+CacheDataUtils.getCstCheckResstatSystemRelMap().get(resstattyeId).getResstattypeName());
						}*/
					}
				}
			}
		}
		
	}
	
	public class ResidentFavorSplit {

		// <同城市同资源计划分类, <一线人工级别, 级别对应的资源计划之和>>> 
		private Map<String, Map<String, Double>> prodDetailResMap = new HashMap<String, Map<String,Double>>();

		// <同城市同资源计划分类, <驻场人工级别, 级别对应的驻场资源计划之和>>> 
		private Map<String, Map<String, Double>> residentDetailResMap = new HashMap<String, Map<String, Double>>();

		// <同城市同资源计划分类, <一线人工级别, 级别对应的资源计划折减之和>>> 
		private Map<String, Map<String, Double>> residentFavorResMap = new HashMap<String, Map<String,Double>>();

		// <同城市同资源计划分类, 对应的名称> 
		private Map<String, String> FavorTypeMap = new HashMap<String, String>();
		
		// <同城市同资源计划分类, 对应的驻场人员服务周期> 
		private Map<String, Map<String, Date>> residentServiceMap = new HashMap<String, Map<String, Date>>();
		
		// <同城市同资源计划分类, <驻场人工级别, 级别对应的驻场人员清单>>> --暂时没用
		private Map<String, Map<String, List<DetailCostInfo>>> residentDetailMap = new HashMap<String, Map<String, List<DetailCostInfo>>>();

		public Map<String, Map<String, Double>> getProdDetailResMap() {
			return prodDetailResMap;
		}

		public void setProdDetailResMap(
				Map<String, Map<String, Double>> prodDetailResMap) {
			this.prodDetailResMap = prodDetailResMap;
		}

		public Map<String, Map<String, Double>> getResidentDetailResMap() {
			return residentDetailResMap;
		}

		public void setResidentDetailResMap(
				Map<String, Map<String, Double>> residentDetailResMap) {
			this.residentDetailResMap = residentDetailResMap;
		}

		public Map<String, Map<String, List<DetailCostInfo>>> getResidentDetailMap() {
			return residentDetailMap;
		}

		public void setResidentDetailMap(
				Map<String, Map<String, List<DetailCostInfo>>> residentDetailMap) {
			this.residentDetailMap = residentDetailMap;
		}

		public Map<String, Map<String, Double>> getResidentFavorResMap() {
			return residentFavorResMap;
		}

		public void setResidentFavorResMap(
				Map<String, Map<String, Double>> residentFavorResMap) {
			this.residentFavorResMap = residentFavorResMap;
		}

		public Map<String, String> getFavorTypeMap() {
			return FavorTypeMap;
		}

		public void setFavorTypeMap(Map<String, String> favorTypeMap) {
			FavorTypeMap = favorTypeMap;
		}

		public Map<String, Map<String, Date>> getResidentServiceMap() {
			return residentServiceMap;
		}

		public void setResidentServiceMap(Map<String, Map<String, Date>> residentServiceMap) {
			this.residentServiceMap = residentServiceMap;
		}
		
	}

}
