package com.dobo.modules.cst.calplugins.cost.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.dobo.common.service.ServiceException;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.entity.soft.CstSoftPackDegreePara;
import com.dobo.modules.cst.entity.soft.CstSoftPackStepRule;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.google.common.collect.Maps;

/**
 * 系统软件包成本模型
 *
 */
public class SoftPackCostImpl extends CalCostService {

	// 订单维度按巡检次数聚合后的服务级别对应系统软件的规模数量
	private Map<String, BigDecimal> orderSoftAmount = Maps.newHashMap();
	// 订单维度按巡检次数聚合后的服务级别一线各级别对应的资源计划
	private Map<String, Map<String, BigDecimal>> orderResMap = Maps.newHashMap();
	
	
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
		
		// 按产品维度计算出总的数量
		orderSoftAmount.clear();
		for(ProdDetailInfo pdi : detailInfoMap.get(prodId).values()) {
			String sla = pdi.getInParaMap().get("SLA");
			// 巡检次数
			String softcheck = pdi.getInParaMap().get("BUYCHECKN");
			if(orderSoftAmount.get(sla+"-"+softcheck) == null) {
				orderSoftAmount.put(sla+"-"+softcheck, BigDecimal.ZERO);
			}
			
			orderSoftAmount.put(sla+"-"+softcheck, orderSoftAmount.get(sla+"-"+softcheck).add(new BigDecimal(pdi.getInParaMap().get("amount"))));
			
		}
		
		// 写入paraMap
		for(String slaId : orderSoftAmount.keySet()) {
			paraMap.put(slaId, orderSoftAmount.get(slaId)+"");
		}
		
		orderResMap.clear();
		// 系统软件服务资源配比表
		Map<String, CstSoftPackDegreePara> cstSoftPackDegreeParaMap = CacheDataUtils.getCstSoftPackDegreeParaMap();
		// 系统软件服务规模阶梯配比表
		Map<String, CstSoftPackStepRule> cstSoftPackStepRuleMap = CacheDataUtils.getCstSoftPackStepRuleMap();
		
