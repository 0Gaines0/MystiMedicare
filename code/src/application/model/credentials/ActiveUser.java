package application.model.credentials;

/**
 * The Class ActiveUser.
 * @author Jeffrey Gaines
 */
public class ActiveUser {
	
	private static User activeUser;
	
	//TODO remove temp credentialManager
	private static CredentialManager credManager;
	
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
	 * @param userId the user id
	 * @param password the password
	 */
	public static void setActiveUser(String userId, String password) {
		//TODO 
		// Reach out to database and query for the user credentials and then create a user object
		// we will likely have to use polymorphism
		credManager = new CredentialManager();
		var cred = credManager.getSpecifiedCredential(userId);
		var user = new User(cred.getUserId(), cred.getUsername(), cred.getPassword(), UserRole.ADMIN);
		ActiveUser.activeUser = user;
	}
}
