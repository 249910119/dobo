/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.base.CstBaseBackFaultPara;
import com.dobo.modules.cst.dao.base.CstBaseBackFaultParaDao;

/**
 * 备件故障率参数获取Service
 * @author admin
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class CstBaseBackFaultParaService extends CrudService<CstBaseBackFaultParaDao, CstBaseBackFaultPara> {

	public CstBaseBackFaultPara get(String id) {
		return super.get(id);
	}
	
	public List<CstBaseBackFaultPara> findList(CstBaseBackFaultPara cstBaseBackFaultPara) {
		return super.findList(cstBaseBackFaultPara);
	}
	
	public Page<CstBaseBackFaultPara> findPage(Page<CstBaseBackFaultPara> page, CstBaseBackFaultPara cstBaseBackFaultPara) {
		return super.findPage(page, cstBaseBackFaultPara);
	}
	
	@Transactional(readOnly = false)
	public void save(CstBaseBackFaultPara cstBaseBackFaultPara) {
		super.save(cstBaseBackFaultPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstBaseBackFaultPara cstBaseBackFaultPara) {
		super.delete(cstBaseBackFaultPara);
	}
	
}