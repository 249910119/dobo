package com.dobo.modules.cst.calplugins.cost.impl;

import java.util.HashMap;
import java.util.Map;

import com.dobo.common.service.ServiceException;
import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 硬件驻场成本模型
 *
 */
public class ResidentManCostImpl extends CalCostService {
	
	/**
	 * 计算产品成本 : 硬件驻场成本模型
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {
		
		try {
			//1.加载所有公式参数
			getCostCalculatePara(module, detailInfo.getInParaMap());
			
			//2.遍历逻辑运算公式
			Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
			for(CstModelCalculateFormula cmcf : pmrMap.values()) {
				if(dcis.getCostInfoMap() == null) {
					Map<String, String> costInfoMap = new HashMap<String, String>();
					dcis.setCostInfoMap(costInfoMap);
				}
				
				String calValue = "";
				
				// 根据明细中工程师的级别，将非对应的角色级别设置为0   
				if("MANCSTH12,MANCSTH13,MANCSTH14,MANCSTH15,MANCSTH16".contains(cmcf.getMeasureId()) &&
						!cmcf.getMeasureId().equals(CacheDataUtils.getCstManLevelRoleRelNewMap().get(paraMap.get("level")+paraMap.get("resstattype")).getMeasureId())) {
					calValue = "0.0";
				} else {
					calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
				}
				
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
	
}
