package application.model.credentials;

/**
 * The Class Credential.
 * @author Jeffrey Gaines
 */
public class Credential {

	private String userId;
	private String username;
	private String password;
	
	

	private static final String PARAM_MUST_NOT_BE_EMPTY_OR_NULL = "must not be empty or null";
	private static final String USER_ID_ERROR_MESSAGE = "userId " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL;
	private static final String USERNAME_ERROR_MESSAGE = "username " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL;
	private static final String PASSWORD_ERROR_MESSAGE = "password " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL;

	/**
	 * Instantiates a new credential.
	 *
	 * @param userId   the user id
	 * @param username the username
	 * @param password the password
	 */
	public Credential(String userId, String username, String password) {
		if (userId == null) {
			throw new NullPointerException(USER_ID_ERROR_MESSAGE);
		} else if (userId.isBlank()) {
			throw new IllegalArgumentException(USER_ID_ERROR_MESSAGE);
		}
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
		
		this.userId = userId;
		this.username = username;
		this.password = password;
	}


	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return this.userId;
	}

	
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}


	/**
	 * Sets the username.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		if (username == null) {
			throw new NullPointerException(USERNAME_ERROR_MESSAGE);
		} else if (username.isBlank()) {
			throw new IllegalArgumentException(USERNAME_ERROR_MESSAGE);
		}
		this.username = username;
	}


	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}


	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password == null) {
			throw new NullPointerException(PASSWORD_ERROR_MESSAGE);
		} else if (password.isBlank()) {
			throw new IllegalArgumentException(PASSWORD_ERROR_MESSAGE);
		}
		this.password = password;
	}
	
	
}
