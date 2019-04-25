package com.dobo.modules.fc.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dobo.common.service.ServiceException;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.excel.ExcelHelper;
import com.dobo.common.utils.excel.SxssfExcelHelper;
import com.dobo.modules.fc.common.FcCalcService;
import com.dobo.modules.fc.common.FcUtils;
import com.dobo.modules.fc.entity.BasSybInitial;
import com.dobo.modules.fc.rest.entity.FcActualCalcCostResultRest;
import com.dobo.modules.fc.rest.entity.FcActualOrderInfoRest;
import com.dobo.modules.fc.rest.entity.FcActualPayDetailRest;
import com.dobo.modules.fc.rest.entity.FcActualReceiptDetailRest;
import com.dobo.modules.fc.rest.entity.FcProjectInfoRest;
import com.dobo.modules.fc.rest.entity.FcSaleOrgInfoRest;
import com.dobo.modules.fc.rest.entity.FcSalePrjInfoRest;
import com.dobo.modules.fc.service.BasSybBuRelService;
import com.dobo.modules.fc.service.BasSybCbzxRelService;
import com.dobo.modules.fc.service.BasSybInitialService;
import com.dobo.modules.fc.service.BasSybNewsybRelService;
import com.dobo.modules.fc.service.BasSybXmbhRelService;
import com.dobo.modules.fc.service.FcActualPayDetailService;
import com.dobo.modules.fc.service.FcActualReceiptDetailService;
import com.dobo.modules.fc.service.FcOrderInfoService;
import com.dobo.modules.fc.service.FcProjectInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RestController
public class FcActualFeeCalcService {
	
	protected static final Logger LOGGER = Logger.getLogger(FcActualFeeCalcService.class);
	
	@Autowired
	private FcProjectInfoService fcProjectInfoService;
	@Autowired
	private FcActualReceiptDetailService fcActualReceiptDetailService;
	@Autowired
	private FcOrderInfoService fcOrderInfoService;
	@Autowired
	private FcActualPayDetailService fcActualPayDetailService;
	@Autowired
	private BasSybNewsybRelService basSybNewsybRelService;
	@Autowired
	private BasSybBuRelService basSybBuRelService;
	@Autowired
	private BasSybXmbhRelService basSybXmbhRelService;
	@Autowired
	private BasSybInitialService basSybInitialService;
	@Autowired
	private BasSybCbzxRelService basSybCbzxRelService;
	
	/**
	 * 根据事业部名称或项目号取最新归属事业部名称
	 * 
	 * @param erpxmbh
	 * @param saleOrgName
	 * @param sybNewsybMap
	 * @param xmbhSybMap
	 * @return
	 */
	private String getNewSaleOrgName(String erpxmbh, String saleOrgName, 
			Map<String, String> sybNewsybMap, Map<String, String> xmbhSybMap){
		String newSaleOrgName = saleOrgName;
		//根据项目号归属事业部获取新事业部名称
		if(StringUtils.isNotBlank(saleOrgName) && sybNewsybMap.containsKey(saleOrgName)){
			newSaleOrgName = sybNewsybMap.get(saleOrgName);
		}
		//根据项目号重新设置项目的事业部名称
		if(StringUtils.isNotBlank(erpxmbh) && xmbhSybMap.containsKey(erpxmbh)){
			newSaleOrgName = xmbhSybMap.get(erpxmbh);
		}
		return newSaleOrgName;
	}

	/**
	 * 按项目计算某时间段的计划外财务费用：按项目
	 * 
	 * 分为两部分
	 * 	1.项目范围：指定时间段内，项目实际到款总和小于项目计划收款总和
	 * 	2.按照项目维度现金流模拟计算项目实际财务利息占用
	 * 	3.按照平贷6%利率，不计存息，单个项目核算，项目间不拆借
	 *  4.现金流包含：实际到款、实际分包支付、实际采购付款、订单边界成本（自有+托管，有wbm订单按照订单服务期，没有的按照crm立项项目服务期）
	 *  5.当期财务费用：某计算区间内对应的财务利息
	 *  6.当期应缴财务费用：当期财务费用减去当期对应计划内财务费用
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/service/fc/calcActualPrj", method = RequestMethod.POST, produces = "application/json")
	public String calcActualCostByPrj(HttpServletRequest request) {
		long start = System.currentTimeMillis();

		String line;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		
		JSONObject json = new JSONObject();
		try{
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
			JSONObject jsonReq = JSON.parseObject(sb.toString());

			String calcYearMonth = jsonReq.getString("calcYearMonth");//计算周期
			String projectCode = jsonReq.getString("projectCode");
			//String insertDB = jsonReq.getString("insertDB");//A0:结果入库/A1:结果不入库
			String userId = jsonReq.getString("userId");
			String fstSvcTypeName = jsonReq.getString("fstSvcTypeName");//业务大类名称：运维/外包等
			String notAllReceived = jsonReq.getString("notAllReceived");//项目未收完款：Y/N
			notAllReceived = StringUtils.isBlank(notAllReceived)?"Y":notAllReceived;//默认Y

			String prjScopeName = "计算期间";
			
			//解析计算月份（如201601-201612）
			Date calcYearMonthBeginDate = null;
			Date calcYearMonthEndDate = null;
			if(calcYearMonth != null){
				String[] calcYearMonthArray = calcYearMonth.split("-");
				if(calcYearMonthArray.length == 1){
					calcYearMonthBeginDate = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(calcYearMonthArray[0],"yyyyMMdd"));
					calcYearMonthEndDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthArray[0],"yyyyMMdd"));
				}else if(calcYearMonthArray.length == 2){
					calcYearMonthBeginDate = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(calcYearMonthArray[0],"yyyyMMdd"));
					calcYearMonthEndDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthArray[1],"yyyyMMdd"));
				}
			}
			
			if(calcYearMonthBeginDate.after(calcYearMonthEndDate)){
				throw new ServiceException("计算月份异常");
			}
					
			String yyyymmddB = DateUtils.formatDate(calcYearMonthBeginDate, "yyyyMMdd");
			String yyyymmddE = DateUtils.formatDate(calcYearMonthEndDate, "yyyyMMdd");
			
			//计算月份为1月，项目范围：立项时间在1月之前，且计划收款时间在1月期间
			List<FcProjectInfoRest> fcProjectInfoList = fcProjectInfoService.findListByPlanReceiptTime(yyyymmddB, yyyymmddE, projectCode, fstSvcTypeName, notAllReceived);
			//收款
			List<FcActualReceiptDetailRest> fcActualReceiptDetailList = fcActualReceiptDetailService.findListByPlanReceiptTime(yyyymmddB, yyyymmddE, projectCode, fstSvcTypeName, notAllReceived);
			//订单
			List<FcActualOrderInfoRest> fcActualOrderInfoList = fcOrderInfoService.findListByPlanReceiptTime(yyyymmddB, yyyymmddE, projectCode, fstSvcTypeName, notAllReceived);

			//付款：分包、采购、费用
			//List<FcActualPayDetailRest> fcActualPayDetailList = fcActualPayDetailService.findListByPlanReceiptTime(yyyymmddB, yyyymmddE, projectCode, fstSvcTypeName, notAllReceived);
			List<FcActualPayDetailRest> fcActualPayDetailList = fcActualPayDetailService.findListByActualPayReceiptTime(yyyymmddB, yyyymmddE, projectCode, fstSvcTypeName, notAllReceived);

			//需要计算的项目：Map<财务项目号, FcSaleOrgInfoRest>
			Map<String, FcSalePrjInfoRest> fcSalePrjInfoRestMap = Maps.newHashMap();
			//付款信息（支付类型:订单、分包、采购、费用、期初）
			Map<String, List<FcActualPayDetailRest>> fcActualPayDetailListMap = Maps.newHashMap();
			//到款信息
			Map<String, List<FcActualReceiptDetailRest>> fcActualReceiptListMap = Maps.newHashMap();
						
			double loanRate = FcUtils.getFcLoanRateLatest("OUTER");	//最新贷款利率
			double depositRate = 0.0;//不计存息 FcUtils.getFcDepositRateLatest(null);// 最新存款利率

			Map<String, FcProjectInfoRest> fcProjectInfoListMap = Maps.newHashMap();
			if(fcProjectInfoList != null){
				for (FcProjectInfoRest fpiRest : fcProjectInfoList) {
//					if((fpiRest.getSaleOrg()!=null && fpiRest.getSaleOrg().contains("测试"))
//					|| (fpiRest.getSaleOrgName()!=null && fpiRest.getSaleOrgName().contains("测试"))) {
						fcProjectInfoListMap.put(fpiRest.getProjectCode(), fpiRest);
//					}
				}
			}

			List<FcActualOrderInfoRest> fcActualOrderInfoListAdjust = Lists.newArrayList();
			List<String> wbmOrderidList = Lists.newArrayList();
			
			//解析orderId
			for (FcActualOrderInfoRest order : fcActualOrderInfoList) {
				String wbmOrderid = order.getWbmOrderid();
				if(StringUtils.isBlank(wbmOrderid)) {
					fcActualOrderInfoListAdjust.add(order);
				}else {
					List<String> list = Arrays.asList(wbmOrderid.split(","));
					wbmOrderidList.addAll(list);
				}
			}
			
			//需要重新按照项目的服务期拆分成本的项目
			/*Set<String> reAddProjectCodes = Sets.newHashSet();
			if(!wbmOrderidList.isEmpty()) {
				List<FcActualOrderInfoRest> fcActualOrderInfoListWbm = fcOrderInfoService.findListByWbmOrderid(wbmOrderidList);
				if(fcActualOrderInfoListWbm != null) {
					for(FcActualOrderInfoRest order : fcActualOrderInfoListWbm) {
						if(order.getStartDate() == null || order.getSumCost() ==  null || order.getSumCost() == 0L) {
							reAddProjectCodes.add(order.getErpxmbh());
						}else {
							fcActualOrderInfoListAdjust.add(order);
						}
					}
				}
			}*/

