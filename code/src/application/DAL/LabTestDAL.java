package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import application.model.objects.LabTest;
import application.viewModel.operations.RoutineCheckUpAnchorPaneViewModel;

/**
 * The Class LabTestDAL.
 * 
 * @author Jeffrey Gaines
 */
public class LabTestDAL {
	
	private static final String QUERY_FOR_LAB_TEST = "SELECT * FROM cs3230f24b.lab_test WHERE labe_code = ?";

	/**
	 * Instantiates a new lab test DAL.
	 */
	public LabTestDAL() {

	}

	/**
	 * Adds the lab test.
	 *
	 * @param labCode the lab code
	 */
	public void addLabTest(String labCode) {
		try {
			this.addInitLabResult(labCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select latest lab code.
	 *
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String selectLatestLabCode() throws SQLException {
		String query = "SELECT * FROM cs3230f24b.lab_test ORDER BY labe_code DESC LIMIT 1";
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				var labCode = rs.getString("labe_code");
				return labCode;
			}
		}
		return "";
	}

	/**
	 * Select lab test from lab code.
	 *
	 * @param labCode the lab code
	 * @return the lab test
	 * @throws SQLException the SQL exception
	 */
	public LabTest selectLabTestFromLabCode(String labCode) throws SQLException {
		String query = QUERY_FOR_LAB_TEST;
		LabTest test = null;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, labCode);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				test = new LabTest(rs.getString("labe_code"), rs.getString("name"),
						Double.parseDouble(rs.getString("low_value")), Double.parseDouble(rs.getString("high_value")));

			}
		}
		return test;
	}

	/**
	 * Gets the all lab tests.
	 *
	 * @return the all lab tests
	 * @throws SQLException the SQL exception
	 */
	public List<LabTest> getAllLabTests() throws SQLException {
		String query = "SELECT * FROM cs3230f24b.lab_test";
		var tests = new ArrayList<LabTest>();
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				var test = new LabTest(rs.getString("labe_code"), rs.getString("name"),
						Double.parseDouble(rs.getString("low_value")), Double.parseDouble(rs.getString("high_value")));
				tests.add(test);

			}
		}
		return tests;

	}

	/**
	 * Insert diagnosis from final page.
	 *
	 * @param visitId        the visit id
	 * @param initDiagnosis  the init diagnosis
	 * @param finalDiagnosis the final diagnosis
	 * @throws SQLException
	 */
	public void insertDiagnosisFromFinalPage(String visitId, String initDiagnosis, String finalDiagnosis)
			throws SQLException {
		if (this.getDiagnosisFromVisitId(visitId)) {
			var updateQuery = "UPDATE cs3230f24b.diagnosis SET initial_diagnosis = ?, final_diagnosis = ? WHERE visit_id = ?";
			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
				stmt.setString(1, initDiagnosis);
				stmt.setString(2, finalDiagnosis);
				stmt.setString(3, visitId);
				stmt.executeUpdate();

			}
		} else {
			var insertQuery = "INSERT INTO cs3230f24b.diagnosis (visit_id, initial_diagnosis, final_diagnosis) VALUES (?, ?, ?);";
			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
				stmt.setString(1, visitId);
				stmt.setString(2, initDiagnosis);
				stmt.setString(3, finalDiagnosis);
				stmt.executeUpdate();

			}
		}
	}

	/**
	 * Gets the diagnosis from visit id.
	 *
	 * @param visitId the visit id
	 * @return the diagnosis from visit id
	 * @throws SQLException the SQL exception
	 */
	public boolean getDiagnosisFromVisitId(String visitId) throws SQLException {
		String query = "SELECT * FROM cs3230f24b.diagnosis WHERE visit_id = ?";
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, visitId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;

			}
		}
		return false;
	}

	/**
	 * Adds the init lab result.
	 *
	 * @param labCode the lab code
	 * @throws SQLException the SQL exception
	 */
	public void addInitLabResult(String labCode) throws SQLException {
		String insertResult = "INSERT INTO cs3230f24b.test_result (visit_id, lab_code, date_performed, is_abnormal)"
				+ "VALUES (?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(insertResult)) {
			LocalDateTime now = LocalDateTime.now();
		        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDateTime = now.format(formatter);

			var visit = RoutineCheckUpAnchorPaneViewModel.getCurrentVisitId();
			stmt.setString(1, visit);
			stmt.setString(2, labCode);
			stmt.setString(3, formattedDateTime);
			stmt.setInt(4, -1);
			stmt.executeUpdate();

		}
	}

	/**
	 * Insert final diagnosis and is abnormal.
	 *
	 * @param visitId     the visit id
	 * @param labCode     the lab code
	 * @param finalResult the final result
	 * @throws SQLException the SQL exception
	 */
	public void insertFinalResultAndIsAbnormal(String visitId, String labCode, String finalResult) throws SQLException {
		var currentTest = this.getLabTestFromLabCode(labCode);
		var abnormal = 0;

		var numericResult = Double.parseDouble(finalResult);
		if (numericResult > currentTest.getHighValue() || numericResult < currentTest.getLowValue()) {
			abnormal = 1;
		}

		String sql = "UPDATE cs3230f24b.test_result SET result = ?, is_abnormal = ? WHERE visit_id = ? AND lab_code = ?";
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, finalResult);
			stmt.setInt(2, abnormal);
			stmt.setString(3, visitId);
			stmt.setString(4, labCode);
			stmt.executeUpdate();

		}

	}

	/**
	 * Gets the lab test from lab code.
	 *
	 * @param labCode the lab code
	 * @return the lab test from lab code
	 * @throws SQLException the SQL exception
	 */
	public LabTest getLabTestFromLabCode(String labCode) throws SQLException {
		LabTest test = null;
		String query = QUERY_FOR_LAB_TEST;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, labCode);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				test = new LabTest(rs.getString("labe_code"), rs.getString("name"),
						Double.parseDouble(rs.getString("low_value")), Double.parseDouble(rs.getString("high_value")));
			}
		}
		return test;

	}

	/**
	 * Gets the all lab test from visit.
	 *
	 * @param visitId the visit id
	 * @return the all lab test from visit
	 * @throws SQLException the SQL exception
	 */
	public List<LabTest> getAllLabTestFromVisit(String visitId) throws SQLException {
		String getAllQuery = "SELECT * FROM cs3230f24b.test_result WHERE visit_id = ?";
		var tests = new ArrayList<LabTest>();
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(getAllQuery)) {
			stmt.setString(1, visitId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				var test = this.selectLabTestFromLabCode(rs.getString("lab_code"));
				tests.add(test);
			}
		}
		return tests;
	}

}
