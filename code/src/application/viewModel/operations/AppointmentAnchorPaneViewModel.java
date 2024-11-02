package application.viewModel.operations;

import java.time.LocalDate;

import application.model.credentials.AppointmentsManager;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class AppointmentAnchorPaneViewModel.
 * @author Jeffrey Gaines
 */
public class AppointmentAnchorPaneViewModel {

	private ObjectProperty<Patient> patientListProperty;
	private ObjectProperty<Doctor> doctorListProperty;
	private ObjectProperty<LocalDate> appointmentDate;
	private StringProperty reasonForAppointment;
	private StringProperty patientStatus;
	private ObjectProperty<String> selectedTime;
	private AppointmentsManager appointmentManager;
	
	/**
	 * Instantiates a new appointment anchor pane view model.
	 */
	public AppointmentAnchorPaneViewModel() {
		this.patientListProperty = new SimpleObjectProperty<Patient>();
		this.doctorListProperty = new SimpleObjectProperty<Doctor>();
		this.appointmentDate = new SimpleObjectProperty<LocalDate>();
		this.reasonForAppointment = new SimpleStringProperty();
		this.patientStatus = new SimpleStringProperty();
		this.selectedTime = new SimpleObjectProperty<String>();
		this.appointmentManager = new AppointmentsManager();
		
	}
	
	/**
	 * Adds the appointment.
	 *
	 * @return true, if successful
	 */
	public boolean addAppointment() {
		var patient = this.getPatientListProperty().getValue();
		var doctor = this.getDoctorListProperty().getValue();
		var date = this.getAppointmentDate().getValue();
		var time = this.getSelectedTime().getValue();
		var reason = this.getReasonForAppointment().getValue();
		var status = this.getPatientStatus().getValue();
		
		return this.appointmentManager.createAppointment(patient, doctor, date, time, reason, status);
	}

	/**
	 * Gets the patient list property.
	 *
	 * @return the patient list property
	 */
	public ObjectProperty<Patient> getPatientListProperty() {
		return this.patientListProperty;
	}

	/**
	 * Gets the doctor list property.
	 *
	 * @return the doctor list property
	 */
	public ObjectProperty<Doctor> getDoctorListProperty() {
		return this.doctorListProperty;
	}

	/**
	 * Gets the appointment date.
	 *
	 * @return the appointment date
	 */
	public ObjectProperty<LocalDate> getAppointmentDate() {
		return this.appointmentDate;
	}

	/**
	 * Gets the reason for appointment.
	 *
	 * @return the reason for appointment
	 */
	public StringProperty getReasonForAppointment() {
		return this.reasonForAppointment;
	}

	/**
	 * Gets the patient status.
	 *
	 * @return the patient status
	 */
	public StringProperty getPatientStatus() {
		return this.patientStatus;
	}

	/**
	 * Gets the selected time.
	 *
	 * @return the selected time
	 */
	public ObjectProperty<String> getSelectedTime() {
		return this.selectedTime;
	}
}
