import java.util.List;

public class Main {
	
	public static void main( String[] args) {
		
		DBConnector db = new DBConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/library", "root", "");
		
		List<Book> books = db.search("terry ");
		
		for(Book book : books) {
			System.out.println(book.getTitle());
		}
	}
	
}
