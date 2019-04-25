/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.base.CstDictPara;
import com.dobo.modules.cst.dao.base.CstDictParaDao;

/**
 * 成本模型字典Service
 * @author admin
 * @version 2016-12-14
 */
@Service
@Transactional(readOnly = true)
public class CstDictParaService extends CrudService<CstDictParaDao, CstDictPara> {

	@Override
    public CstDictPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstDictPara> findList(CstDictPara cstDictPara) {
		return super.findList(cstDictPara);
	}
	
	@Override
    public Page<CstDictPara> findPage(Page<CstDictPara> page, CstDictPara cstDictPara) {
		return super.findPage(page, cstDictPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstDictPara cstDictPara) {
		super.save(cstDictPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstDictPara cstDictPara) {
		super.delete(cstDictPara);
	}
	
}