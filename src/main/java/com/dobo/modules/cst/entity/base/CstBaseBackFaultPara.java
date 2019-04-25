/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件故障率参数获取Entity
 * @author admin
 * @version 2019-03-27
 */
public class CstBaseBackFaultPara extends DataEntity<CstBaseBackFaultPara> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// 明细ID
	private String mfrName;		// 厂商
	private String equipTypeName;		// 技术方向
	private String modelGroupName;		// 型号组
	private String resstattypeName;		// 资源计划分类
	private String resourceDesc;		// 资源描述
	private Double onlineAmount;		// 在保数量
	private Double changeAmount;		// 更换数量
	private Double backFaultRate;		// 备件故障率
	private Double averFaultCost;		// 平均故障成本
	private String status;		// A0:有效/A1:无效
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstBaseBackFaultPara() {
		super();
	}

	public CstBaseBackFaultPara(String id){
		super(id);
	}

	@Length(min=1, max=32, message="明细ID长度必须介于 1 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=0, max=32, message="厂商长度必须介于 0 和 32 之间")
	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	
	@Length(min=0, max=64, message="技术方向长度必须介于 0 和 64 之间")
	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}
	
	public String getModelGroupName() {
		return modelGroupName;
	}

	public void setModelGroupName(String modelGroupName) {
		this.modelGroupName = modelGroupName;
	}

	@Length(min=0, max=64, message="资源计划分类长度必须介于 0 和 64 之间")
	public String getResstattypeName() {
		return resstattypeName;
	}

	public void setResstattypeName(String resstattypeName) {
		this.resstattypeName = resstattypeName;
	}
	
	@Length(min=0, max=256, message="资源描述长度必须介于 0 和 256 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	public Double getOnlineAmount() {
		return onlineAmount;
	}

	public void setOnlineAmount(Double onlineAmount) {
		this.onlineAmount = onlineAmount;
	}
	
	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}
	
	public Double getBackFaultRate() {
		return backFaultRate;
	}

	public void setBackFaultRate(Double backFaultRate) {
		this.backFaultRate = backFaultRate;
	}
	
	public Double getAverFaultCost() {
		return averFaultCost;
	}

	public void setAverFaultCost(Double averFaultCost) {
		this.averFaultCost = averFaultCost;
	}
	
	@Length(min=1, max=2, message="A0:有效/A1:无效长度必须介于 1 和 2 之间")
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