/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 故障率工时参数Entity
 * @author admin
 * @version 2019-03-18
 */
public class CstBaseCaseHour extends DataEntity<CstBaseCaseHour> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// 明细ID
	private String mfrName;		// 厂商
	private String equipTypeName;		// 技术方向
	private String modelgroupName;		// 型号组
	private String resourceDesc;		// 资源描述
	private Double lineoneCaseHour;		// 故障处理标准工时-2019一线
	private Double remoteCaseHour;		// CASE诊断、远程支持工时
	private String status;		// A0:有效/A1:无效
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）

	private Double sumLineoneCaseHour = 0.0;		// 故障处理标准工时-2019一线
	private Double sumRemoteCaseHour = 0.0;		// CASE诊断、远程支持工时
	private Double calcAmount = 0.0;		// 统计同厂商+技术方向
	
	public CstBaseCaseHour() {
		super();
	}

	public CstBaseCaseHour(String id){
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
	
	@Length(min=0, max=64, message="型号组长度必须介于 0 和 64 之间")
	public String getModelgroupName() {
		return modelgroupName;
	}

	public void setModelgroupName(String modelgroupName) {
		this.modelgroupName = modelgroupName;
	}
	
	@Length(min=0, max=256, message="资源描述长度必须介于 0 和 256 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	public Double getLineoneCaseHour() {
		if(lineoneCaseHour == null && calcAmount > 0) {
			lineoneCaseHour = BigDecimal.valueOf(sumLineoneCaseHour).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return lineoneCaseHour;
	}

	public void setLineoneCaseHour(Double lineoneCaseHour) {
		this.lineoneCaseHour = lineoneCaseHour;
	}
	
	public Double getRemoteCaseHour() {
		if(remoteCaseHour == null && calcAmount > 0) {
			remoteCaseHour = BigDecimal.valueOf(sumRemoteCaseHour).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return remoteCaseHour;
	}

	public void setRemoteCaseHour(Double remoteCaseHour) {
		this.remoteCaseHour = remoteCaseHour;
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

	public Double getSumLineoneCaseHour() {
		return sumLineoneCaseHour;
	}

	public void setSumLineoneCaseHour(Double sumLineoneCaseHour) {
		this.sumLineoneCaseHour = sumLineoneCaseHour;
	}

	public Double getSumRemoteCaseHour() {
		return sumRemoteCaseHour;
	}

	public void setSumRemoteCaseHour(Double sumRemoteCaseHour) {
		this.sumRemoteCaseHour = sumRemoteCaseHour;
	}

	public Double getCalcAmount() {
		return calcAmount;
	}

	public void setCalcAmount(Double calcAmount) {
		this.calcAmount = calcAmount;
	}
	
}