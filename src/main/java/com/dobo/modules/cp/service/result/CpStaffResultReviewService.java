/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.service.result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.ReflectHelper;
import com.dobo.modules.cp.dao.result.CpStaffAssessTypeDao;
import com.dobo.modules.cp.dao.result.CpStaffInfoDao;
import com.dobo.modules.cp.dao.result.CpStaffResultReviewDao;
import com.dobo.modules.cp.dao.result.CpStaffResultScoreDao;
import com.dobo.modules.cp.entity.result.CpStaffAssessType;
import com.dobo.modules.cp.entity.result.CpStaffInfo;
import com.dobo.modules.cp.entity.result.CpStaffResultReview;
import com.dobo.modules.cp.entity.result.CpStaffResultScore;
import com.dobo.modules.cp.word.TestWordTemplate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 系统人员测评计算结果Service
 * @author admin
 * @version 2018-06-08
 */
@Service
@Transactional(readOnly = true)
public class CpStaffResultReviewService extends CrudService<CpStaffResultReviewDao, CpStaffResultReview> {

	@Autowired
	CpStaffResultScoreDao cpStaffResultScoreDao;
	@Autowired
	CpStaffAssessTypeDao cpStaffAssessTypeDao;
	@Autowired
	CpStaffInfoDao cpStaffInfoDao;
	
	public CpStaffResultReview get(String id) {
		return super.get(id);
	}
	
	public List<CpStaffResultReview> findList(CpStaffResultReview cpStaffResultReview) {
		return super.findList(cpStaffResultReview);
	}
	
	public Page<CpStaffResultReview> findPage(Page<CpStaffResultReview> page, CpStaffResultReview cpStaffResultReview) {
		return super.findPage(page, cpStaffResultReview);
	}
	
	@Transactional(readOnly = false)
	public void save(CpStaffResultReview cpStaffResultReview) {
		super.save(cpStaffResultReview);
	}
	
	@Transactional(readOnly = false)
	public void delete(CpStaffResultReview cpStaffResultReview) {
		super.delete(cpStaffResultReview);
	}
	
	public void calculateStaffAssess(String staffId, String jobLevelId) throws NumberFormatException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CpStaffResultScore cpStaffResultScore = new CpStaffResultScore();
		cpStaffResultScore.setStaffId(staffId);
		cpStaffResultScore.setJobLevelId(jobLevelId);
		cpStaffResultScore.setStatus("A0");
		List<CpStaffResultScore> csrsList = cpStaffResultScoreDao.findList(cpStaffResultScore);
		
		// 按照被评测人员、评委类型分组遍历测评数据
		Map<String, Map<String, List<CpStaffResultScore>>> csrsMap = Maps.newHashMap();
		for(CpStaffResultScore csrs : csrsList) {
			if(csrsMap.get(csrs.getStaffId()) == null) {
				Map<String, List<CpStaffResultScore>> raterMap = Maps.newHashMap();
				csrsMap.put(csrs.getStaffId(), raterMap);
			}
			if(csrsMap.get(csrs.getStaffId()).get(csrs.getRaterLevel()) == null) {
				List<CpStaffResultScore> list = Lists.newArrayList();
				csrsMap.get(csrs.getStaffId()).put(csrs.getRaterLevel(), list);
			}
			
			csrsMap.get(csrs.getStaffId()).get(csrs.getRaterLevel()).add(csrs);
		}
		
		CpStaffAssessType cpStaffAssessType = new CpStaffAssessType();
		cpStaffAssessType.setJobLevelId(jobLevelId);
		List<CpStaffAssessType> csatList = cpStaffAssessTypeDao.findList(cpStaffAssessType);
		Map<String, String> actionMap = Maps.newHashMap();
		/**
		 *  他评合计 
		 *  个人素质和能力他评=上级评分*50%+下级评分*30%+同事评分*20%；
		 *  团队他评分=上级评分*40%+下级评分*50%+同事评分*10%；
		 *  当期业绩他评分=上级评分*50%+下级评分*40%+同事评分*10%；
		 *  长期发展他评分=上级评分*50%+下级评分*40%+同事评分*10%。
		 */
		// 计算他评时，获取模块公式和行为的对应关系
		// <评分角色，<行为项，公式>>
		Map<String, Map<String, Double>> actionRaterMap = Maps.newHashMap();
		String[] raterLevels = new String[]{"本人","上级","下级","同事"};
		for(String str : raterLevels) {
			Map<String, Double> map = Maps.newHashMap();
			actionRaterMap.put(str, map);
			Double module1RaterRate = 1.0;
			Double module2RaterRate = 1.0;
			Double module3RaterRate = 1.0;
			Double module4RaterRate = 1.0;
			if("上级".equals(str)) {
				module1RaterRate = 0.5;
				module2RaterRate = 0.4;
				module3RaterRate = 0.5;
				module4RaterRate = 0.5;
			} else if("下级".equals(str)) {
				module1RaterRate = 0.3;
				module2RaterRate = 0.5;
				module3RaterRate = 0.4;
				module4RaterRate = 0.4;
			} else if("同事".equals(str)) {
				module1RaterRate = 0.2;
				module2RaterRate = 0.1;
				module3RaterRate = 0.1;
				module4RaterRate = 0.1;
			}
			for(CpStaffAssessType csat : csatList) {
				actionMap.put(csat.getNumberName(), csat.getActionId());
				if("module1".equals(csat.getModuleId())) {
					actionRaterMap.get(str).put(csat.getNumberName(), module1RaterRate);
				} else if("module2".equals(csat.getModuleId())) {
					actionRaterMap.get(str).put(csat.getNumberName(), module2RaterRate);
				} else if("module3".equals(csat.getModuleId())) {
					actionRaterMap.get(str).put(csat.getNumberName(), module3RaterRate);
				} else if("module4".equals(csat.getModuleId())) {
					actionRaterMap.get(str).put(csat.getNumberName(), module4RaterRate);
				}
			}
		}
		
