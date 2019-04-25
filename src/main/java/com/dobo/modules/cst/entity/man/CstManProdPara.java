/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.entity.man;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 服务产品系数定义表Entity
 * @author admin
 * @version 2016-11-08
 */
public class CstManProdPara extends DataEntity<CstManProdPara> {
	
	private static final long serialVersionUID = 1L;
	private String prodId;		// 服务产品编号
	private String prodName;		// 服务产品
	private String costType;		// 成本分类
	private Double line2And3MgrScale;  // 二/三线人工系数_费用
	private Double cmoMgrScale;  // CMO人工系数_费用
	private Double deliveryMgrScale;  // 交付管理系数_费用
	private Double riskMgrScale;  // 风险系数_费用
	private Double productLineMgrScale;  // 产品线管理系数_费用
	private Double qyMgrScale;		// 管理系数
	private Double zkMgrScale;		// 管理系数
	private Double mgrScale;		// 管理系数
	private Double toolScale;		//工具系数
	private Double riskScale;		//风险系数
	private String status;		// 状态
	private String remarkId;		// 备注
	private String preStatus;		// 更新前状态
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记
	public CstManProdPara() {
		super();
	}

	public CstManProdPara(String id){
		super(id);
	}

	@Length(min=1, max=60, message="服务产品编号长度必须介于 1 和 60 之间")
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	@Length(min=1, max=60, message="服务产品长度必须介于 1 和 60 之间")
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public Double getLine2And3MgrScale() {
		return line2And3MgrScale;
	}

	public void setLine2And3MgrScale(Double line2And3MgrScale) {
		this.line2And3MgrScale = line2And3MgrScale;
	}

	public Double getCmoMgrScale() {
		return cmoMgrScale;
	}

	public void setCmoMgrScale(Double cmoMgrScale) {
		this.cmoMgrScale = cmoMgrScale;
	}

	public Double getDeliveryMgrScale() {
		return deliveryMgrScale;
	}

	public void setDeliveryMgrScale(Double deliveryMgrScale) {
		this.deliveryMgrScale = deliveryMgrScale;
	}

	public Double getRiskMgrScale() {
		return riskMgrScale;
	}

	public void setRiskMgrScale(Double riskMgrScale) {
		this.riskMgrScale = riskMgrScale;
	}

	public Double getProductLineMgrScale() {
		return productLineMgrScale;
	}

	public void setProductLineMgrScale(Double productLineMgrScale) {
		this.productLineMgrScale = productLineMgrScale;
	}

	@NotNull(message="管理系数不能为空")
	public Double getQyMgrScale() {
		return qyMgrScale;
	}

	public void setQyMgrScale(Double qyMgrScale) {
		this.qyMgrScale = qyMgrScale;
	}
	
	@NotNull(message="管理系数不能为空")
	public Double getZkMgrScale() {
		return zkMgrScale;
	}

	public void setZkMgrScale(Double zkMgrScale) {
		this.zkMgrScale = zkMgrScale;
	}
	
	@NotNull(message="管理系数不能为空")
	public Double getToolScale() {
		return toolScale;
	}

	public void setToolScale(Double toolScale) {
		this.toolScale = toolScale;
	}

	@NotNull(message="管理系数不能为空")
	public Double getRiskScale() {
		return riskScale;
	}

	public void setRiskScale(Double riskScale) {
		this.riskScale = riskScale;
	}

	@NotNull(message="管理系数不能为空")
	public Double getMgrScale() {
		return mgrScale;
	}

	public void setMgrScale(Double mgrScale) {
		this.mgrScale = mgrScale;
	}
	
	@Length(min=1, max=2, message="状态长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=128, message="备注长度必须介于 0 和 128 之间")
	public String getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(String remarkId) {
		this.remarkId = remarkId;
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
	
	@Length(min=0, max=1, message="保存标记长度必须介于 0 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
}