/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.man;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 项目管理配比及饱和度表Entity
 * @author admin
 * @version 2016-12-08
 */
public class CstManManageDegreePara extends DataEntity<CstManManageDegreePara> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// 服务产品编码
	private String prodName;		// 服务产品
	private String serviceLevel;		// 服务级别标识
	private String serviceLevelName;		// 服务级别
	private Double pmLevel3Scale;		// PM3级配比
	private Double pmLevel4Scale;		// PM4级配比
	private Double pmLevel5Scale;		// PM5级配比
	private Double pmDegree;		// PM饱和度
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstManManageDegreePara() {
		super();
	}

	public CstManManageDegreePara(String id){
		super(id);
	}

	@Length(min=1, max=30, message="服务产品编码长度必须介于 1 和 30 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=0, max=64, message="服务产品长度必须介于 0 和 64 之间")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
	@Length(min=0, max=30, message="服务级别标识长度必须介于 0 和 30 之间")
	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	
	@Length(min=1, max=64, message="服务级别长度必须介于 1 和 64 之间")
	public String getServiceLevelName() {
		return serviceLevelName;
	}

	public void setServiceLevelName(String serviceLevelName) {
		this.serviceLevelName = serviceLevelName;
	}
	
	public Double getPmLevel3Scale() {
		return pmLevel3Scale;
	}

	public void setPmLevel3Scale(Double pmLevel3Scale) {
		this.pmLevel3Scale = pmLevel3Scale;
	}
	
	public Double getPmLevel4Scale() {
		return pmLevel4Scale;
	}

	public void setPmLevel4Scale(Double pmLevel4Scale) {
		this.pmLevel4Scale = pmLevel4Scale;
	}
	
	public Double getPmLevel5Scale() {
		return pmLevel5Scale;
	}

	public void setPmLevel5Scale(Double pmLevel5Scale) {
		this.pmLevel5Scale = pmLevel5Scale;
	}
	
	public Double getPmDegree() {
		return pmDegree;
	}

	public void setPmDegree(Double pmDegree) {
		this.pmDegree = pmDegree;
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