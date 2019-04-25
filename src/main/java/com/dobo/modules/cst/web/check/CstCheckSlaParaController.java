/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.check;

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
import com.dobo.modules.cst.entity.check.CstCheckSlaPara;
import com.dobo.modules.cst.service.check.CstCheckSlaParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 巡检级别配比定义Controller
 * @author admin
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/check/cstCheckSlaPara")
public class CstCheckSlaParaController extends BaseController {

	@Autowired
	private CstCheckSlaParaService cstCheckSlaParaService;
	
	@ModelAttribute
	public CstCheckSlaPara get(@RequestParam(required=false) String id) {
		CstCheckSlaPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstCheckSlaParaService.get(id);
		}
		if (entity == null){
			entity = new CstCheckSlaPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:check:cstCheckSlaPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstCheckSlaPara cstCheckSlaPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstCheckSlaPara> page = cstCheckSlaParaService.findPage(new Page<CstCheckSlaPara>(request, response), cstCheckSlaPara); 
		model.addAttribute("page", page);
		return "modules/cst/check/cstCheckSlaParaList";
	}

	@RequiresPermissions("cst:check:cstCheckSlaPara:view")
	@RequestMapping(value = "form")
	public String form(CstCheckSlaPara cstCheckSlaPara, Model model) {
		model.addAttribute("cstCheckSlaPara", cstCheckSlaPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/check/cstCheckSlaParaForm";
	}

	@RequiresPermissions("cst:check:cstCheckSlaPara:edit")
	@RequestMapping(value = "save")
	public String save(CstCheckSlaPara cstCheckSlaPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstCheckSlaPara)){
			return form(cstCheckSlaPara, model);
		}
		cstCheckSlaParaService.save(cstCheckSlaPara);
		addMessage(redirectAttributes, "保存巡检级别配比定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_CHECK_SLA_PARA_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckSlaPara/?repage";
	}
	
	@RequiresPermissions("cst:check:cstCheckSlaPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstCheckSlaPara cstCheckSlaPara, RedirectAttributes redirectAttributes) {
		cstCheckSlaParaService.delete(cstCheckSlaPara);
		addMessage(redirectAttributes, "删除巡检级别配比定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_CHECK_SLA_PARA_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/check/cstCheckSlaPara/?repage";
	}

}