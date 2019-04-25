/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.check;

import java.util.List;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;

/**
 * 巡检工时定义表DAO接口
 * @author admin
 * @version 2016-11-22
 */
@MyBatisDao
public interface CstCheckWorkHourDao extends CrudDao<CstCheckWorkHour> {
	
	List<CstCheckWorkHour> getBaseCheckWorkHour();
	
}