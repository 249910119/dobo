package com.dobo.modules.fc.rest;


public class TestCost {/*

	public static void main(String[] args) throws UnsupportedEncodingException {

		for(Future<String> fs : threadResults){  
			projectOrderDetailList.add(fs.get());
        }
		
		System.out.println("##################:"+projectOrderDetailList.size());

		try {
			// 设置最长等待100毫秒
			threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//释放内存占用
		threadResults.clear();
	}

	public static void main(String[] args) throws Exception {
		
		String data = "{\"data\":{\"projectCode\":\"AV2AH70UNX\",\"hasWbmOrder\":\"A1\",\"fcPlanReceiptDetailRestList\":[{\"planReceiptDays\":0,\"planReceiptScale\":0.5,\"payType\":\"02\",\"displayOrder\":1,\"planReceiptAmount\":395000,\"planReceiptTime\":1518364800000},{\"planReceiptDays\":14,\"planReceiptScale\":0.4,\"payType\":\"02\",\"displayOrder\":2,\"planReceiptAmount\":316000,\"planReceiptTime\":1520438400000},{\"planReceiptDays\":125,\"planReceiptScale\":0.1,\"payType\":\"02\",\"displayOrder\":3,\"planReceiptAmount\":79000,\"planReceiptTime\":1535644800000}],\"salesName\":\"郭园清\",\"signNetMoney\":675213.68,\"signMoney\":790000,\"saleOrg\":\"金融政企二事业一部(FY17)\",\"fcOrderInfoRestList\":[],\"fcPlanPayDetailRestList\":[{\"planPayScale\":0.44799999999999995,\"planPayAmount\":353750,\"planPayDays\":0,\"payType\":\"02\",\"displayOrder\":1,\"planPayTime\":1518624000000},{\"planPayScale\":0.358,\"planPayAmount\":283000,\"planPayDays\":25,\"payType\":\"02\",\"displayOrder\":2,\"planPayTime\":1520784000000},{\"planPayScale\":0.09,\"planPayAmount\":70750,\"planPayDays\":177,\"payType\":\"02\",\"displayOrder\":3,\"planPayTime\":1536076800000}],\"sndSvcType\":\"供货\",\"custName\":\"北京控股集团财务有限公司\",\"fstSvcType\":\"集成\",\"projectName\":\"北控集团财务公司数据容灾管理系统项目\"}}";
		JSONObject json = JSON.parseObject(data);
		JSONObject json = new JSONObject();
		FcProjectInfoRest order =  new TestCost().getFcProjectInfo();
		
		//json.put("code", 200);
		json.put("data", order);

		System.out.println(json);
		//json = json.getJSONObject("data");

		System.out.println(json);

		//OrderInfo orderInfo = JSON.toJavaObject(json, OrderInfo.class);
		//OrderInfos orderInfo = json.getObject("data", OrderInfos.class);
		
		//System.out.println(orderInfo.getProdDetailInfoMap().size()+"-------");
		//System.out.println(orderInfo.getOrderId());
		
		//JSONObject json1 = new TestCost().requestNewTopService("http://localhost/wbm/service/", "costCalculate", json.toJSONString(), "GET");
		JSONObject json2 = new TestCost().requestNewTopService("http://localhost:8802/wbm/service/fc/", "calcInnerCost", json.toJSONString(), "POST");
		//JSONObject json3 = new TestCost().requestNewTopService("http://localhost/wbm/service/fc/", "calcOuterCost", json.toJSONString(), "POST");
		System.out.println("执行完成！"+json2.toJSONString());
		long s = new Date().getTime();
		newThread();
		long e = new Date().getTime();
		System.out.println("耗时："+DateUtils.formatDateTime(e-s));

		//按照立项时间查询项目范围
		JSONObject jsonp3 = new JSONObject();
		jsonp3.put("calcYearMonth", "201701-201703");//201701-201702
		//jsonp3.put("projectCode", "WV2AG70C74");
		jsonp3.put("prjScope", "A0");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月
		jsonp3.put("insertDB", "A1"); //A0:结果入库/A1:结果不入库
		jsonp3.put("userId", "1");
		//JSONObject json33 = new TestCost().requestNewTopService(RequestService.srvCalcOuterCostCondition, "", jsonp3.toJSONString(), "POST");
		//System.out.println(json33.toJSONString());

		//按照收款时间查询项目范围
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("calcYearMonth", "201701-201703");//201701-201702
		//jsonreq.put("projectCode", StringUtils.trimToEmpty(dcPrjId));
		jsonreq.put("prjScope", "A1");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月
		jsonreq.put("insertDB", "A1"); //A0:结果入库/A1:结果不入库
		jsonreq.put("userId", "1");
		//JSONObject jsonres = new RequestService().requestService(RequestService.srvCalcOuterCostCondition, "", jsonreq.toJSONString(), "POST");
		//System.out.println(jsonres.toJSONString());

		//收款期到期且尚有未到款
		JSONObject jsonreqBefore = new JSONObject();
		jsonreqBefore.put("calcYearMonth", "201701-201703");//201701-201702
		//jsonreqBefore.put("projectCode", StringUtils.trimToEmpty(dcPrjId));
		jsonreqBefore.put("prjScope", "A2");//A0:按项目立项时间落在计算月/A1:按项目收款时间落在计算月/A2:收款期到期且尚有未到款
		jsonreqBefore.put("insertDB", "A1"); //A0:结果入库/A1:结果不入库
		jsonreqBefore.put("userId", "1");
		//JSONObject jsonresBefore = new RequestService().requestService(RequestService.srvCalcOuterCostCondition, "", jsonreqBefore.toJSONString(), "POST");
		//System.out.println(jsonresBefore.toJSONString());
		System.out.println("执行完成！");
	}
	
	public FcProjectInfoRest getFcProjectInfo() throws ParseException {
		FcProjectInfoRest fcProjectInfo = new FcProjectInfoRest();
		fcProjectInfo.setProjectCode("FVJAG604U7");
		fcProjectInfo.setProjectName("FVJAG604U7");
		fcProjectInfo.setCustName("北京中科软致软件技术有限公司");
		fcProjectInfo.setFstSvcType("运维");
		fcProjectInfo.setSndSvcType("基础运维");
		fcProjectInfo.setSaleOrg("北区服务事业一部");
		fcProjectInfo.setBusinessCode("XXX");
		fcProjectInfo.setSalesName("徐桑");
		fcProjectInfo.setHasWbmOrder("A0");
		fcProjectInfo.setSignMoney(Double.valueOf(1000000));
		fcProjectInfo.setFinancialMonth("201602");

		List<FcOrderInfoRest> fcOrderInfoList = Lists.newArrayList();		// 订单信息
		List<FcActualReceiptDetailRest> fcActualReceiptDetailList = Lists.newArrayList();		// 项目实际到款明细
		List<FcPlanPayDetailRest> fcPlanPayDetailList = Lists.newArrayList();		// 项目计划付款明细
		List<FcPlanReceiptDetailRest> fcPlanReceiptDetailList = Lists.newArrayList();		// 项目计划收款明细
		
		FcOrderInfoRest oi = new FcOrderInfoRest();
		oi.setOrderId("OR160727097990D");
		oi.setFstSvcType("运维");
		oi.setSndSvcType("基础运维");
		oi.setServiceDateBegin(DateUtils.parseDate("2016/01/01", "yyyy/MM/dd"));
		oi.setServiceDateEnd(DateUtils.parseDate("2016/12/31", "yyyy/MM/dd"));
		//oi.setSignAmount(Double.valueOf(1000000));
		oi.setOwnProdCost(Double.valueOf(200000));
		oi.setSpecifySubCost(Double.valueOf(100000));
		fcOrderInfoList.add(oi);
		fcProjectInfo.setFcOrderInfoRestList(fcOrderInfoList);
		
		//计划付款
		FcPlanPayDetailRest ppd = new FcPlanPayDetailRest();
		ppd.setDisplayOrder(1);
		ppd.setPlanPayTime(DateUtils.parseDate("2016/02/05", "yyyy/MM/dd"));
		ppd.setPlanPayAmount(Double.valueOf(200000));
		ppd.setPlanPayScale(Double.valueOf(0.5));
		ppd.setPlanPayDays(0);
		ppd.setPayCurrency("A0");
		ppd.setPayType("A0");
		fcPlanPayDetailList.add(ppd);

		ppd = new FcPlanPayDetailRest();
		ppd.setDisplayOrder(2);
		ppd.setPlanPayTime(DateUtils.parseDate("2016/02/15", "yyyy/MM/dd"));
		ppd.setPlanPayAmount(Double.valueOf(100000));
		ppd.setPlanPayScale(Double.valueOf(0.5));
		ppd.setPlanPayDays(0);
		ppd.setPayCurrency("A0");
		ppd.setPayType("A0");
		fcPlanPayDetailList.add(ppd);
		fcProjectInfo.setFcPlanPayDetailRestList(fcPlanPayDetailList);
		
		//计划收款
		FcPlanReceiptDetailRest prd = new FcPlanReceiptDetailRest();
		prd.setDisplayOrder(1);
		prd.setPlanReceiptTime(DateUtils.parseDate("2016/02/25", "yyyy/MM/dd"));
		prd.setPlanReceiptAmount(Double.valueOf(300000));
		prd.setPlanReceiptScale(Double.valueOf(0.3));
		prd.setPlanReceiptDays(90);
		prd.setPayCurrency("A0");
		prd.setPayType("A0");
		fcPlanReceiptDetailList.add(prd);
		
		prd = new FcPlanReceiptDetailRest();
		prd.setDisplayOrder(2);
		prd.setPlanReceiptTime(DateUtils.parseDate("2016/01/15", "yyyy/MM/dd"));
		prd.setPlanReceiptAmount(Double.valueOf(800000));
		prd.setPlanReceiptScale(Double.valueOf(1));
		prd.setPlanReceiptDays(0);
		prd.setPayCurrency("A0");
		prd.setPayType("A0");
		fcPlanReceiptDetailList.add(prd);
		fcProjectInfo.setFcPlanReceiptDetailRestList(fcPlanReceiptDetailList);
		
		//实际到款
		FcActualReceiptDetailRest ard = new FcActualReceiptDetailRest();
		ard.setDisplayOrder(1);
		ard.setActualReceiptTime(DateUtils.parseDate("2016/02/10", "yyyy/MM/dd"));
		ard.setActualReceiptAmount(Double.valueOf(100000));
		ard.setPayCurrency("A0");
		ard.setPayType("A0");
		fcActualReceiptDetailList.add(ard);
		ard = new FcActualReceiptDetailRest();
		
		ard.setDisplayOrder(1);
		ard.setActualReceiptTime(DateUtils.parseDate("2016/02/20", "yyyy/MM/dd"));
		ard.setActualReceiptAmount(Double.valueOf(200000));
		ard.setPayCurrency("A0");
		ard.setPayType("A0");
		fcActualReceiptDetailList.add(ard);
		fcProjectInfo.setFcActualReceiptDetailRestList(fcActualReceiptDetailList);
		
		return fcProjectInfo;
	}
	
	public JSONObject requestNewTopService(String requestUrl, String serverMethod, String reqPara, String method) throws UnsupportedEncodingException {
		String url = requestUrl + serverMethod;
		StringBuffer params = new StringBuffer();
		if("GET".equals(method)) {
			JSONObject json = JSON.parseObject(reqPara);
			for(Entry<String, Object> entry : json.entrySet()) {
				params.append(entry.getKey())
					.append("=")
					.append(entry.getValue())
					.append("&");
			}
			url = url + "?" + params.substring(0, params.length()-1);
		}

	}

*/}



