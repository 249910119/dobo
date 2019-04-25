package com.dobo.common.utils.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dobo.common.utils.StringUtils;

/**
 * Csv工具抽象类
 * 
 * @param <T> 操作数据类型
 */
public abstract class CsvHelper {
	
	/**
	 * 对象序列化版本号名称
	 */
	public static final String UID = "serialVersionUID";

	/**
	 * 抽象方法：将指定excel文件中的数据转换成数据列表，由子类实现
	 * 
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public abstract List<String[]> readCsv(boolean hasTitle) throws Exception;

	/**
	 * 抽象方法：写入数据到指定csv文件中，由子类实现
	 * 
	 * @param titles 标题列表
	 * @param lists 数据列表
	 * @throws Exception
	 */
	public abstract <T> void writeCsv(String[] titles, List<String[]> lists) throws IOException;
	
	/**
	 * 抽象方法：写入数据到指定csv文件中，由子类实现
	 * 
	 * @param clazz javabean数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles 标题列表
	 * @throws Exception
	 */
	public <T> void writeCsv(Class<T> clazz, List<T> dataModels, String[] fieldNames, String[] titles) throws Exception{
		//将对象转换成数组对象
		List<String[]> lists = new ArrayList<String[]>();
		for (T pod : dataModels) {
			lists.add(StringUtils.toArray(clazz, pod, fieldNames));
		}
		
		writeCsv(titles, lists);
	}

	/**
	 * 抽象方法：写入数据到指定csv文件中，由子类实现
	 * 
	 * @param clazz javabean数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles 标题列表
	 * @param splitCount 拆分行数
	 * @throws Exception
	 */
	public abstract <T> void writeCsv(Class<T> clazz, List<T> dataModels, String[] fieldNames, String[] titles, int splitCount) 
			throws IOException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException;

	public static void main(String[] args) throws Exception {
		String[] aa = {"as","resourceType","dd3","dd2","dd44","dd11"};
		int p = Arrays.binarySearch(aa, "resourceType");
		for(int i=0;i<aa.length;i++){
			if(aa[i].equals("resourceType")){
				System.out.println(i);
			}
		}
		System.out.println(p);
		System.out.println(aa[1]);
	}

}
