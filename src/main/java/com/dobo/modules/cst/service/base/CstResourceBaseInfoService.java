package com.dobo.modules.cst.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dobo.common.persistence.Page;
import com.dobo.common.service.CrudService;
import com.dobo.common.service.ServiceException;
import com.dobo.modules.cst.common.CacheDataUtils;
import com.dobo.modules.cst.dao.base.CstResourceBaseInfoDao;
import com.dobo.modules.cst.entity.base.CstBaseBackCasePara;
import com.dobo.modules.cst.entity.base.CstBaseBackFaultPara;
import com.dobo.modules.cst.entity.base.CstBaseBackPackPara;
import com.dobo.modules.cst.entity.base.CstResourceBaseInfo;
import com.dobo.modules.cst.entity.check.CstCheckSlaPara;
import com.dobo.modules.cst.entity.check.CstCheckWorkHour;
import com.dobo.modules.cst.entity.man.CstManFailureCaseHour;
import com.dobo.modules.cst.entity.man.CstManFailureSlaPara;
import com.dobo.modules.cst.entity.parts.CstPartsEventFailurePara;
import com.dobo.modules.cst.service.check.CstCheckSlaParaService;
import com.dobo.modules.cst.service.check.CstCheckWorkHourService;
import com.dobo.modules.cst.service.man.CstManFailureCaseHourService;
import com.dobo.modules.cst.service.man.CstManFailureSlaParaService;
import com.dobo.modules.cst.service.parts.CstPartsEventFailureParaService;

@Service
@Transactional(readOnly = true)
public class CstResourceBaseInfoService extends CrudService<CstResourceBaseInfoDao, CstResourceBaseInfo>{
	@Autowired
	CstBaseResourceCaseService cstBaseResourceCaseService;
	@Autowired
	CstBaseCaseLineoneService cstBaseCaseLineoneService;
	@Autowired
	CstManFailureCaseHourService cstManFailureCaseHourService;
	@Autowired
	CstManFailureSlaParaService cstManFailureSlaParaService;
	@Autowired
	CstCheckWorkHourService cstCheckWorkHourService;
	@Autowired
	CstCheckSlaParaService cstCheckSlaParaService;
	@Autowired
	CstPartsEventFailureParaService cstPartsEventFailureParaService;
	
	@Override
    public CstResourceBaseInfo get(String id) {
		return super.get(id);
	}
	
