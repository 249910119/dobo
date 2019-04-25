/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.model;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.model.CstModelModuleInfo;
import com.dobo.modules.cst.dao.model.CstModelModuleInfoDao;

/**
 * 成本模型信息表，定义成本一级分类（人工类、备件类）Service
 * @author admin
 * @version 2016-11-09
 */
@Service
@Transactional(readOnly = true)
public class CstModelModuleInfoService extends CrudService<CstModelModuleInfoDao, CstModelModuleInfo> {

	@Override
    public CstModelModuleInfo get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstModelModuleInfo> findList(CstModelModuleInfo cstModelModuleInfo) {
		return super.findList(cstModelModuleInfo);
	}
	
	@Override
    public Page<CstModelModuleInfo> findPage(Page<CstModelModuleInfo> page, CstModelModuleInfo cstModelModuleInfo) {
		return super.findPage(page, cstModelModuleInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstModelModuleInfo cstModelModuleInfo) {
		super.save(cstModelModuleInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstModelModuleInfo cstModelModuleInfo) {
		super.delete(cstModelModuleInfo);
	}
	
}