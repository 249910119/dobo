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
import com.dobo.modules.cst.entity.man.CstManFailureDegree;
import com.dobo.modules.cst.service.man.CstManFailureDegreeService;
import com.dobo.modules.sys.entity.Dict;
import com.dobo.modules.sys.utils.DictUtils;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 故障饱和度定义表Controller
 * @author admin
 * @version 2016-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/man/cstManFailureDegree")
public class CstManFailureDegreeController extends BaseController {

	@Autowired
	private CstManFailureDegreeService cstManFailureDegreeService;
	
	@ModelAttribute
	public CstManFailureDegree get(@RequestParam(required=false) String id) {
		CstManFailureDegree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstManFailureDegreeService.get(id);
		}
		if (entity == null){
			entity = new CstManFailureDegree();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:man:cstManFailureDegree:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstManFailureDegree cstManFailureDegree, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstManFailureDegree> page = cstManFailureDegreeService.findPage(new Page<CstManFailureDegree>(request, response), cstManFailureDegree); 
		model.addAttribute("page", page);
		model.addAttribute("slaList", getSlaList());
		return "modules/cst/man/cstManFailureDegreeList";
	}

	@RequiresPermissions("cst:man:cstManFailureDegree:view")
	@RequestMapping(value = "form")
	public String form(CstManFailureDegree cstManFailureDegree, Model model) {
		model.addAttribute("cstManFailureDegree", cstManFailureDegree);
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("slaList", getSlaList());
		return "modules/cst/man/cstManFailureDegreeForm";
	}

	@RequiresPermissions("cst:man:cstManFailureDegree:edit")
	@RequestMapping(value = "save")
	public String save(CstManFailureDegree cstManFailureDegree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstManFailureDegree)){
			return form(cstManFailureDegree, model);
		}
		cstManFailureDegreeService.save(cstManFailureDegree);
		addMessage(redirectAttributes, "保存故障饱和度定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_DEGREE);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureDegree/?repage";
	}
	
	@RequiresPermissions("cst:man:cstManFailureDegree:edit")
	@RequestMapping(value = "delete")
	public String delete(CstManFailureDegree cstManFailureDegree, RedirectAttributes redirectAttributes) {
		cstManFailureDegreeService.delete(cstManFailureDegree);
		addMessage(redirectAttributes, "删除故障饱和度定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_DEGREE);
		return "redirect:"+Global.getAdminPath()+"/cst/man/cstManFailureDegree/?repage";
	}

	public List<Dict> getSlaList(){
		List<Dict> slaList = DictUtils.getDictList("SLA_DEVICE");
		slaList.addAll(DictUtils.getDictList("SLA_SOFT"));
		return slaList;
	}
}