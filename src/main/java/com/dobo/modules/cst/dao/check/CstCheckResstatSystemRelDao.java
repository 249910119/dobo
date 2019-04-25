/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.check;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.check.CstCheckResstatSystemRel;

/**
 * 巡检-资源计划分类对应设备大类关系表DAO接口
 * @author admin
 * @version 2016-11-22
 */
@MyBatisDao
public interface CstCheckResstatSystemRelDao extends CrudDao<CstCheckResstatSystemRel> {
	
}