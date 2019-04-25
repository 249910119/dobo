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
import com.dobo.modules.cst.entity.man.CstManFailureManRate;
import com.dobo.modules.cst.service.man.CstManFailureManRateService;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 故障人工费率定义表Controller
 * @author admin
 * @version 2016-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManFailureManRate")
public class CstManFailureManRateController extends BaseController {

	@Autowired
	private CstManFailureManRateService cstManFailureManRateService;
	
	@ModelAttribute
	public CstManFailureManRate get(@RequestParam(required=false) String id) {
		CstManFailureManRate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManFailureManRateService.get(id);
		}
		if (entity == null){
			entity = new CstManFailureManRate();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManFailureManRate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManFailureManRate cstManFailureManRate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManFailureManRate> page = cstManFailureManRateService.findPage(new Page<CstManFailureManRate>(request, response), cstManFailureManRate); 
		model.addAttribute("page", page);
		return "modules/cst/man/cstManFailureManRateList";
	}

	@RequiresPermissions("cst:man:cstManFailureManRate:view")
	@RequestMapping(value = "form")
	public String form(CstManFailureManRate cstManFailureManRate, Model model) {
		model.addAttribute("cstManFailureManRate", cstManFailureManRate);
		model.addAttribute("user", UserUtils.getUser());
		return "modules/cst/man/cstManFailureManRateForm";
	}

	@RequiresPermissions("cst:man:cstManFailureManRate:edit")
	@RequestMapping(value = "save")
	public String save(CstManFailureManRate cstManFailureManRate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManFailureManRate)){
			return form(cstManFailureManRate, model);
		}
		cstManFailureManRateService.save(cstManFailureManRate);
		addMessage(redirectAttributes, "保存故障人工费率定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_MAN_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureManRate/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManFailureManRate:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManFailureManRate cstManFailureManRate, RedirectAttributes redirectAttributes) {
		cstManFailureManRateService.delete(cstManFailureManRate);
		addMessage(redirectAttributes, "删除故障人工费率定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_MAN_RATE);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureManRate/?repage";
	}

}