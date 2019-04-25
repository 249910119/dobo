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
import com.dobo.common.utils.StringUtils;
import com.dobo.common.web.BaseController;
import com.dobo.modules.fc.entity.FcProjectInfo;
import com.dobo.modules.fc.service.FcProjectInfoService;

/**
 * 财务费用计算对应项目信息Controller
 * @author admin
 * @version 2016-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/fc/fcProjectInfo")
public class FcProjectInfoController extends BaseController {

	@Autowired
	private FcProjectInfoService fcProjectInfoService;
	
	@ModelAttribute
	public FcProjectInfo get(@RequestParam(required=false) String id) {
		FcProjectInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fcProjectInfoService.get(id);
		}
		if (entity == null){
			entity = new FcProjectInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("fc:fcProjectInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(FcProjectInfo fcProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FcProjectInfo> page = fcProjectInfoService.findPage(new Page<FcProjectInfo>(request, response), fcProjectInfo); 
		model.addAttribute("page", page);
		return "modules/fc/fcProjectInfoList";
	}

	@RequiresPermissions("fc:fcProjectInfo:view")
	@RequestMapping(value = "form")
	public String form(FcProjectInfo fcProjectInfo, Model model) {
		model.addAttribute("fcProjectInfo", fcProjectInfo);
		return "modules/fc/fcProjectInfoForm";
	}

	@RequiresPermissions("fc:fcProjectInfo:edit")
	@RequestMapping(value = "save")
	public String save(FcProjectInfo fcProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fcProjectInfo)){
			return form(fcProjectInfo, model);
		}
		fcProjectInfoService.save(fcProjectInfo);
		addMessage(redirectAttributes, "保存项目信息成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcProjectInfo/?repage";
	}
	
	@RequiresPermissions("fc:fcProjectInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(FcProjectInfo fcProjectInfo, RedirectAttributes redirectAttributes) {
		fcProjectInfoService.delete(fcProjectInfo);
		addMessage(redirectAttributes, "删除项目信息成功");
		return "redirect:"+Global.getAdminPath()+"/fc/fcProjectInfo/?repage";
	}
	
	/**
	 * 项目信息弹出选择框数据
	 * @param fcProjectInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectList")
	public String selectList(FcProjectInfo fcProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(fcProjectInfo, request, response, model);
		return "modules/fc/projectSelectList";
	}

}