/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsRiskCostPara;
import com.dobo.modules.cst.dao.parts.CstPartsRiskCostParaDao;

/**
 * 备件风险储备金Service
 * @author admin
 * @version 2019-01-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsRiskCostParaService extends CrudService<CstPartsRiskCostParaDao, CstPartsRiskCostPara> {

	public CstPartsRiskCostPara get(String id) {
		return super.get(id);
	}
	
	public List<CstPartsRiskCostPara> findList(CstPartsRiskCostPara cstPartsRiskCostPara) {
		return super.findList(cstPartsRiskCostPara);
	}
	
	public Page<CstPartsRiskCostPara> findPage(Page<CstPartsRiskCostPara> page, CstPartsRiskCostPara cstPartsRiskCostPara) {
		return super.findPage(page, cstPartsRiskCostPara);
	}
	
	@Transactional(readOnly = false)
	public void save(CstPartsRiskCostPara cstPartsRiskCostPara) {
		super.save(cstPartsRiskCostPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstPartsRiskCostPara cstPartsRiskCostPara) {
		super.delete(cstPartsRiskCostPara);
	}
	
}