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
import com.dobo.common.utils.StringUtils;
import com.dobo.common.web.BaseController;
import com.dobo.modules.cop.entity.detail.CopSalesOrgAccount;
import com.dobo.modules.cop.service.detail.CopSalesOrgAccountService;

/**
 * 单次服务成本事业部阀值Controller
 * @author admin
 * @version 2018-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/tetail/copSalesOrgAccount")
public class CopSalesOrgAccountController extends BaseController {

	@Autowired
	private CopSalesOrgAccountService copSalesOrgAccountService;
	
	@ModelAttribute
	public CopSalesOrgAccount get(@RequestParam(required=false) String id) {
		CopSalesOrgAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copSalesOrgAccountService.get(id);
		}
		if (entity == null){
			entity = new CopSalesOrgAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:tetail:copSalesOrgAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopSalesOrgAccount copSalesOrgAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopSalesOrgAccount> page = copSalesOrgAccountService.findPage(new Page<CopSalesOrgAccount>(request, response), copSalesOrgAccount); 
		model.addAttribute("page", page);
		return "modules/cop/tetail/copSalesOrgAccountList";
	}

	@RequiresPermissions("cop:tetail:copSalesOrgAccount:view")
	@RequestMapping(value = "form")
	public String form(CopSalesOrgAccount copSalesOrgAccount, Model model) {
		model.addAttribute("copSalesOrgAccount", copSalesOrgAccount);
		return "modules/cop/tetail/copSalesOrgAccountForm";
	}

	@RequiresPermissions("cop:tetail:copSalesOrgAccount:edit")
	@RequestMapping(value = "save")
	public String save(CopSalesOrgAccount copSalesOrgAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copSalesOrgAccount)){
			return form(copSalesOrgAccount, model);
		}
		copSalesOrgAccountService.save(copSalesOrgAccount);
		addMessage(redirectAttributes, "保存单次服务成本事业部阀值成功");
		return "redirect:"+Global.getAdminPath()+"/cop/tetail/copSalesOrgAccount/?repage";
	}
	
	@RequiresPermissions("cop:tetail:copSalesOrgAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(CopSalesOrgAccount copSalesOrgAccount, RedirectAttributes redirectAttributes) {
		copSalesOrgAccountService.delete(copSalesOrgAccount);
		addMessage(redirectAttributes, "删除单次服务成本事业部阀值成功");
		return "redirect:"+Global.getAdminPath()+"/cop/tetail/copSalesOrgAccount/?repage";
	}

}