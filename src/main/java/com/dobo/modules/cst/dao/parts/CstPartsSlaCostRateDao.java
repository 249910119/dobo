/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.parts;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.parts.CstPartsSlaCostRate;

/**
 * 故障成本SLA采购成本系数DAO接口
 * @author admin
 * @version 2017-01-11
 */
@MyBatisDao
public interface CstPartsSlaCostRateDao extends CrudDao<CstPartsSlaCostRate> {
	
}