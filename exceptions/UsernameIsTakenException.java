package exceptions;

public class UsernameIsTakenException extends Exception{
	
	private static final long serialVersionUID = 8791539771959531187L;

	public UsernameIsTakenException() {
		super("This username already exists ");
	}
}
