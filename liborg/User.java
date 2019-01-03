package liborg;

public class User {
	private static int id;
	private static String username;
	private static String email;
	
	private static boolean isLoggedIn = false;
	
	//Getters
	public static int getId() { return User.id; }
	public static String getUsername() { return User.username; }
	public static String getEmail() { return User.email; }
	
	//Setters
	public static void setId(int id) { User.id = id; }
	public static void setUsername(String username) { User.username = username; }
	public static void setEmail(String email) { User.email = email; }
	
	public static void logIn(int id, String username, String email) {
		User.id = id;
		User.username = username;
		User.email = email;
		User.isLoggedIn = true;
	}
	
	public static void logOut() {
		User.id = 0;
		User.username = "";
		User.email = "";
		User.isLoggedIn = false;
	}
	
	public static boolean isLoggedIn() {
		return User.isLoggedIn;
	}
}
