/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.soft;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.soft.CstSoftPackStepRule;
import com.dobo.modules.cst.dao.soft.CstSoftPackStepRuleDao;

/**
 * 系统软件服务规模阶梯配比表Service
 * @author admin
 * @version 2017-03-02
 */
@Service
@Transactional(readOnly = true)
public class CstSoftPackStepRuleService extends CrudService<CstSoftPackStepRuleDao, CstSoftPackStepRule> {

	@Override
    public CstSoftPackStepRule get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstSoftPackStepRule> findList(CstSoftPackStepRule cstSoftPackStepRule) {
		return super.findList(cstSoftPackStepRule);
	}
	
	@Override
    public Page<CstSoftPackStepRule> findPage(Page<CstSoftPackStepRule> page, CstSoftPackStepRule cstSoftPackStepRule) {
		return super.findPage(page, cstSoftPackStepRule);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstSoftPackStepRule cstSoftPackStepRule) {
		super.save(cstSoftPackStepRule);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstSoftPackStepRule cstSoftPackStepRule) {
		super.delete(cstSoftPackStepRule);
	}
	
}