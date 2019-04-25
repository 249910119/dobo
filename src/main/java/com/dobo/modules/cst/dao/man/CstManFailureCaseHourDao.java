/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.man;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.man.CstManFailureCaseHour;

/**
 * 故障CASE处理工时定义表DAO接口
 * @author admin
 * @version 2016-11-08
 */
@MyBatisDao
public interface CstManFailureCaseHourDao extends CrudDao<CstManFailureCaseHour> {
	
}