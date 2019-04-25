package com.dobo.modules.cst.rest.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("serial")
public class ProdDetailInfo implements Serializable {
		
		private String detailId;  //明细标识
		private String dcPrjId;  //项目编号
		private String orderId;  //订单编号
		private String resourceId; //资源ID
		private String detailType;  //类型
		private String mfrName;  //厂商
		private String resourceName;  //型号
		private String detailModel;  //型号组
		private String equipType;  //技术方向
		private String prodName;  
		/*private String amount;  //数量
		private String serviceTime;  //现场巡检次数
		private String slaId;  //SLA
		private String regionId;  //城市
		private String serviceCycle;  //服务周期(天)
*/		
		private Date serviceBegin;  //开始时间
		private Date serviceEnd;  //截止时间
		//输入参数集合，
		/**
		 * 如：资源ID-resourceId,数量-amount，SLA-SLA，城市-SECTION，服务周期(天)-BUYPRDMON 人员及时性-URGENT
		 * 现场巡检-BUYCHECKN 远程巡检次数-BUYFARCHK 深度巡检-BUYDEPCHK
		 */
		private Map<String, String> inParaMap;
		
		/**
		 *  巡检拆分到月份中的台次数 <巡检类别, Map<年月, 台次>>
		 */
		private Map<String, Map<String, Double>> monthCheckNumMap;
		
		public ProdDetailInfo() {}
		
		public ProdDetailInfo(String detailId, String dcPrjId, String mfrName, String detailType, String detailModel, String resourceId, Map<String, String> inParaMap) {
			this.detailId = detailId;
			this.dcPrjId = dcPrjId;
			this.resourceId = resourceId;
			this.mfrName = mfrName;
			this.detailType = detailType;
			this.detailModel = detailModel;
			/*this.amount = amount;
			this.telServiceTime = telServiceTime;
			this.serviceTime = serviceTime;
			this.slaId = slaId;
			this.regionId = regionId;
			this.serviceCycle = serviceCycle;
			this.serviceBegin = serviceBegin;
			this.serviceEnd = serviceEnd;*/
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

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getMfrName() {
			return mfrName;
		}
		
		public void setMfrName(String mfrName) {
			this.mfrName = mfrName;
		}
		
		public String getDetailType() {
			return detailType;
		}
		
		public void setDetailType(String detailType) {
			this.detailType = detailType;
		}
		
		public String getDetailModel() {
			return detailModel;
		}
		
		public void setDetailModel(String detailModel) {
			this.detailModel = detailModel;
		}

		public String getEquipType() {
			return equipType;
		}

		public void setEquipType(String equipType) {
			this.equipType = equipType;
		}

		public String getProdName() {
			return prodName;
		}

		public void setProdName(String prodName) {
			this.prodName = prodName;
		}

		public String getResourceId() {
			return resourceId;
		}

		public void setResourceId(String resourceId) {
			this.resourceId = resourceId;
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
		
		public Map<String, String> getInParaMap() {
			return inParaMap;
		}

		public void setInParaMap(Map<String, String> inParaMap) {
			this.inParaMap = inParaMap;
		}

		public Map<String, Map<String, Double>> getMonthCheckNumMap() {
			return monthCheckNumMap;
		}

		public void setMonthCheckNumMap(Map<String, Map<String, Double>> monthCheckNumMap) {
			this.monthCheckNumMap = monthCheckNumMap;
		}

		public String getResourceName() {
			return resourceName;
		}

		public void setResourceName(String resourceName) {
			this.resourceName = resourceName;
		}
		
		/*public String getTelServiceTime() {
			return telServiceTime;
		}
		
		public String getAmount() {
			return amount;
		}
		
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
		public void setTelServiceTime(String telServiceTime) {
			this.telServiceTime = telServiceTime;
		}
		
		public String getServiceTime() {
			return serviceTime;
		}
		
		public void setServiceTime(String serviceTime) {
			this.serviceTime = serviceTime;
		}
		
		public String getSlaId() {
			return slaId;
		}
		
		public void setSlaId(String slaId) {
			this.slaId = slaId;
		}
		
		public String getRegionId() {
			return regionId;
		}
		
		public void setRegionId(String regionId) {
			this.regionId = regionId;
		}
		
		public String getServiceCycle() {
			return serviceCycle;
		}
		
		public void setServiceCycle(String serviceCycle) {
			this.serviceCycle = serviceCycle;
		}
		*/
		
}
