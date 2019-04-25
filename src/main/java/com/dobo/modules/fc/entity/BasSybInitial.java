/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 事业部初始现金流Entity
 * @author admin
 * @version 2017-12-24
 */
public class BasSybInitial extends DataEntity<BasSybInitial> {
	
	private static final long serialVersionUID = 1L;
	private String relId;		// 编号
	private String sybmc;		// 事业部名称
	private Double initialLoans;		// 初始现金流
	private Double payProfit;		// 上缴利润
	private Double loadLimit;		// 贷款额度
	private String fiscalYear;		// 财年
	private String remark;		// 备注
	
	public BasSybInitial() {
		super();
	}

	public BasSybInitial(String id){
		super(id);
	}

	@Length(min=0, max=60, message="编号长度必须介于 0 和 60 之间")
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
	
	@Length(min=0, max=360, message="事业部名称长度必须介于 0 和 360 之间")
	public String getSybmc() {
		return sybmc;
	}

	public void setSybmc(String sybmc) {
		this.sybmc = sybmc;
	}
	
	public Double getInitialLoans() {
		return initialLoans;
	}

	public void setInitialLoans(Double initialLoans) {
		this.initialLoans = initialLoans;
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
	
	@Length(min=0, max=20, message="财年长度必须介于 0 和 20 之间")
	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	@Length(min=0, max=360, message="备注长度必须介于 0 和 360 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}