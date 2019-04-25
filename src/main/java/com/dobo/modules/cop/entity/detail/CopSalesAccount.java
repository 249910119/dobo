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
 * 销售员账户表Entity
 * @author admin
 * @version 2017-06-09
 */
public class CopSalesAccount extends DataEntity<CopSalesAccount> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// account_id
	private String dcPrjId;		// dc_prj_id
	private String dcPrjName;	//项目名称
	private String orderId;		// order_id
	private String staffId;		// staff_id
	private String prjType;		//项目状态
	private Double prodAmount;		// prod_amount
	private Double finalAmount;		// final_amount
	private Double usedAmount;		// used_amount
	private Double backProdAmount;		// back_prod_amount
	private Double backFinalAmount;		// back_final_amount
	private Double backUsedAmount;		// back_used_amount
	private Date serviceBegin;		//SERVICE_BEGIN
	private Date serviceEnd;		//SERVICE_END
	private String isShared;		// A0:可以；A1:不可以
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CopSalesAccount() {
		super();
	}

	public CopSalesAccount(String id){
		super(id);
	}

	@Length(min=1, max=32, message="account_id长度必须介于 1 和 32 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=15, message="dc_prj_id长度必须介于 1 和 15 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}
	
	@Length(min=1, max=128, message="dc_prj_name长度必须介于 1 和 128 之间")
	public String getDcPrjName() {
		return dcPrjName;
	}

	public void setDcPrjName(String dcPrjName) {
		this.dcPrjName = dcPrjName;
	}

	@Length(min=1, max=128, message="order_id长度必须介于 1 和 128 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=1, max=15, message="staff_id长度必须介于 1 和 15 之间")
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@Length(min=1, max=30, message="prj_type长度必须介于 1 和 30 之间")
	public String getPrjType() {
		return prjType;
	}

	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

	@NotNull(message="prod_amount不能为空")
	public Double getProdAmount() {
		return prodAmount;
	}

	public void setProdAmount(Double prodAmount) {
		this.prodAmount = prodAmount;
	}
	
	@NotNull(message="final_amount不能为空")
	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}
	
	@NotNull(message="used_amount不能为空")
	public Double getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Double usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	@NotNull(message="back_prod_amount不能为空")
	public Double getBackProdAmount() {
		return backProdAmount;
	}

	public void setBackProdAmount(Double backProdAmount) {
		this.backProdAmount = backProdAmount;
	}
	
	@NotNull(message="back_final_amount不能为空")
	public Double getBackFinalAmount() {
		return backFinalAmount;
	}

	public void setBackFinalAmount(Double backFinalAmount) {
		this.backFinalAmount = backFinalAmount;
	}
	
	@NotNull(message="back_used_amount不能为空")
	public Double getBackUsedAmount() {
		return backUsedAmount;
	}

	public void setBackUsedAmount(Double backUsedAmount) {
		this.backUsedAmount = backUsedAmount;
	}
	
	@Length(min=1, max=2, message="A0:可以；A1:不可以长度必须介于 1 和 2 之间")
	public String getIsShared() {
		return isShared;
	}

	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getServiceBegin() {
		return serviceBegin;
	}

	public void setServiceBegin(Date serviceBegin) {
		this.serviceBegin = serviceBegin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getServiceEnd() {
		return serviceEnd;
	}

	public void setServiceEnd(Date serviceEnd) {
		this.serviceEnd = serviceEnd;
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
	
}