/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.cases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.cases.CstManCaseSupportPrice;
import com.dobo.modules.cst.dao.cases.CstManCaseSupportPriceDao;

/**
 * 单次、先行支持服务单价表Service
 * @author admin
 * @version 2017-06-05
 */
@Service
@Transactional(readOnly = true)
public class CstManCaseSupportPriceService extends CrudService<CstManCaseSupportPriceDao, CstManCaseSupportPrice> {

	@Override
    public CstManCaseSupportPrice get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManCaseSupportPrice> findList(CstManCaseSupportPrice cstManCaseSupportPrice) {
		return super.findList(cstManCaseSupportPrice);
	}
	
	@Override
    public Page<CstManCaseSupportPrice> findPage(Page<CstManCaseSupportPrice> page, CstManCaseSupportPrice cstManCaseSupportPrice) {
		return super.findPage(page, cstManCaseSupportPrice);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManCaseSupportPrice cstManCaseSupportPrice) {
		super.save(cstManCaseSupportPrice);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManCaseSupportPrice cstManCaseSupportPrice) {
		super.delete(cstManCaseSupportPrice);
	}
	
}