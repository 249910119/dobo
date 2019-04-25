/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.base.CstBaseBackCasePara;
import com.dobo.modules.cst.dao.base.CstBaseBackCaseParaDao;

/**
 * 备件故障事件级别占比参数获取Service
 * @author admin
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class CstBaseBackCaseParaService extends CrudService<CstBaseBackCaseParaDao, CstBaseBackCasePara> {

	public CstBaseBackCasePara get(String id) {
		return super.get(id);
	}
	
	public List<CstBaseBackCasePara> findList(CstBaseBackCasePara cstBaseBackCasePara) {
		return super.findList(cstBaseBackCasePara);
	}
	
	public Page<CstBaseBackCasePara> findPage(Page<CstBaseBackCasePara> page, CstBaseBackCasePara cstBaseBackCasePara) {
		return super.findPage(page, cstBaseBackCasePara);
	}
	
	@Transactional(readOnly = false)
	public void save(CstBaseBackCasePara cstBaseBackCasePara) {
		super.save(cstBaseBackCasePara);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstBaseBackCasePara cstBaseBackCasePara) {
		super.delete(cstBaseBackCasePara);
	}
	
}