package com.dobo.modules.cop.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.IdGen;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cop.entity.detail.CopCaseDetail;
import com.dobo.modules.cop.entity.detail.CopCaseDetailPrice;
import com.dobo.modules.cop.entity.detail.CopCasePriceConfirm;
import com.dobo.modules.cop.entity.detail.CopSalesAccount;
import com.dobo.modules.cop.entity.detail.CopSalesOrgProject;
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;
import com.dobo.modules.cop.rest.entity.CopCaseData;
import com.dobo.modules.cop.rest.entity.CostData;
import com.dobo.modules.cop.service.detail.CopCaseDetailPriceService;
import com.dobo.modules.cop.service.detail.CopCaseDetailService;
import com.dobo.modules.cop.service.detail.CopCasePriceConfirmService;
import com.dobo.modules.cop.service.detail.CopSalesAccountService;
import com.dobo.modules.cop.service.detail.CopSalesOrgAccountService;
import com.dobo.modules.cop.service.detail.CopSalesOrgProjectService;
import com.dobo.modules.cop.service.detail.CopSalesUseDetailService;
import com.dobo.modules.cst.service.detail.CstOrderCostInfoService;
import com.dobo.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RestController
public class WbmCasePriceService {
	
	@Autowired
	CopCaseDetailService copCaseDetailService;
	@Autowired
	CopCaseDetailPriceService copCaseDetailPriceService;
	@Autowired
	CopCasePriceConfirmService copCasePriceConfirmService;
	@Autowired
	CstOrderCostInfoService cstOrderCostInfoService;
	@Autowired
	CopSalesAccountService copSalesAccountService;
	@Autowired
	CopSalesOrgAccountService copSalesOrgAccountService;
	@Autowired
	CopSalesOrgProjectService copSalesOrgProjectService;
	@Autowired
	CopSalesUseDetailService copSalesUseDetailService;

