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
import com.dobo.modules.cst.entity.model.MeasureDef;
import com.dobo.modules.cst.service.model.MeasureDefService;

/**
 * 指标定义表
 * @author admin
 * @version 2016-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/model/measureDef")
public class MeasureDefController extends BaseController {

	@Autowired
	private MeasureDefService measureDefService;
	
	@ModelAttribute
	public MeasureDef get(@RequestParam(required=false) String id) {
		MeasureDef entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = measureDefService.get(id);
		}
		if (entity == null){
			entity = new MeasureDef();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:model:measureDef:view")
	@RequestMapping(value = {"list", ""})
	public String list(MeasureDef measureDef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MeasureDef> page = measureDefService.findPage(new Page<MeasureDef>(request, response), measureDef); 
		model.addAttribute("page", page);
		return "modules/cst/model/measureDefList";
	}

	@RequiresPermissions("cst:model:measureDef:view")
	@RequestMapping(value = "form")
	public String form(MeasureDef measureDef, Model model) {
		model.addAttribute("costType", measureDef);
		return "modules/cst/model/measureDefForm";
	}

	@RequiresPermissions("cst:model:measureDef:edit")
	@RequestMapping(value = "save")
	public String save(MeasureDef measureDef, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, measureDef)){
			return form(measureDef, model);
		}
		measureDefService.save(measureDef);
		addMessage(redirectAttributes, "保存指标定义表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/model/measureDef/?repage";
	}
	
	@RequiresPermissions("cst:model:measureDef:edit")
	@RequestMapping(value = "delete")
	public String delete(MeasureDef measureDef, RedirectAttributes redirectAttributes) {
		measureDefService.delete(measureDef);
		addMessage(redirectAttributes, "删除指标定义表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/model/measureDef/?repage";
	}

	/**
	 * 指标定义表弹出选择框数据
	 * @param measureDef
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectList")
	public String selectList(MeasureDef measureDef, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(measureDef, request, response, model);
		return "modules/cst/model/measureDefSelectList";
	}
}