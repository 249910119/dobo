/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 故障case样本Entity
 * @author admin
 * @version 2019-02-26
 */
public class CstBaseResourceCase extends DataEntity<CstBaseResourceCase> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// 明细ID
	private String resourceId;		// 资源标识
	private String resourceName;		// 资源名称
	private String resourceClass;		// A0:硬件设备/A1:软件资源/A2:人工资源/A3:点数资源/A4:CASE资源
	private String mfrName;		// 厂商
	private String equipTypeName;		// 技术方向
	private String modelgroupName;		// 型号组
	private String resourceTypeName;		// 资源计划分类
	private String resourceDesc;		// 资源描述
	private Double caseAmount = 0.0;		// case数量
	private Double yearSampleAmount = 0.0;		// 年化样本数
	private String status;		// A0:有效/A1:无效/A2:/审核中/A3:修改中
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）

	private String collectionId;		// 归集ID
	
	public CstBaseResourceCase() {
		super();
	}

	public CstBaseResourceCase(String id){
		super(id);
	}

	@Length(min=1, max=32, message="明细ID长度必须介于 1 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=1, max=32, message="资源标识长度必须介于 1 和 32 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Length(min=1, max=128, message="资源名称长度必须介于 1 和 128 之间")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	@Length(min=1, max=2, message="A0:硬件设备/A1:软件资源/A2:人工资源/A3:点数资源/A4:CASE资源长度必须介于 1 和 2 之间")
	public String getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}
	
	@Length(min=0, max=32, message="厂商长度必须介于 0 和 32 之间")
	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	
	@Length(min=0, max=32, message="技术方向长度必须介于 0 和 32 之间")
	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}
	
	@Length(min=0, max=32, message="型号组长度必须介于 0 和 32 之间")
	public String getModelgroupName() {
		return modelgroupName;
	}

	public void setModelgroupName(String modelgroupName) {
		this.modelgroupName = modelgroupName;
	}
	
	@Length(min=0, max=32, message="资源计划分类长度必须介于 0 和 32 之间")
	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}
	
	@Length(min=0, max=256, message="资源描述长度必须介于 0 和 256 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	public Double getCaseAmount() {
		return caseAmount;
	}

	public void setCaseAmount(Double caseAmount) {
		this.caseAmount = caseAmount;
	}
	
	public Double getYearSampleAmount() {
		return yearSampleAmount;
	}

	public void setYearSampleAmount(Double yearSampleAmount) {
		this.yearSampleAmount = yearSampleAmount;
	}
	
	@Length(min=1, max=2, message="A0:有效/A1:无效/A2:/审核中/A3:修改中长度必须介于 1 和 2 之间")
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

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	
}