package dbConnection;

import java.sql.*;
import liborg.User;
import exceptions.*;

public class UsersOperator {

	public static void logIn(DBConnector db, String username, String password) throws NoServerConnectionException, InvalidLogInDataException {
		
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username and password
			if( result.next() ) {
				User.logIn(result.getInt("id"), result.getString("username"), result.getString("email"), result.getBoolean("is_admin"));
			} else {
				User.logOut();
				throw new InvalidLogInDataException();
			}
			
		} catch (SQLException e) {
			throw new NoServerConnectionException();
		}
		
	}
	

	public static void register(DBConnector db, String username, String password, String email) throws NoServerConnectionException, UsernameIsTakenException {
		
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT id FROM users WHERE username = '" + username + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username already
			if( result.next() ) {
				throw new UsernameIsTakenException();
			} 
			
			query = "INSERT INTO `users` (`id`, `username`, `password`, `email`, `is_admin`) VALUES (NULL, '"+username+"', '"+password+"', '"+email+"', '0');";
			statement.executeUpdate(query);
			
			//Check if the user is actually registered
			query = "SELECT id FROM users WHERE username = '" + username + "';";
			result = statement.executeQuery(query);
			if( result.next() ) {
				User.logIn(result.getInt("id"), username, email, false);
			} 
			else throw new NoServerConnectionException();
			
		} catch (SQLException e) {
			throw new NoServerConnectionException();
		}
	}
	
}
