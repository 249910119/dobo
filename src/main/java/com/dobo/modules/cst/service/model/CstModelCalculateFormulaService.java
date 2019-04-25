/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.model;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.dao.model.CstModelCalculateFormulaDao;

/**
 * 成本计算公式定义表： 1.根据创建时间、状态、更新时间和更新前状态作为时间戳判断条件; 2.定义到二级成本分类，比如：一线(工时\人工\费用\激励)； 3.指标类型要与订单成本明细一一对应；Service
 * @author admin
 * @version 2016-11-13
 */
@Service
@Transactional(readOnly = true)
public class CstModelCalculateFormulaService extends CrudService<CstModelCalculateFormulaDao, CstModelCalculateFormula> {

	@Override
    public CstModelCalculateFormula get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstModelCalculateFormula> findList(CstModelCalculateFormula cstModelCalculateFormula) {
		return super.findList(cstModelCalculateFormula);
	}
	
	@Override
    public Page<CstModelCalculateFormula> findPage(Page<CstModelCalculateFormula> page, CstModelCalculateFormula cstModelCalculateFormula) {
		return super.findPage(page, cstModelCalculateFormula);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstModelCalculateFormula cstModelCalculateFormula) {
		super.save(cstModelCalculateFormula);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstModelCalculateFormula cstModelCalculateFormula) {
		super.delete(cstModelCalculateFormula);
	}
	
}