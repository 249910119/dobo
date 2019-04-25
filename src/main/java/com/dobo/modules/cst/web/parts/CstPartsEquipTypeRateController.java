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
import com.dobo.modules.cst.entity.parts.CstPartsEquipTypeRate;
import com.dobo.modules.cst.service.parts.CstPartsEquipTypeRateService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * cst_parts_equip_type_rateController
 * @author admin
 * @version 2017-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsEquipTypeRate")
public class CstPartsEquipTypeRateController extends BaseController {

	@Autowired
	private CstPartsEquipTypeRateService cstPartsEquipTypeRateService;
	
	@ModelAttribute
	public CstPartsEquipTypeRate get(@RequestParam(required=false) String id) {
		CstPartsEquipTypeRate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsEquipTypeRateService.get(id);
		}
		if (entity == null){
			entity = new CstPartsEquipTypeRate();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsEquipTypeRate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsEquipTypeRate cstPartsEquipTypeRate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsEquipTypeRate> page = cstPartsEquipTypeRateService.findPage(new Page<CstPartsEquipTypeRate>(request, response), cstPartsEquipTypeRate); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsEquipTypeRateList";
	}

	@RequiresPermissions("cst:parts:cstPartsEquipTypeRate:view")
	@RequestMapping(value = "form")
	public String form(CstPartsEquipTypeRate cstPartsEquipTypeRate, Model model) {
		model.addAttribute("cstPartsEquipTypeRate", cstPartsEquipTypeRate);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsEquipTypeRateForm";
	}

	@RequiresPermissions("cst:parts:cstPartsEquipTypeRate:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsEquipTypeRate cstPartsEquipTypeRate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsEquipTypeRate)){
			return form(cstPartsEquipTypeRate, model);
		}
		cstPartsEquipTypeRateService.save(cstPartsEquipTypeRate);
		addMessage(redirectAttributes, "保存cst_parts_equip_type_rate成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_EQUIPTYPE_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsEquipTypeRate/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsEquipTypeRate:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsEquipTypeRate cstPartsEquipTypeRate, RedirectAttributes redirectAttributes) {
		cstPartsEquipTypeRateService.delete(cstPartsEquipTypeRate);
		addMessage(redirectAttributes, "删除cst_parts_equip_type_rate成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_EQUIPTYPE_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsEquipTypeRate/?repage";
	}

}