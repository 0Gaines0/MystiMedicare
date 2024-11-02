package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.credentials.Doctor;
import application.model.credentials.Patient;
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
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button createAppointmentBtn;

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

    @FXML
    void initialize() {
        this.validateFXML();
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
    
	private void validateFXML() {
		assert this.appointmentDatePicker != null : "fx:id=\"appointmentDatePicker\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.createAppointmentBtn != null : "fx:id=\"createAppointmentBtn\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.doctorComboBox != null : "fx:id=\"doctorComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.patientComboBox != null : "fx:id=\"patientComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.patientStatusTextField != null : "fx:id=\"patientStatusTextField\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.reasonTextArea != null : "fx:id=\"reasonTextArea\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
        assert this.timePickerComboBox != null : "fx:id=\"timePickerComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
	}

}
