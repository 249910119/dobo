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
import com.dobo.modules.cst.entity.detail.CstOrderBackCostInfo;
import com.dobo.modules.cst.service.detail.CstOrderBackCostInfoService;

/**
 * 订单备件故障成本（自有、分包）Controller
 * @author admin
 * @version 2019-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/detail/cstOrderBackCostInfo")
public class CstOrderBackCostInfoController extends BaseController {

	@Autowired
	private CstOrderBackCostInfoService cstOrderBackCostInfoService;
	
	@ModelAttribute
	public CstOrderBackCostInfo get(@RequestParam(required=false) String id) {
		CstOrderBackCostInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstOrderBackCostInfoService.get(id);
		}
		if (entity == null){
			entity = new CstOrderBackCostInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:detail:cstOrderBackCostInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstOrderBackCostInfo cstOrderBackCostInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstOrderBackCostInfo> page = cstOrderBackCostInfoService.findPage(new Page<CstOrderBackCostInfo>(request, response), cstOrderBackCostInfo); 
		model.addAttribute("page", page);
		return "modules/cst/detail/cstOrderBackCostInfoList";
	}

	@RequiresPermissions("cst:detail:cstOrderBackCostInfo:view")
	@RequestMapping(value = "form")
	public String form(CstOrderBackCostInfo cstOrderBackCostInfo, Model model) {
		model.addAttribute("cstOrderBackCostInfo", cstOrderBackCostInfo);
		return "modules/cst/detail/cstOrderBackCostInfoForm";
	}

	@RequiresPermissions("cst:detail:cstOrderBackCostInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstOrderBackCostInfo cstOrderBackCostInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstOrderBackCostInfo)){
			return form(cstOrderBackCostInfo, model);
		}
		cstOrderBackCostInfoService.save(cstOrderBackCostInfo);
		addMessage(redirectAttributes, "保存订单备件故障成本（自有、分包）成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstOrderBackCostInfo/?repage";
	}
	
	@RequiresPermissions("cst:detail:cstOrderBackCostInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstOrderBackCostInfo cstOrderBackCostInfo, RedirectAttributes redirectAttributes) {
		cstOrderBackCostInfoService.delete(cstOrderBackCostInfo);
		addMessage(redirectAttributes, "删除订单备件故障成本（自有、分包）成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstOrderBackCostInfo/?repage";
	}

}