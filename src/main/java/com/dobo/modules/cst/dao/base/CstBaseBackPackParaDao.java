/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.base;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.base.CstBaseBackPackPara;

/**
 * 备件合作包取值参数获取DAO接口
 * @author admin
 * @version 2019-03-27
 */
@MyBatisDao
public interface CstBaseBackPackParaDao extends CrudDao<CstBaseBackPackPara> {
	
}