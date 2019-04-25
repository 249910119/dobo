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
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.fc.entity.FcActualPayDetail;
import com.dobo.modules.fc.service.FcActualPayDetailService;

/**
 * 实际付款明细Controller
 * @author admin
 * @version 2017-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcActualPayDetail")
public class FcActualPayDetailController extends BaseController {

	@Autowired
	private FcActualPayDetailService fcActualPayDetailService;
	
	@ModelAttribute
	public FcActualPayDetail get(@RequestParam(required=false) String id) {
		FcActualPayDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcActualPayDetailService.get(id);
		}
		if (entity == null){
			entity = new FcActualPayDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcActualPayDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcActualPayDetail fcActualPayDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcActualPayDetail> page = fcActualPayDetailService.findPage(new Page<FcActualPayDetail>(request, response), fcActualPayDetail); 
		model.addAttribute("page", page);
		return "modules/fc/fcActualPayDetailList";
	}

	@RequiresPermissions("fc:fcActualPayDetail:view")
	@RequestMapping(value = "form")
	public String form(FcActualPayDetail fcActualPayDetail, Model model) {
		model.addAttribute("fcActualPayDetail", fcActualPayDetail);
		return "modules/fc/fcActualPayDetailForm";
	}

	@RequiresPermissions("fc:fcActualPayDetail:edit")
	@RequestMapping(value = "save")
	public String save(FcActualPayDetail fcActualPayDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcActualPayDetail)){
			return form(fcActualPayDetail, model);
		}
		fcActualPayDetailService.save(fcActualPayDetail);
		addMessage(redirectAttributes, "保存实际付款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcActualPayDetail/?repage";
	}
	
	@RequiresPermissions("fc:fcActualPayDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(FcActualPayDetail fcActualPayDetail, RedirectAttributes redirectAttributes) {
		fcActualPayDetailService.delete(fcActualPayDetail);
		addMessage(redirectAttributes, "删除实际付款明细成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcActualPayDetail/?repage";
	}

}