/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.FcProjectInfoNocalc;

/**
 * 无需计算计划内财务费用项目DAO接口
 * @author admin
 * @version 2017-07-05
 */
@MyBatisDao
public interface FcProjectInfoNocalcDao extends CrudDao<FcProjectInfoNocalc> {
	
}