package com.dobo.modules.cst.calplugins.price.impl;

import java.util.HashMap;
import java.util.Map;

import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.price.CalPriceService;
import com.dobo.modules.cst.common.BaseParaUtils;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 单次、先行支持报价模型
 *
 */
public class CaseSupportManPriceImpl extends CalPriceService {
	
	/**
	 * 计算产品价格-产品清单维度
	 * @param module		模型
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	@Override
    public void cost(CstModelProdModuleRel module, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		paraMap.clear();
		
		String prodId = module.getProdId();
		
		if(proddciMap.get(prodId) == null) {
			proddciMap.put(prodId, new HashMap<String, DetailCostInfo>());
		}
		// 返回成本明细集合 <detailId, costInfo>
		Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
		
		for(ProdDetailInfo detailInfo : detailInfoMap.get(prodId).values()) {

			// 按人员报价
			if(!"1".equals(detailInfo.getInParaMap().get("priceType"))) {
				continue;
			}
			
			if(dcisMap.get(detailInfo.getDetailId()) == null) {
				dcisMap.put(detailInfo.getDetailId(), new DetailCostInfo(detailInfo.getDetailId()));
			}
			cost(module, detailInfo, dcisMap.get(detailInfo.getDetailId()));
		}
		
	}
	
	/**
	 * 遍历模型公式计算产品价格
	 * @param module		模型
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void calculateFormulaForCost(CstModelProdModuleRel module, DetailCostInfo dcis) {
		
		// 将服务类型对应的价格计算公式放在参数中，根据服务类型获取对应的计算公式；
		String[] serviceTypeCal = paraMap.get("serviceTypeCal").split(";");
		String serviceTypeFormulaIds = "";
		
		for(String stc : serviceTypeCal) {
			if(paraMap.get("serviceType").equals(stc.split(":")[0])) {
				serviceTypeFormulaIds = stc.split(":")[1];
			}
		}
		
		// 根据服务类型和工程师级别获取对应的服务单价
		String resourceType = paraMap.get("resourceType"); // 0内部，1外部
		String serviceType = paraMap.get("serviceType"); // 1故障、3巡检、5非故障技术支持、6专业化服务
		String roleId = paraMap.get("roleId"); // 3一线、4二线、2-PM
		String engineerLevel = paraMap.get("engineerLevel"); // 1初级、2中级、3高级、4专家
		
		String ftPrice = "0"; // 当前服务的单次价格
		String ftToProjectPrice = "0"; // 当前服务的单次入项目价格
		String prepayPrice = "0"; // 当前服务的预付费价格

		String prodId = "RXSA-CASE-SUPPORT";
		if("0".equals(resourceType)) {
			if("1".equals(serviceType)) {
				if("3".equals(roleId)) {
					prodId = prodId + "1";
				} else if("4".equals(roleId)) {
					prodId = prodId + "2";
				}
			} else if("3".equals(serviceType)) {
				prodId = prodId + "3";
			} else if("5".equals(serviceType)) {
				prodId = prodId + "4";
			} else if("6".equals(serviceType)) {
				prodId = prodId + "5";
			}
			
			String priceType = "";
			if("1".equals(engineerLevel)) {
				priceType = "juniorPrice";
			} else if("2".equals(engineerLevel)) {
				priceType = "middlePrice";
			} else if("3".equals(engineerLevel)) {
				priceType = "seniorPrice";
			} else if("4".equals(engineerLevel)) {
				priceType = "expertPrice";
			}
			
			prepayPrice = BaseParaUtils.getBaseDataPara("cstManCaseSupportPrice", prodId+"1", priceType);
			ftPrice = BaseParaUtils.getBaseDataPara("cstManCaseSupportPrice", prodId+"2", priceType);
			ftToProjectPrice = BaseParaUtils.getBaseDataPara("cstManCaseSupportPrice", prodId+"3", priceType);
			
		}
		
		paraMap.put("prepayPrice", prepayPrice+"");
		paraMap.put("ftPrice", ftPrice+"");
		paraMap.put("ftToProjectPrice", ftToProjectPrice+"");
		
		Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
		for(CstModelCalculateFormula cmcf : pmrMap.values()) {
			if(dcis.getCostInfoMap() == null) {
				Map<String, String> costInfoMap = new HashMap<String, String>();
				dcis.setCostInfoMap(costInfoMap);
			}
			
			String calValue = "0";
			// 根据服务类型对应的计算公式计算价格，其他服务为0
			if("A0".equals(cmcf.getIsModelPara()) && !serviceTypeFormulaIds.contains(cmcf.getMeasureId())) {
				calValue = "0";
			} else {
				calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
			}
			
			dcis.getCostInfoMap().put(cmcf.getMeasureId(), calValue);
			
			// 参数放在参数集合
			if("A0".equals(cmcf.getIsModelPara())) { //是否成本参数(A0:是/A1:否)
				paraMap.put(cmcf.getMeasureId(), calValue);
			}
		}
	}
	
}