	/**
	 * 报价接口（人工）
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/service/wbm.case.manPrice", method = RequestMethod.POST, produces = "application/json")
	public String getManDetailPrice(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		
		String line;
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		User user = new User("admin");
		Date date = new Date();
		String retVal = "";
		String responseJsonStr = "";
			
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

			while ((line = br.readLine()) != null) 
				sb.append(line);
			
			br.close();
			responseJsonStr = sb.toString();
			
			JSONObject jsonObject = JSONObject.parseObject(responseJsonStr);
			// 先判断报价是否对应到具体存在的销售员名下
			String salesId = StringUtils.toString(jsonObject.get("salesId"));
			
			Map<String, String> staffMap = copSalesOrgAccountService.getSalesByCase(salesId);
			
			if(staffMap == null || staffMap.size() == 0) {
				json.put("code", 11);
				json.put("message", "失败， 未找到["+salesId+"]该销售员,请联系交付宝系统管理员。");
				retVal = json.toJSONString();
				return retVal;
			}
			
			// 报价类型：正常报价=normal 取消=cancel 核销=write_off 转先行支持=prepay
			String invokeType = StringUtils.toString(jsonObject.get("invoke_type"));
			if(invokeType == null || "".equals(invokeType)) invokeType = "normal"; 
			
			//检查CASE单次报价明细表中单次报价ID是否已存在
			String onceNum = StringUtils.toString(jsonObject.get("onceNum"));
			CopCaseDetail copDetail = new CopCaseDetail();
			copDetail.setOnceNum(onceNum);
			List<CopCaseDetail> copDetailList = copCaseDetailService.findList(copDetail);
			
			if("cancel,write_off,prepay".contains(invokeType)){
				if(copDetailList == null || copDetailList.isEmpty()) {
					json.put("code", 10);
					json.put("message", "失败， 未找到该单次报价明细。");
					retVal = json.toJSONString();
					return retVal;
				} else {
					// 多次核销检查是否已经核销完
					for(CopCaseDetail ccd : copDetailList) {
						if(ccd.getManWorkload() < 0 || ccd.getManTravelPrice() < 0) {
							json.put("code", 10);
							json.put("message", "失败， 该单次报价已经核销，不能再次核销。");
							retVal = json.toJSONString();
							return retVal;
						}
					}
				}
				
			}
			
			if("normal".contains(invokeType) && copDetailList != null && !copDetailList.isEmpty()){
				for(CopCaseDetail copCaseDetail : copDetailList){
					copCaseDetail.setPreStatus(copCaseDetail.getStatus());
					copCaseDetail.setStatusChangeDate(date);
					copCaseDetail.setStatus("A1");
					copCaseDetailService.save(copCaseDetail);
				}
			}
			
			//检查CASE单次报价表中单次报价ID是否已存在
			CopCaseDetailPrice copDetailPrice = new CopCaseDetailPrice();
			copDetailPrice.setOnceNum(onceNum);
			List<CopCaseDetailPrice> copDetailPriceList = copCaseDetailPriceService.findList(copDetailPrice);
			if("normal".contains(invokeType) && copDetailPriceList != null && !copDetailPriceList.isEmpty()){
				for(CopCaseDetailPrice copCasePrice : copDetailPriceList){
					copCasePrice.setPreStatus(copCasePrice.getStatus());
					copCasePrice.setStatusChangeDate(date);
					copCasePrice.setStatus("A1");
					copCaseDetailPriceService.save(copCasePrice);
				}
			}
			
			String isSpecialAudit = StringUtils.toString(jsonObject.get("specialApproved"));
			
			//解析报价数组
			List<CopCaseDetail> copCaseDetailList = new ArrayList<CopCaseDetail>();
			// 核销单次需要同步厂商型号序列号
			Map<String, CopCaseDetail> newCCDMap = Maps.newHashMap();
			JSONArray data = (JSONArray) jsonObject.get("data");
			List<CopCaseData> copCaseDataList = data.toJavaList(CopCaseData.class);
			for(CopCaseData copCaseData : copCaseDataList){
				CopCaseDetail copCaseDetail = new CopCaseDetail();
				copCaseDetail.setPriceNum(copCaseData.getNum());
				copCaseDetail.setCaseId((StringUtils.toString(jsonObject.get("caseId"))));
				copCaseDetail.setCaseCode(StringUtils.toString(jsonObject.get("caseCode")));
				copCaseDetail.setOnceNum((StringUtils.toString(jsonObject.get("onceNum"))));
				copCaseDetail.setOnceCode(StringUtils.toString(jsonObject.get("onceCode")));
				copCaseDetail.setPriceType("1");
				copCaseDetail.setServiceType(copCaseData.getServiceType());
				
				copCaseDetail.setDcPrjId(StringUtils.toString(jsonObject.get("projectCode")));
				copCaseDetail.setDcPrjName(StringUtils.toString(jsonObject.get("projectName")));
				copCaseDetail.setServiceContent(StringUtils.toString(jsonObject.get("serviceContent")));
				copCaseDetail.setPartsInfo(StringUtils.toString(jsonObject.get("partsInfo")));
				copCaseDetail.setFactoryId((StringUtils.toString(jsonObject.get("factoryId"))));
				copCaseDetail.setFactoryName(StringUtils.toString(jsonObject.get("factoryName")));
				copCaseDetail.setModelId((StringUtils.toString(jsonObject.get("modelId"))));
				copCaseDetail.setModelName(StringUtils.toString(jsonObject.get("modelName")));
				copCaseDetail.setSnId(StringUtils.toString(jsonObject.get("Sn")));
				copCaseDetail.setIsSpecialAudit(isSpecialAudit);
				copCaseDetail.setSpecialAuditRemark(StringUtils.toString(jsonObject.get("specialApprovedRemark")));
				copCaseDetail.setWoProjectCode(StringUtils.toString(jsonObject.get("writeOffProjectCode")));
				copCaseDetail.setWoProjectName(StringUtils.toString(jsonObject.get("writeOffProjectName")));
				copCaseDetail.setWoReason(StringUtils.toString(jsonObject.get("writeOffRemark")));
				copCaseDetail.setWoApprovalBy(StringUtils.toString(jsonObject.get("approvalBy")));
				String approvalDate = StringUtils.toString(jsonObject.get("approvalOn"));
				if(approvalDate != null && !"".equals(approvalDate)) {
					copCaseDetail.setWoApprovalDate(DateUtils.parseDate(approvalDate));
				}
				copCaseDetail.setWoApprovalRemark(StringUtils.toString(jsonObject.get("approvalRemark")));
				copCaseDetail.setInvokeType(invokeType);
				
				copCaseDetail.setManRoleId(copCaseData.getRoleId());
				copCaseDetail.setManEngineerLevel(copCaseData.getEngineerLevel());
				copCaseDetail.setManResourceType(copCaseData.getResourceType());
				copCaseDetail.setManWorkload(copCaseData.getWorkload()==null?(double) 0 : copCaseData.getWorkload());
				copCaseDetail.setManPrice(copCaseData.getPrice()==null?(double) 0 : copCaseData.getPrice());
				copCaseDetail.setManTravelPrice(copCaseData.getTravelPrice()==null?(double) 0 : copCaseData.getTravelPrice());
				
				copCaseDetail.setStatus("A0");
				copCaseDetail.setCreateBy(user);
				copCaseDetail.setCreateDate(date);
				copCaseDetail.setUpdateBy(user);
				copCaseDetail.setUpdateDate(date);
				copCaseDetailList.add(copCaseDetail);
				
				if("normal".contains(invokeType)) {
					copCaseDetailService.save(copCaseDetail);
				} else {
					copCaseDetail.setId(IdGen.uuid());
					copCaseDetail.setDelFlag("0");
					copCaseDetail.setSaveFlag("1");
					
					newCCDMap.put(copCaseData.getNum(), copCaseDetail);
				}
			}
			
			Double wbmCostPrepay = (double) 0;
			Double wbmCostPrepayTravel = (double) 0;
			Double wbmCostFt = (double) 0;
			Double wbmCostFtTravel = (double) 0;
			Double wbmCostFtToProject = (double) 0;
			Double wbmCostFtToProjectTravel = (double) 0;
			
			//返回CASE单次报价结果
			String prodId = "RXSA-CASE-SUPPORT_PRICE";
			Map<String, List<CopCaseDetailPrice>> copCaseDetailPriceMap = CstOrderCostInfoService.getCalculateCaseDetailPrice(copCaseDetailList, prodId);
			
			// 报价核销和取消操作--报价明细
			Map<String, String> idToPriceNumMap = Maps.newHashMap();
			if("cancel,write_off,prepay".contains(invokeType)){
				// 根据2次报价明细的差额补充核销退的数据
				for(CopCaseDetail copCaseDetail1 : copDetailList){
					// 核销同步厂商型号序列号
					CopCaseDetail newCCD = newCCDMap.get(copCaseDetail1.getPriceNum());
					copCaseDetail1.setFactoryId(newCCD.getFactoryId());
					copCaseDetail1.setFactoryName(newCCD.getFactoryName());
					copCaseDetail1.setModelId(newCCD.getModelId());
					copCaseDetail1.setModelName(newCCD.getModelName());
					copCaseDetail1.setSnId(newCCD.getSnId());
					copCaseDetailService.save(copCaseDetail1);
					
					boolean hasItem = false;
					if(isSpecialAudit == null || !"1".equals(isSpecialAudit)) { // 特批单次的核销为全部核销
						for(CopCaseDetail copCaseDetail2 : copCaseDetailList) {
							if(copCaseDetail1.getPriceNum().equals(copCaseDetail2.getPriceNum())) {
								copCaseDetail2.setManWorkload(copCaseDetail2.getManWorkload()-copCaseDetail1.getManWorkload());
								copCaseDetail2.setManPrice(copCaseDetail2.getManPrice()-copCaseDetail1.getManPrice());
								copCaseDetail2.setManTravelPrice(copCaseDetail2.getManTravelPrice()-copCaseDetail1.getManTravelPrice());
								
								copCaseDetailService.insert(copCaseDetail2);
								hasItem = true;
							}
						}
					}
					// 全部核销扣减
					if(!hasItem) {
						CopCaseDetail copCaseDetail = new CopCaseDetail();
						copCaseDetail.setPriceNum(copCaseDetail1.getPriceNum());
						copCaseDetail.setCaseId(copCaseDetail1.getCaseId());
						copCaseDetail.setCaseCode(copCaseDetail1.getCaseCode());
						copCaseDetail.setOnceNum(copCaseDetail1.getOnceNum());
						copCaseDetail.setOnceCode(copCaseDetail1.getOnceCode());
						copCaseDetail.setPriceType(copCaseDetail1.getPriceType());
						copCaseDetail.setServiceType(copCaseDetail1.getServiceType());
						copCaseDetail.setDcPrjId(copCaseDetail1.getDcPrjId());
						copCaseDetail.setDcPrjName(copCaseDetail1.getDcPrjName());
						copCaseDetail.setServiceContent(copCaseDetail1.getServiceContent());
						copCaseDetail.setPartsInfo(copCaseDetail1.getPartsInfo());
						copCaseDetail.setFactoryId(newCCD.getFactoryId());
						copCaseDetail.setFactoryName(newCCD.getFactoryName());
						copCaseDetail.setModelId(newCCD.getModelId());
						copCaseDetail.setModelName(newCCD.getModelName());
						copCaseDetail.setSnId(newCCD.getSnId());
						copCaseDetail.setIsSpecialAudit(copCaseDetail1.getIsSpecialAudit());
						copCaseDetail.setSpecialAuditRemark(copCaseDetail1.getSpecialAuditRemark());
						copCaseDetail.setWoProjectCode(StringUtils.toString(jsonObject.get("writeOffProjectCode")));
						copCaseDetail.setWoProjectName(StringUtils.toString(jsonObject.get("writeOffProjectName")));
						copCaseDetail.setWoReason(StringUtils.toString(jsonObject.get("writeOffRemark")));
						copCaseDetail.setWoApprovalBy(StringUtils.toString(jsonObject.get("approvalBy")));
						String approvalDate = StringUtils.toString(jsonObject.get("approvalOn"));
						if(approvalDate != null && !"".equals(approvalDate)) {
							copCaseDetail.setWoApprovalDate(DateUtils.parseDate(approvalDate));
						}
						copCaseDetail.setWoApprovalRemark(StringUtils.toString(jsonObject.get("approvalRemark")));
						copCaseDetail.setInvokeType(invokeType);
						copCaseDetail.setManRoleId(copCaseDetail1.getManRoleId());
						copCaseDetail.setManEngineerLevel(copCaseDetail1.getManEngineerLevel());
						copCaseDetail.setManResourceType(copCaseDetail1.getManResourceType());
						copCaseDetail.setManWorkload(0-copCaseDetail1.getManWorkload());
						copCaseDetail.setManPrice(0-copCaseDetail1.getManPrice());
						copCaseDetail.setManTravelPrice(0-copCaseDetail1.getManTravelPrice());
						copCaseDetail.setStatus("A0");
						copCaseDetail.setCreateBy(user);
						copCaseDetail.setCreateDate(date);
						copCaseDetail.setUpdateBy(user);
						copCaseDetail.setUpdateDate(date);
						
						copCaseDetailService.save(copCaseDetail);
						idToPriceNumMap.put(copCaseDetail.getPriceNum(), copCaseDetail.getId());
					}
				}
			}

			List<CopCaseDetailPrice> copCaseDetailPriceList = new ArrayList<CopCaseDetailPrice>();
			for(Entry<String, List<CopCaseDetailPrice>> entry : copCaseDetailPriceMap.entrySet()){
				List<CopCaseDetailPrice> list = entry.getValue();
				for(CopCaseDetailPrice copCaseDetailPrice : list){
					copCaseDetailPrice.setOnceCode(StringUtils.toString(jsonObject.get("onceCode")));
					copCaseDetailPrice.setCaseCode(StringUtils.toString(jsonObject.get("caseCode")));
					copCaseDetailPrice.setAuditCostPrepay(copCaseDetailPrice.getWbmCostPrepay());
					copCaseDetailPrice.setAuditCostPrepayTravel(copCaseDetailPrice.getWbmCostPrepayTravel());
					copCaseDetailPrice.setAuditCostFt(copCaseDetailPrice.getWbmCostFt());
					copCaseDetailPrice.setAuditCostFtTravel(copCaseDetailPrice.getWbmCostFtTravel());
					copCaseDetailPrice.setAuditCostFtToPrj(copCaseDetailPrice.getWbmCostFtToProject());
					copCaseDetailPrice.setAuditCostFtToPrjTravel(copCaseDetailPrice.getWbmCostFtToProjectTravel());
					copCaseDetailPrice.setStatus("A0");
					// 需要特批的单次报价设置状态为未特批
					if(isSpecialAudit != null && "1".equals(isSpecialAudit) && "normal".contains(invokeType)) {
						copCaseDetailPrice.setStatus("A2");
					}
					copCaseDetailPrice.setCreateBy(user);
					copCaseDetailPrice.setCreateDate(date);
					copCaseDetailPrice.setUpdateBy(user);
					copCaseDetailPrice.setUpdateDate(date);
					copCaseDetailPrice.setDelFlag("0");
					copCaseDetailPrice.setSaveFlag("1");
					copCaseDetailPriceList.add(copCaseDetailPrice);
					
					if("normal".contains(invokeType)) {
						copCaseDetailPriceService.save(copCaseDetailPrice);
					}
					
					wbmCostPrepay = wbmCostPrepay + (copCaseDetailPrice.getWbmCostPrepay()==null?0 : copCaseDetailPrice.getWbmCostPrepay());
					wbmCostPrepayTravel = wbmCostPrepayTravel + (copCaseDetailPrice.getWbmCostPrepayTravel()==null?0 : copCaseDetailPrice.getWbmCostPrepayTravel());
					wbmCostFt = wbmCostFt + (copCaseDetailPrice.getWbmCostFt()==null?0 : copCaseDetailPrice.getWbmCostFt());
					wbmCostFtTravel = wbmCostFtTravel + (copCaseDetailPrice.getWbmCostFtTravel()==null?0 : copCaseDetailPrice.getWbmCostFtTravel());
					wbmCostFtToProject = wbmCostFtToProject + (copCaseDetailPrice.getWbmCostFtToProject()==null?0 : copCaseDetailPrice.getWbmCostFtToProject());
					wbmCostFtToProjectTravel = wbmCostFtToProjectTravel + (copCaseDetailPrice.getWbmCostFtToProjectTravel()==null?0 : copCaseDetailPrice.getWbmCostFtToProjectTravel());
				}
			}
			
			// 报价核销和取消操作--报价明细价格
			if("cancel,write_off,prepay".contains(invokeType)){
				// 根据2次报价明细的差额补充核销退的报价数据
				for(CopCaseDetailPrice copCasePrice1 : copDetailPriceList){
					boolean hasPriceItem = false;
					if(isSpecialAudit == null || !"1".equals(isSpecialAudit)) { // 特批单次的核销为全部核销
						for(CopCaseDetailPrice copCasePrice2 : copCaseDetailPriceList) {
							if(copCasePrice1.getPriceNum().equals(copCasePrice2.getPriceNum())) {
								copCasePrice2.setWbmCostFt(copCasePrice2.getWbmCostFt()-copCasePrice1.getWbmCostFt());
								copCasePrice2.setWbmCostFtTravel(copCasePrice2.getWbmCostFtTravel()-copCasePrice1.getWbmCostFtTravel());
								copCasePrice2.setWbmCostFtToProject(copCasePrice2.getWbmCostFtToProject()-copCasePrice1.getWbmCostFtToProject());
								copCasePrice2.setWbmCostFtToProjectTravel(copCasePrice2.getWbmCostFtToProjectTravel()-copCasePrice1.getWbmCostFtToProjectTravel());
								copCasePrice2.setWbmCostPrepay(copCasePrice2.getWbmCostPrepay()-copCasePrice1.getWbmCostPrepay());
								copCasePrice2.setWbmCostPrepayTravel(copCasePrice2.getWbmCostPrepayTravel()-copCasePrice1.getWbmCostPrepayTravel());
								copCasePrice2.setAuditCostFt(copCasePrice2.getAuditCostFt()-copCasePrice1.getAuditCostFt());
								copCasePrice2.setAuditCostFtTravel(copCasePrice2.getAuditCostFtTravel()-copCasePrice1.getAuditCostFtTravel());
								copCasePrice2.setAuditCostFtToPrj(copCasePrice2.getAuditCostFtToPrj()-copCasePrice1.getAuditCostFtToPrj());
								copCasePrice2.setAuditCostFtToPrjTravel(copCasePrice2.getAuditCostFtToPrjTravel()-copCasePrice1.getAuditCostFtToPrjTravel());
								copCasePrice2.setAuditCostPrepay(copCasePrice2.getAuditCostPrepay()-copCasePrice1.getAuditCostPrepay());
								copCasePrice2.setAuditCostPrepayTravel(copCasePrice2.getAuditCostPrepayTravel()-copCasePrice1.getAuditCostPrepayTravel());
								
								copCaseDetailPriceService.save(copCasePrice2);
								hasPriceItem = true;
							}
						}
					}					
					// 全量核销扣减
					if(!hasPriceItem) {
						CopCaseDetailPrice ccdp = new CopCaseDetailPrice();
						ccdp.setCaseDetailId(idToPriceNumMap.get(ccdp.getPriceNum()));
						ccdp.setPriceNum(copCasePrice1.getPriceNum());
						ccdp.setOnceNum(copCasePrice1.getOnceNum());
						ccdp.setOnceCode(copCasePrice1.getOnceCode());
						ccdp.setCaseId(copCasePrice1.getCaseId());
						ccdp.setCaseCode(copCasePrice1.getCaseCode());
						ccdp.setPriceType(copCasePrice1.getPriceType());
						ccdp.setWbmCostPrepay(0-copCasePrice1.getWbmCostPrepay());
						ccdp.setWbmCostPrepayTravel(0-copCasePrice1.getWbmCostPrepayTravel());
						ccdp.setWbmCostFt(0-copCasePrice1.getWbmCostFt());
						ccdp.setWbmCostFtTravel(0-copCasePrice1.getWbmCostFtTravel());
						ccdp.setWbmCostFtToProject(0-copCasePrice1.getWbmCostFtToProject());
						ccdp.setWbmCostFtToProjectTravel(0-copCasePrice1.getWbmCostFtToProjectTravel());
						ccdp.setStatus("0");
						ccdp.setCreateBy(user);
						ccdp.setCreateDate(date);
						ccdp.setUpdateBy(user);
						ccdp.setUpdateDate(date);
						ccdp.setAuditCostPrepay(0-copCasePrice1.getAuditCostPrepay());
						ccdp.setAuditCostPrepayTravel(0-copCasePrice1.getAuditCostPrepayTravel());
						ccdp.setAuditCostFt(0-copCasePrice1.getAuditCostFt());
						ccdp.setAuditCostFtTravel(0-copCasePrice1.getAuditCostFtTravel());
						ccdp.setAuditCostFtToPrj(0-copCasePrice1.getAuditCostFtToPrj());
						ccdp.setAuditCostFtToPrjTravel(0-copCasePrice1.getAuditCostFtToPrjTravel());
						
						copCaseDetailPriceService.save(copCasePrice1);
					}
				}
			}
			
			json.put("code", 200);
			json.put("message", "成功");
			json.put("onceNum", onceNum);
			json.put("wbmCostPrepay", wbmCostPrepay);
			json.put("wbmCostPrepayTravel", wbmCostPrepayTravel);
			json.put("wbmCostFt", wbmCostFt);
			json.put("wbmCostFtTravel", wbmCostFtTravel);
			json.put("wbmCostFtToProject", wbmCostFtToProject);
			json.put("wbmCostFtToProjectTravel", wbmCostFtToProjectTravel);
			retVal = json.toJSONString();
			return retVal;
		} catch (Exception e) {
			json.put("code", 10);
			json.put("message", "失败，"+e.getMessage());
			retVal = json.toJSONString();
			return retVal;
		}
	}
	
	
	/**
	 * 报价接口（备件）
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/service/wbm.case.backPrice", method = RequestMethod.POST, produces = "application/json")
	public String getBackDetailPrice(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		
		String line;
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		User user = new User("admin");
		Date date = new Date();
		String retVal = "";
		String responseJsonStr = "";
			
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

			while ((line = br.readLine()) != null) 
				sb.append(line);
			
			br.close();
			responseJsonStr = sb.toString();
			
			JSONObject jsonObject = JSONObject.parseObject(responseJsonStr);
			// 先判断报价是否对应到具体存在的销售员名下
			String salesId = StringUtils.toString(jsonObject.get("salesId"));
			
			Map<String, String> staffMap = copSalesOrgAccountService.getSalesByCase(salesId);
			
			if(staffMap == null || staffMap.size() == 0) {
				json.put("code", 11);
				json.put("message", "失败， 未找到["+salesId+"]该销售员,请联系交付宝系统管理员。");
				retVal = json.toJSONString();
				return retVal;
			}
			
			// 报价类型：正常报价=normal 取消=cancel 核销=write_off 转先行支持=prepay
			String invokeType = StringUtils.toString(jsonObject.get("invoke_type"));
			if(invokeType == null || "".equals(invokeType)) invokeType = "normal"; 
			
			//检查CASE单次报价明细表中单次报价ID是否已存在
			String onceNum = StringUtils.toString(jsonObject.get("onceNum"));
			CopCaseDetail copDetail = new CopCaseDetail();
			copDetail.setOnceNum(onceNum);
			List<CopCaseDetail> copDetailList = copCaseDetailService.findList(copDetail);

			if("cancel,write_off,prepay".contains(invokeType)){
				if(copDetailList == null || copDetailList.isEmpty()) {
					json.put("code", 10);
					json.put("message", "失败， 未找到该单次报价明细。");
					retVal = json.toJSONString();
					return retVal;
				} else if("write_off".contains(invokeType)) {
					// 多次核销检查是否已经核销完
					for(CopCaseDetail ccd : copDetailList) {
						if(ccd.getPartAmount() < 0 || ccd.getPartDeliveryPrice() < 0) {
							json.put("code", 10);
							json.put("message", "失败， 该单次报价已经核销，不能再次核销。");
							retVal = json.toJSONString();
							return retVal;
						}
					}
				}
				
			}
			
			if("normal".contains(invokeType) && copDetailList != null && !copDetailList.isEmpty()){
				for(CopCaseDetail copCaseDetail : copDetailList){
					copCaseDetail.setPreStatus(copCaseDetail.getStatus());
					copCaseDetail.setStatusChangeDate(date);
					copCaseDetail.setStatus("A1");
					copCaseDetailService.save(copCaseDetail);
				}
			}
			//检查CASE单次报价表中单次报价ID是否已存在
			CopCaseDetailPrice copDetailPrice = new CopCaseDetailPrice();
			copDetailPrice.setOnceNum(onceNum);
			List<CopCaseDetailPrice> copDetailPriceList = copCaseDetailPriceService.findList(copDetailPrice);
			if("normal".contains(invokeType) && copDetailPriceList != null && !copDetailPriceList.isEmpty()){
				for(CopCaseDetailPrice copCasePrice : copDetailPriceList){
					copCasePrice.setPreStatus(copCasePrice.getStatus());
					copCasePrice.setStatusChangeDate(date);
					copCasePrice.setStatus("A1");
					copCaseDetailPriceService.save(copCasePrice);
				}
			}
			
			String isSpecialAudit = StringUtils.toString(jsonObject.get("specialApproved"));
			
			//解析报价数组
			List<CopCaseDetail> copCaseDetailList = new ArrayList<CopCaseDetail>();
			// 核销单次需要同步厂商型号序列号
			Map<String, CopCaseDetail> newCCDMap = Maps.newHashMap();
			JSONArray data = (JSONArray) jsonObject.get("data");
			List<CopCaseData> copCaseDataList = data.toJavaList(CopCaseData.class);
			for(CopCaseData copCaseData : copCaseDataList){
				CopCaseDetail copCaseDetail = new CopCaseDetail();
				copCaseDetail.setPriceNum(copCaseData.getNum());
				copCaseDetail.setCaseId((StringUtils.toString(jsonObject.get("caseId"))));
				copCaseDetail.setCaseCode(StringUtils.toString(jsonObject.get("caseCode")));
				copCaseDetail.setOnceNum((StringUtils.toString(jsonObject.get("onceNum"))));
				copCaseDetail.setOnceCode(StringUtils.toString(jsonObject.get("onceCode")));
				copCaseDetail.setPriceType("2");
				copCaseDetail.setServiceType(copCaseData.getServiceType());
				
				copCaseDetail.setDcPrjId(StringUtils.toString(jsonObject.get("projectCode")));
				copCaseDetail.setDcPrjName(StringUtils.toString(jsonObject.get("projectName")));
				copCaseDetail.setServiceContent(StringUtils.toString(jsonObject.get("serviceContent")));
				copCaseDetail.setPartsInfo(StringUtils.toString(jsonObject.get("partsInfo")));
				copCaseDetail.setFactoryId((StringUtils.toString(jsonObject.get("factoryId"))));
				copCaseDetail.setFactoryName(StringUtils.toString(jsonObject.get("factoryName")));
				copCaseDetail.setModelId((StringUtils.toString(jsonObject.get("modelId"))));
				copCaseDetail.setModelName(StringUtils.toString(jsonObject.get("modelName")));
				copCaseDetail.setSnId(StringUtils.toString(jsonObject.get("Sn")));
				copCaseDetail.setIsSpecialAudit(isSpecialAudit);
				copCaseDetail.setSpecialAuditRemark(StringUtils.toString(jsonObject.get("specialApprovedRemark")));
				copCaseDetail.setWoProjectCode(StringUtils.toString(jsonObject.get("writeOffProjectCode")));
				copCaseDetail.setWoProjectName(StringUtils.toString(jsonObject.get("writeOffProjectName")));
				copCaseDetail.setWoReason(StringUtils.toString(jsonObject.get("writeOffRemark")));
				copCaseDetail.setWoApprovalBy(StringUtils.toString(jsonObject.get("approvalBy")));
				String approvalDate = StringUtils.toString(jsonObject.get("approvalOn"));
				if(approvalDate != null && !"".equals(approvalDate)) {
					copCaseDetail.setWoApprovalDate(DateUtils.parseDate(approvalDate));
				}
				copCaseDetail.setWoApprovalRemark(StringUtils.toString(jsonObject.get("approvalRemark")));
				copCaseDetail.setInvokeType(invokeType);
				
				copCaseDetail.setPartPn(copCaseData.getPartPn());
				copCaseDetail.setPartAmount(copCaseData.getAmount()==null?(double) 0 : copCaseData.getAmount());
				copCaseDetail.setPartPrice(copCaseData.getPartsPrice()==null?(double) 0 : copCaseData.getPartsPrice());
				copCaseDetail.setPartDeliveryPrice(copCaseData.getDeliveryPrice()==null?(double) 0 : copCaseData.getDeliveryPrice());
				
				copCaseDetail.setStatus("A0");
				copCaseDetail.setCreateBy(user);
				copCaseDetail.setCreateDate(date);
				copCaseDetail.setUpdateBy(user);
				copCaseDetail.setUpdateDate(date);
				copCaseDetailList.add(copCaseDetail);

				if("normal".contains(invokeType)) {
					copCaseDetailService.save(copCaseDetail);
				} else {
					copCaseDetail.setId(IdGen.uuid());
					copCaseDetail.setDelFlag("0");
					copCaseDetail.setSaveFlag("1");
					
					newCCDMap.put(copCaseData.getNum(), copCaseDetail);
				}
			}
			
			Double wbmCostPrepay = (double) 0;
			Double wbmCostPrepayTravel = (double) 0;
			Double wbmCostFt = (double) 0;
			Double wbmCostFtTravel = (double) 0;
			Double wbmCostFtToProject = (double) 0;
			Double wbmCostFtToProjectTravel = (double) 0;
			
			//返回CASE单次报价结果
			String prodId = "RXSA-CASE-SUPPORT_PRICE";
			Map<String, List<CopCaseDetailPrice>> copCaseDetailPriceMap = CstOrderCostInfoService.getCalculateCaseDetailPrice(copCaseDetailList, prodId);

			// 报价核销和取消操作--报价明细
			Map<String, String> idToPriceNumMap = Maps.newHashMap();
			if("cancel,write_off,prepay".contains(invokeType)){
				Map<String, CopCaseDetail> checkMap = Maps.newHashMap();
				for(CopCaseDetail ccd : copDetailList) {
					if(checkMap.get(ccd.getPriceNum()) == null) {
						checkMap.put(ccd.getPriceNum(), ccd);
					} else {
						CopCaseDetail checkCcd = checkMap.get(ccd.getPriceNum());
						checkCcd.setPartAmount(checkCcd.getPartAmount()+ccd.getPartAmount());
						checkCcd.setPartDeliveryPrice(checkCcd.getPartDeliveryPrice()+ccd.getPartDeliveryPrice());
						checkCcd.setManWorkload(checkCcd.getManWorkload()+ccd.getManWorkload());
						checkCcd.setManTravelPrice(checkCcd.getManTravelPrice()+ccd.getManTravelPrice());
						checkMap.put(ccd.getPriceNum(), checkCcd);
					}
				}
				
				for(CopCaseDetail copCaseDetail1 : checkMap.values()) {
					// 根据2次报价明细的差额补充核销退的数据
					// 核销同步厂商型号序列号
					CopCaseDetail newCCD = newCCDMap.get(copCaseDetail1.getPriceNum());
					copCaseDetail1.setFactoryId(newCCD.getFactoryId());
					copCaseDetail1.setFactoryName(newCCD.getFactoryName());
					copCaseDetail1.setModelId(newCCD.getModelId());
					copCaseDetail1.setModelName(newCCD.getModelName());
					copCaseDetail1.setSnId(newCCD.getSnId());
					copCaseDetailService.save(copCaseDetail1);
					
					boolean hasItem = false;
					if(isSpecialAudit == null || !"1".equals(isSpecialAudit)) { // 特批单次的核销为全部核销
						for(CopCaseDetail copCaseDetail2 : copCaseDetailList) {
							if(copCaseDetail1.getPriceNum().equals(copCaseDetail2.getPriceNum())) {
								copCaseDetail2.setPartAmount(copCaseDetail2.getPartAmount()-copCaseDetail1.getPartAmount());
								// 备件核销单价不变
								copCaseDetail2.setPartDeliveryPrice(copCaseDetail2.getPartDeliveryPrice()-copCaseDetail1.getPartDeliveryPrice());
								copCaseDetailService.insert(copCaseDetail2);
								hasItem = true;
							}
						}
					}
					// 全部核销扣减
					if(!hasItem) {
						CopCaseDetail copCaseDetail = new CopCaseDetail();
						copCaseDetail.setPriceNum(copCaseDetail1.getPriceNum());
						copCaseDetail.setCaseId(copCaseDetail1.getCaseId());
						copCaseDetail.setCaseCode(copCaseDetail1.getCaseCode());
						copCaseDetail.setOnceNum(copCaseDetail1.getOnceNum());
						copCaseDetail.setOnceCode(copCaseDetail1.getOnceCode());
						copCaseDetail.setPriceType(copCaseDetail1.getPriceType());
						copCaseDetail.setServiceType(copCaseDetail1.getServiceType());
						copCaseDetail.setDcPrjId(copCaseDetail1.getDcPrjId());
						copCaseDetail.setDcPrjName(copCaseDetail1.getDcPrjName());
						copCaseDetail.setServiceContent(copCaseDetail1.getServiceContent());
						copCaseDetail.setPartsInfo(copCaseDetail1.getPartsInfo());
						copCaseDetail.setFactoryId(newCCD.getFactoryId());
						copCaseDetail.setFactoryName(newCCD.getFactoryName());
						copCaseDetail.setModelId(newCCD.getModelId());
						copCaseDetail.setModelName(newCCD.getModelName());
						copCaseDetail.setSnId(newCCD.getSnId());
						copCaseDetail.setIsSpecialAudit(copCaseDetail1.getIsSpecialAudit());
						copCaseDetail.setSpecialAuditRemark(copCaseDetail1.getSpecialAuditRemark());
						copCaseDetail.setWoProjectCode(StringUtils.toString(jsonObject.get("writeOffProjectCode")));
						copCaseDetail.setWoProjectName(StringUtils.toString(jsonObject.get("writeOffProjectName")));
						copCaseDetail.setWoReason(StringUtils.toString(jsonObject.get("writeOffRemark")));
						copCaseDetail.setWoApprovalBy(StringUtils.toString(jsonObject.get("approvalBy")));
						String approvalDate = StringUtils.toString(jsonObject.get("approvalOn"));
						if(approvalDate != null && !"".equals(approvalDate)) {
							copCaseDetail.setWoApprovalDate(DateUtils.parseDate(approvalDate));
						}
						copCaseDetail.setWoApprovalRemark(StringUtils.toString(jsonObject.get("approvalRemark")));
						copCaseDetail.setInvokeType(invokeType);
						copCaseDetail.setManRoleId(copCaseDetail1.getManRoleId());
						copCaseDetail.setManEngineerLevel(copCaseDetail1.getManEngineerLevel());
						copCaseDetail.setManResourceType(copCaseDetail1.getManResourceType());
						copCaseDetail.setPartAmount(0-copCaseDetail1.getPartAmount());
						copCaseDetail.setPartDeliveryPrice(0-copCaseDetail1.getPartDeliveryPrice());
						copCaseDetail.setStatus("A0");
						copCaseDetail.setCreateBy(user);
						copCaseDetail.setCreateDate(date);
						copCaseDetail.setUpdateBy(user);
						copCaseDetail.setUpdateDate(date);
						
						copCaseDetailService.save(copCaseDetail);
						idToPriceNumMap.put(copCaseDetail.getPriceNum(), copCaseDetail.getId());
					}
				}
			}
			
			List<CopCaseDetailPrice> copCaseDetailPriceList = new ArrayList<CopCaseDetailPrice>();
			for(Entry<String, List<CopCaseDetailPrice>> entry : copCaseDetailPriceMap.entrySet()){
				List<CopCaseDetailPrice> list = entry.getValue();
				for(CopCaseDetailPrice copCaseDetailPrice : list){
					copCaseDetailPrice.setOnceCode(StringUtils.toString(jsonObject.get("onceCode")));
					copCaseDetailPrice.setCaseCode(StringUtils.toString(jsonObject.get("caseCode")));
					copCaseDetailPrice.setAuditCostPrepay(copCaseDetailPrice.getWbmCostPrepay());
					copCaseDetailPrice.setAuditCostPrepayTravel(copCaseDetailPrice.getWbmCostPrepayTravel());
					copCaseDetailPrice.setAuditCostFt(copCaseDetailPrice.getWbmCostFt());
					copCaseDetailPrice.setAuditCostFtTravel(copCaseDetailPrice.getWbmCostFtTravel());
					copCaseDetailPrice.setAuditCostFtToPrj(copCaseDetailPrice.getWbmCostFtToProject());
					copCaseDetailPrice.setAuditCostFtToPrjTravel(copCaseDetailPrice.getWbmCostFtToProjectTravel());
					copCaseDetailPrice.setStatus("A0");
					// 需要特批的单次报价设置状态为未特批
					if(isSpecialAudit != null && "1".equals(isSpecialAudit) && "normal".contains(invokeType)) {
						copCaseDetailPrice.setStatus("A2");
					}
					copCaseDetailPrice.setCreateBy(user);
					copCaseDetailPrice.setCreateDate(date);
					copCaseDetailPrice.setUpdateBy(user);
					copCaseDetailPrice.setUpdateDate(date);
					copCaseDetailPrice.setDelFlag("0");
					copCaseDetailPrice.setSaveFlag("1");

					copCaseDetailPriceList.add(copCaseDetailPrice);
					
					if("normal".contains(invokeType)) {
						copCaseDetailPriceService.save(copCaseDetailPrice);
					}
					
					wbmCostPrepay = wbmCostPrepay + (copCaseDetailPrice.getWbmCostPrepay()==null?0 : copCaseDetailPrice.getWbmCostPrepay());
					wbmCostPrepayTravel = wbmCostPrepayTravel + (copCaseDetailPrice.getWbmCostPrepayTravel()==null?0 : copCaseDetailPrice.getWbmCostPrepayTravel());
					wbmCostFt = wbmCostFt + (copCaseDetailPrice.getWbmCostFt()==null?0 : copCaseDetailPrice.getWbmCostFt());
					wbmCostFtTravel = wbmCostFtTravel + (copCaseDetailPrice.getWbmCostFtTravel()==null?0 : copCaseDetailPrice.getWbmCostFtTravel());
					wbmCostFtToProject = wbmCostFtToProject + (copCaseDetailPrice.getWbmCostFtToProject()==null?0 : copCaseDetailPrice.getWbmCostFtToProject());
					wbmCostFtToProjectTravel = wbmCostFtToProjectTravel + (copCaseDetailPrice.getWbmCostFtToProjectTravel()==null?0 : copCaseDetailPrice.getWbmCostFtToProjectTravel());
				}
			}
			
			// 报价核销和取消操作--报价明细价格
			if("cancel,write_off,prepay".contains(invokeType)){
				
				Map<String, CopCaseDetailPrice> checkCcdpMap = Maps.newHashMap();
				for(CopCaseDetailPrice ccdp : copDetailPriceList) {
					if(checkCcdpMap.get(ccdp.getPriceNum()) == null) {
						checkCcdpMap.put(ccdp.getPriceNum(), ccdp);
					} else {
						CopCaseDetailPrice checkCcdp = checkCcdpMap.get(ccdp.getPriceNum());checkCcdp.setAuditCostFt(checkCcdp.getAuditCostFt()+ccdp.getAuditCostFt());
						checkCcdp.setWbmCostFt(checkCcdp.getWbmCostFt()+ccdp.getWbmCostFt());
						checkCcdp.setWbmCostFtTravel(checkCcdp.getWbmCostFtTravel()+ccdp.getWbmCostFtTravel());
						checkCcdp.setWbmCostFtToProject(checkCcdp.getWbmCostFtToProject()+ccdp.getWbmCostFtToProject());
						checkCcdp.setWbmCostFtToProjectTravel(checkCcdp.getWbmCostFtToProjectTravel()+ccdp.getWbmCostFtToProjectTravel());
						checkCcdp.setWbmCostPrepay(checkCcdp.getWbmCostPrepay()+ccdp.getWbmCostPrepay());
						checkCcdp.setWbmCostPrepayTravel(checkCcdp.getWbmCostPrepayTravel()+ccdp.getWbmCostPrepayTravel());
						
						checkCcdp.setAuditCostFt(checkCcdp.getAuditCostFt()+ccdp.getAuditCostFt());
						checkCcdp.setAuditCostFtTravel(checkCcdp.getAuditCostFtTravel()+ccdp.getAuditCostFtTravel());
						checkCcdp.setAuditCostFtToPrj(checkCcdp.getAuditCostFtToPrj()+ccdp.getAuditCostFtToPrj());
						checkCcdp.setAuditCostFtToPrjTravel(checkCcdp.getAuditCostFtToPrjTravel()+ccdp.getAuditCostFtToPrjTravel());
						checkCcdp.setAuditCostPrepay(checkCcdp.getAuditCostPrepay()+ccdp.getAuditCostPrepay());
						checkCcdp.setAuditCostPrepayTravel(checkCcdp.getAuditCostPrepayTravel()+ccdp.getAuditCostPrepayTravel());
						checkCcdpMap.put(ccdp.getPriceNum(), checkCcdp);
					}
				}
				
				// 根据2次报价明细的差额补充核销退的报价数据
				for(CopCaseDetailPrice copCasePrice1 : checkCcdpMap.values()){
					boolean hasPriceItem = false;
					if(isSpecialAudit == null || !"1".equals(isSpecialAudit)) { // 特批单次的核销为全部核销
						for(CopCaseDetailPrice copCasePrice2 : copCaseDetailPriceList) {
							if(copCasePrice1.getPriceNum().equals(copCasePrice2.getPriceNum())) {
								copCasePrice2.setWbmCostFt(copCasePrice2.getWbmCostFt()-copCasePrice1.getWbmCostFt());
								copCasePrice2.setWbmCostFtTravel(copCasePrice2.getWbmCostFtTravel()-copCasePrice1.getWbmCostFtTravel());
								copCasePrice2.setWbmCostFtToProject(copCasePrice2.getWbmCostFtToProject()-copCasePrice1.getWbmCostFtToProject());
								copCasePrice2.setWbmCostFtToProjectTravel(copCasePrice2.getWbmCostFtToProjectTravel()-copCasePrice1.getWbmCostFtToProjectTravel());
								copCasePrice2.setWbmCostPrepay(copCasePrice2.getWbmCostPrepay()-copCasePrice1.getWbmCostPrepay());
								copCasePrice2.setWbmCostPrepayTravel(copCasePrice2.getWbmCostPrepayTravel()-copCasePrice1.getWbmCostPrepayTravel());
								copCasePrice2.setAuditCostFt(copCasePrice2.getAuditCostFt()-copCasePrice1.getAuditCostFt());
								copCasePrice2.setAuditCostFtTravel(copCasePrice2.getAuditCostFtTravel()-copCasePrice1.getAuditCostFtTravel());
								copCasePrice2.setAuditCostFtToPrj(copCasePrice2.getAuditCostFtToPrj()-copCasePrice1.getAuditCostFtToPrj());
								copCasePrice2.setAuditCostFtToPrjTravel(copCasePrice2.getAuditCostFtToPrjTravel()-copCasePrice1.getAuditCostFtToPrjTravel());
								copCasePrice2.setAuditCostPrepay(copCasePrice2.getAuditCostPrepay()-copCasePrice1.getAuditCostPrepay());
								copCasePrice2.setAuditCostPrepayTravel(copCasePrice2.getAuditCostPrepayTravel()-copCasePrice1.getAuditCostPrepayTravel());
								
								copCaseDetailPriceService.save(copCasePrice2);
								hasPriceItem = true;
							}
						}
					}					
					// 全量核销扣减
					if(!hasPriceItem) {
						CopCaseDetailPrice ccdp = new CopCaseDetailPrice();
						ccdp.setCaseDetailId(idToPriceNumMap.get(ccdp.getPriceNum()));
						ccdp.setPriceNum(copCasePrice1.getPriceNum());
						ccdp.setOnceNum(copCasePrice1.getOnceNum());
						ccdp.setOnceCode(copCasePrice1.getOnceCode());
						ccdp.setCaseId(copCasePrice1.getCaseId());
						ccdp.setCaseCode(copCasePrice1.getCaseCode());
						ccdp.setPriceType(copCasePrice1.getPriceType());
						ccdp.setWbmCostPrepay(0-copCasePrice1.getWbmCostPrepay());
						ccdp.setWbmCostPrepayTravel(0-copCasePrice1.getWbmCostPrepayTravel());
						ccdp.setWbmCostFt(0-copCasePrice1.getWbmCostFt());
						ccdp.setWbmCostFtTravel(0-copCasePrice1.getWbmCostFtTravel());
						ccdp.setWbmCostFtToProject(0-copCasePrice1.getWbmCostFtToProject());
						ccdp.setWbmCostFtToProjectTravel(0-copCasePrice1.getWbmCostFtToProjectTravel());
						ccdp.setStatus("0");
						ccdp.setCreateBy(user);
						ccdp.setCreateDate(date);
						ccdp.setUpdateBy(user);
						ccdp.setUpdateDate(date);
						ccdp.setAuditCostPrepay(0-copCasePrice1.getAuditCostPrepay());
						ccdp.setAuditCostPrepayTravel(0-copCasePrice1.getAuditCostPrepayTravel());
						ccdp.setAuditCostFt(0-copCasePrice1.getAuditCostFt());
						ccdp.setAuditCostFtTravel(0-copCasePrice1.getAuditCostFtTravel());
						ccdp.setAuditCostFtToPrj(0-copCasePrice1.getAuditCostFtToPrj());
						ccdp.setAuditCostFtToPrjTravel(0-copCasePrice1.getAuditCostFtToPrjTravel());
						
						copCaseDetailPriceService.save(copCasePrice1);
					}
				}
			}
			
			json.put("code", 200);
			json.put("message", "成功");
			json.put("onceNum", onceNum);
			json.put("wbmCostPrepay", wbmCostPrepay);
			json.put("wbmCostPrepayTravel", wbmCostPrepayTravel);
			json.put("wbmCostFt", wbmCostFt);
			json.put("wbmCostFtTravel", wbmCostFtTravel);
			json.put("wbmCostFtToProject", wbmCostFtToProject);
			json.put("wbmCostFtToProjectTravel", wbmCostFtToProjectTravel);
			retVal = json.toJSONString();
			return retVal;
		} catch (Exception e) {
			json.put("code", 10);
			json.put("message", "失败，"+e.getMessage());
			retVal = json.toJSONString();
			return retVal;
		}
	}
	
	
	/**
	 * 先行支持费用查询
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/service/wbm.case.prepayQuery", method = RequestMethod.POST, produces = "application/json")
	public String getPrjAccount(HttpServletRequest request) throws ParseException{
		
		String line;
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		Date date = DateUtils.parseDate(DateUtils.getDate(), "yyyy-MM-dd");
		String retVal = "";
		String responseJsonStr = "";
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

			while ((line = br.readLine()) != null) 
				sb.append(line);
			
			br.close();
			responseJsonStr = sb.toString();
			
			JSONObject jsonObject = JSONObject.parseObject(responseJsonStr);
			String saleItcode = StringUtils.toString(jsonObject.get("saleItcode"));
			String projectCode = StringUtils.toString(jsonObject.get("projectCode"));
			String priceType = StringUtils.toString(jsonObject.get("priceType"));
			String priceTypeName = StringUtils.toString(jsonObject.get("priceTypeName"));
			
			//可以使用的项目资源
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			CopSalesAccount copSalesAccount = new CopSalesAccount();
			copSalesAccount.setStaffId(saleItcode);
			copSalesAccount.setDcPrjId(projectCode);
			copSalesAccount.setIsShared("A0");
			copSalesAccount.setStatus("A0");
			List<CopSalesAccount> copSalesAccountList = copSalesAccountService.findStaffPrjList(copSalesAccount);
			List<Map<String, Object>> accountList = new ArrayList<Map<String, Object>>();
			Map<String, List<CopSalesAccount>> accountMap = new HashMap<String, List<CopSalesAccount>>();
			for(CopSalesAccount account : copSalesAccountList){
				if(!accountMap.containsKey(account.getDcPrjId())){
					List<CopSalesAccount> list = new ArrayList<CopSalesAccount>();
					list.add(account);
					accountMap.put(account.getDcPrjId(), list);
				}else{
					accountMap.get(account.getDcPrjId()).add(account);
				}
			}
			for(Entry<String, List<CopSalesAccount>> entry : accountMap.entrySet()){
				Map<String, Object> copSalesAccountMap = new HashMap<String, Object>();
				copSalesAccountMap.put("projectCode", entry.getKey());
				copSalesAccountMap.put("projectName", entry.getKey());
				
				Double deliverAmount = (double) 0;
				Double usedAmount = (double) 0;
				Date serviceBegin = null;
				Date serviceEnd = null;
				
				for(CopSalesAccount salesAccount : entry.getValue()){
					//服务开始时间大于当前时间或者服务结束时间小于当前时间(不在保)
					if(salesAccount.getServiceBegin().compareTo(date) > 0 || salesAccount.getServiceEnd().compareTo(date) < 0){
						continue;
					}
					
					//人工
					if("1".equals(priceType)){
						deliverAmount = deliverAmount + salesAccount.getFinalAmount();
						usedAmount = usedAmount + salesAccount.getUsedAmount();
					}else{//备件
						deliverAmount = deliverAmount + salesAccount.getBackFinalAmount();
						usedAmount = usedAmount + salesAccount.getBackUsedAmount();
					}
					
					if(serviceBegin == null){
						serviceBegin = salesAccount.getServiceBegin();
					}else{
						serviceBegin = serviceBegin.compareTo(salesAccount.getServiceBegin()) > 0?salesAccount.getServiceBegin() : serviceBegin;
					}
					
					if(serviceEnd == null){
						serviceEnd = salesAccount.getServiceEnd();
					}else{
						serviceEnd = serviceEnd.compareTo(salesAccount.getServiceEnd()) < 0?salesAccount.getServiceEnd() : serviceEnd;
					}
				}
				
				if(deliverAmount.equals(usedAmount)) { // 预付费无剩余，不显示
					continue;
				}
				
				copSalesAccountMap.put("deliverAmount", deliverAmount);
				copSalesAccountMap.put("usedAmount", usedAmount);
				copSalesAccountMap.put("availableAmount", deliverAmount - usedAmount);
				String serviceBeginStr = serviceBegin == null?"" : sdf.format(serviceBegin);
				String serviceEndStr = serviceEnd == null?"" : sdf.format(serviceEnd);
				copSalesAccountMap.put("serviceBegin", serviceBeginStr);
				copSalesAccountMap.put("serviceEnd", serviceEndStr);
				accountList.add(copSalesAccountMap);
			}
			
			json.put("code", 200);
			json.put("message", "成功");
			json.put("piceType", priceType);
			json.put("piceTypeName", priceTypeName);
			json.put("data", accountList);
			
			retVal = json.toJSONString();
			return retVal;
		} catch (Exception e) {
			json.put("code", 10);
			json.put("message", "失败，"+e.getMessage());
			retVal = json.toJSONString();
			return retVal;
		}
	}
	
	
	/**
	 * 完成报价确认
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/service/wbm.case.priceConfirm", method = RequestMethod.POST, produces = "application/json")
	public String getPriceConfirm(HttpServletRequest request) {
		
		String line;
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		User user = new User("admin");
		Date date = new Date();
		String retVal = "";
		String responseJsonStr = "";
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

			while ((line = br.readLine()) != null) 
				sb.append(line);
			
			br.close();
			responseJsonStr = sb.toString();
			
			JSONObject jsonObject = JSONObject.parseObject(responseJsonStr);
			JSONArray dataObject = (JSONArray) jsonObject.get("data");
			String projectCode = StringUtils.toString(jsonObject.get("projectCode"));//项目编号
			String priceType = StringUtils.toString(jsonObject.get("priceType"));//1人员，2备件
			String onceNum = StringUtils.toString(jsonObject.get("onceNum")); // 报价ID
			String payType = StringUtils.toString(jsonObject.get("payType")); // 支付类型：1预付费，2单次，3单次入项目
			String salesId = StringUtils.toString(jsonObject.get("salesId"));
			String ifApprove = StringUtils.toString(jsonObject.get("ifApprove")); // 是否单次超阀值审批 1是  0否
			String caseLevel = StringUtils.toString(jsonObject.get("caseLevel")); // 事件级别:1\2\3\4\5
			Double costPay = StringUtils.toDouble(jsonObject.get("costPrepay"));
			Double costPayTravel = StringUtils.toDouble(jsonObject.get("costPrepayTravel"));
			// 报价类型：正常报价=normal 取消=cancel 核销=write_off 转先行支持=prepay
			String invokeType = StringUtils.toString(jsonObject.get("invoke_type"));
			
			/*if("1".equals(payType) || "prepay".equals(invokeType)) {
				json.put("code", 10);
				json.put("message", "产品线正在核对单次数据，预付费使用暂停，请稍候再处理。");
				retVal = json.toJSONString();
				return retVal;
			}*/
			
