package com.dobo.modules.cst.calplugins.cost.impl;

import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 硬件故障人工成本模型
 *
 */
public class FaultManCostImpl extends CalCostService {
	
	/**
	 * 计算产品成本 : 硬件故障解决
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {

		// BAKCSTM21--单件备件人工成本;BAKCSTU21	BAKCSTM22-单件管控管理人工成本; BAKCSTF22-单件管控管理费用成本 BAKCSTU22-单件管控管理激励成本

		// 单件备件人工激励;需要在备件模型计算后的结果参数中提取，提供故障人工计算用
		/*if(dcis.getCostInfoMap() != null) {
			paraMap.put("BAKCSTM21", dcis.getCostInfoMap().get("BAKCSTM21"));
			paraMap.put("BAKCSTU21", dcis.getCostInfoMap().get("BAKCSTU21"));
			paraMap.put("BAKCSTM22", dcis.getCostInfoMap().get("BAKCSTM22"));
			paraMap.put("BAKCSTF22", dcis.getCostInfoMap().get("BAKCSTF22"));
			paraMap.put("BAKCSTU22", dcis.getCostInfoMap().get("BAKCSTU22"));
		}*/
		
		super.cost(module, detailInfo, dcis);
		
	}
	
}
