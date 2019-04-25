/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 项目计划付款明细Entity
 * @author admin
 * @version 2016-11-06
 */
public class FcPlanPayDetail extends DataEntity<FcPlanPayDetail> {
	
	private static final long serialVersionUID = 1L;
	private String fcProjectInfoId;		// 项目信息
	private FcProjectInfo fcProjectInfo;		// 项目信息
	private Integer displayOrder;		// 期次
	private Date planPayTime;		// 计划收款时间
	private Double planPayAmount;		// 计划收款金额
	private Double planPayScale;		// 计划收款比例
	private String payType;		// 支付类型
	private String payCurrency;		// 支付币种
	private Integer planPayDays;		// 收款时间间隔天数
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public FcPlanPayDetail() {
		super();
	}

	public FcPlanPayDetail(String id){
		super(id);
	}

	public FcPlanPayDetail(String fcProjectInfoId, Date planPayTime, Double planPayAmount) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.planPayTime = planPayTime;
		this.planPayAmount = planPayAmount;
	}

	public FcPlanPayDetail(String fcProjectInfoId, FcProjectInfo fcProjectInfo, Integer displayOrder, Date planPayTime,
			Double planPayAmount, Double planPayScale, String payType, String payCurrency, Integer planPayDays) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.fcProjectInfo = fcProjectInfo;
		this.displayOrder = displayOrder;
		this.planPayTime = planPayTime;
		this.planPayAmount = planPayAmount;
		this.planPayScale = planPayScale;
		this.payType = payType;
		this.payCurrency = payCurrency;
		this.planPayDays = planPayDays;
	}

	@Length(min=1, max=64, message="项目信息长度必须介于 1 和 64 之间")
	public String getFcProjectInfoId() {
		return fcProjectInfoId;
	}

	public void setFcProjectInfoId(String fcProjectInfoId) {
		this.fcProjectInfoId = fcProjectInfoId;
	}
	
	@NotNull(message="期次不能为空")
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="计划收款时间不能为空")
	public Date getPlanPayTime() {
		return planPayTime;
	}

	public void setPlanPayTime(Date planPayTime) {
		this.planPayTime = planPayTime;
	}
	
	@NotNull(message="计划收款金额不能为空")
	public Double getPlanPayAmount() {
		return planPayAmount;
	}

	public void setPlanPayAmount(Double planPayAmount) {
		this.planPayAmount = planPayAmount;
	}
	
	@NotNull(message="计划收款比例不能为空")
	public Double getPlanPayScale() {
		return planPayScale;
	}

	public void setPlanPayScale(Double planPayScale) {
		this.planPayScale = planPayScale;
	}
	
	@Length(min=1, max=2, message="支付类型长度必须介于 1 和 2 之间")
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
	
	@NotNull(message="收款时间间隔天数不能为空")
	public Integer getPlanPayDays() {
		return planPayDays;
	}

	public void setPlanPayDays(Integer planPayDays) {
		this.planPayDays = planPayDays;
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