/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.base;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;

/**
 * 资源主数据DAO
 */
@MyBatisDao
public interface CstResourceBaseInfoDao extends CrudDao<CstResourceBaseInfo> {
	
}