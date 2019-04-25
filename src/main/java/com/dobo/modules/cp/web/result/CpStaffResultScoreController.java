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
import com.dobo.modules.cp.entity.result.CpStaffResultScore;
import com.dobo.modules.cp.service.result.CpStaffResultScoreService;

/**
 * 系统人员测评记录结果Controller
 * @author admin
 * @version 2018-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cp/result/cpStaffResultScore")
public class CpStaffResultScoreController extends BaseController {

	@Autowired
	private CpStaffResultScoreService cpStaffResultScoreService;
	
	@ModelAttribute
	public CpStaffResultScore get(@RequestParam(required=false) String id) {
		CpStaffResultScore entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cpStaffResultScoreService.get(id);
		}
		if (entity == null){
			entity = new CpStaffResultScore();
		}
		return entity;
	}
	
	@RequiresPermissions("cp:result:cpStaffResultScore:view")
	@RequestMapping(value = {"list", ""})
	public String list(CpStaffResultScore cpStaffResultScore, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CpStaffResultScore> page = cpStaffResultScoreService.findPage(new Page<CpStaffResultScore>(request, response), cpStaffResultScore); 
		model.addAttribute("page", page);
		return "modules/cp/result/cpStaffResultScoreList";
	}

	@RequiresPermissions("cp:result:cpStaffResultScore:view")
	@RequestMapping(value = "form")
	public String form(CpStaffResultScore cpStaffResultScore, Model model) {
		model.addAttribute("cpStaffResultScore", cpStaffResultScore);
		return "modules/cp/result/cpStaffResultScoreForm";
	}

	@RequiresPermissions("cp:result:cpStaffResultScore:edit")
	@RequestMapping(value = "save")
	public String save(CpStaffResultScore cpStaffResultScore, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cpStaffResultScore)){
			return form(cpStaffResultScore, model);
		}
		cpStaffResultScoreService.save(cpStaffResultScore);
		addMessage(redirectAttributes, "保存系统人员测评记录结果成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffResultScore/?repage";
	}
	
	@RequiresPermissions("cp:result:cpStaffResultScore:edit")
	@RequestMapping(value = "delete")
	public String delete(CpStaffResultScore cpStaffResultScore, RedirectAttributes redirectAttributes) {
		cpStaffResultScoreService.delete(cpStaffResultScore);
		addMessage(redirectAttributes, "删除系统人员测评记录结果成功");
		return "redirect:"+Global.getAdminPath()+"/cp/result/cpStaffResultScore/?repage";
	}

}