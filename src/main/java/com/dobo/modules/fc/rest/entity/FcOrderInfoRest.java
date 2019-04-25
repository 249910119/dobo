/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务费用计算对应订单信息Entity
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcOrderInfoRest implements Serializable {
	
	public final static String exportName = "订单信息";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"projectCode", "orderId", "fstSvcType", "sndSvcType", "serviceDateBegin", "serviceDateEnd", "ownProdCost", "specifySubCost", "tgCost", "saleOrgName"
			,"caclProdCost","payDate","payCost"};
	
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"项目编号","订单编号","产品线大类","产品线小类","服务期开始","服务期结束","自有产品成本","指定分包成本", "托管成本", "事业部名称"
			, "参与计算产品成本", "支付日期", "支付成本"};

	private String projectCode;		// 项目编号
	private String orderId;		// 订单编号
	private String fstSvcType;		// 产品线大类
	private String sndSvcType;		// 产品线小类
	private Date serviceDateBegin;		// 服务期开始
	private Date serviceDateEnd;		// 服务期结束
	private Double signAmount;		// 签约金额
	private Double ownProdCost;		// 自有产品成本
	private Double specifySubCost;		// 指定分包成本
	private Double tgCost;		// 托管成本
	private String saleOrgName;		// 事业部名称
	private Double caclProdCost;		// 计算实际费用时的产品成本
	private Date payDate;		// 支付日期
	private Double payCost;		// 支付成本
	
	public FcOrderInfoRest() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Date getServiceDateBegin() {
		return serviceDateBegin;
	}

	public void setServiceDateBegin(Date serviceDateBegin) {
		this.serviceDateBegin = serviceDateBegin;
	}

	public Date getServiceDateEnd() {
		return serviceDateEnd;
	}

	public void setServiceDateEnd(Date serviceDateEnd) {
		this.serviceDateEnd = serviceDateEnd;
	}

	public Double getSignAmount() {
		return signAmount;
	}

	public void setSignAmount(Double signAmount) {
		this.signAmount = signAmount;
	}

	public Double getOwnProdCost() {
		return ownProdCost;
	}

	public void setOwnProdCost(Double ownProdCost) {
		this.ownProdCost = ownProdCost;
	}

	public Double getSpecifySubCost() {
		return specifySubCost;
	}

	public void setSpecifySubCost(Double specifySubCost) {
		this.specifySubCost = specifySubCost;
	}

	public Double getTgCost() {
		return tgCost;
	}

	public void setTgCost(Double tgCost) {
		this.tgCost = tgCost;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getSaleOrgName() {
		return saleOrgName;
	}

	public void setSaleOrgName(String saleOrgName) {
		this.saleOrgName = saleOrgName;
	}

	public Double getCaclProdCost() {
		return caclProdCost;
	}

	public void setCaclProdCost(Double caclProdCost) {
		this.caclProdCost = caclProdCost;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Double getPayCost() {
		return payCost;
	}

	public void setPayCost(Double payCost) {
		this.payCost = payCost;
	}
	 
}