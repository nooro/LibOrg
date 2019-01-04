package dbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import liborg.Book;
import liborg.User;

public class DBConnector {
	
	private Connection connection;
	
	private boolean isConnected;
	
	public DBConnector(String dbDriver, String url, String user, String password) {
		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(url, user, password);
			this.isConnected = true;
		} catch (ClassNotFoundException e) {
			this.isConnected = false;
		} catch (SQLException e) {
			this.isConnected = false;
		}
	}
	
	public boolean isConnected() { return this.isConnected; }
	
	public void closeConnection() { 
		try {
			this.connection.close();
		} catch (SQLException e) {}
	}
	
	//SEARCH.......................................
	public List<Book> search(String searchString) {
		Statement statement;
		String [] splittedSearchString = searchString.split(" ");
		
		//Build the query 
		String query = "SELECT * FROM books";
		if(splittedSearchString.length >= 2) {
			for(int i = 0; i < splittedSearchString.length; i++) {
				if( i == 0)
					query += " WHERE title LIKE '%"+splittedSearchString[i]+"%' OR  author LIKE '%"+splittedSearchString[i]+"%'";
				else
					query += " AND title LIKE '%"+splittedSearchString[i]+"%' OR author LIKE '%"+splittedSearchString[i]+"%'";
			}
			query += ";";
		} else {
			query = "SELECT * FROM books WHERE title LIKE '%"+searchString+"%' OR author LIKE '%"+searchString+"%';";
		}
		
		
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			List <Book> books = new LinkedList<Book>();
			
			while(result.next()) {
				books.add(new Book( result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("genre"), result.getInt("publishing_year"), result.getInt("pages"), result.getInt("amount")));
			}
			
			return books;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//LOG IN....................................................
	public enum LogInResult {
		NO_SERVER_CONNECTION,
		INVALID_DATA,
		LOGGED_IN
	}
	
	public LogInResult logIn(String username, String password) {
		if(!this.isConnected) {
			return LogInResult.NO_SERVER_CONNECTION;
		}

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username and password
			if( result.next() ) {
				User.logIn(result.getInt("id"), result.getString("username"), result.getString("email"));
				return LogInResult.LOGGED_IN;
			} else {
				User.logOut();
				return LogInResult.INVALID_DATA;
			}
			
		} catch (SQLException e) {
			return LogInResult.NO_SERVER_CONNECTION;
		}
	}
	
	//REGISTRATION........................................................
	public enum RegistrationResult {
		NO_SERVER_CONNECTION,
		USERNAME_TAKEN,
		REGISTERED,
		SQL_ERROR
	}
	
	public RegistrationResult register(String username, String password, String email) {
		if(!this.isConnected) {
			return RegistrationResult.NO_SERVER_CONNECTION;
		}
		 
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT id FROM users WHERE username = '" + username + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username already
			if( result.next() ) {
				return RegistrationResult.USERNAME_TAKEN;
			} 
			
			query = "INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES (NULL, '"+username+"', '"+password+"', '"+email+"');";
			statement.executeUpdate(query);
			
			//Check if the user is actually registered
			query = "SELECT id FROM users WHERE username = '" + username + "';";
			result = statement.executeQuery(query);
			if( result.next() ) {
				User.logIn(result.getInt("id"), username, email);
				return RegistrationResult.REGISTERED;
			} 
			else return RegistrationResult.SQL_ERROR;
			
		} catch (SQLException e) {
			return RegistrationResult.NO_SERVER_CONNECTION;
		}
	}
}
