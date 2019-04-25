package com.dobo.modules.cst.calplugins.cost.impl;

import java.util.Map;

import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 单次、先行支持人工成本模型
 *
 */
public class CaseSupportPartsCostImpl extends CalCostService {
	
	/**
	 * 计算产品成本 : 单次、先行支持成本模型
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {
		
		if(!"2".equals(detailInfo.getInParaMap().get("priceType"))) {
			return;
		}

		//1.加载所有公式参数
		getCostCalculatePara(module, detailInfo.getInParaMap());
		//2.遍历逻辑运算公式
		calculateFormulaForCost(module, dcis);
		
	}
	
	/**
	 * 获取成本计算过程中的参数
	 */
	@Override
    public void getCostCalculatePara(CstModelProdModuleRel module, Map<String, String> inParaMap) {
		super.getCostCalculatePara(module, inParaMap);
		
		// 2017-8-31 单次报价上线前历史数据状态,这些数据在计算资源时不需要再乘以成本系数 
		String preStatus = inParaMap.get("preStatus");
		if(null != preStatus && "A2".equals(preStatus)) {
			paraMap.put("payScale", "1");
		}
	}
	
}
