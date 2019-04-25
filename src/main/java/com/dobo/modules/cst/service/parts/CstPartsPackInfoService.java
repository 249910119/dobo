/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.parts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.parts.CstPartsPackInfo;
import com.dobo.modules.cst.dao.parts.CstPartsPackInfoDao;

/**
 * 包名归属字典---主要用于维护主数据参考用Service
 * @author admin
 * @version 2019-01-18
 */
@Service
@Transactional(readOnly = true)
public class CstPartsPackInfoService extends CrudService<CstPartsPackInfoDao, CstPartsPackInfo> {

	public CstPartsPackInfo get(String id) {
		return super.get(id);
	}
	
	public List<CstPartsPackInfo> findList(CstPartsPackInfo cstPartsPackInfo) {
		return super.findList(cstPartsPackInfo);
	}
	
	public Page<CstPartsPackInfo> findPage(Page<CstPartsPackInfo> page, CstPartsPackInfo cstPartsPackInfo) {
		return super.findPage(page, cstPartsPackInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CstPartsPackInfo cstPartsPackInfo) {
		super.save(cstPartsPackInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstPartsPackInfo cstPartsPackInfo) {
		super.delete(cstPartsPackInfo);
	}
	
}