/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.service.detail;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.utils.IdGen;
import com.dobo.common.utils.Reflections;
import com.dobo.modules.cst.calplugins.cost.CalCostService;
import com.dobo.modules.cst.calplugins.cost.impl.CheckManCostImpl;
import com.dobo.modules.cst.calplugins.cost.impl.CheckManCostImpl.CheckDetailSplit;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.dao.detail.CstDetailCostInfoDao;
import com.dobo.modules.cst.entity.detail.CstDetailCostInfo;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;
import com.dobo.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 订单清单成本明细Service
 * @author admin
 * @version 2016-11-14
 */
@Service
@Transactional(readOnly = true)
public class CstDetailCostInfoService extends CrudService<CstDetailCostInfoDao, CstDetailCostInfo> {

	@Autowired
	CstDetailCostInfoDao cstDetailCostInfoDao;
	
	/**
	 * 计算订单服务产品对应清单的成本
	 * @param codiList
	 * @return
	 */
	public static Map<String, List<CstDetailCostInfo>> getCalculateDetailCost(List<CstOrderDetailInfo> codiList, boolean flag) {
		
		String dcPrjId = codiList.get(0).getDcPrjId();
		Map<String, List<CstDetailCostInfo>> cstDetailCostInfoMap = new HashMap<String, List<CstDetailCostInfo>>();
		Map<String, Map<String, ProdDetailInfo>> prodDetailInfoMap = new TreeMap<String, Map<String, ProdDetailInfo>>(
				new Comparator<String>() {
					@Override
                    public int compare(String obj1, String obj2) {
						return obj1.compareTo(obj2);
					}
				});
		
		for(CstOrderDetailInfo cod : codiList) {
			String prodId = getProdMap().get(cod.getProdId());
			if(!prodDetailInfoMap.keySet().contains(prodId)) {
				Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
				prodDetailInfoMap.put(prodId, pdiList);
			}
			ProdDetailInfo pdi = new ProdDetailInfo();
			pdi.setDetailId(cod.getDetailId());
			pdi.setDetailId(cod.getDetailId());
			pdi.setDcPrjId(cod.getDcPrjId());
			pdi.setOrderId(cod.getOrderId());
			pdi.setMfrName(cod.getMfrName());
			pdi.setResourceName(cod.getResourceName());
			pdi.setDetailModel(cod.getDetailModel());
			
			pdi.setServiceBegin(cod.getBeginDate());
			pdi.setServiceEnd(cod.getEndDate());
			
			Map<String, String> inParaMap = new HashMap<String, String>();
			//inParaMap.put("resourceId", cod.getResId());
			//inParaMap.put("SECTION", cod.getCityId());
			//System.out.println(cod.getMfrName().toUpperCase()+"|"+cod.getResourceName().toUpperCase());
			
			inParaMap.put("resourceId", CacheDataUtils.getCstResourceBaseInfoTestMap().get(cod.getMfrName().toUpperCase()+cod.getResourceName().toUpperCase()).getResourceId());
			inParaMap.put("SECTION", CacheDataUtils.getCstManCityParaTestMap().get(cod.getCityId()).getCityId());
			inParaMap.put("amount", cod.getAmount());
			inParaMap.put("SLA", getSlaMap().get(cod.getSlaId()));
			inParaMap.put("BUYPRDMON", cod.getCycle());
			inParaMap.put("BUYCHECKN", cod.getCheckN()+"");
			inParaMap.put("BUYFARCHK", cod.getCheckF()+"");
			inParaMap.put("BUYDEPCHK", cod.getCheckD()+"");
			inParaMap.put("URGENT", cod.getUrgentId()+"");
			inParaMap.put("WORKKIND", cod.getWorkkindScale()+"");
			//inParaMap.put("prodServiceModel", cod.getProdServiceModel());
			//inParaMap.put("finalManCost", cod.getFinalManCost());
			//inParaMap.put("finalRiskAbilityCost", cod.getFinalRiskAbilityCost());
			//inParaMap.put("finalUrgeCost", cod.getFinalUrgeCost());
			
			pdi.setInParaMap(inParaMap);
			
			prodDetailInfoMap.get(prodId).put(pdi.getDetailId(), pdi);
		}
		
		//System.out.println("begin calculate "+new Date());	
		//long start = System.currentTimeMillis();
		Map<String, List<DetailCostInfo>> pdciMap = new HashMap<String, List<DetailCostInfo>>();
		// 返回成本明细集合 <prodId, <detailID, costInfo>>
		Map<String, Map<String, DetailCostInfo>> proddciMap = new HashMap<String, Map<String,DetailCostInfo>>();
		for(String prodId : prodDetailInfoMap.keySet()) {
			//System.out.println(prodId+":"+prodDetailInfoMap.get(prodId).size());
			//System.out.println(prodDetailInfoMap.get(prodId).get(0).getDcPrjId());
			// 获取服务的模型集合
			List<CstModelProdModuleRel> models = CacheDataUtils.getProdModuleRelMap().get(prodId);
			// 遍历产品成本模型
			for(CstModelProdModuleRel model : models) {
				CalCostService costService = null;
				try {
					Class<?> costClass = Class.forName(model.getClassName());
					//System.out.println(model.getClassName());
					costService = (CalCostService)costClass.newInstance();
					
					costService.cost(model, prodDetailInfoMap, proddciMap);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (InstantiationException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (IllegalAccessException e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				} catch (Exception e) {
					throw new RuntimeException("模型类【" + model.getClassName() + "】异常，请联系系统管理员处理！【" + e.getMessage() + "】");
				}
			}
			
		}
		
		for(String prodId : proddciMap.keySet()) {
			List<DetailCostInfo> retDetailCostlist = new ArrayList<DetailCostInfo>();
			Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
			for(String detailId : dcisMap.keySet()) {
				DetailCostInfo detailCostInfo = dcisMap.get(detailId);
				detailCostInfo.setDetailId(detailId);
				retDetailCostlist.add(detailCostInfo);
			}
			pdciMap.put(prodId, retDetailCostlist);
		}
		
		//System.out.println("end calculate "+new Date());
		//long end = System.currentTimeMillis();
		//System.out.println("项目号: "+dcPrjId+",数量:"+codiList.size()+"----耗时:"+DateUtils.formatDateTime(end-start));
		
		//将清单成本明细写入表中
		for(String prodId : pdciMap.keySet()) {
			List<CstDetailCostInfo> cdciList = Lists.newArrayList();
			
			User user = new User("admin");
			Date date = new Date();
			for(DetailCostInfo dci : pdciMap.get(prodId)) {
				Map<String, CstDetailCostInfo> cdciMap = new HashMap<String, CstDetailCostInfo>();
				//CstDetailCostInfo
				for(String measureId : dci.getCostInfoMap().keySet()) {
					String outkey = dci.getDetailId()+getDetailCostTypeMap(prodId).get(measureId);
					if(cdciMap.get(outkey) == null) {
						CstDetailCostInfo cdci = new CstDetailCostInfo();
						cdci.setId(IdGen.uuid());
						cdci.setProdId(prodId);
						cdci.setDcPrjId(dcPrjId);
						cdci.setDetailId(dci.getDetailId());
						cdci.setKeyId(getDetailCostTypeMap(prodId).get(measureId));
						cdci.setStatus("A0");
						cdci.setCreateBy(user);
						cdci.setCreateDate(date);
						cdci.setUpdateBy(user);
						cdci.setUpdateDate(date);
						cdci.setDelFlag("0");
						if (flag) {
							for (CstOrderDetailInfo cstOrderDetailInfo : codiList) {
								if (cstOrderDetailInfo.getDetailId().equals(dci.getDetailId())) {
									cdci.setMfrName(cstOrderDetailInfo.getMfrName());
									cdci.setResourceName(cstOrderDetailInfo.getResourceName());
									cdci.setBeginDate(cstOrderDetailInfo.getBeginDate());
									cdci.setEndDate(cstOrderDetailInfo.getEndDate());
								}
							}
						}
						cdciMap.put(outkey, cdci);
					}
					
					for(String key : getClassFileCostMeasureMap(prodId).keySet()) {
						if(getClassFileCostMeasureMap(prodId).get(key).contains(measureId)) {
							Reflections.setFieldValue(cdciMap.get(outkey), key, Double.valueOf(dci.getCostInfoMap().get(measureId)));
						}
					}
					
				}
				
				for(String key : cdciMap.keySet()) {
					cdciList.add(cdciMap.get(key));
				}
			}
			cstDetailCostInfoMap.put(prodId, cdciList);
		}
		
		return cstDetailCostInfoMap;
	}
	
	/**
	 * 拆分订单中巡检对应清单的巡检矩阵
	 * @param codiList
	 * @return
	 */
	public Map<CheckDetailSplit, Map<String, ProdDetailInfo>> getCheckProdDetailMap(List<CstOrderDetailInfo> codiList) {
		Map<CheckDetailSplit, Map<String, ProdDetailInfo>> checkDetailMap = null;

		Map<String, ProdDetailInfo> pdiList = Maps.newHashMap();
		String prodId = "RXSA-03-02-02";
		for(CstOrderDetailInfo cod : codiList) {
			if(prodId.equals(getProdMap().get(cod.getProdId()))) {
				ProdDetailInfo pdi = new ProdDetailInfo();
				pdi.setDetailId(cod.getDetailId());
				pdi.setDcPrjId(cod.getDcPrjId());
				pdi.setOrderId(cod.getOrderId());
				pdi.setMfrName(cod.getMfrName());
				pdi.setDetailModel(cod.getDetailModel());
				pdi.setResourceName(cod.getResourceName());
				
				
				pdi.setServiceBegin(cod.getBeginDate());
				pdi.setServiceEnd(cod.getEndDate());
				
				Map<String, String> inParaMap = new HashMap<String, String>();
				
				inParaMap.put("resourceId", CacheDataUtils.getCstResourceBaseInfoTestMap().get(cod.getMfrName().toUpperCase()+cod.getResourceName().toUpperCase()).getResourceId());
				inParaMap.put("SECTION", CacheDataUtils.getCstManCityParaTestMap().get(cod.getCityId()).getCityId());
				inParaMap.put("amount", cod.getAmount());
				inParaMap.put("SLA", getSlaMap().get(cod.getSlaId()));
				inParaMap.put("BUYPRDMON", cod.getCycle());
				inParaMap.put("BUYCHECKN", cod.getCheckN()+"");
				inParaMap.put("BUYFARCHK", cod.getCheckF()+"");
				inParaMap.put("BUYDEPCHK", cod.getCheckD()+"");
				inParaMap.put("URGENT", cod.getUrgentId()+"");
				inParaMap.put("WORKKIND", cod.getWorkkindScale()+"");
				pdi.setInParaMap(inParaMap);
				
				pdiList.put(pdi.getDetailId(), pdi);
			}
			
		}
		
		if(pdiList.size() > 0) {
			checkDetailMap = new HashMap<CheckDetailSplit, Map<String, ProdDetailInfo>>();
			CheckManCostImpl checkManCostImpl = new CheckManCostImpl();
			CheckDetailSplit checkDetailSplit = checkManCostImpl.splitDetailsByDateOrder(pdiList);
			checkDetailMap.put(checkDetailSplit, pdiList);
		}
		
		return checkDetailMap;
	}
	
	
	@Override
    public CstDetailCostInfo get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<CstDetailCostInfo> findList(CstDetailCostInfo cstDetailCostInfo) {
		return super.findList(cstDetailCostInfo);
	}
	
	@Override
    public Page<CstDetailCostInfo> findPage(Page<CstDetailCostInfo> page, CstDetailCostInfo cstDetailCostInfo) {
		return super.findPage(page, cstDetailCostInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstDetailCostInfo cstDetailCostInfo) {
		super.save(cstDetailCostInfo);
	}
	
	@Transactional(readOnly = false)
	public void addDetailbatch(List<CstDetailCostInfo> cstDetailCostInfoList) {
		cstDetailCostInfoDao.addDetailBatch(cstDetailCostInfoList);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstDetailCostInfo cstDetailCostInfo) {
		super.delete(cstDetailCostInfo);
	}
	
	/**
	 * 清单成本明细与成本分类集合<measureId, costType>
	 * @return
	 */
	private static Map<String, String> getDetailCostTypeMap(String prodId) {
		Map<String, String> detailCostTypeMap = new HashMap<String, String>();
		// 故障-人工成本  、 驻场
		if("RXSA-03-01-01,RXSA-03-02-02,RXSA-03-02-05,RXSA-02-01-03".contains(prodId)) {
			detailCostTypeMap.put("MANCSTH11", "人工-一线1级"); //MANCSTH11	一线1级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH12", "人工-一线2级"); //MANCSTH12	一线2级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH13", "人工-一线3级"); //MANCSTH13	一线3级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH14", "人工-一线4级"); //MANCSTH14	一线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH15", "人工-一线5级"); //MANCSTH15	一线5级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH24", "人工-二线4级"); //MANCSTH24	二线4级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH25", "人工-二线5级"); //MANCSTH25	二线5级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH26", "人工-二线6级"); //MANCSTH26	二线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH36", "人工-三线6级"); //MANCSTH36	三线6级人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH41", "人工-cmo"); //MANCSTH41	CMO人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH81", "人工-pmo"); //MANCSTH81	PMO人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTH51", "人工-资源岗"); //MANCSTH51	资源岗人工资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHG3", "人工-PM3级"); //MANCSTHG3	PM3级
			detailCostTypeMap.put("MANCSTHG4", "人工-PM4级"); //MANCSTHG4	PM4级
			detailCostTypeMap.put("MANCSTHG5", "人工-PM5级"); //MANCSTHG5	PM5级
			detailCostTypeMap.put("MANCSTHT1", "人工-一线1级-差旅"); //MANCSTHT1	一线1级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT2", "人工-一线2级-差旅"); //MANCSTHT2	一线2级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT3", "人工-一线3级-差旅"); //MANCSTHT3	一线3级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT4", "人工-一线4级-差旅"); //MANCSTHT4	一线4级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHT5", "人工-一线5级-差旅"); //MANCSTHT5	一线5级差旅资源计划（单位：人月）
			detailCostTypeMap.put("MANCSTHZ1", "人工-一线1级-折减"); //MANCSTHZ1	一线1级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ2", "人工-一线2级-折减"); //MANCSTHZ2	一线2级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ3", "人工-一线3级-折减"); //MANCSTHZ3	一线3级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ4", "人工-一线4级-折减"); //MANCSTHZ4	一线4级人工资源计划驻场折减（单位：人月）
			detailCostTypeMap.put("MANCSTHZ5", "人工-一线5级-折减"); //MANCSTHZ5	一线5级人工资源计划驻场折减（单位：人月）
			
			detailCostTypeMap.put("MANCSTM11", "人工-一线1级"); //MANCSTM11	一线1级人工包
			detailCostTypeMap.put("MANCSTM12", "人工-一线2级"); //MANCSTM12	一线2级人工包
			detailCostTypeMap.put("MANCSTM13", "人工-一线3级"); //MANCSTM13	一线3级人工包
			detailCostTypeMap.put("MANCSTM14", "人工-一线4级"); //MANCSTM14	一线4级人工包
			detailCostTypeMap.put("MANCSTM15", "人工-一线5级"); //MANCSTM15	一线5级人工包
			detailCostTypeMap.put("MANCSTF11", "人工-一线1级"); //MANCSTF11	一线1级费用包
			detailCostTypeMap.put("MANCSTF12", "人工-一线2级"); //MANCSTF12	一线2级费用包
			detailCostTypeMap.put("MANCSTF13", "人工-一线3级"); //MANCSTF13	一线3级费用包
			detailCostTypeMap.put("MANCSTF14", "人工-一线4级"); //MANCSTF14	一线4级费用包
			detailCostTypeMap.put("MANCSTF15", "人工-一线5级"); //MANCSTF15	一线5级费用包
			detailCostTypeMap.put("MANCSTU11", "人工-一线1级"); //MANCSTU11	一线1级激励包
			detailCostTypeMap.put("MANCSTU12", "人工-一线2级"); //MANCSTU12	一线2级激励包
			detailCostTypeMap.put("MANCSTU13", "人工-一线3级"); //MANCSTU13	一线3级激励包
			detailCostTypeMap.put("MANCSTU14", "人工-一线4级"); //MANCSTU14	一线4级激励包
			detailCostTypeMap.put("MANCSTU15", "人工-一线5级"); //MANCSTU15	一线5级激励包
			detailCostTypeMap.put("MANCSTM24", "人工-二线4级"); //MANCSTM24	二线4级人工包
			detailCostTypeMap.put("MANCSTM25", "人工-二线5级"); //MANCSTM25	二线5级人工包
			detailCostTypeMap.put("MANCSTM26", "人工-二线6级"); //MANCSTM26	二线6级人工包
			detailCostTypeMap.put("MANCSTF24", "人工-二线4级"); //MANCSTF24	二线4级费用包
			detailCostTypeMap.put("MANCSTF25", "人工-二线5级"); //MANCSTF25	二线5级费用包
			detailCostTypeMap.put("MANCSTF26", "人工-二线6级"); //MANCSTF26	二线6级费用包
			detailCostTypeMap.put("MANCSTU24", "人工-二线4级"); //MANCSTU24	二线4级激励包
			detailCostTypeMap.put("MANCSTU25", "人工-二线5级"); //MANCSTU25	二线5级激励包
			detailCostTypeMap.put("MANCSTU26", "人工-二线6级"); //MANCSTU26	二线6级激励包
			detailCostTypeMap.put("MANCSTM31", "人工-三线6级"); //MANCSTM31	三线6级人工包
			detailCostTypeMap.put("MANCSTF31", "人工-三线6级"); //MANCSTF31	三线6级费用包
			detailCostTypeMap.put("MANCSTU31", "人工-三线6级"); //MANCSTU31	三线6级激励包
			detailCostTypeMap.put("MANCSTM41", "人工-cmo"); //MANCSTM41	cmo人工包
			detailCostTypeMap.put("MANCSTF41", "人工-cmo"); //MANCSTF41	cmo费用包
			detailCostTypeMap.put("MANCSTU41", "人工-cmo"); //MANCSTU41	cmo激励包
			detailCostTypeMap.put("MANCSTM81", "人工-pmo"); //MANCSTM81	pmo人工包
			detailCostTypeMap.put("MANCSTF81", "人工-pmo"); //MANCSTF81	pmo费用包
			detailCostTypeMap.put("MANCSTU81", "人工-pmo"); //MANCSTU81	pmo激励包
			detailCostTypeMap.put("MANCSTM51", "人工-资源岗"); //MANCSTM51	资源岗人工包
			detailCostTypeMap.put("MANCSTF51", "人工-资源岗"); //MANCSTF51	资源岗费用包
			detailCostTypeMap.put("MANCSTU51", "人工-资源岗"); //MANCSTU51	资源岗激励包
			detailCostTypeMap.put("MANCSTM61", "人工-区域管理"); //MANCSTM61	区域管理人工包
			detailCostTypeMap.put("MANCSTF61", "人工-区域管理"); //MANCSTF61	区域管理费用包
			detailCostTypeMap.put("MANCSTU61", "人工-区域管理"); //MANCSTU61	区域管理激励包
			detailCostTypeMap.put("MANCSTM71", "人工-总控管理"); //MANCSTM71	总控管理人工包
			detailCostTypeMap.put("MANCSTF71", "人工-总控管理"); //MANCSTF71	总控管理费用包
			detailCostTypeMap.put("MANCSTU71", "人工-总控管理"); //MANCSTU71	总控管理激励包
			detailCostTypeMap.put("MANCSTMT1", "人工-一线1级-差旅"); //MANCSTMT1	一线1级差旅人工包
			detailCostTypeMap.put("MANCSTMT2", "人工-一线2级-差旅"); //MANCSTMT2	一线2级差旅人工包
			detailCostTypeMap.put("MANCSTMT3", "人工-一线3级-差旅"); //MANCSTMT3	一线3级差旅人工包
			detailCostTypeMap.put("MANCSTMT4", "人工-一线4级--差旅"); //MANCSTMT4	一线4级差旅人工包
			detailCostTypeMap.put("MANCSTMT5", "人工-一线5级-差旅"); //MANCSTMT5	一线5级差旅人工包
			detailCostTypeMap.put("MANCSTFT1", "人工-一线1级-差旅"); //MANCSTFT1	一线1级差旅费用包
			detailCostTypeMap.put("MANCSTFT2", "人工-一线2级-差旅"); //MANCSTFT2	一线2级差旅费用包
			detailCostTypeMap.put("MANCSTFT3", "人工-一线3级-差旅"); //MANCSTFT3	一线3级差旅费用包
			detailCostTypeMap.put("MANCSTFT4", "人工-一线4级-差旅"); //MANCSTFT4	一线4级差旅费用包
			detailCostTypeMap.put("MANCSTFT5", "人工-一线5级-差旅"); //MANCSTFT5	一线5级差旅费用包
			detailCostTypeMap.put("MANCSTUT1", "人工-一线1级-差旅"); //MANCSTUT1	一线1级差旅激励包
			detailCostTypeMap.put("MANCSTUT2", "人工-一线2级-差旅"); //MANCSTUT2	一线2级差旅激励包
			detailCostTypeMap.put("MANCSTUT3", "人工-一线3级-差旅"); //MANCSTUT3	一线3级差旅激励包
			detailCostTypeMap.put("MANCSTUT4", "人工-一线4级-差旅"); //MANCSTUT4	一线4级差旅激励包
			detailCostTypeMap.put("MANCSTUT5", "人工-一线5级-差旅"); //MANCSTUT5	一线5级差旅激励包
			detailCostTypeMap.put("MANCSTTF1", "人工-一线1级-差旅"); //MANCSTTF1	一线1级差旅费
			detailCostTypeMap.put("MANCSTTF2", "人工-一线2级-差旅"); //MANCSTTF2	一线2级差旅费
			detailCostTypeMap.put("MANCSTTF3", "人工-一线3级-差旅"); //MANCSTTF3	一线3级差旅费
			detailCostTypeMap.put("MANCSTTF4", "人工-一线4级-差旅"); //MANCSTTF4	一线4级差旅费
			detailCostTypeMap.put("MANCSTTF5", "人工-一线5级-差旅"); //MANCSTTF5	一线5级差旅费
			detailCostTypeMap.put("MANCSTMZ1", "人工-一线1级-折减"); //MANCSTMZ1	一线1级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ2", "人工-一线2级-折减"); //MANCSTMZ2	一线2级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ3", "人工-一线3级-折减"); //MANCSTMZ3	一线3级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ4", "人工-一线4级-折减"); //MANCSTMZ4	一线4级人工驻场折减
			detailCostTypeMap.put("MANCSTMZ5", "人工-一线5级-折减"); //MANCSTMZ5	一线5级人工驻场折减
			detailCostTypeMap.put("MANCSTFZ1", "人工-一线1级-折减"); //MANCSTFZ1	一线1级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ2", "人工-一线2级-折减"); //MANCSTFZ2	一线2级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ3", "人工-一线3级-折减"); //MANCSTFZ3	一线3级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ4", "人工-一线4级-折减"); //MANCSTFZ4	一线4级费用驻场折减
			detailCostTypeMap.put("MANCSTFZ5", "人工-一线5级-折减"); //MANCSTFZ5	一线5级费用驻场折减
			detailCostTypeMap.put("MANCSTUZ1", "人工-一线1级-折减"); //MANCSTUZ1	一线1级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ2", "人工-一线2级-折减"); //MANCSTUZ2	一线2级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ3", "人工-一线3级-折减"); //MANCSTUZ3	一线3级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ4", "人工-一线4级-折减"); //MANCSTUZ4	一线4级激励驻场折减
			detailCostTypeMap.put("MANCSTUZ5", "人工-一线5级-折减"); //MANCSTUZ5	一线5级激励驻场折减
			detailCostTypeMap.put("MANCSTM6Z", "人工-区域管理-折减"); //MANCSTM6Z	区域管理人工折减
			detailCostTypeMap.put("MANCSTF6Z", "人工-区域管理-折减"); //MANCSTF6Z	区域管理费用折减
			detailCostTypeMap.put("MANCSTU6Z", "人工-区域管理-折减"); //MANCSTU6Z	区域管理激励折减

			detailCostTypeMap.put("MANCSTMM1", "人工-管理"); //MANCSTMM1	管理
			detailCostTypeMap.put("MANCSTTT1", "人工-工具"); //MANCSTTT1	工具
			detailCostTypeMap.put("MANCSTMR1", "人工-风险"); //MANCSTMR1	风险
			detailCostTypeMap.put("MANCSTMG3", "人工-PM3级"); //MANCSTMG3	PM3级人工包
			detailCostTypeMap.put("MANCSTFG3", "人工-PM3级"); //MANCSTFG3	PM3级费用包
			detailCostTypeMap.put("MANCSTUG3", "人工-PM3级"); //MANCSTUG3	PM3级激励包
			detailCostTypeMap.put("MANCSTMG4", "人工-PM4级"); //MANCSTMG4	PM4级人工包
			detailCostTypeMap.put("MANCSTFG4", "人工-PM4级"); //MANCSTFG4	PM4级费用包
			detailCostTypeMap.put("MANCSTUG4", "人工-PM4级"); //MANCSTUG4	PM4级激励包
			detailCostTypeMap.put("MANCSTMG5", "人工-PM5级"); //MANCSTMG5	PM5级人工包
			detailCostTypeMap.put("MANCSTFG5", "人工-PM5级"); //MANCSTFG5	PM5级费用包
			detailCostTypeMap.put("MANCSTUG5", "人工-PM5级"); //MANCSTUG5	PM5级激励包
			// 故障-备件成本
			detailCostTypeMap.put("BAKCSTT11", "备件-故障成本"); //BAKCSTT11	故障成本
			detailCostTypeMap.put("BAKCSTM21", "备件-备件人工成本"); //BAKCSTT21	备件人员人工成本
			detailCostTypeMap.put("BAKCSTU21", "备件-备件人工成本"); //BAKCSTT21	备件人员激励成本
			detailCostTypeMap.put("BAKCSTM22", "备件-总控管理"); //BAKCSTM22	单件管控管理人工成本
			detailCostTypeMap.put("BAKCSTF22", "备件-总控管理"); //BAKCSTF22	单件管控管理费用成本
			detailCostTypeMap.put("BAKCSTU22", "备件-总控管理"); //BAKCSTU22	单件管控管理激励成本
			detailCostTypeMap.put("BAKCSTT41", "备件-高频储备成本"); //BAKCSTT41	高频储备成本
			detailCostTypeMap.put("BAKCSTT42", "备件-项目储备成本"); //BAKCSTT42	项目储备成本
			detailCostTypeMap.put("BAKCSTT51", "备件-物业、水电成本"); //BAKCSTT51	物业、水电成本
			detailCostTypeMap.put("BAKCSTT52", "备件-包材成本"); //BAKCSTT52	包材成本
			detailCostTypeMap.put("BAKCSTT53", "备件-仓库租赁成本"); //BAKCSTT53	仓库租赁成本
			detailCostTypeMap.put("BAKCSTT33", "备件-回收取件运输成本"); //BAKCSTT33	回收取件运输成本
			detailCostTypeMap.put("BAKCSTT32", "备件-调拨运输成本"); //BAKCSTT32	调拨运输成本
			detailCostTypeMap.put("BAKCSTT31", "备件-故障件发货运输成本"); //BAKCSTT31	故障件发货运输成本
			detailCostTypeMap.put("BAKCSTMM1", "备件-管理"); //BAKCSTMM1	管理
			detailCostTypeMap.put("BAKCSTTT1", "备件-工具"); //BAKCSTTT1	工具
			detailCostTypeMap.put("BAKCSTMR1", "备件-风险"); //BAKCSTMR1	风险
		}
		
		
		return detailCostTypeMap;
	}
	
	/**
	 * 类属性与清单成本明细对应集合<fileType, measureId>
	 * @return
	 */
	private static Map<String, String> getClassFileCostMeasureMap(String prodId) {
		Map<String, String> classFileCostMeasureMap = new HashMap<String, String>();
		
		if("RXSA-03-01-01,RXSA-03-02-02,RXSA-03-02-05,RXSA-02-01-03".contains(prodId)) {
			classFileCostMeasureMap.put("resPlan", "MANCSTH11,MANCSTH12,MANCSTH13,MANCSTH14,MANCSTH15,MANCSTH24,MANCSTH25,MANCSTH26,MANCSTH36,MANCSTH41,MANCSTH51,MANCSTH81,MANCSTHT1,MANCSTHT2,MANCSTHT3,MANCSTHT4,MANCSTHT5,MANCSTHG3,MANCSTHG4,MANCSTHG5,MANCSTHZ1,MANCSTHZ2,MANCSTHZ3,MANCSTHZ4,MANCSTHZ5");
			classFileCostMeasureMap.put("manCost", "MANCSTM11,MANCSTM12,MANCSTM13,MANCSTM14,MANCSTM15,MANCSTM24,MANCSTM25,MANCSTM26,MANCSTM31,MANCSTM41,MANCSTM51,MANCSTM61,MANCSTM71,MANCSTM81,MANCSTMT1,MANCSTMT2,MANCSTMT3,MANCSTMT4,MANCSTMT5,MANCSTMM1,MANCSTMG3,MANCSTMG4,MANCSTMG5,MANCSTMZ1,MANCSTMZ2,MANCSTMZ3,MANCSTMZ4,MANCSTMZ5,MANCSTM6Z,BAKCSTM22,BAKCSTM21,BAKCSTMM1");
			classFileCostMeasureMap.put("feeCost", "MANCSTF11,MANCSTF12,MANCSTF13,MANCSTF14,MANCSTF15,MANCSTF24,MANCSTF25,MANCSTF26,MANCSTF31,MANCSTF41,MANCSTF51,MANCSTF61,MANCSTF71,MANCSTF81,MANCSTFT1,MANCSTFT2,MANCSTFT3,MANCSTFT4,MANCSTFT5,MANCSTFG3,MANCSTFG4,MANCSTFG5,MANCSTFZ1,MANCSTFZ2,MANCSTFZ3,MANCSTFZ4,MANCSTFZ5,MANCSTF6Z,MANCSTTT1,MANCSTMR1,BAKCSTF22,BAKCSTTT1,BAKCSTMR1");
			classFileCostMeasureMap.put("urgeCost", "MANCSTU11,MANCSTU12,MANCSTU13,MANCSTU14,MANCSTU15,MANCSTU24,MANCSTU25,MANCSTU26,MANCSTU31,MANCSTU41,MANCSTU51,MANCSTU61,MANCSTU71,MANCSTU81,MANCSTUT1,MANCSTUT2,MANCSTUT3,MANCSTUT4,MANCSTUT5,MANCSTUG3,MANCSTUG4,MANCSTUG5,MANCSTUZ1,MANCSTUZ2,MANCSTUZ3,MANCSTUZ4,MANCSTUZ5,MANCSTU6Z,BAKCSTU22,BAKCSTU21");
			classFileCostMeasureMap.put("travlFee", "MANCSTTF1,MANCSTTF2,MANCSTTF3,MANCSTTF4,MANCSTTF5");
			
			classFileCostMeasureMap.put("baseFee", "BAKCSTT11,BAKCSTT31,BAKCSTT32,BAKCSTT33,BAKCSTT41,BAKCSTT42,BAKCSTT51,BAKCSTT52,BAKCSTT53");
		}

		return classFileCostMeasureMap;
	}
	
	private static Map<String, String> getSlaMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		slaMap.put("高级服务A", "SLA_DEVICE_A");	
		slaMap.put("标准服务B", "SLA_DEVICE_B");	
		slaMap.put("基础服务C", "SLA_DEVICE_C");	
		slaMap.put("特惠服务D", "SLA_DEVICE_D");	
		slaMap.put("高级服务SP", "SLA_SOFT_A");	
		slaMap.put("基础服务SP+", "SLA_SOFT_B");	
		slaMap.put("基础服务SP-", "SLA_SOFT_C");	
		
		return slaMap;
	}
	
	private static Map<String, String> getProdMap() {
		Map<String, String> slaMap = new HashMap<String, String>();
		slaMap.put("故障解决服务", "RXSA-03-01-01");	
		slaMap.put("硬件故障解决服务", "RXSA-03-01-01");	
		slaMap.put("健康检查服务", "RXSA-03-02-02");	
		slaMap.put("硬件健康检查服务", "RXSA-03-02-02");	
		slaMap.put("驻场服务", "RXSA-03-02-05");	
		slaMap.put("系统软件高级服务SP", "RXSA-02-01-03");	
		
		return slaMap;
	}
	
}