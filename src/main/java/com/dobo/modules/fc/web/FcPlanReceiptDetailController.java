/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.web;

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
import com.dobo.common.utils.SpringContextHolder;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.fc.dao.FcPlanInnerFeeDao;
import com.dobo.modules.fc.entity.FcPlanReceiptDetail;
import com.dobo.modules.fc.service.FcPlanReceiptDetailService;

/**
 * 项目计划收款明细Controller
 * @author admin
 * @version 2016-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcPlanReceiptDetail")
public class FcPlanReceiptDetailController extends BaseController {

	@Autowired
	private FcPlanReceiptDetailService fcPlanReceiptDetailService;
	private static FcPlanInnerFeeDao fcPlanInnerFeeDao = SpringContextHolder.getBean(FcPlanInnerFeeDao.class);
	
	@ModelAttribute
	public FcPlanReceiptDetail get(@RequestParam(required=false) String id) {
		FcPlanReceiptDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcPlanReceiptDetailService.get(id);
		}
		if (entity == null){
			entity = new FcPlanReceiptDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcPlanReceiptDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcPlanReceiptDetail fcPlanReceiptDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcPlanReceiptDetail> page = fcPlanReceiptDetailService.findPage(new Page<FcPlanReceiptDetail>(request, response), fcPlanReceiptDetail); 
		model.addAttribute("page", page);
		return "modules/fc/fcPlanReceiptDetailList";
	}

	@RequiresPermissions("fc:fcPlanReceiptDetail:view")
	@RequestMapping(value = "form")
	public String form(FcPlanReceiptDetail fcPlanReceiptDetail, Model model) {
		model.addAttribute("fcPlanReceiptDetail", fcPlanReceiptDetail);
		return "modules/fc/fcPlanReceiptDetailForm";
	}

	@RequiresPermissions("fc:fcPlanReceiptDetail:edit")
	@RequestMapping(value = "save")
	public String save(FcPlanReceiptDetail fcPlanReceiptDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcPlanReceiptDetail)){
			return form(fcPlanReceiptDetail, model);
		}
		fcPlanReceiptDetailService.save(fcPlanReceiptDetail);
		addMessage(redirectAttributes, "保存项目计划收款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanReceiptDetail/?repage";
	}
	
	@RequiresPermissions("fc:fcPlanReceiptDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(FcPlanReceiptDetail fcPlanReceiptDetail, RedirectAttributes redirectAttributes) {
		fcPlanReceiptDetailService.delete(fcPlanReceiptDetail);
		addMessage(redirectAttributes, "删除项目计划收款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanReceiptDetail/?repage";
	}

}