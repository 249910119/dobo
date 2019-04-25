/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.dao.model.CstModelProdModuleRelDao;

/**
 * 产品成本模型使用定义表，定义某产品使用的成本模型Service
 * @author admin
 * @version 2016-11-12
 */
@Service
@Transactional(readOnly = true)
public class CstModelProdModuleRelService extends CrudService<CstModelProdModuleRelDao, CstModelProdModuleRel> {

	
	@Override
    public CstModelProdModuleRel get(String id) {
		CstModelProdModuleRel cstModelProdModuleRel = super.get(id);
		return cstModelProdModuleRel;
	}
	
	@Override
    public List<CstModelProdModuleRel> findList(CstModelProdModuleRel cstModelProdModuleRel) {
		return super.findList(cstModelProdModuleRel);
	}
	
	@Override
    public Page<CstModelProdModuleRel> findPage(Page<CstModelProdModuleRel> page, CstModelProdModuleRel cstModelProdModuleRel) {
		return super.findPage(page, cstModelProdModuleRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstModelProdModuleRel cstModelProdModuleRel) {
		super.save(cstModelProdModuleRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstModelProdModuleRel cstModelProdModuleRel) {
		super.delete(cstModelProdModuleRel);
	}
	
}