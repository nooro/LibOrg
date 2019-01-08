package dbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import liborg.User;

public class UsersOperator {

	public static DBConnector.Result logIn(DBConnector db, String username, String password) {
		if(!db.isConnected()) {
			return DBConnector.Result.NO_SERVER_CONNECTION;
		}

		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username and password
			if( result.next() ) {
				User.logIn(result.getInt("id"), result.getString("username"), result.getString("email"), result.getBoolean("is_admin"));
				return DBConnector.Result.LOGGED_IN;
			} else {
				User.logOut();
				return DBConnector.Result.LOG_IN_INVALID_DATA;
			}
			
		} catch (SQLException e) {
			return DBConnector.Result.SQL_ERROR;
		}
	}

	public static DBConnector.Result register(DBConnector db, String username, String password, String email) {
		if(!db.isConnected()) {
			return DBConnector.Result.NO_SERVER_CONNECTION;
		}
		 
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT id FROM users WHERE username = '" + username + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username already
			if( result.next() ) {
				return DBConnector.Result.REGISTER_USERNAME_TAKEN;
			} 
			
			query = "INSERT INTO `users` (`id`, `username`, `password`, `email`, `is_admin`) VALUES (NULL, '"+username+"', '"+password+"', '"+email+"', '0');";
			statement.executeUpdate(query);
			
			//Check if the user is actually registered
			query = "SELECT id FROM users WHERE username = '" + username + "';";
			result = statement.executeQuery(query);
			if( result.next() ) {
				User.logIn(result.getInt("id"), username, email, false);
				return DBConnector.Result.REGISTERED;
			} 
			else return DBConnector.Result.SQL_ERROR;
			
		} catch (SQLException e) {
			return DBConnector.Result.NO_SERVER_CONNECTION;
		}
	}
}
