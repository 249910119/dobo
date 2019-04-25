/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;

/**
 * 财务费用计算结果信息
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcCalcCostResultRest implements Serializable {
	
	public final static String exportName = "计算结果";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"projectCode", "projectName", "fstSvcType", "saleOrg", "salesName", "buName", "saleOrgName", "loanRate", "depositRate", "financialCost", "status", "financialMonth", "remark"};

	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"项目编号","项目名称","产品线","事业部","销售员","BU名称","新事业部名称","贷款利率","存款利率","财务费用","状态","计算月份","备注"};

	private String projectCode;		// 项目编号
	private String projectName;		// 项目名称
	private String fstSvcType;		// 产品线大类
	private String saleOrg;		// 事业部
	private String salesName;		// 销售员
	private String buName;		// 事业部
	private String saleOrgName;		// 销售员
	private Double loanRate;		// 贷息利率
	private Double depositRate;		// 存息利率
	private Double financialCost;		// 财务费用
	private String status;		// 状态
	private String remark;		// 备注
	private String financialMonth;		// 财务费用计算年月(计划外)
	
	public FcCalcCostResultRest() {
	}

	public FcCalcCostResultRest(String projectCode, String projectName, String fstSvcType, String saleOrg,
			String salesName, String buName, String saleOrgName, Double loanRate, Double depositRate, 
			Double financialCost, String status, String financialMonth, String remark) {
		super();
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.fstSvcType = fstSvcType;
		this.saleOrg = saleOrg;
		this.salesName = salesName;
		this.buName = buName;
		this.saleOrgName = saleOrgName;
		this.loanRate = loanRate;
		this.depositRate = depositRate;
		this.financialCost = financialCost;
		this.status = status;
		this.financialMonth = financialMonth;
		this.remark = remark;
	}

	public FcCalcCostResultRest(String saleOrgName, String saleOrg, Double loanRate, Double depositRate, Double financialCost, String status, String financialMonth) {
		super();
		this.saleOrgName = saleOrgName;
		this.saleOrg = saleOrg;
		this.loanRate = loanRate;
		this.depositRate = depositRate;
		this.financialCost = financialCost;
		this.status = status;
		this.financialMonth = financialMonth;
	}

	public String getSaleOrgName() {
		return saleOrgName;
	}

	public void setSaleOrgName(String saleOrgName) {
		this.saleOrgName = saleOrgName;
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

	public String getFstSvcType() {
		return fstSvcType;
	}

	public void setFstSvcType(String fstSvcType) {
		this.fstSvcType = fstSvcType;
	}

	public String getSaleOrg() {
		return saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public Double getFinancialCost() {
		return financialCost;
	}

	public void setFinancialCost(Double financialCost) {
		this.financialCost = financialCost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFinancialMonth() {
		return financialMonth;
	}

	public void setFinancialMonth(String financialMonth) {
		this.financialMonth = financialMonth;
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

	public String getBuName() {
		return buName;
	}

	public void setBuName(String buName) {
		this.buName = buName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}