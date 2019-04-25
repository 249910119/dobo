/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 成本计算公式定义表： 1.根据创建时间、状态、更新时间和更新前状态作为时间戳判断条件; 2.定义到二级成本分类，比如：一线(工时\人工\费用\激励)； 3.指标类型要与订单成本明细一一对应；Entity
 * @author admin
 * @version 2016-11-13
 */
public class CstModelCalculateFormula extends DataEntity<CstModelCalculateFormula> {
	
	private static final long serialVersionUID = 1L;
	private String moduleId;		// 成本模型标识
	private String isModelPara;		// 是否成本参数
	private String costType;		// 成本类型代码：与订单成本明细一一对应
	private String returnType;		// 返回类型
	private String measureId;		// 指标代码：与订单成本明细一一对应
	private String formulaName;		// 计算公式名称
	private String formulaDesc;		// 计算公式描述
	private String formula;		// 计算公式
	private Long displayOrder;		// 显示顺序
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	private CstModelModuleInfo cstModelModuleInfo;
	private MeasureDef measureDef;
	private CostType ctType;
	
	public CstModelCalculateFormula() {
		super();
	}

	public CstModelCalculateFormula(String id){
		super(id);
	}

	@Length(min=1, max=64, message="成本模型标识长度必须介于 1 和 64 之间")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Length(min=1, max=2, message="是否成本参数长度必须介于 1 和 2 之间")
	public String getIsModelPara() {
		return isModelPara;
	}

	public void setIsModelPara(String isModelPara) {
		this.isModelPara = isModelPara;
	}
	
	@Length(min=1, max=6, message="成本类型代码：与订单成本明细一一对应长度必须介于 1 和 6 之间")
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	@Length(min=1, max=2, message="返回类型长度必须介于 1 和 2 之间")
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	@Length(min=1, max=9, message="指标代码：与订单成本明细一一对应长度必须介于 1 和 9 之间")
	public String getMeasureId() {
		return measureId;
	}

	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}
	
	@Length(min=1, max=128, message="计算公式名称长度必须介于 1 和 128 之间")
	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}
	
	@Length(min=0, max=255, message="计算公式描述长度必须介于 0 和 255 之间")
	public String getFormulaDesc() {
		return formulaDesc;
	}

	public void setFormulaDesc(String formulaDesc) {
		this.formulaDesc = formulaDesc;
	}
	
	@Length(min=0, max=512, message="计算公式长度必须介于 0 和 512 之间")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
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

	public CstModelModuleInfo getCstModelModuleInfo() {
		return cstModelModuleInfo;
	}

	public void setCstModelModuleInfo(CstModelModuleInfo cstModelModuleInfo) {
		this.cstModelModuleInfo = cstModelModuleInfo;
	}
	
	public CostType getCtType() {
		return ctType;
	}

	public void setCtType(CostType ctType) {
		this.ctType = ctType;
	}

	public MeasureDef getMeasureDef() {
		return measureDef;
	}

	public void setMeasureDef(MeasureDef measureDef) {
		this.measureDef = measureDef;
	}
}