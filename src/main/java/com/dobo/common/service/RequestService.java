package com.dobo.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class RequestService {

	public static String SRV_IP_PORT = "localhost:80";
	//计划外财务费用计算-传值
	public static String srvCalcOuterCost = "http://"+SRV_IP_PORT+"/wbm/service/fc/calcOuterCost";
	//计划外财务费用计算-传条件
	public static String srvCalcOuterCostCondition= "http://"+SRV_IP_PORT+"/wbm/service/fc/calcOuterCostCondition";
	//计划外财务费用计算-传条件-按照事业部现金流
	public static String srvCalcActualOuterCostCondition= "http://"+SRV_IP_PORT+"/wbm/service/fc/calcActualOuterCostCondition";
	//计划内财务费用计算-传值
	public static String srvCalcInnerCost = "http://"+SRV_IP_PORT+"/wbm/service/fc/calcInnerCost";
	//计划内财务费用计算-传条件
	public static String srvCalcInnerCostCondition= "http://"+SRV_IP_PORT+"/wbm/service/fc/calcInnerCostCondition";
	
	/**
	 * 服务调用请求
	 * 
	 * @param requestUrl:服务地址
	 * @param serverMethod:服务调用方法
	 * @param reqPara:参数-json格式
	 * @param method:get or post
	 * @return 
	 * 	A）、成功：{"code":"0","msg":"返回成功信息","data":"返回数据"}
	 *	B）、失败：{"code":"1","msg":"返回失败信息","data":"返回数据"}
	 * @throws UnsupportedEncodingException 
	 */
	public static JSONObject requestService(String requestUrl, String serverMethod, String reqPara, String method) throws UnsupportedEncodingException {
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
