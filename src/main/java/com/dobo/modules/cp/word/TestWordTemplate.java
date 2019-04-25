package com.dobo.modules.cp.word;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestWordTemplate {
	
	private static WordTemplate template;
	//private static final String DOCX_MODEL_PATH = "doc/model.docx";
	//private static final String DOCX_FILE_WRITE = "doc/replaceModelWord.docx";
	public final String DOCX_MODEL_7_PATH = "doc/staff_7.docx";
	public final String DOCX_MODEL_7_NOLOW_PATH = "doc/staff_7_nolow.docx";
	public final String DOCX_MODEL_7_GROUP_PATH = "doc/staff_group7.docx";
	private static final String DOCX_FILE_WRITE = "doc/";
	public final String DOCX_MODEL_6_PATH = "doc/staff_6.docx";
	public final String DOCX_MODEL_6_NOLOW_PATH = "doc/staff_6_nolow.docx";
	public final String DOCX_MODEL_6_GROUP_PATH = "doc/staff_group6.docx";
	public final String DOCX_MODEL_5_PATH = "doc/staff_5.docx";
	public final String DOCX_MODEL_5_NOLOW_PATH = "doc/staff_5_nolow.docx";
	public final String DOCX_MODEL_5_GROUP_PATH = "doc/staff_group5.docx";
	private static Map<String,String> map = new HashMap<String, String>();
	
	//@BeforeClass
	public void init(String filepath){
		File file = new File(filepath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			template = new WordTemplate(fileInputStream);
		} catch(IOException exception){
			exception.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//设置参数
		/*map.put("title", "docx模板生成的内容");
		map.put("user1", "张三");
		map.put("user2", "李四");
		map.put("text1", "上海");
		map.put("text2", "广州");
		map.put("cell1", "1行1列");
		map.put("cell2", "1行2列");
		map.put("cell3", "2行1列");
		map.put("cell4", "2行2列");
		map.put("year", "2016");
		map.put("month", "04");
		map.put("day", "03");*/
	}
	
	public void testReplaceTag(Map<String, String> map){
		template.replaceTag(map);
	}
	
	//@Test
	public void testWrite(String jobLevelId, String fileName){
		//testReplaceTag();
		String filePath = DOCX_FILE_WRITE+jobLevelId+"/"+fileName;
		File file = new File(filePath);
		FileOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			out = new FileOutputStream(file);
			bos = new BufferedOutputStream(out);
			template.write(bos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
