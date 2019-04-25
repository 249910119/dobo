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
 * 故障饱和度定义表Entity
 * @author admin
 * @version 2016-11-08
 */
public class CstManFailureDegree extends DataEntity<CstManFailureDegree> {
	
	private static final long serialVersionUID = 1L;
	private String slaName;		// 服务级别
	private String slaId;		// 服务级别标识
	private Double line1Degree;		// 一线饱和度
	private Double line2Degree;		// 二线饱和度
	private Double line3Degree;		// 三线饱和度
	private Double cmoDegree;		// CMO饱和度
	private Double resMgrDegree;		// 资源岗饱和度
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstManFailureDegree() {
		super();
	}

	public CstManFailureDegree(String id){
		super(id);
	}

	@Length(min=1, max=30, message="服务级别长度必须介于 1 和 30 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@Length(min=1, max=30, message="服务级别标识长度必须介于 1 和 30 之间")
	public String getSlaId() {
		return slaId;
	}

	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}
	
	@NotNull(message="一线饱和度不能为空")
	public Double getLine1Degree() {
		return line1Degree;
	}

	public void setLine1Degree(Double line1Degree) {
		this.line1Degree = line1Degree;
	}
	
	@NotNull(message="二线饱和度不能为空")
	public Double getLine2Degree() {
		return line2Degree;
	}

	public void setLine2Degree(Double line2Degree) {
		this.line2Degree = line2Degree;
	}
	
	@NotNull(message="三线饱和度不能为空")
	public Double getLine3Degree() {
		return line3Degree;
	}

	public void setLine3Degree(Double line3Degree) {
		this.line3Degree = line3Degree;
	}
	
	@NotNull(message="CMO饱和度不能为空")
	public Double getCmoDegree() {
		return cmoDegree;
	}

	public void setCmoDegree(Double cmoDegree) {
		this.cmoDegree = cmoDegree;
	}
	
	@NotNull(message="资源岗饱和度不能为空")
	public Double getResMgrDegree() {
		return resMgrDegree;
	}

	public void setResMgrDegree(Double resMgrDegree) {
		this.resMgrDegree = resMgrDegree;
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