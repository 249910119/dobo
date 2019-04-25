package com.dobo.common.utils.excel;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

import com.dobo.common.utils.ReflectHelper;

/**
 * Excel工具抽象类
 * 
 * @param <T> 操作数据类型
 */
public abstract class ExcelHelper {
	
	/**
	 * 对象序列化版本号名称
	 */
	public static final String UID = "serialVersionUID";

	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * 
	 * @param clazz 数据类型
	 * @param sheetNo 工作表编号
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(Class<T> clazz, int sheetNo, boolean hasTitle)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		return readExcel(clazz, fieldNames, sheetNo, hasTitle);
	}

	/**
	 * 将指定excel文件中的数据转换成数据列表
	 * 
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			boolean hasTitle) throws Exception {
		return readExcel(clazz, fieldNames, 0, hasTitle);
	}

	/**
	 * 抽象方法：将指定excel文件中的数据转换成数据列表，由子类实现
	 * 
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param sheetNo 工作表编号
	 * @param hasTitle 是否带有标题
	 * @return 返回转换后的数据列表
	 * @throws Exception
	 */
	public abstract <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception;

	/**
	 * 写入数据到指定excel文件中
	 * 
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @throws Exception
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(clazz, dataModels, fieldNames, fieldNames, null);
	}

	/**
	 * 写入数据到指定excel文件中
	 * 
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param sheetName sheet名称
	 * @throws Exception
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels, String sheetName)
			throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(clazz, dataModels, fieldNames, fieldNames, sheetName);
	}

	/**
	 * 写入数据到指定excel文件中
	 * 
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @throws Exception
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels, String[] fieldNames) 
			throws Exception {
		writeExcel(clazz, dataModels, fieldNames, fieldNames, null);
	}

	/**
	 * 写入数据到指定excel文件中
	 * 
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param sheetName sheet名称
	 * @throws Exception
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels, String[] fieldNames, String sheetName) 
			throws Exception {
		writeExcel(clazz, dataModels, fieldNames, fieldNames, sheetName);
	}

	/**
	 * 写入数据到指定excel文件中
	 * 
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @throws Exception
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels, 
			String[] fieldNames, String[] titles) throws Exception {
		writeExcel(clazz, dataModels, fieldNames, fieldNames, null);
	}

	/**
	 * 抽象方法：写入数据到指定excel文件中，由子类实现
	 * 
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles 标题列表
	 * @throws Exception
	 */
	public abstract <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles, String sheetName) throws Exception;

	/**
	 * 判断传入属性类型与指定的是否一致
	 * 
	 * @param clazz 数据类型
	 * @param fieldName 属性名
	 * @param inFieldType 需要判定的属性类型 如：java.lang.Double
	 * 
	 * @return 如果返回一致返回true，否则返回false
	 */
	protected <T> boolean isSameFieldType(Class<T> clazz, String fieldName, String inFieldType) {
		try {
			return inFieldType.equals(ReflectHelper.getFieldType(clazz, fieldName));
		} catch (Exception e) {
			return false;
		}
	}

	protected void setCellStyleWithDate(Workbook workbook, Cell cell, CellStyle cellStyle, DataFormat format) {
        cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
        cell.setCellStyle(cellStyle);
	}

	/**
	 * 根据类型将指定参数转换成对应的类型
	 * 
	 * @param value 指定参数
	 * @param type 指定类型
	 * @return 返回类型转换后的对象
	 */
	protected <T> Object parseValueWithType(String value, Class<?> type) {
		Object result = null;
		try { // 根据属性的类型将内容转换成对应的类型
			if (Boolean.TYPE == type) {
				result = Boolean.parseBoolean(value);
			} else if (Byte.TYPE == type) {
				result = Byte.parseByte(value);
			} else if (Short.TYPE == type) {
				result = Short.parseShort(value);
			} else if (Integer.TYPE == type) {
				result = Integer.parseInt(value);
			} else if (Long.TYPE == type) {
				result = Long.parseLong(value);
			} else if (Float.TYPE == type) {
				result = Float.parseFloat(value);
			} else if (Double.TYPE == type) {
				result = Double.parseDouble(value);
			} else {
				result = (Object) value;
			}
		} catch (Exception e) {
			// 把异常吞掉直接返回null
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		/*long start00 = System.currentTimeMillis();	
		
		BusinTypeSort p = new BusinTypeSort();
		System.out.println(ReflectHelper.getFieldNames(p));
		
		List<BusinTypeSort> list = new ArrayList<BusinTypeSort>();
		for(int i=0;i<3000;i++){
			list.add(new BusinTypeSort("BusinTypeSort"+i, "BusinTypeSort", BigDecimal.valueOf(i), new Date(), "admin", "remark"));
		}

		long start01 = System.currentTimeMillis();	
		System.out.println(list.size()+"条数据,"+ReflectHelper.getFieldNames(p).size()+"个字段 构造数据耗时---------" + DataUtil.getTimeDifference(new Date(start01), new Date(start00)));

		String[] titles = new String[]{"主键", "名称", "seq", "modifyDate", "modifyStaff", "remark"};
		String[] fieldNames = new String[]{"businsortId", "businsortName", "seq", "modifyDate", "modifyStaff", "remark"};

		long start1 = System.currentTimeMillis();
		File file1 = new File("E:\\JXL2003.xls");
		ExcelHelper eh1 = JxlExcelHelper.getInstance(file1);
		//eh1.writeExcel(BusinTypeSort.class, list);
		eh1.writeExcel(BusinTypeSort.class, list, fieldNames, titles);
		//List<BusinTypeSort> list1 = eh1.readExcel(BusinTypeSort.class, fieldNames, true);
		long start2 = System.currentTimeMillis();	
		System.out.println("JXL2003---------" + DataUtil.getTimeDifference(new Date(start2), new Date(start1)));
		
		File file2 = new File("E:\\POI2003.xls");
		ExcelHelper eh2 = HssfExcelHelper.getInstance(file2);
		//eh2.writeExcel(BusinTypeSort.class, list);
		eh2.writeExcel(BusinTypeSort.class, list, fieldNames, titles);
		//List<BusinTypeSort> list2 = eh2.readExcel(BusinTypeSort.class, fieldNames, true);
		long start3 = System.currentTimeMillis();	
		System.out.println("POI2003---------" + DataUtil.getTimeDifference(new Date(start3), new Date(start2)));
		
		File file3 = new File("E:\\POI2007.xlsx");
		ExcelHelper eh3 = XssfExcelHelper.getInstance(file3);
		//eh3.writeExcel(BusinTypeSort.class, list);
		eh3.writeExcel(BusinTypeSort.class, list, fieldNames, titles);
		//List<BusinTypeSort> list3 = eh3.readExcel(BusinTypeSort.class, fieldNames, true);
		long start4 = System.currentTimeMillis();	
		System.out.println("POI2007---------" + DataUtil.getTimeDifference(new Date(start4), new Date(start3)));
		
		File file4 = new File("E:\\POI2007S.xlsx");
		ExcelHelper eh4 = SxssfExcelHelper.getInstance(file4);
		//eh4.writeExcel(BusinTypeSort.class, list);
		eh4.writeExcel(BusinTypeSort.class, list, fieldNames, titles);
		//List<BusinTypeSort> list4 = eh4.readExcel(BusinTypeSort.class, fieldNames, true);
		long start5 = System.currentTimeMillis();	
		System.out.println("POI2007S---------" + DataUtil.getTimeDifference(new Date(start5), new Date(start4)));
*/
		/*for (BusinTypeSort user : list4) {
			System.out.println(user);
		}*/
		
		/*10000条数据50个字段 构造数据耗时---------47毫秒
		JXL2003---------28秒980毫秒
		POI2003---------4秒741毫秒
		POI2007---------39秒668毫秒
		POI2007S---------21秒347毫秒*/
	}

}
