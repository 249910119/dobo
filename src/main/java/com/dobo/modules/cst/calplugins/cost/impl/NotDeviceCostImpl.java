package com.dobo.modules.cst.calplugins.cost.impl;

import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 预付费非硬件成本模型
 *
 */
public class NotDeviceCostImpl extends CalCostService {
	
	/**
	 * 计算产品成本 : 预付费
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {

		super.cost(module, detailInfo, dcis);
		
	}
	
}
