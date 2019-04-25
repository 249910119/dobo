/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.cop.dao.detail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.annotation.MyBatisDao;
import com.dobo.modules.cop.entity.detail.CopCaseDetail;

/**
 * top单次、先行支持服务报价清单DAO接口
 * @author admin
 * @version 2017-06-08
 */
@MyBatisDao
public interface CopCaseDetailDao extends CrudDao<CopCaseDetail> {

	/**
	 * 查询所有数据列表
	 * 
	 * @param createDateStr
	 * @param dcPrjId
	 * @return
	 */
	public List<CopCaseDetail> getCaseConfirmList(
		@Param("createDateStr") String createDateStr,
		@Param("onceCode") String onceCode
	);
	
	/**
	 * 查询所有数据列表
	 * 
	 * @param onceCodes
	 * @param status
	 * @return
	 */
	public List<CopCaseDetail> getCaseBatch(
		@Param("onceCodes") List<String> onceCodes,
		@Param("status") String status
	);
}