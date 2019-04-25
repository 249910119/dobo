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
import com.dobo.modules.cst.entity.check.CstCheckResmgrHour;
import com.dobo.modules.cst.service.check.CstCheckResmgrHourService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检-资源岗巡检安排工时表Controller
 * @author admin
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckResmgrHour")
public class CstCheckResmgrHourController extends BaseController {

	@Autowired
	private CstCheckResmgrHourService cstCheckResmgrHourService;
	
	@ModelAttribute
	public CstCheckResmgrHour get(@RequestParam(required=false) String id) {
		CstCheckResmgrHour entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckResmgrHourService.get(id);
		}
		if (entity == null){
			entity = new CstCheckResmgrHour();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckResmgrHour:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckResmgrHour cstCheckResmgrHour, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckResmgrHour> page = cstCheckResmgrHourService.findPage(new Page<CstCheckResmgrHour>(request, response), cstCheckResmgrHour); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckResmgrHourList";
	}

	@RequiresPermissions("cst:check:cstCheckResmgrHour:view")
	@RequestMapping(value = "form")
	public String form(CstCheckResmgrHour cstCheckResmgrHour, Model model) {
		model.addAttribute("cstCheckResmgrHour", cstCheckResmgrHour);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckResmgrHourForm";
	}

	@RequiresPermissions("cst:check:cstCheckResmgrHour:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckResmgrHour cstCheckResmgrHour, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckResmgrHour)){
			return form(cstCheckResmgrHour, model);
		}
		cstCheckResmgrHourService.save(cstCheckResmgrHour);
		addMessage(redirectAttributes, "保存巡检-资源岗巡检安排工时表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESMGR_HOUR);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckResmgrHour/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckResmgrHour:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckResmgrHour cstCheckResmgrHour, RedirectAttributes redirectAttributes) {
		cstCheckResmgrHourService.delete(cstCheckResmgrHour);
		addMessage(redirectAttributes, "删除巡检-资源岗巡检安排工时表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESMGR_HOUR);
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckResmgrHour/?repage";
	}

}