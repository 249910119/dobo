/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.man;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 故障人工费率定义表Entity
 * @author admin
 * @version 2016-11-08
 */
public class CstManFailureManRate extends DataEntity<CstManFailureManRate> {
	
	private static final long serialVersionUID = 1L;
	private String deliveryRole;		// 生产角色
	private String isResident;		// 是否驻场
	private Double manCostYear;		// 人工年包
	private Double feeCostYear;		// 费用年包
	private Double urgeCostYear;		// 激励年包
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstManFailureManRate() {
		super();
	}

	public CstManFailureManRate(String id){
		super(id);
	}

	@Length(min=1, max=30, message="生产角色长度必须介于 1 和 30 之间")
	public String getDeliveryRole() {
		return deliveryRole;
	}

	public void setDeliveryRole(String deliveryRole) {
		this.deliveryRole = deliveryRole;
	}
	
	@Length(min=1, max=2, message="是否驻场长度必须介于 1 和 2 之间")
	public String getIsResident() {
		return isResident;
	}

	public void setIsResident(String isResident) {
		this.isResident = isResident;
	}
	
	@NotNull(message="人工年包不能为空")
	public Double getManCostYear() {
		return manCostYear;
	}

	public void setManCostYear(Double manCostYear) {
		this.manCostYear = manCostYear;
	}
	
	@NotNull(message="费用年包不能为空")
	public Double getFeeCostYear() {
		return feeCostYear;
	}

	public void setFeeCostYear(Double feeCostYear) {
		this.feeCostYear = feeCostYear;
	}
	
	@NotNull(message="激励年包不能为空")
	public Double getUrgeCostYear() {
		return urgeCostYear;
	}

	public void setUrgeCostYear(Double urgeCostYear) {
		this.urgeCostYear = urgeCostYear;
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
	
}