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
import com.dobo.modules.fc.entity.FcPlanInnerFee;
import com.dobo.modules.fc.service.FcPlanInnerFeeService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 项目计划内财务费用Controller
 * @author admin
 * @version 2016-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcPlanInnerFee")
public class FcPlanInnerFeeController extends BaseController {

	@Autowired
	private FcPlanInnerFeeService fcPlanInnerFeeService;
	
	@ModelAttribute
	public FcPlanInnerFee get(@RequestParam(required=false) String id) {
		FcPlanInnerFee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcPlanInnerFeeService.get(id);
		}
		if (entity == null){
			entity = new FcPlanInnerFee();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcPlanInnerFee:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcPlanInnerFee fcPlanInnerFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcPlanInnerFee> page = fcPlanInnerFeeService.findPage(new Page<FcPlanInnerFee>(request, response), fcPlanInnerFee); 
		model.addAttribute("page", page);
		return "modules/fc/fcPlanInnerFeeList";
	}

	@RequiresPermissions("fc:fcPlanInnerFee:view")
	@RequestMapping(value = "form")
	public String form(FcPlanInnerFee fcPlanInnerFee, Model model) {
		model.addAttribute("fcPlanInnerFee", fcPlanInnerFee);
		return "modules/fc/fcPlanInnerFeeForm";
	}
	
	@RequiresPermissions("fc:fcPlanInnerFee:edit")
	@RequestMapping(value = "count")
	public String count(FcPlanInnerFee fcPlanInnerFee, Model model) {
		model.addAttribute("fcPlanInnerFee", fcPlanInnerFee);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/fc/fcPlanInnerFeeCount";
	}

	@RequiresPermissions("fc:fcPlanInnerFee:edit")
	@RequestMapping(value = "save")
	public String save(FcPlanInnerFee fcPlanInnerFee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcPlanInnerFee)){
			return form(fcPlanInnerFee, model);
		}
		fcPlanInnerFeeService.save(fcPlanInnerFee);
		addMessage(redirectAttributes, "保存项目计划内财务费用成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanInnerFee/?repage";
	}
	
	@RequiresPermissions("fc:fcPlanInnerFee:edit")
	@RequestMapping(value = "delete")
	public String delete(FcPlanInnerFee fcPlanInnerFee, RedirectAttributes redirectAttributes) {
		fcPlanInnerFeeService.delete(fcPlanInnerFee);
		addMessage(redirectAttributes, "删除项目计划内财务费用成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanInnerFee/?repage";
	}

	@RequiresPermissions("fc:fcPlanInnerFee:edit")
	@RequestMapping(value = "planCount")
	public String planCount(FcPlanInnerFee fcPlanInnerFee, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) 
			throws UnsupportedEncodingException {
		//项目号
		String dcPrjId = request.getParameter("dcPrjId");
		//项目立项时间范围
		String dcPrjDateBegin = request.getParameter("dcPrjDateBegin");
		String dcPrjDateEnd = request.getParameter("dcPrjDateEnd");
		//计划收款时间范围
		String planPayDateBegin = request.getParameter("planPayDateBegin");
		String planPayDateEnd = request.getParameter("planPayDateEnd");
		//计算结果
		String planResult = request.getParameter("planResult");//A0:结果入库/A1:结果不入库
		
		//按照立项时间查询项目范围
		if(!StringUtils.isEmpty(dcPrjDateBegin) && !StringUtils.isEmpty(dcPrjDateEnd)){
			String dcDateBegin = DateUtils.formatDate(DateUtils.parseDate(dcPrjDateBegin), "yyyyMM");
			String dcDateEnd = DateUtils.formatDate(DateUtils.parseDate(dcPrjDateEnd), "yyyyMM");
			
			JSONObject jsonreq = new JSONObject();
			jsonreq.put("calcYearMonth", dcDateBegin + "-" + dcDateEnd);//201701-201702
			jsonreq.put("projectCode", StringUtils.trimToEmpty(dcPrjId));
			jsonreq.put("prjScope", "A0");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月
			jsonreq.put("insertDB", planResult); //A0:结果入库/A1:结果不入库
			jsonreq.put("userId", UserUtils.getUser().getId());
			JSONObject jsonres = RequestService.requestService(RequestService.srvCalcInnerCostCondition, "", jsonreq.toJSONString(), "POST");
			
			addMessage(redirectAttributes, (String)jsonres.get("msg"));
		}

		//按照收款时间查询项目范围
		if(!StringUtils.isEmpty(planPayDateBegin) && !StringUtils.isEmpty(planPayDateEnd)){
			String planDateBegin = DateUtils.formatDate(DateUtils.parseDate(planPayDateBegin), "yyyyMM");
			String planDateEnd = DateUtils.formatDate(DateUtils.parseDate(planPayDateEnd), "yyyyMM");
			
			JSONObject jsonreq = new JSONObject();
			jsonreq.put("calcYearMonth", planDateBegin + "-" + planDateEnd);//201701-201702
			jsonreq.put("projectCode", StringUtils.trimToEmpty(dcPrjId));
			jsonreq.put("prjScope", "A1");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月
			jsonreq.put("insertDB", planResult); //A0:结果入库/A1:结果不入库
			jsonreq.put("userId", UserUtils.getUser().getId());
			JSONObject jsonres = RequestService.requestService(RequestService.srvCalcInnerCostCondition, "", jsonreq.toJSONString(), "POST");
			
			addMessage(redirectAttributes, (String)jsonres.get("msg"));
			
			//收款期到期且尚有未到款
			JSONObject jsonreqBefore = new JSONObject();
			jsonreqBefore.put("calcYearMonth", planDateBegin + "-" + planDateEnd);//201701-201702
			jsonreqBefore.put("projectCode", StringUtils.trimToEmpty(dcPrjId));
			jsonreqBefore.put("prjScope", "A2");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月/A2:收款期到期且尚有未到款
			jsonreqBefore.put("insertDB", planResult); //A0:结果入库/A1:结果不入库
			jsonreq.put("userId", UserUtils.getUser().getId());
			JSONObject jsonresBefore = RequestService.requestService(RequestService.srvCalcInnerCostCondition, "", jsonreqBefore.toJSONString(), "POST");

			addMessage(redirectAttributes, (String)jsonresBefore.get("msg"));
		}

		return "modules/fc/fcPlanInnerFeeCount";
	}
}