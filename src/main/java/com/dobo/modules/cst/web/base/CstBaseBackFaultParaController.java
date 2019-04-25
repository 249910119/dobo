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
import com.dobo.modules.cst.entity.base.CstBaseBackFaultPara;
import com.dobo.modules.cst.service.base.CstBaseBackFaultParaService;

/**
 * 备件故障率参数获取Controller
 * @author admin
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstBaseBackFaultPara")
public class CstBaseBackFaultParaController extends BaseController {

	@Autowired
	private CstBaseBackFaultParaService cstBaseBackFaultParaService;
	
	@ModelAttribute
	public CstBaseBackFaultPara get(@RequestParam(required=false) String id) {
		CstBaseBackFaultPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstBaseBackFaultParaService.get(id);
		}
		if (entity == null){
			entity = new CstBaseBackFaultPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstBaseBackFaultPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstBaseBackFaultPara cstBaseBackFaultPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstBaseBackFaultPara> page = cstBaseBackFaultParaService.findPage(new Page<CstBaseBackFaultPara>(request, response), cstBaseBackFaultPara); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstBaseBackFaultParaList";
	}

	@RequiresPermissions("cst:base:cstBaseBackFaultPara:view")
	@RequestMapping(value = "form")
	public String form(CstBaseBackFaultPara cstBaseBackFaultPara, Model model) {
		model.addAttribute("cstBaseBackFaultPara", cstBaseBackFaultPara);
		return "modules/cst/base/cstBaseBackFaultParaForm";
	}

	@RequiresPermissions("cst:base:cstBaseBackFaultPara:edit")
	@RequestMapping(value = "save")
	public String save(CstBaseBackFaultPara cstBaseBackFaultPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstBaseBackFaultPara)){
			return form(cstBaseBackFaultPara, model);
		}
		cstBaseBackFaultParaService.save(cstBaseBackFaultPara);
		addMessage(redirectAttributes, "保存备件故障率参数获取成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseBackFaultPara/?repage";
	}
	
	@RequiresPermissions("cst:base:cstBaseBackFaultPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstBaseBackFaultPara cstBaseBackFaultPara, RedirectAttributes redirectAttributes) {
		cstBaseBackFaultParaService.delete(cstBaseBackFaultPara);
		addMessage(redirectAttributes, "删除备件故障率参数获取成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseBackFaultPara/?repage";
	}

}