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
import com.dobo.modules.cst.entity.parts.CstPartsFailureRateCost;
import com.dobo.modules.cst.service.parts.CstPartsFailureRateCostService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件故障率与采购成本定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsFailureRateCost")
public class CstPartsFailureRateCostController extends BaseController {

	@Autowired
	private CstPartsFailureRateCostService cstPartsFailureRateCostService;
	
	@ModelAttribute
	public CstPartsFailureRateCost get(@RequestParam(required=false) String id) {
		CstPartsFailureRateCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsFailureRateCostService.get(id);
		}
		if (entity == null){
			entity = new CstPartsFailureRateCost();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsFailureRateCost:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsFailureRateCost cstPartsFailureRateCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsFailureRateCost> page = cstPartsFailureRateCostService.findPage(new Page<CstPartsFailureRateCost>(request, response), cstPartsFailureRateCost); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsFailureRateCostList";
	}

	@RequiresPermissions("cst:parts:cstPartsFailureRateCost:view")
	@RequestMapping(value = "form")
	public String form(CstPartsFailureRateCost cstPartsFailureRateCost, Model model) {
		model.addAttribute("cstPartsFailureRateCost", cstPartsFailureRateCost);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsFailureRateCostForm";
	}

	@RequiresPermissions("cst:parts:cstPartsFailureRateCost:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsFailureRateCost cstPartsFailureRateCost, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsFailureRateCost)){
			return form(cstPartsFailureRateCost, model);
		}
		cstPartsFailureRateCostService.save(cstPartsFailureRateCost);
		addMessage(redirectAttributes, "保存备件故障率与采购成本定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_PARTS_FAILURE_RATE_COST_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsFailureRateCost/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsFailureRateCost:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsFailureRateCost cstPartsFailureRateCost, RedirectAttributes redirectAttributes) {
		cstPartsFailureRateCostService.delete(cstPartsFailureRateCost);
		addMessage(redirectAttributes, "删除备件故障率与采购成本定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_PARTS_FAILURE_RATE_COST_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsFailureRateCost/?repage";
	}

}