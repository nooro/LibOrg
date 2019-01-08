package dbConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import liborg.Book;

public class DBConnector {
	
	private Connection connection;
	private boolean isConnected;
	
	public DBConnector(String dbDriver, String url, String user, String password) {
		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(url, user, password);
			this.isConnected = true;
		} 
		catch (ClassNotFoundException | SQLException e) { 
			this.isConnected = false; 
		} 
		
	}
	
	public boolean isConnected() { return this.isConnected; }
	public Connection getConnection () { return this.connection; }
	
	public void closeConnection() { 
		try {
			this.connection.close();
		} catch (SQLException e) {}
	}
	
	
	public List<Book> search(String searchString) {
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
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			List <Book> books = new LinkedList<Book>();
			
			while(result.next()) {
				books.add(new Book( result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("genre"), result.getInt("publishing_year"), result.getInt("pages"), result.getInt("amount")));
			}
			
			return books;
			
		} catch (SQLException e) {
			return null;
		}
	}
}
