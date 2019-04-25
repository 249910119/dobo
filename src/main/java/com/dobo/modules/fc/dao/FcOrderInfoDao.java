/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcOrderInfo;
import com.dobo.modules.fc.rest.entity.FcActualOrderInfoRest;
import com.dobo.modules.fc.rest.entity.FcOrderInfoRest;

/**
 * 财务费用计算对应订单信息DAO接口
 * @author admin
 * @version 2016-11-05
 */
@MyBatisDao
public interface FcOrderInfoDao extends CrudDao<FcOrderInfo> {

	/**
	 * 根据项目计划收款时间查询自有订单信息-已过收款期但未完成收款
	 * 
	 * @param planReceiptTimeB
	 * @param projectCode
	 * @return
	 */
	//public List<FcOrderInfoRest> findListByPlanReceiptTimeBefore(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("projectCode")String projectCode);

	/**
	 * 根据订单号从WBM的订单签约表查询成本信息
	 * 
	 * @param wbmOrderIds
	 * @return
	 */
	public List<FcActualOrderInfoRest> findListByWbmOrderid(@Param("wbmOrderIds")List<String> wbmOrderIds);


	/**
	 * 根据项目计划收款时间查询自有订单信息
	 * 
	 * @param planReceiptTimeB
	 * @param planReceiptTimeE
	 * @param projectCode
	 * @return
	 */
	public List<FcActualOrderInfoRest> findListByPlanReceiptTime(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE,@Param("projectCode")String projectCode
			,@Param("fstSvcTypeName")String fstSvcTypeName, @Param("notAllReceived")String notAllReceived);
	
	public List<FcActualOrderInfoRest> findListByActual(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE,@Param("projectCode")String projectCode);

	/**
	 * 根据项目立项时间时间查询自有订单信息
	 * 
	 * @param htlxspjsrqB
	 * @param htlxspjsrqE
	 * @param projectCode
	 * @return
	 */
	public List<FcOrderInfoRest> findListByHtlxrq(@Param("htlxspjsrqB")String htlxspjsrqB, @Param("htlxspjsrqE")String htlxspjsrqE,@Param("projectCode")String projectCode);
	
}