/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 包名归属字典---主要用于维护主数据参考用Entity
 * @author admin
 * @version 2019-01-18
 */
public class CstPartsPackInfo extends DataEntity<CstPartsPackInfo> {
	
	private static final long serialVersionUID = 1L;
	private String packId;		// 合作包ID
	private String packCode;		// 合作包代码
	private String packName;		// 合作包名称
	private Date beginDate;		// 开始时间
	private Date endDate;		// 截止时间
	private String province;		// 省份
	private String mfrName;		// 厂商
	private String equiptypeName;		// 技术方向全称
	private String topPackCode;		// TOP合作代码
	private String topPackName;		// TOP合作包名名称
	private String supplier;		// 供应商名称
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstPartsPackInfo() {
		super();
	}

	public CstPartsPackInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="合作包ID长度必须介于 0 和 32 之间")
	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}
	
	@Length(min=0, max=32, message="合作包代码长度必须介于 0 和 32 之间")
	public String getPackCode() {
		return packCode;
	}

	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}
	
	@Length(min=0, max=64, message="合作包名称长度必须介于 0 和 64 之间")
	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=15, message="省份长度必须介于 0 和 15 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="厂商长度必须介于 0 和 64 之间")
	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	
	@Length(min=0, max=64, message="技术方向全称长度必须介于 0 和 64 之间")
	public String getEquiptypeName() {
		return equiptypeName;
	}

	public void setEquiptypeName(String equiptypeName) {
		this.equiptypeName = equiptypeName;
	}
	
	@Length(min=0, max=32, message="TOP合作代码长度必须介于 0 和 32 之间")
	public String getTopPackCode() {
		return topPackCode;
	}

	public void setTopPackCode(String topPackCode) {
		this.topPackCode = topPackCode;
	}
	
	@Length(min=0, max=64, message="TOP合作包名名称长度必须介于 0 和 64 之间")
	public String getTopPackName() {
		return topPackName;
	}

	public void setTopPackName(String topPackName) {
		this.topPackName = topPackName;
	}
	
	@Length(min=0, max=64, message="供应商名称长度必须介于 0 和 64 之间")
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	@Length(min=0, max=2, message="状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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