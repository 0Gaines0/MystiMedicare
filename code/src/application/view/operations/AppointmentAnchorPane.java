package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import application.DAL.DoctorDAL;
import application.DAL.PatientDAL;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import application.viewModel.operations.AppointmentAnchorPaneViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
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
	private ComboBox<String> patientStatusComboBox;

	@FXML
	private TextArea reasonTextArea;

	@FXML
	private ComboBox<String> timePickerComboBox;

	@FXML
	private Button cancelBtn;

	@FXML
	private Button createAppointmentBtn;

	private AppointmentAnchorPaneViewModel viewModel;
	private PatientDAL patientDAL;
	private DoctorDAL doctorDAL;

	@FXML
	void initialize() {
		this.validateFXMLComponents();
		this.bindToViewModel();
		this.populateTimePicker();
		this.setUpCreateAppointmentBtn();
		this.setUpDoctorsComboBox();
		this.setUpPatientsComboBox();
		this.setUpStatusComboBox();

	}

	/**
	 * Instantiates a new appointment anchor pane.
	 */
	public AppointmentAnchorPane() {
		this.viewModel = new AppointmentAnchorPaneViewModel();
		this.patientDAL = new PatientDAL();
		this.doctorDAL = new DoctorDAL();
	}

	private void setUpStatusComboBox() {
		this.patientStatusComboBox.getItems().add("Scheduled");
		this.patientStatusComboBox.getItems().add("Completed");
		this.patientStatusComboBox.getItems().add("Canncelled");
		this.patientStatusComboBox.setValue("Scheduled");

	}

	private void setUpCreateAppointmentBtn() {
		this.createAppointmentBtn.setOnAction(((event) -> {
			var result = this.validateAllFields();
			if (!result.isBlank()) {
				this.popUpError(result);
			} else if (this.viewModel.addAppointment()) {
				this.popUpConformation("Appointment was added!");
			} else {
				this.popUpError("That date and time was already book, try another time");
			}
		}));
	}

	private String validateAllFields() {
		var result = "";
		result += this.validateComboBoxes();
		result += this.validateDatePicker();
		result += this.validateReasonForAppointment();

		return result;
	}

	private String validateReasonForAppointment() {
		var result = "";

		if (this.reasonTextArea.textProperty().getValue() == null
				|| this.reasonTextArea.textProperty().getValue().isBlank()) {
			result += "Reason must be inputted" + System.lineSeparator();
		}

		return result;
	}

	private String validateDatePicker() {
		var result = "";

		if (this.appointmentDatePicker.getValue() == null) {
			result += "Date for appointment must be selected" + System.lineSeparator();
		} else if (this.appointmentDatePicker.getValue().isBefore(LocalDate.now())) {
			result += "Date must be after the current date" + System.lineSeparator();
		}

		return result;
	}

	private String validateComboBoxes() {
		var result = "";
		if (this.patientComboBox.getSelectionModel().getSelectedItem() == null) {
			result += "Patient must be selected" + System.lineSeparator();
		}
		if (this.doctorComboBox.getSelectionModel().getSelectedItem() == null) {
			result += "Doctor must be selected" + System.lineSeparator();
		}
		if (this.timePickerComboBox.getSelectionModel().getSelectedItem() == null) {
			result += "Time for appointment must be selected" + System.lineSeparator();
		}
		if (this.patientStatusComboBox.getSelectionModel().getSelectedItem() == null) {
			result += "Status for appointment must be selected" + System.lineSeparator();
		}
		return result;
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

	private void bindToViewModel() {
		this.doctorComboBox.valueProperty().bindBidirectional(this.viewModel.getDoctorListProperty());
		this.patientComboBox.valueProperty().bindBidirectional(this.viewModel.getPatientListProperty());

		this.appointmentDatePicker.valueProperty().bindBidirectional(this.viewModel.getAppointmentDate());

		this.timePickerComboBox.valueProperty().bindBidirectional(this.viewModel.getSelectedTime());

		this.reasonTextArea.textProperty().bindBidirectional(this.viewModel.getReasonForAppointment());

		this.patientStatusComboBox.valueProperty().bindBidirectional(this.viewModel.getPatientStatus());
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
			List<Doctor> doctors = this.doctorDAL.getAllDoctor();
			this.doctorComboBox.setItems(FXCollections.observableArrayList(doctors));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setUpPatientsComboBox() {
		try {
			List<Patient> patients = this.patientDAL.getAllPatients();
			this.patientComboBox.setItems(FXCollections.observableArrayList(patients));
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
		assert this.patientStatusComboBox != null
				: "fx:id=\"patientStatusComboBox\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
		assert this.reasonTextArea != null
				: "fx:id=\"reasonTextArea\" was not injected: check your FXML file 'AppointmentAnchorPane.fxml'.";
	}

}
