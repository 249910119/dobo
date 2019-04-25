/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 实际付款明细Entity
 * @author admin
 * @version 2017-12-25
 */
public class FcActualPayDetail extends DataEntity<FcActualPayDetail> {
	
	private static final long serialVersionUID = 1L;
	private String fkId;		// fk_id
	private String cgdddm;		// 采购订单代码
	private String xmdm;		// 项目代码
	private String gysdm;		// 供应商代码
	private String gysmc;		// 供应商名称
	private String gsdm;		// 公司代码
	private String sbumc;		// SBU名称
	private String bumc;		// BU名称
	private String sybmc;		// 事业部名称
	private String ywfwdm;		// 业务范围代码
	private String ywfwmc;		// 业务范围名称
	private String fkpzdm;		// 付款凭证代码
	private Double fkje;		// 付款金额
	private String fkrq;		// 付款日期
	private String pays;		// 付款方式
	private String invsdate;		// 开票日
	private String invedate;		// 到期日
	private String formid;		// 表单号
	
	public FcActualPayDetail() {
		super();
	}

	public FcActualPayDetail(String id){
		super(id);
	}

	@Length(min=0, max=60, message="fk_id长度必须介于 0 和 60 之间")
	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}
	
	@Length(min=0, max=60, message="采购订单代码长度必须介于 0 和 60 之间")
	public String getCgdddm() {
		return cgdddm;
	}

	public void setCgdddm(String cgdddm) {
		this.cgdddm = cgdddm;
	}
	
	@Length(min=0, max=108, message="项目代码长度必须介于 0 和 108 之间")
	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}
	
	@Length(min=0, max=108, message="供应商代码长度必须介于 0 和 108 之间")
	public String getGysdm() {
		return gysdm;
	}

	public void setGysdm(String gysdm) {
		this.gysdm = gysdm;
	}
	
	@Length(min=0, max=240, message="供应商名称长度必须介于 0 和 240 之间")
	public String getGysmc() {
		return gysmc;
	}

	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
	}
	
	@Length(min=0, max=100, message="公司代码长度必须介于 0 和 100 之间")
	public String getGsdm() {
		return gsdm;
	}

	public void setGsdm(String gsdm) {
		this.gsdm = gsdm;
	}
	
	@Length(min=0, max=200, message="SBU名称长度必须介于 0 和 200 之间")
	public String getSbumc() {
		return sbumc;
	}

	public void setSbumc(String sbumc) {
		this.sbumc = sbumc;
	}
	
	@Length(min=0, max=200, message="BU名称长度必须介于 0 和 200 之间")
	public String getBumc() {
		return bumc;
	}

	public void setBumc(String bumc) {
		this.bumc = bumc;
	}
	
	@Length(min=0, max=240, message="事业部名称长度必须介于 0 和 240 之间")
	public String getSybmc() {
		return sybmc;
	}

	public void setSybmc(String sybmc) {
		this.sybmc = sybmc;
	}
	
	@Length(min=0, max=24, message="业务范围代码长度必须介于 0 和 24 之间")
	public String getYwfwdm() {
		return ywfwdm;
	}

	public void setYwfwdm(String ywfwdm) {
		this.ywfwdm = ywfwdm;
	}
	
	@Length(min=0, max=240, message="业务范围名称长度必须介于 0 和 240 之间")
	public String getYwfwmc() {
		return ywfwmc;
	}

	public void setYwfwmc(String ywfwmc) {
		this.ywfwmc = ywfwmc;
	}
	
	@Length(min=0, max=120, message="付款凭证代码长度必须介于 0 和 120 之间")
	public String getFkpzdm() {
		return fkpzdm;
	}

	public void setFkpzdm(String fkpzdm) {
		this.fkpzdm = fkpzdm;
	}
	
	public Double getFkje() {
		return fkje;
	}

	public void setFkje(Double fkje) {
		this.fkje = fkje;
	}
	
	@Length(min=0, max=48, message="付款日期长度必须介于 0 和 48 之间")
	public String getFkrq() {
		return fkrq;
	}

	public void setFkrq(String fkrq) {
		this.fkrq = fkrq;
	}
	
	@Length(min=0, max=60, message="付款方式长度必须介于 0 和 60 之间")
	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}
	
	@Length(min=0, max=48, message="开票日长度必须介于 0 和 48 之间")
	public String getInvsdate() {
		return invsdate;
	}

	public void setInvsdate(String invsdate) {
		this.invsdate = invsdate;
	}
	
	@Length(min=0, max=48, message="到期日长度必须介于 0 和 48 之间")
	public String getInvedate() {
		return invedate;
	}

	public void setInvedate(String invedate) {
		this.invedate = invedate;
	}
	
	@Length(min=0, max=60, message="表单号长度必须介于 0 和 60 之间")
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}
	
}