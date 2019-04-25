/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.detail;

import java.util.List;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.detail.CstDetailCostInfo;

/**
 * 订单清单成本明细DAO接口
 * @author admin
 * @version 2016-11-14
 */
@MyBatisDao
public interface CstDetailCostInfoDao extends CrudDao<CstDetailCostInfo> {
	
	public int addDetailBatch(List<CstDetailCostInfo> list);
	
}