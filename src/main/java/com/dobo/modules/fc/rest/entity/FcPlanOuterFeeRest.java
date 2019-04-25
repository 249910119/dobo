/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目计划外财务费用Entity
 * @author admin
 * @version 2016-11-06
 */
@SuppressWarnings("serial")
public class FcPlanOuterFeeRest implements Serializable {

	private String projectCode;
	private String financialMonth;		// 财务费用年月
	private Double monthBalance;		// 月底结余
	private Double financialCost;		// 财务费用
	private Date calculateTime;		// 计算时间
	private Double loanRate;		// 贷息利率
	private Double depositRate;		// 存息利率
	private String remarks;
	private String calcParaJson;		// 计算参数
	
	public FcPlanOuterFeeRest() {
	}

	public FcPlanOuterFeeRest(String projectCode, String financialMonth, Date calculateTime, Double financialCost, String remarks) {
		super();
		this.projectCode = projectCode;
		this.financialMonth = financialMonth;
		this.calculateTime = calculateTime;
		this.financialCost = financialCost;
		this.remarks = remarks;
	}

	public FcPlanOuterFeeRest(String projectCode, String financialMonth, Double monthBalance, Double financialCost, Date calculateTime,
			Double loanRate, Double depositRate, String remarks) {
		super();
		this.projectCode = projectCode;
		this.financialMonth = financialMonth;
		this.monthBalance = monthBalance;
		this.financialCost = financialCost;
		this.calculateTime = calculateTime;
		this.loanRate = loanRate;
		this.depositRate = depositRate;
		this.remarks = remarks;
	}

	public Double getFinancialCost() {
		return financialCost;
	}

	public void setFinancialCost(Double financialCost) {
		this.financialCost = financialCost;
	}

	public Date getCalculateTime() {
		return calculateTime;
	}

	public void setCalculateTime(Date calculateTime) {
		this.calculateTime = calculateTime;
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

	public Double getMonthBalance() {
		return monthBalance;
	}

	public void setMonthBalance(Double monthBalance) {
		this.monthBalance = monthBalance;
	}

	public String getFinancialMonth() {
		return financialMonth;
	}

	public void setFinancialMonth(String financialMonth) {
		this.financialMonth = financialMonth;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getCalcParaJson() {
		return calcParaJson;
	}

	public void setCalcParaJson(String calcParaJson) {
		this.calcParaJson = calcParaJson;
	}
	
}