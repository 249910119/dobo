/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.model;

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
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.service.model.CstModelProdModuleRelService;

/**
 * 产品成本模型使用定义表，定义某产品使用的成本模型Controller
 * @author admin
 * @version 2016-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/model/cstModelProdModuleRel")
public class CstModelProdModuleRelController extends BaseController {

	@Autowired
	private CstModelProdModuleRelService cstModelProdModuleRelService;
	
	@ModelAttribute
	public CstModelProdModuleRel get(@RequestParam(required=false) String id) {
		CstModelProdModuleRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstModelProdModuleRelService.get(id);
		}
		if (entity == null){
			entity = new CstModelProdModuleRel();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:model:cstModelProdModuleRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstModelProdModuleRel cstModelProdModuleRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstModelProdModuleRel> page = cstModelProdModuleRelService.findPage(new Page<CstModelProdModuleRel>(request, response), cstModelProdModuleRel); 
		model.addAttribute("page", page);
		return "modules/cst/model/cstModelProdModuleRelList";
	}

	@RequiresPermissions("cst:model:cstModelProdModuleRel:view")
	@RequestMapping(value = "form")
	public String form(CstModelProdModuleRel cstModelProdModuleRel, Model model) {
		model.addAttribute("cstModelProdModuleRel", cstModelProdModuleRel);
		return "modules/cst/model/cstModelProdModuleRelForm";
	}

	@RequiresPermissions("cst:model:cstModelProdModuleRel:edit")
	@RequestMapping(value = "save")
	public String save(CstModelProdModuleRel cstModelProdModuleRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstModelProdModuleRel)){
			return form(cstModelProdModuleRel, model);
		}
		cstModelProdModuleRelService.save(cstModelProdModuleRel);
		addMessage(redirectAttributes, "保存产品成本模型使用定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_PRODMODULEREL_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelProdModuleRel/?repage";
	}
	
	@RequiresPermissions("cst:model:cstModelProdModuleRel:edit")
	@RequestMapping(value = "delete")
	public String delete(CstModelProdModuleRel cstModelProdModuleRel, RedirectAttributes redirectAttributes) {
		cstModelProdModuleRelService.delete(cstModelProdModuleRel);
		addMessage(redirectAttributes, "删除产品成本模型使用定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_PRODMODULEREL_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelProdModuleRel/?repage";
	}

}