/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.man;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.man.CstManFailureSlaPara;

/**
 * 故障级别配比定义表DAO接口
 * @author admin
 * @version 2016-11-08
 */
@MyBatisDao
public interface CstManFailureSlaParaDao extends CrudDao<CstManFailureSlaPara> {
	
}