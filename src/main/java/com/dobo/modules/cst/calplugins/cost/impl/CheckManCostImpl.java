package com.dobo.modules.cst.calplugins.cost.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dobo.common.service.ServiceException;
import com.dobo.common.utils.DateUtils;
import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.check.CstCheckDistanceStepHour;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 硬件巡检成本模型
 *
 */
public class CheckManCostImpl extends CalCostService {
	// 巡检矩阵拆分用到的实体类
	private CheckDetailSplit checkDetailSplit;
	
	/**
	 * 计算产品成本-产品清单维度
	 * @param module		模型
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	@Override
    public void cost(CstModelProdModuleRel module, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		// 切时间片处理,按照服务周期和巡检次数遍历,获取同订单、同城市、同设备大类下的路程工时系数，再平均到明细中
		checkDetailSplit = this.splitDetailsByDateOrder(detailInfoMap.get(module.getProdId()));
		
		super.cost(module, detailInfoMap, proddciMap);
	}
	
	/**
	 * 计算产品成本 : 硬件巡检
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {
		
		try {
			// 现场巡检-BUYCHECKN 远程巡检次数-BUYFARCHK 深度巡检-BUYDEPCHK
			String[] checkTypes = new String[]{"BUYCHECKN","BUYFARCHK","BUYDEPCHK"}; 
			// 清单明细中巡检级别只能按单个巡检级别计算，需要把清单拆分成3条处理;获取巡检次数不等于0的巡检级别
			String checkType = "";
			for(String checkLevel : checkTypes) {
				if(detailInfo.getInParaMap().get(checkLevel) != null && 
						!"null".equals(detailInfo.getInParaMap().get(checkLevel)) &&
						Integer.parseInt(detailInfo.getInParaMap().get(checkLevel)) > 0) {
					checkType = checkLevel;
				}
			}
			
			// 巡检次数为0,添加0记录
			if("".equals(checkType)) {
				//2.遍历逻辑运算公式
				Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
				for(CstModelCalculateFormula cmcf : pmrMap.values()) {
					if(dcis.getCostInfoMap() == null) {
						Map<String, String> costInfoMap = new HashMap<String, String>();
						dcis.setCostInfoMap(costInfoMap);
					}
					
					dcis.getCostInfoMap().put(cmcf.getMeasureId(), "0");
				}
				 
				return ;
			}
			
			paraMap.put("checkLevel", checkType);
			
			//1.加载所有公式参数
			getCostCalculatePara(module, detailInfo.getInParaMap());
			
			// 资源计划分类
			String resstattypeId = CacheDataUtils.getCstResourceBaseInfoMap().get(detailInfo.getInParaMap().get("resourceId")).getResstattypeId();
			// 清单对应的设备大类
			String sametype = detailInfo.getInParaMap().get("SECTION")+
					CacheDataUtils.getCstCheckResstatSystemRelMap().get(resstattypeId).getSystemId();
			// 首先获取路程工时系数
			Map<Integer, List<CstCheckDistanceStepHour>> ccdshMap = CacheDataUtils.getCstCheckDistanceStepHourMap();
			
			/**通过计算加载以下参数 */
			// line1CheckLocaleHour	一线人工资源计划-现场工时（单位：人月）=（（（∑mγm+β*首次巡检系数⑦）*单台设备巡检工时⑧）*各级别一线工程师巡检配比⑨）/8/250*12
			String line1CheckLocaleHour = "";
			// line1CheckNCityHour	一线人工资源计划-市内路程工时'-现场巡检（单位：人月）=（∑nγmn*单台路程工时③*路程工时系数②）/8/250*12
			String line1CheckNCityHour = "(0";
			// line1CheckFCityHour	一线人工资源计划-市内路程工时'-远程巡检（单位：人月）
			String line1CheckFCityHour = "(0";
			// line1CheckDCityHour	一线人工资源计划-市内路程工时'-深度巡检（单位：人月）
			String line1CheckDCityHour = "(0";
			
