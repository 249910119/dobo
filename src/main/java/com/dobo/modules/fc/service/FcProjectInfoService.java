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
import com.dobo.modules.fc.dao.FcProjectInfoDao;
import com.dobo.modules.fc.entity.FcProjectInfo;
import com.dobo.modules.fc.rest.entity.FcProjectInfoRest;

/**
 * 财务费用计算对应项目信息Service
 * @author admin
 * @version 2016-11-05
 */
@Service
@Transactional(readOnly = true)
public class FcProjectInfoService extends CrudService<FcProjectInfoDao, FcProjectInfo> {

	@Autowired
	private FcProjectInfoDao fcProjectInfoDao;
	
	/*public List<FcProjectInfoRest> findListByPlanReceiptTimeBefore(String planReceiptTimeB, String projectCode) {
		return fcProjectInfoDao.findListByPlanReceiptTimeBefore(planReceiptTimeB, projectCode);
	}*/
	
	public List<FcProjectInfoRest> findListByPlanReceiptTime(String planReceiptTimeB, String planReceiptTimeE, String projectCode, String fstSvcTypeName, String notAllReceived) {
		return fcProjectInfoDao.findListByPlanReceiptTime(planReceiptTimeB, planReceiptTimeE, projectCode, fstSvcTypeName, notAllReceived);
	}
	
	public List<FcProjectInfoRest> findListByActual(String planReceiptTimeB, String planReceiptTimeE, String projectCode) {
		return fcProjectInfoDao.findListByActual(planReceiptTimeB, planReceiptTimeE, projectCode);
	}
	
	public List<FcProjectInfoRest> findListByHtlxrq(String htlxspjsrqB, String htlxspjsrqE, String projectCode) {
		return fcProjectInfoDao.findListByHtlxrq(htlxspjsrqB, htlxspjsrqE, projectCode);
	}

	@Override
    public FcProjectInfo get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcProjectInfo> findList(FcProjectInfo fcProjectInfo) {
		return super.findList(fcProjectInfo);
	}
	
	@Override
    public Page<FcProjectInfo> findPage(Page<FcProjectInfo> page, FcProjectInfo fcProjectInfo) {
		return super.findPage(page, fcProjectInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcProjectInfo fcProjectInfo) {
		super.save(fcProjectInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcProjectInfo fcProjectInfo) {
		super.delete(fcProjectInfo);
	}
	
}