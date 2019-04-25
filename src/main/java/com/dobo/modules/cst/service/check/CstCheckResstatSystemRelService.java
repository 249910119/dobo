/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.check;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.check.CstCheckResstatSystemRel;
import com.dobo.modules.cst.dao.check.CstCheckResstatSystemRelDao;

/**
 * 巡检-资源计划分类对应设备大类关系表Service
 * @author admin
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class CstCheckResstatSystemRelService extends CrudService<CstCheckResstatSystemRelDao, CstCheckResstatSystemRel> {

	@Override
    public CstCheckResstatSystemRel get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstCheckResstatSystemRel> findList(CstCheckResstatSystemRel cstCheckResstatSystemRel) {
		return super.findList(cstCheckResstatSystemRel);
	}
	
	@Override
    public Page<CstCheckResstatSystemRel> findPage(Page<CstCheckResstatSystemRel> page, CstCheckResstatSystemRel cstCheckResstatSystemRel) {
		return super.findPage(page, cstCheckResstatSystemRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckResstatSystemRel cstCheckResstatSystemRel) {
		super.save(cstCheckResstatSystemRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckResstatSystemRel cstCheckResstatSystemRel) {
		super.delete(cstCheckResstatSystemRel);
	}
	
}