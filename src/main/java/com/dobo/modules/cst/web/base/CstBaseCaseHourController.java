/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.base;

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
import com.dobo.modules.cst.entity.base.CstBaseCaseHour;
import com.dobo.modules.cst.service.base.CstBaseCaseHourService;

/**
 * 故障率工时参数Controller
 * @author admin
 * @version 2019-03-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstBaseCaseHour")
public class CstBaseCaseHourController extends BaseController {

	@Autowired
	private CstBaseCaseHourService cstBaseCaseHourService;
	
	@ModelAttribute
	public CstBaseCaseHour get(@RequestParam(required=false) String id) {
		CstBaseCaseHour entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstBaseCaseHourService.get(id);
		}
		if (entity == null){
			entity = new CstBaseCaseHour();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstBaseCaseHour:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstBaseCaseHour cstBaseCaseHour, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstBaseCaseHour> page = cstBaseCaseHourService.findPage(new Page<CstBaseCaseHour>(request, response), cstBaseCaseHour); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstBaseCaseHourList";
	}

	@RequiresPermissions("cst:base:cstBaseCaseHour:view")
	@RequestMapping(value = "form")
	public String form(CstBaseCaseHour cstBaseCaseHour, Model model) {
		model.addAttribute("cstBaseCaseHour", cstBaseCaseHour);
		return "modules/cst/base/cstBaseCaseHourForm";
	}

	@RequiresPermissions("cst:base:cstBaseCaseHour:edit")
	@RequestMapping(value = "save")
	public String save(CstBaseCaseHour cstBaseCaseHour, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstBaseCaseHour)){
			return form(cstBaseCaseHour, model);
		}
		cstBaseCaseHourService.save(cstBaseCaseHour);
		addMessage(redirectAttributes, "保存故障率工时参数成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseCaseHour/?repage";
	}
	
	@RequiresPermissions("cst:base:cstBaseCaseHour:edit")
	@RequestMapping(value = "delete")
	public String delete(CstBaseCaseHour cstBaseCaseHour, RedirectAttributes redirectAttributes) {
		cstBaseCaseHourService.delete(cstBaseCaseHour);
		addMessage(redirectAttributes, "删除故障率工时参数成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseCaseHour/?repage";
	}

}