/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.cases;

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
import com.dobo.modules.cst.entity.cases.CstCaseStandHourScale;
import com.dobo.modules.cst.service.cases.CstCaseStandHourScaleService;

/**
 * 单次、先行支持标准工时系数Controller
 * @author admin
 * @version 2017-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/case/cstCaseStandHourScale")
public class CstCaseStandHourScaleController extends BaseController {

	@Autowired
	private CstCaseStandHourScaleService cstCaseStandHourScaleService;
	
	@ModelAttribute
	public CstCaseStandHourScale get(@RequestParam(required=false) String id) {
		CstCaseStandHourScale entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCaseStandHourScaleService.get(id);
		}
		if (entity == null){
			entity = new CstCaseStandHourScale();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:case:cstCaseStandHourScale:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCaseStandHourScale cstCaseStandHourScale, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCaseStandHourScale> page = cstCaseStandHourScaleService.findPage(new Page<CstCaseStandHourScale>(request, response), cstCaseStandHourScale); 
		model.addAttribute("page", page);
		return "modules/cst/case/cstCaseStandHourScaleList";
	}

	@RequiresPermissions("cst:case:cstCaseStandHourScale:view")
	@RequestMapping(value = "form")
	public String form(CstCaseStandHourScale cstCaseStandHourScale, Model model) {
		model.addAttribute("cstCaseStandHourScale", cstCaseStandHourScale);
		return "modules/cst/case/cstCaseStandHourScaleForm";
	}

	@RequiresPermissions("cst:case:cstCaseStandHourScale:edit")
	@RequestMapping(value = "save")
	public String save(CstCaseStandHourScale cstCaseStandHourScale, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCaseStandHourScale)){
			return form(cstCaseStandHourScale, model);
		}
		cstCaseStandHourScaleService.save(cstCaseStandHourScale);
		addMessage(redirectAttributes, "保存单次、先行支持标准工时系数表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/case/cstCaseStandHourScale/?repage";
	}
	
	@RequiresPermissions("cst:case:cstCaseStandHourScale:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCaseStandHourScale cstCaseStandHourScale, RedirectAttributes redirectAttributes) {
		cstCaseStandHourScaleService.delete(cstCaseStandHourScale);
		addMessage(redirectAttributes, "删除单次、先行支持标准工时系数表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/case/cstCaseStandHourScale/?repage";
	}

}