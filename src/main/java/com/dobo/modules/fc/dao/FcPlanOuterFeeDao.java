/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcPlanOuterFee;

/**
 * 项目计划外财务费用DAO接口
 * @author admin
 * @version 2016-11-06
 */
@MyBatisDao
public interface FcPlanOuterFeeDao extends CrudDao<FcPlanOuterFee> {
	
}