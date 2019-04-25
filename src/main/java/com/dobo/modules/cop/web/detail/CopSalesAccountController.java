/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.web.detail;

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
import com.dobo.modules.cop.entity.detail.CopSalesAccount;
import com.dobo.modules.cop.service.detail.CopSalesAccountService;

/**
 * 销售员账户表Controller
 * @author admin
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/detail/copSalesAccount")
public class CopSalesAccountController extends BaseController {

	@Autowired
	private CopSalesAccountService copSalesAccountService;
	
	@ModelAttribute
	public CopSalesAccount get(@RequestParam(required=false) String id) {
		CopSalesAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copSalesAccountService.get(id);
		}
		if (entity == null){
			entity = new CopSalesAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:detail:copSalesAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopSalesAccount copSalesAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopSalesAccount> page = copSalesAccountService.findPage(new Page<CopSalesAccount>(request, response), copSalesAccount); 
		model.addAttribute("page", page);
		return "modules/cop/detail/copSalesAccountList";
	}

	@RequiresPermissions("cop:detail:copSalesAccount:view")
	@RequestMapping(value = "form")
	public String form(CopSalesAccount copSalesAccount, Model model) {
		model.addAttribute("copSalesAccount", copSalesAccount);
		return "modules/cop/detail/copSalesAccountForm";
	}

	@RequiresPermissions("cop:detail:copSalesAccount:edit")
	@RequestMapping(value = "save")
	public String save(CopSalesAccount copSalesAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copSalesAccount)){
			return form(copSalesAccount, model);
		}
		copSalesAccountService.save(copSalesAccount);
		addMessage(redirectAttributes, "保存销售员账户表成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copSalesAccount/?repage";
	}
	
	@RequiresPermissions("cop:detail:copSalesAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(CopSalesAccount copSalesAccount, RedirectAttributes redirectAttributes) {
		copSalesAccountService.delete(copSalesAccount);
		addMessage(redirectAttributes, "删除销售员账户表成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copSalesAccount/?repage";
	}

}