/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.fc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.fc.dao.FcProjectInfoNocalcDao;
import com.dobo.modules.fc.entity.FcProjectInfoNocalc;
import com.google.common.collect.Lists;

/**
 * 无需计算计划内财务费用项目Service
 * @author admin
 * @version 2017-07-05
 */
@Service
@Transactional(readOnly = true)
public class FcProjectInfoNocalcService extends CrudService<FcProjectInfoNocalcDao, FcProjectInfoNocalc> {
	
	/**
	 * 无需计算计划外财务费用项目
	 * 
	 * @return
	 */
	public List<String> getFcProjectCodeNocalcList(){
		FcProjectInfoNocalc prjNocalc = new FcProjectInfoNocalc();
		prjNocalc.setStatus("A0");
		List<FcProjectInfoNocalc> prjNocalcList = findList(prjNocalc);
		List<String> retList = Lists.newArrayList();
		for(FcProjectInfoNocalc prj : prjNocalcList){
			retList.add(prj.getProjectCode());
		}
		return retList;
	}

	@Override
    public FcProjectInfoNocalc get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<FcProjectInfoNocalc> findList(FcProjectInfoNocalc fcProjectInfoNocalc) {
		return super.findList(fcProjectInfoNocalc);
	}
	
	@Override
    public Page<FcProjectInfoNocalc> findPage(Page<FcProjectInfoNocalc> page, FcProjectInfoNocalc fcProjectInfoNocalc) {
		return super.findPage(page, fcProjectInfoNocalc);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(FcProjectInfoNocalc fcProjectInfoNocalc) {
		super.save(fcProjectInfoNocalc);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(FcProjectInfoNocalc fcProjectInfoNocalc) {
		super.delete(fcProjectInfoNocalc);
	}
	
}