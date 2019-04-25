/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.soft;

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
import com.dobo.modules.cst.entity.soft.CstSoftPackDegreePara;
import com.dobo.modules.cst.service.soft.CstSoftPackDegreeParaService;

/**
 * 系统软件服务资源配比表Controller
 * @author admin
 * @version 2017-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/soft/cstSoftPackDegreePara")
public class CstSoftPackDegreeParaController extends BaseController {

	@Autowired
	private CstSoftPackDegreeParaService cstSoftPackDegreeParaService;
	
	@ModelAttribute
	public CstSoftPackDegreePara get(@RequestParam(required=false) String id) {
		CstSoftPackDegreePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstSoftPackDegreeParaService.get(id);
		}
		if (entity == null){
			entity = new CstSoftPackDegreePara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:soft:cstSoftPackDegreePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstSoftPackDegreePara cstSoftPackDegreePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstSoftPackDegreePara> page = cstSoftPackDegreeParaService.findPage(new Page<CstSoftPackDegreePara>(request, response), cstSoftPackDegreePara); 
		model.addAttribute("page", page);
		return "modules/cst/soft/cstSoftPackDegreeParaList";
	}

	@RequiresPermissions("cst:soft:cstSoftPackDegreePara:view")
	@RequestMapping(value = "form")
	public String form(CstSoftPackDegreePara cstSoftPackDegreePara, Model model) {
		model.addAttribute("cstSoftPackDegreePara", cstSoftPackDegreePara);
		return "modules/cst/soft/cstSoftPackDegreeParaForm";
	}

	@RequiresPermissions("cst:soft:cstSoftPackDegreePara:edit")
	@RequestMapping(value = "save")
	public String save(CstSoftPackDegreePara cstSoftPackDegreePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstSoftPackDegreePara)){
			return form(cstSoftPackDegreePara, model);
		}
		cstSoftPackDegreeParaService.save(cstSoftPackDegreePara);
		addMessage(redirectAttributes, "保存系统软件服务资源配比表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/soft/cstSoftPackDegreePara/?repage";
	}
	
	@RequiresPermissions("cst:soft:cstSoftPackDegreePara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstSoftPackDegreePara cstSoftPackDegreePara, RedirectAttributes redirectAttributes) {
		cstSoftPackDegreeParaService.delete(cstSoftPackDegreePara);
		addMessage(redirectAttributes, "删除系统软件服务资源配比表成功");
		return "redirect:"+Global.getAdminPath()+"/cst/soft/cstSoftPackDegreePara/?repage";
	}

}