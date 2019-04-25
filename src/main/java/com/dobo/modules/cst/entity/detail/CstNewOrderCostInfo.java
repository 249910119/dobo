/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.detail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 资源模型成本Entity
 * @author admin
 * @version 2019-03-26
 */
public class CstNewOrderCostInfo extends DataEntity<CstNewOrderCostInfo> {

	//导出excel字段
	public final static String[] expFieldNames = new String[]{
			"dcPrjId","pdId","prodName","keyId","faultTime","manLine1level1","manLine1level2","manLine1level3","manLine1level4","manLine1level5","manLine1level6",
			"manOutFee","manPm3","manPm4","manPm5",
			"manLine2expert","manCmo","manDelivery","manRisk","manProdline",
			"bakGzcbZy","bakGzcbFb","bakBjrg","bakXmcbcb","bakGpcbcb","bakCkzlcb","bakBccb","bakHsqjyscb","bakGzjfhyscb","bakWysdcb","bakDbyscb","bakFxcbj",
			"mfrName","resourceName","modelgroupName","equipTypeName","beginDate","endDate","packId"
	};
	//导出excel字段标题
	public final static String[] expTitles = new String[]{
			"项目编号","明细标识","服务产品","分类标识","故障次数","人工-一线一级","人工-一线二级","人工-一线三级","人工-一线四级","人工-一线五级","人工-一线六级",
			"外部资源","人工-PM3级","人工-PM4级","人工-PM5级",
			"人工-二线专家","人工-cmo","交付管理","风险","产品管理",
			"备件-故障成本_自有","备件-故障成本_分包","备件-备件人工","备件-项目储备成本","备件-高频储备成本","备件-仓库租赁成本","备件-包材成本","备件-回收取件运输成本","备件-故障件发货运输成本","备件-物业、水电成本","备件-调拨运输成本","备件-风险储备金",
			"厂商","型号","型号组","技术方向","服务开始日期","服务结束日期","包ID"
	};

	//批量插入字段
	public final static String[] insertFieldNames = new String[]{
			"id","dcPrjId","orderId","prodId","pdId","keyId","manLine1level1","manLine1level2","manLine1level3","manLine1level4","manLine1level5","manLine1level6",
			"manOutFee","manPm3","manPm4","manPm5",
			"manLine2expert","manCmo","manDelivery","manRisk","manProdline",
			"bakGzcbZy","bakGzcbFb","bakBjrg","bakXmcbcb","bakGpcbcb",
			"bakCkzlcb","bakBccb","bakHsqjyscb","bakGzjfhyscb","bakWysdcb","bakDbyscb","bakFxcbj",
			"status","mfrName","resourceName","modelgroupName","equipTypeName","beginDate","endDate",
			"resourceId","remarks","createBy","createDate","updateBy",
			"updateDate","delFlag"
	};
	
	private static final long serialVersionUID = 1L;
	private String dcPrjId;		// 项目ID
	private String prodId;		// 产品ID
	private String prodName;		// 产品ID
	private String pdId;		// 明细ID
	private String keyId;		// 分类标识
	private BigDecimal faultTime;           // 故障次数
	private BigDecimal manLine1level1;		// 人工-一线一级
	private BigDecimal manLine1level2;		// 人工-一线二级
	private BigDecimal manLine1level3;		// 人工-一线三级
	private BigDecimal manLine1level4;		// 人工-一线四级
	private BigDecimal manLine1level5;		// 人工-一线五级
	private BigDecimal manLine1level6;		// 人工-一线六级
	private BigDecimal manOutFee;		// 外包资源（外援费用）
	private BigDecimal manPm3;		// 人工-PM3级
	private BigDecimal manPm4;		// 人工-PM4级
	private BigDecimal manPm5;		// 人工-PM5级
	private BigDecimal manLine2expert;		// 人工-二线专家
	private BigDecimal manCmo;		// 人工-cmo
	private BigDecimal manDelivery = BigDecimal.ZERO;		// 交付管理人工包
	private BigDecimal manRisk = BigDecimal.ZERO;		// 风险
	private BigDecimal manProdline = BigDecimal.ZERO;		// 产品线管理
	private BigDecimal bakGzcbZy;		// 备件-故障成本_自有
	private BigDecimal bakGzcbFb;		// 备件-故障成本_合作
	private BigDecimal bakBjrg;		    // 备件-备件人工
	private BigDecimal bakXmcbcb;		// 备件-项目储备成本
	private BigDecimal bakGpcbcb;		// 备件-高频储备成本
	private BigDecimal bakCkzlcb;		// 备件-仓库租赁成本
	private BigDecimal bakBccb;		    // 备件-包材成本
	private BigDecimal bakHsqjyscb;		// 备件-回收取件运输成本
	private BigDecimal bakGzjfhyscb;	// 备件-故障件发货运输成本
	private BigDecimal bakWysdcb;		// 备件-物业、水电成本
	private BigDecimal bakDbyscb;		// 备件-调拨运输成本
	private BigDecimal bakFxcbj;		// 备件-风险储备金
	//private BigDecimal bakBackcost;		// 备件-纯备件成本
	//private BigDecimal bakMancost;		// 备件-备件人工成本
	//private BigDecimal bakOpercost;		// 备件-备件运作
	private String status;		// status
	private String mfrName;		// mfr_name
	private String resourceName;		// resource_name
	private String modelgroupName;		// modelgroup_name
	private String equipTypeName;		// equip_type_name
	private String resourceId;		// resource_id
	private Date beginDate;		// 服务开始时间
	private Date endDate;		// 服务结束时间
	private String orderId;		// 订单编号
	private String packId;		// 包ID
	private String city;
	
