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
	
	public enum Result {
		NO_SERVER_CONNECTION,
		SQL_ERROR,

		LOG_IN_INVALID_DATA,
		LOGGED_IN,
		
		REGISTER_USERNAME_TAKEN,
		REGISTERED,
		
		NEW_BOOK_IS_ADDED,
		AMOUNT_IS_CHANGED,
		
		BOOK_IS_REMOVED,
		INVALID_BOOK_ID
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
	public Result logIn(String username, String password) {
		if(!this.isConnected) {
			return Result.NO_SERVER_CONNECTION;
		}

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username and password
			if( result.next() ) {
				User.logIn(result.getInt("id"), result.getString("username"), result.getString("email"), result.getBoolean("is_admin"));
				return Result.LOGGED_IN;
			} else {
				User.logOut();
				return Result.LOG_IN_INVALID_DATA;
			}
			
		} catch (SQLException e) {
			return Result.NO_SERVER_CONNECTION;
		}
	}
	
	//REGISTRATION.....................................................................
	public Result register(String username, String password, String email) {
		if(!this.isConnected) {
			return Result.NO_SERVER_CONNECTION;
		}
		 
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT id FROM users WHERE username = '" + username + "';";
			ResultSet result = statement.executeQuery(query);
			
			//There is user with that username already
			if( result.next() ) {
				return Result.REGISTER_USERNAME_TAKEN;
			} 
			
			query = "INSERT INTO `users` (`id`, `username`, `password`, `email`, `is_admin`) VALUES (NULL, '"+username+"', '"+password+"', '"+email+"', '0');";
			statement.executeUpdate(query);
			
			//Check if the user is actually registered
			query = "SELECT id FROM users WHERE username = '" + username + "';";
			result = statement.executeQuery(query);
			if( result.next() ) {
				User.logIn(result.getInt("id"), username, email, false);
				return Result.REGISTERED;
			} 
			else return Result.SQL_ERROR;
			
		} catch (SQLException e) {
			return Result.NO_SERVER_CONNECTION;
		}
	}
	
	//ADD NEW BOOK..................................................................................................
	public Result addNewBook(String title, String author, String genre, int publishingYear, int pages, int amount) {
		if(!this.isConnected) {
			return Result.NO_SERVER_CONNECTION;
		}
		
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT id, amount FROM books WHERE title = '" + title + "' AND author = '"+author+"';";
			ResultSet result = statement.executeQuery(query);
			
			//If this book is already in the system just increment the amount with the new amount
			if(result.next()) {
				query = "UPDATE `books` SET `amount` = '"+(result.getInt("amount")+amount)+"' WHERE `books`.`id` = " + result.getInt("id") + ";";
				statement.executeUpdate(query);
				return Result.AMOUNT_IS_CHANGED;
			}
			else { //add the new book
				query = "INSERT INTO `books` (`id`, `title`, `author`, `genre`, `publishing_year`, `pages`, `amount`) VALUES (NULL, '"+title+"', '"+author+"', '"+genre+"', '"+publishingYear+"', '"+pages+"', '"+amount+"');";
				statement.executeUpdate(query);
				
				//Check if the book is added
				query = "SELECT id FROM books WHERE title = '" + title + "' AND author = '"+author+"';";
				result = statement.executeQuery(query);
				if( result.next() ) {
					return Result.NEW_BOOK_IS_ADDED;
				} else return Result.SQL_ERROR;
			}
			
		}catch(SQLException e) {
			return Result.NO_SERVER_CONNECTION;
		}
	}
	
	//REMOVE BOOK.....................
	public Result removeBook(int id) {
		if(!this.isConnected) {
			return Result.NO_SERVER_CONNECTION;
		}
		
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT amount FROM books WHERE id = '" + id + "';";
			ResultSet result = statement.executeQuery(query);
			
			//Check if there is book with the given id
			if(result.next()) {
				//If this is the only book remove it
				if(result.getInt("amount") == 1) {
					query = "DELETE FROM `books` WHERE `books`.`id` = " + id + ";";
					statement.executeUpdate(query);
					return Result.BOOK_IS_REMOVED;
				}
				else { //Take one from the current amount
					query = "UPDATE `books` SET `amount` = '"+(result.getInt("amount")-1)+"' WHERE `books`.`id` = " + id + ";";
					statement.executeUpdate(query);
					return Result.AMOUNT_IS_CHANGED;
				}
			}
			else return Result.INVALID_BOOK_ID;
			
		}catch(SQLException e) {
			return Result.SQL_ERROR;
		}
	}

}
