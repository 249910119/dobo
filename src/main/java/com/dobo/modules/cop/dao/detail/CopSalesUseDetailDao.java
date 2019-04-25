/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.dao.detail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;

/**
 * 销售员账户使用明细表DAO接口
 * @author admin
 * @version 2017-06-09
 */
@MyBatisDao
public interface CopSalesUseDetailDao extends CrudDao<CopSalesUseDetail> {

	/**
	 * 查询所有数据列表
	 * 
	 * @param createDateStr
	 * @param dcPrjId
	 * @return
	 */
	public List<CopSalesUseDetail> getCaseAccountUsedList(
		@Param("createDateStr") String createDateStr,
		@Param("dcPrjId") String dcPrjId
	);
	
	/**
	 * 查询相同项目号数据列表
	 * 
	 * @param createDateStr
	 * @param dcPrjId
	 * @return
	 */
	public List<CopSalesUseDetail> getSameCaseAccountUsedList(
		@Param("createDateStr") String createDateStr,
		@Param("dcPrjId") String dcPrjId
	);
	
}