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
import com.dobo.modules.cst.entity.parts.CstPartsEventFailurePara;
import com.dobo.modules.cst.service.parts.CstPartsEventFailureParaService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件事件故障参数定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsEventFailurePara")
public class CstPartsEventFailureParaController extends BaseController {

	@Autowired
	private CstPartsEventFailureParaService cstPartsEventFailureParaService;
	
	@ModelAttribute
	public CstPartsEventFailurePara get(@RequestParam(required=false) String id) {
		CstPartsEventFailurePara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsEventFailureParaService.get(id);
		}
		if (entity == null){
			entity = new CstPartsEventFailurePara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsEventFailurePara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsEventFailurePara cstPartsEventFailurePara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsEventFailurePara> page = cstPartsEventFailureParaService.findPage(new Page<CstPartsEventFailurePara>(request, response), cstPartsEventFailurePara); 
		model.addAttribute("page", page);
		return "modules/cst/parts/cstPartsEventFailureParaList";
	}

	@RequiresPermissions("cst:parts:cstPartsEventFailurePara:view")
	@RequestMapping(value = "form")
	public String form(CstPartsEventFailurePara cstPartsEventFailurePara, Model model) {
		model.addAttribute("cstPartsEventFailurePara", cstPartsEventFailurePara);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/parts/cstPartsEventFailureParaForm";
	}

	@RequiresPermissions("cst:parts:cstPartsEventFailurePara:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsEventFailurePara cstPartsEventFailurePara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsEventFailurePara)){
			return form(cstPartsEventFailurePara, model);
		}
		cstPartsEventFailureParaService.save(cstPartsEventFailurePara);
		addMessage(redirectAttributes, "保存备件事件故障参数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_PARTS_EVENT_FAILURE_PARA_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsEventFailurePara/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsEventFailurePara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsEventFailurePara cstPartsEventFailurePara, RedirectAttributes redirectAttributes) {
		cstPartsEventFailureParaService.delete(cstPartsEventFailurePara);
		addMessage(redirectAttributes, "删除备件事件故障参数定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_PARTS_EVENT_FAILURE_PARA_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsEventFailurePara/?repage";
	}

}