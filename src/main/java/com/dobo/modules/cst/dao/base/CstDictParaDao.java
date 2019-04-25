/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.base;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.base.CstDictPara;

/**
 * 成本模型字典DAO接口
 * @author admin
 * @version 2016-12-14
 */
@MyBatisDao
public interface CstDictParaDao extends CrudDao<CstDictPara> {
	
}