/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.service.detail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cop.dao.detail.CopSalesAccountDao;
import com.dobo.modules.cop.entity.detail.CopSalesAccount;

/**
 * 销售员账户表Service
 * @author admin
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class CopSalesAccountService extends CrudService<CopSalesAccountDao, CopSalesAccount> {

	@Autowired
	CopSalesAccountDao copSalesAccountDao;
	
	@Override
    public CopSalesAccount get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopSalesAccount> findList(CopSalesAccount copSalesAccount) {
		return super.findList(copSalesAccount);
	}
	
	@Override
    public Page<CopSalesAccount> findPage(Page<CopSalesAccount> page, CopSalesAccount copSalesAccount) {
		return super.findPage(page, copSalesAccount);
	}
	
	public List<CopSalesAccount> findStaffPrjList(CopSalesAccount copSalesAccount) {
		return copSalesAccountDao.findStaffPrjList(copSalesAccount);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopSalesAccount copSalesAccount) {
		super.save(copSalesAccount);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopSalesAccount copSalesAccount) {
		super.delete(copSalesAccount);
	}
	
}