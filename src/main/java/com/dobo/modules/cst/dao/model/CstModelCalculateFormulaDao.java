/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.model;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;

/**
 * 成本计算公式定义表： 1.根据创建时间、状态、更新时间和更新前状态作为时间戳判断条件; 2.定义到二级成本分类，比如：一线(工时\人工\费用\激励)； 3.指标类型要与订单成本明细一一对应；DAO接口
 * @author admin
 * @version 2016-11-13
 */
@MyBatisDao
public interface CstModelCalculateFormulaDao extends CrudDao<CstModelCalculateFormula> {
	
}