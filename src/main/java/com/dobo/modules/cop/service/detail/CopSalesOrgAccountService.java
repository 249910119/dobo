/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.service.detail;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cop.dao.detail.CopSalesOrgAccountDao;
import com.dobo.modules.cop.entity.detail.CopSalesOrgAccount;

/**
 * 单次服务成本事业部阀值Service
 * @author admin
 * @version 2018-04-20
 */
@Service
@Transactional(readOnly = true)
public class CopSalesOrgAccountService extends CrudService<CopSalesOrgAccountDao, CopSalesOrgAccount> {

	@Autowired
	CopSalesOrgAccountDao copSalesOrgAccountDao;
	
	@Override
    public CopSalesOrgAccount get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopSalesOrgAccount> findList(CopSalesOrgAccount copSalesOrgAccount) {
		return super.findList(copSalesOrgAccount);
	}
	
	public Map<String, String> getSalesByCase(String staffId) {
		return copSalesOrgAccountDao.getSalesByCase(staffId);
	}
	
	public Double getSalesOrgCaseUsedAmount(String staffId) {
		return copSalesOrgAccountDao.getSalesOrgCaseUsedAmount(staffId);
	}
	
	@Override
    public Page<CopSalesOrgAccount> findPage(Page<CopSalesOrgAccount> page, CopSalesOrgAccount copSalesOrgAccount) {
		return super.findPage(page, copSalesOrgAccount);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopSalesOrgAccount copSalesOrgAccount) {
		super.save(copSalesOrgAccount);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopSalesOrgAccount copSalesOrgAccount) {
		super.delete(copSalesOrgAccount);
	}
	
}