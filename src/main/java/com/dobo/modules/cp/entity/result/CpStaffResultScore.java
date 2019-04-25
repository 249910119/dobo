/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.entity.result;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.dobo.common.persistence.DataEntity;

/**
 * 系统人员测评记录结果Entity
 * @author admin
 * @version 2018-06-08
 */
public class CpStaffResultScore extends DataEntity<CpStaffResultScore> {
	
	private static final long serialVersionUID = 1L;
	private String staffId;		// ITCode(被评人)
	private String staffName;		// 姓名(被评人)
	private String raterId;		// ITCode(评委)
	private String raterName;		// 姓名(评委)
	private String raterLevel;		// 评委类型
	private Double percents;		// 权重
	private String jobLevel;		// 职位
	private String jobLevelId;		// 职位级别
	private String templateId;		// 模板号
	private Double number1;		// 序号1
	private Double number2;		// 序号2
	private Double number3;		// 序号3
	private Double number4;		// 序号4
	private Double number5;		// 序号5
	private Double number6;		// 序号6
	private Double number7;		// 序号7
	private Double number8;		// 序号8
	private Double number9;		// 序号9
	private Double number10;		// 序号10
	private Double number11;		// 序号11
	private Double number12;		// 序号12
	private Double number13;		// 序号13
	private Double number14;		// 序号14
	private Double number15;		// 序号15
	private Double number16;		// 序号16
	private Double number17;		// 序号17
	private Double number18;		// 序号18
	private Double number19;		// 序号19
	private Double number20;		// 序号20
	private Double number21;		// 序号21
	private Double number22;		// 序号22
	private Double number23;		// 序号23
	private Double number24;		// 序号24
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	private String status;		// 状态（A0:有效/A1:无效）
	private String preStatus;		// 更新前状态（A0:有效/A1:无效）
	private Date statusChangeDate;		// 状态更新时间
	private String saveFlag;		// 保存标记（0：加时间戳新增保存；1：原纪录直接更新；）
	private Date endDate;		// 有效截止日期
	
	public CpStaffResultScore() {
		super();
	}

	public CpStaffResultScore(String id){
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
	
	@Length(min=1, max=20, message="ITCode(评委)长度必须介于 1 和 20 之间")
	public String getRaterId() {
		return raterId;
	}

	public void setRaterId(String raterId) {
		this.raterId = raterId;
	}
	
	@Length(min=1, max=20, message="姓名(评委)长度必须介于 1 和 20 之间")
	public String getRaterName() {
		return raterName;
	}

	public void setRaterName(String raterName) {
		this.raterName = raterName;
	}
	
	@Length(min=1, max=20, message="评委类型长度必须介于 1 和 20 之间")
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
		return number1==null?0.0:number1;
	}

	public void setNumber1(Double number1) {
		this.number1 = number1;
	}
	
	public Double getNumber2() {
		return number2==null?0.0:number2;
	}

	public void setNumber2(Double number2) {
		this.number2 = number2;
	}
	
	public Double getNumber3() {
		return number3==null?0.0:number3;
	}

	public void setNumber3(Double number3) {
		this.number3 = number3;
	}
	
	public Double getNumber4() {
		return number4==null?0.0:number4;
	}

	public void setNumber4(Double number4) {
		this.number4 = number4;
	}
	
	public Double getNumber5() {
		return number5==null?0.0:number5;
	}

	public void setNumber5(Double number5) {
		this.number5 = number5;
	}
	
	public Double getNumber6() {
		return number6==null?0.0:number6;
	}

	public void setNumber6(Double number6) {
		this.number6 = number6;
	}
	
	public Double getNumber7() {
		return number7==null?0.0:number7;
	}

	public void setNumber7(Double number7) {
		this.number7 = number7;
	}
	
	public Double getNumber8() {
		return number8==null?0.0:number8;
	}

	public void setNumber8(Double number8) {
		this.number8 = number8;
	}
	
	public Double getNumber9() {
		return number9==null?0.0:number9;
	}

	public void setNumber9(Double number9) {
		this.number9 = number9;
	}
	
	public Double getNumber10() {
		return number10==null?0.0:number10;
	}

	public void setNumber10(Double number10) {
		this.number10 = number10;
	}
	
	public Double getNumber11() {
		return number11==null?0.0:number11;
	}

	public void setNumber11(Double number11) {
		this.number11 = number11;
	}
	
	public Double getNumber12() {
		return number12==null?0.0:number12;
	}

	public void setNumber12(Double number12) {
		this.number12 = number12;
	}
	
	public Double getNumber13() {
		return number13==null?0.0:number13;
	}

	public void setNumber13(Double number13) {
		this.number13 = number13;
	}
	
	public Double getNumber14() {
		return number14==null?0.0:number14;
	}

	public void setNumber14(Double number14) {
		this.number14 = number14;
	}
	
	public Double getNumber15() {
		return number15==null?0.0:number15;
	}

	public void setNumber15(Double number15) {
		this.number15 = number15;
	}
	
	public Double getNumber16() {
		return number16==null?0.0:number16;
	}

	public void setNumber16(Double number16) {
		this.number16 = number16;
	}
	
	public Double getNumber17() {
		return number17==null?0.0:number17;
	}

	public void setNumber17(Double number17) {
		this.number17 = number17;
	}
	
	public Double getNumber18() {
		return number18==null?0.0:number18;
	}

	public void setNumber18(Double number18) {
		this.number18 = number18;
	}
	
	public Double getNumber19() {
		return number19==null?0.0:number19;
	}

	public void setNumber19(Double number19) {
		this.number19 = number19;
	}
	
	public Double getNumber20() {
		return number20==null?0.0:number20;
	}

	public void setNumber20(Double number20) {
		this.number20 = number20;
	}
	
	public Double getNumber21() {
		return number21==null?0.0:number21;
	}

	public void setNumber21(Double number21) {
		this.number21 = number21;
	}
	
	public Double getNumber22() {
		return number22==null?0.0:number22;
	}

	public void setNumber22(Double number22) {
		this.number22 = number22;
	}
	
	public Double getNumber23() {
		return number23==null?0.0:number23;
	}

	public void setNumber23(Double number23) {
		this.number23 = number23;
	}
	
	public Double getNumber24() {
		return number24==null?0.0:number24;
	}

	public void setNumber24(Double number24) {
		this.number24 = number24;
	}
	
	@Length(min=0, max=32, message="remark1长度必须介于 0 和 32 之间")
	public String getRemark1() {
		return remark1;
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
	
}