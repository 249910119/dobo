/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.parts;

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
import com.dobo.modules.cst.entity.parts.CstPartsFillRate;
import com.dobo.modules.cst.service.parts.CstPartsFillRateService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件满足率定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsFillRate")
public class CstPartsFillRateController extends BaseController {

	@Autowired
	private CstPartsFillRateService cstPartsFillRateService;
	
	@ModelAttribute
	public CstPartsFillRate get(@RequestParam(required=false) String id) {
		CstPartsFillRate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsFillRateService.get(id);
		}
		if (entity == null){
			entity = new CstPartsFillRate();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsFillRate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsFillRate cstPartsFillRate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsFillRate> page = cstPartsFillRateService.findPage(new Page<CstPartsFillRate>(request, response), cstPartsFillRate); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsFillRateList";
	}

	@RequiresPermissions("cst:parts:cstPartsFillRate:view")
	@RequestMapping(value = "form")
	public String form(CstPartsFillRate cstPartsFillRate, Model model) {
		model.addAttribute("cstPartsFillRate", cstPartsFillRate);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsFillRateForm";
	}

	@RequiresPermissions("cst:parts:cstPartsFillRate:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsFillRate cstPartsFillRate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsFillRate)){
			return form(cstPartsFillRate, model);
		}
		cstPartsFillRateService.save(cstPartsFillRate);
		addMessage(redirectAttributes, "保存备件满足率定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_FILL_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsFillRate/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsFillRate:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsFillRate cstPartsFillRate, RedirectAttributes redirectAttributes) {
		cstPartsFillRateService.delete(cstPartsFillRate);
		addMessage(redirectAttributes, "删除备件满足率定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_FILL_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsFillRate/?repage";
	}

}