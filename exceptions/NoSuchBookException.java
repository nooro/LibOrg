package exceptions;

public class NoSuchBookException extends Exception {

	private static final long serialVersionUID = 1740409784487443978L;
	
	public NoSuchBookException() {
		super("No such book ");
	}
	
}
