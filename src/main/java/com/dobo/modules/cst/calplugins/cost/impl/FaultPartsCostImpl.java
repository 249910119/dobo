package com.dobo.modules.cst.calplugins.cost.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dobo.common.service.ServiceException;
import com.dobo.common.utils.DateUtils;
import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.common.BaseParaUtils;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.base.CstDictPara;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.man.CstManCityPara;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.entity.parts.CstPartsCooperCost;
import com.dobo.modules.cst.entity.parts.CstPartsCooperModelDetail;
import com.dobo.modules.cst.entity.parts.CstPartsCooperToOnwer;
import com.dobo.modules.cst.entity.parts.CstPartsEquipTypeRate;
import com.dobo.modules.cst.entity.parts.CstPartsEventFailurePara;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.google.common.collect.Maps;

/**
 * 硬件备件成本模型 
 *
 */
public class FaultPartsCostImpl extends CalCostService {
	
	// 小于1的备件技术方向备件人工单件成本系数
	private Map<String, CstPartsEquipTypeRate> equipManRateMap = new HashMap<String, CstPartsEquipTypeRate>();
	// 小于1的备件技术方向备件激励单件成本系数
	private Map<String, CstPartsEquipTypeRate> equipUrgeRateMap = new HashMap<String, CstPartsEquipTypeRate>();
	
