package liborg;

public class Password {
	public static String crypt(String password) {
		return "SECURED@" + password;
	}
	
	public static String decrypt(String password) {
		return password.split("@")[1];
	}
}
