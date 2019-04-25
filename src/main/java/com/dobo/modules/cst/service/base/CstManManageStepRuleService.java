/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.base.CstManManageStepRule;
import com.dobo.modules.cst.dao.base.CstManManageStepRuleDao;

/**
 * 项目管理工作量阶梯配比Service
 * @author admin
 * @version 2016-12-12
 */
@Service
@Transactional(readOnly = true)
public class CstManManageStepRuleService extends CrudService<CstManManageStepRuleDao, CstManManageStepRule> {

	@Override
    public CstManManageStepRule get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManManageStepRule> findList(CstManManageStepRule cstManManageStepRule) {
		return super.findList(cstManManageStepRule);
	}
	
	@Override
    public Page<CstManManageStepRule> findPage(Page<CstManManageStepRule> page, CstManManageStepRule cstManManageStepRule) {
		return super.findPage(page, cstManManageStepRule);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManManageStepRule cstManManageStepRule) {
		super.save(cstManManageStepRule);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManManageStepRule cstManManageStepRule) {
		super.delete(cstManManageStepRule);
	}
	
}