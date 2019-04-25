package com.dobo.modules.cst.common;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dobo.common.utils.EhCacheUtils;
import com.dobo.common.utils.SpringContextHolder;
import com.dobo.modules.cst.dao.base.CstBaseBackCaseParaDao;
import com.dobo.modules.cst.dao.base.CstBaseBackFaultParaDao;
import com.dobo.modules.cst.dao.base.CstBaseBackPackParaDao;
import com.dobo.modules.cst.dao.base.CstBaseCaseHourDao;
import com.dobo.modules.cst.dao.base.CstBaseCaseLineoneDao;
import com.dobo.modules.cst.dao.base.CstBaseResourceCaseDao;
import com.dobo.modules.cst.dao.base.CstDictParaDao;
import com.dobo.modules.cst.dao.base.CstManManageStepRuleDao;
import com.dobo.modules.cst.dao.base.CstResourceBaseInfoDao;
import com.dobo.modules.cst.dao.cases.CstCaseStandHourScaleDao;
import com.dobo.modules.cst.dao.cases.CstManCaseSupportPriceDao;
import com.dobo.modules.cst.dao.check.CstCheckDistanceStepHourDao;
import com.dobo.modules.cst.dao.check.CstCheckDistanceUnitHourDao;
import com.dobo.modules.cst.dao.check.CstCheckFirstcheckParaDao;
import com.dobo.modules.cst.dao.check.CstCheckResmgrHourDao;
import com.dobo.modules.cst.dao.check.CstCheckResstatSystemRelDao;
import com.dobo.modules.cst.dao.check.CstCheckSlaParaDao;
import com.dobo.modules.cst.dao.check.CstCheckWorkHourDao;
import com.dobo.modules.cst.dao.man.CstManCityParaDao;
import com.dobo.modules.cst.dao.man.CstManFailureCaseHourDao;
import com.dobo.modules.cst.dao.man.CstManFailureDegreeDao;
import com.dobo.modules.cst.dao.man.CstManFailureManRateDao;
import com.dobo.modules.cst.dao.man.CstManFailureSlaParaDao;
import com.dobo.modules.cst.dao.man.CstManManageDegreeParaDao;
import com.dobo.modules.cst.dao.man.CstManProdParaDao;
import com.dobo.modules.cst.dao.man.CstManStandbyParaDao;
import com.dobo.modules.cst.dao.model.CstModelCalculateFormulaDao;
import com.dobo.modules.cst.dao.model.CstModelModuleInfoDao;
import com.dobo.modules.cst.dao.model.CstModelParaDefDao;
import com.dobo.modules.cst.dao.model.CstModelProdModuleRelDao;
import com.dobo.modules.cst.dao.parts.CstPartsCooperCostDao;
import com.dobo.modules.cst.dao.parts.CstPartsCooperModelDetailDao;
import com.dobo.modules.cst.dao.parts.CstPartsCooperToOnwerDao;
import com.dobo.modules.cst.dao.parts.CstPartsEquipTypeRateDao;
import com.dobo.modules.cst.dao.parts.CstPartsEventFailureParaDao;
import com.dobo.modules.cst.dao.parts.CstPartsFailureRateCostDao;
import com.dobo.modules.cst.dao.parts.CstPartsFillRateDao;
import com.dobo.modules.cst.dao.parts.CstPartsPrjStoreParaDao;
import com.dobo.modules.cst.dao.parts.CstPartsRiskCostParaDao;
import com.dobo.modules.cst.dao.parts.CstPartsSlaCostRateDao;
import com.dobo.modules.cst.dao.parts.CstPartsTransportCostDao;
import com.dobo.modules.cst.dao.parts.CstPartsWeightAmountDao;
import com.dobo.modules.cst.dao.soft.CstSoftPackDegreeParaDao;
import com.dobo.modules.cst.dao.soft.CstSoftPackStepRuleDao;
import com.dobo.modules.cst.dao.temp.CstManNotDeviceParaDao;
import com.dobo.modules.cst.entity.base.CstBaseBackCasePara;
import com.dobo.modules.cst.entity.base.CstBaseBackFaultPara;
import com.dobo.modules.cst.entity.base.CstBaseBackPackPara;
import com.dobo.modules.cst.entity.base.CstBaseCaseHour;
import com.dobo.modules.cst.entity.base.CstBaseCaseLineone;
import com.dobo.modules.cst.entity.base.CstBaseResourceCase;
import com.dobo.modules.cst.entity.base.CstDictPara;
import com.dobo.modules.cst.entity.base.CstManLevelRoleRel;
import com.dobo.modules.cst.entity.base.CstManManageStepRule;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.cases.CstCaseStandHourScale;
import com.dobo.modules.cst.entity.cases.CstManCaseSupportPrice;
import com.dobo.modules.cst.entity.check.CstCheckDistanceStepHour;
import com.dobo.modules.cst.entity.check.CstCheckDistanceUnitHour;
import com.dobo.modules.cst.entity.check.CstCheckFirstcheckPara;
import com.dobo.modules.cst.entity.check.CstCheckResmgrHour;
import com.dobo.modules.cst.entity.check.CstCheckResstatSystemRel;
import com.dobo.modules.cst.entity.check.CstCheckSlaPara;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;
import com.dobo.modules.cst.entity.man.CstManCityPara;
import com.dobo.modules.cst.entity.man.CstManFailureCaseHour;
import com.dobo.modules.cst.entity.man.CstManFailureDegree;
import com.dobo.modules.cst.entity.man.CstManFailureManRate;
import com.dobo.modules.cst.entity.man.CstManFailureSlaPara;
import com.dobo.modules.cst.entity.man.CstManManageDegreePara;
import com.dobo.modules.cst.entity.man.CstManProdPara;
import com.dobo.modules.cst.entity.man.CstManStandbyPara;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelModuleInfo;
import com.dobo.modules.cst.entity.model.CstModelParaDef;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.entity.parts.CstPartsCooperCost;
import com.dobo.modules.cst.entity.parts.CstPartsCooperModelDetail;
import com.dobo.modules.cst.entity.parts.CstPartsCooperToOnwer;
import com.dobo.modules.cst.entity.parts.CstPartsEquipTypeRate;
import com.dobo.modules.cst.entity.parts.CstPartsEventFailurePara;
import com.dobo.modules.cst.entity.parts.CstPartsFailureRateCost;
import com.dobo.modules.cst.entity.parts.CstPartsFillRate;
import com.dobo.modules.cst.entity.parts.CstPartsPrjStorePara;
import com.dobo.modules.cst.entity.parts.CstPartsRiskCostPara;
import com.dobo.modules.cst.entity.parts.CstPartsSlaCostRate;
import com.dobo.modules.cst.entity.parts.CstPartsTransportCost;
import com.dobo.modules.cst.entity.parts.CstPartsWeightAmount;
import com.dobo.modules.cst.entity.soft.CstSoftPackDegreePara;
import com.dobo.modules.cst.entity.soft.CstSoftPackStepRule;
import com.dobo.modules.cst.entity.temp.CstManNotDevicePara;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * 系统配置缓存工具类
 */
public class CacheDataUtils {
	
	private static CstModelParaDefDao cstModelParaDefDao = SpringContextHolder.getBean(CstModelParaDefDao.class);

	private static CstModelModuleInfoDao cstModelModuleInfoDao = SpringContextHolder.getBean(CstModelModuleInfoDao.class);

	private static CstModelCalculateFormulaDao cstModelCalculateFormulaDao = SpringContextHolder.getBean(CstModelCalculateFormulaDao.class);

	private static CstModelProdModuleRelDao cstModelProdModuleRelDao = SpringContextHolder.getBean(CstModelProdModuleRelDao.class);

	/**
	 * 成本模型缓存
	 */
	public static String CST_MODELINFO_CACHE = "cstModelCache";
	
	public static final String CACHE_PRODMODULEREL_MAP = "prodModuleRelMap";
	public static final String CACHE_MODULEINFO_MAP = "moduleInfoMap";
	public static final String CACHE_MODELPARADEF_MAP = "modelParaDefMap";
	public static final String CACHE_MODELCALFORMULA_MAP = "modelCalFormulaMap";

	/**
	 * 成本模型缓存
	 */
	public static String CST_BASEDATA_CACHE = "cstBaseDataCache";

	/**
	 * 初始成本模型缓存
	 */
	public static void initCstBaseDataCache(){
		getProdModuleRelMap();
		getModuleInfoMap();
		getModelParaDefMap();
		getModelCalculateFormulaMap();		
	}
	
