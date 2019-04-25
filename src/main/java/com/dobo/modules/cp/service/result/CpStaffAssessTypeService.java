/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.service.result;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cp.entity.result.CpStaffAssessType;
import com.dobo.modules.cp.dao.result.CpStaffAssessTypeDao;

/**
 * 系统人员测评行为、能力和模块划分Service
 * @author admin
 * @version 2018-06-08
 */
@Service
@Transactional(readOnly = true)
public class CpStaffAssessTypeService extends CrudService<CpStaffAssessTypeDao, CpStaffAssessType> {

	public CpStaffAssessType get(String id) {
		return super.get(id);
	}
	
	public List<CpStaffAssessType> findList(CpStaffAssessType cpStaffAssessType) {
		return super.findList(cpStaffAssessType);
	}
	
	public Page<CpStaffAssessType> findPage(Page<CpStaffAssessType> page, CpStaffAssessType cpStaffAssessType) {
		return super.findPage(page, cpStaffAssessType);
	}
	
	@Transactional(readOnly = false)
	public void save(CpStaffAssessType cpStaffAssessType) {
		super.save(cpStaffAssessType);
	}
	
	@Transactional(readOnly = false)
	public void delete(CpStaffAssessType cpStaffAssessType) {
		super.delete(cpStaffAssessType);
	}
	
}