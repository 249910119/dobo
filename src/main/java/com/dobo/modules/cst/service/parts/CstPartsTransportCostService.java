/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsTransportCost;
import com.dobo.modules.cst.dao.parts.CstPartsTransportCostDao;

/**
 * 备件发货运输平均成本定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsTransportCostService extends CrudService<CstPartsTransportCostDao, CstPartsTransportCost> {

	@Override
    public CstPartsTransportCost get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsTransportCost> findList(CstPartsTransportCost cstPartsTransportCost) {
		return super.findList(cstPartsTransportCost);
	}
	
	@Override
    public Page<CstPartsTransportCost> findPage(Page<CstPartsTransportCost> page, CstPartsTransportCost cstPartsTransportCost) {
		return super.findPage(page, cstPartsTransportCost);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsTransportCost cstPartsTransportCost) {
		super.save(cstPartsTransportCost);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsTransportCost cstPartsTransportCost) {
		super.delete(cstPartsTransportCost);
	}
	
}