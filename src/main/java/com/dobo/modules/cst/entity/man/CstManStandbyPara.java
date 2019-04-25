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
 * 非工作时间比重定义表Entity
 * @author admin
 * @version 2016-11-08
 */
public class CstManStandbyPara extends DataEntity<CstManStandbyPara> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// 服务产品编号
	private String prodName2;		// 服务产品名称
	private String deliveryRole;		// 生产角色
	private Double standbyScale;		// 非工作时间比重
	private String status;		// 状态
	private Date statusChangeDate;		// 状态更新时间
	private String preStatus;		// 新前状态
	private String saveFlag;		// 保存标记
	
	public CstManStandbyPara() {
		super();
	}

	public CstManStandbyPara(String id){
		super(id);
	}

	@Length(min=1, max=60, message="服务产品编号长度必须介于 1 和 60 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=1, max=60, message="服务产品名称长度必须介于 1 和 60 之间")
	public String getProdName2() {
		return prodName2;
	}

	public void setProdName2(String prodName2) {
		this.prodName2 = prodName2;
	}
	
	@Length(min=1, max=30, message="生产角色长度必须介于 1 和 30 之间")
	public String getDeliveryRole() {
		return deliveryRole;
	}

	public void setDeliveryRole(String deliveryRole) {
		this.deliveryRole = deliveryRole;
	}
	
	@NotNull(message="非工作时间比重不能为空")
	public Double getStandbyScale() {
		return standbyScale;
	}

	public void setStandbyScale(Double standbyScale) {
		this.standbyScale = standbyScale;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	
	@Length(min=0, max=2, message="新前状态长度必须介于 0 和 2 之间")
	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	
	@Length(min=0, max=1, message="保存标记长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}