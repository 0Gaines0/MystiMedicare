package application.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Class LabTestDAL.
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
	 * @param name the name
	 * @param unitMeasurement the unit measurement
	 * @param lowValue the low value
	 * @param highValue the high value
	 * @throws SQLException the SQL exception
	 */
	public void addLabTest(String name, String unitMeasurement, double lowValue, double highValue) throws SQLException {
	    String query = "INSERT INTO cs3230f24b.lab_test ( name, unit_measurement, low_value, high_value) " +
	                   "VALUES (?, ?, ?, ?)";
	    try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, name);
	        stmt.setString(2, unitMeasurement);
	        stmt.setDouble(3, lowValue);
	        stmt.setDouble(4, highValue);
	        stmt.executeUpdate();
	    }
	}
	
}
