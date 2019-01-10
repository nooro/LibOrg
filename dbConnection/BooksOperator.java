package dbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
	public static void takeBook( DBConnector db, String username, int bookID ) throws NoServerConnectionException, NoSuchUserException, NoSuchBookException, BookNotAvailableException {
		try  {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT id FROM users WHERE username = '"+username+"';";
			ResultSet result = statement.executeQuery(query);
			
			if(result.next()) {
				int userID = result.getInt("id");

				query = "SELECT amount FROM books WHERE id = '"+bookID+"';";
				result = statement.executeQuery(query);
				
				if(result.next()) {
					int booksAmount = result.getInt("amount");
					
					if(booksAmount > 0) {
						String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
						String returnDate = calculateReturnDate(currentDate);
						query = "INSERT INTO `users_books`(`user_id`, `book_id`, `take_date`, `return_date`) VALUES ('"+userID+"', '"+bookID+"', '"+currentDate+"', '"+returnDate+"');";
						statement.executeUpdate(query);
						
						query = "UPDATE `books` SET `amount` = '"+(booksAmount-1)+"' WHERE `books`.`id` = "+bookID+";";
						statement.executeUpdate(query);
					} else throw new BookNotAvailableException();
					 
				} else throw new NoSuchBookException();
			} else throw new NoSuchUserException();
		} catch(SQLException e) {
			throw new NoServerConnectionException();
		}
	}
	
	private static String calculateReturnDate(String now) {
		String[] newDate = now.split("-");
		
		if(Integer.parseInt(newDate[1]) < 12) {
			newDate[1] = ""+(Integer.parseInt(newDate[1])+1);
			if(Integer.parseInt(newDate[1]) < 10) {
				newDate[1] = "0"+newDate[1];
			}
			
		} else {
			newDate[1] = "01";
			newDate[0] = ""+(Integer.parseInt(newDate[0]) + 1);
		}
		
		if(newDate[2].equals("31")) {
			if(newDate[1].equals("02")) {
				newDate[2] = "28";
			} else if(newDate[1].equals("01") || newDate[1].equals("08")){
				newDate[2] = "31";
			} else {
				newDate[2] = "30";
			}
		}
		
		return newDate[0] + "-" + newDate[1] + "-" + newDate[2];
	}
	
	
	public static void returnBook(DBConnector db, String username, int bookID) throws NoServerConnectionException, NoSuchUserException, NoSuchBookException {
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT id FROM users WHERE username = '"+username+"';";
			ResultSet result = statement.executeQuery(query);
			
			if(result.next()) {
				int userID = result.getInt("id");
				
				query = "SELECT amount FROM books WHERE id = '"+bookID+"';";
				result = statement.executeQuery(query);
				
				if(result.next()) {
					int booksAmount = result.getInt("amount");
					
					query = "DELETE FROM `users_books` WHERE `user_id` = "+userID+" AND `book_id` = "+bookID+";";
					
					int deletedRows = statement.executeUpdate(query);
					if( deletedRows != 0) {
						query = "UPDATE `books` SET `amount` = '"+(booksAmount+deletedRows)+"' WHERE `books`.`id` = "+bookID+";";
						statement.executeUpdate(query);
					}
				} else throw new NoSuchBookException();
				
			} else throw new NoSuchUserException();
			
		}catch(SQLException e) {
			throw new NoServerConnectionException();
		}
	}
	
	public static boolean isBookAvailable(DBConnector db, int bookID) throws NoSuchBookException, NoServerConnectionException {
		try {
			Statement statement = db.getConnection().createStatement();
			String query = "SELECT amount FROM books WHERE id = '"+bookID+"';";
			ResultSet result = statement.executeQuery(query);
			
			if(result.next()) {
				int amount = result.getInt("amount");
				
				if(amount > 0) {
					return true;
				} else return false;
				
			} else throw new NoSuchBookException();
				
		}catch(SQLException e) {
			throw new NoServerConnectionException();
		}
	}
}