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
import com.dobo.modules.cst.entity.parts.CstPartsCooperModelDetail;
import com.dobo.modules.cst.service.parts.CstPartsCooperModelDetailService;

/**
 * 备件合作清单Controller
 * @author admin
 * @version 2019-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsCooperModelDetail")
public class CstPartsCooperModelDetailController extends BaseController {

	@Autowired
	private CstPartsCooperModelDetailService cstPartsCooperModelDetailService;
	
	@ModelAttribute
	public CstPartsCooperModelDetail get(@RequestParam(required=false) String id) {
		CstPartsCooperModelDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsCooperModelDetailService.get(id);
		}
		if (entity == null){
			entity = new CstPartsCooperModelDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsCooperModelDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsCooperModelDetail cstPartsCooperModelDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsCooperModelDetail> page = cstPartsCooperModelDetailService.findPage(new Page<CstPartsCooperModelDetail>(request, response), cstPartsCooperModelDetail); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsCooperModelDetailList";
	}

	@RequiresPermissions("cst:parts:cstPartsCooperModelDetail:view")
	@RequestMapping(value = "form")
	public String form(CstPartsCooperModelDetail cstPartsCooperModelDetail, Model model) {
		model.addAttribute("cstPartsCooperModelDetail", cstPartsCooperModelDetail);
		return "modules/cst/parts/cstPartsCooperModelDetailForm";
	}

	@RequiresPermissions("cst:parts:cstPartsCooperModelDetail:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsCooperModelDetail cstPartsCooperModelDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsCooperModelDetail)){
			return form(cstPartsCooperModelDetail, model);
		}
		cstPartsCooperModelDetailService.save(cstPartsCooperModelDetail);
		addMessage(redirectAttributes, "保存备件合作清单成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsCooperModelDetail/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsCooperModelDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsCooperModelDetail cstPartsCooperModelDetail, RedirectAttributes redirectAttributes) {
		cstPartsCooperModelDetailService.delete(cstPartsCooperModelDetail);
		addMessage(redirectAttributes, "删除备件合作清单成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsCooperModelDetail/?repage";
	}

}