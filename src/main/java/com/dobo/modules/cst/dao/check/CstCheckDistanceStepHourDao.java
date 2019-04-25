/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.check;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.check.CstCheckDistanceStepHour;

/**
 * 巡检-路程工时阶梯系数表DAO接口
 * @author admin
 * @version 2016-11-21
 */
@MyBatisDao
public interface CstCheckDistanceStepHourDao extends CrudDao<CstCheckDistanceStepHour> {
	
}