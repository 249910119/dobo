/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.entity.detail;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * top单次、先行支持服务报价清单Entity
 * @author admin
 * @version 2017-06-08
 */
public class CopCaseDetail extends DataEntity<CopCaseDetail> {
	
	private static final long serialVersionUID = 1L;
	private String priceNum;		// price_num
	private String onceNum;			// once_num
	private String onceCode;		// once_code
	private String caseId;			// case_id
	private String caseCode;		// case_code
	private String priceType;		// 1人员，2备件
	private String serviceType;		// 1故障、3巡检、5非故障技术支持、6专业化服务
	private String dcPrjId;			//项目编号
	private String dcPrjName;		//项目名称
	private String serviceContent; 	// 服务内容
	private String partsInfo;		// 备件要求
	private String factoryId;		// 故障设备厂商id
	private String factoryName;		// 故障设备厂商名称
	private String modelId;			// 故障设备型号id
	private String modelName;		// 故障设备型号
	private String snId;			// 故障设备序列号
	private String manRoleId;		// 3一线、4二线、2-PM
	private String manEngineerLevel;		// 1初级、2中级、3高级、4专家
	private String manResourceType;		// 0内部，1外部
	private Double manWorkload = 0.0;		// man_workload
	private Double manPrice = 0.0;		// man_外援费用
	private Double manTravelPrice = 0.0;		// man_差旅费用
	private String partPn;		// part_pn
	private Double partAmount = 0.0;		// part_amount
	private Double partPrice = 0.0;		// part_price
	private Double partDeliveryPrice = 0.0;		// part_delivery_price
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private String isSpecialAudit;	// 是否特批。0否1是
	private String specialAuditRemark;		// 特批情况说明
	private String woProjectCode;    // 核销对应项目编号
	private String woProjectName;    // 核销对应项目名称
	private String woReason;         // 核销情况说明
	private Date woApprovalDate;      // 核销审批时间
	private String woApprovalBy;      // 核销审批人
	private String woApprovalRemark;   // 核销审批意见
	private String invokeType;		// 报价类型：正常报价=normal 取消=cancel 核销=write_off 转先行支持=prepay
	
	private String payType; 
	private String caseConfirmId;
	
	public CopCaseDetail() {
		super();
	}

