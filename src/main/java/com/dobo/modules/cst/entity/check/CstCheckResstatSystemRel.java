/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.check;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 巡检-资源计划分类对应设备大类关系表Entity
 * @author admin
 * @version 2016-11-22
 */
public class CstCheckResstatSystemRel extends DataEntity<CstCheckResstatSystemRel> {
	
	private static final long serialVersionUID = 1L;
	private String resstattypeName;		// 资源计划分类
	private String systemName;		// 设备大类
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String resstattypeId;		// 资源计划分类标识
	private String systemId;		// 设备大类标识
	private String descTypeName;		// 折减对应的设备大类
	
	public CstCheckResstatSystemRel() {
		super();
	}

	public CstCheckResstatSystemRel(String id){
		super(id);
	}

	@Length(min=1, max=128, message="资源计划分类长度必须介于 1 和 128 之间")
	public String getResstattypeName() {
		return resstattypeName;
	}

	public void setResstattypeName(String resstattypeName) {
		this.resstattypeName = resstattypeName;
	}
	
	@Length(min=1, max=30, message="设备大类长度必须介于 1 和 30 之间")
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
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
	
	@Length(min=0, max=64, message="资源计划分类标识长度必须介于 0 和 64 之间")
	public String getResstattypeId() {
		return resstattypeId;
	}

	public void setResstattypeId(String resstattypeId) {
		this.resstattypeId = resstattypeId;
	}
	
	@Length(min=0, max=64, message="设备大类标识长度必须介于 0 和 64 之间")
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDescTypeName() {
		return descTypeName;
	}

	public void setDescTypeName(String descTypeName) {
		this.descTypeName = descTypeName;
	}
	
}