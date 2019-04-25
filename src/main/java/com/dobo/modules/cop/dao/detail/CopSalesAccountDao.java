/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.dao.detail;

import java.util.List;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cop.entity.detail.CopSalesAccount;

/**
 * 销售员账户表DAO接口
 * @author admin
 * @version 2017-06-09
 */
@MyBatisDao
public interface CopSalesAccountDao extends CrudDao<CopSalesAccount> {
	
	public List<CopSalesAccount> findStaffPrjList(CopSalesAccount copSalesAccount);
}