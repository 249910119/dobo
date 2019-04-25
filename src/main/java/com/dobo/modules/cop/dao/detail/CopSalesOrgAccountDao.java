/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.dao.detail;

import java.util.Map;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cop.entity.detail.CopSalesOrgAccount;

/**
 * 单次服务成本事业部阀值DAO接口
 * @author admin
 * @version 2018-04-20
 */
@MyBatisDao
public interface CopSalesOrgAccountDao extends CrudDao<CopSalesOrgAccount> {

	public Map<String, String> getSalesByCase(String staffId);
	
	public Double getSalesOrgCaseUsedAmount(String staffId);
}