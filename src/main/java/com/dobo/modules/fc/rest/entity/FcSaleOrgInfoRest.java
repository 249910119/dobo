/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 财务费用计算对应事业部信息Entity
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcSaleOrgInfoRest implements Serializable {
	
	public final static String exportName = "事业部信息";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"saleOrg", "saleOrgName", "buName", "sybInitial", "sybFee", "orderCost", "subCost", "cgCost", "receiptNetMoney", "loadLimit",
			"loanRate", "depositRate", "actualStartDate", "actualEndDate", "financialCost", "endBalance", "payProfit", "needLoans", "remark"};
	
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"事业部","新事业部名称","BU名称","期初值","费用支出","订单成本","分包成本", "采购成本", "收款净额", "贷款额度", 
			"贷息利率","存息利率","计息开始日期","计息截止日期","财务费用","期末结余", "期末上缴利润", "期末需贷款", "备注"};

	private String saleOrg;			// 事业部
	private String saleOrgName;		// 新事业部名称
	private String buName;			// BU名称
	private Double sybInitial;		// 期初值
	private Double sybFee;			// 费用支出
	private Double orderCost;		// 订单成本
	private Double subCost;			// 分包成本
	private Double cgCost;			// 采购成本
	private Double receiptNetMoney;	// 收款净额
	private Double loadLimit;		// 贷款额度
	private Double loanRate;			// 贷息利率
	private Double depositRate;		// 存息利率
	private Date actualStartDate;	// 计息开始日期
	private Date actualEndDate;		// 计息截止日期
	private Double financialCost;	// 财务费用
	private Double endBalance;		// 期末结余=期初值+过程现金流
	private Double payProfit;		// 期末上缴利润
	private Double needLoans;		// 期末需贷款=期末结余-期末上缴利润

	private String remark;			// 备注
	
	private List<FcActualReceiptDetailRest> fcActualReceiptDetailRestList;		// 到款明细
	private List<FcActualPayDetailRest> fcActualPayDetailRestList;				// 付款明细(分包、采购、订单、费用、期初)
	private List<FcActualCalcCostResultRest> fcActualCalcCostResultRestList;	// 财务费用
	
	public FcSaleOrgInfoRest() {
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

	public Double getSybInitial() {
		return sybInitial;
	}

	public void setSybInitial(Double sybInitial) {
		this.sybInitial = sybInitial;
	}

	public Double getSybFee() {
		return sybFee;
	}

	public void setSybFee(Double sybFee) {
		this.sybFee = sybFee;
	}

	public Double getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(Double orderCost) {
		this.orderCost = orderCost;
	}

	public Double getSubCost() {
		return subCost;
	}

	public void setSubCost(Double subCost) {
		this.subCost = subCost;
	}

	public Double getCgCost() {
		return cgCost;
	}

	public void setCgCost(Double cgCost) {
		this.cgCost = cgCost;
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

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
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

	public void setFcActualPayDetailRestList(List<FcActualPayDetailRest> fcActualPayDetailRestList) {
		this.fcActualPayDetailRestList = fcActualPayDetailRestList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getReceiptNetMoney() {
		return receiptNetMoney;
	}

	public void setReceiptNetMoney(Double receiptNetMoney) {
		this.receiptNetMoney = receiptNetMoney;
	}

	public List<FcActualCalcCostResultRest> getFcActualCalcCostResultRestList() {
		return fcActualCalcCostResultRestList;
	}

	public void setFcActualCalcCostResultRestList(List<FcActualCalcCostResultRest> fcActualCalcCostResultRestList) {
		this.fcActualCalcCostResultRestList = fcActualCalcCostResultRestList;
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

	public Double getNeedLoans() {
		return needLoans;
	}

	public void setNeedLoans(Double needLoans) {
		this.needLoans = needLoans;
	}

	public Double getPayProfit() {
		return payProfit;
	}

	public void setPayProfit(Double payProfit) {
		this.payProfit = payProfit;
	}

	public Double getLoadLimit() {
		return loadLimit;
	}

	public void setLoadLimit(Double loadLimit) {
		this.loadLimit = loadLimit;
	}
	
}