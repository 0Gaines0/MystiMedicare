package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.credentials.ActiveUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class NavigationPage.
 * @author Jeffrey
 */
public class NavigationPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane baseAnchorPane;

    @FXML
    private HBox editPatientHBox;

    @FXML
    private HBox registerPatientHBox;
    
    @FXML
    private BorderPane parentBorderPane;
    
    @FXML
    private Label fullNameLabel;
	
	@FXML
    private Label usernameLabel;
    
    private RegisterPatientAnchorPane registerPatientCodeBehind;
    private EditPatientAnchorPane editPatientCodeBehind;

    @FXML
    void initialize() {
        this.validateFXMLComponents();
		this.setUpSideBarButtons();
		this.setUpUsernameLabel();

    }
    
    /**
     * Instantiates a new navigation page.
     */
    public NavigationPage() {
    	this.registerPatientCodeBehind = new RegisterPatientAnchorPane();
    	this.editPatientCodeBehind = new EditPatientAnchorPane();
    }
    
    /**
     * Open navigation page.
     */
    public void openNavigationPage() {
    	var newStage = new Stage();
		try {
			var loader = new FXMLLoader(getClass().getResource(Main.NAVIGATION_PAGE));
			Parent parent = loader.load();
			var scene = new Scene(parent);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(((Stage) (parent.getScene().getWindow())));
			newStage.setTitle(Main.WINDOW_TITLE);
			newStage.setScene(scene);
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException error) {
			error.printStackTrace();
		}
    }
    
    private void setUpUsernameLabel() {
		this.usernameLabel.setText(ActiveUser.getActiveUser().getUsername());
	}
    
    private void setUpSideBarButtons() {
    	this.setUpRegisterPatientHBox();
    	this.setUpEditPatientHBox();
	}

	private void setUpEditPatientHBox() {
		this.editPatientHBox.setOnMouseClicked(((event) -> {
			this.editPatientCodeBehind.openAnchorPane(this.parentBorderPane, Main.EDIT_PATIENT_ANCHOR_PANE);
		}));
	}

	private void setUpRegisterPatientHBox() {
		this.registerPatientHBox.setOnMouseClicked(((event) -> {
			this.registerPatientCodeBehind.openAnchorPane(this.parentBorderPane, Main.REGISTER_PATIENT_ANCHOR_PANE);
		}));
	}

	private void validateFXMLComponents() {
		assert this.baseAnchorPane != null : "fx:id=\"baseAnchorPane\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.editPatientHBox != null : "fx:id=\"editPatientHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.registerPatientHBox != null : "fx:id=\"registerPatientHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
	}

}
