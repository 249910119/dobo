/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.check;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 巡检-首次巡检系数表Entity
 * @author admin
 * @version 2016-11-21
 */
public class CstCheckFirstcheckPara extends DataEntity<CstCheckFirstcheckPara> {
	
	private static final long serialVersionUID = 1L;
	private String slaName;		// 巡检服务级别(深度巡检，远程巡检，现场巡检)
	private Double firstCheckScale;		// 首次巡检系数
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstCheckFirstcheckPara() {
		super();
	}

	public CstCheckFirstcheckPara(String id){
		super(id);
	}

	@Length(min=1, max=30, message="巡检服务级别(深度巡检，远程巡检，现场巡检)长度必须介于 1 和 30 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@NotNull(message="首次巡检系数不能为空")
	public Double getFirstCheckScale() {
		return firstCheckScale;
	}

	public void setFirstCheckScale(Double firstCheckScale) {
		this.firstCheckScale = firstCheckScale;
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