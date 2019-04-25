/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.entity.result;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.dobo.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;

/**
 * 系统人员测评计算结果Entity
 * @author admin
 * @version 2018-06-08
 */
public class CpStaffResultReview extends DataEntity<CpStaffResultReview> {
	
	private static final long serialVersionUID = 1L;
	private String staffId;		// ITCode(被评人)
	private String staffName;		// 姓名(被评人)
	private int raterNum = 0;		// 评委人数
	private String raterLevel;		// 评委类型(自评	\他评\	上级评\	下级评	\同事评)
	private Double percents;		// 权重
	private String jobLevel;		// 职位
	private String jobLevelId;		// 职位级别
	private String templateId;		// 模板号
	private Double number1 = 0.0;		// 序号1-得分
	private Double number2 = 0.0;		// 序号2
	private Double number3 = 0.0;		// 序号3
	private Double number4 = 0.0;		// 序号4
	private Double number5 = 0.0;		// 序号5
	private Double number6 = 0.0;		// 序号6
	private Double number7 = 0.0;		// 序号7
	private Double number8 = 0.0;    	// 序号8
	private Double number9 = 0.0;		// 序号9
	private Double number10 = 0.0;		// 序号10
	private Double number11 = 0.0;		// 序号11
	private Double number12 = 0.0;		// 序号12
	private Double number13 = 0.0;		// 序号13
	private Double number14 = 0.0;		// 序号14
	private Double number15 = 0.0;		// 序号15
	private Double number16 = 0.0;		// 序号16
	private Double number17 = 0.0;		// 序号17
	private Double number18 = 0.0;		// 序号18
	private Double number19 = 0.0;		// 序号19
	private Double number20 = 0.0;		// 序号20
	private Double number21 = 0.0;		// 序号21
	private Double number22 = 0.0;		// 序号22
	private Double number23 = 0.0;		// 序号23
	private Double number24 = 0.0;		// 序号24
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private Date endDate;		// 有效截止日期
	private Double grandMean = 0.0;		// 大均值
	private String orgName;
	
	public CpStaffResultReview() {
		super();
	}

	public CpStaffResultReview(String id){
		super(id);
	}

	@Length(min=1, max=20, message="ITCode(被评人)长度必须介于 1 和 20 之间")
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@Length(min=1, max=20, message="姓名(被评人)长度必须介于 1 和 20 之间")
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@NotNull(message="评委人数不能为空")
	public int getRaterNum() {
		return raterNum;
	}

	public void setRaterNum(int raterNum) {
		this.raterNum = raterNum;
	}
	
	@Length(min=1, max=20, message="评委类型(自评、他评、上级评、下级评	、同事评)长度必须介于 1 和 20 之间")
	public String getRaterLevel() {
		return raterLevel;
	}

	public void setRaterLevel(String raterLevel) {
		this.raterLevel = raterLevel;
	}
	
	@NotNull(message="权重不能为空")
	public Double getPercents() {
		return percents;
	}

	public void setPercents(Double percents) {
		this.percents = percents;
	}
	
	@Length(min=1, max=20, message="职位长度必须介于 1 和 20 之间")
	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	
	@Length(min=1, max=4, message="职位级别长度必须介于 1 和 4 之间")
	public String getJobLevelId() {
		return jobLevelId;
	}

	public void setJobLevelId(String jobLevelId) {
		this.jobLevelId = jobLevelId;
	}
	
	@Length(min=0, max=20, message="模板号长度必须介于 0 和 20 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public Double getNumber1() {
		return number1;
	}

	public void setNumber1(Double number1) {
		this.number1 = number1;
	}
	
	public Double getNumber2() {
		return number2;
	}

	public void setNumber2(Double number2) {
		this.number2 = number2;
	}
	
	public Double getNumber3() {
		return number3;
	}

	public void setNumber3(Double number3) {
		this.number3 = number3;
	}
	
	public Double getNumber4() {
		return number4;
	}

	public void setNumber4(Double number4) {
		this.number4 = number4;
	}
	
	public Double getNumber5() {
		return number5;
	}

	public void setNumber5(Double number5) {
		this.number5 = number5;
	}
	
	public Double getNumber6() {
		return number6;
	}

	public void setNumber6(Double number6) {
		this.number6 = number6;
	}
	
	public Double getNumber7() {
		return number7;
	}

