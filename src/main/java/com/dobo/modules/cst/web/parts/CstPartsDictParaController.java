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
import com.dobo.modules.cst.entity.parts.CstPartsDictPara;
import com.dobo.modules.cst.service.parts.CstPartsDictParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件参数字典定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsDictPara")
public class CstPartsDictParaController extends BaseController {

	@Autowired
	private CstPartsDictParaService cstPartsDictParaService;
	
	@ModelAttribute
	public CstPartsDictPara get(@RequestParam(required=false) String id) {
		CstPartsDictPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsDictParaService.get(id);
		}
		if (entity == null){
			entity = new CstPartsDictPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsDictPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsDictPara cstPartsDictPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsDictPara> page = cstPartsDictParaService.findPage(new Page<CstPartsDictPara>(request, response), cstPartsDictPara); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsDictParaList";
	}

	@RequiresPermissions("cst:parts:cstPartsDictPara:view")
	@RequestMapping(value = "form")
	public String form(CstPartsDictPara cstPartsDictPara, Model model) {
		model.addAttribute("cstPartsDictPara", cstPartsDictPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsDictParaForm";
	}

	@RequiresPermissions("cst:parts:cstPartsDictPara:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsDictPara cstPartsDictPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsDictPara)){
			return form(cstPartsDictPara, model);
		}
		cstPartsDictParaService.save(cstPartsDictPara);
		addMessage(redirectAttributes, "保存备件参数字典定义成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsDictPara/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsDictPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsDictPara cstPartsDictPara, RedirectAttributes redirectAttributes) {
		cstPartsDictParaService.delete(cstPartsDictPara);
		addMessage(redirectAttributes, "删除备件参数字典定义成功");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsDictPara/?repage";
	}

}