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
import com.dobo.modules.cst.entity.model.CstModelParaDef;
import com.dobo.modules.cst.service.model.CstModelParaDefService;
import com.dobo.modules.fc.entity.FcProjectInfo;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 成本参数定义表：定义成本模型公式中用到的简单和复杂参数Controller
 * @author admin
 * @version 2016-11-11
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/model/cstModelParaDef")
public class CstModelParaDefController extends BaseController {

	@Autowired
	private CstModelParaDefService cstModelParaDefService;
	
	@ModelAttribute
	public CstModelParaDef get(@RequestParam(required=false) String id) {
		CstModelParaDef entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstModelParaDefService.get(id);
		}
		if (entity == null){
			entity = new CstModelParaDef();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:model:cstModelParaDef:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstModelParaDef cstModelParaDef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstModelParaDef> page = cstModelParaDefService.findPage(new Page<CstModelParaDef>(request, response), cstModelParaDef); 
		model.addAttribute("page", page);
		return "modules/cst/model/cstModelParaDefList";
	}

	@RequiresPermissions("cst:model:cstModelParaDef:view")
	@RequestMapping(value = "form")
	public String form(CstModelParaDef cstModelParaDef, Model model) {
		model.addAttribute("cstModelParaDef", cstModelParaDef);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/model/cstModelParaDefForm";
	}

	@RequiresPermissions("cst:model:cstModelParaDef:edit")
	@RequestMapping(value = "save")
	public String save(CstModelParaDef cstModelParaDef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstModelParaDef)){
			return form(cstModelParaDef, model);
		}
		cstModelParaDefService.save(cstModelParaDef);
		addMessage(redirectAttributes, "保存成本参数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_MODELPARADEF_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelParaDef/?repage";
	}
	
	@RequiresPermissions("cst:model:cstModelParaDef:edit")
	@RequestMapping(value = "delete")
	public String delete(CstModelParaDef cstModelParaDef, RedirectAttributes redirectAttributes) {
		cstModelParaDefService.delete(cstModelParaDef);
		addMessage(redirectAttributes, "删除成本参数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_MODELPARADEF_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelParaDef/?repage";
	}
	
}