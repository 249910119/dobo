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
 * 备件项目储备成本系数定义Entity
 * @author admin
 * @version 2016-11-15
 */
public class CstPartsPrjStorePara extends DataEntity<CstPartsPrjStorePara> {
	
	private static final long serialVersionUID = 1L;
	private String province;		// 省
	private Double costScale;		// 成本系数
	private String costScaleLevel;		// 成本系数级别
	private String costDesc;		// 说明
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsPrjStorePara() {
		super();
	}

	public CstPartsPrjStorePara(String id){
		super(id);
	}

	@Length(min=1, max=30, message="省长度必须介于 1 和 30 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@NotNull(message="成本系数不能为空")
	public Double getCostScale() {
		return costScale;
	}

	public void setCostScale(Double costScale) {
		this.costScale = costScale;
	}
	
	@Length(min=1, max=2, message="成本系数级别长度必须介于 1 和 2 之间")
	public String getCostScaleLevel() {
		return costScaleLevel;
	}

	public void setCostScaleLevel(String costScaleLevel) {
		this.costScaleLevel = costScaleLevel;
	}
	
	@Length(min=0, max=128, message="说明长度必须介于 0 和 128 之间")
	public String getCostDesc() {
		return costDesc;
	}

	public void setCostDesc(String costDesc) {
		this.costDesc = costDesc;
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