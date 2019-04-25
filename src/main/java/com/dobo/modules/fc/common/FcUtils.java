package com.dobo.modules.fc.common;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import com.dobo.common.config.Global;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.EhCacheUtils;
import com.dobo.common.utils.SpringContextHolder;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.fc.dao.FcInterrestRateDao;
import com.dobo.modules.fc.rest.entity.FcSaleOrgInfoRest;


/**
 * 系统配置缓存工具类
 */
public class FcUtils {
	
	private static FcInterrestRateDao fcInterrestRateDao = SpringContextHolder.getBean(FcInterrestRateDao.class);	//利率定义

	/**
	 * 财务费用计算参数缓存
	 */
	public static String FC_FCPLANFEECALC_CACHE = "fcFcPlanFeeCalcCache";
    
    /**
     * 根据分包和采购付款去税算法
     * 
     * @param pay
     * @param taxName
     * @return
     */
    public static Double getNoTaxMoney(Double pay, String taxName){
		
    	/*
	    	01	17%增值税	未税=含税/(1+17%)	0.17
			02	5%营业税	未税=含税*(1-5%)	0.05
			03	3%营业税	未税=含税*(1-3%)	0.03
			04	征17%退14%增值税		
			05	0	未税=含税*1	0
			00	其它		
			06	6%增值税	未税=含税/(1+6%)	0.06
			07	3%增值税	未税=含税/(1+3%)	0.03
		*/
    	
    	if(pay == null){
    		return 0.0;
    	}

    	Double ret = pay;
    	
    	if(StringUtils.isNotBlank(taxName)){	
			if("17%增值税,征17%退14%增值税,6%增值税,3%增值税".contains(taxName)){
				int tax = 0;
				if("征17%退14%增值税".equals(taxName)){
					tax = 17;
				}else{
					tax = Integer.valueOf(taxName.split("%")[0]);
				}
				BigDecimal taxPercent = BigDecimal.valueOf(tax).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
				//签约净额/(1+税)
				ret = BigDecimal.valueOf(pay).divide(BigDecimal.ONE.add(taxPercent),2,BigDecimal.ROUND_HALF_UP).doubleValue();
			}else if("5%营业税,3%营业税".contains(taxName)){
				int tax = Integer.valueOf(taxName.split("%")[0]);
				BigDecimal taxPercent = BigDecimal.valueOf(tax).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
				//签约净额*(1-税)
				ret = BigDecimal.valueOf(pay).multiply(BigDecimal.ONE.subtract(taxPercent)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			}
    	}
		
		return ret;
    }
	
	/**
	 * 计划内财务费用文件目录
	 * @param userId
	 * @return
	 */
	public static String getFcInnerUserFileDir(String userId){
		return Global.getUserfilesBaseDir() 
				+ File.separator + "userfiles" + File.separator + userId
				+ File.separator + "files" + File.separator + "fc" + File.separator + "inner";
	}

	/**
	 * 计划外财务费用文件目录
	 * @param userId
	 * @return
	 */
	public static String getFcOuterUserFileDir(String userId){
		return Global.getUserfilesBaseDir() 
				+ File.separator + "userfiles" + File.separator + userId 
				+ File.separator + "files" + File.separator + "fc" + File.separator + "outer";
	}
	
	/**
	 * 计算存贷息
	 * 
	 * @param amount 参与存贷息计算总金额
	 * @param rateValue 存贷息利率
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static double calcInterrestCost(double amount, double rateValue, Date start, Date end){
		double intervalDays = DateUtils.getDistanceOfTwoDate(start, end) - 1;
		intervalDays = intervalDays < 0 ? 0 : intervalDays;
		//System.out.println("intervalDays:"+intervalDays);
		return BigDecimal.valueOf(Math.abs(amount) * rateValue * intervalDays / 365).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
	}
	
	/**
	 * 计算贷息
	 * 事业部贷款超过600万按照15%超贷利率，其他按照超过300万15%超贷
	 * 
	 * @param amount 参与贷息计算总金额
	 * @param rateValue 贷息利率
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param fsoRest
	 * @return
	 */
	public static double calcLoanCost(double amount, double rateValue, Date start, Date end, FcSaleOrgInfoRest fsoRest){
		double cost = 0.0;
		double passRateValue = 0.15;//超贷利率
		Double loadLimit = fsoRest.getLoadLimit();//贷款额度系统初始为负值
		
		if(loadLimit !=null && amount < loadLimit){
			double cost1 = calcInterrestCost(loadLimit, rateValue, start, end);
			double cost2 = calcInterrestCost(amount-(loadLimit), passRateValue, start, end);
			cost = cost1 + cost2;
		}else{
			cost = calcInterrestCost(amount, rateValue, start, end);
		}

		 return cost;
	}
	
	/**
	 * 计算贷息
	 * 
	 * @param amount 参与贷息计算总金额
	 * @param rateValue 贷息利率
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static double calcLoanCost(double amount, double rateValue, Date start, Date end){
		return calcInterrestCost(amount, rateValue, start, end);
	}
	
	/**
	 * 计算存息
	 * 
	 * @param amount 参与存息计算总金额
	 * @param rateValue 存息利率
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static double calcDepositCost(double amount, double rateValue, Date start, Date end){
		return calcInterrestCost(amount, rateValue, start, end);
	}

	/**
	 * 最新存款利率
	 * @param createDateStr
	 * @return
	 */
	public static double getFcDepositRateLatest(String createDateStr){
		return FcUtils.getFcDepositRateLatest("A0", createDateStr);
	}
	
	public static double getFcLoanRateLatest(String fcType){
		return getFcLoanRateLatest(fcType,null,null);
	}
	
	/**
	 * 最新贷款利率
	 * @param fcType 计划内外：INNER|OUTER
	 * @param fstSvcType
	 * @param createDateStr
	 * @return
	 */
	public static double getFcLoanRateLatest(String fcType, String fstSvcType, String createDateStr){
		String loanRateType = "B0";//贷息-代理
		if("OUTER".equals(fcType) && fstSvcType != null
				&& (!".集成/供货类,.纯代理服务类,集成,代理".contains(fstSvcType) && !"11,16,22,23".contains(fstSvcType))){
			loanRateType = "B1";//贷息-自有
		}
		return FcUtils.getFcLoanRateLatest(loanRateType,createDateStr);	//最新贷款利率
	}

	/**
	 * 最新存款利率
	 * @param rateType
	 * @param createDateStr
	 * @return
	 */
	public static Double getFcDepositRateLatest(String rateType, String createDateStr){
		if(StringUtils.isBlank(createDateStr)){
			createDateStr = DateUtils.getDate("yyyyMMdd");
		}
		if(createDateStr.length() == 6){
			createDateStr = createDateStr + "01";
		}
		String rateKey = rateType+createDateStr;
		Double rate = (Double)EhCacheUtils.get(FC_FCPLANFEECALC_CACHE, rateKey);
		if (rate == null){
			rate = fcInterrestRateDao.getLatestInterrestRate(rateType, createDateStr).getRateValue();
			EhCacheUtils.put(FC_FCPLANFEECALC_CACHE, rateKey, rate);
		}
		return rate;
	}
	
	/**
	 * 最新贷款利率
	 * @param rateType
	 * @param createDateStr
	 * @return
	 */
	public static Double getFcLoanRateLatest(String rateType, String createDateStr){
		if(StringUtils.isBlank(createDateStr)){
			createDateStr = DateUtils.getDate("yyyyMMdd");
		}
		if(createDateStr.length() == 6){
			createDateStr = createDateStr + "01";
		}
		String rateKey = rateType+createDateStr;
		Double rate = (Double)EhCacheUtils.get(FC_FCPLANFEECALC_CACHE, rateKey);
		if (rate == null){
			rate = fcInterrestRateDao.getLatestInterrestRate(rateType, createDateStr).getRateValue();
			EhCacheUtils.put(FC_FCPLANFEECALC_CACHE, rateKey, rate);
		}
		return rate;
	}
	
}
