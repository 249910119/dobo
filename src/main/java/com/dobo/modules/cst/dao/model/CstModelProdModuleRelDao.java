/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.model;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;

/**
 * 产品成本模型使用定义表，定义某产品使用的成本模型DAO接口
 * @author admin
 * @version 2016-11-12
 */
@MyBatisDao
public interface CstModelProdModuleRelDao extends CrudDao<CstModelProdModuleRel> {
	
}