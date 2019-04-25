/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.cases;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 单次、先行支持标准工时系数Entity
 * @author admin
 * @version 2017-06-05
 */
public class CstCaseStandHourScale extends DataEntity<CstCaseStandHourScale> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// 服务产品标识
	private String prodDesc;		// 服务产品描述
	private String paytypeId;		// 支付方式标识
	private String paytypeDesc;		// 支付方式描述
	private Double yearFailureRate;		// 年化故障率
	private Double cmoOnceHour;		// CMO单次CASE处理工时
	private Double line2OnceHour;		// 二线单次CASE工时
	private Double pmOnceHour;		// PM单次CASE处理工时
	private Double resMgrOnceHour;		// 项目部管理单次CASE处理工时
	private Double line2Level4Scale;		// 二线4级配比系数
	private Double line2Level5Scale;		// 二线5级配比系数
	private Double line2Level6Scale;		// 二线6级配比系数
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstCaseStandHourScale() {
		super();
	}

	public CstCaseStandHourScale(String id){
		super(id);
	}

	@Length(min=0, max=30, message="服务产品标识长度必须介于 0 和 30 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=0, max=255, message="服务产品描述长度必须介于 0 和 255 之间")
	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	
	@Length(min=0, max=30, message="支付方式标识长度必须介于 0 和 30 之间")
	public String getPaytypeId() {
		return paytypeId;
	}

	public void setPaytypeId(String paytypeId) {
		this.paytypeId = paytypeId;
	}
	
	@Length(min=0, max=255, message="支付方式描述长度必须介于 0 和 255 之间")
	public String getPaytypeDesc() {
		return paytypeDesc;
	}

	public void setPaytypeDesc(String paytypeDesc) {
		this.paytypeDesc = paytypeDesc;
	}
	
	public Double getYearFailureRate() {
		return yearFailureRate;
	}

	public void setYearFailureRate(Double yearFailureRate) {
		this.yearFailureRate = yearFailureRate;
	}
	
	public Double getCmoOnceHour() {
		return cmoOnceHour;
	}

	public void setCmoOnceHour(Double cmoOnceHour) {
		this.cmoOnceHour = cmoOnceHour;
	}
	
	public Double getLine2OnceHour() {
		return line2OnceHour;
	}

	public void setLine2OnceHour(Double line2OnceHour) {
		this.line2OnceHour = line2OnceHour;
	}
	
	public Double getPmOnceHour() {
		return pmOnceHour;
	}

	public void setPmOnceHour(Double pmOnceHour) {
		this.pmOnceHour = pmOnceHour;
	}
	
	public Double getResMgrOnceHour() {
		return resMgrOnceHour;
	}

	public void setResMgrOnceHour(Double resMgrOnceHour) {
		this.resMgrOnceHour = resMgrOnceHour;
	}
	
	public Double getLine2Level4Scale() {
		return line2Level4Scale;
	}

	public void setLine2Level4Scale(Double line2Level4Scale) {
		this.line2Level4Scale = line2Level4Scale;
	}
	
	public Double getLine2Level5Scale() {
		return line2Level5Scale;
	}

	public void setLine2Level5Scale(Double line2Level5Scale) {
		this.line2Level5Scale = line2Level5Scale;
	}
	
	public Double getLine2Level6Scale() {
		return line2Level6Scale;
	}

	public void setLine2Level6Scale(Double line2Level6Scale) {
		this.line2Level6Scale = line2Level6Scale;
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