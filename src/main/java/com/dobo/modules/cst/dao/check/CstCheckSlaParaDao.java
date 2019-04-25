/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.check;

import java.util.List;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.check.CstCheckSlaPara;

/**
 * 巡检级别配比定义DAO接口
 * @author admin
 * @version 2016-11-22
 */
@MyBatisDao
public interface CstCheckSlaParaDao extends CrudDao<CstCheckSlaPara> {

	List<CstCheckSlaPara> getBaseCheckSlaPara();
}