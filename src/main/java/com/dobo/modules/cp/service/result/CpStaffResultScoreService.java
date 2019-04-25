/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.service.result;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cp.entity.result.CpStaffResultScore;
import com.dobo.modules.cp.dao.result.CpStaffResultScoreDao;

/**
 * 系统人员测评记录结果Service
 * @author admin
 * @version 2018-06-08
 */
@Service
@Transactional(readOnly = true)
public class CpStaffResultScoreService extends CrudService<CpStaffResultScoreDao, CpStaffResultScore> {

	public CpStaffResultScore get(String id) {
		return super.get(id);
	}
	
	public List<CpStaffResultScore> findList(CpStaffResultScore cpStaffResultScore) {
		return super.findList(cpStaffResultScore);
	}
	
	public Page<CpStaffResultScore> findPage(Page<CpStaffResultScore> page, CpStaffResultScore cpStaffResultScore) {
		return super.findPage(page, cpStaffResultScore);
	}
	
	@Transactional(readOnly = false)
	public void save(CpStaffResultScore cpStaffResultScore) {
		super.save(cpStaffResultScore);
	}
	
	@Transactional(readOnly = false)
	public void delete(CpStaffResultScore cpStaffResultScore) {
		super.delete(cpStaffResultScore);
	}
	
}