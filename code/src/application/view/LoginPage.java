package application.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.view.operations.NavigationPage;
import application.viewModel.LoginPageViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginPage {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private TextField userIDTextField;

	@FXML
	private Button loginBtn;

	private LoginPageViewModel loginPageViewModel;
	private NavigationPage navigationPageCodeBehind;

	@FXML
	void initialize() {
		this.fxmlValidComponents();
		this.setUpLoginBtn();
		this.bindToViewModel();

	}

	/**
	 * Instantiates a new login page.
	 */
	public LoginPage() {
		this.loginPageViewModel = new LoginPageViewModel();
		this.navigationPageCodeBehind = new NavigationPage();
	}

	private void setUpLoginBtn() {
		this.loginBtn.setOnAction(((event) -> {
			if (this.loginFieldsAreNull() || this.loginFieldsAreEmpty()) {
				var errorPopUp = new Alert(AlertType.ERROR);
				errorPopUp.setContentText("Login failed, invalid username of password");
				errorPopUp.showAndWait();
			} else {
				try {
					if (this.loginPageViewModel.userLoginIsSuccessful()) {
						this.closeWindow();
						this.navigationPageCodeBehind.openNavigationPage();
					} else {
						var errorPopUp = new Alert(AlertType.ERROR);
						errorPopUp.setContentText("Login failed, incorrect username of password");
						errorPopUp.showAndWait();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}));
	}
	
	private void closeWindow() {
		var stage = (Stage) this.anchorPane.getScene().getWindow();
		stage.close();
	}

	private boolean loginFieldsAreNull() {
		var userId = this.userIDTextField.textProperty().getValue() == null;
		var password = this.passwordTextField.textProperty().getValue() == null;

		return userId || password;
	}

	private boolean loginFieldsAreEmpty() {
		var userId = this.userIDTextField.textProperty().getValue().isBlank();
		var password = this.passwordTextField.textProperty().getValue().isBlank();

		return userId || password;
	}

	private void fxmlValidComponents() {
		assert this.anchorPane != null
				: "fx:id=\"anchorPane\" was not injected: check your FXML file 'LoginPage.fxml'.";
		assert this.passwordTextField != null
				: "fx:id=\"passwordTextField\" was not injected: check your FXML file 'LoginPage.fxml'.";
		assert this.userIDTextField != null
				: "fx:id=\"userIDTextField\" was not injected: check your FXML file 'LoginPage.fxml'.";
	}
	
	private void bindToViewModel() {
		this.userIDTextField.textProperty().bindBidirectional(this.loginPageViewModel.getUserIdProperty());
		this.passwordTextField.textProperty().bindBidirectional(this.loginPageViewModel.getPasswordProperty());
	}

}
