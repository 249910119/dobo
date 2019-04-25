/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.web.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobo.common.config.Global;
import com.dobo.common.persistence.Page;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.StringUtils;
import com.dobo.common.utils.csv.CsvHelper;
import com.dobo.common.utils.csv.OpenCsvHelper;
import com.dobo.common.web.BaseController;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.base.CstDictPara;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.service.base.CstBaseCaseLineoneService;
import com.dobo.modules.cst.service.base.CstBaseResourceCaseService;
import com.dobo.modules.cst.service.base.CstDictParaService;

/**
 * 成本模型字典Controller
 * @author admin
 * @version 2016-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cst/cache/cstParaCache")
public class CstParaCacheController extends BaseController {

	@Autowired
	private CstDictParaService cstDictParaService;
	@Autowired
	private CstBaseResourceCaseService cstBaseResourceCaseService;
	@Autowired
	private CstBaseCaseLineoneService cstBaseCaseLineoneService;
	
	//成本模型计算结果文件目录
	public static String cst_filedir = Global.getUserfilesBaseDir() + File.separator + "cst";
	
	@ModelAttribute
	public CstDictPara get(@RequestParam(required=false) String id) {
		CstDictPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cstDictParaService.get(id);
		}
		if (entity == null){
			entity = new CstDictPara();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(CstDictPara cstDictPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CstDictPara> page = cstDictParaService.findPage(new Page<CstDictPara>(request, response), cstDictPara); 
		model.addAttribute("page", page);
		return "modules/cst/cache/cstParaCacheList";
	}
	
	 /**
    * 数据库读取后计算成本模型-多线程
    * 
    * @return
    * @throws Exception
    */
	@RequestMapping(value = "cstParaCacheFresh")
	public String cstParaCacheFresh(HttpServletRequest request) throws Exception {
		
		CacheDataUtils.freshModuleCfgParaCache();
		CacheDataUtils.freshCstCfgParaCache();
		
		return "modules/cst/cache/cstParaCacheList";
	}

	 /**
	   * 根据故障case样本数据计算导出主数据的故障率
	   * 
	   * @return
	   * @throws Exception
	   */
    @RequestMapping(value = "calcBaseCasePara")
	public String calcBaseCasePara() throws Exception {
    	
    	Map<String, CstResourceBaseInfo> cstResourceBaseInfoByIdMap = CacheDataUtils.getCstResourceBaseInfoMap();

		List<CstResourceBaseInfo> cstResourceBaseInfoList = new ArrayList<CstResourceBaseInfo>();
		for(CstResourceBaseInfo crbi : cstResourceBaseInfoByIdMap.values()) {
    		if(!"A0".equals(crbi.getResourceClass())) continue;
    		if(!"A0".equals(crbi.getStatus())) continue;
    		
    		cstBaseResourceCaseService.calcBaseCasePara(crbi);
    		cstResourceBaseInfoList.add(crbi);
		}
		
		String[] fieldNames = CstResourceBaseInfo.exportfieldNames;
		String[] titles = CstResourceBaseInfo.exportfieldTitles;
		
		String fileFullName = cst_filedir + File.separator +"主数据故障率参数_"+DateUtils.getDate()+".csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(CstResourceBaseInfo.class, cstResourceBaseInfoList, fieldNames, titles, 1048575);//最大行1048576
    	
		return "modules/cst/cache/cstParaCacheList";
    }

	 /**
	   * 根据故障配比明细数据计算导出主数据的故障一级个级别配比
	   * 
	   * @return
	   * @throws Exception
	   */
   @RequestMapping(value = "calcCaseLineOnePara")
	public String calcCaseLineOnePara() throws Exception {
   	
   	Map<String, CstResourceBaseInfo> cstResourceBaseInfoByIdMap = CacheDataUtils.getCstResourceBaseInfoMap();

		List<CstResourceBaseInfo> cstResourceBaseInfoList = new ArrayList<CstResourceBaseInfo>();
		for(CstResourceBaseInfo crbi : cstResourceBaseInfoByIdMap.values()) {
   		if(!"A0".equals(crbi.getResourceClass())) continue;
   		if(!"A0".equals(crbi.getStatus())) continue;
   		
   		List<CstResourceBaseInfo> crbiList = cstBaseCaseLineoneService.calcCaseLineOnePara(crbi);
   		cstResourceBaseInfoList.addAll(crbiList);
		}
		
		String[] fieldNames = CstResourceBaseInfo.exportfieldNames;
		String[] titles = CstResourceBaseInfo.exportfieldTitles;
		
		String fileFullName = cst_filedir + File.separator +"主数据故障一线配比参数_"+DateUtils.getDate()+".csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(CstResourceBaseInfo.class, cstResourceBaseInfoList, fieldNames, titles, 1048575);//最大行1048576
   	
		return "modules/cst/cache/cstParaCacheList";
   }

}