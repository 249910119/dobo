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
 * 单次服务成本事业部阀值Entity
 * @author admin
 * @version 2018-04-20
 */
public class CopSalesOrgAccount extends DataEntity<CopSalesOrgAccount> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// account_id
	private String orgId;		// 事业部Id
	private String orgName;		// 事业部名称
	private String buName;		// BU名称
	private Double signAmount;		// 签约任务
	private Double caseMaxAmount;		// 单次阀值
	private Double caseUsedAmount;		// 单次使用金额
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CopSalesOrgAccount() {
		super();
	}

	public CopSalesOrgAccount(String id){
		super(id);
	}

	@Length(min=1, max=32, message="account_id长度必须介于 1 和 32 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=15, message="事业部Id长度必须介于 1 和 15 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=1, max=128, message="事业部名称长度必须介于 1 和 128 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=1, max=128, message="BU名称长度必须介于 1 和 128 之间")
	public String getBuName() {
		return buName;
	}

	public void setBuName(String buName) {
		this.buName = buName;
	}
	
	@NotNull(message="签约任务不能为空")
	public Double getSignAmount() {
		return signAmount;
	}

	public void setSignAmount(Double signAmount) {
		this.signAmount = signAmount;
	}
	
	@NotNull(message="单次阀值不能为空")
	public Double getCaseMaxAmount() {
		return caseMaxAmount;
	}

	public void setCaseMaxAmount(Double caseMaxAmount) {
		this.caseMaxAmount = caseMaxAmount;
	}
	
	@NotNull(message="单次使用金额不能为空")
	public Double getCaseUsedAmount() {
		return caseUsedAmount;
	}

	public void setCaseUsedAmount(Double caseUsedAmount) {
		this.caseUsedAmount = caseUsedAmount;
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