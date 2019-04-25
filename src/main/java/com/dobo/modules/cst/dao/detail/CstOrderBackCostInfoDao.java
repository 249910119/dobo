/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.detail;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.detail.CstOrderBackCostInfo;

/**
 * 订单备件故障成本（自有、分包）DAO接口
 * @author admin
 * @version 2019-01-18
 */
@MyBatisDao
public interface CstOrderBackCostInfoDao extends CrudDao<CstOrderBackCostInfo> {
	
}