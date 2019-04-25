/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.rest.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务费用计算对应订单信息Entity
 * @author admin
 * @version 2016-11-05
 */
@SuppressWarnings("serial")
public class FcActualOrderInfoRest implements Serializable {
	
	public final static String exportName = "订单信息";
	
	//导出字段
	public final static String[] exportfieldNames = {
			"erpxmbh","saleOrg","fstSvcType", "sndSvcType", "startDate", "endDate", "ownCost", "tgCost", "subCost", 
			"saleOrgName","caclProdCost","borderStartDate","borderEndDate","borderCost","remark"};
	
	//导出字段对应标题
	public final static String[] exportfieldTitles = {
			"财务项目编号","事业部","产品大类","产品小类","服务期开始","服务期结束","自有成本", "托管成本","分包成本", 
			"新年事业部名称", "参与计算产品成本", "边界服务期开始", "边界服务期结束", "边界成本", "备注"};

	private String erpxmbh;			// 项目编号
	private String saleOrg;			// 事业部名称
	private String fstSvcType;		// 产品线大类
	private String sndSvcType;		// 产品线小类
	private Date startDate;			// 服务期开始
	private Date endDate;			// 服务期结束
	private Double ownCost;			// 自有产品成本
	private Double tgCost;			// 托管成本
	private Double subCost;			// 指定分包成本
	private String saleOrgName;		// 新事业部名称
	private Double caclProdCost;	// 计算实际费用时的产品成本
	private Date borderStartDate;	// 边界服务期开始
	private Date borderEndDate;		// 边界服务期结束
	private Double borderCost;		// 支付成本
	private String remark;			// 备注

	private String wbmOrderid;		// wbm订单：多个逗号分割
	private String dataType;		// 数据类型：PROJECT/ORDER
	
	public FcActualOrderInfoRest() {
	}

	public Double getSumCost() {
		double own = this.ownCost == null ? 0L : this.ownCost;
		double tg = this.tgCost == null ? 0L : this.tgCost;
		double sub = this.subCost == null ? 0L : this.subCost;
		return own+tg+sub;
	}

	public String getErpxmbh() {
		return erpxmbh;
	}

	public void setErpxmbh(String erpxmbh) {
		this.erpxmbh = erpxmbh;
	}

	public String getSaleOrg() {
		return saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}

	public String getFstSvcType() {
		return fstSvcType;
	}

	public void setFstSvcType(String fstSvcType) {
		this.fstSvcType = fstSvcType;
	}

	public String getSndSvcType() {
		return sndSvcType;
	}

	public void setSndSvcType(String sndSvcType) {
		this.sndSvcType = sndSvcType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getOwnCost() {
		return ownCost;
	}

	public void setOwnCost(Double ownCost) {
		this.ownCost = ownCost;
	}

	public Double getTgCost() {
		return tgCost;
	}

	public void setTgCost(Double tgCost) {
		this.tgCost = tgCost;
	}

	public Double getSubCost() {
		return subCost;
	}

	public void setSubCost(Double subCost) {
		this.subCost = subCost;
	}

	public String getSaleOrgName() {
		return saleOrgName;
	}

	public void setSaleOrgName(String saleOrgName) {
		this.saleOrgName = saleOrgName;
	}

	public Double getCaclProdCost() {
		return caclProdCost;
	}

	public void setCaclProdCost(Double caclProdCost) {
		this.caclProdCost = caclProdCost;
	}

	public Date getBorderStartDate() {
		return borderStartDate;
	}

	public void setBorderStartDate(Date borderStartDate) {
		this.borderStartDate = borderStartDate;
	}

	public Date getBorderEndDate() {
		return borderEndDate;
	}

	public void setBorderEndDate(Date borderEndDate) {
		this.borderEndDate = borderEndDate;
	}

	public Double getBorderCost() {
		return borderCost;
	}

	public void setBorderCost(Double borderCost) {
		this.borderCost = borderCost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static String getExportname() {
		return exportName;
	}

	public static String[] getExportfieldnames() {
		return exportfieldNames;
	}

	public static String[] getExportfieldtitles() {
		return exportfieldTitles;
	}

	public String getWbmOrderid() {
		return wbmOrderid;
	}

	public void setWbmOrderid(String wbmOrderid) {
		this.wbmOrderid = wbmOrderid;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	 
}