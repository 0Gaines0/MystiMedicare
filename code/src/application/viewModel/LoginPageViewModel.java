package application.viewModel;

import application.model.credentials.ActiveUser;
import application.model.credentials.CredentialManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginPageViewModel {

	private StringProperty userIdProperty;
	private StringProperty passwordProperty;
	private CredentialManager credManager;

	/**
	 * Instantiates a new login page view model.
	 */
	public LoginPageViewModel() {
		this.userIdProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
		this.credManager = new CredentialManager();
	}

	/**
	 * User login is successful.
	 *
	 * @return true, if successful
	 */
	public boolean userLoginIsSuccessful() {
		var userId = this.userIdProperty.getValue().trim();
		var password = this.passwordProperty.getValue().trim();

		// TODO
		// Implement ability to check if user exist in database

		if (this.credManager.userIdExist(userId) && this.credManager.passwordsMatch(userId, password)) {
			ActiveUser.setActiveUser(userId, password);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Gets the user id property.
	 *
	 * @return the user id property
	 */
	public StringProperty getUserIdProperty() {
		return this.userIdProperty;
	}

	/**
	 * Gets the password property.
	 *
	 * @return the password property
	 */
	public StringProperty getPasswordProperty() {
		return this.passwordProperty;
	}

}
