/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.parts;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.parts.CstPartsPackInfo;

/**
 * 包名归属字典---主要用于维护主数据参考用DAO接口
 * @author admin
 * @version 2019-01-18
 */
@MyBatisDao
public interface CstPartsPackInfoDao extends CrudDao<CstPartsPackInfo> {
	
}