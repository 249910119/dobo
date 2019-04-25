package com.dobo.modules.cst.entity.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class CstCaseDetailInfoBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String onceNum;		// 单次报价号
	private String caseId;		// 事件ID
	private String priceType;		// 1人员，2备件
	private BigDecimal gzLine1Level2 = BigDecimal.ZERO; //	故障1线2级工作量
	private BigDecimal gzLine1Level3 = BigDecimal.ZERO; //	故障1线3级工作量
	private BigDecimal gzLine1Level4 = BigDecimal.ZERO; //	故障1线4级工作量
	private BigDecimal gzLine1Level6 = BigDecimal.ZERO; //	故障1线6级工作量
	private BigDecimal gztfLine1Level2 = BigDecimal.ZERO; //	故障1线2级差旅费
	private BigDecimal gztfLine1Level3 = BigDecimal.ZERO; //	故障1线3级差旅费
	private BigDecimal gztfLine1Level4 = BigDecimal.ZERO; //	故障1线4级差旅费
	private BigDecimal gztfLine1Level6 = BigDecimal.ZERO; //	故障1线6级差旅费
	private BigDecimal gzxcwy = BigDecimal.ZERO; //	故障现场外援费
	private BigDecimal gzLine2Level4 = BigDecimal.ZERO; //	故障2线4级工作量
	private BigDecimal gzLine2Level5 = BigDecimal.ZERO; //	故障2线5级工作量
	private BigDecimal gzLine2Level6 = BigDecimal.ZERO; //	故障2线6级工作量
	private BigDecimal gzycwy = BigDecimal.ZERO; //	故障远程外援费
	private BigDecimal xjLine1Level2 = BigDecimal.ZERO; //	巡检1线2级工作量
	private BigDecimal xjLine1Level3 = BigDecimal.ZERO; //	巡检1线3级工作量
	private BigDecimal xjLine1Level4 = BigDecimal.ZERO; //	巡检1线4级工作量
	private BigDecimal xjLine1Level6 = BigDecimal.ZERO; //	巡检1线6级工作量
	private BigDecimal xjtfLine1Level2 = BigDecimal.ZERO; //	巡检1线2级差旅费
	private BigDecimal xjtfLine1Level3 = BigDecimal.ZERO; //	巡检1线3级差旅费
	private BigDecimal xjtfLine1Level4 = BigDecimal.ZERO; //	巡检1线4级差旅费
	private BigDecimal xjtfLine1Level6 = BigDecimal.ZERO; //	巡检1线6级差旅费
	private BigDecimal xjwy = BigDecimal.ZERO; //	巡检外援费
	private BigDecimal fgzzcLine1Level2 = BigDecimal.ZERO; //	非故障技术支持1线2级工作量
	private BigDecimal fgzzcLine1Level3 = BigDecimal.ZERO; //	非故障技术支持1线3级工作量
	private BigDecimal fgzzcLine1Level4 = BigDecimal.ZERO; //	非故障技术支持1线4级工作量
	private BigDecimal fgzzcLine1Level6 = BigDecimal.ZERO; //	非故障技术支持1线6级工作量
	private BigDecimal fgzzctfLine1Level2 = BigDecimal.ZERO; //	非故障技术支持1线2级差旅费
	private BigDecimal fgzzctfLine1Level3 = BigDecimal.ZERO; //	非故障技术支持1线3级差旅费
	private BigDecimal fgzzctfLine1Level4 = BigDecimal.ZERO; //	非故障技术支持1线4级差旅费
	private BigDecimal fgzzctfLine1Level6 = BigDecimal.ZERO; //	非故障技术支持1线6级差旅费
	private BigDecimal fgzzcwy = BigDecimal.ZERO; //	非故障技术支持外援费
	private BigDecimal zyhLine1Level2 = BigDecimal.ZERO; //	专业化1线2级工作量
	private BigDecimal zyhLine1Level3 = BigDecimal.ZERO; //	专业化1线3级工作量
	private BigDecimal zyhLine1Level4 = BigDecimal.ZERO; //	专业化1线4级工作量
	private BigDecimal zyhLine1Level6 = BigDecimal.ZERO; //	专业化1线6级工作量
	private BigDecimal zyhPmLevel3 = BigDecimal.ZERO; //	专业化PM3级工作量
	private BigDecimal zyhPmLevel4 = BigDecimal.ZERO; //	专业化PM4级工作量
	private BigDecimal zyhPmLevel5 = BigDecimal.ZERO; //	专业化PM5级工作量
	private BigDecimal zyhtfLine1Level2 = BigDecimal.ZERO; //	专业化1线2级差旅费
	private BigDecimal zyhtfLine1Level3 = BigDecimal.ZERO; //	专业化1线3级差旅费
	private BigDecimal zyhtfLine1Level4 = BigDecimal.ZERO; //	专业化1线4级差旅费
	private BigDecimal zyhtfLine1Level6 = BigDecimal.ZERO; //	专业化1线6级差旅费
	private BigDecimal zyhwy = BigDecimal.ZERO; //	专业化外援费

	private BigDecimal bjPrice = BigDecimal.ZERO; //	备件价格
	private BigDecimal bjTravelPrice = BigDecimal.ZERO; //	备件专送费
	
	public String getOnceNum() {
		return onceNum;
	}
	public void setOnceNum(String onceNum) {
		this.onceNum = onceNum;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public BigDecimal getGzLine1Level2() {
		return gzLine1Level2;
	}
	public void setGzLine1Level2(BigDecimal gzLine1Level2) {
		this.gzLine1Level2 = gzLine1Level2;
	}
	public BigDecimal getGzLine1Level3() {
		return gzLine1Level3;
	}
	public void setGzLine1Level3(BigDecimal gzLine1Level3) {
		this.gzLine1Level3 = gzLine1Level3;
	}
	public BigDecimal getGzLine1Level4() {
		return gzLine1Level4;
	}
	public void setGzLine1Level4(BigDecimal gzLine1Level4) {
		this.gzLine1Level4 = gzLine1Level4;
	}
	public BigDecimal getGzLine1Level6() {
		return gzLine1Level6;
	}
	public void setGzLine1Level6(BigDecimal gzLine1Level6) {
		this.gzLine1Level6 = gzLine1Level6;
	}
	public BigDecimal getGztfLine1Level2() {
		return gztfLine1Level2;
	}
	public void setGztfLine1Level2(BigDecimal gztfLine1Level2) {
		this.gztfLine1Level2 = gztfLine1Level2;
	}
	public BigDecimal getGztfLine1Level3() {
		return gztfLine1Level3;
	}
	public void setGztfLine1Level3(BigDecimal gztfLine1Level3) {
		this.gztfLine1Level3 = gztfLine1Level3;
	}
	public BigDecimal getGztfLine1Level4() {
		return gztfLine1Level4;
	}
	public void setGztfLine1Level4(BigDecimal gztfLine1Level4) {
		this.gztfLine1Level4 = gztfLine1Level4;
	}
	public BigDecimal getGztfLine1Level6() {
		return gztfLine1Level6;
	}
	public void setGztfLine1Level6(BigDecimal gztfLine1Level6) {
		this.gztfLine1Level6 = gztfLine1Level6;
	}
	public BigDecimal getGzxcwy() {
		return gzxcwy;
	}
	public void setGzxcwy(BigDecimal gzxcwy) {
		this.gzxcwy = gzxcwy;
	}
	public BigDecimal getGzLine2Level4() {
		return gzLine2Level4;
	}
	public void setGzLine2Level4(BigDecimal gzLine2Level4) {
		this.gzLine2Level4 = gzLine2Level4;
	}
	public BigDecimal getGzLine2Level5() {
		return gzLine2Level5;
	}
	public void setGzLine2Level5(BigDecimal gzLine2Level5) {
		this.gzLine2Level5 = gzLine2Level5;
	}
	public BigDecimal getGzLine2Level6() {
		return gzLine2Level6;
	}
	public void setGzLine2Level6(BigDecimal gzLine2Level6) {
		this.gzLine2Level6 = gzLine2Level6;
	}
	public BigDecimal getGzycwy() {
		return gzycwy;
	}
	public void setGzycwy(BigDecimal gzycwy) {
		this.gzycwy = gzycwy;
	}
	public BigDecimal getXjLine1Level2() {
		return xjLine1Level2;
	}
	public void setXjLine1Level2(BigDecimal xjLine1Level2) {
		this.xjLine1Level2 = xjLine1Level2;
	}
	public BigDecimal getXjLine1Level3() {
		return xjLine1Level3;
	}
	public void setXjLine1Level3(BigDecimal xjLine1Level3) {
		this.xjLine1Level3 = xjLine1Level3;
	}
	public BigDecimal getXjLine1Level4() {
		return xjLine1Level4;
	}
	public void setXjLine1Level4(BigDecimal xjLine1Level4) {
		this.xjLine1Level4 = xjLine1Level4;
	}
	public BigDecimal getXjLine1Level6() {
		return xjLine1Level6;
	}
	public void setXjLine1Level6(BigDecimal xjLine1Level6) {
		this.xjLine1Level6 = xjLine1Level6;
	}
	public BigDecimal getXjtfLine1Level2() {
		return xjtfLine1Level2;
	}
	public void setXjtfLine1Level2(BigDecimal xjtfLine1Level2) {
		this.xjtfLine1Level2 = xjtfLine1Level2;
	}
	public BigDecimal getXjtfLine1Level3() {
		return xjtfLine1Level3;
	}
	public void setXjtfLine1Level3(BigDecimal xjtfLine1Level3) {
		this.xjtfLine1Level3 = xjtfLine1Level3;
	}
	public BigDecimal getXjtfLine1Level4() {
		return xjtfLine1Level4;
	}
	public void setXjtfLine1Level4(BigDecimal xjtfLine1Level4) {
		this.xjtfLine1Level4 = xjtfLine1Level4;
	}
	public BigDecimal getXjtfLine1Level6() {
		return xjtfLine1Level6;
	}
	public void setXjtfLine1Level6(BigDecimal xjtfLine1Level6) {
		this.xjtfLine1Level6 = xjtfLine1Level6;
	}
	public BigDecimal getXjwy() {
		return xjwy;
	}
	public void setXjwy(BigDecimal xjwy) {
		this.xjwy = xjwy;
	}
	public BigDecimal getFgzzcLine1Level2() {
		return fgzzcLine1Level2;
	}
	public void setFgzzcLine1Level2(BigDecimal fgzzcLine1Level2) {
		this.fgzzcLine1Level2 = fgzzcLine1Level2;
	}
	public BigDecimal getFgzzcLine1Level3() {
		return fgzzcLine1Level3;
	}
	public void setFgzzcLine1Level3(BigDecimal fgzzcLine1Level3) {
		this.fgzzcLine1Level3 = fgzzcLine1Level3;
	}
	public BigDecimal getFgzzcLine1Level4() {
		return fgzzcLine1Level4;
	}
	public void setFgzzcLine1Level4(BigDecimal fgzzcLine1Level4) {
		this.fgzzcLine1Level4 = fgzzcLine1Level4;
	}
	public BigDecimal getFgzzcLine1Level6() {
		return fgzzcLine1Level6;
	}
	public void setFgzzcLine1Level6(BigDecimal fgzzcLine1Level6) {
		this.fgzzcLine1Level6 = fgzzcLine1Level6;
	}
	public BigDecimal getFgzzctfLine1Level2() {
		return fgzzctfLine1Level2;
	}
	public void setFgzzctfLine1Level2(BigDecimal fgzzctfLine1Level2) {
		this.fgzzctfLine1Level2 = fgzzctfLine1Level2;
	}
	public BigDecimal getFgzzctfLine1Level3() {
		return fgzzctfLine1Level3;
	}
	public void setFgzzctfLine1Level3(BigDecimal fgzzctfLine1Level3) {
		this.fgzzctfLine1Level3 = fgzzctfLine1Level3;
	}
	public BigDecimal getFgzzctfLine1Level4() {
		return fgzzctfLine1Level4;
	}
	public void setFgzzctfLine1Level4(BigDecimal fgzzctfLine1Level4) {
		this.fgzzctfLine1Level4 = fgzzctfLine1Level4;
	}
	public BigDecimal getFgzzctfLine1Level6() {
		return fgzzctfLine1Level6;
	}
	public void setFgzzctfLine1Level6(BigDecimal fgzzctfLine1Level6) {
		this.fgzzctfLine1Level6 = fgzzctfLine1Level6;
	}
	public BigDecimal getFgzzcwy() {
		return fgzzcwy;
	}
	public void setFgzzcwy(BigDecimal fgzzcwy) {
		this.fgzzcwy = fgzzcwy;
	}
	public BigDecimal getZyhLine1Level2() {
		return zyhLine1Level2;
	}
	public void setZyhLine1Level2(BigDecimal zyhLine1Level2) {
		this.zyhLine1Level2 = zyhLine1Level2;
	}
	public BigDecimal getZyhLine1Level3() {
		return zyhLine1Level3;
	}
	public void setZyhLine1Level3(BigDecimal zyhLine1Level3) {
		this.zyhLine1Level3 = zyhLine1Level3;
	}
	public BigDecimal getZyhLine1Level4() {
		return zyhLine1Level4;
	}
	public void setZyhLine1Level4(BigDecimal zyhLine1Level4) {
		this.zyhLine1Level4 = zyhLine1Level4;
	}
	public BigDecimal getZyhLine1Level6() {
		return zyhLine1Level6;
	}
	public void setZyhLine1Level6(BigDecimal zyhLine1Level6) {
		this.zyhLine1Level6 = zyhLine1Level6;
	}
	public BigDecimal getZyhPmLevel3() {
		return zyhPmLevel3;
	}
	public void setZyhPmLevel3(BigDecimal zyhPmLevel3) {
		this.zyhPmLevel3 = zyhPmLevel3;
	}
	public BigDecimal getZyhPmLevel4() {
		return zyhPmLevel4;
	}
	public void setZyhPmLevel4(BigDecimal zyhPmLevel4) {
		this.zyhPmLevel4 = zyhPmLevel4;
	}
	public BigDecimal getZyhPmLevel5() {
		return zyhPmLevel5;
	}
	public void setZyhPmLevel5(BigDecimal zyhPmLevel5) {
		this.zyhPmLevel5 = zyhPmLevel5;
	}
	public BigDecimal getZyhtfLine1Level2() {
		return zyhtfLine1Level2;
	}
	public void setZyhtfLine1Level2(BigDecimal zyhtfLine1Level2) {
		this.zyhtfLine1Level2 = zyhtfLine1Level2;
	}
	public BigDecimal getZyhtfLine1Level3() {
		return zyhtfLine1Level3;
	}
	public void setZyhtfLine1Level3(BigDecimal zyhtfLine1Level3) {
		this.zyhtfLine1Level3 = zyhtfLine1Level3;
	}
	public BigDecimal getZyhtfLine1Level4() {
		return zyhtfLine1Level4;
	}
	public void setZyhtfLine1Level4(BigDecimal zyhtfLine1Level4) {
		this.zyhtfLine1Level4 = zyhtfLine1Level4;
	}
	public BigDecimal getZyhtfLine1Level6() {
		return zyhtfLine1Level6;
	}
	public void setZyhtfLine1Level6(BigDecimal zyhtfLine1Level6) {
		this.zyhtfLine1Level6 = zyhtfLine1Level6;
	}
	public BigDecimal getZyhwy() {
		return zyhwy;
	}
	public void setZyhwy(BigDecimal zyhwy) {
		this.zyhwy = zyhwy;
	}
	public BigDecimal getBjPrice() {
		return bjPrice;
	}
	public void setBjPrice(BigDecimal bjPrice) {
		this.bjPrice = bjPrice;
	}
	public BigDecimal getBjTravelPrice() {
		return bjTravelPrice;
	}
	public void setBjTravelPrice(BigDecimal bjTravelPrice) {
		this.bjTravelPrice = bjTravelPrice;
	}

}
