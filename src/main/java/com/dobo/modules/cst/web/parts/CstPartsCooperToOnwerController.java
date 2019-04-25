/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.parts;

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
import com.dobo.modules.cst.entity.parts.CstPartsCooperToOnwer;
import com.dobo.modules.cst.service.parts.CstPartsCooperToOnwerService;

/**
 * 备件合作转自有时间清单Controller
 * @author admin
 * @version 2019-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsCooperToOnwer")
public class CstPartsCooperToOnwerController extends BaseController {

	@Autowired
	private CstPartsCooperToOnwerService cstPartsCooperToOnwerService;
	
	@ModelAttribute
	public CstPartsCooperToOnwer get(@RequestParam(required=false) String id) {
		CstPartsCooperToOnwer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsCooperToOnwerService.get(id);
		}
		if (entity == null){
			entity = new CstPartsCooperToOnwer();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsCooperToOnwer:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsCooperToOnwer cstPartsCooperToOnwer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsCooperToOnwer> page = cstPartsCooperToOnwerService.findPage(new Page<CstPartsCooperToOnwer>(request, response), cstPartsCooperToOnwer); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsCooperToOnwerList";
	}

	@RequiresPermissions("cst:parts:cstPartsCooperToOnwer:view")
	@RequestMapping(value = "form")
	public String form(CstPartsCooperToOnwer cstPartsCooperToOnwer, Model model) {
		model.addAttribute("cstPartsCooperToOnwer", cstPartsCooperToOnwer);
		return "modules/cst/parts/cstPartsCooperToOnwerForm";
	}

	@RequiresPermissions("cst:parts:cstPartsCooperToOnwer:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsCooperToOnwer cstPartsCooperToOnwer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsCooperToOnwer)){
			return form(cstPartsCooperToOnwer, model);
		}
		cstPartsCooperToOnwerService.save(cstPartsCooperToOnwer);
		addMessage(redirectAttributes, "保存备件合作转自有时间清单成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsCooperToOnwer/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsCooperToOnwer:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsCooperToOnwer cstPartsCooperToOnwer, RedirectAttributes redirectAttributes) {
		cstPartsCooperToOnwerService.delete(cstPartsCooperToOnwer);
		addMessage(redirectAttributes, "删除备件合作转自有时间清单成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsCooperToOnwer/?repage";
	}

}