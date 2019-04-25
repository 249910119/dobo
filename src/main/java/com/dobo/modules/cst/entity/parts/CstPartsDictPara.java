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
 * 备件参数字典定义Entity
 * @author admin
 * @version 2016-11-15
 */
public class CstPartsDictPara extends DataEntity<CstPartsDictPara> {
	
	private static final long serialVersionUID = 1L;
	private String paraType;		// 参数类型
	private String paraId;		// 参数标识
	private String paraName;		// 参数名称
	private Double paraValue;		// 参数值
	private String referCostItem;		// 对应成本项
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsDictPara() {
		super();
	}

	public CstPartsDictPara(String id){
		super(id);
	}

	@Length(min=1, max=2, message="参数类型长度必须介于 1 和 2 之间")
	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}
	
	@Length(min=1, max=128, message="参数标识长度必须介于 1 和 128 之间")
	public String getParaId() {
		return paraId;
	}

	public void setParaId(String paraId) {
		this.paraId = paraId;
	}
	
	@Length(min=1, max=128, message="参数名称长度必须介于 1 和 128 之间")
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	
	@NotNull(message="参数值不能为空")
	public Double getParaValue() {
		return paraValue;
	}

	public void setParaValue(Double paraValue) {
		this.paraValue = paraValue;
	}
	
	@Length(min=0, max=255, message="对应成本项长度必须介于 0 和 255 之间")
	public String getReferCostItem() {
		return referCostItem;
	}

	public void setReferCostItem(String referCostItem) {
		this.referCostItem = referCostItem;
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