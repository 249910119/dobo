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
import com.dobo.modules.cst.entity.base.CstBaseResourceCase;
import com.dobo.modules.cst.service.base.CstBaseResourceCaseService;

/**
 * 故障case样本Controller
 * @author admin
 * @version 2019-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstBaseResourceCase")
public class CstBaseResourceCaseController extends BaseController {

	@Autowired
	private CstBaseResourceCaseService cstBaseResourceCaseService;
	
	@ModelAttribute
	public CstBaseResourceCase get(@RequestParam(required=false) String id) {
		CstBaseResourceCase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstBaseResourceCaseService.get(id);
		}
		if (entity == null){
			entity = new CstBaseResourceCase();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstBaseResourceCase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstBaseResourceCase cstBaseResourceCase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstBaseResourceCase> page = cstBaseResourceCaseService.findPage(new Page<CstBaseResourceCase>(request, response), cstBaseResourceCase); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstBaseResourceCaseList";
	}

	@RequiresPermissions("cst:base:cstBaseResourceCase:view")
	@RequestMapping(value = "form")
	public String form(CstBaseResourceCase cstBaseResourceCase, Model model) {
		model.addAttribute("cstBaseResourceCase", cstBaseResourceCase);
		return "modules/cst/base/cstBaseResourceCaseForm";
	}

	@RequiresPermissions("cst:base:cstBaseResourceCase:edit")
	@RequestMapping(value = "save")
	public String save(CstBaseResourceCase cstBaseResourceCase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstBaseResourceCase)){
			return form(cstBaseResourceCase, model);
		}
		cstBaseResourceCaseService.save(cstBaseResourceCase);
		addMessage(redirectAttributes, "保存故障case样本成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseResourceCase/?repage";
	}
	
	@RequiresPermissions("cst:base:cstBaseResourceCase:edit")
	@RequestMapping(value = "delete")
	public String delete(CstBaseResourceCase cstBaseResourceCase, RedirectAttributes redirectAttributes) {
		cstBaseResourceCaseService.delete(cstBaseResourceCase);
		addMessage(redirectAttributes, "删除故障case样本成功");
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstBaseResourceCase/?repage";
	}

}