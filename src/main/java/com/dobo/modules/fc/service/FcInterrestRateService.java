/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.fc.dao.FcInterrestRateDao;
import com.dobo.modules.fc.entity.FcInterrestRate;

/**
 * 存息贷息利率定义Service
 * @author admin
 * @version 2016-11-05
 */
@Service
@Transactional(readOnly = true)
public class FcInterrestRateService extends CrudService<FcInterrestRateDao, FcInterrestRate> {

	//public static final String CACHE_FCINTERRESTRATE_LIST = "fcInterrestRateList";

	@Override
    public FcInterrestRate get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcInterrestRate> findList(FcInterrestRate fcInterrestRate) {
		return super.findList(fcInterrestRate);
	}
	
	@Override
    public Page<FcInterrestRate> findPage(Page<FcInterrestRate> page, FcInterrestRate fcInterrestRate) {
		return super.findPage(page, fcInterrestRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcInterrestRate fcInterrestRate) {
		super.save(fcInterrestRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcInterrestRate fcInterrestRate) {
		super.delete(fcInterrestRate);
	}
	
}