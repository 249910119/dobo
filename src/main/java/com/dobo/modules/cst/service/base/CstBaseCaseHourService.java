/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.base.CstBaseCaseHour;
import com.dobo.modules.cst.dao.base.CstBaseCaseHourDao;

/**
 * 故障率工时参数Service
 * @author admin
 * @version 2019-03-18
 */
@Service
@Transactional(readOnly = true)
public class CstBaseCaseHourService extends CrudService<CstBaseCaseHourDao, CstBaseCaseHour> {

	public CstBaseCaseHour get(String id) {
		return super.get(id);
	}
	
	public List<CstBaseCaseHour> findList(CstBaseCaseHour cstBaseCaseHour) {
		return super.findList(cstBaseCaseHour);
	}
	
	public Page<CstBaseCaseHour> findPage(Page<CstBaseCaseHour> page, CstBaseCaseHour cstBaseCaseHour) {
		return super.findPage(page, cstBaseCaseHour);
	}
	
	@Transactional(readOnly = false)
	public void save(CstBaseCaseHour cstBaseCaseHour) {
		super.save(cstBaseCaseHour);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstBaseCaseHour cstBaseCaseHour) {
		super.delete(cstBaseCaseHour);
	}
	
}