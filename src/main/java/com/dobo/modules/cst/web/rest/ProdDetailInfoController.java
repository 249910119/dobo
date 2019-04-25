package com.dobo.modules.cst.web.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dobo.common.config.Global;
import com.dobo.common.persistence.DBConn;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.Reflections;
import com.dobo.common.utils.StringUtils;
import com.dobo.common.utils.csv.CsvHelper;
import com.dobo.common.utils.csv.OpenCsvHelper;
import com.dobo.common.utils.excel.ExportExcel;
import com.dobo.common.utils.excel.ImportExcel;
import com.dobo.modules.cop.entity.detail.CopCaseDetail;
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;
import com.dobo.modules.cop.service.detail.CopCaseDetailService;
import com.dobo.modules.cop.service.detail.CopSalesUseDetailService;
import com.dobo.modules.cst.calplugins.cost.impl.CheckManCostImpl.CheckDetailSplit;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.check.CstCheckSlaPara;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;
import com.dobo.modules.cst.entity.detail.CstDetailCostInfo;
import com.dobo.modules.cst.entity.detail.CstNewOrderCostInfo;
import com.dobo.modules.cst.entity.detail.CstOrderCostInfo;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;
import com.dobo.modules.cst.entity.man.CstManFailureCaseHour;
import com.dobo.modules.cst.entity.man.CstManFailureSlaPara;
import com.dobo.modules.cst.entity.parts.CstPartsEquipTypeRate;
import com.dobo.modules.cst.entity.parts.CstPartsEventFailurePara;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.dobo.modules.cst.service.base.CstResourceBaseInfoService;
import com.dobo.modules.cst.service.detail.CstDetailCostInfoService;
import com.dobo.modules.cst.service.detail.CstNewOrderCostInfoService;
import com.dobo.modules.cst.service.detail.CstOrderCostInfoService;
import com.dobo.modules.cst.service.detail.CstOrderDetailInfoService;
import com.dobo.modules.cst.service.man.CstManFailureCaseHourService;
import com.dobo.modules.cst.service.parts.CstPartsEventFailureParaService;
import com.dobo.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Controller
@RequestMapping(value = "${adminPath}/cst/rest/entity/prodDetailInfo")
public class ProdDetailInfoController {
	
	@Autowired
	CstDetailCostInfoService cstDetailCostInfoService;
	
	@Autowired
	CstOrderDetailInfoService cstOrderDetailInfoService;

	@Autowired
	CstOrderCostInfoService cstOrderCostInfoService;
	@Autowired
	CstNewOrderCostInfoService cstNewOrderCostInfoService;
	@Autowired
	CstManFailureCaseHourService cstManFailureCaseHourService;
	@Autowired
	CstPartsEventFailureParaService cstPartsEventFailureParaService;
	@Autowired
	CopCaseDetailService copCaseDetailService;
	@Autowired
	CstResourceBaseInfoService cstResourceBaseInfoService;
	@Autowired
	CopSalesUseDetailService copSalesUseDetailService;
	
	private Map<String, List<CstOrderDetailInfo>> cstOrderDetailInfosMap;

	//成本模型计算结果文件目录
	public static String cst_filedir = Global.getUserfilesBaseDir() + File.separator + "cst";
	
	@RequestMapping(value = {"list", ""})
	public String list(ProdDetailInfo prodDetailInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/cst/test/prodDetailInfoList";
	}