			// 校验该项目的单次是否需要进行事业部单次成本阀值判断
			boolean needCheckProject = true;
			CopSalesOrgProject copSalesOrgProject = new CopSalesOrgProject();
			copSalesOrgProject.setDcPrjId(projectCode);
			copSalesOrgProject.setStatus("A0");
			List<CopSalesOrgProject> csopList = copSalesOrgProjectService.findList(copSalesOrgProject);
			if(csopList!=null && csopList.size() > 0) {
				for(CopSalesOrgProject csop : csopList) {
					if(new Date().compareTo(csop.getEndDate()) <= 0) {
						needCheckProject = false;
					}
				}
			}
		    
			Map<String, String> staffMap = copSalesOrgAccountService.getSalesByCase(salesId);
			// 先判断报价是否对应到具体存在的销售员名下
			if(staffMap == null || staffMap.size() == 0) {
				json.put("code", 11);
				json.put("message", "失败， 未找到["+salesId+"]该销售员,请联系交付宝系统管理员。");
				retVal = json.toJSONString();
				return retVal;
			}
			
			// 根据销售员判断其所属事业部的单次是否达到阀值；2019-03-20 暂停单次事业部限额的限制
			/*if("1".equals(payType) || (ifApprove != null && "1".equals(ifApprove)) || (caseLevel != null && "1".equals(caseLevel)) || !needCheckProject) {
				// 不需要判断阀值的
			} else {
				CopSalesOrgAccount copSalesOrgAccount = new CopSalesOrgAccount(); 
				copSalesOrgAccount.setOrgId(staffMap.get("ORG_ID"));
				copSalesOrgAccount.setStatus("A0");
				List<CopSalesOrgAccount> csoaList = copSalesOrgAccountService.findList(copSalesOrgAccount);
				// 先判断报价是否对应到具体存在的销售员名下
				if(csoaList == null || csoaList.size() == 0) {
					json.put("code", 12);
					json.put("message", "失败， 未找到["+salesId+"]该销售员对应的事业部单次服务阀值,请联系WBM系统管理员。");
					retVal = json.toJSONString();
					return retVal;
				}
				CopSalesOrgAccount csoa = csoaList.get(0);
				
				Double orgCaseUsedAmount = copSalesOrgAccountService.getSalesOrgCaseUsedAmount(salesId);
				
				if(orgCaseUsedAmount != null && (orgCaseUsedAmount+costPay+costPayTravel) > csoa.getCaseMaxAmount()) {
					json.put("code", 13);
					json.put("message", "["+salesId+"]该销售员所在事业部单次服务已超出阀值,请联系运维产品经理。");
					retVal = json.toJSONString();
					return retVal;
				}
			}*/
			
