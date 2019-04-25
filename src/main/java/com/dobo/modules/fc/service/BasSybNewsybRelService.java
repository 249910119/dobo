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
import com.dobo.modules.fc.entity.BasSybCbzxRel;
import com.dobo.modules.fc.entity.BasSybInitial;
import com.dobo.modules.fc.entity.BasSybNewsybRel;
import com.google.common.collect.Maps;
import com.dobo.modules.fc.dao.BasSybInitialDao;
import com.dobo.modules.fc.dao.BasSybNewsybRelDao;

/**
 * 事业部对应新事业部名称Service
 * @author admin
 * @version 2017-12-22
 */
@Service
@Transactional(readOnly = true)
public class BasSybNewsybRelService extends CrudService<BasSybNewsybRelDao, BasSybNewsybRel> {

	@Autowired
	private BasSybNewsybRelDao basSybNewsybRelDao;
	
	public List<BasSybNewsybRel> findListByFiscalYear(String fiscalYear) {
		return basSybNewsybRelDao.findListByFiscalYear(fiscalYear);
	}

	@Override
    public BasSybNewsybRel get(String id) {
		return super.get(id);
	}
	
	public Map<String, String> findMapByFy(String fy) {
		Map<String, String> map = Maps.newHashMap();
		List<BasSybNewsybRel> list = this.findListByFiscalYear(fy);
		if(list != null){
			for(BasSybNewsybRel bo : list){
				map.put(bo.getSybmc(), bo.getNewSybmc());
			}
		}
		return map;
	}
	
	@Override
    public List<BasSybNewsybRel> findList(BasSybNewsybRel basSybNewsybRel) {
		return super.findList(basSybNewsybRel);
	}
	
	@Override
    public Page<BasSybNewsybRel> findPage(Page<BasSybNewsybRel> page, BasSybNewsybRel basSybNewsybRel) {
		return super.findPage(page, basSybNewsybRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BasSybNewsybRel basSybNewsybRel) {
		super.save(basSybNewsybRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BasSybNewsybRel basSybNewsybRel) {
		super.delete(basSybNewsybRel);
	}
	
}