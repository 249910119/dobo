/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.detail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.detail.CstOrderCostInfo;

/**
 * 订单成本明细DAO接口
 * @author admin
 * @version 2017-01-23
 */
@MyBatisDao
public interface CstOrderCostInfoDao extends CrudDao<CstOrderCostInfo> {

	public int addDetailBatch(@Param("list")List<CstOrderCostInfo> list);
}