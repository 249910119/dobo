/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.model;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 成本参数定义表：定义成本模型公式中用到的简单和复杂参数Entity
 * @author admin
 * @version 2016-11-11
 */
public class CstModelParaDef extends DataEntity<CstModelParaDef> {
	
	private static final long serialVersionUID = 1L;
	private String moduleId;		// 成本模型标识
	private String paraId;		// 参数标识
	private String paraName;		// 参数名称
	private String paraType;		// 参数类型
	private String paraUnit;		// 参数单位
	private String paraValue;		// 参数计算公式
	private String paraFormula;		// 参数计算公式
	private String paraCalcClass;		// 参数计算类：对应的java类全名（含包路径）
	private Long displayOrder;		// 显示顺序
	private String status;		// 状态
	private Date statusChangeDate;		// 状态更新时间
	private String preStatus;		// 更新前状态
	private String saveFlag;		// 保存标记
	private CstModelModuleInfo cstModelModuleInfo;		//成本模型信息
	
	public CstModelModuleInfo getCstModelModuleInfo() {
		return cstModelModuleInfo;
	}

	public void setCstModelModuleInfo(CstModelModuleInfo cstModelModuleInfo) {
		this.cstModelModuleInfo = cstModelModuleInfo;
	}

	public CstModelParaDef() {
		super();
	}

	public CstModelParaDef(String id){
		super(id);
	}

	@Length(min=1, max=64, message="成本模型标识长度必须介于 1 和 64 之间")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Length(min=1, max=64, message="参数标识长度必须介于 1 和 64 之间")
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
	
	@Length(min=1, max=2, message="参数类型长度必须介于 1 和 2 之间")
	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}
	
	@Length(min=0, max=30, message="参数单位长度必须介于 0 和 30 之间")
	public String getParaUnit() {
		return paraUnit;
	}

	public void setParaUnit(String paraUnit) {
		this.paraUnit = paraUnit;
	}
	
	@Length(min=0, max=512, message="参数计算公式长度必须介于 0 和 30 之间")
	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	
	@Length(min=0, max=255, message="参数计算公式长度必须介于 0 和 255 之间")
	public String getParaFormula() {
		return paraFormula;
	}

	public void setParaFormula(String paraFormula) {
		this.paraFormula = paraFormula;
	}
	
	@Length(min=0, max=128, message="参数计算类：对应的java类全名（含包路径）长度必须介于 0 和 128 之间")
	public String getParaCalcClass() {
		return paraCalcClass;
	}

	public void setParaCalcClass(String paraCalcClass) {
		this.paraCalcClass = paraCalcClass;
	}
	
	@NotNull(message="显示顺序不能为空")
	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
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
	
	@Length(min=0, max=2, message="更新前状态长度必须介于 0 和 2 之间")
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