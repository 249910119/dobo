/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.check;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.check.CstCheckDistanceUnitHour;

/**
 * 巡检-单台路程工时表DAO接口
 * @author admin
 * @version 2016-11-22
 */
@MyBatisDao
public interface CstCheckDistanceUnitHourDao extends CrudDao<CstCheckDistanceUnitHour> {
	
}