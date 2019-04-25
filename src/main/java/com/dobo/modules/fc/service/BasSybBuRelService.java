/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.fc.dao.BasSybBuRelDao;
import com.dobo.modules.fc.dao.BasSybXmbhRelDao;
import com.dobo.modules.fc.entity.BasSybBuRel;
import com.dobo.modules.fc.entity.BasSybXmbhRel;
import com.google.common.collect.Maps;

/**
 * 事业部对应BU名称Service
 * @author admin
 * @version 2017-12-22
 */
@Service
@Transactional(readOnly = true)
public class BasSybBuRelService extends CrudService<BasSybBuRelDao, BasSybBuRel> {

	@Autowired
	private BasSybBuRelDao basSybBuRelDao;
	
	public List<BasSybBuRel> findListByFiscalYear(String fiscalYear) {
		return basSybBuRelDao.findListByFiscalYear(fiscalYear);
	}

	@Override
    public BasSybBuRel get(String id) {
		return super.get(id);
	}
	
	public Map<String, String> findMapByFy(String fy) {
		Map<String, String> map = Maps.newHashMap();
		List<BasSybBuRel> list = this.findListByFiscalYear(fy);
		if(list != null){
			for(BasSybBuRel bo : list){
				map.put(bo.getSybmc(), bo.getBumc());
			}
		}
		return map;
	}
	
	@Override
    public List<BasSybBuRel> findList(BasSybBuRel basSybBuRel) {
		return super.findList(basSybBuRel);
	}
	
	@Override
    public Page<BasSybBuRel> findPage(Page<BasSybBuRel> page, BasSybBuRel basSybBuRel) {
		return super.findPage(page, basSybBuRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BasSybBuRel basSybBuRel) {
		super.save(basSybBuRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BasSybBuRel basSybBuRel) {
		super.delete(basSybBuRel);
	}
	
}