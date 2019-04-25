/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 财务项目编号对应事业部名称Entity
 * @author admin
 * @version 2017-12-22
 */
public class BasSybXmbhRel extends DataEntity<BasSybXmbhRel> {
	
	private static final long serialVersionUID = 1L;
	private String relId;		// ID
	private String erpxmbh;		// 财务项目号
	private String sybmc;		// 事业部名称
	private String fiscalYear;		// 财年
	private String remark;		// 备注
	
	public BasSybXmbhRel() {
		super();
	}

	public BasSybXmbhRel(String id){
		super(id);
	}

	@Length(min=0, max=60, message="ID长度必须介于 0 和 60 之间")
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
	
	@Length(min=0, max=24, message="财务项目号长度必须介于 0 和 24 之间")
	public String getErpxmbh() {
		return erpxmbh;
	}

	public void setErpxmbh(String erpxmbh) {
		this.erpxmbh = erpxmbh;
	}
	
	@Length(min=0, max=360, message="事业部名称长度必须介于 0 和 360 之间")
	public String getSybmc() {
		return sybmc;
	}

	public void setSybmc(String sybmc) {
		this.sybmc = sybmc;
	}
	
	@Length(min=0, max=20, message="财年长度必须介于 0 和 20 之间")
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