/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.soft;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 系统软件服务资源配比表Entity
 * @author admin
 * @version 2017-03-02
 */
public class CstSoftPackDegreePara extends DataEntity<CstSoftPackDegreePara> {
	
	private static final long serialVersionUID = 1L;
	private String ruleId;		// 服务内容ID
	private String ruleDesc;		// 服务内容描述
	private BigDecimal line1Level2Scale;		// 1线2级配比
	private BigDecimal line1Level3Scale;		// 1线3级配比
	private BigDecimal line1Level4Scale;		// 1线4级配比
	private BigDecimal pmLevel3Scale;		// PM3级配比
	private BigDecimal pmLevel4Scale;		// PM4级配比
	private BigDecimal pmLevel5Scale;		// PM5级配比
	private BigDecimal slaAScale;		// 高级服务SP系数
	private BigDecimal slaBScale;		// 基础服务SP+系数
	private BigDecimal slaCScale;		// 基础服务SP-系数
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstSoftPackDegreePara() {
		super();
	}

	public CstSoftPackDegreePara(String id){
		super(id);
	}

	@Length(min=1, max=32, message="服务内容ID长度必须介于 1 和 32 之间")
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=0, max=64, message="服务内容描述长度必须介于 0 和 64 之间")
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	public BigDecimal getLine1Level2Scale() {
		return line1Level2Scale;
	}

	public void setLine1Level2Scale(BigDecimal line1Level2Scale) {
		this.line1Level2Scale = line1Level2Scale;
	}
	
	public BigDecimal getLine1Level3Scale() {
		return line1Level3Scale;
	}

	public void setLine1Level3Scale(BigDecimal line1Level3Scale) {
		this.line1Level3Scale = line1Level3Scale;
	}
	
	public BigDecimal getLine1Level4Scale() {
		return line1Level4Scale;
	}

	public void setLine1Level4Scale(BigDecimal line1Level4Scale) {
		this.line1Level4Scale = line1Level4Scale;
	}
	
	public BigDecimal getPmLevel3Scale() {
		return pmLevel3Scale;
	}

	public void setPmLevel3Scale(BigDecimal pmLevel3Scale) {
		this.pmLevel3Scale = pmLevel3Scale;
	}
	
	public BigDecimal getPmLevel4Scale() {
		return pmLevel4Scale;
	}

	public void setPmLevel4Scale(BigDecimal pmLevel4Scale) {
		this.pmLevel4Scale = pmLevel4Scale;
	}
	
	public BigDecimal getPmLevel5Scale() {
		return pmLevel5Scale;
	}

	public void setPmLevel5Scale(BigDecimal pmLevel5Scale) {
		this.pmLevel5Scale = pmLevel5Scale;
	}
	
	public BigDecimal getSlaAScale() {
		return slaAScale;
	}

	public void setSlaAScale(BigDecimal slaAScale) {
		this.slaAScale = slaAScale;
	}
	
	public BigDecimal getSlaBScale() {
		return slaBScale;
	}

	public void setSlaBScale(BigDecimal slaBScale) {
		this.slaBScale = slaBScale;
	}
	
	public BigDecimal getSlaCScale() {
		return slaCScale;
	}

	public void setSlaCScale(BigDecimal slaCScale) {
		this.slaCScale = slaCScale;
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