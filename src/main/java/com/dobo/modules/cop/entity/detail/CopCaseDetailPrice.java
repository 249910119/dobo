/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.entity.detail;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;

/**
 * 单次、先行支持服务报价明细Entity
 * @author admin
 * @version 2017-06-08
 */
public class CopCaseDetailPrice extends DataEntity<CopCaseDetailPrice> {
	
	private static final long serialVersionUID = 1L;
	private String caseDetailId;		// 主键
	private String priceNum;		// price_num
	private String onceNum;		// once_num
	private String onceCode;		// once_code
	private String caseId;		// case_id
	private String caseCode;		// case_code
	private String priceType;		// 1人员，2备件
	private Double wbmCostPrepay;		// wbm_cost_prepay
	private Double wbmCostPrepayTravel;		// wbm_cost_prepay_travel
	private Double wbmCostFt;		// wbm_cost_ft
	private Double wbmCostFtTravel;		// wbm_cost_ft_travel
	private Double wbmCostFtToProject;		// wbm_cost_ft_to_project
	private Double wbmCostFtToProjectTravel;		// wbm_cost_ft_to_project_travel
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private Double auditCostPrepay;		// wbm_cost_prepay
	private Double auditCostPrepayTravel;		// wbm_cost_prepay_travel
	private Double auditCostFt;		// wbm_cost_ft
	private Double auditCostFtTravel;		// wbm_cost_ft_travel
	private Double auditCostFtToPrj;		// wbm_cost_ft_to_project
	private Double auditCostFtToPrjTravel;		// wbm_cost_ft_to_project_travel
	private String payType;		// 1：预付费:2：单次:3：单次入项目
	
	public CopCaseDetailPrice() {
		super();
	}

	public CopCaseDetailPrice(String id){
		super(id);
	}

	@Length(min=1, max=32, message="主键长度必须介于 1 和 32 之间")
	public String getCaseDetailId() {
		return caseDetailId;
	}

	public void setCaseDetailId(String caseDetailId) {
		this.caseDetailId = caseDetailId;
	}
	
	@Length(min=1, max=32, message="price_num长度必须介于 1 和 32 之间")
	public String getPriceNum() {
		return priceNum;
	}

	public void setPriceNum(String priceNum) {
		this.priceNum = priceNum;
	}
	
	@Length(min=1, max=32, message="once_num长度必须介于 1 和 32 之间")
	public String getOnceNum() {
		return onceNum;
	}

	public void setOnceNum(String onceNum) {
		this.onceNum = onceNum;
	}
	
	@Length(min=1, max=128, message="once_code长度必须介于 1 和 128 之间")
	public String getOnceCode() {
		return onceCode;
	}

	public void setOnceCode(String onceCode) {
		this.onceCode = onceCode;
	}
	
	@Length(min=1, max=32, message="case_id长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Length(min=1, max=256, message="case_code长度必须介于 1 和 256 之间")
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	@Length(min=1, max=1, message="1人员，2备件长度必须介于 1 和 1 之间")
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@NotNull(message="wbm_cost_prepay不能为空")
	public Double getWbmCostPrepay() {
		return wbmCostPrepay;
	}

	public void setWbmCostPrepay(Double wbmCostPrepay) {
		this.wbmCostPrepay = wbmCostPrepay;
	}
	
	@NotNull(message="wbm_cost_prepay_travel不能为空")
	public Double getWbmCostPrepayTravel() {
		return wbmCostPrepayTravel;
	}

	public void setWbmCostPrepayTravel(Double wbmCostPrepayTravel) {
		this.wbmCostPrepayTravel = wbmCostPrepayTravel;
	}
	
	@NotNull(message="wbm_cost_ft不能为空")
	public Double getWbmCostFt() {
		return wbmCostFt;
	}

	public void setWbmCostFt(Double wbmCostFt) {
		this.wbmCostFt = wbmCostFt;
	}
	
	@NotNull(message="wbm_cost_ft_travel不能为空")
	public Double getWbmCostFtTravel() {
		return wbmCostFtTravel;
	}

	public void setWbmCostFtTravel(Double wbmCostFtTravel) {
		this.wbmCostFtTravel = wbmCostFtTravel;
	}
	
	@NotNull(message="wbm_cost_ft_to_project不能为空")
	public Double getWbmCostFtToProject() {
		return wbmCostFtToProject;
	}

	public void setWbmCostFtToProject(Double wbmCostFtToProject) {
		this.wbmCostFtToProject = wbmCostFtToProject;
	}
	
	@NotNull(message="wbm_cost_ft_to_project_travel不能为空")
	public Double getWbmCostFtToProjectTravel() {
		return wbmCostFtToProjectTravel;
	}

	public void setWbmCostFtToProjectTravel(Double wbmCostFtToProjectTravel) {
		this.wbmCostFtToProjectTravel = wbmCostFtToProjectTravel;
	}
	
	@Length(min=1, max=2, message="状态（A0:有效/A1:无效）长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2, message="更新前状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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
	
	@Length(min=1, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public Double getAuditCostPrepay() {
		return auditCostPrepay;
	}

	public void setAuditCostPrepay(Double auditCostPrepay) {
		this.auditCostPrepay = auditCostPrepay;
	}

	public Double getAuditCostPrepayTravel() {
		return auditCostPrepayTravel;
	}

	public void setAuditCostPrepayTravel(Double auditCostPrepayTravel) {
		this.auditCostPrepayTravel = auditCostPrepayTravel;
	}

	public Double getAuditCostFt() {
		return auditCostFt;
	}

	public void setAuditCostFt(Double auditCostFt) {
		this.auditCostFt = auditCostFt;
	}

	public Double getAuditCostFtTravel() {
		return auditCostFtTravel;
	}

	public void setAuditCostFtTravel(Double auditCostFtTravel) {
		this.auditCostFtTravel = auditCostFtTravel;
	}

	public Double getAuditCostFtToPrj() {
		return auditCostFtToPrj;
	}

	public void setAuditCostFtToPrj(Double auditCostFtToPrj) {
		this.auditCostFtToPrj = auditCostFtToPrj;
	}

	public Double getAuditCostFtToPrjTravel() {
		return auditCostFtToPrjTravel;
	}

	public void setAuditCostFtToPrjTravel(Double auditCostFtToPrjTravel) {
		this.auditCostFtToPrjTravel = auditCostFtToPrjTravel;
	}
	
	@Length(min=1, max=1, message="1：预付费:2：单次:3：单次入项目长度必须介于 1 和 1 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}