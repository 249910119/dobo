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
import com.dobo.modules.cst.common.Constants;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.service.model.CstModelCalculateFormulaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 成本计算公式定义表： 1.根据创建时间、状态、更新时间和更新前状态作为时间戳判断条件; 2.定义到二级成本分类，比如：一线(工时\人工\费用\激励)； 3.指标类型要与订单成本明细一一对应；Controller
 * @author admin
 * @version 2016-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/model/cstModelCalculateFormula")
public class CstModelCalculateFormulaController extends BaseController {

	@Autowired
	private CstModelCalculateFormulaService cstModelCalculateFormulaService;
	
	@ModelAttribute
	public CstModelCalculateFormula get(@RequestParam(required=false) String id) {
		CstModelCalculateFormula entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstModelCalculateFormulaService.get(id);
		}
		if (entity == null){
			entity = new CstModelCalculateFormula();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:model:cstModelCalculateFormula:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstModelCalculateFormula cstModelCalculateFormula, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstModelCalculateFormula> page = cstModelCalculateFormulaService.findPage(new Page<CstModelCalculateFormula>(request, response), cstModelCalculateFormula); 
		model.addAttribute("page", page);
		return "modules/cst/model/cstModelCalculateFormulaList";
	}

	@RequiresPermissions("cst:model:cstModelCalculateFormula:view")
	@RequestMapping(value = "form")
	public String form(CstModelCalculateFormula cstModelCalculateFormula, Model model) {
		model.addAttribute("cstModelCalculateFormula", cstModelCalculateFormula);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/model/cstModelCalculateFormulaForm";
	}

	@RequiresPermissions("cst:model:cstModelCalculateFormula:edit")
	@RequestMapping(value = "save")
	public String save(CstModelCalculateFormula cstModelCalculateFormula, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstModelCalculateFormula)){
			return form(cstModelCalculateFormula, model);
		}
		cstModelCalculateFormulaService.save(cstModelCalculateFormula);
		addMessage(redirectAttributes, "保存成本计算公式定义表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_MODELCALFORMULA_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelCalculateFormula/?repage";
	}
	
	@RequiresPermissions("cst:model:cstModelCalculateFormula:edit")
	@RequestMapping(value = "delete")
	public String delete(CstModelCalculateFormula cstModelCalculateFormula, RedirectAttributes redirectAttributes) {
		cstModelCalculateFormulaService.delete(cstModelCalculateFormula);
		addMessage(redirectAttributes, "删除成本计算公式定义表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CacheDataUtils.CACHE_MODELCALFORMULA_MAP);
		return "redirect:"+Global.getAdminPath()+"/cst/model/cstModelCalculateFormula/?repage";
	}

}