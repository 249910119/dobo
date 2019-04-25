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
import com.dobo.modules.fc.entity.BasSybCbzxRel;
import com.dobo.modules.fc.service.BasSybCbzxRelService;

/**
 * 事业部包含的成本中心代码Controller
 * @author admin
 * @version 2017-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/basSybCbzxRel")
public class BasSybCbzxRelController extends BaseController {

	@Autowired
	private BasSybCbzxRelService basSybCbzxRelService;
	
	@ModelAttribute
	public BasSybCbzxRel get(@RequestParam(required=false) String id) {
		BasSybCbzxRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basSybCbzxRelService.get(id);
		}
		if (entity == null){
			entity = new BasSybCbzxRel();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:basSybCbzxRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasSybCbzxRel basSybCbzxRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasSybCbzxRel> page = basSybCbzxRelService.findPage(new Page<BasSybCbzxRel>(request, response), basSybCbzxRel); 
		model.addAttribute("page", page);
		return "modules/fc/basSybCbzxRelList";
	}

	@RequiresPermissions("fc:basSybCbzxRel:view")
	@RequestMapping(value = "form")
	public String form(BasSybCbzxRel basSybCbzxRel, Model model) {
		model.addAttribute("basSybCbzxRel", basSybCbzxRel);
		return "modules/fc/basSybCbzxRelForm";
	}

	@RequiresPermissions("fc:basSybCbzxRel:edit")
	@RequestMapping(value = "save")
	public String save(BasSybCbzxRel basSybCbzxRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basSybCbzxRel)){
			return form(basSybCbzxRel, model);
		}
		basSybCbzxRelService.save(basSybCbzxRel);
		addMessage(redirectAttributes, "保存事业部包含的成本中心代码成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybCbzxRel/?repage";
	}
	
	@RequiresPermissions("fc:basSybCbzxRel:edit")
	@RequestMapping(value = "delete")
	public String delete(BasSybCbzxRel basSybCbzxRel, RedirectAttributes redirectAttributes) {
		basSybCbzxRelService.delete(basSybCbzxRel);
		addMessage(redirectAttributes, "删除事业部包含的成本中心代码成功");
		return "redirect:"+Global.getAdminPath()+"/fc/basSybCbzxRel/?repage";
	}

}