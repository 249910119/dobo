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
import com.dobo.modules.fc.dao.FcActualReceiptDetailDao;
import com.dobo.modules.fc.entity.FcActualReceiptDetail;
import com.dobo.modules.fc.rest.entity.FcActualReceiptDetailRest;

/**
 * 项目实际到款明细Service
 * @author admin
 * @version 2016-11-06
 */
@Service
@Transactional(readOnly = true)
public class FcActualReceiptDetailService extends CrudService<FcActualReceiptDetailDao, FcActualReceiptDetail> {

	@Autowired
	private FcActualReceiptDetailDao fcActualReceiptDetailDao;
	
	/*public List<FcActualReceiptDetailRest> findListByPlanReceiptTimeBefore(String planReceiptTimeB, String projectCode) {
		return fcActualReceiptDetailDao.findListByPlanReceiptTimeBefore(planReceiptTimeB, projectCode);
	}*/
	
	public List<FcActualReceiptDetailRest> findListByPlanReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode, String fstSvcTypeName, String notAllReceived) {
		return fcActualReceiptDetailDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode, fstSvcTypeName, notAllReceived);
	}
	
	public List<FcActualReceiptDetailRest> findListByActual(String planReceiptTimeB, String planReceiptTimeE, String projectCode) {
		return fcActualReceiptDetailDao.findListByActual(planReceiptTimeB, planReceiptTimeE, projectCode);
	}
	
	public List<FcActualReceiptDetailRest> findListByHtlxrq(String htlxspjsrqB, String htlxspjsrqE, String projectCode) {
		return fcActualReceiptDetailDao.findListByHtlxrq(htlxspjsrqB, htlxspjsrqE, projectCode);
	}

	@Override
    public FcActualReceiptDetail get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcActualReceiptDetail> findList(FcActualReceiptDetail fcActualReceiptDetail) {
		return super.findList(fcActualReceiptDetail);
	}
	
	@Override
    public Page<FcActualReceiptDetail> findPage(Page<FcActualReceiptDetail> page, FcActualReceiptDetail fcActualReceiptDetail) {
		return super.findPage(page, fcActualReceiptDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcActualReceiptDetail fcActualReceiptDetail) {
		super.save(fcActualReceiptDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcActualReceiptDetail fcActualReceiptDetail) {
		super.delete(fcActualReceiptDetail);
	}
	
}