/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.detail;

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
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;
import com.dobo.modules.cst.service.detail.CstOrderDetailInfoService;

/**
 * 订单明细信息Controller
 * @author admin
 * @version 2016-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/detail/cstOrderDetailInfo")
public class CstOrderDetailInfoController extends BaseController {

	@Autowired
	private CstOrderDetailInfoService cstOrderDetailInfoService;
	
	@ModelAttribute
	public CstOrderDetailInfo get(@RequestParam(required=false) String id) {
		CstOrderDetailInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstOrderDetailInfoService.get(id);
		}
		if (entity == null){
			entity = new CstOrderDetailInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:detail:cstOrderDetailInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstOrderDetailInfo cstOrderDetailInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstOrderDetailInfo> page = cstOrderDetailInfoService.findPage(new Page<CstOrderDetailInfo>(request, response), cstOrderDetailInfo); 
		model.addAttribute("page", page);
		return "modules/cst/detail/cstOrderDetailInfoList";
	}

	@RequiresPermissions("cst:detail:cstOrderDetailInfo:view")
	@RequestMapping(value = "form")
	public String form(CstOrderDetailInfo cstOrderDetailInfo, Model model) {
		model.addAttribute("cstOrderDetailInfo", cstOrderDetailInfo);
		return "modules/cst/detail/cstOrderDetailInfoForm";
	}

	@RequiresPermissions("cst:detail:cstOrderDetailInfo:edit")
	@RequestMapping(value = "save")
	public String save(CstOrderDetailInfo cstOrderDetailInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cstOrderDetailInfo)){
			return form(cstOrderDetailInfo, model);
		}
		cstOrderDetailInfoService.save(cstOrderDetailInfo);
		addMessage(redirectAttributes, "保存订单明细信息成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstOrderDetailInfo/?repage";
	}
	
	@RequiresPermissions("cst:detail:cstOrderDetailInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CstOrderDetailInfo cstOrderDetailInfo, RedirectAttributes redirectAttributes) {
		cstOrderDetailInfoService.delete(cstOrderDetailInfo);
		addMessage(redirectAttributes, "删除订单明细信息成功");
		return "redirect:"+Global.getAdminPath()+"/cst/detail/cstOrderDetailInfo/?repage";
	}

}