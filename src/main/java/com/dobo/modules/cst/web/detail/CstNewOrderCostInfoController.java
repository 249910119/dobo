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
import com.dobo.modules.cst.entity.detail.CstNewOrderCostInfo;
import com.dobo.modules.cst.service.detail.CstNewOrderCostInfoService;

/**
 * 资源模型成本Controller
 * @author admin
 * @version 2019-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/detail/cstNewOrderCostInfo")
public class CstNewOrderCostInfoController extends BaseController {

	@Autowired
	private CstNewOrderCostInfoService cstNewOrderCostInfoService;
	
	@ModelAttribute
	public CstNewOrderCostInfo get(@RequestParam(required=false) String id) {
		CstNewOrderCostInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstNewOrderCostInfoService.get(id);
		}
		if (entity == null){
			entity = new CstNewOrderCostInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:detail:cstNewOrderCostInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstNewOrderCostInfo cstNewOrderCostInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstNewOrderCostInfo> page = cstNewOrderCostInfoService.findPage(new Page<CstNewOrderCostInfo>(request, response), cstNewOrderCostInfo); 
		model.addAttribute("page", page);
		return "modules/cst/detail/cstNewOrderCostInfoList";
	}

	@RequiresPermissions("cst:detail:cstNewOrderCostInfo:view")
	@RequestMapping(value = "form")
	public String form(CstNewOrderCostInfo cstNewOrderCostInfo, Model model) {
		model.addAttribute("cstNewOrderCostInfo", cstNewOrderCostInfo);
		return "modules/cst/detail/cstNewOrderCostInfoForm";
	}

	@RequiresPermissions("cst:detail:cstNewOrderCostInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstNewOrderCostInfo cstNewOrderCostInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstNewOrderCostInfo)){
			return form(cstNewOrderCostInfo, model);
		}
		cstNewOrderCostInfoService.save(cstNewOrderCostInfo);
		addMessage(redirectAttributes, "保存资源模型成本成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstNewOrderCostInfo/?repage";
	}
	
	@RequiresPermissions("cst:detail:cstNewOrderCostInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstNewOrderCostInfo cstNewOrderCostInfo, RedirectAttributes redirectAttributes) {
		cstNewOrderCostInfoService.delete(cstNewOrderCostInfo);
		addMessage(redirectAttributes, "删除资源模型成本成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstNewOrderCostInfo/?repage";
	}

}