			// line1TravelNFee	一线差旅费'--现场巡检（单位：人月） 
			String line1TravelNFee = "(0";
			// line1TravelFFee	一线差旅费'--远程巡检（单位：人月）
			String line1TravelFFee = "(0";
			// line1TravelDFee	一线差旅费'--深度巡检（单位：人月）
			String line1TravelDFee = "(0";
			
			for(String check : detailInfo.getMonthCheckNumMap().keySet()) {
				
				for(String yyyyMM : detailInfo.getMonthCheckNumMap().get(check).keySet()) {
					
					// 获取路程工时系数并代入计算
					Double distanceHourScale = 0.0;
					for(CstCheckDistanceStepHour ccdsh : ccdshMap.get(checkDetailSplit.getCitySysMap().get(check).get(yyyyMM).get(sametype).size())) {
						if(ccdsh.getSystemResnumMin()<=checkDetailSplit.getSplitMonthMap().get(check).get(yyyyMM).get(sametype) &&
								ccdsh.getSystemResnumMax()>=checkDetailSplit.getSplitMonthMap().get(check).get(yyyyMM).get(sametype)) {
							distanceHourScale = ccdsh.getDistanceHour();
							break;
						}
					}
					
					// 清单中m月当前行 同城市同设备大类的台次之和
					String currMonthAmount = detailInfo.getMonthCheckNumMap().get(check).get(yyyyMM) + "";
					// m月清单中 同城市同设备大类的台次之和
					String currMonthSum = checkDetailSplit.getSplitMonthMap().get(check).get(yyyyMM).get(sametype) + "";
					
					// 同城市同设备大类的m列清单中，（∑n（γmn*单台设备巡检工时⑧）之和
					Double amount = 0.0;
					for(String value : checkDetailSplit.getSplitMonthResMap().get(check).get(yyyyMM).get(sametype)) {
						String[] strSplit = value.split(":");
						amount += Double.parseDouble(strSplit[1])*Double.parseDouble(strSplit[2]);
					}
					
					// 现场巡检
					if("BUYCHECKN".equals(check)) {
						// 一线人工资源计划-现场工时' =（（（∑mγm+β*首次巡检系数⑦）*单台设备巡检工时⑧）*各级别一线工程师巡检配比⑨）/8/250*12
						line1CheckLocaleHour = "(amount*BUYCHECKN+amount*firstCheckNScale)*line1LocalHour";
						// 一线人工资源计划-市内路程工时'=（∑nγmn*单台路程工时③*路程工时系数②）/8/250*12
						line1CheckNCityHour += "+" + currMonthAmount +
								"*" + paraMap.get("unitDistanceNHour") + "*" + distanceHourScale;
						// 一线差旅费'=（差旅费④-住宿费及其他④）+ROUND（（∑n（γmn*单台设备巡检工时⑧）)/8，0）*住宿费及其他④ ，并按照同订单、同城市、同设备大类拆分
						line1TravelNFee += "+(" + paraMap.get("travelFee") + "-"+ paraMap.get("standAccmFee") +"+" + 
												Math.round(amount/8) + "*"+ paraMap.get("standAccmFee") +")" +
											"*" + currMonthAmount + "/" + currMonthSum;
					}
					// 远程巡检
					else if("BUYFARCHK".equals(check)) {
						// 一线人工资源计划-现场工时' =（（（∑mγm+β*首次巡检系数⑦）*单台设备巡检工时⑧）*各级别一线工程师巡检配比⑨）/8/250*12
						line1CheckLocaleHour = "(amount*BUYFARCHK+amount*firstCheckFScale)*line1RemoteHour";
						// 一线人工资源计划-市内路程工时'=（∑nγmn*单台路程工时③*路程工时系数②）/8/250*12
						line1CheckFCityHour += "+" + currMonthAmount +
								"*" + paraMap.get("unitDistanceFHour") + "*" + distanceHourScale;
						/*// 一线人工差旅资源计划'（单位：人月）=差旅工时④/8/250*12 ，并按照同订单、同城市、同设备大类拆分
						line1TravelFHour += "+" + currMonthAmount + "/" + currMonthSum;
						// 一线差旅费'=（差旅费④-住宿费及其他④）+ROUND（（∑n（γmn*单台设备巡检工时⑧）)/8，0）*住宿费及其他④ ，并按照同订单、同城市、同设备大类拆分
						line1TravelFFee += "+(" + paraMap.get("travelFee") + "-"+ paraMap.get("standAccmFee") +"+" + 
												Math.round(amount/8) + "*"+ paraMap.get("standAccmFee") +")" +
											"*" + currMonthAmount + "/" + currMonthSum;*/
						
						// 远程巡检差旅资源计划和差旅费为0
						line1TravelFFee += "0";   // 一线差旅费'=（差旅费④-300）+ROUND（（∑n（γmn*单台设备巡检工时⑧）)/8，0）*300 ，并按照同订单、同城市、同设备大类拆分
					}
					// 深度巡检
					else if("BUYDEPCHK".equals(check)) {
						// 一线人工资源计划-现场工时' =（（（∑mγm+β*首次巡检系数⑦）*单台设备巡检工时⑧）*各级别一线工程师巡检配比⑨）/8/250*12
						line1CheckLocaleHour = "(amount*BUYDEPCHK+amount*firstCheckDScale)*line1DepthHour";
						// 一线人工资源计划-市内路程工时'=（∑nγmn*单台路程工时③*路程工时系数②）/8/250*12
						line1CheckDCityHour += "+" + currMonthAmount +
								"*" + paraMap.get("unitDistanceDHour") + "*" + distanceHourScale;
						// 一线差旅费'=（差旅费④-住宿费及其他④）+ROUND（（∑n（γmn*单台设备巡检工时⑧）)/8，0）*住宿费及其他④ ，并按照同订单、同城市、同设备大类拆分
						line1TravelDFee += "+(" + paraMap.get("travelFee") + "-"+ paraMap.get("standAccmFee") +"+" + 
												Math.round(amount/8) + "*"+ paraMap.get("standAccmFee") +")" +
											"*" + currMonthAmount + "/" + currMonthSum;
					}
				}
			}
			
