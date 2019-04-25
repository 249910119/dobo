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
import com.dobo.modules.cst.entity.base.CstBaseBackCasePara;
import com.dobo.modules.cst.service.base.CstBaseBackCaseParaService;

/**
 * 备件故障事件级别占比参数获取Controller
 * @author admin
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstBaseBackCasePara")
public class CstBaseBackCaseParaController extends BaseController {

	@Autowired
	private CstBaseBackCaseParaService cstBaseBackCaseParaService;
	
	@ModelAttribute
	public CstBaseBackCasePara get(@RequestParam(required=false) String id) {
		CstBaseBackCasePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstBaseBackCaseParaService.get(id);
		}
		if (entity == null){
			entity = new CstBaseBackCasePara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstBaseBackCasePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstBaseBackCasePara cstBaseBackCasePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstBaseBackCasePara> page = cstBaseBackCaseParaService.findPage(new Page<CstBaseBackCasePara>(request, response), cstBaseBackCasePara); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstBaseBackCaseParaList";
	}

	@RequiresPermissions("cst:base:cstBaseBackCasePara:view")
	@RequestMapping(value = "form")
	public String form(CstBaseBackCasePara cstBaseBackCasePara, Model model) {
		model.addAttribute("cstBaseBackCasePara", cstBaseBackCasePara);
		return "modules/cst/base/cstBaseBackCaseParaForm";
	}

	@RequiresPermissions("cst:base:cstBaseBackCasePara:edit")
	@RequestMapping(value = "save")
	public String save(CstBaseBackCasePara cstBaseBackCasePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstBaseBackCasePara)){
			return form(cstBaseBackCasePara, model);
		}
		cstBaseBackCaseParaService.save(cstBaseBackCasePara);
		addMessage(redirectAttributes, "保存备件故障事件级别占比参数获取成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseBackCasePara/?repage";
	}
	
	@RequiresPermissions("cst:base:cstBaseBackCasePara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstBaseBackCasePara cstBaseBackCasePara, RedirectAttributes redirectAttributes) {
		cstBaseBackCaseParaService.delete(cstBaseBackCasePara);
		addMessage(redirectAttributes, "删除备件故障事件级别占比参数获取成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseBackCasePara/?repage";
	}

}