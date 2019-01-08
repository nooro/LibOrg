package exceptions;

public class NoServerConnectionException extends Exception {
	
	private static final long serialVersionUID = -2450598005492818800L;

	public NoServerConnectionException() {
		super("No server connection ");
	}
	
}
