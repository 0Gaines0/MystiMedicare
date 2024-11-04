/*
 * 
 */
package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class VisitDAL {

	private static final String INSERT_TO_VISIT = "INSERT INTO visit (appointment_id, nurse_id, doctor_id, patient_id, date, systolic_bp, diastolic_bp, temp, pulse, height, weight, symptoms, is_editable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
	/**
	 * Adds the routine check up visit.
	 *
	 * @param appointment_id the appointment id
	 * @param nurse_id the nurse id
	 * @param doctor_id the doctor id
	 * @param patient_id the patient id
	 * @param date the date
	 * @param systolic_bp the systolic bp
	 * @param diastolic_bp the diastolic bp
	 * @param temp the temp
	 * @param pulse the pulse
	 * @param height the height
	 * @param weight the weight
	 * @param symptoms the symptoms
	 * @throws SQLException the SQL exception
	 */
	public void addRoutineCheckUpVisit(String appointment_id, String nurse_id, String doctor_id, String patient_id,
			String date, String systolic_bp, String diastolic_bp, String temp, String pulse, String height,
			String weight, String symptoms) throws SQLException {
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_TO_VISIT)) {

			pstmt.setString(1, appointment_id);
			pstmt.setString(2, nurse_id);
			pstmt.setString(3, doctor_id);
			pstmt.setString(4, patient_id);
			pstmt.setString(5, date);
			pstmt.setInt(6, Integer.parseInt(systolic_bp));
			pstmt.setInt(7, Integer.parseInt(diastolic_bp));
			pstmt.setDouble(8, Double.parseDouble(temp));
			pstmt.setInt(9, Integer.parseInt(pulse));
			pstmt.setDouble(10, Double.parseDouble(height));
			pstmt.setDouble(11, Double.parseDouble(weight));
			pstmt.setString(12, symptoms);
			pstmt.setInt(13, 1);

			pstmt.executeUpdate();

		}
	}

}