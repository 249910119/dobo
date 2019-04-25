package com.dobo.modules.cst.common;

import java.util.List;

import com.dobo.common.service.ServiceException;
import com.dobo.common.utils.Reflections;
import com.dobo.modules.cst.entity.check.CstCheckResmgrHour;
import com.dobo.modules.cst.entity.parts.CstPartsFailureRateCost;
import com.dobo.modules.cst.entity.parts.CstPartsRiskCostPara;
import com.dobo.modules.sys.utils.AreaUtils;

public class BaseParaUtils {
	
	/**
	 * 
	 * @param type    数据表 
	 * @param key     唯一标识
	 * @return
	 */
	public static Object getBaseData(String type, String key) {
		Object obj = null;
		String dataKeys = ""; 
		// 参数表中逻辑类需要多个参数确定一条数据时，多个参数","分隔;
		for(String str : key.split(",")) {
			dataKeys += str;
		}
		// 主数据表
		if("cstResourceBaseInfo".equals(type)) {
			obj = CacheDataUtils.getCstResourceBaseInfoMap().get(dataKeys);
		}
		//故障CASE处理工时定义表
		else if("cstManFailureCaseHour".equals(type)) {
			obj = CacheDataUtils.getCstManFailureCaseHourMap().get(dataKeys);
		}
		//故障级别配比定义表
		else if("cstManFailureSlaPara".equals(type)) {
			obj = CacheDataUtils.getCstManFailureSlaParaMap().get(dataKeys);
		}
		//故障饱和度定义表
		else if("cstManFailureDegree".equals(type)) {
			obj = CacheDataUtils.getCstManFailureDegreeMap().get(dataKeys);
		}
		//故障人工费率定义表
		else if("cstManFailureManRate".equals(type)) {
			obj = CacheDataUtils.getCstManFailureManRateMap().get(dataKeys);
		}
		//服务产品系数定义表
		else if("cstManProdPara".equals(type)) {
			obj = CacheDataUtils.getCstManProdParaMap().get(dataKeys);
		}
		//地域系数定义表
		else if("cstManCityPara".equals(type)) {
			obj = CacheDataUtils.getCstManCityParaMap().get(dataKeys);
		}
		//非工作时间比重定义表
		else if("cstManStandbyPara".equals(type)) {
			obj = CacheDataUtils.getCstManStandbyParaMap().get(dataKeys);
		}
		//备件故障率与采购成本定义表
		else if("cstPartsFailureRateCost".equals(type)) {
			 List<CstPartsFailureRateCost> cpfracList  = CacheDataUtils.getCstPartsFailureRateAndCostMap().get(dataKeys);
			//根据备件类型加权
			Double failureRate = 0.0;		// 备件故障率
		    Double averageCost = 0.0;		// 采购平均成本
		    Double sumFailureCost = 0.0;		// 备件故障率*采购平均成本
			for(CstPartsFailureRateCost cpfrac : cpfracList) {
				failureRate = failureRate + cpfrac.getFailureRate();
				averageCost = averageCost + cpfrac.getAverageCost();
				sumFailureCost = sumFailureCost + cpfrac.getFailureRate()*cpfrac.getAverageCost();
			}
			CstPartsFailureRateCost cstPartsFailureRateAndCost = new CstPartsFailureRateCost();
			cstPartsFailureRateAndCost.setAverageCost(averageCost);
			cstPartsFailureRateAndCost.setFailureRate(failureRate);
			cstPartsFailureRateAndCost.setSumFailureCost(sumFailureCost);
			obj = cstPartsFailureRateAndCost;
		}
		//备件加权平均在保量定义表
		else if("cstPartsWeightAmount".equals(type)) {
			obj = CacheDataUtils.getCstPartsWeightAmountMap().get(dataKeys);
		}
		//备件事件故障参数定义表
		else if("cstPartsEventFailurePara".equals(type)) {
			obj = CacheDataUtils.getCstPartsEventFailureParaMap().get(dataKeys);
		}
		//备件满足率定义表
		else if("cstPartsFillRate".equals(type)) {
			//通过传入的cityId获取对应的省份
			dataKeys = AreaUtils.getProvince(dataKeys).getName();
			obj = CacheDataUtils.getCstPartsFillRateMap().get(dataKeys);
		}
		//备件项目储备成本系数定义表
		else if("cstPartsPrjStorePara".equals(type)) {
			//通过传入的cityId获取对应的省份Id
			dataKeys = AreaUtils.getProvince(dataKeys).getName();
			obj = CacheDataUtils.getCstPartsPrjStoreParaMap().get(dataKeys);
		}
		//备件发货运输平均成本定义表
		else if("cstPartsTransportCost".equals(type)) {
			obj = CacheDataUtils.getCstPartsTransportCostMap().get(dataKeys);
		}
		//备件故障成本SLA采购成本系数
		else if("cstPartsSlaCostRate".equals(type)) {
			obj = CacheDataUtils.getCstPartsSlaCostRateMap().get(dataKeys);
		}
		//备件各技术方向人员成本系数
		else if("cstPartsEquipTypeRate".equals(type)) {
			obj = CacheDataUtils.getCstPartsEquipTypeRateMap().get(dataKeys);
		}
		//备件型号组维度风险准备金系数
		else if("cstPartsRiskCostPara".equals(type)) {
			obj = CacheDataUtils.getCstPartsRiskCostParaMap().get(dataKeys);
			if(obj == null) {
				CstPartsRiskCostPara cprcp = new CstPartsRiskCostPara();
				cprcp.setRiskCostScale(0.2); // 查找不到对应的风险系数，风险成本系数就为0.2
				obj = cprcp;
			}
		}
		//备件参数字典定义表
		else if("cstDictPara".equals(type)) {
			obj = CacheDataUtils.getCstDictParaMap().get(dataKeys);
		}
		//巡检工时定义表
		else if("cstCheckWorkHour".equals(type)) {
			obj = CacheDataUtils.getCstCheckWorkHourMap().get(dataKeys);
		}
		//巡检级别配比定义表
		else if("cstCheckSlaPara".equals(type)) {
			obj = CacheDataUtils.getCstCheckSlaParaMap().get(dataKeys);
		}
		//巡检-资源岗巡检安排工时表
		else if("cstCheckResmgrHour".equals(type)) {
			for(CstCheckResmgrHour ccrh : CacheDataUtils.getCstCheckResmgrHourMap().values()) {
				if(!"A0".equals(ccrh.getStatus())) continue;
				obj = ccrh;
			}
		}
		//巡检-单台路程工时表
		else if("cstCheckDistanceUnitHour".equals(type)) {
			obj = CacheDataUtils.getCstCheckDistanceUnitHourMap().get(dataKeys);
		}
		//巡检-首次巡检系数表
		else if("cstCheckFirstcheckPara".equals(type)) {
			obj = CacheDataUtils.getCstCheckFirstcheckParaMap().get(dataKeys);
		}
		// 巡检-资源计划分类对应设备大类关系表
		else if("cstCheckResstatSystemRel".equals(type)) {
			obj = CacheDataUtils.getCstCheckResstatSystemRelMap().get(dataKeys);
		}
		// 驻场工程师级别与人员角色对应关系
		else if("cstManLevelRoleRel".equals(type)) {
			obj = CacheDataUtils.getCstManLevelRoleRelMap().get(dataKeys);
		}
		// 驻场工程师级别与人员角色对应关系
		else if("cstManLevelRoleRelNew".equals(type)) {
			obj = CacheDataUtils.getCstManLevelRoleRelNewMap().get(dataKeys);
		}
		// 项目管理配比及饱和度
		else if("cstManManageDegreePara".equals(type)) {
			obj = CacheDataUtils.getCstManManageDegreeParaMap().get(dataKeys);
		}
		// 非设备类系数参数
		else if("cstManNotDevicePara".equals(type)) {
			obj = CacheDataUtils.getCstManNotDeviceParaMap().get(dataKeys);
		}
		// 单次、先行支持服务单价
		else if("cstManCaseSupportPrice".equals(type)) {
			obj = CacheDataUtils.getCstManCaseSupportPriceMap().get(dataKeys);
		}
		// 单次、先行支持标准工时系数
		else if("cstCaseStandHourScale".equals(type)) {
			obj = CacheDataUtils.getCstCaseStandHourScaleMap().get(dataKeys);
		}
		
		return obj;
	}
	
	/**
	 * 缓存中获取主数据参数值
	 * @param type    数据表 
	 * @param key     唯一标识
	 * @param paraName     参数名称
	 * @return
	 */
	public static String getBaseDataPara(String type, String key, String paraName) {
		String value = "";
		
		Object object = getBaseData(type, key);
		if(object != null) {
			Object obj = Reflections.getFieldValue(object, paraName);
			value = String.valueOf(obj);
		} else {
			System.out.println("#########提取参数BaseParaUtils:"+type+"|"+key+"|"+paraName);
			// 抛出异常
			throw new ServiceException(type+":"+key+":"+paraName+".提取参数失败");
		}

		return value;
	}

}
