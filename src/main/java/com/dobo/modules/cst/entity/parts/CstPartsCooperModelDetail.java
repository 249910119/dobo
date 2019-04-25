/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件合作清单Entity
 * @author admin
 * @version 2019-01-11
 */
public class CstPartsCooperModelDetail extends DataEntity<CstPartsCooperModelDetail> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源标识
	private String resourceDesc;		// 资源描述（格式：厂商|型号|型号组|技术方向）
	private String packId;		// 合作包ID
	private String isCooper;		// 是否合作（0：否； 1：是）
	private String isHighEnd;		// 是否高端（0：否； 1：是）
	private String dateId;		// 时间ID：代表下面的开始和截止时间
	private Date beginDate;		// 开始时间
	private Date endDate;		// 截止时间
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstPartsCooperModelDetail() {
		super();
	}

	public CstPartsCooperModelDetail(String id){
		super(id);
	}

	@Length(min=0, max=30, message="资源标识长度必须介于 0 和 30 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Length(min=0, max=255, message="资源描述（格式：厂商|型号|型号组|技术方向）长度必须介于 0 和 255 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	@Length(min=0, max=30, message="合作包ID长度必须介于 0 和 30 之间")
	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}
	
	@Length(min=0, max=1, message="是否合作（0：否； 1：是）长度必须介于 0 和 1 之间")
	public String getIsCooper() {
		return isCooper;
	}

	public void setIsCooper(String isCooper) {
		this.isCooper = isCooper;
	}
	
	@Length(min=0, max=1, message="是否高端（0：否； 1：是）长度必须介于 0 和 1 之间")
	public String getIsHighEnd() {
		return isHighEnd;
	}

	public void setIsHighEnd(String isHighEnd) {
		this.isHighEnd = isHighEnd;
	}
	
	@Length(min=0, max=15, message="时间ID：代表下面的开始和截止时间长度必须介于 0 和 15 之间")
	public String getDateId() {
		return dateId;
	}

	public void setDateId(String dateId) {
		this.dateId = dateId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=2, message="状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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
	
	@Length(min=0, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}