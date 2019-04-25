/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.temp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.entity.temp.CstManNotDevicePara;
import com.dobo.modules.cst.dao.temp.CstManNotDeviceParaDao;

/**
 * 非设备类系数表Service
 * @author admin
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class CstManNotDeviceParaService extends CrudService<CstManNotDeviceParaDao, CstManNotDevicePara> {

	@Override
    public CstManNotDevicePara get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstManNotDevicePara> findList(CstManNotDevicePara cstManNotDevicePara) {
		return super.findList(cstManNotDevicePara);
	}
	
	@Override
    public Page<CstManNotDevicePara> findPage(Page<CstManNotDevicePara> page, CstManNotDevicePara cstManNotDevicePara) {
		return super.findPage(page, cstManNotDevicePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstManNotDevicePara cstManNotDevicePara) {
		super.save(cstManNotDevicePara);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstManNotDevicePara cstManNotDevicePara) {
		super.delete(cstManNotDevicePara);
	}
	
}