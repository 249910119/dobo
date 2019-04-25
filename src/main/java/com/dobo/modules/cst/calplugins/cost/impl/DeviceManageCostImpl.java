package com.dobo.modules.cst.calplugins.cost.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.base.CstManManageStepRule;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;

/**
 * 硬件项目管理成本模型
 *
 */
public class DeviceManageCostImpl extends CalCostService {

	// 巡检类别代码：现场、远程、深度
	private static String[] checkType = new String[]{"BUYCHECKN","BUYFARCHK","BUYDEPCHK"};
	// 项目管理规模聚类
	private static Map<String, String> orderProdMap() {
		Map<String, String> orderProdMap = new HashMap<String, String>();
		// 故障类
		orderProdMap.put("A0", "RXSA-03-01-01,RXSA-03-01-02,RXSA-03-01-03,RXSA-07-05");
		// 巡检类
		orderProdMap.put("A1", "RXSA-03-02-02,RXSA-03-02-03");
		// 驻场类
		orderProdMap.put("A2", "RXSA-03-02-05");
		
		return orderProdMap;
	}
	
	/**
	 * 计算产品成本-产品清单维度
	 * @param module		模型
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	@Override
    public void cost(CstModelProdModuleRel module, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		paraMap.clear();
		
		String prodId = module.getProdId();
		
		// 订单维度聚类后的规模数量
		BigDecimal orderProdAmount = BigDecimal.ZERO;
		for(String prod : detailInfoMap.keySet()) {
			for(ProdDetailInfo pdi : detailInfoMap.get(prod).values()) {
				if(orderProdMap().get("A0").contains(prodId) && orderProdMap().get("A0").contains(prod)) {
					orderProdAmount = orderProdAmount.add(ExpressionCalculate.getMathValue((HashMap<String, String>) pdi.getInParaMap(), "amount*BUYPRDMON/365", 8));
				} else if(orderProdMap().get("A1").contains(prodId) && orderProdMap().get("A1").contains(prod)) {
					for(String check : checkType) {
						if(pdi.getInParaMap().get(check) != null && 
								!"null".equals(pdi.getInParaMap().get(check)) &&
								Integer.parseInt(pdi.getInParaMap().get(check)) > 0) {
							orderProdAmount = orderProdAmount.add(ExpressionCalculate.getMathValue((HashMap<String, String>) pdi.getInParaMap(), "amount*"+check, 8));
						}
					}
				}  else if(orderProdMap().get("A2").contains(prodId) && orderProdMap().get("A2").contains(prod)) {
					orderProdAmount = orderProdAmount.add(ExpressionCalculate.getMathValue((HashMap<String, String>) pdi.getInParaMap(), "amount*BUYPRDMON/365", 8));
				} 
			}
		}
		
		paraMap.put("orderProdAmount", orderProdAmount+"");
		// 项目管理成本需要在计算前，将工作量进行线性处理，获取4个参数：区间数量，区间工作量
		CstManManageStepRule cmmsr = new CstManManageStepRule();
		List<CstManManageStepRule> cmmsrList = CacheDataUtils.getCstManManageStepRuleMap().get(prodId);
		for(CstManManageStepRule cstManManageStepRule : cmmsrList) {
			// 订单维度按服务类别聚合后的规模数量
			if(cstManManageStepRule.getAreaMin() <= orderProdAmount.doubleValue() && 
					cstManManageStepRule.getAreaMax() > orderProdAmount.doubleValue()) {
				cmmsr = cstManManageStepRule;
			}
		}
		// 项目管理成本需要在计算前，将工作量进行线性处理，获取4个参数：区间数量，区间工作量
		paraMap.put("areaMin", cmmsr.getAreaMin()+"");
		paraMap.put("areaMax", cmmsr.getAreaMax()+"");
		paraMap.put("minValue", cmmsr.getAreaMinValue()+"");
		paraMap.put("maxValue", cmmsr.getAreaMaxValue()+"");
			
		
		if(proddciMap.get(prodId) == null) {
			proddciMap.put(prodId, new HashMap<String, DetailCostInfo>());
		}
		// 返回成本明细集合 <detailId, costInfo>
		Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
		
		for(ProdDetailInfo detailInfo : detailInfoMap.get(prodId).values()) {
			if(dcisMap.get(detailInfo.getDetailId()) == null) {
				dcisMap.put(detailInfo.getDetailId(), new DetailCostInfo());
			}
			cost(module, detailInfo, dcisMap.get(detailInfo.getDetailId()));
		}
		
	}
	
	/**
	 * 计算产品成本 : 硬件项目管理
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {

		// 是否驻场(A0：不驻场 A1：驻场)
		if("RXSA-03-02-05".equals(module.getProdId())) {
			paraMap.put("isResident", "A1");
		} else {
			paraMap.put("isResident", "A0");
		}
		
		// 项目管理配比及饱和度表中，对应服务级别设值;清单明细中的规模数量 (单位：故障-台年，巡检-台次，驻场-人年)
		if(orderProdMap().get("A0").contains(module.getProdId())) {
			paraMap.put("serviceLevel", detailInfo.getInParaMap().get("SLA"));
			paraMap.put("unitAmount", ExpressionCalculate.getStringValue((HashMap<String, String>) detailInfo.getInParaMap(), "amount*BUYPRDMON/365", 8));
		} else if(orderProdMap().get("A2").contains(module.getProdId())) {
			paraMap.put("serviceLevel", paraMap.get("isResident"));
			paraMap.put("unitAmount", ExpressionCalculate.getStringValue((HashMap<String, String>) detailInfo.getInParaMap(), "amount*BUYPRDMON/365", 8));
		} else if(orderProdMap().get("A1").contains(module.getProdId())) {
			String checkLevel = "";
			for(String check : checkType) {
				if(detailInfo.getInParaMap().get(check) != null && 
						!"null".equals(detailInfo.getInParaMap().get(check)) &&
						Integer.parseInt(detailInfo.getInParaMap().get(check)) > 0) {
					checkLevel = check;
				}
			}
			if("".equals(checkLevel)) return ;
			paraMap.put("serviceLevel", checkLevel);
			paraMap.put("unitAmount", ExpressionCalculate.getStringValue((HashMap<String, String>) detailInfo.getInParaMap(), "amount*"+checkLevel, 8));
		}
		
		super.cost(module, detailInfo, dcis);
		
	}
	
}
