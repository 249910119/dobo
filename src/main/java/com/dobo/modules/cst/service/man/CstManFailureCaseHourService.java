/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManFailureCaseHour;
import com.dobo.modules.cst.dao.man.CstManFailureCaseHourDao;

/**
 * 故障CASE处理工时定义表Service
 * @author admin
 * @version 2016-11-08
 */
@Service
@Transactional(readOnly = true)
public class CstManFailureCaseHourService extends CrudService<CstManFailureCaseHourDao, CstManFailureCaseHour> {

	@Override
    public CstManFailureCaseHour get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManFailureCaseHour> findList(CstManFailureCaseHour cstManFailureCaseHour) {
		return super.findList(cstManFailureCaseHour);
	}
	
	@Override
    public Page<CstManFailureCaseHour> findPage(Page<CstManFailureCaseHour> page, CstManFailureCaseHour cstManFailureCaseHour) {
		return super.findPage(page, cstManFailureCaseHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManFailureCaseHour cstManFailureCaseHour) {
		super.save(cstManFailureCaseHour);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManFailureCaseHour cstManFailureCaseHour) {
		super.delete(cstManFailureCaseHour);
	}
	
}