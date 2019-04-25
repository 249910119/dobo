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
import com.dobo.modules.fc.dao.FcPlanReceiptDetailDao;
import com.dobo.modules.fc.entity.FcPlanReceiptDetail;
import com.dobo.modules.fc.rest.entity.FcPlanReceiptDetailRest;

/**
 * 项目计划收款明细Service
 * @author admin
 * @version 2016-11-06
 */
@Service
@Transactional(readOnly = true)
public class FcPlanReceiptDetailService extends CrudService<FcPlanReceiptDetailDao, FcPlanReceiptDetail> {

	@Autowired
	private FcPlanReceiptDetailDao fcPlanReceiptDetailDao;
	
	/*public List<FcPlanReceiptDetailRest> findListByPlanReceiptTimeBefore(String planReceiptTimeB, String projectCode) {
		return fcPlanReceiptDetailDao.findListByPlanReceiptTimeBefore(planReceiptTimeB, projectCode);
	}*/
	
	public List<FcPlanReceiptDetailRest> findListByPlanReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode) {
		return fcPlanReceiptDetailDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode);
	}
	
	public List<FcPlanReceiptDetailRest> findListByHtlxrq(String htlxspjsrqB, String htlxspjsrqE, String projectCode) {
		return fcPlanReceiptDetailDao.findListByHtlxrq(htlxspjsrqB, htlxspjsrqE, projectCode);
	}

	@Override
    public FcPlanReceiptDetail get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcPlanReceiptDetail> findList(FcPlanReceiptDetail fcPlanReceiptDetail) {
		return super.findList(fcPlanReceiptDetail);
	}
	
	@Override
    public Page<FcPlanReceiptDetail> findPage(Page<FcPlanReceiptDetail> page, FcPlanReceiptDetail fcPlanReceiptDetail) {
		return super.findPage(page, fcPlanReceiptDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcPlanReceiptDetail fcPlanReceiptDetail) {
		super.save(fcPlanReceiptDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcPlanReceiptDetail fcPlanReceiptDetail) {
		super.delete(fcPlanReceiptDetail);
	}
	
}