/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManManageDegreePara;
import com.dobo.modules.cst.dao.man.CstManManageDegreeParaDao;

/**
 * 项目管理配比及饱和度表Service
 * @author admin
 * @version 2016-12-08
 */
@Service
@Transactional(readOnly = true)
public class CstManManageDegreeParaService extends CrudService<CstManManageDegreeParaDao, CstManManageDegreePara> {

	@Override
    public CstManManageDegreePara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManManageDegreePara> findList(CstManManageDegreePara cstManManageDegreePara) {
		return super.findList(cstManManageDegreePara);
	}
	
	@Override
    public Page<CstManManageDegreePara> findPage(Page<CstManManageDegreePara> page, CstManManageDegreePara cstManManageDegreePara) {
		return super.findPage(page, cstManManageDegreePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManManageDegreePara cstManManageDegreePara) {
		super.save(cstManManageDegreePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManManageDegreePara cstManManageDegreePara) {
		super.delete(cstManManageDegreePara);
	}
	
}