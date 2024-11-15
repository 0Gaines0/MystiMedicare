package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.credentials.Address;
import application.model.objects.LabTest;
import application.viewModel.operations.RoutineCheckUpAnchorPaneViewModel;

/**
 * The Class LabTestDAL.
 * 
 * @author Jeffrey Gaines
 */
public class LabTestDAL {

	/**
	 * Instantiates a new lab test DAL.
	 */
	public LabTestDAL() {

	}

	/**
	 * Adds the lab test.
	 *
	 * @param name            the name
	 * @param unitMeasurement the unit measurement
	 * @param lowValue        the low value
	 * @param highValue       the high value
	 * @return the lab test
	 * @throws SQLException the SQL exception
	 */
	public LabTest addLabTest(String name, String unitMeasurement, double lowValue, double highValue)
			throws SQLException {
		String query = "INSERT INTO cs3230f24b.lab_test ( name, unit_measurement, low_value, high_value) "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, unitMeasurement);
			stmt.setDouble(3, lowValue);
			stmt.setDouble(4, highValue);
			stmt.executeUpdate();

			var labCode = this.selectLatestLabCode();
			var test = new LabTest(labCode, name, lowValue, highValue);
			this.addInitLabResult(labCode);
			return test;
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
		String query = "SELECT * FROM cs3230f24b.lab_test WHERE labe_code = ?";
		LabTest test = null;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				test = new LabTest(rs.getString("labe_code"), rs.getString("name"),
						Double.parseDouble(rs.getString("low_value")), Double.parseDouble(rs.getString("high_value")));
				
			}
		}
		return test;
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
			var visit = RoutineCheckUpAnchorPaneViewModel.getCurrentVisitId();
			stmt.setString(1, visit);
			stmt.setString(2, labCode);
			stmt.setString(3, LocalDate.now().toString());
			stmt.setInt(4, -1);
			stmt.executeUpdate();

		}
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
				var test = this.selectLabTestFromLabCode(rs.getString("labe_code"));
				tests.add(test);
			}
		}
		return tests;
	}

}
