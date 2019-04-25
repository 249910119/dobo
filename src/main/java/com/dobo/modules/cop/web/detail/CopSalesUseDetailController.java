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
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;
import com.dobo.modules.cop.service.detail.CopSalesUseDetailService;

/**
 * 销售员账户使用明细表Controller
 * @author admin
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/detail/copSalesUseDetail")
public class CopSalesUseDetailController extends BaseController {

	@Autowired
	private CopSalesUseDetailService copSalesUseDetailService;
	
	@ModelAttribute
	public CopSalesUseDetail get(@RequestParam(required=false) String id) {
		CopSalesUseDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copSalesUseDetailService.get(id);
		}
		if (entity == null){
			entity = new CopSalesUseDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:detail:copSalesUseDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopSalesUseDetail copSalesUseDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopSalesUseDetail> page = copSalesUseDetailService.findPage(new Page<CopSalesUseDetail>(request, response), copSalesUseDetail); 
		model.addAttribute("page", page);
		return "modules/cop/detail/copSalesUseDetailList";
	}

	@RequiresPermissions("cop:detail:copSalesUseDetail:view")
	@RequestMapping(value = "form")
	public String form(CopSalesUseDetail copSalesUseDetail, Model model) {
		model.addAttribute("copSalesUseDetail", copSalesUseDetail);
		return "modules/cop/detail/copSalesUseDetailForm";
	}

	@RequiresPermissions("cop:detail:copSalesUseDetail:edit")
	@RequestMapping(value = "save")
	public String save(CopSalesUseDetail copSalesUseDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copSalesUseDetail)){
			return form(copSalesUseDetail, model);
		}
		copSalesUseDetailService.save(copSalesUseDetail);
		addMessage(redirectAttributes, "保存销售员账户使用明细表成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copSalesUseDetail/?repage";
	}
	
	@RequiresPermissions("cop:detail:copSalesUseDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(CopSalesUseDetail copSalesUseDetail, RedirectAttributes redirectAttributes) {
		copSalesUseDetailService.delete(copSalesUseDetail);
		addMessage(redirectAttributes, "删除销售员账户使用明细表成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copSalesUseDetail/?repage";
	}

}