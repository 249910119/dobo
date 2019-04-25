/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.fc.dao.FcPlanOuterFeeDao;
import com.dobo.modules.fc.entity.FcPlanOuterFee;

/**
 * 项目计划外财务费用Service
 * @author admin
 * @version 2016-11-06
 */
@Service
@Transactional(readOnly = true)
public class FcPlanOuterFeeService extends CrudService<FcPlanOuterFeeDao, FcPlanOuterFee> {
	
	public List<FcPlanOuterFee> findListBeforeFinancialMonth(FcPlanOuterFee fcPlanOuterFee) {
		return super.findList(fcPlanOuterFee);
	}

	@Override
    public FcPlanOuterFee get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcPlanOuterFee> findList(FcPlanOuterFee fcPlanOuterFee) {
		return super.findList(fcPlanOuterFee);
	}
	
	@Override
    public Page<FcPlanOuterFee> findPage(Page<FcPlanOuterFee> page, FcPlanOuterFee fcPlanOuterFee) {
		return super.findPage(page, fcPlanOuterFee);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcPlanOuterFee fcPlanOuterFee) {
		super.save(fcPlanOuterFee);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcPlanOuterFee fcPlanOuterFee) {
		super.delete(fcPlanOuterFee);
	}
	
}