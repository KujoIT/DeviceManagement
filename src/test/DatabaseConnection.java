package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseConnection {
	private static final String hostName = "localhost";
	private static final String dbName = "DeviceList";
	private static final String userName = "root";
	private static final String password = "kujo1207"; // 
	private static final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			connection = DriverManager.getConnection(connectionURL, userName, password);
		}
		return connection;	
	}
	

	public static boolean isValidId(int id) throws SQLException {
	         Connection conn = DatabaseConnection.getConnection();
	         String sql = "SELECT * FROM device WHERE id = ?";
	         PreparedStatement ppst = conn.prepareStatement(sql);
	         ppst.setInt(1, id);  // 1: dau ? dau tien, 
	         ResultSet result = ppst.executeQuery();
	         if (result.next()) { // kiểm tra nếu có ít nhất một dòng trong kết quả
	             return false;
	         }
	         return true;
	}
 
}
