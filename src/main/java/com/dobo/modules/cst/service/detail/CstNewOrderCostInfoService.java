/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.detail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.IdGen;
import com.dobo.common.utils.Reflections;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.dao.detail.CstNewOrderCostInfoDao;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.detail.CstNewOrderCostInfo;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.dobo.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 资源模型成本Service
 * @author admin
 * @version 2019-03-26
 */
@Service
@Transactional(readOnly = true)
public class CstNewOrderCostInfoService extends CrudService<CstNewOrderCostInfoDao, CstNewOrderCostInfo> {

	@Autowired
	CstNewOrderCostInfoDao cstNewOrderCostInfoDao;

	/**
	 * 计算订单服务产品对应清单的成本
	 * @param codiList
	 * @return <prodId, 模型成本list>
	 */
	public static Map<String, List<CstNewOrderCostInfo>> getCalculateDetailCost(List<CstOrderDetailInfo> codiList, boolean flag) {
		
		String orderId = codiList.get(0).getOrderId();
		String dcPrjId = codiList.get(0).getDcPrjId();
		Map<String, List<CstNewOrderCostInfo>> cstDetailCostInfoMap = new HashMap<String, List<CstNewOrderCostInfo>>();
		Map<String, Map<String, ProdDetailInfo>> prodDetailInfoMap = new TreeMap<String, Map<String, ProdDetailInfo>>(
				new Comparator<String>() {
					@Override
                    public int compare(String obj1, String obj2) {
						return obj1.compareTo(obj2);
					}
				});
		
		Map<String, CstResourceBaseInfo> cstResourceBaseInfoMap = CacheDataUtils.getCstResourceBaseInfoTestMap();
		Map<String, CstResourceBaseInfo> cstResourceBaseInfoByIdMap = CacheDataUtils.getCstResourceBaseInfoMap();
		
		for(CstOrderDetailInfo cod : codiList) {
			String prodId = cod.getProdId();
			if(!prodDetailInfoMap.keySet().contains(prodId)) {
				Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
				prodDetailInfoMap.put(prodId, pdiList);
			}
			ProdDetailInfo pdi = new ProdDetailInfo();
			pdi.setProdName(cod.getProdName());
			pdi.setDetailId(cod.getDetailId());
			pdi.setDcPrjId(cod.getDcPrjId());
			pdi.setOrderId(cod.getOrderId());
			pdi.setMfrName(cod.getMfrName());
			pdi.setResourceName(cod.getResourceName());
			pdi.setDetailModel(cod.getDetailModel());
			
			pdi.setServiceBegin(cod.getBeginDate());
			pdi.setServiceEnd(cod.getEndDate());
			
			Map<String, String> inParaMap = new HashMap<String, String>();
			inParaMap.put("resourceId", cod.getResourceId());
			//inParaMap.put("SECTION", cod.getCityId());
			//System.out.println(cod.getMfrName().toUpperCase()+"|"+cod.getResourceName().toUpperCase());
			
			if(!StringUtils.isEmpty(cod.getMfrName()) && !StringUtils.isEmpty(cod.getResourceName())){
				CstResourceBaseInfo cstResourceBaseInfo = cstResourceBaseInfoMap.get(cod.getMfrName().toUpperCase()+cod.getResourceName().toUpperCase());
				if(cstResourceBaseInfo != null) {
					pdi.setEquipType(cstResourceBaseInfo.getEquipTypeName());
					pdi.setDetailModel(cstResourceBaseInfo.getModelGroupName());
					pdi.setResourceId(cstResourceBaseInfo.getResourceId());
					inParaMap.put("resourceId", cstResourceBaseInfo.getResourceId());
				}
			} else {
				CstResourceBaseInfo cstResourceBaseInfo = cstResourceBaseInfoByIdMap.get(cod.getResourceId());
				if(cstResourceBaseInfo != null) {
					pdi.setMfrName(cstResourceBaseInfo.getMfrName());
					pdi.setResourceName(cstResourceBaseInfo.getResourceName());
					pdi.setEquipType(cstResourceBaseInfo.getEquipTypeName());
					pdi.setDetailModel(cstResourceBaseInfo.getModelGroupName());
					pdi.setResourceId(cstResourceBaseInfo.getResourceId());
				}
			}
			
			inParaMap.put("SECTION", cod.getCityId());
			inParaMap.put("amount", cod.getAmount());
			inParaMap.put("SLA", cod.getSlaId());
			inParaMap.put("beginDate", DateUtils.formatDate(cod.getBeginDate(), "yyyy-MM-dd"));
			inParaMap.put("endDate", DateUtils.formatDate(cod.getEndDate(), "yyyy-MM-dd"));
			inParaMap.put("calDateCycle", "");
			inParaMap.put("BUYPRDMON", cod.getCycle());
			inParaMap.put("BUYCHECKN", cod.getCheckN()+"");
			inParaMap.put("BUYFARCHK", cod.getCheckF()+"");
			inParaMap.put("BUYDEPCHK", cod.getCheckD()+"");
			inParaMap.put("URGENT", cod.getUrgentId());
			inParaMap.put("WORKKIND", cod.getWorkkindScale()+"");
			inParaMap.put("BYFEE", cod.getByFee());
			inParaMap.put("PTOSCOST", cod.getClzsFee());
			inParaMap.put("PMANCOST", cod.getManCost());
			inParaMap.put("DELIVCOST", cod.getDelivCost());
			inParaMap.put("PRODCOST", cod.getProdCost());
			inParaMap.put("PRISKCOST", cod.getRiskCost());
			inParaMap.put("PBACKCOST", cod.getBackCost());
			inParaMap.put("OTHERCOST", cod.getOtherCost());
			//inParaMap.put("PSUBCOST", cod.getSubCost());
			
			pdi.setInParaMap(inParaMap);
			
			prodDetailInfoMap.get(prodId).put(pdi.getDetailId(), pdi);
		}
		
		Map<String, List<DetailCostInfo>> pdciMap = new HashMap<String, List<DetailCostInfo>>();
		// 返回成本明细集合 <prodId, <detailID, costInfo>>
		Map<String, Map<String, DetailCostInfo>> proddciMap = new HashMap<String, Map<String,DetailCostInfo>>();
		for(String prodId : prodDetailInfoMap.keySet()) {
			//System.out.println(prodId+":"+prodDetailInfoMap.get(prodId).size());
			//System.out.println(prodDetailInfoMap.get(prodId).get(0).getDcPrjId());
			// 获取服务的模型集合
			List<CstModelProdModuleRel> models = CacheDataUtils.getProdModuleRelMap().get(prodId);
			// 遍历产品成本模型
			for(CstModelProdModuleRel model : models) {
				CalCostService costService = null;
				try {
					Class<?> costClass = Class.forName(model.getClassName());
					//System.out.println(model.getClassName());
					costService = (CalCostService)costClass.newInstance();
					
					costService.cost(model, prodDetailInfoMap, proddciMap);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (InstantiationException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (IllegalAccessException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				}
			}
			
		}
		
		for(String prodId : proddciMap.keySet()) {
			List<DetailCostInfo> retDetailCostlist = new ArrayList<DetailCostInfo>();
			Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
			for(String detailId : dcisMap.keySet()) {
				DetailCostInfo detailCostInfo = dcisMap.get(detailId);
				detailCostInfo.setDetailId(detailId);
				retDetailCostlist.add(detailCostInfo);
			}
			pdciMap.put(prodId, retDetailCostlist);
		}

		User user = new User("admin");
		Date date = new Date();
		String remarks = DateUtils.formatDate(new Date(), "yyyyMMdd");
		//Map<String, CstPartsEventFailurePara> cpefpMap = CacheDataUtils.getCstPartsEventFailureParaMap();
		//将清单成本明细写入表中
		for(String prodId : pdciMap.keySet()) {
			List<CstNewOrderCostInfo> cdciList = Lists.newArrayList();
			
			for(DetailCostInfo dci : pdciMap.get(prodId)) {
				ProdDetailInfo prodDetailInfo = prodDetailInfoMap.get(prodId).get(dci.getDetailId());
				if(dci.getCostInfoMap() == null) continue;
				Map<String, CstNewOrderCostInfo> cdciMap = new HashMap<String, CstNewOrderCostInfo>();
				//CstNewOrderCostInfo
				for(String measureId : dci.getCostInfoMap().keySet()) {
					if(getCostTypeTogetherMap(prodId).get(measureId) == null || "".equals(getCostTypeTogetherMap(prodId).get(measureId).trim())) continue;
					String outkey = dci.getDetailId()+getCostTypeTogetherMap(prodId).get(measureId);
					if(cdciMap.get(outkey) == null) {
						CstNewOrderCostInfo cdci = new CstNewOrderCostInfo();
						cdci.setId(IdGen.uuid());
						cdci.setProdId(prodId);
						cdci.setOrderId(orderId);
						cdci.setPdId(dci.getDetailId());
						cdci.setDcPrjId(dcPrjId);
						
						if(prodDetailInfo != null) {
							cdci.setDcPrjId(prodDetailInfo.getDcPrjId());
							cdci.setResourceId(prodDetailInfo.getResourceId());
							cdci.setMfrName(prodDetailInfo.getMfrName());
							cdci.setResourceName(prodDetailInfo.getResourceName());
							cdci.setModelgroupName(prodDetailInfo.getDetailModel());
							cdci.setEquipTypeName(prodDetailInfo.getEquipType());
							cdci.setBeginDate(prodDetailInfo.getServiceBegin());
							cdci.setEndDate(prodDetailInfo.getServiceEnd());
							cdci.setProdName(prodDetailInfo.getProdName());
						}

						// 驻场服务对应的折减写服务期
						if("RXSA-03-02-05".equals(prodId)) {
							if(dci.getDetailId().contains("_")) {
								cdci.setPdId(dci.getDetailId().split("_")[0]);
							}
							
							if(dci.getServiceBegin() != null && dci.getServiceEnd() != null) {
								cdci.setBeginDate(dci.getServiceBegin());
								cdci.setEndDate(dci.getServiceEnd());
							}
							
						}
						
						cdci.setKeyId(getCostTypeTogetherMap(prodId).get(measureId));
						cdci.setStatus("A0");
						cdci.setCreateBy(user);
						cdci.setCreateDate(date);
						cdci.setUpdateBy(user);
						cdci.setUpdateDate(date);
						cdci.setDelFlag("0");
						cdci.setRemarks(remarks);
						if (flag) {
							for (CstOrderDetailInfo cstOrderDetailInfo : codiList) {
								if (cstOrderDetailInfo.getDetailId().equals(dci.getDetailId())) {
									cdci.setMfrName(cstOrderDetailInfo.getMfrName());
									cdci.setResourceName(cstOrderDetailInfo.getResourceName());
									cdci.setBeginDate(cstOrderDetailInfo.getBeginDate());
									cdci.setEndDate(cstOrderDetailInfo.getEndDate());
									
								}
							}
						}
						cdciMap.put(outkey, cdci);
					}
					
					CstNewOrderCostInfo cdci = cdciMap.get(outkey);
					//  交付管理\风险\产品线管理 --人工和备件的需要合并处理
					if("MANCSTGZ1,MANCSTXJ1,MANCSTZC1,MANCSTZJ1,MANCSTGL1,MANCSTZY1,BAKCSTBJ1".contains(measureId)) {
						cdci.setManDelivery(cdci.getManDelivery().add(new BigDecimal(dci.getCostInfoMap().get(measureId))));
					} else if("MANCSTGZ2,MANCSTXJ2,MANCSTZC2,MANCSTZJ2,MANCSTGL2,MANCSTZY2,BAKCSTBJ2".contains(measureId)) {
						cdci.setManRisk(cdci.getManRisk().add(new BigDecimal(dci.getCostInfoMap().get(measureId))));
					} else if("MANCSTGZ3,MANCSTXJ3,MANCSTZC3,MANCSTZJ3,MANCSTGL3,MANCSTZY3,BAKCSTBJ3".contains(measureId)) {
						cdci.setManProdline(cdci.getManProdline().add(new BigDecimal(dci.getCostInfoMap().get(measureId))));
					} else {
						for(String key : getClassFileCostMap(prodId).keySet()) {
							if(getClassFileCostMap(prodId).get(key).contains(measureId)) {
								Reflections.setFieldValue(cdci, key, new BigDecimal(dci.getCostInfoMap().get(measureId)));
							}
						}
					}
				} 
				
				for(String key : cdciMap.keySet()) {
					cdciList.add(cdciMap.get(key));
				}
				
				/*// 备件故障成本（自有、分包）
				if(dci.getBackSubCostMap() != null && dci.getBackSubCostMap().size() > 0) {
					for(String packId : dci.getBackSubCostMap().keySet()) {
						CstOrderBackCostInfo cobci = new CstOrderBackCostInfo();
						cobci.setId(IdGen.uuid());
						cobci.setDcPrjId(dcPrjId);
						cobci.setOrderId(orderId);
						cobci.setDetailId(dci.getDetailId());
						cobci.setProdId(prodId);
						cobci.setCity(prodDetailInfo.getInParaMap().get("SECTION"));
						cobci.setSla(prodDetailInfo.getInParaMap().get("SLA"));
						cobci.setSla(prodDetailInfo.getInParaMap().get("amount"));
						cobci.setResourceId(prodDetailInfo.getResourceId());
						cobci.setBeginDate(prodDetailInfo.getServiceBegin());
						cobci.setEndDate(prodDetailInfo.getServiceEnd());
						
						cobci.setPackId(packId);
						cobci.setBackCost(Double.valueOf(dci.getBackSubCostMap().get(packId)));
						cobci.setStatus("A0");
						cobci.setCreateBy(user);
						cobci.setCreateDate(date);
						cobci.setUpdateBy(user);
						cobci.setUpdateDate(date);
						cobci.setDelFlag("0");
						cobci.setRemarks(remarks);
						
						cobciList.add(cobci);
					}
				}*/
			}
			//Map<String, Object> cociMap = Maps.newHashMap();
			//cociMap.put("orderCost", cdciList);
			//cociMap.put("orderBackCost", cobciList);
			cstDetailCostInfoMap.put(prodId, cdciList);
		}
		
		return cstDetailCostInfoMap;
	}

	/**
	 * 计算订单服务产品对应清单的成本
	 * @param codiList
	 * @return <prodId, <明细标识，明细总成本>>
	 */
	public static Map<String, Map<String, String>> getCalculateOrderDetailCost(List<CstOrderDetailInfo> codiList, boolean flag) {
		
		Map<String, Map<String, String>> cstDetailCostInfoMap = new HashMap<String, Map<String, String>>();
		Map<String, Map<String, ProdDetailInfo>> prodDetailInfoMap = new TreeMap<String, Map<String, ProdDetailInfo>>(
				new Comparator<String>() {
					@Override
                    public int compare(String obj1, String obj2) {
						return obj1.compareTo(obj2);
					}
				});
		
		Map<String, CstResourceBaseInfo> cstResourceBaseInfoMap = CacheDataUtils.getCstResourceBaseInfoTestMap();
		Map<String, CstResourceBaseInfo> cstResourceBaseInfoByIdMap = CacheDataUtils.getCstResourceBaseInfoMap();
		
		for(CstOrderDetailInfo cod : codiList) {
			String prodId = cod.getProdId();
			if(!prodDetailInfoMap.keySet().contains(prodId)) {
				Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
				prodDetailInfoMap.put(prodId, pdiList);
			}
			ProdDetailInfo pdi = new ProdDetailInfo();
			pdi.setProdName(cod.getProdName());
			pdi.setDetailId(cod.getDetailId());
			pdi.setDcPrjId(cod.getDcPrjId());
			pdi.setOrderId(cod.getOrderId());
			pdi.setMfrName(cod.getMfrName());
			pdi.setResourceName(cod.getResourceName());
			pdi.setDetailModel(cod.getDetailModel());
			
			pdi.setServiceBegin(cod.getBeginDate());
			pdi.setServiceEnd(cod.getEndDate());
			
			Map<String, String> inParaMap = new HashMap<String, String>();
			inParaMap.put("resourceId", cod.getResourceId());
			
			if(!StringUtils.isEmpty(cod.getMfrName()) && !StringUtils.isEmpty(cod.getResourceName())){
				CstResourceBaseInfo cstResourceBaseInfo = cstResourceBaseInfoMap.get(cod.getMfrName().toUpperCase()+cod.getResourceName().toUpperCase());
				if(cstResourceBaseInfo != null) {
					pdi.setEquipType(cstResourceBaseInfo.getEquipTypeName());
					pdi.setDetailModel(cstResourceBaseInfo.getModelGroupName());
					pdi.setResourceId(cstResourceBaseInfo.getResourceId());
					inParaMap.put("resourceId", cstResourceBaseInfo.getResourceId());
				}
			} else {
				CstResourceBaseInfo cstResourceBaseInfo = cstResourceBaseInfoByIdMap.get(cod.getResourceId());
				if(cstResourceBaseInfo != null) {
					pdi.setMfrName(cstResourceBaseInfo.getMfrName());
					pdi.setResourceName(cstResourceBaseInfo.getResourceName());
					pdi.setEquipType(cstResourceBaseInfo.getEquipTypeName());
					pdi.setDetailModel(cstResourceBaseInfo.getModelGroupName());
					pdi.setResourceId(cstResourceBaseInfo.getResourceId());
				}
			}
			
			inParaMap.put("SECTION", cod.getCityId());
			inParaMap.put("amount", cod.getAmount());
			inParaMap.put("SLA", cod.getSlaId());
			inParaMap.put("beginDate", DateUtils.formatDate(cod.getBeginDate(), "yyyy-MM-dd"));
			inParaMap.put("endDate", DateUtils.formatDate(cod.getEndDate(), "yyyy-MM-dd"));
			inParaMap.put("calDateCycle", "");
			inParaMap.put("BUYPRDMON", cod.getCycle());
			inParaMap.put("BUYCHECKN", cod.getCheckN()+"");
			inParaMap.put("BUYFARCHK", cod.getCheckF()+"");
			inParaMap.put("BUYDEPCHK", cod.getCheckD()+"");
			inParaMap.put("URGENT", cod.getUrgentId());
			inParaMap.put("WORKKIND", cod.getWorkkindScale()+"");
			inParaMap.put("BYFEE", cod.getByFee());
			inParaMap.put("ZCMANTYPE", cod.getManType());
			inParaMap.put("PTOSCOST", cod.getClzsFee());
			inParaMap.put("PMANCOST", cod.getManCost());
			inParaMap.put("DELIVCOST", cod.getDelivCost());
			inParaMap.put("PRODCOST", cod.getProdCost());
			inParaMap.put("PRISKCOST", cod.getRiskCost());
			inParaMap.put("PBACKCOST", cod.getBackCost());
			inParaMap.put("OTHERCOST", cod.getOtherCost());
			//inParaMap.put("PSUBCOST", cod.getSubCost());
			
			pdi.setInParaMap(inParaMap);
			
			prodDetailInfoMap.get(prodId).put(pdi.getDetailId(), pdi);
		}
		
		// 返回成本明细集合 <prodId, <detailID, costInfo>>
		Map<String, Map<String, DetailCostInfo>> proddciMap = new HashMap<String, Map<String,DetailCostInfo>>();
		for(String prodId : prodDetailInfoMap.keySet()) {
			// 获取服务的模型集合
			List<CstModelProdModuleRel> models = CacheDataUtils.getProdModuleRelMap().get(prodId);
			// 遍历产品成本模型
			for(CstModelProdModuleRel model : models) {
				CalCostService costService = null;
				try {
					Class<?> costClass = Class.forName(model.getClassName());
					//System.out.println(model.getClassName());
					costService = (CalCostService)costClass.newInstance();
					
					costService.cost(model, prodDetailInfoMap, proddciMap);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (InstantiationException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (IllegalAccessException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				}
			}
			
		}
		
		// 一线	外部资源	PM	二线专家	CMO	备件	备件人工	备件运作	交付管理	风险	产品管理
		String lineOneStr = "MANCSTM11,MANCSTM12,MANCSTM13,MANCSTM14,MANCSTM15,MANCSTM16,MANCSTF11,MANCSTF12,MANCSTF13,MANCSTF14,MANCSTF15,MANCSTF16,"
						  + "MANCSTU11,MANCSTU12,MANCSTU13,MANCSTU14,MANCSTU15,MANCSTU16,MANCSTMZ1,MANCSTMZ2,MANCSTMZ3,MANCSTMZ4,MANCSTMZ5,MANCSTMZ6,"
						  + "MANCSTFZ1,MANCSTFZ2,MANCSTFZ3,MANCSTFZ4,MANCSTFZ5,MANCSTFZ6,MANCSTUZ1,MANCSTUZ2,MANCSTUZ3,MANCSTUZ4,MANCSTUZ5,MANCSTUZ6,"
						  + "MANCSTTF1,MANCSTTF2,MANCSTTF3,MANCSTTF4,MANCSTTF5,MANCSTTF6";
		String outStr = "MANCSTYZ1";
		String pmStr = "MANCSTMG3,MANCSTFG3,MANCSTUG3,MANCSTMG4,MANCSTFG4,MANCSTUG4,MANCSTMG5,MANCSTFG5,MANCSTUG5";
		String lineTwoStr = "MANCSTGZ5";
		String cmoStr = "MANCSTGZ4";
		String backBackStr = "BAKCSTT11,BAKCSTT12,BAKCSTT41,BAKCSTT42";
		String backManStr = "BAKCSTM20";
		String backOptrStr = "BAKCSTT51,BAKCSTT52,BAKCSTT53,BAKCSTT31,BAKCSTT32,BAKCSTT33,BAKCSTT61";
		String dilverStr = "MANCSTGZ1,MANCSTXJ1,MANCSTZC1,MANCSTZJ1,MANCSTGL1,MANCSTZY1,BAKCSTBJ1";
		String riskStr = "MANCSTGZ2,MANCSTXJ2,MANCSTZC2,MANCSTZJ2,MANCSTGL2,MANCSTZY2,BAKCSTBJ2";
		String prodMagrStr = "MANCSTGZ3,MANCSTXJ3,MANCSTZC3,MANCSTZJ3,MANCSTGL3,MANCSTZY3,BAKCSTBJ3";
		
		String manType = "";
		String costType ;
		for(String prodId : proddciMap.keySet()) {
			Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
			
			// 按照结构计算服务对应的结构成本
			if(cstDetailCostInfoMap.get(prodId) == null) {
				cstDetailCostInfoMap.put(prodId, new HashMap<String, String>());
			}
			// 一线	外部资源	PM	二线专家	CMO	备件	备件人工	备件运作	交付管理	风险	产品管理
			Double lineOneCost = 0.0;
			Double outCost = 0.0;
			Double pmCost = 0.0;
			Double lineTwoCost = 0.0;
			Double cmoCost = 0.0;
			Double backBackCost = 0.0;
			Double backManCost = 0.0;
			Double backOptrCost = 0.0;
			Double dilverCost = 0.0;
			Double riskCost = 0.0;
			Double prodMagrCost = 0.0;
			
			for(String detailId : dcisMap.keySet()) {
				DetailCostInfo detailCostInfo = dcisMap.get(detailId);
				
				if("RXSA-03-02-05".equals(prodId)) {
					if(detailId.contains("_")) {
						detailId = detailId.split("_")[0];
					}
					manType = prodDetailInfoMap.get(prodId).get(detailId).getInParaMap().get("ZCMANTYPE");
				}
				
				if(cstDetailCostInfoMap.get(detailId) == null) {
					cstDetailCostInfoMap.put(detailId, new HashMap<String, String>());
					cstDetailCostInfoMap.get(detailId).put("manCost", "0");
					cstDetailCostInfoMap.get(detailId).put("hostCost", "0");
					cstDetailCostInfoMap.get(detailId).put("urgeCost", "0");
					cstDetailCostInfoMap.get(detailId).put("backCost", "0");
				}
				
				Double splitCost = 0.0;
				Double manCost = Double.valueOf(cstDetailCostInfoMap.get(detailId).get("manCost"));
				Double hostCost = Double.valueOf(cstDetailCostInfoMap.get(detailId).get("hostCost"));
				Double urgeCost = Double.valueOf(cstDetailCostInfoMap.get(detailId).get("urgeCost"));
				Double backCost = Double.valueOf(cstDetailCostInfoMap.get(detailId).get("backCost"));
				
				for(String measureId : detailCostInfo.getCostInfoMap().keySet()) {
					costType = getOrderCostTypeTogetherMap(prodId, manType).get(measureId);
					if(costType != null) {
						if("splitCost".equals(costType)) {
							splitCost = splitCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if("manCost".equals(costType)) {
							manCost = manCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if("hostCost".equals(costType)) {
							hostCost = hostCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if("urgeCost".equals(costType)) {
							urgeCost = urgeCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if("backCost".equals(costType)) {
							backCost = backCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						}
						
						// 订单结构成本
						if(lineOneStr.contains(measureId)) {
							lineOneCost = lineOneCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(outStr.contains(measureId)) {
							outCost = outCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(pmStr.contains(measureId)) {
							pmCost = pmCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(lineTwoStr.contains(measureId)) {
							lineTwoCost = lineTwoCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(cmoStr.contains(measureId)) {
							cmoCost = cmoCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(backBackStr.contains(measureId)) {
							backBackCost = backBackCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(backManStr.contains(measureId)) {
							backManCost = backManCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(backOptrStr.contains(measureId)) {
							backOptrCost = backOptrCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(dilverStr.contains(measureId)) {
							dilverCost = dilverCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(riskStr.contains(measureId)) {
							riskCost = riskCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						} else if(prodMagrStr.contains(measureId)) {
							prodMagrCost = prodMagrCost + Double.valueOf(detailCostInfo.getCostInfoMap().get(measureId));
						}
					}
				}
				manCost = manCost + splitCost*0.682;
				hostCost = hostCost + splitCost*0.318;
				
				cstDetailCostInfoMap.get(detailId).put("manCost", manCost+"");
				cstDetailCostInfoMap.get(detailId).put("hostCost", hostCost+"");
				cstDetailCostInfoMap.get(detailId).put("urgeCost", urgeCost+"");
				cstDetailCostInfoMap.get(detailId).put("backCost", backCost+"");
			}
			
			cstDetailCostInfoMap.get(prodId).put("lineOneCost", lineOneCost+"");
			cstDetailCostInfoMap.get(prodId).put("outCost", outCost+"");
			cstDetailCostInfoMap.get(prodId).put("pmCost", pmCost+"");
			cstDetailCostInfoMap.get(prodId).put("lineTwoCost", lineTwoCost+"");
			cstDetailCostInfoMap.get(prodId).put("cmoCost", cmoCost+"");
			cstDetailCostInfoMap.get(prodId).put("backBackCost", backBackCost+"");
			cstDetailCostInfoMap.get(prodId).put("backManCost", backManCost+"");
			cstDetailCostInfoMap.get(prodId).put("backOptrCost", backOptrCost+"");
			cstDetailCostInfoMap.get(prodId).put("dilverCost", dilverCost+"");
			cstDetailCostInfoMap.get(prodId).put("riskCost", riskCost+"");
			cstDetailCostInfoMap.get(prodId).put("prodMagrCost", prodMagrCost+"");
		}
		
		return cstDetailCostInfoMap;
	}

	/**
	 * 清单成本明细与成本分类集合<measureId, costType>
	 * @return
	 */
	private static Map<String, String> getCostTypeTogetherMap(String prodId) {
		Map<String, String> detailCostTypeMap = new HashMap<String, String>();
		
		// 单次、先行支持服务
		if("RXSA-CASE-SUPPORT".equals(prodId)) {
			detailCostTypeMap.put("CASE01H12", "资源"); // CASE01H12	故障现场一线2级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H13", "资源"); // CASE01H13	故障现场一线3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H14", "资源"); // CASE01H14	故障现场一线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H16", "资源"); // CASE01H16	故障现场一线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H24", "资源"); // CASE01H24	故障现场二线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H25", "资源"); // CASE01H25	故障现场二线5级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H26", "资源"); // CASE01H26	故障现场二线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H41", "资源"); // CASE01H41	故障现场CMO人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE01H51", "资源"); // CASE01H51	故障项目部管理资源计划（单位：人月）
			detailCostTypeMap.put("CASE01TW1", "费用"); // CASE01TW1	故障现场外援费用
			detailCostTypeMap.put("CASE01TF2", "费用"); // CASE01TF2	故障现场一线2级差旅费
			detailCostTypeMap.put("CASE01TF3", "费用"); // CASE01TF3	故障现场一线3级差旅费
			detailCostTypeMap.put("CASE01TF4", "费用"); // CASE01TF4	故障现场一线4级差旅费
			detailCostTypeMap.put("CASE01TF6", "费用"); // CASE01TF6	故障现场一线6级差旅费
			detailCostTypeMap.put("CASE02H24", "资源"); // CASE02H24	故障远程二线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE02H25", "资源"); // CASE02H25	故障远程二线5级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE02H26", "资源"); // CASE02H26	故障远程二线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE02H41", "资源"); // CASE02H41	故障远程CMO人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE02H51", "资源"); // CASE02H51	故障项目部管理资源计划（单位：人月）
			detailCostTypeMap.put("CASE02TW1", "费用"); // CASE02TW1	故障远程外援费用
			detailCostTypeMap.put("CASE03H12", "资源"); // CASE03H12	巡检一线2级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H13", "资源"); // CASE03H13	巡检一线3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H14", "资源"); // CASE03H14	巡检一线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H16", "资源"); // CASE03H16	巡检一线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H23", "资源"); // CASE03H23	巡检PM3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H24", "资源"); // CASE03H24	巡检PM4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H25", "资源"); // CASE03H25	巡检PM5级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE03H51", "资源"); // CASE03H51	巡检项目部管理资源计划（单位：人月）
			detailCostTypeMap.put("CASE03TW1", "费用"); // CASE03TW1	巡检外援费用
			detailCostTypeMap.put("CASE03TF2", "费用"); // CASE03TF2	巡检一线2级差旅费
			detailCostTypeMap.put("CASE03TF3", "费用"); // CASE03TF3	巡检一线3级差旅费
			detailCostTypeMap.put("CASE03TF4", "费用"); // CASE03TF4	巡检一线4级差旅费
			detailCostTypeMap.put("CASE03TF6", "费用"); // CASE03TF6	巡检一线6级差旅费
			detailCostTypeMap.put("CASE04H12", "资源"); // CASE04H12	非故障技术支持一线2级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H13", "资源"); // CASE04H13	非故障技术支持一线3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H14", "资源"); // CASE04H14	非故障技术支持一线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H16", "资源"); // CASE04H16	非故障技术支持一线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H23", "资源"); // CASE04H23	非故障技术支持PM3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H24", "资源"); // CASE04H24	非故障技术支持PM4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H25", "资源"); // CASE04H25	非故障技术支持PM5级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE04H51", "资源"); // CASE04H51	非故障技术支持项目部管理资源计划（单位：人月）
			detailCostTypeMap.put("CASE04TW1", "费用"); // CASE04TW1	非故障技术支持外援费用
			detailCostTypeMap.put("CASE04TF2", "费用"); // CASE04TF2	非故障技术支持一线2级差旅费
			detailCostTypeMap.put("CASE04TF3", "费用"); // CASE04TF3	非故障技术支持一线3级差旅费
			detailCostTypeMap.put("CASE04TF4", "费用"); // CASE04TF4	非故障技术支持一线4级差旅费
			detailCostTypeMap.put("CASE04TF6", "费用"); // CASE04TF6	非故障技术支持一线6级差旅费
			detailCostTypeMap.put("CASE05H12", "资源"); // CASE05H12	专业化一线2级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05H13", "资源"); // CASE05H13	专业化一线3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05H14", "资源"); // CASE05H14	专业化一线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05H16", "资源"); // CASE05H16	专业化一线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05H23", "资源"); // CASE05H23	专业化PM3级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05H24", "资源"); // CASE05H24	专业化PM4级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05H25", "资源"); // CASE05H25	专业化PM5级人工资源计划（单位：人月）
			detailCostTypeMap.put("CASE05TW1", "费用"); // CASE05TW1	专业化外援费用
			detailCostTypeMap.put("CASE05TF2", "费用"); // CASE05TF2	专业化一线2级差旅费
			detailCostTypeMap.put("CASE05TF3", "费用"); // CASE05TF3	专业化一线3级差旅费
			detailCostTypeMap.put("CASE05TF4", "费用"); // CASE05TF4	专业化一线4级差旅费
			detailCostTypeMap.put("CASE05TF6", "费用"); // CASE05TF6	专业化一线6级差旅费
			
			detailCostTypeMap.put("CASECTH12", "资源"); // CASECTH12	一线2级人工资源计划
			detailCostTypeMap.put("CASECTH13", "资源"); // CASECTH13	一线3级人工资源计划
			detailCostTypeMap.put("CASECTH14", "资源"); // CASECTH14	一线4级人工资源计划
			detailCostTypeMap.put("CASECTH16", "资源"); // CASECTH16	一线6级人工资源计划
			detailCostTypeMap.put("CASECTH24", "资源"); // CASECTH24	二线4级人工资源计划
			detailCostTypeMap.put("CASECTH25", "资源"); // CASECTH25	二线5级人工资源计划
			detailCostTypeMap.put("CASECTH26", "资源"); // CASECTH26	二线6级人工资源计划
			detailCostTypeMap.put("CASECTH41", "资源"); // CASECTH41	cmo人工资源计划
			detailCostTypeMap.put("CASECTH51", "资源"); // CASECTH51	项目部管理资源计划
			detailCostTypeMap.put("CASECTW11", "费用"); // CASECTW11	外部资源
			detailCostTypeMap.put("CASECTF12", "费用"); // CASECTF12	一线2级差旅费
			detailCostTypeMap.put("CASECTF13", "费用"); // CASECTF13	一线3级差旅费
			detailCostTypeMap.put("CASECTF14", "费用"); // CASECTF14	一线4级差旅费
			detailCostTypeMap.put("CASECTF16", "费用"); // CASECTF16	一线6级差旅费
			detailCostTypeMap.put("CASECTHG3", "资源"); // CASECTH23	PM3级人工资源计划
			detailCostTypeMap.put("CASECTHG4", "资源"); // CASECTH24	PM4级人工资源计划
			detailCostTypeMap.put("CASECTHG5", "资源"); // CASECTH25	PM5级人工资源计划
			
			detailCostTypeMap.put("CASECTM12", "人工"); // CASECTM12	一线2级人工包
			detailCostTypeMap.put("CASECTM13", "人工"); // CASECTM13	一线3级人工包
			detailCostTypeMap.put("CASECTM14", "人工"); // CASECTM14	一线4级人工包
			detailCostTypeMap.put("CASECTM16", "人工"); // CASECTM16	一线6级人工包
			detailCostTypeMap.put("CASECTU12", "激励"); // CASECTU12	一线2级激励包
			detailCostTypeMap.put("CASECTU13", "激励"); // CASECTU13	一线3级激励包
			detailCostTypeMap.put("CASECTU14", "激励"); // CASECTU14	一线4级激励包
			detailCostTypeMap.put("CASECTU16", "激励"); // CASECTU16	一线6级激励包
			detailCostTypeMap.put("CASECTM24", "人工"); // CASECTM24	二线4级人工包
			detailCostTypeMap.put("CASECTM25", "人工"); // CASECTM25	二线5级人工包
			detailCostTypeMap.put("CASECTM26", "人工"); // CASECTM26	二线6级人工包
			detailCostTypeMap.put("CASECTU24", "激励"); // CASECTU24	二线4级激励包
			detailCostTypeMap.put("CASECTU25", "激励"); // CASECTU25	二线5级激励包
			detailCostTypeMap.put("CASECTU26", "激励"); // CASECTU26	二线6级激励包
			detailCostTypeMap.put("CASECTM41", "人工"); // CASECTM41	cmo人工包
			detailCostTypeMap.put("CASECTU41", "激励"); // CASECTU41	cmo激励包
			detailCostTypeMap.put("CASECTMG3", "人工"); // CASECTMG3	PM3级人工包
			detailCostTypeMap.put("CASECTMG4", "人工"); // CASECTMG4	PM4级人工包
			detailCostTypeMap.put("CASECTMG5", "人工"); // CASECTMG5	PM5级人工包
			detailCostTypeMap.put("CASECTUG3", "激励"); // CASECTUG3	PM3级激励包
			detailCostTypeMap.put("CASECTUG4", "激励"); // CASECTUG4	PM4级激励包
			detailCostTypeMap.put("CASECTUG5", "激励"); // CASECTUG5	PM5级激励包
			detailCostTypeMap.put("CASECTM51", "人工"); // CASECTM51	项目部管理人工包
			detailCostTypeMap.put("CASECTU51", "激励"); // CASECTU51	项目部管理激励包
			detailCostTypeMap.put("CASECTM71", "人工"); // CASECTM71	总控管理人工包
			detailCostTypeMap.put("CASECTU71", "激励"); // CASECTU71	总控管理激励包
			detailCostTypeMap.put("CASECTM61", "人工"); // CASECTM61	综合管理成本(区域管理)
			detailCostTypeMap.put("CASECTMM1", "人工"); // CASECTMM1	管理-人工
			detailCostTypeMap.put("CASECTTT1", "人工"); // CASECTTT1	管理-人工
			detailCostTypeMap.put("CASECTMR1", "人工"); // CASECTMR1	管理-人工

			detailCostTypeMap.put("BAKCSTT11", "费用"); //BAKCSTT11	备件费用（故障成本）
			detailCostTypeMap.put("BAKCSTT31", "费用"); //BAKCSTT31	备件专送费用（故障件发货运输成本）
		} 
		// 故障-人工成本  、 驻场
		//if("RXSA-03-01-01,RXSA-03-02-02,RXSA-03-02-05,RXSA-02-01-03,RXSA-TEMP".contains(prodId)) {
		else {
			detailCostTypeMap.put("MANCSTH10", "资源"); //MANCSTH10	故障次数
			detailCostTypeMap.put("MANCSTH11", "资源"); //MANCSTH11	一线1级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH12", "资源"); //MANCSTH12	一线2级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH13", "资源"); //MANCSTH13	一线3级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH14", "资源"); //MANCSTH14	一线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH15", "资源"); //MANCSTH15	一线5级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH16", "资源"); //MANCSTH16	一线6级人工资源计划（单位：人月）
			/*detailCostTypeMap.put("MANCSTH24", "资源"); //MANCSTH24	二线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH25", "资源"); //MANCSTH25	二线5级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH26", "资源"); //MANCSTH26	二线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH36", "资源"); //MANCSTH36	三线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH41", "资源"); //MANCSTH41	CMO人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH81", "资源"); //MANCSTH81	PMO人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH51", "资源"); //MANCSTH51	资源岗人工资源计划（单位：人月）*/
			detailCostTypeMap.put("MANCSTHG3", "资源"); //MANCSTHG3	PM3级
			detailCostTypeMap.put("MANCSTHG4", "资源"); //MANCSTHG4	PM4级
			detailCostTypeMap.put("MANCSTHG5", "资源"); //MANCSTHG5	PM5级
			/*detailCostTypeMap.put("MANCSTHT1", "资源"); //MANCSTHT1	一线1级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT2", "资源"); //MANCSTHT2	一线2级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT3", "资源"); //MANCSTHT3	一线3级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT4", "资源"); //MANCSTHT4	一线4级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT5", "资源"); //MANCSTHT5	一线5级差旅资源计划（单位：人月）*/
			detailCostTypeMap.put("MANCSTHZ1", "资源"); //MANCSTHZ1	一线1级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ2", "资源"); //MANCSTHZ2	一线2级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ3", "资源"); //MANCSTHZ3	一线3级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ4", "资源"); //MANCSTHZ4	一线4级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ5", "资源"); //MANCSTHZ5	一线5级人工资源计划驻场折减（单位：人月）	
			detailCostTypeMap.put("MANCSTHZ6", "资源"); //MANCSTHZ6	一线6级人工资源计划驻场折减（单位：人月）		
			
			detailCostTypeMap.put("MANCSTM11", "日常"); //MANCSTM11	一线1级人工包
			detailCostTypeMap.put("MANCSTM12", "日常"); //MANCSTM12	一线2级人工包
			detailCostTypeMap.put("MANCSTM13", "日常"); //MANCSTM13	一线3级人工包
			detailCostTypeMap.put("MANCSTM14", "日常"); //MANCSTM14	一线4级人工包
			detailCostTypeMap.put("MANCSTM15", "日常"); //MANCSTM15	一线5级人工包
			detailCostTypeMap.put("MANCSTM16", "日常"); //MANCSTM16	一线6级人工包
			detailCostTypeMap.put("MANCSTF11", "费用"); //MANCSTF11	一线1级费用包
			detailCostTypeMap.put("MANCSTF12", "费用"); //MANCSTF12	一线2级费用包
			detailCostTypeMap.put("MANCSTF13", "费用"); //MANCSTF13	一线3级费用包
			detailCostTypeMap.put("MANCSTF14", "费用"); //MANCSTF14	一线4级费用包
			detailCostTypeMap.put("MANCSTF15", "费用"); //MANCSTF15	一线5级费用包
			detailCostTypeMap.put("MANCSTF16", "费用"); //MANCSTF16	一线6级费用包
			detailCostTypeMap.put("MANCSTU11", "激励"); //MANCSTU11	一线1级激励包
			detailCostTypeMap.put("MANCSTU12", "激励"); //MANCSTU12	一线2级激励包
			detailCostTypeMap.put("MANCSTU13", "激励"); //MANCSTU13	一线3级激励包
			detailCostTypeMap.put("MANCSTU14", "激励"); //MANCSTU14	一线4级激励包
			detailCostTypeMap.put("MANCSTU15", "激励"); //MANCSTU15	一线5级激励包
			detailCostTypeMap.put("MANCSTU16", "激励"); //MANCSTU16	一线6级激励包
			
			detailCostTypeMap.put("MANCSTMG3", "日常"); //MANCSTMG3	PM3级人工包
			detailCostTypeMap.put("MANCSTFG3", "费用"); //MANCSTFG3	PM3级费用包
			detailCostTypeMap.put("MANCSTUG3", "激励"); //MANCSTUG3	PM3级激励包
			detailCostTypeMap.put("MANCSTMG4", "日常"); //MANCSTMG4	PM4级人工包
			detailCostTypeMap.put("MANCSTFG4", "费用"); //MANCSTFG4	PM4级费用包
			detailCostTypeMap.put("MANCSTUG4", "激励"); //MANCSTUG4	PM4级激励包
			detailCostTypeMap.put("MANCSTMG5", "日常"); //MANCSTMG5	PM5级人工包
			detailCostTypeMap.put("MANCSTFG5", "费用"); //MANCSTFG5	PM5级费用包
			detailCostTypeMap.put("MANCSTUG5", "激励"); //MANCSTUG5	PM5级激励包
			
			/*detailCostTypeMap.put("MANCSTF26", "费用"); //MANCSTF26	二线6级费用包
			detailCostTypeMap.put("MANCSTU24", "激励"); //MANCSTU24	二线4级激励包
			detailCostTypeMap.put("MANCSTU25", "激励"); //MANCSTU25	二线5级激励包
			detailCostTypeMap.put("MANCSTU26", "激励"); //MANCSTU26	二线6级激励包
			detailCostTypeMap.put("MANCSTM31", "人工"); //MANCSTM31	三线6级人工包
			detailCostTypeMap.put("MANCSTF31", "费用"); //MANCSTF31	三线6级费用包
			detailCostTypeMap.put("MANCSTU31", "激励"); //MANCSTU31	三线6级激励包
			detailCostTypeMap.put("MANCSTM41", "人工"); //MANCSTM41	cmo人工包
			detailCostTypeMap.put("MANCSTF41", "费用"); //MANCSTF41	cmo费用包
			detailCostTypeMap.put("MANCSTU41", "激励"); //MANCSTU41	cmo激励包
			detailCostTypeMap.put("MANCSTM81", "人工"); //MANCSTM81	pmo人工包
			detailCostTypeMap.put("MANCSTF81", "费用"); //MANCSTF81	pmo费用包
			detailCostTypeMap.put("MANCSTU81", "激励"); //MANCSTU81	pmo激励包
			detailCostTypeMap.put("MANCSTM51", "人工"); //MANCSTM51	资源岗人工包
			detailCostTypeMap.put("MANCSTF51", "费用"); //MANCSTF51	资源岗费用包
			detailCostTypeMap.put("MANCSTU51", "激励"); //MANCSTU51	资源岗激励包
			detailCostTypeMap.put("MANCSTM61", "人工"); //MANCSTM61	区域管理人工包
			detailCostTypeMap.put("MANCSTF61", "费用"); //MANCSTF61	区域管理费用包
			detailCostTypeMap.put("MANCSTU61", "激励"); //MANCSTU61	区域管理激励包
			detailCostTypeMap.put("MANCSTM71", "人工"); //MANCSTM71	总控管理人工包
			detailCostTypeMap.put("MANCSTF71", "费用"); //MANCSTF71	总控管理费用包
			detailCostTypeMap.put("MANCSTU71", "激励"); //MANCSTU71	总控管理激励包
			detailCostTypeMap.put("MANCSTMT1", "人工"); //MANCSTMT1	一线1级差旅人工包
			detailCostTypeMap.put("MANCSTMT2", "人工"); //MANCSTMT2	一线2级差旅人工包
			detailCostTypeMap.put("MANCSTMT3", "人工"); //MANCSTMT3	一线3级差旅人工包
			detailCostTypeMap.put("MANCSTMT4", "人工"); //MANCSTMT4	一线4级差旅人工包
			detailCostTypeMap.put("MANCSTMT5", "人工"); //MANCSTMT5	一线5级差旅人工包
			detailCostTypeMap.put("MANCSTFT1", "费用"); //MANCSTFT1	一线1级差旅费用包
			detailCostTypeMap.put("MANCSTFT2", "费用"); //MANCSTFT2	一线2级差旅费用包
			detailCostTypeMap.put("MANCSTFT3", "费用"); //MANCSTFT3	一线3级差旅费用包
			detailCostTypeMap.put("MANCSTFT4", "费用"); //MANCSTFT4	一线4级差旅费用包
			detailCostTypeMap.put("MANCSTFT5", "费用"); //MANCSTFT5	一线5级差旅费用包
			detailCostTypeMap.put("MANCSTUT1", "激励"); //MANCSTUT1	一线1级差旅激励包
			detailCostTypeMap.put("MANCSTUT2", "激励"); //MANCSTUT2	一线2级差旅激励包
			detailCostTypeMap.put("MANCSTUT3", "激励"); //MANCSTUT3	一线3级差旅激励包
			detailCostTypeMap.put("MANCSTUT4", "激励"); //MANCSTUT4	一线4级差旅激励包
			detailCostTypeMap.put("MANCSTUT5", "激励"); //MANCSTUT5	一线5级差旅激励包
*/			
			detailCostTypeMap.put("MANCSTTF1", "差旅"); //MANCSTTF1	一线1级差旅费
			detailCostTypeMap.put("MANCSTTF2", "差旅"); //MANCSTTF2	一线2级差旅费
			detailCostTypeMap.put("MANCSTTF3", "差旅"); //MANCSTTF3	一线3级差旅费
			detailCostTypeMap.put("MANCSTTF4", "差旅"); //MANCSTTF4	一线4级差旅费
			detailCostTypeMap.put("MANCSTTF5", "差旅"); //MANCSTTF5	一线5级差旅费
			detailCostTypeMap.put("MANCSTTF6", "差旅"); //MANCSTTF6	一线6级差旅费
			
			detailCostTypeMap.put("MANCSTMZ1", "日常"); //MANCSTMZ1	一线1级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ2", "日常"); //MANCSTMZ2	一线2级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ3", "日常"); //MANCSTMZ3	一线3级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ4", "日常"); //MANCSTMZ4	一线4级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ5", "日常"); //MANCSTMZ5	一线5级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ6", "日常"); //MANCSTMZ6	一线6级人工驻场折减
			detailCostTypeMap.put("MANCSTFZ1", "费用"); //MANCSTFZ1	一线1级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ2", "费用"); //MANCSTFZ2	一线2级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ3", "费用"); //MANCSTFZ3	一线3级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ4", "费用"); //MANCSTFZ4	一线4级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ5", "费用"); //MANCSTFZ5	一线5级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ6", "费用"); //MANCSTFZ6	一线6级费用驻场折减
			detailCostTypeMap.put("MANCSTUZ1", "激励"); //MANCSTUZ1	一线1级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ2", "激励"); //MANCSTUZ2	一线2级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ3", "激励"); //MANCSTUZ3	一线3级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ4", "激励"); //MANCSTUZ4	一线4级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ5", "激励"); //MANCSTUZ5	一线5级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ6", "激励"); //MANCSTUZ6	一线6级激励驻场折减
			
			/*detailCostTypeMap.put("MANCSTM6Z", "人工"); //MANCSTM6Z	区域管理人工折减
			detailCostTypeMap.put("MANCSTF6Z", "费用"); //MANCSTF6Z	区域管理费用折减
			detailCostTypeMap.put("MANCSTU6Z", "激励"); //MANCSTU6Z	区域管理激励折减
			
			detailCostTypeMap.put("MANCSTTT1", "费用"); //MANCSTTT1	工具
			detailCostTypeMap.put("MANCSTMR1", "费用"); //MANCSTMR1	风险*/
			
			detailCostTypeMap.put("MANCSTGZ5", "日常"); //MANCSTM21	二/三线人工包
			detailCostTypeMap.put("MANCSTGZ4", "日常"); //MANCSTM41	CMO人工包
			detailCostTypeMap.put("MANCSTGZ1", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTGZ2", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTGZ3", "日常"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTXJ1", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTXJ2", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTXJ3", "日常"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTZC1", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTZC2", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTZC3", "日常"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTZJ1", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTZJ2", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTZJ3", "日常"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTGL1", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTGL2", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTGL3", "日常"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTZY1", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTZY2", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTZY3", "日常"); //MANCSTM61	产品线管理人工包

			detailCostTypeMap.put("MANCSTYZ1", "费用"); //MANCSTYZ1	外部资源
			//detailCostTypeMap.put("MANCSTH12", "日常"); //MANCSTH12	一线1级人工成本
			//detailCostTypeMap.put("BACKCSTM1", "日常"); //BACKCSTM1	纯备件
			//detailCostTypeMap.put("BACKCSTM2", "日常"); //BACKCSTM2	备件运作
			//detailCostTypeMap.put("BACKCSTM3", "日常"); //BACKCSTM3	备件人工
			
			// 故障-备件成本
			detailCostTypeMap.put("BAKCSTT11", "日常"); //BAKCSTT11	故障成本-自有
			detailCostTypeMap.put("BAKCSTT12", "日常"); //BAKCSTT12	故障成本-合作
			detailCostTypeMap.put("BAKCSTM20", "日常"); //BAKCSTT21	备件人员人工: 人工成本+激励成本
			//detailCostTypeMap.put("BAKCSTU21", "日常"); //BAKCSTT21	备件人员激励成本
			//detailCostTypeMap.put("BAKCSTM22", "日常"); //BAKCSTM22	单件管控管理人工成本
			//detailCostTypeMap.put("BAKCSTF22", "日常"); //BAKCSTF22	单件管控管理费用成本
			//detailCostTypeMap.put("BAKCSTU22", "日常"); //BAKCSTU22	单件管控管理激励成本
			detailCostTypeMap.put("BAKCSTT41", "日常"); //BAKCSTT41	高频储备成本
			detailCostTypeMap.put("BAKCSTT42", "日常"); //BAKCSTT42	项目储备成本
			detailCostTypeMap.put("BAKCSTT51", "日常"); //BAKCSTT51	物业、水电成本
			detailCostTypeMap.put("BAKCSTT52", "日常"); //BAKCSTT52	包材成本
			detailCostTypeMap.put("BAKCSTT53", "日常"); //BAKCSTT53	仓库租赁成本
			detailCostTypeMap.put("BAKCSTT33", "日常"); //BAKCSTT33	回收取件运输成本
			detailCostTypeMap.put("BAKCSTT32", "日常"); //BAKCSTT32	调拨运输成本
			detailCostTypeMap.put("BAKCSTT31", "日常"); //BAKCSTT31	故障件发货运输成本
			detailCostTypeMap.put("BAKCSTT61", "日常"); //BAKCSTT61	风险储备金
			detailCostTypeMap.put("BAKCSTBJ1", "日常"); //BAKCSTBJ1	交付管理人工包
			detailCostTypeMap.put("BAKCSTBJ2", "日常"); //BAKCSTBJ2	风险-人工
			detailCostTypeMap.put("BAKCSTBJ3", "日常"); //BAKCSTBJ3	产品线管理人工包
		}
		
		return detailCostTypeMap;
	}
	
	/**
	 * 类属性与清单成本明细对应集合<fileType, measureId>
	 * @return
	 */
	private static Map<String, String> getClassFileCostMap(String prodId) {
		Map<String, String> classFileCostMeasureMap = new HashMap<String, String>();
		
		// 单次、先行支持服务
		if("RXSA-CASE-SUPPORT".equals(prodId)) {
			// classFileCostMeasureMap.put("manLine1level1", "MANCSTH11,MANCSTM11,MANCSTF11,MANCSTU11");	// 人工-一线一级
			classFileCostMeasureMap.put("manLine1level2", "CASECTM12,CASECTU12,CASECTH12");	// 人工-一线二级
			classFileCostMeasureMap.put("manLine1level3", "CASECTM13,CASECTU13,CASECTH13");	// 人工-一线三级
			classFileCostMeasureMap.put("manLine1level4", "CASECTM14,CASECTU14,CASECTH14");	// 人工-一线四级
			// classFileCostMeasureMap.put("manLine1level5", "MANCSTH15,MANCSTM15,MANCSTF15,MANCSTU15");	// 人工-一线五级
			classFileCostMeasureMap.put("manLine1level6", "CASECTM16,CASECTU16,CASECTH16");	// 人工-一线六级
			// classFileCostMeasureMap.put("manLine1level1Des", "MANCSTHZ1,MANCSTMZ1,MANCSTFZ1,MANCSTUZ1");	// 人工-一线一级-折减
			// classFileCostMeasureMap.put("manLine1level2Des", "MANCSTHZ2,MANCSTMZ2,MANCSTFZ2,MANCSTUZ2");	// 人工-一线二级-折减
			// classFileCostMeasureMap.put("manLine1level3Des", "MANCSTHZ3,MANCSTMZ3,MANCSTFZ3,MANCSTUZ3");	// 人工-一线三级-折减
			// classFileCostMeasureMap.put("manLine1level4Des", "MANCSTHZ4,MANCSTMZ4,MANCSTFZ4,MANCSTUZ4");	// 人工-一线四级-折减
			// classFileCostMeasureMap.put("manLine1level5Des", "MANCSTHZ5,MANCSTMZ5,MANCSTFZ5,MANCSTUZ5");	// 人工-一线五级-折减
			// classFileCostMeasureMap.put("manLine1level1Travl", "MANCSTHT1,MANCSTMT1,MANCSTFT1,MANCSTUT1,MANCSTTF1");	// 人工-一线一级-差旅
			classFileCostMeasureMap.put("manLine1level2Travl", "CASECTF12");	// 人工-一线二级-差旅
			classFileCostMeasureMap.put("manLine1level3Travl", "CASECTF13");	// 人工-一线三级-差旅
			classFileCostMeasureMap.put("manLine1level4Travl", "CASECTF14");	// 人工-一线四级-差旅
			// classFileCostMeasureMap.put("manLine1level5Travl", "MANCSTHT5,MANCSTMT5,MANCSTFT5,MANCSTUT5,MANCSTTF5");	// 人工-一线五级-差旅
			classFileCostMeasureMap.put("manLine1level6Travl", "CASECTF16");	// 人工-一线六级-差旅
			classFileCostMeasureMap.put("manOutFee", "CASECTW11");	// 外援费用 -外部资源
			classFileCostMeasureMap.put("manLine2level4", "CASECTM24,CASECTU24,CASECTH24");	// 人工-二线四级
			classFileCostMeasureMap.put("manLine2level5", "CASECTM25,CASECTU25,CASECTH25");	// 人工-二线五级
			classFileCostMeasureMap.put("manLine2level6", "CASECTM26,CASECTU26,CASECTH26");	// 人工-二线六级
			// classFileCostMeasureMap.put("manLine3level6", "MANCSTH36,MANCSTM31,MANCSTF31,MANCSTU31");	// 人工-三线六级
			classFileCostMeasureMap.put("manCmo", "CASECTM41,CASECTU41,CASECTH41");	// 人工-cmo
			// classFileCostMeasureMap.put("manPmo", "MANCSTH81,MANCSTM81,MANCSTF81,MANCSTU81");	// 人工-pmo
			classFileCostMeasureMap.put("manZyg", "CASECTM51,CASECTU51,CASECTH51");	// 人工-资源岗
			classFileCostMeasureMap.put("manZkgl", "CASECTM71,CASECTU71");	// 人工-总控管理
			classFileCostMeasureMap.put("manQygl", "CASECTM61");	// 人工-区域管理
			// classFileCostMeasureMap.put("manQyglDes", "MANCSTM6Z,MANCSTF6Z,MANCSTU6Z");	// 人工-区域管理_折减
			classFileCostMeasureMap.put("manPm3", "CASECTMG3,CASECTUG3,CASECTHG3");	// 人工-PM3级
			classFileCostMeasureMap.put("manPm4", "CASECTMG4,CASECTUG4,CASECTHG4");	// 人工-PM4级
			classFileCostMeasureMap.put("manPm5", "CASECTMG5,CASECTUG5,CASECTHG5");	// 人工-PM5级
			classFileCostMeasureMap.put("bakGzcbZy", "BAKCSTT11");	// 备件-故障成本
			// classFileCostMeasureMap.put("bakXmcbcb", "BAKCSTT42");	// 备件-项目储备成本
			// classFileCostMeasureMap.put("bakGpcbcb", "BAKCSTT41");	// 备件-高频储备成本
			// classFileCostMeasureMap.put("bakBjrgcb", "BAKCSTM21,BAKCSTU21");	// 备件-备件人工成本
			// classFileCostMeasureMap.put("bakZkgl", "BAKCSTM22,BAKCSTF22,BAKCSTU22");	// 备件-总控管理
			// classFileCostMeasureMap.put("bakCkzlcb", "BAKCSTT53");	// 备件-仓库租赁成本
			// classFileCostMeasureMap.put("bakBccb", "BAKCSTT52");	// 备件-包材成本
			// classFileCostMeasureMap.put("bakHsqjyscb", "BAKCSTT33");	// 备件-回收取件运输成本
			classFileCostMeasureMap.put("bakGzjfhyscb", "BAKCSTT31");	// 备件-故障件发货运输成本
			// classFileCostMeasureMap.put("bakWysdcb", "BAKCSTT51");	// 备件-物业、水电成本
			// classFileCostMeasureMap.put("bakDbyscb", "BAKCSTT32");	// 备件-调拨运输成本
			classFileCostMeasureMap.put("managerCost", "CASECTMM1");	// 管理
			classFileCostMeasureMap.put("toolCost", "CASECTTT1");	// 工具
			classFileCostMeasureMap.put("riskCost", "CASECTMR1");	// 风险
			
		} 
		//if("RXSA-03-01-01,RXSA-03-02-02,RXSA-03-02-05,RXSA-02-01-03,RXSA-TEMP".contains(prodId)) {
		else {
			classFileCostMeasureMap.put("faultTime", "MANCSTH10");	// 故障次数
			classFileCostMeasureMap.put("manLine1level1", "MANCSTH11,MANCSTM11,MANCSTF11,MANCSTU11,MANCSTHZ1,MANCSTMZ1,MANCSTFZ1,MANCSTUZ1,MANCSTTF1");	// 人工-一线一级
			classFileCostMeasureMap.put("manLine1level2", "MANCSTH12,MANCSTM12,MANCSTF12,MANCSTU12,MANCSTHZ2,MANCSTMZ2,MANCSTFZ2,MANCSTUZ2,MANCSTTF2");	// 人工-一线二级
			classFileCostMeasureMap.put("manLine1level3", "MANCSTH13,MANCSTM13,MANCSTF13,MANCSTU13,MANCSTHZ3,MANCSTMZ3,MANCSTFZ3,MANCSTUZ3,MANCSTTF3");	// 人工-一线三级
			classFileCostMeasureMap.put("manLine1level4", "MANCSTH14,MANCSTM14,MANCSTF14,MANCSTU14,MANCSTHZ4,MANCSTMZ4,MANCSTFZ4,MANCSTUZ4,MANCSTTF4");	// 人工-一线四级
			classFileCostMeasureMap.put("manLine1level5", "MANCSTH15,MANCSTM15,MANCSTF15,MANCSTU15,MANCSTHZ5,MANCSTMZ5,MANCSTFZ5,MANCSTUZ5,MANCSTTF5");	// 人工-一线五级
			classFileCostMeasureMap.put("manLine1level6", "MANCSTH16,MANCSTM16,MANCSTF16,MANCSTU16,MANCSTHZ6,MANCSTMZ6,MANCSTFZ6,MANCSTUZ6,MANCSTTF6");	// 人工-一线六级
			/*classFileCostMeasureMap.put("manLine1level1Des", "MANCSTHZ1,MANCSTMZ1,MANCSTFZ1,MANCSTUZ1");	// 人工-一线一级-折减
			classFileCostMeasureMap.put("manLine1level2Des", "MANCSTHZ2,MANCSTMZ2,MANCSTFZ2,MANCSTUZ2");	// 人工-一线二级-折减
			classFileCostMeasureMap.put("manLine1level3Des", "MANCSTHZ3,MANCSTMZ3,MANCSTFZ3,MANCSTUZ3");	// 人工-一线三级-折减
			classFileCostMeasureMap.put("manLine1level4Des", "MANCSTHZ4,MANCSTMZ4,MANCSTFZ4,MANCSTUZ4");	// 人工-一线四级-折减
			classFileCostMeasureMap.put("manLine1level5Des", "MANCSTHZ5,MANCSTMZ5,MANCSTFZ5,MANCSTUZ5");	// 人工-一线五级-折减
			classFileCostMeasureMap.put("manLine1level1Travl", "MANCSTHT1,MANCSTMT1,MANCSTFT1,MANCSTUT1,MANCSTTF1");	// 人工-一线一级-差旅
			classFileCostMeasureMap.put("manLine1level2Travl", "MANCSTHT2,MANCSTMT2,MANCSTFT2,MANCSTUT2,MANCSTTF2");	// 人工-一线二级-差旅
			classFileCostMeasureMap.put("manLine1level3Travl", "MANCSTHT3,MANCSTMT3,MANCSTFT3,MANCSTUT3,MANCSTTF3");	// 人工-一线三级-差旅
			classFileCostMeasureMap.put("manLine1level4Travl", "MANCSTHT4,MANCSTMT4,MANCSTFT4,MANCSTUT4,MANCSTTF4");	// 人工-一线四级-差旅
			classFileCostMeasureMap.put("manLine1level5Travl", "MANCSTHT5,MANCSTMT5,MANCSTFT5,MANCSTUT5,MANCSTTF5");	// 人工-一线五级-差旅
			classFileCostMeasureMap.put("manLine2level4", "MANCSTH24,MANCSTM24,MANCSTF24,MANCSTU24");	// 人工-二线四级
			classFileCostMeasureMap.put("manLine2level5", "MANCSTH25,MANCSTM25,MANCSTF25,MANCSTU25");	// 人工-二线五级
			classFileCostMeasureMap.put("manLine2level6", "MANCSTH26,MANCSTM26,MANCSTF26,MANCSTU26");	// 人工-二线六级
			classFileCostMeasureMap.put("manLine3level6", "MANCSTH36,MANCSTM31,MANCSTF31,MANCSTU31");	// 人工-三线六级
			classFileCostMeasureMap.put("manCmo", "MANCSTH41,MANCSTM41,MANCSTF41,MANCSTU41");	// 人工-cmo
			classFileCostMeasureMap.put("manPmo", "MANCSTH81,MANCSTM81,MANCSTF81,MANCSTU81");	// 人工-pmo
			classFileCostMeasureMap.put("manZyg", "MANCSTH51,MANCSTM51,MANCSTF51,MANCSTU51");	// 人工-资源岗*/
			
			classFileCostMeasureMap.put("manPm3", "MANCSTHG3,MANCSTMG3,MANCSTFG3,MANCSTUG3");	// 人工-PM3级
			classFileCostMeasureMap.put("manPm4", "MANCSTHG4,MANCSTMG4,MANCSTFG4,MANCSTUG4");	// 人工-PM4级
			classFileCostMeasureMap.put("manPm5", "MANCSTHG5,MANCSTMG5,MANCSTFG5,MANCSTUG5");	// 人工-PM5级
			classFileCostMeasureMap.put("manOutFee", "MANCSTYZ1");	// 外援费用 -外部资源
			
			classFileCostMeasureMap.put("manLine2expert", "MANCSTGZ5");	// 人工-二线专家
			classFileCostMeasureMap.put("manCmo", "MANCSTGZ4");	// CMO人工包
			classFileCostMeasureMap.put("manDelivery", "MANCSTGZ1,MANCSTXJ1,MANCSTZC1,MANCSTZJ1,MANCSTGL1,MANCSTZY1,BAKCSTBJ1");	// 交付管理人工包
			classFileCostMeasureMap.put("manRisk", "MANCSTGZ2,MANCSTXJ2,MANCSTZC2,MANCSTZJ2,MANCSTGL2,MANCSTZY2,BAKCSTBJ2");	// 风险-人工
			classFileCostMeasureMap.put("manProdline", "MANCSTGZ3,MANCSTXJ3,MANCSTZC3,MANCSTZJ3,MANCSTGL3,MANCSTZY3,BAKCSTBJ3");	// 产品线管理人工包
			//classFileCostMeasureMap.put("bakBackCost", "BACKCSTM1");	// 备件-纯备件成本
			//classFileCostMeasureMap.put("bakOperCost", "BACKCSTM2");  //	备件运作
			//classFileCostMeasureMap.put("bakManCost", "BACKCSTM3");   //	备件人工
			
			classFileCostMeasureMap.put("bakGzcbZy", "BAKCSTT11");	// 备件-故障成本_自有
			classFileCostMeasureMap.put("bakGzcbFb", "BAKCSTT12");	// 备件-故障成本_合作
			classFileCostMeasureMap.put("bakBjrg", "BAKCSTM20");	// 备件-备件人工
			classFileCostMeasureMap.put("bakXmcbcb", "BAKCSTT42");	// 备件-项目储备成本
			classFileCostMeasureMap.put("bakGpcbcb", "BAKCSTT41");	// 备件-高频储备成本
			classFileCostMeasureMap.put("bakCkzlcb", "BAKCSTT53");	// 备件-仓库租赁成本
			classFileCostMeasureMap.put("bakWysdcb", "BAKCSTT51");	// 备件-物业、水电成本
			classFileCostMeasureMap.put("bakBccb", "BAKCSTT52");	// 备件-包材成本
			classFileCostMeasureMap.put("bakGzjfhyscb", "BAKCSTT31");	// 备件-故障件发货运输成本
			classFileCostMeasureMap.put("bakDbyscb", "BAKCSTT32");	 // 备件-调拨运输成本
			classFileCostMeasureMap.put("bakHsqjyscb", "BAKCSTT33");	// 备件-回收取件运输成本
			classFileCostMeasureMap.put("bakFxcbj", "BAKCSTT61");	// 备件-备件风险金
		}
		
		return classFileCostMeasureMap;
	}

	/**
	 * 清单成本明细与成本分类集合<measureId, costType>
	 * @return
	 */
	private static Map<String, String> getOrderCostTypeTogetherMap(String prodId, String manType) {
		Map<String, String> detailCostTypeMap = new HashMap<String, String>();
		
		// 非驻场服务
		if(!"RXSA-03-02-05".equals(prodId)) {
			detailCostTypeMap.put("MANCSTM11", "splitCost"); //MANCSTM11	一线1级人工包
			detailCostTypeMap.put("MANCSTM12", "splitCost"); //MANCSTM12	一线2级人工包
			detailCostTypeMap.put("MANCSTM13", "splitCost"); //MANCSTM13	一线3级人工包
			detailCostTypeMap.put("MANCSTM14", "splitCost"); //MANCSTM14	一线4级人工包
			detailCostTypeMap.put("MANCSTM15", "splitCost"); //MANCSTM15	一线5级人工包
			detailCostTypeMap.put("MANCSTM16", "splitCost"); //MANCSTM16	一线6级人工包
			detailCostTypeMap.put("MANCSTF11", "splitCost"); //MANCSTF11	一线1级费用包
			detailCostTypeMap.put("MANCSTF12", "splitCost"); //MANCSTF12	一线2级费用包
			detailCostTypeMap.put("MANCSTF13", "splitCost"); //MANCSTF13	一线3级费用包
			detailCostTypeMap.put("MANCSTF14", "splitCost"); //MANCSTF14	一线4级费用包
			detailCostTypeMap.put("MANCSTF15", "splitCost"); //MANCSTF15	一线5级费用包
			detailCostTypeMap.put("MANCSTF16", "splitCost"); //MANCSTF16	一线6级费用包
			detailCostTypeMap.put("MANCSTU11", "splitCost"); //MANCSTU11	一线1级激励包
			detailCostTypeMap.put("MANCSTU12", "splitCost"); //MANCSTU12	一线2级激励包
			detailCostTypeMap.put("MANCSTU13", "splitCost"); //MANCSTU13	一线3级激励包
			detailCostTypeMap.put("MANCSTU14", "splitCost"); //MANCSTU14	一线4级激励包
			detailCostTypeMap.put("MANCSTU15", "splitCost"); //MANCSTU15	一线5级激励包
			detailCostTypeMap.put("MANCSTU16", "splitCost"); //MANCSTU16	一线6级激励包
			
			detailCostTypeMap.put("MANCSTMG3", "splitCost"); //MANCSTMG3	PM3级人工包
			detailCostTypeMap.put("MANCSTFG3", "splitCost"); //MANCSTFG3	PM3级费用包
			detailCostTypeMap.put("MANCSTUG3", "splitCost"); //MANCSTUG3	PM3级激励包
			detailCostTypeMap.put("MANCSTMG4", "splitCost"); //MANCSTMG4	PM4级人工包
			detailCostTypeMap.put("MANCSTFG4", "splitCost"); //MANCSTFG4	PM4级费用包
			detailCostTypeMap.put("MANCSTUG4", "splitCost"); //MANCSTUG4	PM4级激励包
			detailCostTypeMap.put("MANCSTMG5", "splitCost"); //MANCSTMG5	PM5级人工包
			detailCostTypeMap.put("MANCSTFG5", "splitCost"); //MANCSTFG5	PM5级费用包
			detailCostTypeMap.put("MANCSTUG5", "splitCost"); //MANCSTUG5	PM5级激励包
			
			detailCostTypeMap.put("MANCSTTF1", "splitCost"); //MANCSTTF1	一线1级差旅费
			detailCostTypeMap.put("MANCSTTF2", "splitCost"); //MANCSTTF2	一线2级差旅费
			detailCostTypeMap.put("MANCSTTF3", "splitCost"); //MANCSTTF3	一线3级差旅费
			detailCostTypeMap.put("MANCSTTF4", "splitCost"); //MANCSTTF4	一线4级差旅费
			detailCostTypeMap.put("MANCSTTF5", "splitCost"); //MANCSTTF5	一线5级差旅费
			detailCostTypeMap.put("MANCSTTF6", "splitCost"); //MANCSTTF6	一线6级差旅费
			
			detailCostTypeMap.put("MANCSTMZ1", "splitCost"); //MANCSTMZ1	一线1级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ2", "splitCost"); //MANCSTMZ2	一线2级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ3", "splitCost"); //MANCSTMZ3	一线3级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ4", "splitCost"); //MANCSTMZ4	一线4级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ5", "splitCost"); //MANCSTMZ5	一线5级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ6", "splitCost"); //MANCSTMZ6	一线6级人工驻场折减
			detailCostTypeMap.put("MANCSTFZ1", "splitCost"); //MANCSTFZ1	一线1级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ2", "splitCost"); //MANCSTFZ2	一线2级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ3", "splitCost"); //MANCSTFZ3	一线3级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ4", "splitCost"); //MANCSTFZ4	一线4级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ5", "splitCost"); //MANCSTFZ5	一线5级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ6", "splitCost"); //MANCSTFZ6	一线6级费用驻场折减
			detailCostTypeMap.put("MANCSTUZ1", "splitCost"); //MANCSTUZ1	一线1级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ2", "splitCost"); //MANCSTUZ2	一线2级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ3", "splitCost"); //MANCSTUZ3	一线3级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ4", "splitCost"); //MANCSTUZ4	一线4级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ5", "splitCost"); //MANCSTUZ5	一线5级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ6", "splitCost"); //MANCSTUZ6	一线6级激励驻场折减
			
			detailCostTypeMap.put("MANCSTGZ5", "splitCost"); //MANCSTM21	二/三线人工包
			detailCostTypeMap.put("MANCSTGZ4", "splitCost"); //MANCSTM41	CMO人工包
			detailCostTypeMap.put("MANCSTGZ1", "splitCost"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTGZ2", "splitCost"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTGZ3", "manCost"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTXJ1", "splitCost"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTXJ2", "splitCost"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTXJ3", "manCost"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTZC1", "splitCost"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTZC2", "splitCost"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTZC3", "manCost"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTZJ1", "splitCost"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTZJ2", "splitCost"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTZJ3", "manCost"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTGL1", "splitCost"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTGL2", "splitCost"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTGL3", "manCost"); //MANCSTM61	产品线管理人工包
			detailCostTypeMap.put("MANCSTZY1", "splitCost"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTZY2", "splitCost"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTZY3", "manCost"); //MANCSTM61	产品线管理人工包

			detailCostTypeMap.put("MANCSTYZ1", "splitCost"); //MANCSTYZ1	外部资源
			
			// 故障-备件成本
			detailCostTypeMap.put("BAKCSTT11", "backCost"); //BAKCSTT11	故障成本-自有
			detailCostTypeMap.put("BAKCSTT12", "backCost"); //BAKCSTT12	故障成本-合作
			detailCostTypeMap.put("BAKCSTM20", "splitCost"); //BAKCSTT21	备件人员人工: 人工成本+激励成本
			//detailCostTypeMap.put("BAKCSTU21", "日常"); //BAKCSTT21	备件人员激励成本
			//detailCostTypeMap.put("BAKCSTM22", "日常"); //BAKCSTM22	单件管控管理人工成本
			//detailCostTypeMap.put("BAKCSTF22", "日常"); //BAKCSTF22	单件管控管理费用成本
			//detailCostTypeMap.put("BAKCSTU22", "日常"); //BAKCSTU22	单件管控管理激励成本
			detailCostTypeMap.put("BAKCSTT41", "backCost"); //BAKCSTT41	高频储备成本
			detailCostTypeMap.put("BAKCSTT42", "backCost"); //BAKCSTT42	项目储备成本
			detailCostTypeMap.put("BAKCSTT51", "splitCost"); //BAKCSTT51	物业、水电成本
			detailCostTypeMap.put("BAKCSTT52", "splitCost"); //BAKCSTT52	包材成本
			detailCostTypeMap.put("BAKCSTT53", "splitCost"); //BAKCSTT53	仓库租赁成本
			detailCostTypeMap.put("BAKCSTT33", "splitCost"); //BAKCSTT33	回收取件运输成本
			detailCostTypeMap.put("BAKCSTT32", "splitCost"); //BAKCSTT32	调拨运输成本
			detailCostTypeMap.put("BAKCSTT31", "splitCost"); //BAKCSTT31	故障件发货运输成本
			detailCostTypeMap.put("BAKCSTT61", "splitCost"); //BAKCSTT61	风险储备金
			detailCostTypeMap.put("BAKCSTBJ1", "splitCost"); //BAKCSTBJ1	交付管理人工包
			detailCostTypeMap.put("BAKCSTBJ2", "splitCost"); //BAKCSTBJ2	风险-人工
			detailCostTypeMap.put("BAKCSTBJ3", "manCost"); //BAKCSTBJ3	产品线管理人工包
		}  else {
			if(manType != null && "A1".equals(manType)) { // 托管-驻场
				detailCostTypeMap.put("MANCSTM11", "hostCost"); //MANCSTM11	一线1级人工包
				detailCostTypeMap.put("MANCSTM12", "hostCost"); //MANCSTM12	一线2级人工包
				detailCostTypeMap.put("MANCSTM13", "hostCost"); //MANCSTM13	一线3级人工包
				detailCostTypeMap.put("MANCSTM14", "hostCost"); //MANCSTM14	一线4级人工包
				detailCostTypeMap.put("MANCSTM15", "hostCost"); //MANCSTM15	一线5级人工包
				detailCostTypeMap.put("MANCSTM16", "hostCost"); //MANCSTM16	一线6级人工包
				detailCostTypeMap.put("MANCSTF11", "hostCost"); //MANCSTF11	一线1级费用包
				detailCostTypeMap.put("MANCSTF12", "hostCost"); //MANCSTF12	一线2级费用包
				detailCostTypeMap.put("MANCSTF13", "hostCost"); //MANCSTF13	一线3级费用包
				detailCostTypeMap.put("MANCSTF14", "hostCost"); //MANCSTF14	一线4级费用包
				detailCostTypeMap.put("MANCSTF15", "hostCost"); //MANCSTF15	一线5级费用包
				detailCostTypeMap.put("MANCSTF16", "hostCost"); //MANCSTF16	一线6级费用包
				detailCostTypeMap.put("MANCSTU11", "hostCost"); //MANCSTU11	一线1级激励包
				detailCostTypeMap.put("MANCSTU12", "hostCost"); //MANCSTU12	一线2级激励包
				detailCostTypeMap.put("MANCSTU13", "hostCost"); //MANCSTU13	一线3级激励包
				detailCostTypeMap.put("MANCSTU14", "hostCost"); //MANCSTU14	一线4级激励包
				detailCostTypeMap.put("MANCSTU15", "hostCost"); //MANCSTU15	一线5级激励包
				detailCostTypeMap.put("MANCSTU16", "hostCost"); //MANCSTU16	一线6级激励包
				
				detailCostTypeMap.put("MANCSTMG3", "splitCost"); //MANCSTMG3	PM3级人工包
				detailCostTypeMap.put("MANCSTFG3", "splitCost"); //MANCSTFG3	PM3级费用包
				detailCostTypeMap.put("MANCSTUG3", "splitCost"); //MANCSTUG3	PM3级激励包
				detailCostTypeMap.put("MANCSTMG4", "splitCost"); //MANCSTMG4	PM4级人工包
				detailCostTypeMap.put("MANCSTFG4", "splitCost"); //MANCSTFG4	PM4级费用包
				detailCostTypeMap.put("MANCSTUG4", "splitCost"); //MANCSTUG4	PM4级激励包
				detailCostTypeMap.put("MANCSTMG5", "splitCost"); //MANCSTMG5	PM5级人工包
				detailCostTypeMap.put("MANCSTFG5", "splitCost"); //MANCSTFG5	PM5级费用包
				detailCostTypeMap.put("MANCSTUG5", "splitCost"); //MANCSTUG5	PM5级激励包
				
				detailCostTypeMap.put("MANCSTTF1", "hostCost"); //MANCSTTF1	一线1级差旅费
				detailCostTypeMap.put("MANCSTTF2", "hostCost"); //MANCSTTF2	一线2级差旅费
				detailCostTypeMap.put("MANCSTTF3", "hostCost"); //MANCSTTF3	一线3级差旅费
				detailCostTypeMap.put("MANCSTTF4", "hostCost"); //MANCSTTF4	一线4级差旅费
				detailCostTypeMap.put("MANCSTTF5", "hostCost"); //MANCSTTF5	一线5级差旅费
				detailCostTypeMap.put("MANCSTTF6", "hostCost"); //MANCSTTF6	一线6级差旅费
				
				detailCostTypeMap.put("MANCSTMZ1", "hostCost"); //MANCSTMZ1	一线1级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ2", "hostCost"); //MANCSTMZ2	一线2级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ3", "hostCost"); //MANCSTMZ3	一线3级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ4", "hostCost"); //MANCSTMZ4	一线4级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ5", "hostCost"); //MANCSTMZ5	一线5级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ6", "hostCost"); //MANCSTMZ6	一线6级人工驻场折减
				detailCostTypeMap.put("MANCSTFZ1", "hostCost"); //MANCSTFZ1	一线1级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ2", "hostCost"); //MANCSTFZ2	一线2级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ3", "hostCost"); //MANCSTFZ3	一线3级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ4", "hostCost"); //MANCSTFZ4	一线4级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ5", "hostCost"); //MANCSTFZ5	一线5级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ6", "hostCost"); //MANCSTFZ6	一线6级费用驻场折减
				detailCostTypeMap.put("MANCSTUZ1", "hostCost"); //MANCSTUZ1	一线1级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ2", "hostCost"); //MANCSTUZ2	一线2级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ3", "hostCost"); //MANCSTUZ3	一线3级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ4", "hostCost"); //MANCSTUZ4	一线4级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ5", "hostCost"); //MANCSTUZ5	一线5级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ6", "hostCost"); //MANCSTUZ6	一线6级激励驻场折减
				
				detailCostTypeMap.put("MANCSTZC1", "splitCost"); //MANCSTM31	交付管理人工包
				detailCostTypeMap.put("MANCSTZC2", "splitCost"); //MANCSTM51	风险-人工
				detailCostTypeMap.put("MANCSTZC3", "manCost"); //MANCSTM61	产品线管理人工包
				detailCostTypeMap.put("MANCSTGL1", "splitCost"); //MANCSTM31	交付管理人工包
				detailCostTypeMap.put("MANCSTGL2", "splitCost"); //MANCSTM51	风险-人工
				detailCostTypeMap.put("MANCSTGL3", "manCost"); //MANCSTM61	产品线管理人工包
				detailCostTypeMap.put("MANCSTZJ1", "splitCost"); //MANCSTM31	交付管理人工包
				detailCostTypeMap.put("MANCSTZJ2", "splitCost"); //MANCSTM51	风险-人工
				detailCostTypeMap.put("MANCSTZJ3", "manCost"); //MANCSTM61	产品线管理人工包
				
			} else { // 正编-驻场
				detailCostTypeMap.put("MANCSTM11", "manCost"); //MANCSTM11	一线1级人工包
				detailCostTypeMap.put("MANCSTM12", "manCost"); //MANCSTM12	一线2级人工包
				detailCostTypeMap.put("MANCSTM13", "manCost"); //MANCSTM13	一线3级人工包
				detailCostTypeMap.put("MANCSTM14", "manCost"); //MANCSTM14	一线4级人工包
				detailCostTypeMap.put("MANCSTM15", "manCost"); //MANCSTM15	一线5级人工包
				detailCostTypeMap.put("MANCSTM16", "manCost"); //MANCSTM16	一线6级人工包
				detailCostTypeMap.put("MANCSTF11", "manCost"); //MANCSTF11	一线1级费用包
				detailCostTypeMap.put("MANCSTF12", "manCost"); //MANCSTF12	一线2级费用包
				detailCostTypeMap.put("MANCSTF13", "manCost"); //MANCSTF13	一线3级费用包
				detailCostTypeMap.put("MANCSTF14", "manCost"); //MANCSTF14	一线4级费用包
				detailCostTypeMap.put("MANCSTF15", "manCost"); //MANCSTF15	一线5级费用包
				detailCostTypeMap.put("MANCSTF16", "manCost"); //MANCSTF16	一线6级费用包
				detailCostTypeMap.put("MANCSTU11", "urgeCost"); //MANCSTU11	一线1级激励包
				detailCostTypeMap.put("MANCSTU12", "urgeCost"); //MANCSTU12	一线2级激励包
				detailCostTypeMap.put("MANCSTU13", "urgeCost"); //MANCSTU13	一线3级激励包
				detailCostTypeMap.put("MANCSTU14", "urgeCost"); //MANCSTU14	一线4级激励包
				detailCostTypeMap.put("MANCSTU15", "urgeCost"); //MANCSTU15	一线5级激励包
				detailCostTypeMap.put("MANCSTU16", "urgeCost"); //MANCSTU16	一线6级激励包
				
				detailCostTypeMap.put("MANCSTMG3", "splitCost"); //MANCSTMG3	PM3级人工包
				detailCostTypeMap.put("MANCSTFG3", "splitCost"); //MANCSTFG3	PM3级费用包
				detailCostTypeMap.put("MANCSTUG3", "splitCost"); //MANCSTUG3	PM3级激励包
				detailCostTypeMap.put("MANCSTMG4", "splitCost"); //MANCSTMG4	PM4级人工包
				detailCostTypeMap.put("MANCSTFG4", "splitCost"); //MANCSTFG4	PM4级费用包
				detailCostTypeMap.put("MANCSTUG4", "splitCost"); //MANCSTUG4	PM4级激励包
				detailCostTypeMap.put("MANCSTMG5", "splitCost"); //MANCSTMG5	PM5级人工包
				detailCostTypeMap.put("MANCSTFG5", "splitCost"); //MANCSTFG5	PM5级费用包
				detailCostTypeMap.put("MANCSTUG5", "splitCost"); //MANCSTUG5	PM5级激励包
				
				detailCostTypeMap.put("MANCSTTF1", "manCost"); //MANCSTTF1	一线1级差旅费
				detailCostTypeMap.put("MANCSTTF2", "manCost"); //MANCSTTF2	一线2级差旅费
				detailCostTypeMap.put("MANCSTTF3", "manCost"); //MANCSTTF3	一线3级差旅费
				detailCostTypeMap.put("MANCSTTF4", "manCost"); //MANCSTTF4	一线4级差旅费
				detailCostTypeMap.put("MANCSTTF5", "manCost"); //MANCSTTF5	一线5级差旅费
				detailCostTypeMap.put("MANCSTTF6", "manCost"); //MANCSTTF6	一线6级差旅费
				
				detailCostTypeMap.put("MANCSTMZ1", "manCost"); //MANCSTMZ1	一线1级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ2", "manCost"); //MANCSTMZ2	一线2级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ3", "manCost"); //MANCSTMZ3	一线3级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ4", "manCost"); //MANCSTMZ4	一线4级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ5", "manCost"); //MANCSTMZ5	一线5级人工驻场折减
				detailCostTypeMap.put("MANCSTMZ6", "manCost"); //MANCSTMZ6	一线6级人工驻场折减
				detailCostTypeMap.put("MANCSTFZ1", "manCost"); //MANCSTFZ1	一线1级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ2", "manCost"); //MANCSTFZ2	一线2级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ3", "manCost"); //MANCSTFZ3	一线3级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ4", "manCost"); //MANCSTFZ4	一线4级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ5", "manCost"); //MANCSTFZ5	一线5级费用驻场折减
				detailCostTypeMap.put("MANCSTFZ6", "manCost"); //MANCSTFZ6	一线6级费用驻场折减
				detailCostTypeMap.put("MANCSTUZ1", "urgeCost"); //MANCSTUZ1	一线1级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ2", "urgeCost"); //MANCSTUZ2	一线2级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ3", "urgeCost"); //MANCSTUZ3	一线3级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ4", "urgeCost"); //MANCSTUZ4	一线4级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ5", "urgeCost"); //MANCSTUZ5	一线5级激励驻场折减
				detailCostTypeMap.put("MANCSTUZ6", "urgeCost"); //MANCSTUZ6	一线6级激励驻场折减
				
				detailCostTypeMap.put("MANCSTZC1", "splitCost"); //MANCSTM31	交付管理人工包
				detailCostTypeMap.put("MANCSTZC2", "splitCost"); //MANCSTM51	风险-人工
				detailCostTypeMap.put("MANCSTZC3", "manCost"); //MANCSTM61	产品线管理人工包
				detailCostTypeMap.put("MANCSTGL1", "splitCost"); //MANCSTM31	交付管理人工包
				detailCostTypeMap.put("MANCSTGL2", "splitCost"); //MANCSTM51	风险-人工
				detailCostTypeMap.put("MANCSTGL3", "manCost"); //MANCSTM61	产品线管理人工包
				detailCostTypeMap.put("MANCSTZJ1", "splitCost"); //MANCSTM31	交付管理人工包
				detailCostTypeMap.put("MANCSTZJ2", "splitCost"); //MANCSTM51	风险-人工
				detailCostTypeMap.put("MANCSTZJ3", "manCost"); //MANCSTM61	产品线管理人工包
				
			}
		}
		
		return detailCostTypeMap;
	}
	
	/**
	 * 根据资源模型成本对应到订单中
	 * 类属性与清单成本明细对应集合<fileType, measureId>
	 * @return
	 */
	private static Map<String, String> getOrderFileCostMap(String prodId, String manType) {
		Map<String, String> classFileCostMeasureMap = new HashMap<String, String>();
		
		// 非驻场服务
		if(!"RXSA-03-02-05".equals(prodId)) {
			classFileCostMeasureMap.put("splitCost", "MANCSTM11,MANCSTF11,MANCSTU11,MANCSTMZ1,MANCSTFZ1,MANCSTUZ1,MANCSTTF1,"   
					+ "MANCSTM12,MANCSTF12,MANCSTU12,MANCSTMZ2,MANCSTFZ2,MANCSTUZ2,MANCSTTF2,"
					+ "MANCSTM13,MANCSTF13,MANCSTU13,MANCSTMZ3,MANCSTFZ3,MANCSTUZ3,MANCSTTF3,"
					+ "MANCSTM14,MANCSTF14,MANCSTU14,MANCSTMZ4,MANCSTFZ4,MANCSTUZ4,MANCSTTF4,"
					+ "MANCSTM15,MANCSTF15,MANCSTU15,MANCSTMZ5,MANCSTFZ5,MANCSTUZ5,MANCSTTF5,"
					+ "MANCSTM16,MANCSTF16,MANCSTU16,MANCSTMZ6,MANCSTFZ6,MANCSTUZ6,MANCSTTF6,"   // 人工-一线
					+ "CASECTW11,"   // 人工-外部资源
					+ "MANCSTMG3,MANCSTFG3,MANCSTUG3,MANCSTMG4,MANCSTFG4,MANCSTUG4,MANCSTMG5,MANCSTFG5,MANCSTUG5,"  // 人工-PM
					+ "MANCSTGZ5,"   // 人工-二线专家
					+ "MANCSTGZ4,"   // 人工-CMO
					+ "BAKCSTM20,"   // 备件-备件人工
					+ "BAKCSTT51,BAKCSTT52,BAKCSTT53,BAKCSTT31,BAKCSTT32,BAKCSTT33,BAKCSTT61,"   // 备件-备件运作
					+ "MANCSTGZ1,MANCSTXJ1,MANCSTZC1,MANCSTZJ1,MANCSTGL1,MANCSTZY1,"   // 人工-交付管理
					+ "MANCSTGZ2,MANCSTXJ2,MANCSTZC2,MANCSTZJ2,MANCSTGL2,MANCSTZY2");    // 人工-风险
					
			classFileCostMeasureMap.put("manCost", "MANCSTGZ3,MANCSTXJ3,MANCSTZC3,MANCSTZJ3,MANCSTGL3,MANCSTZY3");	// 人工-产品管理
			classFileCostMeasureMap.put("backCost", "BAKCSTT11,BAKCSTT12,BAKCSTT41,BAKCSTT42");	// 备件-备件
			
		} else {
			if("A1".equals(manType)) { // 托管-驻场
				classFileCostMeasureMap.put("splitCost", "MANCSTMG3,MANCSTFG3,MANCSTUG3,MANCSTMG4,MANCSTFG4,MANCSTUG4,MANCSTMG5,MANCSTFG5,MANCSTUG5,"  // 人工-PM
						+ "MANCSTGZ1,MANCSTXJ1,MANCSTZC1,MANCSTZJ1,MANCSTGL1,MANCSTZY1,"   // 人工-交付管理
						+ "MANCSTGZ2,MANCSTXJ2,MANCSTZC2,MANCSTZJ2,MANCSTGL2,MANCSTZY2");    // 人工-风险
						
				classFileCostMeasureMap.put("manCost", "MANCSTGZ3,MANCSTXJ3,MANCSTZC3,MANCSTZJ3,MANCSTGL3,MANCSTZY3");	// 人工-产品管理
				classFileCostMeasureMap.put("hostCost", "MANCSTM11,MANCSTF11,MANCSTU11,MANCSTMZ1,MANCSTFZ1,MANCSTUZ1,MANCSTTF1,"   
						+ "MANCSTM12,MANCSTF12,MANCSTU12,MANCSTMZ2,MANCSTFZ2,MANCSTUZ2,MANCSTTF2,"
						+ "MANCSTM13,MANCSTF13,MANCSTU13,MANCSTMZ3,MANCSTFZ3,MANCSTUZ3,MANCSTTF3,"
						+ "MANCSTM14,MANCSTF14,MANCSTU14,MANCSTMZ4,MANCSTFZ4,MANCSTUZ4,MANCSTTF4,"
						+ "MANCSTM15,MANCSTF15,MANCSTU15,MANCSTMZ5,MANCSTFZ5,MANCSTUZ5,MANCSTTF5,"
						+ "MANCSTM16,MANCSTF16,MANCSTU16,MANCSTMZ6,MANCSTFZ6,MANCSTUZ6,MANCSTTF6");   // 人工-一线
			
			} else {  // 正编-驻场
				classFileCostMeasureMap.put("splitCost", "MANCSTMG3,MANCSTFG3,MANCSTUG3,MANCSTMG4,MANCSTFG4,MANCSTUG4,MANCSTMG5,MANCSTFG5,MANCSTUG5,"  // 人工-PM
						+ "MANCSTGZ1,MANCSTXJ1,MANCSTZC1,MANCSTZJ1,MANCSTGL1,MANCSTZY1,"   // 人工-交付管理
						+ "MANCSTGZ2,MANCSTXJ2,MANCSTZC2,MANCSTZJ2,MANCSTGL2,MANCSTZY2");    // 人工-风险
						
				classFileCostMeasureMap.put("manCost", "MANCSTGZ3,MANCSTXJ3,MANCSTZC3,MANCSTZJ3,MANCSTGL3,MANCSTZY3,"   // 人工-产品管理
						+ "MANCSTM11,MANCSTF11,MANCSTMZ1,MANCSTFZ1,MANCSTTF1,"   
						+ "MANCSTM12,MANCSTF12,MANCSTMZ2,MANCSTFZ2,MANCSTTF2,"
						+ "MANCSTM13,MANCSTF13,MANCSTMZ3,MANCSTFZ3,MANCSTTF3,"
						+ "MANCSTM14,MANCSTF14,MANCSTMZ4,MANCSTFZ4,MANCSTTF4,"
						+ "MANCSTM15,MANCSTF15,MANCSTMZ5,MANCSTFZ5,MANCSTTF5,"
						+ "MANCSTM16,MANCSTF16,MANCSTMZ6,MANCSTFZ6,MANCSTTF6");   // 人工-一线
				classFileCostMeasureMap.put("urgeCost", "MANCSTU11,MANCSTUZ1,MANCSTU12,MANCSTUZ2,MANCSTU13,"
						+ "MANCSTUZ3,MANCSTU14,MANCSTUZ4,MANCSTU15,MANCSTUZ5,MANCSTU16,MANCSTUZ6");	// 人工-一线
			}
		}
		
		return classFileCostMeasureMap;
	}
	
	public CstNewOrderCostInfo get(String id) {
		return super.get(id);
	}
	
	public List<CstNewOrderCostInfo> findList(CstNewOrderCostInfo cstNewOrderCostInfo) {
		return super.findList(cstNewOrderCostInfo);
	}
	
	public Page<CstNewOrderCostInfo> findPage(Page<CstNewOrderCostInfo> page, CstNewOrderCostInfo cstNewOrderCostInfo) {
		return super.findPage(page, cstNewOrderCostInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CstNewOrderCostInfo cstNewOrderCostInfo) {
		super.save(cstNewOrderCostInfo);
	}
	
	@Transactional(readOnly = false)
	public void addDetailbatch(List<CstNewOrderCostInfo> cstOrderCostInfoList) {
		cstNewOrderCostInfoDao.addDetailBatch(cstOrderCostInfoList);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstNewOrderCostInfo cstNewOrderCostInfo) {
		super.delete(cstNewOrderCostInfo);
	}
	
}