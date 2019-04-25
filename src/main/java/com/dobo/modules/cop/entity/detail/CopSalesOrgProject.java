/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.entity.detail;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 限额项目Entity
 * @author admin
 * @version 2018-06-01
 */
public class CopSalesOrgProject extends DataEntity<CopSalesOrgProject> {
	
	private static final long serialVersionUID = 1L;
	private String dcPrjId;		// 项目编号
	private String dcPrjName;		// 项目名称
	private String orgId;		// 事业部Id
	private String orgName;		// 事业部名称
	private String saleId;		// 销售员ID
	private String fiscalYear;		// 财年
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private Date endDate;		// 有效截止日期
	
	public CopSalesOrgProject() {
		super();
	}

	public CopSalesOrgProject(String id){
		super(id);
	}

	@Length(min=1, max=32, message="项目编号长度必须介于 1 和 32 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}
	
	@Length(min=1, max=128, message="项目名称长度必须介于 1 和 128 之间")
	public String getDcPrjName() {
		return dcPrjName;
	}

	public void setDcPrjName(String dcPrjName) {
		this.dcPrjName = dcPrjName;
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
	
	@Length(min=1, max=30, message="销售员ID长度必须介于 1 和 30 之间")
	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	
	@Length(min=1, max=4, message="财年长度必须介于 1 和 4 之间")
	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}