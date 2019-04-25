/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.man;

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
import com.dobo.modules.cst.entity.man.CstManCityPara;
import com.dobo.modules.cst.service.man.CstManCityParaService;
import com.dobo.modules.sys.entity.User;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 地域系数定义表Controller
 * @author admin
 * @version 2016-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManCityPara")
public class CstManCityParaController extends BaseController {

	@Autowired
	private CstManCityParaService cstManCityParaService;
	
	@ModelAttribute
	public CstManCityPara get(@RequestParam(required=false) String id) {
		CstManCityPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManCityParaService.get(id);
		}
		if (entity == null){
			entity = new CstManCityPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManCityPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManCityPara cstManCityPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManCityPara> page = cstManCityParaService.findPage(new Page<CstManCityPara>(request, response), cstManCityPara); 
		model.addAttribute("page", page);
		return "modules/cst/man/cstManCityParaList";
	}

	@RequiresPermissions("cst:man:cstManCityPara:view")
	@RequestMapping(value = "form")
	public String form(CstManCityPara cstManCityPara, Model model) {
		model.addAttribute("cstManCityPara", cstManCityPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/man/cstManCityParaForm";
	}

	@RequiresPermissions("cst:man:cstManCityPara:edit")
	@RequestMapping(value = "save")
	public String save(CstManCityPara cstManCityPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManCityPara)){
			return form(cstManCityPara, model);
		}
		cstManCityParaService.save(cstManCityPara);
		addMessage(redirectAttributes, "保存地域系数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, "cstManCityParaTest");
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManCityPara/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManCityPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManCityPara cstManCityPara, RedirectAttributes redirectAttributes) {
		cstManCityParaService.delete(cstManCityPara);
		addMessage(redirectAttributes, "删除地域系数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, "cstManCityParaTest");
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManCityPara/?repage";
	}

}