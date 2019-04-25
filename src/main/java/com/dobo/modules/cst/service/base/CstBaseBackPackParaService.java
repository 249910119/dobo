/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.base.CstBaseBackPackPara;
import com.dobo.modules.cst.dao.base.CstBaseBackPackParaDao;

/**
 * 备件合作包取值参数获取Service
 * @author admin
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class CstBaseBackPackParaService extends CrudService<CstBaseBackPackParaDao, CstBaseBackPackPara> {

	public CstBaseBackPackPara get(String id) {
		return super.get(id);
	}
	
	public List<CstBaseBackPackPara> findList(CstBaseBackPackPara cstBaseBackPackPara) {
		return super.findList(cstBaseBackPackPara);
	}
	
	public Page<CstBaseBackPackPara> findPage(Page<CstBaseBackPackPara> page, CstBaseBackPackPara cstBaseBackPackPara) {
		return super.findPage(page, cstBaseBackPackPara);
	}
	
	@Transactional(readOnly = false)
	public void save(CstBaseBackPackPara cstBaseBackPackPara) {
		super.save(cstBaseBackPackPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstBaseBackPackPara cstBaseBackPackPara) {
		super.delete(cstBaseBackPackPara);
	}
	
}