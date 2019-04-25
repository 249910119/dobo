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
import com.dobo.modules.cst.entity.parts.CstPartsPrjStorePara;
import com.dobo.modules.cst.service.parts.CstPartsPrjStoreParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件项目储备成本系数定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsPrjStorePara")
public class CstPartsPrjStoreParaController extends BaseController {

	@Autowired
	private CstPartsPrjStoreParaService cstPartsPrjStoreParaService;
	
	@ModelAttribute
	public CstPartsPrjStorePara get(@RequestParam(required=false) String id) {
		CstPartsPrjStorePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsPrjStoreParaService.get(id);
		}
		if (entity == null){
			entity = new CstPartsPrjStorePara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsPrjStorePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsPrjStorePara cstPartsPrjStorePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsPrjStorePara> page = cstPartsPrjStoreParaService.findPage(new Page<CstPartsPrjStorePara>(request, response), cstPartsPrjStorePara); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsPrjStoreParaList";
	}

	@RequiresPermissions("cst:parts:cstPartsPrjStorePara:view")
	@RequestMapping(value = "form")
	public String form(CstPartsPrjStorePara cstPartsPrjStorePara, Model model) {
		model.addAttribute("cstPartsPrjStorePara", cstPartsPrjStorePara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsPrjStoreParaForm";
	}

	@RequiresPermissions("cst:parts:cstPartsPrjStorePara:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsPrjStorePara cstPartsPrjStorePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsPrjStorePara)){
			return form(cstPartsPrjStorePara, model);
		}
		cstPartsPrjStoreParaService.save(cstPartsPrjStorePara);
		addMessage(redirectAttributes, "保存备件项目储备成本系数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_PRJ_STORE_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsPrjStorePara/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsPrjStorePara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsPrjStorePara cstPartsPrjStorePara, RedirectAttributes redirectAttributes) {
		cstPartsPrjStoreParaService.delete(cstPartsPrjStorePara);
		addMessage(redirectAttributes, "删除备件项目储备成本系数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_PRJ_STORE_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsPrjStorePara/?repage";
	}

}