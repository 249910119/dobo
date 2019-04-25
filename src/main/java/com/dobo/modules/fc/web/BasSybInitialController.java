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
import com.dobo.modules.fc.entity.BasSybInitial;
import com.dobo.modules.fc.service.BasSybInitialService;

/**
 * 事业部初始现金流Controller
 * @author admin
 * @version 2017-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/basSybInitial")
public class BasSybInitialController extends BaseController {

	@Autowired
	private BasSybInitialService basSybInitialService;
	
	@ModelAttribute
	public BasSybInitial get(@RequestParam(required=false) String id) {
		BasSybInitial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basSybInitialService.get(id);
		}
		if (entity == null){
			entity = new BasSybInitial();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:basSybInitial:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasSybInitial basSybInitial, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasSybInitial> page = basSybInitialService.findPage(new Page<BasSybInitial>(request, response), basSybInitial); 
		model.addAttribute("page", page);
		return "modules/fc/basSybInitialList";
	}

	@RequiresPermissions("fc:basSybInitial:view")
	@RequestMapping(value = "form")
	public String form(BasSybInitial basSybInitial, Model model) {
		model.addAttribute("basSybInitial", basSybInitial);
		return "modules/fc/basSybInitialForm";
	}

	@RequiresPermissions("fc:basSybInitial:edit")
	@RequestMapping(value = "save")
	public String save(BasSybInitial basSybInitial, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basSybInitial)){
			return form(basSybInitial, model);
		}
		basSybInitialService.save(basSybInitial);
		addMessage(redirectAttributes, "保存事业部初始现金流成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybInitial/?repage";
	}
	
	@RequiresPermissions("fc:basSybInitial:edit")
	@RequestMapping(value = "delete")
	public String delete(BasSybInitial basSybInitial, RedirectAttributes redirectAttributes) {
		basSybInitialService.delete(basSybInitial);
		addMessage(redirectAttributes, "删除事业部初始现金流成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybInitial/?repage";
	}

}