/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.model;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.model.CstModelModuleInfo;

/**
 * 成本模型信息表，定义成本一级分类（人工类、备件类）DAO接口
 * @author admin
 * @version 2016-11-09
 */
@MyBatisDao
public interface CstModelModuleInfoDao extends CrudDao<CstModelModuleInfo> {
	
}