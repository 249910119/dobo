/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.BasSybInitial;

/**
 * 事业部初始现金流DAO接口
 * @author admin
 * @version 2017-12-24
 */
@MyBatisDao
public interface BasSybInitialDao extends CrudDao<BasSybInitial> {
	public List<BasSybInitial> findListByFiscalYear(@Param("fiscalYear")String fiscalYear);
}