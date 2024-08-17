package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class HelloJDBC {
	private static final String hostName = "localhost";
	private static final String dbName = "jdbc";
	private static final String userName = "root";
	private static final String password = "kujo1207"; // password mysql ca nhan
	// jdbc:mysql://hostname:port/dbname
	private static final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// 2. Open connection
		Connection con = DriverManager.getConnection(connectionURL, userName, password);

		DatabaseMetaData metaData = con.getMetaData();
		System.err.println(metaData.getDriverName());
		System.err.println(metaData.getUserName());

		// 3. Create Statement
        Statement st = con.createStatement();

		// 4. Execute query
        String sqlInsert = "INSERT INTO user(username, password, createdDAte) VALUE('gpcaaaaaeesdaoder', '1243', now());";
        int numberRowsAffected = st.executeUpdate(sqlInsert);
        if (numberRowsAffected == 0) {
            System.out.println("insertion failed");
        } else {
            System.out.println("inserted successfully : " + numberRowsAffected);
        }
        st.close();

		// 5. Close connection
		con.close();
	}
}