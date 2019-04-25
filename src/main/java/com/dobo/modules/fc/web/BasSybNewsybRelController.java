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
import com.dobo.modules.fc.entity.BasSybNewsybRel;
import com.dobo.modules.fc.service.BasSybNewsybRelService;

/**
 * 事业部对应新事业部名称Controller
 * @author admin
 * @version 2017-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/basSybNewsybRel")
public class BasSybNewsybRelController extends BaseController {

	@Autowired
	private BasSybNewsybRelService basSybNewsybRelService;
	
	@ModelAttribute
	public BasSybNewsybRel get(@RequestParam(required=false) String id) {
		BasSybNewsybRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basSybNewsybRelService.get(id);
		}
		if (entity == null){
			entity = new BasSybNewsybRel();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:basSybNewsybRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasSybNewsybRel basSybNewsybRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasSybNewsybRel> page = basSybNewsybRelService.findPage(new Page<BasSybNewsybRel>(request, response), basSybNewsybRel); 
		model.addAttribute("page", page);
		return "modules/fc/basSybNewsybRelList";
	}

	@RequiresPermissions("fc:basSybNewsybRel:view")
	@RequestMapping(value = "form")
	public String form(BasSybNewsybRel basSybNewsybRel, Model model) {
		model.addAttribute("basSybNewsybRel", basSybNewsybRel);
		return "modules/fc/basSybNewsybRelForm";
	}

	@RequiresPermissions("fc:basSybNewsybRel:edit")
	@RequestMapping(value = "save")
	public String save(BasSybNewsybRel basSybNewsybRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basSybNewsybRel)){
			return form(basSybNewsybRel, model);
		}
		basSybNewsybRelService.save(basSybNewsybRel);
		addMessage(redirectAttributes, "保存事业部对应新事业部名称成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybNewsybRel/?repage";
	}
	
	@RequiresPermissions("fc:basSybNewsybRel:edit")
	@RequestMapping(value = "delete")
	public String delete(BasSybNewsybRel basSybNewsybRel, RedirectAttributes redirectAttributes) {
		basSybNewsybRelService.delete(basSybNewsybRel);
		addMessage(redirectAttributes, "删除事业部对应新事业部名称成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybNewsybRel/?repage";
	}

}