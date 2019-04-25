/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 财务费用计算对应项目信息Entity
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcSalePrjInfoRest implements Serializable {
	
	public final static String exportName = "项目信息";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"erpxmbh","fstSvcType","sndSvcType","saleOrg", "saleOrgName", "buName", "prjInitial", "orderCost", "subCost", "cgCost", "feeCost", "receiptNetMoney", 
			"loanRate", "depositRate", "actualStartDate", "actualEndDate", "endBalance", "startDate", "endDate", "financialCost", "xmsyCwfy", "curFinancialCost", "remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"财务项目编号","产品线大类","产品线小类","事业部","新事业部名称","BU名称","期初值","订单成本","分包成本", "采购成本", "费用成本", "收款净额", 
			"贷息利率","存息利率","计息开始日期","计息截止日期","期末结余", "项目服务期开始", "项目服务期截止","当期财务费用", "计划内财务费用", "当期应缴计划外利息", "备注"};

	private String erpxmbh;			// 财务项目编号
	private String fstSvcType;		// 产品线大类
	private String sndSvcType;		// 产品线小类
	private String saleOrg;			// 事业部
	private String saleOrgName;		// 新事业部名称
	private String buName;			// BU名称
	private Double prjInitial;		// 期初值
	private Double orderCost;		// 订单成本
	private Double subCost;			// 分包成本
	private Double cgCost;			// 采购成本
	private Double feeCost;			// 费用成本
	private Double receiptNetMoney;	// 收款净额
	private Double loadLimit;		// 贷款额度
	private Double loanRate;		// 贷息利率
	private Double depositRate;		// 存息利率
	private Date actualStartDate;	// 计息开始日期
	private Date actualEndDate;		// 计息截止日期
	private Double financialCost;	// 当期财务费用
	private Double endBalance;		// 期末结余=期初值+过程现金流

	private String remark;			// 备注

	private Date startDate;      	// 项目服务期开始
	private Date endDate;      		// 项目服务期截止
	private Double xmsyCwfy;		// 当期计划内利息
	private Double curFinancialCost;// 当期应缴计划外利息=当期计划外-当期计划内
	
	private List<FcActualReceiptDetailRest> fcActualReceiptDetailRestList;		// 到款明细
	private List<FcActualPayDetailRest> fcActualPayDetailRestList;				// 付款明细(分包、采购、订单、期初)
	private List<FcActualCalcCostResultRest> fcActualCalcCostResultRestList;	// 财务费用
	
	public FcSalePrjInfoRest() {
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

	public Double getFeeCost() {
		return feeCost;
	}

	public void setFeeCost(Double feeCost) {
		this.feeCost = feeCost;
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

	public Double getLoadLimit() {
		return loadLimit;
	}

	public void setLoadLimit(Double loadLimit) {
		this.loadLimit = loadLimit;
	}

	public Double getPrjInitial() {
		return prjInitial;
	}

	public void setPrjInitial(Double prjInitial) {
		this.prjInitial = prjInitial;
	}

	public String getErpxmbh() {
		return erpxmbh;
	}

	public void setErpxmbh(String erpxmbh) {
		this.erpxmbh = erpxmbh;
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

	public Double getXmsyCwfy() {
		return xmsyCwfy;
	}

	public void setXmsyCwfy(Double xmsyCwfy) {
		this.xmsyCwfy = xmsyCwfy;
	}

	public Double getCurFinancialCost() {
		return curFinancialCost;
	}

	public void setCurFinancialCost(Double curFinancialCost) {
		this.curFinancialCost = curFinancialCost;
	}
	
}