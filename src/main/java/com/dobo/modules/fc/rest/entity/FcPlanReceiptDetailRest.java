/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目计划收款明细Entity
 * @author admin
 * @version 2016-11-06
 */
@SuppressWarnings("serial")
public class FcPlanReceiptDetailRest implements Serializable {
	
	public final static String exportName = "计划收款";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"projectCode", "displayOrder", "planReceiptScale", "planReceiptTime", "planReceiptAmount", "payType", "planReceiptDays"};
	
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"项目编号","期次","计划收款比例","计划收款时间","计划收款金额","支付类型","收款时间间隔天数"};

	private String projectCode;		// 项目编号
	private Integer displayOrder;		// 期次
	private Date planReceiptTime;		// 计划收款时间
	private Double planReceiptAmount;		// 计划收款金额
	private Double planReceiptScale;		// 计划收款比例
	private String payType;		// 支付类型
	private String payCurrency;		// 支付币种
	private Integer planReceiptDays;		// 收款时间间隔天数
	
	private Double loanAmountInner = 0.0;	//贷款利息-财务费用
	private Double depositAmountInner = 0.0;	//存款利息
	private Double receiptLeftInner = 0.0;//收款结余
	
	public FcPlanReceiptDetailRest() {
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getPlanReceiptTime() {
		return planReceiptTime;
	}

	public void setPlanReceiptTime(Date planReceiptTime) {
		this.planReceiptTime = planReceiptTime;
	}

	public Double getPlanReceiptAmount() {
		return planReceiptAmount;
	}

	public void setPlanReceiptAmount(Double planReceiptAmount) {
		this.planReceiptAmount = planReceiptAmount;
	}

	public Double getPlanReceiptScale() {
		return planReceiptScale;
	}

	public void setPlanReceiptScale(Double planReceiptScale) {
		this.planReceiptScale = planReceiptScale;
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

	public Integer getPlanReceiptDays() {
		return planReceiptDays;
	}

	public void setPlanReceiptDays(Integer planReceiptDays) {
		this.planReceiptDays = planReceiptDays;
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