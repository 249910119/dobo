/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.detail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.detail.CstNewOrderCostInfo;

/**
 * 资源模型成本DAO接口
 * @author admin
 * @version 2019-03-26
 */
@MyBatisDao
public interface CstNewOrderCostInfoDao extends CrudDao<CstNewOrderCostInfo> {

	public int addDetailBatch(@Param("list")List<CstNewOrderCostInfo> list);
}