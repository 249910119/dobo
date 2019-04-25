/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcInterrestRate;

/**
 * 存息贷息利率定义DAO接口
 * @author admin
 * @version 2016-11-05
 */
@MyBatisDao
public interface FcInterrestRateDao extends CrudDao<FcInterrestRate> {
	
	/**
	 * 获取最新贷款利率
	 * @param rateType
	 * @param createDateStr
	 * @return
	 */
	public FcInterrestRate getLatestInterrestRate(@Param("rateType")String rateType,@Param("createDateStr")String createDateStr);
}