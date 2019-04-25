/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.dobo.common.config.Global;
import com.dobo.common.persistence.Page;
import com.dobo.common.service.RequestService;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.common.web.BaseController;
import com.dobo.modules.fc.entity.FcPlanOuterFee;
import com.dobo.modules.fc.service.FcPlanOuterFeeService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 项目计划外财务费用Controller
 * @author admin
 * @version 2016-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcPlanOuterFee")
public class FcPlanOuterFeeController extends BaseController {

	@Autowired
	private FcPlanOuterFeeService fcPlanOuterFeeService;
	
	@ModelAttribute
	public FcPlanOuterFee get(@RequestParam(required=false) String id) {
		FcPlanOuterFee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcPlanOuterFeeService.get(id);
		}
		if (entity == null){
			entity = new FcPlanOuterFee();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcPlanOuterFee:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcPlanOuterFee fcPlanOuterFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcPlanOuterFee> page = fcPlanOuterFeeService.findPage(new Page<FcPlanOuterFee>(request, response), fcPlanOuterFee); 
		model.addAttribute("page", page);
		return "modules/fc/fcPlanOuterFeeList";
	}

	@RequiresPermissions("fc:fcPlanOuterFee:view")
	@RequestMapping(value = "form")
	public String form(FcPlanOuterFee fcPlanOuterFee, Model model) {
		model.addAttribute("fcPlanOuterFee", fcPlanOuterFee);
		return "modules/fc/fcPlanOuterFeeForm";
	}
	
	@RequiresPermissions("fc:fcPlanOuterFee:edit")
	@RequestMapping(value = "count")
	public String count(FcPlanOuterFee fcPlanOuterFee, Model model) {
		model.addAttribute("fcPlanOuterFee", fcPlanOuterFee);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/fc/fcPlanOuterFeeCount";
	}

	@RequiresPermissions("fc:fcPlanOuterFee:edit")
	@RequestMapping(value = "save")
	public String save(FcPlanOuterFee fcPlanOuterFee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcPlanOuterFee)){
			return form(fcPlanOuterFee, model);
		}
		fcPlanOuterFeeService.save(fcPlanOuterFee);
		addMessage(redirectAttributes, "保存项目计划外财务费用成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanOuterFee/?repage";
	}
	
	@RequiresPermissions("fc:fcPlanOuterFee:edit")
	@RequestMapping(value = "delete")
	public String delete(FcPlanOuterFee fcPlanOuterFee, RedirectAttributes redirectAttributes) {
		fcPlanOuterFeeService.delete(fcPlanOuterFee);
		addMessage(redirectAttributes, "删除项目计划外财务费用成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanOuterFee/?repage";
	}
	
	@RequiresPermissions("fc:fcPlanOuterFee:edit")
	@RequestMapping(value = "planCount")
	public String planCount(FcPlanOuterFee fcPlanOuterFee, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) 
			throws UnsupportedEncodingException {
		//项目号
		String dcPrjId = request.getParameter("dcPrjId");
		//计划收款时间范围
		String planReceiptDateBegin = request.getParameter("planReceiptDateBegin");
		String planReceiptDateEnd = request.getParameter("planReceiptDateEnd");
		//计算结果
		String planResult = request.getParameter("planResult");//A0:结果入库/A1:结果不入库

		//按照收款时间查询项目范围
		if(!StringUtils.isEmpty(planReceiptDateBegin) && !StringUtils.isEmpty(planReceiptDateEnd)){
			String planDateBegin = DateUtils.formatDate(DateUtils.parseDate(planReceiptDateBegin), "yyyyMM");
			String planDateEnd = DateUtils.formatDate(DateUtils.parseDate(planReceiptDateEnd), "yyyyMM");
			
			JSONObject jsonreq = new JSONObject();
			jsonreq.put("calcYearMonth", planDateBegin + "-" + planDateEnd);//201701-201702
			jsonreq.put("projectCode", StringUtils.trimToEmpty(dcPrjId));
			//jsonreq.put("prjScope", "A1");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月
			jsonreq.put("insertDB", planResult); //A0:结果入库/A1:结果不入库
			jsonreq.put("userId", UserUtils.getUser().getId());
			//JSONObject jsonres = RequestService.requestService(RequestService.srvCalcOuterCostCondition, "", jsonreq.toJSONString(), "POST");
			JSONObject jsonres = RequestService.requestService(RequestService.srvCalcActualOuterCostCondition, "", jsonreq.toJSONString(), "POST");
			
			addMessage(redirectAttributes, (String)jsonres.get("msg"));
		}

		return "modules/fc/fcPlanOuterFeeCount";
	}

}