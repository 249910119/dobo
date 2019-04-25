/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.model;

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
import com.dobo.modules.cst.common.Constants;
import com.dobo.modules.cst.entity.model.CostType;
import com.dobo.modules.cst.entity.model.CstModelModuleInfo;
import com.dobo.modules.cst.service.model.CostTypeService;

/**
 * 成本类型定义
 * @author admin
 * @version 2016-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/model/costType")
public class CostTypeController extends BaseController {

	@Autowired
	private CostTypeService costTypeService;
	
	@ModelAttribute
	public CostType get(@RequestParam(required=false) String id) {
		CostType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = costTypeService.get(id);
		}
		if (entity == null){
			entity = new CostType();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:model:costType:view")
	@RequestMapping(value = {"list", ""})
	public String list(CostType costType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CostType> page = costTypeService.findPage(new Page<CostType>(request, response), costType); 
		model.addAttribute("page", page);
		return "modules/cst/model/costTypeList";
	}

	@RequiresPermissions("cst:model:costType:view")
	@RequestMapping(value = "form")
	public String form(CostType costType, Model model) {
		model.addAttribute("costType", costType);
		return "modules/cst/model/costTypeForm";
	}

	@RequiresPermissions("cst:model:costType:edit")
	@RequestMapping(value = "save")
	public String save(CostType costType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, costType)){
			return form(costType, model);
		}
		costTypeService.save(costType);
		addMessage(redirectAttributes, "保存成本类型定义表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/model/costType/?repage";
	}
	
	@RequiresPermissions("cst:model:costType:edit")
	@RequestMapping(value = "delete")
	public String delete(CostType costType, RedirectAttributes redirectAttributes) {
		costTypeService.delete(costType);
		addMessage(redirectAttributes, "删除成本类型定义表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/model/costType/?repage";
	}

	/**
	 * 成本类型定义表弹出选择框数据
	 * @param cstModelModuleInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectList")
	public String selectList(CostType costType, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(costType, request, response, model);
		return "modules/cst/model/costTypeSelectList";
	}
}