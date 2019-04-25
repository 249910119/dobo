/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.model;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.model.CstModelParaDef;

/**
 * 成本参数定义表：定义成本模型公式中用到的简单和复杂参数DAO接口
 * @author admin
 * @version 2016-11-11
 */
@MyBatisDao
public interface CstModelParaDefDao extends CrudDao<CstModelParaDef> {
	
}