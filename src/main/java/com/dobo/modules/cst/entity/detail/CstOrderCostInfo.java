/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.detail;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;

/**
 * 订单成本明细Entity
 * @author admin
 * @version 2017-01-23
 */
public class CstOrderCostInfo extends DataEntity<CstOrderCostInfo> {

	//导出excel字段
	public final static String[] expFieldNames = new String[]{
			"dcPrjId","pdId","prodId","keyId","manLine1level1","manLine1level2","manLine1level3","manLine1level4","manLine1level5","manLine1level6",
			"manLine1level1Des","manLine1level2Des","manLine1level3Des","manLine1level4Des","manLine1level5Des",
			"manLine1level1Travl","manLine1level2Travl","manLine1level3Travl","manLine1level4Travl","manLine1level5Travl","manLine1level6Travl","manOutFee",
			"manLine2level4","manLine2level5","manLine2level6","manLine3level6",
			"manCmo","manPmo","manZyg","manZkgl","manQygl","manQyglDes","manPm3","manPm4","manPm5",
			"bakGzcbZy","bakGzcbFb","bakXmcbcb","bakGpcbcb","bakBjrgcb","bakZkgl","bakCkzlcb","bakBccb","bakHsqjyscb","bakGzjfhyscb","bakWysdcb","bakDbyscb","bakFxcbj",
			"managerCost","toolCost","riskCost",
			"mfrName","resourceName","modelgroupName","equipTypeName","beginDate","endDate","city"
	};
	//导出excel字段标题
	public final static String[] expTitles = new String[]{
			"项目编号","明细标识","服务产品","分类标识","人工-一线一级","人工-一线二级","人工-一线三级","人工-一线四级","人工-一线五级","人工-一线六级",
			"人工-一线一级-折减","人工-一线二级-折减","人工-一线三级-折减","人工-一线四级-折减","人工-一线五级-折减",
			"人工-一线一级-差旅","人工-一线二级-差旅","人工-一线三级-差旅","人工-一线四级-差旅","人工-一线五级-差旅","人工-一线六级-差旅","外部资源",
			"人工-二线四级","人工-二线五级","人工-二线六级","人工-三线六级",
			"人工-cmo","人工-pmo","人工-资源岗","人工-总控管理","人工-区域管理","人工-区域管理_折减","人工-PM3级","人工-PM4级","人工-PM5级",
			"备件-故障成本_自有","备件-故障成本_分包","备件-项目储备成本","备件-高频储备成本","备件-备件人工成本","备件-总控管理","备件-仓库租赁成本","备件-包材成本","备件-回收取件运输成本","备件-故障件发货运输成本","备件-物业、水电成本","备件-调拨运输成本","备件-风险储备金",
			"管理","工具","风险",
			"厂商","型号","型号组","技术方向","服务开始日期","服务结束日期","城市"
	};

	//批量插入字段
	public final static String[] insertFieldNames = new String[]{
			"id","dcPrjId","orderId","prodId","pdId","keyId","manLine1level1","manLine1level2","manLine1level3","manLine1level4","manLine1level5",
			"manLine1level6","manLine1level1Des","manLine1level2Des","manLine1level3Des","manLine1level4Des",
			"manLine1level5Des","manLine1level1Travl","manLine1level2Travl","manLine1level3Travl","manLine1level4Travl",
			"manLine1level5Travl","manLine1level6Travl","manOutFee","manLine2level4","manLine2level5","manLine2level6","manLine3level6","manCmo","manPmo",
			"manZyg","manZkgl","manQygl","manQyglDes","manPm3","manPm4","manPm5","bakGzcbZy","bakGzcbFb","bakXmcbcb","bakGpcbcb",
			"bakBjrgcb","bakZkgl","bakCkzlcb","bakBccb","bakHsqjyscb","bakGzjfhyscb","bakWysdcb","bakDbyscb","bakFxcbj",
			"managerCost","toolCost","riskCost","status","mfrName","resourceName","modelgroupName","equipTypeName","beginDate","endDate",
			"resourceId","remarks","createBy","createDate","updateBy",
			"updateDate","delFlag"
	};
	
