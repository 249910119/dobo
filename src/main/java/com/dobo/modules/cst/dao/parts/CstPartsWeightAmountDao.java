/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.parts;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.parts.CstPartsWeightAmount;

/**
 * 备件加权平均在保量定义DAO接口
 * @author admin
 * @version 2016-11-15
 */
@MyBatisDao
public interface CstPartsWeightAmountDao extends CrudDao<CstPartsWeightAmount> {
	
}