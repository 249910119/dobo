/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.BasSybXmbhRel;

/**
 * 财务项目编号对应事业部名称DAO接口
 * @author admin
 * @version 2017-12-22
 */
@MyBatisDao
public interface BasSybXmbhRelDao extends CrudDao<BasSybXmbhRel> {
	public List<BasSybXmbhRel> findListByFiscalYear(@Param("fiscalYear")String fiscalYear);
}