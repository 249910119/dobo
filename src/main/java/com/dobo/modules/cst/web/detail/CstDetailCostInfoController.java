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
import com.dobo.modules.cst.entity.detail.CstDetailCostInfo;
import com.dobo.modules.cst.service.detail.CstDetailCostInfoService;

/**
 * 订单清单成本明细Controller
 * @author admin
 * @version 2016-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/detail/cstDetailCostInfo")
public class CstDetailCostInfoController extends BaseController {

	@Autowired
	private CstDetailCostInfoService cstDetailCostInfoService;
	
	@ModelAttribute
	public CstDetailCostInfo get(@RequestParam(required=false) String id) {
		CstDetailCostInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstDetailCostInfoService.get(id);
		}
		if (entity == null){
			entity = new CstDetailCostInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:detail:cstDetailCostInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstDetailCostInfo cstDetailCostInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstDetailCostInfo> page = cstDetailCostInfoService.findPage(new Page<CstDetailCostInfo>(request, response), cstDetailCostInfo); 
		model.addAttribute("page", page);
		return "modules/cst/detail/cstDetailCostInfoList";
	}

	@RequiresPermissions("cst:detail:cstDetailCostInfo:view")
	@RequestMapping(value = "form")
	public String form(CstDetailCostInfo cstDetailCostInfo, Model model) {
		model.addAttribute("cstDetailCostInfo", cstDetailCostInfo);
		return "modules/cst/detail/cstDetailCostInfoForm";
	}

	@RequiresPermissions("cst:detail:cstDetailCostInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstDetailCostInfo cstDetailCostInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstDetailCostInfo)){
			return form(cstDetailCostInfo, model);
		}
		cstDetailCostInfoService.save(cstDetailCostInfo);
		addMessage(redirectAttributes, "保存订单清单成本明细成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstDetailCostInfo/?repage";
	}
	
	@RequiresPermissions("cst:detail:cstDetailCostInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstDetailCostInfo cstDetailCostInfo, RedirectAttributes redirectAttributes) {
		cstDetailCostInfoService.delete(cstDetailCostInfo);
		addMessage(redirectAttributes, "删除订单清单成本明细成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstDetailCostInfo/?repage";
	}

}