		// 按不同服务级别分规模计算
		for(String slaAndCheckId : orderSoftAmount.keySet()) {
			String slaId = slaAndCheckId.split("-")[0];
			String softcheck = slaAndCheckId.split("-")[1];
			BigDecimal softAmount = orderSoftAmount.get(slaAndCheckId);
			for(CstSoftPackStepRule cspsr : cstSoftPackStepRuleMap.values()) {
				// （规模数量/步长）* 区间的人天数
				BigDecimal perDay = BigDecimal.ZERO;
				// 规模数量大于数量区间时 = 区间值/步长  * 区间系数 
				if(softAmount.compareTo(cspsr.getAreaMax()) > 0) {
					perDay = (cspsr.getAreaMax().subtract(cspsr.getAreaMin())).divide(cspsr.getStep(), 0, BigDecimal.ROUND_UP).multiply(cspsr.getAreaValue());
				}
				// 规模数量在数量区间时 = (规模数量-区间最小值)/步长  * 区间系数 
				else if(softAmount.compareTo(cspsr.getAreaMin()) > 0 && softAmount.compareTo(cspsr.getAreaMax()) <= 0) {
					perDay = (softAmount.subtract(cspsr.getAreaMin())).divide(cspsr.getStep(), 0, BigDecimal.ROUND_UP).multiply(cspsr.getAreaValue());
				}
				//健康检查 需要附加一个参数计算  300/(x+300)
				if("CALSOFTPACKC002".equals(cspsr.getRuleId())) {
					perDay = perDay.multiply(BigDecimal.valueOf(300).divide(softAmount.add(BigDecimal.valueOf(300)), 8, BigDecimal.ROUND_HALF_UP)).setScale(0, BigDecimal.ROUND_HALF_UP);
					//乘以巡检次数
					if(new BigDecimal(softcheck).compareTo(BigDecimal.ZERO) > 0) {
						perDay = perDay.multiply(new BigDecimal(softcheck));
					} else {
						perDay = BigDecimal.ZERO;
					}
				}
				
				// 资源计划为0，跳过
				if(perDay.compareTo(BigDecimal.ZERO) == 0) continue;
				
				CstSoftPackDegreePara cspdp = cstSoftPackDegreeParaMap.get(cspsr.getRuleId());
				
				BigDecimal slaScale = BigDecimal.ZERO;
				// SLA_SOFT_A 高级服务SP; SLA_SOFT_B 基础服务SP+; SLA_SOFT_C 基础服务SP-
				
				if("SLA_SOFT_A".equals(slaId)) {
					slaScale = cspdp.getSlaAScale();
				} else if("SLA_SOFT_B".equals(slaId)) {
					slaScale = cspdp.getSlaBScale();
				} else if("SLA_SOFT_C".equals(slaId)) {
					slaScale = cspdp.getSlaCScale();
				} else if("SLA_SOFT_D".equals(slaId)) {
					slaScale = cspdp.getSlaAScale();
				} else if("SLA_SOFT_E".equals(slaId)) {
					slaScale = cspdp.getSlaBScale();
				}
				
				if(orderResMap.get(slaId+"-"+softcheck) == null) {
					orderResMap.put(slaId+"-"+softcheck, new HashMap<String, BigDecimal>());
				}
				Map<String, BigDecimal> slaOrderResMap = orderResMap.get(slaId+"-"+softcheck);
				//项目管理
				if("CALSOFTPACKC001".equals(cspsr.getRuleId())) {
					slaOrderResMap.put("Level3ManagerWorkSum", perDay.multiply(cspdp.getPmLevel3Scale()).multiply(slaScale));
					slaOrderResMap.put("Level4ManagerWorkSum", perDay.multiply(cspdp.getPmLevel4Scale()).multiply(slaScale));
					slaOrderResMap.put("Level5ManagerWorkSum", perDay.multiply(cspdp.getPmLevel5Scale()).multiply(slaScale));
				} else {
					BigDecimal line1Level2ManWorkdaySum = slaOrderResMap.get("line1Level2ManWorkSum");
					if(line1Level2ManWorkdaySum == null) {
						line1Level2ManWorkdaySum = BigDecimal.ZERO;
					}
					BigDecimal line1Level3ManWorkdaySum = slaOrderResMap.get("line1Level3ManWorkSum");
					if(line1Level3ManWorkdaySum == null) {
						line1Level3ManWorkdaySum = BigDecimal.ZERO;
					}
					
					BigDecimal line1Level4ManWorkdaySum = slaOrderResMap.get("line1Level4ManWorkSum");
					if(line1Level4ManWorkdaySum == null) {
						line1Level4ManWorkdaySum = BigDecimal.ZERO;
					}
					slaOrderResMap.put("line1Level2ManWorkSum", perDay.multiply(cspdp.getLine1Level2Scale()).multiply(slaScale).add(line1Level2ManWorkdaySum));
					slaOrderResMap.put("line1Level3ManWorkSum", perDay.multiply(cspdp.getLine1Level3Scale()).multiply(slaScale).add(line1Level3ManWorkdaySum));
					slaOrderResMap.put("line1Level4ManWorkSum", perDay.multiply(cspdp.getLine1Level4Scale()).multiply(slaScale).add(line1Level4ManWorkdaySum));
				}
			}
			
		}
		
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
	 * 计算产品成本-单条清单维度
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {
		
		//try {

			if(!detailInfo.getInParaMap().containsKey("SLA")){
				throw new ServiceException("成本模型["+module.getModuleId()+"]计算明细["+detailInfo.getDetailId()+"]获取SLA为空！");
			}
			
			String slaId = detailInfo.getInParaMap().get("SLA");
			
			// 巡检次数
			String softcheck = detailInfo.getInParaMap().get("BUYCHECKN");

			if(!orderSoftAmount.containsKey(slaId+"-"+softcheck)){
				throw new ServiceException("成本模型["+module.getModuleId()+"]计算明细["+detailInfo.getDetailId()+"],订单维度各服务级别对应系统软件的规模数量为空！slaId："+slaId);
			}
			// 添加到参数缓存
			paraMap.put("orderSoftAmount", paraMap.get(slaId+"-"+softcheck).toString());
			for(String resSum : orderResMap.get(slaId+"-"+softcheck).keySet()) {
				paraMap.put(resSum, orderResMap.get(slaId+"-"+softcheck).get(resSum).toString());
			}
			
			//1.加载所有公式参数
			getCostCalculatePara(module, detailInfo.getInParaMap());
			//2.遍历逻辑运算公式
			calculateFormulaForCost(module, dcis);

		/*} catch (Exception e) {
			throw new ServiceException("成本模型["+module.getModuleId()+"]计算明细["+detailInfo.getDetailId()+"]失败："+e.getMessage());
		}*/
	}
	
}
