package com.dobo.common.utils.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.ReflectHelper;
import com.dobo.common.utils.StringUtils;


/**
 * 基于POI实现的Excel工具类 2007 大量数据，优化内存
 *
 */
public class SxssfExcelHelper extends ExcelHelper {

	private static SxssfExcelHelper instance = null; // 单例对象

	private File file; // 操作文件

	/**
	 * 私有化构造方法
	 *
	 * @param file
	 *            文件对象
	 */
	private SxssfExcelHelper(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 获取单例对象并进行初始化
	 *
	 * @param file
	 *            文件对象
	 * @return 返回初始化后的单例对象
	 */
	public static SxssfExcelHelper getInstance(File file) {
		if (instance == null) {
			// 当单例对象为null时进入同步代码块
			synchronized (SxssfExcelHelper.class) {
				// 再次判断单例对象是否为null，防止多线程访问时多次生成对象
				if (instance == null) {
					instance = new SxssfExcelHelper(file);
				}
			}
		} else {
			// 如果操作的文件对象不同，则重置文件对象
			if (!file.equals(instance.getFile())) {
				instance.setFile(file);
			}
		}
		if (file != null && !file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		return instance;
	}

	/**
	 * 获取单例对象并进行初始化
	 *
	 * @param filePath
	 *            文件路径
	 * @return 返回初始化后的单例对象
	 */
	public static SxssfExcelHelper getInstance(String filePath) {
		return getInstance(new File(filePath));
	}

	@Override
	public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception {
		List<T> dataModels = new ArrayList<T>();
		// 获取excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(file)));
		XSSFSheet sheet = workbook.getSheetAt(sheetNo);
		int start = sheet.getFirstRowNum() + (hasTitle ? 1 : 0); // 如果有标题则从第二行开始
		try {
			for (int i = start; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				// 生成实例并通过反射调用setter方法
				T target = clazz.newInstance();
				for (int j = 0; j < fieldNames.length; j++) {
					String fieldName = fieldNames[j];
					if (fieldName == null || UID.equals(fieldName)) {
						continue; // 过滤serialVersionUID属性
					}
					// 获取excel单元格的内容
					XSSFCell cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					String content = getCellContent(cell);
					// 如果属性是日期类型则将内容转换成日期对象
					if (isSameFieldType(clazz,fieldName,"java.util.Date")) {
						// 如果属性是日期类型则将内容转换成日期对象
						ReflectHelper.setValueByFieldName(target, fieldName, DateUtils.parseDate(content,"yyyy-MM-dd"));
					} else {
						Field field = clazz.getDeclaredField(fieldName);
						ReflectHelper.setValueByFieldName(target, fieldName, parseValueWithType(content, field.getType()));
					}
				}
				dataModels.add(target);
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return dataModels;
	}

	@Override
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles, String sheetName) throws Exception {
		SXSSFWorkbook workbook = null;
		// 检测文件是否存在，如果存在则修改文件，否则创建文件
		if (file.exists()) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
			workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
		} else {
			workbook = new SXSSFWorkbook(1000);
		}
		
		ZipSecureFile.setMinInflateRatio(0L);

		//处理工作表sheet名称
		if(StringUtils.isBlank(sheetName)){
			sheetName = DateUtils.formatDate(new Date(), "yyMMddHHmmssSS");
		}else if(workbook.getSheet(sheetName) != null){
			sheetName += "_"+(workbook.getNumberOfSheets() + 1);
		}
		
		Sheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format= workbook.createDataFormat();
		SXSSFRow headRow = (SXSSFRow)sheet.createRow(0);
		// 添加表格标题
		for (int i = 0; i < titles.length; i++) {
			SXSSFCell cell = headRow.createCell(i);
			//cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
			// 设置字体加粗
			//CellStyle cellStyle = workbook.createCellStyle();
			/*Font font = workbook.createFont();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);*/
			// 设置自动换行
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			// 设置单元格宽度
			sheet.setColumnWidth(i, titles[i].length() * 1000);
		}
		// 添加表格内容
		for (int i = 0; i < dataModels.size(); i++) {
			T target = dataModels.get(i);
			SXSSFRow row = (SXSSFRow)sheet.createRow(i + 1);
			// 遍历属性列表
			for (int j = 0; j < fieldNames.length; j++) {
				// 通过反射获取属性的值域
				String fieldName = fieldNames[j];
				if (fieldName == null || UID.equals(fieldName)) {
					continue; // 过滤serialVersionUID属性
				}
				Object result = ReflectHelper.getValueByFieldName(target, fieldName);
				
				SXSSFCell cell = row.createCell(j);
				// 如果是日期类型则进行格式化处理
				if (isSameFieldType(clazz,fieldName,"java.util.Date")) {
					if(result == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue((Date) result);
					}
					setCellStyleWithDate(workbook,cell,cellStyle,format);
				}else if (isSameFieldType(clazz,fieldName,"java.math.BigDecimal")
						|| isSameFieldType(clazz,fieldName,"java.lang.Double")
						|| isSameFieldType(clazz,fieldName,"java.lang.Integer")
						|| isSameFieldType(clazz,fieldName,"java.lang.Long")){
					String results = StringUtils.toString(result);
					if(StringUtils.isBlank(results)){
						results = "0";
					}
					cell.setCellValue(Double.parseDouble(results));
				}else{
					cell.setCellValue(StringUtils.toString(result));
				}
			}
		}
		// 将数据写到磁盘上
		FileOutputStream fos = new FileOutputStream(file);
		try {
			workbook.write(new FileOutputStream(file));
		} finally {
			if (fos != null) {
				fos.close(); // 不管是否有异常发生都关闭文件输出流
			}

			if(workbook != null){
				workbook.close();
			}
		}
	}

	@Override
	protected <T> Object parseValueWithType(String value, Class<?> type) {
		// 由于Excel2007的numeric类型只返回double型，所以对于类型为整型的属性，要提前对numeric字符串进行转换
		if (Byte.TYPE == type || Short.TYPE == type || Integer.TYPE == type || Long.TYPE == type) {
			value = String.valueOf((long) Double.parseDouble(value));
		}
		return super.parseValueWithType(value, type);
	}

	/**
	 * 获取单元格的内容
	 *
	 * @param cell 单元格
	 * @return 返回单元格内容
	 */
	@SuppressWarnings("deprecation")
	private String getCellContent(Cell cell) {
		StringBuffer buffer = new StringBuffer();
		switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC : // 数字
				buffer.append(cell.getNumericCellValue());
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN : // 布尔
				buffer.append(cell.getBooleanCellValue());
				break;
			case XSSFCell.CELL_TYPE_FORMULA : // 公式
				buffer.append(cell.getCellFormula());
				break;
			case XSSFCell.CELL_TYPE_STRING : // 字符串
				buffer.append(cell.getStringCellValue());
				break;
			case XSSFCell.CELL_TYPE_BLANK : // 空值
			case XSSFCell.CELL_TYPE_ERROR : // 故障
			default :
				break;
		}
		return buffer.toString();
	}
}
