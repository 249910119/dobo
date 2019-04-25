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
import com.dobo.common.utils.SpringContextHolder;
import com.dobo.common.utils.StringUtils;
import com.dobo.common.web.BaseController;
import com.dobo.modules.fc.dao.FcPlanInnerFeeDao;
import com.dobo.modules.fc.entity.FcActualReceiptDetail;
import com.dobo.modules.fc.service.FcActualReceiptDetailService;

/**
 * 项目实际到款明细Controller
 * @author admin
 * @version 2016-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcActualReceiptDetail")
public class FcActualReceiptDetailController extends BaseController {

	@Autowired
	private FcActualReceiptDetailService fcActualReceiptDetailService;
	private static FcPlanInnerFeeDao fcPlanInnerFeeDao = SpringContextHolder.getBean(FcPlanInnerFeeDao.class);
	
	@ModelAttribute
	public FcActualReceiptDetail get(@RequestParam(required=false) String id) {
		FcActualReceiptDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcActualReceiptDetailService.get(id);
		}
		if (entity == null){
			entity = new FcActualReceiptDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcActualReceiptDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcActualReceiptDetail fcActualReceiptDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcActualReceiptDetail> page = fcActualReceiptDetailService.findPage(new Page<FcActualReceiptDetail>(request, response), fcActualReceiptDetail); 
		model.addAttribute("page", page);
		return "modules/fc/fcActualReceiptDetailList";
	}

	@RequiresPermissions("fc:fcActualReceiptDetail:view")
	@RequestMapping(value = "form")
	public String form(FcActualReceiptDetail fcActualReceiptDetail, Model model) {
		model.addAttribute("fcActualReceiptDetail", fcActualReceiptDetail);
		return "modules/fc/fcActualReceiptDetailForm";
	}

	@RequiresPermissions("fc:fcActualReceiptDetail:edit")
	@RequestMapping(value = "save")
	public String save(FcActualReceiptDetail fcActualReceiptDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcActualReceiptDetail)){
			return form(fcActualReceiptDetail, model);
		}
		fcActualReceiptDetailService.save(fcActualReceiptDetail);
		addMessage(redirectAttributes, "保存项目实际到款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcActualReceiptDetail/?repage";
	}
	
	@RequiresPermissions("fc:fcActualReceiptDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(FcActualReceiptDetail fcActualReceiptDetail, RedirectAttributes redirectAttributes) {
		fcActualReceiptDetailService.delete(fcActualReceiptDetail);
		addMessage(redirectAttributes, "删除项目实际到款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcActualReceiptDetail/?repage";
	}

}