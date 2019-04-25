/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManProdPara;
import com.dobo.modules.cst.dao.man.CstManProdParaDao;

/**
 * 服务产品系数定义表Service
 * @author admin
 * @version 2016-11-08
 */
@Service
@Transactional(readOnly = true)
public class CstManProdParaService extends CrudService<CstManProdParaDao, CstManProdPara> {

	@Override
    public CstManProdPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManProdPara> findList(CstManProdPara cstManProdPara) {
		return super.findList(cstManProdPara);
	}
	
	@Override
    public Page<CstManProdPara> findPage(Page<CstManProdPara> page, CstManProdPara cstManProdPara) {
		return super.findPage(page, cstManProdPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManProdPara cstManProdPara) {
		super.save(cstManProdPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManProdPara cstManProdPara) {
		super.delete(cstManProdPara);
	}
	
}