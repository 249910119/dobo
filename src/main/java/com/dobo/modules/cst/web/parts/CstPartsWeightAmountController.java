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
import com.dobo.modules.cst.entity.parts.CstPartsWeightAmount;
import com.dobo.modules.cst.service.parts.CstPartsWeightAmountService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件加权平均在保量定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsWeightAmount")
public class CstPartsWeightAmountController extends BaseController {

	@Autowired
	private CstPartsWeightAmountService cstPartsWeightAmountService;
	
	@ModelAttribute
	public CstPartsWeightAmount get(@RequestParam(required=false) String id) {
		CstPartsWeightAmount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsWeightAmountService.get(id);
		}
		if (entity == null){
			entity = new CstPartsWeightAmount();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsWeightAmount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsWeightAmount cstPartsWeightAmount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsWeightAmount> page = cstPartsWeightAmountService.findPage(new Page<CstPartsWeightAmount>(request, response), cstPartsWeightAmount); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsWeightAmountList";
	}

	@RequiresPermissions("cst:parts:cstPartsWeightAmount:view")
	@RequestMapping(value = "form")
	public String form(CstPartsWeightAmount cstPartsWeightAmount, Model model) {
		model.addAttribute("cstPartsWeightAmount", cstPartsWeightAmount);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsWeightAmountForm";
	}

	@RequiresPermissions("cst:parts:cstPartsWeightAmount:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsWeightAmount cstPartsWeightAmount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsWeightAmount)){
			return form(cstPartsWeightAmount, model);
		}
		cstPartsWeightAmountService.save(cstPartsWeightAmount);
		addMessage(redirectAttributes, "保存备件加权平均在保量定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_PARTS_WEIGHT_AMOUNT_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsWeightAmount/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsWeightAmount:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsWeightAmount cstPartsWeightAmount, RedirectAttributes redirectAttributes) {
		cstPartsWeightAmountService.delete(cstPartsWeightAmount);
		addMessage(redirectAttributes, "删除备件加权平均在保量定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_PARTS_WEIGHT_AMOUNT_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsWeightAmount/?repage";
	}

}