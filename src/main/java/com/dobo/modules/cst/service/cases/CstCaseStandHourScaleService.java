/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.cases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.cases.CstCaseStandHourScale;
import com.dobo.modules.cst.dao.cases.CstCaseStandHourScaleDao;

/**
 * 单次、先行支持标准工时系数Service
 * @author admin
 * @version 2017-06-05
 */
@Service
@Transactional(readOnly = true)
public class CstCaseStandHourScaleService extends CrudService<CstCaseStandHourScaleDao, CstCaseStandHourScale> {

	@Override
    public CstCaseStandHourScale get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstCaseStandHourScale> findList(CstCaseStandHourScale cstCaseStandHourScale) {
		return super.findList(cstCaseStandHourScale);
	}
	
	@Override
    public Page<CstCaseStandHourScale> findPage(Page<CstCaseStandHourScale> page, CstCaseStandHourScale cstCaseStandHourScale) {
		return super.findPage(page, cstCaseStandHourScale);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCaseStandHourScale cstCaseStandHourScale) {
		super.save(cstCaseStandHourScale);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCaseStandHourScale cstCaseStandHourScale) {
		super.delete(cstCaseStandHourScale);
	}
	
}