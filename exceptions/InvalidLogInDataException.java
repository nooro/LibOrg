package exceptions;

public class InvalidLogInDataException extends Exception {

	private static final long serialVersionUID = -3333004253240630949L;

	public InvalidLogInDataException() {
		super("Invalid username-password combination ");
	}

}
