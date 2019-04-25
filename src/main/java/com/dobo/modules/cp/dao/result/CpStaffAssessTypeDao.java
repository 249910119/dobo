/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.dao.result;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cp.entity.result.CpStaffAssessType;

/**
 * 系统人员测评行为、能力和模块划分DAO接口
 * @author admin
 * @version 2018-06-08
 */
@MyBatisDao
public interface CpStaffAssessTypeDao extends CrudDao<CpStaffAssessType> {
	
}