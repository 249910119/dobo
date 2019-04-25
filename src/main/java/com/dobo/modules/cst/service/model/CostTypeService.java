/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.model;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.dao.model.CostTypeDao;
import com.dobo.modules.cst.entity.model.CostType;

/**
 * 成本类型定义
 * @author admin
 * @version 2016-11-13
 */
@Service
@Transactional(readOnly = true)
public class CostTypeService extends CrudService<CostTypeDao, CostType> {

	@Override
    public CostType get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CostType> findList(CostType costType) {
		return super.findList(costType);
	}
	
	@Override
    public Page<CostType> findPage(Page<CostType> page, CostType costType) {
		return super.findPage(page, costType);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CostType costType) {
		super.save(costType);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CostType costType) {
		super.delete(costType);
	}
	
}