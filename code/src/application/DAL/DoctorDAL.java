package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.model.credentials.Doctor;

public class DoctorDAL {

	private static final String QUERY_FOR_ALL_DOCTORS = "SELECT * FROM cs3230f24b.doctor";
	private static final String QUERY_FOR_DOCTOR_ID = "SELECT * FROM cs3230f24b.doctor WHERE id = ?";
	private AddressDAL addressDAL;
	
	/**
	 * doctordal
	 */
	public DoctorDAL() {
		this.addressDAL = new AddressDAL();
	}

	/**
	 * Gets the all doctor.
	 *
	 * @return the all doctor
	 * @throws SQLException 
	 */
	public List<Doctor> getAllDoctor() throws SQLException {
		String query = QUERY_FOR_ALL_DOCTORS;

		var doctors = new ArrayList<Doctor>();

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				var address = this.addressDAL.getAddressById(rs.getString("address_id"));
				var date = LocalDate.parse(rs.getString("dob"));
				var doctor = new Doctor(rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), date,
						address.getId(), rs.getString("phone"), rs.getString("gender"));
				doctors.add(doctor);
			}

			return doctors;
		}
	}
	
	/**
	 * Gets the patient using id.
	 *
	 * @param id the id
	 * @return the patient using id
	 * @throws SQLException the SQL exception
	 */
	public Doctor getDoctorUsingId(String id) throws SQLException {
		String query = QUERY_FOR_DOCTOR_ID;
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				var address = this.addressDAL.getAddressById(rs.getString("address_id"));
				var doctor = new Doctor(rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"),
						LocalDate.parse(rs.getString("dob")), address.getId(), rs.getString("phone"), rs.getString("gender"));
				return doctor;
			}
		}

		return null;
	}
	
	
}
