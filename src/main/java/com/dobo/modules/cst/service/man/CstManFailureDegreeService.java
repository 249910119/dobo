/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.man;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.man.CstManFailureDegree;
import com.dobo.modules.cst.dao.man.CstManFailureDegreeDao;

/**
 * 故障饱和度定义表Service
 * @author admin
 * @version 2016-11-08
 */
@Service
@Transactional(readOnly = true)
public class CstManFailureDegreeService extends CrudService<CstManFailureDegreeDao, CstManFailureDegree> {

	@Override
    public CstManFailureDegree get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManFailureDegree> findList(CstManFailureDegree cstManFailureDegree) {
		return super.findList(cstManFailureDegree);
	}
	
	@Override
    public Page<CstManFailureDegree> findPage(Page<CstManFailureDegree> page, CstManFailureDegree cstManFailureDegree) {
		return super.findPage(page, cstManFailureDegree);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManFailureDegree cstManFailureDegree) {
		super.save(cstManFailureDegree);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManFailureDegree cstManFailureDegree) {
		super.delete(cstManFailureDegree);
	}
	
}