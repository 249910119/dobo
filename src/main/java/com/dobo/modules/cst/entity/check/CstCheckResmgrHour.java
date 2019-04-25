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
 * 巡检-资源岗巡检安排工时表Entity
 * @author admin
 * @version 2016-11-22
 */
public class CstCheckResmgrHour extends DataEntity<CstCheckResmgrHour> {
	
	private static final long serialVersionUID = 1L;
	private Double pmoCheckHour;		// PMO单次巡检安排工时
	private Double resMgrCheckHour;		// 资源岗单次巡检安排工时
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstCheckResmgrHour() {
		super();
	}

	public CstCheckResmgrHour(String id){
		super(id);
	}

	@NotNull(message="PMO单次巡检安排工时不能为空")
	public Double getPmoCheckHour() {
		return pmoCheckHour;
	}

	public void setPmoCheckHour(Double pmoCheckHour) {
		this.pmoCheckHour = pmoCheckHour;
	}
	
	@NotNull(message="资源岗单次巡检安排工时不能为空")
	public Double getResMgrCheckHour() {
		return resMgrCheckHour;
	}

	public void setResMgrCheckHour(Double resMgrCheckHour) {
		this.resMgrCheckHour = resMgrCheckHour;
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