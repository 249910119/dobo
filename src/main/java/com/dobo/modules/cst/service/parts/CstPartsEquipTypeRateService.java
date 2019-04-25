/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsEquipTypeRate;
import com.dobo.modules.cst.dao.parts.CstPartsEquipTypeRateDao;

/**
 * cst_parts_equip_type_rateService
 * @author admin
 * @version 2017-01-19
 */
@Service
@Transactional(readOnly = true)
public class CstPartsEquipTypeRateService extends CrudService<CstPartsEquipTypeRateDao, CstPartsEquipTypeRate> {

	@Override
    public CstPartsEquipTypeRate get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstPartsEquipTypeRate> findList(CstPartsEquipTypeRate cstPartsEquipTypeRate) {
		return super.findList(cstPartsEquipTypeRate);
	}
	
	@Override
    public Page<CstPartsEquipTypeRate> findPage(Page<CstPartsEquipTypeRate> page, CstPartsEquipTypeRate cstPartsEquipTypeRate) {
		return super.findPage(page, cstPartsEquipTypeRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstPartsEquipTypeRate cstPartsEquipTypeRate) {
		super.save(cstPartsEquipTypeRate);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstPartsEquipTypeRate cstPartsEquipTypeRate) {
		super.delete(cstPartsEquipTypeRate);
	}
	
}