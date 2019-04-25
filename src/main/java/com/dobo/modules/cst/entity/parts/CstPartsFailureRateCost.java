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
 * 备件故障率与采购成本定义Entity
 * @author admin
 * @version 2016-11-15
 */
public class CstPartsFailureRateCost extends DataEntity<CstPartsFailureRateCost> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源标识
	private String resourceDesc;		// 资源描述
	private String partsTypeName;		// 备件类型
	private Double failureRate;		// 备件故障率
	private Double averageCost;		// 采购平均成本
	private Double sumFailureCost;		// 备件故障率*采购平均成本
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsFailureRateCost() {
		super();
	}

	public CstPartsFailureRateCost(String id){
		super(id);
	}

	@Length(min=1, max=30, message="资源标识长度必须介于 1 和 30 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Length(min=1, max=255, message="资源描述长度必须介于 1 和 255 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	@Length(min=1, max=2, message="备件类型长度必须介于 1 和 2 之间")
	public String getPartsTypeName() {
		return partsTypeName;
	}

	public void setPartsTypeName(String partsTypeName) {
		this.partsTypeName = partsTypeName;
	}
	
	@NotNull(message="备件故障率不能为空")
	public Double getFailureRate() {
		return failureRate;
	}

	public void setFailureRate(Double failureRate) {
		this.failureRate = failureRate;
	}
	
	@NotNull(message="采购平均成本不能为空")
	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}
	
	public Double getSumFailureCost() {
		return sumFailureCost;
	}

	public void setSumFailureCost(Double sumFailureCost) {
		this.sumFailureCost = sumFailureCost;
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