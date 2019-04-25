/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目实际到款明细Entity
 * @author admin
 * @version 2016-11-06
 */
@SuppressWarnings("serial")
public class FcActualReceiptDetailRest implements Serializable {
	
	public final static String exportName = "项目实际收款";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"saleOrg", "saleOrgName", "buName", "erpxmbh", "taxType", "receiptDate", "receiptMoney", "receiptNetMoney", "remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"事业部","新事业部名称","BU名称","财务项目编号","税率", "到款时间", "到款金额", "到款净额", "备注"};
	
	//导出字段
	public final static String[] exportfieldNamesPrj = {
			"saleOrg", "saleOrgName", "buName", "erpxmbh", "taxType", "receiptDate", "receiptMoney", "receiptNetMoney", "remark"};
	//导出字段对应标题
	public final static String[] exportfieldTitlesPrj = {
			"事业部","新事业部名称","BU名称","财务项目编号","税率", "到款时间", "到款金额", "到款净额", "备注"};

	private String saleOrg;			// 事业部
	private String saleOrgName;		// 新事业部名称
	private String buName;			// BU名称
	private String erpxmbh;			// 财务项目编号
	private String taxType;			// 税率
	private Date receiptDate;		// 到款时间
	private Double receiptMoney;	// 到款金额
	private Double receiptNetMoney;	// 到款净额
	private String remark;			// 备注
	
	public FcActualReceiptDetailRest() {
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

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Double getReceiptMoney() {
		return receiptMoney;
	}

	public void setReceiptMoney(Double receiptMoney) {
		this.receiptMoney = receiptMoney;
	}

	public Double getReceiptNetMoney() {
		return receiptNetMoney;
	}

	public void setReceiptNetMoney(Double receiptNetMoney) {
		this.receiptNetMoney = receiptNetMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}