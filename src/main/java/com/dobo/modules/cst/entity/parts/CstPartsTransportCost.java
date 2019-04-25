/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件发货运输平均成本定义Entity
 * @author admin
 * @version 2016-11-15
 */
public class CstPartsTransportCost extends DataEntity<CstPartsTransportCost> {
	
	private static final long serialVersionUID = 1L;
	private String slaLevel;		// 服务级别
	private String deliveryType;		// 发货类型
	private Double localFillRate;		// 本地备件满足率
	private String city;		// 城市
	private String province;		// 省
	private String transportType;		// 发货运输级别
	private String cityLevel;		// 城市级别
	private Double transProvinceCost;		// 跨省专送运输平均成本
	private Double localCityCost;		// 本市专送运输平均成本
	private Double thirdDeliveryCost;		// 第三方物流发货运输平均成本
	private Double thirdPickCost;		// 第三方物流回收取件运输平均成本
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsTransportCost() {
		super();
	}

	public CstPartsTransportCost(String id){
		super(id);
	}

	@Length(min=1, max=20, message="服务级别长度必须介于 1 和 20 之间")
	public String getSlaLevel() {
		return slaLevel;
	}

	public void setSlaLevel(String slaLevel) {
		this.slaLevel = slaLevel;
	}
	
	@Length(min=1, max=2, message="发货类型长度必须介于 1 和 2 之间")
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	@NotNull(message="本地备件满足率不能为空")
	public Double getLocalFillRate() {
		return localFillRate;
	}

	public void setLocalFillRate(Double localFillRate) {
		this.localFillRate = localFillRate;
	}
	
	@Length(min=1, max=30, message="城市长度必须介于 1 和 30 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=1, max=30, message="省长度必须介于 1 和 30 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=1, max=2, message="发货运输级别长度必须介于 1 和 2 之间")
	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	@Length(min=1, max=2, message="城市级别长度必须介于 1 和 2 之间")
	public String getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(String cityLevel) {
		this.cityLevel = cityLevel;
	}
	
	@NotNull(message="跨省专送运输平均成本不能为空")
	public Double getTransProvinceCost() {
		return transProvinceCost;
	}

	public void setTransProvinceCost(Double transProvinceCost) {
		this.transProvinceCost = transProvinceCost;
	}
	
	@NotNull(message="本市专送运输平均成本不能为空")
	public Double getLocalCityCost() {
		return localCityCost;
	}

	public void setLocalCityCost(Double localCityCost) {
		this.localCityCost = localCityCost;
	}
	
	@NotNull(message="第三方物流发货运输平均成本不能为空")
	public Double getThirdDeliveryCost() {
		return thirdDeliveryCost;
	}

	public void setThirdDeliveryCost(Double thirdDeliveryCost) {
		this.thirdDeliveryCost = thirdDeliveryCost;
	}
	
	@NotNull(message="第三方物流回收取件运输平均成本不能为空")
	public Double getThirdPickCost() {
		return thirdPickCost;
	}

	public void setThirdPickCost(Double thirdPickCost) {
		this.thirdPickCost = thirdPickCost;
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