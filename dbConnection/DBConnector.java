package dbConnection;

import java.sql.*;
import exceptions.NoServerConnectionException;


public class DBConnector {
	
	private Connection connection;
	
	public DBConnector(String dbDriver, String url, String user, String password) throws NoServerConnectionException {
		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(url, user, password);
		} 
		catch (ClassNotFoundException | SQLException e) {
			throw new NoServerConnectionException();
		} 
		
	}
	
	public Connection getConnection () { return this.connection; }
	
	public void closeConnection() { 
		try {
			this.connection.close();
		} catch (SQLException e) {}
	}
	
}
