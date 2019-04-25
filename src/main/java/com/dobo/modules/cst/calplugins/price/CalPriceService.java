package com.dobo.modules.cst.calplugins.price;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dobo.modules.cst.calplugins.ExpressionCalculate;
import com.dobo.modules.cst.common.BaseParaUtils;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.entity.model.CstModelCalculateFormula;
import com.dobo.modules.cst.entity.model.CstModelParaDef;
import com.dobo.modules.cst.entity.model.CstModelProdModuleRel;
import com.dobo.modules.cst.rest.entity.DetailCostInfo;
import com.dobo.modules.cst.rest.entity.ProdDetailInfo;


public class CalPriceService {

	public HashMap<String, String> paraMap = new HashMap<String, String>();
	
	/**
	 * 计算产品价格-产品清单维度
	 * @param module		模型
	 * @param detailInfoMap    输入清单明细信息<prodId, Map<String, ProdDetailInfo>>
	 * @param proddciMap	         输出清单成本明细<prodId, Map<detailId, DetailCostInfo>>
	 */
	public void cost(CstModelProdModuleRel module, Map<String, Map<String, ProdDetailInfo>> detailInfoMap, Map<String, Map<String, DetailCostInfo>> proddciMap) {
		paraMap.clear();
		
		String prodId = module.getProdId();
		
		if(proddciMap.get(prodId) == null) {
			proddciMap.put(prodId, new HashMap<String, DetailCostInfo>());
		}
		// 返回成本明细集合 <detailId, costInfo>
		Map<String, DetailCostInfo> dcisMap = proddciMap.get(prodId);
		
		for(ProdDetailInfo detailInfo : detailInfoMap.get(prodId).values()) {
			if(dcisMap.get(detailInfo.getDetailId()) == null) {
				dcisMap.put(detailInfo.getDetailId(), new DetailCostInfo(detailInfo.getDetailId()));
			}
			cost(module, detailInfo, dcisMap.get(detailInfo.getDetailId()));
		}
		
	}
	
	/**
	 * 计算产品价格-单条清单维度
	 * @param module		模型
	 * @param detailInfo    输入清单明细信息
	 * @param dcis		            输出清单成本明细
	 */
	public void cost(CstModelProdModuleRel module, ProdDetailInfo detailInfo, DetailCostInfo dcis) {
		//try {
			//1.加载所有公式参数
			getCostCalculatePara(module, detailInfo.getInParaMap());
			//2.遍历逻辑运算公式
			calculateFormulaForCost(module, dcis);
		/*} catch (Exception e) {
			throw new ServiceException("成本模型["+module.getModuleId()+"]计算明细["+detailInfo.getDetailId()+"]失败："+e.getMessage());
		}*/
	}
	
	/**
	 * 遍历模型公式计算产品价格
	 * @param module		模型
	 * @param dcis		            输出清单成本明细
	 */
	public void calculateFormulaForCost(CstModelProdModuleRel module, DetailCostInfo dcis) {
		Map<String, CstModelCalculateFormula> pmrMap = CacheDataUtils.getModelCalculateFormulaMap().get(module.getModuleId());
		for(CstModelCalculateFormula cmcf : pmrMap.values()) {
			if(dcis.getCostInfoMap() == null) {
				Map<String, String> costInfoMap = new HashMap<String, String>();
				dcis.setCostInfoMap(costInfoMap);
			}
			
			String calValue = ExpressionCalculate.getStringValue(paraMap, cmcf.getFormula(), 8);
			dcis.getCostInfoMap().put(cmcf.getMeasureId(), calValue);
			
			/*if("A0".equals(cmcf.getReturnType())) { //返回类型(A0:成本/A1:工时)
				dcis.getCostInfoMap().get(cmcf.getMeasureId())[0] = calValue;
				}
				else if("A1".equals(cmcf.getReturnType())) { //返回类型(A0:成本/A1:工时)
					dcis.getCostInfoMap().get(cmcf.getMeasureId())[1] = calValue;
				}*/
			
			// 参数放在参数集合
			if("A0".equals(cmcf.getIsModelPara())) { //是否成本参数(A0:是/A1:否)
				paraMap.put(cmcf.getMeasureId(), calValue);
			}
		}
	}
	
	/**
	 * 获取价格计算过程中的参数
	 */
	public void getCostCalculatePara(CstModelProdModuleRel module, Map<String, String> inParaMap) {
		List<CstModelParaDef> modelParaList = CacheDataUtils.getModelParaDefMap().get(module.getModuleId());
		// 根据模型与产品对应获取产品ID
		paraMap.put("prodId", module.getProdId());
		for(CstModelParaDef cmp : modelParaList) {
			//A0:直接取值/A1:公式计算/A2:逻辑算法类/A3:输入参数
			if("A0".equals(cmp.getParaType())) {
				paraMap.put(cmp.getParaId(), cmp.getParaValue());
			} else if("A1".equals(cmp.getParaType())) {
				paraMap.put(cmp.getParaId(), ExpressionCalculate.getExpressionValue(paraMap, cmp.getParaFormula()));
			} else if("A2".equals(cmp.getParaType())) {
				/**
				 * A2:逻辑算法类    缓存定义名称(bean)|间接参数,数据key(通过paraMap.get(StrSpilt[1]) 获取)|获取的数据属性
				 * 如：cstManFailureSlaPara|resourceId,SLA|line1Level1Scale
				 */
				//System.out.println(cmp.getParaCalcClass());
				String[] StrSpilt = cmp.getParaCalcClass().split("\\|");
				//间接参数,数据key(通过paraMap.get(StrSpilt[1]) 获取)
				String paras = "";
				for(String para : StrSpilt[1].split(",")) {
					// 转化后参数找不到时，取参数本身的值
					if(paraMap.get(para) == null || "".equals(paraMap.get(para).trim()) || "null".equals(paraMap.get(para))) {
						paras += para;
					} else {
						paras += paraMap.get(para);
					}
				}
				paraMap.put(cmp.getParaId(), BaseParaUtils.getBaseDataPara(StrSpilt[0], paras, StrSpilt[2]));
			} else if("A3".equals(cmp.getParaType())) {
				paraMap.put(cmp.getParaId(), inParaMap.get(cmp.getParaId()));
			}
		}
	}
}
