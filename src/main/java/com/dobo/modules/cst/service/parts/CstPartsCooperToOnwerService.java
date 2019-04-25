/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsCooperToOnwer;
import com.dobo.modules.cst.dao.parts.CstPartsCooperToOnwerDao;

/**
 * 备件合作转自有时间清单Service
 * @author admin
 * @version 2019-01-10
 */
@Service
@Transactional(readOnly = true)
public class CstPartsCooperToOnwerService extends CrudService<CstPartsCooperToOnwerDao, CstPartsCooperToOnwer> {

	public CstPartsCooperToOnwer get(String id) {
		return super.get(id);
	}
	
	public List<CstPartsCooperToOnwer> findList(CstPartsCooperToOnwer cstPartsCooperToOnwer) {
		return super.findList(cstPartsCooperToOnwer);
	}
	
	public Page<CstPartsCooperToOnwer> findPage(Page<CstPartsCooperToOnwer> page, CstPartsCooperToOnwer cstPartsCooperToOnwer) {
		return super.findPage(page, cstPartsCooperToOnwer);
	}
	
	@Transactional(readOnly = false)
	public void save(CstPartsCooperToOnwer cstPartsCooperToOnwer) {
		super.save(cstPartsCooperToOnwer);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstPartsCooperToOnwer cstPartsCooperToOnwer) {
		super.delete(cstPartsCooperToOnwer);
	}
	
}