	private static final long serialVersionUID = 1L;
	private String dcPrjId;		// 项目ID
	private String orderId;		// 订单编号
	private String prodId;		// 产品ID
	private String pdId;		// 明细ID
	private String keyId;		// 分类标识
	private BigDecimal manLine1level1;		// 人工-一线一级
	private BigDecimal manLine1level2;		// 人工-一线二级
	private BigDecimal manLine1level3;		// 人工-一线三级
	private BigDecimal manLine1level4;		// 人工-一线四级
	private BigDecimal manLine1level5;		// 人工-一线五级
	private BigDecimal manLine1level6;		// 人工-一线六级
	private BigDecimal manLine1level1Des;		// 人工-一线一级-折减
	private BigDecimal manLine1level2Des;		// 人工-一线二级-折减
	private BigDecimal manLine1level3Des;		// 人工-一线三级-折减
	private BigDecimal manLine1level4Des;		// 人工-一线四级-折减
	private BigDecimal manLine1level5Des;		// 人工-一线五级-折减
	private BigDecimal manLine1level1Travl;		// 人工-一线一级-差旅
	private BigDecimal manLine1level2Travl;		// 人工-一线二级-差旅
	private BigDecimal manLine1level3Travl;		// 人工-一线三级-差旅
	private BigDecimal manLine1level4Travl;		// 人工-一线四级-差旅
	private BigDecimal manLine1level5Travl;		// 人工-一线五级-差旅
	private BigDecimal manLine1level6Travl;		// 人工-一线六级-差旅
	private BigDecimal manOutFee;		// 外部资源（单次、先行支持外援费用）
	private BigDecimal manLine2level4;		// 人工-二线四级
	private BigDecimal manLine2level5;		// 人工-二线五级
	private BigDecimal manLine2level6;		// 人工-二线六级
	private BigDecimal manLine3level6;		// 人工-三线六级
	private BigDecimal manCmo;		// 人工-cmo
	private BigDecimal manPmo;		// 人工-pmo
	private BigDecimal manZyg;		// 人工-资源岗
	private BigDecimal manZkgl;		// 人工-总控管理
	private BigDecimal manQygl;		// 人工-区域管理
	private BigDecimal manQyglDes;		// 人工-区域管理_折减
	private BigDecimal manPm3;		// 人工-PM3级
	private BigDecimal manPm4;		// 人工-PM4级
	private BigDecimal manPm5;		// 人工-PM5级
	private BigDecimal bakGzcbZy;		// 备件-故障成本_自有
	private BigDecimal bakGzcbFb;		// 备件-故障成本_分包
	private BigDecimal bakXmcbcb;		// 备件-项目储备成本
	private BigDecimal bakGpcbcb;		// 备件-高频储备成本
	private BigDecimal bakBjrgcb;		// 备件-备件人工成本
	private BigDecimal bakZkgl;		// 备件-总控管理
	private BigDecimal bakCkzlcb;		// 备件-仓库租赁成本
	private BigDecimal bakBccb;		// 备件-包材成本
	private BigDecimal bakHsqjyscb;		// 备件-回收取件运输成本
	private BigDecimal bakGzjfhyscb;		// 备件-故障件发货运输成本
	private BigDecimal bakWysdcb;		// 备件-物业、水电成本
	private BigDecimal bakDbyscb;		// 备件-调拨运输成本
	private BigDecimal bakFxcbj;		// 备件-风险储备金
	private BigDecimal managerCost = BigDecimal.ZERO;		// 管理
	private BigDecimal toolCost = BigDecimal.ZERO;		// 工具
	private BigDecimal riskCost = BigDecimal.ZERO;		// 风险
	private String status;		// status
	private String mfrName;		//厂商
	private String resourceName;	//型号
	private String modelgroupName;  //型号组
	private String equipTypeName;  //技术方向
	private String resourceId;  //资源ID
	private Date beginDate;	//  服务开始日期
	private Date endDate;	//  服务结束日期
	private String city;
	
	public CstOrderCostInfo() {
		super();
	}

