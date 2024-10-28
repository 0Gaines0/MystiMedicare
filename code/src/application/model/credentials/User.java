package application.model.credentials;

/**
 * user class
 * @author jeffrey gaines
 */
public class User {

	private String id;
	private String username;
	private String password;
	private UserRole role;
	
	private static final String PARAM_MUST_NOT_BE_EMPTY_OR_NULL = "must not be empty or null";
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		this.id = "";
		this.username = "";
		this.password = "";
		this.role = UserRole.NONE;
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param username the username
	 * @param password the password
	 * @param role the role
	 */
	public User(String userId, String username, String password, UserRole role) {
		if (userId == null || userId.isBlank()) {
			throw new IllegalArgumentException("userId " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("username " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("password " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (role == null) {
			throw new IllegalArgumentException("role " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		this.id = userId;
		this.username = username;
		this.password = password;
		this.role = role;
	
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param username the new username
	 */
	public void setUsername(String username) {
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
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public UserRole getRole() {
		return this.role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}
}
