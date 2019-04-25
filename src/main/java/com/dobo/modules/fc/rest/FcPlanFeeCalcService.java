package com.dobo.modules.fc.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dobo.common.service.ServiceException;
import com.dobo.modules.fc.common.FcCalcService;
import com.dobo.modules.fc.entity.FcOrderInfo;
import com.dobo.modules.fc.entity.FcPlanInnerFee;
import com.dobo.modules.fc.entity.FcPlanPayDetail;
import com.dobo.modules.fc.entity.FcPlanReceiptDetail;
import com.dobo.modules.fc.entity.FcProjectInfo;
import com.dobo.modules.fc.rest.entity.FcOrderInfoRest;
import com.dobo.modules.fc.rest.entity.FcPlanInnerFeeRest;
import com.dobo.modules.fc.rest.entity.FcPlanPayDetailRest;
import com.dobo.modules.fc.rest.entity.FcPlanReceiptDetailRest;
import com.dobo.modules.fc.rest.entity.FcProjectInfoRest;
import com.dobo.modules.fc.service.FcOrderInfoService;
import com.dobo.modules.fc.service.FcPlanInnerFeeService;
import com.dobo.modules.fc.service.FcPlanPayDetailService;
import com.dobo.modules.fc.service.FcPlanReceiptDetailService;
import com.dobo.modules.fc.service.FcProjectInfoService;
import com.google.common.collect.Lists;

@RestController
public class FcPlanFeeCalcService {
	
	protected static final Logger LOGGER = Logger.getLogger(FcPlanFeeCalcService.class);
	
	@Autowired
	private FcProjectInfoService fcProjectInfoService;
	@Autowired
	private FcPlanPayDetailService fcPlanPayDetailService;
	@Autowired
	private FcPlanReceiptDetailService fcPlanReceiptDetailService;
	@Autowired
	private FcPlanInnerFeeService fcPlanInnerFeeService;
	@Autowired
	private FcOrderInfoService fcOrderInfoService;


	/**
	 * 计算计划内财务费用
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/service/fc/calcInnerCost", method = RequestMethod.POST, produces = "application/json")
	public String calcInnerCost(HttpServletRequest request) {
		//System.out.println("begin read json "+new Date());
		
		String line;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		JSONObject json = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((line = br.readLine()) != null) 
				sb.append(line);
			br.close();
			
			json = JSON.parseObject(sb.toString());
			
			JSONObject retJson = calcInner(json);
			
			json = new JSONObject();
			json.put("data", retJson.get("data"));
			json.put("code", retJson.get("code"));
			json.put("msg", retJson.get("msg"));

			//System.out.println("end writer json "+new Date());	
		} catch (Exception e) {
			LOGGER.error("计划内财务费用计算失败", e);
			json = new JSONObject();
			json.put("data", "");
			json.put("code", "1");
			json.put("msg", "计划内财务费用计算失败："+e.getMessage());
		}
		
		return json.toJSONString();
	}

	/**
	 * 计算计划内财务费用
	 * @param calcParaJson
	 * @return
	 * @throws ParseException
	 */
	private JSONObject calcInner(JSONObject calcParaJson) throws ParseException{
		JSONObject json = new JSONObject();

		FcProjectInfoRest fpiRest = JSON.toJavaObject(calcParaJson.getJSONObject("data"), FcProjectInfoRest.class);
		String projectCode = fpiRest.getProjectCode();

		String data = "";
		String code = "0";
		String msg = projectCode+" 计划内财务费用计算成功";
		
		String insertDB = calcParaJson.getString("insertDB");
		
		//查看是否已存在使用标记未数据库的有效计划内财务费用
		List<FcProjectInfo> fpiList = fcProjectInfoService.findList(new FcProjectInfo(fpiRest.getProjectCode(), "A0"));
		String fpiId = null;
		Double existsInnerFee = null;
		if(fpiList != null && fpiList.size() == 1){
			fpiId = fpiList.get(0).getId();
			List<FcPlanInnerFee> existsInnerFeeList = fcPlanInnerFeeService.findList(new FcPlanInnerFee(fpiId, "A0", "1"));
			if(existsInnerFeeList != null && existsInnerFeeList.size() > 0){
				existsInnerFee = existsInnerFeeList.get(0).getFinancialCost();
			}
		}
		
		//计算计划内财务费用
		Double financialCost = 0.0;
		if(existsInnerFee != null){
			financialCost = existsInnerFee;
		}else{
			FcPlanInnerFeeRest fcPlanInnerFeeRest = FcCalcService.calcInner(fpiRest);
			fcPlanInnerFeeRest.setCalcParaJson(calcParaJson.toString());
			fpiRest.setFcPlanInnerFeeRestList(Lists.newArrayList(fcPlanInnerFeeRest));
			financialCost = fcPlanInnerFeeRest.getFinancialCost();
			
			if(financialCost == null){
				msg = fcPlanInnerFeeRest.getRemarks();
				code = "1";
			}else{
				try {
					//如果数据库中该项目的最新有效与计算的财务费用相等，则无需再入库
					boolean hasSameInnerFee = false;
					if(fpiId != null && financialCost != null){
						List<FcPlanInnerFee> fpifList = fcPlanInnerFeeService.findList(new FcPlanInnerFee(fpiId, "A0", "1", financialCost));
						if(fpifList != null && fpifList.size() > 0){
							hasSameInnerFee = true;
						}
					}
					
					//保存计划内财务费用到数据库
					if(!"A1".equals(insertDB) && !hasSameInnerFee){
						saveInner(fpiRest);
					}
				} catch (Exception e) {
					financialCost = null;
					code = "1";
					msg = "计划内财务费用计算失败："+e.getMessage();
				}
			}
		}

		//财务费用为空
		if(financialCost != null){
			data = BigDecimal.valueOf(financialCost).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		}
		
		json.put("data", data);
		json.put("code", code);
		json.put("msg", msg);
		
		return json;
	}
	
