/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.man;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.man.CstManFailureDegree;

/**
 * 故障饱和度定义表DAO接口
 * @author admin
 * @version 2016-11-08
 */
@MyBatisDao
public interface CstManFailureDegreeDao extends CrudDao<CstManFailureDegree> {
	
}