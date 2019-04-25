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
import com.dobo.modules.cp.entity.result.CpStaffInfo;
import com.dobo.modules.cp.service.result.CpStaffInfoService;

/**
 * 系统人员Controller
 * @author admin
 * @version 2018-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/cp/result/cpStaffInfo")
public class CpStaffInfoController extends BaseController {

	@Autowired
	private CpStaffInfoService cpStaffInfoService;
	
	@ModelAttribute
	public CpStaffInfo get(@RequestParam(required=false) String id) {
		CpStaffInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cpStaffInfoService.get(id);
		}
		if (entity == null){
			entity = new CpStaffInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cp:result:cpStaffInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CpStaffInfo cpStaffInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CpStaffInfo> page = cpStaffInfoService.findPage(new Page<CpStaffInfo>(request, response), cpStaffInfo); 
		model.addAttribute("page", page);
		return "modules/cp/result/cpStaffInfoList";
	}

	@RequiresPermissions("cp:result:cpStaffInfo:view")
	@RequestMapping(value = "form")
	public String form(CpStaffInfo cpStaffInfo, Model model) {
		model.addAttribute("cpStaffInfo", cpStaffInfo);
		return "modules/cp/result/cpStaffInfoForm";
	}

	@RequiresPermissions("cp:result:cpStaffInfo:edit")
	@RequestMapping(value = "save")
	public String save(CpStaffInfo cpStaffInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cpStaffInfo)){
			return form(cpStaffInfo, model);
		}
		cpStaffInfoService.save(cpStaffInfo);
		addMessage(redirectAttributes, "保存系统人员成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffInfo/?repage";
	}
	
	@RequiresPermissions("cp:result:cpStaffInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CpStaffInfo cpStaffInfo, RedirectAttributes redirectAttributes) {
		cpStaffInfoService.delete(cpStaffInfo);
		addMessage(redirectAttributes, "删除系统人员成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffInfo/?repage";
	}

}