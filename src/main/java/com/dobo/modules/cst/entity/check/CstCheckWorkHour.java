/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.check;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 巡检工时定义表Entity
 * @author admin
 * @version 2016-11-22
 */
public class CstCheckWorkHour extends DataEntity<CstCheckWorkHour> {
	
	private static final long serialVersionUID = 1L;
	private String resModelId;		// 型号组标识
	private String resModelDesc;		// 型号组描述
	private Double line1RemoteHour;		// 单台设备远程巡检一线工时
	private Double line1LocalHour;		// 单台设备现场巡检一线工时
	private Double line1DepthHour;		// 单台设备深度巡检一线工时
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String mfrName;		// 厂商
	private String equipTypeName;	// 技术方向
	private Double calcAmount = 0.0;		// 统计同厂商+技术方向
	private Double sumLine1RemoteHour = 0.0;		// 单台设备远程巡检一线工时
	private Double sumLine1LocalHour = 0.0;		// 单台设备现场巡检一线工时
	private Double sumLine1DepthHour = 0.0;		// 单台设备深度巡检一线工时
	
	public CstCheckWorkHour() {
		super();
	}

	public CstCheckWorkHour(String id){
		super(id);
	}

	@Length(min=1, max=64, message="型号组标识长度必须介于 1 和 64 之间")
	public String getResModelId() {
		return resModelId;
	}

	public void setResModelId(String resModelId) {
		this.resModelId = resModelId;
	}
	
	@Length(min=1, max=128, message="型号组描述长度必须介于 1 和 128 之间")
	public String getResModelDesc() {
		return resModelDesc;
	}

	public void setResModelDesc(String resModelDesc) {
		this.resModelDesc = resModelDesc;
	}
	
	@NotNull(message="单台设备远程巡检一线工时不能为空")
	public Double getLine1RemoteHour() {
		if(line1RemoteHour == null && calcAmount > 0) {
			line1RemoteHour = BigDecimal.valueOf(sumLine1RemoteHour).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1RemoteHour;
	}

	public void setLine1RemoteHour(Double line1RemoteHour) {
		this.line1RemoteHour = line1RemoteHour;
	}
	
	@NotNull(message="单台设备现场巡检一线工时不能为空")
	public Double getLine1LocalHour() {
		if(line1LocalHour == null && calcAmount > 0) {
			line1LocalHour = BigDecimal.valueOf(sumLine1LocalHour).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1LocalHour;
	}

	public void setLine1LocalHour(Double line1LocalHour) {
		this.line1LocalHour = line1LocalHour;
	}
	
	@NotNull(message="单台设备深度巡检一线工时不能为空")
	public Double getLine1DepthHour() {
		if(line1DepthHour == null && calcAmount > 0) {
			line1DepthHour = BigDecimal.valueOf(sumLine1DepthHour).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1DepthHour;
	}

	public void setLine1DepthHour(Double line1DepthHour) {
		this.line1DepthHour = line1DepthHour;
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

	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}

	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}

	public Double getCalcAmount() {
		return calcAmount;
	}

	public void setCalcAmount(Double calcAmount) {
		this.calcAmount = calcAmount;
	}

	public Double getSumLine1RemoteHour() {
		return sumLine1RemoteHour;
	}

	public void setSumLine1RemoteHour(Double sumLine1RemoteHour) {
		this.sumLine1RemoteHour = sumLine1RemoteHour;
	}

	public Double getSumLine1LocalHour() {
		return sumLine1LocalHour;
	}

	public void setSumLine1LocalHour(Double sumLine1LocalHour) {
		this.sumLine1LocalHour = sumLine1LocalHour;
	}

	public Double getSumLine1DepthHour() {
		return sumLine1DepthHour;
	}

	public void setSumLine1DepthHour(Double sumLine1DepthHour) {
		this.sumLine1DepthHour = sumLine1DepthHour;
	}
	
}