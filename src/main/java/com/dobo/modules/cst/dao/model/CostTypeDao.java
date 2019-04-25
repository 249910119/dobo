/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.model;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.model.CostType;

/**
 * 成本类型定义表
 * @author wudla
 * @version 2016-11-14
 */
@MyBatisDao
public interface CostTypeDao extends CrudDao<CostType> {
	
}