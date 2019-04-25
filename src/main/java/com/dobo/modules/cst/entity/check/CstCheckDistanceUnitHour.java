/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.check;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 巡检-单台路程工时表Entity
 * @author admin
 * @version 2016-11-22
 */
public class CstCheckDistanceUnitHour extends DataEntity<CstCheckDistanceUnitHour> {
	
	private static final long serialVersionUID = 1L;
	private String slaName;		// 巡检服务级别(深度巡检，远程巡检，现场巡检)
	private String systemName;		// 设备大类
	private Double unitDistanceHour;		// 单台路程工时
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String systemId;		// 设备大类标识
	
	public CstCheckDistanceUnitHour() {
		super();
	}

	public CstCheckDistanceUnitHour(String id){
		super(id);
	}

	@Length(min=1, max=30, message="巡检服务级别(深度巡检，远程巡检，现场巡检)长度必须介于 1 和 30 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@Length(min=1, max=30, message="设备大类长度必须介于 1 和 30 之间")
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@NotNull(message="单台路程工时不能为空")
	public Double getUnitDistanceHour() {
		return unitDistanceHour;
	}

	public void setUnitDistanceHour(Double unitDistanceHour) {
		this.unitDistanceHour = unitDistanceHour;
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
	
	@Length(min=0, max=1, message="保存标记长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
	@Length(min=0, max=64, message="设备大类标识长度必须介于 0 和 64 之间")
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
}