/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.soft;

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
import com.dobo.modules.cst.entity.soft.CstSoftPackStepRule;
import com.dobo.modules.cst.service.soft.CstSoftPackStepRuleService;

/**
 * 系统软件服务规模阶梯配比表Controller
 * @author admin
 * @version 2017-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/soft/cstSoftPackStepRule")
public class CstSoftPackStepRuleController extends BaseController {

	@Autowired
	private CstSoftPackStepRuleService cstSoftPackStepRuleService;
	
	@ModelAttribute
	public CstSoftPackStepRule get(@RequestParam(required=false) String id) {
		CstSoftPackStepRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstSoftPackStepRuleService.get(id);
		}
		if (entity == null){
			entity = new CstSoftPackStepRule();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:soft:cstSoftPackStepRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstSoftPackStepRule cstSoftPackStepRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstSoftPackStepRule> page = cstSoftPackStepRuleService.findPage(new Page<CstSoftPackStepRule>(request, response), cstSoftPackStepRule); 
		model.addAttribute("page", page);
		return "modules/cst/soft/cstSoftPackStepRuleList";
	}

	@RequiresPermissions("cst:soft:cstSoftPackStepRule:view")
	@RequestMapping(value = "form")
	public String form(CstSoftPackStepRule cstSoftPackStepRule, Model model) {
		model.addAttribute("cstSoftPackStepRule", cstSoftPackStepRule);
		return "modules/cst/soft/cstSoftPackStepRuleForm";
	}

	@RequiresPermissions("cst:soft:cstSoftPackStepRule:edit")
	@RequestMapping(value = "save")
	public String save(CstSoftPackStepRule cstSoftPackStepRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstSoftPackStepRule)){
			return form(cstSoftPackStepRule, model);
		}
		cstSoftPackStepRuleService.save(cstSoftPackStepRule);
		addMessage(redirectAttributes, "保存系统软件服务规模阶梯配比表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/soft/cstSoftPackStepRule/?repage";
	}
	
	@RequiresPermissions("cst:soft:cstSoftPackStepRule:edit")
	@RequestMapping(value = "delete")
	public String delete(CstSoftPackStepRule cstSoftPackStepRule, RedirectAttributes redirectAttributes) {
		cstSoftPackStepRuleService.delete(cstSoftPackStepRule);
		addMessage(redirectAttributes, "删除系统软件服务规模阶梯配比表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/soft/cstSoftPackStepRule/?repage";
	}

}