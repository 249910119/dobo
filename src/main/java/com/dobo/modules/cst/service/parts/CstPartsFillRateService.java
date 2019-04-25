/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsFillRate;
import com.dobo.modules.cst.dao.parts.CstPartsFillRateDao;

/**
 * 备件满足率定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsFillRateService extends CrudService<CstPartsFillRateDao, CstPartsFillRate> {

	@Override
    public CstPartsFillRate get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsFillRate> findList(CstPartsFillRate cstPartsFillRate) {
		return super.findList(cstPartsFillRate);
	}
	
	@Override
    public Page<CstPartsFillRate> findPage(Page<CstPartsFillRate> page, CstPartsFillRate cstPartsFillRate) {
		return super.findPage(page, cstPartsFillRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsFillRate cstPartsFillRate) {
		super.save(cstPartsFillRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsFillRate cstPartsFillRate) {
		super.delete(cstPartsFillRate);
	}
	
}