	public CopCaseDetail(String id){
		super(id);
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
	
	@Length(min=1, max=128, message="once_code长度必须介于 1 和128 之间")
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
	
	@Length(min=1, max=1, message="price_type长度必须介于 1 和 1 之间")
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@Length(min=1, max=1, message="service_type长度必须介于 1 和 1 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=0, max=256, message="项目编号长度必须介于 0 和 256 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}

	@Length(min=0, max=256, message="项目名称长度必须介于0 和 256 之间")
	public String getDcPrjName() {
		return dcPrjName;
	}

	public void setDcPrjName(String dcPrjName) {
		this.dcPrjName = dcPrjName;
	}

	@Length(min=0, max=4000, message="服务内容长度必须介于 0 和 4000 之间")
	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	@Length(min=0, max=4000, message="备件要求长度必须介于 0 和 4000 之间")
	public String getPartsInfo() {
		return partsInfo;
	}

	public void setPartsInfo(String partsInfo) {
		this.partsInfo = partsInfo;
	}

	@Length(min=0, max=20, message="故障设备厂商id长度必须介于 0 和 20 之间")
	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	@Length(min=0, max=256, message="故障设备厂商名称长度必须介于 0 和 256 之间")
	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	@Length(min=0, max=20, message="故障设备型号id长度必须介于 0 和 20 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Length(min=0, max=256, message="故障设备型号长度必须介于 0 和 256之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Length(min=0, max=256, message="故障设备序列号长度必须介于 0 和 256 之间")
	public String getSnId() {
		return snId;
	}

	public void setSnId(String snId) {
		this.snId = snId;
	}
	
	@Length(min=0, max=1, message="3一线、4二线、2-PM长度必须介于 0 和 1 之间")
	public String getManRoleId() {
		return manRoleId;
	}

	public void setManRoleId(String manRoleId) {
		this.manRoleId = manRoleId;
	}
	
	@Length(min=0, max=1, message="1初级、2中级、3高级、4专家长度必须介于 0 和 1 之间")
	public String getManEngineerLevel() {
		return manEngineerLevel;
	}

	public void setManEngineerLevel(String manEngineerLevel) {
		this.manEngineerLevel = manEngineerLevel;
	}
	
	@Length(min=0, max=1, message="0内部，1外部长度必须介于 0 和 1 之间")
	public String getManResourceType() {
		return manResourceType;
	}

	public void setManResourceType(String manResourceType) {
		this.manResourceType = manResourceType;
	}
	
	public Double getManWorkload() {
		return manWorkload;
	}

	public void setManWorkload(Double manWorkload) {
		this.manWorkload = manWorkload;
	}
	
	public Double getManPrice() {
		return manPrice;
	}

	public void setManPrice(Double manPrice) {
		this.manPrice = manPrice;
	}
	
	public Double getManTravelPrice() {
		return manTravelPrice;
	}

	public void setManTravelPrice(Double manTravelPrice) {
		this.manTravelPrice = manTravelPrice;
	}
	
	@Length(min=0, max=256, message="part_pn长度必须介于 0 和 256 之间")
	public String getPartPn() {
		return partPn;
	}

	public void setPartPn(String partPn) {
		this.partPn = partPn;
	}
	
	public Double getPartAmount() {
		return partAmount;
	}

	public void setPartAmount(Double partAmount) {
		this.partAmount = partAmount;
	}
	
	public Double getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(Double partPrice) {
		this.partPrice = partPrice;
	}
	
	public Double getPartDeliveryPrice() {
		return partDeliveryPrice;
	}

	public void setPartDeliveryPrice(Double partDeliveryPrice) {
		this.partDeliveryPrice = partDeliveryPrice;
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
	
	public String getIsSpecialAudit() {
		return isSpecialAudit;
	}

	public void setIsSpecialAudit(String isSpecialAudit) {
		this.isSpecialAudit = isSpecialAudit;
	}

	public String getSpecialAuditRemark() {
		return specialAuditRemark;
	}

	public void setSpecialAuditRemark(String specialAuditRemark) {
		this.specialAuditRemark = specialAuditRemark;
	}

	public String getWoProjectCode() {
		return woProjectCode;
	}

	public void setWoProjectCode(String woProjectCode) {
		this.woProjectCode = woProjectCode;
	}

	public String getWoProjectName() {
		return woProjectName;
	}

	public void setWoProjectName(String woProjectName) {
		this.woProjectName = woProjectName;
	}

	public String getWoReason() {
		return woReason;
	}

	public void setWoReason(String woReason) {
		this.woReason = woReason;
	}

	public Date getWoApprovalDate() {
		return woApprovalDate;
	}

	public void setWoApprovalDate(Date woApprovalDate) {
		this.woApprovalDate = woApprovalDate;
	}

	public String getWoApprovalBy() {
		return woApprovalBy;
	}

	public void setWoApprovalBy(String woApprovalBy) {
		this.woApprovalBy = woApprovalBy;
	}

	public String getWoApprovalRemark() {
		return woApprovalRemark;
	}

	public void setWoApprovalRemark(String woApprovalRemark) {
		this.woApprovalRemark = woApprovalRemark;
	}

	public String getInvokeType() {
		return invokeType;
	}

	public void setInvokeType(String invokeType) {
		this.invokeType = invokeType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCaseConfirmId() {
		return caseConfirmId;
	}

	public void setCaseConfirmId(String caseConfirmId) {
		this.caseConfirmId = caseConfirmId;
	}
	
}