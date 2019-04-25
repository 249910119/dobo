/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.service.detail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cop.dao.detail.CopCaseDetailDao;
import com.dobo.modules.cop.entity.detail.CopCaseDetail;

/**
 * top单次、先行支持服务报价清单Service
 * @author admin
 * @version 2017-06-08
 */
@Service
@Transactional(readOnly = true)
public class CopCaseDetailService extends CrudService<CopCaseDetailDao, CopCaseDetail> {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected CopCaseDetailDao copCaseDetailDao;
	
	/**
	 * 查询所有数据列表
	 * 
	 * @param createDateStr
	 * @param onceCode
	 * @return
	 */
	public List<CopCaseDetail> getCaseConfirmList(String createDateStr, String onceCode) {
		return copCaseDetailDao.getCaseConfirmList(createDateStr, onceCode);
	}
	
	/**
	 * 查询所有数据列表
	 * 
	 * @param onceCodes
	 * @param status
	 * @return
	 */
	public List<CopCaseDetail> getCaseBatch(List<String> onceCodes, String status) {
		return copCaseDetailDao.getCaseBatch(onceCodes, status);
	}
	
	@Override
    public CopCaseDetail get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopCaseDetail> findList(CopCaseDetail copCaseDetail) {
		return super.findList(copCaseDetail);
	}
	
	@Override
    public Page<CopCaseDetail> findPage(Page<CopCaseDetail> page, CopCaseDetail copCaseDetail) {
		return super.findPage(page, copCaseDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopCaseDetail copCaseDetail) {
		super.save(copCaseDetail);
	}
	
	@Transactional(readOnly = false)
	public void insert(CopCaseDetail copCaseDetail) {
		copCaseDetailDao.insert(copCaseDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopCaseDetail copCaseDetail) {
		super.delete(copCaseDetail);
	}
	
}