package application.model.credentials;

import java.time.LocalDate;


/**
 * The Class Appointment.
 * @author Jeffrey Gaines
 */
public class Appointment {

	/** The id. */
	private String id;
	
	/** The patient. */
	private Patient patient;
	
	/** The doctor. */
	private Doctor doctor;
	
	/** The date of appointment. */
	private LocalDate dateOfAppointment;
	
	/** The reason. */
	private String reason;
	
	/** The status. */
	private String status;

	/**
	 * Instantiates a new appointment with default values.
	 */
	public Appointment() {
		this.id = "";
		this.patient = null;
		this.doctor = null;
		this.dateOfAppointment = null;
		this.reason = "";
		this.status = "";
	}

	/**
	 * Instantiates a new appointment with given parameters.
	 *
	 * @param id                the appointment ID
	 * @param patient           the patient
	 * @param doctor            the doctor
	 * @param dateOfAppointment the date of appointment
	 * @param reason            the reason for the appointment
	 * @param status            the status of the appointment
	 * @throws IllegalArgumentException if any parameter is invalid
	 */
	public Appointment(String id, Patient patient, Doctor doctor, LocalDate dateOfAppointment, String reason,
			String status) {
		this.setId(id);
		this.setPatient(patient);
		this.setDoctor(doctor);
		this.setDateOfAppointment(dateOfAppointment);
		this.setReason(reason);
		this.setStatus(status);
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		if (id == null || id.trim().isEmpty()) {
			throw new IllegalArgumentException("Appointment ID cannot be empty");
		}
		this.id = id;
	}

	/**
	 * Sets the patient.
	 *
	 * @param patient the new patient
	 */
	public void setPatient(Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException("Patient cannot be null");
		}
		this.patient = patient;
	}

	/**
	 * Sets the doctor.
	 *
	 * @param doctor the new doctor
	 */
	public void setDoctor(Doctor doctor) {
		if (doctor == null) {
			throw new IllegalArgumentException("Doctor cannot be null");
		}
		this.doctor = doctor;
	}

	/**
	 * Sets the date of appointment.
	 *
	 * @param dateOfAppointment the new date of appointment
	 */
	public void setDateOfAppointment(LocalDate dateOfAppointment) {
		if (dateOfAppointment == null || dateOfAppointment.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Date of appointment must be today or a future date");
		}
		this.dateOfAppointment = dateOfAppointment;
	}

	/**
	 * Sets the reason.
	 *
	 * @param reason the new reason
	 */
	public void setReason(String reason) {
		if (reason == null || reason.trim().isEmpty()) {
			throw new IllegalArgumentException("Reason for appointment cannot be empty");
		}
		this.reason = reason;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		if (status == null || (!status.equalsIgnoreCase("scheduled") && !status.equalsIgnoreCase("completed")
				&& !status.equalsIgnoreCase("canceled"))) {
			throw new IllegalArgumentException("Status must be 'scheduled', 'completed', or 'canceled'");
		}
		this.status = status;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Gets the patient.
	 *
	 * @return the patient
	 */
	public Patient getPatient() {
		return this.patient;
	}

	/**
	 * Gets the doctor.
	 *
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return this.doctor;
	}

	/**
	 * Gets the date of appointment.
	 *
	 * @return the date of appointment
	 */
	public LocalDate getDateOfAppointment() {
		return this.dateOfAppointment;
	}

	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}
}
