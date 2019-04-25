package com.dobo.modules.cst.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dobo.common.service.RequestService;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.FileUtils;
import com.dobo.common.utils.Reflections;
import com.dobo.common.utils.csv.CsvHelper;
import com.dobo.common.utils.csv.OpenCsvHelper;
import com.dobo.common.utils.excel.ImportExcel;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.dobo.modules.fc.rest.entity.FcActualReceiptDetailRest;
import com.dobo.modules.fc.rest.entity.FcOrderInfoRest;
import com.dobo.modules.fc.rest.entity.FcPlanPayDetailRest;
import com.dobo.modules.fc.rest.entity.FcPlanReceiptDetailRest;
import com.dobo.modules.fc.rest.entity.FcProjectInfoRest;

@Controller
@RequestMapping(value = "${adminPath}/cst/rest/entity/calcCost")
public class calcCostController {

	@RequestMapping(value = {"list", ""})
	public String list(ProdDetailInfo prodDetailInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/cst/test/calcCostList";
	}

	@RequestMapping(value = "inPlanExcel")
	public String inExcel(@RequestParam("file") MultipartFile[] file) throws Exception {
		long start = System.currentTimeMillis();
		
		putMap();
		//ImportExcel(导入文件对象,标题行号，数据行号=标题行号+1,工作表编号)
		Map<String, List<Object>> fcObjectListMap = 	 getFcObjectListMap(new ImportExcel(file[0], 1, 0), FcProjectInfoRest.class);
		Map<String, List<Object>> fcOrderInfoListMap = 	 getFcObjectListMap(new ImportExcel(file[0], 1, 1), FcOrderInfoRest.class);
		Map<String, List<Object>> fcPlanPayListMap = 	 getFcObjectListMap(new ImportExcel(file[0], 1, 2), FcPlanPayDetailRest.class);
		Map<String, List<Object>> fcPlanReceiptListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 3), FcPlanReceiptDetailRest.class);
		
		List<String[]> jsonList = new LinkedList<String[]>();
		FcProjectInfoRest fcProjectInfoRest = new FcProjectInfoRest();
		for (Entry<String, List<Object>> entry : fcObjectListMap.entrySet()) {
			//System.out.println(entry.getKey());
			fcProjectInfoRest = (FcProjectInfoRest) entry.getValue().get(0);
			List<FcOrderInfoRest> fcOrderInfos = new LinkedList<FcOrderInfoRest>();		// 订单信息
			List<FcPlanPayDetailRest> fcPlanPayDetails = new LinkedList<FcPlanPayDetailRest>();		// 项目计划付款明细
			List<FcPlanReceiptDetailRest> fcPlanReceiptDetails = new LinkedList<FcPlanReceiptDetailRest>();		// 项目计划收款明细
			
			List<Object> fcOrderInfoList = fcOrderInfoListMap.get(fcProjectInfoRest.getProjectCode());
			if(fcOrderInfoList != null){
				for (Object fcOrderInfoRest : fcOrderInfoList) {
					fcOrderInfos.add((FcOrderInfoRest) fcOrderInfoRest);
				}
			}
			List<Object> fcPlanPayList = fcPlanPayListMap.get(fcProjectInfoRest.getProjectCode());
			if(fcPlanPayList != null){
				for (Object fcPlanPay : fcPlanPayList) {
					fcPlanPayDetails.add((FcPlanPayDetailRest) fcPlanPay);
				}
			}
			List<Object> fcPlanReceiptList = fcPlanReceiptListMap.get(fcProjectInfoRest.getProjectCode());
			if(fcPlanReceiptList != null){
				for (Object fcPlanReceipt : fcPlanReceiptList) {
					fcPlanReceiptDetails.add((FcPlanReceiptDetailRest) fcPlanReceipt);
				}
			}
			
			fcProjectInfoRest.setFcOrderInfoRestList(fcOrderInfos);
			fcProjectInfoRest.setFcPlanPayDetailRestList(fcPlanPayDetails);
			fcProjectInfoRest.setFcPlanReceiptDetailRestList(fcPlanReceiptDetails);
			JSONObject json = new JSONObject();
			json.put("data", fcProjectInfoRest);
			
			JSONObject json2 = RequestService.requestService("http://localhost/wbm/service/fc/", "calcInnerCost", json.toJSONString(), "POST");
			
			String financialCost = json2.get("data").toString();
			String status = "";
			if (json2.get("code").toString().equals("0")) {
				status = "成功";
			} else {
				status = "失败";
			}
			
			jsonList.add(new String[]{fcProjectInfoRest.getProjectCode(),fcProjectInfoRest.getProjectName(),fcProjectInfoRest.getFstSvcType(),fcProjectInfoRest.getSaleOrg(),fcProjectInfoRest.getSalesName(),financialCost,status});
		}
		
		String[] titles = new String[]{"项目编号","项目名称","产品线","事业部","销售员","财务费用","状态"};
		String fileName = FileUtils.getFileNameWithoutExtension(file[0].getOriginalFilename());
		String fileFullName = "D:\\fc\\"+fileName+"_计划内结果.csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(titles, jsonList);
		
		System.out.println(DateUtils.formatDateTime(System.currentTimeMillis()-start));
		
		return "modules/cst/test/calcCostList";
	 } 
	
	/**
	 * 成熟度外
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "outPlanExcel")
	public String outExcel(@RequestParam("file") MultipartFile[] file) throws Exception {
		long start = System.currentTimeMillis();

		//财务费用计算月份，为空则从项目信息中取
		String calcYearMonth = "201601-201612";
		
		putMap();
		//ImportExcel(导入文件对象,标题行号，数据行号=标题行号+1,工作表编号)
		Map<String, List<Object>> fcObjectListMap = 		getFcObjectListMap(new ImportExcel(file[0], 1, 0), FcProjectInfoRest.class);
		Map<String, List<Object>> fcOrderInfoListMap = 		getFcObjectListMap(new ImportExcel(file[0], 1, 1), FcOrderInfoRest.class);
		Map<String, List<Object>> fcPlanReceiptListMap = 	getFcObjectListMap(new ImportExcel(file[0], 1, 3), FcPlanReceiptDetailRest.class);
		Map<String, List<Object>> fcActualReceiptListMap = 	getFcObjectListMap(new ImportExcel(file[0], 1, 4), FcActualReceiptDetailRest.class);
		List<String[]> jsonList = new LinkedList<String[]>();
		FcProjectInfoRest fcProjectInfoRest = new FcProjectInfoRest();
		int c = 0;
		for (Entry<String, List<Object>> entry : fcObjectListMap.entrySet()) {
			
			c ++;

			fcProjectInfoRest = (FcProjectInfoRest) entry.getValue().get(0);
			
			//解析计算月份（如201601-201612）
			String calcYearMonthBegin = fcProjectInfoRest.getFinancialMonth();
			String calcYearMonthEnd = fcProjectInfoRest.getFinancialMonth();
			if(calcYearMonth != null){
				String[] calcYearMonthArray = calcYearMonth.split("-");
				if(calcYearMonthArray.length == 1){
					calcYearMonthBegin = calcYearMonthArray[0];
					calcYearMonthEnd = calcYearMonthArray[0];
				}else if(calcYearMonthArray.length == 2){
					calcYearMonthBegin = calcYearMonthArray[0];
					calcYearMonthEnd = calcYearMonthArray[1];
				}
			}

			Date calcYearMonthBeginDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthBegin,"yyyyMM"));
			Date calcYearMonthEndDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthEnd,"yyyyMM"));
			List<String> monthBw = DateUtils.getMonthBetween(calcYearMonthBeginDate, calcYearMonthEndDate);

			//根据计算月份间隔循环
			for(String yyyymm : monthBw){
				System.out.println("########执行项目数及计算月份：" + c + " , "+yyyymm);
				
				List<FcOrderInfoRest> fcOrderInfos = new LinkedList<FcOrderInfoRest>();		// 订单信息
				List<FcActualReceiptDetailRest> fcAtualReceiptDetails = new LinkedList<FcActualReceiptDetailRest>();		// 项目计划付款明细
				List<FcPlanReceiptDetailRest> fcPlanReceiptDetails = new LinkedList<FcPlanReceiptDetailRest>();		// 项目计划收款明细
				
				List<Object> fcOrderInfoList = fcOrderInfoListMap.get(fcProjectInfoRest.getProjectCode());
				if(fcOrderInfoList != null){
					for (Object fcOrderInfoRest : fcOrderInfoList) {
						fcOrderInfos.add((FcOrderInfoRest) fcOrderInfoRest);
					}
				}
				List<Object> fcActualReceiptList = fcActualReceiptListMap.get(fcProjectInfoRest.getProjectCode());
				if(fcActualReceiptList != null){
					for (Object fcActualReceipt : fcActualReceiptList) {
						fcAtualReceiptDetails.add((FcActualReceiptDetailRest) fcActualReceipt);
					}
				}
				List<Object> fcPlanReceiptList = fcPlanReceiptListMap.get(fcProjectInfoRest.getProjectCode());
				if(fcPlanReceiptList != null){
					for (Object fcPlanReceipt : fcPlanReceiptList) {
						fcPlanReceiptDetails.add((FcPlanReceiptDetailRest) fcPlanReceipt);
					}
				}
				
				fcProjectInfoRest.setFinancialMonth(yyyymm);//重设计算月份
				fcProjectInfoRest.setFcOrderInfoRestList(fcOrderInfos);
				fcProjectInfoRest.setFcActualReceiptDetailRestList(fcAtualReceiptDetails);
				fcProjectInfoRest.setFcPlanReceiptDetailRestList(fcPlanReceiptDetails);
				JSONObject json = new JSONObject();
				json.put("data", fcProjectInfoRest);
				
				JSONObject json2 = RequestService.requestService("http://localhost/wbm/service/fc/", "calcOuterCost", json.toJSONString(), "POST");
				
				String financialCost = json2.get("data").toString();
				String status = "";
				if (json2.get("code").toString().equals("0")) {
					status = "成功";
				} else {
					status = "失败";
				}

				jsonList.add(new String[]{fcProjectInfoRest.getProjectCode(),fcProjectInfoRest.getProjectName(),fcProjectInfoRest.getFstSvcType(),fcProjectInfoRest.getSaleOrg(),fcProjectInfoRest.getSalesName(),
						yyyymm,financialCost,status});
			}
		}
		
		String[] titles = new String[]{"项目编号","项目名称","产品线","事业部","销售员","计算月份","财务费用","状态"};
		String fileName = FileUtils.getFileNameWithoutExtension(file[0].getOriginalFilename());
		String fileFullName = "D:\\fc\\"+fileName+"_计划外结果.csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(titles, jsonList);
		
		System.out.println(DateUtils.formatDateTime(System.currentTimeMillis()-start));
		
		return "modules/cst/test/calcCostList";
	} 
	
	/*@RequestMapping(value = "inPlanExcel")
	public String inExcel(@RequestParam("file") MultipartFile[] file) throws Exception {
		long start = System.currentTimeMillis();
		
		putMap();
		//ImportExcel(导入文件对象,标题行号，数据行号=标题行号+1,工作表编号)
		Map<String, List<Object>> fcObjectListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 0), FcProjectInfoRest.class);
		Map<String, List<Object>> fcOrderInfoListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 1), FcOrderInfoRest.class);
		Map<String, List<Object>> fcPlanPayListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 2), FcPlanPayDetailRest.class);
		Map<String, List<Object>> fcPlanReceiptListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 3), FcPlanReceiptDetailRest.class);
		
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
		        .setNameFormat("FcInners-%d")
		        .setDaemon(true)
		        .build();
		
		ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1, threadFactory);

		//ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);
		
		//线程返回结果集
		ArrayList<Future<FcPlanInnerFeeRest>> threadResults = new ArrayList<Future<FcPlanInnerFeeRest>>();
		
		FcProjectInfoRest fcProjectInfoRest = new FcProjectInfoRest();
		for (Entry<String, List<Object>> entry : fcObjectListMap.entrySet()) {
			fcProjectInfoRest = (FcProjectInfoRest) entry.getValue().get(0);
			List<FcOrderInfoRest> fcOrderInfos = new LinkedList<FcOrderInfoRest>();		// 订单信息
			List<FcPlanPayDetailRest> fcPlanPayDetails = new LinkedList<FcPlanPayDetailRest>();		// 项目计划付款明细
			List<FcPlanReceiptDetailRest> fcPlanReceiptDetails = new LinkedList<FcPlanReceiptDetailRest>();		// 项目计划收款明细
			
			List<Object> fcOrderInfoList = fcOrderInfoListMap.get(fcProjectInfoRest.getProjectCode());
			if(fcOrderInfoList != null){
				for (Object fcOrderInfoRest : fcOrderInfoList) {
					fcOrderInfos.add((FcOrderInfoRest) fcOrderInfoRest);
				}
			}
			List<Object> fcPlanPayList = fcPlanPayListMap.get(fcProjectInfoRest.getProjectCode());
			if(fcPlanPayList != null){
				for (Object fcPlanPay : fcPlanPayList) {
					fcPlanPayDetails.add((FcPlanPayDetailRest) fcPlanPay);
				}
			}
			List<Object> fcPlanReceiptList = fcPlanReceiptListMap.get(fcProjectInfoRest.getProjectCode());
			if(fcPlanReceiptList != null){
				for (Object fcPlanReceipt : fcPlanReceiptList) {
					fcPlanReceiptDetails.add((FcPlanReceiptDetailRest) fcPlanReceipt);
				}
			}
			
			fcProjectInfoRest.setFcOrderInfoRestList(fcOrderInfos);
			fcProjectInfoRest.setFcPlanPayDetailRestList(fcPlanPayDetails);
			fcProjectInfoRest.setFcPlanReceiptDetailRestList(fcPlanReceiptDetails);
			
			//FcPlanInnerFeeRest fcInnerFee = FcCalcService.calcInner(fcProjectInfoRest);
			
			CallThreadInnerCostCalc cpq = new CallThreadInnerCostCalc(fcProjectInfoRest);
			threadResults.add(threadPool.submit(cpq));
		}
		
		threadPool.shutdown();  

		List<String[]> jsonList = new LinkedList<String[]>();
		for(Future<FcPlanInnerFeeRest> fs : threadResults){  
			FcPlanInnerFeeRest fpiFee = fs.get();
			jsonList.add(new String[]{fpiFee.getProjectCode(),fpiFee.getFinancialCost()+""});
        }

		try {
			// 设置最长等待100毫秒
			threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);

			System.out.println("##################:"+threadResults.size());
			System.out.println("计算耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-start));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//释放内存占用
		threadResults.clear();
		
		String[] titles = new String[]{"项目编号","财务费用"};
		String fileFullName = "D:\\fc\\财务-计划内.csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(titles, jsonList);
		
		return "modules/cst/test/calcCostList";
	 }*/ 

	/*@RequestMapping(value = "outPlanExcel")
	public String outExcel(@RequestParam("file") MultipartFile[] file) throws Exception {
		
		long start = System.currentTimeMillis();

		//财务费用计算月份，为空则从项目信息中取
		String calcYearMonth = "201601-201612";
		
		putMap();
		//ImportExcel(导入文件对象,标题行号，数据行号=标题行号+1,工作表编号)
		Map<String, List<Object>> fcObjectListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 0), FcProjectInfoRest.class);
		Map<String, List<Object>> fcOrderInfoListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 1), FcOrderInfoRest.class);
		Map<String, List<Object>> fcPlanReceiptListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 2), FcPlanReceiptDetailRest.class);
		Map<String, List<Object>> fcActualReceiptListMap = getFcObjectListMap(new ImportExcel(file[0], 1, 3), FcActualReceiptDetailRest.class);
		FcProjectInfoRest fcProjectInfoRest = new FcProjectInfoRest();

		ThreadFactory threadFactory = new ThreadFactoryBuilder()
		        .setNameFormat("FcOuters-%d")
		        .setDaemon(true)
		        .build();
		
		ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1, threadFactory);
		
		//线程返回结果集
		ArrayList<Future<FcPlanOuterFeeRest>> threadResults = new ArrayList<Future<FcPlanOuterFeeRest>>();
		
		for (Entry<String, List<Object>> entry : fcObjectListMap.entrySet()) {

			fcProjectInfoRest = (FcProjectInfoRest) entry.getValue().get(0);
			
			//解析计算月份（如201601-201612）
			String calcYearMonthBegin = fcProjectInfoRest.getFinancialMonth();
			String calcYearMonthEnd = fcProjectInfoRest.getFinancialMonth();
			if(calcYearMonth != null){
				String[] calcYearMonthArray = calcYearMonth.split("-");
				if(calcYearMonthArray.length == 1){
					calcYearMonthBegin = calcYearMonthArray[0];
					calcYearMonthEnd = calcYearMonthArray[0];
				}else if(calcYearMonthArray.length == 2){
					calcYearMonthBegin = calcYearMonthArray[0];
					calcYearMonthEnd = calcYearMonthArray[1];
				}
			}

			Date calcYearMonthBeginDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthBegin,"yyyyMM"));
			Date calcYearMonthEndDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(calcYearMonthEnd,"yyyyMM"));
			List<String> monthBw = DateUtils.getMonthBetween(calcYearMonthBeginDate, calcYearMonthEndDate);

			//根据计算月份间隔循环
			for(String yyyymm : monthBw){
				//System.out.println("########执行项目数及计算月份：" + c + " , "+yyyymm);
				
				List<FcOrderInfoRest> fcOrderInfos = new LinkedList<FcOrderInfoRest>();		// 订单信息
				List<FcActualReceiptDetailRest> fcAtualReceiptDetails = new LinkedList<FcActualReceiptDetailRest>();		// 项目计划付款明细
				List<FcPlanReceiptDetailRest> fcPlanReceiptDetails = new LinkedList<FcPlanReceiptDetailRest>();		// 项目计划收款明细
				
				List<Object> fcOrderInfoList = fcOrderInfoListMap.get(fcProjectInfoRest.getProjectCode());
				if(fcOrderInfoList != null){
					for (Object fcOrderInfoRest : fcOrderInfoList) {
						fcOrderInfos.add((FcOrderInfoRest) fcOrderInfoRest);
					}
				}
				List<Object> fcActualReceiptList = fcActualReceiptListMap.get(fcProjectInfoRest.getProjectCode());
				if(fcActualReceiptList != null){
					for (Object fcActualReceipt : fcActualReceiptList) {
						fcAtualReceiptDetails.add((FcActualReceiptDetailRest) fcActualReceipt);
					}
				}
				List<Object> fcPlanReceiptList = fcPlanReceiptListMap.get(fcProjectInfoRest.getProjectCode());
				if(fcPlanReceiptList != null){
					for (Object fcPlanReceipt : fcPlanReceiptList) {
						fcPlanReceiptDetails.add((FcPlanReceiptDetailRest) fcPlanReceipt);
					}
				}
				
				fcProjectInfoRest.setFinancialMonth(yyyymm);//重设计算月份
				fcProjectInfoRest.setFcOrderInfoRestList(fcOrderInfos);
				fcProjectInfoRest.setFcActualReceiptDetailRestList(fcAtualReceiptDetails);
				fcProjectInfoRest.setFcPlanReceiptDetailRestList(fcPlanReceiptDetails);
				
				//获取计算月份计算月及以前的计划外财务费用信息
				List<FcProjectInfo> fcProjectInfoList = fcProjectInfoService.findList(new FcProjectInfo(fcProjectInfoRest.getProjectCode(),"A0"));
				if(fcProjectInfoList != null && fcProjectInfoList.size() > 1){
					throw new ServiceException("获取该月多条项目信息异常，项目编号："+fcProjectInfoRest.getProjectCode()+"，财务费用所在年月："+fcProjectInfoRest.getFinancialMonth());
				}

				Map<String, FcPlanOuterFee> fcPlanOuterFeeMap = Maps.newHashMap();
				if(fcProjectInfoList != null && !fcProjectInfoList.isEmpty()){
					FcProjectInfo fcProjectInfo = fcProjectInfoList.get(0);
					//计算当月库中财务费用
					List<FcPlanOuterFee> fpofList = fcPlanOuterFeeService.findList(new FcPlanOuterFee(fcProjectInfo.getId(),fcProjectInfoRest.getFinancialMonth(),"A0"));
					//计算当月的上一月库中财务费用
					Date lastDayOfLastMonthDate = DateUtils.getLastDayOfLastMonth(DateUtils.parseDate(fcProjectInfoRest.getFinancialMonth()));
					String lastDayOfLastMonthDateStr = DateUtils.formatDate(lastDayOfLastMonthDate, "yyyyMM");
					List<FcPlanOuterFee> fpofList2 = fcPlanOuterFeeService.findList(new FcPlanOuterFee(fcProjectInfo.getId(),lastDayOfLastMonthDateStr,"A0"));
					fpofList.addAll(fpofList2);
					
					if(fpofList != null){
						for(FcPlanOuterFee fcPlanOuterFee : fpofList){
							//计划外财务费用计算所在月份的上一月份的最后一天:yyyyMMdd
							Date lastDayOfMonthDate = DateUtils.getLastDayOfMonth(DateUtils.parseDate(fcPlanOuterFee.getFinancialMonth()));//月末
							String lastDayOfMonth = DateUtils.formatDate(lastDayOfMonthDate,FcCalcService.PATTERN_YYYYMMDD);
							if(fcPlanOuterFeeMap.containsKey(lastDayOfMonth)){
								throw new ServiceException("获取该月多条计划外财务费用异常，项目编号："+fcPlanOuterFee.getFcProjectInfo().getProjectCode()+"，财务费用所在年月："+fcPlanOuterFee.getFinancialMonth());
							}else{
								fcPlanOuterFeeMap.put(lastDayOfMonth, fcPlanOuterFee);
							}
						}
					}
				}
				
				CallThreadOuterCostCalc cpq = new CallThreadOuterCostCalc(fcProjectInfoRest,true,fcPlanOuterFeeMap);
				threadResults.add(threadPool.submit(cpq));
			}
		}
		
		threadPool.shutdown();  

		List<String[]> jsonList = new LinkedList<String[]>();
		for(Future<FcPlanOuterFeeRest> fs : threadResults){  
			FcPlanOuterFeeRest fpiFee = fs.get();
			jsonList.add(new String[]{fpiFee.getProjectCode(),fpiFee.getFinancialMonth(),fpiFee.getFinancialCost()+""});
        }

		try {
			// 设置最长等待100毫秒
			threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);

			System.out.println("##################:"+threadResults.size());
			System.out.println("计算耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-start));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//释放内存占用
		threadResults.clear();
		
		String[] titles = new String[]{"项目编号","所在月份","财务费用"};
		String fileFullName = "D:\\fc\\财务-计划外.csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(titles, jsonList);
		
		return "modules/cst/test/calcCostList";
	} */
	
	@SuppressWarnings("rawtypes")
	public Map<String, List<Object>> getFcObjectListMap(ImportExcel importExcel, Class class1) throws InstantiationException, IllegalAccessException{
		
		Map<String, List<Object>> fcObjectListMap = new LinkedHashMap<String, List<Object>>();
		int lastCellNum = importExcel.getLastCellNum();		//获取最后一个列号
		int lastDataRowNum = importExcel.getLastDataRowNum();	//获取最后一个数据行号
		Row headRow = importExcel.getRow(0);
		String name = class1.getName();
		Map<String, String> map = PropertyMap.get(name);
		for (int i = 1; i < lastDataRowNum; i++) {
			//获取数据行
			Object fcObject = class1.newInstance();
			Row row = importExcel.getRow(i);
			//项目编号
			String projectCode = importExcel.getCellValue(row, 0).toString();
			for (int j = 0; j < lastCellNum; j++) {
				//获取表头数据
				Object headCellValue = importExcel.getCellValue(headRow, j);
				//获取每一行的数据
				Object dataCellValue = importExcel.getCellValue(row, j);
				String fieldName = map.get(headCellValue);
				//System.out.println("########name："+fieldName+",value:"+dataCellValue);
				if (fieldName != "" && fieldName != null) {
					if ("serviceDateBegin,serviceDateEnd,planPayTime,planReceiptTime,actualReceiptTime".contains(fieldName)) {
						Reflections.setFieldValue(fcObject, fieldName, dataCellValue);
					} else {
						Class<? extends Object> dataClass = dataCellValue.getClass();
						if (dataClass.getName().equals("java.lang.Double")) {
							if ("displayOrder,planPayDays,planReceiptDays".contains(fieldName)) {
								Double value = (Double) dataCellValue;
								Integer integer = BigDecimal.valueOf(value).intValue();
								Reflections.setFieldValue(fcObject, fieldName, integer);
							}else if ("financialMonth".contains(fieldName)) {
								Double value = (Double) dataCellValue;
								Integer integer = BigDecimal.valueOf(value).intValue();
								String string = integer.toString();
								Reflections.setFieldValue(fcObject, fieldName, string);
							} else {
								Double value = (Double) dataCellValue;
								Reflections.setFieldValue(fcObject, fieldName, value);
							}
						} else {
							Reflections.setFieldValue(fcObject, fieldName, dataCellValue.toString());
						}
					}
				}
			}
			//按照项目号聚合
			if (fcObjectListMap == null || fcObjectListMap.size() == 0) {
				List<Object> fcObjectList = new LinkedList<Object>();
				fcObjectList.add(fcObject);
				fcObjectListMap.put(projectCode, fcObjectList);
			} else {
				List<Object> fcObjectList = fcObjectListMap.get(projectCode);
				if (fcObjectList != null && fcObjectList.size() != 0) {
					fcObjectList.add(fcObject);
					fcObjectListMap.put(projectCode, fcObjectList);
				} else {
					fcObjectList = new LinkedList<Object>();
					fcObjectList.add(fcObject);
					fcObjectListMap.put(projectCode, fcObjectList);
				}
			}
		}
		return fcObjectListMap;
	}
	
	/**
	 * Map赋值
	 */
	private Map<String, String> fcPrjPropertyMap;
	private Map<String, String> fcOrdPropertyMap;
	private Map<String, String> fcPayPropertyMap;
	private Map<String, String> fcPlanRecPropertyMap;
	private Map<String, String> fcActRecPropertyMap;
	private Map<String, Map<String, String>> PropertyMap;
	
	public void putMap(){
		if (PropertyMap == null) {
			PropertyMap = new LinkedHashMap<String, Map<String,String>>();
			fcPrjPropertyMap = new LinkedHashMap<String, String>();
			//FcProjectInfoRest 项目信息
			fcPrjPropertyMap.put("项目编号","projectCode");
			fcPrjPropertyMap.put("项目名称","projectName");
			fcPrjPropertyMap.put("客户名称","custName");
			fcPrjPropertyMap.put("产品线大类","fstSvcType");
			fcPrjPropertyMap.put("产品线小类","sndSvcType");
			fcPrjPropertyMap.put("事业部","saleOrg");
			fcPrjPropertyMap.put("销售员","salesName");
			fcPrjPropertyMap.put("是否有WBM订单","hasWbmOrder");
			fcPrjPropertyMap.put("签约金额","signMoney");
			fcPrjPropertyMap.put("签约净额","signNetMoney");
			fcPrjPropertyMap.put("财务费用计算年月","financialMonth");
			PropertyMap.put(FcProjectInfoRest.class.getName(), fcPrjPropertyMap);
			//FcOrderInfoRest 订单信息
			fcOrdPropertyMap = new LinkedHashMap<String, String>();
			fcPrjPropertyMap.put("项目编号","projectCode");
			fcOrdPropertyMap.put("订单编号","orderId");
			fcOrdPropertyMap.put("产品线大类","fstSvcType");
			fcOrdPropertyMap.put("产品线小类","sndSvcType");
			fcOrdPropertyMap.put("服务期开始","serviceDateBegin");
			fcOrdPropertyMap.put("服务期结束","serviceDateEnd");
			fcOrdPropertyMap.put("自有产品成本","ownProdCost");
			fcOrdPropertyMap.put("指定分包成本","specifySubCost");
			PropertyMap.put(FcOrderInfoRest.class.getName(), fcOrdPropertyMap);
			//FcPlanPayDetailRest 计划付款
			fcPayPropertyMap = new LinkedHashMap<String, String>();
			fcPrjPropertyMap.put("项目编号","projectCode");
			fcPayPropertyMap.put("期次","displayOrder");
			fcPayPropertyMap.put("计划付款时间","planPayTime");
			fcPayPropertyMap.put("计划付款金额","planPayAmount");
			fcPayPropertyMap.put("计划付款比例","planPayScale");
			fcPayPropertyMap.put("支付类型","payType");
			fcPayPropertyMap.put("付款时间间隔天数","planPayDays");
			PropertyMap.put(FcPlanPayDetailRest.class.getName(), fcPayPropertyMap);
			//FcPlanReceiptDetailRest 计划收款
			fcPlanRecPropertyMap = new LinkedHashMap<String, String>();
			fcPrjPropertyMap.put("项目编号","projectCode");
			fcPlanRecPropertyMap.put("期次","displayOrder");
			fcPlanRecPropertyMap.put("计划收款时间","planReceiptTime");
			fcPlanRecPropertyMap.put("计划收款金额","planReceiptAmount");
			fcPlanRecPropertyMap.put("计划收款比例","planReceiptScale");
			fcPlanRecPropertyMap.put("支付类型","payType");
			fcPlanRecPropertyMap.put("收款时间间隔天数","planReceiptDays");
			PropertyMap.put(FcPlanReceiptDetailRest.class.getName(), fcPlanRecPropertyMap);
			//FcActualReceiptDetailRest 实际收款
			fcActRecPropertyMap = new LinkedHashMap<String, String>();
			fcPrjPropertyMap.put("项目编号","projectCode");
			fcActRecPropertyMap.put("期次","displayOrder");
			fcActRecPropertyMap.put("实际收款时间","actualReceiptTime");
			fcActRecPropertyMap.put("实际收款金额","actualReceiptAmount");
			fcActRecPropertyMap.put("支付类型","payType");
			PropertyMap.put(FcActualReceiptDetailRest.class.getName(), fcActRecPropertyMap);
		}
	}
}

