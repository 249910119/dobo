/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 财务费用计算对应订单信息Entity
 * @author admin
 * @version 2016-11-05
 */
public class FcOrderInfo extends DataEntity<FcOrderInfo> {
	
	private static final long serialVersionUID = 1L;
	private String fcProjectInfoId;		// 项目信息
	private FcProjectInfo fcProjectInfo;		// 项目信息
	private String orderId;		// 订单编号
	private String fstSvcType;		// 产品线大类
	private String sndSvcType;		// 产品线小类
	private Date serviceDateBegin;		// 服务期开始
	private Date serviceDateEnd;		// 服务期结束
	private Double signAmount;		// 签约金额
	private Double ownProdCost;		// 自有产品成本
	private Double specifySubCost;		// 指定分包成本
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public FcOrderInfo() {
		super();
	}

	public FcOrderInfo(String id){
		super(id);
	}

	public FcOrderInfo(String fcProjectInfoId, String orderId, String status){
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.orderId = orderId;
		this.status = status;
	}

	public FcOrderInfo(String fcProjectInfoId, FcProjectInfo fcProjectInfo, String orderId, String fstSvcType,
			String sndSvcType, Date serviceDateBegin, Date serviceDateEnd, Double signAmount, Double ownProdCost,
			Double specifySubCost) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.fcProjectInfo = fcProjectInfo;
		this.orderId = orderId;
		this.fstSvcType = fstSvcType;
		this.sndSvcType = sndSvcType;
		this.serviceDateBegin = serviceDateBegin;
		this.serviceDateEnd = serviceDateEnd;
		this.signAmount = signAmount;
		this.ownProdCost = ownProdCost;
		this.specifySubCost = specifySubCost;
	}

	@Length(min=1, max=64, message="项目信息长度必须介于 1 和 64 之间")
	public String getFcProjectInfoId() {
		return fcProjectInfoId;
	}

	public void setFcProjectInfoId(String fcProjectInfoId) {
		this.fcProjectInfoId = fcProjectInfoId;
	}
	
	@Length(min=1, max=30, message="订单编号长度必须介于 1 和 30 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="服务期开始不能为空")
	public Date getServiceDateBegin() {
		return serviceDateBegin;
	}

	public void setServiceDateBegin(Date serviceDateBegin) {
		this.serviceDateBegin = serviceDateBegin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="服务期结束不能为空")
	public Date getServiceDateEnd() {
		return serviceDateEnd;
	}

	public void setServiceDateEnd(Date serviceDateEnd) {
		this.serviceDateEnd = serviceDateEnd;
	}
	
	@NotNull(message="签约金额不能为空")
	public Double getSignAmount() {
		return signAmount;
	}

	public void setSignAmount(Double signAmount) {
		this.signAmount = signAmount;
	}
	
	@NotNull(message="自有产品成本不能为空")
	public Double getOwnProdCost() {
		return ownProdCost;
	}

	public void setOwnProdCost(Double ownProdCost) {
		this.ownProdCost = ownProdCost;
	}
	
	@NotNull(message="指定分包成本不能为空")
	public Double getSpecifySubCost() {
		return specifySubCost;
	}

	public void setSpecifySubCost(Double specifySubCost) {
		this.specifySubCost = specifySubCost;
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

	public FcProjectInfo getFcProjectInfo() {
		return fcProjectInfo;
	}

	public void setFcProjectInfo(FcProjectInfo fcProjectInfo) {
		this.fcProjectInfo = fcProjectInfo;
	}
	
}