	/**
	 * 刷新成本模型配置数据
	 */
	public static void freshModuleCfgParaCache(){
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CACHE_PRODMODULEREL_MAP);
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CACHE_MODULEINFO_MAP);
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CACHE_MODELPARADEF_MAP);
		EhCacheUtils.remove(CacheDataUtils.CST_MODELINFO_CACHE, CACHE_MODELCALFORMULA_MAP);
		
	}

	/**
	 * 产品成本对应模型定义集合
	 * @return
	 */
	public static Map<String, List<CstModelProdModuleRel>> getProdModuleRelMap(){
		@SuppressWarnings("unchecked")
		Map<String, List<CstModelProdModuleRel>> prodModuleRelMap = (Map<String, List<CstModelProdModuleRel>>)EhCacheUtils.get(CST_MODELINFO_CACHE, CACHE_PRODMODULEREL_MAP);
		if (prodModuleRelMap == null){
			prodModuleRelMap = Maps.newHashMap();
			for (CstModelProdModuleRel cmpmr : cstModelProdModuleRelDao.findAllList(new CstModelProdModuleRel())){
				if(!"A0".equals(cmpmr.getStatus())) continue;
				List<CstModelProdModuleRel> pmrList = prodModuleRelMap.get(cmpmr.getProdId());
				if(pmrList == null) {
					prodModuleRelMap.put(cmpmr.getProdId(), Lists.newArrayList(cmpmr));
				} else {
					prodModuleRelMap.get(cmpmr.getProdId()).add(cmpmr);
				}
			}
			EhCacheUtils.put(CST_MODELINFO_CACHE, CACHE_PRODMODULEREL_MAP, prodModuleRelMap);
		}
		
		return prodModuleRelMap;
	}

	/**
	 * 成本模型信息集合
	 * @return
	 */
	public static Map<String, CstModelModuleInfo> getModuleInfoMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstModelModuleInfo> moduleInfoMap = (Map<String, CstModelModuleInfo>)EhCacheUtils.get(CST_MODELINFO_CACHE, CACHE_MODULEINFO_MAP);
		if (moduleInfoMap == null){
			moduleInfoMap = Maps.newHashMap();
			for (CstModelModuleInfo cmmi : cstModelModuleInfoDao.findAllList(new CstModelModuleInfo())){
				if(!"A0".equals(cmmi.getStatus())) continue;
				moduleInfoMap.put(cmmi.getId(), cmmi);
			}
			EhCacheUtils.put(CST_MODELINFO_CACHE, CACHE_MODULEINFO_MAP, moduleInfoMap);
		}
		
		return moduleInfoMap;
	}

	/**
	 * 成本参数定义集合
	 * @return
	 */
	public static Map<String, List<CstModelParaDef>> getModelParaDefMap(){
		@SuppressWarnings("unchecked")
		Map<String, List<CstModelParaDef>> modelParaDefMap = (Map<String, List<CstModelParaDef>>)EhCacheUtils.get(CST_MODELINFO_CACHE, CACHE_MODELPARADEF_MAP);
		if (modelParaDefMap == null){
			modelParaDefMap = Maps.newHashMap();
			for (CstModelParaDef cmpd : cstModelParaDefDao.findAllList(new CstModelParaDef())){
				if(!"A0".equals(cmpd.getStatus())) continue;
				List<CstModelParaDef> pmrList = modelParaDefMap.get(cmpd.getModuleId());
				if(pmrList == null) {
					modelParaDefMap.put(cmpd.getModuleId(), Lists.newArrayList(cmpd));
				} else {
					modelParaDefMap.get(cmpd.getModuleId()).add(cmpd);
				}
			}
			EhCacheUtils.put(CST_MODELINFO_CACHE, CACHE_MODELPARADEF_MAP, modelParaDefMap);
		}
		
		return modelParaDefMap;
	}

	/**
	 * 成本计算公式定义集合
	 * @return
	 */
	public static Map<String, Map<String, CstModelCalculateFormula>> getModelCalculateFormulaMap(){
		@SuppressWarnings("unchecked")
		Map<String, Map<String, CstModelCalculateFormula>> modelCalculateFormulaMap = (Map<String, Map<String, CstModelCalculateFormula>>)EhCacheUtils.get(CST_MODELINFO_CACHE, CACHE_MODELCALFORMULA_MAP);
		if (modelCalculateFormulaMap == null){
			modelCalculateFormulaMap = Maps.newHashMap();
			for (CstModelCalculateFormula cmcf : cstModelCalculateFormulaDao.findAllList(new CstModelCalculateFormula())){
				if(!"A0".equals(cmcf.getStatus())) continue;
				Map<String, CstModelCalculateFormula> pmrMap = modelCalculateFormulaMap.get(cmcf.getModuleId());
				if(pmrMap == null) {
					pmrMap = new LinkedHashMap<String, CstModelCalculateFormula>();
					modelCalculateFormulaMap.put(cmcf.getModuleId(), pmrMap);
				}
				modelCalculateFormulaMap.get(cmcf.getModuleId()).put(cmcf.getMeasureId(), cmcf);
			}
			EhCacheUtils.put(CST_MODELINFO_CACHE, CACHE_MODELCALFORMULA_MAP, modelCalculateFormulaMap);
		}
		
		return modelCalculateFormulaMap;
	}
	
	/**
	 * ******************************************主数据缓存**************************************************************
	 */

	// 资源主数据-resourceBaseInfo
	private static CstResourceBaseInfoDao cstResourceBaseInfoDao = SpringContextHolder.getBean(CstResourceBaseInfoDao.class);
	private static CstBaseResourceCaseDao cstBaseResourceCaseDao = SpringContextHolder.getBean(CstBaseResourceCaseDao.class);
	private static CstBaseCaseHourDao cstBaseCaseHourDao = SpringContextHolder.getBean(CstBaseCaseHourDao.class);
	private static CstBaseCaseLineoneDao cstBaseCaseLineoneDao = SpringContextHolder.getBean(CstBaseCaseLineoneDao.class);
	private static CstBaseBackFaultParaDao cstBaseBackFaultParaDao = SpringContextHolder.getBean(CstBaseBackFaultParaDao.class);
	private static CstBaseBackCaseParaDao cstBaseBackCaseParaDao = SpringContextHolder.getBean(CstBaseBackCaseParaDao.class);
	private static CstBaseBackPackParaDao cstBaseBackPackParaDao = SpringContextHolder.getBean(CstBaseBackPackParaDao.class);
	private static CstDictParaDao cstDictParaDao = SpringContextHolder.getBean(CstDictParaDao.class);
	//人工
	private static CstManFailureCaseHourDao cstManFailureCaseHourDao = SpringContextHolder.getBean(CstManFailureCaseHourDao.class);
	private static CstManFailureSlaParaDao cstManFailureSlaParaDao = SpringContextHolder.getBean(CstManFailureSlaParaDao.class);
	private static CstManFailureDegreeDao cstManFailureDegreeDao = SpringContextHolder.getBean(CstManFailureDegreeDao.class);
	private static CstManFailureManRateDao cstManFailureManRateDao = SpringContextHolder.getBean(CstManFailureManRateDao.class);
	private static CstManProdParaDao cstManProdParaDao = SpringContextHolder.getBean(CstManProdParaDao.class);
	private static CstManCityParaDao cstManCityParaDao = SpringContextHolder.getBean(CstManCityParaDao.class);
	private static CstManStandbyParaDao cstManStandbyParaDao = SpringContextHolder.getBean(CstManStandbyParaDao.class);
	//备件
	private static CstPartsFailureRateCostDao cstPartsFailureRateCostDao = SpringContextHolder.getBean(CstPartsFailureRateCostDao.class);
	private static CstPartsWeightAmountDao cstPartsWeightAmountDao = SpringContextHolder.getBean(CstPartsWeightAmountDao.class);
	private static CstPartsEventFailureParaDao cstPartsEventFailureParaDao = SpringContextHolder.getBean(CstPartsEventFailureParaDao.class);
	private static CstPartsFillRateDao cstPartsFillRateDao = SpringContextHolder.getBean(CstPartsFillRateDao.class);
	private static CstPartsPrjStoreParaDao cstPartsPrjStoreParaDao = SpringContextHolder.getBean(CstPartsPrjStoreParaDao.class);
	private static CstPartsTransportCostDao cstPartsTransportCostDao = SpringContextHolder.getBean(CstPartsTransportCostDao.class);
	private static CstPartsSlaCostRateDao cstPartsSlaCostRateDao = SpringContextHolder.getBean(CstPartsSlaCostRateDao.class);
	private static CstPartsEquipTypeRateDao cstPartsEquipTypeRateDao = SpringContextHolder.getBean(CstPartsEquipTypeRateDao.class);
	private static CstPartsRiskCostParaDao cstPartsRiskCostParaDao = SpringContextHolder.getBean(CstPartsRiskCostParaDao.class);
	private static CstPartsCooperModelDetailDao cstPartsCooperModelDetailDao = SpringContextHolder.getBean(CstPartsCooperModelDetailDao.class);
	private static CstPartsCooperToOnwerDao cstPartsCooperToOnwerDao = SpringContextHolder.getBean(CstPartsCooperToOnwerDao.class);
	private static CstPartsCooperCostDao cstPartsCooperCostDao = SpringContextHolder.getBean(CstPartsCooperCostDao.class);
	
	//巡检
	private static CstCheckWorkHourDao cstCheckWorkHourDao = SpringContextHolder.getBean(CstCheckWorkHourDao.class);
	private static CstCheckSlaParaDao cstCheckSlaParaDao = SpringContextHolder.getBean(CstCheckSlaParaDao.class);
	private static CstCheckResmgrHourDao cstCheckResmgrHourDao = SpringContextHolder.getBean(CstCheckResmgrHourDao.class);
	private static CstCheckDistanceStepHourDao cstCheckDistanceStepHourDao = SpringContextHolder.getBean(CstCheckDistanceStepHourDao.class);
	private static CstCheckDistanceUnitHourDao cstCheckDistanceUnitHourDao = SpringContextHolder.getBean(CstCheckDistanceUnitHourDao.class);
	private static CstCheckFirstcheckParaDao cstCheckFirstcheckParaDao = SpringContextHolder.getBean(CstCheckFirstcheckParaDao.class);
	private static CstCheckResstatSystemRelDao cstCheckResstatSystemRelDao = SpringContextHolder.getBean(CstCheckResstatSystemRelDao.class);
	//项目管理
	private static CstManManageStepRuleDao cstManManageStepRuleDao = SpringContextHolder.getBean(CstManManageStepRuleDao.class);
	private static CstManManageDegreeParaDao cstManManageDegreeParaDao = SpringContextHolder.getBean(CstManManageDegreeParaDao.class);
	// 系统软件包
	private static CstSoftPackDegreeParaDao cstSoftPackDegreeParaDao = SpringContextHolder.getBean(CstSoftPackDegreeParaDao.class);
	private static CstSoftPackStepRuleDao cstSoftPackStepRuleDao = SpringContextHolder.getBean(CstSoftPackStepRuleDao.class);
	// 预付费非硬件
	private static CstManNotDeviceParaDao cstManNotDeviceParaDao = SpringContextHolder.getBean(CstManNotDeviceParaDao.class);
	// 单次、先行支持服务单价
	private static CstManCaseSupportPriceDao cstManCaseSupportPriceDao = SpringContextHolder.getBean(CstManCaseSupportPriceDao.class);
	// 单次、先行支持标准工时系数
	private static CstCaseStandHourScaleDao cstCaseStandHourScaleDao = SpringContextHolder.getBean(CstCaseStandHourScaleDao.class);
	
	/**
	 * 资源主数据缓存
	 */
	public static String CST_RESOURCE_BASE_INFO_CACHE = "cstResourceBaseInfoCache";
	/**
	 * 添加资源主数据需要自动获取参数缓存
	 */
	public static String CST_RESOURCE_BASE_PARA_CACHE = "cstResourceBaseParaCache";
	/**
	 * 故障CASE处理工时定义缓存
	 */
	public static String CST_MAN_FAILURE_CASE_HOUR_CACHE = "cstManFailureCaseHourCache";
	/**
	 * 故障级别配比定义缓存
	 */
	public static String CST_MAN_FAILURE_SLA_PARA_CACHE = "cstManFailureSlaParaCache";
	/**
	 * 备件故障率与采购成本定义缓存
	 */
	public static String CST_PARTS_FAILURE_RATE_COST_CACHE = "cstPartsFailureRateCostCache";
	/**
	 * 备件事件故障参数定义缓存
	 */
	public static String CST_PARTS_EVENT_FAILURE_PARA_CACHE = "cstPartsEventFailureParaCache";
	/**
	 * 备件加权平均在保量定义缓存
	 */
	public static String CST_PARTS_WEIGHT_AMOUNT_CACHE = "cstPartsWeightAmountCache";
	/**
	 * 备件合作设备清单定义缓存
	 */
	public static String CST_PARTS_COOPER_MODELDETAIL_CACHE = "cstPartsCooperModelDetailCache";
	/**
	 * 备件合作转自有设备清单定义缓存
	 */
	public static String CST_PARTS_COOPER_TO_ONWER_CACHE = "cstPartsCooperToOnwerCache";
	/**
	 * 备件合作设备成本定义缓存
	 */
	public static String CST_PARTS_COOPER_COST_CACHE = "cstPartsCooperCostCache";
	/**
	 * 巡检工时定义缓存
	 */
	public static String CST_CHECK_WORK_HOUR_CACHE = "cstCheckWorkHourCache";
	/**
	 * 巡检级别配比定义缓存
	 */
	public static String CST_CHECK_SLA_PARA_CACHE = "cstCheckSlaParaCache";
	/**
	 * 主数据其他数据字典缓存
	 */
	public static String CST_BASE_DATA_DICT_CACHE = "cstBaseDateDictCache";

	/**
	 * 初始成本模型配置数据
	 */
	public static void initCstCfgParaCache(){
		getCstResourceBaseInfoMap();
		getCstManFailureCaseHourMap();
		getCstManFailureSlaParaMap();
		getCstManFailureDegreeMap();		
		getCstManFailureManRateMap();		
		getCstManProdParaMap();		
		getCstManCityParaMap();		
		getCstManStandbyParaMap();		
		getCstPartsFailureRateAndCostMap();		
		getCstPartsWeightAmountMap();		
		getCstPartsEventFailureParaMap();		
		getCstPartsFillRateMap();		
		getCstPartsPrjStoreParaMap();		
		getCstPartsTransportCostMap();			
		getCstPartsSlaCostRateMap();			
		getCstPartsEquipTypeRateMap();
		getCstPartsRiskCostParaMap();
		getCstPartsRiskCalParaMap();
		getCstPartsCooperModelDetailMap();
	    getCstPartsCooperToOnwerMap();
	    getCstPartsCooperCostMap();
		getCstDictParaMap();				
		getCstCheckWorkHourMap();				
		getCstCheckSlaParaMap();				
		getCstCheckResmgrHourMap();				
		getCstCheckDistanceStepHourMap();				
		getCstCheckDistanceUnitHourMap();				
		getCstCheckFirstcheckParaMap();				
		getCstCheckResstatSystemRelMap();						
		getCstManLevelRoleRelMap();								
		getCstManLevelRoleRelNewMap();					
		getCstManManageStepRuleMap();						
		getCstManManageDegreeParaMap();				
		getCstSoftPackDegreeParaMap();						
		getCstSoftPackStepRuleMap();				
		getCstManNotDeviceParaMap();							
		getCstResourceBaseInfoTestMap();				
		getCstManCityParaTestMap();
		
	}
	
	/**
	 * 刷新成本模型配置数据
	 */
	public static void freshCstCfgParaCache(){
		EhCacheUtils.remove(CacheDataUtils.CST_MAN_FAILURE_CASE_HOUR_CACHE, "dataMap");
		EhCacheUtils.remove(CacheDataUtils.CST_MAN_FAILURE_SLA_PARA_CACHE, "dataMap");
		EhCacheUtils.remove(CacheDataUtils.CST_CHECK_WORK_HOUR_CACHE, "dataMap");
		EhCacheUtils.remove(CacheDataUtils.CST_CHECK_SLA_PARA_CACHE, "dataMap");
		EhCacheUtils.remove(CacheDataUtils.CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_EQUIPTYPE_RATE);
		
	}
	
	/**
	 * 资源主数据集合
	 * @return
	 */
	public static Map<String, CstResourceBaseInfo> getCstResourceBaseInfoMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstResourceBaseInfo> crbiMap = (Map<String, CstResourceBaseInfo>)EhCacheUtils.get(CST_RESOURCE_BASE_INFO_CACHE, "dataMap");
		if (crbiMap == null){
			crbiMap = Maps.newHashMap();
			for (CstResourceBaseInfo crbi : cstResourceBaseInfoDao.findAllList(new CstResourceBaseInfo())){
				crbiMap.put(crbi.getResourceId(), crbi);
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_INFO_CACHE, "dataMap", crbiMap);
		}
		
		return crbiMap;
	}
	
	/**
	 * 故障CASE处理工时定义集合
	 * @return
	 */
	public static Map<String, CstManFailureCaseHour> getCstManFailureCaseHourMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManFailureCaseHour> cmfchMap = (Map<String, CstManFailureCaseHour>)EhCacheUtils.get(CST_MAN_FAILURE_CASE_HOUR_CACHE, "dataMap");
		if (cmfchMap == null){
			cmfchMap = Maps.newHashMap();
			for (CstManFailureCaseHour cmfch : cstManFailureCaseHourDao.findAllList(new CstManFailureCaseHour())){
				if(!"A0".equals(cmfch.getStatus())) continue;
				cmfchMap.put(cmfch.getResourceId(), cmfch);
			}
			EhCacheUtils.put(CST_MAN_FAILURE_CASE_HOUR_CACHE, "dataMap", cmfchMap);
		}
		
		return cmfchMap;
	}
	
	/**
	 * 故障级别配比定义集合
	 * @return
	 */
	public static Map<String, CstManFailureSlaPara> getCstManFailureSlaParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManFailureSlaPara> cmfspMap = (Map<String, CstManFailureSlaPara>)EhCacheUtils.get(CST_MAN_FAILURE_SLA_PARA_CACHE, "dataMap");
		if (cmfspMap == null){
			cmfspMap = Maps.newHashMap();
			for (CstManFailureSlaPara cmfsp : cstManFailureSlaParaDao.findAllList(new CstManFailureSlaPara())){
				if(!"A0".equals(cmfsp.getStatus())) continue;
				cmfspMap.put(cmfsp.getResourceId()+cmfsp.getSlaId(), cmfsp);
			}
			EhCacheUtils.put(CST_MAN_FAILURE_SLA_PARA_CACHE, "dataMap", cmfspMap);
		}
		
		return cmfspMap;
	}
	
	/**
	 * 故障饱和度定义集合
	 * @return
	 */
	public static Map<String, CstManFailureDegree> getCstManFailureDegreeMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManFailureDegree> cmfdMap = (Map<String, CstManFailureDegree>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_DEGREE);
		if (cmfdMap == null){
			cmfdMap = Maps.newHashMap();
			for (CstManFailureDegree cmfd : cstManFailureDegreeDao.findAllList(new CstManFailureDegree())){
				if(!"A0".equals(cmfd.getStatus())) continue;
				cmfdMap.put(cmfd.getSlaId(), cmfd);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_DEGREE, cmfdMap);
		}
		
		return cmfdMap;
	}
	
	/**
	 * 故障人工费率定义集合
	 * @return
	 */
	public static Map<String, CstManFailureManRate> getCstManFailureManRateMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManFailureManRate> cmfmrMap = (Map<String, CstManFailureManRate>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_MAN_RATE);
		if (cmfmrMap == null){
			cmfmrMap = Maps.newHashMap();
			for (CstManFailureManRate cmfmr : cstManFailureManRateDao.findAllList(new CstManFailureManRate())){
				if(!"A0".equals(cmfmr.getStatus())) continue;
				cmfmrMap.put(cmfmr.getDeliveryRole()+cmfmr.getIsResident(), cmfmr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_FAILURE_MAN_RATE, cmfmrMap);
		}
		
		return cmfmrMap;
	}
	
	/**
	 * 服务产品系数定义集合
	 * @return
	 */
	public static Map<String, CstManProdPara> getCstManProdParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManProdPara> cmppMap = (Map<String, CstManProdPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_PROD_PARA);
		if (cmppMap == null){
			cmppMap = Maps.newHashMap();
			for (CstManProdPara cmpp : cstManProdParaDao.findAllList(new CstManProdPara())){
				if(!"A0".equals(cmpp.getStatus())) continue;
				cmppMap.put(cmpp.getProdId(), cmpp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_PROD_PARA, cmppMap);
		}
		
		return cmppMap;
	}
	
	/**
	 * 地域系数定义集合
	 * @return
	 */
	public static Map<String, CstManCityPara> getCstManCityParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManCityPara> cmcpMap = (Map<String, CstManCityPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_CITY_PARA);
		if (cmcpMap == null){
			cmcpMap = Maps.newHashMap();
			for (CstManCityPara cmpp : cstManCityParaDao.findAllList(new CstManCityPara())){
				if(!"A0".equals(cmpp.getStatus())) continue;
				cmcpMap.put(cmpp.getCityId(), cmpp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_CITY_PARA, cmcpMap);
		}
		
		return cmcpMap;
	}
	
	/**
	 * 非工作时间比重定义集合
	 * @return
	 */
	public static Map<String, CstManStandbyPara> getCstManStandbyParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManStandbyPara> cmspMap = (Map<String, CstManStandbyPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_STANDBY_PARA);
		if (cmspMap == null){
			cmspMap = Maps.newHashMap();
			for (CstManStandbyPara cmsp : cstManStandbyParaDao.findAllList(new CstManStandbyPara())){
				if(!"A0".equals(cmsp.getStatus())) continue;
				cmspMap.put(cmsp.getProdId()+cmsp.getDeliveryRole(), cmsp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_STANDBY_PARA, cmspMap);
		}
		
		return cmspMap;
	}
	

	/**
	 * 备件故障率与采购成本定义集合
	 * @return
	 */
	public static Map<String, List<CstPartsFailureRateCost>> getCstPartsFailureRateAndCostMap(){
		@SuppressWarnings("unchecked")
		Map<String, List<CstPartsFailureRateCost>> cpfracMap = (Map<String, List<CstPartsFailureRateCost>>)EhCacheUtils.get(CST_PARTS_FAILURE_RATE_COST_CACHE, "dataMap");
		if (cpfracMap == null){
			cpfracMap = Maps.newHashMap();
			for (CstPartsFailureRateCost cpfrac : cstPartsFailureRateCostDao.findAllList(new CstPartsFailureRateCost())){
				if(!"A0".equals(cpfrac.getStatus())) continue;
				List<CstPartsFailureRateCost> cofracList = cpfracMap.get(cpfrac.getResourceId());
				if(cofracList == null) {
					cpfracMap.put(cpfrac.getResourceId(), Lists.newArrayList(cpfrac));
				} else {
					cpfracMap.get(cpfrac.getResourceId()).add(cpfrac);
				}
			}
			EhCacheUtils.put(CST_PARTS_FAILURE_RATE_COST_CACHE, "dataMap", cpfracMap);
		}
		
		return cpfracMap;
	}
	
	/**
	 * 备件加权平均在保量定义集合
	 * @return
	 */
	public static Map<String, CstPartsWeightAmount> getCstPartsWeightAmountMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsWeightAmount> cpwaMap = (Map<String, CstPartsWeightAmount>)EhCacheUtils.get(CST_PARTS_WEIGHT_AMOUNT_CACHE, "dataMap");
		if (cpwaMap == null){
			cpwaMap = Maps.newHashMap();
			for (CstPartsWeightAmount cpwa : cstPartsWeightAmountDao.findAllList(new CstPartsWeightAmount())){
				if(!"A0".equals(cpwa.getStatus())) continue;
				cpwaMap.put(cpwa.getResourceId(), cpwa);
			}
			EhCacheUtils.put(CST_PARTS_WEIGHT_AMOUNT_CACHE, "dataMap", cpwaMap);
		}
		
		return cpwaMap;
	}
	
	/**
	 * 备件事件故障参数定义集合
	 * @return
	 */
	public static Map<String, CstPartsEventFailurePara> getCstPartsEventFailureParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsEventFailurePara> cpefpMap = (Map<String, CstPartsEventFailurePara>)EhCacheUtils.get(CST_PARTS_EVENT_FAILURE_PARA_CACHE, "dataMap");
		if (cpefpMap == null){
			cpefpMap = Maps.newHashMap();
			for (CstPartsEventFailurePara cpefp : cstPartsEventFailureParaDao.findAllList(new CstPartsEventFailurePara())){
				if(!"A0".equals(cpefp.getStatus())) continue;
				cpefpMap.put(cpefp.getResourceId(), cpefp);
			}
			EhCacheUtils.put(CST_PARTS_EVENT_FAILURE_PARA_CACHE, "dataMap", cpefpMap);
		}
		
		return cpefpMap;
	}
	
	/**
	 * 备件满足率定义集合
	 * @return
	 */
	public static Map<String, CstPartsFillRate> getCstPartsFillRateMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsFillRate> cpfrMap = (Map<String, CstPartsFillRate>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_FILL_RATE);
		if (cpfrMap == null){
			cpfrMap = Maps.newHashMap();
			for (CstPartsFillRate cpfr : cstPartsFillRateDao.findAllList(new CstPartsFillRate())){
				if(!"A0".equals(cpfr.getStatus())) continue;
				cpfrMap.put(cpfr.getProvince(), cpfr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_FILL_RATE, cpfrMap);
		}
		
		return cpfrMap;
	}
	
	/**
	 * 备件项目储备成本系数定义集合
	 * @return
	 */
	public static Map<String, CstPartsPrjStorePara> getCstPartsPrjStoreParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsPrjStorePara> cppspMap = (Map<String, CstPartsPrjStorePara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_PRJ_STORE_PARA);
		if (cppspMap == null){
			cppspMap = Maps.newHashMap();
			for (CstPartsPrjStorePara cppsp : cstPartsPrjStoreParaDao.findAllList(new CstPartsPrjStorePara())){
				if(!"A0".equals(cppsp.getStatus())) continue;
				cppspMap.put(cppsp.getProvince(), cppsp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_PRJ_STORE_PARA, cppspMap);
		}
		
		return cppspMap;
	}
	
	/**
	 * 备件发货运输平均成本定义集合
	 * @return
	 */
	public static Map<String, CstPartsTransportCost> getCstPartsTransportCostMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsTransportCost> cptcMap = (Map<String, CstPartsTransportCost>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_TRANSPORT_COST);
		if (cptcMap == null){
			cptcMap = Maps.newHashMap();
			for (CstPartsTransportCost cptc : cstPartsTransportCostDao.findAllList(new CstPartsTransportCost())){
				if(!"A0".equals(cptc.getStatus())) continue;
				cptcMap.put(cptc.getSlaLevel()+cptc.getCity(), cptc);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_TRANSPORT_COST, cptcMap);
		}
		
		return cptcMap;
	}
	
	/**
	 * 故障成本SLA采购成本系数集合
	 * @return
	 */
	public static Map<String, CstPartsSlaCostRate> getCstPartsSlaCostRateMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsSlaCostRate> cpscrMap = (Map<String, CstPartsSlaCostRate>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_SLA_COST_RATE);
		if (cpscrMap == null){
			cpscrMap = Maps.newHashMap();
			for (CstPartsSlaCostRate cpscr : cstPartsSlaCostRateDao.findAllList(new CstPartsSlaCostRate())){
				if(!"A0".equals(cpscr.getStatus())) continue;
				cpscrMap.put(cpscr.getSlaId(), cpscr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_SLA_COST_RATE, cpscrMap);
		}
		
		return cpscrMap;
	}
	
	/**
	 * 备件各技术方向人员成本系数集合
	 * @return
	 */
	public static Map<String, CstPartsEquipTypeRate> getCstPartsEquipTypeRateMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsEquipTypeRate> cpetrMap = (Map<String, CstPartsEquipTypeRate>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_EQUIPTYPE_RATE);
		if (cpetrMap == null){
			cpetrMap = Maps.newHashMap();
			for (CstPartsEquipTypeRate cpetr : cstPartsEquipTypeRateDao.findAllList(new CstPartsEquipTypeRate())){
				if(!"A0".equals(cpetr.getStatus())) continue;
				cpetrMap.put(cpetr.getEquiptypeId(), cpetr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_EQUIPTYPE_RATE, cpetrMap);
		}
		
		return cpetrMap;
	}
	
	/**
	 * 备件型号组维度风险准备金系数集合
	 * @return
	 */
	public static Map<String, CstPartsRiskCostPara> getCstPartsRiskCostParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstPartsRiskCostPara> cprcpMap = (Map<String, CstPartsRiskCostPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_RISK_COST_PARA);
		if (cprcpMap == null){
			cprcpMap = Maps.newHashMap();
			for (CstPartsRiskCostPara cpetr : cstPartsRiskCostParaDao.findAllList(new CstPartsRiskCostPara())){
				if(!"A0".equals(cpetr.getStatus())) continue;
				cprcpMap.put(cpetr.getModelgroupId(), cpetr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_RISK_COST_PARA, cprcpMap);
		}
		
		return cprcpMap;
	}
	
	/**
	 * 备件型号组维度风险准备金系数集合
	 * ∑(风险成本系数【风险成本系数<1】)*历史在保设备数量（风险成本系数【风险成本系数<1】）
	 * @return
	 */
	public static Map<String, String> getCstPartsRiskCalParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, String> cprcpMap = (Map<String, String>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_RISK_CAL_PARA);
		// 风险成本系数等于1的“历史在保设备数量”之和
		BigDecimal historicalAmountForOne = BigDecimal.ZERO;
		// ∑(风险成本系数【风险成本系数<1】)*历史在保设备数量（风险成本系数【风险成本系数<1】）
		BigDecimal remaindPara = BigDecimal.ZERO;
		if (cprcpMap == null){
			cprcpMap = Maps.newHashMap();
			for (CstPartsRiskCostPara cprcp : cstPartsRiskCostParaDao.findAllList(new CstPartsRiskCostPara())){
				if(!"A0".equals(cprcp.getStatus())) continue;
				
				if(cprcp.getRiskCostScale() < 1) {
					remaindPara = BigDecimal.valueOf(cprcp.getRiskCostScale()).multiply(BigDecimal.valueOf(cprcp.getHistoricalAmount())).add(remaindPara);
				} else {
					historicalAmountForOne = historicalAmountForOne.add(BigDecimal.valueOf(cprcp.getHistoricalAmount()));
				}
			}
			cprcpMap.put("historicalAmountForOne", historicalAmountForOne+"");
			cprcpMap.put("remaindPara", remaindPara+"");
			
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_PARTS_RISK_CAL_PARA, cprcpMap);
		}
		
		return cprcpMap;
	}
	
	/**
	 * 备件合作设备清单系数集合
	 * @return
	 */
	public static Map<String, Map<String, CstPartsCooperModelDetail>> getCstPartsCooperModelDetailMap(){
		@SuppressWarnings("unchecked")
		Map<String, Map<String, CstPartsCooperModelDetail>> cpcmdMap = (Map<String, Map<String, CstPartsCooperModelDetail>>)EhCacheUtils.get(CST_PARTS_COOPER_MODELDETAIL_CACHE, "dataMap");
		if (cpcmdMap == null){
			cpcmdMap = Maps.newHashMap();
			for (CstPartsCooperModelDetail cpcmd : cstPartsCooperModelDetailDao.findAllList(new CstPartsCooperModelDetail())){
				if(!"A0".equals(cpcmd.getStatus())) continue;
				if(cpcmdMap.get(cpcmd.getResourceId()) == null) {
					Map<String, CstPartsCooperModelDetail> cpcmds = Maps.newHashMap();
					cpcmdMap.put(cpcmd.getResourceId(), cpcmds);
				}
				cpcmdMap.get(cpcmd.getResourceId()).put(cpcmd.getDateId(), cpcmd);
			}
			EhCacheUtils.put(CST_PARTS_COOPER_MODELDETAIL_CACHE, "dataMap", cpcmdMap);
		}
		
		return cpcmdMap;
	}
	
	/**
	 * 备件合作转自有设备清单系数集合
	 * @return
	 */
	public static Map<String, Map<String, CstPartsCooperToOnwer>> getCstPartsCooperToOnwerMap(){
		@SuppressWarnings("unchecked")
		Map<String, Map<String, CstPartsCooperToOnwer>> cpctoMap = (Map<String, Map<String, CstPartsCooperToOnwer>>)EhCacheUtils.get(CST_PARTS_COOPER_TO_ONWER_CACHE, "dataMap");
		if (cpctoMap == null){
			cpctoMap = Maps.newHashMap();
			for (CstPartsCooperToOnwer cpcmd : cstPartsCooperToOnwerDao.findAllList(new CstPartsCooperToOnwer())){
				if(!"A0".equals(cpcmd.getStatus())) continue;
				if(cpctoMap.get(cpcmd.getResourceId()) == null) {
					Map<String, CstPartsCooperToOnwer> cpcmds = Maps.newHashMap();
					cpctoMap.put(cpcmd.getResourceId(), cpcmds);
				}
				cpctoMap.get(cpcmd.getResourceId()).put(cpcmd.getDateId(), cpcmd);
			}
			EhCacheUtils.put(CST_PARTS_COOPER_TO_ONWER_CACHE, "dataMap", cpctoMap);
		}
		
		return cpctoMap;
	}
	
	/**
	 * 备件合作设备成本系数集合
	 * @return
	 */
	public static Map<String, Map<String, CstPartsCooperCost>> getCstPartsCooperCostMap(){
		@SuppressWarnings("unchecked")
		Map<String, Map<String, CstPartsCooperCost>> cpccMap = (Map<String, Map<String, CstPartsCooperCost>>)EhCacheUtils.get(CST_PARTS_COOPER_COST_CACHE, "dataMap");
		if (cpccMap == null){
			cpccMap = Maps.newHashMap();
			for (CstPartsCooperCost cpcc : cstPartsCooperCostDao.findAllList(new CstPartsCooperCost())){
				if(!"A0".equals(cpcc.getStatus())) continue;
				if(cpccMap.get(cpcc.getDateId()) == null) {
					Map<String, CstPartsCooperCost> cpccs = Maps.newHashMap();
					cpccMap.put(cpcc.getDateId(), cpccs);
				}
				cpccMap.get(cpcc.getDateId()).put(cpcc.getMfrId().toUpperCase()+"_"+cpcc.getEquiptypeId().toUpperCase()+"_"+cpcc.getProvince(), cpcc);
			}
			EhCacheUtils.put(CST_PARTS_COOPER_COST_CACHE, "dataMap", cpccMap);
		}
		
		return cpccMap;
	}
	
	/**
	 * 备件参数字典定义集合
	 * @return
	 */
	public static Map<String, CstDictPara> getCstDictParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstDictPara> cdpMap = (Map<String, CstDictPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_DICT_PARA);
		if (cdpMap == null){
			cdpMap = Maps.newHashMap();
			for (CstDictPara cdp : cstDictParaDao.findAllList(new CstDictPara())){
				if(!"A0".equals(cdp.getStatus())) continue;
				cdpMap.put(cdp.getParaId(), cdp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_DICT_PARA, cdpMap);
		}
		
		return cdpMap;
	}
	
	/**
	 * 巡检工时定义集合
	 * @return
	 */
	public static Map<String, CstCheckWorkHour> getCstCheckWorkHourMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckWorkHour> ccwhMap = (Map<String, CstCheckWorkHour>)EhCacheUtils.get(CST_CHECK_WORK_HOUR_CACHE, "dataMap");
		if (ccwhMap == null){
			ccwhMap = Maps.newHashMap();
			for (CstCheckWorkHour ccwh : cstCheckWorkHourDao.findAllList(new CstCheckWorkHour())){
				if(!"A0".equals(ccwh.getStatus())) continue;
				ccwhMap.put(ccwh.getResModelId(), ccwh);
			}
			EhCacheUtils.put(CST_CHECK_WORK_HOUR_CACHE, "dataMap", ccwhMap);
		}
		
		return ccwhMap;
	}
	
	/**
	 * 巡检级别配比定义集合
	 * @return
	 */
	public static Map<String, CstCheckSlaPara> getCstCheckSlaParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckSlaPara> ccspMap = (Map<String, CstCheckSlaPara>)EhCacheUtils.get(CST_CHECK_SLA_PARA_CACHE, "dataMap");
		if (ccspMap == null){
			ccspMap = Maps.newHashMap();
			for (CstCheckSlaPara ccsp : cstCheckSlaParaDao.findAllList(new CstCheckSlaPara())){
				if(!"A0".equals(ccsp.getStatus())) continue;
				ccspMap.put(ccsp.getResModelId()+ccsp.getSlaName(), ccsp);
			}
			EhCacheUtils.put(CST_CHECK_SLA_PARA_CACHE, "dataMap", ccspMap);
		}
		
		return ccspMap;
	}
	
	/**
	 * 巡检-资源岗巡检安排工时集合
	 * @return
	 */
	public static Map<String, CstCheckResmgrHour> getCstCheckResmgrHourMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckResmgrHour> cprhMap = (Map<String, CstCheckResmgrHour>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESMGR_HOUR);
		if (cprhMap == null){
			cprhMap = Maps.newHashMap();
			for (CstCheckResmgrHour cprh : cstCheckResmgrHourDao.findAllList(new CstCheckResmgrHour())){
				if(!"A0".equals(cprh.getStatus())) continue;
				cprhMap.put(cprh.getId(), cprh);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESMGR_HOUR, cprhMap);
		}
		
		return cprhMap;
	}
	
	/**
	 * 巡检-路程工时阶梯系数集合
	 * @return
	 */
	public static Map<Integer, List<CstCheckDistanceStepHour>> getCstCheckDistanceStepHourMap(){
		@SuppressWarnings("unchecked")
		Map<Integer, List<CstCheckDistanceStepHour>> ccdshMap = (Map<Integer, List<CstCheckDistanceStepHour>>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_STEP_HOUR);
		if (ccdshMap == null){
			ccdshMap = Maps.newHashMap();
			for (CstCheckDistanceStepHour ccdsh : cstCheckDistanceStepHourDao.findAllList(new CstCheckDistanceStepHour())){
				if(!"A0".equals(ccdsh.getStatus())) continue;
				List<CstCheckDistanceStepHour> ccdshList = ccdshMap.get(ccdsh.getTypeResnum());
				if(ccdshList == null) {
					ccdshMap.put(ccdsh.getTypeResnum(), Lists.newArrayList(ccdsh));
				} else {
					ccdshMap.get(ccdsh.getTypeResnum()).add(ccdsh);
				}
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_STEP_HOUR, ccdshMap);
		}
		
		return ccdshMap;
	}
	
	/**
	 * 巡检-单台路程工时集合
	 * @return
	 */
	public static Map<String, CstCheckDistanceUnitHour> getCstCheckDistanceUnitHourMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckDistanceUnitHour> ccduhMap = (Map<String, CstCheckDistanceUnitHour>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_UNIT_HOUR);
		if (ccduhMap == null){
			ccduhMap = Maps.newHashMap();
			for (CstCheckDistanceUnitHour ccduh : cstCheckDistanceUnitHourDao.findAllList(new CstCheckDistanceUnitHour())){
				if(!"A0".equals(ccduh.getStatus())) continue;
				ccduhMap.put(ccduh.getSlaName()+ccduh.getSystemId(), ccduh);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_DISTANCE_UNIT_HOUR, ccduhMap);
		}
		
		return ccduhMap;
	}
	
	/**
	 * 巡检-首次巡检系数集合
	 * @return
	 */
	public static Map<String, CstCheckFirstcheckPara> getCstCheckFirstcheckParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckFirstcheckPara> ccfpMap = (Map<String, CstCheckFirstcheckPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_FIRSTCHECK_PARA);
		if (ccfpMap == null){
			ccfpMap = Maps.newHashMap();
			for (CstCheckFirstcheckPara ccfp : cstCheckFirstcheckParaDao.findAllList(new CstCheckFirstcheckPara())){
				if(!"A0".equals(ccfp.getStatus())) continue;
				ccfpMap.put(ccfp.getSlaName(), ccfp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_FIRSTCHECK_PARA, ccfpMap);
		}
		
		return ccfpMap;
	}
	
	/**
	 * 巡检-资源计划分类对应设备大类关系集合
	 * @return
	 */
	public static Map<String, CstCheckResstatSystemRel> getCstCheckResstatSystemRelMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckResstatSystemRel> ccrsrMap = (Map<String, CstCheckResstatSystemRel>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESSTAT_SYSTEM_REL);
		if (ccrsrMap == null){
			ccrsrMap = Maps.newHashMap();
			for (CstCheckResstatSystemRel ccrsr : cstCheckResstatSystemRelDao.findAllList(new CstCheckResstatSystemRel())){
				if(!"A0".equals(ccrsr.getStatus())) continue;
				ccrsrMap.put(ccrsr.getResstattypeId(), ccrsr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_CHECK_RESSTAT_SYSTEM_REL, ccrsrMap);
		}
		
		return ccrsrMap;
	}
	
	/**
	 * 驻场工程师级别与人员角色对应关系集合-FY18
	 * @return
	 */
	public static Map<String, CstManLevelRoleRel> getCstManLevelRoleRelMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManLevelRoleRel> cmlrrMap = (Map<String, CstManLevelRoleRel>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MANLEVEL_ROLE_REL);
		if (cmlrrMap == null){
			cmlrrMap = Maps.newHashMap();
			//高级	一线4级
			CstManLevelRoleRel cmlrr2 = new CstManLevelRoleRel("A0", "一线4级", "A4", "MANCSTH14");
			//中级	一线3级
			CstManLevelRoleRel cmlrr3 = new CstManLevelRoleRel("A1", "一线3级", "A3", "MANCSTH13");
			//初级	一线2级
			CstManLevelRoleRel cmlrr4 = new CstManLevelRoleRel("A2", "一线2级", "A2", "MANCSTH12");

			cmlrrMap.put(cmlrr2.getManLevel(), cmlrr2);
			cmlrrMap.put(cmlrr3.getManLevel(), cmlrr3);
			cmlrrMap.put(cmlrr4.getManLevel(), cmlrr4);
			
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MANLEVEL_ROLE_REL, cmlrrMap);
		}
		
		return cmlrrMap;
	}
	
	/**
	 * 驻场工程师级别、资源计划分类与人员角色对应关系集合
	 * @return
	 */
	public static Map<String, CstManLevelRoleRel> getCstManLevelRoleRelNewMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManLevelRoleRel> cmlrrMap = (Map<String, CstManLevelRoleRel>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MANLEVEL_ROLE_REL_NEW);
		if (cmlrrMap == null){
			cmlrrMap = Maps.newHashMap();
			/**
			 *资源计划分类	  初级A2	  中级A1   高级A0	     专家AA
				存储	一线3级	一线4级	一线5级	一线6级
				网络	一线2级	一线3级	一线4级	一线6级
				PC服务器	一线2级	一线3级	一线4级	一线6级
				HP小型机	一线2级	一线3级	一线4级	一线6级
				IBM小型机	一线2级	一线3级	一线4级	一线6级
				硬件其它	一线2级	一线3级	一线4级	一线6级
				SUN小型机	一线2级	一线3级	一线4级	一线6级
				Oracle	一线3级	一线4级	一线5级	一线6级
				DB2	一线3级	一线4级	一线5级	一线6级
				中间件	一线3级	一线4级	一线5级	一线6级
				备份软件	一线3级	一线4级	一线5级	一线6级
				软件其它	一线3级	一线4级	一线5级	一线6级
                 
		                      分类	初级	中级	高级
					硬件	一线2级	一线3级	一线4级
					软件	-	一线4级	一线5级
					PM	-	PM4级	PM5级
			 */
			Map<String, String[]> levelRelMap = Maps.newHashMap();
			levelRelMap.put("一线2级", new String[]{"A2","MANCSTH12"});
			levelRelMap.put("一线3级", new String[]{"A3","MANCSTH13"});
			levelRelMap.put("一线4级", new String[]{"A4","MANCSTH14"});
			levelRelMap.put("一线5级", new String[]{"A5","MANCSTH15"});
			levelRelMap.put("一线6级", new String[]{"A6","MANCSTH16"});
			levelRelMap.put("PM4级", new String[]{"G4","MANCSTHG4"});
			levelRelMap.put("PM5级", new String[]{"G5","MANCSTHG5"});
			
			Map<String, String[]> relMap = Maps.newHashMap();
			relMap.put("存储", new String[]{"一线3级","一线4级","一线5级","一线6级"});
			relMap.put("网络", new String[]{"一线2级","一线3级","一线4级","一线6级"});
			relMap.put("PC服务器", new String[]{"一线2级","一线3级","一线4级","一线6级"});
			relMap.put("HP小型机", new String[]{"一线2级","一线3级","一线4级","一线6级"});
			relMap.put("IBM小型机", new String[]{"一线2级","一线3级","一线4级","一线6级"});
			relMap.put("硬件其它", new String[]{"一线2级","一线3级","一线4级","一线6级"});
			relMap.put("SUN小型机", new String[]{"一线2级","一线3级","一线4级","一线6级"});
			relMap.put("Oracle", new String[]{"一线3级","一线4级","一线5级","一线6级"});
			relMap.put("DB2", new String[]{"一线3级","一线4级","一线5级","一线6级"});
			relMap.put("中间件", new String[]{"一线3级","一线4级","一线5级","一线6级"});
			relMap.put("备份软件", new String[]{"一线3级","一线4级","一线5级","一线6级"});
			relMap.put("软件其它", new String[]{"一线3级","一线4级","一线5级","一线6级"});

			relMap.put("硬件", new String[]{"一线2级","一线3级","一线4级"});
			relMap.put("软件", new String[]{"","一线4级","一线5级"});
			relMap.put("PM", new String[]{"","PM4级","PM5级"});
			
			String[] levelStr = new String[]{"A2","A1","A0","AA"};
			
			for(String key : relMap.keySet()) {
				String[] relStr = relMap.get(key);
				for(int i=0; i<levelStr.length; i++) {
					// 初始格式： CstManLevelRoleRel("A2", "存储", "一线3级", "A3", "MANCSTH13");
					if(i < relStr.length && !"".equals(relStr[i])) {
						CstManLevelRoleRel cmlrr = new CstManLevelRoleRel(levelStr[i], key, relStr[i], levelRelMap.get(relStr[i])[0], levelRelMap.get(relStr[i])[1]);
						cmlrrMap.put(cmlrr.getManLevel()+cmlrr.getResstattypeName(), cmlrr);
					}
				}
			}
			
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MANLEVEL_ROLE_REL_NEW, cmlrrMap);
		}
		
		return cmlrrMap;
	}
	
	/**
	 * 项目管理-项目管理工作量阶梯配比表
	 * @return
	 */
	public static Map<String, List<CstManManageStepRule>> getCstManManageStepRuleMap(){
		@SuppressWarnings("unchecked")
		Map<String, List<CstManManageStepRule>> cmmsrMap = (Map<String, List<CstManManageStepRule>>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_MANAGE_STEP_RULE);
		if (cmmsrMap == null){
			cmmsrMap = Maps.newHashMap();
			for (CstManManageStepRule cmmsr : cstManManageStepRuleDao.findAllList(new CstManManageStepRule())){
				if(!"A0".equals(cmmsr.getStatus())) continue;
				if(cmmsrMap.get(cmmsr.getProdId()) == null) {
					cmmsrMap.put(cmmsr.getProdId(), Lists.newArrayList(cmmsr));
				} else {
					cmmsrMap.get(cmmsr.getProdId()).add(cmmsr);
				}
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_MANAGE_STEP_RULE, cmmsrMap);
		}
		
		return cmmsrMap;
	}
	
	/**
	 * 项目管理-项目管理配比及饱和度表
	 * @return
	 */
	public static Map<String, CstManManageDegreePara> getCstManManageDegreeParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManManageDegreePara> cmmdpMap = (Map<String, CstManManageDegreePara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_MANAGE_DEGREE_PARA);
		if (cmmdpMap == null){
			cmmdpMap = Maps.newHashMap();
			for (CstManManageDegreePara cmmdp : cstManManageDegreeParaDao.findAllList(new CstManManageDegreePara())){
				if(!"A0".equals(cmmdp.getStatus())) continue;
				cmmdpMap.put(cmmdp.getProdId()+cmmdp.getServiceLevel(), cmmdp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_MANAGE_DEGREE_PARA, cmmdpMap);
		}
		
		return cmmdpMap;
	}
	
	/**
	 * 软件包-系统软件服务资源配比表
	 * @return
	 */
	public static Map<String, CstSoftPackDegreePara> getCstSoftPackDegreeParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstSoftPackDegreePara> cspdpMap = (Map<String, CstSoftPackDegreePara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_SOFT_PACK_DEGREE_PARA);
		if (cspdpMap == null){
			cspdpMap = Maps.newHashMap();
			for (CstSoftPackDegreePara cspdp : cstSoftPackDegreeParaDao.findAllList(new CstSoftPackDegreePara())){
				if(!"A0".equals(cspdp.getStatus())) continue;
				cspdpMap.put(cspdp.getRuleId(), cspdp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_SOFT_PACK_DEGREE_PARA, cspdpMap);
		}
		
		return cspdpMap;
	}
	
	/**
	 * 软件包-系统软件服务规模阶梯配比表
	 * @return
	 */
	public static Map<String, CstSoftPackStepRule> getCstSoftPackStepRuleMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstSoftPackStepRule> cspsrMap = (Map<String, CstSoftPackStepRule>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_SOFT_PACK_STEP_RULE);
		if (cspsrMap == null){
			cspsrMap = Maps.newHashMap();
			for (CstSoftPackStepRule cspsr : cstSoftPackStepRuleDao.findAllList(new CstSoftPackStepRule())){
				if(!"A0".equals(cspsr.getStatus())) continue;
				cspsrMap.put(cspsr.getId(), cspsr);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_SOFT_PACK_STEP_RULE, cspsrMap);
		}
		
		return cspsrMap;
	}
	
	/**
	 * 预付费-非设备类系数表
	 * @return
	 */
	public static Map<String, CstManNotDevicePara> getCstManNotDeviceParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManNotDevicePara> cmndpMap = (Map<String, CstManNotDevicePara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_NOT_DEVICE_PARA);
		if (cmndpMap == null){
			cmndpMap = Maps.newHashMap();
			for (CstManNotDevicePara cmndp : cstManNotDeviceParaDao.findAllList(new CstManNotDevicePara())){
				if(!"A0".equals(cmndp.getStatus())) continue;
				cmndpMap.put(cmndp.getProdServiceId(), cmndp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_NOT_DEVICE_PARA, cmndpMap);
		}
		
		return cmndpMap;
	}
	
	/**
	 * 单次、先行支持服务单价表
	 * @return
	 */
	public static Map<String, CstManCaseSupportPrice> getCstManCaseSupportPriceMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManCaseSupportPrice> cmcspMap = (Map<String, CstManCaseSupportPrice>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_CASE_SUPPORT_PRICE);
		if (cmcspMap == null){
			cmcspMap = Maps.newHashMap();
			for (CstManCaseSupportPrice cmcsp : cstManCaseSupportPriceDao.findAllList(new CstManCaseSupportPrice())){
				if(!"A0".equals(cmcsp.getStatus())) continue;
				cmcspMap.put(cmcsp.getSupportId(), cmcsp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_MAN_CASE_SUPPORT_PRICE, cmcspMap);
		}
		
		return cmcspMap;
	}
	
	/**
	 * 单次、先行支持标准工时系数表
	 * @return
	 */
	public static Map<String, CstCaseStandHourScale> getCstCaseStandHourScaleMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCaseStandHourScale> ccshsMap = (Map<String, CstCaseStandHourScale>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, Constants.CST_CASE_STAND_HOUR_SCALE);
		if (ccshsMap == null){
			ccshsMap = Maps.newHashMap();
			for (CstCaseStandHourScale ccshs : cstCaseStandHourScaleDao.findAllList(new CstCaseStandHourScale())){
				if(!"A0".equals(ccshs.getStatus())) continue;
				ccshsMap.put(ccshs.getProdId()+ccshs.getPaytypeId(), ccshs);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, Constants.CST_CASE_STAND_HOUR_SCALE, ccshsMap);
		}
		
		return ccshsMap;
	}


	/**
	 * 资源主数据集合
	 * @return
	 */
	public static Map<String, CstResourceBaseInfo> getCstResourceBaseInfoTestMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstResourceBaseInfo> crbiMap = (Map<String, CstResourceBaseInfo>)EhCacheUtils.get("cstResourceBaseInfoCacheTest", "dataMap");
		if (crbiMap == null){
			crbiMap = Maps.newHashMap();
			for (CstResourceBaseInfo crbi : cstResourceBaseInfoDao.findAllList(new CstResourceBaseInfo())){
				crbiMap.put(crbi.getMfrName().toUpperCase()+crbi.getResourceName().toUpperCase(), crbi);
			}
			EhCacheUtils.put("cstResourceBaseInfoCacheTest", "dataMap", crbiMap);
		}
		
		return crbiMap;
	}

	/**
	 * 故障case样本集合
	 * @return
	 */
	public static Map<String, CstBaseResourceCase> getCstBaseResourceCaseMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstBaseResourceCase> cbrcMap = (Map<String, CstBaseResourceCase>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_RESOURCE_CASE);
		if (cbrcMap == null){
			Map<String, CstResourceBaseInfo> crbiMap = getCstResourceBaseInfoMap();
			cbrcMap = Maps.newHashMap();
			for (CstBaseResourceCase cbrc : cstBaseResourceCaseDao.findAllList(new CstBaseResourceCase())){
				// 分别按照 厂商+型号、 技术方向+型号组+厂商、 技术方向+厂商、 技术方向 、资源计划分类 归集case样本数量
				/**1. 厂商+型号 */
				cbrcMap.put(cbrc.getResourceId(), cbrc);
				CstResourceBaseInfo crbi = crbiMap.get(cbrc.getResourceId());
				/**2.技术方向+型号组+厂商 */
				if(cbrcMap.get("G_"+crbi.getModelGroupId()) == null) {
					cbrcMap.put("G_"+crbi.getModelGroupId(), new CstBaseResourceCase());
				}
				CstBaseResourceCase cbrc2 = cbrcMap.get("G_"+crbi.getModelGroupId());
				cbrc2.setCaseAmount(cbrc2.getCaseAmount()+cbrc.getCaseAmount());
				cbrc2.setYearSampleAmount(cbrc2.getYearSampleAmount()+cbrc.getYearSampleAmount());
				/**3. 技术方向+厂商 */
				if(cbrcMap.get(crbi.getEquipTypeId()+"_"+crbi.getMfrId()) == null) {
					cbrcMap.put(crbi.getEquipTypeId()+"_"+crbi.getMfrId(), new CstBaseResourceCase());
				}
				CstBaseResourceCase cbrc3 = cbrcMap.get(crbi.getEquipTypeId()+"_"+crbi.getMfrId());
				cbrc3.setCaseAmount(cbrc3.getCaseAmount()+cbrc.getCaseAmount());
				cbrc3.setYearSampleAmount(cbrc3.getYearSampleAmount()+cbrc.getYearSampleAmount());
				/**4. 技术方向 */
				if(cbrcMap.get("E_"+crbi.getEquipTypeId()) == null) {
					cbrcMap.put("E_"+crbi.getEquipTypeId(), new CstBaseResourceCase());
				}
				CstBaseResourceCase cbrc4 = cbrcMap.get("E_"+crbi.getEquipTypeId());
				cbrc4.setCaseAmount(cbrc4.getCaseAmount()+cbrc.getCaseAmount());
				cbrc4.setYearSampleAmount(cbrc4.getYearSampleAmount()+cbrc.getYearSampleAmount());
				/**5. 资源计划分类 */
				if(cbrcMap.get("R_"+crbi.getResstattypeId()) == null) {
					cbrcMap.put("R_"+crbi.getResstattypeId(), new CstBaseResourceCase());
				}
				CstBaseResourceCase cbrc5 = cbrcMap.get("R_"+crbi.getResstattypeId());
				cbrc5.setCaseAmount(cbrc5.getCaseAmount()+cbrc.getCaseAmount());
				cbrc5.setYearSampleAmount(cbrc5.getYearSampleAmount()+cbrc.getYearSampleAmount());
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_RESOURCE_CASE, cbrcMap);
		}
		
		return cbrcMap;
	}

	/**
	 * 故障case一线标准工时样本集合
	 * @return
	 */
	public static Map<String, CstBaseCaseHour> getCstBaseCaseHourMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstBaseCaseHour> cbchMap = (Map<String, CstBaseCaseHour>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CASE_HOUR);
		if (cbchMap == null){
			cbchMap = Maps.newHashMap();
			for (CstBaseCaseHour cbch : cstBaseCaseHourDao.findAllList(new CstBaseCaseHour())){
				// 分别按照技术方向+型号组+厂商、 技术方向+厂商、 技术方向   归集case故障率标准工时样本数量
				/**1. 厂商+技术方向+型号组 */
				cbchMap.put(cbch.getMfrName().toUpperCase()+"_"+cbch.getEquipTypeName().toUpperCase()+"_"+cbch.getModelgroupName().toUpperCase(), cbch);
				/**2.技术方向+厂商 */
				if(cbchMap.get(cbch.getMfrName().toUpperCase()+"_"+cbch.getEquipTypeName().toUpperCase()) == null) {
					cbchMap.put(cbch.getMfrName().toUpperCase()+"_"+cbch.getEquipTypeName().toUpperCase(), new CstBaseCaseHour());
				}
				CstBaseCaseHour cbch2 = cbchMap.get(cbch.getMfrName().toUpperCase()+"_"+cbch.getEquipTypeName().toUpperCase());
				cbch2.setSumLineoneCaseHour(cbch2.getSumLineoneCaseHour()+cbch.getLineoneCaseHour());
				cbch2.setSumRemoteCaseHour(cbch2.getSumRemoteCaseHour()+cbch.getRemoteCaseHour());
				cbch2.setCalcAmount(cbch2.getCalcAmount()+1);
				/**3. 技术方向 */
				if(cbchMap.get(cbch.getEquipTypeName().toUpperCase()) == null) {
					cbchMap.put(cbch.getEquipTypeName().toUpperCase(), new CstBaseCaseHour());
				}
				CstBaseCaseHour cbch3 = cbchMap.get(cbch.getEquipTypeName().toUpperCase());
				cbch3.setSumLineoneCaseHour(cbch3.getSumLineoneCaseHour()+cbch.getLineoneCaseHour());
				cbch3.setSumRemoteCaseHour(cbch3.getSumRemoteCaseHour()+cbch.getRemoteCaseHour());
				cbch3.setCalcAmount(cbch3.getCalcAmount()+1);
				/**4. 整体平均数 */
				if(cbchMap.get("ALL_CASE_HOUR_PARA") == null) {
					cbchMap.put("ALL_CASE_HOUR_PARA", new CstBaseCaseHour());
				}
				CstBaseCaseHour cbch4 = cbchMap.get("ALL_CASE_HOUR_PARA");
				cbch4.setSumLineoneCaseHour(cbch4.getSumLineoneCaseHour()+cbch.getLineoneCaseHour());
				cbch4.setSumRemoteCaseHour(cbch4.getSumRemoteCaseHour()+cbch.getRemoteCaseHour());
				cbch4.setCalcAmount(cbch4.getCalcAmount()+1);
				
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CASE_HOUR, cbchMap);
		}
		
		return cbchMap;
	}

	/**
	 * 故障一线配比集合
	 * @return
	 */
	public static Map<String, CstBaseCaseLineone> getCstBaseCaseLineoneMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstBaseCaseLineone> cbclMap = (Map<String, CstBaseCaseLineone>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CASE_LINE_ONE);
		if (cbclMap == null){
			Map<String, CstResourceBaseInfo> crbiMap = getCstResourceBaseInfoMap();
			cbclMap = Maps.newHashMap();
			for (CstBaseCaseLineone cbcl : cstBaseCaseLineoneDao.findAllList(new CstBaseCaseLineone())){
				// 分别按照 厂商+型号、 技术方向+型号组+厂商、 技术方向+厂商、 技术方向 、资源计划分类 归集case样本数量
				/**1. 厂商+型号 */
				cbclMap.put(cbcl.getResourceId(), cbcl);
				CstResourceBaseInfo crbi = crbiMap.get(cbcl.getResourceId());
				/**2.技术方向+型号组+厂商 */
				if(cbclMap.get("G_"+crbi.getModelGroupId()) == null) {
					cbclMap.put("G_"+crbi.getModelGroupId(), new CstBaseCaseLineone());
				}
				CstBaseCaseLineone cbcl2 = cbclMap.get("G_"+crbi.getModelGroupId());
				cbcl2.setSampleAmount(cbcl2.getSampleAmount()+cbcl.getSampleAmount());
				cbcl2.setLine1OneAmount(cbcl2.getLine1OneAmount()+cbcl.getLine1OneAmount());
				cbcl2.setLine1TwoAmount(cbcl2.getLine1TwoAmount()+cbcl.getLine1TwoAmount());
				cbcl2.setLine1ThdAmount(cbcl2.getLine1ThdAmount()+cbcl.getLine1ThdAmount());
				cbcl2.setLine1FourAmount(cbcl2.getLine1FourAmount()+cbcl.getLine1FourAmount());
				/**3. 技术方向+厂商 */
				if(cbclMap.get(crbi.getEquipTypeId()+"_"+crbi.getMfrId()) == null) {
					cbclMap.put(crbi.getEquipTypeId()+"_"+crbi.getMfrId(), new CstBaseCaseLineone());
				}
				CstBaseCaseLineone cbcl3 = cbclMap.get(crbi.getEquipTypeId()+"_"+crbi.getMfrId());
				cbcl3.setSampleAmount(cbcl3.getSampleAmount()+cbcl.getSampleAmount());
				cbcl3.setLine1OneAmount(cbcl3.getLine1OneAmount()+cbcl.getLine1OneAmount());
				cbcl3.setLine1TwoAmount(cbcl3.getLine1TwoAmount()+cbcl.getLine1TwoAmount());
				cbcl3.setLine1ThdAmount(cbcl3.getLine1ThdAmount()+cbcl.getLine1ThdAmount());
				cbcl3.setLine1FourAmount(cbcl3.getLine1FourAmount()+cbcl.getLine1FourAmount());
				/**4. 技术方向 */
				if(cbclMap.get("E_"+crbi.getEquipTypeId()) == null) {
					cbclMap.put("E_"+crbi.getEquipTypeId(), new CstBaseCaseLineone());
				}
				CstBaseCaseLineone cbcl4 = cbclMap.get("E_"+crbi.getEquipTypeId());
				cbcl4.setSampleAmount(cbcl4.getSampleAmount()+cbcl.getSampleAmount());
				cbcl4.setLine1OneAmount(cbcl4.getLine1OneAmount()+cbcl.getLine1OneAmount());
				cbcl4.setLine1TwoAmount(cbcl4.getLine1TwoAmount()+cbcl.getLine1TwoAmount());
				cbcl4.setLine1ThdAmount(cbcl4.getLine1ThdAmount()+cbcl.getLine1ThdAmount());
				cbcl4.setLine1FourAmount(cbcl4.getLine1FourAmount()+cbcl.getLine1FourAmount());
				/**5. 资源计划分类 */
				if(cbclMap.get("R_"+crbi.getResstattypeId()) == null) {
					cbclMap.put("R_"+crbi.getResstattypeId(), new CstBaseCaseLineone());
				}
				CstBaseCaseLineone cbcl5 = cbclMap.get("R_"+crbi.getResstattypeId());
				cbcl5.setSampleAmount(cbcl5.getSampleAmount()+cbcl.getSampleAmount());
				cbcl5.setLine1OneAmount(cbcl5.getLine1OneAmount()+cbcl.getLine1OneAmount());
				cbcl5.setLine1TwoAmount(cbcl5.getLine1TwoAmount()+cbcl.getLine1TwoAmount());
				cbcl5.setLine1ThdAmount(cbcl5.getLine1ThdAmount()+cbcl.getLine1ThdAmount());
				cbcl5.setLine1FourAmount(cbcl5.getLine1FourAmount()+cbcl.getLine1FourAmount());
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CASE_LINE_ONE, cbclMap);
		}
		
		return cbclMap;
	}
	
	/**
	 * 巡检工时定义集合
	 * @return
	 */
	public static Map<String, CstCheckWorkHour> getCstBaseCheckWorkHourMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckWorkHour> ccwhMap = (Map<String, CstCheckWorkHour>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CHECK_WORK_HOUR);
		if (ccwhMap == null){
			ccwhMap = Maps.newHashMap();
			for (CstCheckWorkHour ccwh : cstCheckWorkHourDao.getBaseCheckWorkHour()){
				if(!"A0".equals(ccwh.getStatus())) continue;
				ccwhMap.put(ccwh.getResModelId(), ccwh);
				/**2. 技术方向+厂商 */
				if(ccwhMap.get(ccwh.getEquipTypeName()+"_"+ccwh.getMfrName()) == null) {
					ccwhMap.put(ccwh.getEquipTypeName()+"_"+ccwh.getMfrName(), new CstCheckWorkHour());
				}
				CstCheckWorkHour ccwh1 = ccwhMap.get(ccwh.getEquipTypeName()+"_"+ccwh.getMfrName());
				ccwh1.setSumLine1RemoteHour(ccwh1.getSumLine1RemoteHour()+ccwh.getLine1RemoteHour());
				ccwh1.setSumLine1LocalHour(ccwh1.getSumLine1LocalHour()+ccwh.getLine1LocalHour());
				ccwh1.setSumLine1DepthHour(ccwh1.getSumLine1DepthHour()+ccwh.getLine1DepthHour());
				ccwh1.setCalcAmount(ccwh1.getCalcAmount()+1);
				/**3. 技术方向 */
				if(ccwhMap.get(ccwh.getEquipTypeName()) == null) {
					ccwhMap.put(ccwh.getEquipTypeName(), new CstCheckWorkHour());
				}
				CstCheckWorkHour ccwh2 = ccwhMap.get(ccwh.getEquipTypeName());
				ccwh2.setSumLine1RemoteHour(ccwh2.getSumLine1RemoteHour()+ccwh.getLine1RemoteHour());
				ccwh2.setSumLine1LocalHour(ccwh2.getSumLine1LocalHour()+ccwh.getLine1LocalHour());
				ccwh2.setSumLine1DepthHour(ccwh2.getSumLine1DepthHour()+ccwh.getLine1DepthHour());
				ccwh2.setCalcAmount(ccwh2.getCalcAmount()+1);
				/**4. 整体平均数 */
				if(ccwhMap.get("ALL_CHECK_WORK_HOUR") == null) {
					ccwhMap.put("ALL_CHECK_WORK_HOUR", new CstCheckWorkHour());
				}
				CstCheckWorkHour ccwh3 = ccwhMap.get("ALL_CHECK_WORK_HOUR");
				ccwh3.setSumLine1RemoteHour(ccwh3.getSumLine1RemoteHour()+ccwh.getLine1RemoteHour());
				ccwh3.setSumLine1LocalHour(ccwh3.getSumLine1LocalHour()+ccwh.getLine1LocalHour());
				ccwh3.setSumLine1DepthHour(ccwh3.getSumLine1DepthHour()+ccwh.getLine1DepthHour());
				ccwh3.setCalcAmount(ccwh3.getCalcAmount()+1);
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CHECK_WORK_HOUR, ccwhMap);
		}
		
		return ccwhMap;
	}
	
	/**
	 * 巡检级别配比定义集合
	 * @return
	 */
	public static Map<String, CstCheckSlaPara> getCstBaseCheckSlaParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstCheckSlaPara> ccspMap = (Map<String, CstCheckSlaPara>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CHECK_SLA_PARA);
		if (ccspMap == null){
			ccspMap = Maps.newHashMap();
			for (CstCheckSlaPara ccsp : cstCheckSlaParaDao.getBaseCheckSlaPara()){
				if(!"A0".equals(ccsp.getStatus())) continue;
				ccspMap.put(ccsp.getResModelId()+ccsp.getSlaName(), ccsp);
				/**2. 技术方向+厂商 */
				if(ccspMap.get(ccsp.getEquipTypeName()+"_"+ccsp.getMfrName()+ccsp.getSlaName()) == null) {
					ccspMap.put(ccsp.getEquipTypeName()+"_"+ccsp.getMfrName()+ccsp.getSlaName(), new CstCheckSlaPara());
				}
				CstCheckSlaPara ccsp1 = ccspMap.get(ccsp.getEquipTypeName()+"_"+ccsp.getMfrName()+ccsp.getSlaName());
				ccsp1.setSumLine1Level1Scale(ccsp1.getSumLine1Level1Scale()+ccsp.getLine1Level1Scale());
				ccsp1.setSumLine1Level2Scale(ccsp1.getSumLine1Level2Scale()+ccsp.getLine1Level2Scale());
				ccsp1.setSumLine1Level3Scale(ccsp1.getSumLine1Level3Scale()+ccsp.getLine1Level3Scale());
				ccsp1.setSumLine1Level4Scale(ccsp1.getSumLine1Level4Scale()+ccsp.getLine1Level4Scale());
				ccsp1.setSumLine1Level5Scale(ccsp1.getSumLine1Level5Scale()+ccsp.getLine1Level5Scale());
				ccsp1.setCalcAmount(ccsp1.getCalcAmount()+1);
				/**3. 技术方向 */
				if(ccspMap.get(ccsp.getEquipTypeName()+ccsp.getSlaName()) == null) {
					ccspMap.put(ccsp.getEquipTypeName()+ccsp.getSlaName(), new CstCheckSlaPara());
				}
				CstCheckSlaPara ccsp2 = ccspMap.get(ccsp.getEquipTypeName()+ccsp.getSlaName());
				ccsp2.setSumLine1Level1Scale(ccsp2.getSumLine1Level1Scale()+ccsp.getLine1Level1Scale());
				ccsp2.setSumLine1Level2Scale(ccsp2.getSumLine1Level2Scale()+ccsp.getLine1Level2Scale());
				ccsp2.setSumLine1Level3Scale(ccsp2.getSumLine1Level3Scale()+ccsp.getLine1Level3Scale());
				ccsp2.setSumLine1Level4Scale(ccsp2.getSumLine1Level4Scale()+ccsp.getLine1Level4Scale());
				ccsp2.setSumLine1Level5Scale(ccsp2.getSumLine1Level5Scale()+ccsp.getLine1Level5Scale());
				ccsp2.setCalcAmount(ccsp2.getCalcAmount()+1);
				/**4. 整体平均数 */
				if(ccspMap.get("ALL_CHECK_SLA_PARA"+ccsp.getSlaName()) == null) {
					ccspMap.put("ALL_CHECK_SLA_PARA"+ccsp.getSlaName(), new CstCheckSlaPara());
				}
				CstCheckSlaPara ccsp3 = ccspMap.get("ALL_CHECK_SLA_PARA"+ccsp.getSlaName());
				ccsp3.setSumLine1Level1Scale(ccsp3.getSumLine1Level1Scale()+ccsp.getLine1Level1Scale());
				ccsp3.setSumLine1Level2Scale(ccsp3.getSumLine1Level2Scale()+ccsp.getLine1Level2Scale());
				ccsp3.setSumLine1Level3Scale(ccsp3.getSumLine1Level3Scale()+ccsp.getLine1Level3Scale());
				ccsp3.setSumLine1Level4Scale(ccsp3.getSumLine1Level4Scale()+ccsp.getLine1Level4Scale());
				ccsp3.setSumLine1Level5Scale(ccsp3.getSumLine1Level5Scale()+ccsp.getLine1Level5Scale());
				ccsp3.setCalcAmount(ccsp3.getCalcAmount()+1);
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_CHECK_SLA_PARA, ccspMap);
		}
		
		return ccspMap;
	}
	
	/**
	 * 备件-故障率参数生成需要获取参数定义
	 * @return
	 */
	public static Map<String, CstBaseBackFaultPara> getCstBaseBackFaultParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstBaseBackFaultPara> ccspMap = (Map<String, CstBaseBackFaultPara>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_BACK_FAULT_PARA);
		if (ccspMap == null){
			ccspMap = Maps.newHashMap();
			for (CstBaseBackFaultPara cbbfp : cstBaseBackFaultParaDao.findList(new CstBaseBackFaultPara())){
				if(!"A0".equals(cbbfp.getStatus())) continue;

				/**1. 技术方向+型号组+厂商 */
				if(cbbfp.getMfrName() != null && !"".equals(cbbfp.getMfrName()) && 
						cbbfp.getModelGroupName() != null && !"".equals(cbbfp.getModelGroupName()) &&
						cbbfp.getEquipTypeName() != null && !"".equals(cbbfp.getEquipTypeName())) {
					ccspMap.put(cbbfp.getEquipTypeName().toUpperCase()+"_"+cbbfp.getModelGroupName().toUpperCase()+"_"+cbbfp.getMfrName().toUpperCase(), cbbfp);
				}
				/**2. 技术方向+厂商 */
				if(cbbfp.getMfrName() != null && !"".equals(cbbfp.getMfrName()) && cbbfp.getEquipTypeName() != null && !"".equals(cbbfp.getEquipTypeName())) {
					ccspMap.put(cbbfp.getEquipTypeName().toUpperCase()+"_"+cbbfp.getMfrName().toUpperCase(), cbbfp);
				}
				/**3. 技术方向 */
				else if(cbbfp.getEquipTypeName() != null && !"".equals(cbbfp.getEquipTypeName())) {
					ccspMap.put(cbbfp.getEquipTypeName().toUpperCase(), cbbfp);
				}
				/**4. 资源计划分类 */
				else if(cbbfp.getResstattypeName() != null && !"".equals(cbbfp.getResstattypeName())) {
					ccspMap.put(cbbfp.getResstattypeName().toUpperCase(), cbbfp);
				}
				/**5. 整体平均数 */
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_BACK_FAULT_PARA, ccspMap);
		}
		
		return ccspMap;
	}
	
	/**
	 * 备件-事件故障级别配比参数生成需要获取参数定义
	 * @return
	 */
	public static Map<String, CstBaseBackCasePara> getCstBaseBackCaseParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstBaseBackCasePara> ccspMap = (Map<String, CstBaseBackCasePara>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_BACK_CASE_PARA);
		if (ccspMap == null){
			ccspMap = Maps.newHashMap();
			for (CstBaseBackCasePara cbbcp : cstBaseBackCaseParaDao.findList(new CstBaseBackCasePara())){
				if(!"A0".equals(cbbcp.getStatus())) continue;

				/**1.资源计划分类名称 */
				if(cbbcp.getResstattypeName() != null) {
					ccspMap.put(cbbcp.getResstattypeName(), cbbcp);
				}
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_BACK_CASE_PARA, ccspMap);
		}
		
		return ccspMap;
	}
	
	/**
	 * 备件-合作商标识参数生成需要获取参数定义
	 * @return
	 */
	public static Map<String, CstBaseBackPackPara> getCstBaseBackPackParaMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstBaseBackPackPara> ccspMap = (Map<String, CstBaseBackPackPara>)EhCacheUtils.get(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_BACK_PACK_PARA);
		if (ccspMap == null){
			ccspMap = Maps.newHashMap();
			for (CstBaseBackPackPara cbbpp : cstBaseBackPackParaDao.findList(new CstBaseBackPackPara())){
				if(!"A0".equals(cbbpp.getStatus())) continue;

				/**1.技术方向+厂商 */
				if(cbbpp.getEquipTypeName() != null && cbbpp.getMfrName() != null) {
					ccspMap.put(cbbpp.getEquipTypeName().toUpperCase()+"_"+cbbpp.getMfrName().toUpperCase(), cbbpp);
				}
			}
			EhCacheUtils.put(CST_RESOURCE_BASE_PARA_CACHE, Constants.CST_BASE_BACK_PACK_PARA, ccspMap);
		}
		
		return ccspMap;
	}
	
	/**
	 * 地域系数定义集合
	 * @return
	 */
	public static Map<String, CstManCityPara> getCstManCityParaTestMap(){
		@SuppressWarnings("unchecked")
		Map<String, CstManCityPara> cmcpMap = (Map<String, CstManCityPara>)EhCacheUtils.get(CST_BASE_DATA_DICT_CACHE, "cstManCityParaTest");
		if (cmcpMap == null){
			cmcpMap = Maps.newHashMap();
			for (CstManCityPara cmpp : cstManCityParaDao.findAllList(new CstManCityPara())){
				if(!"A0".equals(cmpp.getStatus())) continue;
				cmcpMap.put(cmpp.getCityName(), cmpp);
			}
			EhCacheUtils.put(CST_BASE_DATA_DICT_CACHE, "cstManCityParaTest", cmcpMap);
		}
		
		return cmcpMap;
	}
	
}
