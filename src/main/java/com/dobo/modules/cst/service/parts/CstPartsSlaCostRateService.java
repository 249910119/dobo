/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsSlaCostRate;
import com.dobo.modules.cst.dao.parts.CstPartsSlaCostRateDao;

/**
 * 故障成本SLA采购成本系数Service
 * @author admin
 * @version 2017-01-11
 */
@Service
@Transactional(readOnly = true)
public class CstPartsSlaCostRateService extends CrudService<CstPartsSlaCostRateDao, CstPartsSlaCostRate> {

	@Override
    public CstPartsSlaCostRate get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsSlaCostRate> findList(CstPartsSlaCostRate cstPartsSlaCostRate) {
		return super.findList(cstPartsSlaCostRate);
	}
	
	@Override
    public Page<CstPartsSlaCostRate> findPage(Page<CstPartsSlaCostRate> page, CstPartsSlaCostRate cstPartsSlaCostRate) {
		return super.findPage(page, cstPartsSlaCostRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsSlaCostRate cstPartsSlaCostRate) {
		super.save(cstPartsSlaCostRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsSlaCostRate cstPartsSlaCostRate) {
		super.delete(cstPartsSlaCostRate);
	}
	
}