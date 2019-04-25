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
import com.dobo.modules.cop.dao.detail.CopCaseDetailPriceDao;
import com.dobo.modules.cop.entity.detail.CopCaseDetailPrice;

/**
 * 单次、先行支持服务报价明细Service
 * @author admin
 * @version 2017-06-08
 */
@Service
@Transactional(readOnly = true)
public class CopCaseDetailPriceService extends CrudService<CopCaseDetailPriceDao, CopCaseDetailPrice> {

	@Autowired
	CopCaseDetailPriceDao copCaseDetailPriceDao;
	
	@Override
    public CopCaseDetailPrice get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopCaseDetailPrice> findList(CopCaseDetailPrice copCaseDetailPrice) {
		return super.findList(copCaseDetailPrice);
	}
	
	@Override
    public Page<CopCaseDetailPrice> findPage(Page<CopCaseDetailPrice> page, CopCaseDetailPrice copCaseDetailPrice) {
		return super.findPage(page, copCaseDetailPrice);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopCaseDetailPrice copCaseDetailPrice) {
		super.save(copCaseDetailPrice);
	}
	
	@Transactional(readOnly = false)
	public void addDetailbatch(List<CopCaseDetailPrice> copCaseDetailPriceList) {
		copCaseDetailPriceDao.addDetailBatch(copCaseDetailPriceList);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopCaseDetailPrice copCaseDetailPrice) {
		super.delete(copCaseDetailPrice);
	}
	
	/**
	 * 查询所有数据列表
	 * 
	 * @param onceCodes
	 * @param status
	 * @return
	 */
	public List<CopCaseDetailPrice> getCasePriceBatch(List<String> onceCodes, String status) {
		return copCaseDetailPriceDao.getCasePriceBatch(onceCodes, status);
	}
	
}