package com.dobo.common.utils.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dobo.common.utils.Collections3;
import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.StringUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * 基于opencsv实现的Csv工具类
 *
 */
public class OpenCsvHelper extends CsvHelper {

	private static OpenCsvHelper instance = null; // 单例对象

	private File file; // 操作文件

	/**
	 * 私有化构造方法
	 *
	 * @param file
	 *            文件对象
	 */
	private OpenCsvHelper(File file) {
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
	public static OpenCsvHelper getInstance(File file) {
		if (instance == null) {
			// 当单例对象为null时进入同步代码块
			synchronized (OpenCsvHelper.class) {
				// 再次判断单例对象是否为null，防止多线程访问时多次生成对象
				if (instance == null) {
					instance = new OpenCsvHelper(file);
				}
			}
		} else {
			// 如果操作的文件对象不同，则重置文件对象
			if (!file.equals(instance.getFile())) {
				instance.setFile(file);
			}
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
	public static OpenCsvHelper getInstance(String filePath) {
		return getInstance(new File(filePath));
	}

	@Override
	public List<String[]> readCsv(boolean hasTitle) throws Exception {
        List<String[]> list = new ArrayList<String[]>();
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "GBK"), 
	        		CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
			list.add(reader.readNext());   //用readnext读取之后就不存在于stream中了
			list.addAll(reader.readAll()); //此时读取的已经是第二行了
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return list;
	}

	@Override
	public <T> void writeCsv(String[] titles, List<String[]> lists) throws IOException {
		long start1 = System.currentTimeMillis();

		// 检测文件是否存在，如果存在则修改文件，否则创建文件
		if (!file.exists()) {
			file.createNewFile();
		}
		
		CSVWriter writer = null;
		try {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file),"GBK"),
					CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
			
			writer.writeNext(titles);
			writer.writeAll(lists);
			writer.flush();
			writer.close();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		}

		long start2 = System.currentTimeMillis();
		System.out.println("csv文件写入CSV耗时：" + DateUtils.getDistanceOfTwoDate(new Date(start2), new Date(start1)) + ","+file.getAbsolutePath());
	}

	@Override
	public <T> void writeCsv(Class<T> clazz, List<T> lists, String[] fieldNames, String[] titles, int splitCount) 
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		List<List<T>> sublist = Collections3.splitList(lists, splitCount);
		String fileFullName = file.getAbsolutePath();
		for (int i=0;i<sublist.size();i++) {
			List<T> dataList = sublist.get(i);
			String newFileFullName = StringUtils.trimFileExtension(fileFullName) + "_" + i + ".csv";
			if(i==0) {
				newFileFullName = StringUtils.trimFileExtension(fileFullName) + ".csv";
			}
			
			//将对象转换成数组对象
			List<String[]> listarray = new ArrayList<String[]>();
			for (T pod : dataList) {
				listarray.add(StringUtils.toArray(clazz, pod, fieldNames));
			}
			//dataList.clear();
			
			//写入csv文件
			getInstance(new File(newFileFullName)).writeCsv(titles, listarray);
		}
	}

}
