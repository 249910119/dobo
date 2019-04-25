/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.check;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.check.CstCheckFirstcheckPara;
import com.dobo.modules.cst.dao.check.CstCheckFirstcheckParaDao;

/**
 * 巡检-首次巡检系数表Service
 * @author admin
 * @version 2016-11-21
 */
@Service
@Transactional(readOnly = true)
public class CstCheckFirstcheckParaService extends CrudService<CstCheckFirstcheckParaDao, CstCheckFirstcheckPara> {

	@Override
    public CstCheckFirstcheckPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstCheckFirstcheckPara> findList(CstCheckFirstcheckPara cstCheckFirstcheckPara) {
		return super.findList(cstCheckFirstcheckPara);
	}
	
	@Override
    public Page<CstCheckFirstcheckPara> findPage(Page<CstCheckFirstcheckPara> page, CstCheckFirstcheckPara cstCheckFirstcheckPara) {
		return super.findPage(page, cstCheckFirstcheckPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstCheckFirstcheckPara cstCheckFirstcheckPara) {
		super.save(cstCheckFirstcheckPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstCheckFirstcheckPara cstCheckFirstcheckPara) {
		super.delete(cstCheckFirstcheckPara);
	}
	
}