	/**
	 * 保存计划内财务费用
	 * @param fpiRest
	 */
	private void saveInner(FcProjectInfoRest fpiRest){
		//写入项目计划内财务费用，如果已存在则将其置为无效
		List<FcProjectInfo> fpiList = fcProjectInfoService.findList(new FcProjectInfo(fpiRest.getProjectCode(), "A0"));
		FcProjectInfo fpi = null;
		if(fpiList == null || fpiList.isEmpty()){
			fpi = new FcProjectInfo();
		}else if(fpiList.size() == 1){
			fpi = fpiList.get(0);
		}else{
			throw new ServiceException("该项目库中存在多条有效信息，无法保存，项目编号："+fpiRest.getProjectCode());
		}
		
		if(StringUtils.isEmpty(fpi.getProjectCode()))
			fpi.setProjectCode(fpiRest.getProjectCode());
		if(!StringUtils.equals(fpi.getProjectName(), fpiRest.getProjectName()))
			fpi.setProjectName(fpiRest.getProjectName());
		if(!StringUtils.equals(fpi.getCustName(), fpiRest.getCustName()))
			fpi.setCustName(fpiRest.getCustName());
		if(!StringUtils.equals(fpi.getFstSvcType(), fpiRest.getFstSvcType()))
			fpi.setFstSvcType(fpiRest.getFstSvcType());
		if(!StringUtils.equals(fpi.getSndSvcType(), fpiRest.getSndSvcType()))
			fpi.setSndSvcType(fpiRest.getSndSvcType());
		if(!StringUtils.equals(fpi.getSaleOrg(), fpiRest.getSaleOrg()))
			fpi.setSaleOrg(fpiRest.getSaleOrg());
		if(!StringUtils.equals(fpi.getBusinessCode(), fpiRest.getBusinessCode()))
			fpi.setBusinessCode(fpiRest.getBusinessCode());
		if(!StringUtils.equals(fpi.getSalesName(), fpiRest.getSalesName()))
			fpi.setSalesName(fpiRest.getSalesName());
		if(StringUtils.isEmpty(fpi.getHasWbmOrder()))
			fpi.setHasWbmOrder("A1");
		
		fpi.setSaveFlag("1");//直接更新
		fcProjectInfoService.save(fpi);
		
		//订单信息
		if(fpiRest.getFcOrderInfoRestList() != null){
			for(FcOrderInfoRest ordRest : fpiRest.getFcOrderInfoRestList()){
				List<FcOrderInfo> fcOrderInfoList = fcOrderInfoService.findList(new FcOrderInfo(fpi.getId(), ordRest.getOrderId(), "A0"));
				if(fcOrderInfoList == null || fcOrderInfoList.isEmpty()){
					FcOrderInfo ord = new FcOrderInfo(
							fpi.getId(), fpi, ordRest.getOrderId(), ordRest.getFstSvcType(), ordRest.getSndSvcType(), ordRest.getServiceDateBegin(), 
							ordRest.getServiceDateEnd(), ordRest.getSignAmount(), ordRest.getOwnProdCost(), ordRest.getSpecifySubCost());
					fcOrderInfoService.save(ord);
				}
			}
		}
		
		//计划收款
		if(fpiRest.getFcPlanReceiptDetailRestList() != null){
			for(FcPlanReceiptDetailRest fprdRest : fpiRest.getFcPlanReceiptDetailRestList()){
				List<FcPlanReceiptDetail> list = fcPlanReceiptDetailService.findList(new FcPlanReceiptDetail(fpi.getId(), fprdRest.getPlanReceiptTime(), fprdRest.getPlanReceiptAmount(), "A0"));
				if(list == null || list.isEmpty()){
					FcPlanReceiptDetail fprd = new FcPlanReceiptDetail(
					fpi.getId(), fpi, fprdRest.getDisplayOrder(), fprdRest.getPlanReceiptTime(), fprdRest.getPlanReceiptAmount(), 
					fprdRest.getPlanReceiptScale(), fprdRest.getPayType(), fprdRest.getPayCurrency(), fprdRest.getPlanReceiptDays());
					fcPlanReceiptDetailService.save(fprd);
				}
			}
		}
		
		//计划付款
		if(fpiRest.getFcPlanPayDetailRestList() != null){
			for(FcPlanPayDetailRest fprdRest : fpiRest.getFcPlanPayDetailRestList()){
				List<FcPlanPayDetail> list = fcPlanPayDetailService.findList(new FcPlanPayDetail(fpi.getId(), fprdRest.getPlanPayTime(), fprdRest.getPlanPayAmount()));
				if(list == null || list.isEmpty()){
					FcPlanPayDetail fprd = new FcPlanPayDetail(
					fpi.getId(), fpi, fprdRest.getDisplayOrder(), fprdRest.getPlanPayTime(), fprdRest.getPlanPayAmount(), 
					fprdRest.getPlanPayScale(), fprdRest.getPayType(), fprdRest.getPayCurrency(), 
					fprdRest.getPlanPayDays());
					fcPlanPayDetailService.save(fprd);
				}
			}
		}
		
		//计划内
		if(fpiRest.getFcPlanInnerFeeRestList() != null){
			for(FcPlanInnerFeeRest fpifRest : fpiRest.getFcPlanInnerFeeRestList()){
				List<FcPlanInnerFee> fpifList = fcPlanInnerFeeService.findList(new FcPlanInnerFee(fpi.getId(), "A0"));
				
				FcPlanInnerFee fpif = null;
				if(fpifList == null || fpifList.isEmpty()){
					fpif = new FcPlanInnerFee(
							fpi.getId(), fpi, fpifRest.getFinancialCost(), fpifRest.getCalculateTime(), 
							fpifRest.getLoanRate(), fpifRest.getDepositRate());
				}else if(fpifList.size() == 1){
					fpif = fpifList.get(0);
					fpif.setFcProjectInfoId(fpi.getId());
					fpif.setFcProjectInfo(fpi);
					fpif.setFinancialCost(fpifRest.getFinancialCost());
					fpif.setCalculateTime(fpifRest.getCalculateTime());
					fpif.setLoanRate(fpifRest.getLoanRate());
					fpif.setDepositRate(fpifRest.getDepositRate());
					fpif.setSaveFlag("0");//加时间戳
					fpif.setCalcParaJson(fpifRest.getCalcParaJson());
				}else{
					throw new ServiceException("该项目库中已存在多条计划内财务费用，无法保存，项目编号："+fpiRest.getProjectCode());
				}
				fcPlanInnerFeeService.save(fpif);
			}
		}
	}
	
}
