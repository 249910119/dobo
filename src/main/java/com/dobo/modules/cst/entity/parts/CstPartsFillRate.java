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
 * 备件满足率定义Entity
 * @author admin
 * @version 2016-11-15
 */
public class CstPartsFillRate extends DataEntity<CstPartsFillRate> {
	
	private static final long serialVersionUID = 1L;
	private String province;		// 省
	private String storeLevel;		// 省
	private Double localFillRate;		// 本地备件满足率
	private Double fillRate;		// 备件满足率
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;
	
	public CstPartsFillRate() {
		super();
	}

	public CstPartsFillRate(String id){
		super(id);
	}

	@Length(min=1, max=30, message="省长度必须介于 1 和 30 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=1, max=2, message="省长度必须介于 1 和 2 之间")
	public String getStoreLevel() {
		return storeLevel;
	}

	public void setStoreLevel(String storeLevel) {
		this.storeLevel = storeLevel;
	}
	
	@NotNull(message="本地备件满足率不能为空")
	public Double getLocalFillRate() {
		return localFillRate;
	}

	public void setLocalFillRate(Double localFillRate) {
		this.localFillRate = localFillRate;
	}
	
	@NotNull(message="备件满足率不能为空")
	public Double getFillRate() {
		return fillRate;
	}

	public void setFillRate(Double fillRate) {
		this.fillRate = fillRate;
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
	@Length(min=0, max=2, message="更新前状态长度必须介于 0 和 2 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}