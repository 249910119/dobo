/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.temp;

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
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.entity.temp.CstManNotDevicePara;
import com.dobo.modules.cst.service.temp.CstManNotDeviceParaService;

/**
 * 非设备类系数表Controller
 * @author admin
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/temp/cstManNotDevicePara")
public class CstManNotDeviceParaController extends BaseController {

	@Autowired
	private CstManNotDeviceParaService cstManNotDeviceParaService;
	
	@ModelAttribute
	public CstManNotDevicePara get(@RequestParam(required=false) String id) {
		CstManNotDevicePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManNotDeviceParaService.get(id);
		}
		if (entity == null){
			entity = new CstManNotDevicePara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:temp:cstManNotDevicePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManNotDevicePara cstManNotDevicePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManNotDevicePara> page = cstManNotDeviceParaService.findPage(new Page<CstManNotDevicePara>(request, response), cstManNotDevicePara); 
		model.addAttribute("page", page);
		return "modules/cst/temp/cstManNotDeviceParaList";
	}

	@RequiresPermissions("cst:temp:cstManNotDevicePara:view")
	@RequestMapping(value = "form")
	public String form(CstManNotDevicePara cstManNotDevicePara, Model model) {
		model.addAttribute("cstManNotDevicePara", cstManNotDevicePara);
		return "modules/cst/temp/cstManNotDeviceParaForm";
	}

	@RequiresPermissions("cst:temp:cstManNotDevicePara:edit")
	@RequestMapping(value = "save")
	public String save(CstManNotDevicePara cstManNotDevicePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManNotDevicePara)){
			return form(cstManNotDevicePara, model);
		}
		cstManNotDeviceParaService.save(cstManNotDevicePara);
		addMessage(redirectAttributes, "保存非设备类系数表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/temp/cstManNotDevicePara/?repage";
	}
	
	@RequiresPermissions("cst:temp:cstManNotDevicePara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManNotDevicePara cstManNotDevicePara, RedirectAttributes redirectAttributes) {
		cstManNotDeviceParaService.delete(cstManNotDevicePara);
		addMessage(redirectAttributes, "删除非设备类系数表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/temp/cstManNotDevicePara/?repage";
	}

}