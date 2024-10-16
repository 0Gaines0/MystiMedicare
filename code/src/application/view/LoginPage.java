package application.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
    void initialize() {
        assert this.anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert this.passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert this.userIDTextField != null : "fx:id=\"userIDTextField\" was not injected: check your FXML file 'LoginPage.fxml'.";

    }
    
    public LoginPage() {
    	
    }

}