	/**
	 * 计算产品成本-产品清单维度
	 * @param module		模型
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	@Override
    public void cost(CstModelProdModuleRel module, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		equipManRateMap.clear();
		equipUrgeRateMap.clear();
		
		Map<String, CstPartsEquipTypeRate> cretrMap = CacheDataUtils.getCstPartsEquipTypeRateMap();
		for(CstPartsEquipTypeRate cpetr : cretrMap.values()) {
			// 备件人工成本包
			if(cpetr.getEquipManRate() != null && cpetr.getEquipManRate() < 1) {
				equipManRateMap.put(cpetr.getEquiptypeId(), cpetr);
			}
			// 备件人工激励包
			if(cpetr.getEquipUrgeRate() != null && cpetr.getEquipUrgeRate() < 1) {
				equipUrgeRateMap.put(cpetr.getEquiptypeId(), cpetr);
			}
		}
		
		try {
			String prodId = module.getProdId();
			Map<String, Map<String, BigDecimal>> slaAmountMap = this.getResourceMap(detailInfoMap.get(prodId));
			
			if(proddciMap.get(prodId) == null) {
				proddciMap.put(prodId, new HashMap<String, DetailCostInfo>());
			}
			// 返回成本明细集合 <detailId, costInfo>
			Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
			
			for(ProdDetailInfo detailInfo : detailInfoMap.get(prodId).values()) {
				if(dcisMap.get(detailInfo.getDetailId()) == null) {
					dcisMap.put(detailInfo.getDetailId(), new DetailCostInfo(detailInfo.getDetailId()));
				}
				DetailCostInfo dcis = dcisMap.get(detailInfo.getDetailId());
				// 需要先清理参数
				paraMap.clear(); 
				//1.加载所有公式参数
				getCostCalculatePara(module, detailInfo.getInParaMap());
				// 重新加载【分包产品】计算故障成本： 所需要的故障成本SLA采购成本系数和采购均价折扣系数
				for(String sla : slaAmountMap.get(paraMap.get("resourceId")).keySet()) {
					BigDecimal slaAmount = slaAmountMap.get(paraMap.get("resourceId")).get(sla);
					if("SLA_DEVICE_A".equals(sla)) {
						if(BigDecimal.ONE.compareTo(slaAmount) == 0) {
							paraMap.put("slaCostRate", "4");
						} else if(slaAmount.compareTo(BigDecimal.valueOf(2)) >= 0 &&
								slaAmount.compareTo(BigDecimal.valueOf(5)) <= 0) {
							paraMap.put("slaCostRate", "3.5");
						} else if(slaAmount.compareTo(BigDecimal.valueOf(6)) >= 0 &&
								slaAmount.compareTo(BigDecimal.valueOf(10)) <= 0) {
							paraMap.put("slaCostRate", "3");
						} else if(slaAmount.compareTo(BigDecimal.valueOf(11)) >= 0 &&
								slaAmount.compareTo(BigDecimal.valueOf(15)) <= 0) {
							paraMap.put("slaCostRate", "1.68");
						} else if(slaAmount.compareTo(BigDecimal.valueOf(16)) >= 0 &&
								slaAmount.compareTo(BigDecimal.valueOf(20)) <= 0) {
							paraMap.put("slaCostRate", "1.58");
						} else {
							paraMap.put("slaCostRate", "1.58");
						}
					} else if("SLA_DEVICE_B".equals(sla)) {
						paraMap.put("slaCostRate", "1");
					}
				}
				CstPartsEventFailurePara cpefp = (CstPartsEventFailurePara) BaseParaUtils.getBaseData("cstPartsEventFailurePara", paraMap.get("resourceId"));
				if(cpefp.getPrjSub() != null && "1".equals(cpefp.getPrjSub())) {
					// 采购均价折扣系数
					CstDictPara cdp = (CstDictPara) BaseParaUtils.getBaseData("cstDictPara", "Parts_Subcontractproductdiscounrate");
					paraMap.put("purchaseAvrRate", cdp.getParaValue()+"");
					// 分包的产品 故障成本SLA采购成本系数设置为1
					//paraMap.put("slaCostRate", "1");
				}
				
				// 重新加载【备件人工成本包、备件人工激励包】计算备件人员成本
				CstPartsEquipTypeRate cpetr = (CstPartsEquipTypeRate) BaseParaUtils.getBaseData("cstPartsEquipTypeRate", paraMap.get("equipTypeId"));
				// 备件人工成本包
				if(cpetr.getEquipManRate() != null && cpetr.getEquipManRate() == 1) {
					BigDecimal artificialcost = new BigDecimal(paraMap.get("artificialcost"));
					BigDecimal historicalReplacementNumber = new BigDecimal(paraMap.get("historicalReplacementMan"));
					for(CstPartsEquipTypeRate cpetr1 : equipManRateMap.values()) {
						// 剩下人工成本包=人工成本包 -∑((人工成本包/历史备件更换量*技术方向备件人工单件成本系数【技术方向备件人工单件成本系数<1】)*技术方向历史备件更换量))
						artificialcost = artificialcost.subtract(
								new BigDecimal(paraMap.get("artificialcost")).divide(new BigDecimal(paraMap.get("historicalReplacementNumber")), 8, BigDecimal.ROUND_HALF_UP)
								.multiply(BigDecimal.valueOf(cpetr1.getEquipManRate())).multiply(BigDecimal.valueOf(cpetr1.getHisPartsAmount())));
						// 剩余历史备件更换量：字典表中的值”Parts_HistoricalReplacementNumber "值 减去 物理表【TABLE各技术方向人员成本系数】中 技术方向备件激励单件成本系数<1的数量
						historicalReplacementNumber = historicalReplacementNumber.subtract(BigDecimal.valueOf(cpetr1.getHisPartsAmount())).setScale(8, BigDecimal.ROUND_HALF_UP);
					}
					paraMap.put("artificialcost", artificialcost.toString());
					paraMap.put("historicalReplacementMan", historicalReplacementNumber.toString());
				}
				// 备件人工激励包
				if(cpetr.getEquipUrgeRate() != null && cpetr.getEquipUrgeRate() == 1) {
					BigDecimal excitedArtificiallycost = new BigDecimal(paraMap.get("excitedArtificiallycost"));
					BigDecimal historicalReplacementNumber = new BigDecimal(paraMap.get("historicalReplacementUrge"));
					for(CstPartsEquipTypeRate cpetr1 : equipUrgeRateMap.values()) {
						// 剩下人工激励包=人工激励包 -∑((人工激励包/历史备件更换量*技术方向备件激励单件成本系数【技术方向备件激励单件成本系数<1】)*技术方向历史备件更换量))
						excitedArtificiallycost = excitedArtificiallycost.subtract(
								new BigDecimal(paraMap.get("excitedArtificiallycost")).divide(new BigDecimal(paraMap.get("historicalReplacementNumber")), 8, BigDecimal.ROUND_HALF_UP)
								.multiply(BigDecimal.valueOf(cpetr1.getEquipUrgeRate())).multiply(BigDecimal.valueOf(cpetr1.getHisPartsAmount())));
						// 剩余历史备件更换量：字典表中的值”Parts_HistoricalReplacementNumber "值 减去 物理表【TABLE各技术方向人员成本系数】中 技术方向备件激励单件成本系数<1的数量
						historicalReplacementNumber = historicalReplacementNumber.subtract(BigDecimal.valueOf(cpetr1.getHisPartsAmount())).setScale(8, BigDecimal.ROUND_HALF_UP);
					}
					paraMap.put("excitedArtificiallycost", excitedArtificiallycost.toString());
					paraMap.put("historicalReplacementUrge", historicalReplacementNumber.toString());
				}
				// 重新加载 风险成本所用系数
				Map<String, String> cprcpMap = CacheDataUtils.getCstPartsRiskCalParaMap();
				/**历史在保设备数量：物理数据表“TABL风险成本系数（厂商+技术方向+型号组）”中风险成本系数小于1的“历史在保设备数量” */
				paraMap.put("remaindPara", cprcpMap.get("remaindPara"));
				/**物理数据表“TABL风险成本系数（厂商+技术方向+型号组）”中风险成本系数等于1的“历史在保设备数量”之和 */
				paraMap.put("historicalAmountForOne", cprcpMap.get("historicalAmountForOne"));
				
