/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.entity.detail;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 销售员账户使用明细表Entity
 * @author admin
 * @version 2017-06-09
 */
public class CopSalesUseDetail extends DataEntity<CopSalesUseDetail> {
	
	private static final long serialVersionUID = 1L;
	private String caseConfirmId;		// 主键
	private String salesAccountId;		// 主键
	private String accountId;		// account_id
	private String dcPrjId;		// 预付费具体使用项目
	private String orderId;		// order_id
	private String priceType;		// 1人员，2备件
	private Double useAmount;		// use_amount
	private String useDesc;		// use_desc
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private Double costScale;		// 预付费项目支付占比
	
	public CopSalesUseDetail() {
		super();
	}

	public CopSalesUseDetail(String id){
		super(id);
	}

	@Length(min=1, max=32, message="主键长度必须介于 1 和 32 之间")
	public String getCaseConfirmId() {
		return caseConfirmId;
	}

	public void setCaseConfirmId(String caseConfirmId) {
		this.caseConfirmId = caseConfirmId;
	}
	
	@Length(min=0, max=32, message="主键长度必须介于 0 和 32 之间")
	public String getSalesAccountId() {
		return salesAccountId;
	}

	public void setSalesAccountId(String salesAccountId) {
		this.salesAccountId = salesAccountId;
	}
	
	@Length(min=1, max=32, message="account_id长度必须介于 1 和 32 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=15, message="预付费具体使用项目长度必须介于 1 和 15 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}
	
	@Length(min=1, max=128, message="order_id长度必须介于 1 和 128之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=1, max=1, message="1人员，2备件长度必须介于 1 和 1 之间")
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@NotNull(message="use_amount不能为空")
	public Double getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(Double useAmount) {
		this.useAmount = useAmount;
	}
	
	@Length(min=1, max=255, message="use_desc长度必须介于 1 和 255 之间")
	public String getUseDesc() {
		return useDesc;
	}

	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
	}
	
	@Length(min=1, max=2, message="状态（A0:有效/A1:无效）长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2, message="更新前状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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
	
	@Length(min=1, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public Double getCostScale() {
		return costScale;
	}

	public void setCostScale(Double costScale) {
		this.costScale = costScale;
	}
	
}