	public CstNewOrderCostInfo() {
		super();
	}

	public CstNewOrderCostInfo(String id){
		super(id);
	}

	@Length(min=0, max=256, message="项目ID长度必须介于 0 和 256 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}
	
	@Length(min=0, max=32, message="产品ID长度必须介于 0 和 32 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Length(min=0, max=32, message="明细ID长度必须介于 0 和 32 之间")
	public String getPdId() {
		return pdId;
	}

	public void setPdId(String pdId) {
		this.pdId = pdId;
	}
	
	@Length(min=0, max=32, message="分类标识长度必须介于 0 和 32 之间")
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	public BigDecimal getFaultTime() {
		return faultTime;
	}

	public void setFaultTime(BigDecimal faultTime) {
		this.faultTime = faultTime;
	}

	public BigDecimal getManLine1level1() {
		return manLine1level1;
	}

	public void setManLine1level1(BigDecimal manLine1level1) {
		this.manLine1level1 = manLine1level1;
	}
	
	public BigDecimal getManLine1level2() {
		return manLine1level2;
	}

	public void setManLine1level2(BigDecimal manLine1level2) {
		this.manLine1level2 = manLine1level2;
	}
	
	public BigDecimal getManLine1level3() {
		return manLine1level3;
	}

	public void setManLine1level3(BigDecimal manLine1level3) {
		this.manLine1level3 = manLine1level3;
	}
	
	public BigDecimal getManLine1level4() {
		return manLine1level4;
	}

	public void setManLine1level4(BigDecimal manLine1level4) {
		this.manLine1level4 = manLine1level4;
	}
	
	public BigDecimal getManLine1level5() {
		return manLine1level5;
	}

	public void setManLine1level5(BigDecimal manLine1level5) {
		this.manLine1level5 = manLine1level5;
	}
	
	public BigDecimal getManLine1level6() {
		return manLine1level6;
	}

	public void setManLine1level6(BigDecimal manLine1level6) {
		this.manLine1level6 = manLine1level6;
	}
	
	public BigDecimal getManOutFee() {
		return manOutFee;
	}

	public void setManOutFee(BigDecimal manOutFee) {
		this.manOutFee = manOutFee;
	}
	
	public BigDecimal getManPm3() {
		return manPm3;
	}

	public void setManPm3(BigDecimal manPm3) {
		this.manPm3 = manPm3;
	}
	
	public BigDecimal getManPm4() {
		return manPm4;
	}

	public void setManPm4(BigDecimal manPm4) {
		this.manPm4 = manPm4;
	}
	
	public BigDecimal getManPm5() {
		return manPm5;
	}

	public void setManPm5(BigDecimal manPm5) {
		this.manPm5 = manPm5;
	}
	
	public BigDecimal getManLine2expert() {
		return manLine2expert;
	}

	public void setManLine2expert(BigDecimal manLine2expert) {
		this.manLine2expert = manLine2expert;
	}
	
	public BigDecimal getManCmo() {
		return manCmo;
	}

	public void setManCmo(BigDecimal manCmo) {
		this.manCmo = manCmo;
	}
	
	public BigDecimal getManDelivery() {
		return manDelivery;
	}

	public void setManDelivery(BigDecimal manDelivery) {
		this.manDelivery = manDelivery;
	}
	
	public BigDecimal getManRisk() {
		return manRisk;
	}

	public void setManRisk(BigDecimal manRisk) {
		this.manRisk = manRisk;
	}
	
	public BigDecimal getManProdline() {
		return manProdline;
	}

	public void setManProdline(BigDecimal manProdline) {
		this.manProdline = manProdline;
	}
	
	public BigDecimal getBakGzcbZy() {
		return bakGzcbZy;
	}

	public void setBakGzcbZy(BigDecimal bakGzcbZy) {
		this.bakGzcbZy = bakGzcbZy;
	}

	public BigDecimal getBakGzcbFb() {
		return bakGzcbFb;
	}

	public void setBakGzcbFb(BigDecimal bakGzcbFb) {
		this.bakGzcbFb = bakGzcbFb;
	}

	public BigDecimal getBakXmcbcb() {
		return bakXmcbcb;
	}

	public void setBakXmcbcb(BigDecimal bakXmcbcb) {
		this.bakXmcbcb = bakXmcbcb;
	}

	public BigDecimal getBakGpcbcb() {
		return bakGpcbcb;
	}

	public void setBakGpcbcb(BigDecimal bakGpcbcb) {
		this.bakGpcbcb = bakGpcbcb;
	}

	public BigDecimal getBakBjrg() {
		return bakBjrg;
	}

	public void setBakBjrg(BigDecimal bakBjrg) {
		this.bakBjrg = bakBjrg;
	}

	public BigDecimal getBakCkzlcb() {
		return bakCkzlcb;
	}

	public void setBakCkzlcb(BigDecimal bakCkzlcb) {
		this.bakCkzlcb = bakCkzlcb;
	}

	public BigDecimal getBakBccb() {
		return bakBccb;
	}

	public void setBakBccb(BigDecimal bakBccb) {
		this.bakBccb = bakBccb;
	}

	public BigDecimal getBakHsqjyscb() {
		return bakHsqjyscb;
	}

	public void setBakHsqjyscb(BigDecimal bakHsqjyscb) {
		this.bakHsqjyscb = bakHsqjyscb;
	}

	public BigDecimal getBakGzjfhyscb() {
		return bakGzjfhyscb;
	}

	public void setBakGzjfhyscb(BigDecimal bakGzjfhyscb) {
		this.bakGzjfhyscb = bakGzjfhyscb;
	}

	public BigDecimal getBakWysdcb() {
		return bakWysdcb;
	}

	public void setBakWysdcb(BigDecimal bakWysdcb) {
		this.bakWysdcb = bakWysdcb;
	}

	public BigDecimal getBakDbyscb() {
		return bakDbyscb;
	}

	public void setBakDbyscb(BigDecimal bakDbyscb) {
		this.bakDbyscb = bakDbyscb;
	}

	public BigDecimal getBakFxcbj() {
		return bakFxcbj;
	}

	public void setBakFxcbj(BigDecimal bakFxcbj) {
		this.bakFxcbj = bakFxcbj;
	}
	
	@Length(min=0, max=2, message="status长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=128, message="mfr_name长度必须介于 0 和 128 之间")
	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	
	@Length(min=0, max=128, message="resource_name长度必须介于 0 和 128 之间")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	@Length(min=0, max=128, message="modelgroup_name长度必须介于 0 和 128 之间")
	public String getModelgroupName() {
		return modelgroupName;
	}

	public void setModelgroupName(String modelgroupName) {
		this.modelgroupName = modelgroupName;
	}
	
	@Length(min=0, max=128, message="equip_type_name长度必须介于 0 和 128 之间")
	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}
	
	@Length(min=0, max=30, message="resource_id长度必须介于 0 和 30 之间")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, String> getProdMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		
		slaMap.put("RXSA-03-01-01", "故障解决服务");
		slaMap.put("RXSA-03-01-01", "标准硬件保修服务");	
		slaMap.put("RXSA-03-01-01", "标准硬件保修服务-R类");		
		slaMap.put("RXSA-03-01-01", "硬件故障解决服务");	
		slaMap.put("RXSA-03-01-02", "备件支持服务");	
		slaMap.put("RXSA-03-01-03", "硬件人工支持服务");	
		slaMap.put("RXSA-03-02-02", "标准健康检查服务");	
		slaMap.put("RXSA-03-02-02", "健康检查服务");	
		slaMap.put("RXSA-03-02-02", "硬件健康检查服务");	
		slaMap.put("RXSA-03-02-02", "硬件健康检查增强服务");	
		slaMap.put("RXSA-03-02-05", "驻场服务");	
		slaMap.put("RXSA-02-01-03", "系统软件基础服务SP+");	
		slaMap.put("RXSA-02-01-03", "系统软件基础服务SP-");	
		slaMap.put("RXSA-02-01-03", "系统软件高级服务SP");	
		slaMap.put("RXSA-02-01-03", "系统软件高级服务SP");
		slaMap.put("RXSA-02-01-03", "软件运维服务包");
		slaMap.put("RXSA-06-04-01", "搬迁服务");		
		slaMap.put("RXSA-06-04-01", "数据中心迁移");		
		slaMap.put("RXSA-06-04-02", "其他专业服务");	
		
		return slaMap;
	}
	
}