			if(invokeType == null || "".equals(invokeType)) invokeType = "normal"; 
			
			/** 先查询报价确认表是否存在相同onceNum的报价并且确认的信息，存在报错； */
			CopCasePriceConfirm ccpc = new CopCasePriceConfirm();
			ccpc.setOnceNum(onceNum);
			ccpc.setStatus("A2"); // 报价确认
			List<CopCasePriceConfirm> ccpcList = copCasePriceConfirmService.findList(ccpc);
			if(ccpcList.size() > 0 && "normal".contains(invokeType)) {
				json.put("code", 10);
				json.put("message", "失败，该单次报价已经完成报价确认。");
				retVal = json.toJSONString();
				return retVal;
			}
			
			// 转先行支持:先把原确认的单次中剩余部分调整为0，然后新加报价单次号（原单次号_1）按照预付费流程继续处理；
			Date comfirmDate = date;
			if("prepay".equals(invokeType)) {
				CopCaseDetail copDetail = new CopCaseDetail();
				copDetail.setOnceNum(onceNum);
				List<CopCaseDetail> copDetailList = copCaseDetailService.findList(copDetail);
				Map<String, CopCaseDetail> checkMap = Maps.newHashMap();
				for(CopCaseDetail ccd : copDetailList) {
					if(checkMap.get(ccd.getPriceNum()) == null) {
						checkMap.put(ccd.getPriceNum(), ccd);
					} else {
						CopCaseDetail checkCcd = checkMap.get(ccd.getPriceNum());
						checkCcd.setPartAmount(checkCcd.getPartAmount()+ccd.getPartAmount());
						checkCcd.setPartDeliveryPrice(checkCcd.getPartDeliveryPrice()+ccd.getPartDeliveryPrice());
						checkCcd.setManWorkload(checkCcd.getManWorkload()+ccd.getManWorkload());
						checkCcd.setManTravelPrice(checkCcd.getManTravelPrice()+ccd.getManTravelPrice());
						checkMap.put(ccd.getPriceNum(), checkCcd);
					}
				}
				// 添加新的明细
				Map<String, CopCaseDetail> ccd1map = Maps.newHashMap();
				Map<String, CopCaseDetail> ccd2map = Maps.newHashMap();
				for(CopCaseDetail checkCcd : checkMap.values()) {
					CopCaseDetail copCaseDetail = new CopCaseDetail();
					copCaseDetail.setPriceNum(checkCcd.getPriceNum());
					copCaseDetail.setCaseId(checkCcd.getCaseId());
					copCaseDetail.setCaseCode(checkCcd.getCaseCode());
					copCaseDetail.setOnceNum(checkCcd.getOnceNum());
					copCaseDetail.setOnceCode(checkCcd.getOnceCode());
					copCaseDetail.setPriceType(checkCcd.getPriceType());
					copCaseDetail.setServiceType(checkCcd.getServiceType());
					copCaseDetail.setDcPrjId(checkCcd.getDcPrjId());
					copCaseDetail.setDcPrjName(checkCcd.getDcPrjName());
					copCaseDetail.setServiceContent(checkCcd.getServiceContent());
					copCaseDetail.setPartsInfo(checkCcd.getPartsInfo());
					copCaseDetail.setFactoryId(checkCcd.getFactoryId());
					copCaseDetail.setFactoryName(checkCcd.getFactoryName());
					copCaseDetail.setModelId(checkCcd.getModelId());
					copCaseDetail.setModelName(checkCcd.getModelName());
					copCaseDetail.setSnId(checkCcd.getSnId());
					copCaseDetail.setIsSpecialAudit(checkCcd.getIsSpecialAudit());
					copCaseDetail.setSpecialAuditRemark(checkCcd.getSpecialAuditRemark());
					copCaseDetail.setWoProjectCode(checkCcd.getWoProjectCode());
					copCaseDetail.setWoProjectName(checkCcd.getWoProjectName());
					copCaseDetail.setWoReason(checkCcd.getWoReason());
					copCaseDetail.setWoApprovalBy(checkCcd.getWoApprovalBy());
					copCaseDetail.setWoApprovalDate(checkCcd.getWoApprovalDate());
					copCaseDetail.setWoApprovalRemark(checkCcd.getWoApprovalRemark());
					copCaseDetail.setManRoleId(checkCcd.getManRoleId());
					copCaseDetail.setManEngineerLevel(checkCcd.getManEngineerLevel());
					copCaseDetail.setManResourceType(checkCcd.getManResourceType());
					copCaseDetail.setManWorkload(0-checkCcd.getManWorkload());
					copCaseDetail.setManPrice(checkCcd.getManPrice());
					copCaseDetail.setManTravelPrice(0-checkCcd.getManTravelPrice());
					copCaseDetail.setPartPn(checkCcd.getPartPn());
					copCaseDetail.setPartAmount(0-checkCcd.getPartAmount());
					copCaseDetail.setPartPrice(checkCcd.getPartPrice());
					copCaseDetail.setPartDeliveryPrice(0-checkCcd.getPartDeliveryPrice());
					copCaseDetail.setInvokeType(invokeType);
					copCaseDetail.setRemarks("转预付费明细添加抵消数据");
					copCaseDetail.setStatus("A0");
					copCaseDetail.setCreateBy(user);
					copCaseDetail.setCreateDate(date);
					copCaseDetail.setUpdateBy(user);
					copCaseDetail.setUpdateDate(date);
					copCaseDetail.setId(IdGen.uuid());
					copCaseDetail.setDelFlag("0");
					copCaseDetail.setSaveFlag("1");
					copCaseDetailService.insert(copCaseDetail);
					ccd1map.put(copCaseDetail.getPriceNum(), copCaseDetail);
					
					CopCaseDetail newCopCaseDetail = new CopCaseDetail();
					newCopCaseDetail.setPriceNum(checkCcd.getPriceNum());
					newCopCaseDetail.setCaseId(checkCcd.getCaseId());
					newCopCaseDetail.setCaseCode(checkCcd.getCaseCode());
					newCopCaseDetail.setOnceNum(checkCcd.getOnceNum()+"_1");
					newCopCaseDetail.setOnceCode(checkCcd.getOnceCode()+"_1");
					newCopCaseDetail.setPriceType(checkCcd.getPriceType());
					newCopCaseDetail.setServiceType(checkCcd.getServiceType());
					newCopCaseDetail.setDcPrjId(checkCcd.getDcPrjId());
					newCopCaseDetail.setDcPrjName(checkCcd.getDcPrjName());
					newCopCaseDetail.setServiceContent(checkCcd.getServiceContent());
					newCopCaseDetail.setPartsInfo(checkCcd.getPartsInfo());
					newCopCaseDetail.setFactoryId(checkCcd.getFactoryId());
					newCopCaseDetail.setFactoryName(checkCcd.getFactoryName());
					newCopCaseDetail.setModelId(checkCcd.getModelId());
					newCopCaseDetail.setModelName(checkCcd.getModelName());
					newCopCaseDetail.setSnId(checkCcd.getSnId());
					newCopCaseDetail.setIsSpecialAudit(checkCcd.getIsSpecialAudit());
					newCopCaseDetail.setSpecialAuditRemark(checkCcd.getSpecialAuditRemark());
					newCopCaseDetail.setWoProjectCode(checkCcd.getWoProjectCode());
					newCopCaseDetail.setWoProjectName(checkCcd.getWoProjectName());
					newCopCaseDetail.setWoReason(checkCcd.getWoReason());
					newCopCaseDetail.setWoApprovalBy(checkCcd.getWoApprovalBy());
					newCopCaseDetail.setWoApprovalDate(checkCcd.getWoApprovalDate());
					newCopCaseDetail.setWoApprovalRemark(checkCcd.getWoApprovalRemark());
					newCopCaseDetail.setManRoleId(checkCcd.getManRoleId());
					newCopCaseDetail.setManEngineerLevel(checkCcd.getManEngineerLevel());
					newCopCaseDetail.setManResourceType(checkCcd.getManResourceType());
					newCopCaseDetail.setManWorkload(checkCcd.getManWorkload());
					newCopCaseDetail.setManPrice(checkCcd.getManPrice());
					newCopCaseDetail.setManTravelPrice(checkCcd.getManTravelPrice());
					newCopCaseDetail.setPartPn(checkCcd.getPartPn());
					newCopCaseDetail.setPartAmount(checkCcd.getPartAmount());
					newCopCaseDetail.setPartPrice(checkCcd.getPartPrice());
					newCopCaseDetail.setPartDeliveryPrice(checkCcd.getPartDeliveryPrice());
					newCopCaseDetail.setInvokeType(invokeType);
					newCopCaseDetail.setStatus("A0");
					newCopCaseDetail.setCreateBy(user);
					newCopCaseDetail.setCreateDate(date);
					newCopCaseDetail.setUpdateBy(user);
					newCopCaseDetail.setUpdateDate(date);
					newCopCaseDetail.setId(IdGen.uuid());
					newCopCaseDetail.setDelFlag("0");
					newCopCaseDetail.setSaveFlag("1");
					copCaseDetailService.insert(newCopCaseDetail);
					ccd2map.put(newCopCaseDetail.getPriceNum(), newCopCaseDetail);
				}
				
				String oldPayType = "";
				for(CopCasePriceConfirm ccpcm : ccpcList) {
					// 设置支付类型为：预付费 
					payType = "1";
					oldPayType = ccpcm.getPayType();
				}
				// 转预付费的需要同步更新报价表的确认金额
				CopCaseDetailPrice copDetailPrice = new CopCaseDetailPrice();
				copDetailPrice.setOnceNum(onceNum);
				List<CopCaseDetailPrice> copDetailPriceList = copCaseDetailPriceService.findList(copDetailPrice);
				
				Map<String, CopCaseDetailPrice> checkCcdpMap = Maps.newHashMap();
				for(CopCaseDetailPrice ccdp : copDetailPriceList) {
					if(checkCcdpMap.get(ccdp.getPriceNum()) == null) {
						checkCcdpMap.put(ccdp.getPriceNum(), ccdp);
					} else {
						CopCaseDetailPrice checkCcdp = checkCcdpMap.get(ccdp.getPriceNum());
						checkCcdp.setAuditCostFt(checkCcdp.getAuditCostFt()+ccdp.getAuditCostFt());
						checkCcdp.setAuditCostFtTravel(checkCcdp.getAuditCostFtTravel()+ccdp.getAuditCostFtTravel());
						checkCcdp.setAuditCostFtToPrj(checkCcdp.getAuditCostFtToPrj()+ccdp.getAuditCostFtToPrj());
						checkCcdp.setAuditCostFtToPrjTravel(checkCcdp.getAuditCostFtToPrjTravel()+ccdp.getAuditCostFtToPrjTravel());
						checkCcdp.setAuditCostPrepay(checkCcdp.getAuditCostPrepay()+ccdp.getAuditCostPrepay());
						checkCcdp.setAuditCostPrepayTravel(checkCcdp.getAuditCostPrepayTravel()+ccdp.getAuditCostPrepayTravel());
						checkCcdpMap.put(ccdp.getPriceNum(), checkCcdp);
					}
				}
				// 添加新的报价明细
				for(CopCaseDetailPrice copCasePrice : checkCcdpMap.values()) {
					CopCaseDetailPrice copCaseDetailPrice = new CopCaseDetailPrice();
					copCaseDetailPrice.setCaseDetailId(ccd1map.get(copCasePrice.getPriceNum()).getId());
					copCaseDetailPrice.setPriceNum(copCasePrice.getPriceNum());
					copCaseDetailPrice.setOnceNum(copCasePrice.getOnceNum());
					copCaseDetailPrice.setOnceCode(copCasePrice.getOnceCode());
					copCaseDetailPrice.setCaseId(copCasePrice.getCaseId());
					copCaseDetailPrice.setCaseCode(copCasePrice.getCaseCode());
					copCaseDetailPrice.setPriceType(copCasePrice.getPriceType());
					copCaseDetailPrice.setWbmCostFt(0-copCasePrice.getAuditCostFt());
					copCaseDetailPrice.setWbmCostFtTravel(0-copCasePrice.getAuditCostFtTravel());
					copCaseDetailPrice.setWbmCostFtToProject(0-copCasePrice.getAuditCostFtToPrj());
					copCaseDetailPrice.setWbmCostFtToProjectTravel(0-copCasePrice.getAuditCostFtToPrjTravel());
					copCaseDetailPrice.setWbmCostPrepay(0-copCasePrice.getAuditCostPrepay());
					copCaseDetailPrice.setWbmCostPrepayTravel(0-copCasePrice.getAuditCostPrepayTravel());
					copCaseDetailPrice.setAuditCostFt(0-copCasePrice.getAuditCostFt());
					copCaseDetailPrice.setAuditCostFtTravel(0-copCasePrice.getAuditCostFtTravel());
					copCaseDetailPrice.setAuditCostFtToPrj(0-copCasePrice.getAuditCostFtToPrj());
					copCaseDetailPrice.setAuditCostFtToPrjTravel(0-copCasePrice.getAuditCostFtToPrjTravel());
					copCaseDetailPrice.setAuditCostPrepay(0-copCasePrice.getAuditCostPrepay());
					copCaseDetailPrice.setAuditCostPrepayTravel(0-copCasePrice.getAuditCostPrepayTravel());
					copCaseDetailPrice.setStatus("A0");
					copCaseDetailPrice.setRemarks("转预付费报价明细添加抵消数据");
					copCaseDetailPrice.setCreateBy(user);
					copCaseDetailPrice.setCreateDate(date);
					copCaseDetailPrice.setUpdateBy(user);
					copCaseDetailPrice.setUpdateDate(date);
					copCaseDetailPrice.setDelFlag("0");
					copCaseDetailPrice.setSaveFlag("1");
					copCaseDetailPriceService.save(copCaseDetailPrice);
					
					CopCaseDetailPrice newCopCaseDetailPrice = new CopCaseDetailPrice();
					newCopCaseDetailPrice.setCaseDetailId(ccd2map.get(copCasePrice.getPriceNum()).getId());
					newCopCaseDetailPrice.setPriceNum(copCasePrice.getPriceNum());
					newCopCaseDetailPrice.setOnceNum(copCasePrice.getOnceNum()+"_1");
					newCopCaseDetailPrice.setOnceCode(copCasePrice.getOnceCode()+"_1");
					newCopCaseDetailPrice.setCaseId(copCasePrice.getCaseId());
					newCopCaseDetailPrice.setCaseCode(copCasePrice.getCaseCode());
					newCopCaseDetailPrice.setPriceType(copCasePrice.getPriceType());
					newCopCaseDetailPrice.setWbmCostFt(copCasePrice.getAuditCostFt());
					newCopCaseDetailPrice.setWbmCostFtTravel(copCasePrice.getAuditCostFtTravel());
					newCopCaseDetailPrice.setWbmCostFtToProject(copCasePrice.getAuditCostFtToPrj());
					newCopCaseDetailPrice.setWbmCostFtToProjectTravel(copCasePrice.getAuditCostFtToPrjTravel());
					
					newCopCaseDetailPrice.setAuditCostFt(copCasePrice.getAuditCostFt());
					newCopCaseDetailPrice.setAuditCostFtTravel(copCasePrice.getAuditCostFtTravel());
					newCopCaseDetailPrice.setAuditCostFtToPrj(copCasePrice.getAuditCostFtToPrj());
					newCopCaseDetailPrice.setAuditCostFtToPrjTravel(copCasePrice.getAuditCostFtToPrjTravel());
					if("2".equals(oldPayType)) {
						newCopCaseDetailPrice.setWbmCostPrepay(copCasePrice.getAuditCostFt());
						newCopCaseDetailPrice.setWbmCostPrepayTravel(copCasePrice.getAuditCostFtTravel());
						newCopCaseDetailPrice.setAuditCostPrepay(copCasePrice.getAuditCostFt());
						newCopCaseDetailPrice.setAuditCostPrepayTravel(copCasePrice.getAuditCostFtTravel());
						
					}
					if("3".equals(oldPayType)) {
						newCopCaseDetailPrice.setWbmCostPrepay(copCasePrice.getAuditCostFtToPrj());
						newCopCaseDetailPrice.setWbmCostPrepayTravel(copCasePrice.getAuditCostFtToPrjTravel());
						newCopCaseDetailPrice.setAuditCostPrepay(copCasePrice.getAuditCostFtToPrj());
						newCopCaseDetailPrice.setAuditCostPrepayTravel(copCasePrice.getAuditCostFtToPrjTravel());
						
					}
					newCopCaseDetailPrice.setStatus("A0");
					newCopCaseDetailPrice.setCreateBy(user);
					newCopCaseDetailPrice.setCreateDate(date);
					newCopCaseDetailPrice.setUpdateBy(user);
					newCopCaseDetailPrice.setUpdateDate(date);
					newCopCaseDetailPrice.setDelFlag("0");
					newCopCaseDetailPrice.setSaveFlag("1");
					copCaseDetailPriceService.save(newCopCaseDetailPrice);
				}
				
			}
			
