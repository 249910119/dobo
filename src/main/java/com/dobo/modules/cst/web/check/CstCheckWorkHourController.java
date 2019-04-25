/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.check;

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
import com.dobo.common.utils.EhCacheUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;
import com.dobo.modules.cst.service.check.CstCheckWorkHourService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检工时定义表Controller
 * @author admin
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckWorkHour")
public class CstCheckWorkHourController extends BaseController {

	@Autowired
	private CstCheckWorkHourService cstCheckWorkHourService;
	
	@ModelAttribute
	public CstCheckWorkHour get(@RequestParam(required=false) String id) {
		CstCheckWorkHour entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckWorkHourService.get(id);
		}
		if (entity == null){
			entity = new CstCheckWorkHour();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckWorkHour:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckWorkHour cstCheckWorkHour, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckWorkHour> page = cstCheckWorkHourService.findPage(new Page<CstCheckWorkHour>(request, response), cstCheckWorkHour); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckWorkHourList";
	}

	@RequiresPermissions("cst:check:cstCheckWorkHour:view")
	@RequestMapping(value = "form")
	public String form(CstCheckWorkHour cstCheckWorkHour, Model model) {
		model.addAttribute("cstCheckWorkHour", cstCheckWorkHour);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckWorkHourForm";
	}

	@RequiresPermissions("cst:check:cstCheckWorkHour:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckWorkHour cstCheckWorkHour, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckWorkHour)){
			return form(cstCheckWorkHour, model);
		}
		cstCheckWorkHourService.save(cstCheckWorkHour);
		addMessage(redirectAttributes, "保存巡检工时定义表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_CHECK_WORK_HOUR_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckWorkHour/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckWorkHour:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckWorkHour cstCheckWorkHour, RedirectAttributes redirectAttributes) {
		cstCheckWorkHourService.delete(cstCheckWorkHour);
		addMessage(redirectAttributes, "删除巡检工时定义表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_CHECK_WORK_HOUR_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckWorkHour/?repage";
	}

}