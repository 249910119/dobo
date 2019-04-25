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
import com.dobo.modules.cst.entity.parts.CstPartsRiskCostPara;
import com.dobo.modules.cst.service.parts.CstPartsRiskCostParaService;

/**
 * 备件风险储备金Controller
 * @author admin
 * @version 2019-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsRiskCostPara")
public class CstPartsRiskCostParaController extends BaseController {

	@Autowired
	private CstPartsRiskCostParaService cstPartsRiskCostParaService;
	
	@ModelAttribute
	public CstPartsRiskCostPara get(@RequestParam(required=false) String id) {
		CstPartsRiskCostPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsRiskCostParaService.get(id);
		}
		if (entity == null){
			entity = new CstPartsRiskCostPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsRiskCostPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsRiskCostPara cstPartsRiskCostPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsRiskCostPara> page = cstPartsRiskCostParaService.findPage(new Page<CstPartsRiskCostPara>(request, response), cstPartsRiskCostPara); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsRiskCostParaList";
	}

	@RequiresPermissions("cst:parts:cstPartsRiskCostPara:view")
	@RequestMapping(value = "form")
	public String form(CstPartsRiskCostPara cstPartsRiskCostPara, Model model) {
		model.addAttribute("cstPartsRiskCostPara", cstPartsRiskCostPara);
		return "modules/cst/parts/cstPartsRiskCostParaForm";
	}

	@RequiresPermissions("cst:parts:cstPartsRiskCostPara:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsRiskCostPara cstPartsRiskCostPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsRiskCostPara)){
			return form(cstPartsRiskCostPara, model);
		}
		cstPartsRiskCostParaService.save(cstPartsRiskCostPara);
		addMessage(redirectAttributes, "保存备件风险储备金成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsRiskCostPara/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsRiskCostPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsRiskCostPara cstPartsRiskCostPara, RedirectAttributes redirectAttributes) {
		cstPartsRiskCostParaService.delete(cstPartsRiskCostPara);
		addMessage(redirectAttributes, "删除备件风险储备金成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsRiskCostPara/?repage";
	}

}