/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目计划付款明细Entity
 * @author admin
 * @version 2016-11-06
 */
@SuppressWarnings("serial")
public class FcPlanPayDetailRest implements Serializable {
	
	public final static String exportName = "计划付款";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"projectCode", "displayOrder", "planPayScale", "planPayTime", "planPayAmount", "payType", "planPayDays"};
	
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"项目编号","期次","计划付款比例","计划付款时间","计划付款金额","支付类型","付款时间间隔天数"};

	private String projectCode;		// 项目编号
	private Integer displayOrder;		// 期次
	private Date planPayTime;		// 计划收款时间
	private Double planPayAmount;		// 计划收款金额
	private Double planPayScale;		// 计划收款比例
	private String payType;		// 支付类型
	private String payCurrency;		// 支付币种
	private Integer planPayDays;		// 收款时间间隔天数
	
	private Double loanAmountInner = 0.0;	//贷款利息-财务费用
	private Double depositAmountInner = 0.0;	//存款利息
	private Double receiptLeftInner = 0.0;//收款结余
	
	public FcPlanPayDetailRest() {
	}

	public FcPlanPayDetailRest(Integer displayOrder, Date planPayTime, Double planPayAmount, Double planPayScale,
			String payType, String payCurrency, Integer planPayDays) {
		this.displayOrder = displayOrder;
		this.planPayTime = planPayTime;
		this.planPayAmount = planPayAmount;
		this.planPayScale = planPayScale;
		this.payType = payType;
		this.payCurrency = payCurrency;
		this.planPayDays = planPayDays;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getPlanPayTime() {
		return planPayTime;
	}

	public void setPlanPayTime(Date planPayTime) {
		this.planPayTime = planPayTime;
	}

	public Double getPlanPayAmount() {
		return planPayAmount;
	}

	public void setPlanPayAmount(Double planPayAmount) {
		this.planPayAmount = planPayAmount;
	}

	public Double getPlanPayScale() {
		return planPayScale;
	}

	public void setPlanPayScale(Double planPayScale) {
		this.planPayScale = planPayScale;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public Integer getPlanPayDays() {
		return planPayDays;
	}

	public void setPlanPayDays(Integer planPayDays) {
		this.planPayDays = planPayDays;
	}

	public Double getLoanAmountInner() {
		return loanAmountInner;
	}

	public void setLoanAmountInner(Double loanAmountInner) {
		this.loanAmountInner = loanAmountInner;
	}

	public Double getDepositAmountInner() {
		return depositAmountInner;
	}

	public void setDepositAmountInner(Double depositAmountInner) {
		this.depositAmountInner = depositAmountInner;
	}

	public Double getReceiptLeftInner() {
		return receiptLeftInner;
	}

	public void setReceiptLeftInner(Double receiptLeftInner) {
		this.receiptLeftInner = receiptLeftInner;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}