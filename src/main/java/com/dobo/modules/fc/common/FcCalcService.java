package com.dobo.modules.fc.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.fc.rest.entity.FcActualCalcCostResultRest;
import com.dobo.modules.fc.rest.entity.FcActualPayDetailRest;
import com.dobo.modules.fc.rest.entity.FcActualReceiptDetailRest;
import com.dobo.modules.fc.rest.entity.FcOrderInfoRest;
import com.dobo.modules.fc.rest.entity.FcPlanInnerFeeRest;
import com.dobo.modules.fc.rest.entity.FcPlanPayDetailRest;
import com.dobo.modules.fc.rest.entity.FcPlanReceiptDetailRest;
import com.dobo.modules.fc.rest.entity.FcProjectInfoRest;
import com.dobo.modules.fc.rest.entity.FcSaleOrgInfoRest;
import com.dobo.modules.fc.rest.entity.FcSalePrjInfoRest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public abstract class FcCalcService {

	protected static final Logger LOGGER = Logger.getLogger(FcCalcService.class);
	public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
	
	/**
	 * 计算计划内财务费用
	 * 
	 * 根据项目的收付款金额及时间计算
	 * 
	 * 约定：
	 * 	1.计划收款时间设置为当月月底；
	 * 	2.自有交付成本付款时间设置为当月月底；
	 * 	3.分包成本默认按照背靠背付款
	 * 	4.当收款大于付款时起不计财务费用
	 * 
	 * @param fpiRest
	 * @return financialCost 财务费用
	 * @throws ParseException 
	 */
	public static FcPlanInnerFeeRest calcInner(FcProjectInfoRest fpiRest) throws ParseException {
		if(StringUtils.isEmpty(fpiRest.getFstSvcType())){
			String msg = "项目信息中产品大类为空，项目编号："+fpiRest.getProjectCode();
			LOGGER.debug(msg);
			//throw new ServiceException(msg);
			return new FcPlanInnerFeeRest(fpiRest.getProjectCode(), new Date(), null, msg);
		}

		double loanRate = FcUtils.getFcLoanRateLatest("INNER", fpiRest.getFstSvcType(),DateUtils.getDate("yyyyMM"));	//最新贷款利率
		double depositRate = FcUtils.getFcDepositRateLatest(fpiRest.getFinancialMonth());	//最新存款利率

		List<FcPlanReceiptDetailRest> fcPlanReceiptDetailList = fpiRest.getFcPlanReceiptDetailRestList();		// 项目计划收款明细
		List<FcPlanPayDetailRest> fcPlanPayDetailList = fpiRest.getFcPlanPayDetailRestList();		// 项目计划付款明细
		List<FcOrderInfoRest> fcOrderInfoRestList = fpiRest.getFcOrderInfoRestList();
		
		if(fcPlanReceiptDetailList==null || fcPlanReceiptDetailList.isEmpty()){
			String msg = "财务费用计算失败，无计划收款信息，项目编号："+fpiRest.getProjectCode();
			LOGGER.debug(msg);
			//throw new ServiceException(msg);
			return new FcPlanInnerFeeRest(fpiRest.getProjectCode(), new Date(), 0.0, "财务费用计算失败，无计划收款信息，项目编号："+fpiRest.getProjectCode());
		}

		//将计划收款时间调整为月底
		TreeMap<String, Double> fcPlanReceiptDetailMap = Maps.newTreeMap();//收款时间金额：Map<yyyyMMdd,Double>
		TreeMap<String, Double> fcPlanReceiptDetailScaleMap = Maps.newTreeMap();//收款时间比例：Map<yyyyMMdd,Double>
		for(FcPlanReceiptDetailRest receipt : fcPlanReceiptDetailList){
			Date lastDayOfMonth = DateUtils.getLastDayOfMonth(receipt.getPlanReceiptTime());
			String lastDayOfMonthStr = DateUtils.formatDate(lastDayOfMonth, PATTERN_YYYYMMDD);
			Double planReceiptAmount = receipt.getPlanReceiptAmount();
			Double planReceiptScale = receipt.getPlanReceiptScale();
			if(fcPlanReceiptDetailMap.containsKey(lastDayOfMonthStr)){
				fcPlanReceiptDetailMap.put(lastDayOfMonthStr, fcPlanReceiptDetailMap.get(lastDayOfMonthStr)+planReceiptAmount);
				fcPlanReceiptDetailScaleMap.put(lastDayOfMonthStr, fcPlanReceiptDetailScaleMap.get(lastDayOfMonthStr)+planReceiptScale);
			}else{
				fcPlanReceiptDetailMap.put(lastDayOfMonthStr, planReceiptAmount);
				fcPlanReceiptDetailScaleMap.put(lastDayOfMonthStr, planReceiptScale);
			}
		}
		
		//计划付款信息
		TreeMap<String, Double> fcPlanPayDetailRestMap = Maps.newTreeMap();//Map<yyyyMMdd,Double>
		if(fcPlanPayDetailList != null){
			for(FcPlanPayDetailRest payDetailRest : fcPlanPayDetailList){
				Date planPayTime = payDetailRest.getPlanPayTime();
				String planPayTimeStr = DateUtils.formatDate(planPayTime, PATTERN_YYYYMMDD);
				Double planPayAmount = payDetailRest.getPlanPayAmount();
				if(fcPlanPayDetailRestMap.containsKey(planPayTimeStr)){
					fcPlanPayDetailRestMap.put(planPayTimeStr, fcPlanPayDetailRestMap.get(planPayTimeStr)+planPayAmount);
				}else{
					fcPlanPayDetailRestMap.put(planPayTimeStr, planPayAmount);
				}
			}
		}
		
		//自有订单信息（按月边界付款）
		if(fcOrderInfoRestList != null){
			for(FcOrderInfoRest fcOrder : fcOrderInfoRestList){
				
				double ownProdCost = fcOrder.getOwnProdCost();//自有成本
				//自有成本按照订单的服务期月边界拆分
				if(ownProdCost > 0){
					//两个时间间隔间月份List<201604>
					List<String> monthBw = DateUtils.getMonthBetween(fcOrder.getServiceDateBegin(), fcOrder.getServiceDateEnd()); 
					double daysBw = DateUtils.getDistanceOfTwoDate(fcOrder.getServiceDateBegin(), fcOrder.getServiceDateEnd());//服务期间隔
					double dayCost = ownProdCost / daysBw;//每天成本单价
					
					int monthSize = monthBw.size();
					for(int i=0;i<monthSize;i++){
						String month = monthBw.get(i);
						//月末
						Date firstDayOfMonth = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(month));
						Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateUtils.parseDate(month));
						double orderPayAmount = 0.0;
						Date begin = null;
						Date end = null;
						//第一个月成本
						if(i==0){
							begin = fcOrder.getServiceDateBegin();
							end = lastDayOfMonth;
						}else if(i==monthSize-1){
							begin = firstDayOfMonth;
							end = fcOrder.getServiceDateEnd();
						}else{
							begin = firstDayOfMonth;
							end = lastDayOfMonth;
						}
						
						//每月成本付款
						orderPayAmount = dayCost * (DateUtils.getDistanceOfTwoDate(begin, end));
						Date payDate = lastDayOfMonth;
						String payDateStr = DateUtils.formatDate(payDate, PATTERN_YYYYMMDD);
						
						if(fcPlanPayDetailRestMap.containsKey(payDateStr)){
							fcPlanPayDetailRestMap.put(payDateStr, fcPlanPayDetailRestMap.get(payDateStr)+orderPayAmount);
						}else{
							fcPlanPayDetailRestMap.put(payDateStr, orderPayAmount);
						}
					}
				}
				
				double specifySubCost = fcOrder.getSpecifySubCost();//分包成本
				//分包成本按照计划收款时间拆分
				if(specifySubCost > 0){
					//fcPlanReceiptDetailScaleMap
					//Double planReceiptScale = receipt.getPlanReceiptScale();
					for(String receiptTimeStr : fcPlanReceiptDetailScaleMap.keySet()){
						Double planReceiptScale = fcPlanReceiptDetailScaleMap.get(receiptTimeStr);
						double orderPayAmountSub = specifySubCost * planReceiptScale;
						
						if(fcPlanPayDetailRestMap.containsKey(receiptTimeStr)){
							fcPlanPayDetailRestMap.put(receiptTimeStr, fcPlanPayDetailRestMap.get(receiptTimeStr)+orderPayAmountSub);
						}else{
							fcPlanPayDetailRestMap.put(receiptTimeStr, orderPayAmountSub);
						}
					}
				}
			}
		}

		//整理数据每条收款对应的多条付款
		TreeMap<String, TreeMap<String,Double>> fcPlanPayDetailRestListMap = Maps.newTreeMap();//TreeMap<receiptTime, TreeMap<payTime,Double>>
		String lastReceiptTimeStr = null;//上一次收款时间
		for(String receiptTimeStr : fcPlanReceiptDetailMap.keySet()){
			if(!fcPlanPayDetailRestListMap.containsKey(receiptTimeStr)){
				fcPlanPayDetailRestListMap.put(receiptTimeStr, new TreeMap<String,Double>());
			}
			
			for(String payTimeStr : fcPlanPayDetailRestMap.keySet()){
				Double planPayAmount = fcPlanPayDetailRestMap.get(payTimeStr);
				if((lastReceiptTimeStr==null || Integer.valueOf(payTimeStr) > Integer.valueOf(lastReceiptTimeStr)) 
						&& Integer.valueOf(payTimeStr) <= Integer.valueOf(receiptTimeStr)){
					TreeMap<String, Double> payMap = fcPlanPayDetailRestListMap.get(receiptTimeStr);
					if(payMap.containsKey(payTimeStr)){
						payMap.put(payTimeStr, payMap.get(payTimeStr)+planPayAmount);
					}else{
						payMap.put(payTimeStr, planPayAmount);
					}
					fcPlanPayDetailRestListMap.put(receiptTimeStr, payMap);
				}
			}
			
			lastReceiptTimeStr = receiptTimeStr;
		}
		
		//收付款时间不是月底则置为当月月底
		double lastReceiptLeft = 0L;//上一笔收款结余
		Date lastPlanReceiptTime = null;//上一笔收款时间
		double loanCost = 0.0;	//贷款利息
		double depositCost = 0.0;	//存款利息
		int receiptCount = 0;//收款笔数计数
		for(String receiptTimeStr : fcPlanReceiptDetailMap.keySet()){
			Date receiptTime = DateUtils.parseDate(receiptTimeStr, PATTERN_YYYYMMDD);
			Double receiptAmount = fcPlanReceiptDetailMap.get(receiptTimeStr);
			TreeMap<String, Double> fcPayMap = fcPlanPayDetailRestListMap.get(receiptTimeStr);
			
			//如果该笔收款没有付款
			if(fcPayMap != null && !fcPayMap.isEmpty()){
				//第一笔先付款后收款情况
				if(receiptCount == 0){
					//只有贷息
					for(String payTimeStr : fcPayMap.keySet()){
						Double payAmount = fcPayMap.get(payTimeStr);
						loanCost += FcUtils.calcInterrestCost(payAmount,loanRate,DateUtils.parseDate(payTimeStr, PATTERN_YYYYMMDD), receiptTime);
					}
				}else{
					double lastReceiptLeft4Pay = lastReceiptLeft;//上一笔付款时对应的收款结余
					Date lastPlanPayTime4Pay = lastPlanReceiptTime;//上一笔付款时对应的付款时间
					int paySize = fcPayMap.size();
					int k = 0;
					for(String payTimeStr : fcPayMap.keySet()){
						double payAmount = fcPayMap.get(payTimeStr);
						Date payTime = DateUtils.parseDate(payTimeStr, PATTERN_YYYYMMDD);

						//上一笔付款对应的收款结余>=0,计算该阶段存息
						if(lastReceiptLeft4Pay >= 0){
							depositCost += FcUtils.calcInterrestCost(lastReceiptLeft4Pay,depositRate,lastPlanPayTime4Pay, payTime);
						}else{
							loanCost += FcUtils.calcInterrestCost(lastReceiptLeft4Pay,loanRate,lastPlanPayTime4Pay, payTime);
						}
						
						//结余
						lastPlanPayTime4Pay = payTime;
						lastReceiptLeft4Pay = lastReceiptLeft4Pay - payAmount;		
						
						//上一笔实际收款时间
						k ++;
						
						//该笔收款对应的最后一笔付款，放在付款循环最后执行
						if(k != 0 && k == paySize){
							if(lastReceiptLeft4Pay >= 0){
								depositCost += FcUtils.calcInterrestCost(lastReceiptLeft4Pay,depositRate,lastPlanPayTime4Pay, receiptTime);
							}else{
								loanCost += FcUtils.calcInterrestCost(lastReceiptLeft4Pay,loanRate,lastPlanPayTime4Pay, receiptTime);
							}
						}
					}
				}
			}else{
				if(receiptCount != 0){
					if(lastReceiptLeft >= 0){
						depositCost += FcUtils.calcInterrestCost(lastReceiptLeft,depositRate,lastPlanReceiptTime, receiptTime);
					}else{
						loanCost += FcUtils.calcInterrestCost(lastReceiptLeft,loanRate,lastPlanReceiptTime, receiptTime);
					}
				}
			}
				
			lastPlanReceiptTime = receiptTime;
			
			//计算该笔对应总付款
			double planPayAmountSum = 0.0;	//总付款
			for(String payTimeStr : fcPayMap.keySet()){
				planPayAmountSum += fcPayMap.get(payTimeStr);
			}
			lastReceiptLeft = lastReceiptLeft + receiptAmount - planPayAmountSum;
			
			//收款计数
			receiptCount ++;
		}

		//财务费用：贷息-存息
		BigDecimal financialCost = BigDecimal.valueOf(loanCost).subtract(BigDecimal.valueOf(depositCost)).setScale(0, BigDecimal.ROUND_HALF_UP);
		
		//20170122:计划内财务费用按照存贷息模式计算，如果财务费用小于100则抹零
		financialCost = financialCost.compareTo(new BigDecimal(100))<=0?BigDecimal.ZERO:financialCost;
		
		//将计算结果写入临时对象
		FcPlanInnerFeeRest innerFeeRest = new FcPlanInnerFeeRest(fpiRest.getProjectCode(), new Date(), financialCost.doubleValue(), loanRate, depositRate, fpiRest.getProjectCode());
		
		return innerFeeRest;
	}

	/**
	 * 计算计划外财务费用-按项目
	 * 
	 * @param fspRest
	 * @throws ParseException
	 */
	public static void calcActualPrj(FcSalePrjInfoRest fspRest) throws ParseException {		
		String erpxmbh = fspRest.getErpxmbh();
		Date actualBeginDate = fspRest.getActualStartDate(); 	//计息开始日期
		Date actualEndDate = fspRest.getActualEndDate(); 		//计息截止日期
		
		double loanRate = fspRest.getLoanRate();		//最新贷款利率
		double depositRate = fspRest.getDepositRate();	//最新存款利率

		List<FcActualPayDetailRest> fcActualPayDetailList = fspRest.getFcActualPayDetailRestList();		// 项目实际付款明细
		List<FcActualReceiptDetailRest> fcActualReceiptDetailList = fspRest.getFcActualReceiptDetailRestList();		// 项目实际收款明细

		//将实际收款时间进行排序
		//收款时间金额：Map<yyyyMMdd,Double>
 		TreeMap<String, Double> fcActualReceiptDetailMap = Maps.newTreeMap(); 
		if(fcActualReceiptDetailList!=null){
			for(FcActualReceiptDetailRest receipt : fcActualReceiptDetailList){
				Date receiptDate = receipt.getReceiptDate();
				String receiptDateStr = DateUtils.formatDate(receiptDate, PATTERN_YYYYMMDD);				
				Double receiptNetMoney = receipt.getReceiptNetMoney();
				if(fcActualReceiptDetailMap.containsKey(receiptDateStr)){
					fcActualReceiptDetailMap.put(receiptDateStr, fcActualReceiptDetailMap.get(receiptDateStr)+receiptNetMoney);
				}else{
					fcActualReceiptDetailMap.put(receiptDateStr, receiptNetMoney);
				}
			}
		}
		
		//实际付款时间进行排序 合并（自有成本分摊和采购，分包付款）
		TreeMap<String, Double> fcActualPayDetailRestMap = Maps.newTreeMap(); //Map<yyyyMMdd,Double>
		if(fcActualPayDetailList != null) {
			for(FcActualPayDetailRest payDetailRest : fcActualPayDetailList){
				Date actualPayTime = payDetailRest.getPayDate();
				String actualPayTimeStr = DateUtils.formatDate(actualPayTime, PATTERN_YYYYMMDD);
				Double actualPayAmount = payDetailRest.getPayNetMoney();
				if(fcActualPayDetailRestMap.containsKey(actualPayTimeStr)){
					fcActualPayDetailRestMap.put(actualPayTimeStr, fcActualPayDetailRestMap.get(actualPayTimeStr)+actualPayAmount);
				}else{
					fcActualPayDetailRestMap.put(actualPayTimeStr, actualPayAmount);
				}
			}
		}
		
		//每月底没有付款，则增加一条0付款，用于计算每月利息归集
		List<String> monthBw = DateUtils.getMonthBetween(actualBeginDate, actualEndDate); 
		TreeMap<String, Double> monthFcMap = Maps.newTreeMap();
		int monthSize = monthBw.size();
		for(int i=0;i<monthSize;i++){
			String month = monthBw.get(i);
			//月末
			Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateUtils.parseDate(month));
			if(lastDayOfMonth.compareTo(actualEndDate) > 0){
				lastDayOfMonth = actualEndDate;
			}
			String lastDayOfMonthStr = DateUtils.formatDate(lastDayOfMonth, "yyyyMMdd");
			
			if(!fcActualPayDetailRestMap.containsKey(lastDayOfMonthStr)){
				fcActualPayDetailRestMap.put(lastDayOfMonthStr, 0.0);
			}
			
			monthFcMap.put(lastDayOfMonthStr, 0.0);
		}

		//整理数据每条付款对应的多条收款
		TreeMap<String, TreeMap<String,Double>> fcActualReceiptDetailRestListMap = Maps.newTreeMap(); //TreeMap<receiptTime, TreeMap<payTime,Double>>
		String lastPayTimeStr = null;//上一次付款时间
		for(String payTimeStr : fcActualPayDetailRestMap.keySet()){
			TreeMap<String, Double> receiptMap = Maps.newTreeMap();
			
			for(String receiptTimeStr : fcActualReceiptDetailMap.keySet()){
				Double receiptAmount = fcActualReceiptDetailMap.get(receiptTimeStr);
				if((lastPayTimeStr==null || Integer.valueOf(receiptTimeStr) > Integer.valueOf(lastPayTimeStr)) 
						&& Integer.valueOf(receiptTimeStr) <= Integer.valueOf(payTimeStr)){
					if(receiptMap.containsKey(receiptTimeStr)){
						receiptMap.put(receiptTimeStr, receiptMap.get(receiptTimeStr) + receiptAmount);
					}else{
						receiptMap.put(receiptTimeStr, receiptAmount);
					}
				}
			}
			
			fcActualReceiptDetailRestListMap.put(payTimeStr, receiptMap);
			
			lastPayTimeStr = payTimeStr;
		}
		
		TreeMap<String, FcActualCalcCostResultRest> actualCostRestMap = Maps.newTreeMap();
		String saleOrgName = fspRest.getSaleOrgName();	// 新事业部名称
		String buName = fspRest.getBuName();			// BU名称
		String saleOrg = fspRest.getSaleOrg();			// 事业部
		
		double loanCost = 0.0;	//贷款利息
		double depositCost = 0.0;	//存款利息

		Date lastRowDate = null;//上一条收付对应的日期
		double lastRowLeft = 0.0;//上一条收付对应的余额
		double lastRowFc = 0.0;//上一条收付对应的利息
		
		double endBalance = 0.0;//期末结余
		//财务费用：贷息-存息
		double financialCost = 0.0;
		
		for(String payDateStr : fcActualPayDetailRestMap.keySet()){
			Date payDate = DateUtils.parseDate(payDateStr, PATTERN_YYYYMMDD);
			Double payAmount = fcActualPayDetailRestMap.get(payDateStr);
			TreeMap<String, Double> fcReceiptMap = fcActualReceiptDetailRestListMap.get(payDateStr);

			if(fcReceiptMap != null && !fcReceiptMap.isEmpty()){
				for(String receiptDateStr : fcReceiptMap.keySet()){
					Double receiptAmount = fcReceiptMap.get(receiptDateStr);
					Date receiptDate = DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD);
					
					if(lastRowDate != null){
						if(lastRowLeft > 0){
							depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate, DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD));
						}else{
							loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD));
						}
					}
					
					Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

					lastRowDate = receiptDate;
					lastRowLeft = lastRowLeft + receiptAmount;
					lastRowFc = loanCost-depositCost;//截止当前累计财务费用
					
					//增加明细
					FcActualCalcCostResultRest actualCostRest = actualCostRestMap.get(receiptDateStr);
					if(actualCostRest != null){
						actualCostRest.setReceiptNetMoney(receiptAmount);
						actualCostRest.setRestNetMoney(lastRowLeft);
					}else{
						actualCostRest = new FcActualCalcCostResultRest() ;
						actualCostRest.setErpxmbh(erpxmbh);
						actualCostRest.setSaleOrgName(saleOrgName);
						actualCostRest.setBuName(buName);
						actualCostRest.setSaleOrg(saleOrg);
						actualCostRest.setPayReceiptDate(receiptDate);
						actualCostRest.setPayNetMoney(0.0);
						actualCostRest.setReceiptNetMoney(receiptAmount);
						actualCostRest.setRestNetMoney(lastRowLeft);
						actualCostRest.setFinancialCost(thisFc);
						
						//当期利息
						if(receiptDate.compareTo(actualBeginDate) >= 0 && receiptDate.compareTo(actualEndDate) <= 0) {
							actualCostRest.setIsCurrentPeriod("计算当期");
							financialCost += thisFc;
						}
					}
					actualCostRestMap.put(receiptDateStr, actualCostRest);
				}
				
				//增加明细
				FcActualCalcCostResultRest actualCostRest = actualCostRestMap.get(payDateStr);
				if(actualCostRest != null){					
					actualCostRest.setPayNetMoney(payAmount);

					lastRowDate = payDate;
					lastRowLeft = lastRowLeft - payAmount;
				}else{
					if(lastRowDate != null){
						if(lastRowLeft > 0){
							depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate,payDate);
						}else{
							loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,payDate);
						}
					}
					
					Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

					lastRowDate = payDate;
					lastRowLeft = lastRowLeft - payAmount;
					lastRowFc = loanCost-depositCost;//截止当前累计财务费用

					
					actualCostRest = new FcActualCalcCostResultRest() ;
					actualCostRest.setErpxmbh(erpxmbh);
					actualCostRest.setSaleOrgName(saleOrgName);
					actualCostRest.setBuName(buName);
					actualCostRest.setSaleOrg(saleOrg);
					actualCostRest.setPayReceiptDate(payDate);
					actualCostRest.setPayNetMoney(payAmount);
					actualCostRest.setReceiptNetMoney(0.0);
					actualCostRest.setRestNetMoney(lastRowLeft);
					actualCostRest.setFinancialCost(thisFc);
					
					//当期利息
					if(payDate.compareTo(actualBeginDate) >= 0 && payDate.compareTo(actualEndDate) <= 0) {
						actualCostRest.setIsCurrentPeriod("计算当期");
						financialCost += thisFc;
					}
				}
				actualCostRestMap.put(payDateStr, actualCostRest);
			}else{
				if(lastRowDate != null){
					if(lastRowLeft > 0){
						depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate,payDate);
					}else{
						loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,payDate);
					}
				}
				
				Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

				lastRowDate = payDate;
				lastRowLeft = lastRowLeft - payAmount;
				lastRowFc = loanCost-depositCost;//截止当前累计财务费用
				
				FcActualCalcCostResultRest actualCostRest = new FcActualCalcCostResultRest() ;
				actualCostRest.setErpxmbh(erpxmbh);
				actualCostRest.setSaleOrgName(saleOrgName);
				actualCostRest.setBuName(buName);
				actualCostRest.setSaleOrg(saleOrg);
				actualCostRest.setPayReceiptDate(payDate);
				actualCostRest.setPayNetMoney(payAmount);
				actualCostRest.setReceiptNetMoney(0.0);
				actualCostRest.setRestNetMoney(lastRowLeft);
				actualCostRest.setFinancialCost(thisFc);
				actualCostRestMap.put(payDateStr, actualCostRest);
				
				//当期利息
				if(payDate.compareTo(actualBeginDate) >= 0 && payDate.compareTo(actualEndDate) <= 0) {
					actualCostRest.setIsCurrentPeriod("计算当期");
					financialCost += thisFc;
				}
			}
			
			//期末结余
			if(payDate.compareTo(actualEndDate) == 0){
				endBalance = lastRowLeft;
			}
		}
		
		//财务费用：贷息-存息
		//Double financialCost2 = loanCost - depositCost;
		
		//20170122:计划内财务费用按照存贷息模式计算，如果财务费用小于100则抹零
		//financialCost = financialCost.compareTo(BigDecimal.ZERO)<=0?BigDecimal.ZERO:financialCost;

		Date prjStartDate = fspRest.getStartDate();
		Date prjEndDate = fspRest.getEndDate();
		Double xmsyCwfy = fspRest.getXmsyCwfy()==null?0L:fspRest.getXmsyCwfy();
		
		// 计划内财务费用按比例
		Date calcStartDate = fspRest.getStartDate();
		if(calcStartDate.compareTo(fspRest.getActualStartDate()) < 0){
			calcStartDate = fspRest.getActualStartDate();
		}
		Date calcEndDate = fspRest.getEndDate();
		if(calcEndDate.compareTo(fspRest.getActualEndDate()) < 0){
			calcEndDate = fspRest.getActualEndDate();
		}

