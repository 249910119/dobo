/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.detail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.dao.detail.CstOrderDetailInfoDao;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;

/**
 * 订单明细信息Service
 * @author admin
 * @version 2016-12-02
 */
@Service
@Transactional(readOnly = true)
public class CstOrderDetailInfoService extends CrudService<CstOrderDetailInfoDao, CstOrderDetailInfo> {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected CstOrderDetailInfoDao cstOrderDetailInfoDao;

	/**
	 * 查询所有数据列表
	 * 
	 * @param execOrgNames
	 * @param supportTypes
	 * @param costClassNames
	 * @param prodServiceModes
	 * @param prodStatTypes
	 * @param createDateStr
	 * @param pdId
	 * @param dcPrjId
	 * @return
	 */
	public List<CstOrderDetailInfo> getWbmBaseList(List<String> execOrgNames, List<String> supportTypes, List<String> costClassNames, 
			List<String> prodServiceModes, List<String> prodName, List<String> prodStatTypes, String createDateStr, String pdId, String dcPrjId) {
		return cstOrderDetailInfoDao.getWbmBaseList(execOrgNames, supportTypes, costClassNames, 
				prodServiceModes, prodName, prodStatTypes, createDateStr, pdId, dcPrjId);
	}
	
	@Override
    public CstOrderDetailInfo get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstOrderDetailInfo> findList(CstOrderDetailInfo cstOrderDetailInfo) {
		return super.findList(cstOrderDetailInfo);
	}
	
	@Override
    public Page<CstOrderDetailInfo> findPage(Page<CstOrderDetailInfo> page, CstOrderDetailInfo cstOrderDetailInfo) {
		return super.findPage(page, cstOrderDetailInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstOrderDetailInfo cstOrderDetailInfo) {
		super.save(cstOrderDetailInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstOrderDetailInfo cstOrderDetailInfo) {
		super.delete(cstOrderDetailInfo);
	}
	
}