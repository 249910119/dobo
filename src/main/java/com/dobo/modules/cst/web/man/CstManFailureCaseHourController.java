/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.man;

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
import com.dobo.common.utils.EhCacheUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.common.web.BaseController;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.man.CstManFailureCaseHour;
import com.dobo.modules.cst.service.man.CstManFailureCaseHourService;
import com.dobo.modules.cst.web.base.CstResourceBaseInfoController;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 故障CASE处理工时定义表Controller
 * @author admin
 * @version 2016-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManFailureCaseHour")
public class CstManFailureCaseHourController extends BaseController {

	@Autowired
	private CstManFailureCaseHourService cstManFailureCaseHourService;
	@Autowired
	private CstResourceBaseInfoController cstManFailureCaseHourController;
	
	@ModelAttribute
	public CstManFailureCaseHour get(@RequestParam(required=false) String id) {
		CstManFailureCaseHour entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManFailureCaseHourService.get(id);
		}
		if (entity == null){
			entity = new CstManFailureCaseHour();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManFailureCaseHour:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManFailureCaseHour cstManFailureCaseHour, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManFailureCaseHour> page = cstManFailureCaseHourService.findPage(new Page<CstManFailureCaseHour>(request, response), cstManFailureCaseHour); 
		model.addAttribute("page", page);
		return "modules/cst/man/cstManFailureCaseHourList";
	}

	@RequiresPermissions("cst:man:cstManFailureCaseHour:view")
	@RequestMapping(value = "form")
	public String form(CstManFailureCaseHour cstManFailureCaseHour, Model model) {
		model.addAttribute("cstManFailureCaseHour", cstManFailureCaseHour);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/man/cstManFailureCaseHourForm";
	}

	@RequiresPermissions("cst:man:cstManFailureCaseHour:edit")
	@RequestMapping(value = "save")
	public String save(CstManFailureCaseHour cstManFailureCaseHour, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManFailureCaseHour)){
			return form(cstManFailureCaseHour, model);
		}
		cstManFailureCaseHourService.save(cstManFailureCaseHour);
		addMessage(redirectAttributes, "保存故障CASE处理工时定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MAN_FAILURE_CASE_HOUR_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureCaseHour/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManFailureCaseHour:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManFailureCaseHour cstManFailureCaseHour, RedirectAttributes redirectAttributes) {
		cstManFailureCaseHourService.delete(cstManFailureCaseHour);
		EhCacheUtils.remove(CacheDataUtils.CST_MAN_FAILURE_CASE_HOUR_CACHE, "dataMap");
		addMessage(redirectAttributes, "删除故障CASE处理工时定义成功");
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureCaseHour/?repage";
	}

	/*@RequestMapping(value = "selectList")
	public String resourceBaseInfolist(CstResourceBaseInfo cstResourceBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model){
		cstManFailureCaseHourController.resourceBaseInfolist(cstResourceBaseInfo, request, response, model);
		return "modules/cst/base/resourceBaseSelectList";
	}*/
}