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
 * 巡检级别配比定义Entity
 * @author admin
 * @version 2016-11-22
 */
public class CstCheckSlaPara extends DataEntity<CstCheckSlaPara> {
	
	private static final long serialVersionUID = 1L;
	private String resModelId;		// 型号组
	private String resModelDesc;		// 型号组描述
	private String slaName;		// 巡检服务级别
	private Double line1Level1Scale;		// 一线1级配比
	private Double line1Level2Scale;		// 一线2级配比
	private Double line1Level3Scale;		// 一线3级配比
	private Double line1Level4Scale;		// 一线4级配比
	private Double line1Level5Scale;		// 一线5级配比
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String mfrName;		// 厂商
	private String equipTypeName;	// 技术方向
	private Double calcAmount = 0.0;		// 统计同厂商+技术方向
	private Double sumLine1Level1Scale = 0.0;		// 一线1级配比
	private Double sumLine1Level2Scale = 0.0;		// 一线2级配比
	private Double sumLine1Level3Scale = 0.0;		// 一线3级配比
	private Double sumLine1Level4Scale = 0.0;		// 一线4级配比
	private Double sumLine1Level5Scale = 0.0;		// 一线5级配比
	
	public CstCheckSlaPara() {
		super();
	}

	public CstCheckSlaPara(String id){
		super(id);
	}

	@Length(min=1, max=64, message="型号组长度必须介于 1 和 64 之间")
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
	
	@Length(min=1, max=30, message="巡检服务级别长度必须介于 1 和 30 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@NotNull(message="一线1级配比不能为空")
	public Double getLine1Level1Scale() {
		if(line1Level1Scale == null && calcAmount > 0) {
			line1Level1Scale = BigDecimal.valueOf(sumLine1Level1Scale).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1Level1Scale;
	}

	public void setLine1Level1Scale(Double line1Level1Scale) {
		this.line1Level1Scale = line1Level1Scale;
	}
	
	@NotNull(message="一线2级配比不能为空")
	public Double getLine1Level2Scale() {
		if(line1Level2Scale == null && calcAmount > 0) {
			line1Level2Scale = BigDecimal.valueOf(sumLine1Level2Scale).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1Level2Scale;
	}

	public void setLine1Level2Scale(Double line1Level2Scale) {
		this.line1Level2Scale = line1Level2Scale;
	}
	
	@NotNull(message="一线3级配比不能为空")
	public Double getLine1Level3Scale() {
		if(line1Level3Scale == null && calcAmount > 0) {
			line1Level3Scale = BigDecimal.valueOf(sumLine1Level3Scale).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1Level3Scale;
	}

	public void setLine1Level3Scale(Double line1Level3Scale) {
		this.line1Level3Scale = line1Level3Scale;
	}
	
	@NotNull(message="一线4级配比不能为空")
	public Double getLine1Level4Scale() {
		if(line1Level4Scale == null && calcAmount > 0) {
			line1Level4Scale = BigDecimal.valueOf(sumLine1Level4Scale).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1Level4Scale;
	}

	public void setLine1Level4Scale(Double line1Level4Scale) {
		this.line1Level4Scale = line1Level4Scale;
	}
	
	public Double getLine1Level5Scale() {
		if(line1Level5Scale == null && calcAmount > 0) {
			line1Level5Scale = BigDecimal.valueOf(sumLine1Level5Scale).divide(BigDecimal.valueOf(calcAmount), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return line1Level5Scale;
	}

	public void setLine1Level5Scale(Double line1Level5Scale) {
		this.line1Level5Scale = line1Level5Scale;
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

	public Double getSumLine1Level1Scale() {
		return sumLine1Level1Scale;
	}

	public void setSumLine1Level1Scale(Double sumLine1Level1Scale) {
		this.sumLine1Level1Scale = sumLine1Level1Scale;
	}

	public Double getSumLine1Level2Scale() {
		return sumLine1Level2Scale;
	}

	public void setSumLine1Level2Scale(Double sumLine1Level2Scale) {
		this.sumLine1Level2Scale = sumLine1Level2Scale;
	}

	public Double getSumLine1Level3Scale() {
		return sumLine1Level3Scale;
	}

	public void setSumLine1Level3Scale(Double sumLine1Level3Scale) {
		this.sumLine1Level3Scale = sumLine1Level3Scale;
	}

	public Double getSumLine1Level4Scale() {
		return sumLine1Level4Scale;
	}

	public void setSumLine1Level4Scale(Double sumLine1Level4Scale) {
		this.sumLine1Level4Scale = sumLine1Level4Scale;
	}

	public Double getSumLine1Level5Scale() {
		return sumLine1Level5Scale;
	}

	public void setSumLine1Level5Scale(Double sumLine1Level5Scale) {
		this.sumLine1Level5Scale = sumLine1Level5Scale;
	}
	
}