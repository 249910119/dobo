/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cst.dao.detail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cst.entity.detail.CstOrderDetailInfo;

/**
 * 订单明细信息DAO接口
 * @author admin
 * @version 2016-12-02
 */
@MyBatisDao
public interface CstOrderDetailInfoDao extends CrudDao<CstOrderDetailInfo> {
	
	/**
	 * 查询所有数据列表
	 * 
	 * @param execOrgNames
	 * @param supportTypes
	 * @param costClassNames
	 * @param prodServiceModes
	 * @param prodStatTypes
	 * @param createDateStr
	 * @param pdId
	 * @param dcPrjId
	 * @return
	 */
	public List<CstOrderDetailInfo> getWbmBaseList(
		@Param("execOrgNames") List<String> execOrgNames,
		@Param("supportTypes") List<String> supportTypes,
		@Param("costClassNames") List<String> costClassNames,
		@Param("prodServiceModes") List<String> prodServiceModes,
		@Param("prodName") List<String> prodName,
		@Param("prodStatTypes") List<String> prodStatTypes,
		@Param("createDateStr") String createDateStr,
		@Param("pdId") String pdId,
		@Param("dcPrjId") String dcPrjId
	);
	
}