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
import com.dobo.modules.fc.dao.BasSybCbzxRelDao;
import com.dobo.modules.fc.entity.BasSybCbzxRel;
import com.google.common.collect.Maps;

/**
 * 事业部包含的成本中心代码Service
 * @author admin
 * @version 2017-12-22
 */
@Service
@Transactional(readOnly = true)
public class BasSybCbzxRelService extends CrudService<BasSybCbzxRelDao, BasSybCbzxRel> {

	@Autowired
	private BasSybCbzxRelDao basSybCbzxRelDao;
	
	public List<BasSybCbzxRel> findListByFiscalYear(String fiscalYear) {
		return basSybCbzxRelDao.findListByFiscalYear(fiscalYear);
	}

	@Override
    public BasSybCbzxRel get(String id) {
		return super.get(id);
	}
	
	public Map<String, String> findMapByFy(String fy) {
		Map<String, String> map = Maps.newHashMap();
		List<BasSybCbzxRel> list = this.findListByFiscalYear(fy);
		if(list != null){
			for(BasSybCbzxRel bo : list){
				map.put(bo.getCbzxdm(), bo.getSybmc());
			}
		}
		return map;
	}
	
	@Override
    public List<BasSybCbzxRel> findList(BasSybCbzxRel basSybCbzxRel) {
		return super.findList(basSybCbzxRel);
	}
	
	@Override
    public Page<BasSybCbzxRel> findPage(Page<BasSybCbzxRel> page, BasSybCbzxRel basSybCbzxRel) {
		return super.findPage(page, basSybCbzxRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BasSybCbzxRel basSybCbzxRel) {
		super.save(basSybCbzxRel);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BasSybCbzxRel basSybCbzxRel) {
		super.delete(basSybCbzxRel);
	}
	
}