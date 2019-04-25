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
import com.dobo.modules.cst.entity.base.CstBaseBackPackPara;
import com.dobo.modules.cst.service.base.CstBaseBackPackParaService;

/**
 * 备件合作包取值参数获取Controller
 * @author admin
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstBaseBackPackPara")
public class CstBaseBackPackParaController extends BaseController {

	@Autowired
	private CstBaseBackPackParaService cstBaseBackPackParaService;
	
	@ModelAttribute
	public CstBaseBackPackPara get(@RequestParam(required=false) String id) {
		CstBaseBackPackPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstBaseBackPackParaService.get(id);
		}
		if (entity == null){
			entity = new CstBaseBackPackPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstBaseBackPackPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstBaseBackPackPara cstBaseBackPackPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstBaseBackPackPara> page = cstBaseBackPackParaService.findPage(new Page<CstBaseBackPackPara>(request, response), cstBaseBackPackPara); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstBaseBackPackParaList";
	}

	@RequiresPermissions("cst:base:cstBaseBackPackPara:view")
	@RequestMapping(value = "form")
	public String form(CstBaseBackPackPara cstBaseBackPackPara, Model model) {
		model.addAttribute("cstBaseBackPackPara", cstBaseBackPackPara);
		return "modules/cst/base/cstBaseBackPackParaForm";
	}

	@RequiresPermissions("cst:base:cstBaseBackPackPara:edit")
	@RequestMapping(value = "save")
	public String save(CstBaseBackPackPara cstBaseBackPackPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstBaseBackPackPara)){
			return form(cstBaseBackPackPara, model);
		}
		cstBaseBackPackParaService.save(cstBaseBackPackPara);
		addMessage(redirectAttributes, "保存备件合作包取值参数获取成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseBackPackPara/?repage";
	}
	
	@RequiresPermissions("cst:base:cstBaseBackPackPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstBaseBackPackPara cstBaseBackPackPara, RedirectAttributes redirectAttributes) {
		cstBaseBackPackParaService.delete(cstBaseBackPackPara);
		addMessage(redirectAttributes, "删除备件合作包取值参数获取成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseBackPackPara/?repage";
	}

}