/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务费用计算结果信息
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcActualCalcCostResultRest implements Serializable {
	
	public final static String exportName = "财务费用明细";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"saleOrg","saleOrgName","buName","payReceiptDate","payNetMoney","receiptNetMoney","restNetMoney","financialCost","remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"事业部","新事业部名称","BU名称","收付款时间","付款净额","到款净额","结余净额","财务费用","备注"};
	
	//导出字段
	public final static String[] exportfieldNamesPrj = {
			"erpxmbh","saleOrg","saleOrgName","buName","payReceiptDate","payNetMoney","receiptNetMoney","restNetMoney","financialCost","isCurrentPeriod","remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitlesPrj = {
			"财务项目编号","事业部","新事业部名称","BU名称","收付款时间","付款净额","到款净额","结余净额","财务费用","是否计算当期","备注"};

	private String erpxmbh;			// 财务项目编号
	private String saleOrgName;		// 新事业部名称
	private String buName;			// BU名称
	private String saleOrg;			// 事业部
	private Date payReceiptDate;	// 收付款时间
	private Double payNetMoney;		// 付款净额
	private Double receiptNetMoney;	// 到款净额
	private Double restNetMoney;	// 结余净额
	private Double financialCost;	// 财务费用
	private String remark;			// 备注
	
	private String isCurrentPeriod;	// 是否计算当期
	
	public FcActualCalcCostResultRest() {
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

	public String getSaleOrg() {
		return saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}

	public Double getPayNetMoney() {
		return payNetMoney;
	}

	public void setPayNetMoney(Double payNetMoney) {
		this.payNetMoney = payNetMoney;
	}

	public Double getReceiptNetMoney() {
		return receiptNetMoney;
	}

	public void setReceiptNetMoney(Double receiptNetMoney) {
		this.receiptNetMoney = receiptNetMoney;
	}

	public Double getRestNetMoney() {
		return restNetMoney;
	}

	public void setRestNetMoney(Double restNetMoney) {
		this.restNetMoney = restNetMoney;
	}

	public Double getFinancialCost() {
		return financialCost;
	}

	public void setFinancialCost(Double financialCost) {
		this.financialCost = financialCost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getPayReceiptDate() {
		return payReceiptDate;
	}

	public void setPayReceiptDate(Date payReceiptDate) {
		this.payReceiptDate = payReceiptDate;
	}

	public String getErpxmbh() {
		return erpxmbh;
	}

	public void setErpxmbh(String erpxmbh) {
		this.erpxmbh = erpxmbh;
	}

	public String getIsCurrentPeriod() {
		return isCurrentPeriod;
	}

	public void setIsCurrentPeriod(String isCurrentPeriod) {
		this.isCurrentPeriod = isCurrentPeriod;
	}
	
}