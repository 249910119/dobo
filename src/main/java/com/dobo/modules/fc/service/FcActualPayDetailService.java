/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.IdGen;
import com.dobo.common.xfire.XfireClient;
import com.dobo.modules.fc.common.Constants;
import com.dobo.modules.fc.dao.FcActualPayDetailDao;
import com.dobo.modules.fc.entity.FcActualPayDetail;
import com.dobo.modules.fc.rest.entity.FcActualPayDetailRest;
import com.dobo.modules.sys.entity.User;

/**
 * 财务费用计算需要的实付金额Service
 * @author admin
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class FcActualPayDetailService extends CrudService<FcActualPayDetailDao, FcActualPayDetail> {

	@Autowired
	private FcActualPayDetailDao fcActualPayDetailDao;
	
	@Override
    public FcActualPayDetail get(String id) {
		return super.get(id);
	}
	
	public Date getMaxPayDate() {
		return fcActualPayDetailDao.getMaxPayDate();
	}

	public List<FcActualPayDetailRest> findListByActualPayTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode) {
		return fcActualPayDetailDao.findListByActualPayTime(planReceiptTimeB, planReceiptTimeE, projectCode);
	}

	public List<FcActualPayDetailRest> findListByPlanReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode, String fstSvcTypeName, String notAllReceived) {
		return fcActualPayDetailDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode, fstSvcTypeName, notAllReceived);
	}

	public List<FcActualPayDetailRest> findListByActualPayReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode, String fstSvcTypeName, String notAllReceived) {
		return fcActualPayDetailDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode, fstSvcTypeName, notAllReceived);
	}
	
	@Override
    public List<FcActualPayDetail> findList(FcActualPayDetail fcActualPayDetail) {
		return super.findList(fcActualPayDetail);
	}
	
	@Override
    public Page<FcActualPayDetail> findPage(Page<FcActualPayDetail> page, FcActualPayDetail fcActualPayDetail) {
		return super.findPage(page, fcActualPayDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcActualPayDetail fcActualPayDetail) {
		super.save(fcActualPayDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcActualPayDetail fcActualPayDetail) {
		super.delete(fcActualPayDetail);
	}

	/**
	 * 从OA采购付款系统更新付款信息
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param dcPrjId
	 */
	@Transactional(readOnly = false)
	public void updateCgfkFromOA(String beginDate, String endDate, String dcPrjId) {
		//<?xml version="1.0" encoding="GB2312" ?><RETURN><FLAG>SUCCESS</FLAG><ITEMS><ITEM><CORPID>0031</CORPID><BUSINESS>V201</BUSINESS>
		//<DEP>代理产品线</DEP><VENDORID>0002009286</VENDORID><VENDORNAME>北京普天众力信息技术开发有限责任公司</VENDORNAME><USE>货款</USE>
		//<PAYS>商票</PAYS><INVSDATE>2017-11-07</INVSDATE><INVEDATE>2018-01-06</INVEDATE><PAYK>应付</PAYK>
		//<VOUCHERDATE>2017-11-07</VOUCHERDATE><VOUCHERNUM>900006387</VOUCHERNUM><SPR1>贾月星</SPR1><SPR2>张纯</SPR2>
		//<SPR3>贾月星</SPR3><CASHDATE>2017-11-07</CASHDATE><FORMID>shiyx201710021</FORMID><APPLYEN1>石玉霞</APPLYEN1>
		//<APPLYEN2>冯雪岑</APPLYEN2><ORDER>4500514921</ORDER><PRONUM>AV2AH70EGI</PRONUM><MONEY>391,817.88</MONEY><REASON></REASON>
		//<KMNUM></KMNUM><CCNUM1></CCNUM1><CCNUM2></CCNUM2><TYPE></TYPE></ITEM></ITEMS></RETURN>
		try {
			//客户端接收服务器返回的报文
			String returnXml = XfireClient.sendServices(new String[] {beginDate, endDate, dcPrjId}, Constants.FC_SOA_PAYMENT_URL, Constants.FC_SOA_PAYMENT_METHOD);

			Document document = DocumentHelper.parseText(returnXml);
			document.setXMLEncoding("UTF-8");
			Element rootData = document.getRootElement();

			Element resultCode = rootData.element("FLAG");
			String resultCodeText = resultCode.getText();
			
			if(resultCodeText == null || "ERROR".equals(resultCodeText)){
				throw new RuntimeException("获取付款信息失败: "+resultCodeText);
			}
			
			Element temps = rootData.element("ITEMS");
			@SuppressWarnings("unchecked")
			Iterator<Element> items = temps.elementIterator("ITEM");
			while (items.hasNext()){
				Element tempPay = (Element) items.next();

				String cgdd = tempPay.elementText("ORDER");
				if(StringUtils.isBlank(cgdd)){
					continue;
				}
				
				FcActualPayDetail fcPay = new FcActualPayDetail();
				fcPay.setGsdm(tempPay.elementText("CORPID")); // <CORPID>公司代码</CORPID>
				fcPay.setYwfwdm(tempPay.elementText("BUSINESS")); // <BUSINESS>业务范围</BUSINESS>
				fcPay.setYwfwmc(tempPay.elementText("BUSINESS"));
				fcPay.setSbumc("服务SBU");
				fcPay.setBumc(null);
				fcPay.setSybmc(tempPay.elementText("DEP")); // <DEP>事业部</DEP>
				fcPay.setCgdddm(cgdd); // <ORDER>采购订单</ORDER>
				fcPay.setXmdm(tempPay.elementText("PRONUM")); // <PRONUM>项目号</PRONUM>
				fcPay.setGysdm(tempPay.elementText("VENDORID")); // <VENDORID>供应商编号</VENDORID>
				fcPay.setGysmc(tempPay.elementText("VENDORNAME")); // <VENDORNAME>供应商名称</VENDORNAME>
				fcPay.setPays(tempPay.elementText("PAYS")); // <PAYS>付款方式</PAYS>

				//开票日 <INVSDATE>2017-11-07</INVSDATE>
				if(tempPay.elementText("INVSDATE") != null && !"".equals(tempPay.elementText("INVSDATE").trim())) {
					fcPay.setInvsdate(DateUtils.formatDate(DateUtils.parseDate(tempPay.elementText("INVSDATE"), "yyyy-MM-dd"), "yyyyMMdd"));
				}
				//到期日<INVEDATE>2018-01-06</INVEDATE>
				if(tempPay.elementText("INVEDATE") != null && !"".equals(tempPay.elementText("INVEDATE").trim())) {
					fcPay.setInvedate(DateUtils.formatDate(DateUtils.parseDate(tempPay.elementText("INVEDATE"), "yyyy-MM-dd"), "yyyyMMdd"));
				}
				if(tempPay.elementText("MONEY") != null && !"".equals(tempPay.elementText("MONEY").trim())) {
					fcPay.setFkje(Double.valueOf(tempPay.elementText("MONEY").replaceAll(",", ""))); // <MONEY>应付含税金额</MONEY>
				}
				if(tempPay.elementText("VOUCHERDATE") != null && !"".equals(tempPay.elementText("VOUCHERDATE").trim())) {
					fcPay.setFkrq(DateUtils.formatDate(DateUtils.parseDate(tempPay.elementText("VOUCHERDATE"), "yyyy-MM-dd"), "yyyyMMdd")); // <VOUCHERDATE>记账日期</VOUCHERDATE>
				}
				fcPay.setFkpzdm(tempPay.elementText("VOUCHERNUM")); // <VOUCHERNUM>凭证号</VOUCHERNUM>
				fcPay.setFormid(tempPay.elementText("FORMID")); // <FORMID>表单号</FORMID>
				fcPay.setFkId(IdGen.uuid());
				fcPay.setCreateBy(new User("admin"));
				fcPay.setCreateDate(new Date());
				
				super.save(fcPay);
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}