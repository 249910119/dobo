/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.detail;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.dobo.common.persistence.DataEntity;
import com.dobo.common.utils.DateUtils;
import com.dobo.modules.cst.common.CacheDataUtils;

/**
 * 订单明细信息Entity
 * @author admin
 * @version 2016-12-02
 */
public class CstOrderDetailInfo extends DataEntity<CstOrderDetailInfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// order_id 订单编号
	private String prodName;		// prod_name 服务产品
	private String detailId;		// detail_id 明细标识
	private String dcPrjId;  //项目编号
	private String resourceId;
	private String mfrName;		// 厂商
	private String resourceName;	// 型号
	private String detailModel;  //型号组
	private String city;		// city 地市
	private String slaName;		// sla_name 服务级别
	private String amount;		// 资源数量
	private Date beginDate;		// 服务开始时间
	private Date endDate;		// 服务结束时间
	private Long checkN;		// 现场巡检
	private Long checkF;		// 远程巡检
	private Long checkD;		// 深度巡检
	//private String prodServiceModel;	// 产品服务方式
	private String byFee;	 // 搬运费
	private String clzsFee;	  // 差旅/住宿费
	private String manCost;	  // PMANCOST	   人工成本-日常
	private String backCost;   // PBACKCOST	     备件成本-备件
	//private String subCost;	 // PBACKCOST	     备件成本-备件
	private String otherCost;	// OTHERCOST	其他工程成本
	private String delivCost;	// DELIVCOST	交付管理
	private String prodCost;	// PRODCOST	       产品管理
	private String riskCost;	// PRISKCOST	风险成本
	//private String finalManCost;	// 核定人工成本
	//private String finalBackCost;	// 核定备件成本
	//private String finalRiskAbilityCost;	// 核定风险及能力成本
	//private String finalUrgeCost;	// 核定人工激励成本
	private String urgent;		// 驻场人员及时性
	private String workkindScale;		// 驻场复用比例
	private String manType;     // 驻场人员类型
	private String status;		// status
	
	private String prodId; 
	private String cityId; 
	private String slaId; 
	private String cycle; 
	
	public CstOrderDetailInfo() {
		super();
	}

	public CstOrderDetailInfo(String id){
		super(id);
	}

	@Length(min=1, max=32, message="order_id长度必须介于 1 和 32 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=32, message="detail_id长度必须介于 0 和 32 之间")
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Length(min=0, max=32, message="厂商长度必须介于 0 和 32 之间")
	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	
	@Length(min=0, max=10, message="amount长度必须介于 0 和 10 之间")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=64, message="型号长度必须介于 0 和 64 之间")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Length(min=1, max=32, message="prod_id长度必须介于 1 和 32 之间")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Length(min=0, max=10, message="city_id长度必须介于 0 和 10 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Length(min=0, max=10, message="sla_id长度必须介于 0 和 10 之间")
	public String getSlaName() {
		return slaName;
	}

	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@Length(min=0, max=2, message="status长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Long getCheckN() {
		return checkN == null ? 0 : checkN;
	}

	public void setCheckN(Long checkN) {
		this.checkN = checkN;
	}
	
	public Long getCheckF() {
		return checkF == null ? 0 : checkF;
	}

	public void setCheckF(Long checkF) {
		this.checkF = checkF;
	}
	
	public Long getCheckD() {
		return checkD == null ? 0 : checkD;
	}

	public void setCheckD(Long checkD) {
		this.checkD = checkD;
	}
	
	public String getByFee() {
		return byFee;
	}

	public void setByFee(String byFee) {
		this.byFee = byFee;
	}

	public String getClzsFee() {
		return clzsFee = clzsFee == null ? "0" : clzsFee;
	}

	public void setClzsFee(String clzsFee) {
		this.clzsFee = clzsFee;
	}

	public String getManCost() {
		return manCost;
	}

	public void setManCost(String manCost) {
		this.manCost = manCost;
	}

	public String getBackCost() {
		return backCost;
	}

	public void setBackCost(String backCost) {
		this.backCost = backCost;
	}

	public String getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(String otherCost) {
		this.otherCost = otherCost;
	}

	public String getDelivCost() {
		return delivCost;
	}

	public void setDelivCost(String delivCost) {
		this.delivCost = delivCost;
	}

	public String getProdCost() {
		return prodCost;
	}

	public void setProdCost(String prodCost) {
		this.prodCost = prodCost;
	}

	public String getRiskCost() {
		return riskCost;
	}

	public void setRiskCost(String riskCost) {
		this.riskCost = riskCost;
	}
	
	@Length(min=0, max=20, message="驻场人员及时性长度必须介于 0 和 20 之间")
	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	@Length(min=0, max=20, message="驻场复用比例长度必须介于 0 和 20 之间")
	public String getWorkkindScale() {
		return workkindScale;
	}

	public void setWorkkindScale(String workkindScale) {
		this.workkindScale = workkindScale;
	}
	
	public String getManType() {
		return manType;
	}

	public void setManType(String manType) {
		this.manType = manType;
	}

	public String getProdId() {
		if(prodName != null && !"".equals(prodName.trim())) {
			prodId = getProdMap().get(prodName.trim());
		}
		return prodId;
	}

	public String getSlaId() {
		if(slaName != null && !"".equals(slaName.trim())) {
			slaId = getSlaMap().get(slaName.trim());
		}
		return slaId;
	}
	
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}

	public String getCityId() {
		if(city != null && !"".equals(city.trim())) {
			cityId = CacheDataUtils.getCstManCityParaTestMap().get(city.trim()).getCityId();
		}
		return cityId;
	}

	public String getUrgentId() {
		if(urgent != null && !"".equals(urgent.trim())) {
			return getUrgentMap().get(urgent.trim());
		} else {
			return null;
		}
	}

	public String getCycle() {
		if(this.getBeginDate() != null && this.getEndDate() != null) {
			cycle = DateUtils.getDistanceOfTwoDate(this.getBeginDate(), this.getEndDate()) + "";
		}
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}

	public String getDetailModel() {
		return detailModel;
	}

	public void setDetailModel(String detailModel) {
		this.detailModel = detailModel;
	}
	
	public Map<String, String> getSlaMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		/**
		 * A+	高级服务A	1级，2级空着，3级配比除填写1,2,3级的总合
			B+	标准服务B	按对应级别配比
			标准服务B	标准服务B	按对应级别配比
			高级服务A	高级服务A	1级，2级空着，3级配比除填写1,2,3级的总合
			基础服务B	标准服务B	按对应级别配比
			基础服务C	标准服务B	按对应级别配比
			基础服务D	标准服务B	按对应级别配比
			特惠服务D	标准服务B	按对应级别配比
		 */
		/*slaMap.put("A+（全覆盖备件）", "SLA_DEVICE_A+");	
		slaMap.put("A+（标准备件）", "SLA_DEVICE_A+");	
		slaMap.put("A+", "SLA_DEVICE_A+");	
		slaMap.put("B+", "SLA_DEVICE_B+");	
		slaMap.put("-", "SLA_DEVICE_B");	
		slaMap.put("基础服务C", "SLA_DEVICE_C");	
		slaMap.put("特惠服务D", "SLA_DEVICE_D");*/	
		slaMap.put("A+（全覆盖备件）", "SLA_DEVICE_A");	
		slaMap.put("A+（标准备件）", "SLA_DEVICE_A");	
		slaMap.put("A+", "SLA_DEVICE_A");	
		slaMap.put("B+", "SLA_DEVICE_B");	
		slaMap.put("-", "SLA_DEVICE_B");	
		slaMap.put("基础服务C", "SLA_DEVICE_B");	
		slaMap.put("特惠服务D", "SLA_DEVICE_B");
		slaMap.put("高级服务A", "SLA_DEVICE_A");	
		slaMap.put("标准服务B", "SLA_DEVICE_B");	
		slaMap.put("高级服务SP", "SLA_SOFT_A");	
		slaMap.put("基础服务SP+", "SLA_SOFT_B");	
		slaMap.put("基础服务SP-", "SLA_SOFT_C");		
		slaMap.put("高级SP+", "SLA_SOFT_D");	
		slaMap.put("标准SP", "SLA_SOFT_E");		
		
		return slaMap;
	}
	
	public Map<String, String> getProdMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		
		slaMap.put("故障解决服务", "RXSA-03-01-01");
		slaMap.put("标准硬件保修服务", "RXSA-03-01-01");	
		slaMap.put("标准硬件保修服务-R类", "RXSA-03-01-01");		
		slaMap.put("硬件故障解决服务", "RXSA-03-01-01");	
		slaMap.put("备件支持服务", "RXSA-03-01-02");	
		slaMap.put("硬件人工支持服务", "RXSA-03-01-03");	
		slaMap.put("标准健康检查服务", "RXSA-03-02-02");	
		slaMap.put("健康检查服务", "RXSA-03-02-02");	
		slaMap.put("硬件健康检查服务", "RXSA-03-02-02");	
		slaMap.put("硬件健康检查增强服务", "RXSA-03-02-02");	
		slaMap.put("驻场服务", "RXSA-03-02-05");		
		slaMap.put("系统软件基础服务SP+", "RXSA-02-01-03");
		slaMap.put("系统软件基础服务SP-", "RXSA-02-01-03");
		slaMap.put("系统软件高级服务SP", "RXSA-02-01-03");
		slaMap.put("软件运维服务包", "RXSA-02-01-03");
		slaMap.put("调整成本类", "RXSA-TEMP");		
		slaMap.put("HA软件保修服务", "RXSA-TEMP");		
		slaMap.put("HA软件健康检查服务", "RXSA-TEMP");		
		slaMap.put("系统软件故障解决服务", "RXSA-TEMP");		
		slaMap.put("系统软件健康检查服务", "RXSA-TEMP");		
		slaMap.put("先行支持服务", "RXSA-TEMP");		
		slaMap.put("人天类服务", "RXSA-TEMP");		
		slaMap.put("调整类", "RXSA-TEMP");		
		slaMap.put("搬迁服务", "RXSA-06-04-01");		
		slaMap.put("数据中心迁移", "RXSA-06-04-01");		
		slaMap.put("其他专业服务", "RXSA-06-04-02");	
		
		return slaMap;
	}
	
	private Map<String, String> getUrgentMap() {
		Map<String, String> urgentMap = new HashMap<String, String>();
		urgentMap.put("正常需求(默认)", "URGENT1");	
		urgentMap.put("人员紧急到场", "URGENT2");	
		
		return urgentMap;
	}
	
}