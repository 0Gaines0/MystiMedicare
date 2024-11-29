package application.DAL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.credentials.ActiveUser;
import application.model.credentials.Address;
import application.model.credentials.Patient;
import application.model.credentials.UserRole;

/**
 * The Class PatientDAL.
 * 
 * @author Jeffrey Gaines
 */
public class PatientDAL {

	private static final String QUERY_FOR_PATIENT = "SELECT * " + "FROM patient p "
			+ "WHERE p.last_name = ? AND p.first_name = ? AND p.dob = ? AND p.gender = ? AND p.address_id = ?";

	private static final String QUERY_FOR_ALL_PATIENTS = "SELECT * FROM cs3230f24b.patient";
	
	private static final String QUERY_FOR_PATIENT_ID = "SELECT * FROM cs3230f24b.patient p WHERE p.id = ?";
	
	private static final String QUERY_FOR_PATIENT_SEARCH = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.dob, \n"
			+ "    a.address_id, a.street, a.city, a.state, a.zip_code, \n"
			+ "    p.phone, p.status, p.gender\n"
			+ "FROM patient p\n"
			+ "LEFT JOIN address a ON p.address_id = a.address_id\n"
			+ "LEFT JOIN visit v ON p.id = v.patient_id\n"
			+ "LEFT JOIN diagnosis d ON v.diagnosis_id = d.id\n"
			+ "LEFT JOIN test_result tr ON v.testresults_id = tr.id\n"
			+ "LEFT JOIN lab_test lt ON tr.lab_code = lt.labe_code\n"
			+ "WHERE (p.first_name LIKE ? OR ? IS NULL) \n"
			+ "    AND (p.last_name LIKE ? OR ? IS NULL) \n"
			+ "    AND (p.dob = ? OR ? IS NULL);\n"
			+ "";

	private AddressDAL addressDAL;
	
	/**
	 * patientdal
	 */
	public PatientDAL() {
		this.addressDAL = new AddressDAL();
	}
	
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
	public boolean patientExists(String firstName, String lastName, String dob, String gender, String addessID)
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
     * searches patients 
     * 
     * @param fName
     * @param lName
     * @param dob
     * @return patients that match desc
     */
    public List<Patient> handleSearch(String fName, String lName, LocalDate dob) {
        String query = QUERY_FOR_PATIENT_SEARCH;
        List<Patient> patients = new ArrayList<Patient>();

        try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = conn.prepareStatement(query)) {
        	
            stmt.setString(1, fName == null ? null : "%" + fName + "%");
            stmt.setString(2, fName);
            stmt.setString(3, lName == null ? null : "%" + lName + "%");
            stmt.setString(4, lName);
            stmt.setDate(5, dob == null ? null : Date.valueOf(dob));
            stmt.setDate(6, dob == null ? null : Date.valueOf(dob));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                LocalDate patientDob = LocalDate.parse(rs.getString("dob"));
                Address address = this.addressDAL.getAddressById(rs.getString("address_id"));
                String phone = rs.getString("phone");
                String status = rs.getString("status");
                String gender = rs.getString("gender");

                Patient patient = new Patient(id, lastName, firstName, patientDob, address, phone, status, gender);
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

	/**
	 * Gets the all patients.
	 *
	 * @return the all patients
	 * @throws SQLException the SQL exception
	 */
	public List<Patient> getAllPatients() throws SQLException {
		String query = QUERY_FOR_ALL_PATIENTS;
		var patients = new ArrayList<Patient>();

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				var address = this.addressDAL.getAddressById(rs.getString("address_id"));
				var patient = new Patient(rs.getString("id"), rs.getString("last_name"), rs.getString("first_name"),
						LocalDate.parse(rs.getString("dob")), address, rs.getString("phone"), rs.getString("status"),
						rs.getString("gender"));
				patients.add(patient);
			}

			return patients;
		}
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
	public Patient getPatient(String firstName, String lastName, String dob, String gender, String addessID)
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
				var address = this.addressDAL.getAddressById(rs.getString("address_id"));
				var patient = new Patient(rs.getString("id"), rs.getString("last_name"), rs.getString("first_name"),
						LocalDate.parse(rs.getString("dob")), address, rs.getString("gender"), rs.getString("phone"),
						rs.getString("status"));
				return patient;
			}
		}

		return null;
	}
	
	/**
	 * Gets the patient using id.
	 *
	 * @param id the id
	 * @return the patient using id
	 * @throws SQLException the SQL exception
	 */
	public Patient getPatientUsingId(String id) throws SQLException {
		String query = QUERY_FOR_PATIENT_ID;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				var address = this.addressDAL.getAddressById(rs.getString("address_id"));
				var patient = new Patient(rs.getString("id"), rs.getString("last_name"), rs.getString("first_name"),
						LocalDate.parse(rs.getString("dob")), address, rs.getString("gender"), rs.getString("phone"),
						rs.getString("status"));
				return patient;
			}
		}

		return null;
	}

	/**
	 * Register patient.
	 *
	 * @param lastName  the last name
	 * @param firstName the first name
	 * @param dob       the dob
	 * @param gender    the gender
	 * @param addressId the address id
	 * @param phone     the phone
	 * @param status    the status
	 * @throws SQLException the SQL exception
	 */
	public void registerPatient(String lastName, String firstName, String dob, String gender, String addressId,
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

	
	/**
	 * Update patient.
	 *
	 * @param patientId the patient id
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param dob the dob
	 * @param gender the gender
	 * @param newAddressId the new address id
	 * @param phone the phone
	 * @param status the status
	 * @throws SQLException the SQL exception
	 */
	public void updatePatient(String patientId, String lastName, String firstName, String dob, String gender, String newAddressId, String phone, String status) throws SQLException {
		String query = "UPDATE patient SET last_name = ?, first_name = ?, dob = ?, gender = ?, "
				+ "address_id = ?, phone = ?, status = ? WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, lastName);
			stmt.setString(2, firstName);
			stmt.setString(3, dob);
			stmt.setString(4, gender);
			stmt.setString(5, newAddressId);
			stmt.setString(6, phone);
			stmt.setString(7, status);
			stmt.setString(8, patientId);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Patient information updated successfully.");
			} else {
				System.out.println("Patient not found or old address ID did not match. No update was made.");
			}
		}
	}
}
