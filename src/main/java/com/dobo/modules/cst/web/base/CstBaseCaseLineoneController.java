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
import com.dobo.modules.cst.entity.base.CstBaseCaseLineone;
import com.dobo.modules.cst.service.base.CstBaseCaseLineoneService;

/**
 * 故障配比明细Controller
 * @author admin
 * @version 2019-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstBaseCaseLineone")
public class CstBaseCaseLineoneController extends BaseController {

	@Autowired
	private CstBaseCaseLineoneService cstBaseCaseLineoneService;
	
	@ModelAttribute
	public CstBaseCaseLineone get(@RequestParam(required=false) String id) {
		CstBaseCaseLineone entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstBaseCaseLineoneService.get(id);
		}
		if (entity == null){
			entity = new CstBaseCaseLineone();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstBaseCaseLineone:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstBaseCaseLineone cstBaseCaseLineone, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstBaseCaseLineone> page = cstBaseCaseLineoneService.findPage(new Page<CstBaseCaseLineone>(request, response), cstBaseCaseLineone); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstBaseCaseLineoneList";
	}

	@RequiresPermissions("cst:base:cstBaseCaseLineone:view")
	@RequestMapping(value = "form")
	public String form(CstBaseCaseLineone cstBaseCaseLineone, Model model) {
		model.addAttribute("cstBaseCaseLineone", cstBaseCaseLineone);
		return "modules/cst/base/cstBaseCaseLineoneForm";
	}

	@RequiresPermissions("cst:base:cstBaseCaseLineone:edit")
	@RequestMapping(value = "save")
	public String save(CstBaseCaseLineone cstBaseCaseLineone, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstBaseCaseLineone)){
			return form(cstBaseCaseLineone, model);
		}
		cstBaseCaseLineoneService.save(cstBaseCaseLineone);
		addMessage(redirectAttributes, "保存故障配比明细成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseCaseLineone/?repage";
	}
	
	@RequiresPermissions("cst:base:cstBaseCaseLineone:edit")
	@RequestMapping(value = "delete")
	public String delete(CstBaseCaseLineone cstBaseCaseLineone, RedirectAttributes redirectAttributes) {
		cstBaseCaseLineoneService.delete(cstBaseCaseLineone);
		addMessage(redirectAttributes, "删除故障配比明细成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseCaseLineone/?repage";
	}

}