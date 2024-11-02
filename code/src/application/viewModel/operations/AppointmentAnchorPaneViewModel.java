package application.viewModel.operations;

import java.time.LocalDate;

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

	private ListProperty<Patient> patientListProperty;
	private ListProperty<Doctor> doctorListProperty;
	private ObjectProperty<LocalDate> appointmentDate;
	private StringProperty reasonForAppointment;
	private StringProperty patientStatus;
	private ObjectProperty<String> selectedTime;
	
	/**
	 * Instantiates a new appointment anchor pane view model.
	 */
	public AppointmentAnchorPaneViewModel() {
		this.patientListProperty = new SimpleListProperty<Patient>();
		this.doctorListProperty = new SimpleListProperty<Doctor>();
		this.appointmentDate = new SimpleObjectProperty<LocalDate>();
		this.reasonForAppointment = new SimpleStringProperty();
		this.patientStatus = new SimpleStringProperty();
		this.selectedTime = new SimpleObjectProperty<String>();
		
	}

	/**
	 * Gets the patient list property.
	 *
	 * @return the patient list property
	 */
	public ListProperty<Patient> getPatientListProperty() {
		return this.patientListProperty;
	}

	/**
	 * Gets the doctor list property.
	 *
	 * @return the doctor list property
	 */
	public ListProperty<Doctor> getDoctorListProperty() {
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
