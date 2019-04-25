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
import com.dobo.modules.cst.entity.man.CstManStandbyPara;
import com.dobo.modules.cst.service.man.CstManStandbyParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 非工作时间比重定义表Controller
 * @author admin
 * @version 2016-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManStandbyPara")
public class CstManStandbyParaController extends BaseController {

	@Autowired
	private CstManStandbyParaService cstManStandbyParaService;
	
	@ModelAttribute
	public CstManStandbyPara get(@RequestParam(required=false) String id) {
		CstManStandbyPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManStandbyParaService.get(id);
		}
		if (entity == null){
			entity = new CstManStandbyPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManStandbyPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManStandbyPara cstManStandbyPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManStandbyPara> page = cstManStandbyParaService.findPage(new Page<CstManStandbyPara>(request, response), cstManStandbyPara); 
		model.addAttribute("page", page);
		return "modules/cst/man/cstManStandbyParaList";
	}

	@RequiresPermissions("cst:man:cstManStandbyPara:view")
	@RequestMapping(value = "form")
	public String form(CstManStandbyPara cstManStandbyPara, Model model) {
		model.addAttribute("cstManStandbyPara", cstManStandbyPara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/man/cstManStandbyParaForm";
	}

	@RequiresPermissions("cst:man:cstManStandbyPara:edit")
	@RequestMapping(value = "save")
	public String save(CstManStandbyPara cstManStandbyPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManStandbyPara)){
			return form(cstManStandbyPara, model);
		}
		cstManStandbyParaService.save(cstManStandbyPara);
		addMessage(redirectAttributes, "保存非工作时间比重定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_STANDBY_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManStandbyPara/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManStandbyPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManStandbyPara cstManStandbyPara, RedirectAttributes redirectAttributes) {
		cstManStandbyParaService.delete(cstManStandbyPara);
		addMessage(redirectAttributes, "删除非工作时间比重定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_STANDBY_PARA);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManStandbyPara/?repage";
	}

}