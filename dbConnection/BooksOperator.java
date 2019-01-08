package dbConnection;

import java.sql.*;
import exceptions.*;

public class BooksOperator {
	
	public static void addNewBook(DBConnector db, String title, String author, String genre, int publishingYear, int pages, int amount) throws NoServerConnectionException {
		
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT id, amount FROM books WHERE title = '" + title + "' AND author = '"+author+"';";
			ResultSet result = statement.executeQuery(query);
			
			//If this book is already in the system just increment the amount with the new amount
			if(result.next()) {
				query = "UPDATE `books` SET `amount` = '"+(result.getInt("amount")+amount)+"' WHERE `books`.`id` = " + result.getInt("id") + ";";
				statement.executeUpdate(query);
			}
			else { //add the new book
				query = "INSERT INTO `books` (`id`, `title`, `author`, `genre`, `publishing_year`, `pages`, `amount`) VALUES (NULL, '"+title+"', '"+author+"', '"+genre+"', '"+publishingYear+"', '"+pages+"', '"+amount+"');";
				statement.executeUpdate(query);
				
				//Check if the book is added
				query = "SELECT id FROM books WHERE title = '" + title + "' AND author = '"+author+"';";
				result = statement.executeQuery(query);
				
				if( !result.next() ) {
					 throw new NoServerConnectionException();
				}
			}
			
		}catch(SQLException e) {
			throw new NoServerConnectionException();
		}
	}
	
	
	public static void removeBook(DBConnector db, int id) throws NoServerConnectionException, InvalidIDException {
		
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
				}
				else { //If there are more than one book take one from the current amount
					query = "UPDATE `books` SET `amount` = '"+(result.getInt("amount")-1)+"' WHERE `books`.`id` = " + id + ";";
					statement.executeUpdate(query);
				}
			}
			else throw new InvalidIDException("There is no book with id " + id);
			
		}catch(SQLException e) {
			throw new NoServerConnectionException();
		}
	}	
}
