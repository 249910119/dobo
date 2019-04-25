/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsWeightAmount;
import com.dobo.modules.cst.dao.parts.CstPartsWeightAmountDao;

/**
 * 备件加权平均在保量定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsWeightAmountService extends CrudService<CstPartsWeightAmountDao, CstPartsWeightAmount> {

	@Override
    public CstPartsWeightAmount get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsWeightAmount> findList(CstPartsWeightAmount cstPartsWeightAmount) {
		return super.findList(cstPartsWeightAmount);
	}
	
	@Override
    public Page<CstPartsWeightAmount> findPage(Page<CstPartsWeightAmount> page, CstPartsWeightAmount cstPartsWeightAmount) {
		return super.findPage(page, cstPartsWeightAmount);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsWeightAmount cstPartsWeightAmount) {
		super.save(cstPartsWeightAmount);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsWeightAmount cstPartsWeightAmount) {
		super.delete(cstPartsWeightAmount);
	}
	
}