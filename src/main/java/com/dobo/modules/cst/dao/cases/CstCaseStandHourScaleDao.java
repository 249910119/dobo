/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.cases;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.cases.CstCaseStandHourScale;

/**
 * 单次、先行支持标准工时系数DAO接口
 * @author admin
 * @version 2017-06-05
 */
@MyBatisDao
public interface CstCaseStandHourScaleDao extends CrudDao<CstCaseStandHourScale> {
	
}