	public void calBaseResourcePara(String resourceId) {
		try {
			CstResourceBaseInfo crbi = this.get(resourceId);
			/**1.故障CASE处理工时定义 */
			CstManFailureCaseHour cfchSelect = new CstManFailureCaseHour();
			cfchSelect.setResourceId(resourceId);
			cfchSelect.setStatus("A0");
			CstManFailureCaseHour cfch = cstManFailureCaseHourService.get(cfchSelect);
			if(cfch == null) {
				cstBaseResourceCaseService.calcBaseCasePara(crbi);
				cfch = new CstManFailureCaseHour();
				cfch.setResourceId(resourceId);
				cfch.setResourceDesc(crbi.getMfrId()+"|"+crbi.getResourceName()+crbi.getModelGroupName()+""+crbi.getEquipTypeName());
				cfch.setYearFailureRate(Double.valueOf(crbi.getFailureRate()));
				cfch.setLine1OnceHour(Double.valueOf(crbi.getLineOneCaseHour()));
				cfch.setLine2OnceHour(Double.valueOf(crbi.getRemoteCaseHour()));
				cstManFailureCaseHourService.save(cfch);
			}
			/**2.故障级别配比参数定义 */
			CstManFailureSlaPara cmfspSelect = new CstManFailureSlaPara();
			cmfspSelect.setResourceId(resourceId);
			cmfspSelect.setSlaId("SLA_DEVICE_B");
			cmfspSelect.setStatus("A0");
			CstManFailureSlaPara cmfspB = cstManFailureSlaParaService.get(cmfspSelect);
			if(cmfspB == null) {
				List<CstResourceBaseInfo> crbiList = cstBaseCaseLineoneService.calcCaseLineOnePara(crbi);
				for(CstResourceBaseInfo obj : crbiList) {
					CstManFailureSlaPara cmfsp = new CstManFailureSlaPara();
					cmfsp.setResourceId(resourceId);
					cmfsp.setResourceDesc(crbi.getMfrId()+"|"+crbi.getResourceName()+crbi.getModelGroupName()+""+crbi.getEquipTypeName());
					cmfsp.setSlaName(obj.getSlaName());
					if("标准服务B".equals(obj.getSlaName())) {
						cmfsp.setSlaId("SLA_DEVICE_B");
					} else if("高级服务A".equals(obj.getSlaName())) {
						cmfsp.setSlaId("SLA_DEVICE_A");
					}
					cmfsp.setLine1Level1Scale(Double.valueOf(obj.getFailureLineOne1Rate()));
					cmfsp.setLine1Level2Scale(Double.valueOf(obj.getFailureLineOne2Rate()));
					cmfsp.setLine1Level3Scale(Double.valueOf(obj.getFailureLineOne3Rate()));
					cmfsp.setLine1Level4Scale(Double.valueOf(obj.getFailureLineOne4Rate()));
					cmfsp.setLine1Level5Scale(Double.valueOf(obj.getFailureLineOne5Rate()));
					cstManFailureSlaParaService.save(cmfsp);
				}
			}
			/**3.巡检工时定义定义 */
			CstCheckWorkHour ccwhSelect = new CstCheckWorkHour();
			ccwhSelect.setResModelId(crbi.getModelGroupId());
			ccwhSelect.setStatus("A0");
			CstCheckWorkHour ccwh = cstCheckWorkHourService.get(ccwhSelect);
			if(ccwh == null) {
				CstCheckWorkHour ccwh1 = CacheDataUtils.getCstBaseCheckWorkHourMap().get(crbi.getEquipTypeName()+"_"+crbi.getMfrName());
				CstCheckWorkHour ccwh2 = CacheDataUtils.getCstBaseCheckWorkHourMap().get(crbi.getEquipTypeName());
				CstCheckWorkHour ccwh3 = CacheDataUtils.getCstBaseCheckWorkHourMap().get("ALL_CHECK_WORK_HOUR");
				if(ccwh1 != null) {
					ccwh = ccwh1;
				} else if(ccwh2 != null) {
					ccwh = ccwh2;
				} else if(ccwh3 != null) {
					ccwh = ccwh3;
				}
				CstCheckWorkHour newccwh = new CstCheckWorkHour();
				newccwh.setResModelId(crbi.getModelGroupId());
				newccwh.setResModelDesc(crbi.getMfrName()+"|"+crbi.getModelGroupName()+"|"+crbi.getEquipTypeName());
				newccwh.setLine1RemoteHour(ccwh.getLine1RemoteHour());
				newccwh.setLine1LocalHour(ccwh.getLine1LocalHour());
				newccwh.setLine1DepthHour(ccwh.getLine1DepthHour());
				cstCheckWorkHourService.save(newccwh);
			}
			/**3.巡检级别配比参数定义 */
			CstCheckSlaPara ccspSelect = new CstCheckSlaPara();
			ccspSelect.setResModelId(crbi.getModelGroupId());
			ccspSelect.setSlaName("BUYCHECKN");
			ccspSelect.setStatus("A0");
			CstCheckSlaPara ccsp = cstCheckSlaParaService.get(ccspSelect);
			if(ccsp == null) {
				String[] checkInfo = new String[]{"BUYCHECKN","BUYFARCHK","BUYDEPCHK"};
				for(String check : checkInfo) {
					CstCheckSlaPara ccsp1 = CacheDataUtils.getCstBaseCheckSlaParaMap().get(crbi.getEquipTypeName()+"_"+crbi.getMfrName()+check);
					CstCheckSlaPara ccsp2 = CacheDataUtils.getCstBaseCheckSlaParaMap().get(crbi.getEquipTypeName()+check);
					CstCheckSlaPara ccsp3 = CacheDataUtils.getCstBaseCheckSlaParaMap().get("ALL_CHECK_SLA_PARA"+check);
					if(ccsp1 != null) {
						ccsp = ccsp1;
					} else if(ccsp2 != null) {
						ccsp = ccsp2;
					} else if(ccsp3 != null) {
						ccsp = ccsp3;
					}
					
					CstCheckSlaPara newccsp = new CstCheckSlaPara();
					newccsp.setResModelId(crbi.getModelGroupId());
					newccsp.setResModelDesc(crbi.getMfrName()+"|"+crbi.getModelGroupName()+"|"+crbi.getEquipTypeName());
					newccsp.setSlaName(check);
					newccsp.setLine1Level1Scale(ccsp.getLine1Level1Scale());
					newccsp.setLine1Level2Scale(ccsp.getLine1Level2Scale());
					newccsp.setLine1Level3Scale(ccsp.getLine1Level3Scale());
					newccsp.setLine1Level4Scale(ccsp.getLine1Level4Scale());
					newccsp.setLine1Level5Scale(ccsp.getLine1Level5Scale());
					cstCheckSlaParaService.save(newccsp);
				}
			}
			/**4.备件故障率参数 */
			CstPartsEventFailurePara cpefpSelect = new CstPartsEventFailurePara();
			cpefpSelect.setResourceId(resourceId);
			cpefpSelect.setStatus("A0");
			CstPartsEventFailurePara cpefp = cstPartsEventFailureParaService.get(cpefpSelect);
			if(cpefp == null) {
				cpefp = new CstPartsEventFailurePara();
				cpefp.setResourceId(resourceId);
				cpefp.setEventFailureRate(cfch.getYearFailureRate()); // 事件故障率取人工年化故障率
				cpefp.setPrjSub("0"); // 采购是否分包（0否1是）	默认为0
				cpefp.setFailureCostScale(1.0); // 设备型号故障成本价格系数	默认为1
				CstBaseBackFaultPara cbbfp1 = CacheDataUtils.getCstBaseBackFaultParaMap().get(crbi.getEquipTypeName().toUpperCase()+"_"+crbi.getModelGroupName().toUpperCase()+"_"+crbi.getMfrName().toUpperCase());
				CstBaseBackFaultPara cbbfp2 = CacheDataUtils.getCstBaseBackFaultParaMap().get(crbi.getEquipTypeName().toUpperCase()+"_"+crbi.getMfrName().toUpperCase());
				CstBaseBackFaultPara cbbfp3 = CacheDataUtils.getCstBaseBackFaultParaMap().get(crbi.getEquipTypeName().toUpperCase());
				CstBaseBackFaultPara cbbfp4 = CacheDataUtils.getCstBaseBackFaultParaMap().get(crbi.getResstattypeName().toUpperCase());
				CstBaseBackFaultPara cbbfp = new CstBaseBackFaultPara();
				if(cbbfp1 != null) {
					cbbfp = cbbfp1;
				} else if(cbbfp2 != null) {
					cbbfp = cbbfp2;
				} else if(cbbfp3 != null) {
					cbbfp = cbbfp3;
				} else if(cbbfp4 != null) {
					cbbfp = cbbfp4;
				}
				cpefp.setFailureRate(cbbfp.getBackFaultRate()); 
				cpefp.setAverageFailureCost(cbbfp.getAverFaultCost());
				// 故障事件级别配比
				CstBaseBackCasePara cbbcp = CacheDataUtils.getCstBaseBackCaseParaMap().get(crbi.getResstattypeName());
				if(cbbcp == null) {
					cbbcp = CacheDataUtils.getCstBaseBackCaseParaMap().get("整体");
				}
				cpefp.setLevel1EventScale(cbbcp.getLevel1CaseRate());
				cpefp.setLevel2EventScale(cbbcp.getLevel2CaseRate());
				cpefp.setLevel3EventScale(cbbcp.getLevel3CaseRate());
				cpefp.setLevel4EventScale(cbbcp.getLevel4CaseRate());
				// 合作商标识	根据厂商+技术方向获取对应的包名
				CstBaseBackPackPara cbbpp = CacheDataUtils.getCstBaseBackPackParaMap().get(crbi.getEquipTypeName().toUpperCase()+"_"+crbi.getMfrName().toUpperCase());
				if(cbbpp != null) {
					cpefp.setPartJointPackCostId(cbbpp.getCooperCode());
				} else {
					// 主数据的 技术方向+厂商 取不到数据时，取 其他 pjpcl_13 自有-其它
					cpefp.setPartJointPackCostId("pjpcl_13");
				}
				cpefp.setResourceDesc(crbi.getMfrName()+"|"+crbi.getResourceName()+"|"+crbi.getEquipTypeName()+"|"+crbi.getModelGroupName());
				cstPartsEventFailureParaService.save(cpefp);
			}
		} catch (Exception e) {
			 throw new ServiceException("主数据资源模型参数初始错误,主数据ID："+resourceId+",reason："+e.getMessage());
		}
	}
	
	@Override
    public List<CstResourceBaseInfo> findList(CstResourceBaseInfo cstResourceBaseInfo) {
		return super.findList(cstResourceBaseInfo);
	}
	
	@Override
    public Page<CstResourceBaseInfo> findPage(Page<CstResourceBaseInfo> page, CstResourceBaseInfo cstResourceBaseInfo) {
		return super.findPage(page, cstResourceBaseInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(CstResourceBaseInfo cstResourceBaseInfo) {
		super.save(cstResourceBaseInfo);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(CstResourceBaseInfo cstResourceBaseInfo) {
		super.delete(cstResourceBaseInfo);
	}
}
