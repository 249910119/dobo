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
import com.dobo.modules.cst.entity.parts.CstPartsPackInfo;
import com.dobo.modules.cst.service.parts.CstPartsPackInfoService;

/**
 * 包名归属字典---主要用于维护主数据参考用Controller
 * @author admin
 * @version 2019-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsPackInfo")
public class CstPartsPackInfoController extends BaseController {

	@Autowired
	private CstPartsPackInfoService cstPartsPackInfoService;
	
	@ModelAttribute
	public CstPartsPackInfo get(@RequestParam(required=false) String id) {
		CstPartsPackInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsPackInfoService.get(id);
		}
		if (entity == null){
			entity = new CstPartsPackInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsPackInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsPackInfo cstPartsPackInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsPackInfo> page = cstPartsPackInfoService.findPage(new Page<CstPartsPackInfo>(request, response), cstPartsPackInfo); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsPackInfoList";
	}

	@RequiresPermissions("cst:parts:cstPartsPackInfo:view")
	@RequestMapping(value = "form")
	public String form(CstPartsPackInfo cstPartsPackInfo, Model model) {
		model.addAttribute("cstPartsPackInfo", cstPartsPackInfo);
		return "modules/cst/parts/cstPartsPackInfoForm";
	}

	@RequiresPermissions("cst:parts:cstPartsPackInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsPackInfo cstPartsPackInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsPackInfo)){
			return form(cstPartsPackInfo, model);
		}
		cstPartsPackInfoService.save(cstPartsPackInfo);
		addMessage(redirectAttributes, "保存包名归属字典---主要用于维护主数据参考用成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsPackInfo/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsPackInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsPackInfo cstPartsPackInfo, RedirectAttributes redirectAttributes) {
		cstPartsPackInfoService.delete(cstPartsPackInfo);
		addMessage(redirectAttributes, "删除包名归属字典---主要用于维护主数据参考用成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsPackInfo/?repage";
	}

}