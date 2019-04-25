/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.temp;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 非设备类系数表Entity
 * @author admin
 * @version 2017-03-07
 */
public class CstManNotDevicePara extends DataEntity<CstManNotDevicePara> {
	
	private static final long serialVersionUID = 1L;
	private String prodDetail;		// 产品细分分类
	private String prodServiceId;		// 产品服务方式标识
	private String prodServiceName;		// 产品服务方式名称
	private Double line1Level2Scale;		// 一线2级配比
	private Double line1Level3Scale;		// 一线3级配比
	private Double line1Level4Scale;		// 一线4级配比
	private Double standardAmount;		// 标量
	private Double prodDegree;		// 饱和度
	private Double line2Level4HourScale;		// 二线4级工时系数
	private Double line2Level5HourScale;		// 二线5级工时系数
	private Double line2Level6HourScale;		// 二线6级工时系数
	private Double cmoHourScale;		// CMO工时系数
	private Double pmLevel3HourScale;		// PM3级工时系数
	private Double pmLevel4HourScale;		// PM4级工时系数
	private Double pmLevel5HourScale;		// PM5级工时系数
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	
	public CstManNotDevicePara() {
		super();
	}

	public CstManNotDevicePara(String id){
		super(id);
	}

	@Length(min=0, max=64, message="产品细分分类长度必须介于 0 和 64 之间")
	public String getProdDetail() {
		return prodDetail;
	}

	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	
	@Length(min=1, max=64, message="产品服务方式标识长度必须介于 1 和 64 之间")
	public String getProdServiceId() {
		return prodServiceId;
	}

	public void setProdServiceId(String prodServiceId) {
		this.prodServiceId = prodServiceId;
	}
	
	@Length(min=1, max=128, message="产品服务方式名称长度必须介于 1 和 128 之间")
	public String getProdServiceName() {
		return prodServiceName;
	}

	public void setProdServiceName(String prodServiceName) {
		this.prodServiceName = prodServiceName;
	}
	
	@NotNull(message="一线2级配比不能为空")
	public Double getLine1Level2Scale() {
		return line1Level2Scale;
	}

	public void setLine1Level2Scale(Double line1Level2Scale) {
		this.line1Level2Scale = line1Level2Scale;
	}
	
	@NotNull(message="一线3级配比不能为空")
	public Double getLine1Level3Scale() {
		return line1Level3Scale;
	}

	public void setLine1Level3Scale(Double line1Level3Scale) {
		this.line1Level3Scale = line1Level3Scale;
	}
	
	@NotNull(message="一线4级配比不能为空")
	public Double getLine1Level4Scale() {
		return line1Level4Scale;
	}

	public void setLine1Level4Scale(Double line1Level4Scale) {
		this.line1Level4Scale = line1Level4Scale;
	}
	
	@NotNull(message="标量不能为空")
	public Double getStandardAmount() {
		return standardAmount;
	}

	public void setStandardAmount(Double standardAmount) {
		this.standardAmount = standardAmount;
	}
	
	public Double getProdDegree() {
		return prodDegree;
	}

	public void setProdDegree(Double prodDegree) {
		this.prodDegree = prodDegree;
	}
	
	@NotNull(message="二线4级工时系数不能为空")
	public Double getLine2Level4HourScale() {
		return line2Level4HourScale;
	}

	public void setLine2Level4HourScale(Double line2Level4HourScale) {
		this.line2Level4HourScale = line2Level4HourScale;
	}
	
	@NotNull(message="二线5级工时系数不能为空")
	public Double getLine2Level5HourScale() {
		return line2Level5HourScale;
	}

	public void setLine2Level5HourScale(Double line2Level5HourScale) {
		this.line2Level5HourScale = line2Level5HourScale;
	}
	
	@NotNull(message="二线6级工时系数不能为空")
	public Double getLine2Level6HourScale() {
		return line2Level6HourScale;
	}

	public void setLine2Level6HourScale(Double line2Level6HourScale) {
		this.line2Level6HourScale = line2Level6HourScale;
	}
	
	@NotNull(message="CMO工时系数不能为空")
	public Double getCmoHourScale() {
		return cmoHourScale;
	}

	public void setCmoHourScale(Double cmoHourScale) {
		this.cmoHourScale = cmoHourScale;
	}
	
	@NotNull(message="PM3级工时系数不能为空")
	public Double getPmLevel3HourScale() {
		return pmLevel3HourScale;
	}

	public void setPmLevel3HourScale(Double pmLevel3HourScale) {
		this.pmLevel3HourScale = pmLevel3HourScale;
	}
	
	@NotNull(message="PM4级工时系数不能为空")
	public Double getPmLevel4HourScale() {
		return pmLevel4HourScale;
	}

	public void setPmLevel4HourScale(Double pmLevel4HourScale) {
		this.pmLevel4HourScale = pmLevel4HourScale;
	}
	
	@NotNull(message="PM5级工时系数不能为空")
	public Double getPmLevel5HourScale() {
		return pmLevel5HourScale;
	}

	public void setPmLevel5HourScale(Double pmLevel5HourScale) {
		this.pmLevel5HourScale = pmLevel5HourScale;
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
	
	@Length(min=0, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}