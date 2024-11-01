package application.view.operations;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AppointmentAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<Doctor> doctorComboBox;

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private TextField patientStatusTextField;

    @FXML
    private TextArea reasonTextArea;

    @FXML
    void initialize() {
        this.validateFXMLComponents();

    }

	private void validateFXMLComponents() {
		assert this.appointmentDatePicker != null : "fx:id=\"appointmentDatePicker\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
        assert this.doctorComboBox != null : "fx:id=\"doctorComboBox\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
        assert this.patientComboBox != null : "fx:id=\"patientComboBox\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
        assert this.patientStatusTextField != null : "fx:id=\"patientStatusTextField\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
        assert this.reasonTextArea != null : "fx:id=\"reasonTextArea\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
	}

}