		// 根据测评计算规则计算自评	上级评     下级评	同事评    他评
		for(String staffid : csrsMap.keySet()) {
			Map<String, CpStaffResultReview> staffCsrsMap = Maps.newHashMap();
			for(String raterLevel : csrsMap.get(staffid).keySet()) {
				CpStaffResultReview cpStaffResultReview = new CpStaffResultReview();
				cpStaffResultReview.setStaffId(staffid);
				cpStaffResultReview.setRaterNum(csrsMap.get(staffid).get(raterLevel).size());
				cpStaffResultReview.setRaterLevel(raterLevel);
				
				Map<String, Integer> numberCountMap =Maps.newHashMap();
				for(CpStaffResultScore csrs : csrsMap.get(staffid).get(raterLevel)) {
					cpStaffResultReview.setStaffName(csrs.getStaffName());
					cpStaffResultReview.setJobLevel(csrs.getJobLevel());
					cpStaffResultReview.setJobLevelId(csrs.getJobLevelId());
					cpStaffResultReview.setTemplateId(csrs.getTemplateId());
					if(csrs.getNumber1() != null && csrs.getNumber1() != 0) {
						numberCountMap.put("number1", numberCountMap.get("number1")==null?0+1:numberCountMap.get("number1")+1);
					}
					if(csrs.getNumber2() != null && csrs.getNumber2() != 0) {
						numberCountMap.put("number2", numberCountMap.get("number2")==null?0+1:numberCountMap.get("number2")+1);
					}
					if(csrs.getNumber3() != null && csrs.getNumber3() != 0) {
						numberCountMap.put("number3", numberCountMap.get("number3")==null?0+1:numberCountMap.get("number3")+1);
					}
					if(csrs.getNumber4() != null && csrs.getNumber4() != 0) {
						numberCountMap.put("number4", numberCountMap.get("number4")==null?0+1:numberCountMap.get("number4")+1);
					}
					if(csrs.getNumber5() != null && csrs.getNumber5() != 0) {
						numberCountMap.put("number5", numberCountMap.get("number5")==null?0+1:numberCountMap.get("number5")+1);
					}
					if(csrs.getNumber6() != null && csrs.getNumber6() != 0) {
						numberCountMap.put("number6", numberCountMap.get("number6")==null?0+1:numberCountMap.get("number6")+1);
					}
					if(csrs.getNumber7() != null && csrs.getNumber7() != 0) {
						numberCountMap.put("number7", numberCountMap.get("number7")==null?0+1:numberCountMap.get("number7")+1);
					}
					if(csrs.getNumber8() != null && csrs.getNumber8() != 0) {
						numberCountMap.put("number8", numberCountMap.get("number8")==null?0+1:numberCountMap.get("number8")+1);
					}
					if(csrs.getNumber9() != null && csrs.getNumber9() != 0) {
						numberCountMap.put("number9", numberCountMap.get("number9")==null?0+1:numberCountMap.get("number9")+1);
					}
					if(csrs.getNumber10() != null && csrs.getNumber10() != 0) {
						numberCountMap.put("number10", numberCountMap.get("number10")==null?0+1:numberCountMap.get("number10")+1);
					}
					if(csrs.getNumber11() != null && csrs.getNumber11() != 0) {
						numberCountMap.put("number11", numberCountMap.get("number11")==null?0+1:numberCountMap.get("number11")+1);
					}
					if(csrs.getNumber12() != null && csrs.getNumber12() != 0) {
						numberCountMap.put("number12", numberCountMap.get("number12")==null?0+1:numberCountMap.get("number12")+1);
					}
					if(csrs.getNumber13() != null && csrs.getNumber13() != 0) {
						numberCountMap.put("number13", numberCountMap.get("number13")==null?0+1:numberCountMap.get("number13")+1);
					}
					if(csrs.getNumber14() != null && csrs.getNumber14() != 0) {
						numberCountMap.put("number14", numberCountMap.get("number14")==null?0+1:numberCountMap.get("number14")+1);
					}
					if(csrs.getNumber15() != null && csrs.getNumber15() != 0) {
						numberCountMap.put("number15", numberCountMap.get("number15")==null?0+1:numberCountMap.get("number15")+1);
					}
					if(csrs.getNumber16() != null && csrs.getNumber16() != 0) {
						numberCountMap.put("number16", numberCountMap.get("number16")==null?0+1:numberCountMap.get("number16")+1);
					}
					if(csrs.getNumber17() != null && csrs.getNumber17() != 0) {
						numberCountMap.put("number17", numberCountMap.get("number17")==null?0+1:numberCountMap.get("number17")+1);
					}
					if(csrs.getNumber18() != null && csrs.getNumber18() != 0) {
						numberCountMap.put("number18", numberCountMap.get("number18")==null?0+1:numberCountMap.get("number18")+1);
					}
					if(csrs.getNumber19() != null && csrs.getNumber19() != 0) {
						numberCountMap.put("number19", numberCountMap.get("number19")==null?0+1:numberCountMap.get("number19")+1);
					}
					if(csrs.getNumber20() != null && csrs.getNumber20() != 0) {
						numberCountMap.put("number20", numberCountMap.get("number20")==null?0+1:numberCountMap.get("number20")+1);
					}
					if(csrs.getNumber21() != null && csrs.getNumber21() != 0) {
						numberCountMap.put("number21", numberCountMap.get("number21")==null?0+1:numberCountMap.get("number21")+1);
					}
					if(csrs.getNumber22() != null && csrs.getNumber22() != 0) {
						numberCountMap.put("number22", numberCountMap.get("number22")==null?0+1:numberCountMap.get("number22")+1);
					}
					if(csrs.getNumber23() != null && csrs.getNumber23() != 0) {
						numberCountMap.put("number23", numberCountMap.get("number23")==null?0+1:numberCountMap.get("number23")+1);
					}
					cpStaffResultReview.setNumber1(cpStaffResultReview.getNumber1()+csrs.getNumber1());
					cpStaffResultReview.setNumber2(cpStaffResultReview.getNumber2()+csrs.getNumber2());
					cpStaffResultReview.setNumber3(cpStaffResultReview.getNumber3()+csrs.getNumber3());
					cpStaffResultReview.setNumber4(cpStaffResultReview.getNumber4()+csrs.getNumber4());
					cpStaffResultReview.setNumber5(cpStaffResultReview.getNumber5()+csrs.getNumber5());
					cpStaffResultReview.setNumber6(cpStaffResultReview.getNumber6()+csrs.getNumber6());
					cpStaffResultReview.setNumber7(cpStaffResultReview.getNumber7()+csrs.getNumber7());
					cpStaffResultReview.setNumber8(cpStaffResultReview.getNumber8()+csrs.getNumber8());
					cpStaffResultReview.setNumber9(cpStaffResultReview.getNumber9()+csrs.getNumber9());
					cpStaffResultReview.setNumber10(cpStaffResultReview.getNumber10()+csrs.getNumber10());
					cpStaffResultReview.setNumber11(cpStaffResultReview.getNumber11()+csrs.getNumber11());
					cpStaffResultReview.setNumber12(cpStaffResultReview.getNumber12()+csrs.getNumber12());
					cpStaffResultReview.setNumber13(cpStaffResultReview.getNumber13()+csrs.getNumber13());
					cpStaffResultReview.setNumber14(cpStaffResultReview.getNumber14()+csrs.getNumber14());
					cpStaffResultReview.setNumber15(cpStaffResultReview.getNumber15()+csrs.getNumber15());
					cpStaffResultReview.setNumber16(cpStaffResultReview.getNumber16()+csrs.getNumber16());
					cpStaffResultReview.setNumber17(cpStaffResultReview.getNumber17()+csrs.getNumber17());
					cpStaffResultReview.setNumber18(cpStaffResultReview.getNumber18()+csrs.getNumber18());
					cpStaffResultReview.setNumber19(cpStaffResultReview.getNumber19()+csrs.getNumber19());
					cpStaffResultReview.setNumber20(cpStaffResultReview.getNumber20()+csrs.getNumber20());
					cpStaffResultReview.setNumber21(cpStaffResultReview.getNumber21()+csrs.getNumber21());
					cpStaffResultReview.setNumber22(cpStaffResultReview.getNumber22()+csrs.getNumber22());
					cpStaffResultReview.setNumber23(cpStaffResultReview.getNumber23()+csrs.getNumber23());
					//cpStaffResultReview.setNumber24(cpStaffResultReview.getNumber24()+csrs.getNumber24());
					if(csrs.getRemark1() != null)
						cpStaffResultReview.setRemark1(cpStaffResultReview.getRemark1()+csrs.getRemark1().toUpperCase().replace("A1", "1").replace("A2", "2").replace("A3", "3").replace("A4", "4"));
				}
				
				if(numberCountMap.get("number1") != null)
					cpStaffResultReview.setNumber1(cpStaffResultReview.getNumber1()/numberCountMap.get("number1"));
				if(numberCountMap.get("number2") != null)
					cpStaffResultReview.setNumber2(cpStaffResultReview.getNumber2()/numberCountMap.get("number2"));
				if(numberCountMap.get("number3") != null)
					cpStaffResultReview.setNumber3(cpStaffResultReview.getNumber3()/numberCountMap.get("number3"));
				if(numberCountMap.get("number4") != null)
					cpStaffResultReview.setNumber4(cpStaffResultReview.getNumber4()/numberCountMap.get("number4"));
				if(numberCountMap.get("number5") != null)
					cpStaffResultReview.setNumber5(cpStaffResultReview.getNumber5()/numberCountMap.get("number5"));
				if(numberCountMap.get("number6") != null)
					cpStaffResultReview.setNumber6(cpStaffResultReview.getNumber6()/numberCountMap.get("number6"));
				if(numberCountMap.get("number7") != null)
					cpStaffResultReview.setNumber7(cpStaffResultReview.getNumber7()/numberCountMap.get("number7"));
				if(numberCountMap.get("number8") != null)
					cpStaffResultReview.setNumber8(cpStaffResultReview.getNumber8()/numberCountMap.get("number8"));
				if(numberCountMap.get("number9") != null)
					cpStaffResultReview.setNumber9(cpStaffResultReview.getNumber9()/numberCountMap.get("number9"));
				if(numberCountMap.get("number10") != null)
					cpStaffResultReview.setNumber10(cpStaffResultReview.getNumber10()/numberCountMap.get("number10"));
				if(numberCountMap.get("number11") != null)
					cpStaffResultReview.setNumber11(cpStaffResultReview.getNumber11()/numberCountMap.get("number11"));
				if(numberCountMap.get("number12") != null)
					cpStaffResultReview.setNumber12(cpStaffResultReview.getNumber12()/numberCountMap.get("number12"));
				if(numberCountMap.get("number13") != null)
					cpStaffResultReview.setNumber13(cpStaffResultReview.getNumber13()/numberCountMap.get("number13"));
				if(numberCountMap.get("number14") != null)
					cpStaffResultReview.setNumber14(cpStaffResultReview.getNumber14()/numberCountMap.get("number14"));
				if(numberCountMap.get("number15") != null)
					cpStaffResultReview.setNumber15(cpStaffResultReview.getNumber15()/numberCountMap.get("number15"));
				if(numberCountMap.get("number16") != null)
					cpStaffResultReview.setNumber16(cpStaffResultReview.getNumber16()/numberCountMap.get("number16"));
				if(numberCountMap.get("number17") != null)
					cpStaffResultReview.setNumber17(cpStaffResultReview.getNumber17()/numberCountMap.get("number17"));
				if(numberCountMap.get("number18") != null)
					cpStaffResultReview.setNumber18(cpStaffResultReview.getNumber18()/numberCountMap.get("number18"));
				if(numberCountMap.get("number19") != null)
					cpStaffResultReview.setNumber19(cpStaffResultReview.getNumber19()/numberCountMap.get("number19"));
				if(numberCountMap.get("number20") != null)
					cpStaffResultReview.setNumber20(cpStaffResultReview.getNumber20()/numberCountMap.get("number20"));
				if(numberCountMap.get("number21") != null)
					cpStaffResultReview.setNumber21(cpStaffResultReview.getNumber21()/numberCountMap.get("number21"));
				if(numberCountMap.get("number22") != null)
					cpStaffResultReview.setNumber22(cpStaffResultReview.getNumber22()/numberCountMap.get("number22"));
				if(numberCountMap.get("number23") != null)
					cpStaffResultReview.setNumber23(cpStaffResultReview.getNumber23()/numberCountMap.get("number23"));
				//cpStaffResultReview.setNumber24(cpStaffResultReview.getNumber24()/numberCountMap.get("number24"));
				
				//String perInfo = this.duplicatRemoval(cpStaffResultReview.getRemark1()).replace("1", "A1").replace("2", "A2").replace("3", "A3").replace("4", "A4");
				//String perInfo = cpStaffResultReview.getRemark1().replaceAll("1", "A1").replaceAll("2", "A2").replaceAll("3", "A3").replaceAll("4", "A4");
				cpStaffResultReview.setRemark1(cpStaffResultReview.getRemark1());
				cpStaffResultReview.setStatus("A0");
				
				// 大均值
				Double grandMean = 0.0;
				for(String key : actionMap.keySet()) {
					grandMean = grandMean + Double.valueOf(ReflectHelper.getValueByFieldName(cpStaffResultReview, key).toString());
				}
				cpStaffResultReview.setGrandMean(grandMean/actionMap.size());
				
				this.save(cpStaffResultReview);
				staffCsrsMap.put(raterLevel, cpStaffResultReview);
			}
			
			/**
			 *  他评合计 
			 *  个人素质和能力他评=上级评分*50%+下级评分*30%+同事评分*20%；
			 *  团队他评分=上级评分*40%+下级评分*50%+同事评分*10%；
			 *  当期业绩他评分=上级评分*50%+下级评分*40%+同事评分*10%；
			 *  长期发展他评分=上级评分*50%+下级评分*40%+同事评分*10%。
			 *  
			 *  当个人评定中没有下级评时，公式需要重新初始
			 *  个人素质和能力他评=上级评分*60%+同事评分*40%；
			 *	团队他评分=上级评分*60%+同事评分*40%；
			 *	当期业绩他评分=上级评分*60%+同事评分*40%
			 */

			boolean haveLowRater = true;
			if(!staffCsrsMap.keySet().contains("下级")) {
				haveLowRater = false;
			}
			
			CpStaffResultReview cpStaffResultReview = new CpStaffResultReview();
			cpStaffResultReview.setStaffId(staffid);
			cpStaffResultReview.setRaterNum(1);
			cpStaffResultReview.setRaterLevel("他评");
			for(String rl : staffCsrsMap.keySet()) {
				if(!"上级,下级,同事".contains(rl)) {
					continue;
				}
				
				CpStaffResultReview csrs = staffCsrsMap.get(rl);
				cpStaffResultReview.setStaffName(csrs.getStaffName());
				cpStaffResultReview.setJobLevel(csrs.getJobLevel());
				cpStaffResultReview.setJobLevelId(csrs.getJobLevelId());
				
				cpStaffResultReview.setNumber1(cpStaffResultReview.getNumber1()+csrs.getNumber1()*this.actionRater(rl, "number1", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber2(cpStaffResultReview.getNumber2()+csrs.getNumber2()*this.actionRater(rl, "number2", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber3(cpStaffResultReview.getNumber3()+csrs.getNumber3()*this.actionRater(rl, "number3", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber4(cpStaffResultReview.getNumber4()+csrs.getNumber4()*this.actionRater(rl, "number4", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber5(cpStaffResultReview.getNumber5()+csrs.getNumber5()*this.actionRater(rl, "number5", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber6(cpStaffResultReview.getNumber6()+csrs.getNumber6()*this.actionRater(rl, "number6", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber7(cpStaffResultReview.getNumber7()+csrs.getNumber7()*this.actionRater(rl, "number7", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber8(cpStaffResultReview.getNumber8()+csrs.getNumber8()*this.actionRater(rl, "number8", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber9(cpStaffResultReview.getNumber9()+csrs.getNumber9()*this.actionRater(rl, "number9", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber10(cpStaffResultReview.getNumber10()+csrs.getNumber10()*this.actionRater(rl, "number10", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber11(cpStaffResultReview.getNumber11()+csrs.getNumber11()*this.actionRater(rl, "number11", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber12(cpStaffResultReview.getNumber12()+csrs.getNumber12()*this.actionRater(rl, "number12", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber13(cpStaffResultReview.getNumber13()+csrs.getNumber13()*this.actionRater(rl, "number13", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber14(cpStaffResultReview.getNumber14()+csrs.getNumber14()*this.actionRater(rl, "number14", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber15(cpStaffResultReview.getNumber15()+csrs.getNumber15()*this.actionRater(rl, "number15", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber16(cpStaffResultReview.getNumber16()+csrs.getNumber16()*this.actionRater(rl, "number16", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber17(cpStaffResultReview.getNumber17()+csrs.getNumber17()*this.actionRater(rl, "number17", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber18(cpStaffResultReview.getNumber18()+csrs.getNumber18()*this.actionRater(rl, "number18", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber19(cpStaffResultReview.getNumber19()+csrs.getNumber19()*this.actionRater(rl, "number19", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber20(cpStaffResultReview.getNumber20()+csrs.getNumber20()*this.actionRater(rl, "number20", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber21(cpStaffResultReview.getNumber21()+csrs.getNumber21()*this.actionRater(rl, "number21", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber22(cpStaffResultReview.getNumber22()+csrs.getNumber22()*this.actionRater(rl, "number22", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber23(cpStaffResultReview.getNumber23()+csrs.getNumber23()*this.actionRater(rl, "number23", actionRaterMap, haveLowRater));
				cpStaffResultReview.setNumber24(cpStaffResultReview.getNumber24()+csrs.getNumber24());
				cpStaffResultReview.setRemark1(cpStaffResultReview.getRemark1()+csrs.getRemark1().replace("A1", "1").replace("A2", "2").replace("A3", "3").replace("A4", "4"));
			}
			
			cpStaffResultReview.setStatus("A0");
			// 大均值
			Double grandMean = 0.0;
			for(String key : actionMap.keySet()) {
				grandMean = grandMean + Double.valueOf(ReflectHelper.getValueByFieldName(cpStaffResultReview, key).toString());
			}
			cpStaffResultReview.setGrandMean(grandMean/actionMap.size());
			
			//String perInfo = cpStaffResultReview.getRemark1().replace("1", "A1").replace("2", "A2").replace("3", "A3").replace("3", "A3");
			cpStaffResultReview.setRemark1(cpStaffResultReview.getRemark1());
			
			this.save(cpStaffResultReview);
			
		}
	}
	
	public void exportStaffAssess(String jobLevelId) throws NumberFormatException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CpStaffAssessType cpStaffAssessType = new CpStaffAssessType();
		cpStaffAssessType.setJobLevelId(jobLevelId);
		List<CpStaffAssessType> csatList = cpStaffAssessTypeDao.findList(cpStaffAssessType);
		// 各项行为集合
		Map<String, CpStaffAssessType> actionTitleMap = Maps.newHashMap();
		// 各项能力集合
		Map<String, String> abilityTitleMap = Maps.newHashMap();
		// 各项能力评分集合
		Map<String, List<String>> abilityMap = Maps.newHashMap();;
		// 各项模块评分集合
		Map<String, List<String>> moduleMap = Maps.newHashMap();
		
		for(CpStaffAssessType csat : csatList) {
			actionTitleMap.put(csat.getNumberName(), csat);
			abilityTitleMap.put(csat.getAbilityId(), csat.getAbilityName());
			
			if(abilityMap.get(csat.getAbilityId()) == null) {
				List<String> abilityList = Lists.newArrayList();
				abilityMap.put(csat.getAbilityId(), abilityList);
			}
			abilityMap.get(csat.getAbilityId()).add(csat.getNumberName());
			
			if(moduleMap.get(csat.getModuleId()) == null) {
				List<String> moduleList = Lists.newArrayList();
				moduleMap.put(csat.getModuleId(), moduleList);
			}
			moduleMap.get(csat.getModuleId()).add(csat.getNumberName());
		}
		
		// 取评分结果数据
		CpStaffResultReview cpStaffResultReview = new CpStaffResultReview();
		cpStaffResultReview.setJobLevelId(jobLevelId);
		List<CpStaffResultReview> csrrList = this.findList(cpStaffResultReview);
		
		// 他评集合
		List<CpStaffResultReview> csrrOtherList = Lists.newArrayList();
		// 同级评分集合
		Map<String, List<CpStaffResultReview>> csrrJobMap = Maps.newHashMap();
		// 单人评分集合
		Map<String, List<CpStaffResultReview>> csrrStaffMap = Maps.newHashMap();
		// 各项能力得分汇总集合
		Map<String, Double> abilitySumMap = Maps.newHashMap();
		// 有效的能力计数
		Map<String, Integer> abilityCountMap = Maps.newHashMap();
		// 各项维度得分汇总集合
		Map<String, Double> moduleSumMap = Maps.newHashMap();
		// 有效的维度计数
		Map<String, Integer> moduleCountMap = Maps.newHashMap();
		for(CpStaffResultReview csrr : csrrList) {
			if(csrrStaffMap.get(csrr.getStaffId()) == null) {
				List<CpStaffResultReview> staffList = Lists.newArrayList();
				csrrStaffMap.put(csrr.getStaffId(), staffList);
			}
			csrrStaffMap.get(csrr.getStaffId()).add(csrr);
			
			// 他评集合
			if("他评".equals(csrr.getRaterLevel())) {
				csrrOtherList.add(csrr);

				// 各项能力得分汇总
				for(String abilityId : abilityMap.keySet()) {
					Double scores = 0.0;
					for(String number : abilityMap.get(abilityId)) {
						scores = scores + Double.valueOf(ReflectHelper.getValueByFieldName(csrr, number).toString());
					}
					
					if(scores > 0) abilityCountMap.put(abilityId, abilityCountMap.get(abilityId)==null?0+1:abilityCountMap.get(abilityId)+1);
					if(abilitySumMap.get(abilityId) == null) abilitySumMap.put(abilityId, 0.0);
					abilitySumMap.put(abilityId, abilitySumMap.get(abilityId)+scores/abilityMap.get(abilityId).size());
				}

				// 各项模块得分汇总
				for(String moduleId : moduleMap.keySet()) {
					Double scores = 0.0;
					for(String number : moduleMap.get(moduleId)) {
						scores = scores + Double.valueOf(ReflectHelper.getValueByFieldName(csrr, number).toString());
					}
					
					if(scores > 0) moduleCountMap.put(moduleId, moduleCountMap.get(moduleId)==null?0+1:moduleCountMap.get(moduleId)+1);
					if(moduleSumMap.get(moduleId) == null) moduleSumMap.put(moduleId, 0.0);
					moduleSumMap.put(moduleId, moduleSumMap.get(moduleId)+scores/moduleMap.get(moduleId).size());
				}
			}

			if(csrrJobMap.get(csrr.getRaterLevel()) == null) {
				List<CpStaffResultReview> staffList = Lists.newArrayList();
				csrrJobMap.put(csrr.getRaterLevel(), staffList);
			}
			csrrJobMap.get(csrr.getRaterLevel()).add(csrr);
			
		}
		
		// 各项能力职级均分
		Map<String, String> aveAbilityMap = Maps.newHashMap();
		for(String key : abilitySumMap.keySet()) {
			if(abilityCountMap.get(key) == null) continue;
			aveAbilityMap.put("ave_"+key, BigDecimal.valueOf(abilitySumMap.get(key)/abilityCountMap.get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
		}
		
		// 各项模块职级均分
		Map<String, String> aveModuleMap = Maps.newHashMap();
		for(String key : moduleSumMap.keySet()) {
			if(moduleCountMap.get(key) == null) continue;
			aveModuleMap.put("ave_"+key, BigDecimal.valueOf(moduleSumMap.get(key)/moduleCountMap.get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
		}
		
		// 按照单个员工评分生成word
		for(String staffId : csrrStaffMap.keySet()) {
			// 判断是否包含下级评分
			boolean haveLowRater = false;
			for(CpStaffResultReview csrr : csrrStaffMap.get(staffId)) {
				if("下级".equals(csrr.getRaterLevel())) {
					haveLowRater = true;
				}
			}
			
			// 测评结果来源:包含职员姓名，所属部门
			Map<String, String> table1Map = Maps.newHashMap();
			// 整体大均值和百分位，专业能力数据
			Map<String, String> table2Map = Maps.newHashMap();
			// 各项能力行为得分 <行为描述项评委类型，得分>	
			Map<String, String> table3Map = Maps.newHashMap();
			// 各项能力得分 <能力项评委类型，得分>	
			Map<String, String> table4Map = Maps.newHashMap();
			// 各模块维度得分得分 <模块项评委类型，得分>	
			Map<String, String> table5Map = Maps.newHashMap();
			// 能力行为高分项 <行为描述项ID评委类型，得分>	
			Map<String, String> table6Map = Maps.newHashMap();
			// 能力行为低分项 <行为描述项ID评委类型，得分>	
			Map<String, String> table7Map = Maps.newHashMap();
			// 自评和他评能力对比 <行为描述项ID评委类型，得分>	
			Map<String, String> table8Map = Maps.newHashMap();
			
			String rater = "";
			CpStaffResultReview ownerCsrr = null; // 自评分记录
			List<String> topParaList = Lists.newArrayList(); // top5能力行为项
			List<String> lowParaList = Lists.newArrayList(); // low5能力行为项
			Map<String, Map<String, Double>> compAbilityMap = Maps.newHashMap();
			for(CpStaffResultReview csrr : csrrStaffMap.get(staffId)) {

				table1Map.put("staff_name", csrr.getStaffName());
				table1Map.put("staff_org", csrr.getOrgName());
				
				if("本人".equals(csrr.getRaterLevel())) {
					rater = "owner";
					ownerCsrr = csrr;
				} else if("上级".equals(csrr.getRaterLevel())) {
					rater = "higher";
				} else if("下级".equals(csrr.getRaterLevel())) {
					rater = "lower";
				} else if("同事".equals(csrr.getRaterLevel())) {
					rater = "coller";
				} else if("他评".equals(csrr.getRaterLevel())) {
					rater = "other";
					
					int count = 0 ;
					for(CpStaffResultReview csrrOther : csrrOtherList) {
						if(csrrOther.getGrandMean() < csrr.getGrandMean()) {
							count ++;
						}
					}
					table2Map.put("table2_grandMean", BigDecimal.valueOf(csrr.getGrandMean()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
					table2Map.put("table2_percent", BigDecimal.valueOf(count*100).divide(BigDecimal.valueOf(csrrOtherList.size()), 0, BigDecimal.ROUND_HALF_UP)+"%");
					
					// 获取他评的高分项（Top5）
					List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(csrr.numberMap().entrySet());
					Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
			            //降序排序
			            public int compare(Entry<String, String> o1,
			                    Entry<String, String> o2) {
			                return o2.getValue().compareTo(o1.getValue());
			            }
			            
			        });
					
					int i = 1;
					for(Map.Entry<String,String> top : list) {
						if(i>5) break;
						if(!actionTitleMap.keySet().contains(top.getKey())) continue;
						table6Map.put("top"+i+"_"+rater, new BigDecimal(top.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
						table6Map.put("top"+i+"_action", actionTitleMap.get(top.getKey()).getActionName());
						table6Map.put("top"+i+"_ability", actionTitleMap.get(top.getKey()).getAbilityName());
						
						topParaList.add(top.getKey());
						i++;
					}
					
					//  获取他评的低分项（Top5）
					Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
			            //升序排序
			            public int compare(Entry<String, String> o1,
			                    Entry<String, String> o2) {
			                return o1.getValue().compareTo(o2.getValue());
			            }
			            
			        });
					
					int j = 1;
					for(Map.Entry<String,String> low : list) {
						if(j>5) break;
						if(!actionTitleMap.keySet().contains(low.getKey())) continue;
						table7Map.put("low"+j+"_"+rater, new BigDecimal(low.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
						table7Map.put("low"+j+"_action", actionTitleMap.get(low.getKey()).getActionName());
						table7Map.put("low"+j+"_ability", actionTitleMap.get(low.getKey()).getAbilityName());

						lowParaList.add(low.getKey());
						j++;
					}
					
				}

				// 个人专业技能
				char [] arr =csrr.getRemark1().toCharArray();
				Map<String, Integer> skillCountMap = Maps.newHashMap();
				for (char c : arr) {
					String skillId = String.valueOf(c);
					if("1,2,3,4".contains(skillId)) {skillId = "A"+skillId; }
					if(!skillCountMap.containsKey(skillId)) skillCountMap.put(skillId, 0);
					skillCountMap.put(skillId, skillCountMap.get(skillId)+1);
				}
				List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(skillCountMap.entrySet());
				Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
		            //降序排序
		            public int compare(Entry<String, Integer> o1,
		                    Entry<String, Integer> o2) {
		                return o2.getValue().compareTo(o1.getValue());
		            }
		        });
				String staffSkill = "";
		        for(Map.Entry<String,Integer> comp : list) {
		        	staffSkill = staffSkill + comp.getKey() + ":" + comp.getValue() + "  ";
		        }
		        
				table2Map.put("table2_skill_"+rater, staffSkill);
				
				table1Map.put("table1_"+rater, csrr.getRaterNum()+"");
				
				table3Map.put("action1_"+rater, csrr.getNumber1()+"");
				table3Map.put("action2_"+rater, csrr.getNumber2()+"");
				table3Map.put("action3_"+rater, csrr.getNumber3()+"");
				table3Map.put("action4_"+rater, csrr.getNumber4()+"");
				table3Map.put("action5_"+rater, csrr.getNumber5()+"");
				table3Map.put("action6_"+rater, csrr.getNumber6()+"");
				table3Map.put("action7_"+rater, csrr.getNumber7()+"");
				table3Map.put("action8_"+rater, csrr.getNumber8()+"");
				table3Map.put("action9_"+rater, csrr.getNumber9()+"");
				table3Map.put("action10_"+rater, csrr.getNumber10()+"");
				table3Map.put("action11_"+rater, csrr.getNumber11()+"");
				table3Map.put("action12_"+rater, csrr.getNumber12()+"");
				table3Map.put("action13_"+rater, csrr.getNumber13()+"");
				table3Map.put("action14_"+rater, csrr.getNumber14()+"");
				table3Map.put("action15_"+rater, csrr.getNumber15()+"");
				table3Map.put("action15_"+rater, csrr.getNumber16()+"");
				table3Map.put("action17_"+rater, csrr.getNumber17()+"");
				table3Map.put("action18_"+rater, csrr.getNumber18()+"");
				table3Map.put("action19_"+rater, csrr.getNumber19()+"");
				table3Map.put("action20_"+rater, csrr.getNumber20()+"");
				table3Map.put("action21_"+rater, csrr.getNumber21()+"");
				table3Map.put("action22_"+rater, csrr.getNumber22()+"");
				table3Map.put("action23_"+rater, csrr.getNumber23()+"");
				
				// 各项能力得分
				for(String abilityId : abilityMap.keySet()) {
					Double scores = 0.0;
					for(String number : abilityMap.get(abilityId)) {
						scores = scores + Double.valueOf(ReflectHelper.getValueByFieldName(csrr, number).toString());
					}
					
					table4Map.put(abilityId+"_"+rater, BigDecimal.valueOf(scores/abilityMap.get(abilityId).size()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
					
					if(compAbilityMap.get(rater) == null) {
						Map<String, Double> comabMap = Maps.newHashMap();
						compAbilityMap.put(rater, comabMap);
					}
					compAbilityMap.get(rater).put(abilityId, scores/abilityMap.get(abilityId).size());
					
				}

				// 各维度得分
				for(String moduleId : moduleMap.keySet()) {
					Double scores = 0.0;
					for(String number : moduleMap.get(moduleId)) {
						scores = scores + Double.valueOf(ReflectHelper.getValueByFieldName(csrr, number).toString());
					}
					table5Map.put(moduleId+"_"+rater, BigDecimal.valueOf(scores/moduleMap.get(moduleId).size()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
				}
				table5Map.put("percent_"+rater, csrr.getGrandMean()+""); // 大均值
				
			}
			
			// 能力行为高分项:本人评分
			for(int i=1;i<=topParaList.size();i++) {
				if(ownerCsrr == null) {
					table6Map.put("top"+i+"_owner", "");
				} else {
					Object topOwner = ReflectHelper.getValueByFieldName(ownerCsrr, topParaList.get(i-1));
					if(ownerCsrr == null || topOwner == null) {
						table6Map.put("top"+i+"_owner", "");
					} else {
						table6Map.put("top"+i+"_owner", topOwner.toString());
					}
				}
			}
			// 能力行为低分项:本人评分
			for(int i=1;i<=lowParaList.size();i++) {
				if(ownerCsrr == null) {
					table6Map.put("low"+i+"_owner", "");
				} else {
					Object lowOwner = ReflectHelper.getValueByFieldName(ownerCsrr, lowParaList.get(i-1));
					if(ownerCsrr == null || lowOwner == null) {
						table6Map.put("low"+i+"_owner", "");
					} else {
						table6Map.put("low"+i+"_owner", lowOwner.toString());
					}
				}
			}
			
			// 自评和他评能力对比,根据 自评-他评 的数值倒序排列
			Map<String, Double> dvalueMap = Maps.newHashMap();
			if(compAbilityMap.get("owner") != null) {
				for(String abilityId : compAbilityMap.get("owner").keySet()) {
					dvalueMap.put(abilityId, compAbilityMap.get("owner").get(abilityId)-compAbilityMap.get("other").get(abilityId));
				}
			} else {
				for(String abilityId : compAbilityMap.get("other").keySet()) {
					dvalueMap.put(abilityId, compAbilityMap.get("other").get(abilityId));
				}
			}
			
			List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(dvalueMap.entrySet());
			Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
	            //降序排序
	            public int compare(Entry<String, Double> o1,
	                    Entry<String, Double> o2) {
	                return o2.getValue().compareTo(o1.getValue());
	            }
	        });
			
			int i = 1;
			for(Map.Entry<String,Double> comp : list) {
				String key = comp.getKey();
				table8Map.put("comp"+i+"_ability", abilityTitleMap.get(key));
				if(compAbilityMap.get("owner") != null) {
					table8Map.put("comp"+i+"_owner", BigDecimal.valueOf(compAbilityMap.get("owner").get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
				} else {
					table8Map.put("comp"+i+"_owner", "");
				}
				
				table8Map.put("comp"+i+"_other", BigDecimal.valueOf(compAbilityMap.get("other").get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
				table8Map.put("comp"+i+"_dvl", BigDecimal.valueOf(dvalueMap.get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
				i++;
			}
			
			// 遍历不同的评级数据，补充数据
			String[] raterStr = new String[]{"owner","higher","lower","coller"};
			for(String ratestr : raterStr) {
				if(table1Map.get("table1_"+ratestr) == null) {
					table1Map.put("table1_"+ratestr, "0");
				}
				if(table2Map.get("table2_skill_"+ratestr) == null) {
					table2Map.put("table2_skill_"+ratestr, "");
				}
				for(String abilityId : abilityTitleMap.keySet()) {
					if(table4Map.get(abilityId+"_"+ratestr) == null) {
						table4Map.put(abilityId+"_"+ratestr, "");
					}
				}
				for(String moduleId : moduleMap.keySet()) {
					if(table5Map.get(moduleId+"_"+ratestr) == null) {
						table5Map.put(moduleId+"_"+ratestr, "");
					}
				}
				
			}
			
			
			
			TestWordTemplate twt = new TestWordTemplate();
			if(haveLowRater) {
				if("7".equals(jobLevelId)) {
					twt.init(twt.DOCX_MODEL_7_PATH);
				} else if("6".equals(jobLevelId)) {
					twt.init(twt.DOCX_MODEL_6_PATH);
				} else if("5".equals(jobLevelId)) {
					twt.init(twt.DOCX_MODEL_5_PATH);
				}
			} else {
				if("7".equals(jobLevelId)) {
					twt.init(twt.DOCX_MODEL_7_NOLOW_PATH);
				} else if("6".equals(jobLevelId)) {
					twt.init(twt.DOCX_MODEL_6_NOLOW_PATH);
				} else if("5".equals(jobLevelId)) {
					twt.init(twt.DOCX_MODEL_5_NOLOW_PATH);
				}
			}
			
			System.out.println(table1Map.get("staff_name")+"____"+table1Map.get("staff_org"));
			twt.testReplaceTag(table1Map);
			twt.testReplaceTag(table2Map);
			twt.testReplaceTag(table3Map);
			twt.testReplaceTag(table4Map);
			twt.testReplaceTag(table5Map);
			twt.testReplaceTag(table6Map);
			twt.testReplaceTag(table7Map);
			twt.testReplaceTag(table8Map);
			twt.testReplaceTag(aveAbilityMap);
			twt.testReplaceTag(aveModuleMap);
			
			twt.testWrite(jobLevelId, "staff_"+staffId+".docx");
		}
		
	}
	
	public void exportStaffGroupAssess(String jobLevelId) throws NumberFormatException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CpStaffAssessType cpStaffAssessType = new CpStaffAssessType();
		cpStaffAssessType.setJobLevelId(jobLevelId);
		List<CpStaffAssessType> csatList = cpStaffAssessTypeDao.findList(cpStaffAssessType);
		CpStaffInfo cpStaffInfo = new CpStaffInfo();
		List<CpStaffInfo> csiList = cpStaffInfoDao.findList(cpStaffInfo);
		
		// 测评人员集合
		Map<String, CpStaffInfo> csiMap = Maps.newHashMap();
		for(CpStaffInfo csi : csiList) {
			csiMap.put(csi.getStaffId(), csi);
		}
		
		// 各项行为集合
		Map<String, CpStaffAssessType> actionTitleMap = Maps.newHashMap();
		// 各项能力集合
		Map<String, String> abilityTitleMap = Maps.newHashMap();
		// 各项能力评分集合
		Map<String, List<String>> abilityMap = Maps.newHashMap();
		// 各项模块评分集合
		Map<String, List<String>> moduleMap = Maps.newHashMap();
		
		for(CpStaffAssessType csat : csatList) {
			actionTitleMap.put(csat.getNumberName(), csat);
			abilityTitleMap.put(csat.getAbilityId(), csat.getAbilityName());
			
			if(abilityMap.get(csat.getAbilityId()) == null) {
				List<String> abilityList = Lists.newArrayList();
				abilityMap.put(csat.getAbilityId(), abilityList);
			}
			abilityMap.get(csat.getAbilityId()).add(csat.getNumberName());
			
			if(moduleMap.get(csat.getModuleId()) == null) {
				List<String> moduleList = Lists.newArrayList();
				moduleMap.put(csat.getModuleId(), moduleList);
			}
			moduleMap.get(csat.getModuleId()).add(csat.getNumberName());
		}
		
		// 取评分结果数据
		CpStaffResultReview csrrQuery = new CpStaffResultReview();
		csrrQuery.setJobLevelId(jobLevelId);
		List<CpStaffResultReview> csrrList = this.findList(csrrQuery);
		
		// 1.合并多人的评分记录
		Map<String, List<CpStaffResultReview>> csrrMap = Maps.newHashMap();
		for(CpStaffResultReview csrs : csrrList) {
			if(csrrMap.get(csrs.getRaterLevel()) == null) {
				List<CpStaffResultReview> list = Lists.newArrayList();
				csrrMap.put(csrs.getRaterLevel(), list);
			}
			csrrMap.get(csrs.getRaterLevel()).add(csrs);
		}

		// 自评集合
		List<CpStaffResultReview> csrrOwnerList = Lists.newArrayList();
		// 他评集合
		List<CpStaffResultReview> csrrOtherList = Lists.newArrayList();
		// 团队评分汇总集合
		Map<String, CpStaffResultReview> groupMap =Maps.newHashMap();
		// 成员专业能力评价集合
		Map<String, Map<String, String>> staffSkillMap =Maps.newHashMap();
		for(String raterLevel : csrrMap.keySet()) {
			CpStaffResultReview cpStaffResultReview = new CpStaffResultReview();
			//csrr.setStaffId(staffid);
			cpStaffResultReview.setRaterLevel(raterLevel);
			
			Map<String, Integer> numberCountMap =Maps.newHashMap();
			for(CpStaffResultReview csrs : csrrMap.get(raterLevel)) {
				cpStaffResultReview.setRaterNum(cpStaffResultReview.getRaterNum()+csrs.getRaterNum());
				cpStaffResultReview.setJobLevel(csrs.getJobLevel());
				cpStaffResultReview.setJobLevelId(csrs.getJobLevelId());
				cpStaffResultReview.setTemplateId(csrs.getTemplateId());

				// 自评集合
				if("本人".equals(csrs.getRaterLevel())) {
					csrrOwnerList.add(csrs);
				}
				// 他评集合
				if("他评".equals(csrs.getRaterLevel())) {
					csrrOtherList.add(csrs);
				}
				
				if(csrs.getNumber1() != 0) {
					numberCountMap.put("number1", numberCountMap.get("number1")==null?0+1:numberCountMap.get("number1")+1);
				}
				if(csrs.getNumber2() != 0) {
					numberCountMap.put("number2", numberCountMap.get("number2")==null?0+1:numberCountMap.get("number2")+1);
				}
				if(csrs.getNumber3() != 0) {
					numberCountMap.put("number3", numberCountMap.get("number3")==null?0+1:numberCountMap.get("number3")+1);
				}
				if(csrs.getNumber4() != 0) {
					numberCountMap.put("number4", numberCountMap.get("number4")==null?0+1:numberCountMap.get("number4")+1);
				}
				if(csrs.getNumber5() != 0) {
					numberCountMap.put("number5", numberCountMap.get("number5")==null?0+1:numberCountMap.get("number5")+1);
				}
				if(csrs.getNumber6() != 0) {
					numberCountMap.put("number6", numberCountMap.get("number6")==null?0+1:numberCountMap.get("number6")+1);
				}
				if(csrs.getNumber7() != 0) {
					numberCountMap.put("number7", numberCountMap.get("number7")==null?0+1:numberCountMap.get("number7")+1);
				}
				if(csrs.getNumber8() != 0) {
					numberCountMap.put("number8", numberCountMap.get("number8")==null?0+1:numberCountMap.get("number8")+1);
				}
				if(csrs.getNumber9() != 0) {
					numberCountMap.put("number9", numberCountMap.get("number9")==null?0+1:numberCountMap.get("number9")+1);
				}
				if(csrs.getNumber10() != 0) {
					numberCountMap.put("number10", numberCountMap.get("number10")==null?0+1:numberCountMap.get("number10")+1);
				}
				if(csrs.getNumber11() != 0) {
					numberCountMap.put("number11", numberCountMap.get("number11")==null?0+1:numberCountMap.get("number11")+1);
				}
				if(csrs.getNumber12() != 0) {
					numberCountMap.put("number12", numberCountMap.get("number12")==null?0+1:numberCountMap.get("number12")+1);
				}
				if(csrs.getNumber13() != 0) {
					numberCountMap.put("number13", numberCountMap.get("number13")==null?0+1:numberCountMap.get("number13")+1);
				}
				if(csrs.getNumber14() != 0) {
					numberCountMap.put("number14", numberCountMap.get("number14")==null?0+1:numberCountMap.get("number14")+1);
				}
				if(csrs.getNumber15() != 0) {
					numberCountMap.put("number15", numberCountMap.get("number15")==null?0+1:numberCountMap.get("number15")+1);
				}
				if(csrs.getNumber16() != 0) {
					numberCountMap.put("number16", numberCountMap.get("number16")==null?0+1:numberCountMap.get("number16")+1);
				}
				if(csrs.getNumber17() != 0) {
					numberCountMap.put("number17", numberCountMap.get("number17")==null?0+1:numberCountMap.get("number17")+1);
				}
				if(csrs.getNumber18() != 0) {
					numberCountMap.put("number18", numberCountMap.get("number18")==null?0+1:numberCountMap.get("number18")+1);
				}
				if(csrs.getNumber19() != 0) {
					numberCountMap.put("number19", numberCountMap.get("number19")==null?0+1:numberCountMap.get("number19")+1);
				}
				if(csrs.getNumber20() != 0) {
					numberCountMap.put("number20", numberCountMap.get("number20")==null?0+1:numberCountMap.get("number20")+1);
				}
				if(csrs.getNumber21() != 0) {
					numberCountMap.put("number21", numberCountMap.get("number21")==null?0+1:numberCountMap.get("number21")+1);
				}
				if(csrs.getNumber22() != 0) {
					numberCountMap.put("number22", numberCountMap.get("number22")==null?0+1:numberCountMap.get("number22")+1);
				}
				if(csrs.getNumber23() != 0) {
					numberCountMap.put("number23", numberCountMap.get("number23")==null?0+1:numberCountMap.get("number23")+1);
				}
				cpStaffResultReview.setNumber1(cpStaffResultReview.getNumber1()+csrs.getNumber1());
				cpStaffResultReview.setNumber2(cpStaffResultReview.getNumber2()+csrs.getNumber2());
				cpStaffResultReview.setNumber3(cpStaffResultReview.getNumber3()+csrs.getNumber3());
				cpStaffResultReview.setNumber4(cpStaffResultReview.getNumber4()+csrs.getNumber4());
				cpStaffResultReview.setNumber5(cpStaffResultReview.getNumber5()+csrs.getNumber5());
				cpStaffResultReview.setNumber6(cpStaffResultReview.getNumber6()+csrs.getNumber6());
				cpStaffResultReview.setNumber7(cpStaffResultReview.getNumber7()+csrs.getNumber7());
				cpStaffResultReview.setNumber8(cpStaffResultReview.getNumber8()+csrs.getNumber8());
				cpStaffResultReview.setNumber9(cpStaffResultReview.getNumber9()+csrs.getNumber9());
				cpStaffResultReview.setNumber10(cpStaffResultReview.getNumber10()+csrs.getNumber10());
				cpStaffResultReview.setNumber11(cpStaffResultReview.getNumber11()+csrs.getNumber11());
				cpStaffResultReview.setNumber12(cpStaffResultReview.getNumber12()+csrs.getNumber12());
				cpStaffResultReview.setNumber13(cpStaffResultReview.getNumber13()+csrs.getNumber13());
				cpStaffResultReview.setNumber14(cpStaffResultReview.getNumber14()+csrs.getNumber14());
				cpStaffResultReview.setNumber15(cpStaffResultReview.getNumber15()+csrs.getNumber15());
				cpStaffResultReview.setNumber16(cpStaffResultReview.getNumber16()+csrs.getNumber16());
				cpStaffResultReview.setNumber17(cpStaffResultReview.getNumber17()+csrs.getNumber17());
				cpStaffResultReview.setNumber18(cpStaffResultReview.getNumber18()+csrs.getNumber18());
				cpStaffResultReview.setNumber19(cpStaffResultReview.getNumber19()+csrs.getNumber19());
				cpStaffResultReview.setNumber20(cpStaffResultReview.getNumber20()+csrs.getNumber20());
				cpStaffResultReview.setNumber21(cpStaffResultReview.getNumber21()+csrs.getNumber21());
				cpStaffResultReview.setNumber22(cpStaffResultReview.getNumber22()+csrs.getNumber22());
				cpStaffResultReview.setNumber23(cpStaffResultReview.getNumber23()+csrs.getNumber23());
				//cpStaffResultReview.setNumber24(cpStaffResultReview.getNumber24()+csrs.getNumber24());
				if(staffSkillMap.get(csrs.getStaffName()) == null) {
					Map<String, String> skillMap = Maps.newHashMap();
					staffSkillMap.put(csrs.getStaffName(), skillMap);
				}
				// 个人专业技能
				char [] arr =csrs.getRemark1().toCharArray();
				Map<String, Integer> skillCountMap = Maps.newHashMap();
				for (char c : arr) {
					String skillId = String.valueOf(c);
					if("1,2,3,4".contains(skillId)) {skillId = "A"+skillId; }
					if(!skillCountMap.containsKey(skillId)) skillCountMap.put(skillId, 0);
					skillCountMap.put(skillId, skillCountMap.get(skillId)+1);
				}
				List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(skillCountMap.entrySet());
				Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
		            //降序排序
		            public int compare(Entry<String, Integer> o1,
		                    Entry<String, Integer> o2) {
		                return o2.getValue().compareTo(o1.getValue());
		            }
		        });
				String staffSkill = "";
		        for(Map.Entry<String,Integer> comp : list) {
		        	staffSkill = staffSkill + comp.getKey() + ":" + comp.getValue() + "  ";
		        }
				staffSkillMap.get(csrs.getStaffName()).put(csrs.getRaterLevel(), staffSkill);
			}
			
			if(numberCountMap.get("number1") != null)
				cpStaffResultReview.setNumber1(cpStaffResultReview.getNumber1()/numberCountMap.get("number1"));
			if(numberCountMap.get("number2") != null)
				cpStaffResultReview.setNumber2(cpStaffResultReview.getNumber2()/numberCountMap.get("number2"));
			if(numberCountMap.get("number3") != null)
				cpStaffResultReview.setNumber3(cpStaffResultReview.getNumber3()/numberCountMap.get("number3"));
			if(numberCountMap.get("number4") != null)
				cpStaffResultReview.setNumber4(cpStaffResultReview.getNumber4()/numberCountMap.get("number4"));
			if(numberCountMap.get("number5") != null)
				cpStaffResultReview.setNumber5(cpStaffResultReview.getNumber5()/numberCountMap.get("number5"));
			if(numberCountMap.get("number6") != null)
				cpStaffResultReview.setNumber6(cpStaffResultReview.getNumber6()/numberCountMap.get("number6"));
			if(numberCountMap.get("number7") != null)
				cpStaffResultReview.setNumber7(cpStaffResultReview.getNumber7()/numberCountMap.get("number7"));
			if(numberCountMap.get("number8") != null)
				cpStaffResultReview.setNumber8(cpStaffResultReview.getNumber8()/numberCountMap.get("number8"));
			if(numberCountMap.get("number9") != null)
				cpStaffResultReview.setNumber9(cpStaffResultReview.getNumber9()/numberCountMap.get("number9"));
			if(numberCountMap.get("number10") != null)
				cpStaffResultReview.setNumber10(cpStaffResultReview.getNumber10()/numberCountMap.get("number10"));
			if(numberCountMap.get("number11") != null)
				cpStaffResultReview.setNumber11(cpStaffResultReview.getNumber11()/numberCountMap.get("number11"));
			if(numberCountMap.get("number12") != null)
				cpStaffResultReview.setNumber12(cpStaffResultReview.getNumber12()/numberCountMap.get("number12"));
			if(numberCountMap.get("number13") != null)
				cpStaffResultReview.setNumber13(cpStaffResultReview.getNumber13()/numberCountMap.get("number13"));
			if(numberCountMap.get("number14") != null)
				cpStaffResultReview.setNumber14(cpStaffResultReview.getNumber14()/numberCountMap.get("number14"));
			if(numberCountMap.get("number15") != null)
				cpStaffResultReview.setNumber15(cpStaffResultReview.getNumber15()/numberCountMap.get("number15"));
			if(numberCountMap.get("number16") != null)
				cpStaffResultReview.setNumber16(cpStaffResultReview.getNumber16()/numberCountMap.get("number16"));
			if(numberCountMap.get("number17") != null)
				cpStaffResultReview.setNumber17(cpStaffResultReview.getNumber17()/numberCountMap.get("number17"));
			if(numberCountMap.get("number18") != null)
				cpStaffResultReview.setNumber18(cpStaffResultReview.getNumber18()/numberCountMap.get("number18"));
			if(numberCountMap.get("number19") != null)
				cpStaffResultReview.setNumber19(cpStaffResultReview.getNumber19()/numberCountMap.get("number19"));
			if(numberCountMap.get("number20") != null)
				cpStaffResultReview.setNumber20(cpStaffResultReview.getNumber20()/numberCountMap.get("number20"));
			if(numberCountMap.get("number21") != null)
				cpStaffResultReview.setNumber21(cpStaffResultReview.getNumber21()/numberCountMap.get("number21"));
			if(numberCountMap.get("number22") != null)
				cpStaffResultReview.setNumber22(cpStaffResultReview.getNumber22()/numberCountMap.get("number22"));
			if(numberCountMap.get("number23") != null)
				cpStaffResultReview.setNumber23(cpStaffResultReview.getNumber23()/numberCountMap.get("number23"));
			//cpStaffResultReview.setNumber24(cpStaffResultReview.getNumber24()/numberCountMap.get("number24"));
			
			// 大均值
			Double grandMean = (cpStaffResultReview.getNumber1()+cpStaffResultReview.getNumber2()+cpStaffResultReview.getNumber3()+cpStaffResultReview.getNumber4()
							+cpStaffResultReview.getNumber5()+cpStaffResultReview.getNumber6()+cpStaffResultReview.getNumber7()+cpStaffResultReview.getNumber8()
							+cpStaffResultReview.getNumber9()+cpStaffResultReview.getNumber10()+cpStaffResultReview.getNumber11()+cpStaffResultReview.getNumber12()
							+cpStaffResultReview.getNumber13()+cpStaffResultReview.getNumber14()+cpStaffResultReview.getNumber15()+cpStaffResultReview.getNumber16()
							+cpStaffResultReview.getNumber17()+cpStaffResultReview.getNumber18()+cpStaffResultReview.getNumber19()+cpStaffResultReview.getNumber20()
							+cpStaffResultReview.getNumber21()+cpStaffResultReview.getNumber22()+cpStaffResultReview.getNumber23())/23;
			cpStaffResultReview.setGrandMean(grandMean);
			
			groupMap.put(raterLevel, cpStaffResultReview);
		}
		
		// 团队中个人专业技能集合 
		Map<String, String> skillMap = Maps.newHashMap();
		int nums = 1;
		for(String staffName : staffSkillMap.keySet()) {
			skillMap.put("skill_staff"+nums, staffName);
			String[] raterStr = new String[]{"owner","higher","lower","coller","other"};
			for(String key : raterStr) {
				String raterKey = "";
				if("owner".equals(key)) {
					raterKey = "本人";
				} else if("higher".equals(key)) {
					raterKey = "上级";
				} else if("lower".equals(key)) {
					raterKey = "下级";
				} else if("coller".equals(key)) {
					raterKey = "同事";
				} else if("other".equals(key)) {
					raterKey = "他评";
				}
				
				if(staffSkillMap.get(staffName).get(raterKey) != null) {
					skillMap.put("skill_"+key+nums, staffSkillMap.get(staffName).get(raterKey));
				} else {
					skillMap.put("skill_"+key+nums, "");
				}
			}
			nums ++;
		}
		
		// 技术方向和能力水平统计
		Map<String, String> skillTotolMap = Maps.newHashMap();
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234";
		char[] skillChar = chars.toCharArray();
		for(char c : skillChar) {
			String str = String.valueOf(c);
			int ownerCount = 0;
			String ownerStaff = "";
			for(CpStaffResultReview owner : csrrOwnerList) {
				if(owner.getRemark1().contains(str)) {
					ownerCount ++;
					ownerStaff = ownerStaff + " " +owner.getStaffName();
				}
			}
			skillTotolMap.put("owner_count_"+str, ownerCount+"");
			skillTotolMap.put("owner_staff_"+str, ownerStaff+"");
			int otherCount = 0;
			String otherStaff = "";
			for(CpStaffResultReview other : csrrOtherList) {
				if(other.getRemark1().contains(str)) {
					otherCount ++;
					otherStaff = otherStaff + " " +other.getStaffName();
				}
			}
			skillTotolMap.put("other_count_"+str, otherCount+"");
			skillTotolMap.put("other_staff_"+str, otherStaff+"");
			
		}
		
		// 整体大均值、团队成员大均值排名 <姓名，[他评大均值，百分位]>
		Map<String, String[]> groupAveMap = Maps.newHashMap();
		// 团队整体的他评大均值
		BigDecimal sumOtherGrand = BigDecimal.ZERO;
		for(CpStaffResultReview other : csrrOtherList) {
			sumOtherGrand = sumOtherGrand.add(BigDecimal.valueOf(other.getGrandMean()));
			int count = 0 ;
			for(CpStaffResultReview csrrOther : csrrOtherList) {
				if(csrrOther.getGrandMean() < other.getGrandMean()) {
					count ++;
				}
			}
			String[] aveStr = new String[2];
			aveStr[0] = BigDecimal.valueOf(other.getGrandMean()).setScale(2, BigDecimal.ROUND_HALF_UP)+"";
			aveStr[1] = BigDecimal.valueOf(count*100).divide(BigDecimal.valueOf(csrrOtherList.size()), 0, BigDecimal.ROUND_HALF_UP)+"%";
			groupAveMap.put(other.getStaffId(), aveStr);
		}
		
		List<Map.Entry<String,String[]>> orderlist = new ArrayList<Map.Entry<String,String[]>>(groupAveMap.entrySet());
		Collections.sort(orderlist,new Comparator<Map.Entry<String,String[]>>() {
            //降序排序
            public int compare(Entry<String, String[]> o1,
                    Entry<String, String[]> o2) {
                return o2.getValue()[0].compareTo(o1.getValue()[0]);
            }
            
        });
		
		// 整体大均值和百分位，专业能力数据
		Map<String, String> table2Map = Maps.newHashMap();
		int num = 1;
		for(Map.Entry<String,String[]> order : orderlist) {
			String staffId = order.getKey();
			table2Map.put("org"+num, csiMap.get(staffId).getFullOrgName());
			table2Map.put("jobLevel"+num, csiMap.get(staffId).getRemark2());
			table2Map.put("staff"+num, csiMap.get(staffId).getStaffName());
			table2Map.put("grandMean"+num, order.getValue()[0]);
			table2Map.put("percent"+num, order.getValue()[1]);
			num++;
		}

		// 团队整体的大均值--他评
		table2Map.put("ave_grandMean", sumOtherGrand.divide(BigDecimal.valueOf(csrrOtherList.size()), 2, BigDecimal.ROUND_HALF_UP)+"");
		
		// 测评结果来源
		Map<String, String> table1Map = Maps.newHashMap();
		// 各项能力行为得分 <行为描述项评委类型，得分>	
		Map<String, String> table3Map = Maps.newHashMap();
		// 各项能力得分 <能力项评委类型，得分>	
		Map<String, String> table4Map = Maps.newHashMap();
		// 各模块维度得分得分 <模块项评委类型，得分>	
		Map<String, String> table5Map = Maps.newHashMap();
		// 能力行为高分项 <行为描述项ID评委类型，得分>	
		Map<String, String> table6Map = Maps.newHashMap();
		// 能力行为低分项 <行为描述项ID评委类型，得分>	
		Map<String, String> table7Map = Maps.newHashMap();
		// 自评和他评能力对比 <行为描述项ID评委类型，得分>	
		Map<String, String> table8Map = Maps.newHashMap();
		
		String rater = "";
		CpStaffResultReview ownerCsrr = null; // 自评分记录
		List<String> topParaList = Lists.newArrayList(); // top5能力行为项
		List<String> lowParaList = Lists.newArrayList(); // low5能力行为项
		Map<String, Map<String, Double>> compAbilityMap = Maps.newHashMap();
		for(CpStaffResultReview csrr : groupMap.values()) {

			if("本人".equals(csrr.getRaterLevel())) {
				rater = "owner";
				ownerCsrr = csrr;
			} else if("上级".equals(csrr.getRaterLevel())) {
				rater = "higher";
			} else if("下级".equals(csrr.getRaterLevel())) {
				rater = "lower";
			} else if("同事".equals(csrr.getRaterLevel())) {
				rater = "coller";
			} else if("他评".equals(csrr.getRaterLevel())) {
				rater = "other";
				
				// 获取他评的高分项（Top5）
				List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(csrr.numberMap().entrySet());
				Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
		            //降序排序
		            public int compare(Entry<String, String> o1,
		                    Entry<String, String> o2) {
		                return o2.getValue().compareTo(o1.getValue());
		            }
		            
		        });
				
				int i = 1;
				for(Map.Entry<String,String> top : list) {
					if(i>5) break;
					if(!actionTitleMap.keySet().contains(top.getKey())) continue;
					table6Map.put("top"+i+"_"+rater, new BigDecimal(top.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
					table6Map.put("top"+i+"_action", actionTitleMap.get(top.getKey()).getActionName());
					table6Map.put("top"+i+"_ability", actionTitleMap.get(top.getKey()).getAbilityName());
					
					topParaList.add(top.getKey());
					i++;
				}
				
				//  获取他评的低分项（Top5）
				Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
		            //升序排序
		            public int compare(Entry<String, String> o1,
		                    Entry<String, String> o2) {
		                return o1.getValue().compareTo(o2.getValue());
		            }
		            
		        });
				
				int j = 1;
				for(Map.Entry<String,String> low : list) {
					if(j>5) break;
					if(!actionTitleMap.keySet().contains(low.getKey())) continue;
					table7Map.put("low"+j+"_"+rater, new BigDecimal(low.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
					table7Map.put("low"+j+"_action", actionTitleMap.get(low.getKey()).getActionName());
					table7Map.put("low"+j+"_ability", actionTitleMap.get(low.getKey()).getAbilityName());

					lowParaList.add(low.getKey());
					j++;
				}
				
			}
			
			table1Map.put("table1_"+rater, csrr.getRaterNum()+"");
			
			table3Map.put("action1_"+rater, csrr.getNumber1()+"");
			table3Map.put("action2_"+rater, csrr.getNumber2()+"");
			table3Map.put("action3_"+rater, csrr.getNumber3()+"");
			table3Map.put("action4_"+rater, csrr.getNumber4()+"");
			table3Map.put("action5_"+rater, csrr.getNumber5()+"");
			table3Map.put("action6_"+rater, csrr.getNumber6()+"");
			table3Map.put("action7_"+rater, csrr.getNumber7()+"");
			table3Map.put("action8_"+rater, csrr.getNumber8()+"");
			table3Map.put("action9_"+rater, csrr.getNumber9()+"");
			table3Map.put("action10_"+rater, csrr.getNumber10()+"");
			table3Map.put("action11_"+rater, csrr.getNumber11()+"");
			table3Map.put("action12_"+rater, csrr.getNumber12()+"");
			table3Map.put("action13_"+rater, csrr.getNumber13()+"");
			table3Map.put("action14_"+rater, csrr.getNumber14()+"");
			table3Map.put("action15_"+rater, csrr.getNumber15()+"");
			table3Map.put("action15_"+rater, csrr.getNumber16()+"");
			table3Map.put("action17_"+rater, csrr.getNumber17()+"");
			table3Map.put("action18_"+rater, csrr.getNumber18()+"");
			table3Map.put("action19_"+rater, csrr.getNumber19()+"");
			table3Map.put("action20_"+rater, csrr.getNumber20()+"");
			table3Map.put("action21_"+rater, csrr.getNumber21()+"");
			table3Map.put("action22_"+rater, csrr.getNumber22()+"");
			table3Map.put("action23_"+rater, csrr.getNumber23()+"");
			
			// 各项能力得分
			for(String abilityId : abilityMap.keySet()) {
				Double scores = 0.0;
				for(String number : abilityMap.get(abilityId)) {
					scores = scores + Double.valueOf(ReflectHelper.getValueByFieldName(csrr, number).toString());
				}
				table4Map.put(abilityId+"_"+rater, BigDecimal.valueOf(scores/abilityMap.get(abilityId).size()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
				
				if(compAbilityMap.get(rater) == null) {
					Map<String, Double> comabMap = Maps.newHashMap();
					compAbilityMap.put(rater, comabMap);
				}
				compAbilityMap.get(rater).put(abilityId, scores/abilityMap.get(abilityId).size());
				
			}

			// 各维度得分
			for(String moduleId : moduleMap.keySet()) {
				Double scores = 0.0;
				for(String number : moduleMap.get(moduleId)) {
					scores = scores + Double.valueOf(ReflectHelper.getValueByFieldName(csrr, number).toString());
				}
				table5Map.put(moduleId+"_"+rater, BigDecimal.valueOf(scores/moduleMap.get(moduleId).size()).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
			}
			//table5Map.put("percent_"+rater, csrr.getGrandMean()+""); // 大均值
			
		}
		
		// 能力行为高分项:本人评分
		for(int i=1;i<=topParaList.size();i++) {
			String ownerTop = ReflectHelper.getValueByFieldName(ownerCsrr, topParaList.get(i-1)).toString();
			table6Map.put("top"+i+"_owner", new BigDecimal(ownerTop).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
		}
		// 能力行为低分项:本人评分
		for(int i=1;i<=lowParaList.size();i++) {
			String ownerLow = ReflectHelper.getValueByFieldName(ownerCsrr, lowParaList.get(i-1)).toString();
			table7Map.put("low"+i+"_owner", new BigDecimal(ownerLow).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
		}
		
		// 自评和他评能力对比,根据 自评-他评 的数值倒序排列
		Map<String, Double> dvalueMap = Maps.newHashMap();
		for(String abilityId : compAbilityMap.get("owner").keySet()) {
			dvalueMap.put(abilityId, compAbilityMap.get("owner").get(abilityId)-compAbilityMap.get("other").get(abilityId));
		}
		
		List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(dvalueMap.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
            //降序排序
            public int compare(Entry<String, Double> o1,
                    Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
		
		int i = 1;
		for(Map.Entry<String,Double> comp : list) {
			String key = comp.getKey();
			table8Map.put("comp"+i+"_ability", abilityTitleMap.get(key));
			table8Map.put("comp"+i+"_owner", BigDecimal.valueOf(compAbilityMap.get("owner").get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
			table8Map.put("comp"+i+"_other", BigDecimal.valueOf(compAbilityMap.get("other").get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
			table8Map.put("comp"+i+"_dvl", BigDecimal.valueOf(dvalueMap.get(key)).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
			i++;
		}
		
		
		TestWordTemplate twt = new TestWordTemplate();
		if("7".equals(jobLevelId)) {
			twt.init(twt.DOCX_MODEL_7_GROUP_PATH);
		} else if("6".equals(jobLevelId)) {
			twt.init(twt.DOCX_MODEL_6_GROUP_PATH);
		} else if("5".equals(jobLevelId)) {
			twt.init(twt.DOCX_MODEL_5_GROUP_PATH);
		}
		twt.testReplaceTag(table1Map);
		twt.testReplaceTag(table2Map);
		//twt.testReplaceTag(table3Map);
		// 团体各项能力得分 均值
		for(String abilityId : abilityMap.keySet()) {
			BigDecimal owner = new BigDecimal(table4Map.get(abilityId+"_owner"));
			BigDecimal other = new BigDecimal(table4Map.get(abilityId+"_other"));
			table4Map.put(abilityId+"_ave", (owner.add(other)).divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP)+"");
		}
		
		twt.testReplaceTag(table4Map);
		// 团体各维度得分 均值
		for(String moduleId : moduleMap.keySet()) {
			BigDecimal owner = new BigDecimal(table5Map.get(moduleId+"_owner"));
			BigDecimal other = new BigDecimal(table5Map.get(moduleId+"_other"));
			table5Map.put(moduleId+"_ave", (owner.add(other)).divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP)+"");
		}
		
		twt.testReplaceTag(table5Map);
		twt.testReplaceTag(table6Map);
		twt.testReplaceTag(table7Map);
		twt.testReplaceTag(table8Map);
		twt.testReplaceTag(skillMap);
		twt.testReplaceTag(skillTotolMap);
		
		twt.testWrite(jobLevelId, "staff_group"+jobLevelId+".docx");
	
		
	}
	
	private Double actionRater(String raterLevel, String file, Map<String,Map<String,Double>> actionRatermap, boolean haveLowRater) {
		if(haveLowRater) {
			return  actionRatermap.get(raterLevel).get(file) == null ? 0.0 : actionRatermap.get(raterLevel).get(file);
		} else {
			return "上级".equals(raterLevel) ? 0.6 : 0.4;
		}
	}
	
	/**
	 * 字符串去重
	 * @param source
	 * @return
	 */
	private String duplicatRemoval(String source)
    {
        String[] str = source.split("");
        if (str.length == 0 )
        {
            return null;
        }
        List<String> list = Lists.newArrayList();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++)
        {
            if (!list.contains(str[i]))
            {
                list.add(str[i]);
                sb.append(str[i]);
            }  
        }
        return sb.toString();
    }
	
}