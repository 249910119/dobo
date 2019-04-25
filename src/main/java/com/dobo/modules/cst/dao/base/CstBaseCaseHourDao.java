/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.base;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.base.CstBaseCaseHour;

/**
 * 故障率工时参数DAO接口
 * @author admin
 * @version 2019-03-18
 */
@MyBatisDao
public interface CstBaseCaseHourDao extends CrudDao<CstBaseCaseHour> {
	
}