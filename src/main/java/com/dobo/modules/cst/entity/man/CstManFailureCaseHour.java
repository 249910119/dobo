/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.man;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 故障CASE处理工时定义表Entity
 * @author admin
 * @version 2016-11-08
 */
public class CstManFailureCaseHour extends DataEntity<CstManFailureCaseHour> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源标识
	private String resourceDesc;		// 资源描述
	private Double yearFailureRate;		// 年化故障率
	private Double line1OnceHour;		// 一线单次CASE现场处理故障工时
	private Double line2OnceHour;		// 二线单次CASE诊断、远程支持工时
	private Double line3OnceHour;		// 三线单次CASE远程处理故障工时
	private Double cmoOnceHour;		// CMO单次CASE处理工时
	private Double resMgrOnceHour;		// 资源岗单次CASE处理工时
	private String status;		// 状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String preStatus;		// 更新前状态
	
	public CstManFailureCaseHour() {
		super();
	}

	public CstManFailureCaseHour(String id){
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
	
	@NotNull(message="年化故障率不能为空")
	public Double getYearFailureRate() {
		return yearFailureRate;
	}

	public void setYearFailureRate(Double yearFailureRate) {
		this.yearFailureRate = yearFailureRate;
	}
	
	@NotNull(message="一线单次CASE现场处理故障工时不能为空")
	public Double getLine1OnceHour() {
		return line1OnceHour;
	}

	public void setLine1OnceHour(Double line1OnceHour) {
		this.line1OnceHour = line1OnceHour;
	}
	
	@NotNull(message="二线单次CASE诊断、远程支持工时不能为空")
	public Double getLine2OnceHour() {
		return line2OnceHour;
	}

	public void setLine2OnceHour(Double line2OnceHour) {
		this.line2OnceHour = line2OnceHour;
	}
	
	@NotNull(message="三线单次CASE远程处理故障工时不能为空")
	public Double getLine3OnceHour() {
		return line3OnceHour;
	}

	public void setLine3OnceHour(Double line3OnceHour) {
		this.line3OnceHour = line3OnceHour;
	}
	
	@NotNull(message="CMO单次CASE处理工时不能为空")
	public Double getCmoOnceHour() {
		return cmoOnceHour;
	}

	public void setCmoOnceHour(Double cmoOnceHour) {
		this.cmoOnceHour = cmoOnceHour;
	}
	
	@NotNull(message="资源岗单次CASE处理工时不能为空")
	public Double getResMgrOnceHour() {
		return resMgrOnceHour;
	}

	public void setResMgrOnceHour(Double resMgrOnceHour) {
		this.resMgrOnceHour = resMgrOnceHour;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	@Length(min=0, max=2, message="更新前状态长度必须介于 0 和 2 之间")
	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	
}