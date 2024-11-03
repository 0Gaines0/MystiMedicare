package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.DAL.AppointmentDAL;
import application.DAL.DoctorDAL;
import application.model.credentials.Appointment;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import application.viewModel.operations.EditAppointmentAnchorPaneViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private ComboBox<String> patientStatusComboBox;

	@FXML
	private TextArea reasonTextArea;

	@FXML
	private ComboBox<String> timePickerComboBox;

	@FXML
	private ComboBox<Appointment> patientAppointments;

	private EditAppointmentAnchorPaneViewModel viewmodel;

	/**
	 * the edit appointment anchor pane
	 */
	public EditAppointmentAnchorPane() {
		this.viewmodel = new EditAppointmentAnchorPaneViewModel();
	}

	@FXML
	void initialize() {
		this.validateFXML();
		this.bindToViewModel();
		this.setupButtons();
		this.populateTimePicker();
		this.setUpStatusComboBox();
		this.setUpAppointmentComboBox();
		this.listenForChangedAppointment();
		// set doctor to appt doctor
		// set the appt date and time to the earliest appt
		// fill in reason and status
	}

	private void listenForChangedAppointment() {
		this.patientAppointments.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldEvent, newEvent) -> {
					if (newEvent != null) {
						this.setUpPatientComboBox(newEvent.getPatient());
						this.setUpDoctorsComboBox(newEvent.getDoctor());
						this.setUpDatePicker(newEvent.getDateOfAppointment());
						this.setUpTimePicker(newEvent.getTimeOfAppointment());
						this.reasonTextArea.setText(newEvent.getReason());
						this.patientStatusComboBox.setValue(newEvent.getStatus());

					}
				});
	}

	private void setUpStatusComboBox() {
		this.patientStatusComboBox.getItems().add("Scheduled");
		this.patientStatusComboBox.getItems().add("Completed");
		this.patientStatusComboBox.getItems().add("Canncelled");

	}

	private void setUpAppointmentComboBox() {
		try {
			var appointments = AppointmentDAL.getAppointmentsForPatient(SearchPatientAnchorPane.getSelectedPatient());
			this.patientAppointments.setItems(FXCollections.observableArrayList(appointments));
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

	private void setUpTimePicker(String time) {
		var convertedTime = this.convertTo12HourFormat(time);
		this.timePickerComboBox.setValue(convertedTime);

	}

	private String convertTo12HourFormat(String time) {
		DateTimeFormatter twentyFourHourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter twelveHourFormatter = DateTimeFormatter.ofPattern("h:mm a");

		LocalTime localTime = LocalTime.parse(time, twentyFourHourFormatter);
		return localTime.format(twelveHourFormatter);
	}

	private void setUpDatePicker(LocalDate date) {
		this.appointmentDatePicker.valueProperty().set(date);
	}

	private void setUpPatientComboBox(Patient patient) {
		this.patientComboBox.getItems().clear();
		this.patientComboBox.getItems().add(patient);
		this.patientComboBox.setValue(patient);
	}

	private void setUpDoctorsComboBox(Doctor doctor) {
		try {
			List<Doctor> doctors = DoctorDAL.getAllDoctor();
			this.doctorComboBox.setItems(FXCollections.observableArrayList(doctors));
			this.doctorComboBox.setValue(doctor);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setupButtons() {
		this.cancelBtn.setOnAction((event) -> {
			this.openAnchorPane((BorderPane) this.editAnchorPane.getParent(), Main.SEARCH_PATIENT_ANCHOR_PANE);
		});
		this.updateBtn.setOnAction((event) -> {
			var result = this.viewmodel.updateAppointment();
			if (result) {
				this.popUpConformation("Appointment was updated");
			} else {
				this.popUpError("Appointment time already exist, please try another");
			}
		});
	}

	private void popUpError(String reasonForError) {
		var errorPopUp = new Alert(AlertType.ERROR);
		errorPopUp.setContentText(reasonForError);
		errorPopUp.showAndWait();
	}

	private void popUpConformation(String reasonForConfirm) {
		var errorPopUp = new Alert(AlertType.CONFIRMATION);
		errorPopUp.setContentText(reasonForConfirm);
		errorPopUp.showAndWait();
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
			error.printStackTrace();
		}
	}

	private void bindToViewModel() {
		this.doctorComboBox.valueProperty().bindBidirectional(this.viewmodel.getDoctorListProperty());
		this.patientComboBox.valueProperty().bindBidirectional(this.viewmodel.getPatientListProperty());

		this.appointmentDatePicker.valueProperty().bindBidirectional(this.viewmodel.getAppointmentDate());

		this.timePickerComboBox.valueProperty().bindBidirectional(this.viewmodel.getSelectedTime());

		this.reasonTextArea.textProperty().bindBidirectional(this.viewmodel.getReasonForAppointment());

		this.patientStatusComboBox.valueProperty().bindBidirectional(this.viewmodel.getPatientStatus());
		this.patientAppointments.valueProperty().bindBidirectional(this.viewmodel.getSelectedAppointmentProperty());
	}

	private void validateFXML() {
		assert this.appointmentDatePicker != null
				: "fx:id=\"appointmentDatePicker\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.cancelBtn != null
				: "fx:id=\"cancelBtn\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.updateBtn != null
				: "fx:id=\"createAppointmentBtn\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.doctorComboBox != null
				: "fx:id=\"doctorComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.patientComboBox != null
				: "fx:id=\"patientComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.editAnchorPane != null
				: "fx:id=\"editAnchorPane\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.patientStatusComboBox != null
				: "fx:id=\"patientStatusTextField\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.reasonTextArea != null
				: "fx:id=\"reasonTextArea\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
		assert this.timePickerComboBox != null
				: "fx:id=\"timePickerComboBox\" was not injected: check your FXML file 'EditAppointmentAnchorPane.fxml'.";
	}

}
