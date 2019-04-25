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
import com.dobo.modules.fc.entity.FcProjectInfoNocalc;
import com.dobo.modules.fc.service.FcProjectInfoNocalcService;

/**
 * 无需计算计划内财务费用项目Controller
 * @author admin
 * @version 2017-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcProjectInfoNocalc")
public class FcProjectInfoNocalcController extends BaseController {

	@Autowired
	private FcProjectInfoNocalcService fcProjectInfoNocalcService;
	
	@ModelAttribute
	public FcProjectInfoNocalc get(@RequestParam(required=false) String id) {
		FcProjectInfoNocalc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcProjectInfoNocalcService.get(id);
		}
		if (entity == null){
			entity = new FcProjectInfoNocalc();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcProjectInfoNocalc:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcProjectInfoNocalc fcProjectInfoNocalc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcProjectInfoNocalc> page = fcProjectInfoNocalcService.findPage(new Page<FcProjectInfoNocalc>(request, response), fcProjectInfoNocalc); 
		model.addAttribute("page", page);
		return "modules/fc/fcProjectInfoNocalcList";
	}

	@RequiresPermissions("fc:fcProjectInfoNocalc:view")
	@RequestMapping(value = "form")
	public String form(FcProjectInfoNocalc fcProjectInfoNocalc, Model model) {
		model.addAttribute("fcProjectInfoNocalc", fcProjectInfoNocalc);
		return "modules/fc/fcProjectInfoNocalcForm";
	}

	@RequiresPermissions("fc:fcProjectInfoNocalc:edit")
	@RequestMapping(value = "save")
	public String save(FcProjectInfoNocalc fcProjectInfoNocalc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcProjectInfoNocalc)){
			return form(fcProjectInfoNocalc, model);
		}
		fcProjectInfoNocalcService.save(fcProjectInfoNocalc);
		addMessage(redirectAttributes, "保存无需计算计划内财务费用项目成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcProjectInfoNocalc/?repage";
	}
	
	@RequiresPermissions("fc:fcProjectInfoNocalc:edit")
	@RequestMapping(value = "delete")
	public String delete(FcProjectInfoNocalc fcProjectInfoNocalc, RedirectAttributes redirectAttributes) {
		fcProjectInfoNocalcService.delete(fcProjectInfoNocalc);
		addMessage(redirectAttributes, "删除无需计算计划内财务费用项目成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcProjectInfoNocalc/?repage";
	}

}