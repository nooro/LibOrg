package liborg;

public class TakenBook {
	private String bookTitle;
	private String username;
	private String dateTaken;
	private String dateToReturn;
	
	public TakenBook(String username, String bookTitle, String dateTaken, String dateToReturn) {
		this.bookTitle = bookTitle;
		this.username = username;
		this.dateTaken = dateTaken;
		this.dateToReturn = dateToReturn;
	}
	
	public String toString() {
		return this.username + " | " + this.bookTitle + " | " + this.dateTaken + " | " + this.dateToReturn;
	}
}
