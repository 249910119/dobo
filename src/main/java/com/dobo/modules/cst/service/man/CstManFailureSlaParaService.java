/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManFailureSlaPara;
import com.dobo.modules.cst.dao.man.CstManFailureSlaParaDao;

/**
 * 故障级别配比定义表Service
 * @author admin
 * @version 2016-11-08
 */
@Service
@Transactional(readOnly = true)
public class CstManFailureSlaParaService extends CrudService<CstManFailureSlaParaDao, CstManFailureSlaPara> {

	@Override
    public CstManFailureSlaPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManFailureSlaPara> findList(CstManFailureSlaPara cstManFailureSlaPara) {
		return super.findList(cstManFailureSlaPara);
	}
	
	@Override
    public Page<CstManFailureSlaPara> findPage(Page<CstManFailureSlaPara> page, CstManFailureSlaPara cstManFailureSlaPara) {
		return super.findPage(page, cstManFailureSlaPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManFailureSlaPara cstManFailureSlaPara) {
		super.save(cstManFailureSlaPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManFailureSlaPara cstManFailureSlaPara) {
		super.delete(cstManFailureSlaPara);
	}
	
}