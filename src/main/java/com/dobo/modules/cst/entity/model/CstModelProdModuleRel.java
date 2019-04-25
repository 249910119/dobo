/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.model;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 产品成本模型使用定义表，定义某产品使用的成本模型Entity
 * @author admin
 * @version 2016-11-12
 */
public class CstModelProdModuleRel extends DataEntity<CstModelProdModuleRel> {
	
	private static final long serialVersionUID = 1L;
	private String cpmName;		// 成本模型使用名称
	private String moduleId;		// 成本模型标识
	private String prodId;		// 产品标识
	private String status;		// 状态
	private String className;		// 参数计算类：对应的java类全名（含包路径）
	private Long calOrder;		// 显示顺序
	private CstModelModuleInfo cstModelModuleInfo;		//成本模型信息
	
	public CstModelModuleInfo getCstModelModuleInfo() {
		return cstModelModuleInfo;
	}

	public void setCstModelModuleInfo(CstModelModuleInfo cstModelModuleInfo) {
		this.cstModelModuleInfo = cstModelModuleInfo;
	}

	
	public CstModelProdModuleRel() {
		super();
	}

	public CstModelProdModuleRel(String id){
		super(id);
	}

	@Length(min=1, max=128, message="成本模型使用名称长度必须介于 1 和 128 之间")
	public String getCpmName() {
		return cpmName;
	}

	public void setCpmName(String cpmName) {
		this.cpmName = cpmName;
	}
	
	@Length(min=1, max=64, message="成本模型标识长度必须介于 1 和 64 之间")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Length(min=1, max=64, message="产品标识长度必须介于 1 和 64 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=1, max=128, message="成本模型计算类长度必须介于 1 和 128 之间")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getCalOrder() {
		return calOrder;
	}

	public void setCalOrder(Long calOrder) {
		this.calOrder = calOrder;
	}
	
}