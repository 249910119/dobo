/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsCooperModelDetail;
import com.dobo.modules.cst.dao.parts.CstPartsCooperModelDetailDao;

/**
 * 备件合作清单Service
 * @author admin
 * @version 2019-01-11
 */
@Service
@Transactional(readOnly = true)
public class CstPartsCooperModelDetailService extends CrudService<CstPartsCooperModelDetailDao, CstPartsCooperModelDetail> {

	public CstPartsCooperModelDetail get(String id) {
		return super.get(id);
	}
	
	public List<CstPartsCooperModelDetail> findList(CstPartsCooperModelDetail cstPartsCooperModelDetail) {
		return super.findList(cstPartsCooperModelDetail);
	}
	
	public Page<CstPartsCooperModelDetail> findPage(Page<CstPartsCooperModelDetail> page, CstPartsCooperModelDetail cstPartsCooperModelDetail) {
		return super.findPage(page, cstPartsCooperModelDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(CstPartsCooperModelDetail cstPartsCooperModelDetail) {
		super.save(cstPartsCooperModelDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstPartsCooperModelDetail cstPartsCooperModelDetail) {
		super.delete(cstPartsCooperModelDetail);
	}
	
}