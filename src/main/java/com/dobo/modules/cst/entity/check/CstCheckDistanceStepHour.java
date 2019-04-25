/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.check;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 巡检-路程工时阶梯系数表Entity
 * @author admin
 * @version 2016-11-21
 */
public class CstCheckDistanceStepHour extends DataEntity<CstCheckDistanceStepHour> {
	
	private static final long serialVersionUID = 1L;
	private Integer systemResnumMin;		// 同城同设备大类下设备数量下限
	private Integer systemResnumMax;		// 同城同设备大类下设备数量上限
	private Integer typeResnum;		// 设备大类下设备类型种类数
	private Double distanceHour;		// 路程工时系数
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstCheckDistanceStepHour() {
		super();
	}

	public CstCheckDistanceStepHour(String id){
		super(id);
	}

	@NotNull(message="同城同设备大类下设备数量下限不能为空")
	public Integer getSystemResnumMin() {
		return systemResnumMin;
	}

	public void setSystemResnumMin(Integer systemResnumMin) {
		this.systemResnumMin = systemResnumMin;
	}
	
	@NotNull(message="同城同设备大类下设备数量上限不能为空")
	public Integer getSystemResnumMax() {
		return systemResnumMax;
	}

	public void setSystemResnumMax(Integer systemResnumMax) {
		this.systemResnumMax = systemResnumMax;
	}
	
	@NotNull(message="设备大类下设备类型种类数不能为空")
	public Integer getTypeResnum() {
		return typeResnum;
	}

	public void setTypeResnum(Integer typeResnum) {
		this.typeResnum = typeResnum;
	}
	
	@NotNull(message="路程工时系数不能为空")
	public Double getDistanceHour() {
		return distanceHour;
	}

	public void setDistanceHour(Double distanceHour) {
		this.distanceHour = distanceHour;
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
	
}