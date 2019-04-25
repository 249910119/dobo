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
import com.dobo.modules.cst.entity.check.CstCheckDistanceUnitHour;
import com.dobo.modules.cst.service.check.CstCheckDistanceUnitHourService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检-单台路程工时表Controller
 * @author admin
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckDistanceUnitHour")
public class CstCheckDistanceUnitHourController extends BaseController {

	@Autowired
	private CstCheckDistanceUnitHourService cstCheckDistanceUnitHourService;
	
	@ModelAttribute
	public CstCheckDistanceUnitHour get(@RequestParam(required=false) String id) {
		CstCheckDistanceUnitHour entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckDistanceUnitHourService.get(id);
		}
		if (entity == null){
			entity = new CstCheckDistanceUnitHour();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckDistanceUnitHour:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckDistanceUnitHour cstCheckDistanceUnitHour, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckDistanceUnitHour> page = cstCheckDistanceUnitHourService.findPage(new Page<CstCheckDistanceUnitHour>(request, response), cstCheckDistanceUnitHour); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckDistanceUnitHourList";
	}

	@RequiresPermissions("cst:check:cstCheckDistanceUnitHour:view")
	@RequestMapping(value = "form")
	public String form(CstCheckDistanceUnitHour cstCheckDistanceUnitHour, Model model) {
		model.addAttribute("cstCheckDistanceUnitHour", cstCheckDistanceUnitHour);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckDistanceUnitHourForm";
	}

	@RequiresPermissions("cst:check:cstCheckDistanceUnitHour:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckDistanceUnitHour cstCheckDistanceUnitHour, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckDistanceUnitHour)){
			return form(cstCheckDistanceUnitHour, model);
		}
		cstCheckDistanceUnitHourService.save(cstCheckDistanceUnitHour);
		addMessage(redirectAttributes, "保存巡检-单台路程工时表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_UNIT_HOUR);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckDistanceUnitHour/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckDistanceUnitHour:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckDistanceUnitHour cstCheckDistanceUnitHour, RedirectAttributes redirectAttributes) {
		cstCheckDistanceUnitHourService.delete(cstCheckDistanceUnitHour);
		addMessage(redirectAttributes, "删除巡检-单台路程工时表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_UNIT_HOUR);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckDistanceUnitHour/?repage";
	}

}