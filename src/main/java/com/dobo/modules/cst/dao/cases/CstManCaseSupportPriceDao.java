/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.cases;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.cases.CstManCaseSupportPrice;

/**
 * 单次、先行支持服务单价表DAO接口
 * @author admin
 * @version 2017-06-05
 */
@MyBatisDao
public interface CstManCaseSupportPriceDao extends CrudDao<CstManCaseSupportPrice> {
	
}