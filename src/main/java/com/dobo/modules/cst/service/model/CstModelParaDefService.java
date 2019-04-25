/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.entity.model.CstModelParaDef;
import com.dobo.modules.cst.dao.model.CstModelParaDefDao;

/**
 * 成本参数定义表：定义成本模型公式中用到的简单和复杂参数Service
 * @author admin
 * @version 2016-11-11
 */
@Service
@Transactional(readOnly = true)
public class CstModelParaDefService extends CrudService<CstModelParaDefDao, CstModelParaDef> {

	
	@Override
    public CstModelParaDef get(String id) {
		CstModelParaDef cstModelParaDef = super.get(id);
		return cstModelParaDef;
	}
	
	@Override
    public List<CstModelParaDef> findList(CstModelParaDef cstModelParaDef) {
		return super.findList(cstModelParaDef);
	}
	
	@Override
    public Page<CstModelParaDef> findPage(Page<CstModelParaDef> page, CstModelParaDef cstModelParaDef) {
		return super.findPage(page, cstModelParaDef);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstModelParaDef cstModelParaDef) {
		super.save(cstModelParaDef);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstModelParaDef cstModelParaDef) {
		super.delete(cstModelParaDef);
	}
	
}