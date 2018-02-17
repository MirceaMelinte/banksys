package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersistenceConfig {
	
	final static String connectString = "jdbc:oracle:thin:@localhost:1521:orcl";
	final static String userName = "mskn";
	final static String password = "admin";
	
	public static Connection establishConnection(Connection connection) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			connection = DriverManager.getConnection(connectString, userName, password);
			
			System.out.println("Connection to [" + connectString + "] successfully established. ");
			
		} catch (SQLException e) {
			
			System.out.println("Error establishing the connection. ");
			System.out.println("Connection string in use: " + connectString + "(user/pwd " + userName + "/" + password + ")" );
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			try {
				connection.close();
			} catch (SQLException e1) {
				System.out.println("Error closing the connection. ");
				e1.printStackTrace();
			}
		}
		
		return connection;
	}
}