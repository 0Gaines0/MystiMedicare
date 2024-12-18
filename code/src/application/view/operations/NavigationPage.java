package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import application.DAL.UserDAL;
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
 * 
 * @author Jeffrey
 */
public class NavigationPage {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

    @FXML
    private HBox adminHBox;
    
	@FXML
	private AnchorPane baseAnchorPane;

    @FXML
    private HBox logoutHBox;

	@FXML
	private HBox registerPatientHBox;

	@FXML
	private HBox searchPatientHBox;

	@FXML
	private BorderPane parentBorderPane;

	@FXML
	private Label fullNameLabel;

	@FXML
	private Label usernameLabel;
	
    @FXML
    private HBox usernameHBox;

    @FXML
    private HBox usernameHBox1;
	
    @FXML
    private HBox createAppointmentHBox;
    
    @FXML
    private HBox beginAppointmentHBox;

	private RegisterPatientAnchorPane registerPatientCodeBehind;
	private SearchPatientAnchorPane searchPatientCodeBehind;
	private AppointmentAnchorPane createAppointmentCodeBehind;
	private SelectAppointmentAnchorPane selectAppointmentCodeBehind;
	private AdminQueryAnchorPane adminPageCodeBehind;

	private UserDAL userDAL;


	/**
	 * Instantiates a new navigation page.
	 */
	public NavigationPage() {
		this.registerPatientCodeBehind = new RegisterPatientAnchorPane();
		this.searchPatientCodeBehind = new SearchPatientAnchorPane();
		this.createAppointmentCodeBehind = new AppointmentAnchorPane();
		this.selectAppointmentCodeBehind = new SelectAppointmentAnchorPane();
		this.adminPageCodeBehind = new AdminQueryAnchorPane();
		this.userDAL = new UserDAL();
	}

	@FXML
    void initialize() {
    	this.validateFXMLComponents();
    	this.setUpSideBarButtons();
		this.setUpUsernameLabel();
		this.setUpFullNameLabel();
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
	
	private void setUpFullNameLabel() {
		try {
			this.fullNameLabel.setText(this.userDAL.retrieveUserFullName(Integer.parseInt(ActiveUser.getActiveUser().getId())));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void setUpSideBarButtons() {
		this.setUpRegisterPatientHBox();
		this.setUpSearchPatientHBox();
		this.setUpCreateAppointmentHBox();
		this.setUpBeginAppointmentHBox();
		this.setUpLogoutHBox();
		this.setUpAdminHBox();
	}
	
	private void setUpLogoutHBox() {
		this.logoutHBox.setOnMouseClicked((event) -> {
			var newStage = new Stage();
			try {
				var loader = new FXMLLoader(getClass().getResource(Main.LOGIN_WINDOW_FROM_NAV));
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
			ActiveUser.setActiveUser(null);
	        this.closePage();
		});
	}

	private void closePage() {
		Stage stage = (Stage) this.parentBorderPane.getScene().getWindow();
		stage.close();
	}
	
	private void setUpBeginAppointmentHBox() {
		this.beginAppointmentHBox.setOnMouseClicked(((event) -> {
			this.selectAppointmentCodeBehind.openAnchorPane(this.parentBorderPane, Main.SELECT_APPOINTMENT_ANCHOR_PANE);
		}));
	}

	private void setUpCreateAppointmentHBox() {
		this.createAppointmentHBox.setOnMouseClicked(((event) -> {
			this.createAppointmentCodeBehind.openAnchorPane(this.parentBorderPane, Main.CREATE_APPOINTMENT_ANCHOR_PANE);
		}));
	}

	private void setUpRegisterPatientHBox() {
		this.registerPatientHBox.setOnMouseClicked(((event) -> {
			this.registerPatientCodeBehind.openAnchorPane(this.parentBorderPane, Main.REGISTER_PATIENT_ANCHOR_PANE);
		}));
	}

	private void setUpSearchPatientHBox() {
		this.searchPatientHBox.setOnMouseClicked(((event) -> {
			this.searchPatientCodeBehind.openAnchorPane(this.parentBorderPane, Main.SEARCH_PATIENT_ANCHOR_PANE);
		}));
	}
	
	private void setUpAdminHBox() {
		this.adminHBox.setOnMouseClicked((event) -> {
			this.adminPageCodeBehind.openAnchorPane(this.parentBorderPane, Main.ADMIN_QUERY_ANCHOR_PANE);
		});
	}

	private void validateFXMLComponents() {
		assert this.baseAnchorPane != null : "fx:id=\"baseAnchorPane\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.createAppointmentHBox != null : "fx:id=\"createAppointmentHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.fullNameLabel != null : "fx:id=\"fullNameLabel\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.adminHBox != null : "fx:id=\"adminHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.logoutHBox != null : "fx:id=\"logoutHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.parentBorderPane != null : "fx:id=\"parentBorderPane\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.registerPatientHBox != null : "fx:id=\"registerPatientHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.searchPatientHBox != null : "fx:id=\"searchPatientHBox\" was not injected: check your FXML file 'NavigationPage.fxml'.";
        assert this.usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'NavigationPage.fxml'.";
	}

}
