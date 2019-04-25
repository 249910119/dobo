/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.entity.result;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 系统人员Entity
 * @author admin
 * @version 2018-06-25
 */
public class CpStaffInfo extends DataEntity<CpStaffInfo> {
	
	private static final long serialVersionUID = 1L;
	private String staffId;		// staff_id
	private String staffName;		// staff_name
	private String orgName;		// org_name
	private String fullOrgName;		// full_org_name
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CpStaffInfo() {
		super();
	}

	public CpStaffInfo(String id){
		super(id);
	}

	@Length(min=1, max=20, message="staff_id长度必须介于 1 和 20 之间")
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@Length(min=1, max=30, message="staff_name长度必须介于 1 和 30 之间")
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@Length(min=1, max=15, message="org_name长度必须介于 1 和 15 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=1, max=256, message="full_org_name长度必须介于 1 和 256 之间")
	public String getFullOrgName() {
		return fullOrgName;
	}

	public void setFullOrgName(String fullOrgName) {
		this.fullOrgName = fullOrgName;
	}
	
	@Length(min=0, max=32, message="remark1长度必须介于 0 和 32 之间")
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@Length(min=0, max=32, message="remark2长度必须介于 0 和 32 之间")
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@Length(min=0, max=32, message="remark3长度必须介于 0 和 32 之间")
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
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