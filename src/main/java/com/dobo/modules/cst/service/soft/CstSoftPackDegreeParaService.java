/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.soft;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.soft.CstSoftPackDegreePara;
import com.dobo.modules.cst.dao.soft.CstSoftPackDegreeParaDao;

/**
 * 系统软件服务资源配比表Service
 * @author admin
 * @version 2017-03-02
 */
@Service
@Transactional(readOnly = true)
public class CstSoftPackDegreeParaService extends CrudService<CstSoftPackDegreeParaDao, CstSoftPackDegreePara> {

	@Override
    public CstSoftPackDegreePara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstSoftPackDegreePara> findList(CstSoftPackDegreePara cstSoftPackDegreePara) {
		return super.findList(cstSoftPackDegreePara);
	}
	
	@Override
    public Page<CstSoftPackDegreePara> findPage(Page<CstSoftPackDegreePara> page, CstSoftPackDegreePara cstSoftPackDegreePara) {
		return super.findPage(page, cstSoftPackDegreePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstSoftPackDegreePara cstSoftPackDegreePara) {
		super.save(cstSoftPackDegreePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstSoftPackDegreePara cstSoftPackDegreePara) {
		super.delete(cstSoftPackDegreePara);
	}
	
}