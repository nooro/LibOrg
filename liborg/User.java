package liborg;

public class User {
	private static int id;
	private static String username;
	private static String email;
	private static boolean isAdmin;
	
	private static boolean isLoggedIn = false;
	
	//Getters
	public static int getId() { return User.id; }
	public static String getUsername() { return User.username; }
	public static String getEmail() { return User.email; }
	public static boolean isAdmin() { return User.isAdmin; }
	
	public static void logIn(int id, String username, String email, boolean isAdmin) {
		User.id = id;
		User.username = username;
		User.email = email;
		User.isLoggedIn = true;
		User.isAdmin = isAdmin;
	}
	
	public static void logOut() {
		User.id = 0;
		User.username = "";
		User.email = "";
		User.isLoggedIn = false;
		User.isAdmin = false;
	}
	
	public static boolean isLoggedIn() {
		return User.isLoggedIn;
	}
}
