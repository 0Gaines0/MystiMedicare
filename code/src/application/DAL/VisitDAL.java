/*
 * 
 */
package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VisitDAL {

	private static final String INSERT_TO_VISIT = "INSERT INTO your_table_name (appointment_id, nurse_id, doctor_id, patient_id, date, systolic_bp, diastolic_bp, temp, pulse, height, weight, symptoms, is_editable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
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
	public void addRoutineCheckUpVisit(String appointment_id, String nurse_id, String doctor_id,
			String patient_id, String date, String systolic_bp, String diastolic_bp, String temp, String pulse,
			String height, String weight, String symptoms) throws SQLException {
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_TO_VISIT)) {

			pstmt.setString(1, appointment_id);
			pstmt.setString(2, nurse_id);
			pstmt.setString(3, doctor_id);
			pstmt.setString(4, patient_id);
			pstmt.setString(5, date);
			pstmt.setString(6, systolic_bp);
			pstmt.setString(7, diastolic_bp);
			pstmt.setString(8, temp);
			pstmt.setString(9, pulse);
			pstmt.setString(10, height);
			pstmt.setString(11, weight);
			pstmt.setString(12, symptoms);
			pstmt.setInt(13, 1);

			pstmt.executeUpdate();
			
		}
	}

}