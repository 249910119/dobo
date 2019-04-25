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
import com.dobo.modules.fc.dao.FcPlanPayDetailDao;
import com.dobo.modules.fc.entity.FcPlanPayDetail;
import com.dobo.modules.fc.rest.entity.FcPlanPayDetailRest;

/**
 * 项目计划付款明细Service
 * @author admin
 * @version 2016-11-06
 */
@Service
@Transactional(readOnly = true)
public class FcPlanPayDetailService extends CrudService<FcPlanPayDetailDao, FcPlanPayDetail> {

	@Autowired
	private FcPlanPayDetailDao fcPlanPayDetailDao;
	
	public List<FcPlanPayDetailRest> findListByPlanReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode) {
		return fcPlanPayDetailDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode);
	}
	
	public List<FcPlanPayDetailRest> findListByHtlxrq(String htlxspjsrqB, String htlxspjsrqE, String projectCode) {
		return fcPlanPayDetailDao.findListByHtlxrq(htlxspjsrqB, htlxspjsrqE, projectCode);
	}

	@Override
    public FcPlanPayDetail get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcPlanPayDetail> findList(FcPlanPayDetail fcPlanPayDetail) {
		return super.findList(fcPlanPayDetail);
	}
	
	@Override
    public Page<FcPlanPayDetail> findPage(Page<FcPlanPayDetail> page, FcPlanPayDetail fcPlanPayDetail) {
		return super.findPage(page, fcPlanPayDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcPlanPayDetail fcPlanPayDetail) {
		super.save(fcPlanPayDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcPlanPayDetail fcPlanPayDetail) {
		super.delete(fcPlanPayDetail);
	}
	
}