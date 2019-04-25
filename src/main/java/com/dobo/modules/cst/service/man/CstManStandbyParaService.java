/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManStandbyPara;
import com.dobo.modules.cst.dao.man.CstManStandbyParaDao;

/**
 * 非工作时间比重定义表Service
 * @author admin
 * @version 2016-11-08
 */
@Service
@Transactional(readOnly = true)
public class CstManStandbyParaService extends CrudService<CstManStandbyParaDao, CstManStandbyPara> {

	@Override
    public CstManStandbyPara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManStandbyPara> findList(CstManStandbyPara cstManStandbyPara) {
		return super.findList(cstManStandbyPara);
	}
	
	@Override
    public Page<CstManStandbyPara> findPage(Page<CstManStandbyPara> page, CstManStandbyPara cstManStandbyPara) {
		return super.findPage(page, cstManStandbyPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManStandbyPara cstManStandbyPara) {
		super.save(cstManStandbyPara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManStandbyPara cstManStandbyPara) {
		super.delete(cstManStandbyPara);
	}
	
}