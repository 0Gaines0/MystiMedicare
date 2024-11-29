
package application.DAL;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.credentials.ActiveUser;
import application.model.credentials.Patient;
import application.model.credentials.User;
import application.model.credentials.UserRole;

/**
 * the user dal class
 * 
 * @author danielrivera
 */
public class UserDAL {

	private static final String REPORT_QUERY = "SELECT v.date, p.id AS patient_id, p.first_name AS patient_first_name, p.last_name AS patient_last_name, "
	        + "d.first_name AS doctor_first_name, d.last_name AS doctor_last_name, "
	        + "n.first_name AS nurse_first_name, n.last_name AS nurse_last_name, "
	        + "t.id AS test_id, t.date_performed AS test_perform_date, t.is_abnormal AS test_is_abnormal, di.final_diagnosis AS diagnosis_name "
	        + "FROM visit v "
	        + "JOIN patient p ON v.patient_id = p.id "
	        + "JOIN doctor d ON v.doctor_id = d.id "
	        + "JOIN nurse n ON v.nurse_id = n.id "
	        + "LEFT JOIN test_result t ON v.id = t.visit_id "
	        + "LEFT JOIN diagnosis di ON v.id = di.visit_id "
	        + "WHERE DATE(v.date) BETWEEN ? AND ? "
	        + "ORDER BY v.date, p.last_name";


	/**
	 * userdal
	 */
	public UserDAL() {

	}

	/**
	 * login for user
	 * 
	 * @param username
	 * @param password
	 * @return the user logged in
	 * @throws SQLException
	 */
	public User loginUser(String username, String password) throws SQLException {
		String query = "SELECT u.id, u.username, u.role, u.password " + "FROM `user` u "
				+ "LEFT JOIN nurse n ON u.id = n.id " + "WHERE u.id = ? AND u.password = ?";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String roleString = rs.getString("role").toUpperCase();
				UserRole role;
				try {
					role = UserRole.valueOf(roleString);
				} catch (IllegalArgumentException e) {
					role = UserRole.NONE;
				}
				User user = new User(rs.getString("id"), rs.getString("username"), rs.getString("password"), role);
				return user;
			}
		}
		return null;
	}

	/**
	 * checks the userid
	 * 
	 * @param userId
	 * @return true if the id exist
	 * @throws SQLException
	 */
	public boolean checkUserId(String userId) throws SQLException {
		String query = "SELECT EXISTS(SELECT 1 FROM `user` WHERE id = ?);";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, userId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getBoolean(1);
			}
		}
		return false;
	}

	/**
	 * registers patient
	 * 
	 * @param patient
	 * @throws SQLException
	 */
	public void registerPatient(Patient patient) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.NURSE) {
			String query = "INSERT INTO patient (id, last_name, first_name, dob, address_id, phone, status, gender) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, patient.getId());
				stmt.setString(2, patient.getLastName());
				stmt.setString(3, patient.getFirstName());
				stmt.setString(4, patient.getDob().toString());
				stmt.setString(5, patient.getAddress().getId());
				stmt.setString(6, patient.getPhone());
				stmt.setString(7, patient.getStatus());
				stmt.setString(8, patient.getGender());
				stmt.executeUpdate();
			}
		}
	}

	/**
	 * edits patient
	 * 
	 * @param patient
	 * @throws SQLException
	 */
	public void editPatient(Patient patient) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.NURSE) {
			String query = "UPDATE patient "
					+ "SET last_name = ?, first_name = ?, dob = ?, address_id = ?, phone = ?, status = ?, gender = ? "
					+ "WHERE id = ?";

			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, patient.getLastName());
				stmt.setString(2, patient.getFirstName());
				stmt.setString(3, patient.getDob().toString());
				stmt.setString(4, patient.getAddress().getId());
				stmt.setString(5, patient.getPhone());
				stmt.setString(6, patient.getStatus());
				stmt.setString(7, patient.getGender());
				stmt.setString(8, patient.getId());
				stmt.executeUpdate();
			}
		}
	}

	/**
	 * gets the users full name
	 * 
	 * @param userId
	 * @return the users full name
	 * @throws SQLException
	 */
	public String retrieveUserFullName(int userId) throws SQLException {
		String query = """
				SELECT u.id, CASE u.role
					WHEN 'nurse' THEN CONCAT(n.first_name, ' ', n.last_name)
					WHEN 'doctor' THEN CONCAT(d.first_name, ' ', d.last_name)
					WHEN 'admin' THEN CONCAT(a.first_name, ' ', a.last_name)
					ELSE ''
				END AS full_name FROM
					user u
						LEFT JOIN
					nurse n ON u.id = n.id
						LEFT JOIN
					doctor d ON u.id = d.id
						LEFT JOIN
					admin a ON u.id = a.id
				WHERE u.id = ?""";
		String fullName = "";
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				fullName = rs.getString("full_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return fullName != null ? fullName : "";
	}

	/**
	 * exe the admin query
	 * 
	 * @param query
	 * 
	 * @return list of results
	 */
	public List<Map<String, Object>> exeAdminQuery(String query) {
		List<Map<String, Object>> results = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query);
				var resultSet = stmt.executeQuery(query)) {

			var metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
				results.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * the generate report
	 * 
	 * @param start
	 * @param end
	 */
	public void generateReport(LocalDate start, LocalDate end) {
		String query = REPORT_QUERY;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query);
				BufferedWriter writer = new BufferedWriter(new FileWriter("visit_report.txt"))) {
			pstmt.setDate(1, Date.valueOf(start));
			pstmt.setDate(2, Date.valueOf(end));
			ResultSet rs = pstmt.executeQuery();
			writer.write(
					"Visit Date | Patient ID | Patient Name | Doctor Name | Nurse Name | Test Name | Test Perform Date | Test Result Abnormality | Diagnosis\n");
			writer.write(
					"----------------------------------------------------------------------------------------------\n");
			while (rs.next()) {
				LocalDate visitDate = rs.getDate("date").toLocalDate();
				int patientId = rs.getInt("patient_id");
				String patientFirstName = rs.getString("patient_first_name");
				String patientLastName = rs.getString("patient_last_name");
				String doctorFirstName = rs.getString("doctor_first_name");
				String doctorLastName = rs.getString("doctor_last_name");
				String nurseFirstName = rs.getString("nurse_first_name");
				String nurseLastName = rs.getString("nurse_last_name");
				String testId = rs.getString("test_id");
				LocalDate testPerformDate = rs.getDate("test_perform_date").toLocalDate();
				String testResultAbnormality = rs.getString("test_is_abnormal");
				String diagnosisName = rs.getString("diagnosis_name");

				writer.write(String.format("%s | %d | %s %s | %s %s | %s %s | %s | %s | %s | %s\n", visitDate,
						patientId, patientFirstName, patientLastName, doctorFirstName, doctorLastName, nurseFirstName,
						nurseLastName, testId, testPerformDate, testResultAbnormality, diagnosisName));
			}
			System.out.println("Report generated successfully!");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