//		double prjDays = DateUtils.getDistanceOfTwoDate(prjStartDate,prjEndDate);
//		double currentDays = DateUtils.getDistanceOfTwoDate(calcStartDate,calcEndDate);
//		Double currentXmsyCwfy = xmsyCwfy*currentDays/prjDays;
		//当期计划内利息
		fspRest.setXmsyCwfy(BigDecimal.valueOf(xmsyCwfy).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());

		fspRest.setFcActualCalcCostResultRestList(Lists.newArrayList(actualCostRestMap.values()));
		fspRest.setEndBalance(BigDecimal.valueOf(endBalance).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		fspRest.setFinancialCost(BigDecimal.valueOf(financialCost).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		double needFc = fspRest.getFinancialCost()-fspRest.getXmsyCwfy();
		fspRest.setCurFinancialCost(needFc < 0 ? 0 : needFc);
	}

	/**
	 * 计算计划外财务费用-按项目
	 * 20190128-计算规则：以20181231为当期开始时间，之前的收入支出做结余；计算20190101之后的财务费用
	 * @param fspRest
	 * @throws ParseException
	 */
	public static void calcActualPrjNew(FcSalePrjInfoRest fspRest) throws ParseException {		
		String erpxmbh = fspRest.getErpxmbh();
		Date actualBeginDate = fspRest.getActualStartDate(); 	//计息开始日期
		Date actualEndDate = fspRest.getActualEndDate(); 		//计息截止日期
		
		double loanRate = fspRest.getLoanRate();		//最新贷款利率
		double depositRate = fspRest.getDepositRate();	//最新存款利率

		List<FcActualPayDetailRest> fcActualPayDetailList = fspRest.getFcActualPayDetailRestList();		// 项目实际付款明细
		List<FcActualReceiptDetailRest> fcActualReceiptDetailList = fspRest.getFcActualReceiptDetailRestList();		// 项目实际收款明细

		//将实际收款时间进行排序
		//收款时间金额：Map<yyyyMMdd,Double>
 		TreeMap<String, Double> fcActualReceiptDetailMap = Maps.newTreeMap(); 
		if(fcActualReceiptDetailList!=null){
			for(FcActualReceiptDetailRest receipt : fcActualReceiptDetailList){
				Date receiptDate = receipt.getReceiptDate();
				String receiptDateStr = DateUtils.formatDate(receiptDate, PATTERN_YYYYMMDD);				
				Double receiptNetMoney = receipt.getReceiptNetMoney();
				if(fcActualReceiptDetailMap.containsKey(receiptDateStr)){
					fcActualReceiptDetailMap.put(receiptDateStr, fcActualReceiptDetailMap.get(receiptDateStr)+receiptNetMoney);
				}else{
					fcActualReceiptDetailMap.put(receiptDateStr, receiptNetMoney);
				}
			}
		}
		
		//实际付款时间进行排序 合并（自有成本分摊和采购，分包付款）
		TreeMap<String, Double> fcActualPayDetailRestMap = Maps.newTreeMap(); //Map<yyyyMMdd,Double>
		if(fcActualPayDetailList != null) {
			for(FcActualPayDetailRest payDetailRest : fcActualPayDetailList){
				Date actualPayTime = payDetailRest.getPayDate();
				String actualPayTimeStr = DateUtils.formatDate(actualPayTime, PATTERN_YYYYMMDD);
				Double actualPayAmount = payDetailRest.getPayNetMoney();
				if(fcActualPayDetailRestMap.containsKey(actualPayTimeStr)){
					fcActualPayDetailRestMap.put(actualPayTimeStr, fcActualPayDetailRestMap.get(actualPayTimeStr)+actualPayAmount);
				}else{
					fcActualPayDetailRestMap.put(actualPayTimeStr, actualPayAmount);
				}
			}
		}
		
		//每月底没有付款，则增加一条0付款，用于计算每月利息归集
		List<String> monthBw = DateUtils.getMonthBetween(actualBeginDate, actualEndDate); 
		TreeMap<String, Double> monthFcMap = Maps.newTreeMap();
		int monthSize = monthBw.size();
		for(int i=0;i<monthSize;i++){
			String month = monthBw.get(i);
			//月末
			Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateUtils.parseDate(month));
			if(lastDayOfMonth.compareTo(actualEndDate) > 0){
				lastDayOfMonth = actualEndDate;
			}
			String lastDayOfMonthStr = DateUtils.formatDate(lastDayOfMonth, "yyyyMMdd");
			
			if(!fcActualPayDetailRestMap.containsKey(lastDayOfMonthStr)){
				fcActualPayDetailRestMap.put(lastDayOfMonthStr, 0.0);
			}
			
			monthFcMap.put(lastDayOfMonthStr, 0.0);
		}
		
		// 添加一条收款，收款时间等于计算财务费用的开始时间，在计算财务费用的时候，当收款小于等于开始时间时，不计算财务费用，只累加当期收入和支出
		String actualBeginDateStr = DateUtils.formatDate(actualBeginDate, "yyyyMMdd");
		if(!fcActualPayDetailRestMap.containsKey(actualBeginDateStr)) {
			fcActualPayDetailRestMap.put(actualBeginDateStr, 0.0);
		}

		//整理数据每条付款对应的多条收款:收款时间要小于等于当前的付款时间
		TreeMap<String, TreeMap<String,Double>> fcActualReceiptDetailRestListMap = Maps.newTreeMap(); //TreeMap<receiptTime, TreeMap<payTime,Double>>
		String lastPayTimeStr = null;//上一次付款时间
		for(String payTimeStr : fcActualPayDetailRestMap.keySet()){
			TreeMap<String, Double> receiptMap = Maps.newTreeMap();
			
			for(String receiptTimeStr : fcActualReceiptDetailMap.keySet()){
				Double receiptAmount = fcActualReceiptDetailMap.get(receiptTimeStr);
				if((lastPayTimeStr==null || Integer.valueOf(receiptTimeStr) > Integer.valueOf(lastPayTimeStr)) 
						&& Integer.valueOf(receiptTimeStr) <= Integer.valueOf(payTimeStr)){
					if(receiptMap.containsKey(receiptTimeStr)){
						receiptMap.put(receiptTimeStr, receiptMap.get(receiptTimeStr) + receiptAmount);
					}else{
						receiptMap.put(receiptTimeStr, receiptAmount);
					}
				}
			}
			
			fcActualReceiptDetailRestListMap.put(payTimeStr, receiptMap);
			
			lastPayTimeStr = payTimeStr;
		}
		
		TreeMap<String, FcActualCalcCostResultRest> actualCostRestMap = Maps.newTreeMap();
		String saleOrgName = fspRest.getSaleOrgName();	// 新事业部名称
		String buName = fspRest.getBuName();			// BU名称
		String saleOrg = fspRest.getSaleOrg();			// 事业部
		
		double loanCost = 0.0;	//贷款利息
		double depositCost = 0.0;	//存款利息

		Date lastRowDate = null;//上一条收付对应的日期
		double lastRowLeft = 0.0;//上一条收付对应的余额
		double lastRowFc = 0.0;//上一条收付对应的利息
		
		double endBalance = 0.0;//期末结余
		//财务费用：贷息-存息
		double financialCost = 0.0;
		
		for(String payDateStr : fcActualPayDetailRestMap.keySet()){
			Date payDate = DateUtils.parseDate(payDateStr, PATTERN_YYYYMMDD);
			// 根据财务费用开始计算时间计算当期初始数据
			Double payAmount = fcActualPayDetailRestMap.get(payDateStr);
			TreeMap<String, Double> fcReceiptMap = fcActualReceiptDetailRestListMap.get(payDateStr);
			if(payDate.compareTo(actualBeginDate) <= 0) {
				if(fcReceiptMap != null && !fcReceiptMap.isEmpty()){
					for(String receiptDateStr : fcReceiptMap.keySet()){
						lastRowLeft = lastRowLeft + fcReceiptMap.get(receiptDateStr);
					}
				}
				lastRowLeft = lastRowLeft - payAmount;
				lastRowDate = payDate;
				
				// 项目期初值
				if(payDate.compareTo(actualBeginDate) == 0) {
					fspRest.setPrjInitial(lastRowLeft);
				}
				
				continue;
			}
			
			if(fcReceiptMap != null && !fcReceiptMap.isEmpty()){
				for(String receiptDateStr : fcReceiptMap.keySet()){
					Double receiptAmount = fcReceiptMap.get(receiptDateStr);
					Date receiptDate = DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD);
					
					if(lastRowDate != null){
						if(lastRowLeft > 0){
							depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate, DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD));
						}else{
							loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD));
						}
					}
					
					Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

					lastRowDate = receiptDate;
					lastRowLeft = lastRowLeft + receiptAmount;
					lastRowFc = loanCost-depositCost;//截止当前累计财务费用
					
					//增加明细
					FcActualCalcCostResultRest actualCostRest = actualCostRestMap.get(receiptDateStr);
					if(actualCostRest != null){
						actualCostRest.setReceiptNetMoney(receiptAmount);
						actualCostRest.setRestNetMoney(lastRowLeft);
					}else{
						actualCostRest = new FcActualCalcCostResultRest() ;
						actualCostRest.setErpxmbh(erpxmbh);
						actualCostRest.setSaleOrgName(saleOrgName);
						actualCostRest.setBuName(buName);
						actualCostRest.setSaleOrg(saleOrg);
						actualCostRest.setPayReceiptDate(receiptDate);
						actualCostRest.setPayNetMoney(0.0);
						actualCostRest.setReceiptNetMoney(receiptAmount);
						actualCostRest.setRestNetMoney(lastRowLeft);
						actualCostRest.setFinancialCost(thisFc);
						
						//当期利息
						if(receiptDate.compareTo(actualBeginDate) >= 0 && receiptDate.compareTo(actualEndDate) <= 0) {
							actualCostRest.setIsCurrentPeriod("计算当期");
							financialCost += thisFc;
						}
					}
					actualCostRestMap.put(receiptDateStr, actualCostRest);
				}
				
				//增加明细
				FcActualCalcCostResultRest actualCostRest = actualCostRestMap.get(payDateStr);
				if(actualCostRest != null){					
					actualCostRest.setPayNetMoney(payAmount);

					lastRowDate = payDate;
					lastRowLeft = lastRowLeft - payAmount;
				}else{
					if(lastRowDate != null){
						if(lastRowLeft > 0){
							depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate,payDate);
						}else{
							loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,payDate);
						}
					}
					
					Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

					lastRowDate = payDate;
					lastRowLeft = lastRowLeft - payAmount;
					lastRowFc = loanCost-depositCost;//截止当前累计财务费用

					
					actualCostRest = new FcActualCalcCostResultRest() ;
					actualCostRest.setErpxmbh(erpxmbh);
					actualCostRest.setSaleOrgName(saleOrgName);
					actualCostRest.setBuName(buName);
					actualCostRest.setSaleOrg(saleOrg);
					actualCostRest.setPayReceiptDate(payDate);
					actualCostRest.setPayNetMoney(payAmount);
					actualCostRest.setReceiptNetMoney(0.0);
					actualCostRest.setRestNetMoney(lastRowLeft);
					actualCostRest.setFinancialCost(thisFc);
					
					//当期利息
					if(payDate.compareTo(actualBeginDate) >= 0 && payDate.compareTo(actualEndDate) <= 0) {
						actualCostRest.setIsCurrentPeriod("计算当期");
						financialCost += thisFc;
					}
				}
				actualCostRestMap.put(payDateStr, actualCostRest);
			}else{
				if(lastRowDate != null){
					if(lastRowLeft > 0){
						depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate,payDate);
					}else{
						loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,payDate);
					}
				}
				
				Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

				lastRowDate = payDate;
				lastRowLeft = lastRowLeft - payAmount;
				lastRowFc = loanCost-depositCost;//截止当前累计财务费用
				
				FcActualCalcCostResultRest actualCostRest = new FcActualCalcCostResultRest() ;
				actualCostRest.setErpxmbh(erpxmbh);
				actualCostRest.setSaleOrgName(saleOrgName);
				actualCostRest.setBuName(buName);
				actualCostRest.setSaleOrg(saleOrg);
				actualCostRest.setPayReceiptDate(payDate);
				actualCostRest.setPayNetMoney(payAmount);
				actualCostRest.setReceiptNetMoney(0.0);
				actualCostRest.setRestNetMoney(lastRowLeft);
				actualCostRest.setFinancialCost(thisFc);
				actualCostRestMap.put(payDateStr, actualCostRest);
				
				//当期利息
				if(payDate.compareTo(actualBeginDate) >= 0 && payDate.compareTo(actualEndDate) <= 0) {
					actualCostRest.setIsCurrentPeriod("计算当期");
					financialCost += thisFc;
				}
			}
			
			//期末结余
			if(payDate.compareTo(actualEndDate) == 0){
				endBalance = lastRowLeft;
			}
		}
		
		//财务费用：贷息-存息
		//Double financialCost2 = loanCost - depositCost;
		
		//20170122:计划内财务费用按照存贷息模式计算，如果财务费用小于100则抹零
		//financialCost = financialCost.compareTo(BigDecimal.ZERO)<=0?BigDecimal.ZERO:financialCost;

		Date prjStartDate = fspRest.getStartDate();
		Date prjEndDate = fspRest.getEndDate();
		Double xmsyCwfy = fspRest.getXmsyCwfy()==null?0L:fspRest.getXmsyCwfy();
		
		// 计划内财务费用按比例
		Date calcStartDate = fspRest.getStartDate();
		if(calcStartDate.compareTo(fspRest.getActualStartDate()) < 0){
			calcStartDate = fspRest.getActualStartDate();
		}
		Date calcEndDate = fspRest.getEndDate();
		if(calcEndDate.compareTo(fspRest.getActualEndDate()) < 0){
			calcEndDate = fspRest.getActualEndDate();
		}

		double prjDays = DateUtils.getDistanceOfTwoDate(prjStartDate,prjEndDate);
		double currentDays = DateUtils.getDistanceOfTwoDate(calcStartDate,calcEndDate);
		Double currentXmsyCwfy = xmsyCwfy*currentDays/prjDays;
		//当期计划内利息
		fspRest.setXmsyCwfy(BigDecimal.valueOf(currentXmsyCwfy).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());

		fspRest.setFcActualCalcCostResultRestList(Lists.newArrayList(actualCostRestMap.values()));
		fspRest.setEndBalance(BigDecimal.valueOf(endBalance).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		fspRest.setFinancialCost(BigDecimal.valueOf(financialCost).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		double needFc = fspRest.getFinancialCost()-fspRest.getXmsyCwfy();
		fspRest.setCurFinancialCost(needFc < 0 ? 0 : needFc);
	}
	
	/**
	 * 计算财务费用-按照部门维度
	 * 
	 * 约定：计划收款时间设置为当月月底，自有交付成本付款时间设置为当月月底
	 * 
	 * @param fsoRest
	 * @throws ParseException 
	 */
	public static void calcActualOrg(FcSaleOrgInfoRest fsoRest) throws ParseException {		
		Date actualBeginDate = fsoRest.getActualStartDate(); 	//计息开始日期
		Date actualEndDate = fsoRest.getActualEndDate(); 		//计息截止日期
		
		double loanRate = fsoRest.getLoanRate();		//最新贷款利率
		double depositRate = fsoRest.getDepositRate();	//最新存款利率

		List<FcActualPayDetailRest> fcActualPayDetailList = fsoRest.getFcActualPayDetailRestList();		// 项目实际付款明细
		List<FcActualReceiptDetailRest> fcActualReceiptDetailList = fsoRest.getFcActualReceiptDetailRestList();		// 项目实际收款明细

		//将实际收款时间进行排序
		//收款时间金额：Map<yyyyMMdd,Double>
 		TreeMap<String, Double> fcActualReceiptDetailMap = Maps.newTreeMap(); 
		if(fcActualReceiptDetailList!=null){
			for(FcActualReceiptDetailRest receipt : fcActualReceiptDetailList){
				Date receiptDate = receipt.getReceiptDate();
				String receiptDateStr = DateUtils.formatDate(receiptDate, PATTERN_YYYYMMDD);				
				Double receiptNetMoney = receipt.getReceiptNetMoney();
				if(fcActualReceiptDetailMap.containsKey(receiptDateStr)){
					fcActualReceiptDetailMap.put(receiptDateStr, fcActualReceiptDetailMap.get(receiptDateStr)+receiptNetMoney);
				}else{
					fcActualReceiptDetailMap.put(receiptDateStr, receiptNetMoney);
				}
			}
		}
		
		//实际付款时间进行排序 合并（自有成本分摊和采购，分包付款）
		TreeMap<String, Double> fcActualPayDetailRestMap = Maps.newTreeMap(); //Map<yyyyMMdd,Double>
		if(fcActualPayDetailList != null) {
			for(FcActualPayDetailRest payDetailRest : fcActualPayDetailList){
				Date actualPayTime = payDetailRest.getPayDate();
				String actualPayTimeStr = DateUtils.formatDate(actualPayTime, PATTERN_YYYYMMDD);
				Double actualPayAmount = payDetailRest.getPayNetMoney();
				if(fcActualPayDetailRestMap.containsKey(actualPayTimeStr)){
					fcActualPayDetailRestMap.put(actualPayTimeStr, fcActualPayDetailRestMap.get(actualPayTimeStr)+actualPayAmount);
				}else{
					fcActualPayDetailRestMap.put(actualPayTimeStr, actualPayAmount);
				}
			}
		}
		
		//每月底没有付款，则增加一条0付款，用于计算每月利息归集
		List<String> monthBw = DateUtils.getMonthBetween(actualBeginDate, actualEndDate); 
		TreeMap<String, Double> monthFcMap = Maps.newTreeMap();
		int monthSize = monthBw.size();
		for(int i=0;i<monthSize;i++){
			String month = monthBw.get(i);
			//月末
			Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateUtils.parseDate(month));
			if(lastDayOfMonth.compareTo(actualEndDate) > 0){
				lastDayOfMonth = actualEndDate;
			}
			String lastDayOfMonthStr = DateUtils.formatDate(lastDayOfMonth, "yyyyMMdd");
			
			if(!fcActualPayDetailRestMap.containsKey(lastDayOfMonthStr)){
				fcActualPayDetailRestMap.put(lastDayOfMonthStr, 0.0);
			}
			
			monthFcMap.put(lastDayOfMonthStr, 0.0);
		}

		//整理数据每条付款对应的多条收款
		TreeMap<String, TreeMap<String,Double>> fcActualReceiptDetailRestListMap = Maps.newTreeMap(); //TreeMap<receiptTime, TreeMap<payTime,Double>>
		String lastPayTimeStr = null;//上一次付款时间
		for(String payTimeStr : fcActualPayDetailRestMap.keySet()){
			TreeMap<String, Double> receiptMap = Maps.newTreeMap();
			
			for(String receiptTimeStr : fcActualReceiptDetailMap.keySet()){
				Double receiptAmount = fcActualReceiptDetailMap.get(receiptTimeStr);
				if((lastPayTimeStr==null || Integer.valueOf(receiptTimeStr) > Integer.valueOf(lastPayTimeStr)) 
						&& Integer.valueOf(receiptTimeStr) <= Integer.valueOf(payTimeStr)){
					if(receiptMap.containsKey(receiptTimeStr)){
						receiptMap.put(receiptTimeStr, receiptMap.get(receiptTimeStr) + receiptAmount);
					}else{
						receiptMap.put(receiptTimeStr, receiptAmount);
					}
				}
			}
			
			fcActualReceiptDetailRestListMap.put(payTimeStr, receiptMap);
			
			lastPayTimeStr = payTimeStr;
		}
		
		TreeMap<String, FcActualCalcCostResultRest> actualCostRestMap = Maps.newTreeMap();
		String saleOrgName = fsoRest.getSaleOrgName();	// 新事业部名称
		String buName = fsoRest.getBuName();			// BU名称
		String saleOrg = fsoRest.getSaleOrg();			// 事业部
		
		double loanCost = 0.0;	//贷款利息
		double depositCost = 0.0;	//存款利息

		Date lastRowDate = null;//上一条收付对应的日期
		double lastRowLeft = 0.0;//上一条收付对应的余额
		double lastRowFc = 0.0;//上一条收付对应的利息
		
		double endBalance = 0.0;//期末结余
		
		for(String payDateStr : fcActualPayDetailRestMap.keySet()){
			Date payDate = DateUtils.parseDate(payDateStr, PATTERN_YYYYMMDD);
			Double payAmount = fcActualPayDetailRestMap.get(payDateStr);
			TreeMap<String, Double> fcReceiptMap = fcActualReceiptDetailRestListMap.get(payDateStr);

			if(fcReceiptMap != null && !fcReceiptMap.isEmpty()){
				for(String receiptDateStr : fcReceiptMap.keySet()){
					Double receiptAmount = fcReceiptMap.get(receiptDateStr);
					Date receiptDate = DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD);
					
					if(lastRowDate != null){
						if(lastRowLeft > 0){
							depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate, DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD));
						}else{
							loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,DateUtils.parseDate(receiptDateStr, PATTERN_YYYYMMDD),fsoRest);
						}
					}
					
					Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

					lastRowDate = receiptDate;
					lastRowLeft = lastRowLeft + receiptAmount;
					lastRowFc = loanCost-depositCost;//截止当前累计财务费用
					
					//增加明细
					FcActualCalcCostResultRest actualCostRest = actualCostRestMap.get(receiptDateStr);
					if(actualCostRest != null){
						actualCostRest.setReceiptNetMoney(receiptAmount);
						actualCostRest.setRestNetMoney(lastRowLeft);
					}else{
						actualCostRest = new FcActualCalcCostResultRest() ;
						actualCostRest.setSaleOrgName(saleOrgName);
						actualCostRest.setBuName(buName);
						actualCostRest.setSaleOrg(saleOrg);
						actualCostRest.setPayReceiptDate(receiptDate);
						actualCostRest.setPayNetMoney(0.0);
						actualCostRest.setReceiptNetMoney(receiptAmount);
						actualCostRest.setRestNetMoney(lastRowLeft);
						actualCostRest.setFinancialCost(thisFc);
					}
					actualCostRestMap.put(receiptDateStr, actualCostRest);
				}
				
				//增加明细
				FcActualCalcCostResultRest actualCostRest = actualCostRestMap.get(payDateStr);
				if(actualCostRest != null){					
					actualCostRest.setPayNetMoney(payAmount);

					lastRowDate = payDate;
					lastRowLeft = lastRowLeft - payAmount;
				}else{
					if(lastRowDate != null){
						if(lastRowLeft > 0){
							depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate,payDate);
						}else{
							loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,payDate,fsoRest);
						}
					}
					
					Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

					lastRowDate = payDate;
					lastRowLeft = lastRowLeft - payAmount;
					lastRowFc = loanCost-depositCost;//截止当前累计财务费用

					
					actualCostRest = new FcActualCalcCostResultRest() ;
					actualCostRest.setSaleOrgName(saleOrgName);
					actualCostRest.setBuName(buName);
					actualCostRest.setSaleOrg(saleOrg);
					actualCostRest.setPayReceiptDate(payDate);
					actualCostRest.setPayNetMoney(payAmount);
					actualCostRest.setReceiptNetMoney(0.0);
					actualCostRest.setRestNetMoney(lastRowLeft);
					actualCostRest.setFinancialCost(thisFc);
				}
				actualCostRestMap.put(payDateStr, actualCostRest);
			}else{
				if(lastRowDate != null){
					if(lastRowLeft > 0){
						depositCost += FcUtils.calcDepositCost(lastRowLeft,depositRate,lastRowDate,payDate);
					}else{
						loanCost += FcUtils.calcLoanCost(lastRowLeft,loanRate,lastRowDate,payDate,fsoRest);
					}
				}
				
				Double thisFc = loanCost-depositCost - lastRowFc;		//本次财务费用

				lastRowDate = payDate;
				lastRowLeft = lastRowLeft - payAmount;
				lastRowFc = loanCost-depositCost;//截止当前累计财务费用
				
				FcActualCalcCostResultRest actualCostRest = new FcActualCalcCostResultRest() ;
				actualCostRest.setSaleOrgName(saleOrgName);
				actualCostRest.setBuName(buName);
				actualCostRest.setSaleOrg(saleOrg);
				actualCostRest.setPayReceiptDate(payDate);
				actualCostRest.setPayNetMoney(payAmount);
				actualCostRest.setReceiptNetMoney(0.0);
				actualCostRest.setRestNetMoney(lastRowLeft);
				actualCostRest.setFinancialCost(thisFc);
				actualCostRestMap.put(payDateStr, actualCostRest);
			}
			
			//期末结余
			if(payDate.compareTo(actualEndDate) == 0){
				endBalance = lastRowLeft;
			}
		}
		
		//财务费用：贷息-存息
		Double financialCost = loanCost - depositCost;
		
		//20170122:计划内财务费用按照存贷息模式计算，如果财务费用小于100则抹零
		//financialCost = financialCost.compareTo(BigDecimal.ZERO)<=0?BigDecimal.ZERO:financialCost;

		fsoRest.setFcActualCalcCostResultRestList(Lists.newArrayList(actualCostRestMap.values()));
		fsoRest.setFinancialCost(BigDecimal.valueOf(financialCost).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		fsoRest.setEndBalance(BigDecimal.valueOf(endBalance).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		Double payProfit = fsoRest.getPayProfit()==null?0.0:fsoRest.getPayProfit();
		fsoRest.setNeedLoans(BigDecimal.valueOf(endBalance-payProfit).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	
}
