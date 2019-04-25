/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManFailureManRate;
import com.dobo.modules.cst.dao.man.CstManFailureManRateDao;

/**
 * 故障人工费率定义表Service
 * @author admin
 * @version 2016-11-08
 */
@Service
@Transactional(readOnly = true)
public class CstManFailureManRateService extends CrudService<CstManFailureManRateDao, CstManFailureManRate> {

	@Override
    public CstManFailureManRate get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManFailureManRate> findList(CstManFailureManRate cstManFailureManRate) {
		return super.findList(cstManFailureManRate);
	}
	
	@Override
    public Page<CstManFailureManRate> findPage(Page<CstManFailureManRate> page, CstManFailureManRate cstManFailureManRate) {
		return super.findPage(page, cstManFailureManRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManFailureManRate cstManFailureManRate) {
		super.save(cstManFailureManRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManFailureManRate cstManFailureManRate) {
		super.delete(cstManFailureManRate);
	}
	
}