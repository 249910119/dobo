package com.dobo.modules.cst.web.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobo.common.persistence.Page;
import com.dobo.common.utils.StringUtils;
import com.dobo.common.web.BaseController;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.service.base.CstResourceBaseInfoService;

@Controller
@RequestMapping(value = "${adminPath}/cst/base/cstResourceBaseInfo")
public class CstResourceBaseInfoController extends BaseController {

	@Autowired
	private CstResourceBaseInfoService cstResourceBaseInfoService;
	
	@ModelAttribute
	public CstResourceBaseInfo get(@RequestParam(required=false) String id) {
		CstResourceBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstResourceBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new CstResourceBaseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("cst:base:cstResourceBaseInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CstResourceBaseInfo cstResourceBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstResourceBaseInfo> page = cstResourceBaseInfoService.findPage(new Page<CstResourceBaseInfo>(request, response), cstResourceBaseInfo); 
		model.addAttribute("page", page);
		return "modules/cst/base/cstResourceBaseInfoList";
	}

	@RequiresPermissions("cst:base:cstResourceBaseInfo:view")
	@RequestMapping(value = "form")
	public String form(CstResourceBaseInfo cstResourceBaseInfo, Model model) {
		model.addAttribute("cstResourceBaseInfo", cstResourceBaseInfo);
		return "modules/cst/base/cstResourceBaseInfoForm";
	}

	@RequestMapping(value = "selectList")
	public String resourceBaseInfolist(CstResourceBaseInfo cstResourceBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model){
		list(cstResourceBaseInfo, request, response, model);
		return "modules/cst/base/resourceBaseSelectList";
	}
}
