package com.dobo.modules.cst.rest.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class TestCost {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		//new TestCost().duplicatRemoval("nmhn");
		
		JSONObject json1b = new JSONObject();
		
		Map<String, Map<String, String>> strMap = new HashMap<String, Map<String, String>>();
		Map<String, String> str1Map = new HashMap<String, String>();
		str1Map.put("test1", "111111");
		str1Map.put("test2", "222222");
		str1Map.put("test3", "333333");
		strMap.put("order1", str1Map);
		
		json1b.put("data", strMap);
		
		String str = json1b.toJSONString();
		
		json1b = JSON.parseObject(str);
		
		Map<String, Map<String, String>> str2Map = (Map<String, Map<String, String>>) json1b.get("data");
		
		for(String key : str2Map.keySet()) {
			for(String id : str2Map.get(key).keySet()) {
				System.out.println(key+":"+id+""+str2Map.get(key).get(id));
			}
		}
		
		json1b.put("calcYearMonth", "20190101-20190331");
		json1b.put("projectCode", "");
		//json1b.put("prjScope", "A1");
		json1b.put("insertDB", "A0");
		json1b.put("userId", "1");
		new TestCost().requestNewTopService("http://localhost:8802/wbm/service/", "fc/calcActualPrj", json1b.toJSONString(), "POST");
		
		//new TestCost().getPaymentInfo("2017-11-01", "2017-11-21", "");
		
		JSONObject json = new JSONObject();
//		OrderInfo order =  new TestCost().getOrderDetails();

		//json.put("code", 200);
//		json.put("data", order);

		//json.put("orderId", "OR160607174712D");  --OR160628136150D 巡检  --DVJAG705W0 驻场 RVJAGZSTWS W3NAF6M042
//		String orderJson = "{\"onceNum\":\"RVJAGZSTWP\"}";
		
		//wbm.case.manPrice
		json.put("caseId", 15672);
		json.put("caseCode", "CS1504300083");
		json.put("onceNum", 123121231);
		json.put("onceCode", "SC1504300088");
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", 1000092);
		map.put("serviceType", "1");
		map.put("serviceTypeName", "故障现场支持");
		map.put("roleId", 4);
		map.put("roleName", "二线");
		map.put("engineerLevel", "3");
		map.put("engineerLevelName", "高级");
		map.put("resourceType", "0");
		map.put("resourceTypeName", "内部");
		map.put("workload", 2);
		map.put("price", 500);
		map.put("travelPrice", 0);
		mapList.add(map);
		json.put("data", mapList);
		
		//wbm.case.prepayQuery
		JSONObject json1 = new JSONObject();
		json1.put("projectCode", "WVJAH60YLU");
		json1.put("priceType", "1");
		json1.put("priceTypeName", "人员");
		json1.put("saleItcode", "renypa");
		json1.put("saleName", "胡夏阳");
		
		//wbm.case.priceConfirm
		JSONObject json2 = new JSONObject();
		json2.put("projectCode", "ABC");
		json2.put("caseId", 15672);
		json2.put("caseCode", "CS1504300083");
		json2.put("onceNum", 123121231);
		json2.put("onceCode", "SC1504300088");
		json2.put("priceType", "1");
		json2.put("priceTypeName", "人员");
		json2.put("serviceType", "1");
		json2.put("serviceTypeName", "故障现场支持");
		json2.put("payType", "1");
		json2.put("costPrepay", 9000);
		json2.put("costPrepayTravel", 1500);
		json2.put("remark", "");
		List<Map<String, Object>> mapList2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map21 = new HashMap<String, Object>();
		map21.put("projectCode", "ABC");
		map21.put("actualCost", 10000);
		mapList2.add(map21);
		Map<String, Object> map22 = new HashMap<String, Object>();
		map22.put("projectCode", "ABC");
		map22.put("actualCost", 500);
		mapList2.add(map22);
		json2.put("data", mapList2);
		
		
		//JSONObject jsonT = new TestCost().requestNewTopService("http://10.1.200.174:8802/wbm/service/", "wbm.case.prepayQuery", json1.toJSONString(), "POST");
		
		JSONObject json3 = new JSONObject();
		json3.put("onceNum", 1035675);
		json3.put("payType", 2);
		json3.put("dcPrjId", "O2O170602205929");
		
		json3.put("data", json3.toJSONString());
		//JSONObject jsonT = new TestCost().requestNewTopService("http://localhost/wbm/service/", "caseCostCalculate", json3.toJSONString(), "GET");
		
		
		List<Map<String, String>> perpayOrderList = new ArrayList<Map<String, String>>();
		Map<String, String> prePayOrder = new HashMap<String, String>();
		prePayOrder.put("orderId", "DR20170101");
		prePayOrder.put("prodManCost", "10000");
		prePayOrder.put("prodBackCost", "5000");
		prePayOrder.put("finalManCost", "8000");
		prePayOrder.put("finalBackCost", "5000");
		perpayOrderList.add(prePayOrder);	
		Map<String, String> prePayOrder2 = new HashMap<String, String>();
		prePayOrder2.put("orderId", "DR20170101");
		prePayOrder2.put("prodManCost", "10000");
		prePayOrder2.put("prodBackCost", "5000");
		prePayOrder2.put("finalManCost", "8000");
		prePayOrder2.put("finalBackCost", "5000");
		perpayOrderList.add(prePayOrder2);

		JSONObject jsonPrePay = new JSONObject();
		jsonPrePay.put("prjType", "delivery");
		jsonPrePay.put("dcPrjId", "ABC");
		jsonPrePay.put("changedPrjId", "");
		jsonPrePay.put("data", perpayOrderList);
		
		System.out.println(jsonPrePay.toJSONString());
		
//		json1 = json1.getJSONObject("data");;

//		System.out.println(BigDecimal.valueOf(2.33).intValue());

//		OrderInfo orderInfo = JSON.toJavaObject(json1, OrderInfo.class);
		//OrderInfos orderInfo = json.getObject("data", OrderInfos.class);
//		for(DetailCostInfo dci : orderInfo.getDetailCostInfoMap().get("RXSA-03-01-01")) {
//			System.out.println(dci.getBackCost());
//			System.out.println(dci.getCostInfoMap().size());
//		}
//		System.out.println(orderInfo.getOrderId());
		
		//String str = "{\"invoke_type\":\"prepay\",\"projectCode\":\"C5588-上海电信维保服务(WVJAG60F29)\",\"projectName\":\"C5588-上海电信维保服务(WVJAG60F29)\",\"salesId\":\"yedl\",\"salesName\":\"叶冬兰\",\"caseId\":119377,\"caseCode\":\"CS1803080047\",\"onceNum\":1052833,\"onceCode\":\"SC1803092833\",\"priceType\":\"1\",\"priceTypeName\":\"人员\",\"serviceType\":\"1\",\"serviceTypeName\":\"故障\",\"payType\":\"1\",\"payTypeName\":\"预付费\",\"shareNo\":\"入之后的呼叫项目\",\"costPrepay\":\"1300\",\"costPrepayTravel\":\"0\",\"remark\":\"\",\"data\":[{\"projectCode\":\"FVSAI60LFOW2\",\"actualCost\":\"1300\"}],\"areaName\":\"东区:上海项目一部\",\"province\":\"上海\",\"cityId\":\"310100\",\"city\":\"上海市\"}";
		
		//JSONObject json12 = new TestCost().requestNewTopService("http://localhost:8802/wbm/service/", "wbm.case.priceConfirm", str, "POST");
		//JSONObject json12 = new TestCost().requestNewTopService("http://127.0.0.1:8808/api/pl/", "deliveryProject", str, "POST");
		
		JSONObject jsons = new JSONObject();
		
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("orderId", "DR20170101");
		maps.put("prodManCost", "10000");
		Map<String, String> maps1 = new HashMap<String, String>();
		maps1.put("orderId", "DR220170101");
		maps1.put("prodManCost", "120000");
		lists.add(maps);
		lists.add(maps1);
		
		jsons.put("invoke_type", "test");
		jsons.put("data", lists);
		System.out.println(jsons.toJSONString());
		
		String stss = jsons.toJSONString();
		
		JSONObject jsons1 = JSONObject.parseObject(stss); 
		
		JSONArray data = (JSONArray) jsons1.get("data");
		
		List<Map> list = data.toJavaObject(List.class);
		
		for(Map<String, String> dataMap : list) {
			for(String key : dataMap.keySet()) {
				System.out.println(key+":"+dataMap.get(key));
			}
		}
	}

	private String duplicatRemoval(String source)
    {
        String[] str = source.split("");
        if (str.length == 0 )
        {
            return null;
        }
        List<String> list = Lists.newArrayList();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++)
        {
            if (!list.contains(str[i]))
            {
                list.add(str[i]);
                sb.append(str[i]);
            }  
        }
        return sb.toString();
    }
	
	public OrderInfo getOrderDetails() {
		String dcOrderId = "DC001";
		String[] arrOrder = dcOrderId.split(",");
		List<OrderInfo> oi = new ArrayList<OrderInfo>();
		OrderInfo orderInfo = new OrderInfo();

		List<ProdDetailInfo> detailInfos = new ArrayList<ProdDetailInfo>();
		
		//String resId = "150621474,150621479,9648,9661,5272,5310,5322,5335,5354,13865,13877,13890,13903,13917,13932,13976,13989,14005,14024,14082,14094,14112,14201,13489,13524,13538,13551,13562,13578,13595,12274,12288,12300,12313,12331,12348,11313,150321183,11353,11374,12192,12204,150621487,150621489,150621454,150621456,150621457,150621459,150621460,150621461,150621462,150621463,150621464,150621466,150621467,150621468,150621510,150621511,150621513,150621514,150621515,150621518,150621519,150621520,150621521,150621522,150621523,150621524,150621525,150621527,150621531,150721537,150721539,150721543,150721545,150721546,150721547,150721558,150721560,150721562,150721564,150721571,150721574,150721576,150721577,150721599,150721600,150721601,150821607,150821610,150821611,150821623,150821624,150821629,150821630,150821660,150821664,150821665,150921671,150921696,150921697,150921700,150921738,150921741,150921742,150921743,150921744,150921745,150921747,150921753,151021758,151021773,151021776,151021778,151021779,151021780,151021781,150621481,150621488,150621493,150621494,150621501,150621504,150621516,150621517,150621530,150721557,150721559,150721565,150721566,150721580,150721581,150721587,150721589,150721590,150721598,150821608,150821614,150821620,150821621,150821631,150821632,150821633,150821634,150821635,150821636,150821637,150821638,150821639,150821640,150821643,150821648,150821649,150821651,150821652,150821653,150821655,150821659,150821661,150821662,150821663,150921672,150921676,150921677,150921679,150921680,150921681,150921682,150921683,150921684,150921685,150921686,150921689,150921690,150921691,150921692,150921693,150921694,150921698,150921699,150921713,150921714,150921715,150921720,150921730,150921731,150921735,150921746,150921748,150921749,150921750,151021754,20088,20100,150621496,150621497,150621498,150721535,150721536,150721538,150721556,150721563,150721604,150721605,150721606,150821612,150821613,150821641,150921701,150921702,150921703,150921734,150921737,150921739,150921751,151021764,151021768,151021784,151021785,151021788,151021789,151021791,151021794,151021795,151021796,151121816,151121829,151121830,151121831,151121832,151121833,151121835,151121837,151121838,151121840,151121841,151121842,151121845,151221864,151221869,151221870,151221874,151221875,151221878,151221879,151221882,151221885,151221886,151221887,151221888,151221893,151221894,151221897,151221900,151221902,151221905,151221908,16010152,16010153,16010154,16010155,16010156,16010157,16010158,16010160,16010161,16010162,16010163,12216,12229,12251,2925,401,969,1200,1212,1225,1241,1259,1273,1293,13602,13613,13624,13638,13650,13663,13678,13698,13708,13720,13733,13748,13763,13792,13805,13819,13830,13846,12716,12733,10299,10312,10324,10339,10356,10457,10469,10483,10498,10520,3749,3762,3774,150821615,150821616,151121799,151121802,151121805,151121806,151121807,151121808,151121809,151121810,151121811,151121812,151121819,151121825,151121826,151121827,151121828,151121834,151121836,151121839,151121853,151121854,151121855,151121857,151121861,151121862,151221863,151221866,151221868,151221871,151221876,151221877,151221880,151221881,151221883,151221884,151221889,151221890,151221891,151221892,151221895,151221896,151221898,151221899,151221901,151221903,151221904,151221907,16010164,16010165,151221911,151221912,160121928,160121929,160121930";
		String resId = "150621474";
		String[] resIds = resId.split(",");
		System.out.println(resIds.length);
		for(int i=0; i<resIds.length;i++){
			
			ProdDetailInfo detail = new ProdDetailInfo();
			detail.setDetailId("RD14071"+i);
			//detail.setDcPrjId("FFLAE6M028");
			//detail.setResourceId("DCSA-01-01-05");
			//detail.setDetailType("A0");
			//detail.setMfrName("Sun");
			//detail.setDetailModel("390");
			Map<String, String> inParaMap = new HashMap<String, String>();
			inParaMap.put("amount", "1");
			inParaMap.put("resourceId", "79");
			inParaMap.put("BUYPRDMON", "100");
			inParaMap.put("SLA", "SLA_DEVICE_A");
			inParaMap.put("SECTION", "CHN0101");
			detail.setInParaMap(inParaMap);
			
			detailInfos.add(detail);
		}
		
		Map<String, List<ProdDetailInfo>> pdMap = new HashMap<String, List<ProdDetailInfo>>();
		pdMap.put("RXSA-03-01-01", detailInfos);
		//orderInfo.setProdDetailInfoMap(pdMap);
		
		orderInfo.setOrderId("OR140711124284D");
		orderInfo.setOrderClass("预审");
		orderInfo.setOrderName("星展银行sun服务器维保");
		orderInfo.setOrderState("正式交付");
		
		return orderInfo;
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
		
		JSONObject json = new JSONObject();
		OutputStreamWriter outWriter = null;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
	        conn.setRequestProperty("Content-Type", "text/plain");

			if("GET".equals(method)){
				conn.connect();
			}else{
				//打开读写属性，默认均为false 
				conn.setDoOutput(true);
				//conn.setDoInput(true); 
	           	// 设置请求方式，默认为GET 
				conn.setRequestMethod(method);
		        // put 请求不能使用缓存 
				//conn.setUseCaches(false); 
				//conn.setInstanceFollowRedirects(true); 
				//客户端向发送服务器请求数据报文
				outWriter = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); 
				outWriter.write(reqPara==null?"":reqPara);
				outWriter.flush();
			}
			//客户端接收服务器返回的报文
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
			json = JSON.parseObject(sb.toString());
			System.out.println("服务端响应数据:"+sb.toString());
		} catch (MalformedURLException e1) {
			json.put("error", "MalformedURLException: "+e1.toString());
			e1.printStackTrace();
		} catch (IOException e2) {
			json.put("error", "IOException: "+e2.toString());
			e2.printStackTrace();
		} catch (Exception e) {
			json.put("error", "Exception: "+e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (outWriter != null) {
					outWriter.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

}
