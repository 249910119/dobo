/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.base;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.dao.base.CstBaseCaseLineoneDao;
import com.dobo.modules.cst.entity.base.CstBaseCaseLineone;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.google.common.collect.Lists;

/**
 * 故障配比明细Service
 * @author admin
 * @version 2019-02-28
 */
@Service
@Transactional(readOnly = true)
public class CstBaseCaseLineoneService extends CrudService<CstBaseCaseLineoneDao, CstBaseCaseLineone> {

	public CstBaseCaseLineone get(String id) {
		return super.get(id);
	}
	
	public List<CstBaseCaseLineone> findList(CstBaseCaseLineone cstBaseCaseLineone) {
		return super.findList(cstBaseCaseLineone);
	}
	
	public Page<CstBaseCaseLineone> findPage(Page<CstBaseCaseLineone> page, CstBaseCaseLineone cstBaseCaseLineone) {
		return super.findPage(page, cstBaseCaseLineone);
	}
	
	@Transactional(readOnly = false)
	public void save(CstBaseCaseLineone cstBaseCaseLineone) {
		super.save(cstBaseCaseLineone);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstBaseCaseLineone cstBaseCaseLineone) {
		super.delete(cstBaseCaseLineone);
	}

	public List<CstResourceBaseInfo> calcCaseLineOnePara(CstResourceBaseInfo crbi) {
		List<CstResourceBaseInfo> crbiList =Lists.newArrayList();
		// 故障样本数基数
		Double baseAmount = 20.0;
		
		Map<String, CstBaseCaseLineone> cbclMap = CacheDataUtils.getCstBaseCaseLineoneMap();
		/**
		 * 故障配比取值原则(标准服务B)：	
		 * 1、2、3、4级故障配比率=1、2、3、4、5级故障配比/样本总数	
		 *	1、厂商+型号：按故障配比明细里的样本总数此类累加>=20时，取对应故障配比率，若不足20，追溯（2）	
		 *	2、技术方向+型号组+厂商：按故障配比明细里的样本总数此类累加>=20时，取对应故障配比率，若不足20，追溯（3）	
		 *	3、技术方向+厂商：按故障配比明细里的样本总数此类累加>=20时，取对应故障配比率，若不足20，追溯（4）	
		 *	4、技术方向：按故障配比明细里的样本总数此类累加>=20时，取对应故障配比率，若不足20，追溯（5）	
		 *	5、资源计划分类：按故障配比明细里的样本总数此类累加>=20时，取对应故障配比率，若不足20，追溯（6）	
		 *	6、故障配比率（总）	
		 *	以上所有在算故障配比率时，case数及在保数均在追溯范围内整体累加，计算故障配比率	
		 *
		 *  高级服务A：	1级，2级为0，3级配比除填写1,2,3级的总合,4级，5级不变
		 */
		
		CstBaseCaseLineone cbcl1 = cbclMap.get(crbi.getResourceId());
		CstBaseCaseLineone cbcl2 = cbclMap.get("G_"+crbi.getModelGroupId());
		CstBaseCaseLineone cbcl3 = cbclMap.get(crbi.getEquipTypeId()+"_"+crbi.getMfrId());
		CstBaseCaseLineone cbcl4 = cbclMap.get("E_"+crbi.getEquipTypeId());
		CstBaseCaseLineone cbcl5 = cbclMap.get("R_"+crbi.getResstattypeId());
		
		CstBaseCaseLineone cbcl = null;
		if(cbcl1 != null && cbcl1.getSampleAmount() >= baseAmount) {
			cbcl = cbcl1;
		} else if(cbcl2 != null && cbcl2.getSampleAmount() >= baseAmount) {
			cbcl = cbcl2;
		} else if(cbcl3 != null && cbcl3.getSampleAmount() >= baseAmount) {
			cbcl = cbcl3;
		} else if(cbcl4 != null && cbcl4.getSampleAmount() >= baseAmount) {
			cbcl = cbcl4;
		} else if(cbcl5 != null && cbcl5.getSampleAmount() >= baseAmount) {
			cbcl = cbcl5;
		}
		
		// 资源标识","厂商","资源名称","技术方向","型号组
		CstResourceBaseInfo crbiB = new CstResourceBaseInfo();
		crbiB.setResourceId("WBM"+crbi.getResourceId());
		crbiB.setMfrName(crbi.getMfrName());
		crbiB.setResourceName(crbi.getResourceName());
		crbiB.setEquipTypeName(crbi.getEquipTypeName());
		crbiB.setModelGroupName(crbi.getModelGroupName());
		crbiB.setSlaName("标准服务B");
		
		crbiB.setFailureLineOne1Rate(BigDecimal.valueOf(cbcl.getLine1OneAmount()).divide(BigDecimal.valueOf(cbcl.getSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		crbiB.setFailureLineOne2Rate(BigDecimal.valueOf(cbcl.getLine1TwoAmount()).divide(BigDecimal.valueOf(cbcl.getSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		crbiB.setFailureLineOne3Rate(BigDecimal.valueOf(cbcl.getLine1ThdAmount()).divide(BigDecimal.valueOf(cbcl.getSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		crbiB.setFailureLineOne4Rate(BigDecimal.valueOf(cbcl.getLine1FourAmount()).divide(BigDecimal.valueOf(cbcl.getSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		crbiB.setFailureLineOne5Rate(BigDecimal.valueOf(cbcl.getLine1FiveAmount()).divide(BigDecimal.valueOf(cbcl.getSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		
		crbiList.add(crbiB);

		CstResourceBaseInfo crbiA = new CstResourceBaseInfo();
		crbiA.setResourceId("WBM"+crbi.getResourceId());
		crbiA.setMfrName(crbi.getMfrName());
		crbiA.setResourceName(crbi.getResourceName());
		crbiA.setEquipTypeName(crbi.getEquipTypeName());
		crbiA.setModelGroupName(crbi.getModelGroupName());
		crbiA.setSlaName("高级服务A");
		// 高级服务A：	1级，2级为0，3级配比除填写1,2,3级的总合,4级，5级不变
		crbiA.setFailureLineOne1Rate("0");
		crbiA.setFailureLineOne2Rate("0");
		crbiA.setFailureLineOne3Rate((BigDecimal.valueOf(cbcl.getLine1OneAmount()).add(BigDecimal.valueOf(cbcl.getLine1TwoAmount())).add(BigDecimal.valueOf(cbcl.getLine1ThdAmount()))).divide(BigDecimal.valueOf(cbcl.getSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		crbiA.setFailureLineOne4Rate(crbiB.getFailureLineOne4Rate());
		crbiA.setFailureLineOne5Rate(crbiB.getFailureLineOne5Rate());
		
		crbiList.add(crbiA);
		
		return crbiList;
	}
	
}