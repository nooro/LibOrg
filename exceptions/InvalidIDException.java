package exceptions;

public class InvalidIDException extends Exception{

	private static final long serialVersionUID = 1564681586571836134L;

	public InvalidIDException(String message) {
		super(message);
	}
	
}
