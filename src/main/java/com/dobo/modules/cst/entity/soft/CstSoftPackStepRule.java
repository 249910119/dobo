/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.soft;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 系统软件服务规模阶梯配比表Entity
 * @author admin
 * @version 2017-03-02
 */
public class CstSoftPackStepRule extends DataEntity<CstSoftPackStepRule> {
	
	private static final long serialVersionUID = 1L;
	private String ruleId;		// 系统软件服务ID
	private String ruleDesc;		// 系统软件服务内容描述
	private BigDecimal areaMin;		// 区间最小边界
	private BigDecimal areaMax;		// 区间最大边界
	private BigDecimal areaValue;		// 区间值
	private BigDecimal step;		// 步长值
	private String judgeType;		// A0:左开右闭 A1:右开左闭
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstSoftPackStepRule() {
		super();
	}

	public CstSoftPackStepRule(String id){
		super(id);
	}

	@Length(min=1, max=32, message="系统软件服务ID长度必须介于 1 和 32 之间")
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=1, max=64, message="系统软件服务内容描述长度必须介于 1 和 64 之间")
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	@NotNull(message="区间最小边界不能为空")
	public BigDecimal getAreaMin() {
		return areaMin;
	}

	public void setAreaMin(BigDecimal areaMin) {
		this.areaMin = areaMin;
	}
	
	@NotNull(message="区间最大边界不能为空")
	public BigDecimal getAreaMax() {
		return areaMax;
	}

	public void setAreaMax(BigDecimal areaMax) {
		this.areaMax = areaMax;
	}
	
	@NotNull(message="区间值不能为空")
	public BigDecimal getAreaValue() {
		return areaValue;
	}

	public void setAreaValue(BigDecimal areaValue) {
		this.areaValue = areaValue;
	}
	
	public BigDecimal getStep() {
		return step;
	}

	public void setStep(BigDecimal step) {
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