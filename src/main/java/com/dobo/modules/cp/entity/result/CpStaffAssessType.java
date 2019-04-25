/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.entity.result;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 系统人员测评行为、能力和模块划分Entity
 * @author admin
 * @version 2018-06-08
 */
public class CpStaffAssessType extends DataEntity<CpStaffAssessType> {
	
	private static final long serialVersionUID = 1L;
	private String jobLevelId;		// 职位级别
	private String moduleId;		// 模块ID
	private String moduleName;		// 模块名称
	private String abilityId;		// 能力ID
	private String abilityName;		// 能力名称
	private String actionId;		// 行为ID
	private String actionName;		// 行为名称
	private String numberName;		// 对应的记录表序号名称
	private String moduleOrder;		// 模块排序
	private String abilityOrder;		// 能力排序
	private String actionOrder;		// 行为排序
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private Date endDate;		// 有效截止日期
	
	public CpStaffAssessType() {
		super();
	}

	public CpStaffAssessType(String id){
		super(id);
	}

	@Length(min=1, max=4, message="职位级别长度必须介于 1 和 4 之间")
	public String getJobLevelId() {
		return jobLevelId;
	}

	public void setJobLevelId(String jobLevelId) {
		this.jobLevelId = jobLevelId;
	}
	
	@Length(min=1, max=15, message="模块ID长度必须介于 1 和 15 之间")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Length(min=1, max=30, message="模块名称长度必须介于 1 和 30 之间")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@Length(min=1, max=15, message="能力ID长度必须介于 1 和 15 之间")
	public String getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}
	
	@Length(min=1, max=30, message="能力名称长度必须介于 1 和 30 之间")
	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}
	
	@Length(min=1, max=15, message="行为ID长度必须介于 1 和 15 之间")
	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
	@Length(min=1, max=256, message="行为名称长度必须介于 1 和 256 之间")
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	@Length(min=1, max=20, message="对应的记录表序号名称长度必须介于 1 和 20 之间")
	public String getNumberName() {
		return numberName;
	}

	public void setNumberName(String numberName) {
		this.numberName = numberName;
	}
	
	@Length(min=0, max=4, message="模块排序长度必须介于 0 和 4 之间")
	public String getModuleOrder() {
		return moduleOrder;
	}

	public void setModuleOrder(String moduleOrder) {
		this.moduleOrder = moduleOrder;
	}
	
	@Length(min=0, max=4, message="能力排序长度必须介于 0 和 4 之间")
	public String getAbilityOrder() {
		return abilityOrder;
	}

	public void setAbilityOrder(String abilityOrder) {
		this.abilityOrder = abilityOrder;
	}
	
	@Length(min=0, max=4, message="行为排序长度必须介于 0 和 4 之间")
	public String getActionOrder() {
		return actionOrder;
	}

	public void setActionOrder(String actionOrder) {
		this.actionOrder = actionOrder;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}