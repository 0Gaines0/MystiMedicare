package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import application.viewModel.operations.EditAppointmentAnchorPaneViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EditAppointmentAnchorPane {
	
	@FXML
	private AnchorPane editAnchorPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private ComboBox<Doctor> doctorComboBox;

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private TextField patientStatusTextField;

    @FXML
    private TextArea reasonTextArea;

    @FXML
    private ComboBox<String> timePickerComboBox;
  
    private EditAppointmentAnchorPaneViewModel viewmodel;
    private Patient selectedPatient;
    
    
    /**
     * the edit appointment anchor pane
     */
    public EditAppointmentAnchorPane() {
    	this.viewmodel = new EditAppointmentAnchorPaneViewModel();
    	this.selectedPatient = new Patient();
    }

    @FXML
    void initialize() {
        this.validateFXML();
        this.setupButtons();
        //set doctor to appt doctor
        //set the appt date and time to the earliest appt
        //fill in reason and status
    }
    
    private void setupButtons() {
    	this.cancelBtn.setOnAction((event) -> {
    		//cancel and go back to the search
    	});
    	this.updateBtn.setOnAction((event) -> {
    		//update appointment dal call
    	});
    }

	/**
	 * Open anchor pane.
	 *
	 * @param parent        the parent
	 * @param newAnchorPath the new anchor path
	 * @param patient 		the patient to edit
	 */
	public void openAnchorPane(BorderPane parent, String newAnchorPath, Patient patient) {
		try {
			AnchorPane currentAnchor = (AnchorPane) parent.getCenter();
			var loader = new FXMLLoader(getClass().getResource(newAnchorPath));
			AnchorPane newAnchor = loader.load();
			parent.setCenter(newAnchor);
			parent.getChildren().remove(currentAnchor);
			this.selectedPatient = patient;
		} catch (IOException error) {
			error.getMessage();
		}
	}
    
	private void validateFXML() {
		assert this.appointmentDatePicker != null : "fx:id=\"appointmentDatePicker\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.updateBtn != null : "fx:id=\"createAppointmentBtn\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.doctorComboBox != null : "fx:id=\"doctorComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.patientComboBox != null : "fx:id=\"patientComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.editAnchorPane != null : "fx:id=\"editAnchorPane\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.patientStatusTextField != null : "fx:id=\"patientStatusTextField\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.reasonTextArea != null : "fx:id=\"reasonTextArea\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.timePickerComboBox != null : "fx:id=\"timePickerComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
	}

}
