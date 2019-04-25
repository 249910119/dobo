/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 财务费用计算对应项目信息Entity
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcProjectInfoRest implements Serializable {
	
	public final static String exportName = "项目信息";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"projectCode", "projectName", "custName", "fstSvcType", "sndSvcType", "saleOrg", "salesName", "hasWbmOrder", "signMoney", "buName", "saleOrgName"};
	
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"项目编号","项目名称","客户名称","产品线大类","产品线小类","事业部","销售员","是否有WBM订单","签约金额", "BU名称", "新事业部名称"};
	
	private String projectCode;		// 项目编号
	private String projectName;		// 项目名称
	private String custName;		// 客户名称
	private String fstSvcType;		// 产品线大类
	private String sndSvcType;		// 产品线小类
	private String saleOrg;			// 事业部
	private String saleOrgName;		// 事业部名称
	private String buName;			// BU名称
	private String businessCode;	// 业务范围代码
	private String salesName;		// 销售员
	private String hasWbmOrder;		// 是否有WBM订单
	private Double signMoney;		// 签约金额
	private Double signNetMoney;	// 签约净额
	private String financialMonth;	// 财务费用计算年月(计划外)
	private Date actualBeginDate;   // 计算实际财务费用时的开始日期
	private Date actualEndDate;     // 计算实际财务费用时的截止日期
	private Double loanRate;		// 贷息利率
	private Double depositRate;		// 存息利率

	private Double xmsyCwfy;		// 计划内利息
	private Date startDate;      	// 项目服务期开始
	private Date endDate;      		// 项目服务期截止
	private String taxType;			// 税率
	
	private Double financialCost;	// 财务费用
	private Double endBalance;		// 期末结余
	private List<FcActualCalcCostResultRest> fcActualCalcCostResultRestList;	// 财务费用
	
	private List<FcOrderInfoRest> fcOrderInfoRestList;		// 订单信息
	private List<FcActualReceiptDetailRest> fcActualReceiptDetailRestList;		// 项目实际到款明细
	private List<FcActualPayDetailRest> fcActualPayDetailRestList;		// 项目实际付款明细
	private List<FcPlanInnerFeeRest> fcPlanInnerFeeRestList;		// 项目计划内财务费用
	private List<FcPlanOuterFeeRest> fcPlanOuterFeeRestList;		// 项目计划外财务费用
	private List<FcActualFeeRest> fcActualFeeRestList;		           // 项目财务费用
	private List<FcPlanPayDetailRest> fcPlanPayDetailRestList;		// 项目计划付款明细
	private List<FcPlanReceiptDetailRest> fcPlanReceiptDetailRestList;		// 项目计划收款明细
	
	private List<String> sybProjects = new ArrayList<String>(); // 按事业部计算时包含的项目集合
	
	public FcProjectInfoRest() {
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getFstSvcType() {
		return fstSvcType;
	}

	public void setFstSvcType(String fstSvcType) {
		this.fstSvcType = fstSvcType;
	}

	public String getSndSvcType() {
		return sndSvcType;
	}

	public void setSndSvcType(String sndSvcType) {
		this.sndSvcType = sndSvcType;
	}

	public String getSaleOrg() {
		return saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}

	public String getSaleOrgName() {
		return saleOrgName;
	}

	public void setSaleOrgName(String saleOrgName) {
		this.saleOrgName = saleOrgName;
	}

	public String getBuName() {
		return buName;
	}

	public void setBuName(String buName) {
		this.buName = buName;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getHasWbmOrder() {
		return hasWbmOrder;
	}

	public void setHasWbmOrder(String hasWbmOrder) {
		this.hasWbmOrder = hasWbmOrder;
	}

	public List<FcOrderInfoRest> getFcOrderInfoRestList() {
		return fcOrderInfoRestList;
	}

	public void setFcOrderInfoRestList(List<FcOrderInfoRest> fcOrderInfoRestList) {
		this.fcOrderInfoRestList = fcOrderInfoRestList;
	}

	public List<FcActualReceiptDetailRest> getFcActualReceiptDetailRestList() {
		return fcActualReceiptDetailRestList;
	}

	public void setFcActualReceiptDetailRestList(List<FcActualReceiptDetailRest> fcActualReceiptDetailRestList) {
		this.fcActualReceiptDetailRestList = fcActualReceiptDetailRestList;
	}

	public List<FcActualPayDetailRest> getFcActualPayDetailRestList() {
		return fcActualPayDetailRestList;
	}

	public void setFcActualPayDetailRestList(
			List<FcActualPayDetailRest> fcActualPayDetailRestList) {
		this.fcActualPayDetailRestList = fcActualPayDetailRestList;
	}

	public List<FcPlanInnerFeeRest> getFcPlanInnerFeeRestList() {
		return fcPlanInnerFeeRestList;
	}

	public void setFcPlanInnerFeeRestList(List<FcPlanInnerFeeRest> fcPlanInnerFeeRestList) {
		this.fcPlanInnerFeeRestList = fcPlanInnerFeeRestList;
	}

	public List<FcPlanOuterFeeRest> getFcPlanOuterFeeRestList() {
		return fcPlanOuterFeeRestList;
	}

	public void setFcPlanOuterFeeRestList(List<FcPlanOuterFeeRest> fcPlanOuterFeeRestList) {
		this.fcPlanOuterFeeRestList = fcPlanOuterFeeRestList;
	}

	public List<FcActualFeeRest> getFcActualFeeRestList() {
		return fcActualFeeRestList;
	}

	public void setFcActualFeeRestList(List<FcActualFeeRest> fcActualFeeRestList) {
		this.fcActualFeeRestList = fcActualFeeRestList;
	}

	public List<FcPlanPayDetailRest> getFcPlanPayDetailRestList() {
		return fcPlanPayDetailRestList;
	}

	public void setFcPlanPayDetailRestList(List<FcPlanPayDetailRest> fcPlanPayDetailRestList) {
		this.fcPlanPayDetailRestList = fcPlanPayDetailRestList;
	}

	public List<FcPlanReceiptDetailRest> getFcPlanReceiptDetailRestList() {
		return fcPlanReceiptDetailRestList;
	}

	public void setFcPlanReceiptDetailRestList(List<FcPlanReceiptDetailRest> fcPlanReceiptDetailRestList) {
		this.fcPlanReceiptDetailRestList = fcPlanReceiptDetailRestList;
	}

	public Double getSignMoney() {
		return signMoney;
	}

	public void setSignMoney(Double signMoney) {
		this.signMoney = signMoney;
	}

	public Double getSignNetMoney() {
		return signNetMoney;
	}

	public void setSignNetMoney(Double signNetMoney) {
		this.signNetMoney = signNetMoney;
	}

	public String getFinancialMonth() {
		return financialMonth;
	}

	public void setFinancialMonth(String financialMonth) {
		this.financialMonth = financialMonth;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public List<String> getSybProjects() {
		return sybProjects;
	}

	public void setSybProjects(List<String> sybProjects) {
		this.sybProjects = sybProjects;
	}

	public Date getActualBeginDate() {
		return actualBeginDate;
	}

	public void setActualBeginDate(Date actualBeginDate) {
		this.actualBeginDate = actualBeginDate;
	}

	public Double getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}

	public Double getDepositRate() {
		return depositRate;
	}

	public void setDepositRate(Double depositRate) {
		this.depositRate = depositRate;
	}

	public Double getFinancialCost() {
		return financialCost;
	}

	public void setFinancialCost(Double financialCost) {
		this.financialCost = financialCost;
	}

	public Double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

	public List<FcActualCalcCostResultRest> getFcActualCalcCostResultRestList() {
		return fcActualCalcCostResultRestList;
	}

	public void setFcActualCalcCostResultRestList(List<FcActualCalcCostResultRest> fcActualCalcCostResultRestList) {
		this.fcActualCalcCostResultRestList = fcActualCalcCostResultRestList;
	}

	public Double getXmsyCwfy() {
		return xmsyCwfy;
	}

	public void setXmsyCwfy(Double xmsyCwfy) {
		this.xmsyCwfy = xmsyCwfy;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	
}