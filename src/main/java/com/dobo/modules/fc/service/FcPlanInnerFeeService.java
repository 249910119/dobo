/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.fc.dao.FcPlanInnerFeeDao;
import com.dobo.modules.fc.entity.FcPlanInnerFee;

/**
 * 项目计划内财务费用Service
 * @author admin
 * @version 2016-11-06
 */
@Service
@Transactional(readOnly = true)
public class FcPlanInnerFeeService extends CrudService<FcPlanInnerFeeDao, FcPlanInnerFee> {

	@Override
    public FcPlanInnerFee get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcPlanInnerFee> findList(FcPlanInnerFee fcPlanInnerFee) {
		return super.findList(fcPlanInnerFee);
	}
	
	@Override
    public Page<FcPlanInnerFee> findPage(Page<FcPlanInnerFee> page, FcPlanInnerFee fcPlanInnerFee) {
		return super.findPage(page, fcPlanInnerFee);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcPlanInnerFee fcPlanInnerFee) {
		super.save(fcPlanInnerFee);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcPlanInnerFee fcPlanInnerFee) {
		super.delete(fcPlanInnerFee);
	}
	
}