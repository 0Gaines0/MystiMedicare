package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.credentials.ActiveUser;
import application.model.credentials.Appointment;
import application.model.credentials.Doctor;
import application.model.credentials.Patient;
import application.model.credentials.UserRole;

/**
 * The Class AppointmentDAL.
 * 
 * @author Jeffrey Gaines
 */
public class AppointmentDAL {

	private static final String QUERY_FOR_APPOINTMENT = "SELECT * FROM cs3230f24b.appointment WHERE date = ? AND doctor_id = ?";
	private static final String UPDATE_FOR_APPOINTMENT = "UPDATE appointment" + " SET patient_id = ?,"
			+ "doctor_id = ?," + "date = ?," + "reason = ?," + "status = ?" + " WHERE id = ?";
	private static final String QUERY_APPOINTMENT_FOR_TODAY = "SELECT * FROM cs3230f24b.appointment WHERE DATE(date) = ? AND status = ?";

	private PatientDAL patientDAL;
	private DoctorDAL doctorDAL;
	
	/**
	 * appointmentdal
	 */
	public AppointmentDAL() {
		this.patientDAL = new PatientDAL();
		this.doctorDAL = new DoctorDAL();
	}


	/**
	 * Appointment exists.
	 *
	 * @param date   the date
	 * @param time   the time
	 * @param doctor the doctor
	 * @return true, if successful
	 * @throws SQLException
	 */
	public boolean appointmentExists(LocalDate date, String time, Doctor doctor) throws SQLException {
		var result = date.toString() + " " + time;
		String query = QUERY_FOR_APPOINTMENT;

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, result);
			pstmt.setString(2, doctor.getId());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Update appointment.
	 *
	 * @param appointmentID     the appointment ID
	 * @param patient           the patient
	 * @param doctor            the doctor
	 * @param date              the date
	 * @param timeOfAppointment the time of appointment
	 * @param reason            the reason
	 * @param status            the status
	 * @return true, if successful
	 * @throws SQLException
	 */
	public boolean updateAppointment(String appointmentID, Patient patient, Doctor doctor, LocalDate date,
			String timeOfAppointment, String reason, String status) throws SQLException {
		if (this.appointmentExists(date, timeOfAppointment, doctor)) {
			return false;
		} else {
			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(UPDATE_FOR_APPOINTMENT)) {
				var dateConvert = date.toString() + " " + timeOfAppointment;

				stmt.setString(1, patient.getId());
				stmt.setString(2, doctor.getId());
				stmt.setString(3, dateConvert);
				stmt.setString(4, reason);
				stmt.setString(5, status);
				stmt.setString(6, appointmentID);
				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Appointment information updated successfully.");
					return true;
				} else {
					System.out.println("Patient not found or old address ID did not match. No update was made.");
					return false;
				}
			}
		}
	}

	/**
	 * Select appointments from today.
	 *
	 * @param date the date
	 * @return the list
	 * @throws SQLException 
	 */
	public List<Appointment> selectAppointmentsFromToday(LocalDate date) throws SQLException {
		var result = new ArrayList<Appointment>();

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(QUERY_APPOINTMENT_FOR_TODAY)) {
			
			stmt.setString(1, date.toString());
			stmt.setString(2, "scheduled");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				var id = rs.getString("id");
				var currPatient = this.patientDAL.getPatientUsingId(rs.getString("patient_id"));
				var currDoctor = this.doctorDAL.getDoctorUsingId(rs.getString("doctor_id"));
				var dateArray = rs.getString("date").split(" ");
				var apptDate = LocalDate.parse(dateArray[0]);
				var time = dateArray[1];
				var reason = rs.getString("reason");
				var status = rs.getString("status");
				if (!date.isBefore(LocalDate.now())) {
					var appointment = new Appointment(id, currPatient, currDoctor, apptDate, reason, status, time);
					result.add(appointment);
				}

			}
		}

		return result;
	}
	
	/**
	 * Sets the appointment to complete.
	 *
	 * @param appointmentID the new appointment to complete
	 * @throws SQLException 
	 */
	public void setAppointmentToComplete(String appointmentID) throws SQLException {
		String query = "UPDATE cs3230f24b.appointment SET status = 'completed' WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, appointmentID);
			stmt.executeUpdate();
		}
	}

	/**
	 * Creates the appointment.
	 *
	 * @param patient           the patient
	 * @param doctor            the doctor
	 * @param date              the date
	 * @param timeOfAppointment the time of appointment
	 * @param reason            the reason
	 * @param status            the status
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public boolean createAppointment(Patient patient, Doctor doctor, LocalDate date, String timeOfAppointment,
			String reason, String status) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.NURSE) {
			var result = date.toString() + " " + timeOfAppointment;

			String query = "INSERT INTO appointment (date, doctor_id, patient_id, reason, status) "
					+ "VALUES (?,?,?,?,?)";

			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, result);
				stmt.setString(2, doctor.getId());
				stmt.setString(3, patient.getId());
				stmt.setString(4, reason);
				stmt.setString(5, status);
				stmt.executeUpdate();
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the appointments for patient.
	 *
	 * @param patient the patient
	 * @return the appointments for patient
	 * @throws SQLException
	 */
	public List<Appointment> getAppointmentsForPatient(Patient patient) throws SQLException {
		var result = new ArrayList<Appointment>();
		String query = "SELECT * FROM cs3230f24b.appointment WHERE patient_id = ?";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, patient.getId());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				var id = rs.getString("id");
				var currPatient = this.patientDAL.getPatientUsingId(rs.getString("patient_id"));
				var currDoctor = this.doctorDAL.getDoctorUsingId(rs.getString("doctor_id"));
				var dateArray = rs.getString("date").split(" ");
				var date = LocalDate.parse(dateArray[0]);
				var time = dateArray[1];
				var reason = rs.getString("reason");
				var status = rs.getString("status");
				if (!date.isBefore(LocalDate.now())) {
					var appointment = new Appointment(id, currPatient, currDoctor, date, reason, status, time);
					result.add(appointment);
				}

			}
		}

		return result;
	}
	
	/**
	 * Gets the all appointments for patient.
	 *
	 * @param patient the patient
	 * @return the all appointments for patient
	 * @throws SQLException the SQL exception
	 */
	public List<Appointment> getAllAppointmentsForPatient(Patient patient) throws SQLException {
		var result = new ArrayList<Appointment>();
		String query = "SELECT * FROM cs3230f24b.appointment WHERE patient_id = ?";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, patient.getId());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				var id = rs.getString("id");
				var currPatient = this.patientDAL.getPatientUsingId(rs.getString("patient_id"));
				var currDoctor = this.doctorDAL.getDoctorUsingId(rs.getString("doctor_id"));
				var dateArray = rs.getString("date").split(" ");
				var date = LocalDate.parse(dateArray[0]);
				var time = dateArray[1];
				var reason = rs.getString("reason");
				var status = rs.getString("status");
				var appointment = new Appointment(id, currPatient, currDoctor, date, reason, status, time);
				result.add(appointment);

			}
		}

		return result;
	}

};