	public void setNumber7(Double number7) {
		this.number7 = number7;
	}
	
	public Double getNumber8() {
		return number8;
	}

	public void setNumber8(Double number8) {
		this.number8 = number8;
	}
	
	public Double getNumber9() {
		return number9;
	}

	public void setNumber9(Double number9) {
		this.number9 = number9;
	}
	
	public Double getNumber10() {
		return number10;
	}

	public void setNumber10(Double number10) {
		this.number10 = number10;
	}
	
	public Double getNumber11() {
		return number11;
	}

	public void setNumber11(Double number11) {
		this.number11 = number11;
	}
	
	public Double getNumber12() {
		return number12;
	}

	public void setNumber12(Double number12) {
		this.number12 = number12;
	}
	
	public Double getNumber13() {
		return number13;
	}

	public void setNumber13(Double number13) {
		this.number13 = number13;
	}
	
	public Double getNumber14() {
		return number14;
	}

	public void setNumber14(Double number14) {
		this.number14 = number14;
	}
	
	public Double getNumber15() {
		return number15;
	}

	public void setNumber15(Double number15) {
		this.number15 = number15;
	}
	
	public Double getNumber16() {
		return number16;
	}

	public void setNumber16(Double number16) {
		this.number16 = number16;
	}
	
	public Double getNumber17() {
		return number17;
	}

	public void setNumber17(Double number17) {
		this.number17 = number17;
	}
	
	public Double getNumber18() {
		return number18;
	}

	public void setNumber18(Double number18) {
		this.number18 = number18;
	}
	
	public Double getNumber19() {
		return number19;
	}

	public void setNumber19(Double number19) {
		this.number19 = number19;
	}
	
	public Double getNumber20() {
		return number20;
	}

	public void setNumber20(Double number20) {
		this.number20 = number20;
	}
	
	public Double getNumber21() {
		return number21;
	}

	public void setNumber21(Double number21) {
		this.number21 = number21;
	}
	
	public Double getNumber22() {
		return number22;
	}

	public void setNumber22(Double number22) {
		this.number22 = number22;
	}
	
	public Double getNumber23() {
		return number23;
	}

	public void setNumber23(Double number23) {
		this.number23 = number23;
	}
	
	public Double getNumber24() {
		return number24;
	}

	public void setNumber24(Double number24) {
		this.number24 = number24;
	}
	
	@Length(min=0, max=32, message="remark1长度必须介于 0 和 32 之间")
	public String getRemark1() {
		return remark1==null?"":remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@Length(min=0, max=32, message="remark2长度必须介于 0 和 32 之间")
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@Length(min=0, max=32, message="remark3长度必须介于 0 和 32 之间")
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	@Length(min=1, max=2, message="状态（A0:有效/A1:无效）长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2, message="更新前状态（A0:有效/A1:无效）长度必须介于 0 和 2 之间")
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
	
	@Length(min=1, max=1, message="保存标记（0：加时间戳新增保存；1：原纪录直接更新；）长度必须介于 1 和 1 之间")
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getGrandMean() {
		return grandMean;
	}

	public void setGrandMean(Double grandMean) {
		this.grandMean = grandMean;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Map<String, String> numberMap() {
		Map<String, String> numberMap = Maps.newHashMap();
		numberMap.put("number1",this.number1+"");
		numberMap.put("number2",this.number2+"");
		numberMap.put("number3",this.number3+"");
		numberMap.put("number4",this.number4+"");
		numberMap.put("number5",this.number5+"");
		numberMap.put("number6",this.number6+"");
		numberMap.put("number7",this.number7+"");
		numberMap.put("number8",this.number8+"");
		numberMap.put("number9",this.number9+"");
		numberMap.put("number10",this.number10+"");
		numberMap.put("number11",this.number11+"");
		numberMap.put("number12",this.number12+"");
		numberMap.put("number13",this.number13+"");
		numberMap.put("number14",this.number14+"");
		numberMap.put("number15",this.number15+"");
		numberMap.put("number16",this.number16+"");
		numberMap.put("number17",this.number17+"");
		numberMap.put("number18",this.number18+"");
		numberMap.put("number19",this.number19+"");
		numberMap.put("number20",this.number20+"");
		numberMap.put("number21",this.number21+"");
		numberMap.put("number22",this.number22+"");
		numberMap.put("number23",this.number23+"");
		
		return numberMap;
	}
}