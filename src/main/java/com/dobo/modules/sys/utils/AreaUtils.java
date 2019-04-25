/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">dobo</a> All rights reserved.
 */
package com.dobo.modules.sys.utils;

import java.util.Map;

import com.dobo.common.utils.CacheUtils;
import com.dobo.common.utils.SpringContextHolder;
import com.dobo.modules.sys.dao.AreaDao;
import com.dobo.modules.sys.entity.Area;
import com.google.common.collect.Maps;

/**
 * 区域缓存工具类
 */
public class AreaUtils {
	
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);

	public static final String CACHE_AREA_CODE_MAP = "areaCodeMap";
	
	public static final String CACHE_AREA_ID_MAP = "areaIdMap";

	/**
	 * 通过不同区域代码获取对应的省份，省份代码的级别为2
	 * @param code
	 * @return
	 */
	public static Area getProvince(String code){
		
		Area area = getAreaMapByCode().get(code);
		
		if(Integer.valueOf(area.getType()) <= 2) {
			return area;
		} else {
			return getProvinceById(area.getParentId());
		}
	}
	
	/**
	 * 通过不同区域代码获取对应的省份，省份代码的级别为2
	 * @param id
	 * @return
	 */
	public static Area getProvinceById(String id){
		
		Area area = getAreaMapById().get(id);
		
		if(Integer.valueOf(area.getType()) <= 2) {
			return area;
		} else {
			return getProvinceById(area.getParentId());
		}
	}
	
	/**
	 * 返回区域列表,注：区域代码，不是区域ID
	 * @return
	 */
	public static Map<String, Area> getAreaMapByCode(){
		@SuppressWarnings("unchecked")
		Map<String, Area> areaMap = (Map<String, Area>)CacheUtils.get(CACHE_AREA_CODE_MAP);
		if (areaMap==null){
			areaMap = Maps.newHashMap();
			for (Area area : areaDao.findAllList(new Area())){
				areaMap.put(area.getCode(), area);
			}
			CacheUtils.put(CACHE_AREA_CODE_MAP, areaMap);
		}
		
		return areaMap;
	}
	
	/**
	 * 返回区域列表,注：区域代码，不是区域ID
	 * @return
	 */
	public static Map<String, Area> getAreaMapById(){
		@SuppressWarnings("unchecked")
		Map<String, Area> areaMap = (Map<String, Area>)CacheUtils.get(CACHE_AREA_ID_MAP);
		if (areaMap==null){
			areaMap = Maps.newHashMap();
			for (Area area : areaDao.findAllList(new Area())){
				areaMap.put(area.getId(), area);
			}
			CacheUtils.put(CACHE_AREA_ID_MAP, areaMap);
		}
		
		return areaMap;
	}
	
}
