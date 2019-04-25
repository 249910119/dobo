package com.dobo.common.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class DBConn {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Connection getConnForCfg(){
		Connection conn = null;
		try {
			conn = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	} 

    public static Connection getConnForNative(){
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@10.126.2.62:1521/pwbmtest";
        String user = "dobo";
        String password = "doboserver198198";

        /*String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@10.1.180.198:1521:wbmtest";
        String user = "dobo";
        String password = "dobotest321";*/
        
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
}