    public static void nativeBatchInsert(List<CstNewOrderCostInfo> cstOrderCostInfoList) throws SQLException {
    	StringBuilder prepareInsertSql = new StringBuilder("INSERT INTO dobo.cst_new_order_cost_info (");
    	String[] insertFieldNames = CstNewOrderCostInfo.insertFieldNames;
    	StringBuilder fieldNames = new StringBuilder();
    	StringBuilder fieldMarks = new StringBuilder();
    	for(String fieldName : insertFieldNames){
    		fieldNames.append(StringUtils.toUnderScoreCase(fieldName)).append(",");
    		fieldMarks.append("?,");
    	}
    	//去掉最后的逗号
    	prepareInsertSql.append(fieldNames.substring(0, fieldNames.length()-1));
    	//去掉最后的逗号
    	prepareInsertSql.append(") VALUES (").append(fieldMarks.substring(0, fieldMarks.length()-1)).append(")");
    	
    	//System.out.println("#####:"+prepareInsertSql);
    	
        Connection connection = null;
        long startTime = 0;
        try{
            connection = DBConn.getConnForNative();
            startTime=System.currentTimeMillis();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(prepareInsertSql.toString());
            int num = 0;
            for (int i = 0;i< cstOrderCostInfoList.size();i++){
            	CstNewOrderCostInfo cstOrder = cstOrderCostInfoList.get(i);

            	for(int j=0;j<insertFieldNames.length;j++){
            		String fieldName = insertFieldNames[j];
            		Field field = Reflections.getFieldByFieldName(cstOrder, fieldName);
            		Object fieldValue = Reflections.getFieldValue(cstOrder, fieldName);
            		if(field.getType() == String.class){
                        statement.setString(j+1, fieldValue==null?"":fieldValue.toString());
            		}else if(field.getType() == Double.class){
                        statement.setDouble(j+1, fieldValue==null?0.0:Double.valueOf(fieldValue.toString()));
            		}else if(field.getType() == Date.class){
            			Date date = fieldValue==null?new Date():(Date)fieldValue;
                        statement.setDate(j+1, new java.sql.Date(date.getTime()));
            		}else if(field.getType() == User.class){
                        statement.setString(j+1, "admin");
            		}else if(field.getType() == BigDecimal.class){
                        statement.setBigDecimal(j+1, fieldValue==null?null:new BigDecimal(fieldValue.toString()));
            		}else{
                    	System.out.println(fieldName+":"+field.getType()+"");
                    	throw new Exception("字段["+fieldName+"]类型未解析："+field.getType());
            		}
            	}
            	
                statement.addBatch();
                num++;
                if(num !=0 && num%2000 == 0){
                    statement.executeBatch();
                    connection.commit();
                    num = 0;
                }
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("成本模型插入数据库耗时："+DateUtils.formatDateTime(System.currentTimeMillis()-startTime));
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            System.out.println("成本模型插入数据库失败："+e.getMessage());
        }finally {
            if(connection != null){
                connection.close();
                System.out.println("关闭数据库连接！");
            }
        }
    }

    @RequestMapping(value = "calcBaseData")
	public String calcBaseData() throws Exception {
    	CstResourceBaseInfo crbSelect = new CstResourceBaseInfo();
    	crbSelect.setStatus("A0");
    	List<CstResourceBaseInfo> crbList = cstResourceBaseInfoService.findList(crbSelect);
    	CstManFailureCaseHour cmfchSelect = new CstManFailureCaseHour();
    	cmfchSelect.setStatus("A0");
    	List<CstManFailureCaseHour> cmfchList = cstManFailureCaseHourService.findList(cmfchSelect);
    	Map<String, CstManFailureCaseHour> cmfchMap = new HashMap<String, CstManFailureCaseHour>();
    	for (CstManFailureCaseHour cmfch : cmfchList){
    		cmfchMap.put(cmfch.getResourceId(), cmfch);
    	}

    	CstPartsEventFailurePara cpefpSelect = new CstPartsEventFailurePara();
    	cmfchSelect.setStatus("A0");
    	List<CstPartsEventFailurePara> cpefpList = cstPartsEventFailureParaService.findList(cpefpSelect);
    	Map<String, CstPartsEventFailurePara> cpefpMap = new HashMap<String, CstPartsEventFailurePara>();
    	for (CstPartsEventFailurePara cpefp : cpefpList){
    		cpefpMap.put(cpefp.getResourceId(), cpefp);
    	}
		
    	for(CstResourceBaseInfo crbi : crbList) {
    		if(!"A0".equals(crbi.getResourceClass())) continue;
    		if(!"A0".equals(crbi.getStatus())) continue;
    		if(cmfchMap.get(crbi.getResourceId()) == null || cpefpMap.get(crbi.getResourceId()) == null) {
    			cstResourceBaseInfoService.calBaseResourcePara(crbi.getResourceId());
    		}
    	}
    	
		return "modules/cst/test/prodDetailInfoList";
    }
    
    @RequestMapping(value = "checkBaseDataPara")
	public String checkBaseDataPara() throws Exception {
    	Map<String, Map<String, String>> needDataMap = Maps.newHashMap();
    	
    	Map<String, CstResourceBaseInfo> cstResourceBaseInfoByIdMap = CacheDataUtils.getCstResourceBaseInfoMap();
		//故障CASE处理工时定义集合
    	Map<String, CstManFailureCaseHour> cstManFailureCaseHourMap = CacheDataUtils.getCstManFailureCaseHourMap();
    	//故障级别配比定义集合
    	Map<String, CstManFailureSlaPara> cstManFailureSlaParaMap = CacheDataUtils.getCstManFailureSlaParaMap();
    	//巡检工时定义集合
    	Map<String, CstCheckWorkHour> cstCheckWorkHourMap = CacheDataUtils.getCstCheckWorkHourMap();
    	//巡检级别配比定义集合
    	Map<String, CstCheckSlaPara> cstCheckSlaParaMap = CacheDataUtils.getCstCheckSlaParaMap();
    	//备件事件故障参数定义集合
    	Map<String, CstPartsEventFailurePara> cstPartsEventFailureParaMap = CacheDataUtils.getCstPartsEventFailureParaMap();
    	//备件各技术方向人员成本系数集合
    	Map<String, CstPartsEquipTypeRate> cstPartsEquipTypeRateMap = CacheDataUtils.getCstPartsEquipTypeRateMap();
    	
    	for(CstResourceBaseInfo crbi : cstResourceBaseInfoByIdMap.values()) {
    		if(!"A0".equals(crbi.getResourceClass())) continue;
    		if(!"A0".equals(crbi.getStatus())) continue;
    		
    		Map<String, String> datamap = Maps.newHashMap();
    		if(cstManFailureCaseHourMap != null && cstManFailureCaseHourMap.get(crbi.getResourceId()) == null) {
    			datamap.put("cmfch", "no");
    		}
    		if(cstManFailureSlaParaMap != null && cstManFailureSlaParaMap.get(crbi.getResourceId()+"SLA_DEVICE_A") == null) {
    			datamap.put("cmfsp", "no");
    		}
    		if(cstCheckWorkHourMap != null && cstCheckWorkHourMap.get(crbi.getModelGroupId()) == null) {
    			datamap.put("ccwh", "no");
    		}
    		if(cstCheckSlaParaMap != null && cstCheckSlaParaMap.get(crbi.getModelGroupId()+"BUYFARCHK") == null) {
    			datamap.put("ccsp", "no");
    		}
    		if(cstPartsEventFailureParaMap != null && cstPartsEventFailureParaMap.get(crbi.getResourceId()) == null) {
    			datamap.put("cpefp", "no");
    		}
    		if(cstPartsEquipTypeRateMap != null && cstPartsEquipTypeRateMap.get(crbi.getEquipTypeId()) == null) {
    			datamap.put("cpetr", "no");
    		}
    		
    		if(datamap.size() > 0) {
    			needDataMap.put(crbi.getResourceId(), datamap);
    		}
    	}

		List<String[]> jsonList = new LinkedList<String[]>();
		for(String resourceId : needDataMap.keySet()) {
			Map<String, String> paramap = needDataMap.get(resourceId);
			CstResourceBaseInfo crbi = cstResourceBaseInfoByIdMap.get(resourceId);
			
			jsonList.add(new String[]{crbi.getResourceId(),crbi.getMfrName(),crbi.getResourceName(),
							crbi.getModelGroupName(),crbi.getEquipTypeName(),crbi.getResstattypeName(),
							paramap.get("cmfch")==null?"":paramap.get("cmfch"),
							paramap.get("cmfsp")==null?"":paramap.get("cmfsp"),
							paramap.get("ccwh")==null?"":paramap.get("ccwh"),
							paramap.get("ccsp")==null?"":paramap.get("ccsp"),
							paramap.get("cpefp")==null?"":paramap.get("cpefp"),
							paramap.get("cpetr")==null?"":paramap.get("cpetr")});
		}
    	String[] titles = new String[]{"主键","厂商","型号","型号组","技术方向","资源统计类型","故障CASE处理工时定义","故障级别配比定义","巡检工时定义","巡检级别配比定义","备件事件故障参数定义","备件各技术方向人员成本系数"};
		String fileFullName = cst_filedir + File.separator +"主数据资源模型参数_"+DateUtils.getDate()+".csv";
		CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
		csvHelper.writeCsv(titles, jsonList);
    	
		return "modules/cst/test/prodDetailInfoList";
    }
	
    /**
     * 读取excel后计算成本模型-多线程
     * 
     * @param file
     * @return
     * @throws IOException 
     * @throws InvalidFormatException 
     * @throws Exception
     */
	@RequestMapping(value = "excelThread")
	public String excelThread(@RequestParam("fileThread") MultipartFile[] file) throws Exception {
		long begin = System.currentTimeMillis();
		//对map赋值
		this.putMap();
		// 导入Excel数据
		if (file != null && file.length != 0) {
			cstOrderDetailImport(file);
		}
		
		String inExcelFileName = file[0].getOriginalFilename();
		
		if (cstOrderDetailInfosMap != null && cstOrderDetailInfosMap.size() != 0) {
			
			long initPara = System.currentTimeMillis();
			//初始成本模型及配置参数
			CacheDataUtils.initCstBaseDataCache();
			CacheDataUtils.initCstCfgParaCache();
			System.out.println("初始成本模型及配置参数耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-initPara));

			final ThreadFactory threadFactory = new ThreadFactoryBuilder()
			        .setNameFormat("CstOrders-%d")
			        .setDaemon(true)
			        .build();
			
			int procs = Runtime.getRuntime().availableProcessors()+1;
			final ExecutorService pool = Executors.newFixedThreadPool(procs, threadFactory);
			
			System.out.println("CPU核数(+1)：" + procs);

			//线程返回结果集
			List<Future<Map<String, List<CstNewOrderCostInfo>>>> threadResults = Lists.newArrayList();

			int taskSize = cstOrderDetailInfosMap.size();
			int taskNum = 1;
			for (Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry : cstOrderDetailInfosMap.entrySet()) {
				CstNewOrderCalcCallable cpq = new CstNewOrderCalcCallable(taskSize,taskNum,cstOrderDetailInfosEntry);
				Future<Map<String, List<CstNewOrderCostInfo>>> f = pool.submit(cpq);  
				threadResults.add(f);
				taskNum ++;
			}

			//拒绝传入新任务
			pool.shutdown();
			
			System.out.println("线程池关闭，拒绝传入新任务:"+DateUtils.getDateTime());

			List<CstNewOrderCostInfo> cstOrderCostInfoList = Lists.newArrayList();
			
			try {
				for(Future<Map<String, List<CstNewOrderCostInfo>>> fs : threadResults){  
					while(!fs.isDone());//Future返回如果没有完成，则一直循环等待，直到Future返回完
					Map<String, List<CstNewOrderCostInfo>> map = fs.get();
					for(List<CstNewOrderCostInfo> subList : map.values()){
						cstOrderCostInfoList.addAll(subList);
					}
				}
				
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException ie) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
				ie.printStackTrace();
				System.out.println(">>><<<线程异常中断："+ie.getMessage());
				System.out.println(">>><<<######线程池状态 isTerminated:"+pool.isTerminated()+",isShutdown:"+pool.isShutdown());
				return null;
			} catch (Exception e) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
				e.printStackTrace();
				System.out.println(">>><<<线程异常中断2"+e.getMessage());
				System.out.println(">>><<<######线程池状态 isTerminated:"+pool.isTerminated()+",isShutdown:"+pool.isShutdown());
				return null;
			}

			System.out.println("成本模型线程计算总计耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-begin));
			
			//释放内存占用
			//threadResults.clear();
			
			cstOrderDetailInfosMap.clear();
			file = null;

			long exportcsv = System.currentTimeMillis();
			String[] fieldNames = CstNewOrderCostInfo.expFieldNames;
			String[] titles = CstNewOrderCostInfo.expTitles;
			String fileFullName = cst_filedir + File.separator + inExcelFileName + File.separator 
					+ "运维资源计划("+inExcelFileName+")"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			File file2 = new File(fileFullName);
			if (!file2.getParentFile().exists()){
				file2.getParentFile().mkdirs();
			}
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstNewOrderCostInfo.class, cstOrderCostInfoList, fieldNames, titles, 1048575);//最大行1048576
			System.out.println("写入csv耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-exportcsv));

			//写入数据库
			//nativeBatchInsert(cstOrderCostInfoList);
			
			boolean isInsertDB = false;
			if(isInsertDB){
				long insertdb = System.currentTimeMillis();
				int batchCount = 2000;
	            int batchLastIndex = batchCount;
				for (int index = 0; index < cstOrderCostInfoList.size();) {
	                if (batchLastIndex >= cstOrderCostInfoList.size()) {
	                    batchLastIndex = cstOrderCostInfoList.size();
	                    cstNewOrderCostInfoService.addDetailbatch(cstOrderCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    break;// 数据插入完毕，退出循环
	                } else {
	                	cstNewOrderCostInfoService.addDetailbatch(cstOrderCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    index = batchLastIndex;// 设置下一批下标
	                    batchLastIndex = index + (batchCount - 1);
	                }
	            }
				System.out.println("成本模型插入数据库耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-insertdb));
			}
			
			System.out.println("成本模型执行耗时总计:" + DateUtils.formatDateTime(System.currentTimeMillis() - begin));//全部时间
		}
		
		return "modules/cst/test/prodDetailInfoList";
	 }
	
    /**
     * 数据库读取后计算成本模型-多线程
     * 
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "execBaseCostThread")
	public String execBaseCostThread(HttpServletRequest request) throws Exception {
		String dcPrjId = request.getParameter("dcPrjId");
		String createDate = request.getParameter("createDate");
		String createDateStr = null;
		if(!StringUtils.isEmpty(createDate) && !StringUtils.isEmpty(createDate)){
			createDateStr = DateUtils.formatDate(DateUtils.parseDate(createDate), "yyyyMMdd");
		}else{
			return "modules/cst/test/prodDetailInfoList";
		}

		long begin = System.currentTimeMillis();

		List<String> execOrgName = new ArrayList<String>();
		execOrgName.add("运维");
		execOrgName.add("运维-快捷");
		execOrgName.add("运维-非MA");
		List<String> supportType = new ArrayList<String>();
		supportType.add("自有");
		supportType.add("备件分包");
		supportType.add("备件自主外协");
		List<String> costClassName = new ArrayList<String>();
		costClassName.add("其它");
		costClassName.add("响应支持");
		costClassName.add("主动服务");
		costClassName.add("云产品");
		List<String> prodName = new ArrayList<String>();
		prodName.add("备件支持服务");
		prodName.add("硬件故障解决服务");
		prodName.add("标准硬件保修服务");
		prodName.add("标准硬件保修服务-R类");
		prodName.add("标准健康检查服务");
		prodName.add("其他专业服务");
		prodName.add("搬迁服务");
		prodName.add("软件运维服务包");
		prodName.add("数据中心迁移");
		prodName.add("系统软件高级服务SP");
		prodName.add("系统软件基础服务SP-");
		prodName.add("系统软件基础服务SP+");
		prodName.add("硬件健康检查服务");
		prodName.add("硬件健康检查增强服务");
		prodName.add("硬件人工支持服务");
		prodName.add("驻场服务");
		/*List<String> prodServiceMode = new ArrayList<String>();
		prodServiceMode.add("硬件");
		prodServiceMode.add("软件");
		prodServiceMode.add("硬件不含二线");
		prodServiceMode.add("软件不含二线");
		prodServiceMode.add("硬件非MA");
		prodServiceMode.add("软件非MA");*/
		List<String> prodStatType = null;
		String pdId = null;//"PD1502151927271";
		//String createDateStr = "20170228";
		//String dcPrjId = "RTPAFZS110";
		
		List<CstOrderDetailInfo> baseList = cstOrderDetailInfoService.getWbmBaseList(execOrgName, supportType, costClassName, null, prodName, prodStatType, createDateStr, pdId, dcPrjId);

		cstOrderDetailInfosMap = new LinkedHashMap<String, List<CstOrderDetailInfo>>();
		
		//按照订单号聚合
		for(CstOrderDetailInfo info : baseList) {
			if(cstOrderDetailInfosMap.get(info.getOrderId()) == null) {
				cstOrderDetailInfosMap.put(info.getOrderId(), new LinkedList<CstOrderDetailInfo>());
			}
			cstOrderDetailInfosMap.get(info.getOrderId()).add(info);
		}
		
		baseList.clear();
		
		if (cstOrderDetailInfosMap != null && cstOrderDetailInfosMap.size() != 0) {
			
			long initPara = System.currentTimeMillis();
			//初始成本模型及配置参数
			CacheDataUtils.initCstBaseDataCache();
			CacheDataUtils.initCstCfgParaCache();
			System.out.println("初始成本模型及配置参数耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-initPara));

			final ThreadFactory threadFactory = new ThreadFactoryBuilder()
			        .setNameFormat("CstOrders-%d")
			        .setDaemon(true)
			        .build();

			int procs = Runtime.getRuntime().availableProcessors()+1;
			final ExecutorService pool = Executors.newFixedThreadPool(procs, threadFactory);
			
			System.out.println("CPU核数(+1)：" + procs);

			//线程返回结果集
			List<Future<Map<String, List<CstNewOrderCostInfo>>>> threadResults = Lists.newArrayList();

			int taskSize = cstOrderDetailInfosMap.size();
			int taskNum = 1;
			for (Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry : cstOrderDetailInfosMap.entrySet()) {
				CstNewOrderCalcCallable cpq = new CstNewOrderCalcCallable(taskSize,taskNum,cstOrderDetailInfosEntry);
				Future<Map<String, List<CstNewOrderCostInfo>>> f = pool.submit(cpq);  
				threadResults.add(f);
				taskNum ++;
			}

			//拒绝传入新任务
			pool.shutdown();  
			System.out.println("线程池关闭，拒绝传入新任务:"+DateUtils.getDateTime());

			List<CstNewOrderCostInfo> cstOrderCostInfoList = Lists.newArrayList();
			
			try {
				for(Future<Map<String, List<CstNewOrderCostInfo>>> fs : threadResults){  
					while(!fs.isDone());//Future返回如果没有完成，则一直循环等待，直到Future返回完
					Map<String, List<CstNewOrderCostInfo>> map = fs.get();
					for(List<CstNewOrderCostInfo> subList : map.values()){
						cstOrderCostInfoList.addAll(subList);
					}
				}
				
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException ie) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
				ie.printStackTrace();
				System.out.println(">>><<<线程异常中断："+ie.getMessage());
				System.out.println(">>><<<######线程池状态 isTerminated:"+pool.isTerminated()+",isShutdown:"+pool.isShutdown());
				return null;
			} catch (Exception e) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
				e.printStackTrace();
				System.out.println(">>><<<线程异常中断2： "+e.getMessage());  
				System.out.println(">>><<<######线程池状态 isTerminated:"+pool.isTerminated()+",isShutdown:"+pool.isShutdown());
				return null;
			}

			System.out.println("成本模型线程计算总计耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-begin));
			
			//释放内存占用
			//threadResults.clear();
			
			cstOrderDetailInfosMap.clear();

			long exportcsv = System.currentTimeMillis();
			String[] fieldNames = CstNewOrderCostInfo.expFieldNames;
			String[] titles = CstNewOrderCostInfo.expTitles;
			String fileFullName = cst_filedir + File.separator + "运维资源计划(基础表截止"+createDateStr+")"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			File file = new File(fileFullName);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstNewOrderCostInfo.class, cstOrderCostInfoList, fieldNames, titles, 1048575);//最大行1048576
			System.out.println("写入csv耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-exportcsv));

			//写入数据库
			nativeBatchInsert(cstOrderCostInfoList);
			
			boolean isInsertDB = false;
			if(isInsertDB){
				long insertdb = System.currentTimeMillis();
				int batchCount = 2000;
	            int batchLastIndex = batchCount;
				for (int index = 0; index < cstOrderCostInfoList.size();) {
	                if (batchLastIndex >= cstOrderCostInfoList.size()) {
	                    batchLastIndex = cstOrderCostInfoList.size();
	                    cstNewOrderCostInfoService.addDetailbatch(cstOrderCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    break;// 数据插入完毕，退出循环
	                } else {
	                	cstNewOrderCostInfoService.addDetailbatch(cstOrderCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    index = batchLastIndex;// 设置下一批下标
	                    batchLastIndex = index + (batchCount - 1);
	                }
	            }
				System.out.println("写入数据库耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-insertdb));
			}
			
			System.out.println("系统运行耗时总计:" + DateUtils.formatDateTime(System.currentTimeMillis() - begin));//全部时间
		}
		
		// 单次case计算资源计划
		List<CopCaseDetail> caseList = copCaseDetailService.getCaseConfirmList(createDateStr, dcPrjId);
		
		Map<String, List<CopCaseDetail>> copCaseMap = new LinkedHashMap<String, List<CopCaseDetail>>();
		//按照单次报价号聚合
		for(CopCaseDetail info : caseList) {
			if(copCaseMap.get(info.getCaseConfirmId()) == null) {
				copCaseMap.put(info.getCaseConfirmId(), new LinkedList<CopCaseDetail>());
			}
			copCaseMap.get(info.getCaseConfirmId()).add(info);
		}

		// 单次case-预付费报价支付明细(多个项目支付的单次case)
		List<CopSalesUseDetail> csudList = copSalesUseDetailService.getSameCaseAccountUsedList(createDateStr, dcPrjId);
		// 按照单次报价号（单次报价确认ID）聚合
		Map<String, List<CopSalesUseDetail>> sameDcPrjIdMap = new LinkedHashMap<String, List<CopSalesUseDetail>>();
		for(CopSalesUseDetail csud : csudList) {
			if(sameDcPrjIdMap.get(csud.getCaseConfirmId()) == null) {
				sameDcPrjIdMap.put(csud.getCaseConfirmId(), new LinkedList<CopSalesUseDetail>());
			}
			sameDcPrjIdMap.get(csud.getCaseConfirmId()).add(csud);
		}

		// 单次case-预付费报价支付明细
		List<CopSalesUseDetail> copSalesUseDetailList = copSalesUseDetailService.getCaseAccountUsedList(createDateStr, dcPrjId);
		// 按照单次报价号（单次报价确认ID）聚合
		Map<String, List<CopSalesUseDetail>> confirmDcPrjIdMap = new LinkedHashMap<String, List<CopSalesUseDetail>>();
		for(CopSalesUseDetail csud : copSalesUseDetailList) {
			if(confirmDcPrjIdMap.get(csud.getCaseConfirmId()) == null) {
				confirmDcPrjIdMap.put(csud.getCaseConfirmId(), new LinkedList<CopSalesUseDetail>());
			}
			confirmDcPrjIdMap.get(csud.getCaseConfirmId()).add(csud);
		}
		
		if (copCaseMap != null && copCaseMap.size() != 0) {
			
			final ThreadFactory threadFactory = new ThreadFactoryBuilder()
			        .setNameFormat("CstCases-%d")
			        .setDaemon(true)
			        .build();

			int procs = Runtime.getRuntime().availableProcessors()+1;
			final ExecutorService pool = Executors.newFixedThreadPool(procs, threadFactory);
			
			System.out.println("CPU核数(+1)：" + procs);

			//线程返回结果集
			List<Future<Map<String, List<CstOrderCostInfo>>>> threadResults = Lists.newArrayList();

			int taskSize = copCaseMap.size();
			int taskNum = 1;
			for (Entry<String, List<CopCaseDetail>> cstCaseDetailInfosEntry : copCaseMap.entrySet()) {
				CstCaseCalcCallable cpq = new CstCaseCalcCallable(taskSize,taskNum,cstCaseDetailInfosEntry,confirmDcPrjIdMap,sameDcPrjIdMap);
				Future<Map<String, List<CstOrderCostInfo>>> f = pool.submit(cpq);  
				threadResults.add(f);
				taskNum ++;
			}

			//拒绝传入新任务
			pool.shutdown();  
			System.out.println("线程池关闭，拒绝传入新任务:"+DateUtils.getDateTime());

			List<CstOrderCostInfo> cstCaseCostInfoList = Lists.newArrayList();
			
			try {
				for(Future<Map<String, List<CstOrderCostInfo>>> fs : threadResults){  
					while(!fs.isDone());//Future返回如果没有完成，则一直循环等待，直到Future返回完
					Map<String, List<CstOrderCostInfo>> map = fs.get();
					for(List<CstOrderCostInfo> subList : map.values()){
						cstCaseCostInfoList.addAll(subList);
					}
				}
				
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException ie) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
				ie.printStackTrace();
				System.out.println(">>><<<线程异常中断："+ie.getMessage());
				System.out.println(">>><<<######线程池状态 isTerminated:"+pool.isTerminated()+",isShutdown:"+pool.isShutdown());
				return null;
			} catch (Exception e) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
				e.printStackTrace();
				System.out.println(">>><<<线程异常中断2： "+e.getMessage());  
				System.out.println(">>><<<######线程池状态 isTerminated:"+pool.isTerminated()+",isShutdown:"+pool.isShutdown());
				return null;
			}
			
			long exportcsv = System.currentTimeMillis();
			String[] fieldNames = CstOrderCostInfo.expFieldNames;
			String[] titles = CstOrderCostInfo.expTitles;
			String fileFullName = cst_filedir + File.separator + "单次case计划(时间截止"+createDateStr+")"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			File file = new File(fileFullName);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstOrderCostInfo.class, cstCaseCostInfoList, fieldNames, titles, 1048575);//最大行1048576
			System.out.println("写入csv耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-exportcsv));
			
			//写入数据库
			//nativeBatchInsert(cstCaseCostInfoList);
			
			boolean isInsertDB = false;
			if(isInsertDB){
				long insertdb = System.currentTimeMillis();
				int batchCount = 2000;
	            int batchLastIndex = batchCount;
				for (int index = 0; index < cstCaseCostInfoList.size();) {
	                if (batchLastIndex >= cstCaseCostInfoList.size()) {
	                    batchLastIndex = cstCaseCostInfoList.size();
	                    cstOrderCostInfoService.addDetailbatch(cstCaseCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    break;// 数据插入完毕，退出循环
	                } else {
	                	cstOrderCostInfoService.addDetailbatch(cstCaseCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    index = batchLastIndex;// 设置下一批下标
	                    batchLastIndex = index + (batchCount - 1);
	                }
	            }
				System.out.println("写入数据库耗时：" + DateUtils.formatDateTime(System.currentTimeMillis()-insertdb));
			}
		}
		
		return "modules/cst/test/prodDetailInfoList";
	 }
	
	/**
	 * 单个文件上传处理
	 * @param file
	 * @return
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "singleExcel")
	public String singleExcel(@RequestParam("singleFile") MultipartFile[] file) throws Exception {
		//对map赋值
		this.putMap();
		// 导入Excel数据
		if (file != null && file.length != 0) {
			cstOrderDetailImport(file);
		}
		
		String inExcelFileName = file[0].getOriginalFilename();
		
		int count = cstOrderDetailInfosMap.size();
		int a = 1;
		int costSum = 0;
		int checkSum = 0;
		if (cstOrderDetailInfosMap != null && cstOrderDetailInfosMap.size() != 0) {
			Map<String, List<CstDetailCostInfo>> cstOrderDetailInfoMaps = new HashMap<String, List<CstDetailCostInfo>>();
			Map<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>> checkProdDetailMaps = new HashMap<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>>();
			
			for (Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry : cstOrderDetailInfosMap.entrySet()) {
				String dcPrjId = cstOrderDetailInfosEntry.getKey();//项目号
				List<CstOrderDetailInfo> value2 = cstOrderDetailInfosEntry.getValue();
				for (CstOrderDetailInfo cstOrderDetailInfo : value2) {
					cstOrderDetailInfo.getOrderId();
				}
				// 计算传入的数据
				System.out.println("一共" + count + "个项目,现在是第" + a + "个," + dcPrjId + ",共" + value2.size() + "行");
				a++;
				Map<String, List<CstDetailCostInfo>> cstOrderDetailInfoMap = CstDetailCostInfoService.getCalculateDetailCost(cstOrderDetailInfosEntry.getValue(), true);
				for (Entry<String, List<CstDetailCostInfo>> cstEntry : cstOrderDetailInfoMap.entrySet()) {
					List<CstDetailCostInfo> value = cstEntry.getValue();
					String key = cstEntry.getKey();
					if (cstOrderDetailInfoMaps == null || cstOrderDetailInfoMaps.size() == 0) {
						cstOrderDetailInfoMaps.put(key, value);
					} else {
						List<CstDetailCostInfo> list = cstOrderDetailInfoMaps.get(key);
						if (list == null || list.size() == 0) {
							cstOrderDetailInfoMaps.put(key, value);
						} else {
							list.addAll(value);
							cstOrderDetailInfoMaps.put(key, list);
						}
					}
				}
				Map<CheckDetailSplit, Map<String, ProdDetailInfo>> checkProdDetailMap = cstDetailCostInfoService.getCheckProdDetailMap(cstOrderDetailInfosEntry.getValue());
				if (checkProdDetailMap != null && checkProdDetailMap.size() != 0) {
					checkProdDetailMaps.put(dcPrjId, checkProdDetailMap);

					List<ProdDetailInfo> list = Lists.newArrayList();
					for(Map<String, ProdDetailInfo> cdciList : checkProdDetailMap.values()){
						for(ProdDetailInfo prodDetailInfo : cdciList.values()) {
							list.add(prodDetailInfo);
						}
					}
					checkSum = checkSum + list.size();
				}
			}

			List<CstDetailCostInfo> list2 = Lists.newArrayList();
			for(List<CstDetailCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				list2.addAll(cdciList);
			}
			costSum = costSum + list2.size();
			
			List<CstDetailCostInfo> dataModels = Lists.newArrayList();
			for(List<CstDetailCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				dataModels.addAll(cdciList);
			}
			/*
			String[] fieldNames = new String[]{"dcPrjId","mfrName","resourceName","detailId","prodId","keyId","resPlan","manCost","feeCost","urgeCost","travlFee","baseFee","beginDate","endDate"};
			String[] titles = new String[]{"项目编号","厂商","型号","明细标识","服务产品","成本标识","资源计划","人工","费用","激励","差旅","其他","服务开始时间","服务结束时间"};
			String fileFullName = "D:\\全项目样例-20161216_"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstDetailCostInfo.class, dataModels, fieldNames, titles, 1048575);//最大行1048576
*/
			ExportExcel ee = new ExportExcel();
			if (cstOrderDetailInfoMaps != null) {
				cstOrderDetailExport(cstOrderDetailInfoMaps, ee);
			}
			if (checkProdDetailMaps != null) {
				checkProdDetailExport(checkProdDetailMaps, ee);
			}
			
			String fileFullName = cst_filedir + File.separator + inExcelFileName + File.separator 
					+ "运维资源计划("+inExcelFileName+")"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			File file2 = new File(fileFullName);
			if (!file2.getParentFile().exists()){
				file2.getParentFile().mkdirs();
			}
			
			ee.writeFile(fileFullName);
			ee.dispose();
			/*int batchCount = 2000;
            int batchLastIndex = batchCount;
			for (int index = 0; index < list2.size();) {
                if (batchLastIndex >= list2.size()) {
                    batchLastIndex = list2.size();
    				cstDetailCostInfoService.addDetailbatch(list2.subList(index, batchLastIndex));
                    break;// 数据插入完毕，退出循环
                } else {
    				cstDetailCostInfoService.addDetailbatch(list2.subList(index, batchLastIndex));
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }*/
			
		}
		return "modules/cst/test/prodDetailInfoList";
	 }
	
	
	@RequestMapping(value = "excel")
	public String excel(@RequestParam("file") MultipartFile[] file) throws Exception {
		long begin = System.currentTimeMillis();
		//对map赋值
		this.putMap();
		long mapEnd = System.currentTimeMillis();
		// 导入Excel数据
		if (file != null && file.length != 0) {
			cstOrderDetailImport(file);
		}
		
		String inExcelFileName = file[0].getOriginalFilename();
		
		long importEnd = System.currentTimeMillis();
		long test1 = 0;
		long test2 = 0;
		long test3 = 0;
		long test4 = 0;
		long exportEnd = 0;
		long exportCstEnd = 0;
		long exportCheckEnd = 0;
		int count = cstOrderDetailInfosMap.size();
		int a = 1;
		int costSum = 0;
		int checkSum = 0;
		long exportcsv = 0;
		long insertdb = 0;
		
		/* 清单明细成本measureId 维度数据：
		 if (cstOrderDetailInfosMap != null && cstOrderDetailInfosMap.size() != 0) {
			Map<String, Map<CheckDetailSplit, List<ProdDetailInfo>>> checkProdDetailMaps = new HashMap<String, Map<CheckDetailSplit,List<ProdDetailInfo>>>();
			Map<String, List<CstDetailCostInfo>> cstOrderDetailInfoMaps = new HashMap<String, List<CstDetailCostInfo>>();
			
			for (Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry : cstOrderDetailInfosMap.entrySet()) {
				String dcPrjId = cstOrderDetailInfosEntry.getKey();//项目号
				// 计算传入的数据
				System.out.println("一共" + count + "个项目,现在是第" + a + "个," + dcPrjId + ",共" + value2.size() + "行");
				a++;
				long cstimMap = System.currentTimeMillis();
				Map<String, List<CstDetailCostInfo>> cstOrderDetailInfoMap = CstDetailCostInfoService.getCalculateDetailCost(cstOrderDetailInfosEntry.getValue(), false);
				long cstMap = System.currentTimeMillis();
				for (Entry<String, List<CstDetailCostInfo>> cstEntry : cstOrderDetailInfoMap.entrySet()) {
					List<CstDetailCostInfo> value = cstEntry.getValue();
					String key = cstEntry.getKey();
					if (cstOrderDetailInfoMaps == null || cstOrderDetailInfoMaps.size() == 0) {
						cstOrderDetailInfoMaps.put(key, value);
					} else {
						List<CstDetailCostInfo> list = cstOrderDetailInfoMaps.get(key);
						if (list == null || list.size() == 0) {
							cstOrderDetailInfoMaps.put(key, value);
						} else {
							list.addAll(value);
							cstOrderDetailInfoMaps.put(key, list);
						}
					}
				}
				long cstOrdEnd = System.currentTimeMillis();
				Map<CheckDetailSplit, List<ProdDetailInfo>> checkProdDetailMap = cstDetailCostInfoService.getCheckProdDetailMap(cstOrderDetailInfosEntry.getValue());
				long checkEnd = System.currentTimeMillis();
				if (checkProdDetailMap != null && checkProdDetailMap.size() != 0) {
					checkProdDetailMaps.put(dcPrjId, checkProdDetailMap);

					List<ProdDetailInfo> list = Lists.newArrayList();
					for(List<ProdDetailInfo> cdciList : checkProdDetailMap.values()){
						list.addAll(cdciList);
					}
					checkSum = checkSum + list.size();
				}
				
				long checkProdEnd = System.currentTimeMillis();
				test1 = test1 + cstMap - cstimMap;
				test2 = test2 + cstOrdEnd - cstMap;
				test3 = test3 + checkEnd - cstOrdEnd;
				test4 = test4 + checkProdEnd - checkEnd;
			}

			List<CstDetailCostInfo> list2 = Lists.newArrayList();
			for(List<CstDetailCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				list2.addAll(cdciList);
			}
			costSum = costSum + list2.size();
			
			exportcsv = System.currentTimeMillis();
			List<CstDetailCostInfo> dataModels = Lists.newArrayList();
			for(List<CstDetailCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				dataModels.addAll(cdciList);
			}
			String[] fieldNames = new String[]{"dcPrjId","detailId","prodId","keyId","resPlan","manCost","feeCost","urgeCost","travlFee","baseFee"};
			String[] titles = new String[]{"项目编号","明细标识","服务产品","成本标识","资源计划","人工","费用","激励","差旅","其他"};
			String fileFullName = "D:\\全项目样例-20161216_"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstDetailCostInfo.class, dataModels, fieldNames, titles, 1048575);//最大行1048576

			insertdb = System.currentTimeMillis();
			int batchCount = 2000;
            int batchLastIndex = batchCount;
			for (int index = 0; index < list2.size();) {
                if (batchLastIndex >= list2.size()) {
                    batchLastIndex = list2.size();
    				//cstDetailCostInfoService.addDetailbatch(list2.subList(index, batchLastIndex));
                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                    break;// 数据插入完毕，退出循环
                } else {
    				//cstDetailCostInfoService.addDetailbatch(list2.subList(index, batchLastIndex));
                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }
			
		}*/
		
		// 清单明细成本聚合后维度数据：
		if (cstOrderDetailInfosMap != null && cstOrderDetailInfosMap.size() != 0) {
			Map<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>> checkProdDetailMaps = new HashMap<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>>();
			Map<String, List<CstOrderCostInfo>> cstOrderDetailInfoMaps = new HashMap<String, List<CstOrderCostInfo>>();
			
			for (Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry : cstOrderDetailInfosMap.entrySet()) {
				String dcPrjId = cstOrderDetailInfosEntry.getKey();//项目号
				// 计算传入的数据
				System.out.println("一共" + count + "个项目,现在是第" + a + "个," + dcPrjId + ",共" + cstOrderDetailInfosEntry.getValue().size() + "行");
				a++;
				long cstimMap = System.currentTimeMillis();
				Map<String, List<CstOrderCostInfo>> cstOrderDetailInfoMap = CstOrderCostInfoService.getCalculateDetailCost(cstOrderDetailInfosEntry.getValue(), false);
				long cstMap = System.currentTimeMillis();
				for (Entry<String, List<CstOrderCostInfo>> cstEntry : cstOrderDetailInfoMap.entrySet()) {
					List<CstOrderCostInfo> value = cstEntry.getValue();
					String key = cstEntry.getKey();
					if (cstOrderDetailInfoMaps == null || cstOrderDetailInfoMaps.size() == 0) {
						cstOrderDetailInfoMaps.put(key, value);
					} else {
						List<CstOrderCostInfo> list = cstOrderDetailInfoMaps.get(key);
						if (list == null || list.size() == 0) {
							cstOrderDetailInfoMaps.put(key, value);
						} else {
							list.addAll(value);
							cstOrderDetailInfoMaps.put(key, list);
						}
					}
				}
				long cstOrdEnd = System.currentTimeMillis();
				Map<CheckDetailSplit, Map<String, ProdDetailInfo>> checkProdDetailMap = cstDetailCostInfoService.getCheckProdDetailMap(cstOrderDetailInfosEntry.getValue());
				long checkEnd = System.currentTimeMillis();
				if (checkProdDetailMap != null && checkProdDetailMap.size() != 0) {
					checkProdDetailMaps.put(dcPrjId, checkProdDetailMap);

					List<ProdDetailInfo> list = Lists.newArrayList();
					for(Map<String, ProdDetailInfo> cdciList : checkProdDetailMap.values()){
						for(ProdDetailInfo prodDetailInfo : cdciList.values()) {
							list.add(prodDetailInfo);
						}
					}
					checkSum = checkSum + list.size();
				}
				
				long checkProdEnd = System.currentTimeMillis();
				test1 = test1 + cstMap - cstimMap;
				test2 = test2 + cstOrdEnd - cstMap;
				test3 = test3 + checkEnd - cstOrdEnd;
				test4 = test4 + checkProdEnd - checkEnd;
			}

			List<CstOrderCostInfo> cstOrderCostInfoList = Lists.newArrayList();
			for(List<CstOrderCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				cstOrderCostInfoList.addAll(cdciList);
			}
			costSum = costSum + cstOrderCostInfoList.size();
			
			String[] fieldNames = CstOrderCostInfo.expFieldNames;
			String[] titles = CstOrderCostInfo.expTitles;

			exportcsv = System.currentTimeMillis();
			String fileFullName = cst_filedir + File.separator + inExcelFileName + File.separator 
					+ "运维资源计划("+inExcelFileName+")"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			File file3 = new File(fileFullName);
			if (!file3.getParentFile().exists()){
				file3.getParentFile().mkdirs();
			}
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstOrderCostInfo.class, cstOrderCostInfoList, fieldNames, titles, 1048575);//最大行1048576

			insertdb = System.currentTimeMillis();
			
			//写入数据库
			//nativeBatchInsert(cstOrderCostInfoList);

			boolean isInsertDB = false;
			if(isInsertDB){
				int batchCount = 1000;
	            int batchLastIndex = batchCount;
				for (int index = 0; index < cstOrderCostInfoList.size();) {
	                if (batchLastIndex >= cstOrderCostInfoList.size()) {
	                    batchLastIndex = cstOrderCostInfoList.size();
	    				cstOrderCostInfoService.addDetailbatch(cstOrderCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    break;// 数据插入完毕，退出循环
	                } else {
	                	cstOrderCostInfoService.addDetailbatch(cstOrderCostInfoList.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    index = batchLastIndex;// 设置下一批下标
	                    batchLastIndex = index + (batchCount - 1);
	                }
	            }
			}
			
		}
		
		
		long end = System.currentTimeMillis();
		System.out.println("系统运行时间1------------》》》》》》》" + DateUtils.formatDateTime(end - begin));//全部时间
		System.out.println("对map赋值时间2------------》》》》》》》" + DateUtils.formatDateTime(mapEnd - begin));//对map赋值时间
		System.out.println("导入文件时间3------------》》》》》》》" + DateUtils.formatDateTime(importEnd - mapEnd));//导入文件时间
		System.out.println("成本计算时间4------------》》》》》》》" + DateUtils.formatDateTime(test1));
		System.out.println("成本加入集合时间5------------》》》》》》》" + DateUtils.formatDateTime(test2));
		System.out.println("巡检拆分时间6------------》》》》》》》" + DateUtils.formatDateTime(test3));
		System.out.println("巡检拆分加入集合时间7------------》》》》》》》" + DateUtils.formatDateTime(test4));
		System.out.println("导出成本时间8------------》》》》》》》" + DateUtils.formatDateTime(exportCstEnd - exportEnd));
		System.out.println("导出巡检拆分时间9------------》》》》》》》" + DateUtils.formatDateTime(exportCheckEnd - exportCstEnd));
		System.out.println("计算导出总时间10------------》》》》》》》" + DateUtils.formatDateTime(end - importEnd));
		System.out.println("导出csv------------》》》》》》》" + DateUtils.formatDateTime(insertdb - exportcsv));
		System.out.println("写入数据库时间11------------》》》》》》》" + DateUtils.formatDateTime(end - insertdb));
		System.out.println("成本及资源计划总条数: "+costSum);
		System.out.println("巡检拆分总条数: "+checkSum);
		
		return "modules/cst/test/prodDetailInfoList";
	 }
	
	/**
	 * 解析excel文件数据
	 * @param file
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	
	public void cstOrderDetailImport(MultipartFile[] file) throws InvalidFormatException, IOException{
		
		ImportExcel importExcel = new ImportExcel(file[0], 1, 0);
		int lastCellNum = importExcel.getLastCellNum();		//获取最后一个列号
		int lastDataRowNum = importExcel.getLastDataRowNum();	//获取最后一个数据行号
		
		cstOrderDetailInfosMap = new LinkedHashMap<String, List<CstOrderDetailInfo>>();
		//获取表头
		Row headRow = importExcel.getRow(0);
		for (int i = 1; i < lastDataRowNum; i++) {
			//获取数据行
			Row row = importExcel.getRow(i);
			CstOrderDetailInfo cstOrderDetailInfo = new CstOrderDetailInfo();
			for (int j = 0; j < lastCellNum; j++) {
				//获取表头数据
				Object headCellValue = importExcel.getCellValue(headRow, j);
				//获取每一行的数据
				Object dataCellValue = importExcel.getCellValue(row, j);
				String fieldName = propertyMap.get(headCellValue);
				if (fieldName != "" && fieldName != null) {
					if ("checkN,checkD,checkF".contains(fieldName)) {
						Class<? extends Object> dataClass = dataCellValue.getClass();
						if (dataClass.getName().equals("java.lang.String")) {
							long longValue;
							if ((String)dataCellValue == "") {
								longValue = 0;
							} else {
								longValue = Long.parseLong((String)dataCellValue);
							}
							Reflections.setFieldValue(cstOrderDetailInfo, fieldName, longValue);
						}
						if (dataClass.getName().equals("java.lang.Double")) {
							Double double1 = (Double) dataCellValue;
							long longValue = double1.longValue();
							Reflections.setFieldValue(cstOrderDetailInfo, fieldName, longValue);
						}
					} else if ("beginDate,endDate".contains(fieldName)) {
						Reflections.setFieldValue(cstOrderDetailInfo, fieldName, dataCellValue);
					} else if ("resourceName".contains(fieldName)) {
						Class<? extends Object> dataClass = dataCellValue.getClass();
						if (dataClass.getName().equals("java.lang.Double")) {
							String string = dataCellValue.toString()+"";
							String split = string.substring(0, string.length()-2);
							Reflections.setFieldValue(cstOrderDetailInfo, fieldName, split);
						} else {
							String value = dataCellValue.toString();
							Reflections.setFieldValue(cstOrderDetailInfo, fieldName, value);
						}
					} else if ("workkindScale".contains(fieldName)) {
						if (dataCellValue.toString().equals("0.0")) {
							Reflections.setFieldValue(cstOrderDetailInfo, fieldName, "0");
						} else {
							String value = dataCellValue.toString();
							Reflections.setFieldValue(cstOrderDetailInfo, fieldName, value);
						}
					} else {
						String value = dataCellValue.toString();
						Reflections.setFieldValue(cstOrderDetailInfo, fieldName, value);
					}
				}
			}
			//按照项目号聚合
			if (cstOrderDetailInfosMap == null || cstOrderDetailInfosMap.size() == 0) {
				List<CstOrderDetailInfo> cstOrderDetailInfos = new LinkedList<CstOrderDetailInfo>();
				cstOrderDetailInfos.add(cstOrderDetailInfo);
				cstOrderDetailInfosMap.put(cstOrderDetailInfo.getOrderId(), cstOrderDetailInfos);
			} else {
				List<CstOrderDetailInfo> cstOrderDetailInfos = cstOrderDetailInfosMap.get(cstOrderDetailInfo.getOrderId());
				if (cstOrderDetailInfos != null && cstOrderDetailInfos.size() != 0) {
					cstOrderDetailInfos.add(cstOrderDetailInfo);
					cstOrderDetailInfosMap.put(cstOrderDetailInfo.getOrderId(), cstOrderDetailInfos);
				} else {
					cstOrderDetailInfos = new LinkedList<CstOrderDetailInfo>();
					cstOrderDetailInfos.add(cstOrderDetailInfo);
					cstOrderDetailInfosMap.put(cstOrderDetailInfo.getOrderId(), cstOrderDetailInfos);
				}
			}
		}
	}
	
	/**
	 * 成本计算结果导出
	 * @param cstOrderDetailInfoMap
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	
	public void cstOrderDetailExport(Map<String, List<CstDetailCostInfo>> cstOrderDetailInfoMap, ExportExcel ee) throws FileNotFoundException, IOException{
		
		//表头
		List<String> headerList = new LinkedList<String>();
		for (Entry<String, String> entry : titleMap.entrySet()) {
			headerList.add(entry.getKey());
		}
		
		List<List<String>> dataList = new LinkedList<List<String>>();
		List<String> sheetTitleList = new LinkedList<String>();
		for (Entry<String, List<CstDetailCostInfo>> cstDetailCostInfos : cstOrderDetailInfoMap.entrySet()) {
			sheetTitleList.add(cstDetailCostInfos.getKey());
			List<CstDetailCostInfo> value = cstDetailCostInfos.getValue();
			dataList = new LinkedList<List<String>>();
			for (CstDetailCostInfo cstDetailCostInfo : value) {
				List<String> dataRowList = new LinkedList<String>();
				dataList = new LinkedList<List<String>>();
				for (String string : headerList) {
					Object fieldValue = Reflections.getFieldValue(cstDetailCostInfo, titleMap.get(string));
					if (fieldValue == null) {
						fieldValue = "";
					}
					dataRowList.add(fieldValue.toString());
				}
				dataList.add(dataRowList);
			}
		}
		//创建excel表格
		ee.createExcel();
		int headSize = headerList.size();
		for (String sheetName : sheetTitleList) {
			ee.createSheet(sheetName);
			ee.createHeader(headerList);
			List<CstDetailCostInfo> cstDetailCostInfoList = cstOrderDetailInfoMap.get(sheetName);
			for (CstDetailCostInfo cstDetailCostInfo : cstDetailCostInfoList) {
				Row row = ee.addRow();
				for (int j = 0; j < headSize; j++) {
					if ("beginDate,endDate".contains(titleMap.get(headerList.get(j)))) {
						Date fieldValue = (Date) Reflections.getFieldValue(cstDetailCostInfo, titleMap.get(headerList.get(j)));
						ee.addCell(row, j, DateUtils.formatDate(fieldValue, "yyyy-MM-dd")).setCellStyle(null);
					} else {
						Object fieldValue = Reflections.getFieldValue(cstDetailCostInfo, titleMap.get(headerList.get(j)));
						ee.addCell(row, j, fieldValue).setCellStyle(null);
					}
				}
			}
			ee.clear();
		}
	}
	
	/**
	 * 巡检拆分结果导出
	 * 
	 * @param checkProdDetailMap
	 * @throws ParseException
	 */
	private void checkProdDetailExport(Map<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>> checkProdDetailMap, ExportExcel ee) throws ParseException {
		
		//表头
		List<String> headerList = new LinkedList<String>();
		List<String> dateList = new LinkedList<String>();
		for (Entry<String, String> header : prodDetailMap.entrySet()) {
			String key = header.getKey();
			headerList.add(key);
		}
		
		//表头标题
		Map<String, List<String>> titleListMap = new HashMap<String, List<String>>();
		List<String> dcPrjTitleList = new LinkedList<String>();
		Map<String, Map<String, ProdDetailInfo>> prodDetailInfosMap = new HashMap<String, Map<String, ProdDetailInfo>>();
		for (Entry<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>> entry : checkProdDetailMap.entrySet()) {
			Map<String, ProdDetailInfo> prodDetailInfos = null;
			//项目号
			String dcPrj = entry.getKey();
			//项目加入标题集合中
			dcPrjTitleList.add(dcPrj);
			for (Entry<CheckDetailSplit, Map<String, ProdDetailInfo>> checkProdEntry : entry.getValue().entrySet()) {
				CheckDetailSplit key = checkProdEntry.getKey();
				//ProdDetailInfo集合
				prodDetailInfos = checkProdEntry.getValue();
				
				//<巡检类型，<年月,<同城市同设备大类, 巡检台次>>> 
				List<String> titleList = new LinkedList<String>();
				for (Entry<String, Map<String, Map<String, Double>>> splitMonthEntry : key.getSplitMonthMap().entrySet()) {
					titleList.add(splitMonthEntry.getKey());
					Set<Entry<String,Map<String,Double>>> dateEntrySet = splitMonthEntry.getValue().entrySet();
					for (Entry<String, Map<String, Double>> dateEntry : dateEntrySet) {
						dateList.add(dateEntry.getKey());
					}
				}
				titleListMap.put(dcPrj, titleList);
				prodDetailInfosMap.put(dcPrj, prodDetailInfos);
			}
			//排序，时间拆分
			Collections.sort(dateList);
			if (dateList != null && dateList.size() != 0) {
				SimpleDateFormat sim=new SimpleDateFormat("yyyyMM");
				List<String> monthBetween = DateUtils.getMonthBetween(sim.parse(dateList.get(0)), sim.parse(dateList.get(dateList.size() - 1)));
				headerList.addAll(monthBetween);
			}
		}
		ee.createSheet("巡检拆分");
		for (String dcPrjTitle : dcPrjTitleList) {
			//项目号标题
			ee.createTitle(dcPrjTitle);
			//巡检次数以及数据
			for (String titleEntry : titleListMap.get(dcPrjTitle)) {
				ee.createTitle(checkMap.get(titleEntry));
				ee.createHeader(headerList);
				for (ProdDetailInfo prodDetailInfo : prodDetailInfosMap.get(dcPrjTitle).values()) {
					Map<String, Double> prodMap = prodDetailInfo.getMonthCheckNumMap().get(titleEntry);
					if (prodMap != null && prodMap.size() != 0) {
						Row row = ee.addRow();
						for (int i = 0; i < headerList.size(); i++) {
							Double double1 = prodMap.get(headerList.get(i));
							if (double1 != null && double1 != 0) {
								ee.addCell(row, i, double1).setCellStyle(null);
							}else {
								String fieldName = prodDetailMap.get(headerList.get(i));
								if (fieldName != null && fieldName != "") {
									Object fieldValue = Reflections.getFieldValue(prodDetailInfo, fieldName);
									ee.addCell(row, i, fieldValue).setCellStyle(null);
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * Map赋值
	 */
	private Map<String, String> propertyMap;
	private Map<String, String> titleMap;
	private Map<String, String> prodDetailMap;
	private Map<String, String> checkMap;
	
	public void putMap(){
		propertyMap = new LinkedHashMap<String, String>();
		propertyMap.put("明细标识","detailId");
		propertyMap.put("项目编号","dcPrjId");
		propertyMap.put("订单编号","orderId");
		propertyMap.put("服务产品","prodName");
//			propertyMap.put("资源ID","resourceId");
//			propertyMap.put("类型","detailType");
		propertyMap.put("厂商","mfrName");
		propertyMap.put("型号组","detailModel");
		propertyMap.put("型号","resourceName");
		propertyMap.put("地市","city");
		propertyMap.put("服务级别","slaName");
		propertyMap.put("服务开始时间","beginDate");
		propertyMap.put("服务结束时间","endDate");
		propertyMap.put("现场次数","checkN");
		propertyMap.put("远程次数","checkF");
		propertyMap.put("深度次数","checkD");
		propertyMap.put("资源数量","amount");
		propertyMap.put("即时性","urgent");
		propertyMap.put("复用比例","workkindScale");
		propertyMap.put("产品服务方式","prodServiceModel");
		propertyMap.put("核定人工成本","finalManCost");
		propertyMap.put("核定风险及能力成本","finalRiskAbilityCost");
		propertyMap.put("核定人工激励成本","finalUrgeCost");

		titleMap = new LinkedHashMap<String, String>();
		titleMap.put("项目编号", "dcPrjId");;
		titleMap.put("明细标识", "detailId");
		titleMap.put("成本标识", "keyId");
		titleMap.put("厂商", "mfrName");
		titleMap.put("型号", "resourceName");
		titleMap.put("服务开始时间", "beginDate");
		titleMap.put("服务结束时间", "endDate");
//			titleMap.put("服务产品", "prodId");
		titleMap.put("资源计划", "resPlan");
		titleMap.put("人工", "manCost");
		titleMap.put("费用", "feeCost");
		titleMap.put("激励", "urgeCost");
		titleMap.put("差旅", "travlFee");
//			titleMap.put("其他", "baseFee");

		prodDetailMap = new LinkedHashMap<String, String>();
		prodDetailMap.put("明细标识", "detailId");
		prodDetailMap.put("项目编号", "dcPrjId");
//			prodDetailMap.put("订单编号", "orderId");
//			prodDetailMap.put("资源ID", "resourceId");
//			prodDetailMap.put("类型", "detailType");
		prodDetailMap.put("厂商", "mfrName");
		prodDetailMap.put("型号组", "detailModel");
		prodDetailMap.put("开始时间", "serviceBegin");
		prodDetailMap.put("截止时间", "serviceEnd");

		// 现场巡检-BUYCHECKN 远程巡检次数-BUYFARCHK 深度巡检-BUYDEPCHK
		checkMap = new HashMap<String, String>();
		checkMap.put("BUYCHECKN", "现场巡检");
		checkMap.put("BUYFARCHK", "远程巡检");
		checkMap.put("BUYDEPCHK", "深度巡检");
	}
	
	@RequestMapping(value = "execBaseCost")
	public String execBaseCost() throws Exception {

		List<String> execOrgName = new ArrayList<String>();
		execOrgName.add("运维");
		execOrgName.add("运维-快捷");
		List<String> supportType = new ArrayList<String>();
		supportType.add("自有");
		supportType.add("备件分包");
		List<String> costClassName = new ArrayList<String>();
		costClassName.add("其它");
		costClassName.add("响应支持");
		costClassName.add("主动服务");
		costClassName.add("云产品");
		List<String> prodServiceMode = new ArrayList<String>();
		prodServiceMode.add("硬件");
		prodServiceMode.add("软件");
		prodServiceMode.add("硬件不含二线");
		prodServiceMode.add("软件不含二线");
		prodServiceMode.add("硬件非MA");
		prodServiceMode.add("软件非MA");
		List<String> prodStatType = null;
		String createDateStr = "20170228";
		String pdId = "PD1502151927271";
		String dcPrjId = "RTPAFZS110";
		boolean isInsertDB = false;//计算结果是否写入库
		
		List<CstOrderDetailInfo> baseList = cstOrderDetailInfoService.getWbmBaseList(execOrgName, supportType, costClassName, prodServiceMode, null, prodStatType, createDateStr, pdId, dcPrjId);
		
		cstOrderDetailInfosMap = new LinkedHashMap<String, List<CstOrderDetailInfo>>();

		long begin = System.currentTimeMillis();
		//对map赋值
		this.putMap();
		long mapEnd = System.currentTimeMillis();
		long importEnd = System.currentTimeMillis();
		long test1 = 0;
		long test2 = 0;
		long test3 = 0;
		long test4 = 0;
		long exportEnd = 0;
		long exportCstEnd = 0;
		long exportCheckEnd = 0;
		int count = cstOrderDetailInfosMap.size();
		int a = 1;
		int costSum = 0;
		int checkSum = 0;
		long exportcsv = 0;
		long insertdb = 0;
		
		//按照订单号聚合
		for(CstOrderDetailInfo info : baseList) {
			if(cstOrderDetailInfosMap.get(info.getOrderId()) == null) {
				cstOrderDetailInfosMap.put(info.getOrderId(), new LinkedList<CstOrderDetailInfo>());
			}
			cstOrderDetailInfosMap.get(info.getOrderId()).add(info);
		}
		

		// 清单明细成本聚合后维度数据：
		if (cstOrderDetailInfosMap != null && cstOrderDetailInfosMap.size() != 0) {
			Map<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>> checkProdDetailMaps = new HashMap<String, Map<CheckDetailSplit, Map<String, ProdDetailInfo>>>();
			Map<String, List<CstOrderCostInfo>> cstOrderDetailInfoMaps = new HashMap<String, List<CstOrderCostInfo>>();
			
			for (Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry : cstOrderDetailInfosMap.entrySet()) {
				String orderId = cstOrderDetailInfosEntry.getKey();//订单号
				// 计算传入的数据
				System.out.println("一共" + count + "个订单,现在是第" + a + "个," + orderId + ",共" + cstOrderDetailInfosEntry.getValue().size() + "行");
				a++;
				long cstimMap = System.currentTimeMillis();
				Map<String, List<CstOrderCostInfo>> cstOrderDetailInfoMap = CstOrderCostInfoService.getCalculateDetailCost(cstOrderDetailInfosEntry.getValue(), false);
				long cstMap = System.currentTimeMillis();
				for (Entry<String, List<CstOrderCostInfo>> cstEntry : cstOrderDetailInfoMap.entrySet()) {
					List<CstOrderCostInfo> value = cstEntry.getValue();
					String key = cstEntry.getKey();
					if (cstOrderDetailInfoMaps == null || cstOrderDetailInfoMaps.size() == 0) {
						cstOrderDetailInfoMaps.put(key, value);
					} else {
						List<CstOrderCostInfo> list = cstOrderDetailInfoMaps.get(key);
						if (list == null || list.size() == 0) {
							cstOrderDetailInfoMaps.put(key, value);
						} else {
							list.addAll(value);
							cstOrderDetailInfoMaps.put(key, list);
						}
					}
				}
				long cstOrdEnd = System.currentTimeMillis();
				Map<CheckDetailSplit, Map<String, ProdDetailInfo>> checkProdDetailMap = cstDetailCostInfoService.getCheckProdDetailMap(cstOrderDetailInfosEntry.getValue());
				long checkEnd = System.currentTimeMillis();
				if (checkProdDetailMap != null && checkProdDetailMap.size() != 0) {
					checkProdDetailMaps.put(orderId, checkProdDetailMap);
					List<ProdDetailInfo> list = Lists.newArrayList();
					for(Map<String, ProdDetailInfo> cdciList : checkProdDetailMap.values()){
						for(ProdDetailInfo prodDetailInfo : cdciList.values()) {
							list.add(prodDetailInfo);
						}
					}
					checkSum = checkSum + list.size();
				}
				
				long checkProdEnd = System.currentTimeMillis();
				test1 = test1 + cstMap - cstimMap;
				test2 = test2 + cstOrdEnd - cstMap;
				test3 = test3 + checkEnd - cstOrdEnd;
				test4 = test4 + checkProdEnd - checkEnd;
			}

			List<CstOrderCostInfo> list2 = Lists.newArrayList();
			for(List<CstOrderCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				list2.addAll(cdciList);
			}
			costSum = costSum + list2.size();
			
			exportcsv = System.currentTimeMillis();
			List<CstOrderCostInfo> dataModels = Lists.newArrayList();
			for(List<CstOrderCostInfo> cdciList : cstOrderDetailInfoMaps.values()){
				dataModels.addAll(cdciList);
			}
			String[] fieldNames = CstOrderCostInfo.expFieldNames;
			String[] titles = CstOrderCostInfo.expTitles;

			String fileFullName = cst_filedir + File.separator + "运维资源计划(基础表截止"+createDateStr+")"+DateUtils.getDate("yyyyMMddHHmmss")+".csv";
			File file = new File(fileFullName);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			CsvHelper csvHelper = OpenCsvHelper.getInstance(fileFullName);
			csvHelper.writeCsv(CstOrderCostInfo.class, dataModels, fieldNames, titles, 1048575);//最大行1048576

			if(isInsertDB){
				insertdb = System.currentTimeMillis();
				int batchCount = 1000;
	            int batchLastIndex = batchCount;
				for (int index = 0; index < list2.size();) {
	                if (batchLastIndex >= list2.size()) {
	                    batchLastIndex = list2.size();
	    				cstOrderCostInfoService.addDetailbatch(list2.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    break;// 数据插入完毕，退出循环
	                } else {
	                	cstOrderCostInfoService.addDetailbatch(list2.subList(index, batchLastIndex));
	                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
	                    index = batchLastIndex;// 设置下一批下标
	                    batchLastIndex = index + (batchCount - 1);
	                }
	            }
			}
			
		}
		
		long end = System.currentTimeMillis();
		System.out.println("系统运行时间1------------》》》》》》》" + DateUtils.formatDateTime(end - begin));//全部时间
		System.out.println("对map赋值时间2------------》》》》》》》" + DateUtils.formatDateTime(mapEnd - begin));//对map赋值时间
		System.out.println("导入文件时间3------------》》》》》》》" + DateUtils.formatDateTime(importEnd - mapEnd));//导入文件时间
		System.out.println("成本计算时间4------------》》》》》》》" + DateUtils.formatDateTime(test1));
		System.out.println("成本加入集合时间5------------》》》》》》》" + DateUtils.formatDateTime(test2));
		System.out.println("巡检拆分时间6------------》》》》》》》" + DateUtils.formatDateTime(test3));
		System.out.println("巡检拆分加入集合时间7------------》》》》》》》" + DateUtils.formatDateTime(test4));
		System.out.println("导出成本时间8------------》》》》》》》" + DateUtils.formatDateTime(exportCstEnd - exportEnd));
		System.out.println("导出巡检拆分时间9------------》》》》》》》" + DateUtils.formatDateTime(exportCheckEnd - exportCstEnd));
		System.out.println("计算导出总时间10------------》》》》》》》" + DateUtils.formatDateTime(end - importEnd));
		System.out.println("导出csv------------》》》》》》》" + DateUtils.formatDateTime(insertdb - exportcsv));
		System.out.println("写入数据库时间11------------》》》》》》》" + DateUtils.formatDateTime(end - insertdb));
		System.out.println("成本及资源计划总条数: "+costSum);
		System.out.println("巡检拆分总条数: "+checkSum);
		
		return "modules/cst/test/prodDetailInfoList";
	}
	
	/*private Map<String, String> getPropertyMap(){
		Map<String, String> propertyMap = new LinkedHashMap<String, String>();
		propertyMap.put("明细标识","detailId");
		propertyMap.put("项目编号","dcPrjId");
//		propertyMap.put("订单编号","orderId");
		propertyMap.put("服务产品","prodId");
//		propertyMap.put("资源ID","resourceId");
//		propertyMap.put("类型","detailType");
		propertyMap.put("厂商","mfrName");
		propertyMap.put("型号组","detailModel");
		propertyMap.put("型号","resourceName");
		propertyMap.put("地市","cityId");
		propertyMap.put("服务级别","slaId");
		propertyMap.put("服务开始时间","beginDate");
		propertyMap.put("服务结束时间","endDate");
		propertyMap.put("现场次数","checkN");
		propertyMap.put("远程次数","checkF");
		propertyMap.put("深度次数","checkD");
		propertyMap.put("资源数量","amount");
		return propertyMap;
	}
	private Map<String, String> getTitleMap(){
		Map<String, String> titleMap = new LinkedHashMap<String, String>();
		titleMap.put("明细标识", "detailId");
		titleMap.put("成本标识", "keyId");
//		titleMap.put("服务产品", "prodId");
		titleMap.put("资源计划", "resPlan");
		titleMap.put("人工", "manCost");
		titleMap.put("费用", "feeCost");
		titleMap.put("激励", "urgeCost");
		titleMap.put("差旅", "travlFee");
//		titleMap.put("其他", "baseFee");
		return titleMap;
	}
	private Map<String, String> getProdDetailMap(){
		Map<String, String> prodDetailMap = new LinkedHashMap<String, String>();
		prodDetailMap.put("明细标识", "detailId");
		prodDetailMap.put("项目编号", "dcPrjId");
//		prodDetailMap.put("订单编号", "orderId");
//		prodDetailMap.put("资源ID", "resourceId");
//		prodDetailMap.put("类型", "detailType");
		prodDetailMap.put("厂商", "mfrName");
		prodDetailMap.put("型号组", "detailModel");
		prodDetailMap.put("开始时间", "serviceBegin");
		prodDetailMap.put("截止时间", "serviceEnd");
		return prodDetailMap;
	}
	private Map<String, String> getCheckMap(){
		Map<String, String> checkMap = new LinkedHashMap<String, String>();
		checkMap.put("BUYCHECKN", "现场巡检");
		checkMap.put("BUYFARCHK", "远程巡检");
		checkMap.put("BUYDEPCHK", "深度巡检");
		return checkMap;
	}*/
	
	@Test
	public void test() throws FileNotFoundException, IOException{
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(map == null);
	}
}

class CstOrderCalcCallable implements Callable<Map<String, List<CstOrderCostInfo>>>{
    private Integer taskSize;
    private Integer taskNum;
    private Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry;
    
    public CstOrderCalcCallable(Integer taskSize, Integer taskNum, Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry) {
		this.taskSize = taskSize;
		this.taskNum = taskNum;
		this.cstOrderDetailInfosEntry = cstOrderDetailInfosEntry;
	}
    
	@Override
    public Map<String, List<CstOrderCostInfo>> call() throws InterruptedException {
		Map<String, List<CstOrderCostInfo>> cstOrderDetailInfoMap = null;
		//try {
			System.out.println(">>>[" + taskSize + "] 第(" + taskNum + ")个任务启动 ");  
			long s = System.currentTimeMillis();
			cstOrderDetailInfoMap = CstOrderCostInfoService.getCalculateDetailCost(cstOrderDetailInfosEntry.getValue(), false);
			long e = System.currentTimeMillis();
			System.out.println("<<<<<<[" + taskSize + "] 第(" + taskNum + ")个任务结束,项目号："+cstOrderDetailInfosEntry.getKey()+"("+cstOrderDetailInfosEntry.getValue().size()+")，耗时："+DateUtils.formatDateTime(e-s));
			
			TimeUnit.MILLISECONDS.sleep(100);
		/*} catch (InterruptedException e1) {
			System.out.println(">>><<<线程sleep异常中断： "+e1.getMessage());  
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println(">>><<<线程异常中断： "+e.getMessage());  
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}*/
		
        return cstOrderDetailInfoMap;
    }  
}  

class CstNewOrderCalcCallable implements Callable<Map<String, List<CstNewOrderCostInfo>>>{
    private Integer taskSize;
    private Integer taskNum;
    private Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry;
    
    public CstNewOrderCalcCallable(Integer taskSize, Integer taskNum, Entry<String, List<CstOrderDetailInfo>> cstOrderDetailInfosEntry) {
		this.taskSize = taskSize;
		this.taskNum = taskNum;
		this.cstOrderDetailInfosEntry = cstOrderDetailInfosEntry;
	}
    
	@Override
    public Map<String, List<CstNewOrderCostInfo>> call() throws InterruptedException {
		Map<String, List<CstNewOrderCostInfo>> cstOrderDetailInfoMap = null;
		//try {
			System.out.println(">>>[" + taskSize + "] 第(" + taskNum + ")个任务启动 ");  
			long s = System.currentTimeMillis();
			cstOrderDetailInfoMap = CstNewOrderCostInfoService.getCalculateDetailCost(cstOrderDetailInfosEntry.getValue(), false);
			long e = System.currentTimeMillis();
			System.out.println("<<<<<<[" + taskSize + "] 第(" + taskNum + ")个任务结束,项目号："+cstOrderDetailInfosEntry.getKey()+"("+cstOrderDetailInfosEntry.getValue().size()+")，耗时："+DateUtils.formatDateTime(e-s));
			
			TimeUnit.MILLISECONDS.sleep(100);
		/*} catch (InterruptedException e1) {
			System.out.println(">>><<<线程sleep异常中断： "+e1.getMessage());  
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println(">>><<<线程异常中断： "+e.getMessage());  
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}*/
		
        return cstOrderDetailInfoMap;
    }  
}  

class CstCaseCalcCallable implements Callable<Map<String, List<CstOrderCostInfo>>>{
    private Integer taskSize;
    private Integer taskNum;
    private Entry<String, List<CopCaseDetail>> cstCaseDetailInfosEntry;
    private Map<String, List<CopSalesUseDetail>> confirmDcPrjIdMap;
    private Map<String, List<CopSalesUseDetail>> sameDcPrjIdMap;
    
    public CstCaseCalcCallable(Integer taskSize, Integer taskNum, Entry<String, List<CopCaseDetail>> cstCaseDetailInfosEntry, 
    		Map<String, List<CopSalesUseDetail>> confirmDcPrjIdMap, Map<String, List<CopSalesUseDetail>> sameDcPrjIdMap) {
		this.taskSize = taskSize;
		this.taskNum = taskNum;
		this.cstCaseDetailInfosEntry = cstCaseDetailInfosEntry;
		this.confirmDcPrjIdMap = confirmDcPrjIdMap;
		this.sameDcPrjIdMap = sameDcPrjIdMap;
	}
    
	@Override
    public Map<String, List<CstOrderCostInfo>> call() throws InterruptedException {
		Map<String, List<CstOrderCostInfo>> caseCostMap = null;
		//try {
			System.out.println(">>>[" + taskSize + "] 第(" + taskNum + ")个任务启动 ");  
			long s = System.currentTimeMillis();
			
			List<CopCaseDetail> copCaseList = cstCaseDetailInfosEntry.getValue();
			CopCaseDetail ccd = copCaseList.get(0);
			String payType = ccd.getPayType();
			String dcprjId = ccd.getOnceCode();
			if("1".equals(payType)) {
				// 1-预付费   取支付项目编号
				if(confirmDcPrjIdMap.get(cstCaseDetailInfosEntry.getKey()) != null) {
					dcprjId = confirmDcPrjIdMap.get(cstCaseDetailInfosEntry.getKey()).get(0).getDcPrjId();
				} else {
					dcprjId = ccd.getDcPrjId();
				}
			}
			
			caseCostMap = CstOrderCostInfoService.getCalculateCaseDetailCost(copCaseList, payType, dcprjId, sameDcPrjIdMap);
				
			long e = System.currentTimeMillis();
			System.out.println("<<<<<<[" + taskSize + "] 第(" + taskNum + ")个任务结束,项目号："+cstCaseDetailInfosEntry.getKey()+"("+cstCaseDetailInfosEntry.getValue().size()+")，耗时："+DateUtils.formatDateTime(e-s));
			
			TimeUnit.MILLISECONDS.sleep(100);
		/*} catch (InterruptedException e1) {
			System.out.println(">>><<<线程sleep异常中断： "+e1.getMessage());  
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println(">>><<<线程异常中断： "+e.getMessage());  
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}*/
		
        return caseCostMap;
    }  
} 