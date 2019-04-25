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
import com.dobo.modules.cst.entity.model.CstModelModuleInfo;
import com.dobo.modules.cst.entity.model.CstModelParaDef;
import com.dobo.modules.cst.service.model.CstModelModuleInfoService;

/**
 * 成本模型信息表，定义成本一级分类（人工类、备件类）Controller
 * @author admin
 * @version 2016-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/model/cstModelModuleInfo")
public class CstModelModuleInfoController extends BaseController {

	@Autowired
	private CstModelModuleInfoService cstModelModuleInfoService;
	
	@ModelAttribute
	public CstModelModuleInfo get(@RequestParam(required=false) String id) {
		CstModelModuleInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstModelModuleInfoService.get(id);
		}
		if (entity == null){
			entity = new CstModelModuleInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:model:cstModelModuleInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstModelModuleInfo cstModelModuleInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstModelModuleInfo> page = cstModelModuleInfoService.findPage(new Page<CstModelModuleInfo>(request, response), cstModelModuleInfo); 
		model.addAttribute("page", page);
		return "modules/cst/model/cstModelModuleInfoList";
	}

	@RequiresPermissions("cst:model:cstModelModuleInfo:view")
	@RequestMapping(value = "form")
	public String form(CstModelModuleInfo cstModelModuleInfo, Model model) {
		model.addAttribute("cstModelModuleInfo", cstModelModuleInfo);
		return "modules/cst/model/cstModelModuleInfoForm";
	}

	@RequiresPermissions("cst:model:cstModelModuleInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstModelModuleInfo cstModelModuleInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstModelModuleInfo)){
			return form(cstModelModuleInfo, model);
		}
		cstModelModuleInfoService.save(cstModelModuleInfo);
		addMessage(redirectAttributes, "保存成本模型信息表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_MODULEINFO_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelModuleInfo/?repage";
	}
	
	@RequiresPermissions("cst:model:cstModelModuleInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstModelModuleInfo cstModelModuleInfo, RedirectAttributes redirectAttributes) {
		cstModelModuleInfoService.delete(cstModelModuleInfo);
		addMessage(redirectAttributes, "删除成本模型信息表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_MODULEINFO_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelModuleInfo/?repage";
	}
	
	/**
	 * 模型信息弹出选择框数据
	 * @param cstModelModuleInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectList")
	public String selectList(CstModelModuleInfo cstModelModuleInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		list(cstModelModuleInfo, request, response, model);
		return "modules/cst/model/projectSelectList";
	}
}