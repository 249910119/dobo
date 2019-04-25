/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.fc.dao.FcOrderInfoDao;
import com.dobo.modules.fc.entity.FcOrderInfo;
import com.dobo.modules.fc.rest.entity.FcActualOrderInfoRest;
import com.dobo.modules.fc.rest.entity.FcOrderInfoRest;

/**
 * 财务费用计算对应订单信息Service
 * @author admin
 * @version 2016-11-05
 */
@Service
@Transactional(readOnly = true)
public class FcOrderInfoService extends CrudService<FcOrderInfoDao, FcOrderInfo> {

	@Autowired
	private FcOrderInfoDao fcOrderInfoDao;
	
	/*public List<FcOrderInfoRest> findListByPlanReceiptTimeBefore(String planReceiptTimeB, String projectCode) {
		return fcOrderInfoDao.findListByPlanReceiptTimeBefore(planReceiptTimeB, projectCode);
	}*/
	
	public List<FcActualOrderInfoRest> findListByWbmOrderid(List<String> wbmOrderIds) {
		return fcOrderInfoDao.findListByWbmOrderid(wbmOrderIds);
	}
	
	public List<FcActualOrderInfoRest> findListByPlanReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode, String fstSvcTypeName, String notAllReceived) {
		return fcOrderInfoDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode, fstSvcTypeName, notAllReceived);
	}
	
	public List<FcActualOrderInfoRest> findListByActual(String planReceiptTimeB, String planReceiptTimeE, String projectCode) {
		return fcOrderInfoDao.findListByActual(planReceiptTimeB, planReceiptTimeE, projectCode);
	}
	
	public List<FcOrderInfoRest> findListByHtlxrq(String htlxspjsrqB, String htlxspjsrqE, String projectCode) {
		return fcOrderInfoDao.findListByHtlxrq(htlxspjsrqB, htlxspjsrqE, projectCode);
	}

	@Override
    public FcOrderInfo get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcOrderInfo> findList(FcOrderInfo fcOrderInfo) {
		return super.findList(fcOrderInfo);
	}
	
	@Override
    public Page<FcOrderInfo> findPage(Page<FcOrderInfo> page, FcOrderInfo fcOrderInfo) {
		return super.findPage(page, fcOrderInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcOrderInfo fcOrderInfo) {
		super.save(fcOrderInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcOrderInfo fcOrderInfo) {
		super.delete(fcOrderInfo);
	}
	
}