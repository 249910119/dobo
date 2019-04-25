/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.service.result;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cp.entity.result.CpStaffInfo;
import com.dobo.modules.cp.dao.result.CpStaffInfoDao;

/**
 * 系统人员Service
 * @author admin
 * @version 2018-06-25
 */
@Service
@Transactional(readOnly = true)
public class CpStaffInfoService extends CrudService<CpStaffInfoDao, CpStaffInfo> {

	public CpStaffInfo get(String id) {
		return super.get(id);
	}
	
	public List<CpStaffInfo> findList(CpStaffInfo cpStaffInfo) {
		return super.findList(cpStaffInfo);
	}
	
	public Page<CpStaffInfo> findPage(Page<CpStaffInfo> page, CpStaffInfo cpStaffInfo) {
		return super.findPage(page, cpStaffInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CpStaffInfo cpStaffInfo) {
		super.save(cpStaffInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CpStaffInfo cpStaffInfo) {
		super.delete(cpStaffInfo);
	}
	
}