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
import com.dobo.modules.fc.entity.BasSybNewsybRel;
import com.dobo.modules.fc.entity.BasSybXmbhRel;
import com.google.common.collect.Maps;
import com.dobo.modules.fc.dao.BasSybNewsybRelDao;
import com.dobo.modules.fc.dao.BasSybXmbhRelDao;

/**
 * 财务项目编号对应事业部名称Service
 * @author admin
 * @version 2017-12-22
 */
@Service
@Transactional(readOnly = true)
public class BasSybXmbhRelService extends CrudService<BasSybXmbhRelDao, BasSybXmbhRel> {

	@Autowired
	private BasSybXmbhRelDao basSybXmbhRelDao;
	
	public List<BasSybXmbhRel> findListByFiscalYear(String fiscalYear) {
		return basSybXmbhRelDao.findListByFiscalYear(fiscalYear);
	}

	@Override
    public BasSybXmbhRel get(String id) {
		return super.get(id);
	}
	
	public Map<String, String> findMapByFy(String fy) {
		Map<String, String> map = Maps.newHashMap();
		List<BasSybXmbhRel> list = this.findListByFiscalYear(fy);
		if(list != null){
			for(BasSybXmbhRel bo : list){
				map.put(bo.getErpxmbh(), bo.getSybmc());
			}
		}
		return map;
	}
	
	@Override
    public List<BasSybXmbhRel> findList(BasSybXmbhRel basSybXmbhRel) {
		return super.findList(basSybXmbhRel);
	}
	
	@Override
    public Page<BasSybXmbhRel> findPage(Page<BasSybXmbhRel> page, BasSybXmbhRel basSybXmbhRel) {
		return super.findPage(page, basSybXmbhRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BasSybXmbhRel basSybXmbhRel) {
		super.save(basSybXmbhRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BasSybXmbhRel basSybXmbhRel) {
		super.delete(basSybXmbhRel);
	}
	
}