/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目实际付款明细Entity
 * @author admin
 * @version 2016-11-06
 */
@SuppressWarnings("serial")
public class FcActualPayDetailRest implements Serializable {
	
	public final static String exportName = "实际付款";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"saleOrg", "saleOrgName", "buName", "erpxmbh", "taxType", "payType", "exchangeType", 
			"billStartDate", "billEndDate", "payDate", "payMoney", "payNetMoney", "remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"事业部","新事业部名称","BU名称","财务项目编号","税率", "支付类型", "汇兑类型", 
			"开票日", "到期日", "付款日期", "付款金额", "付款净额", "备注"};
	
	//导出字段
	public final static String[] exportfieldNamesPrj = {
			"saleOrg", "saleOrgName", "buName", "erpxmbh", "taxType", "payType", "exchangeType", 
			"billStartDate", "billEndDate", "payDate", "payMoney", "payNetMoney", "remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitlesPrj = {
			"事业部","新事业部名称","BU名称","财务项目编号","税率", "支付类型", "汇兑类型", 
			"开票日", "到期日", "付款日期", "付款金额", "付款净额", "备注"};

	private String saleOrg;			// 事业部
	private String saleOrgName;		// 新事业部名称
	private String buName;			// BU名称
	private String erpxmbh;			// 财务项目编号
	private String taxType;			// 税率
	
	private String payType;			// 支付类型（订单、分包、采购、费用、期初）
	private String exchangeType;	// 汇兑类型（电汇、商票、银票、贴现）
	private Date billStartDate;		// 开票日
	private Date billEndDate;		// 到期日
	private Date payDate;			// 付款日期
	private Double payMoney;		// 付款金额
	private Double payNetMoney;		// 付款净额
	private String remark;			// 备注
	
	public FcActualPayDetailRest() {
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

	public String getErpxmbh() {
		return erpxmbh;
	}

	public void setErpxmbh(String erpxmbh) {
		this.erpxmbh = erpxmbh;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public Date getBillStartDate() {
		return billStartDate;
	}

	public void setBillStartDate(Date billStartDate) {
		this.billStartDate = billStartDate;
	}

	public Date getBillEndDate() {
		return billEndDate;
	}

	public void setBillEndDate(Date billEndDate) {
		this.billEndDate = billEndDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Double getPayNetMoney() {
		return payNetMoney;
	}

	public void setPayNetMoney(Double payNetMoney) {
		this.payNetMoney = payNetMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}