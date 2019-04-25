/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.base;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.base.CstBaseResourceCase;

/**
 * 故障case样本DAO接口
 * @author admin
 * @version 2019-02-26
 */
@MyBatisDao
public interface CstBaseResourceCaseDao extends CrudDao<CstBaseResourceCase> {
	
}