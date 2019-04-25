/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.detail;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.dobo.common.persistence.DataEntity;

/**
 * 订单清单成本明细Entity
 * @author admin
 * @version 2016-11-14
 */
public class CstDetailCostInfo extends DataEntity<CstDetailCostInfo> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// detail_id 明细标识
	private String keyId;		// key_id 成本标识
	private Double resPlan;		// res_plan 资源计划
	private Double manCost;		// man_cost 人工
	private Double feeCost;		// fee_cost 费用
	private Double urgeCost;		// urge_cost 激励
	private Double travlFee;		// travl_fee 差旅
	private Double baseFee;		// base_fee 其他
	private String status;		// status
	private String prodId;		// prod_id
	private String dcPrjId;		// 项目编号
	private String mfrName;		//厂商
	private String resourceName;	//型号
	private Date beginDate;		//服务开始时间
	private Date endDate;		//服务结束时间
	
	public CstDetailCostInfo() {
		super();
	}

	public CstDetailCostInfo(String id){
		super(id);
	}

	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}

	@Length(min=1, max=32, message="detail_id长度必须介于 1 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=1, max=32, message="key_id长度必须介于 1 和 32 之间")
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	@NotNull(message="res_plan不能为空")
	public Double getResPlan() {
		return resPlan;
	}

	public void setResPlan(Double resPlan) {
		this.resPlan = resPlan;
	}
	
	@NotNull(message="man_cost不能为空")
	public Double getManCost() {
		return manCost;
	}

	public void setManCost(Double manCost) {
		this.manCost = manCost;
	}
	
	@NotNull(message="fee_cost不能为空")
	public Double getFeeCost() {
		return feeCost;
	}

	public void setFeeCost(Double feeCost) {
		this.feeCost = feeCost;
	}
	
	@NotNull(message="urge_cost不能为空")
	public Double getUrgeCost() {
		return urgeCost;
	}

	public void setUrgeCost(Double urgeCost) {
		this.urgeCost = urgeCost;
	}
	
	@NotNull(message="travl_fee不能为空")
	public Double getTravlFee() {
		return travlFee;
	}

	public void setTravlFee(Double travlFee) {
		this.travlFee = travlFee;
	}
	
	@NotNull(message="base_fee不能为空")
	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}
	
	@Length(min=1, max=2, message="status长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="prod_id长度必须介于 0 和 32 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}