/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 备件合作成本参数Entity
 * @author admin
 * @version 2019-01-11
 */
public class CstPartsCooperCost extends DataEntity<CstPartsCooperCost> {
	
	private static final long serialVersionUID = 1L;
	private String mfrId;		// 厂商标识
	private String equiptypeId;		// 技术方向标识
	private String equiptypeDesc;		// 资源描述（格式：厂商|型号组|技术方向）
	private String packId;		// 合作包ID
	private String province;		// 省份
	private String dateId;		// 时间ID：开始和截止时间对应
	private Date beginDate;		// 开始时间
	private Date endDate;		// 截止时间
	private Double notHighA;		// 非高端高标（A/A+)合作成本
	private Double notHighB;		// 非高端高标（B)合作成本
	private Double notHighC;		// 非高端高标（C)合作成本
	private Double notHighD;		// 非高端高标（D)合作成本
	private Double highA;		// 高端高标（A/A+)合作成本
	private Double highB;		// 高端高标（B)合作成本
	private Double highC;		// 高端高标（C)合作成本
	private Double highD;		// 高端高标（D)合作成本
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstPartsCooperCost() {
		super();
	}

	public CstPartsCooperCost(String id){
		super(id);
	}

	@Length(min=0, max=30, message="厂商标识长度必须介于 0 和 30 之间")
	public String getMfrId() {
		return mfrId == null ? "" : mfrId;
	}

	public void setMfrId(String mfrId) {
		this.mfrId = mfrId;
	}
	
	@Length(min=0, max=30, message="技术方向标识长度必须介于 0 和 30 之间")
	public String getEquiptypeId() {
		return equiptypeId == null ? "" : equiptypeId;
	}

	public void setEquiptypeId(String equiptypeId) {
		this.equiptypeId = equiptypeId;
	}
	
	@Length(min=0, max=255, message="资源描述（格式：厂商|型号组|技术方向）长度必须介于 0 和 255 之间")
	public String getEquiptypeDesc() {
		return equiptypeDesc;
	}

	public void setEquiptypeDesc(String equiptypeDesc) {
		this.equiptypeDesc = equiptypeDesc;
	}
	
	@Length(min=0, max=30, message="合作包ID长度必须介于 0 和 30 之间")
	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}
	
	@Length(min=0, max=15, message="省份长度必须介于 0 和 15 之间")
	public String getProvince() {
		return province == null ? "" : province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=15, message="时间ID：开始和截止时间对应长度必须介于 0 和 15 之间")
	public String getDateId() {
		return dateId;
	}

	public void setDateId(String dateId) {
		this.dateId = dateId;
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
	
	public Double getNotHighA() {
		return notHighA;
	}

	public void setNotHighA(Double notHighA) {
		this.notHighA = notHighA;
	}
	
	public Double getNotHighB() {
		return notHighB;
	}

	public void setNotHighB(Double notHighB) {
		this.notHighB = notHighB;
	}
	
	public Double getNotHighC() {
		return notHighC;
	}

	public void setNotHighC(Double notHighC) {
		this.notHighC = notHighC;
	}
	
	public Double getNotHighD() {
		return notHighD;
	}

	public void setNotHighD(Double notHighD) {
		this.notHighD = notHighD;
	}
	
	public Double getHighA() {
		return highA;
	}

	public void setHighA(Double highA) {
		this.highA = highA;
	}
	
	public Double getHighB() {
		return highB;
	}

	public void setHighB(Double highB) {
		this.highB = highB;
	}
	
	public Double getHighC() {
		return highC;
	}

	public void setHighC(Double highC) {
		this.highC = highC;
	}
	
	public Double getHighD() {
		return highD;
	}

	public void setHighD(Double highD) {
		this.highD = highD;
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