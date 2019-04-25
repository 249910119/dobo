/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 事业部对应BU名称Entity
 * @author admin
 * @version 2017-12-22
 */
public class BasSybBuRel extends DataEntity<BasSybBuRel> {
	
	private static final long serialVersionUID = 1L;
	private String relId;		// ID
	private String sybmc;		// 事业部名称
	private String bumc;		// BU名称
	private String fiscalYear;		// 财年(YYYY)
	private String remark;		// 备注
	
	public BasSybBuRel() {
		super();
	}

	public BasSybBuRel(String id){
		super(id);
	}

	@Length(min=0, max=60, message="ID长度必须介于 0 和 60 之间")
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
	
	@Length(min=0, max=360, message="事业部名称长度必须介于 0 和 360 之间")
	public String getSybmc() {
		return sybmc;
	}

	public void setSybmc(String sybmc) {
		this.sybmc = sybmc;
	}
	
	@Length(min=0, max=360, message="BU名称长度必须介于 0 和 360 之间")
	public String getBumc() {
		return bumc;
	}

	public void setBumc(String bumc) {
		this.bumc = bumc;
	}
	
	@Length(min=0, max=20, message="财年(YYYY)长度必须介于 0 和 20 之间")
	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	@Length(min=0, max=360, message="备注长度必须介于 0 和 360 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}