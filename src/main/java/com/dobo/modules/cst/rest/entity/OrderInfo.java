package com.dobo.modules.cst.rest.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dobo.modules.cst.entity.detail.CstOrderCostInfo;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;

@SuppressWarnings("serial")
public class OrderInfo implements Serializable {
		
		private String orderId;  //订单编号
		private String orderClass;  //订单类别
		private String orderName;  //订单名称
		private String orderState;  //订单状态
		// 输入项
		private Map<String, Map<String, ProdDetailInfo>> prodDetailMap;
		// 输入项
		private Map<String, List<CstOrderDetailInfo>> prodDetailInfoMap;
		// 输出项
		private Map<String, List<CstOrderCostInfo>> prodCostInfoMap;
		
		public OrderInfo() {}
		
		public OrderInfo(String orderId, String orderClass, String orderName, String orderState, 
				Map<String, List<CstOrderDetailInfo>> detailInfos) {
			this.orderId = orderId;
			this.orderClass = orderClass;
			this.orderName = orderName;
			this.orderState = orderState;
			this.prodDetailInfoMap = detailInfos;
		}
		
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getOrderClass() {
			return orderClass;
		}
		public void setOrderClass(String orderClass) {
			this.orderClass = orderClass;
		}
		public String getOrderName() {
			return orderName;
		}
		public void setOrderName(String orderName) {
			this.orderName = orderName;
		}
		public String getOrderState() {
			return orderState;
		}
		public void setOrderState(String orderState) {
			this.orderState = orderState;
		}

		public Map<String, Map<String, ProdDetailInfo>> getProdDetailMap() {
			return prodDetailMap;
		}

		public void setProdDetailMap(
				Map<String, Map<String, ProdDetailInfo>> prodDetailInfoMap) {
			this.prodDetailMap = prodDetailInfoMap;
		}

		public Map<String, List<CstOrderDetailInfo>> getProdDetailInfoMap() {
			return prodDetailInfoMap;
		}

		public void setProdDetailInfoMap(
				Map<String, List<CstOrderDetailInfo>> prodDetailInfoMap) {
			this.prodDetailInfoMap = prodDetailInfoMap;
		}

		public Map<String, List<CstOrderCostInfo>> getProdCostInfoMap() {
			return prodCostInfoMap;
		}

		public void setProdCostInfoMap(
				Map<String, List<CstOrderCostInfo>> prodCostInfoMap) {
			this.prodCostInfoMap = prodCostInfoMap;
		}
		
}
