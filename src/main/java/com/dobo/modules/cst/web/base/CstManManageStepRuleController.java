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
import com.dobo.modules.cst.entity.base.CstManManageStepRule;
import com.dobo.modules.cst.service.base.CstManManageStepRuleService;

/**
 * 项目管理工作量阶梯配比Controller
 * @author admin
 * @version 2016-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstManManageStepRule")
public class CstManManageStepRuleController extends BaseController {

	@Autowired
	private CstManManageStepRuleService cstManManageStepRuleService;
	
	@ModelAttribute
	public CstManManageStepRule get(@RequestParam(required=false) String id) {
		CstManManageStepRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManManageStepRuleService.get(id);
		}
		if (entity == null){
			entity = new CstManManageStepRule();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstManManageStepRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManManageStepRule cstManManageStepRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManManageStepRule> page = cstManManageStepRuleService.findPage(new Page<CstManManageStepRule>(request, response), cstManManageStepRule); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstManManageStepRuleList";
	}

	@RequiresPermissions("cst:base:cstManManageStepRule:view")
	@RequestMapping(value = "form")
	public String form(CstManManageStepRule cstManManageStepRule, Model model) {
		model.addAttribute("cstManManageStepRule", cstManManageStepRule);
		return "modules/cst/base/cstManManageStepRuleForm";
	}

	@RequiresPermissions("cst:base:cstManManageStepRule:edit")
	@RequestMapping(value = "save")
	public String save(CstManManageStepRule cstManManageStepRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManManageStepRule)){
			return form(cstManManageStepRule, model);
		}
		cstManManageStepRuleService.save(cstManManageStepRule);
		addMessage(redirectAttributes, "保存项目管理工作量阶梯配比成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstManManageStepRule/?repage";
	}
	
	@RequiresPermissions("cst:base:cstManManageStepRule:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManManageStepRule cstManManageStepRule, RedirectAttributes redirectAttributes) {
		cstManManageStepRuleService.delete(cstManManageStepRule);
		addMessage(redirectAttributes, "删除项目管理工作量阶梯配比成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstManManageStepRule/?repage";
	}

}