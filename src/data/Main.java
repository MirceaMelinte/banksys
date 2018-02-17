package data;

import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws SQLException {
		DataServer server = new DataServer();
		server.executeTest();
	}
}
