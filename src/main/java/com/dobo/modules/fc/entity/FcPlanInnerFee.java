/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 项目计划内财务费用Entity
 * @author admin
 * @version 2016-11-06
 */
public class FcPlanInnerFee extends DataEntity<FcPlanInnerFee> {
	
	private static final long serialVersionUID = 1L;
	private String fcProjectInfoId;		// 项目信息
	private FcProjectInfo fcProjectInfo;		// 项目信息
	private Double financialCost;		// 财务费用
	private Date calculateTime;		// 计算时间
	private Double loanRate;		// 贷息利率
	private Double depositRate;		// 存息利率
	private String useFlag;		// 使用标记（0：实时计算；1：数据库获取）默认0，当最新有效的为1时则直接返回客户端，无需计算
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private String calcParaJson;		// 计算参数
	
	public FcPlanInnerFee() {
		super();
	}

	public FcPlanInnerFee(String id){
		super(id);
	}

	public FcPlanInnerFee(String fcProjectInfoId, String status) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.status = status;
	}

	public FcPlanInnerFee(String fcProjectInfoId, String status, String useFlag) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.status = status;
		this.useFlag = useFlag;
	}

	public FcPlanInnerFee(String fcProjectInfoId, String status, String useFlag, Double financialCost) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.status = status;
		this.useFlag = useFlag;
		this.financialCost = financialCost;
	}

	public FcPlanInnerFee(String fcProjectInfoId, FcProjectInfo fcProjectInfo, Double financialCost, Date calculateTime,
			Double loanRate, Double depositRate) {
		super();
		this.fcProjectInfoId = fcProjectInfoId;
		this.fcProjectInfo = fcProjectInfo;
		this.financialCost = financialCost;
		this.calculateTime = calculateTime;
		this.loanRate = loanRate;
		this.depositRate = depositRate;
	}

	@Length(min=1, max=64, message="项目信息长度必须介于 1 和 64 之间")
	public String getFcProjectInfoId() {
		return fcProjectInfoId;
	}

	public void setFcProjectInfoId(String fcProjectInfoId) {
		this.fcProjectInfoId = fcProjectInfoId;
	}
	
	@NotNull(message="财务费用不能为空")
	public Double getFinancialCost() {
		return financialCost;
	}

	public void setFinancialCost(Double financialCost) {
		this.financialCost = financialCost;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="计算时间不能为空")
	public Date getCalculateTime() {
		return calculateTime;
	}

	public void setCalculateTime(Date calculateTime) {
		this.calculateTime = calculateTime;
	}
	
	public Double getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}
	
	public Double getDepositRate() {
		return depositRate;
	}

	public void setDepositRate(Double depositRate) {
		this.depositRate = depositRate;
	}
	
	@Length(min=1, max=1, message="使用标记长度必须介于 1 和 1 之间")
	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
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

	public FcProjectInfo getFcProjectInfo() {
		return fcProjectInfo;
	}

	public void setFcProjectInfo(FcProjectInfo fcProjectInfo) {
		this.fcProjectInfo = fcProjectInfo;
	}

	@Length(min=1, max=4000, message="状态长度必须介于 1 和 4000之间")
	public String getCalcParaJson() {
		return calcParaJson;
	}

	public void setCalcParaJson(String calcParaJson) {
		this.calcParaJson = calcParaJson;
	}
	
}