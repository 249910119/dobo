/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.dao.parts.CstPartsDictParaDao;
import com.dobo.modules.cst.entity.parts.CstPartsDictPara;

/**
 * 备件参数字典定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsDictParaService extends CrudService<CstPartsDictParaDao, CstPartsDictPara> {

	@Override
    public CstPartsDictPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsDictPara> findList(CstPartsDictPara cstPartsDictPara) {
		return super.findList(cstPartsDictPara);
	}
	
	@Override
    public Page<CstPartsDictPara> findPage(Page<CstPartsDictPara> page, CstPartsDictPara cstPartsDictPara) {
		return super.findPage(page, cstPartsDictPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsDictPara cstPartsDictPara) {
		super.save(cstPartsDictPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsDictPara cstPartsDictPara) {
		super.delete(cstPartsDictPara);
	}
	
}