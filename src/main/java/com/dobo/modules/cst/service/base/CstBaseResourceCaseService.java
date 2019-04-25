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
import com.dobo.common.service.ServiceException;
import com.dobo.modules.cst.entity.base.CstBaseCaseHour;
import com.dobo.modules.cst.entity.base.CstBaseResourceCase;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.dao.base.CstBaseResourceCaseDao;

/**
 * 故障case样本Service
 * @author admin
 * @version 2019-02-26
 */
@Service
@Transactional(readOnly = true)
public class CstBaseResourceCaseService extends CrudService<CstBaseResourceCaseDao, CstBaseResourceCase> {

	public CstBaseResourceCase get(String id) {
		return super.get(id);
	}
	
	public List<CstBaseResourceCase> findList(CstBaseResourceCase cstBaseResourceCase) {
		return super.findList(cstBaseResourceCase);
	}
	
	public Page<CstBaseResourceCase> findPage(Page<CstBaseResourceCase> page, CstBaseResourceCase cstBaseResourceCase) {
		return super.findPage(page, cstBaseResourceCase);
	}
	
	@Transactional(readOnly = false)
	public void save(CstBaseResourceCase cstBaseResourceCase) {
		super.save(cstBaseResourceCase);
	}
	
	@Transactional(readOnly = false)
	public void delete(CstBaseResourceCase cstBaseResourceCase) {
		super.delete(cstBaseResourceCase);
	}
	
	public CstResourceBaseInfo calcBaseCasePara(CstResourceBaseInfo crbi) {
		// 故障样本数基数
		Double baseAmount = 20.0;
		
		Map<String, CstBaseResourceCase> cbrcMap = CacheDataUtils.getCstBaseResourceCaseMap();
		/**
		 * 故障率取值原则：	
		 *	故障率=case数/在保数	
		 *	1、厂商+型号：按故障明细里的样本数此类累加>=20时，取对应故障率，若不足20或case数为0，则追溯（2）	
		 *	2、技术方向+型号组+厂商：按故障明细里的样本数此类累加>=20时，取对应故障率，若不足20或case数为0，追溯（3）	
		 *	3、技术方向+厂商：按故障明细里的样本数此类累加>=20时，取对应故障率，若不足20或case数为0，追溯（4）	
		 *	4、技术方向：按故障明细里的样本数此类累加>=20时，取对应故障率，若不足20或case数为0，追溯（5）	
		 *	5、资源计划分类：按故障明细里的样本数此类累加>=20时，取对应故障率，若不足20或case数为0，追溯（6）	
		 *	6、故障率（总）	
		 *	以上所有在算故障率时，case数及在保数均在追溯范围内整体累加，计算故障率	
		 */
		
		CstBaseResourceCase cbrc1 = cbrcMap.get(crbi.getResourceId());
		CstBaseResourceCase cbrc2 = cbrcMap.get("G_"+crbi.getModelGroupId());
		CstBaseResourceCase cbrc3 = cbrcMap.get(crbi.getEquipTypeId()+"_"+crbi.getMfrId());
		CstBaseResourceCase cbrc4 = cbrcMap.get("E_"+crbi.getEquipTypeId());
		CstBaseResourceCase cbrc5 = cbrcMap.get("R_"+crbi.getResstattypeId());
		
		CstBaseResourceCase cbrc = null;
		if(cbrc1 != null && cbrc1.getCaseAmount() > 0.0 && cbrc1.getYearSampleAmount() >= baseAmount) {
			cbrc = cbrc1;
		} else if(cbrc2 != null && cbrc2.getCaseAmount() > 0.0 && cbrc2.getYearSampleAmount() >= baseAmount) {
			cbrc = cbrc2;
		} else if(cbrc3 != null && cbrc3.getCaseAmount() > 0.0 && cbrc3.getYearSampleAmount() >= baseAmount) {
			cbrc = cbrc3;
		} else if(cbrc4 != null && cbrc4.getCaseAmount() > 0.0 && cbrc4.getYearSampleAmount() >= baseAmount) {
			cbrc = cbrc4;
		} else if(cbrc5 != null && cbrc5.getCaseAmount() > 0.0 && cbrc5.getYearSampleAmount() >= baseAmount) {
			cbrc = cbrc5;
		} else {
			throw new ServiceException("资源主数据["+crbi.getResourceId()+"]对应的故障率参数初始匹配未找到，请联系系统管理员！");
		}
		
		crbi.setFailureRate(BigDecimal.valueOf(cbrc.getCaseAmount()).divide(BigDecimal.valueOf(cbrc.getYearSampleAmount()), 8, BigDecimal.ROUND_HALF_UP).toString());
		
		/**标准工时取值规则：		
         *（1）厂商+型号组+技术方向，若有则取，若无，则取值（2）		
		 *（2）厂商+技术方向，若有，则取多项算数平均值，若无，则取值（3）		
		 *（3）技术方向，若有，则取多项算数平均值，若无，则取值（4）			
		 *（4）整体平均数，若有，则取多项算数平均值		
		 */
		Map<String, CstBaseCaseHour> cbchMap = CacheDataUtils.getCstBaseCaseHourMap();
		CstBaseCaseHour cbch1 = cbchMap.get(crbi.getMfrName().toUpperCase()+"_"+crbi.getEquipTypeName().toUpperCase()+"_"+crbi.getModelGroupName().toUpperCase());
		CstBaseCaseHour cbch2 = cbchMap.get(crbi.getMfrName().toUpperCase()+"_"+crbi.getEquipTypeName().toUpperCase());
		CstBaseCaseHour cbch3 = cbchMap.get(crbi.getEquipTypeName().toUpperCase());
		CstBaseCaseHour cbch4 = cbchMap.get("ALL_CASE_HOUR_PARA");
		
		CstBaseCaseHour cbch = null;
		if(cbch1 != null) {
			cbch = cbch1;
		} else if(cbch2 != null) {
			cbch = cbch2;
		} else if(cbch3 != null) {
			cbch = cbch3;
		}  else if(cbch4 != null) {
			cbch = cbch4;
		} else {
			throw new ServiceException("资源主数据["+crbi.getResourceId()+"]对应的故障工时参数初始匹配未找到，请联系系统管理员！");
		}
		
		crbi.setLineOneCaseHour(cbch.getLineoneCaseHour().toString());
		crbi.setRemoteCaseHour(cbch.getRemoteCaseHour().toString());
		
		return crbi;
	}
	
}