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
import com.dobo.modules.fc.entity.FcPlanPayDetail;
import com.dobo.modules.fc.service.FcPlanPayDetailService;

/**
 * 项目计划付款明细Controller
 * @author admin
 * @version 2016-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcPlanPayDetail")
public class FcPlanPayDetailController extends BaseController {

	@Autowired
	private FcPlanPayDetailService fcPlanPayDetailService;
	private static FcPlanInnerFeeDao fcPlanInnerFeeDao = SpringContextHolder.getBean(FcPlanInnerFeeDao.class);
	
	@ModelAttribute
	public FcPlanPayDetail get(@RequestParam(required=false) String id) {
		FcPlanPayDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcPlanPayDetailService.get(id);
		}
		if (entity == null){
			entity = new FcPlanPayDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcPlanPayDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcPlanPayDetail fcPlanPayDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcPlanPayDetail> page = fcPlanPayDetailService.findPage(new Page<FcPlanPayDetail>(request, response), fcPlanPayDetail); 
		model.addAttribute("page", page);
		return "modules/fc/fcPlanPayDetailList";
	}

	@RequiresPermissions("fc:fcPlanPayDetail:view")
	@RequestMapping(value = "form")
	public String form(FcPlanPayDetail fcPlanPayDetail, Model model) {
		model.addAttribute("fcPlanPayDetail", fcPlanPayDetail);
		return "modules/fc/fcPlanPayDetailForm";
	}

	@RequiresPermissions("fc:fcPlanPayDetail:edit")
	@RequestMapping(value = "save")
	public String save(FcPlanPayDetail fcPlanPayDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcPlanPayDetail)){
			return form(fcPlanPayDetail, model);
		}
		fcPlanPayDetailService.save(fcPlanPayDetail);
		addMessage(redirectAttributes, "保存项目计划付款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanPayDetail/?repage";
	}
	
	@RequiresPermissions("fc:fcPlanPayDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(FcPlanPayDetail fcPlanPayDetail, RedirectAttributes redirectAttributes) {
		fcPlanPayDetailService.delete(fcPlanPayDetail);
		addMessage(redirectAttributes, "删除项目计划付款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcPlanPayDetail/?repage";
	}

}