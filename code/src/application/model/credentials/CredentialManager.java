package application.model.credentials;

/**
 * The Class CredentialManager.
 * 
 * @author Jeffrey Gaines
 */
public class CredentialManager {

	private static final String PARAM_MUST_NOT_BE_EMPTY_OR_NULL = "must not be empty or null";
	private static final String USER_ID_ERROR_MESSAGE = "userId " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL;
	private static final String USERNAME_ERROR_MESSAGE = "username " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL;
	private static final String PASSWORD_ERROR_MESSAGE = "password " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL;

	private static final String MOCK_USERID = "0001";
	private static final String MOCK_USERNAME = "username";
	private static final String MOCK_PASSWORD = "password";

	/**
	 * Instantiates a new credential manager.
	 */
	public CredentialManager() {

	}

	/**
	 * User id exist.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	public boolean userIdExist(String userId) {
		// TODO
		// Add querying the database for this userID

		return userId.equals(MOCK_USERID);

	}
	
	/**
	 * Passwords match.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean passwordsMatch(String userId, String password) {
		var credential = this.getSpecifiedCredential(userId);
		if (credential != null) {
			return credential.getPassword().equals(password);
		} else {
			return false;
		}
	}

	/**
	 * Gets the specified credential.
	 *
	 * @param userId the user id
	 * @return the specified credential
	 */
	public Credential getSpecifiedCredential(String userId) {
		if (userId == null) {
			throw new NullPointerException(USER_ID_ERROR_MESSAGE);
		} else if (userId.isBlank()) {
			throw new IllegalArgumentException(USER_ID_ERROR_MESSAGE);
		}

		// TODO
		// Add querying the database for userId, Username, password and build credential

		var mockCredential = new Credential(MOCK_USERID, MOCK_USERNAME, MOCK_PASSWORD);
		return mockCredential;
	}

	/**
	 * Adds the credential.
	 *
	 * @param username the username
	 * @param password the password
	 * @param role the role
	 * @return true, if successful
	 */
	public boolean addCredential(String username, String password, UserRole role) {
		if (username == null) {
			throw new NullPointerException(USERNAME_ERROR_MESSAGE);
		} else if (username.isBlank()) {
			throw new IllegalArgumentException(USERNAME_ERROR_MESSAGE);
		}
		if (password == null) {
			throw new NullPointerException(PASSWORD_ERROR_MESSAGE);
		} else if (password.isBlank()) {
			throw new IllegalArgumentException(PASSWORD_ERROR_MESSAGE);
		}

		// TODO
		// Post to Database and add row to user field
		
		return false;
	}
}
