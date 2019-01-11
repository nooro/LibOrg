package dbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import exceptions.*;
import liborg.*;

public class BooksCrawler {
	
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
	
	public static List<Book> search(DBConnector db, String searchString) throws NoServerConnectionException {
		Statement statement;
		String [] splittedSearchString = searchString.split(" ");
		
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
			statement = db.getConnection().createStatement();
			ResultSet result = statement.executeQuery(query);
			
			List <Book> books = new LinkedList<Book>();
			
			while(result.next()) {
				books.add(new Book( result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("genre"), result.getInt("publishing_year"), result.getInt("pages"), result.getInt("amount")));
			}
			
			return books;
			
		} catch (SQLException e) {
			throw new NoServerConnectionException();
		}
	}
	
	public static List<TakenBook> getTakenBooks(DBConnector db) throws NoServerConnectionException {
		Statement statement;
		try {
			statement = db.getConnection().createStatement();
			String query = "SELECT users.username, books.title, users_books.take_date, users_books.return_date FROM users INNER JOIN users_books ON users.id = users_books.user_id INNER JOIN books ON books.id = users_books.book_id;";
			ResultSet result = statement.executeQuery(query);
			
			List <TakenBook> takenBooks = new LinkedList<TakenBook>();
			
			while(result.next()) {
				takenBooks.add(new TakenBook(result.getString("users.username"), result.getString("books.title"), result.getString("users_books.take_date"), result.getString("users_books.return_date")));
			}
			
			return takenBooks;
			
		} catch (SQLException e) {
			throw new NoServerConnectionException();
		}
	}
	
	public static List<TakenBook> checkWhoTookBook(DBConnector db, int bookID) throws NoServerConnectionException {
		Statement statement;
		try {
			statement = db.getConnection().createStatement();
			String query = "SELECT users.username, books.title, users_books.take_date, users_books.return_date FROM users INNER JOIN users_books ON users.id = users_books.user_id INNER JOIN books ON books.id = users_books.book_id WHERE users_books.book_id = "+bookID+";";
			ResultSet result = statement.executeQuery(query);
			
			List <TakenBook> takenBooks = new LinkedList<TakenBook>();
			
			while(result.next()) {
				takenBooks.add(new TakenBook(result.getString("users.username"), result.getString("books.title"), result.getString("users_books.take_date"), result.getString("users_books.return_date")));
			}
			
			return takenBooks;
			
		} catch (SQLException e) {
			throw new NoServerConnectionException();
		}
	}
}
