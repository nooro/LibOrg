package dbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.DBConnector.Result;

public class BooksOperator {
	
	public static DBConnector.Result addNewBook(DBConnector db, String title, String author, String genre, int publishingYear, int pages, int amount) {
		if(!db.isConnected()) {
			return Result.NO_SERVER_CONNECTION;
		}
		
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT id, amount FROM books WHERE title = '" + title + "' AND author = '"+author+"';";
			ResultSet result = statement.executeQuery(query);
			
			//If this book is already in the system just increment the amount with the new amount
			if(result.next()) {
				query = "UPDATE `books` SET `amount` = '"+(result.getInt("amount")+amount)+"' WHERE `books`.`id` = " + result.getInt("id") + ";";
				statement.executeUpdate(query);
				return DBConnector.Result.AMOUNT_IS_CHANGED;
			}
			else { //add the new book
				query = "INSERT INTO `books` (`id`, `title`, `author`, `genre`, `publishing_year`, `pages`, `amount`) VALUES (NULL, '"+title+"', '"+author+"', '"+genre+"', '"+publishingYear+"', '"+pages+"', '"+amount+"');";
				statement.executeUpdate(query);
				
				//Check if the book is added
				query = "SELECT id FROM books WHERE title = '" + title + "' AND author = '"+author+"';";
				result = statement.executeQuery(query);
				if( result.next() ) {
					return DBConnector.Result.NEW_BOOK_IS_ADDED;
				} else return DBConnector.Result.SQL_ERROR;
			}
			
		}catch(SQLException e) {
			return DBConnector.Result.NO_SERVER_CONNECTION;
		}
	}
	
	
	public static DBConnector.Result removeBook(DBConnector db, int id) {
		if(!db.isConnected()) {
			return DBConnector.Result.NO_SERVER_CONNECTION;
		}
		
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT amount FROM books WHERE id = '" + id + "';";
			ResultSet result = statement.executeQuery(query);
			
			//Check if there is book with the given id
			if(result.next()) {
				//If this is the only book remove it
				if(result.getInt("amount") == 1) {
					query = "DELETE FROM `books` WHERE `books`.`id` = " + id + ";";
					statement.executeUpdate(query);
					return DBConnector.Result.BOOK_IS_REMOVED;
				}
				else { //If there are more than one book take one from the current amount
					query = "UPDATE `books` SET `amount` = '"+(result.getInt("amount")-1)+"' WHERE `books`.`id` = " + id + ";";
					statement.executeUpdate(query);
					return DBConnector.Result.AMOUNT_IS_CHANGED;
				}
			}
			else return DBConnector.Result.INVALID_BOOK_ID;
			
		}catch(SQLException e) {
			return DBConnector.Result.SQL_ERROR;
		}
	}	
}
