package com.dobo.common.xfire;

import java.net.URL;
import java.util.Map;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.w3c.dom.Document;

public class XfireClient {
	public static String sendService(Object xmlStr, String url, String method) throws Exception{
		Client _client=null;
		try{
			URL _url = new URL(url);
	         _client = new Client(_url);
	        _client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( 60000 ));//设置发送的超时限制,单位是毫秒;
	        _client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	        _client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");

	        Object[] result = _client.invoke(method,new Object[]{xmlStr});
			return result[0].toString();
		}finally{
			if(null!=_client){
				_client.close();
			}
		}
	}
	
	public static String sendService(Object[] xmlStr, String url, String method) throws Exception{
		Client _client=null;
		try{
		URL _url = new URL(url);
         _client = new Client(_url);
        _client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( 60000 ));//设置发送的超时限制,单位是毫秒;
        _client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
        _client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");

			Object[] result = _client.invoke(method,xmlStr);
			return result[0].toString();
		}finally{
			if(null!=_client){
				_client.close();
			}
		}
	}

	public static String sendService(Object xmlStr, String url, String method, Map<String, String> headMap) throws Exception{
		Client _client=null;
		try{
			URL _url = new URL(url);
	         _client = new Client(_url);
	        _client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( 60000 ));//设置发送的超时限制,单位是毫秒;
	        _client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
	        _client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
	       // _client.addOutHandler(new AbstractHandler("abcd","1234")); 
//	        Map<String, String> headMap = new HashMap<String, String>();
//			headMap.put("user", "tester");
			_client.setProperty(CommonsHttpMessageSender.HTTP_HEADERS, headMap);//往head里加参数可以这样加，一般在加密的时候会把相关加密参数放到头部
				Object[] result = _client.invoke(method,new Object[]{xmlStr});
				return result[0].toString();
		}finally{
			if(null!=_client){
				_client.close();
			}
		}
	}

	/**
	 * 跨平台调用webservice，返回报文org.apache.xerces.dom.DocumentImpl,强制转换为w3c的document
	 * @param xmlStr
	 * @param url
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public static String sendServices(Object[] xmlStr, String url, String method) throws Exception{
		Client _client=null;
		try{
		URL _url = new URL(url);
         _client = new Client(_url);
        _client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( 60000 ));//设置发送的超时限制,单位是毫秒;
        _client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
        _client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");

			Object[] result = _client.invoke(method, xmlStr);
			//System.out.println(result[0]);
            Document d = (Document)result[0];
            
			return d.getDocumentElement().getTextContent();
		}finally{
			if(null!=_client){
				_client.close();
			}
		}
	}
	
	public static void main(String[] args) {
		
		String url = "http://10.7.10.176/ws/WbmProjectInfoService?wsdl";
		String method = "getWbmXml";
		
		try {
			
			String returnXMl = XfireClient.sendService(new String[]{"dd"}, url, method);
			System.out.println(returnXMl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

