package application.viewModel;

import java.sql.SQLException;

import application.DAL.UserDAL;
import application.model.credentials.ActiveUser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * the loginpageviewmodel
 * @author jeffrey gaines
 */
public class LoginPageViewModel {

	private StringProperty userIdProperty;
	private StringProperty passwordProperty;
	private UserDAL userDAL;

	/**
	 * Instantiates a new login page view model.
	 */
	public LoginPageViewModel() {
		this.userIdProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
		this.userDAL = new UserDAL();
	}

	/**
	 * User login is successful.
	 *
	 * @return true, if successful
	 * @throws SQLException 
	 */
	public boolean userLoginIsSuccessful() throws SQLException {
		var userId = this.userIdProperty.getValue().trim();
		var password = this.passwordProperty.getValue().trim();
		if (this.userDAL.checkUserId(userId)) {
			var user = this.userDAL.loginUser(userId, String.valueOf(password.hashCode()));
			if (user != null) {
				ActiveUser.setActiveUser(user);
				return true;
			}
			return false;
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
