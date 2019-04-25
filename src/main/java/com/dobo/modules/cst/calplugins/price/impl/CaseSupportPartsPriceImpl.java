package com.dobo.modules.cst.calplugins.price.impl;

import java.util.HashMap;
import java.util.Map;

import com.dobo.modules.cst.calplugins.price.CalPriceService;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 单次、先行支持报价模型
 *
 */
public class CaseSupportPartsPriceImpl extends CalPriceService {
	
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

			// 按备件报价
			if(!"2".equals(detailInfo.getInParaMap().get("priceType"))) {
				continue;
			}
			
			if(dcisMap.get(detailInfo.getDetailId()) == null) {
				dcisMap.put(detailInfo.getDetailId(), new DetailCostInfo(detailInfo.getDetailId()));
			}
			cost(module, detailInfo, dcisMap.get(detailInfo.getDetailId()));
		}
		
	}
	
}