			//CASE单次报价确认
			CopCasePriceConfirm copCasePriceConfirm = new CopCasePriceConfirm();
			copCasePriceConfirm.setDcPrjId(StringUtils.toString(jsonObject.get("projectCode")));
			copCasePriceConfirm.setDcPrjName(StringUtils.toString(jsonObject.get("projectName")));
			copCasePriceConfirm.setSalesId(salesId);
			copCasePriceConfirm.setSalesName(StringUtils.toString(jsonObject.get("salesName")));
			copCasePriceConfirm.setCaseId((StringUtils.toString(jsonObject.get("caseId"))));
			copCasePriceConfirm.setCaseCode(StringUtils.toString(jsonObject.get("caseCode")));
			copCasePriceConfirm.setOnceNum((StringUtils.toString(jsonObject.get("onceNum"))));
			copCasePriceConfirm.setOnceCode(StringUtils.toString(jsonObject.get("onceCode")));
			copCasePriceConfirm.setPriceType(priceType);
			copCasePriceConfirm.setServiceType(StringUtils.toString(jsonObject.get("serviceType")));
			copCasePriceConfirm.setPayType(payType);//1：预付费:2：单次:3：单次入项目
			copCasePriceConfirm.setCostPrepay(StringUtils.toDouble(jsonObject.get("costPrepay")));
			copCasePriceConfirm.setCostPrepayTravel(StringUtils.toDouble(jsonObject.get("costPrepayTravel")));
			copCasePriceConfirm.setCaseDesc(StringUtils.toString(jsonObject.get("remark")));
			copCasePriceConfirm.setPrePayInfo(dataObject == null?"" : dataObject.toJSONString());//预付费在项目中的使用情况(放入Data节点对应数据)
			
