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
import com.dobo.modules.fc.dao.BasSybInitialDao;
import com.dobo.modules.fc.entity.BasSybInitial;

/**
 * 事业部初始现金流Service
 * @author admin
 * @version 2017-12-24
 */
@Service
@Transactional(readOnly = true)
public class BasSybInitialService extends CrudService<BasSybInitialDao, BasSybInitial> {

	@Autowired
	private BasSybInitialDao basSybInitialDao;
	
	public List<BasSybInitial> findListByFiscalYear(String fiscalYear) {
		return basSybInitialDao.findListByFiscalYear(fiscalYear);
	}

	@Override
    public BasSybInitial get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BasSybInitial> findList(BasSybInitial basSybInitial) {
		return super.findList(basSybInitial);
	}
	
	@Override
    public Page<BasSybInitial> findPage(Page<BasSybInitial> page, BasSybInitial basSybInitial) {
		return super.findPage(page, basSybInitial);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BasSybInitial basSybInitial) {
		super.save(basSybInitial);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BasSybInitial basSybInitial) {
		super.delete(basSybInitial);
	}
	
}