/**
 * 计划内财务费用线程
 * @author yuanly
 *
 */
/*class CallThreadInnerCostCalc implements Callable<FcPlanInnerFeeRest>{
    private FcProjectInfoRest fcProjectInfoRest;
    
    public CallThreadInnerCostCalc(FcProjectInfoRest fcProjectInfoRest) {
		this.fcProjectInfoRest = fcProjectInfoRest;
	}
    
	@Override
    public FcPlanInnerFeeRest call(){
		FcPlanInnerFeeRest fcInnerFee = null;
		
    	try {
    		fcInnerFee = FcCalcService.calcInner(fcProjectInfoRest);
    		
    		TimeUnit.MILLISECONDS.sleep(100);
    		
			System.out.println("线程["+Thread.currentThread()+"]计划内财务费用计算成功："+fcProjectInfoRest.getProjectCode()+":"+fcInnerFee.getFinancialCost());
      	} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
    		System.out.println("计划内财务费用计算子线程中断：" + Thread.currentThread() + "，执行异常："+e);
      	}
          
        return fcInnerFee;
    }  
}  */

/**
 * 计划外财务费用线程
 * @author yuanly
 *
 */
/*class CallThreadOuterCostCalc implements Callable<FcPlanOuterFeeRest>{
    private FcProjectInfoRest fcProjectInfoRest;
    private boolean isOverwrite;
    private Map<String, FcPlanOuterFee> fcPlanOuterFeeMap;
    
    public CallThreadOuterCostCalc(FcProjectInfoRest fcProjectInfoRest,boolean isOverwrite,Map<String, FcPlanOuterFee> fcPlanOuterFeeMap) {
		this.fcProjectInfoRest = fcProjectInfoRest;
		this.isOverwrite = isOverwrite;
		this.fcPlanOuterFeeMap = fcPlanOuterFeeMap;
	}
    
	@Override
    public FcPlanOuterFeeRest call(){
		FcPlanOuterFeeRest fcPlanOuterFee = null;
		
    	try {
    		fcPlanOuterFee = FcCalcService.calcOuter(fcProjectInfoRest, isOverwrite, fcPlanOuterFeeMap);
    		
    		TimeUnit.MILLISECONDS.sleep(100);
    		
			System.out.println("线程["+Thread.currentThread()+"]计划外财务费用计算成功："+fcProjectInfoRest.getProjectCode()+":"+fcPlanOuterFee.getFinancialCost());
      	} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
    		System.out.println("计划外财务费用计算子线程中断：" + Thread.currentThread() + "，执行异常："+e);
      	}
          
        return fcPlanOuterFee;
    }  
}  */