package application;

public class UserSession {
	
	private static String username_in_session;
	
	public static String getUsername() {
        return username_in_session;
    }

    public static void setUsername(String username) {
        UserSession.username_in_session = username;
    }
	
}
