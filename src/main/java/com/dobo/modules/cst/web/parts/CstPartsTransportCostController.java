/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.parts;

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
import com.dobo.modules.cst.entity.parts.CstPartsTransportCost;
import com.dobo.modules.cst.service.parts.CstPartsTransportCostService;
import com.dobo.modules.sys.entity.Dict;
import com.dobo.modules.sys.utils.DictUtils;
import com.dobo.modules.sys.utils.UserUtils;

/**
 * 备件发货运输平均成本定义Controller
 * @author admin
 * @version 2016-11-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/parts/cstPartsTransportCost")
public class CstPartsTransportCostController extends BaseController {

	@Autowired
	private CstPartsTransportCostService cstPartsTransportCostService;
	
	@ModelAttribute
	public CstPartsTransportCost get(@RequestParam(required=false) String id) {
		CstPartsTransportCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstPartsTransportCostService.get(id);
		}
		if (entity == null){
			entity = new CstPartsTransportCost();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:parts:cstPartsTransportCost:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstPartsTransportCost cstPartsTransportCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstPartsTransportCost> page = cstPartsTransportCostService.findPage(new Page<CstPartsTransportCost>(request, response), cstPartsTransportCost); 
		model.addAttribute("page", page);
		model.addAttribute("slaList", getSlaList());
		return "modules/cst/parts/cstPartsTransportCostList";
	}

	@RequiresPermissions("cst:parts:cstPartsTransportCost:view")
	@RequestMapping(value = "form")
	public String form(CstPartsTransportCost cstPartsTransportCost, Model model) {
		model.addAttribute("cstPartsTransportCost", cstPartsTransportCost);
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("slaList", getSlaList());
		return "modules/cst/parts/cstPartsTransportCostForm";
	}

	@RequiresPermissions("cst:parts:cstPartsTransportCost:edit")
	@RequestMapping(value = "save")
	public String save(CstPartsTransportCost cstPartsTransportCost, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstPartsTransportCost)){
			return form(cstPartsTransportCost, model);
		}
		cstPartsTransportCostService.save(cstPartsTransportCost);
		addMessage(redirectAttributes, "保存备件发货运输平均成本定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_TRANSPORT_COST);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsTransportCost/?repage";
	}
	
	@RequiresPermissions("cst:parts:cstPartsTransportCost:edit")
	@RequestMapping(value = "delete")
	public String delete(CstPartsTransportCost cstPartsTransportCost, RedirectAttributes redirectAttributes) {
		cstPartsTransportCostService.delete(cstPartsTransportCost);
		addMessage(redirectAttributes, "删除备件发货运输平均成本定义成功");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_TRANSPORT_COST);
		return "redirect:"+Global.getAdminPath()+"/cst/parts/cstPartsTransportCost/?repage";
	}

	public List<Dict> getSlaList(){
		List<Dict> slaList = DictUtils.getDictList("SLA_DEVICE");
		slaList.addAll(DictUtils.getDictList("SLA_SOFT"));
		return slaList;
	}
}