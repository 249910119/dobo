/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目计划内财务费用Entity
 * @author admin
 * @version 2016-11-06
 */
@SuppressWarnings("serial")
public class FcPlanInnerFeeRest implements Serializable {
	
	private String projectCode;
	private Double financialCost;		// 财务费用
	private Date calculateTime;		// 计算时间
	private Double loanRate;		// 贷息利率
	private Double depositRate;		// 存息利率
	private String remarks;
	private String calcParaJson;		// 计算参数
	
	public FcPlanInnerFeeRest() {
	}

	public FcPlanInnerFeeRest(String projectCode, Date calculateTime, Double financialCost, String remarks) {
		super();
		this.projectCode = projectCode;
		this.calculateTime = calculateTime;
		this.financialCost = financialCost;
		this.remarks = remarks;
	}

	public FcPlanInnerFeeRest(String projectCode, Date calculateTime, Double financialCost, Double loanRate, Double depositRate, String remarks) {
		super();
		this.projectCode = projectCode;
		this.calculateTime = calculateTime;
		this.financialCost = financialCost;
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