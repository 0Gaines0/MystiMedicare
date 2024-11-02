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
import application.model.credentials.Patient;

public class DoctorDAL {

	private static final String QUERY_FOR_ALL_DOCTORS = "SELECT * FROM cs3230f24b.doctor";

	/**
	 * Gets the all doctor.
	 *
	 * @return the all doctor
	 * @throws SQLException 
	 */
	public static List<Doctor> getAllDoctor() throws SQLException {
		String query = QUERY_FOR_ALL_DOCTORS;

		var doctors = new ArrayList<Doctor>();

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				var address = AddressDAL.getAddressById(rs.getString("address_id"));
				var date = LocalDate.parse(rs.getString("dob"));
				var doctor = new Doctor(rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), date,
						address.getId(), rs.getString("phone"), rs.getString("gender"));
				doctors.add(doctor);
			}

			return doctors;
		}
	}
}
