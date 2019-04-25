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
import com.dobo.modules.cst.entity.man.CstManProdPara;
import com.dobo.modules.cst.service.man.CstManProdParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 服务产品系数定义表Controller
 * @author admin
 * @version 2016-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManProdPara")
public class CstManProdParaController extends BaseController {

	@Autowired
	private CstManProdParaService cstManProdParaService;
	
	@ModelAttribute
	public CstManProdPara get(@RequestParam(required=false) String id) {
		CstManProdPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManProdParaService.get(id);
		}
		if (entity == null){
			entity = new CstManProdPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManProdPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManProdPara cstManProdPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManProdPara> page = cstManProdParaService.findPage(new Page<CstManProdPara>(request, response), cstManProdPara); 
		model.addAttribute("page", page);
		return "modules/cst/man/cstManProdParaList";
	}

	@RequiresPermissions("cst:man:cstManProdPara:view")
	@RequestMapping(value = "form")
	public String form(CstManProdPara cstManProdPara, Model model) {
		model.addAttribute("cstManProdPara", cstManProdPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/man/cstManProdParaForm";
	}

	@RequiresPermissions("cst:man:cstManProdPara:edit")
	@RequestMapping(value = "save")
	public String save(CstManProdPara cstManProdPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManProdPara)){
			return form(cstManProdPara, model);
		}
		cstManProdParaService.save(cstManProdPara);
		addMessage(redirectAttributes, "保存服务产品系数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_PROD_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManProdPara/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManProdPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManProdPara cstManProdPara, RedirectAttributes redirectAttributes) {
		cstManProdParaService.delete(cstManProdPara);
		addMessage(redirectAttributes, "删除服务产品系数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_PROD_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManProdPara/?repage";
	}

}