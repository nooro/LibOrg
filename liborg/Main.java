package liborg;

import dbConnection.DBConnector;
import ui.LogInForm;

public class Main {
	
	public static void main( String[] args) {

		DBConnector db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
		LogInForm login = new LogInForm(db);
	}
	
}
