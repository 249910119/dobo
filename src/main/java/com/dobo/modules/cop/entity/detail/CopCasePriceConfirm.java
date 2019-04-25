/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.entity.detail;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * CASE单次报价确认表(TOP)Entity
 * @author admin
 * @version 2017-06-09
 */
public class CopCasePriceConfirm extends DataEntity<CopCasePriceConfirm> {
	
	private static final long serialVersionUID = 1L;
	private String dcPrjId;  	// 项目编号
	private String dcPrjName; 	// 项目名称
	private String salesId;		//销售id
	private String salesName;	//销售姓名
	private String onceNum;		// once_num
	private String onceCode;		// once_code
	private String caseId;		// case_id
	private String caseCode;		// case_code
	private String priceType;		// 1人员，2备件
	private String serviceType;		// 1故障、3巡检、5非故障技术支持、6专业化服务
	private String payType;		// 1：预付费:2：单次:3：单次入项目
	private Double costPrepay;		// 人工或备件金额
	private Double costPrepayTravel;		// 人工差旅或备件专送费用
	private String caseDesc;		// 单次和单次入项目用
	private String prePayInfo;		// 预付费在项目中的使用情况，如：[CZ2AF60036:1000]
	private String areaName;		//区域/项目部名称
	private String province;		//事件所在省
	private String cityId;			//事件所在城市id
	private String cityName;		//事件所在城市名称
	private String shareNo;			//事业部标注的用来做分摊的编号信息
	private String resBaseOrg;		//设备归属项目部
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private String resstattypeName;		// 技术方向分类(资源计划分类)
	private Date changePrepayDate;		// 改预付费支持时间
	
	public CopCasePriceConfirm() {
		super();
	}

	public CopCasePriceConfirm(String id){
		super(id);
	}

	@Length(min=1, max=256, message="dcPrjId长度必须介于 1 和 256 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}

	@Length(min=1, max=256, message="dcPrjName长度必须介于 1 和 256 之间")
	public String getDcPrjName() {
		return dcPrjName;
	}

	public void setDcPrjName(String dcPrjName) {
		this.dcPrjName = dcPrjName;
	}

	@Length(min=0, max=30, message="销售id长度必须介于 0和 30之间")
	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	@Length(min=0, max=50, message="销售姓名长度必须介于 0和 50之间")
	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Length(min=1, max=32, message="once_num长度必须介于 1 和 32 之间")
	public String getOnceNum() {
		return onceNum;
	}

	public void setOnceNum(String onceNum) {
		this.onceNum = onceNum;
	}
	
	@Length(min=0, max=128, message="once_code长度必须介于 0 和128之间")
	public String getOnceCode() {
		return onceCode;
	}

	public void setOnceCode(String onceCode) {
		this.onceCode = onceCode;
	}
	
	@Length(min=1, max=32, message="case_id长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Length(min=1, max=256 , message="case_code长度必须介于 1 和 256 之间")
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	@Length(min=1, max=1, message="1人员，2备件长度必须介于 1 和 1 之间")
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@Length(min=1, max=1, message="1故障、3巡检、5非故障技术支持、6专业化服务长度必须介于 1 和 1 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=1, max=1, message="1：预付费:2：单次:3：单次入项目长度必须介于 1 和 1 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public Double getCostPrepay() {
		return costPrepay;
	}

	public void setCostPrepay(Double costPrepay) {
		this.costPrepay = costPrepay;
	}
	
	public Double getCostPrepayTravel() {
		return costPrepayTravel;
	}

	public void setCostPrepayTravel(Double costPrepayTravel) {
		this.costPrepayTravel = costPrepayTravel;
	}
	
	@Length(min=0, max=255, message="单次和单次入项目用长度必须介于 0 和 255 之间")
	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}
	
	@Length(min=0, max=255, message="预付费在项目中的使用情况，如：[CZ2AF60036:1000]长度必须介于 0 和 255 之间")
	public String getPrePayInfo() {
		return prePayInfo;
	}

	public void setPrePayInfo(String prePayInfo) {
		this.prePayInfo = prePayInfo;
	}
	
	@Length(min=0, max=64, message="区域/项目部名称长度必须介于 0 和 64 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=64, message="事件所在省长度必须介于 0 和 64 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="事件所在城市id长度必须介于 0 和 64 之间")
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@Length(min=0, max=64, message="事件所在城市名称长度必须介于 0 和 64 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Length(min=0, max=256, message="分摊编号长度必须介于 0 和 256 之间")
	public String getShareNo() {
		return shareNo;
	}

	public void setShareNo(String shareNo) {
		this.shareNo = shareNo;
	}

	@Length(min=0, max=128, message="设备归属项目部长度必须介于 0 和 128 之间")
	public String getResBaseOrg() {
		return resBaseOrg;
	}

	public void setResBaseOrg(String resBaseOrg) {
		this.resBaseOrg = resBaseOrg;
	}

	@Length(min=1, max=2, message="状态（A0:有效/A1:无效）长度必须介于 1 和 2 之间")
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
	
	@Length(min=1, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getResstattypeName() {
		return resstattypeName;
	}

	public void setResstattypeName(String resstattypeName) {
		this.resstattypeName = resstattypeName;
	}

	public Date getChangePrepayDate() {
		return changePrepayDate;
	}

	public void setChangePrepayDate(Date changePrepayDate) {
		this.changePrepayDate = changePrepayDate;
	}
	
}