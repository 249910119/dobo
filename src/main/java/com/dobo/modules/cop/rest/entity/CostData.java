package com.dobo.modules.cop.rest.entity;


public class CostData {

	private String orderId;
	private String prodManCost;
	private String finalManCost;
	private String prodBackCost;
	private String finalBackCost;
	private String serviceBegin;
	private String serviceEnd;
	
	public CostData(){
		
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProdManCost() {
		return prodManCost;
	}

	public void setProdManCost(String prodManCost) {
		this.prodManCost = prodManCost;
	}

	public String getFinalManCost() {
		return finalManCost;
	}

	public void setFinalManCost(String finalManCost) {
		this.finalManCost = finalManCost;
	}

	public String getProdBackCost() {
		return prodBackCost;
	}

	public void setProdBackCost(String prodBackCost) {
		this.prodBackCost = prodBackCost;
	}

	public String getFinalBackCost() {
		return finalBackCost;
	}

	public void setFinalBackCost(String finalBackCost) {
		this.finalBackCost = finalBackCost;
	}

	public String getServiceBegin() {
		return serviceBegin;
	}

	public void setServiceBegin(String serviceBegin) {
		this.serviceBegin = serviceBegin;
	}

	public String getServiceEnd() {
		return serviceEnd;
	}

	public void setServiceEnd(String serviceEnd) {
		this.serviceEnd = serviceEnd;
	}


}
