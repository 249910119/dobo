/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 无需计算计划内财务费用项目Entity
 * @author admin
 * @version 2017-07-05
 */
public class FcProjectInfoNocalc extends DataEntity<FcProjectInfoNocalc> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// project_code
	private String status;		// status
	private String preStatus;		// pre_status
	private Date statusChangeDate;		// status_change_date
	private String saveFlag;		// save_flag
	
	public FcProjectInfoNocalc() {
		super();
	}

	public FcProjectInfoNocalc(String id){
		super(id);
	}

	@Length(min=1, max=30, message="project_code长度必须介于 1 和 30 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=1, max=2, message="status长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2, message="pre_status长度必须介于 0 和 2 之间")
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
	
	@Length(min=1, max=1, message="save_flag长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}