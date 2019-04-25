/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.check;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.check.CstCheckDistanceStepHour;
import com.dobo.modules.cst.dao.check.CstCheckDistanceStepHourDao;

/**
 * 巡检-路程工时阶梯系数表Service
 * @author admin
 * @version 2016-11-21
 */
@Service
@Transactional(readOnly = true)
public class CstCheckDistanceStepHourService extends CrudService<CstCheckDistanceStepHourDao, CstCheckDistanceStepHour> {

	@Override
    public CstCheckDistanceStepHour get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstCheckDistanceStepHour> findList(CstCheckDistanceStepHour cstCheckDistanceStepHour) {
		return super.findList(cstCheckDistanceStepHour);
	}
	
	@Override
    public Page<CstCheckDistanceStepHour> findPage(Page<CstCheckDistanceStepHour> page, CstCheckDistanceStepHour cstCheckDistanceStepHour) {
		return super.findPage(page, cstCheckDistanceStepHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckDistanceStepHour cstCheckDistanceStepHour) {
		super.save(cstCheckDistanceStepHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckDistanceStepHour cstCheckDistanceStepHour) {
		super.delete(cstCheckDistanceStepHour);
	}
	
}