	public CstOrderCostInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="项目ID长度必须介于 0 和 32 之间")
	public String getDcPrjId() {
		return dcPrjId;
	}

	public void setDcPrjId(String dcPrjId) {
		this.dcPrjId = dcPrjId;
	}

	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 15 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Length(min=0, max=32, message="产品ID长度必须介于 0 和 32 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=0, max=32, message="明细ID长度必须介于 0 和 32 之间")
	public String getPdId() {
		return pdId;
	}

	public void setPdId(String pdId) {
		this.pdId = pdId;
	}
	
	@Length(min=1, max=32, message="分类标识长度必须介于 1 和 32 之间")
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
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
	
	public BigDecimal getManLine1level1Des() {
		return manLine1level1Des;
	}

	public void setManLine1level1Des(BigDecimal manLine1level1Des) {
		this.manLine1level1Des = manLine1level1Des;
	}
	
	public BigDecimal getManLine1level2Des() {
		return manLine1level2Des;
	}

	public void setManLine1level2Des(BigDecimal manLine1level2Des) {
		this.manLine1level2Des = manLine1level2Des;
	}
	
	public BigDecimal getManLine1level3Des() {
		return manLine1level3Des;
	}

	public void setManLine1level3Des(BigDecimal manLine1level3Des) {
		this.manLine1level3Des = manLine1level3Des;
	}
	
	public BigDecimal getManLine1level4Des() {
		return manLine1level4Des;
	}

	public void setManLine1level4Des(BigDecimal manLine1level4Des) {
		this.manLine1level4Des = manLine1level4Des;
	}
	
	public BigDecimal getManLine1level5Des() {
		return manLine1level5Des;
	}

	public void setManLine1level5Des(BigDecimal manLine1level5Des) {
		this.manLine1level5Des = manLine1level5Des;
	}
	
	public BigDecimal getManLine1level1Travl() {
		return manLine1level1Travl;
	}

	public void setManLine1level1Travl(BigDecimal manLine1level1Travl) {
		this.manLine1level1Travl = manLine1level1Travl;
	}
	
	public BigDecimal getManLine1level2Travl() {
		return manLine1level2Travl;
	}

	public void setManLine1level2Travl(BigDecimal manLine1level2Travl) {
		this.manLine1level2Travl = manLine1level2Travl;
	}
	
	public BigDecimal getManLine1level3Travl() {
		return manLine1level3Travl;
	}

	public void setManLine1level3Travl(BigDecimal manLine1level3Travl) {
		this.manLine1level3Travl = manLine1level3Travl;
	}
	
	public BigDecimal getManLine1level4Travl() {
		return manLine1level4Travl;
	}

	public void setManLine1level4Travl(BigDecimal manLine1level4Travl) {
		this.manLine1level4Travl = manLine1level4Travl;
	}
	
	public BigDecimal getManLine1level5Travl() {
		return manLine1level5Travl;
	}

	public void setManLine1level5Travl(BigDecimal manLine1level5Travl) {
		this.manLine1level5Travl = manLine1level5Travl;
	}
	
	public BigDecimal getManLine2level4() {
		return manLine2level4;
	}

	public void setManLine2level4(BigDecimal manLine2level4) {
		this.manLine2level4 = manLine2level4;
	}
	
	public BigDecimal getManLine2level5() {
		return manLine2level5;
	}

	public void setManLine2level5(BigDecimal manLine2level5) {
		this.manLine2level5 = manLine2level5;
	}
	
	public BigDecimal getManLine2level6() {
		return manLine2level6;
	}

	public void setManLine2level6(BigDecimal manLine2level6) {
		this.manLine2level6 = manLine2level6;
	}
	
	public BigDecimal getManLine3level6() {
		return manLine3level6;
	}

	public void setManLine3level6(BigDecimal manLine3level6) {
		this.manLine3level6 = manLine3level6;
	}
	
	public BigDecimal getManCmo() {
		return manCmo;
	}

	public void setManCmo(BigDecimal manCmo) {
		this.manCmo = manCmo;
	}
	
	public BigDecimal getManPmo() {
		return manPmo;
	}

	public void setManPmo(BigDecimal manPmo) {
		this.manPmo = manPmo;
	}
	
	public BigDecimal getManZyg() {
		return manZyg;
	}

	public void setManZyg(BigDecimal manZyg) {
		this.manZyg = manZyg;
	}
	
	public BigDecimal getManZkgl() {
		return manZkgl;
	}

	public void setManZkgl(BigDecimal manZkgl) {
		this.manZkgl = manZkgl;
	}
	
	public BigDecimal getManQygl() {
		return manQygl;
	}

	public void setManQygl(BigDecimal manQygl) {
		this.manQygl = manQygl;
	}
	
	public BigDecimal getManQyglDes() {
		return manQyglDes;
	}

	public void setManQyglDes(BigDecimal manQyglDes) {
		this.manQyglDes = manQyglDes;
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
	
	public BigDecimal getBakBjrgcb() {
		return bakBjrgcb;
	}

	public void setBakBjrgcb(BigDecimal bakBjrgcb) {
		this.bakBjrgcb = bakBjrgcb;
	}
	
	public BigDecimal getBakZkgl() {
		return bakZkgl;
	}

	public void setBakZkgl(BigDecimal bakZkgl) {
		this.bakZkgl = bakZkgl;
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

	public BigDecimal getManagerCost() {
		return managerCost;
	}

	public void setManagerCost(BigDecimal managerCost) {
		this.managerCost = managerCost;
	}
	
	public BigDecimal getToolCost() {
		return toolCost;
	}

	public void setToolCost(BigDecimal toolCost) {
		this.toolCost = toolCost;
	}
	
	public BigDecimal getRiskCost() {
		return riskCost;
	}

	public void setRiskCost(BigDecimal riskCost) {
		this.riskCost = riskCost;
	}
	
	@Length(min=0, max=2, message="status长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMfrName() {
		return mfrName;
	}

	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getModelgroupName() {
		return modelgroupName;
	}

	public void setModelgroupName(String modelgroupName) {
		this.modelgroupName = modelgroupName;
	}

	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getManLine1level6() {
		return manLine1level6;
	}

	public void setManLine1level6(BigDecimal manLine1level6) {
		this.manLine1level6 = manLine1level6;
	}

	public BigDecimal getManLine1level6Travl() {
		return manLine1level6Travl;
	}

	public void setManLine1level6Travl(BigDecimal manLine1level6Travl) {
		this.manLine1level6Travl = manLine1level6Travl;
	}

	public BigDecimal getManOutFee() {
		return manOutFee;
	}

	public void setManOutFee(BigDecimal manOutFee) {
		this.manOutFee = manOutFee;
	}
	
}