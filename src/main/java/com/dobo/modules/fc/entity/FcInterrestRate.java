/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 存息贷息利率定义Entity
 * @author admin
 * @version 2016-11-05
 */
public class FcInterrestRate extends DataEntity<FcInterrestRate> {
	
	private static final long serialVersionUID = 1L;
	private String rateType;		// 利息类型
	private String rateName;		// 利息类型
	private Double rateValue;		// 利率值
	private Date ratePublishDate;		// 发布时间
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public FcInterrestRate() {
		super();
	}

	public FcInterrestRate(String id){
		super(id);
	}

	@Length(min=1, max=2, message="利息类型长度必须介于 1 和 2 之间")
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	@Length(min=1, max=30, message="利息类型长度必须介于 1 和 30 之间")
	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	
	@NotNull(message="利率值不能为空")
	public Double getRateValue() {
		return rateValue;
	}

	public void setRateValue(Double rateValue) {
		this.rateValue = rateValue;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发布时间不能为空")
	public Date getRatePublishDate() {
		return ratePublishDate;
	}

	public void setRatePublishDate(Date ratePublishDate) {
		this.ratePublishDate = ratePublishDate;
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
	
	@Length(min=1, max=1, message="保存标记长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}