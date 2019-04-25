/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件故障事件级别占比参数获取Entity
 * @author admin
 * @version 2019-03-27
 */
public class CstBaseBackCasePara extends DataEntity<CstBaseBackCasePara> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// 明细ID
	private String resstattypeName;		// 资源计划分类名称
	private String resourceDesc;		// 资源描述
	private Double level1CaseRate;		// 一级事件占比
	private Double level2CaseRate;		// 二级事件占比
	private Double level3CaseRate;		// 三级事件占比
	private Double level4CaseRate;		// 四级事件占比
	private String status;		// A0:有效/A1:无效
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstBaseBackCasePara() {
		super();
	}

	public CstBaseBackCasePara(String id){
		super(id);
	}

	@Length(min=1, max=32, message="明细ID长度必须介于 1 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=0, max=32, message="资源计划分类名称长度必须介于 0 和 32 之间")
	public String getResstattypeName() {
		return resstattypeName;
	}

	public void setResstattypeName(String resstattypeName) {
		this.resstattypeName = resstattypeName;
	}
	
	@Length(min=0, max=256, message="资源描述长度必须介于 0 和 256 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	public Double getLevel1CaseRate() {
		return level1CaseRate;
	}

	public void setLevel1CaseRate(Double level1CaseRate) {
		this.level1CaseRate = level1CaseRate;
	}
	
	public Double getLevel2CaseRate() {
		return level2CaseRate;
	}

	public void setLevel2CaseRate(Double level2CaseRate) {
		this.level2CaseRate = level2CaseRate;
	}
	
	public Double getLevel3CaseRate() {
		return level3CaseRate;
	}

	public void setLevel3CaseRate(Double level3CaseRate) {
		this.level3CaseRate = level3CaseRate;
	}
	
	public Double getLevel4CaseRate() {
		return level4CaseRate;
	}

	public void setLevel4CaseRate(Double level4CaseRate) {
		this.level4CaseRate = level4CaseRate;
	}
	
	@Length(min=1, max=2, message="A0:有效/A1:无效长度必须介于 1 和 2 之间")
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