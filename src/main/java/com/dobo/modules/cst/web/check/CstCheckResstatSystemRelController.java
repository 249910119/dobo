/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.check;

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
import com.dobo.common.utils.EhCacheUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.common.Constants;
import com.dobo.modules.cst.entity.check.CstCheckResstatSystemRel;
import com.dobo.modules.cst.service.check.CstCheckResstatSystemRelService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检-资源计划分类对应设备大类关系表Controller
 * @author admin
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckResstatSystemRel")
public class CstCheckResstatSystemRelController extends BaseController {

	@Autowired
	private CstCheckResstatSystemRelService cstCheckResstatSystemRelService;
	
	@ModelAttribute
	public CstCheckResstatSystemRel get(@RequestParam(required=false) String id) {
		CstCheckResstatSystemRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckResstatSystemRelService.get(id);
		}
		if (entity == null){
			entity = new CstCheckResstatSystemRel();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckResstatSystemRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckResstatSystemRel cstCheckResstatSystemRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckResstatSystemRel> page = cstCheckResstatSystemRelService.findPage(new Page<CstCheckResstatSystemRel>(request, response), cstCheckResstatSystemRel); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckResstatSystemRelList";
	}

	@RequiresPermissions("cst:check:cstCheckResstatSystemRel:view")
	@RequestMapping(value = "form")
	public String form(CstCheckResstatSystemRel cstCheckResstatSystemRel, Model model) {
		model.addAttribute("cstCheckResstatSystemRel", cstCheckResstatSystemRel);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckResstatSystemRelForm";
	}

	@RequiresPermissions("cst:check:cstCheckResstatSystemRel:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckResstatSystemRel cstCheckResstatSystemRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckResstatSystemRel)){
			return form(cstCheckResstatSystemRel, model);
		}
		cstCheckResstatSystemRelService.save(cstCheckResstatSystemRel);
		addMessage(redirectAttributes, "保存巡检-资源计划分类对应设备大类关系表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESSTAT_SYSTEM_REL);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckResstatSystemRel/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckResstatSystemRel:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckResstatSystemRel cstCheckResstatSystemRel, RedirectAttributes redirectAttributes) {
		cstCheckResstatSystemRelService.delete(cstCheckResstatSystemRel);
		addMessage(redirectAttributes, "删除巡检-资源计划分类对应设备大类关系表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESSTAT_SYSTEM_REL);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckResstatSystemRel/?repage";
	}

}