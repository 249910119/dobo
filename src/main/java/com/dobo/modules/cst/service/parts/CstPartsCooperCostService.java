/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsCooperCost;
import com.dobo.modules.cst.dao.parts.CstPartsCooperCostDao;

/**
 * 备件合作成本参数Service
 * @author admin
 * @version 2019-01-11
 */
@Service
@Transactional(readOnly = true)
public class CstPartsCooperCostService extends CrudService<CstPartsCooperCostDao, CstPartsCooperCost> {

	public CstPartsCooperCost get(String id) {
		return super.get(id);
	}
	
	public List<CstPartsCooperCost> findList(CstPartsCooperCost cstPartsCooperCost) {
		return super.findList(cstPartsCooperCost);
	}
	
	public Page<CstPartsCooperCost> findPage(Page<CstPartsCooperCost> page, CstPartsCooperCost cstPartsCooperCost) {
		return super.findPage(page, cstPartsCooperCost);
	}
	
	@Transactional(readOnly = false)
	public void save(CstPartsCooperCost cstPartsCooperCost) {
		super.save(cstPartsCooperCost);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstPartsCooperCost cstPartsCooperCost) {
		super.delete(cstPartsCooperCost);
	}
	
}