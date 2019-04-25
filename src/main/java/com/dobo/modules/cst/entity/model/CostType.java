package com.dobo.modules.cst.entity.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.dobo.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

/**
 * 成本类型定义
 * @author wudla
 *
 */
public class CostType extends DataEntity<CostType>{

	private static final long serialVersionUID = 1L;
	private String costType;		//成本类型代码
	private String typeName;		//成本类型名称
	private User modifyStaff;		//维护人
	private Date modifyDate;		//维护时间
	private String remark;		//备注
	private String costField;		//成本域
	private List<CstModelCalculateFormula> calculateFormulas = Lists.newArrayList();
	
	public List<CstModelCalculateFormula> getCalculateFormulas() {
		return calculateFormulas;
	}

	public void setCalculateFormulas(List<CstModelCalculateFormula> calculateFormulas) {
		this.calculateFormulas = calculateFormulas;
	}

	public CostType() {
		super();
	}
	
	public CostType(String costType) {
		super(costType);
	}
	
	@Length(min=1, max=6, message="成本模型标识长度必须介于 1 和 6 之间")
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	@Length(min=1, max=30, message="成本模型标识长度必须介于 1 和 30 之间")
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Length(min=1, max=30, message="成本模型标识长度必须介于 1 和 30 之间")
	public String getCostField() {
		return costField;
	}
	public void setCostField(String costField) {
		this.costField = costField;
	}
	
	@Length(min=1, max=15, message="成本模型标识长度必须介于 1 和 15之间")
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
	
	
}
