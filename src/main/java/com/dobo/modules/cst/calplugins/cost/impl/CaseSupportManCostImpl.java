package com.dobo.modules.cst.calplugins.cost.impl;

import java.util.HashMap;
import java.util.Map;

import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 单次、先行支持人工成本模型
 *
 */
public class CaseSupportManCostImpl extends CalCostService {
	
	/**
	 * 计算产品成本 : 单次、先行支持成本模型
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {
		
		if(!"1".equals(detailInfo.getInParaMap().get("priceType"))) {
			return;
		}
		
		//1.加载所有公式参数
		getCostCalculatePara(module, detailInfo.getInParaMap());
		//2.遍历逻辑运算公式
		calculateFormulaForCost(module, dcis);
		
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
			if(dcis.getCostInfoMap() == null) {
				Map<String, String> costInfoMap = new HashMap<String, String>();
				dcis.setCostInfoMap(costInfoMap);
			}
			
			String calValue = "0";
			
			// 当故障一线人工和故障一线外援任意一个非0则计算(故障二线人工资源计划\故障CMO人工资源计划\故障项目部管理资源计划)，否则为0
			if("CASE01H24,CASE01H25,CASE01H26,CASE01H41,CASE01H51".contains(cmcf.getMeasureId())) {
				
				if(Double.parseDouble(paraMap.get("CASE01H12"))+Double.parseDouble(paraMap.get("CASE01H13"))+
						Double.parseDouble(paraMap.get("CASE01H14"))+Double.parseDouble(paraMap.get("CASE01H16"))+
								Double.parseDouble(paraMap.get("CASE01TW1")) > 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
				}

				else if(Double.parseDouble(paraMap.get("CASE01H12"))+Double.parseDouble(paraMap.get("CASE01H13"))+
						Double.parseDouble(paraMap.get("CASE01H14"))+Double.parseDouble(paraMap.get("CASE01H16"))+
								Double.parseDouble(paraMap.get("CASE01TW1")) < 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, "-"+cmcf.getFormula(), 8);
				}
			} 
			// 当故障二线人工和故障二线外援任意一个非0则计算(故障CMO人工资源计划\故障项目部管理资源计划)，否则为0
			else if("CASE02H41,CASE02H51".contains(cmcf.getMeasureId())) {
				
				if(Double.parseDouble(paraMap.get("CASE02H24"))+Double.parseDouble(paraMap.get("CASE02H25"))+
						Double.parseDouble(paraMap.get("CASE02H26"))+Double.parseDouble(paraMap.get("CASE02TW1")) > 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
				}
				
				else if(Double.parseDouble(paraMap.get("CASE02H24"))+Double.parseDouble(paraMap.get("CASE02H25"))+
						Double.parseDouble(paraMap.get("CASE02H26"))+Double.parseDouble(paraMap.get("CASE02TW1")) < 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, "-"+cmcf.getFormula(), 8);
				}
			} 
			// 当巡检一线人工和巡检一线外援任意一个非0则计算(巡检PM人工资源计划\巡检项目部管理资源计划)，否则为0
			else if("CASE03H23,CASE03H24,CASE03H25,CASE03H51".contains(cmcf.getMeasureId())) {
				
				if(Double.parseDouble(paraMap.get("CASE03H12"))+Double.parseDouble(paraMap.get("CASE03H13"))+
						Double.parseDouble(paraMap.get("CASE03H14"))+Double.parseDouble(paraMap.get("CASE03H16"))+
								Double.parseDouble(paraMap.get("CASE03TW1")) > 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
				}

				else if(Double.parseDouble(paraMap.get("CASE03H12"))+Double.parseDouble(paraMap.get("CASE03H13"))+
						Double.parseDouble(paraMap.get("CASE03H14"))+Double.parseDouble(paraMap.get("CASE03H16"))+
								Double.parseDouble(paraMap.get("CASE03TW1")) < 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, "-"+cmcf.getFormula(), 8);
				}
			}
			// 当非故障技术支持一线人工和非故障技术支持一线外援任意一个非0则计算 (非故障技术支持PM人工资源计划\非故障技术支持项目部管理资源计划)，否则为0
			else if("CASE04H23,CASE04H24,CASE04H25,CASE04H51".contains(cmcf.getMeasureId())) {
				
				if(Double.parseDouble(paraMap.get("CASE04H12"))+Double.parseDouble(paraMap.get("CASE04H13"))+
						Double.parseDouble(paraMap.get("CASE04H14"))+Double.parseDouble(paraMap.get("CASE04H16"))+
								Double.parseDouble(paraMap.get("CASE04TW1")) > 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
				}

				else if(Double.parseDouble(paraMap.get("CASE04H12"))+Double.parseDouble(paraMap.get("CASE04H13"))+
						Double.parseDouble(paraMap.get("CASE04H14"))+Double.parseDouble(paraMap.get("CASE04H16"))+
								Double.parseDouble(paraMap.get("CASE04TW1")) < 0) {
					calValue = ExpressionCalculate.getStringValue(paraMap, "-"+cmcf.getFormula(), 8);
				}
			} 
			// 当故障现场CMO和故障远程CMO、故障现场项目部管理和故障远程项目部管理在同一个case里同时不为时，按一个计算
			else if("CASECTH41,CASECTH51".contains(cmcf.getMeasureId())) {
				
				if(Double.parseDouble(paraMap.get("CASE01H41")) != 0 && Double.parseDouble(paraMap.get("CASE02H41")) != 0 ) {
					paraMap.put("CASE02H41", "0");
				}
				if(Double.parseDouble(paraMap.get("CASE01H51")) != 0 && Double.parseDouble(paraMap.get("CASE02H51")) != 0 ) {
					paraMap.put("CASE02H51", "0");
				}
				calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
			} 
			
			else {
				
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
