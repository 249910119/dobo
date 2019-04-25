/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.web;

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

import com.dobo.common.config.Global;
import com.dobo.common.persistence.Page;
import com.dobo.common.web.BaseController;
import com.dobo.common.utils.SpringContextHolder;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.fc.dao.FcPlanInnerFeeDao;
import com.dobo.modules.fc.entity.FcOrderInfo;
import com.dobo.modules.fc.service.FcOrderInfoService;

/**
 * 财务费用计算对应订单信息Controller
 * @author admin
 * @version 2016-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcOrderInfo")
public class FcOrderInfoController extends BaseController {

	@Autowired
	private FcOrderInfoService fcOrderInfoService;
	private static FcPlanInnerFeeDao fcPlanInnerFeeDao = SpringContextHolder.getBean(FcPlanInnerFeeDao.class);
	
	@ModelAttribute
	public FcOrderInfo get(@RequestParam(required=false) String id) {
		FcOrderInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcOrderInfoService.get(id);
		}
		if (entity == null){
			entity = new FcOrderInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcOrderInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcOrderInfo fcOrderInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcOrderInfo> page = fcOrderInfoService.findPage(new Page<FcOrderInfo>(request, response), fcOrderInfo); 
		model.addAttribute("page", page);
		return "modules/fc/fcOrderInfoList";
	}

	@RequiresPermissions("fc:fcOrderInfo:view")
	@RequestMapping(value = "form")
	public String form(FcOrderInfo fcOrderInfo, Model model) {
		model.addAttribute("fcOrderInfo", fcOrderInfo);
		return "modules/fc/fcOrderInfoForm";
	}

	@RequiresPermissions("fc:fcOrderInfo:edit")
	@RequestMapping(value = "save")
	public String save(FcOrderInfo fcOrderInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcOrderInfo)){
			return form(fcOrderInfo, model);
		}
		fcOrderInfoService.save(fcOrderInfo);
		addMessage(redirectAttributes, "保存订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcOrderInfo/?repage";
	}
	
	@RequiresPermissions("fc:fcOrderInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(FcOrderInfo fcOrderInfo, RedirectAttributes redirectAttributes) {
		fcOrderInfoService.delete(fcOrderInfo);
		addMessage(redirectAttributes, "删除订单信息成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcOrderInfo/?repage";
	}

}