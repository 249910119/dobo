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
 * 备件事件故障参数定义Entity
 * @author admin
 * @version 2016-11-15
 */
public class CstPartsEventFailurePara extends DataEntity<CstPartsEventFailurePara> {
	
	private static final long serialVersionUID = 1L;
	private String resourceId;		// 资源标识
	private String resourceDesc;		// 资源描述
	private Double eventFailureRate;		// 事件故障率
	private Double level1EventScale;		// 1级事件占比
	private Double level2EventScale;		// 2级事件占比
	private Double level3EventScale;		// 3级事件占比
	private Double level4EventScale;		// 4级事件占比
	private Double failureRate;		// 备件故障率
	private Double averageCost;		// 平均采购成本
	private Double averageFailureCost;		// 事件故障率
	private String prjSub;		// 是否分包产品
	private Double failureCostScale;		// 设备型号故障成本价格系数
	private Double finalCostPrice;		// 最终成本价格(不参与计算)
	private String partJointPackCostId;		// 备件合作包备件成本ID,关联字典表获取系数值
	private String status;		// 状态
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	
	public CstPartsEventFailurePara() {
		super();
	}

	public CstPartsEventFailurePara(String id){
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
	
	@NotNull(message="事件故障率不能为空")
	public Double getEventFailureRate() {
		return eventFailureRate;
	}

	public void setEventFailureRate(Double eventFailureRate) {
		this.eventFailureRate = eventFailureRate;
	}
	
	@NotNull(message="1级事件占比不能为空")
	public Double getLevel1EventScale() {
		return level1EventScale;
	}

	public void setLevel1EventScale(Double level1EventScale) {
		this.level1EventScale = level1EventScale;
	}
	
	@NotNull(message="2级事件占比不能为空")
	public Double getLevel2EventScale() {
		return level2EventScale;
	}

	public void setLevel2EventScale(Double level2EventScale) {
		this.level2EventScale = level2EventScale;
	}
	
	@NotNull(message="3级事件占比不能为空")
	public Double getLevel3EventScale() {
		return level3EventScale;
	}

	public void setLevel3EventScale(Double level3EventScale) {
		this.level3EventScale = level3EventScale;
	}
	
	@NotNull(message="4级事件占比不能为空")
	public Double getLevel4EventScale() {
		return level4EventScale;
	}

	public void setLevel4EventScale(Double level4EventScale) {
		this.level4EventScale = level4EventScale;
	}

	@NotNull(message="备件故障率不能为空")
	public Double getFailureRate() {
		return failureRate;
	}

	public void setFailureRate(Double failureRate) {
		this.failureRate = failureRate;
	}

	@NotNull(message="采购平均成本不能为空")
	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	@NotNull(message="sum(备件故障率*采购平均成本)不能为空")
	public Double getAverageFailureCost() {
		return averageFailureCost;
	}

	public void setAverageFailureCost(Double averageFailureCost) {
		this.averageFailureCost = averageFailureCost;
	}

	@Length(min=1, max=1, message="是否分包产品长度必须介于 1 和 1 之间")
	public String getPrjSub() {
		return prjSub;
	}

	public void setPrjSub(String isprjSub) {
		this.prjSub = isprjSub;
	}

	@NotNull(message="设备型号故障成本价格系数不能为空")
	public Double getFailureCostScale() {
		return failureCostScale;
	}

	public void setFailureCostScale(Double failureCostScale) {
		this.failureCostScale = failureCostScale;
	}

	@NotNull(message="最终成本价格不能为空")
	public Double getFinalCostPrice() {
		return finalCostPrice;
	}

	public void setFinalCostPrice(Double finalCostPrice) {
		this.finalCostPrice = finalCostPrice;
	}

	@Length(min=1, max=10, message="备件合作包备件成本系数ID长度必须介于 1 和 10 之间")
	public String getPartJointPackCostId() {
		return partJointPackCostId;
	}

	public void setPartJointPackCostId(String partJointPackCostId) {
		this.partJointPackCostId = partJointPackCostId;
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