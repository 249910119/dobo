package com.dobo.modules.cst.entity.base;

import java.io.Serializable;

/**
 * 驻场工程师级别与人员角色对应关系
 */
public class CstManLevelRoleRel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String manLevel;		// 驻场工程师级别
	private String resstattypeName;		// 资源计划分类
	private String roleName;		// 人员角色名称
	private String roleId;		// 人员角色ID
	private String measureId;		// 人员角色对应的资源计划(工时)measureId
	
	public CstManLevelRoleRel() {
		super();
	}
	
	public CstManLevelRoleRel(String manLevel, String roleName, String roleId, String measureId) {
		this.manLevel = manLevel;
		this.roleName = roleName;
		this.roleId = roleId;
		this.measureId = measureId;
	}
	
	public CstManLevelRoleRel(String manLevel, String resstattypeName, String roleName, String roleId, String measureId) {
		this.manLevel = manLevel;
		this.resstattypeName = resstattypeName;
		this.roleName = roleName;
		this.roleId = roleId;
		this.measureId = measureId;
	}

	public String getManLevel() {
		return manLevel;
	}

	public void setManLevel(String manLevel) {
		this.manLevel = manLevel;
	}

	public String getResstattypeName() {
		return resstattypeName;
	}
	public void setResstattypeName(String resstattypeName) {
		this.resstattypeName = resstattypeName;
	}
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMeasureId() {
		return measureId;
	}
	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}

}