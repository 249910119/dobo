package com.dobo.modules.cop.rest.entity;


public class CopCaseData {

	private String num;
	private String serviceType;
	private String serviceTypeName;
	private String serviceContent;
	private String partsInfo;
	private String factoryId;
	private String factoryName;
	private String modelId;
	private String modelName;
	private String Sn;
	
	private String roleId;
	private String roleName;
	private String engineerLevel;
	private String engineerLevelName;
	private String resourceType;
	private String resourceTypeName;
	private Double workload;
	private Double price;
	private Double travelPrice;
	
	private String partPn;
	private Double amount;
	private Double partsPrice;
	private Double deliveryPrice;
	
	private String projectCode;
	private String projectName;
	private Double actualCost;

	private String onceCode; // 
	private String woProjectCode; //核销对应项目编号
	
	public CopCaseData(){
		
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	
	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getPartsInfo() {
		return partsInfo;
	}

	public void setPartsInfo(String partsInfo) {
		this.partsInfo = partsInfo;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSn() {
		return Sn;
	}

	public void setSn(String sn) {
		Sn = sn;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEngineerLevel() {
		return engineerLevel;
	}

	public void setEngineerLevel(String engineerLevel) {
		this.engineerLevel = engineerLevel;
	}

	public String getEngineerLevelName() {
		return engineerLevelName;
	}

	public void setEngineerLevelName(String engineerLevelName) {
		this.engineerLevelName = engineerLevelName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public Double getWorkload() {
		return workload;
	}

	public void setWorkload(Double workload) {
		this.workload = workload;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTravelPrice() {
		return travelPrice;
	}

	public void setTravelPrice(Double travelPrice) {
		this.travelPrice = travelPrice;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getActualCost() {
		return actualCost;
	}

	public void setActualCost(Double actualCost) {
		this.actualCost = actualCost;
	}

	public String getOnceCode() {
		return onceCode;
	}

	public void setOnceCode(String onceCode) {
		this.onceCode = onceCode;
	}

	public String getWoProjectCode() {
		return woProjectCode;
	}

	public void setWoProjectCode(String woProjectCode) {
		this.woProjectCode = woProjectCode;
	}

	public String getPartPn() {
		return partPn;
	}

	public void setPartPn(String partPn) {
		this.partPn = partPn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPartsPrice() {
		return partsPrice;
	}

	public void setPartsPrice(Double partsPrice) {
		this.partsPrice = partsPrice;
	}

	public Double getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

}
