/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.man;

import java.util.List;

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
import com.dobo.modules.cst.entity.man.CstManFailureSlaPara;
import com.dobo.modules.cst.service.man.CstManFailureSlaParaService;
import com.dobo.modules.sys.entity.Dict;
import com.dobo.modules.sys.utils.DictUtils;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 故障级别配比定义表Controller
 * @author admin
 * @version 2016-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManFailureSlaPara")
public class CstManFailureSlaParaController extends BaseController {

	@Autowired
	private CstManFailureSlaParaService cstManFailureSlaParaService;
	
	@ModelAttribute
	public CstManFailureSlaPara get(@RequestParam(required=false) String id) {
		CstManFailureSlaPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManFailureSlaParaService.get(id);
		}
		if (entity == null){
			entity = new CstManFailureSlaPara();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManFailureSlaPara:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManFailureSlaPara cstManFailureSlaPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManFailureSlaPara> page = cstManFailureSlaParaService.findPage(new Page<CstManFailureSlaPara>(request, response), cstManFailureSlaPara); 
		model.addAttribute("page", page);
		model.addAttribute("slaList", getSlaList());
		return "modules/cst/man/cstManFailureSlaParaList";
	}

	@RequiresPermissions("cst:man:cstManFailureSlaPara:view")
	@RequestMapping(value = "form")
	public String form(CstManFailureSlaPara cstManFailureSlaPara, Model model) {
		model.addAttribute("cstManFailureSlaPara", cstManFailureSlaPara);
		model.addAttribute("user",UserUtils.getUser());
		model.addAttribute("slaList", getSlaList());
		return "modules/cst/man/cstManFailureSlaParaForm";
	}

	@RequiresPermissions("cst:man:cstManFailureSlaPara:edit")
	@RequestMapping(value = "save")
	public String save(CstManFailureSlaPara cstManFailureSlaPara, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManFailureSlaPara)){
			return form(cstManFailureSlaPara, model);
		}
		cstManFailureSlaParaService.save(cstManFailureSlaPara);
		addMessage(redirectAttributes, "保存故障级别配比定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MAN_FAILURE_SLA_PARA_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureSlaPara/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManFailureSlaPara:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManFailureSlaPara cstManFailureSlaPara, RedirectAttributes redirectAttributes) {
		cstManFailureSlaParaService.delete(cstManFailureSlaPara);
		addMessage(redirectAttributes, "删除故障级别配比定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_MAN_FAILURE_SLA_PARA_CACHE, "dataMap");
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureSlaPara/?repage";
	}

	public List<Dict> getSlaList(){
		List<Dict> slaList = DictUtils.getDictList("SLA_DEVICE");
		slaList.addAll(DictUtils.getDictList("SLA_SOFT"));
		return slaList;
	}
}