/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 故障成本SLA采购成本系数Entity
 * @author admin
 * @version 2017-01-11
 */
public class CstPartsSlaCostRate extends DataEntity<CstPartsSlaCostRate> {
	
	private static final long serialVersionUID = 1L;
	private String slaName;		// 服务级别
	private String slaId;		// 服务级别id
	private Double slaCostRate;		// 故障成本SLA采购成本系数
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsSlaCostRate() {
		super();
	}

	public CstPartsSlaCostRate(String id){
		super(id);
	}

	@Length(min=1, max=20, message="服务级别长度必须介于 1 和 20 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@Length(min=1, max=20, message="服务级别id长度必须介于 1 和 20 之间")
	public String getSlaId() {
		return slaId;
	}

	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}
	
	@NotNull(message="故障成本SLA采购成本系数不能为空")
	public Double getSlaCostRate() {
		return slaCostRate;
	}

	public void setSlaCostRate(Double slaCostRate) {
		this.slaCostRate = slaCostRate;
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
	
}