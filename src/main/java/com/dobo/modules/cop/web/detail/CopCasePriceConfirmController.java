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
import com.dobo.modules.cop.entity.detail.CopCasePriceConfirm;
import com.dobo.modules.cop.service.detail.CopCasePriceConfirmService;

/**
 * CASE单次报价确认表(TOP)Controller
 * @author admin
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/detail/copCasePriceConfirm")
public class CopCasePriceConfirmController extends BaseController {

	@Autowired
	private CopCasePriceConfirmService copCasePriceConfirmService;
	
	@ModelAttribute
	public CopCasePriceConfirm get(@RequestParam(required=false) String id) {
		CopCasePriceConfirm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copCasePriceConfirmService.get(id);
		}
		if (entity == null){
			entity = new CopCasePriceConfirm();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:detail:copCasePriceConfirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopCasePriceConfirm copCasePriceConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopCasePriceConfirm> page = copCasePriceConfirmService.findPage(new Page<CopCasePriceConfirm>(request, response), copCasePriceConfirm); 
		model.addAttribute("page", page);
		return "modules/cop/detail/copCasePriceConfirmList";
	}

	@RequiresPermissions("cop:detail:copCasePriceConfirm:view")
	@RequestMapping(value = "form")
	public String form(CopCasePriceConfirm copCasePriceConfirm, Model model) {
		model.addAttribute("copCasePriceConfirm", copCasePriceConfirm);
		return "modules/cop/detail/copCasePriceConfirmForm";
	}

	@RequiresPermissions("cop:detail:copCasePriceConfirm:edit")
	@RequestMapping(value = "save")
	public String save(CopCasePriceConfirm copCasePriceConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copCasePriceConfirm)){
			return form(copCasePriceConfirm, model);
		}
		copCasePriceConfirmService.save(copCasePriceConfirm);
		addMessage(redirectAttributes, "保存CASE单次报价确认表(TOP)成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copCasePriceConfirm/?repage";
	}
	
	@RequiresPermissions("cop:detail:copCasePriceConfirm:edit")
	@RequestMapping(value = "delete")
	public String delete(CopCasePriceConfirm copCasePriceConfirm, RedirectAttributes redirectAttributes) {
		copCasePriceConfirmService.delete(copCasePriceConfirm);
		addMessage(redirectAttributes, "删除CASE单次报价确认表(TOP)成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copCasePriceConfirm/?repage";
	}

}