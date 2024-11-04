package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.credentials.Address;

/**
 * The Class AddressDAL.
 * 
 * @author Jeffrey Gaines
 */
public class AddressDAL {

	private static final String QUERY_FOR_ADDRESS = "SELECT a.* " 
            + "FROM `address` a "
            + "WHERE a.street = ? AND a.city = ? AND a.state = ? AND a.zip_code = ?";

	/**
	 * addressdaL
	 */
	public AddressDAL() {
		
	}
	
	/**
	 * Address exists.
	 *
	 * @param street  the street
	 * @param city    the city
	 * @param state   the state
	 * @param zipCode the zip code
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public boolean addressExists(String street, String city, String state, String zipCode) throws SQLException {
		String query = QUERY_FOR_ADDRESS;

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, street);
			pstmt.setString(2, city);
			pstmt.setString(3, state);
			pstmt.setString(4, zipCode);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Adds the address.
	 *
	 * @param street  the street
	 * @param city    the city
	 * @param state   the state
	 * @param zipCode the zip code
	 * @throws SQLException the SQL exception
	 */
	public void addAddress(String street, String city, String state, String zipCode) throws SQLException {
		if (!this.addressExists(street, city, state, zipCode)) {
			String query = "INSERT INTO address (street, city, state, zip_code) " + "VALUES (?, ?, ?, ?)";
			try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
					PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, street);
				stmt.setString(2, city);
				stmt.setString(3, state);
				stmt.setString(4, zipCode);
				stmt.executeUpdate();
			}
		}

	}

	/**
	 * Gets the address.
	 *
	 * @param street  the street
	 * @param city    the city
	 * @param state   the state
	 * @param zipCode the zip code
	 * @return the address
	 * @throws SQLException the SQL exception
	 */
	public Address getAddress(String street, String city, String state, String zipCode) throws SQLException {
		String query = QUERY_FOR_ADDRESS;

		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, street);
			pstmt.setString(2, city);
			pstmt.setString(3, state);
			pstmt.setString(4, zipCode);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				var address = new Address(rs.getString("address_id"), rs.getString("street"), rs.getString("city"),
						rs.getString("state"), rs.getString("zip_code"));
				return address;
			}
		}
		return null;
	}
	
	/**
	 * Gets the address based on the address ID.
	 *
	 * @param addressId the address ID
	 * @return the address
	 * @throws SQLException the SQL exception
	 */
	public Address getAddressById(String addressId) throws SQLException {
	    String query = "SELECT address_id, street, city, state, zip_code FROM address WHERE address_id = ?";

	    try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setString(1, addressId);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            var address = new Address(rs.getString("address_id"), rs.getString("street"), rs.getString("city"),
	                                      rs.getString("state"), rs.getString("zip_code"));
	            return address;
	        }
	    }
	    return null;
	}


}
