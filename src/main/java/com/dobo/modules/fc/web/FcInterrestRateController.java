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
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.fc.entity.FcInterrestRate;
import com.dobo.modules.fc.service.FcInterrestRateService;

/**
 * 存息贷息利率定义Controller
 * @author admin
 * @version 2016-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcInterrestRate")
public class FcInterrestRateController extends BaseController {

	@Autowired
	private FcInterrestRateService fcInterrestRateService;
	
	@ModelAttribute
	public FcInterrestRate get(@RequestParam(required=false) String id) {
		FcInterrestRate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcInterrestRateService.get(id);
		}
		if (entity == null){
			entity = new FcInterrestRate();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcInterrestRate:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcInterrestRate fcInterrestRate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcInterrestRate> page = fcInterrestRateService.findPage(new Page<FcInterrestRate>(request, response), fcInterrestRate); 
		model.addAttribute("page", page);
		return "modules/fc/fcInterrestRateList";
	}

	@RequiresPermissions("fc:fcInterrestRate:view")
	@RequestMapping(value = "form")
	public String form(FcInterrestRate fcInterrestRate, Model model) {
		model.addAttribute("fcInterrestRate", fcInterrestRate);
		return "modules/fc/fcInterrestRateForm";
	}

	@RequiresPermissions("fc:fcInterrestRate:edit")
	@RequestMapping(value = "save")
	public String save(FcInterrestRate fcInterrestRate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcInterrestRate)){
			return form(fcInterrestRate, model);
		}
		fcInterrestRateService.save(fcInterrestRate);
		addMessage(redirectAttributes, "保存利率定义成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcInterrestRate/?repage";
	}
	
	@RequiresPermissions("fc:fcInterrestRate:edit")
	@RequestMapping(value = "delete")
	public String delete(FcInterrestRate fcInterrestRate, RedirectAttributes redirectAttributes) {
		fcInterrestRateService.delete(fcInterrestRate);
		addMessage(redirectAttributes, "删除利率定义成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcInterrestRate/?repage";
	}

}