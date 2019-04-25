/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.service.detail;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cop.entity.detail.CopSalesOrgProject;
import com.dobo.modules.cop.dao.detail.CopSalesOrgProjectDao;

/**
 * 限额项目Service
 * @author admin
 * @version 2018-06-01
 */
@Service
@Transactional(readOnly = true)
public class CopSalesOrgProjectService extends CrudService<CopSalesOrgProjectDao, CopSalesOrgProject> {

	@Override
    public CopSalesOrgProject get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopSalesOrgProject> findList(CopSalesOrgProject copSalesOrgProject) {
		return super.findList(copSalesOrgProject);
	}
	
	@Override
    public Page<CopSalesOrgProject> findPage(Page<CopSalesOrgProject> page, CopSalesOrgProject copSalesOrgProject) {
		return super.findPage(page, copSalesOrgProject);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopSalesOrgProject copSalesOrgProject) {
		super.save(copSalesOrgProject);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopSalesOrgProject copSalesOrgProject) {
		super.delete(copSalesOrgProject);
	}
	
}