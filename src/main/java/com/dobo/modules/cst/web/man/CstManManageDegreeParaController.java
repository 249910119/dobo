/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.man;

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
import com.dobo.modules.cst.entity.man.CstManManageDegreePara;
import com.dobo.modules.cst.service.man.CstManManageDegreeParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 项目管理配比及饱和度表Controller
 * @author admin
 * @version 2016-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManManageDegreePara")
public class CstManManageDegreeParaController extends BaseController {

	@Autowired
	private CstManManageDegreeParaService cstManManageDegreeParaService;
	
	@ModelAttribute
	public CstManManageDegreePara get(@RequestParam(required=false) String id) {
		CstManManageDegreePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManManageDegreeParaService.get(id);
		}
		if (entity == null){
			entity = new CstManManageDegreePara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManManageDegreePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManManageDegreePara cstManManageDegreePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManManageDegreePara> page = cstManManageDegreeParaService.findPage(new Page<CstManManageDegreePara>(request, response), cstManManageDegreePara); 
		model.addAttribute("page", page);
		return "modules/cst/man/cstManManageDegreeParaList";
	}

	@RequiresPermissions("cst:man:cstManManageDegreePara:view")
	@RequestMapping(value = "form")
	public String form(CstManManageDegreePara cstManManageDegreePara, Model model) {
		model.addAttribute("cstManManageDegreePara", cstManManageDegreePara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/man/cstManManageDegreeParaForm";
	}

	@RequiresPermissions("cst:man:cstManManageDegreePara:edit")
	@RequestMapping(value = "save")
	public String save(CstManManageDegreePara cstManManageDegreePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManManageDegreePara)){
			return form(cstManManageDegreePara, model);
		}
		cstManManageDegreeParaService.save(cstManManageDegreePara);
		addMessage(redirectAttributes, "保存项目管理配比及饱和度表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_MANAGE_DEGREE_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManManageDegreePara/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManManageDegreePara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManManageDegreePara cstManManageDegreePara, RedirectAttributes redirectAttributes) {
		cstManManageDegreeParaService.delete(cstManManageDegreePara);
		addMessage(redirectAttributes, "删除项目管理配比及饱和度表成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_MANAGE_DEGREE_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManManageDegreePara/?repage";
	}

}