			/*String city = StringUtils.toString(jsonObject.get("city"));
			if(null != city && "北京市，天津市，上海市，重庆市，".contains(city)){
				city = city.substring(0, 2);
			}
			List<String> areaList = copCasePriceConfirmService.getExecareaBatch(city);
			String area = areaList.isEmpty()?"" : areaList.get(0);*/
			
			String province = StringUtils.toString(jsonObject.get("province"));
			copCasePriceConfirm.setAreaName(this.getprovinceAreaMap().get(province));
			copCasePriceConfirm.setProvince(province);
			copCasePriceConfirm.setCityId(StringUtils.toString(jsonObject.get("cityId")));
			copCasePriceConfirm.setCityName(StringUtils.toString(jsonObject.get("city")));
			copCasePriceConfirm.setShareNo(StringUtils.toString(jsonObject.get("shareNo")));
			
			String areaName = StringUtils.toString(jsonObject.get("areaName"));
			if(areaName.contains("：")){
				areaName = areaName.split("：")[1];
			}
			copCasePriceConfirm.setResBaseOrg(areaName);
			copCasePriceConfirm.setResstattypeName(StringUtils.toString(jsonObject.get("abilityTypeName")));
			copCasePriceConfirm.setStatus("A0");
			copCasePriceConfirm.setCreateBy(user);
			copCasePriceConfirm.setCreateDate(comfirmDate);
			copCasePriceConfirm.setUpdateBy(user);
			copCasePriceConfirm.setUpdateDate(date);
			if("prepay".equals(invokeType)) {
				copCasePriceConfirm.setOnceCode(copCasePriceConfirm.getOnceCode()+"_1");
				copCasePriceConfirm.setOnceNum(copCasePriceConfirm.getOnceNum()+"_1");
				copCasePriceConfirm.setRemarks("转预付费");
				copCasePriceConfirm.setChangePrepayDate(new Date());
			}
			copCasePriceConfirmService.save(copCasePriceConfirm);
			
			//CopCaseDetail copDetail = new CopCaseDetail();
			//copDetail.setOnceNum(onceNum);
			//List<CopCaseDetail> copDetailList = copCaseDetailService.findList(copDetail);
			//cstOrderCostInfoService.getCalculateCaseDetailCost(copDetailList, StringUtils.toString(jsonObject.get("payType"), StringUtils.toString(jsonObject.get("projectCode"));
			
			//解析报价数组（COP_SALES_USE_DETAIL）
			if("1".equals(payType)){
				JSONArray data = (JSONArray) jsonObject.get("data");
				if(data == null){
					json.put("code", 10);
					json.put("message", "失败，报价类型为预付费时Data数据不能为空");
					retVal = json.toJSONString();
					return retVal;
				}
				List<CopCaseData> copCaseDataList = data.toJavaList(CopCaseData.class);
				for(CopCaseData copCaseData : copCaseDataList){
					//需要支付的金额
					Double actualCost = copCaseData.getActualCost();
					//项目内可用于支付金额
					Double prjCost = (double)0;
					//顺序从项目的订单中扣除所需费用
					CopSalesAccount copSalesAccount = new CopSalesAccount();
					copSalesAccount.setDcPrjId(copCaseData.getProjectCode());
					copSalesAccount.setStatusChangeDate(DateUtils.parseDate(DateUtils.getDate(), "yyyy-MM-dd"));
					copSalesAccount.setStatus("A0");
					List<CopSalesAccount> copSalesAccountList = copSalesAccountService.findList(copSalesAccount);
	
					//计算项目预付费是否足够
					for(CopSalesAccount account : copSalesAccountList){
						//人工
						if("1".equals(priceType)){
							prjCost = prjCost + account.getFinalAmount()-account.getUsedAmount();
						}else{//备件
							prjCost = prjCost + account.getBackFinalAmount()-account.getBackUsedAmount();
						}
					}
					if(prjCost.compareTo(actualCost) < 0){
						json.put("code", 10);
						json.put("message", "失败，项目预付费金额【"+prjCost+"】不足支付");
						retVal = json.toJSONString();
						return retVal;
					}
					
					//该项目下有多个订单
					for(CopSalesAccount account : copSalesAccountList){
						if(actualCost > 0){
							//订单的可用金额
							Double resAccount = (double) 0;
							//人工
							if("1".equals(priceType)){
								resAccount = account.getFinalAmount()-account.getUsedAmount();
							}else{//备件
								resAccount = account.getBackFinalAmount()-account.getBackUsedAmount(); 
							}
							//可用金额大于所需金额
							if(resAccount >= copCaseData.getActualCost()){
								//人工
								if("1".equals(priceType)){
									account.setUsedAmount(account.getUsedAmount()+copCaseData.getActualCost());
								}else{//备件
									account.setBackUsedAmount(account.getBackUsedAmount()+copCaseData.getActualCost());
								}
								actualCost = (double) 0;
							}else{
								//人工
								if("1".equals(priceType)){
									account.setUsedAmount(account.getFinalAmount());
								}else{//备件
									account.setBackUsedAmount(account.getBackFinalAmount());
								}
								actualCost = actualCost-resAccount;
							}
							
							account.setUpdateBy(user);
							account.setUpdateDate(date);
							copSalesAccountService.save(account);
							
							CopSalesUseDetail copSalesUseDetail = new CopSalesUseDetail();
							copSalesUseDetail.setCaseConfirmId(copCasePriceConfirm.getId());
							copSalesUseDetail.setSalesAccountId(account.getId());
							copSalesUseDetail.setAccountId(account.getAccountId());
							copSalesUseDetail.setDcPrjId(copCaseData.getProjectCode());
							copSalesUseDetail.setOrderId(account.getOrderId());
							copSalesUseDetail.setPriceType(priceType);
							copSalesUseDetail.setUseAmount(copCaseData.getActualCost());
							copSalesUseDetail.setUseDesc(copCaseData.getProjectCode()+":"+copCaseData.getActualCost());//使用描述
							copSalesUseDetail.setStatus("A0");
							copSalesUseDetail.setCreateBy(user);
							copSalesUseDetail.setCreateDate(date);
							copSalesUseDetail.setUpdateBy(user);
							copSalesUseDetail.setUpdateDate(date);
							if(copCaseDataList.size() > 1) {
								BigDecimal useAmount = BigDecimal.valueOf(copSalesUseDetail.getUseAmount());
								BigDecimal caseAmount = BigDecimal.valueOf(copCasePriceConfirm.getCostPrepay()+copCasePriceConfirm.getCostPrepayTravel());
								copSalesUseDetail.setCostScale(useAmount.divide(caseAmount, 5, BigDecimal.ROUND_HALF_UP).doubleValue());
							} else {
								copSalesUseDetail.setCostScale(1.0);
							}
							copSalesUseDetailService.save(copSalesUseDetail);
						}else{
							break;
						}
					}
				}
			}
			
