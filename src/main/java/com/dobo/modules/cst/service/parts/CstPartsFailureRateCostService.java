/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsFailureRateCost;
import com.dobo.modules.cst.dao.parts.CstPartsFailureRateCostDao;

/**
 * 备件故障率与采购成本定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsFailureRateCostService extends CrudService<CstPartsFailureRateCostDao, CstPartsFailureRateCost> {

	@Override
    public CstPartsFailureRateCost get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsFailureRateCost> findList(CstPartsFailureRateCost cstPartsFailureRateCost) {
		return super.findList(cstPartsFailureRateCost);
	}
	
	@Override
    public Page<CstPartsFailureRateCost> findPage(Page<CstPartsFailureRateCost> page, CstPartsFailureRateCost cstPartsFailureRateCost) {
		return super.findPage(page, cstPartsFailureRateCost);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsFailureRateCost cstPartsFailureRateCost) {
		super.save(cstPartsFailureRateCost);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsFailureRateCost cstPartsFailureRateCost) {
		super.delete(cstPartsFailureRateCost);
	}
	
}