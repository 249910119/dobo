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
import com.dobo.modules.cop.entity.detail.CopSalesOrgProject;
import com.dobo.modules.cop.service.detail.CopSalesOrgProjectService;

/**
 * 限额项目Controller
 * @author admin
 * @version 2018-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/detail/copSalesOrgProject")
public class CopSalesOrgProjectController extends BaseController {

	@Autowired
	private CopSalesOrgProjectService copSalesOrgProjectService;
	
	@ModelAttribute
	public CopSalesOrgProject get(@RequestParam(required=false) String id) {
		CopSalesOrgProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copSalesOrgProjectService.get(id);
		}
		if (entity == null){
			entity = new CopSalesOrgProject();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:detail:copSalesOrgProject:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopSalesOrgProject copSalesOrgProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopSalesOrgProject> page = copSalesOrgProjectService.findPage(new Page<CopSalesOrgProject>(request, response), copSalesOrgProject); 
		model.addAttribute("page", page);
		return "modules/cop/detail/copSalesOrgProjectList";
	}

	@RequiresPermissions("cop:detail:copSalesOrgProject:view")
	@RequestMapping(value = "form")
	public String form(CopSalesOrgProject copSalesOrgProject, Model model) {
		model.addAttribute("copSalesOrgProject", copSalesOrgProject);
		return "modules/cop/detail/copSalesOrgProjectForm";
	}

	@RequiresPermissions("cop:detail:copSalesOrgProject:edit")
	@RequestMapping(value = "save")
	public String save(CopSalesOrgProject copSalesOrgProject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copSalesOrgProject)){
			return form(copSalesOrgProject, model);
		}
		copSalesOrgProjectService.save(copSalesOrgProject);
		addMessage(redirectAttributes, "保存限额项目成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copSalesOrgProject/?repage";
	}
	
	@RequiresPermissions("cop:detail:copSalesOrgProject:edit")
	@RequestMapping(value = "delete")
	public String delete(CopSalesOrgProject copSalesOrgProject, RedirectAttributes redirectAttributes) {
		copSalesOrgProjectService.delete(copSalesOrgProject);
		addMessage(redirectAttributes, "删除限额项目成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copSalesOrgProject/?repage";
	}

}