/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.check;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.dao.check.CstCheckWorkHourDao;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;

/**
 * 巡检工时定义表Service
 * @author admin
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class CstCheckWorkHourService extends CrudService<CstCheckWorkHourDao, CstCheckWorkHour> {

	@Autowired
	protected CstCheckWorkHourDao cstCheckWorkHourDao;
	
	@Override
    public CstCheckWorkHour get(String id) {
		return super.get(id);
	}
	
    public List<CstCheckWorkHour> getBaseCheckWorkHour() {
		return cstCheckWorkHourDao.getBaseCheckWorkHour();
	}
	
	@Override
    public List<CstCheckWorkHour> findList(CstCheckWorkHour cstCheckWorkHour) {
		return super.findList(cstCheckWorkHour);
	}
	
	@Override
    public Page<CstCheckWorkHour> findPage(Page<CstCheckWorkHour> page, CstCheckWorkHour cstCheckWorkHour) {
		return super.findPage(page, cstCheckWorkHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckWorkHour cstCheckWorkHour) {
		super.save(cstCheckWorkHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckWorkHour cstCheckWorkHour) {
		super.delete(cstCheckWorkHour);
	}
	
}