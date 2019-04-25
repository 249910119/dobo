/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.dao.detail;

import java.util.List;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cop.entity.detail.CopCasePriceConfirm;

/**
 * CASE单次报价确认表(TOP)DAO接口
 * @author admin
 * @version 2017-06-09
 */
@MyBatisDao
public interface CopCasePriceConfirmDao extends CrudDao<CopCasePriceConfirm> {
	
	public List<String> getExecareaBatch(String abc);
}