/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

/**
 * 成本模型信息表，定义成本一级分类（人工类、备件类）Entity
 * @author admin
 * @version 2016-11-09
 */
public class CstModelModuleInfo extends DataEntity<CstModelModuleInfo> {
	
	private static final long serialVersionUID = 1L;
	
	private String moduleType;		// 成本模型类型
	private String moduleName;		// 成本模型名称
	private String moduleDesc;		// 成本模型描述
	private String status;		// 状态
	private Integer versionNo;		// 版本号
	private Date versionDate;		// 版本时间
	
	private List<CstModelParaDef> cstModelParaDefs = Lists.newArrayList();		//成本参数定义
	private List<CstModelProdModuleRel> cstModelProdModuleRels = Lists.newArrayList();		//成本模型使用
	private List<CstModelCalculateFormula> calculateFormulas = Lists.newArrayList();
	
	public CstModelModuleInfo() {
		super();
	}

	public CstModelModuleInfo(String id){
		super(id);
	}

	@Length(min=1, max=2, message="成本模型类型长度必须介于 1 和 2 之间")
	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	
	@Length(min=1, max=128, message="成本模型名称长度必须介于 1 和 128 之间")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@Length(min=0, max=255, message="成本模型描述长度必须介于 0 和 255 之间")
	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotNull(message="版本号不能为空")
	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="版本时间不能为空")
	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public List<CstModelParaDef> getCstModelParaDefs() {
		return cstModelParaDefs;
	}

	public void setCstModelParaDefs(List<CstModelParaDef> cstModelParaDefs) {
		this.cstModelParaDefs = cstModelParaDefs;
	}

	public List<CstModelProdModuleRel> getCstModelProdModuleRels() {
		return cstModelProdModuleRels;
	}

	public void setCstModelProdModuleRels(List<CstModelProdModuleRel> cstModelProdModuleRels) {
		this.cstModelProdModuleRels = cstModelProdModuleRels;
	}

	public List<CstModelCalculateFormula> getCalculateFormulas() {
		return calculateFormulas;
	}

	public void setCalculateFormulas(List<CstModelCalculateFormula> calculateFormulas) {
		this.calculateFormulas = calculateFormulas;
	}
	
}