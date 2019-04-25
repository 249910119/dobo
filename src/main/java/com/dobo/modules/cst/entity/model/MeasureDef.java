package com.dobo.modules.cst.entity.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.dobo.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

/**
 * 指标定义表
 * @author wudla
 *
 */
public class MeasureDef extends DataEntity<MeasureDef>{

	private static final long serialVersionUID = 1L;
	private String measureTypeId;		//指标类型编码
	private String measureId;		//指标代码
	private String measureName;		//指标名称
	private String measureDesc;		//指标描述
	private String measureType;		//指标类型 		A0:简单指标/A1:复合指标
	private String valueType;		//指标值类型
	private String measureUnit;		//指标单位
	private String getType;		//取值类型		A0:直接提取/A1:系统计算
	private String measureFormula;		//指标公式
	private String calcClassId;		//对应计算类
	private User modifyStaff;		//维护人
	private Date modifyDate;		//维护时间
	private String remark;		//备注
	private String statCycle;		//指标统计周期		A0:季度/A1:半年/A2:年度/A3:不定期
	
	private List<CstModelCalculateFormula> calculateFormulas = Lists.newArrayList();
	
	public MeasureDef() {
		super();
	}
	
	public MeasureDef(String measureTypeId) {
		super(measureTypeId);
	}
	@Length(min=1, max=6, message="成本模型标识长度必须介于 1 和 6 之间")
	public String getMeasureTypeId() {
		return measureTypeId;
	}
	public void setMeasureTypeId(String measureTypeId) {
		this.measureTypeId = measureTypeId;
	}
	@Length(min=1, max=9, message="成本模型标识长度必须介于 1 和 9 之间")
	public String getMeasureId() {
		return measureId;
	}
	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}
	@Length(min=1, max=60, message="成本模型标识长度必须介于 1 和 60 之间")
	public String getMeasureName() {
		return measureName;
	}
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	@Length(min=1, max=255, message="成本模型标识长度必须介于 1 和 255 之间")
	public String getMeasureDesc() {
		return measureDesc;
	}
	public void setMeasureDesc(String measureDesc) {
		this.measureDesc = measureDesc;
	}
	@Length(min=1, max=2, message="成本模型标识长度必须介于 1 和 2 之间")
	public String getMeasureType() {
		return measureType;
	}
	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	@Length(min=1, max=2, message="成本模型标识长度必须介于 1 和 2 之间")
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	@Length(min=1, max=10, message="成本模型标识长度必须介于 1 和 10 之间")
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	@Length(min=1, max=2, message="成本模型标识长度必须介于 1 和 2 之间")
	public String getGetType() {
		return getType;
	}
	public void setGetType(String getType) {
		this.getType = getType;
	}
	@Length(min=1, max=200, message="成本模型标识长度必须介于 1 和 200 之间")
	public String getMeasureFormula() {
		return measureFormula;
	}
	public void setMeasureFormula(String measureFormula) {
		this.measureFormula = measureFormula;
	}
	@Length(min=1, max=30, message="成本模型标识长度必须介于 1 和 30 之间")
	public String getCalcClassId() {
		return calcClassId;
	}
	public void setCalcClassId(String calcClassId) {
		this.calcClassId = calcClassId;
	}
	@Length(min=1, max=15, message="成本模型标识长度必须介于 1 和 15 之间")
	public User getModifyStaff() {
		return modifyStaff;
	}
	public void setModifyStaff(User modifyStaff) {
		this.modifyStaff = modifyStaff;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Length(min=1, max=128, message="成本模型标识长度必须介于 1 和 128 之间")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Length(min=1, max=2, message="成本模型标识长度必须介于 1 和 2 之间")
	public String getStatCycle() {
		return statCycle;
	}
	public void setStatCycle(String statCycle) {
		this.statCycle = statCycle;
	}
	public List<CstModelCalculateFormula> getCalculateFormulas() {
		return calculateFormulas;
	}

	public void setCalculateFormulas(List<CstModelCalculateFormula> calculateFormulas) {
		this.calculateFormulas = calculateFormulas;
	}

}
