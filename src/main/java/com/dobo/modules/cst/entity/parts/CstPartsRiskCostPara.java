/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件风险储备金Entity
 * @author admin
 * @version 2019-01-15
 */
public class CstPartsRiskCostPara extends DataEntity<CstPartsRiskCostPara> {
	
	private static final long serialVersionUID = 1L;
	private String modelgroupId;		// 型号组标识
	private String modelgroupDesc;		// 资源描述（格式：厂商|型号组|技术方向）
	private Double historicalAmount = 0.0;		// 历史在保设备数量
	private Double riskCostScale = 0.0;		// 风险成本系数
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstPartsRiskCostPara() {
		super();
	}

	public CstPartsRiskCostPara(String id){
		super(id);
	}

	@Length(min=0, max=30, message="型号组标识长度必须介于 0 和 30 之间")
	public String getModelgroupId() {
		return modelgroupId;
	}

	public void setModelgroupId(String modelgroupId) {
		this.modelgroupId = modelgroupId;
	}
	
	@Length(min=0, max=255, message="资源描述（格式：厂商|型号组|技术方向）长度必须介于 0 和 255 之间")
	public String getModelgroupDesc() {
		return modelgroupDesc;
	}

	public void setModelgroupDesc(String modelgroupDesc) {
		this.modelgroupDesc = modelgroupDesc;
	}
	
	public Double getHistoricalAmount() {
		return historicalAmount;
	}

	public void setHistoricalAmount(Double historicalAmount) {
		this.historicalAmount = historicalAmount;
	}
	
	public Double getRiskCostScale() {
		return riskCostScale;
	}

	public void setRiskCostScale(Double riskCostScale) {
		this.riskCostScale = riskCostScale;
	}
	
	@Length(min=0, max=2, message="状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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
	
	@Length(min=0, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}