package com.dobo.modules.cst.rest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class DetailCostInfo {
	
	private String dcPrjId;
	private String detailId;
	private BigDecimal manCost = BigDecimal.ZERO;  //人工
	private BigDecimal backCost = BigDecimal.ZERO; //备件
	private BigDecimal subCost = BigDecimal.ZERO;  //分包
	private BigDecimal urgeCost = BigDecimal.ZERO;  //激励
	private BigDecimal pmCost = BigDecimal.ZERO;  //项目管理
	private Map<String, String> costInfoMap; // 资源模型大表集合
	private Map<String, String> backSubCostMap; // 备件-风险储备金集合
	
	private String resourceId;
	private String mfrId;
	private String resModel;
	private String groupModel;
	private String equipType;
	private Date serviceBegin;
	private Date serviceEnd;
	
	public DetailCostInfo() {}
	
	public DetailCostInfo(String detailId) {
		this.detailId = detailId;
	}
	
	public DetailCostInfo(String detailId, String resourceId) {
		this.detailId = detailId;
		this.resourceId = resourceId;
	}
	
	public DetailCostInfo(String detailId, String mfrId, String resModel, String groupModel, String equipType) {
		this.detailId = detailId;
		this.mfrId = mfrId;
		this.resModel = resModel;
		this.groupModel = groupModel;
		this.equipType = equipType;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}

	public BigDecimal getManCost() {
		return manCost;
	}

	public void setManCost(BigDecimal manCost) {
		this.manCost = manCost;
	}

	public BigDecimal getBackCost() {
		return backCost;
	}

	public void setBackCost(BigDecimal backCost) {
		this.backCost = backCost;
	}

	public BigDecimal getSubCost() {
		return subCost;
	}

	public void setSubCost(BigDecimal subCost) {
		this.subCost = subCost;
	}

	public BigDecimal getUrgeCost() {
		return urgeCost;
	}

	public void setUrgeCost(BigDecimal urgeCost) {
		this.urgeCost = urgeCost;
	}

	public BigDecimal getPmCost() {
		return pmCost;
	}

	public void setPmCost(BigDecimal pmCost) {
		this.pmCost = pmCost;
	}

	public Map<String, String> getCostInfoMap() {
		return costInfoMap;
	}

	public void setCostInfoMap(Map<String, String> costInfoMap) {
		this.costInfoMap = costInfoMap;
	}

	public Map<String, String> getBackSubCostMap() {
		return backSubCostMap;
	}

	public void setBackSubCostMap(Map<String, String> backSubCostMap) {
		this.backSubCostMap = backSubCostMap;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getMfrId() {
		return mfrId;
	}

	public void setMfrId(String mfrId) {
		this.mfrId = mfrId;
	}

	public String getResModel() {
		return resModel;
	}

	public void setResModel(String resModel) {
		this.resModel = resModel;
	}

	public String getGroupModel() {
		return groupModel;
	}

	public void setGroupModel(String groupModel) {
		this.groupModel = groupModel;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public Date getServiceBegin() {
		return serviceBegin;
	}

	public void setServiceBegin(Date serviceBegin) {
		this.serviceBegin = serviceBegin;
	}

	public Date getServiceEnd() {
		return serviceEnd;
	}

	public void setServiceEnd(Date serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

	@SuppressWarnings("serial")
	public class CostInfo implements Serializable {
		
		private String resTypeId;  //一线1~5级,二线4~6级,三线,CMO,PMO,资源岗,区域管理,总控管理,管理
		private String resCost; //资源成本 包含（人工包、费用包、激励包、差旅费）
		private String resPlan;  //资源计划（单位：人月）
		private String resValue; //组合资源成本和资源计划","分隔
		
		public CostInfo() {}
		
		public CostInfo(String resTypeId, String resCost, String resPlan) {
			this.resTypeId = resTypeId;
			this.resCost = resCost;
			this.resPlan = resPlan;
		}
		
		public CostInfo(String resTypeId, String resValue) {
			this.resTypeId = resTypeId;
			this.resValue = resValue;
		}
		
		/*public CostInfo(String resTypeId, String manCost, String feeCost, String travCost, String urgeCost, String resPlan) {
			this.resTypeId = resTypeId;
			this.manCost = manCost;
			this.feeCost = feeCost;
			this.travCost = travCost;
			this.urgeCost = urgeCost;
			this.resPlan = resPlan;
		}*/

		public String getResTypeId() {
			return resTypeId;
		}

		public void setResTypeId(String resTypeId) {
			this.resTypeId = resTypeId;
		}

		public String getResCost() {
			return resCost;
		}

		public void setResCost(String resCost) {
			this.resCost = resCost;
		}

		public String getResPlan() {
			return resPlan;
		}

		public void setResPlan(String resPlan) {
			this.resPlan = resPlan;
		}

		public String getResValue() {
			resValue = this.resCost + "," + this.resPlan;
			return resValue;
		}

		public void setResValue(String resValue) {
			this.resValue = resValue;
		}
		
	}
}
