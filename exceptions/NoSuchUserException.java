package exceptions;

public class NoSuchUserException extends Exception {

	private static final long serialVersionUID = -8224891815425684355L;

	public NoSuchUserException() {
		super("No such user ");
	}
	
}
