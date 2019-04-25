/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.service.detail;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.modules.cop.dao.detail.CopCaseDetailDao;
import com.dobo.modules.cop.dao.detail.CopCasePriceConfirmDao;
import com.dobo.modules.cop.dao.detail.CopSalesUseDetailDao;
import com.dobo.modules.cop.entity.detail.CopCaseDetail;
import com.dobo.modules.cop.entity.detail.CopCasePriceConfirm;
import com.dobo.modules.cop.entity.detail.CopSalesUseDetail;

/**
 * CASE单次报价确认表(TOP)Service
 * @author admin
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true)
public class CopCasePriceConfirmService extends CrudService<CopCasePriceConfirmDao, CopCasePriceConfirm> {

	@Autowired
	CopCasePriceConfirmDao copCasePriceConfirmDao;
	@Autowired
	protected CopCaseDetailDao copCaseDetailDao;
	@Autowired
	protected CopSalesUseDetailDao copSalesUseDetailDao;
	
	@Override
    public CopCasePriceConfirm get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CopCasePriceConfirm> findList(CopCasePriceConfirm copCasePriceConfirm) {
		return super.findList(copCasePriceConfirm);
	}
	
	@Override
    public Page<CopCasePriceConfirm> findPage(Page<CopCasePriceConfirm> page, CopCasePriceConfirm copCasePriceConfirm) {
		return super.findPage(page, copCasePriceConfirm);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CopCasePriceConfirm copCasePriceConfirm) {
		super.save(copCasePriceConfirm);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CopCasePriceConfirm copCasePriceConfirm) {
		super.delete(copCasePriceConfirm);
	}
	
	@Transactional(readOnly = false)
	public List<String> getExecareaBatch(String abc) {
		return copCasePriceConfirmDao.getExecareaBatch(abc);
	}
	
	/**
	 * 获取所以单次确认明细数据列表
	 * 
	 * @param createDateStr
	 * @param dcPrjId
	 * @return
	 */
	public Map<String, List<CopCaseDetail>> getCopCaseDetailMap(String createDateStr, String dcPrjId) {
		Map<String, List<CopCaseDetail>> copCaseMap = new LinkedHashMap<String, List<CopCaseDetail>>();
		
		// 单次case报价明细
		List<CopCaseDetail> copCaseDetailList = copCaseDetailDao.getCaseConfirmList(createDateStr, dcPrjId);
		// 按照单次报价号（单次报价确认ID）聚合
		for(CopCaseDetail info : copCaseDetailList) {
			if(copCaseMap.get(info.getCaseConfirmId()) == null) {
				copCaseMap.put(info.getCaseConfirmId(), new LinkedList<CopCaseDetail>());
			}
			copCaseMap.get(info.getCaseConfirmId()).add(info);
		}
		
		// 单次case-预付费报价支付明细
		List<CopSalesUseDetail> copSalesUseDetailList = copSalesUseDetailDao.getCaseAccountUsedList(createDateStr, dcPrjId);
		// 按照单次报价号（单次报价确认ID）聚合
		Map<String, List<CopSalesUseDetail>> confirmDcPrjIdMap = new LinkedHashMap<String, List<CopSalesUseDetail>>();
		for(CopSalesUseDetail csud : copSalesUseDetailList) {
			if(confirmDcPrjIdMap.get(csud.getCaseConfirmId()) == null) {
				confirmDcPrjIdMap.put(csud.getCaseConfirmId(), new LinkedList<CopSalesUseDetail>());
			}
			confirmDcPrjIdMap.get(csud.getCaseConfirmId()).add(csud);
		}
		// 单次case-预付费 多项目支付的需要按照项目支付占比拆分报价明细，按项目计算资源计划
		for(String key : confirmDcPrjIdMap.keySet()) {
			if(confirmDcPrjIdMap.get(key).size() > 1) {
				// 按照多个项目拆分明细
				int count = 0;
				for(CopSalesUseDetail csud : confirmDcPrjIdMap.get(key)) {
					count ++;
					for(CopCaseDetail ccd : copCaseMap.get(key)) {
						CopCaseDetail ccdNew = new CopCaseDetail();
						ccdNew.setId(ccd.getId());
						ccdNew.setPriceNum(ccd.getPriceNum());
						ccdNew.setOnceCode(ccd.getOnceCode());
						ccdNew.setOnceNum(ccd.getOnceNum());
						ccdNew.setCaseCode(ccd.getCaseCode());
						ccdNew.setCaseId(ccd.getCaseId());
						ccdNew.setPriceType(ccd.getPriceType());
						ccdNew.setServiceType(ccd.getServiceType());
						ccdNew.setManRoleId(ccd.getManRoleId());
						ccdNew.setManEngineerLevel(ccd.getManEngineerLevel());
						ccdNew.setManResourceType(ccd.getManResourceType());
						ccdNew.setPartPn(ccd.getPartPn());
						ccdNew.setStatus(ccd.getStatus());
						ccdNew.setCreateDate(ccd.getCreateDate());
						ccdNew.setUpdateDate(ccd.getUpdateDate());
						
						ccdNew.setManWorkload(ccd.getManWorkload()*csud.getCostScale());
						ccdNew.setManPrice(ccd.getManPrice()*csud.getCostScale());
						ccdNew.setManTravelPrice(ccd.getManTravelPrice()*csud.getCostScale());
						ccdNew.setPartAmount(ccd.getPartAmount()*csud.getCostScale());
						ccdNew.setPartDeliveryPrice(ccd.getPartDeliveryPrice()*csud.getCostScale());
						ccdNew.setPartPrice(ccd.getPartPrice());
						
						ccdNew.setDcPrjId(csud.getDcPrjId());
						ccdNew.setPayType(ccd.getPayType());
						ccdNew.setCaseConfirmId(ccd.getCaseConfirmId());
						
						if(copCaseMap.get(key+"_"+count) == null) {
							copCaseMap.put(key+"_"+count, new LinkedList<CopCaseDetail>());
						}
						copCaseMap.get(key+"_"+count).add(ccdNew);
					}
				}
				
				// 删除原数据
				copCaseMap.remove(key);
			}
		}
		
		return copCaseMap;
	}
	
}