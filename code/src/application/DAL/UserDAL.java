package application.DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.model.credentials.ActiveUser;
import application.model.credentials.Patient;
import application.model.credentials.User;
import application.model.credentials.UserRole;

/**
 * the user dal class
 * @author danielrivera
 */
public class UserDAL {
	
	/**
	 * userdal
	 */
	public UserDAL() {
		
	}
	
	/**
	 * login for user
	 * @param username
	 * @param password
	 * @return the user logged in
	 * @throws SQLException
	 */
	public User loginUser(String username, String password) throws SQLException {
	    String query = "SELECT u.id, u.username, u.role, u.password " 
	    			 + "FROM `user` u " 
	    			 + "LEFT JOIN nurse n ON u.id = n.id " 
	    			 + "WHERE u.id = ? AND u.password = ?";
	    
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
	 * @param patient
	 * @throws SQLException
	 */
	public void registerPatient(Patient patient) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.NURSE) {
		    String query = "INSERT INTO patient (id, last_name, first_name, dob, address_id, phone, status, gender) " 
	    				+  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
	 * @param patient
	 * @throws SQLException
	 */
	public void editPatient(Patient patient) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.NURSE) {
			String query = "UPDATE patient " 
	    				+  "SET last_name = ?, first_name = ?, dob = ?, address_id = ?, phone = ?, status = ?, gender = ? " 
	    				+  "WHERE id = ?";

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
	 * adds nurse
	 * @param nurse
	 * @throws SQLException
	 */
	public void adminAddNurse(User nurse) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.ADMIN) {
			//TODO
		}
	}
}
