/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

/**
 * 财务费用计算对应项目信息Entity
 * @author admin
 * @version 2016-11-05
 */
public class FcProjectInfo extends DataEntity<FcProjectInfo> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// 项目编号
	private String projectName;		// 项目名称
	private String custName;		// 客户名称
	private String fstSvcType;		// 产品线大类
	private String sndSvcType;		// 产品线小类
	private String saleOrg;		// 事业部
	private String businessCode;		// 业务范围代码
	private String salesName;		// 销售员
	private String hasWbmOrder;		// 是否有WBM订单
	private Double signMoney;		// 签约金额
	private Double signNetMoney;		// 签约净额
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private List<FcOrderInfo> fcOrderInfoList = Lists.newArrayList();		// 订单信息
	private List<FcActualReceiptDetail> fcActualReceiptDetailList = Lists.newArrayList();		// 项目实际到款明细
	private List<FcPlanInnerFee> fcPlanInnerFeeList = Lists.newArrayList();		// 项目计划内财务费用
	private List<FcPlanOuterFee> fcPlanOuterFeeList = Lists.newArrayList();		// 项目计划外财务费用
	private List<FcPlanPayDetail> fcPlanPayDetailList = Lists.newArrayList();		// 项目计划付款明细
	private List<FcPlanReceiptDetail> fcPlanReceiptDetailList = Lists.newArrayList();		// 项目计划收款明细
	
	public FcProjectInfo() {
		super();
	}

	public FcProjectInfo(String id){
		super(id);
	}

	public FcProjectInfo(String projectCode, String status) {
		super();
		this.projectCode = projectCode;
		this.status = status;
	}

	@Length(min=1, max=30, message="项目编号长度必须介于 1 和 30 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=1, max=128, message="项目名称长度必须介于 1 和 128 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=128, message="客户名称长度必须介于 0 和 128 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=1, max=30, message="产品线大类长度必须介于 1 和 30 之间")
	public String getFstSvcType() {
		return fstSvcType;
	}

	public void setFstSvcType(String fstSvcType) {
		this.fstSvcType = fstSvcType;
	}
	
	@Length(min=1, max=30, message="产品线小类长度必须介于 1 和 30 之间")
	public String getSndSvcType() {
		return sndSvcType;
	}

	public void setSndSvcType(String sndSvcType) {
		this.sndSvcType = sndSvcType;
	}
	
	@Length(min=0, max=128, message="事业部长度必须介于 0 和 128 之间")
	public String getSaleOrg() {
		return saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}
	
	@Length(min=0, max=15, message="业务范围代码长度必须介于 0 和 15 之间")
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	@Length(min=0, max=60, message="销售员长度必须介于 0 和 60 之间")
	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
	@Length(min=1, max=2, message="是否有WBM订单长度必须介于 1 和 2 之间")
	public String getHasWbmOrder() {
		return hasWbmOrder;
	}

	public void setHasWbmOrder(String hasWbmOrder) {
		this.hasWbmOrder = hasWbmOrder;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2, message="更新前状态长度必须介于 0 和 2 之间")
	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	
	@Length(min=1, max=1, message="保存标记长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public List<FcOrderInfo> getFcOrderInfoList() {
		return fcOrderInfoList;
	}

	public void setFcOrderInfoList(List<FcOrderInfo> fcOrderInfoList) {
		this.fcOrderInfoList = fcOrderInfoList;
	}

	public List<FcActualReceiptDetail> getFcActualReceiptDetailList() {
		return fcActualReceiptDetailList;
	}

	public void setFcActualReceiptDetailList(List<FcActualReceiptDetail> fcActualReceiptDetailList) {
		this.fcActualReceiptDetailList = fcActualReceiptDetailList;
	}

	public List<FcPlanInnerFee> getFcPlanInnerFeeList() {
		return fcPlanInnerFeeList;
	}

	public void setFcPlanInnerFeeList(List<FcPlanInnerFee> fcPlanInnerFeeList) {
		this.fcPlanInnerFeeList = fcPlanInnerFeeList;
	}

	public List<FcPlanOuterFee> getFcPlanOuterFeeList() {
		return fcPlanOuterFeeList;
	}

	public void setFcPlanOuterFeeList(List<FcPlanOuterFee> fcPlanOuterFeeList) {
		this.fcPlanOuterFeeList = fcPlanOuterFeeList;
	}

	public List<FcPlanPayDetail> getFcPlanPayDetailList() {
		return fcPlanPayDetailList;
	}

	public void setFcPlanPayDetailList(List<FcPlanPayDetail> fcPlanPayDetailList) {
		this.fcPlanPayDetailList = fcPlanPayDetailList;
	}

	public List<FcPlanReceiptDetail> getFcPlanReceiptDetailList() {
		return fcPlanReceiptDetailList;
	}

	public void setFcPlanReceiptDetailList(List<FcPlanReceiptDetail> fcPlanReceiptDetailList) {
		this.fcPlanReceiptDetailList = fcPlanReceiptDetailList;
	}

	public Double getSignMoney() {
		return signMoney;
	}

	public void setSignMoney(Double signMoney) {
		this.signMoney = signMoney;
	}

	public Double getSignNetMoney() {
		return signNetMoney;
	}

	public void setSignNetMoney(Double signNetMoney) {
		this.signNetMoney = signNetMoney;
	}
	
}