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
import com.dobo.modules.cst.entity.parts.CstPartsCooperCost;
import com.dobo.modules.cst.service.parts.CstPartsCooperCostService;

/**
 * 备件合作成本参数Controller
 * @author admin
 * @version 2019-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsCooperCost")
public class CstPartsCooperCostController extends BaseController {

	@Autowired
	private CstPartsCooperCostService cstPartsCooperCostService;
	
	@ModelAttribute
	public CstPartsCooperCost get(@RequestParam(required=false) String id) {
		CstPartsCooperCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsCooperCostService.get(id);
		}
		if (entity == null){
			entity = new CstPartsCooperCost();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsCooperCost:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsCooperCost cstPartsCooperCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsCooperCost> page = cstPartsCooperCostService.findPage(new Page<CstPartsCooperCost>(request, response), cstPartsCooperCost); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsCooperCostList";
	}

	@RequiresPermissions("cst:parts:cstPartsCooperCost:view")
	@RequestMapping(value = "form")
	public String form(CstPartsCooperCost cstPartsCooperCost, Model model) {
		model.addAttribute("cstPartsCooperCost", cstPartsCooperCost);
		return "modules/cst/parts/cstPartsCooperCostForm";
	}

	@RequiresPermissions("cst:parts:cstPartsCooperCost:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsCooperCost cstPartsCooperCost, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsCooperCost)){
			return form(cstPartsCooperCost, model);
		}
		cstPartsCooperCostService.save(cstPartsCooperCost);
		addMessage(redirectAttributes, "保存备件合作成本参数成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsCooperCost/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsCooperCost:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsCooperCost cstPartsCooperCost, RedirectAttributes redirectAttributes) {
		cstPartsCooperCostService.delete(cstPartsCooperCost);
		addMessage(redirectAttributes, "删除备件合作成本参数成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsCooperCost/?repage";
	}

}