			//重新加入
			/*for (FcActualOrderInfoRest order : fcActualOrderInfoList) {
				if(reAddProjectCodes.contains(order.getErpxmbh())) {
					fcActualOrderInfoListAdjust.add(order);
				}
			}*/
			
			//订单自有成本
			if(fcActualPayDetailList != null){
				for (FcActualOrderInfoRest order : fcActualOrderInfoList) {
					//自有成本=自有交付成本+托管成本
					Double calcProdCost = order.getOwnCost()+order.getTgCost();
					
					if(calcProdCost == null || calcProdCost == 0) {
						continue;
					}
					
					String erpxmbh = order.getErpxmbh();			// 财务项目编号
					String saleOrg = order.getSaleOrg();			// 事业部

					if(!fcProjectInfoListMap.containsKey(erpxmbh)){
						LOGGER.debug("订单不计算项目：" + erpxmbh);
						continue;
					}
					
					//项目税率
					String taxType = fcProjectInfoListMap.get(erpxmbh).getTaxType();
					
					//自有成本=自有交付成本+托管成本
					Double calcOwnCost = order.getOwnCost()+order.getTgCost();
					
					LOGGER.info(order.getErpxmbh()+":"+order.getWbmOrderid());
					
					//项目服务期间隔
					double daysBw = DateUtils.getDistanceOfTwoDate(order.getStartDate(),order.getEndDate());
					//每天成本单价
					double dayCost = calcOwnCost / daysBw;
					
					// 边界服务期开始
					Date borderStartDate = order.getStartDate();
					/*if(borderStartDate.compareTo(calcYearMonthBeginDate) < 0){
						borderStartDate = calcYearMonthBeginDate;
					}*/
					
					// 边界服务期结束
					Date borderEndDate = order.getEndDate();
					if(borderEndDate.compareTo(calcYearMonthEndDate) > 0){
						borderEndDate = calcYearMonthEndDate;
					}
					
					List<String> monthBw = DateUtils.getMonthBetween(borderStartDate, borderEndDate); 
					
					//项目订单边界成本
					Double orderCost = 0.0;
					
					int monthSize = monthBw.size();
					for(int i=0;i<monthSize;i++){
						String month = monthBw.get(i);
						//月末
						Date firstDayOfMonth = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(month));
						Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateUtils.parseDate(month));
						Date begin = null;
						Date end = null;
						
						//第一个月成本
						if(i==0){
							begin = borderStartDate;
							end = lastDayOfMonth;
						}else if(i==monthSize-1){
							begin = firstDayOfMonth;
							end = borderEndDate;
						}else{
							begin = firstDayOfMonth;
							end = lastDayOfMonth;
						}
						
						//每月成本付款
						double orderPayAmount = dayCost * (DateUtils.getDistanceOfTwoDate(begin, end));
						orderPayAmount = BigDecimal.valueOf(orderPayAmount).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						Date payDate = (i==monthSize-1)? borderEndDate : lastDayOfMonth;	// 付款日期
						
						//payDate = DateUtils.parseDate(DateUtils.formatDate(payDate, "yyyyMM")+"20", "yyyyMMdd");
						
						Double payMoney = orderPayAmount;	// 付款金额
						
						//加入付款
						FcActualPayDetailRest actualPay = new FcActualPayDetailRest();
						actualPay.setPayType("订单");
						actualPay.setSaleOrg(saleOrg);
						actualPay.setSaleOrgName(null);
						actualPay.setBuName(null);
						actualPay.setErpxmbh(erpxmbh);
						actualPay.setPayDate(payDate);
						actualPay.setPayMoney(payMoney);
						actualPay.setPayNetMoney(payMoney);
						actualPay.setTaxType(taxType);
						
						if("ORDER".equals(order.getDataType())) {
							actualPay.setRemark(order.getWbmOrderid());
						}

						//按照项目号，归集订单每月成本边界
						List<FcActualPayDetailRest> list = fcActualPayDetailListMap.get(erpxmbh);
						if(list == null) {
							list = Lists.newArrayList();
						}
						list.add(actualPay);
						fcActualPayDetailListMap.put(erpxmbh, list);
						
						orderCost = orderCost+payMoney;
					}
					
