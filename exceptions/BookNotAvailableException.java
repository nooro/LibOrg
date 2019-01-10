package exceptions;

public class BookNotAvailableException extends Exception {

	private static final long serialVersionUID = -8454546288850375519L;

	public BookNotAvailableException() {
		super("This book is not available right now ");
	}
}
