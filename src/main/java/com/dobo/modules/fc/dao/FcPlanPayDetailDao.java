/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcPlanPayDetail;
import com.dobo.modules.fc.rest.entity.FcPlanPayDetailRest;

/**
 * 项目计划付款明细DAO接口
 * @author admin
 * @version 2016-11-06
 */
@MyBatisDao
public interface FcPlanPayDetailDao extends CrudDao<FcPlanPayDetail> {

	/**
	 * 根据项目计划收款时间查询计划付款信息
	 * 
	 * @param planReceiptTimeB
	 * @param planReceiptTimeE
	 * @return
	 */
	public List<FcPlanPayDetailRest> findListByPlanReceiptTime(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE,@Param("projectCode")String projectCode);

	/**
	 * 根据项目立项时间时间查询计划付款信息
	 * 
	 * @param htlxspjsrqB
	 * @param htlxspjsrqE
	 * @return
	 */
	public List<FcPlanPayDetailRest> findListByHtlxrq(@Param("htlxspjsrqB")String htlxspjsrqB, @Param("htlxspjsrqE")String htlxspjsrqE,@Param("projectCode")String projectCode);
	
}