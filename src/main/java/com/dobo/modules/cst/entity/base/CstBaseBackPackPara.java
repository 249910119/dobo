/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.base;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件合作包取值参数获取Entity
 * @author admin
 * @version 2019-03-27
 */
public class CstBaseBackPackPara extends DataEntity<CstBaseBackPackPara> {
	
	private static final long serialVersionUID = 1L;
	private String detailId;		// 明细ID
	private String mfrName;		// 厂商
	private String equipTypeName;		// 技术方向
	private String resourceDesc;		// 资源描述
	private String cooperCode;		// 合作代码
	private String packageName;		// 包名称
	private String supplierName;		// 供应商名称
	private String status;		// A0:有效/A1:无效
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstBaseBackPackPara() {
		super();
	}

	public CstBaseBackPackPara(String id){
		super(id);
	}

	@Length(min=1, max=32, message="明细ID长度必须介于 1 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	@Length(min=0, max=32, message="厂商长度必须介于 0 和 32 之间")
	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	
	@Length(min=0, max=64, message="技术方向长度必须介于 0 和 64 之间")
	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}
	
	@Length(min=0, max=256, message="资源描述长度必须介于 0 和 256 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	@Length(min=0, max=32, message="合作代码长度必须介于 0 和 32 之间")
	public String getCooperCode() {
		return cooperCode;
	}

	public void setCooperCode(String cooperCode) {
		this.cooperCode = cooperCode;
	}
	
	@Length(min=0, max=64, message="包名称长度必须介于 0 和 64 之间")
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	@Length(min=0, max=128, message="供应商名称长度必须介于 0 和 128 之间")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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