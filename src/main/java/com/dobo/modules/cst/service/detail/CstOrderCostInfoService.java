/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.detail;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.IdGen;
import com.dobo.common.utils.Reflections;
import com.dobo.modules.cop.entity.detail.CopCaseDetail;
import com.dobo.modules.cop.entity.detail.CopCaseDetailPrice;
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.calplugins.cost.impl.CheckManCostImpl;
import com.dobo.modules.cst.calplugins.cost.impl.CheckManCostImpl.CheckDetailSplit;
import com.dobo.modules.cst.calplugins.price.CalPriceService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.dao.detail.CstOrderCostInfoDao;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.cases.CstCaseDetailInfoBo;
import com.dobo.modules.cst.entity.detail.CstOrderCostInfo;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.dobo.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 订单成本明细Service
 * @author admin
 * @version 2017-01-23
 */
@Service
@Transactional(readOnly = true)
public class CstOrderCostInfoService extends CrudService<CstOrderCostInfoDao, CstOrderCostInfo> {

	@Autowired
	CstOrderCostInfoDao cstOrderCostInfoDao;
	
	/**
	 * 计算top单次、先行支持服务清单进行报价
	 * @param ccdList
	 * @return
	 */
	public static Map<String, List<CopCaseDetailPrice>> getCalculateCaseDetailPrice(List<CopCaseDetail> ccdList, String prodIds) {
		
		Map<String, Map<String, ProdDetailInfo>> prodDetailInfoMap = new TreeMap<String, Map<String, ProdDetailInfo>>();
				
		for(CopCaseDetail ccd : ccdList) {
			if(!prodDetailInfoMap.keySet().contains(prodIds)) {
				Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
				prodDetailInfoMap.put(prodIds, pdiList);
			}
			ProdDetailInfo pdi = new ProdDetailInfo();
			pdi.setDetailId(ccd.getPriceNum());
			pdi.setDcPrjId(ccd.getCaseId());
			pdi.setOrderId(ccd.getOnceNum());
			
			Map<String, String> inParaMap = new HashMap<String, String>();

			inParaMap.put("serid", ccd.getId());
			inParaMap.put("priceType", ccd.getPriceType());
			inParaMap.put("serviceType", ccd.getServiceType());
			if("1".equals(ccd.getPriceType())) { // 按人员报价
				inParaMap.put("roleId", ccd.getManRoleId());
				inParaMap.put("engineerLevel", ccd.getManEngineerLevel());
				inParaMap.put("resourceType", ccd.getManResourceType());
				inParaMap.put("foreignPrice", (ccd.getManPrice() == null ? 0 : ccd.getManPrice()) +"");
				inParaMap.put("travelPrice", (ccd.getManTravelPrice() == null ? 0 : ccd.getManTravelPrice()) +"");
				inParaMap.put("workLoad", (ccd.getManWorkload() == null ? 0 : ccd.getManWorkload()) +"");
			} else if("2".equals(ccd.getPriceType())) { // 按备件报价
				inParaMap.put("partPn", ccd.getPartPn());
				inParaMap.put("amount", (ccd.getPartAmount() == null ? 0 : ccd.getPartAmount()) +"");
				inParaMap.put("price", (ccd.getPartPrice() == null ? 0 : ccd.getPartPrice()) +"");
				inParaMap.put("travelPrice", (ccd.getPartDeliveryPrice() == null ? 0 : ccd.getPartDeliveryPrice()) +"");
			}
			
			pdi.setInParaMap(inParaMap);
			
			prodDetailInfoMap.get(prodIds).put(pdi.getDetailId(), pdi);
		}
		
		// 返回成本明细集合 <prodId, <detailID, costInfo>>
		Map<String, Map<String, DetailCostInfo>> proddciMap = new HashMap<String, Map<String,DetailCostInfo>>();
		for(String prodId : prodDetailInfoMap.keySet()) {
			//System.out.println(prodId+":"+prodDetailInfoMap.get(prodId).size());
			//System.out.println(prodDetailInfoMap.get(prodId).get(0).getDcPrjId());
			// 获取服务的模型集合
			List<CstModelProdModuleRel> models = CacheDataUtils.getProdModuleRelMap().get(prodId);
			// 遍历产品成本模型
			for(CstModelProdModuleRel model : models) {
				CalPriceService costService = null;
				try {
					Class<?> costClass = Class.forName(model.getClassName());
					//System.out.println(model.getClassName());
					costService = (CalPriceService)costClass.newInstance();
					
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
		
		Map<String, List<CopCaseDetailPrice>> copCaseDetailPriceMap = new HashMap<String, List<CopCaseDetailPrice>>();
		
		for(String prodId : proddciMap.keySet()) {
			Map<String, ProdDetailInfo> pdiMap = prodDetailInfoMap.get(prodId);
			List<CopCaseDetailPrice> copCaseDetailPricelist = new ArrayList<CopCaseDetailPrice>();
			Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
			for(String detailId : dcisMap.keySet()) {
				ProdDetailInfo pdi = pdiMap.get(detailId);
				DetailCostInfo detailCostInfo = dcisMap.get(detailId);
				
				CopCaseDetailPrice ccdp = new CopCaseDetailPrice();
				// ccdp.setCaseDetailId(detailId);
				ccdp.setCaseDetailId(pdi.getInParaMap().get("serid"));
				ccdp.setPriceNum(detailId);
				ccdp.setOnceNum(pdi.getOrderId());
				ccdp.setCaseId(pdi.getDcPrjId());
				ccdp.setPriceType(pdi.getInParaMap().get("priceType"));
				ccdp.setWbmCostPrepay(Double.valueOf(detailCostInfo.getCostInfoMap().get("MANPRIM1")));
				ccdp.setWbmCostPrepayTravel(Double.valueOf(detailCostInfo.getCostInfoMap().get("MANPRIT1")));
				ccdp.setWbmCostFt(Double.valueOf(detailCostInfo.getCostInfoMap().get("MANPRIM2")));
				ccdp.setWbmCostFtTravel(Double.valueOf(detailCostInfo.getCostInfoMap().get("MANPRIT2")));
				ccdp.setWbmCostFtToProject(Double.valueOf(detailCostInfo.getCostInfoMap().get("MANPRIM3")));
				ccdp.setWbmCostFtToProjectTravel(Double.valueOf(detailCostInfo.getCostInfoMap().get("MANPRIT3")));
				copCaseDetailPricelist.add(ccdp);
			}
			copCaseDetailPriceMap.put(prodId, copCaseDetailPricelist);
		}

		
		return copCaseDetailPriceMap;
	}

	/**
	 * 计算top单次、先行支持服务清单的成本
	 * @param ccdiList top报价清单
	 * @param payType 1：预付费:2：单次:3：单次入项目。
	 * @param dcPrjId 支付项目号。
	 * @param sameDcPrjIdMap 多个项目支付的单次报价集合
	 * @return
	 */
	public static Map<String, List<CstOrderCostInfo>> getCalculateCaseDetailCost(List<CopCaseDetail> ccdiList, String payType, String dcPrjId, Map<String, List<CopSalesUseDetail>> sameDcPrjIdMap) {
		
		Map<String, List<CstOrderCostInfo>> cstDetailCostInfoMap = new HashMap<String, List<CstOrderCostInfo>>();
		Map<String, Map<String, ProdDetailInfo>> prodDetailInfoMap = new TreeMap<String, Map<String, ProdDetailInfo>>();
		
		CstCaseDetailInfoBo ccdiBo = new CstCaseDetailInfoBo();
		Map<String, String> inParaMap = new HashMap<String, String>();
		ProdDetailInfo pdi = new ProdDetailInfo();
		String prodIds = "RXSA-CASE-SUPPORT";
		String onceNum = "";
		String caseConfirmId = "";
		for(CopCaseDetail ccd : ccdiList) {
			onceNum = ccd.getOnceNum();
			caseConfirmId = ccd.getCaseConfirmId();
			if(!prodDetailInfoMap.keySet().contains(prodIds)) {
				Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
				prodDetailInfoMap.put(prodIds, pdiList);
			}
			if("1".equals(ccd.getPriceType())) { // 报价类型：1 人员 2 备件
				if("1".equals(ccd.getServiceType())) { // 服务类型：1故障
					// 根据角色：3一线、4二线、2-PM 判断现场（1线）、远程（2线）
					if("3".equals(ccd.getManRoleId())) {
						inParaMap.put("prodId", prodIds+"1");
						if("0".equals(ccd.getManResourceType())) { // 0 内部资源
							/**一线：   初级---2级    中级---3级   高级---4级   专家---6级  */
							if("1".equals(ccd.getManEngineerLevel())) { // 1 初级工程师
								ccdiBo.setGzLine1Level2(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine1Level2()));
								ccdiBo.setGztfLine1Level2(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getGztfLine1Level2()));
							} else if("2".equals(ccd.getManEngineerLevel())) { // 2 中级工程师
								ccdiBo.setGzLine1Level3(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine1Level3()));
								ccdiBo.setGztfLine1Level3(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getGztfLine1Level3()));
							} else if("3".equals(ccd.getManEngineerLevel())) { // 3 高级工程师
								ccdiBo.setGzLine1Level4(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine1Level4()));
								ccdiBo.setGztfLine1Level4(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getGztfLine1Level4()));
							} else if("4".equals(ccd.getManEngineerLevel())) { // 4专家工程师
								ccdiBo.setGzLine1Level6(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine1Level6()));
								ccdiBo.setGztfLine1Level6(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getGztfLine1Level6()));
							} 
						} else if("1".equals(ccd.getManResourceType())) { // 1 外部资源
							ccdiBo.setGzxcwy(BigDecimal.valueOf(ccd.getManPrice()).add(ccdiBo.getGzxcwy()));
						}
					} else if("4".equals(ccd.getManRoleId())) {
						inParaMap.put("prodId", prodIds+"2");
						if("0".equals(ccd.getManResourceType())) { // 0 内部资源
							/**二线： 中级---4级   高级---5级   专家---6级  */
							if("2".equals(ccd.getManEngineerLevel())) { // 2 中级工程师
								ccdiBo.setGzLine2Level4(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine2Level4()));
							} else if("3".equals(ccd.getManEngineerLevel())) { // 3 高级工程师
								ccdiBo.setGzLine2Level5(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine2Level5()));
							} else if("4".equals(ccd.getManEngineerLevel())) { // 4专家工程师
								ccdiBo.setGzLine2Level6(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getGzLine2Level6()));
							} 
						} else if("1".equals(ccd.getManResourceType())) { // 1 外部资源
							ccdiBo.setGzycwy(BigDecimal.valueOf(ccd.getManPrice()).add(ccdiBo.getGzycwy()));
						}
					}
				} else if("3".equals(ccd.getServiceType())) { // 服务类型：3巡检
					inParaMap.put("prodId", prodIds+"3");
					if("0".equals(ccd.getManResourceType())) { // 0 内部资源
						/**一线：   初级---2级    中级---3级   高级---4级   专家---6级  */
						if("1".equals(ccd.getManEngineerLevel())) { // 1 初级工程师
							ccdiBo.setXjLine1Level2(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getXjLine1Level2()));
							ccdiBo.setXjtfLine1Level2(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getXjtfLine1Level2()));
						} else if("2".equals(ccd.getManEngineerLevel())) { // 2 中级工程师
							ccdiBo.setXjLine1Level3(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getXjLine1Level3()));
							ccdiBo.setXjtfLine1Level3(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getXjtfLine1Level3()));
						} else if("3".equals(ccd.getManEngineerLevel())) { // 3 高级工程师
							ccdiBo.setXjLine1Level4(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getXjLine1Level4()));
							ccdiBo.setXjtfLine1Level4(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getXjtfLine1Level4()));
						} else if("4".equals(ccd.getManEngineerLevel())) { // 4专家工程师
							ccdiBo.setXjLine1Level6(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getXjLine1Level6()));
							ccdiBo.setXjtfLine1Level6(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getXjtfLine1Level6()));
						} 
					} else if("1".equals(ccd.getManResourceType())) { // 1 外部资源
						ccdiBo.setXjwy(BigDecimal.valueOf(ccd.getManPrice()).add(ccdiBo.getXjwy()));
					}
				} else if("5".equals(ccd.getServiceType())) { // 服务类型：5非故障技术支持
					inParaMap.put("prodId", prodIds+"4");
					if("0".equals(ccd.getManResourceType())) { // 0 内部资源
						/**一线：   初级---2级    中级---3级   高级---4级   专家---6级  */
						if("1".equals(ccd.getManEngineerLevel())) { // 1 初级工程师
							ccdiBo.setFgzzcLine1Level2(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getFgzzcLine1Level2()));
							ccdiBo.setFgzzctfLine1Level2(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getFgzzctfLine1Level2()));
						} else if("2".equals(ccd.getManEngineerLevel())) { // 2 中级工程师
							ccdiBo.setFgzzcLine1Level3(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getFgzzcLine1Level3()));
							ccdiBo.setFgzzctfLine1Level3(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getFgzzctfLine1Level3()));
						} else if("3".equals(ccd.getManEngineerLevel())) { // 3 高级工程师
							ccdiBo.setFgzzcLine1Level4(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getFgzzcLine1Level4()));
							ccdiBo.setFgzzctfLine1Level4(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getFgzzctfLine1Level4()));
						} else if("4".equals(ccd.getManEngineerLevel())) { // 4专家工程师
							ccdiBo.setFgzzcLine1Level6(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getFgzzcLine1Level6()));
							ccdiBo.setFgzzctfLine1Level6(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getFgzzctfLine1Level6()));
						} 
					} else if("1".equals(ccd.getManResourceType())) { // 1 外部资源
						ccdiBo.setFgzzcwy(BigDecimal.valueOf(ccd.getManPrice()).add(ccdiBo.getFgzzcwy()));
					}
				} else if("6".equals(ccd.getServiceType())) { // 服务类型：6专业化服务
					inParaMap.put("prodId", prodIds+"5");
					if("0".equals(ccd.getManResourceType())) { // 0 内部资源
						// 根据角色：3一线、4二线、2-PM
						if("3".equals(ccd.getManRoleId())) {
							/**一线：   初级---2级    中级---3级   高级---4级   专家---6级  */
							if("1".equals(ccd.getManEngineerLevel())) { // 1 初级工程师
								ccdiBo.setZyhLine1Level2(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhLine1Level2()));
								ccdiBo.setZyhtfLine1Level2(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getZyhtfLine1Level2()));
							} else if("2".equals(ccd.getManEngineerLevel())) { // 2 中级工程师
								ccdiBo.setZyhLine1Level3(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhLine1Level3()));
								ccdiBo.setZyhtfLine1Level3(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getZyhtfLine1Level3()));
							} else if("3".equals(ccd.getManEngineerLevel())) { // 3 高级工程师
								ccdiBo.setZyhLine1Level4(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhLine1Level4()));
								ccdiBo.setZyhtfLine1Level4(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getZyhtfLine1Level4()));
							} else if("4".equals(ccd.getManEngineerLevel())) { // 4专家工程师
								ccdiBo.setZyhLine1Level6(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhLine1Level6()));
								ccdiBo.setZyhtfLine1Level6(BigDecimal.valueOf(ccd.getManTravelPrice()).add(ccdiBo.getZyhtfLine1Level6()));
							} 
						} else if("2".equals(ccd.getManRoleId())) {
							/**PM: 初级---PM3级    中级---PM4级   高级---PM5级   */
							if("1".equals(ccd.getManEngineerLevel())) { // 1 初级工程师
								ccdiBo.setZyhPmLevel3(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhPmLevel3()));
							} else if("2".equals(ccd.getManEngineerLevel())) { // 2 中级工程师
								ccdiBo.setZyhPmLevel4(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhPmLevel4()));
							} else if("3".equals(ccd.getManEngineerLevel())) { // 3 高级工程师
								ccdiBo.setZyhPmLevel5(BigDecimal.valueOf(ccd.getManWorkload()).add(ccdiBo.getZyhPmLevel5()));
							}
						}
					} else if("1".equals(ccd.getManResourceType())) { // 1 外部资源
						ccdiBo.setZyhwy(BigDecimal.valueOf(ccd.getManPrice()).add(ccdiBo.getZyhwy()));
					}
				}
			} else if("2".equals(ccd.getPriceType())) {
				ccdiBo.setBjPrice(BigDecimal.valueOf(ccd.getPartPrice()*ccd.getPartAmount()).add(ccdiBo.getBjPrice()));
				ccdiBo.setBjTravelPrice(BigDecimal.valueOf(ccd.getPartDeliveryPrice()).add(ccdiBo.getBjTravelPrice()));
			}
			
			pdi.setDetailId(ccd.getOnceCode());
			pdi.setDcPrjId(ccd.getOnceCode());
			pdi.setOrderId(ccd.getOnceNum());
			pdi.setServiceBegin(ccd.getCreateDate());
			pdi.setServiceEnd(ccd.getCreateDate());
			
			ccdiBo.setOnceNum(ccd.getOnceNum());
			ccdiBo.setCaseId(ccd.getCaseId());
			ccdiBo.setPriceType(ccd.getPriceType());
			
			inParaMap.put("serviceType", ccd.getServiceType());
			// 1：预付费:2：单次:3：单次入项目。
			if("1".equals(payType)) {
				inParaMap.put("payScaleName", "prepayScale");
			} else if("2".equals(payType)) {
				inParaMap.put("payScaleName", "ftScale");
			} else if("3".equals(payType)) {
				inParaMap.put("payScaleName", "ftToProjectScale");
			}
			
			// 2017-8-31 单次报价上线前历史数据状态
			inParaMap.put("preStatus", ccd.getPreStatus());
		}
		
		for(String field : Reflections.getFieldNames(ccdiBo)) {
			inParaMap.put(field, Reflections.getFieldValue(ccdiBo, field).toString());
		}
		
		inParaMap.put("payType", payType);
		pdi.setInParaMap(inParaMap);
		
		prodDetailInfoMap.get(prodIds).put(pdi.getDetailId(), pdi);
		
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
		//将清单成本明细写入表中
		for(String prodId : pdciMap.keySet()) {
			List<CstOrderCostInfo> cdciList = Lists.newArrayList();
			
			for(DetailCostInfo dci : pdciMap.get(prodId)) {
				if(dci.getCostInfoMap() == null) continue;
				Map<String, CstOrderCostInfo> cdciMap = new HashMap<String, CstOrderCostInfo>();
				//CstOrderCostInfo
				for(String measureId : dci.getCostInfoMap().keySet()) {
					String outkey = dci.getDetailId()+getCostTypeTogetherMap(prodId).get(measureId);
					if(cdciMap.get(outkey) == null) {
						CstOrderCostInfo cdci = new CstOrderCostInfo();
						ProdDetailInfo prodDetailInfo = prodDetailInfoMap.get(prodId).get(dci.getDetailId());
						cdci.setId(IdGen.uuid());
						cdci.setProdId(prodId);
						cdci.setOrderId(prodDetailInfo.getOrderId());
						cdci.setPdId(dci.getDetailId());
						cdci.setDcPrjId(dcPrjId);
						
						if(prodDetailInfo != null) {
							cdci.setResourceId("");
							cdci.setMfrName("");
							cdci.setResourceName("");
							cdci.setModelgroupName("");
							cdci.setEquipTypeName("");
							cdci.setBeginDate(prodDetailInfo.getServiceBegin());
							cdci.setEndDate(prodDetailInfo.getServiceEnd());
						}

						cdci.setKeyId(getCostTypeTogetherMap(prodId).get(measureId));
						cdci.setStatus("A0");
						cdci.setCreateBy(user);
						cdci.setCreateDate(date);
						cdci.setUpdateBy(user);
						cdci.setUpdateDate(date);
						cdci.setDelFlag("0");
						cdci.setRemarks(remarks);
						cdciMap.put(outkey, cdci);
					}
					
					CstOrderCostInfo cdci = cdciMap.get(outkey);
					
					for(String key : getClassFileCostMap(prodId).keySet()) {
						if(getClassFileCostMap(prodId).get(key).contains(measureId)) {
							Reflections.setFieldValue(cdci, key, new BigDecimal(dci.getCostInfoMap().get(measureId)));
						}
					}

				}
				
				for(String key : cdciMap.keySet()) {
					cdciList.add(cdciMap.get(key));
				}
			}

			// 单次case-预付费，多个项目支付的需要拆分
			if("1".equals(payType) && sameDcPrjIdMap.keySet().contains(caseConfirmId)) {
				List<CstOrderCostInfo> cdciNewList = new ArrayList<CstOrderCostInfo>(); 
				for(CopSalesUseDetail csud : sameDcPrjIdMap.get(caseConfirmId)) {
					for(CstOrderCostInfo coci : cdciList) {
						CstOrderCostInfo cociNew = new CstOrderCostInfo();
						
						try {
							PropertyUtils.copyProperties(cociNew, coci);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
						
						cociNew.setId(IdGen.uuid());
						cociNew.setDcPrjId(csud.getDcPrjId());
						if(cociNew.getManLine1level2() != null)
							cociNew.setManLine1level2(cociNew.getManLine1level2().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level3() != null)
							cociNew.setManLine1level3(cociNew.getManLine1level3().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level4() != null)
							cociNew.setManLine1level4(cociNew.getManLine1level4().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level6() != null)
							cociNew.setManLine1level6(cociNew.getManLine1level6().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level2Travl() != null)
							cociNew.setManLine1level2Travl(cociNew.getManLine1level2Travl().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level3Travl() != null)
							cociNew.setManLine1level3Travl(cociNew.getManLine1level3Travl().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level4Travl() != null)
							cociNew.setManLine1level4Travl(cociNew.getManLine1level4Travl().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine1level6Travl() != null)
							cociNew.setManLine1level6Travl(cociNew.getManLine1level6Travl().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManOutFee() != null)
							cociNew.setManOutFee(cociNew.getManOutFee().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine2level4() != null)
							cociNew.setManLine2level4(cociNew.getManLine2level4().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine2level5() != null)
							cociNew.setManLine2level5(cociNew.getManLine2level5().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManLine2level6() != null)
							cociNew.setManLine2level6(cociNew.getManLine2level6().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManCmo() != null)
							cociNew.setManCmo(cociNew.getManCmo().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManZyg() != null)
							cociNew.setManZyg(cociNew.getManZyg().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManZkgl() != null)
							cociNew.setManZkgl(cociNew.getManZkgl().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManQygl() != null)
							cociNew.setManQygl(cociNew.getManQygl().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManPm3() != null)
							cociNew.setManPm3(cociNew.getManPm3().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManPm4() != null)
							cociNew.setManPm4(cociNew.getManPm4().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManPm5() != null)
							cociNew.setManPm5(cociNew.getManPm5().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getBakGzcbZy() != null)
							cociNew.setBakGzcbZy(cociNew.getBakGzcbZy().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getBakGzjfhyscb() != null)
							cociNew.setBakGzjfhyscb(cociNew.getBakGzjfhyscb().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getManagerCost() != null)
							cociNew.setManagerCost(cociNew.getManagerCost().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getToolCost() != null)
							cociNew.setToolCost(cociNew.getToolCost().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
						if(cociNew.getRiskCost() != null)
							cociNew.setRiskCost(cociNew.getRiskCost().multiply(BigDecimal.valueOf(csud.getCostScale())).setScale(8, BigDecimal.ROUND_HALF_UP));
					
						cdciNewList.add(cociNew);
					}
				}
				
				cstDetailCostInfoMap.put(onceNum, cdciNewList);
			} else {
				cstDetailCostInfoMap.put(onceNum, cdciList);
			}
			
		}
		
		return cstDetailCostInfoMap;
	}

	/**
	 * 计算订单服务产品对应清单的成本
	 * @param codiList
	 * @return <prodId, 模型成本list>
	 */
	public static Map<String, List<CstOrderCostInfo>> getCalculateDetailCost(List<CstOrderDetailInfo> codiList, boolean flag) {
		
		String orderId = codiList.get(0).getOrderId();
		String dcPrjId = codiList.get(0).getDcPrjId();
		Map<String, List<CstOrderCostInfo>> cstDetailCostInfoMap = new HashMap<String, List<CstOrderCostInfo>>();
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
			//inParaMap.put("prodServiceModel", cod.getProdServiceModel());
			//inParaMap.put("finalManCost", cod.getFinalManCost());
			//inParaMap.put("finalBackCost", cod.getFinalBackCost());
			//inParaMap.put("finalRiskAbilityCost", cod.getFinalRiskAbilityCost());
			//inParaMap.put("finalUrgeCost", cod.getFinalUrgeCost());
			
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
		//将清单成本明细写入表中
		for(String prodId : pdciMap.keySet()) {
			List<CstOrderCostInfo> cdciList = Lists.newArrayList();
			
			for(DetailCostInfo dci : pdciMap.get(prodId)) {
				ProdDetailInfo prodDetailInfo = prodDetailInfoMap.get(prodId).get(dci.getDetailId());
				if(dci.getCostInfoMap() == null) continue;
				Map<String, CstOrderCostInfo> cdciMap = new HashMap<String, CstOrderCostInfo>();
				//CstOrderCostInfo
				for(String measureId : dci.getCostInfoMap().keySet()) {
					String outkey = dci.getDetailId()+getCostTypeTogetherMap(prodId).get(measureId);
					if(cdciMap.get(outkey) == null) {
						CstOrderCostInfo cdci = new CstOrderCostInfo();
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
					
					CstOrderCostInfo cdci = cdciMap.get(outkey);
					//  管理    工具    风险 --人工和备件的需要合并处理
					if("MANCSTMM1,MANCSTTT1,MANCSTMR1,BAKCSTMM1,BAKCSTTT1,BAKCSTMR1".contains(measureId)) {
						if("MANCSTMM1".equals(measureId) || "BAKCSTMM1".equals(measureId)) {
							cdci.setManagerCost(cdci.getManagerCost().add(new BigDecimal(dci.getCostInfoMap().get(measureId))));
						} else if("MANCSTTT1".equals(measureId) || "BAKCSTTT1".equals(measureId)) {
							cdci.setToolCost(cdci.getToolCost().add(new BigDecimal(dci.getCostInfoMap().get(measureId))));
						} else if("MANCSTMR1".equals(measureId) || "BAKCSTMR1".equals(measureId)) {
							cdci.setRiskCost(cdci.getRiskCost().add(new BigDecimal(dci.getCostInfoMap().get(measureId))));
						}
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
	 * 拆分订单中巡检对应清单的巡检矩阵
	 * @param codiList
	 * @return
	 */
	public Map<CheckDetailSplit, Map<String, ProdDetailInfo>> getCheckProdDetailMap(List<CstOrderDetailInfo> codiList) {
		Map<CheckDetailSplit, Map<String, ProdDetailInfo>> checkDetailMap = null;

		Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
		String prodId = "RXSA-03-02-02";
		for(CstOrderDetailInfo cod : codiList) {
			if(prodId.equals(cod.getProdMap().get(cod.getProdId()))) {
				ProdDetailInfo pdi = new ProdDetailInfo();
				pdi.setDetailId(cod.getDetailId());
				pdi.setDcPrjId(cod.getDcPrjId());
				pdi.setOrderId(cod.getOrderId());
				pdi.setMfrName(cod.getMfrName());
				pdi.setDetailModel(cod.getDetailModel());
				pdi.setResourceName(cod.getResourceName());
				
				pdi.setServiceBegin(cod.getBeginDate());
				pdi.setServiceEnd(cod.getEndDate());
				
				Map<String, String> inParaMap = new HashMap<String, String>();
				
				inParaMap.put("resourceId", CacheDataUtils.getCstResourceBaseInfoTestMap().get(cod.getMfrName().toUpperCase()+cod.getResourceName().toUpperCase()).getResourceId());
				inParaMap.put("SECTION", CacheDataUtils.getCstManCityParaTestMap().get(cod.getCityId()).getCityId());
				inParaMap.put("amount", cod.getAmount());
				inParaMap.put("SLA", cod.getSlaMap().get(cod.getSlaId()));
				inParaMap.put("BUYPRDMON", cod.getCycle());
				inParaMap.put("BUYCHECKN", cod.getCheckN()+"");
				inParaMap.put("BUYFARCHK", cod.getCheckF()+"");
				inParaMap.put("BUYDEPCHK", cod.getCheckD()+"");
				inParaMap.put("URGENT", cod.getUrgent()+"");
				inParaMap.put("WORKKIND", cod.getWorkkindScale()+"");
				pdi.setInParaMap(inParaMap);
				
				pdiList.put(pdi.getDetailId(), pdi);
			}
			
		}
		
		if(pdiList.size() > 0) {
			checkDetailMap = new HashMap<CheckDetailSplit, Map<String, ProdDetailInfo>>();
			CheckManCostImpl checkManCostImpl = new CheckManCostImpl();
			CheckDetailSplit checkDetailSplit = checkManCostImpl.splitDetailsByDateOrder(pdiList);
			checkDetailMap.put(checkDetailSplit, pdiList);
		}
		
		return checkDetailMap;
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
			
			detailCostTypeMap.put("MANCSTMG3", "人工"); //MANCSTMG3	PM3级人工包
			detailCostTypeMap.put("MANCSTFG3", "费用"); //MANCSTFG3	PM3级费用包
			detailCostTypeMap.put("MANCSTUG3", "激励"); //MANCSTUG3	PM3级激励包
			detailCostTypeMap.put("MANCSTMG4", "人工"); //MANCSTMG4	PM4级人工包
			detailCostTypeMap.put("MANCSTFG4", "费用"); //MANCSTFG4	PM4级费用包
			detailCostTypeMap.put("MANCSTUG4", "激励"); //MANCSTUG4	PM4级激励包
			detailCostTypeMap.put("MANCSTMG5", "人工"); //MANCSTMG5	PM5级人工包
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
			
			detailCostTypeMap.put("MANCSTM21", "日常"); //MANCSTM21	二/三线人工包
			detailCostTypeMap.put("MANCSTM41", "日常"); //MANCSTM41	CMO人工包
			detailCostTypeMap.put("MANCSTM31", "日常"); //MANCSTM31	交付管理人工包
			detailCostTypeMap.put("MANCSTM51", "日常"); //MANCSTM51	风险-人工
			detailCostTypeMap.put("MANCSTM61", "日常"); //MANCSTM61	产品线管理人工包

			detailCostTypeMap.put("MANCSTYZ1", "费用"); //MANCSTYZ1	外部资源
			detailCostTypeMap.put("MANCSTH12", "日常"); //MANCSTH12	一线1级人工成本
			detailCostTypeMap.put("BACKCSTM1", "日常"); //BACKCSTM1	纯备件
			detailCostTypeMap.put("BACKCSTM2", "日常"); //BACKCSTM2	备件运作
			detailCostTypeMap.put("BACKCSTM3", "日常"); //BACKCSTM3	备件人工
			
			// 故障-备件成本
			/*detailCostTypeMap.put("BAKCSTT11", "费用"); //BAKCSTT11	故障成本
			detailCostTypeMap.put("BAKCSTM21", "人工"); //BAKCSTT21	备件人员人工成本
			detailCostTypeMap.put("BAKCSTU21", "激励"); //BAKCSTT21	备件人员激励成本
			detailCostTypeMap.put("BAKCSTM22", "人工"); //BAKCSTM22	单件管控管理人工成本
			detailCostTypeMap.put("BAKCSTF22", "费用"); //BAKCSTF22	单件管控管理费用成本
			detailCostTypeMap.put("BAKCSTU22", "激励"); //BAKCSTU22	单件管控管理激励成本
			detailCostTypeMap.put("BAKCSTT41", "费用"); //BAKCSTT41	高频储备成本
			detailCostTypeMap.put("BAKCSTT42", "费用"); //BAKCSTT42	项目储备成本
			detailCostTypeMap.put("BAKCSTT51", "费用"); //BAKCSTT51	物业、水电成本
			detailCostTypeMap.put("BAKCSTT52", "费用"); //BAKCSTT52	包材成本
			detailCostTypeMap.put("BAKCSTT53", "费用"); //BAKCSTT53	仓库租赁成本
			detailCostTypeMap.put("BAKCSTT33", "费用"); //BAKCSTT33	回收取件运输成本
			detailCostTypeMap.put("BAKCSTT32", "费用"); //BAKCSTT32	调拨运输成本
			detailCostTypeMap.put("BAKCSTT31", "费用"); //BAKCSTT31	故障件发货运输成本
			detailCostTypeMap.put("BAKCSTMM1", "人工"); //BAKCSTMM1	管理
			detailCostTypeMap.put("BAKCSTTT1", "费用"); //BAKCSTTT1	工具
			detailCostTypeMap.put("BAKCSTMR1", "费用"); //BAKCSTMR1	风险
*/		}
		
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
			classFileCostMeasureMap.put("manLine1level1", "MANCSTH11,MANCSTM11,MANCSTF11,MANCSTU11,MANCSTHZ1,MANCSTMZ1,MANCSTFZ1,MANCSTUZ1,MANCSTHT1,MANCSTMT1,MANCSTFT1,MANCSTUT1,MANCSTTF1");	// 人工-一线一级
			classFileCostMeasureMap.put("manLine1level2", "MANCSTH12,MANCSTM12,MANCSTF12,MANCSTU12,MANCSTHZ2,MANCSTMZ2,MANCSTFZ2,MANCSTUZ2,MANCSTHT2,MANCSTMT2,MANCSTFT2,MANCSTUT2,MANCSTTF2");	// 人工-一线二级
			classFileCostMeasureMap.put("manLine1level3", "MANCSTH13,MANCSTM13,MANCSTF13,MANCSTU13,MANCSTHZ3,MANCSTMZ3,MANCSTFZ3,MANCSTUZ3,MANCSTHT3,MANCSTMT3,MANCSTFT3,MANCSTUT3,MANCSTTF3");	// 人工-一线三级
			classFileCostMeasureMap.put("manLine1level4", "MANCSTH14,MANCSTM14,MANCSTF14,MANCSTU14,MANCSTHZ4,MANCSTMZ4,MANCSTFZ4,MANCSTUZ4,MANCSTHT4,MANCSTMT4,MANCSTFT4,MANCSTUT4,MANCSTTF4");	// 人工-一线四级
			classFileCostMeasureMap.put("manLine1level5", "MANCSTH15,MANCSTM15,MANCSTF15,MANCSTU15,MANCSTHZ5,MANCSTMZ5,MANCSTFZ5,MANCSTUZ5,MANCSTHT5,MANCSTMT5,MANCSTFT5,MANCSTUT5,MANCSTTF5");	// 人工-一线五级
			classFileCostMeasureMap.put("manLine1level6", "MANCSTH16,MANCSTM16,MANCSTF16,MANCSTU16,MANCSTHZ6,MANCSTMZ6,MANCSTFZ6,MANCSTUZ6,MANCSTHT6,MANCSTMT6,MANCSTFT6,MANCSTUT6,MANCSTTF6");	// 人工-一线六级
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
			
			classFileCostMeasureMap.put("manLine2Expert", "MANCSTM21");	// 人工-二线专家
			classFileCostMeasureMap.put("manCmo", "MANCSTM41");	// CMO人工包
			classFileCostMeasureMap.put("manDelivery", "MANCSTM31");	// 交付管理人工包
			classFileCostMeasureMap.put("manRisk", "MANCSTM51");	// 风险-人工
			classFileCostMeasureMap.put("manProdLine", "MANCSTM61");	// 产品线管理人工包
			classFileCostMeasureMap.put("bakBackCost", "BACKCSTM1");	// 备件-纯备件成本
			classFileCostMeasureMap.put("bakOperCost", "BACKCSTM2"); //	备件运作
			classFileCostMeasureMap.put("bakManCost", "BACKCSTM3"); //	备件人工
			
			/*classFileCostMeasureMap.put("bakGzcbZy", "BAKCSTT11");	// 备件-故障成本_自有
			classFileCostMeasureMap.put("bakGzcbFb", "BAKCSTT21");	// 备件-故障成本_分包
			classFileCostMeasureMap.put("bakXmcbcb", "BAKCSTT42");	// 备件-项目储备成本
			classFileCostMeasureMap.put("bakGpcbcb", "BAKCSTT41");	// 备件-高频储备成本
			classFileCostMeasureMap.put("bakBjrgcb", "BAKCSTM21,BAKCSTU21");	// 备件-备件人工成本
			classFileCostMeasureMap.put("bakZkgl", "BAKCSTM22,BAKCSTF22,BAKCSTU22");	// 备件-总控管理
			classFileCostMeasureMap.put("bakCkzlcb", "BAKCSTT53");	// 备件-仓库租赁成本
			classFileCostMeasureMap.put("bakBccb", "BAKCSTT52");	// 备件-包材成本
			classFileCostMeasureMap.put("bakHsqjyscb", "BAKCSTT33");	// 备件-回收取件运输成本
			classFileCostMeasureMap.put("bakGzjfhyscb", "BAKCSTT31");	// 备件-故障件发货运输成本
			classFileCostMeasureMap.put("bakWysdcb", "BAKCSTT51");	// 备件-物业、水电成本
			classFileCostMeasureMap.put("bakDbyscb", "BAKCSTT32");	// 备件-调拨运输成本
			classFileCostMeasureMap.put("bakFxcbj", "BAKCSTT61");	// 备件-风险储备金
			classFileCostMeasureMap.put("managerCost", "MANCSTMM1,BAKCSTMM1");	// 管理
			classFileCostMeasureMap.put("toolCost", "MANCSTTT1,BAKCSTTT1");	// 工具
			classFileCostMeasureMap.put("riskCost", "MANCSTMR1,BAKCSTMR1");	// 风险*/		
		}
		
		return classFileCostMeasureMap;
	}
	/*
	private static Map<String, String> getSlaMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		slaMap.put("A+（全覆盖备件）", "SLA_DEVICE_A+");
		slaMap.put("A+（标准备件）", "SLA_DEVICE_A+");
		slaMap.put("A+", "SLA_DEVICE_A+");
		slaMap.put("B+", "SLA_DEVICE_B+");	
		slaMap.put("高级服务A", "SLA_DEVICE_A");		
		slaMap.put("标准服务B", "SLA_DEVICE_B");	
		slaMap.put("-", "SLA_DEVICE_B");	
		slaMap.put("基础服务C", "SLA_DEVICE_C");	
		slaMap.put("特惠服务D", "SLA_DEVICE_D");	
		slaMap.put("高级服务SP", "SLA_SOFT_A");	
		slaMap.put("基础服务SP+", "SLA_SOFT_B");	
		slaMap.put("基础服务SP-", "SLA_SOFT_C");	
		slaMap.put("高级SP+", "SLA_SOFT_D");	
		slaMap.put("标准SP", "SLA_SOFT_E");	
		
		return slaMap;
	}*/
	
	/*private static Map<String, String> getProdMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		slaMap.put("故障解决服务", "RXSA-03-01-01");	
		slaMap.put("硬件故障解决服务", "RXSA-03-01-01");		
		slaMap.put("备件支持服务", "RXSA-03-01-02");	
		slaMap.put("硬件人工支持服务", "RXSA-03-01-03");	
		slaMap.put("健康检查服务", "RXSA-03-02-02");	
		slaMap.put("硬件健康检查服务", "RXSA-03-02-02");	
		slaMap.put("驻场服务", "RXSA-03-02-05");	
		slaMap.put("系统软件基础服务SP+", "RXSA-02-01-03");	
		slaMap.put("系统软件基础服务SP-", "RXSA-02-01-03");	
		slaMap.put("系统软件高级服务SP", "RXSA-02-01-03");	
		slaMap.put("软件运维服务包", "RXSA-02-01-03");	
		slaMap.put("调整成本类", "RXSA-TEMP");		
		slaMap.put("HA软件保修服务", "RXSA-TEMP");		
		slaMap.put("HA软件健康检查服务", "RXSA-TEMP");		
		slaMap.put("系统软件故障解决服务", "RXSA-TEMP");		
		slaMap.put("系统软件健康检查服务", "RXSA-TEMP");		
		slaMap.put("先行支持服务", "RXSA-TEMP");		
		slaMap.put("人天类服务", "RXSA-TEMP");		
		slaMap.put("调整类", "RXSA-TEMP");		
		slaMap.put("搬迁服务", "RXSA-TEMP");		
		slaMap.put("数据中心迁移", "RXSA-TEMP");		
		slaMap.put("其他专业服务", "RXSA-TEMP");	
		
		return slaMap;
	}*/
	
	@Override
    public CstOrderCostInfo get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstOrderCostInfo> findList(CstOrderCostInfo cstOrderCostInfo) {
		return super.findList(cstOrderCostInfo);
	}
	
	@Override
    public Page<CstOrderCostInfo> findPage(Page<CstOrderCostInfo> page, CstOrderCostInfo cstOrderCostInfo) {
		return super.findPage(page, cstOrderCostInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstOrderCostInfo cstOrderCostInfo) {
		super.save(cstOrderCostInfo);
	}
	
	@Transactional(readOnly = false)
	public void addDetailbatch(List<CstOrderCostInfo> cstOrderCostInfoList) {
		cstOrderCostInfoDao.addDetailBatch(cstOrderCostInfoList);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstOrderCostInfo cstOrderCostInfo) {
		super.delete(cstOrderCostInfo);
	}
	
}