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
import com.dobo.modules.cst.entity.parts.CstPartsSlaCostRate;
import com.dobo.modules.cst.service.parts.CstPartsSlaCostRateService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 故障成本SLA采购成本系数Controller
 * @author admin
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsSlaCostRate")
public class CstPartsSlaCostRateController extends BaseController {

	@Autowired
	private CstPartsSlaCostRateService cstPartsSlaCostRateService;
	
	@ModelAttribute
	public CstPartsSlaCostRate get(@RequestParam(required=false) String id) {
		CstPartsSlaCostRate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsSlaCostRateService.get(id);
		}
		if (entity == null){
			entity = new CstPartsSlaCostRate();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsSlaCostRate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsSlaCostRate cstPartsSlaCostRate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsSlaCostRate> page = cstPartsSlaCostRateService.findPage(new Page<CstPartsSlaCostRate>(request, response), cstPartsSlaCostRate); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsSlaCostRateList";
	}

	@RequiresPermissions("cst:parts:cstPartsSlaCostRate:view")
	@RequestMapping(value = "form")
	public String form(CstPartsSlaCostRate cstPartsSlaCostRate, Model model) {
		model.addAttribute("cstPartsSlaCostRate", cstPartsSlaCostRate);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsSlaCostRateForm";
	}

	@RequiresPermissions("cst:parts:cstPartsSlaCostRate:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsSlaCostRate cstPartsSlaCostRate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsSlaCostRate)){
			return form(cstPartsSlaCostRate, model);
		}
		cstPartsSlaCostRateService.save(cstPartsSlaCostRate);
		addMessage(redirectAttributes, "保存故障成本SLA采购成本系数成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_SLA_COST_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsSlaCostRate/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsSlaCostRate:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsSlaCostRate cstPartsSlaCostRate, RedirectAttributes redirectAttributes) {
		cstPartsSlaCostRateService.delete(cstPartsSlaCostRate);
		addMessage(redirectAttributes, "删除故障成本SLA采购成本系数成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_SLA_COST_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsSlaCostRate/?repage";
	}

}