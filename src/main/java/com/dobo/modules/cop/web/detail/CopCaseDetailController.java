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
import com.dobo.modules.cop.entity.detail.CopCaseDetail;
import com.dobo.modules.cop.service.detail.CopCaseDetailService;

/**
 * top单次、先行支持服务报价清单Controller
 * @author admin
 * @version 2017-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cop/detail/copCaseDetail")
public class CopCaseDetailController extends BaseController {

	@Autowired
	private CopCaseDetailService copCaseDetailService;
	
	@ModelAttribute
	public CopCaseDetail get(@RequestParam(required=false) String id) {
		CopCaseDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = copCaseDetailService.get(id);
		}
		if (entity == null){
			entity = new CopCaseDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("cop:detail:copCaseDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(CopCaseDetail copCaseDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CopCaseDetail> page = copCaseDetailService.findPage(new Page<CopCaseDetail>(request, response), copCaseDetail); 
		model.addAttribute("page", page);
		return "modules/cop/detail/copCaseDetailList";
	}

	@RequiresPermissions("cop:detail:copCaseDetail:view")
	@RequestMapping(value = "form")
	public String form(CopCaseDetail copCaseDetail, Model model) {
		model.addAttribute("copCaseDetail", copCaseDetail);
		return "modules/cop/detail/copCaseDetailForm";
	}

	@RequiresPermissions("cop:detail:copCaseDetail:edit")
	@RequestMapping(value = "save")
	public String save(CopCaseDetail copCaseDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, copCaseDetail)){
			return form(copCaseDetail, model);
		}
		copCaseDetailService.save(copCaseDetail);
		addMessage(redirectAttributes, "保存top单次、先行支持服务报价清单成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copCaseDetail/?repage";
	}
	
	@RequiresPermissions("cop:detail:copCaseDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(CopCaseDetail copCaseDetail, RedirectAttributes redirectAttributes) {
		copCaseDetailService.delete(copCaseDetail);
		addMessage(redirectAttributes, "删除top单次、先行支持服务报价清单成功");
		return "redirect:"+Global.getAdminPath()+"/cop/detail/copCaseDetail/?repage";
	}

}