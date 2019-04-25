/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.man;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 故障级别配比定义表Entity
 * @author admin
 * @version 2016-11-08
 */
public class CstManFailureSlaPara extends DataEntity<CstManFailureSlaPara> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源标识
	private String resourceDesc;		// 资源描述
	private String slaId;		// 服务级别标识
	private String slaName;		// 服务级别
	private Double line1Level1Scale;		// 一线1级配比
	private Double line1Level2Scale;		// 一线2级配比
	private Double line1Level3Scale;		// 一线3级配比
	private Double line1Level4Scale;		// 一线4级配比
	private Double line1Level5Scale;		// 一线5级配比
	private Double line2Level4Scale;		// 二线4级配比
	private Double line2Level5Scale;		// 二线5级配比
	private Double line2Level6Scale;		// 二线6级配比
	private String status;		// 状态
	private Date statusChangeDate;		// 状态更新时间
	private String preStatus;		// 更新前状态
	private String saveFlag;		// 保存标记
	
	public CstManFailureSlaPara() {
		super();
	}

	public CstManFailureSlaPara(String id){
		super(id);
	}

	@Length(min=1, max=30, message="资源标识长度必须介于 1 和 30 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Length(min=1, max=255, message="资源描述长度必须介于 1 和 255 之间")
	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
	
	@Length(min=1, max=30, message="服务级别标识长度必须介于 1 和 30 之间")
	public String getSlaId() {
		return slaId;
	}

	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}
	
	@Length(min=1, max=30, message="服务级别长度必须介于 1 和 30 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@NotNull(message="一线1级配比不能为空")
	public Double getLine1Level1Scale() {
		return line1Level1Scale;
	}

	public void setLine1Level1Scale(Double line1Level1Scale) {
		this.line1Level1Scale = line1Level1Scale;
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
	
	@NotNull(message="一线5级配比不能为空")
	public Double getLine1Level5Scale() {
		return line1Level5Scale;
	}

	public void setLine1Level5Scale(Double line1Level5Scale) {
		this.line1Level5Scale = line1Level5Scale;
	}
	
	@NotNull(message="二线4级配比不能为空")
	public Double getLine2Level4Scale() {
		return line2Level4Scale;
	}

	public void setLine2Level4Scale(Double line2Level4Scale) {
		this.line2Level4Scale = line2Level4Scale;
	}
	
	@NotNull(message="二线5级配比不能为空")
	public Double getLine2Level5Scale() {
		return line2Level5Scale;
	}

	public void setLine2Level5Scale(Double line2Level5Scale) {
		this.line2Level5Scale = line2Level5Scale;
	}
	
	@NotNull(message="二线6级配比不能为空")
	public Double getLine2Level6Scale() {
		return line2Level6Scale;
	}

	public void setLine2Level6Scale(Double line2Level6Scale) {
		this.line2Level6Scale = line2Level6Scale;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	
	@Length(min=0, max=2, message="更新前状态长度必须介于 0 和 2 之间")
	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	
	@Length(min=0, max=1, message="保存标记长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}