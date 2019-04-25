/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcProjectInfo;
import com.dobo.modules.fc.rest.entity.FcProjectInfoRest;

/**
 * 财务费用计算对应项目信息DAO接口
 * @author admin
 * @version 2016-11-05
 */
@MyBatisDao
public interface FcProjectInfoDao extends CrudDao<FcProjectInfo> {

	/**
	 * 根据项目计划收款时间查询项目信息-已过收款期但未完成收款
	 * 
	 * @param planReceiptTimeB
	 * @param projectCode
	 * @return
	 */
	//public List<FcProjectInfoRest> findListByPlanReceiptTimeBefore(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("projectCode")String projectCode);


	/**
	 * 根据项目计划收款时间查询项目信息
	 * 
	 * @param planReceiptTimeB
	 * @param planReceiptTimeE
	 * @param projectCode
	 * @return
	 */
	public List<FcProjectInfoRest> findListByPlanReceiptTime(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE,@Param("projectCode")String projectCode
			,@Param("fstSvcTypeName")String fstSvcTypeName, @Param("notAllReceived")String notAllReceived);
	public List<FcProjectInfoRest> findListByActual(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE,@Param("projectCode")String projectCode);

	/**
	 * 根据项目立项时间时间查询项目信息
	 * 
	 * @param htlxspjsrqB
	 * @param htlxspjsrqE
	 * @param projectCode
	 * @return
	 */
	public List<FcProjectInfoRest> findListByHtlxrq(@Param("htlxspjsrqB")String htlxspjsrqB, @Param("htlxspjsrqE")String htlxspjsrqE,@Param("projectCode")String projectCode);
	
}