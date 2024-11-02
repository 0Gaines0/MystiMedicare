package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.DAL.DoctorDAL;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import application.viewModel.operations.AppointmentAnchorPaneViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
	private ComboBox<String> timePickerComboBox;

	@FXML
	private Button cancelBtn;

	@FXML
	private Button createAppointmentBtn;

	private AppointmentAnchorPaneViewModel viewModel;

	@FXML
	void initialize() {
		this.validateFXMLComponents();
		this.bindToViewModel();
		this.populateTimePicker();
		this.setUpCreateAppointmentBtn();
		this.setUpDoctorsComboBox();

	}

	/**
	 * Instantiates a new appointment anchor pane.
	 */
	public AppointmentAnchorPane() {
		this.viewModel = new AppointmentAnchorPaneViewModel();
	}

	private void setUpCreateAppointmentBtn() {
		this.createAppointmentBtn.setOnAction(((event) -> {

		}));
	}

	private void bindToViewModel() {
		this.doctorComboBox.itemsProperty().bindBidirectional(this.viewModel.getDoctorListProperty());
		this.patientComboBox.itemsProperty().bindBidirectional(this.viewModel.getPatientListProperty());

		this.appointmentDatePicker.valueProperty().bindBidirectional(this.viewModel.getAppointmentDate());

		this.timePickerComboBox.valueProperty().bindBidirectional(this.viewModel.getSelectedTime());

		this.reasonTextArea.textProperty().bindBidirectional(this.viewModel.getReasonForAppointment());

		this.patientStatusTextField.textProperty().bindBidirectional(this.viewModel.getPatientStatus());
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

	private void setUpDoctorsComboBox() {
		try {
			//this.doctorComboBox.getItems().clear();
			List<Doctor> doctors = DoctorDAL.getAllDoctor();
			this.doctorComboBox.setItems(FXCollections.observableArrayList(doctors));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populateTimePicker() {
		LocalTime startTime = LocalTime.of(7, 0);
		LocalTime endTime = LocalTime.of(17, 0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

		while (!startTime.isAfter(endTime)) {
			this.timePickerComboBox.getItems().add(startTime.format(formatter));
			startTime = startTime.plusMinutes(20);
		}
	}

	private void validateFXMLComponents() {
		assert this.appointmentDatePicker != null
				: "fx:id=\"appointmentDatePicker\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
		assert this.doctorComboBox != null
				: "fx:id=\"doctorComboBox\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
		assert this.patientComboBox != null
				: "fx:id=\"patientComboBox\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
		assert this.patientStatusTextField != null
				: "fx:id=\"patientStatusTextField\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
		assert this.reasonTextArea != null
				: "fx:id=\"reasonTextArea\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
	}

}
