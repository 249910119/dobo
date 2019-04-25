package com.dobo.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.dobo.common.utils.DateUtils;
import com.dobo.common.utils.Reflections;
import com.dobo.common.utils.StringUtils;
import com.dobo.modules.cst.entity.detail.CstOrderCostInfo;
import com.dobo.modules.sys.entity.User;
import com.google.common.collect.Lists;

public class InsertTableDemo {

    public static void main(String args[]) throws SQLException {
        Connection connection = null;
        List<String> dataList = getDataList(300000);
        long startTime = 0;
        try{
            connection = getConn();
            startTime=System.currentTimeMillis();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO aa_detail (detail_id) VALUES ( ? ) ");
            int num = 0;
            for (int i = 0;i< dataList.size();i++){
                String s = dataList.get(i);
                statement.setString(1, s);
                statement.addBatch();
                num++;
                if(num !=0 && num%2000 == 0){
                    statement.executeBatch();
                    connection.commit();
                    num = 0;
                }
            }
            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            if(connection != null){
                connection.close();
            }
            long endTime=System.currentTimeMillis();
            System.out.println("方法执行时间："+(endTime-startTime)+"ms");
        }

    }

    public static void nativeBatchInsert(List<CstOrderCostInfo> cstOrderCostInfoList) throws SQLException {
    	StringBuilder prepareInsertSql = new StringBuilder("INSERT INTO dobo.cst_order_cost_info (");
    	String[] insertFieldNames = CstOrderCostInfo.insertFieldNames;
    	StringBuilder marks = new StringBuilder();
    	for(String fieldName : insertFieldNames){
    		prepareInsertSql.append(StringUtils.toUnderScoreCase(fieldName)).append(",");
    		marks.append("?,");
    	}
    	prepareInsertSql.substring(0, prepareInsertSql.length()-1);//去掉最后的逗号
    	prepareInsertSql.append(") VALUES (").append(marks);
    	prepareInsertSql.substring(0, prepareInsertSql.length()-1);//去掉最后的逗号
    	prepareInsertSql.append(")");
    	
        Connection connection = null;
        long startTime = 0;
        try{
            connection = getConn();
            startTime=System.currentTimeMillis();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(prepareInsertSql.toString());
            int num = 0;
            for (int i = 0;i< cstOrderCostInfoList.size();i++){
            	CstOrderCostInfo cstOrder = cstOrderCostInfoList.get(i);

            	for(int j=1;j<=insertFieldNames.length;j++){
            		String fieldName = insertFieldNames[j];
            		Field field = Reflections.getFieldByFieldName(cstOrder, fieldName);
            		Object fieldValue = Reflections.getFieldValue(cstOrder, fieldName);
            		if(field.getType() == String.class){
                        statement.setString(j, fieldValue.toString());
            		}else if(field.getType() == Double.class){
                        statement.setDouble(j, Double.valueOf(fieldValue.toString()));
            		}else if(field.getType() == Double.class){
                        statement.setDate(j, new java.sql.Date(DateUtils.parseDate(fieldValue.toString()).getTime()));
            		}else if(field.getType() == User.class){
                        statement.setString(j, "admin");
            		}
            	}
            	
                statement.addBatch();
                num++;
                if(num !=0 && num%2000 == 0){
                    statement.executeBatch();
                    connection.commit();
                    num = 0;
                }
            }
            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            if(connection != null){
                connection.close();
            }
            long endTime=System.currentTimeMillis();
            System.out.println("执行时间："+(endTime-startTime)+"ms");
        }
    }

    public static Connection getConn(){
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@//10.1.180.198:1521/wbmtest";
        String user = "dobo";
        String password = "dobotest321";
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> getDataList(int f){
        List<String> data = Lists.newArrayList();
        for (int i =0;i<f;i++){
            data.add("测试"+i);
        }
        return data;
    }


}