			// 成功后修改报价确认表状态
			copCasePriceConfirm.setStatus("A2");
			copCasePriceConfirmService.save(copCasePriceConfirm);
			
			json.put("code", 200);
			json.put("message", "成功");
			retVal = json.toJSONString();
			return retVal;
		} catch (Exception e) {
			json.put("code", 10);
			json.put("message", "失败，"+e.getMessage());
			retVal = json.toJSONString();
			return retVal;
		}
	}
	
	
	/**
	 * 写销售员账户表
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/service/wbm.case.salesAccount", method = RequestMethod.POST, produces = "application/json")
	public String getSalesAccount(HttpServletRequest request){
		
		String line;
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		User user = new User("admin");
		Date date = new Date();
		String retVal = "";
		String responseJsonStr = "";
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

			while ((line = br.readLine()) != null) 
				sb.append(line);
			
			br.close();
			responseJsonStr = sb.toString();
			
			//报错信息
			String errorMessage = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			JSONObject jsonObject = JSONObject.parseObject(responseJsonStr);
			String dcPrjId = StringUtils.toString(jsonObject.get("dcPrjId"));
			String dcPrjName = StringUtils.toString(jsonObject.get("dcPrjName"));
			String staffId = StringUtils.toString(jsonObject.get("staffId"));
			String isShared = "A0";
			if(jsonObject.get("isShared") != null && "false".equals(StringUtils.toString(jsonObject.get("isShared")))) {
				isShared = "A1";
			}
			String prjType = StringUtils.toString(jsonObject.get("prjType"));
			String changedPrjId = StringUtils.toString(jsonObject.get("changedPrjId"));
			
			//查询对应销售项目下的账户信息
			CopSalesAccount oldCopSalesAccount = null;
			String oldPrjType = "";
			CopSalesAccount copSalesAccount = new CopSalesAccount();
			copSalesAccount.setDcPrjId(dcPrjId);
			copSalesAccount.setStaffId(staffId);
			copSalesAccount.setStatus("A0");
			List<CopSalesAccount> copSalesAccountList = copSalesAccountService.findList(copSalesAccount);
			if(copSalesAccountList != null && !copSalesAccountList.isEmpty()){
				oldCopSalesAccount = copSalesAccountList.get(0);
				oldPrjType = oldCopSalesAccount.getPrjType();
			}
			
			//接口传来的订单信息
			String orderIds = "";
			BigDecimal prodManCost = BigDecimal.ZERO;
			BigDecimal finalManCost = BigDecimal.ZERO;
			BigDecimal prodBackCost = BigDecimal.ZERO;
			BigDecimal finalBackCost = BigDecimal.ZERO;
			Date serviceBegin = null;
			Date serviceEnd = null;
			
			JSONArray data = (JSONArray) jsonObject.get("data");
			if(data == null){
				json.put("code", 10);
				
				// 项目变更，先行支持服务取消，需要后台提示，进行验证，再作废账户。
				if(copSalesAccountList != null && !copSalesAccountList.isEmpty()) {
					json.put("message", "该项目存在销售预付费账户，请先确认交付项目信息，再调整销售预付费账户！");
				} else {
					json.put("message", "失败，data数据为空！");
				}
				
				retVal = json.toJSONString();
				return retVal;
			}
			
			List<CostData> copCaseDataList = data.toJavaList(CostData.class);
			for(CostData costData : copCaseDataList){
				orderIds = costData.getOrderId() + "," + orderIds;
				prodManCost = prodManCost.add(new BigDecimal(costData.getProdManCost()));
				finalManCost = finalManCost.add(new BigDecimal(costData.getFinalManCost()));
				prodBackCost = prodBackCost.add(new BigDecimal(costData.getProdBackCost()));
				finalBackCost = finalBackCost.add(new BigDecimal(costData.getFinalBackCost()));
				
				Date begin = sdf.parse(costData.getServiceBegin());
				Date end = sdf.parse(costData.getServiceEnd());
				
				if(serviceBegin == null){
					serviceBegin = begin;
				}else{
					if(begin.compareTo(serviceBegin) < 0){
						serviceBegin = begin;
					}
				}
				
				if(serviceEnd == null){
					serviceEnd = end;
				}else{
					if(end.compareTo(serviceEnd) > 0){
						serviceEnd = end;
					}
				}
			}
			
			orderIds = "".equals(orderIds)?"" : orderIds.substring(0, orderIds.length()-1);
			
			//新账户信息保存
			boolean isSaveOld = false;
			boolean isSaveNew = true;
			CopSalesAccount newCopSalesAccount = new CopSalesAccount();
			newCopSalesAccount.setAccountId(IdGen.uuid());//账户ID（和项目绑定）
			newCopSalesAccount.setDcPrjId(dcPrjId);
			newCopSalesAccount.setDcPrjName(dcPrjName);
			newCopSalesAccount.setOrderId(orderIds);
			newCopSalesAccount.setStaffId(staffId);
			newCopSalesAccount.setIsShared(isShared);//是否可跨项目
			newCopSalesAccount.setPrjType(prjType);
			newCopSalesAccount.setServiceBegin(serviceBegin);
			newCopSalesAccount.setServiceEnd(serviceEnd);
			
			newCopSalesAccount.setProdAmount(prodManCost.doubleValue());
			newCopSalesAccount.setFinalAmount(finalManCost.doubleValue());
			newCopSalesAccount.setBackProdAmount(prodBackCost.doubleValue());
			newCopSalesAccount.setBackFinalAmount(finalBackCost.doubleValue());
			
			newCopSalesAccount.setCreateBy(user);
			newCopSalesAccount.setCreateDate(date);
			newCopSalesAccount.setUpdateBy(user);
			newCopSalesAccount.setUpdateDate(date);
			newCopSalesAccount.setStatus("A0");
			newCopSalesAccount.setSaveFlag("1");
			
			//预交付
			if("preDelivery".equals(prjType)){
				if(copSalesAccountList != null && !copSalesAccountList.isEmpty()){
					errorMessage = "失败，预交付状态下已存在对应的销售账户！";
				}else{
					newCopSalesAccount.setUsedAmount((double) 0);
					newCopSalesAccount.setBackUsedAmount((double) 0);
				}
			}
			
			//交付
			else if("delivery".equals(prjType)){
				//已存在该项目
				if(copSalesAccountList != null && !copSalesAccountList.isEmpty()){
					//预交付,预交付变更,预交付转交付,项目号变更--->交付
					if(!"preDelivery".equals(oldPrjType) && !"preDeliveryUpdate".equals(oldPrjType) && !"preDeliveryToDelivery".equals(oldPrjType) && !"codeUpdate".equals(oldPrjType)){
						errorMessage = "失败，交付阶段上级必须为预交付,预交付变更,预交付转交付阶段！";
					}else{
						if(oldCopSalesAccount.getUsedAmount().compareTo(finalManCost.doubleValue()) > 0
							|| oldCopSalesAccount.getBackUsedAmount().compareTo(finalBackCost.doubleValue()) > 0){
							errorMessage = "失败，该项目的金额花费已超过变更后的核定费用！";
						}else{
							newCopSalesAccount.setAccountId(oldCopSalesAccount.getAccountId());//账户ID（和项目绑定）
							newCopSalesAccount.setUsedAmount(oldCopSalesAccount.getUsedAmount());
							newCopSalesAccount.setBackUsedAmount(oldCopSalesAccount.getBackUsedAmount());
							isSaveOld = true;
						}
					}
				}else{
					newCopSalesAccount.setUsedAmount((double) 0);
					newCopSalesAccount.setBackUsedAmount((double) 0);
				}
			}
			
			//交付变更,预交付变更,预交付转交付
			else if("deliveryUpdate".equals(prjType) || "preDeliveryUpdate".equals(prjType) || "preDeliveryToDelivery".equals(prjType)){
				//不存在该项目
				if(copSalesAccountList == null || copSalesAccountList.isEmpty()){
					errorMessage = "失败，交付变更,预交付变更,预交付转交付状态下不存在对应的销售账户！";
				}else{
					//交付,项目号变更--->交付变更
					if("deliveryUpdate".equals(prjType) && !"delivery,deliveryUpdate".contains(oldPrjType) && !"codeUpdate".equals(oldPrjType)){
						errorMessage = "失败，交付变更阶段上级阶段必须为交付阶段！";
					}
					//预交付,项目号变更--->预交付变更
					if("preDeliveryUpdate".equals(prjType) && !"preDelivery".equals(oldPrjType) && !"codeUpdate".equals(oldPrjType)){
						errorMessage = "失败，预交付变更阶段上级阶段必须为预交付阶段！";
					}
					//预交付,预交付变更,项目号变更--->预交付转交付
					if("preDeliveryToDelivery".equals(prjType) && !"preDelivery".equals(oldPrjType) && !"preDeliveryUpdate".equals(oldPrjType) && !"codeUpdate".equals(oldPrjType)){
						errorMessage = "失败，预交付转交付阶段上级阶段必须为预交付阶段或预交付变更阶段！";
					}
					
					if(oldCopSalesAccount.getUsedAmount().compareTo(finalManCost.doubleValue()) > 0
							|| oldCopSalesAccount.getBackUsedAmount().compareTo(finalBackCost.doubleValue()) > 0){
							errorMessage = "失败，该项目的金额花费已超过变更后的核定费用！";
					}else{
						newCopSalesAccount.setAccountId(oldCopSalesAccount.getAccountId());//账户ID（和项目绑定）
						newCopSalesAccount.setUsedAmount(oldCopSalesAccount.getUsedAmount());
						newCopSalesAccount.setBackUsedAmount(oldCopSalesAccount.getBackUsedAmount());
						isSaveOld = true;
					}
				}
			}
			
			//合同取消
			else if("contractCancel".equals(prjType)){
				//不存在该项目
				if(copSalesAccountList == null || copSalesAccountList.isEmpty()){
					errorMessage = "失败，合同取消状态下不存在对应的销售账户！";
				}else{
					isSaveOld = true;
					isSaveNew = false;
				}
			}
			
			//项目号变更
			else if("codeUpdate".equals(prjType)){
				//不存在该项目
				if(copSalesAccountList == null || copSalesAccountList.isEmpty()){
					errorMessage = "失败，项目号变更状态下不存在对应的销售账户！";
				}else{
					if(oldCopSalesAccount.getUsedAmount().compareTo(finalManCost.doubleValue()) > 0
							|| oldCopSalesAccount.getBackUsedAmount().compareTo(finalBackCost.doubleValue()) > 0){
							errorMessage = "失败，该项目的已用金额已超过变更后的核定费用！";
					}else{
						newCopSalesAccount.setAccountId(oldCopSalesAccount.getAccountId());//账户ID（和项目绑定）
						newCopSalesAccount.setDcPrjId(changedPrjId);
						newCopSalesAccount.setUsedAmount(oldCopSalesAccount.getUsedAmount());
						newCopSalesAccount.setBackUsedAmount(oldCopSalesAccount.getBackUsedAmount());
						isSaveOld = true;
					}
				}
			}
			
			//有报错
			if(errorMessage != null && !"".equals(errorMessage)){
				json.put("code", 10);
				json.put("message", errorMessage);
				retVal = json.toJSONString();
				return retVal;
			}
			
			if(isSaveNew){
				copSalesAccountService.save(newCopSalesAccount);
			}
			
			if(isSaveOld){
				oldCopSalesAccount.setStatus("A1");
				oldCopSalesAccount.setPreStatus("A0");
				oldCopSalesAccount.setStatusChangeDate(date);
				copSalesAccountService.save(oldCopSalesAccount);
			}
			
			json.put("code", 200);
			json.put("message", "成功");
			retVal = json.toJSONString();
			return retVal;
		} catch (Exception e) {
			json.put("code", 10);
			json.put("message", "失败，"+e.getMessage());
			retVal = json.toJSONString();
			return retVal;
		}
	}
	

	/**
	 * 批量核销（单次中的明细一次性全部核销）
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/service/wbm.case.batchCaseCancel", method = RequestMethod.POST, produces = "application/json")
	public String getBatchCaseCancel(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		
		String line;
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		JSONObject json = new JSONObject();
		User user = new User("admin");
		Date date = new Date();
		String retVal = "";
		String responseJsonStr = "";
			
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

			while ((line = br.readLine()) != null) 
				sb.append(line);
			
			br.close();
			responseJsonStr = sb.toString();
			
			JSONObject jsonObject = JSONObject.parseObject(responseJsonStr);
			
			// 报价类型：正常报价=normal 取消=cancel 核销=write_off 转先行支持=prepay
			String invokeType = StringUtils.toString(jsonObject.get("invoke_type"));
			
			JSONArray data = (JSONArray) jsonObject.get("data");
			List<CopCaseData> copCaseDataList = data.toJavaList(CopCaseData.class);
			Map<String, String> cancelMap = Maps.newHashMap();
			List<String> onceCodes = Lists.newArrayList();
			for(CopCaseData ccd : copCaseDataList) {
				cancelMap.put(ccd.getOnceCode(), ccd.getWoProjectCode());
				onceCodes.add(ccd.getOnceCode());
			}
			
			//检查CASE单次报价明细表中单次报价ID是否已存在
			List<CopCaseDetail> copDetailList = copCaseDetailService.getCaseBatch(onceCodes, "A0");
			if(copDetailList == null || copDetailList.isEmpty()) {
				json.put("code", 10);
				json.put("message", "失败， 未找到该单次报价明细。");
				retVal = json.toJSONString();
				return retVal;
			}
			
			// 按照priceNum合并明细
			Map<String, CopCaseDetail> checkMap = Maps.newHashMap();
			for(CopCaseDetail ccd : copDetailList) {
				if(checkMap.get(ccd.getPriceNum()) == null) {
					checkMap.put(ccd.getPriceNum(), ccd);
				} else {
					CopCaseDetail checkCcd = checkMap.get(ccd.getPriceNum());
					checkCcd.setPartAmount(checkCcd.getPartAmount()+ccd.getPartAmount());
					checkCcd.setPartDeliveryPrice(checkCcd.getPartDeliveryPrice()+ccd.getPartDeliveryPrice());
					checkCcd.setManWorkload(checkCcd.getManWorkload()+ccd.getManWorkload());
					checkCcd.setManTravelPrice(checkCcd.getManTravelPrice()+ccd.getManTravelPrice());
					checkMap.put(ccd.getPriceNum(), checkCcd);
				}
			}
			
			// 插入核销的明细
			Map<String, String> idToPriceNumMap = Maps.newHashMap();
			for(String priceNum : checkMap.keySet()) {
				CopCaseDetail copCaseDetail1 = checkMap.get(priceNum);
				CopCaseDetail copCaseDetail = new CopCaseDetail();
				copCaseDetail.setPriceNum(copCaseDetail1.getPriceNum());
				copCaseDetail.setCaseId(copCaseDetail1.getCaseId());
				copCaseDetail.setCaseCode(copCaseDetail1.getCaseCode());
				copCaseDetail.setOnceNum(copCaseDetail1.getOnceNum());
				copCaseDetail.setOnceCode(copCaseDetail1.getOnceCode());
				copCaseDetail.setPriceType(copCaseDetail1.getPriceType());
				copCaseDetail.setServiceType(copCaseDetail1.getServiceType());
				copCaseDetail.setDcPrjId(copCaseDetail1.getDcPrjId());
				copCaseDetail.setDcPrjName(copCaseDetail1.getDcPrjName());
				copCaseDetail.setServiceContent(copCaseDetail1.getServiceContent());
				copCaseDetail.setPartsInfo(copCaseDetail1.getPartsInfo());
				copCaseDetail.setFactoryId(copCaseDetail1.getFactoryId());
				copCaseDetail.setFactoryName(copCaseDetail1.getFactoryName());
				copCaseDetail.setModelId(copCaseDetail1.getModelId());
				copCaseDetail.setModelName(copCaseDetail1.getModelName());
				copCaseDetail.setSnId(copCaseDetail1.getSnId());
				copCaseDetail.setIsSpecialAudit(copCaseDetail1.getIsSpecialAudit());
				copCaseDetail.setSpecialAuditRemark(copCaseDetail1.getSpecialAuditRemark());
				copCaseDetail.setWoProjectCode(cancelMap.get(copCaseDetail1.getOnceCode()));
				copCaseDetail.setWoProjectName(copCaseDetail1.getWoProjectName());
				copCaseDetail.setWoReason(copCaseDetail1.getWoReason());
				copCaseDetail.setWoApprovalBy(copCaseDetail1.getWoApprovalBy());
				copCaseDetail.setWoApprovalDate(date);
				copCaseDetail.setWoApprovalRemark(copCaseDetail1.getWoApprovalRemark());
				copCaseDetail.setInvokeType(invokeType);
				copCaseDetail.setManRoleId(copCaseDetail1.getManRoleId());
				copCaseDetail.setManEngineerLevel(copCaseDetail1.getManEngineerLevel());
				copCaseDetail.setManResourceType(copCaseDetail1.getManResourceType());
				if("1".equals(copCaseDetail1.getPriceType())) {
					copCaseDetail.setManWorkload(0 - copCaseDetail1.getManWorkload());
					copCaseDetail.setManPrice(0 - copCaseDetail1.getManPrice());
					copCaseDetail.setManTravelPrice(0 - copCaseDetail1.getManTravelPrice());
				}
				if("2".equals(copCaseDetail1.getPriceType())) {
					copCaseDetail.setPartAmount(0-copCaseDetail1.getPartAmount());
					copCaseDetail.setPartPrice(copCaseDetail1.getPartPrice());
					copCaseDetail.setPartDeliveryPrice(0-copCaseDetail1.getPartDeliveryPrice());
				}
				copCaseDetail.setStatus("A0");
				copCaseDetail.setCreateBy(user);
				copCaseDetail.setCreateDate(date);
				copCaseDetail.setUpdateBy(user);
				copCaseDetail.setUpdateDate(date);
				
				copCaseDetailService.save(copCaseDetail);
				idToPriceNumMap.put(copCaseDetail.getPriceNum(), copCaseDetail.getId());
			}
			
			List<CopCaseDetailPrice> copDetailPriceList = copCaseDetailPriceService.getCasePriceBatch(onceCodes, "A0");
			Map<String, CopCaseDetailPrice> checkCcdpMap = Maps.newHashMap();
			for(CopCaseDetailPrice ccdp : copDetailPriceList) {
				if(checkCcdpMap.get(ccdp.getPriceNum()) == null) {
					checkCcdpMap.put(ccdp.getPriceNum(), ccdp);
				} else {
					CopCaseDetailPrice checkCcdp = checkCcdpMap.get(ccdp.getPriceNum());checkCcdp.setAuditCostFt(checkCcdp.getAuditCostFt()+ccdp.getAuditCostFt());
					checkCcdp.setWbmCostFt(checkCcdp.getWbmCostFt()+ccdp.getWbmCostFt());
					checkCcdp.setWbmCostFtTravel(checkCcdp.getWbmCostFtTravel()+ccdp.getWbmCostFtTravel());
					checkCcdp.setWbmCostFtToProject(checkCcdp.getWbmCostFtToProject()+ccdp.getWbmCostFtToProject());
					checkCcdp.setWbmCostFtToProjectTravel(checkCcdp.getWbmCostFtToProjectTravel()+ccdp.getWbmCostFtToProjectTravel());
					checkCcdp.setWbmCostPrepay(checkCcdp.getWbmCostPrepay()+ccdp.getWbmCostPrepay());
					checkCcdp.setWbmCostPrepayTravel(checkCcdp.getWbmCostPrepayTravel()+ccdp.getWbmCostPrepayTravel());
					
					checkCcdp.setAuditCostFt(checkCcdp.getAuditCostFt()+ccdp.getAuditCostFt());
					checkCcdp.setAuditCostFtTravel(checkCcdp.getAuditCostFtTravel()+ccdp.getAuditCostFtTravel());
					checkCcdp.setAuditCostFtToPrj(checkCcdp.getAuditCostFtToPrj()+ccdp.getAuditCostFtToPrj());
					checkCcdp.setAuditCostFtToPrjTravel(checkCcdp.getAuditCostFtToPrjTravel()+ccdp.getAuditCostFtToPrjTravel());
					checkCcdp.setAuditCostPrepay(checkCcdp.getAuditCostPrepay()+ccdp.getAuditCostPrepay());
					checkCcdp.setAuditCostPrepayTravel(checkCcdp.getAuditCostPrepayTravel()+ccdp.getAuditCostPrepayTravel());
					checkCcdpMap.put(ccdp.getPriceNum(), checkCcdp);
				}
			}
			

			// 插入核销的明细报价
			for(String priceNum : checkCcdpMap.keySet()) {
				CopCaseDetailPrice copCasePrice1 = checkCcdpMap.get(priceNum);
				CopCaseDetailPrice ccdp = new CopCaseDetailPrice();
				ccdp.setCaseDetailId(idToPriceNumMap.get(copCasePrice1.getPriceNum()));
				ccdp.setPriceNum(copCasePrice1.getPriceNum());
				ccdp.setOnceNum(copCasePrice1.getOnceNum());
				ccdp.setOnceCode(copCasePrice1.getOnceCode());
				ccdp.setCaseId(copCasePrice1.getCaseId());
				ccdp.setCaseCode(copCasePrice1.getCaseCode());
				ccdp.setPriceType(copCasePrice1.getPriceType());
				ccdp.setWbmCostPrepay(0-copCasePrice1.getWbmCostPrepay());
				ccdp.setWbmCostPrepayTravel(0-copCasePrice1.getWbmCostPrepayTravel());
				ccdp.setWbmCostFt(0-copCasePrice1.getWbmCostFt());
				ccdp.setWbmCostFtTravel(0-copCasePrice1.getWbmCostFtTravel());
				ccdp.setWbmCostFtToProject(0-copCasePrice1.getWbmCostFtToProject());
				ccdp.setWbmCostFtToProjectTravel(0-copCasePrice1.getWbmCostFtToProjectTravel());
				ccdp.setStatus("0");
				ccdp.setCreateBy(user);
				ccdp.setCreateDate(date);
				ccdp.setUpdateBy(user);
				ccdp.setUpdateDate(date);
				ccdp.setAuditCostPrepay(0-copCasePrice1.getAuditCostPrepay());
				ccdp.setAuditCostPrepayTravel(0-copCasePrice1.getAuditCostPrepayTravel());
				ccdp.setAuditCostFt(0-copCasePrice1.getAuditCostFt());
				ccdp.setAuditCostFtTravel(0-copCasePrice1.getAuditCostFtTravel());
				ccdp.setAuditCostFtToPrj(0-copCasePrice1.getAuditCostFtToPrj());
				ccdp.setAuditCostFtToPrjTravel(0-copCasePrice1.getAuditCostFtToPrjTravel());
				
				copCaseDetailPriceService.save(ccdp);
			}
			
			json.put("code", 200);
			json.put("message", "成功");
			retVal = json.toJSONString();
			return retVal;
			
		}catch (Exception e) {
			json.put("code", 10);
			json.put("message", "失败，"+e.getMessage());
			retVal = json.toJSONString();
			return retVal;
		}
		
	}
	
	/**
	 * 获取省份所属的区域
	 * @return
	 */
	private Map<String, String> getprovinceAreaMap() {
		Map<String, String> provinceAreaMap = new HashMap<String, String>();
		provinceAreaMap.put("北京", "北区"); // 北京	北区
		provinceAreaMap.put("天津", "北区"); // 天津	北区
		provinceAreaMap.put("黑龙江", "北区"); // 黑龙江	北区
		provinceAreaMap.put("吉林", "北区"); // 吉林	北区
		provinceAreaMap.put("辽宁", "北区"); // 辽宁	北区
		provinceAreaMap.put("内蒙古", "北区"); // 内蒙古	北区
		provinceAreaMap.put("河北", "北区"); // 河北	北区
		provinceAreaMap.put("河南", "北区"); // 河南	北区
		provinceAreaMap.put("山西", "北区"); // 山西	北区
		provinceAreaMap.put("山东", "北区"); // 山东	北区
		provinceAreaMap.put("上海", "东区"); // 上海	东区
		provinceAreaMap.put("江苏", "东区"); // 江苏	东区
		provinceAreaMap.put("浙江", "东区"); // 浙江	东区
		provinceAreaMap.put("安徽", "东区"); // 安徽	东区
		provinceAreaMap.put("江西", "东区"); // 江西	东区
		provinceAreaMap.put("湖南", "东区"); // 湖南	东区
		provinceAreaMap.put("湖北", "东区"); // 湖北	东区
		provinceAreaMap.put("广东", "南区"); // 广东	南区
		provinceAreaMap.put("广西", "南区"); // 广西	南区
		provinceAreaMap.put("香港", "南区"); // 香港	南区
		provinceAreaMap.put("澳门", "南区"); // 澳门	南区
		provinceAreaMap.put("福建", "南区"); // 福建	南区
		provinceAreaMap.put("海南", "南区"); // 海南	南区
		provinceAreaMap.put("宁夏", "西北区"); // 宁夏	西北区
		provinceAreaMap.put("甘肃", "西北区"); // 甘肃	西北区
		provinceAreaMap.put("新疆", "西北区"); // 新疆	西北区
		provinceAreaMap.put("陕西", "西北区"); // 陕西	西北区
		provinceAreaMap.put("青海", "西北区"); // 青海	西北区
		provinceAreaMap.put("贵州", "西南区"); // 贵州	西南区
		provinceAreaMap.put("西藏", "西南区"); // 西藏	西南区
		provinceAreaMap.put("四川", "西南区"); // 四川	西南区
		provinceAreaMap.put("云南", "西南区"); // 云南	西南区
		provinceAreaMap.put("重庆", "西南区"); // 重庆	西南区
		
		return provinceAreaMap;
	}
}