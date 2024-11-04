package application.viewModel.operations;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import application.DAL.AppointmentDAL;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class AppointmentAnchorPaneViewModel.
 * 
 * @author Jeffrey Gaines
 */
public class AppointmentAnchorPaneViewModel {

	private ObjectProperty<Patient> patientListProperty;
	private ObjectProperty<Doctor> doctorListProperty;
	private ObjectProperty<LocalDate> appointmentDate;
	private StringProperty reasonForAppointment;
	private StringProperty patientStatus;
	private ObjectProperty<String> selectedTime;
	private AppointmentDAL appointmentDAL;

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
		this.appointmentDAL = new AppointmentDAL();
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
		var time = this.convertTo24HourFormat(this.getSelectedTime().getValue());
		var reason = this.getReasonForAppointment().getValue();
		var status = this.getPatientStatus().getValue();

		try {
			if (this.appointmentDAL.appointmentExists(date, time, doctor)) {
				return false;
			}
			return this.appointmentDAL.createAppointment(patient, doctor, date, time, reason, status);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	private String convertTo24HourFormat(String time) {
		DateTimeFormatter twelveHourFormatter = DateTimeFormatter.ofPattern("h:mm a");
		DateTimeFormatter twentyFourHourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		LocalTime localTime = LocalTime.parse(time, twelveHourFormatter);
		return localTime.format(twentyFourHourFormatter);
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
