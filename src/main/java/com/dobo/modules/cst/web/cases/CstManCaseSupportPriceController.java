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
import com.dobo.modules.cst.entity.cases.CstManCaseSupportPrice;
import com.dobo.modules.cst.service.cases.CstManCaseSupportPriceService;

/**
 * 单次、先行支持服务单价表Controller
 * @author admin
 * @version 2017-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/case/cstManCaseSupportPrice")
public class CstManCaseSupportPriceController extends BaseController {

	@Autowired
	private CstManCaseSupportPriceService cstManCaseSupportPriceService;
	
	@ModelAttribute
	public CstManCaseSupportPrice get(@RequestParam(required=false) String id) {
		CstManCaseSupportPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManCaseSupportPriceService.get(id);
		}
		if (entity == null){
			entity = new CstManCaseSupportPrice();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:case:cstManCaseSupportPrice:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManCaseSupportPrice cstManCaseSupportPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManCaseSupportPrice> page = cstManCaseSupportPriceService.findPage(new Page<CstManCaseSupportPrice>(request, response), cstManCaseSupportPrice); 
		model.addAttribute("page", page);
		return "modules/cst/case/cstManCaseSupportPriceList";
	}

	@RequiresPermissions("cst:case:cstManCaseSupportPrice:view")
	@RequestMapping(value = "form")
	public String form(CstManCaseSupportPrice cstManCaseSupportPrice, Model model) {
		model.addAttribute("cstManCaseSupportPrice", cstManCaseSupportPrice);
		return "modules/cst/case/cstManCaseSupportPriceForm";
	}

	@RequiresPermissions("cst:case:cstManCaseSupportPrice:edit")
	@RequestMapping(value = "save")
	public String save(CstManCaseSupportPrice cstManCaseSupportPrice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManCaseSupportPrice)){
			return form(cstManCaseSupportPrice, model);
		}
		cstManCaseSupportPriceService.save(cstManCaseSupportPrice);
		addMessage(redirectAttributes, "保存单次、先行支持服务单价成功");
		return "redirect:"+Global.getAdminPath()+"/cst/case/cstManCaseSupportPrice/?repage";
	}
	
	@RequiresPermissions("cst:case:cstManCaseSupportPrice:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManCaseSupportPrice cstManCaseSupportPrice, RedirectAttributes redirectAttributes) {
		cstManCaseSupportPriceService.delete(cstManCaseSupportPrice);
		addMessage(redirectAttributes, "删除单次、先行支持服务单价成功");
		return "redirect:"+Global.getAdminPath()+"/cst/case/cstManCaseSupportPrice/?repage";
	}

}