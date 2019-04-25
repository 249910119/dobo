/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 项目管理工作量阶梯配比Entity
 * @author admin
 * @version 2016-12-12
 */
public class CstManManageStepRule extends DataEntity<CstManManageStepRule> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// prod_id
	private Double areaMin;		// area_min
	private Double areaMax;		// area_max
	private Double areaMinValue;		// area_min_value
	private Double areaMaxValue;		// area_max_value
	private Double step;		// step
	private String judgeType;		// A0:左开右闭 A1:右开左闭
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstManManageStepRule() {
		super();
	}

	public CstManManageStepRule(String id){
		super(id);
	}

	@Length(min=0, max=30, message="prod_id长度必须介于 0 和 30 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	public Double getAreaMin() {
		return areaMin;
	}

	public void setAreaMin(Double areaMin) {
		this.areaMin = areaMin;
	}
	
	public Double getAreaMax() {
		return areaMax;
	}

	public void setAreaMax(Double areaMax) {
		this.areaMax = areaMax;
	}
	
	public Double getAreaMinValue() {
		return areaMinValue;
	}

	public void setAreaMinValue(Double areaMinValue) {
		this.areaMinValue = areaMinValue;
	}
	
	public Double getAreaMaxValue() {
		return areaMaxValue;
	}

	public void setAreaMaxValue(Double areaMaxValue) {
		this.areaMaxValue = areaMaxValue;
	}
	
	public Double getStep() {
		return step;
	}

	public void setStep(Double step) {
		this.step = step;
	}
	
	@Length(min=0, max=2, message="A0:左开右闭 A1:右开左闭长度必须介于 0 和 2 之间")
	public String getJudgeType() {
		return judgeType;
	}

	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}
	
	@Length(min=1, max=2, message="状态（A0:有效/A1:无效）长度必须介于 1 和 2 之间")
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