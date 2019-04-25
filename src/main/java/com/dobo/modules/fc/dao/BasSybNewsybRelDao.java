/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.fc.entity.BasSybNewsybRel;

/**
 * 事业部对应新事业部名称DAO接口
 * @author admin
 * @version 2017-12-22
 */
@MyBatisDao
public interface BasSybNewsybRelDao extends CrudDao<BasSybNewsybRel> {
	public List<BasSybNewsybRel> findListByFiscalYear(@Param("fiscalYear")String fiscalYear);
}