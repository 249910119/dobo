/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 资源主数据
 */
public class CstResourceBaseInfo extends DataEntity<CstResourceBaseInfo> {
	
	//导出字段
	public final static String[] exportfieldNames = {
			"resourceId","mfrName","resourceName","equipTypeName", "modelGroupName", "resstattypeName", 
			"failureRate","lineOneCaseHour","remoteCaseHour",
			"slaName","failureLineOne1Rate","failureLineOne2Rate","failureLineOne3Rate","failureLineOne4Rate","failureLineOne5Rate"};
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"资源标识","厂商","资源名称","技术方向","型号组","资源计划分类","故障率","故障率标准工时","远程标准工时",
			"服务级别","一线1级占比","一线2级占比","一线3级占比","一线4级占比","一线5级占比"};
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源标识
	private String resourceName;		// 资源名称
	private String resourceDesc;		// 资源分类
	private String resourceClass;		// 资源类型
	private String modelGroupId;		// 型号组ID
	private String equipTypeId;		// 技术方向ID
	private String modelGroupName;		// 型号组
	private String equipTypeName;		// 技术方向
	private String resstattypeId;		// 资源计划分类
	private String resstattypeName;		// 资源计划分类
	private String mfrId;		    // 厂商ID
	private String mfrName;		    // 厂商
	private String laborLevel;		    // 工程师级别
	private String status;		// 状态
	
	private String failureRate; // 故障率
	private String lineOneCaseHour; // 故障率标准工时-一线
	private String remoteCaseHour; // 远程标准工时
	private String failureLineOne1Rate; // 故障一线1级占比
	private String failureLineOne2Rate; // 故障一线2级占比
	private String failureLineOne3Rate; // 故障一线3级占比
	private String failureLineOne4Rate; // 故障一线4级占比
	private String failureLineOne5Rate; // 故障一线5级占比
	private String slaName;
	
	public CstResourceBaseInfo() {
		super();
	}

	public CstResourceBaseInfo(String id){
		super(id);
	}

	@Length(min=1, max=15, message="资源标识长度必须介于 1 和 15 之间")
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

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	@Length(min=1, max=2, message="资源类型长度必须介于 1 和 2 之间")
	public String getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}

	public String getModelGroupId() {
		return modelGroupId;
	}

	public void setModelGroupId(String modelGroupId) {
		this.modelGroupId = modelGroupId;
	}

	public String getEquipTypeId() {
		return equipTypeId;
	}

	public void setEquipTypeId(String equipTypeId) {
		this.equipTypeId = equipTypeId;
	}

	public String getResstattypeId() {
		return resstattypeId;
	}

	public void setResstattypeId(String resstattypeId) {
		this.resstattypeId = resstattypeId;
	}

	public String getResstattypeName() {
		return resstattypeName;
	}

	public void setResstattypeName(String resstattypeName) {
		this.resstattypeName = resstattypeName;
	}

	public String getMfrId() {
		return mfrId;
	}

	public void setMfrId(String mfrId) {
		this.mfrId = mfrId;
	}

	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}

	public String getLaborLevel() {
		return laborLevel;
	}

	public void setLaborLevel(String laborLevel) {
		this.laborLevel = laborLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModelGroupName() {
		return modelGroupName;
	}

	public void setModelGroupName(String modelGroupName) {
		this.modelGroupName = modelGroupName;
	}

	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}

	public String getFailureRate() {
		return failureRate;
	}

	public void setFailureRate(String failureRate) {
		this.failureRate = failureRate;
	}

	public String getLineOneCaseHour() {
		return lineOneCaseHour;
	}

	public void setLineOneCaseHour(String lineOneCaseHour) {
		this.lineOneCaseHour = lineOneCaseHour;
	}

	public String getRemoteCaseHour() {
		return remoteCaseHour;
	}

	public void setRemoteCaseHour(String remoteCaseHour) {
		this.remoteCaseHour = remoteCaseHour;
	}

	public String getFailureLineOne1Rate() {
		return failureLineOne1Rate;
	}

	public void setFailureLineOne1Rate(String failureLineOne1Rate) {
		this.failureLineOne1Rate = failureLineOne1Rate;
	}

	public String getFailureLineOne2Rate() {
		return failureLineOne2Rate;
	}

	public void setFailureLineOne2Rate(String failureLineOne2Rate) {
		this.failureLineOne2Rate = failureLineOne2Rate;
	}

	public String getFailureLineOne3Rate() {
		return failureLineOne3Rate;
	}

	public void setFailureLineOne3Rate(String failureLineOne3Rate) {
		this.failureLineOne3Rate = failureLineOne3Rate;
	}

	public String getFailureLineOne4Rate() {
		return failureLineOne4Rate;
	}

	public void setFailureLineOne4Rate(String failureLineOne4Rate) {
		this.failureLineOne4Rate = failureLineOne4Rate;
	}

	public String getFailureLineOne5Rate() {
		return failureLineOne5Rate;
	}

	public void setFailureLineOne5Rate(String failureLineOne5Rate) {
		this.failureLineOne5Rate = failureLineOne5Rate;
	}

	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
}