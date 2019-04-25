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
import com.dobo.modules.fc.entity.BasSybXmbhRel;
import com.dobo.modules.fc.service.BasSybXmbhRelService;

/**
 * 财务项目编号对应事业部名称Controller
 * @author admin
 * @version 2017-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/basSybXmbhRel")
public class BasSybXmbhRelController extends BaseController {

	@Autowired
	private BasSybXmbhRelService basSybXmbhRelService;
	
	@ModelAttribute
	public BasSybXmbhRel get(@RequestParam(required=false) String id) {
		BasSybXmbhRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basSybXmbhRelService.get(id);
		}
		if (entity == null){
			entity = new BasSybXmbhRel();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:basSybXmbhRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasSybXmbhRel basSybXmbhRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasSybXmbhRel> page = basSybXmbhRelService.findPage(new Page<BasSybXmbhRel>(request, response), basSybXmbhRel); 
		model.addAttribute("page", page);
		return "modules/fc/basSybXmbhRelList";
	}

	@RequiresPermissions("fc:basSybXmbhRel:view")
	@RequestMapping(value = "form")
	public String form(BasSybXmbhRel basSybXmbhRel, Model model) {
		model.addAttribute("basSybXmbhRel", basSybXmbhRel);
		return "modules/fc/basSybXmbhRelForm";
	}

	@RequiresPermissions("fc:basSybXmbhRel:edit")
	@RequestMapping(value = "save")
	public String save(BasSybXmbhRel basSybXmbhRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basSybXmbhRel)){
			return form(basSybXmbhRel, model);
		}
		basSybXmbhRelService.save(basSybXmbhRel);
		addMessage(redirectAttributes, "保存财务项目编号对应事业部名称成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybXmbhRel/?repage";
	}
	
	@RequiresPermissions("fc:basSybXmbhRel:edit")
	@RequestMapping(value = "delete")
	public String delete(BasSybXmbhRel basSybXmbhRel, RedirectAttributes redirectAttributes) {
		basSybXmbhRelService.delete(basSybXmbhRel);
		addMessage(redirectAttributes, "删除财务项目编号对应事业部名称成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybXmbhRel/?repage";
	}

}