/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.detail;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.detail.CstOrderBackCostInfo;
import com.dobo.modules.cst.dao.detail.CstOrderBackCostInfoDao;

/**
 * 订单备件故障成本（自有、分包）Service
 * @author admin
 * @version 2019-01-18
 */
@Service
@Transactional(readOnly = true)
public class CstOrderBackCostInfoService extends CrudService<CstOrderBackCostInfoDao, CstOrderBackCostInfo> {

	public CstOrderBackCostInfo get(String id) {
		return super.get(id);
	}
	
	public List<CstOrderBackCostInfo> findList(CstOrderBackCostInfo cstOrderBackCostInfo) {
		return super.findList(cstOrderBackCostInfo);
	}
	
	public Page<CstOrderBackCostInfo> findPage(Page<CstOrderBackCostInfo> page, CstOrderBackCostInfo cstOrderBackCostInfo) {
		return super.findPage(page, cstOrderBackCostInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CstOrderBackCostInfo cstOrderBackCostInfo) {
		super.save(cstOrderBackCostInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstOrderBackCostInfo cstOrderBackCostInfo) {
		super.delete(cstOrderBackCostInfo);
	}
	
}