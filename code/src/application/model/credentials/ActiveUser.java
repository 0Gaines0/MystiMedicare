package application.model.credentials;

/**
 * The Class ActiveUser.
 * @author Jeffrey Gaines
 */
public class ActiveUser {
	
	private static User activeUser;
	
	/**
	 * Gets the active user.
	 *
	 * @return the active user
	 */
	public static User getActiveUser() {
		return activeUser;
	}
	
	
	/**
	 * Sets the active user.
	 *
	 * @param user the user
	 */
	public static void setActiveUser(User user) {
		ActiveUser.activeUser = user;
	}
}