				//2.遍历逻辑运算公式
				calculateFormulaForCost(module, dcis);
			}
			
		} catch (Exception e) {
			throw new ServiceException("成本模型["+module.getModuleId()+"]计算明细失败："+e.getMessage());
		}
	}
	
	/**
	 * 遍历模型公式计算产品成本
	 * @param module		模型
	 * @param dcis		            输出清单成本明细
	 */
	@Override
    public void calculateFormulaForCost(CstModelProdModuleRel module, DetailCostInfo dcis) {
		Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
		Map<String, Map<String, CstPartsCooperCost>> cpccMap = CacheDataUtils.getCstPartsCooperCostMap();
		for(CstModelCalculateFormula cmcf : pmrMap.values()) {
			if(dcis.getCostInfoMap() == null) {
				Map<String, String> costInfoMap = new HashMap<String, String>();
				dcis.setCostInfoMap(costInfoMap);
			}
			
			String calValue = "";
			
			// 故障成本：非合作故障成本+合作故障成本
			if("BAKCSTT11".equals(cmcf.getMeasureId())) {
				try {
					CstResourceBaseInfo crbi = CacheDataUtils.getCstResourceBaseInfoMap().get(paraMap.get("resourceId"));
					Date beginDate = DateUtils.parseDate(paraMap.get("beginDate"), "yyyy-MM-dd");
					Date endDate = DateUtils.parseDate(paraMap.get("endDate"), "yyyy-MM-dd");
					Double onwerCycle = Double.valueOf(paraMap.get("BUYPRDMON")); // 自有周期
					Map<String, CstPartsCooperModelDetail> cpcmdMap = CacheDataUtils.getCstPartsCooperModelDetailMap().get(paraMap.get("resourceId"));
					if(cpcmdMap == null) {
						// 找不到对应的备件合作清单数据时，全部按照自有计算
						dcis.getCostInfoMap().put("BAKCSTT12", "0");
					} else {
						Map<String, CstPartsCooperToOnwer> cpctoMap = CacheDataUtils.getCstPartsCooperToOnwerMap().get(paraMap.get("resourceId"));
						CstManCityPara cmcp = CacheDataUtils.getCstManCityParaMap().get(paraMap.get("SECTION"));
						Double cooperCost = 0.0;
						Double packCost = 0.0; // 合作包对应的备件成本
						Double BAKCSTT12 = 0.0;	 //故障成本-分包
						
						// 遍历合作清单中的合作服务期和订单服务期，判断按非合作，合作计算成本
						for(CstPartsCooperModelDetail cpcmd : cpcmdMap.values()) {
							Double otherCycle = 0.0; // 合作周期
							// 是否合作
							if(!"1".equals(cpcmd.getIsCooper())) continue;
							Date sndBeginDate = null;
							Date sndEndDate = null;
							/**1.按时间切割服务周期 */
							// 订单服务期不包含合作清单的服务期
							if(beginDate.compareTo(cpcmd.getEndDate()) >= 0 || endDate.compareTo(cpcmd.getBeginDate()) <= 0) {
								
							}
							// 订单开始时间小于合作清单开始时间
							else if(beginDate.compareTo(cpcmd.getBeginDate()) < 0) {
								//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(beginDate, cpcmd.getBeginDate());
								sndBeginDate = cpcmd.getBeginDate();
								// 订单截止时间小于合作清单截止时间
								if(endDate.compareTo(cpcmd.getEndDate()) <= 0) {
									sndEndDate = endDate;
								} 
								// 订单截止时间大于合作清单截止时间
								else {
									//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(cpcmd.getEndDate(), endDate);
									sndEndDate = cpcmd.getEndDate();
								}
							}
							// 订单开始时间大于合作清单开始时间
							else {
								sndBeginDate = beginDate;
								// 订单截止时间小于合作清单截止时间
								if(endDate.compareTo(cpcmd.getEndDate()) <= 0) {
									sndEndDate = endDate;
								}
								// 订单截止时间大于合作清单截止时间
								else {
									//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(cpcmd.getEndDate(), endDate);
									sndEndDate = cpcmd.getEndDate();
								}
							}
							
							if(cpctoMap != null) {
								for(CstPartsCooperToOnwer cpcto : cpctoMap.values()) {
									// 合作清单服务期不包含合作转自有清单的服务期
									if(sndBeginDate.compareTo(cpcto.getEndDate()) >= 0 || sndEndDate.compareTo(cpcto.getBeginDate()) <= 0) {
										otherCycle = otherCycle + DateUtils.getDistanceOfTwoDate(cpcmd.getBeginDate(), endDate);
									}
									// 合作清单开始时间小于合作转自有清单开始时间
									else if(sndBeginDate.compareTo(cpcto.getBeginDate()) < 0) {
										otherCycle = otherCycle + DateUtils.getDistanceOfTwoDate(sndBeginDate, cpcto.getBeginDate());
										// 合作清单截止时间小于合作转自有清单截止时间
										if(sndEndDate.compareTo(cpcto.getEndDate()) <= 0) {
											//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(cpcto.getBeginDate(), sndEndDate);
										} 
										// 合作清单截止时间大于合作转自有清单截止时间
										else {
											//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(cpcto.getBeginDate(), cpcto.getEndDate());
											otherCycle = otherCycle + DateUtils.getDistanceOfTwoDate(cpcto.getEndDate(), sndEndDate);
										}
									}
									// 合作清单开始时间大于合作转自有清单开始时间
									else {
										// 合作清单截止时间小于合作转自有清单截止时间
										if(sndEndDate.compareTo(cpcto.getEndDate()) <= 0) {
											//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(sndBeginDate, sndEndDate);
										} 
										// 合作清单截止时间大于合作转自有清单截止时间
										else {
											//onwerCycle = onwerCycle + DateUtils.getDistanceOfTwoDate(sndBeginDate, cpcto.getEndDate());
											otherCycle = otherCycle + DateUtils.getDistanceOfTwoDate(cpcto.getEndDate(), sndEndDate);
										}
									}
								}
							} else {
								if(sndBeginDate != null && sndEndDate != null) {
									otherCycle = otherCycle + DateUtils.getDistanceOfTwoDate(sndBeginDate, sndEndDate);
								}
							}
							
							/**2.计算成本 
							 * 非合作故障成本=（在保设备型号数量*备件故障率*采购平均成本*设备型号故障成本价格系数*采购均价折扣系数*故障成本SLA采购成本系数*备件合作包备件成本系数）/365*服务周期
							 * 合作故障成本=(在保设备型号数量*合作成本*备件合作包备件成本系数)/365*服务周期;
							 * 
							 * 非合作故障成本需要遍历完合作设备清单后再计算，先计算合作成本部分
							 *  */
							Map<String, CstPartsCooperCost> cpccmap = cpccMap.get(cpcmd.getDateId());
							if(cpccmap == null) {
								throw new ServiceException("清单["+dcis.getDetailId()+"]对应的备件合作清单成本数据为空，请联系系统管理员！");
							}
							CstPartsCooperCost cpcc = null;
							if(cpccmap.get(crbi.getMfrName().toUpperCase()+"_"+crbi.getEquipTypeName().toUpperCase()+"_"+cmcp.getProvince()) != null) {
								cpcc = cpccmap.get(crbi.getMfrName().toUpperCase()+"_"+crbi.getEquipTypeName().toUpperCase()+"_"+cmcp.getProvince());
							} else if(cpccmap.get(crbi.getMfrName().toUpperCase()+"_"+cmcp.getProvince()) != null) {
								cpcc = cpccmap.get(crbi.getMfrName().toUpperCase()+"_"+cmcp.getProvince());
							} else if(cpccmap.get(crbi.getMfrName().toUpperCase()+"_全国") != null) {
								cpcc = cpccmap.get(crbi.getMfrName().toUpperCase()+"_全国");
							}
							
							if("1".equals(cpcmd.getIsHighEnd())) {
								if("SLA_DEVICE_A+,SLA_DEVICE_A".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getHighA();
								} else if("SLA_DEVICE_B+,SLA_DEVICE_B".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getHighB();
								} else if("SLA_DEVICE_C".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getHighC();
								} else if("SLA_DEVICE_D".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getHighD();
								} 
							} else {
								if("SLA_DEVICE_A+,SLA_DEVICE_A".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getNotHighA();
								} else if("SLA_DEVICE_B+,SLA_DEVICE_B".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getNotHighB();
								} else if("SLA_DEVICE_C".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getNotHighC();
								} else if("SLA_DEVICE_D".contains(paraMap.get("SLA"))) {
									cooperCost = cpcc.getNotHighD();
								} 
							} 
							onwerCycle = onwerCycle - otherCycle;
							packCost = Double.valueOf(paraMap.get("amount"))*cooperCost*Double.valueOf(paraMap.get("partjointpackcostscale"))/365*otherCycle;
							BAKCSTT12 = BAKCSTT12 + packCost;
							// 备件故障成本（自有、分包） <合作包Id，cost>
							if(dcis.getBackSubCostMap() == null) {
								Map<String, String> backCostInfoMap = new HashMap<String, String>();
								dcis.setBackSubCostMap(backCostInfoMap);
							}
							dcis.getBackSubCostMap().put(cpcc.getPackId(), packCost.toString());
						}
						dcis.getCostInfoMap().put("BAKCSTT12", BigDecimal.valueOf(BAKCSTT12).setScale(8, BigDecimal.ROUND_HALF_UP)+"");
						
					}
					paraMap.put("BAKCSTT12", dcis.getCostInfoMap().get("BAKCSTT12"));
					paraMap.put("calDateCycle", onwerCycle.toString());
					calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if("BAKCSTT12".equals(cmcf.getMeasureId())) { // 故障成本-分包(合作)
				continue;
			}
			//运输成本开发需求：故障件发货运输成本计算需要按SLA的各级事件求和；
			else if("BAKCSTT31".equals(cmcf.getMeasureId())) {
				Double localcitycost = 0.0; // 本地专送发货运输成本
				Double transprovincecost = 0.0; // 异地专送发货运输成本
				Double thirddeliverycost = 0.0; // 第三方物流发货运输成本
				CstPartsEventFailurePara cpefp = (CstPartsEventFailurePara) BaseParaUtils.getBaseData("cstPartsEventFailurePara", paraMap.get("resourceId"));
				// sla明细中下属的四个等级事件
				for(int i=1;i<=4;i++) { 
					CstDictPara cpdp = CacheDataUtils.getCstDictParaMap().get(paraMap.get("SLA")+"_"+i);
					// 事件级别比例，根据不同级别获取
					Double levelEventScale = 0.0;
					if(i == 1) {
						levelEventScale = cpefp.getLevel1EventScale();
					}else if(i == 2) {
						levelEventScale = cpefp.getLevel2EventScale();
					}else if(i == 3) {
						levelEventScale = cpefp.getLevel3EventScale();
					}else if(i == 4) {
						levelEventScale = cpefp.getLevel4EventScale();
					}
					paraMap.put("levelEventScale", levelEventScale+"");
					
					if(cpdp.getParaValue() <= 24) {
						localcitycost = localcitycost + Double.parseDouble(ExpressionCalculate.getStringValue(paraMap, paraMap.get("localcitycost"), 8));
						transprovincecost = transprovincecost + Double.parseDouble(ExpressionCalculate.getStringValue(paraMap, paraMap.get("transprovincecost"), 8));
					} else {
						thirddeliverycost = thirddeliverycost + Double.parseDouble(ExpressionCalculate.getStringValue(paraMap, paraMap.get("thirddeliverycost"), 8));
					}
				} 
				//System.out.println(dcis.getDetailId()+"-------------------"+localcitycost+"|"+transprovincecost+"|"+thirddeliverycost);
				calValue = (localcitycost+transprovincecost+thirddeliverycost)*Double.valueOf(paraMap.get("parts_TransportationCostDiscountRate"))*Double.valueOf(paraMap.get("slaCostRate")) + "";
			}
			/** 备件风险储备金：
			  * 1.1、风险成本系数<1  风险金成本=(在保设备型号数量*(风险金成本包/历史在保设备总数量*风险成本系数))/365*服务周期
			  * 1.2、风险成本系数=1  风险金成本=(在保设备型号数量*(剩下风险金成本包/历史在保设备数量(【风险成本系数=1】))/365*服务周期
			  * 剩下风险金成本包=风险金成本包 -∑((风险金成本包/历史在保设备总数量*风险成本系数【风险成本系数<1】)*历史在保设备数量（风险成本系数【风险成本系数<1】）
			  */
			else if("BAKCSTT61".equals(cmcf.getMeasureId())) {
				if(Double.valueOf(paraMap.get("riskCostScale")) == 1) {
					Map<String, String> cprcpMap = CacheDataUtils.getCstPartsRiskCalParaMap();
					paraMap.put("parts_riskcost", "("+paraMap.get("parts_riskcost")+"-"+paraMap.get("parts_riskcost")+"/"+paraMap.get("weightCost")+"*"+cprcpMap.get("remaindPara")+")");
					paraMap.put("weightCost", cprcpMap.get("historicalAmountForOne"));
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
	
	/**
	 * 聚合同一类设备对应的SLA下的数量
	 * @param detailInfoMap
	 * @return
	 */
	private Map<String, Map<String, BigDecimal>> getResourceMap(Map<String, ProdDetailInfo> detailInfoMap) {
		Map<String, Map<String, BigDecimal>> rsmMap = Maps.newHashMap();
		for(String detailId : detailInfoMap.keySet()) {
			ProdDetailInfo pdi = detailInfoMap.get(detailId);
			if(rsmMap.get(pdi.getInParaMap().get("resourceId")) == null) {
				rsmMap.put(pdi.getInParaMap().get("resourceId"), new HashMap<String, BigDecimal>());
			}
			if(rsmMap.get(pdi.getInParaMap().get("resourceId")).get(pdi.getInParaMap().get("SLA")) == null) {
				rsmMap.get(pdi.getInParaMap().get("resourceId")).put(pdi.getInParaMap().get("SLA"), BigDecimal.ZERO);
			}
			BigDecimal amount = rsmMap.get(pdi.getInParaMap().get("resourceId")).get(pdi.getInParaMap().get("SLA"));
			rsmMap.get(pdi.getInParaMap().get("resourceId")).put(pdi.getInParaMap().get("SLA"), amount.add(new BigDecimal(pdi.getInParaMap().get("amount"))));
		}
		
		return rsmMap;
	}
	
}
