/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.check;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.check.CstCheckDistanceUnitHour;
import com.dobo.modules.cst.dao.check.CstCheckDistanceUnitHourDao;

/**
 * 巡检-单台路程工时表Service
 * @author admin
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class CstCheckDistanceUnitHourService extends CrudService<CstCheckDistanceUnitHourDao, CstCheckDistanceUnitHour> {

	@Override
    public CstCheckDistanceUnitHour get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstCheckDistanceUnitHour> findList(CstCheckDistanceUnitHour cstCheckDistanceUnitHour) {
		return super.findList(cstCheckDistanceUnitHour);
	}
	
	@Override
    public Page<CstCheckDistanceUnitHour> findPage(Page<CstCheckDistanceUnitHour> page, CstCheckDistanceUnitHour cstCheckDistanceUnitHour) {
		return super.findPage(page, cstCheckDistanceUnitHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckDistanceUnitHour cstCheckDistanceUnitHour) {
		super.save(cstCheckDistanceUnitHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckDistanceUnitHour cstCheckDistanceUnitHour) {
		super.delete(cstCheckDistanceUnitHour);
	}
	
}