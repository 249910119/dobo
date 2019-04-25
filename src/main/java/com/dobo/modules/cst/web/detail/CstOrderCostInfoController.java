/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.detail;

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
import com.dobo.modules.cst.entity.detail.CstOrderCostInfo;
import com.dobo.modules.cst.service.detail.CstOrderCostInfoService;

/**
 * 订单成本明细Controller
 * @author admin
 * @version 2017-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/detail/cstOrderCostInfo")
public class CstOrderCostInfoController extends BaseController {

	@Autowired
	private CstOrderCostInfoService cstOrderCostInfoService;
	
	@ModelAttribute
	public CstOrderCostInfo get(@RequestParam(required=false) String id) {
		CstOrderCostInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstOrderCostInfoService.get(id);
		}
		if (entity == null){
			entity = new CstOrderCostInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:detail:cstOrderCostInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstOrderCostInfo cstOrderCostInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstOrderCostInfo> page = cstOrderCostInfoService.findPage(new Page<CstOrderCostInfo>(request, response), cstOrderCostInfo); 
		model.addAttribute("page", page);
		return "modules/cst/detail/cstOrderCostInfoList";
	}

	@RequiresPermissions("cst:detail:cstOrderCostInfo:view")
	@RequestMapping(value = "form")
	public String form(CstOrderCostInfo cstOrderCostInfo, Model model) {
		model.addAttribute("cstOrderCostInfo", cstOrderCostInfo);
		return "modules/cst/detail/cstOrderCostInfoForm";
	}

	@RequiresPermissions("cst:detail:cstOrderCostInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstOrderCostInfo cstOrderCostInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstOrderCostInfo)){
			return form(cstOrderCostInfo, model);
		}
		cstOrderCostInfoService.save(cstOrderCostInfo);
		addMessage(redirectAttributes, "保存订单成本明细成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstOrderCostInfo/?repage";
	}
	
	@RequiresPermissions("cst:detail:cstOrderCostInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstOrderCostInfo cstOrderCostInfo, RedirectAttributes redirectAttributes) {
		cstOrderCostInfoService.delete(cstOrderCostInfo);
		addMessage(redirectAttributes, "删除订单成本明细成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstOrderCostInfo/?repage";
	}

}