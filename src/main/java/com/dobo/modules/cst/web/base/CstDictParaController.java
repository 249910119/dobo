/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.base;

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
import com.dobo.modules.cst.entity.base.CstDictPara;
import com.dobo.modules.cst.service.base.CstDictParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 成本模型字典Controller
 * @author admin
 * @version 2016-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstDictPara")
public class CstDictParaController extends BaseController {

	@Autowired
	private CstDictParaService cstDictParaService;
	
	@ModelAttribute
	public CstDictPara get(@RequestParam(required=false) String id) {
		CstDictPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstDictParaService.get(id);
		}
		if (entity == null){
			entity = new CstDictPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstDictPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstDictPara cstDictPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstDictPara> page = cstDictParaService.findPage(new Page<CstDictPara>(request, response), cstDictPara); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstDictParaList";
	}

	@RequiresPermissions("cst:base:cstDictPara:view")
	@RequestMapping(value = "form")
	public String form(CstDictPara cstDictPara, Model model) {
		model.addAttribute("cstDictPara", cstDictPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/base/cstDictParaForm";
	}

	@RequiresPermissions("cst:base:cstDictPara:edit")
	@RequestMapping(value = "save")
	public String save(CstDictPara cstDictPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstDictPara)){
			return form(cstDictPara, model);
		}
		cstDictParaService.save(cstDictPara);
		addMessage(redirectAttributes, "保存成本模型字典成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_DICT_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstDictPara/?repage";
	}
	
	@RequiresPermissions("cst:base:cstDictPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstDictPara cstDictPara, RedirectAttributes redirectAttributes) {
		cstDictParaService.delete(cstDictPara);
		addMessage(redirectAttributes, "删除成本模型字典成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_DICT_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/base/cstDictPara/?repage";
	}

}