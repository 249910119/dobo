/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.common.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.dobo.common.persistence.CrudDao;
import com.dobo.common.persistence.DataEntity;
import com.dobo.common.persistence.Page;
import com.dobo.common.utils.Reflections;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		//新增记录
		if (entity.getIsNewRecord()){
			updateNewRecord(entity);
			entity.preInsert();
			dao.insert(entity);
		}else{
			Object saveFlagObj = null;
			Object statusObj = null;
			try {
				saveFlagObj = Reflections.getFieldValue(entity, "saveFlag");
				statusObj = Reflections.getFieldValue(entity, "status");
			} catch (Exception e) {
				e.printStackTrace();
			}
			//加时间戳更新记录需新增一条记录，且为有效记录
			if(StringUtils.equals((String)saveFlagObj, "0") && StringUtils.equals((String)statusObj, "A0")){
				//修改前数据库记录
				T entitySource  = this.get(entity.getId());
				entity.preInsert();
				updateNewRecord(entity);
				dao.insert(entity);
				
				//原记录
				//加时间戳修改时不修改原记录的更新时间
				//entity.preUpdate();
				updateOldRecord(entitySource, entitySource.getCreateDate());
				dao.update(entitySource);
			}else{
				entity.preUpdate();
				dao.update(entity);
			}
		}
	}

	/**
	 * 更新原记录默认状态字段
	 * @param entity
	 * @param isNewRecord
	 */
	private void updateOldRecord(T entity, Date newRecordCreateDate){
		if(Reflections.getAccessibleField(entity, "status") != null)
			Reflections.invokeSetter(entity, "status", "A1");
		if(Reflections.getAccessibleField(entity, "preStatus") != null)
			Reflections.invokeSetter(entity, "preStatus", "A0");
		if(Reflections.getAccessibleField(entity, "statusChangeDate") != null)
			Reflections.invokeSetter(entity, "statusChangeDate", newRecordCreateDate);
		if(Reflections.getAccessibleField(entity, "saveFlag") != null)
			Reflections.invokeSetter(entity, "saveFlag", "0");
	}
	
	/**
	 * 更新新插入记录默认状态字段
	 * @param entity
	 */
	private void updateNewRecord(T entity){
		try {
			if(Reflections.getAccessibleField(entity, "preStatus") != null && Reflections.getValueByFieldName(entity, "status") == null)
				Reflections.invokeSetter(entity, "status", "A0");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if(Reflections.getAccessibleField(entity, "preStatus") != null)
			Reflections.invokeSetter(entity, "preStatus", null);
		if(Reflections.getAccessibleField(entity, "statusChangeDate") != null)
			Reflections.invokeSetter(entity, "statusChangeDate", null);
		if(Reflections.getAccessibleField(entity, "saveFlag") != null){
			if (entity.getIsNewRecord()) {
				Reflections.invokeSetter(entity, "saveFlag", "1");
			} else {
				Reflections.invokeSetter(entity, "saveFlag", "0");
			}
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

}
