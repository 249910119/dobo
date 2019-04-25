/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcPlanInnerFee;

/**
 * 项目计划内财务费用DAO接口
 * @author admin
 * @version 2016-11-06
 */
@MyBatisDao
public interface FcPlanInnerFeeDao extends CrudDao<FcPlanInnerFee> {
	/**
	 * 获取最大的计算批次号
	 * @return
	 */
	public Long getMaxCalculateNumber();
}