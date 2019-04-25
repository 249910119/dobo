/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.cases;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 单次、先行支持服务单价表Entity
 * @author admin
 * @version 2017-06-05
 */
public class CstManCaseSupportPrice extends DataEntity<CstManCaseSupportPrice> {
	
	private static final long serialVersionUID = 1L;
	private String supportId;		// 支持模式+产品标识
	private String supportName;		// 支持模式+产品
	private BigDecimal juniorPrice;		// 初级
	private BigDecimal middlePrice;		// 中级
	private BigDecimal seniorPrice;		// 高级
	private BigDecimal expertPrice;		// 专家
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstManCaseSupportPrice() {
		super();
	}

	public CstManCaseSupportPrice(String id){
		super(id);
	}

	@Length(min=0, max=30, message="支持模式+产品标识长度必须介于 0 和 30 之间")
	public String getSupportId() {
		return supportId;
	}

	public void setSupportId(String supportId) {
		this.supportId = supportId;
	}
	
	@Length(min=0, max=30, message="支持模式+产品长度必须介于 0 和 30 之间")
	public String getSupportName() {
		return supportName;
	}

	public void setSupportName(String supportName) {
		this.supportName = supportName;
	}
	
	public BigDecimal getJuniorPrice() {
		return juniorPrice;
	}

	public void setJuniorPrice(BigDecimal juniorPrice) {
		this.juniorPrice = juniorPrice;
	}
	
	public BigDecimal getMiddlePrice() {
		return middlePrice;
	}

	public void setMiddlePrice(BigDecimal middlePrice) {
		this.middlePrice = middlePrice;
	}
	
	public BigDecimal getSeniorPrice() {
		return seniorPrice;
	}

	public void setSeniorPrice(BigDecimal seniorPrice) {
		this.seniorPrice = seniorPrice;
	}
	
	public BigDecimal getExpertPrice() {
		return expertPrice;
	}

	public void setExpertPrice(BigDecimal expertPrice) {
		this.expertPrice = expertPrice;
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