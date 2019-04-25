/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsPrjStorePara;
import com.dobo.modules.cst.dao.parts.CstPartsPrjStoreParaDao;

/**
 * 备件项目储备成本系数定义Service
 * @author admin
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class CstPartsPrjStoreParaService extends CrudService<CstPartsPrjStoreParaDao, CstPartsPrjStorePara> {

	@Override
    public CstPartsPrjStorePara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsPrjStorePara> findList(CstPartsPrjStorePara cstPartsPrjStorePara) {
		return super.findList(cstPartsPrjStorePara);
	}
	
	@Override
    public Page<CstPartsPrjStorePara> findPage(Page<CstPartsPrjStorePara> page, CstPartsPrjStorePara cstPartsPrjStorePara) {
		return super.findPage(page, cstPartsPrjStorePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsPrjStorePara cstPartsPrjStorePara) {
		super.save(cstPartsPrjStorePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsPrjStorePara cstPartsPrjStorePara) {
		super.delete(cstPartsPrjStorePara);
	}
	
}