/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.web.result;

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
import com.dobo.modules.cp.entity.result.CpStaffAssessType;
import com.dobo.modules.cp.service.result.CpStaffAssessTypeService;

/**
 * 系统人员测评行为、能力和模块划分Controller
 * @author admin
 * @version 2018-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cp/result/cpStaffAssessType")
public class CpStaffAssessTypeController extends BaseController {

	@Autowired
	private CpStaffAssessTypeService cpStaffAssessTypeService;
	
	@ModelAttribute
	public CpStaffAssessType get(@RequestParam(required=false) String id) {
		CpStaffAssessType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cpStaffAssessTypeService.get(id);
		}
		if (entity == null){
			entity = new CpStaffAssessType();
		}
		return entity;
	}
	
	@RequiresPermissions("cp:result:cpStaffAssessType:view")
	@RequestMapping(value = {"list", ""})
	public String list(CpStaffAssessType cpStaffAssessType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CpStaffAssessType> page = cpStaffAssessTypeService.findPage(new Page<CpStaffAssessType>(request, response), cpStaffAssessType); 
		model.addAttribute("page", page);
		return "modules/cp/result/cpStaffAssessTypeList";
	}

	@RequiresPermissions("cp:result:cpStaffAssessType:view")
	@RequestMapping(value = "form")
	public String form(CpStaffAssessType cpStaffAssessType, Model model) {
		model.addAttribute("cpStaffAssessType", cpStaffAssessType);
		return "modules/cp/result/cpStaffAssessTypeForm";
	}

	@RequiresPermissions("cp:result:cpStaffAssessType:edit")
	@RequestMapping(value = "save")
	public String save(CpStaffAssessType cpStaffAssessType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cpStaffAssessType)){
			return form(cpStaffAssessType, model);
		}
		cpStaffAssessTypeService.save(cpStaffAssessType);
		addMessage(redirectAttributes, "保存系统人员测评行为、能力和模块划分成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffAssessType/?repage";
	}
	
	@RequiresPermissions("cp:result:cpStaffAssessType:edit")
	@RequestMapping(value = "delete")
	public String delete(CpStaffAssessType cpStaffAssessType, RedirectAttributes redirectAttributes) {
		cpStaffAssessTypeService.delete(cpStaffAssessType);
		addMessage(redirectAttributes, "删除系统人员测评行为、能力和模块划分成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffAssessType/?repage";
	}

}