package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCUtil {
	private static ResourceBundle bundle = null;
	
	static {
		bundle = ResourceBundle.getBundle("db");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패..");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(
					bundle.getString("url"), 
					bundle.getString("user"),
					bundle.getString("pass"));
		} catch (SQLException e) {
			System.out.println("접속 실패..");
			e.printStackTrace();
		}
		return conn;
	}
}
