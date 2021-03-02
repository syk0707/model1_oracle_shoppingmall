package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBOpen {

	public static Connection open() {
		Connection con = null;
		
		try {
			Class.forName(Constant.driver);
			con = DriverManager.getConnection(Constant.url, Constant.user, Constant.password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
}