					//需计算的项目
					FcSalePrjInfoRest prj = fcSalePrjInfoRestMap.get(erpxmbh);
					if(prj == null){
						prj = new FcSalePrjInfoRest();
						prj.setErpxmbh(erpxmbh);
						prj.setSaleOrg(saleOrg);
						prj.setSaleOrgName(null);
						prj.setBuName(null);
						prj.setLoanRate(loanRate);
						prj.setDepositRate(depositRate);
						prj.setActualStartDate(calcYearMonthBeginDate);
						prj.setActualEndDate(calcYearMonthEndDate);
						prj.setOrderCost(orderCost);
						
						FcProjectInfoRest fpi = fcProjectInfoListMap.get(erpxmbh);
						prj.setFstSvcType(fpi.getFstSvcType());
						prj.setSndSvcType(fpi.getSndSvcType());
						prj.setStartDate(fpi.getStartDate());
						prj.setEndDate(fpi.getEndDate());
						prj.setXmsyCwfy(fpi.getXmsyCwfy());
					}else{
						Double orderCostPrj = prj.getOrderCost()==null?0.0:prj.getOrderCost();
						prj.setOrderCost(orderCostPrj+orderCost);
					}
					fcSalePrjInfoRestMap.put(erpxmbh, prj);
				}
			}
			
			//采购、分包实际付款
			if(fcActualPayDetailList != null){
				for (FcActualPayDetailRest actualPay : fcActualPayDetailList) {
					String payType = actualPay.getPayType();
					Double payMoney = actualPay.getPayMoney();
					Date payDate = actualPay.getPayDate();
					
					String erpxmbh = actualPay.getErpxmbh();
					String saleOrg = actualPay.getSaleOrg();
					//String saleOrgName = saleOrg;//根据配置再调整
					
					if(payMoney == null || payMoney == 0) {
						continue;
					}
					
					if(!fcProjectInfoListMap.containsKey(erpxmbh)){
						LOGGER.debug("采购、分包、费用项目不计算项目：" + erpxmbh);
						continue;
					}
					
					if(!"分包,采购,费用".contains(payType)){
						continue;
					}
					
					//项目税率
					String prjTaxType = "";
					if(fcProjectInfoListMap.containsKey(erpxmbh)) {
						prjTaxType = fcProjectInfoListMap.get(erpxmbh).getTaxType();
					}
					String taxType = actualPay.getTaxType();
					if(StringUtils.isBlank(taxType) && StringUtils.isNotBlank(prjTaxType)) {
						taxType = prjTaxType;
						actualPay.setTaxType(taxType);
					}
					
					if("采购".contains(payType)){
						/*String exchangeType = actualPay.getExchangeType();
						//商票,银票,国内信用证贴现:按照到期日重新设置付款日期
						if(StringUtils.isNotBlank(exchangeType) && "商票,银票,国内信用证贴现".contains(exchangeType)){
							payDate = actualPay.getBillEndDate();
							//调整后超过截止计算日期则不参与计算
							if(payDate.compareTo(calcYearMonthEndDate) > 0){
								continue;
							}
							actualPay.setPayDate(payDate);
						}*/
						Date billEndDate = actualPay.getBillEndDate();
						if(billEndDate != null && billEndDate.compareTo(payDate) > 0){
							payDate = billEndDate;
							//调整后超过截止计算日期则不参与计算
							if(payDate.compareTo(calcYearMonthEndDate) > 0){
								continue;
							}
							actualPay.setPayDate(payDate);
						}
						//采购去税
						Double noTaxMoney = FcUtils.getNoTaxMoney(payMoney, actualPay.getTaxType());
						actualPay.setPayNetMoney(noTaxMoney);
					}else if("分包".contains(payType)){
						//分包去税
						Double noTaxMoney = FcUtils.getNoTaxMoney(payMoney, actualPay.getTaxType());
						actualPay.setPayNetMoney(noTaxMoney);
					}else if("费用".contains(payType)){
						//分包去税
						actualPay.setPayNetMoney(payMoney);
					}

					//actualPay.setSaleOrgName(saleOrgName);
					//actualPay.setBuName(buName);

					//按照项目归集付款
					List<FcActualPayDetailRest> list = fcActualPayDetailListMap.get(erpxmbh);
					if(list == null) {
						list = Lists.newArrayList();
					}
					list.add(actualPay);
					fcActualPayDetailListMap.put(erpxmbh, list);
					
					//需计算的事业部
					FcSalePrjInfoRest prj = fcSalePrjInfoRestMap.get(erpxmbh);
					if(prj == null){
						prj = new FcSalePrjInfoRest();
						prj.setErpxmbh(erpxmbh);
						prj.setSaleOrg(saleOrg);
						prj.setSaleOrgName(null);
						prj.setBuName(null);
						prj.setLoanRate(loanRate);
						prj.setDepositRate(depositRate);
						prj.setActualStartDate(calcYearMonthBeginDate);
						prj.setActualEndDate(calcYearMonthEndDate);
						
						if("费用".equals(payType)){
							prj.setFeeCost(actualPay.getPayNetMoney());
						}else if("采购".contains(payType)){
							prj.setCgCost(actualPay.getPayNetMoney());
						}else if("分包".contains(payType)){
							prj.setSubCost(actualPay.getPayNetMoney());
						}
						
						FcProjectInfoRest fpi = fcProjectInfoListMap.get(erpxmbh);
						prj.setFstSvcType(fpi.getFstSvcType());
						prj.setSndSvcType(fpi.getSndSvcType());
						prj.setStartDate(fpi.getStartDate());
						prj.setEndDate(fpi.getEndDate());
						prj.setXmsyCwfy(fpi.getXmsyCwfy());
					}else{
						if("采购".contains(payType)){
							Double cgCostOrg = prj.getCgCost()==null?0.0:prj.getCgCost();
							prj.setCgCost(cgCostOrg+actualPay.getPayNetMoney());
						}else if("分包".contains(payType)){
							Double subCostOrg = prj.getSubCost()==null?0.0:prj.getSubCost();
							prj.setSubCost(subCostOrg+actualPay.getPayNetMoney());
						}else if("费用".contains(payType)){
							Double feeCostOrg = prj.getFeeCost()==null?0.0:prj.getFeeCost();
							prj.setFeeCost(feeCostOrg+actualPay.getPayNetMoney());
						}
					}
					fcSalePrjInfoRestMap.put(erpxmbh, prj);
				}
			}
			
			//期初值：年初各事业部现金流
			/*if(basSybInitialList != null){
				for (BasSybInitial sybInitial : basSybInitialList) {
					Double payMoney = sybInitial.getInitialLoans()==null?0.0:sybInitial.getInitialLoans();
					Double payProfit = sybInitial.getPayProfit()==null?0.0:sybInitial.getPayProfit();
					Double loadLimit = sybInitial.getLoadLimit();//为空不可置为0
					
					String saleOrg = sybInitial.getSybmc();
					//事业部名称调整
					String saleOrgName = this.getNewSaleOrgName("", saleOrg, sybNewsybMap, xmbhSybMap);
					String buName = sybBuMap.get(saleOrgName);
					
					Date payDate = DateUtils.parseDate(sybInitial.getFiscalYear()+"0101", "yyyyMMdd");
					
					//加入付款
					FcActualPayDetailRest actualPay = new FcActualPayDetailRest();
					actualPay.setPayType("期初");
					actualPay.setSaleOrg(saleOrg);
					actualPay.setSaleOrgName(saleOrgName);
					actualPay.setBuName(buName);
					actualPay.setPayDate(payDate);
					actualPay.setPayMoney(payMoney);
					actualPay.setPayNetMoney(payMoney);

					//按照新事业部名称，设置为财年初第一天
					List<FcActualPayDetailRest> list = fcActualPayDetailListMap.get(saleOrgName);
					if(list == null) {
						list = Lists.newArrayList();
					}
					list.add(actualPay);
					fcActualPayDetailListMap.put(saleOrgName, list);
					
					//需计算的事业部
					FcSaleOrgInfoRest org = fcSaleOrgInfoRestMap.get(saleOrgName);
					if(org == null){
						org = new FcSaleOrgInfoRest();
						org.setSaleOrg(saleOrg);
						org.setSaleOrgName(saleOrgName);
						org.setBuName(buName);
						org.setLoanRate(loanRate);
						org.setDepositRate(depositRate);
						org.setActualStartDate(calcYearMonthBeginDate);
						org.setActualEndDate(calcYearMonthEndDate);
						org.setSybInitial(payMoney);
						org.setPayProfit(payProfit);
						
						FcProjectInfoRest fpi = fcProjectInfoListMap.get(erpxmbh);
						prj.setStartDate(fpi.getStartDate());
						prj.setEndDate(fpi.getEndDate());
						prj.setXmsyCwfy(fpi.getXmsyCwfy());
					}else{
						Double sybInitialOrg = org.getSybInitial()==null?0.0:org.getSybInitial();
						Double payProfitOrg = org.getPayProfit()==null?0.0:org.getPayProfit();
						org.setSybInitial(sybInitialOrg+payMoney);
						org.setPayProfit(payProfitOrg+payProfit);
					}
					//贷款额度
					org.setLoadLimit(loadLimit);
					fcSaleOrgInfoRestMap.put(saleOrgName, org);
				}
			}*/
			
			//没有项目如果没有计算期末，则增加一条0付款，用于计算归集
			//Map<erpxmbh, List<payDate>>
			Map<String, List<String>> salePrjPaydateMap = Maps.newHashMap();
			for(List<FcActualPayDetailRest> list : fcActualPayDetailListMap.values()){
				for(FcActualPayDetailRest payDetailRest : list){
					String erpxmbh = payDetailRest.getErpxmbh();
					Date payDate = payDetailRest.getPayDate();
					String payDateStr = DateUtils.formatDate(payDate, "yyyyMMdd");
					if(salePrjPaydateMap.containsKey(erpxmbh)){
						salePrjPaydateMap.get(erpxmbh).add(payDateStr);
					}else{
						salePrjPaydateMap.put(erpxmbh, Lists.newArrayList(payDateStr));
					}
				}
			}
			for(String erpxmbh : salePrjPaydateMap.keySet()){
				List<String> paydateList = salePrjPaydateMap.get(erpxmbh);
				if(paydateList==null || !paydateList.contains(yyyymmddE)){
					FcSalePrjInfoRest prj = fcSalePrjInfoRestMap.get(erpxmbh);
					FcActualPayDetailRest zeroPay = new FcActualPayDetailRest();
					zeroPay.setErpxmbh(erpxmbh);
					zeroPay.setSaleOrg(prj.getSaleOrg());
					zeroPay.setSaleOrgName(null);
					zeroPay.setBuName(null);
					zeroPay.setPayType("费用");
					zeroPay.setPayDate(calcYearMonthEndDate);
					zeroPay.setPayMoney(0.0);
					zeroPay.setPayNetMoney(0.0);
					zeroPay.setRemark("项目期末自动增加0付款");
					fcActualPayDetailListMap.get(erpxmbh).add(zeroPay);
				}
			}

			//项目实际到款
			if(fcActualReceiptDetailList != null){
				for (FcActualReceiptDetailRest actualReceipt : fcActualReceiptDetailList) {
					Double aeceiptMoney = actualReceipt.getReceiptMoney();
					if(aeceiptMoney == null || aeceiptMoney == 0) {
						continue;
					}
					String erpxmbh = actualReceipt.getErpxmbh();
					String saleOrg = actualReceipt.getSaleOrg();
					
					if(!fcProjectInfoListMap.containsKey(erpxmbh)){
						LOGGER.debug("到款不计算项目：" + erpxmbh);
						continue;
					}
					
					actualReceipt.setSaleOrgName(null);
					actualReceipt.setBuName(null);
					
					//到款去税
					Double aeceiptNetMoney = FcUtils.getNoTaxMoney(aeceiptMoney, actualReceipt.getTaxType());
					actualReceipt.setReceiptNetMoney(aeceiptNetMoney);

					//按照项目归集收款
					List<FcActualReceiptDetailRest> list = fcActualReceiptListMap.get(erpxmbh);
					if(list == null) {
						list = Lists.newArrayList();
					}
					list.add(actualReceipt);
					fcActualReceiptListMap.put(erpxmbh, list);
					
					//需计算的项目
					FcSalePrjInfoRest prj = fcSalePrjInfoRestMap.get(erpxmbh);
					if(prj == null){
						prj = new FcSalePrjInfoRest();
						prj.setErpxmbh(erpxmbh);
						prj.setSaleOrg(saleOrg);
						prj.setSaleOrgName(null);
						prj.setBuName(null);
						prj.setLoanRate(loanRate);
						prj.setDepositRate(depositRate);
						prj.setActualStartDate(calcYearMonthBeginDate);
						prj.setActualEndDate(calcYearMonthEndDate);
						prj.setReceiptNetMoney(actualReceipt.getReceiptNetMoney());
						
						FcProjectInfoRest fpi = fcProjectInfoListMap.get(erpxmbh);
						prj.setFstSvcType(fpi.getFstSvcType());
						prj.setSndSvcType(fpi.getSndSvcType());
						prj.setStartDate(fpi.getStartDate());
						prj.setEndDate(fpi.getEndDate());
						prj.setXmsyCwfy(fpi.getXmsyCwfy());
					}else{
						Double receiptMoneyNotaxOrg = prj.getReceiptNetMoney()==null?0.0:prj.getReceiptNetMoney();
						prj.setReceiptNetMoney(receiptMoneyNotaxOrg+actualReceipt.getReceiptNetMoney());
					}
					fcSalePrjInfoRestMap.put(erpxmbh, prj);
				}
			}
			
			//循环每个项目，包括费用支出
			for (String erpxmbh : fcSalePrjInfoRestMap.keySet()) {
				FcSalePrjInfoRest fspRest = fcSalePrjInfoRestMap.get(erpxmbh);

				fspRest.setFcActualReceiptDetailRestList(fcActualReceiptListMap.get(erpxmbh));
				fspRest.setFcActualPayDetailRestList(fcActualPayDetailListMap.get(erpxmbh));
				
				String remark = "";
				try {
					FcCalcService.calcActualPrjNew(fspRest);
					remark = "计算成功";
				} catch (ParseException e) {
					LOGGER.error(erpxmbh+" 计划外财务费用计算失败", e);
					remark = "计算失败："+e.getMessage();
				}
				
				fspRest.setRemark(remark);
			}
			
			List<FcSalePrjInfoRest> fcSalePrjInfoRestList = Lists.newArrayList();
			List<FcActualCalcCostResultRest> fcActualCalcCostResultList = Lists.newArrayList();
			for (FcSalePrjInfoRest soi : fcSalePrjInfoRestMap.values()) {
				fcSalePrjInfoRestList.add(soi);
				fcActualCalcCostResultList.addAll(soi.getFcActualCalcCostResultRestList());
			}
			List<FcActualPayDetailRest> fcActualPayDetailRestList = Lists.newArrayList();
			for (List<FcActualPayDetailRest> list : fcActualPayDetailListMap.values()) {
				fcActualPayDetailRestList.addAll(list);
			}
			List<FcActualReceiptDetailRest> fcActualReceiptDetailRestList = Lists.newArrayList();
			for (List<FcActualReceiptDetailRest> list : fcActualReceiptListMap.values()) {
				fcActualReceiptDetailRestList.addAll(list);
			}

			//写入excel
			///opt/virtualdir/wbmfile.war/fc/outercost
			String excelFileName = FcUtils.getFcOuterUserFileDir(userId) + File.separator + "计划外财务费用("+prjScopeName+yyyymmddB+"_"+yyyymmddE+")";
			LOGGER.info("文件生成目录:"+excelFileName);
			
			if(!StringUtils.isEmpty(projectCode)) {
				excelFileName = excelFileName + "_" + projectCode;
			}
			excelFileName = excelFileName + "_" + DateUtils.formatDate(new Date(), "yyMMddHHmmsssss") + ".xlsx";

			LOGGER.info("开始写入Excel:"+FcSalePrjInfoRest.exportName);
			ExcelHelper excelFile = SxssfExcelHelper.getInstance(new File(excelFileName));
			excelFile.writeExcel(FcSalePrjInfoRest.class, fcSalePrjInfoRestList, FcSalePrjInfoRest.exportfieldNames, FcSalePrjInfoRest.exportfieldTitles, FcSalePrjInfoRest.exportName);
			LOGGER.info("完成写入Excel:"+FcSalePrjInfoRest.exportName+";开始写入Excel:"+FcActualPayDetailRest.exportName);
			excelFile.writeExcel(FcActualPayDetailRest.class, fcActualPayDetailRestList, FcActualPayDetailRest.exportfieldNamesPrj, FcActualPayDetailRest.exportfieldTitlesPrj, FcActualPayDetailRest.exportName);
			LOGGER.info("完成写入Excel:"+FcActualPayDetailRest.exportName+";开始写入Excel:"+FcActualReceiptDetailRest.exportName);
			excelFile.writeExcel(FcActualReceiptDetailRest.class, fcActualReceiptDetailRestList, FcActualReceiptDetailRest.exportfieldNamesPrj, FcActualReceiptDetailRest.exportfieldTitlesPrj, FcActualReceiptDetailRest.exportName);
			LOGGER.info("完成写入Excel:"+FcActualReceiptDetailRest.exportName+";开始写入Excel:"+FcActualCalcCostResultRest.exportName);
			excelFile.writeExcel(FcActualCalcCostResultRest.class, fcActualCalcCostResultList, FcActualCalcCostResultRest.exportfieldNamesPrj, FcActualCalcCostResultRest.exportfieldTitlesPrj, FcActualCalcCostResultRest.exportName);
			LOGGER.info("完成写入Excel:"+FcActualCalcCostResultRest.exportName);
			
			json.put("code", "0");
			json.put("msg", "计划外财务费用计算成功");
			//System.out.println("end writer json "+new Date());	
		} catch (Exception e) {
			LOGGER.error("计划外财务费用计算失败", e);
			json.put("code", "1");
			json.put("msg", "计划外财务费用计算失败："+e.getMessage());
		}
		
		System.out.println("计算用时："+DateUtils.formatDateTime(System.currentTimeMillis()-start));
		
		return json.toString();
	}
	
	/**
	 * 按照事业部现金流计算实际财务费用：按事业部
	 * 分为两部分
	 * 	1.项目收付：取项目所有的收付款（采购不含税、分包不含税、锐行产品成本边界），
	 * 		从1.1到12.31止逐笔计算现金流，
	 * 		即将所有项目看成一个大项目计算，按天计息；
	 * 	2.事业部费用支出和期初：取部门所有的费用支出、单次项目支出，其中1月份需加上年起初资金占用，按月计息；
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/service/fc/calcActualOrg", method = RequestMethod.POST, produces = "application/json")
	public String calcActualCostByOrg(HttpServletRequest request) {
		long start = System.currentTimeMillis();

		String line;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		
		JSONObject json = new JSONObject();
		try{
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
			JSONObject jsonReq = JSON.parseObject(sb.toString());

			String calcYearMonth = jsonReq.getString("calcYearMonth");
			String projectCode = jsonReq.getString("projectCode");
			//String prjScope = jsonReq.getString("prjScope");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月
			//String insertDB = jsonReq.getString("insertDB");//A0:结果入库/A1:结果不入库
			String userId = jsonReq.getString("userId");
			String prjScopeName = "计算期间";
			
			//解析计算月份（如201601-201612）
			Date calcYearMonthBeginDate = null;
			Date calcYearMonthEndDate = null;
			if(calcYearMonth != null){
				String[] calcYearMonthArray = calcYearMonth.split("-");
				if(calcYearMonthArray.length == 1){
					calcYearMonthBeginDate = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(calcYearMonthArray[0],"yyyyMMdd"));
					calcYearMonthEndDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthArray[0],"yyyyMMdd"));
				}else if(calcYearMonthArray.length == 2){
					calcYearMonthBeginDate = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(calcYearMonthArray[0],"yyyyMMdd"));
					calcYearMonthEndDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthArray[1],"yyyyMMdd"));
				}
			}
			
			if(calcYearMonthBeginDate.after(calcYearMonthEndDate)){
				throw new ServiceException("计算月份异常");
			}
			
			Date maxPayDate = fcActualPayDetailService.getMaxPayDate();
			if(maxPayDate == null ) {
				maxPayDate = calcYearMonthBeginDate;
			}
			
			//当前计算日期
			Date curCalcDate = DateUtils.parseDate(DateUtils.getDate("yyyyMMdd"),"yyyyMMdd");
			
			//从BI插入新增增量付款明细
			if(maxPayDate.compareTo(curCalcDate) < 0) {
				maxPayDate = DateUtils.addDays(maxPayDate, 1);
				//fcActualPayDetailService.updateCgfkFromOA(DateUtils.formatDate(maxPayDate, "yyyy-MM-dd"), DateUtils.formatDate(calcYearMonthEndDate, "yyyy-MM-dd"), null);

				//insertCgfkFromBI 直接从BI获取
				//truncate table bi.its_cgfk_fwsbu_bi;
                //insert into bi.its_cgfk_fwsbu_bi
                //select ROWNUM FK_ID,b.CGDDDM,b.XQGZH,b.GYSDM,b.GYSMC,b.GSDM,b.SBUMC,b.BUMC,b.SYBMC,b.YWFWDM,b.YWFWMC,b.FKPZDM,b.FKJE,b.FKRQ,NULL PAYS,b.FKRQ INVSDATE,b.QZRQ INVEDATE,NULL FORMID,SYSDATE CREATE_DATE,NULL REMARKS from bi.vw_its_cgfk_fwsbu_bi b;
			}
			
			//从BI插入新增增量到款明细
			//truncate table bi.ITS_SKYH_FWSBU_BI;
            //insert into bi.ITS_SKYH_FWSBU_BI
            //select b.KJND,b.YSKMDM,b.YHKMDM,b.PZLX,b.GSDM,b.YWFWDM,b.YWFWMC,b.JTMC,b.KHDM,b.KHMC,b.DKRQ,b.DKJE,b.DYMC,b.SBUMC,b.XMH,b.XMMC,b.XMZTMS,sysdate,null from bi.vw_its_skyh_fwsbu_bi b

			//从CRM系统获取计划收款信息
//			truncate table bi.GATHER_MONEY_PLAN_CRM;
//			insert into bi.GATHER_MONEY_PLAN_CRM
//			select sys_guid(),b.PROJECT_CODE,b.ERPXMBH,b.BUSINESS_ID,b.GATHER_MONEY_PROP,b.PLAN_GATHER_MONEY_DATE,b.PLAN_GATHER_MONEY_NUM,b.HTLXSPJSRQ,b.ID,sysdate,'20181023更新'
//			from bi.VW_GATHER_MONEY_PLAN_CRM b

			String yyyymmddB = DateUtils.formatDate(calcYearMonthBeginDate, "yyyyMMdd");
			String yyyymmddE = DateUtils.formatDate(calcYearMonthEndDate, "yyyyMMdd");
			
			//财年
			String fy = yyyymmddE.substring(0, 4);
			
			if(!fy.equals(yyyymmddB.substring(0, 4))){
				throw new ServiceException("不能跨年计算："+calcYearMonth);
			}
			
			//收款
			List<FcActualReceiptDetailRest> fcActualReceiptDetailList = fcActualReceiptDetailService.findListByActual(yyyymmddB, yyyymmddE, projectCode);
			//订单
			List<FcActualOrderInfoRest> fcActualOrderInfoList = fcOrderInfoService.findListByActual(yyyymmddB, yyyymmddE, projectCode);
			//付款：分包、采购、费用
			List<FcActualPayDetailRest> fcActualPayDetailList = fcActualPayDetailService.findListByActualPayTime(yyyymmddB, yyyymmddE, projectCode);

			//期初
			List<BasSybInitial> basSybInitialList = basSybInitialService.findListByFiscalYear(fy);
			
			//事业部新旧名称对应关系
			Map<String, String> sybNewsybMap = basSybNewsybRelService.findMapByFy(fy);
			Map<String, String> xmbhSybMap = basSybXmbhRelService.findMapByFy(fy);
			Map<String, String> sybBuMap = basSybBuRelService.findMapByFy(fy);
			Map<String, String> cbzxSybMap = basSybCbzxRelService.findMapByFy(fy);
			
			//需要计算的事业部：Map<事业部名称, FcSaleOrgInfoRest>
			Map<String, FcSaleOrgInfoRest> fcSaleOrgInfoRestMap = Maps.newHashMap();
			//付款信息（支付类型:订单、分包、采购、费用、期初）
			Map<String, List<FcActualPayDetailRest>> fcActualPayDetailListMap = Maps.newHashMap();
			//到款信息
			Map<String, List<FcActualReceiptDetailRest>> fcActualReceiptListMap = Maps.newHashMap();
						
			double loanRate = FcUtils.getFcLoanRateLatest("OUTER");	//最新贷款利率
			double depositRate = FcUtils.getFcDepositRateLatest(null);// 最新存款利率
			
			//订单自有成本
			if(fcActualPayDetailList != null){
				for (FcActualOrderInfoRest order : fcActualOrderInfoList) {
					//自有成本=自有交付成本+托管成本
					Double calcProdCost = order.getOwnCost()+order.getTgCost();
					
					if(calcProdCost == null || calcProdCost == 0) {
						continue;
					}
					
					String erpxmbh = order.getErpxmbh();			// 财务项目编号
					String saleOrg = order.getSaleOrg();			// 事业部
					//事业部名称调整
					String saleOrgName = this.getNewSaleOrgName(erpxmbh, saleOrg, sybNewsybMap, xmbhSybMap);
					String buName = sybBuMap.get(saleOrgName);			// BU名称
					
					//项目服务期间隔
					double daysBw = DateUtils.getDistanceOfTwoDate(order.getStartDate(),order.getEndDate());
					//每天成本单价
					double dayCost = calcProdCost / daysBw;
					
					// 边界服务期开始
					Date borderStartDate = order.getStartDate();
					if(borderStartDate.compareTo(calcYearMonthBeginDate) < 0){
						borderStartDate = calcYearMonthBeginDate;
					}
					
					// 边界服务期结束
					Date borderEndDate = order.getEndDate();
					if(borderEndDate.compareTo(calcYearMonthEndDate) > 0){
						borderEndDate = calcYearMonthEndDate;
					}
					
					List<String> monthBw = DateUtils.getMonthBetween(borderStartDate, borderEndDate); 
					
					//项目订单边界成本
					Double orderCost = 0.0;
					
					int monthSize = monthBw.size();
					for(int i=0;i<monthSize;i++){
						String month = monthBw.get(i);
						//月末
						Date firstDayOfMonth = DateUtils.getFirstDayOfMonth(DateUtils.parseDate(month));
						Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateUtils.parseDate(month));
						Date begin = null;
						Date end = null;
						
						//第一个月成本
						if(i==0){
							begin = borderStartDate;
							end = lastDayOfMonth;
						}else if(i==monthSize-1){
							begin = firstDayOfMonth;
							end = borderEndDate;
						}else{
							begin = firstDayOfMonth;
							end = lastDayOfMonth;
						}
						
						//每月成本付款
						double orderPayAmount = dayCost * (DateUtils.getDistanceOfTwoDate(begin, end));
						orderPayAmount = BigDecimal.valueOf(orderPayAmount).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						Date payDate = (i==monthSize-1)? borderEndDate : lastDayOfMonth;	// 付款日期
						
						//payDate = DateUtils.parseDate(DateUtils.formatDate(payDate, "yyyyMM")+"20", "yyyyMMdd");
						
						Double payMoney = orderPayAmount;	// 付款金额
						
						//加入付款
						FcActualPayDetailRest actualPay = new FcActualPayDetailRest();
						actualPay.setPayType("订单");
						actualPay.setSaleOrg(saleOrg);
						actualPay.setSaleOrgName(saleOrgName);
						actualPay.setBuName(buName);
						actualPay.setErpxmbh(erpxmbh);
						actualPay.setPayDate(payDate);
						actualPay.setPayMoney(payMoney);
						actualPay.setPayNetMoney(payMoney);

						//按照新事业部名称，归集订单每月成本边界
						List<FcActualPayDetailRest> list = fcActualPayDetailListMap.get(saleOrgName);
						if(list == null) {
							list = Lists.newArrayList();
						}
						list.add(actualPay);
						fcActualPayDetailListMap.put(saleOrgName, list);
						
						orderCost = orderCost+payMoney;
					}
					
					//需计算的事业部
					FcSaleOrgInfoRest org = fcSaleOrgInfoRestMap.get(saleOrgName);
					if(org == null){
						org = new FcSaleOrgInfoRest();
						org.setSaleOrg(saleOrg);
						org.setSaleOrgName(saleOrgName);
						org.setBuName(buName);
						org.setLoanRate(loanRate);
						org.setDepositRate(depositRate);
						org.setActualStartDate(calcYearMonthBeginDate);
						org.setActualEndDate(calcYearMonthEndDate);
						org.setOrderCost(orderCost);
					}else{
						Double orderCostOrg = org.getOrderCost()==null?0.0:org.getOrderCost();
						org.setOrderCost(orderCostOrg+orderCost);
					}
					fcSaleOrgInfoRestMap.put(saleOrgName, org);
				}
			}
			
			//期初值：年初各事业部现金流
			if(basSybInitialList != null){
				for (BasSybInitial sybInitial : basSybInitialList) {
					Double payMoney = sybInitial.getInitialLoans()==null?0.0:sybInitial.getInitialLoans();
					Double payProfit = sybInitial.getPayProfit()==null?0.0:sybInitial.getPayProfit();
					Double loadLimit = sybInitial.getLoadLimit();//为空不可置为0
					
					String saleOrg = sybInitial.getSybmc();
					//事业部名称调整
					String saleOrgName = this.getNewSaleOrgName("", saleOrg, sybNewsybMap, xmbhSybMap);
					String buName = sybBuMap.get(saleOrgName);
					
					Date payDate = DateUtils.parseDate(sybInitial.getFiscalYear()+"0101", "yyyyMMdd");
					
					//加入付款
					FcActualPayDetailRest actualPay = new FcActualPayDetailRest();
					actualPay.setPayType("期初");
					actualPay.setSaleOrg(saleOrg);
					actualPay.setSaleOrgName(saleOrgName);
					actualPay.setBuName(buName);
					actualPay.setPayDate(payDate);
					actualPay.setPayMoney(payMoney);
					actualPay.setPayNetMoney(payMoney);

					//按照新事业部名称，设置为财年初第一天
					List<FcActualPayDetailRest> list = fcActualPayDetailListMap.get(saleOrgName);
					if(list == null) {
						list = Lists.newArrayList();
					}
					list.add(actualPay);
					fcActualPayDetailListMap.put(saleOrgName, list);
					
					//需计算的事业部
					FcSaleOrgInfoRest org = fcSaleOrgInfoRestMap.get(saleOrgName);
					if(org == null){
						org = new FcSaleOrgInfoRest();
						org.setSaleOrg(saleOrg);
						org.setSaleOrgName(saleOrgName);
						org.setBuName(buName);
						org.setLoanRate(loanRate);
						org.setDepositRate(depositRate);
						org.setActualStartDate(calcYearMonthBeginDate);
						org.setActualEndDate(calcYearMonthEndDate);
						org.setSybInitial(payMoney);
						org.setPayProfit(payProfit);
					}else{
						Double sybInitialOrg = org.getSybInitial()==null?0.0:org.getSybInitial();
						Double payProfitOrg = org.getPayProfit()==null?0.0:org.getPayProfit();
						org.setSybInitial(sybInitialOrg+payMoney);
						org.setPayProfit(payProfitOrg+payProfit);
					}
					//贷款额度
					org.setLoadLimit(loadLimit);
					fcSaleOrgInfoRestMap.put(saleOrgName, org);
				}
			}
			
			//采购、分包、事业部费用支出
			if(fcActualPayDetailList != null){
				for (FcActualPayDetailRest actualPay : fcActualPayDetailList) {
					String payType = actualPay.getPayType();
					Double payMoney = actualPay.getPayMoney();
					Date payDate = actualPay.getPayDate();
					
					if(payMoney == null || payMoney == 0) {
						continue;
					}
					
					if(!"分包,采购,费用".contains(payType)){
						continue;
					}
					
					String erpxmbh = actualPay.getErpxmbh();
					String saleOrg = actualPay.getSaleOrg();
					String saleOrgName = saleOrg;//根据配置再调整
					
					//费用信息（事业部费用支出），事业部看成一个项目
					if("费用".equals(payType)){
						//从事业部的核算结构查询事业部名称,其中返回的erpxmbh对应成本中心代码
						saleOrg = cbzxSybMap.containsKey(erpxmbh)?cbzxSybMap.get(erpxmbh):saleOrg;
						saleOrgName = this.getNewSaleOrgName("", saleOrg, sybNewsybMap, xmbhSybMap);
						
						//费用按照月计算利息
						Date newPayDate = DateUtils.getLastDayOfMonth(payDate);
						//Date newPayDate = DateUtils.parseDate(DateUtils.formatDate(payDate, "yyyyMM")+"15","yyyyMMdd");
						actualPay.setPayDate(newPayDate);
						actualPay.setPayNetMoney(payMoney);
						
						//按照qijuan给的事业部费用
						/*saleOrgName = this.getNewSaleOrgName("", saleOrg, sybNewsybMap, xmbhSybMap);
						Date newPayDate = DateUtils.parseDate(DateUtils.formatDate(payDate, "yyyyMM")+"15","yyyyMMdd");
						
						//费用按照月计算利息
						//Date lastDayOfMonth = DateUtils.getLastDayOfMonth(payDate);
						actualPay.setPayDate(newPayDate);
						actualPay.setPayNetMoney(payMoney);*/
					}else if("采购".contains(payType)){
						saleOrgName = this.getNewSaleOrgName(erpxmbh, saleOrg, sybNewsybMap, xmbhSybMap);
						
						String exchangeType = actualPay.getExchangeType();
						//商票,银票,国内信用证贴现:按照到期日重新设置付款日期
						if(StringUtils.isNotBlank(exchangeType) && "商票,银票,国内信用证贴现".contains(exchangeType)){
							payDate = actualPay.getBillEndDate();
							//调整后超过截止计算日期则不参与计算
							if(payDate.compareTo(calcYearMonthEndDate) > 0){
								continue;
							}
							actualPay.setPayDate(payDate);
						}
						//采购去税
						Double noTaxMoney = FcUtils.getNoTaxMoney(payMoney, actualPay.getTaxType());
						actualPay.setPayNetMoney(noTaxMoney);
					}else if("分包".contains(payType)){
						saleOrgName = this.getNewSaleOrgName(erpxmbh, saleOrg, sybNewsybMap, xmbhSybMap);
						
						//分包去税
						Double noTaxMoney = FcUtils.getNoTaxMoney(payMoney, actualPay.getTaxType());
						actualPay.setPayNetMoney(noTaxMoney);
					}

					String buName = sybBuMap.get(saleOrgName);
					actualPay.setSaleOrgName(saleOrgName);
					actualPay.setBuName(buName);

					//按照新事业部名称，归集付款
					List<FcActualPayDetailRest> list = fcActualPayDetailListMap.get(saleOrgName);
					if(list == null) {
						list = Lists.newArrayList();
					}
					list.add(actualPay);
					fcActualPayDetailListMap.put(saleOrgName, list);
					
					//需计算的事业部
					FcSaleOrgInfoRest org = fcSaleOrgInfoRestMap.get(saleOrgName);
					if(org == null){
						org = new FcSaleOrgInfoRest();
						org.setSaleOrg(saleOrg);
						org.setSaleOrgName(saleOrgName);
						org.setBuName(buName);
						org.setLoanRate(loanRate);
						org.setDepositRate(depositRate);
						org.setActualStartDate(calcYearMonthBeginDate);
						org.setActualEndDate(calcYearMonthEndDate);
						
						if("费用".equals(payType)){
							org.setSybFee(actualPay.getPayNetMoney());
						}else if("采购".contains(payType)){
							org.setCgCost(actualPay.getPayNetMoney());
						}else if("分包".contains(payType)){
							org.setSubCost(actualPay.getPayNetMoney());
						}
					}else{
						if("费用".equals(payType)){
							Double sybFeeOrg = org.getSybFee()==null?0.0:org.getSybFee();
							org.setSybFee(sybFeeOrg+actualPay.getPayNetMoney());
						}else if("采购".contains(payType)){
							Double cgCostOrg = org.getCgCost()==null?0.0:org.getCgCost();
							org.setCgCost(cgCostOrg+actualPay.getPayNetMoney());
						}else if("分包".contains(payType)){
							Double subCostOrg = org.getSubCost()==null?0.0:org.getSubCost();
							org.setSubCost(subCostOrg+actualPay.getPayNetMoney());
						}
					}
					fcSaleOrgInfoRestMap.put(saleOrgName, org);
				}
			}
			
			//没有事业部如果没有计算期末，则增加一条0付款，用于计算归集
			//Map<saleOrgName, List<payDate>>
			Map<String, List<String>> saleOrgPaydateMap = Maps.newHashMap();
			for(List<FcActualPayDetailRest> list : fcActualPayDetailListMap.values()){
				for(FcActualPayDetailRest payDetailRest : list){
					String saleOrgName = payDetailRest.getSaleOrgName();
					Date payDate = payDetailRest.getPayDate();
					String payDateStr = DateUtils.formatDate(payDate, "yyyyMMdd");
					if(saleOrgPaydateMap.containsKey(saleOrgName)){
						saleOrgPaydateMap.get(saleOrgName).add(payDateStr);
					}else{
						saleOrgPaydateMap.put(saleOrgName, Lists.newArrayList(payDateStr));
					}
				}
			}
			for(String saleOrgName : saleOrgPaydateMap.keySet()){
				List<String> paydateList = saleOrgPaydateMap.get(saleOrgName);
				if(paydateList==null || !paydateList.contains(yyyymmddE)){
					FcSaleOrgInfoRest org = fcSaleOrgInfoRestMap.get(saleOrgName);
					FcActualPayDetailRest zeroPay = new FcActualPayDetailRest();
					zeroPay.setSaleOrg(org.getSaleOrg());
					zeroPay.setSaleOrgName(saleOrgName);
					zeroPay.setBuName(org.getBuName());
					zeroPay.setPayType("费用");
					zeroPay.setPayDate(calcYearMonthEndDate);
					zeroPay.setPayMoney(0.0);
					zeroPay.setPayNetMoney(0.0);
					zeroPay.setRemark("事业部期末自动增加0付款");
					fcActualPayDetailListMap.get(saleOrgName).add(zeroPay);
				}
			}

			//项目实际到款
			if(fcActualReceiptDetailList != null){
				for (FcActualReceiptDetailRest actualReceipt : fcActualReceiptDetailList) {
					Double aeceiptMoney = actualReceipt.getReceiptMoney();
					if(aeceiptMoney == null || aeceiptMoney == 0) {
						continue;
					}
					String saleOrg = actualReceipt.getSaleOrg();
					//事业部名称调整
					String saleOrgName = this.getNewSaleOrgName(actualReceipt.getErpxmbh(), saleOrg, sybNewsybMap, xmbhSybMap);
					String buName = sybBuMap.get(saleOrgName);
					actualReceipt.setSaleOrgName(saleOrgName);
					actualReceipt.setBuName(buName);
					
					//到款去税
					Double aeceiptNetMoney = FcUtils.getNoTaxMoney(aeceiptMoney, actualReceipt.getTaxType());
					actualReceipt.setReceiptNetMoney(aeceiptNetMoney);

					//按照新事业部名称，归集收款
					List<FcActualReceiptDetailRest> list = fcActualReceiptListMap.get(saleOrgName);
					if(list == null) {
						list = Lists.newArrayList();
					}
					list.add(actualReceipt);
					fcActualReceiptListMap.put(saleOrgName, list);
					
					//需计算的事业部
					FcSaleOrgInfoRest org = fcSaleOrgInfoRestMap.get(saleOrgName);
					if(org == null){
						org = new FcSaleOrgInfoRest();
						org.setSaleOrg(saleOrg);
						org.setSaleOrgName(saleOrgName);
						org.setBuName(buName);
						org.setLoanRate(loanRate);
						org.setDepositRate(depositRate);
						org.setActualStartDate(calcYearMonthBeginDate);
						org.setActualEndDate(calcYearMonthEndDate);
						org.setReceiptNetMoney(actualReceipt.getReceiptNetMoney());
					}else{
						Double receiptMoneyNotaxOrg = org.getReceiptNetMoney()==null?0.0:org.getReceiptNetMoney();
						org.setReceiptNetMoney(receiptMoneyNotaxOrg+actualReceipt.getReceiptNetMoney());
					}
					fcSaleOrgInfoRestMap.put(saleOrgName, org);
				}
			}
			
			//循环每个项目，包括费用支出
			int c = 0;
			for (String saleOrgName : fcSaleOrgInfoRestMap.keySet()) {
				FcSaleOrgInfoRest fsoRest = fcSaleOrgInfoRestMap.get(saleOrgName);

				fsoRest.setFcActualReceiptDetailRestList(fcActualReceiptListMap.get(saleOrgName));
				fsoRest.setFcActualPayDetailRestList(fcActualPayDetailListMap.get(saleOrgName));
				
				String remark = "";
				try {
					FcCalcService.calcActualOrg(fsoRest);
					remark = "计算成功";
				} catch (ParseException e) {
					LOGGER.error(saleOrgName+" 财务费用计算失败", e);
					remark = "计算失败："+e.getMessage();
				}
				
				fsoRest.setRemark(remark);

				c++;
				
				System.out.println(c + " | " +saleOrgName + " | " +fsoRest.getFinancialCost());
			}
			
			
			List<FcSaleOrgInfoRest> fcSaleOrgInfoRestList = Lists.newArrayList();
			List<FcActualCalcCostResultRest> fcActualCalcCostResultList = Lists.newArrayList();
			for (FcSaleOrgInfoRest soi : fcSaleOrgInfoRestMap.values()) {
				fcSaleOrgInfoRestList.add(soi);
				fcActualCalcCostResultList.addAll(soi.getFcActualCalcCostResultRestList());
			}
			List<FcActualPayDetailRest> fcActualPayDetailRestList = Lists.newArrayList();
			for (List<FcActualPayDetailRest> list : fcActualPayDetailListMap.values()) {
				fcActualPayDetailRestList.addAll(list);
			}
			List<FcActualReceiptDetailRest> fcActualReceiptDetailRestList = Lists.newArrayList();
			for (List<FcActualReceiptDetailRest> list : fcActualReceiptListMap.values()) {
				fcActualReceiptDetailRestList.addAll(list);
			}

			//写入excel
			///opt/virtualdir/wbmfile.war/fc/outercost
			String excelFileName = FcUtils.getFcOuterUserFileDir(userId) + File.separator + "财务费用("+prjScopeName+yyyymmddE+")";
			LOGGER.info("文件生成目录:"+excelFileName);
			
			if(!StringUtils.isEmpty(projectCode)) {
				excelFileName = excelFileName + "_" + projectCode;
			}
			excelFileName = excelFileName + "_" + DateUtils.formatDate(new Date(), "yyMMddHHmmsssss") + ".xlsx";

			LOGGER.info("开始写入Excel:"+FcSaleOrgInfoRest.exportName);
			ExcelHelper excelFile = SxssfExcelHelper.getInstance(new File(excelFileName));
			excelFile.writeExcel(FcSaleOrgInfoRest.class, fcSaleOrgInfoRestList, FcSaleOrgInfoRest.exportfieldNames, FcSaleOrgInfoRest.exportfieldTitles, FcSaleOrgInfoRest.exportName);
			LOGGER.info("完成写入Excel:"+FcSaleOrgInfoRest.exportName+";开始写入Excel:"+FcActualPayDetailRest.exportName);
			excelFile.writeExcel(FcActualPayDetailRest.class, fcActualPayDetailRestList, FcActualPayDetailRest.exportfieldNames, FcActualPayDetailRest.exportfieldTitles, FcActualPayDetailRest.exportName);
			LOGGER.info("完成写入Excel:"+FcActualPayDetailRest.exportName+";开始写入Excel:"+FcActualReceiptDetailRest.exportName);
			//excelFile.writeExcel(FcActualReceiptDetailRest.class, fcActualReceiptDetailRestList, FcActualReceiptDetailRest.exportfieldNames, FcActualReceiptDetailRest.exportfieldTitles, FcActualReceiptDetailRest.exportName);
			LOGGER.info("完成写入Excel:"+FcActualReceiptDetailRest.exportName+";开始写入Excel:"+FcActualCalcCostResultRest.exportName);
			//excelFile.writeExcel(FcActualCalcCostResultRest.class, fcActualCalcCostResultList, FcActualCalcCostResultRest.exportfieldNames, FcActualCalcCostResultRest.exportfieldTitles, FcActualCalcCostResultRest.exportName);
			LOGGER.info("完成写入Excel:"+FcActualCalcCostResultRest.exportName);
			
			
			/*
			List<String[]> fcSaleOrgInfoRestList = Lists.newArrayList();
			List<String[]> fcActualCalcCostResultList = Lists.newArrayList();
			for (FcSaleOrgInfoRest soi : fcSaleOrgInfoRestMap.values()) {
				fcSaleOrgInfoRestList.add(new String[] {soi.getSaleOrg(),soi.getSaleOrgName(),soi.getBuName(),String.valueOf(soi.getSybInitial()),String.valueOf(soi.getSybFee()),String.valueOf(soi.getOrderCost()),
						String.valueOf(soi.getSubCost()),String.valueOf(soi.getCgCost()),String.valueOf(soi.getReceiptNetMoney()),String.valueOf(soi.getLoanRate()),String.valueOf(soi.getDepositRate()),
						DateUtils.formatDate(soi.getActualStartDate()),DateUtils.formatDate(soi.getActualEndDate()),
						String.valueOf(soi.getFinancialCost()),String.valueOf(soi.getEndBalance()),String.valueOf(soi.getPayProfit()),String.valueOf(soi.getNeedLoans()),soi.getRemark()});
				
				for(FcActualCalcCostResultRest acc : soi.getFcActualCalcCostResultRestList()) {
					fcActualCalcCostResultList.add(new String[] {acc.getSaleOrg(),acc.getSaleOrgName(),acc.getBuName(),acc.getPayReceiptDate()==null?null:DateUtils.formatDate(acc.getPayReceiptDate()),String.valueOf(acc.getPayNetMoney()),
					String.valueOf(acc.getReceiptNetMoney()),String.valueOf(acc.getRestNetMoney()),String.valueOf(acc.getFinancialCost()),acc.getRemark()});
				}
			}
			List<String[]> fcActualPayDetailRestList = Lists.newArrayList();
			for (List<FcActualPayDetailRest> list : fcActualPayDetailListMap.values()) {
				for(FcActualPayDetailRest apd : list) {
					fcActualPayDetailRestList.add(new String[] {apd.getSaleOrg(),apd.getSaleOrgName(),apd.getBuName(),apd.getErpxmbh(),apd.getTaxType(),apd.getPayType(),apd.getExchangeType(),
							apd.getBillStartDate()==null?null:DateUtils.formatDate(apd.getBillStartDate()),apd.getBillEndDate()==null?null:DateUtils.formatDate(apd.getBillEndDate()),apd.getPayDate()==null?null:DateUtils.formatDate(apd.getPayDate()),String.valueOf(apd.getPayMoney()),String.valueOf(apd.getPayNetMoney()),apd.getRemark()});
				}
			}
			List<String[]> fcActualReceiptDetailRestList = Lists.newArrayList();
			for (List<FcActualReceiptDetailRest> list : fcActualReceiptListMap.values()) {
				for(FcActualReceiptDetailRest ard : list) {
					fcActualReceiptDetailRestList.add(new String[] {ard.getSaleOrg(),ard.getSaleOrgName(),ard.getBuName(),ard.getErpxmbh(),ard.getTaxType(),
							ard.getReceiptDate()==null?null:DateUtils.formatDate(ard.getReceiptDate()),String.valueOf(ard.getReceiptMoney()),String.valueOf(ard.getReceiptNetMoney()),ard.getRemark()});
				}
			}

			//写入csv
			///opt/virtualdir/wbmfile.war/fc/outercost
			String excelFileName = getFcOuterUserFileDir(userId) + File.separator + "财务费用("+prjScopeName+yyyymmddE+")";
			if(!StringUtils.isEmpty(projectCode)) excelFileName = excelFileName + "_" + projectCode;
			String excelFileNameNofix = excelFileName + "_" + DateUtils.formatDate(new Date(), "yyMMddHHmmsssss");
			
			CsvHelper csvHelper = OpenCsvHelper.getInstance(excelFileNameNofix+"-"+FcSaleOrgInfoRest.exportName+".csv");
			csvHelper.writeCsv(FcSaleOrgInfoRest.exportfieldTitles, fcSaleOrgInfoRestList);
			
			csvHelper = OpenCsvHelper.getInstance(excelFileNameNofix+"-"+FcActualCalcCostResultRest.exportName+".csv");
			csvHelper.writeCsv(FcActualCalcCostResultRest.exportfieldTitles, fcActualCalcCostResultList);
			
			csvHelper = OpenCsvHelper.getInstance(excelFileNameNofix+"-"+FcActualPayDetailRest.exportName+".csv");
			csvHelper.writeCsv(FcActualPayDetailRest.exportfieldTitles, fcActualPayDetailRestList);
			
			csvHelper = OpenCsvHelper.getInstance(excelFileNameNofix+"-"+FcActualReceiptDetailRest.exportName+".csv");
			csvHelper.writeCsv(FcActualReceiptDetailRest.exportfieldTitles, fcActualReceiptDetailRestList);
			*/
			
			json.put("code", "0");
			json.put("msg", "财务费用计算成功");
			//System.out.println("end writer json "+new Date());	
		} catch (Exception e) {
			LOGGER.error("财务费用计算失败", e);
			json.put("code", "1");
			json.put("msg", "财务费用计算失败："+e.getMessage());
		}
		
		System.out.println("计算用时："+DateUtils.formatDateTime(System.currentTimeMillis()-start));
		
		return json.toString();
	}
	
}
