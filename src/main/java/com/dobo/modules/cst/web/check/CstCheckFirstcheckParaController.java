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
import com.dobo.modules.cst.entity.check.CstCheckFirstcheckPara;
import com.dobo.modules.cst.service.check.CstCheckFirstcheckParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检-首次巡检系数表Controller
 * @author admin
 * @version 2016-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckFirstcheckPara")
public class CstCheckFirstcheckParaController extends BaseController {

	@Autowired
	private CstCheckFirstcheckParaService cstCheckFirstcheckParaService;
	
	@ModelAttribute
	public CstCheckFirstcheckPara get(@RequestParam(required=false) String id) {
		CstCheckFirstcheckPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckFirstcheckParaService.get(id);
		}
		if (entity == null){
			entity = new CstCheckFirstcheckPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckFirstcheckPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckFirstcheckPara cstCheckFirstcheckPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckFirstcheckPara> page = cstCheckFirstcheckParaService.findPage(new Page<CstCheckFirstcheckPara>(request, response), cstCheckFirstcheckPara); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckFirstcheckParaList";
	}

	@RequiresPermissions("cst:check:cstCheckFirstcheckPara:view")
	@RequestMapping(value = "form")
	public String form(CstCheckFirstcheckPara cstCheckFirstcheckPara, Model model) {
		model.addAttribute("cstCheckFirstcheckPara", cstCheckFirstcheckPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckFirstcheckParaForm";
	}

	@RequiresPermissions("cst:check:cstCheckFirstcheckPara:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckFirstcheckPara cstCheckFirstcheckPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckFirstcheckPara)){
			return form(cstCheckFirstcheckPara, model);
		}
		cstCheckFirstcheckParaService.save(cstCheckFirstcheckPara);
		addMessage(redirectAttributes, "保存巡检-首次巡检系数表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_FIRSTCHECK_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckFirstcheckPara/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckFirstcheckPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckFirstcheckPara cstCheckFirstcheckPara, RedirectAttributes redirectAttributes) {
		cstCheckFirstcheckParaService.delete(cstCheckFirstcheckPara);
		addMessage(redirectAttributes, "删除巡检-首次巡检系数表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_FIRSTCHECK_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckFirstcheckPara/?repage";
	}

}