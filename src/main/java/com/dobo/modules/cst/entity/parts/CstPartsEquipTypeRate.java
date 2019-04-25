/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.parts;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * cst_parts_equip_type_rateEntity
 * @author admin
 * @version 2017-01-19
 */
public class CstPartsEquipTypeRate extends DataEntity<CstPartsEquipTypeRate> {
	
	private static final long serialVersionUID = 1L;
	private String equiptypeId;		// 技术方向标识
	private String equiptypeDesc;		// 技术方向描述
	private Double equipManRate;		// 技术方向备件人工单件成本系数
	private Double equipUrgeRate;		// 技术方向备件激励单件成本系数
	private Double slaCostRate;		// 故障成本SLA采购成本系数
	private Double hisPartsAmount;		// 技术方向历史备件更换量
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsEquipTypeRate() {
		super();
	}

	public CstPartsEquipTypeRate(String id){
		super(id);
	}

	@Length(min=1, max=30, message="技术方向标识长度必须介于 1 和 30 之间")
	public String getEquiptypeId() {
		return equiptypeId;
	}

	public void setEquiptypeId(String equiptypeId) {
		this.equiptypeId = equiptypeId;
	}
	
	@Length(min=1, max=255, message="技术方向描述长度必须介于 1 和 255 之间")
	public String getEquiptypeDesc() {
		return equiptypeDesc;
	}

	public void setEquiptypeDesc(String equiptypeDesc) {
		this.equiptypeDesc = equiptypeDesc;
	}
	
	@NotNull(message="技术方向备件人工单件成本系数不能为空")
	public Double getEquipManRate() {
		return equipManRate;
	}

	public void setEquipManRate(Double equipManRate) {
		this.equipManRate = equipManRate;
	}
	
	@NotNull(message="技术方向备件激励单件成本系数不能为空")
	public Double getEquipUrgeRate() {
		return equipUrgeRate;
	}

	public void setEquipUrgeRate(Double equipUrgeRate) {
		this.equipUrgeRate = equipUrgeRate;
	}
	
	@NotNull(message="故障成本SLA采购成本系数不能为空")
	public Double getSlaCostRate() {
		return slaCostRate;
	}

	public void setSlaCostRate(Double slaCostRate) {
		this.slaCostRate = slaCostRate;
	}
	
	@NotNull(message="技术方向历史备件更换量不能为空")
	public Double getHisPartsAmount() {
		return hisPartsAmount;
	}

	public void setHisPartsAmount(Double hisPartsAmount) {
		this.hisPartsAmount = hisPartsAmount;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2, message="更新前状态长度必须介于 0 和 2 之间")
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
	
	@Length(min=1, max=1, message="保存标记长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}