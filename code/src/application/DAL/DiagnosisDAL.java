package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DiagnosisDAL {
	
	private static final String INSERT_DIAGNOSIS = "INSERT INTO cs3230f24b.diagnosis (visit_id, initial_diagnosis, final_diagnosis) VALUES (?, ?, ?)";

	
	/**
	 * Instantiates a new diagnosis DAL.
	 */
	public DiagnosisDAL() {
		
	}
	
	/**
	 * Insert A diagnosis.
	 *
	 * @param visitId the visit id
	 * @param initialDiagnosis the initial diagnosis
	 * @param finalDiagnosis the final diagnosis
	 * @throws SQLException 
	 */
	public void insertADiagnosis(String visitId, String initialDiagnosis, String finalDiagnosis) throws SQLException {
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_DIAGNOSIS)) {

			pstmt.setString(1, visitId);
			pstmt.setString(2, initialDiagnosis);
			pstmt.setString(3, finalDiagnosis);
			pstmt.executeUpdate();

		}
	}
}
