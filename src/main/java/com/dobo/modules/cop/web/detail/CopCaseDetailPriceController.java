/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.web.detail;

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
import com.dobo.modules.cop.entity.detail.CopCaseDetailPrice;
import com.dobo.modules.cop.service.detail.CopCaseDetailPriceService;

/**
 * 单次、先行支持服务报价明细Controller
 * @author admin
 * @version 2017-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/detail/copCaseDetailPrice")
public class CopCaseDetailPriceController extends BaseController {

	@Autowired
	private CopCaseDetailPriceService copCaseDetailPriceService;
	
	@ModelAttribute
	public CopCaseDetailPrice get(@RequestParam(required=false) String id) {
		CopCaseDetailPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copCaseDetailPriceService.get(id);
		}
		if (entity == null){
			entity = new CopCaseDetailPrice();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:detail:copCaseDetailPrice:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopCaseDetailPrice copCaseDetailPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopCaseDetailPrice> page = copCaseDetailPriceService.findPage(new Page<CopCaseDetailPrice>(request, response), copCaseDetailPrice); 
		model.addAttribute("page", page);
		return "modules/cop/detail/copCaseDetailPriceList";
	}

	@RequiresPermissions("cop:detail:copCaseDetailPrice:view")
	@RequestMapping(value = "form")
	public String form(CopCaseDetailPrice copCaseDetailPrice, Model model) {
		model.addAttribute("copCaseDetailPrice", copCaseDetailPrice);
		return "modules/cop/detail/copCaseDetailPriceForm";
	}

	@RequiresPermissions("cop:detail:copCaseDetailPrice:edit")
	@RequestMapping(value = "save")
	public String save(CopCaseDetailPrice copCaseDetailPrice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copCaseDetailPrice)){
			return form(copCaseDetailPrice, model);
		}
		copCaseDetailPriceService.save(copCaseDetailPrice);
		addMessage(redirectAttributes, "保存单次、先行支持服务报价明细成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copCaseDetailPrice/?repage";
	}
	
	@RequiresPermissions("cop:detail:copCaseDetailPrice:edit")
	@RequestMapping(value = "delete")
	public String delete(CopCaseDetailPrice copCaseDetailPrice, RedirectAttributes redirectAttributes) {
		copCaseDetailPriceService.delete(copCaseDetailPrice);
		addMessage(redirectAttributes, "删除单次、先行支持服务报价明细成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copCaseDetailPrice/?repage";
	}

}