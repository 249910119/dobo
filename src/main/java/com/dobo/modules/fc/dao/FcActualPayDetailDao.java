/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcActualPayDetail;
import com.dobo.modules.fc.rest.entity.FcActualPayDetailRest;

/**
 * 财务费用计算需要的实付金额DAO接口
 * @author admin
 * @version 2017-11-30
 */
@MyBatisDao
public interface FcActualPayDetailDao extends CrudDao<FcActualPayDetail> {
	
	public Date getMaxPayDate();
	
	public List<FcActualPayDetailRest> findListByActualPayTime(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE, @Param("projectCode")String projectCode);
	
	public List<FcActualPayDetailRest> findListByPlanReceiptTime(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE, @Param("projectCode")String projectCode
			,@Param("fstSvcTypeName")String fstSvcTypeName, @Param("notAllReceived")String notAllReceived);

	public List<FcActualPayDetailRest> findListByActualPayReceiptTime(@Param("planReceiptTimeB")String planReceiptTimeB, @Param("planReceiptTimeE")String planReceiptTimeE, @Param("projectCode")String projectCode
			,@Param("fstSvcTypeName")String fstSvcTypeName, @Param("notAllReceived")String notAllReceived);

}