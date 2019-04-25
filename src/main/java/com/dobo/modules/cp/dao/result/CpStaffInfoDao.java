/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cp.dao.result;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cp.entity.result.CpStaffInfo;

/**
 * 系统人员DAO接口
 * @author admin
 * @version 2018-06-25
 */
@MyBatisDao
public interface CpStaffInfoDao extends CrudDao<CpStaffInfo> {
	
}