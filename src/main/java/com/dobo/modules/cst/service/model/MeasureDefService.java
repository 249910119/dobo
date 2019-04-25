/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.model;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.dao.model.MeasureDefDao;
import com.dobo.modules.cst.entity.model.MeasureDef;

/**
 * 指标定义表
 * @author wudla
 * @version 2016-11-13
 */
@Service
@Transactional(readOnly = true)
public class MeasureDefService extends CrudService<MeasureDefDao, MeasureDef> {

	@Override
    public MeasureDef get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<MeasureDef> findList(MeasureDef measureDef) {
		return super.findList(measureDef);
	}
	
	@Override
    public Page<MeasureDef> findPage(Page<MeasureDef> page, MeasureDef measureDef) {
		return super.findPage(page, measureDef);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(MeasureDef measureDef) {
		super.save(measureDef);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(MeasureDef measureDef) {
		super.delete(measureDef);
	}
	
}