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
import com.dobo.modules.cst.entity.check.CstCheckSlaPara;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;
import com.dobo.modules.cst.dao.check.CstCheckSlaParaDao;
import com.dobo.modules.cst.dao.check.CstCheckWorkHourDao;

/**
 * 巡检级别配比定义Service
 * @author admin
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class CstCheckSlaParaService extends CrudService<CstCheckSlaParaDao, CstCheckSlaPara> {

	@Autowired
	protected CstCheckSlaParaDao cstCheckSlaParaDao;
	
	@Override
    public CstCheckSlaPara get(String id) {
		return super.get(id);
	}
	
    public List<CstCheckSlaPara> getBaseCheckSlaPara() {
		return cstCheckSlaParaDao.getBaseCheckSlaPara();
	}
	
	@Override
    public List<CstCheckSlaPara> findList(CstCheckSlaPara cstCheckSlaPara) {
		return super.findList(cstCheckSlaPara);
	}
	
	@Override
    public Page<CstCheckSlaPara> findPage(Page<CstCheckSlaPara> page, CstCheckSlaPara cstCheckSlaPara) {
		return super.findPage(page, cstCheckSlaPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckSlaPara cstCheckSlaPara) {
		super.save(cstCheckSlaPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckSlaPara cstCheckSlaPara) {
		super.delete(cstCheckSlaPara);
	}
	
}