package application.model.credentials;

import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentsManager {

	private ArrayList<Appointment> appointmentList;

	/**
	 * Instantiates a new appointments manager.
	 */
	public AppointmentsManager() {
		this.appointmentList = new ArrayList<Appointment>();
	}

	/**
	 * Creates the appointment.
	 *
	 * @param patient the patient
	 * @param doctor the doctor
	 * @param date the date
	 * @param timeOfAppointment the time of appointment
	 * @param reason the reason
	 * @param status the status
	 * @return true, if successful
	 */
	public boolean createAppointment(Patient patient, Doctor doctor, LocalDate date, String timeOfAppointment, String reason, String status) {
		if (this.appointmentExists(date, timeOfAppointment)) {
			return false;
		} else {
			var appointment = new Appointment(patient, doctor, date, reason, status, timeOfAppointment);
			this.appointmentList.add(appointment);
			return true;
		}
	}
	
	private boolean appointmentExists(LocalDate date, String time) {
		for (var appointment : this.appointmentList) {
			if (appointment.getDateOfAppointment().equals(date) && appointment.getTimeOfAppointment().equals(time)) {
				return true;
			}
		}
		return false;
	}
	
	

}
