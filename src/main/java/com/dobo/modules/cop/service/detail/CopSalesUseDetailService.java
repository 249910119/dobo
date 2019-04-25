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
import com.dobo.modules.cop.dao.detail.CopSalesUseDetailDao;
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;

/**
 * 销售员账户使用明细表Service
 * @author admin
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class CopSalesUseDetailService extends CrudService<CopSalesUseDetailDao, CopSalesUseDetail> {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected CopSalesUseDetailDao copSalesUseDetailDao;
	
	/**
	 * 查询所有数据列表
	 * 
	 * @param createDateStr
	 * @param dcPrjId
	 * @return
	 */
	public List<CopSalesUseDetail> getCaseAccountUsedList(String createDateStr, String dcPrjId) {
		return copSalesUseDetailDao.getCaseAccountUsedList(createDateStr, dcPrjId);
	}
	
	/**
	 * 查询相同项目号的数据列表
	 * 
	 * @param createDateStr
	 * @param dcPrjId
	 * @return
	 */
	public List<CopSalesUseDetail> getSameCaseAccountUsedList(String createDateStr, String dcPrjId) {
		return copSalesUseDetailDao.getSameCaseAccountUsedList(createDateStr, dcPrjId);
	}
	
	@Override
    public CopSalesUseDetail get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopSalesUseDetail> findList(CopSalesUseDetail copSalesUseDetail) {
		return super.findList(copSalesUseDetail);
	}
	
	@Override
    public Page<CopSalesUseDetail> findPage(Page<CopSalesUseDetail> page, CopSalesUseDetail copSalesUseDetail) {
		return super.findPage(page, copSalesUseDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopSalesUseDetail copSalesUseDetail) {
		super.save(copSalesUseDetail);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopSalesUseDetail copSalesUseDetail) {
		super.delete(copSalesUseDetail);
	}
	
}