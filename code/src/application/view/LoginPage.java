package application.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

	@FXML
	void initialize() {
		this.fxmlValidComponents();
		this.setUpLoginBtn();

	}

	public LoginPage() {

	}

	private void setUpLoginBtn() {
		this.loginBtn.setOnAction(((event) -> {
			if (this.loginFieldsAreNull() || this.loginFieldsAreEmpty()) {
				var errorPopUp = new Alert(AlertType.ERROR);
				errorPopUp.setContentText("Login failed, invalid username of password");
				errorPopUp.showAndWait();
			}
			else {
				//TODO
				// Use viewmodel to determine if username and password is found in db
			}
		}));
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

}
