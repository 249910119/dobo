/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.check;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.check.CstCheckResmgrHour;
import com.dobo.modules.cst.dao.check.CstCheckResmgrHourDao;

/**
 * 巡检-资源岗巡检安排工时表Service
 * @author admin
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class CstCheckResmgrHourService extends CrudService<CstCheckResmgrHourDao, CstCheckResmgrHour> {

	@Override
    public CstCheckResmgrHour get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstCheckResmgrHour> findList(CstCheckResmgrHour cstCheckResmgrHour) {
		return super.findList(cstCheckResmgrHour);
	}
	
	@Override
    public Page<CstCheckResmgrHour> findPage(Page<CstCheckResmgrHour> page, CstCheckResmgrHour cstCheckResmgrHour) {
		return super.findPage(page, cstCheckResmgrHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckResmgrHour cstCheckResmgrHour) {
		super.save(cstCheckResmgrHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckResmgrHour cstCheckResmgrHour) {
		super.delete(cstCheckResmgrHour);
	}
	
}