package exceptions;

public class BookNotTakenException extends Exception{

	private static final long serialVersionUID = 6078088619627907119L;

	public BookNotTakenException() {
		super("This book is not taken by this user ");
	}
}
