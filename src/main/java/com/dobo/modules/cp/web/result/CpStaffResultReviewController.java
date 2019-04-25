/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.web.result;

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
import com.dobo.modules.cp.entity.result.CpStaffResultReview;
import com.dobo.modules.cp.service.result.CpStaffResultReviewService;

/**
 * 系统人员测评计算结果Controller
 * @author admin
 * @version 2018-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cp/result/cpStaffResultReview")
public class CpStaffResultReviewController extends BaseController {

	@Autowired
	private CpStaffResultReviewService cpStaffResultReviewService;
	
	@ModelAttribute
	public CpStaffResultReview get(@RequestParam(required=false) String id) {
		CpStaffResultReview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cpStaffResultReviewService.get(id);
		}
		if (entity == null){
			entity = new CpStaffResultReview();
		}
		return entity;
	}
	
	@RequiresPermissions("cp:result:cpStaffResultReview:view")
	@RequestMapping(value = {"list", ""})
	public String list(CpStaffResultReview cpStaffResultReview, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CpStaffResultReview> page = cpStaffResultReviewService.findPage(new Page<CpStaffResultReview>(request, response), cpStaffResultReview); 
		model.addAttribute("page", page);
		return "modules/cp/result/cpStaffResultReviewList";
	}

	@RequiresPermissions("cp:result:cpStaffResultReview:view")
	@RequestMapping(value = "form")
	public String form(CpStaffResultReview cpStaffResultReview, Model model) {
		model.addAttribute("cpStaffResultReview", cpStaffResultReview);
		return "modules/cp/result/cpStaffResultReviewForm";
	}

	@RequiresPermissions("cp:result:cpStaffResultReview:edit")
	@RequestMapping(value = "save")
	public String save(CpStaffResultReview cpStaffResultReview, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cpStaffResultReview)){
			return form(cpStaffResultReview, model);
		}
		cpStaffResultReviewService.save(cpStaffResultReview);
		addMessage(redirectAttributes, "保存系统人员测评计算结果成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffResultReview/?repage";
	}
	
	@RequiresPermissions("cp:result:cpStaffResultReview:edit")
	@RequestMapping(value = "delete")
	public String delete(CpStaffResultReview cpStaffResultReview, RedirectAttributes redirectAttributes) {
		cpStaffResultReviewService.delete(cpStaffResultReview);
		addMessage(redirectAttributes, "删除系统人员测评计算结果成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffResultReview/?repage";
	}

}