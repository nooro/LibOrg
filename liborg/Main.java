package liborg;

import dbConnection.*;
import ui.LogInForm;

public class Main {
	
	public static void main( String[] args) {

		DBConnector db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
		new LogInForm(db);
		
	}
	
}
