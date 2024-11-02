package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.credentials.Patient;
import application.viewModel.operations.SearchPatientAnchorPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SearchPatientAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private ListView<Patient> patientListview;

    @FXML
    private AnchorPane searchAnchorPane;

    @FXML
    private Button searchButton;
    
    @FXML
    private Button selectPatientButton;
    
    private SearchPatientAnchorPageViewModel viewModel;
    private EditAppointmentAnchorPane editApptCodeBehind;
    
    /**
     * the search patient anchor pane
     */
    public SearchPatientAnchorPane() {
    	this.viewModel = new SearchPatientAnchorPageViewModel();
    	this.editApptCodeBehind = new EditAppointmentAnchorPane();
    }
    
	/**
	 * Open anchor pane.
	 *
	 * @param parent        the parent
	 * @param newAnchorPath the new anchor path
	 */
	public void openAnchorPane(BorderPane parent, String newAnchorPath) {
		try {
			AnchorPane currentAnchor = (AnchorPane) parent.getCenter();
			var loader = new FXMLLoader(getClass().getResource(newAnchorPath));
			AnchorPane newAnchor = loader.load();
			parent.setCenter(newAnchor);
			parent.getChildren().remove(currentAnchor);
		} catch (IOException error) {
			error.getMessage();
		}
	}

    @FXML
    void initialize() {
        this.validateFXML();
        this.setupButtons();
        this.bindToViewModel();
    }
    
    private void setupButtons() {
    	this.searchButton.setOnAction((event) -> {
    		this.patientListview.getItems().setAll(this.viewModel.searchPatients());
    	});
    	this.selectPatientButton.setOnAction((event) -> {
    		this.editApptCodeBehind.openAnchorPane((BorderPane) this.searchAnchorPane.getParent(), Main.EDIT_APPOINTMENT_ANCHOR_PANE, this.patientListview.getSelectionModel().getSelectedItem());
    	});
    }
    
    private void bindToViewModel() {
		this.fnameField.textProperty().bindBidirectional(this.viewModel.getPatientFirstNameTextProperty());
		this.lnameField.textProperty().bindBidirectional(this.viewModel.getPatientLastNameTextProperty());
		this.viewModel.getPatientDateOfBirthTextProperty().bindBidirectional(this.dobPicker.valueProperty());
    }

	private void validateFXML() {
		assert this.dobPicker != null : "fx:id=\"dobPicker\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.fnameField != null : "fx:id=\"fnameField\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.lnameField != null : "fx:id=\"lnameField\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.patientListview != null : "fx:id=\"patientListview\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.searchAnchorPane != null : "fx:id=\"searchAnchorPane\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
        assert this.selectPatientButton != null : "fx:id=\"selectPatientButton\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
	}

}
