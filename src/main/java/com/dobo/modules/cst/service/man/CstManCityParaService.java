/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManCityPara;
import com.dobo.modules.cst.dao.man.CstManCityParaDao;

/**
 * 地域系数定义表Service
 * @author admin
 * @version 2016-11-07
 */
@Service
@Transactional(readOnly = true)
public class CstManCityParaService extends CrudService<CstManCityParaDao, CstManCityPara> {

	@Override
    public CstManCityPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManCityPara> findList(CstManCityPara cstManCityPara) {
		return super.findList(cstManCityPara);
	}
	
	@Override
    public Page<CstManCityPara> findPage(Page<CstManCityPara> page, CstManCityPara cstManCityPara) {
		return super.findPage(page, cstManCityPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManCityPara cstManCityPara) {
		super.save(cstManCityPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManCityPara cstManCityPara) {
		super.delete(cstManCityPara);
	}
	
}