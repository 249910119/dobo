/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.detail;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 订单备件故障成本（自有、分包）Entity
 * @author admin
 * @version 2019-01-18
 */
public class CstOrderBackCostInfo extends DataEntity<CstOrderBackCostInfo> {

	//导出excel字段
	public final static String[] expFieldNames = new String[]{
			"dcPrjId","orderId","detailId","prodId","resourceId","province","city","sla","amount","beginDate","endDate",
			"backCost","packId"
	};
	//导出excel字段标题
	public final static String[] expTitles = new String[]{
			"项目编号","订单编号","明细标识","服务产品","资源标识","省份","地市","服务级别","数量","开始时间","截止时间",
			"备件成本","合作包Id"
	};
	
	private static final long serialVersionUID = 1L;
	private String dcPrjId;		// 项目编号
	private String orderId;		// 订单编号
	private String detailId;		// 明细标识
	private String prodId;		// 服务产品标识
	private String resourceId;		// 资源标识
	private String province;		// 省份
	private String city;		// 地市
	private String sla;		// 服务级别
	private Double amount;		// 数量
	private Date beginDate;		// 开始时间
	private Date endDate;		// 截止时间
	private Double backCost;		// 备件成本
	private String packId;		// 合作包Id
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstOrderBackCostInfo() {
		super();
	}

	public CstOrderBackCostInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="项目编号长度必须介于 0 和 32 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}
	
	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=32, message="明细标识长度必须介于 0 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=0, max=32, message="服务产品标识长度必须介于 0 和 32 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=0, max=30, message="资源标识长度必须介于 0 和 30 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Length(min=0, max=15, message="省份长度必须介于 0 和 15 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=15, message="地市长度必须介于 0 和 15 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="服务级别长度必须介于 0 和 255 之间")
	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Double getBackCost() {
		return backCost;
	}

	public void setBackCost(Double backCost) {
		this.backCost = backCost;
	}
	
	@Length(min=0, max=15, message="合作包Id长度必须介于 0 和 15 之间")
	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}
	
	@Length(min=0, max=2, message="状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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