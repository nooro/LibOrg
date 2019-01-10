package liborg;

import dbConnection.DBConnector;
import exceptions.NoServerConnectionException;
import ui.LogInForm;

public class Main {
	
	public static void main( String[] args) {

		try {
			DBConnector db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
			new LogInForm(db);
		} catch (NoServerConnectionException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
