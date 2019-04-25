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
 * 地域系数定义表Entity
 * @author admin
 * @version 2016-11-07
 */
public class CstManCityPara extends DataEntity<CstManCityPara> {
	
	private static final long serialVersionUID = 1L;
	private String cityName;		// 城市名称
	private String cityId;		// 城市标识(t_bim_regioninfo定义)
	private String capitalCityId;		// 省会城市标识:t_bim_regioninfo定义
	private String capitalCityName;		// 省会城市名称
	private Double cityHour;		// 市内路途工时
	private Double travelHour;		// 差旅工时
	private Double travelFee;		// 差旅费
	private Double standAccmFee;		// 标准住宿费
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String province;		// 省份
	private Double faultAreaScale;	// 故障率地域差异系数 2019-03-20 添加
	
	public CstManCityPara() {
		super();
	}

	public CstManCityPara(String id){
		super(id);
	}

	@Length(min=1, max=30, message="城市名称长度必须介于 1 和 30 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Length(min=1, max=30, message="城市标识:长度必须介于 1 和 30 之间")
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@Length(min=1, max=30, message="省会城市标识:长度必须介于 1 和 30 之间")
	public String getCapitalCityId() {
		return capitalCityId;
	}

	public void setCapitalCityId(String capitalCityId) {
		this.capitalCityId = capitalCityId;
	}
	
	@Length(min=1, max=30, message="省会城市名称长度必须介于 1 和 30 之间")
	public String getCapitalCityName() {
		return capitalCityName;
	}

	public void setCapitalCityName(String capitalCityName) {
		this.capitalCityName = capitalCityName;
	}
	
	@NotNull(message="市内路途工时不能为空")
	public Double getCityHour() {
		return cityHour;
	}

	public void setCityHour(Double cityHour) {
		this.cityHour = cityHour;
	}
	
	@NotNull(message="差旅工时不能为空")
	public Double getTravelHour() {
		return travelHour;
	}

	public void setTravelHour(Double travelHour) {
		this.travelHour = travelHour;
	}
	
	@NotNull(message="差旅费不能为空")
	public Double getTravelFee() {
		return travelFee;
	}

	public void setTravelFee(Double travelFee) {
		this.travelFee = travelFee;
	}

	@NotNull(message="标准住宿费不能为空")
	public Double getStandAccmFee() {
		return standAccmFee;
	}

	public void setStandAccmFee(Double standAccmFee) {
		this.standAccmFee = standAccmFee;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Double getFaultAreaScale() {
		return faultAreaScale;
	}

	public void setFaultAreaScale(Double faultAreaScale) {
		this.faultAreaScale = faultAreaScale;
	}
	
}