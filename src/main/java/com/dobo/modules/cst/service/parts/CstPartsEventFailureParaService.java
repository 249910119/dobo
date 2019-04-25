/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsEventFailurePara;
import com.dobo.modules.cst.dao.parts.CstPartsEventFailureParaDao;

/**
 * 备件事件故障参数定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsEventFailureParaService extends CrudService<CstPartsEventFailureParaDao, CstPartsEventFailurePara> {

	@Override
    public CstPartsEventFailurePara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsEventFailurePara> findList(CstPartsEventFailurePara cstPartsEventFailurePara) {
		return super.findList(cstPartsEventFailurePara);
	}
	
	@Override
    public Page<CstPartsEventFailurePara> findPage(Page<CstPartsEventFailurePara> page, CstPartsEventFailurePara cstPartsEventFailurePara) {
		return super.findPage(page, cstPartsEventFailurePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsEventFailurePara cstPartsEventFailurePara) {
		super.save(cstPartsEventFailurePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsEventFailurePara cstPartsEventFailurePara) {
		super.delete(cstPartsEventFailurePara);
	}
	
}