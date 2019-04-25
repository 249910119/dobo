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
import com.dobo.modules.fc.entity.BasSybBuRel;
import com.dobo.modules.fc.service.BasSybBuRelService;

/**
 * 事业部对应BU名称Controller
 * @author admin
 * @version 2017-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/basSybBuRel")
public class BasSybBuRelController extends BaseController {

	@Autowired
	private BasSybBuRelService basSybBuRelService;
	
	@ModelAttribute
	public BasSybBuRel get(@RequestParam(required=false) String id) {
		BasSybBuRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basSybBuRelService.get(id);
		}
		if (entity == null){
			entity = new BasSybBuRel();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:basSybBuRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasSybBuRel basSybBuRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasSybBuRel> page = basSybBuRelService.findPage(new Page<BasSybBuRel>(request, response), basSybBuRel); 
		model.addAttribute("page", page);
		return "modules/fc/basSybBuRelList";
	}

	@RequiresPermissions("fc:basSybBuRel:view")
	@RequestMapping(value = "form")
	public String form(BasSybBuRel basSybBuRel, Model model) {
		model.addAttribute("basSybBuRel", basSybBuRel);
		return "modules/fc/basSybBuRelForm";
	}

	@RequiresPermissions("fc:basSybBuRel:edit")
	@RequestMapping(value = "save")
	public String save(BasSybBuRel basSybBuRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basSybBuRel)){
			return form(basSybBuRel, model);
		}
		basSybBuRelService.save(basSybBuRel);
		addMessage(redirectAttributes, "保存事业部对应BU名称成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybBuRel/?repage";
	}
	
	@RequiresPermissions("fc:basSybBuRel:edit")
	@RequestMapping(value = "delete")
	public String delete(BasSybBuRel basSybBuRel, RedirectAttributes redirectAttributes) {
		basSybBuRelService.delete(basSybBuRel);
		addMessage(redirectAttributes, "删除事业部对应BU名称成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybBuRel/?repage";
	}

}