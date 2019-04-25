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
import com.dobo.modules.cst.common.Constants;
import com.dobo.modules.cst.entity.check.CstCheckDistanceStepHour;
import com.dobo.modules.cst.service.check.CstCheckDistanceStepHourService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检-路程工时阶梯系数表Controller
 * @author admin
 * @version 2016-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckDistanceStepHour")
public class CstCheckDistanceStepHourController extends BaseController {

	@Autowired
	private CstCheckDistanceStepHourService cstCheckDistanceStepHourService;
	
	@ModelAttribute
	public CstCheckDistanceStepHour get(@RequestParam(required=false) String id) {
		CstCheckDistanceStepHour entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckDistanceStepHourService.get(id);
		}
		if (entity == null){
			entity = new CstCheckDistanceStepHour();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckDistanceStepHour:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckDistanceStepHour cstCheckDistanceStepHour, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckDistanceStepHour> page = cstCheckDistanceStepHourService.findPage(new Page<CstCheckDistanceStepHour>(request, response), cstCheckDistanceStepHour); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckDistanceStepHourList";
	}

	@RequiresPermissions("cst:check:cstCheckDistanceStepHour:view")
	@RequestMapping(value = "form")
	public String form(CstCheckDistanceStepHour cstCheckDistanceStepHour, Model model) {
		model.addAttribute("cstCheckDistanceStepHour", cstCheckDistanceStepHour);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckDistanceStepHourForm";
	}

	@RequiresPermissions("cst:check:cstCheckDistanceStepHour:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckDistanceStepHour cstCheckDistanceStepHour, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckDistanceStepHour)){
			return form(cstCheckDistanceStepHour, model);
		}
		cstCheckDistanceStepHourService.save(cstCheckDistanceStepHour);
		addMessage(redirectAttributes, "保存巡检-路程工时阶梯系数表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_STEP_HOUR);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckDistanceStepHour/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckDistanceStepHour:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckDistanceStepHour cstCheckDistanceStepHour, RedirectAttributes redirectAttributes) {
		cstCheckDistanceStepHourService.delete(cstCheckDistanceStepHour);
		addMessage(redirectAttributes, "删除巡检-路程工时阶梯系数表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_STEP_HOUR);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckDistanceStepHour/?repage";
	}

}