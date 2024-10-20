package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.credentials.ActiveUser;
import application.model.credentials.Patient;
import application.model.credentials.UserRole;

/**
 * The Class PatientDAL.
 * @author Jeffrey Gaines
 */
public class PatientDAL {

	private static final String QUERY_FOR_PATIENT = "SELECT * " + "FROM patient p "
			+ "WHERE p.last_name = ? AND p.first_name = ? AND p.dob = ? AND p.gender = ? AND p.address_id = ?";

	/**
	 * Patient exists.
	 *
	 * @param firstName the first name
	 * @param lastName  the last name
	 * @param dob       the dob
	 * @param gender    the gender
	 * @param addessID  the addess ID
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public static boolean patientExists(String firstName, String lastName, String dob, String gender, String addessID)
			throws SQLException {

		String query = QUERY_FOR_PATIENT;

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, lastName);
			pstmt.setString(2, firstName);
			pstmt.setString(3, dob);
			pstmt.setString(4, gender);
			pstmt.setString(5, addessID);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the patient.
	 *
	 * @param firstName the first name
	 * @param lastName  the last name
	 * @param dob       the dob
	 * @param gender    the gender
	 * @param addessID  the addess ID
	 * @return the patient
	 * @throws SQLException the SQL exception
	 */
	public static Patient getPatient(String firstName, String lastName, String dob, String gender, String addessID)
			throws SQLException {
		String query = QUERY_FOR_PATIENT;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, lastName);
			pstmt.setString(2, firstName);
			pstmt.setString(3, dob);
			pstmt.setString(4, gender);
			pstmt.setString(5, addessID);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				var address = AddressDAL.getAddressById(rs.getString("address_id"));
				var patient = new Patient(rs.getString("id"), rs.getString("last_name"), rs.getString("first_name"),
						rs.getString("dob"), address, rs.getString("gender"), rs.getString("phone"),
						rs.getString("status"));
				return patient;
			}
		}

		return null;
	}

	/**
	 * Register patient.
	 *
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param dob the dob
	 * @param gender the gender
	 * @param addressId the address id
	 * @param phone the phone
	 * @param status the status
	 * @throws SQLException the SQL exception
	 */
	public static void registerPatient(String lastName, String firstName, String dob, String gender, String addressId,
			String phone, String status) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.NURSE) {
			String query = "INSERT INTO patient (last_name, first_name, dob, gender, address_id, phone, status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, lastName);
				stmt.setString(2, firstName);
				stmt.setString(3, dob);
				stmt.setString(4, gender);
				stmt.setString(5, addressId);
				stmt.setString(6, phone);
				stmt.setString(7, status);
				stmt.executeUpdate();
			}
		}
	}
}