			paraMap.put("line1CheckLocaleHour", ExpressionCalculate.getExpressionValue(paraMap, line1CheckLocaleHour));
			line1CheckNCityHour += ")/8/250*12";
			paraMap.put("line1CheckNCityHour", line1CheckNCityHour);
			line1CheckFCityHour += ")/8/250*12";
			paraMap.put("line1CheckFCityHour", line1CheckFCityHour);
			line1CheckDCityHour += ")/8/250*12";
			paraMap.put("line1CheckDCityHour", line1CheckDCityHour);
			
			line1TravelNFee += ")";
			paraMap.put("line1TravelNFee", line1TravelNFee);
			line1TravelFFee += ")";
			paraMap.put("line1TravelFFee", line1TravelFFee);
			line1TravelDFee += ")";
			paraMap.put("line1TravelDFee", line1TravelDFee);
			
			// 如果Orderinfo行差旅工时=0，差旅资源计划和差旅费为0;
			if(new BigDecimal(paraMap.get("travelHour")).compareTo(BigDecimal.ZERO) == 0) {
				paraMap.put("line1TravelNFee", "0");
				paraMap.put("line1TravelFFee", "0");
				paraMap.put("line1TravelDFee", "0");
			}
			
			//2.遍历逻辑运算公式
			Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
			for(CstModelCalculateFormula cmcf : pmrMap.values()) {
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
		} catch (Exception e) {
			throw new ServiceException("成本模型["+module.getModuleId()+"]计算明细["+detailInfo.getDetailId()+"]失败："+e.getMessage());
		}
	}
	
	/**
	 * 切时间片处理,按照服务周期和巡检次数遍历,获取同订单、同城市、同设备大类下的路程工时系数，再平均到明细中
	 * @param detailInfoList
	 * @throws ParseException 
	 */
	public CheckDetailSplit splitDetailsByDateOrder(Map<String, ProdDetailInfo> detailInfoList) {
		CheckDetailSplit cds = new CheckDetailSplit();
		//<年月,<同城市同设备大类, 巡检台次>>
		//Map<String, Map<String, Double>> splitMonthMap = new HashMap<String, Map<String,Double>>();
		//<年月,<同城市同设备大类, List资源计划分类>>
		//Map<String, Map<String, List<String>>> citySysMap = new HashMap<String, Map<String,List<String>>>();
		//<年月,<同城市同设备大类, List<清单主数据ID，单台巡检工时，台次>>>
		//Map<String, Map<String, List<String>>> splitMonthResMap = new HashMap<String, Map<String,List<String>>>();
		
		// 现场巡检-BUYCHECKN 远程巡检次数-BUYFARCHK 深度巡检-BUYDEPCHK
		String[] checkTypes = new String[]{"BUYCHECKN","BUYFARCHK","BUYDEPCHK"}; 
		// 清单明细中的巡检次数
		String checkNum ;
		// 清单设备对应的单台设备巡检工时
		Double line1CheckHour ;
		// 清单设备台数
		Double amount = 0.0;
		// 清单明细中服务期最大边界
		String maxCheckMonth ;
		// 主数据
		CstResourceBaseInfo cstResourceBaseInfo;
		// 巡检工时定义表
		CstCheckWorkHour cstCheckWorkHour;
		for(ProdDetailInfo detailInfo : detailInfoList.values()) {
			if(detailInfo.getInParaMap().get("amount") == null) {
				throw new ServiceException("清单["+detailInfo.getDetailId()+"]对应的巡检设备台数为空，请联系系统管理员！");
			}
			amount = Double.parseDouble(detailInfo.getInParaMap().get("amount"));
			
			maxCheckMonth = DateUtils.formatDate(detailInfo.getServiceEnd(), "yyyyMM");

			// 返回清单服务周期涉及的年月份集合
			List<String> serviceMonths = DateUtils.getMonthBetween(detailInfo.getServiceBegin(), detailInfo.getServiceEnd());
			// 遍历巡检类型
			for(String check : checkTypes) {
				checkNum = detailInfo.getInParaMap().get(check);
				if(checkNum == null || "null".equals(checkNum) || Integer.parseInt(checkNum) == 0) continue;
				
				 Map<String, Double> monthMap = new HashMap<String, Double>();
				 if(detailInfo.getMonthCheckNumMap() == null) {
					 detailInfo.setMonthCheckNumMap(new HashMap<String, Map<String,Double>>());
				 }
				 // 将拆分后的巡检台次数添加到清单对象中，按现场，远程，深度分类，没有的不添加
				 detailInfo.getMonthCheckNumMap().put(check, monthMap);
				 // 默认按清单服务周期的第一个月份进行第一次巡检
				 monthMap.put(serviceMonths.get(0), amount);
				 BigDecimal count = BigDecimal.ZERO;
				
				 for(int i=1;i<Integer.parseInt(checkNum);i++) {
					 // 计算下次巡检在第几个月份
					 count = count.add(BigDecimal.valueOf(serviceMonths.size()).divide(new BigDecimal(checkNum), 2, BigDecimal.ROUND_HALF_UP));
					 // 返回下次巡检的年月
					 String nextMonth = DateUtils.getAddMonth(serviceMonths.get(0), count.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
					 
					 // 判断边界
					 if(DateUtils.parseDate(nextMonth).after(DateUtils.parseDate(maxCheckMonth))) {
						 nextMonth = maxCheckMonth;
					 }
					 
					 if(monthMap.get(nextMonth) == null) {
						 monthMap.put(nextMonth, amount);
					 } else {
						 monthMap.put(nextMonth, amount+monthMap.get(nextMonth));
					 }
				 }
			}
			
			// 清单中现场、远程、深度巡检次数同时为空或者为0
			if(detailInfo.getMonthCheckNumMap() == null || detailInfo.getMonthCheckNumMap().size() == 0) {
				continue;
				//throw new ServiceException("清单["+detailInfo.getDetailId()+"]对应的巡检次数为空或者为0，请联系系统管理员！");
			}
			
			// 主数据
			cstResourceBaseInfo = CacheDataUtils.getCstResourceBaseInfoMap().get(detailInfo.getInParaMap().get("resourceId"));
			if(cstResourceBaseInfo == null) {
				throw new ServiceException("清单["+detailInfo.getDetailId()+"]对应的资源主数据未找到，请联系系统管理员！");
			}
			// 巡检工时定义表
			cstCheckWorkHour = CacheDataUtils.getCstCheckWorkHourMap().get(cstResourceBaseInfo.getModelGroupId());
			if(cstCheckWorkHour == null) {
				throw new ServiceException("清单["+detailInfo.getDetailId()+"]对应的主数据【resourceId:"+cstResourceBaseInfo.getResourceId()+"】未找到[resModelId:"+cstResourceBaseInfo.getModelGroupId()+"]巡检工时定义，请联系系统管理员！");
			}
			// 资源计划分类
			String resstattypeId = cstResourceBaseInfo.getResstattypeId();
			if(resstattypeId == null) {
				throw new ServiceException("清单["+detailInfo.getDetailId()+"]对应的主数据【resourceId:"+cstResourceBaseInfo.getResourceId()+"】未找到资源计划分类[resstattypeId]，请联系系统管理员！");
			}
			// 清单对应的设备大类
			String sametype = detailInfo.getInParaMap().get("SECTION")+
					CacheDataUtils.getCstCheckResstatSystemRelMap().get(resstattypeId).getSystemId();
			
			// 累加清单明细，维度：同订单&同城市&同设备大类下
			for(String check : detailInfo.getMonthCheckNumMap().keySet()) {
				// 同城市同设备大类对应的资源计划分类集合
				if(cds.getCitySysMap().get(check) == null) {
					cds.getCitySysMap().put(check, new HashMap<String, Map<String, List<String>>>());
				}
				//<年月,<同城市同设备大类, List资源计划分类>>
				Map<String, Map<String, List<String>>> citySysMap = cds.getCitySysMap().get(check);
				
				// 同城市同设备大类对应的巡检台次数量集合
				if(cds.getSplitMonthMap().get(check) == null) {
					cds.getSplitMonthMap().put(check, new HashMap<String, Map<String,Double>>());
				}
				//<年月,<同城市同设备大类, 巡检台次>>
				Map<String, Map<String, Double>> splitMonthMap = cds.getSplitMonthMap().get(check);
				
				// 同城市同设备大类下面对应的设备清单(清单主数据ID，台次，单台设备巡检工时)
				if(cds.getSplitMonthResMap().get(check) == null) {
					cds.getSplitMonthResMap().put(check, new HashMap<String, Map<String,List<String>>>());
				}
				//<年月,<同城市同设备大类, List<清单主数据ID，单台巡检工时，台次>>>
				Map<String, Map<String, List<String>>> splitMonthResMap = cds.getSplitMonthResMap().get(check);
				
				if("BUYCHECKN".equals(check)) {
					line1CheckHour = cstCheckWorkHour.getLine1LocalHour();
				} else if("BUYFARCHK".equals(check)) {
					line1CheckHour = cstCheckWorkHour.getLine1RemoteHour();
				} else if("BUYDEPCHK".equals(check)) {
					line1CheckHour = cstCheckWorkHour.getLine1DepthHour();
				} else {
					line1CheckHour = null;
				}
				
				Map<String, Double> mcnMap = detailInfo.getMonthCheckNumMap().get(check);
				for(String month : mcnMap.keySet()) {
					
					if(citySysMap.get(month) == null) {
						citySysMap.put(month, new HashMap<String, List<String>>());
					}
					if(citySysMap.get(month).get(sametype) == null) {
						citySysMap.get(month).put(sametype, new ArrayList<String>());
					}
					if(!citySysMap.get(month).get(sametype).contains(resstattypeId)) {
						citySysMap.get(month).get(sametype).add(resstattypeId);
					}
					
					if(splitMonthMap.get(month) == null) {
						splitMonthMap.put(month, new HashMap<String, Double>());
					} 
					if(splitMonthMap.get(month).get(sametype) == null) {
						splitMonthMap.get(month).put(sametype, mcnMap.get(month));
					}
					else {
						splitMonthMap.get(month).put(sametype, mcnMap.get(month)+splitMonthMap.get(month).get(sametype));
					}
					
					if(splitMonthResMap.get(month) == null) {
						splitMonthResMap.put(month, new HashMap<String, List<String>>());
					}
					if(splitMonthResMap.get(month).get(sametype) == null) {
						splitMonthResMap.get(month).put(sametype, new ArrayList<String>());
					}
					splitMonthResMap.get(month).get(sametype).add(cstResourceBaseInfo.getResourceId()+":"+line1CheckHour+":"+mcnMap.get(month));
				}
			}
		}
		
		return cds;
	}
	
	public class CheckDetailSplit {

		//<巡检类型，<年月,<同城市同设备大类, 巡检台次>>>  
		private Map<String, Map<String, Map<String, Double>>> splitMonthMap = new HashMap<String, Map<String, Map<String,Double>>>();
		
		//<巡检类型，<年月,<同城市同设备大类, List资源计划分类>>> 
		private Map<String, Map<String, Map<String, List<String>>>> citySysMap = new HashMap<String, Map<String, Map<String, List<String>>>>();
		
		//<巡检类型，<年月,<同城市同设备大类, List<清单主数据ID，单台巡检工时，台次>>>> --同城市同设备大类下面对应的设备清单(清单主数据ID，台次，单台设备巡检工时)
		private Map<String, Map<String, Map<String, List<String>>>> splitMonthResMap = new HashMap<String, Map<String, Map<String, List<String>>>>();
		
		
		/*
		//<年月,<同城市同设备大类, 巡检台次>>  --现场巡检
		private Map<String, Map<String, Integer>> splitMonthNMap = new HashMap<String, Map<String,Integer>>();
		//<年月,<同城市同设备大类, 巡检台次>>  --远程巡检
		private Map<String, Map<String, Integer>> splitMonthFMap = new HashMap<String, Map<String,Integer>>();
		//<年月,<同城市同设备大类, 巡检台次>>  --深度巡检
		private Map<String, Map<String, Integer>> splitMonthDMap = new HashMap<String, Map<String,Integer>>();
		
		//<同城市同设备大类, List资源计划分类>  --现场巡检
		private Map<String, List<String>> citySysNMap = new HashMap<String, List<String>>();
		//<同城市同设备大类, List资源计划分类>  --远程巡检
		private Map<String, List<String>> citySysFMap = new HashMap<String, List<String>>();
		//<同城市同设备大类, List资源计划分类>  --深度巡检
		private Map<String, List<String>> citySysDMap = new HashMap<String, List<String>>();
		*/
		
		public Map<String, Map<String, Map<String, Double>>> getSplitMonthMap() {
			return splitMonthMap;
		}
		public void setSplitMonthMap(
				Map<String, Map<String, Map<String, Double>>> splitMonthMap) {
			this.splitMonthMap = splitMonthMap;
		}
		public Map<String, Map<String, Map<String, List<String>>>> getCitySysMap() {
			return citySysMap;
		}
		public void setCitySysMap(
				Map<String, Map<String, Map<String, List<String>>>> citySysMap) {
			this.citySysMap = citySysMap;
		}
		public Map<String, Map<String, Map<String, List<String>>>> getSplitMonthResMap() {
			return splitMonthResMap;
		}
		public void setSplitMonthResMap(
				Map<String, Map<String, Map<String, List<String>>>> splitMonthResMap) {
			this.splitMonthResMap = splitMonthResMap;
		}
				
	}

}
