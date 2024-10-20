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
	 * login for user
	 * @param username
	 * @param password
	 * @return the user logged in
	 * @throws SQLException
	 */
	public static User loginUser(String username, String password) throws SQLException {
	    String query = "SELECT u.id, u.username, CONCAT(n.first_name, ' ', n.last_name) AS full_name " 
	    			 + "FROM `user` u " 
	    			 + "LEFT JOIN nurse n ON u.id = n.id " 
	    			 + "WHERE u.username = ? AND u.password = ?";
	    
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
	public static boolean checkUserId(String userId) throws SQLException {
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
	    String query = "";
	    
	    try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
	    	PreparedStatement stmt = conn.prepareCall(query)) {
	         
	        stmt.setString(1, patient.getLastName());
	        stmt.setString(2, patient.getFirstName());
	        stmt.setDate(3, java.sql.Date.valueOf(patient.getDob()));
	        stmt.setString(4, patient.getGender());
	        stmt.setString(5, patient.getAddressId());
	        stmt.setString(6, patient.getPhone());
	        stmt.setString(7, patient.getStatus());

	        stmt.executeQuery();
	    }
	}
	
	/**
	 * edits patient
	 * @param patient
	 * @throws SQLException
	 */
	public void editPatient(Patient patient) throws SQLException {
	    String query = "";
	    
	    try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
	    	PreparedStatement stmt = conn.prepareCall(query)) {
	         
	        stmt.setString(1, patient.getId());
	        stmt.setString(2, patient.getFirstName());
	        stmt.setDate(3, java.sql.Date.valueOf(patient.getDob()));
	        stmt.setString(4, patient.getGender());
	        stmt.setString(5, patient.getAddressId());
	        stmt.setString(6, patient.getPhone());
	        stmt.setString(7, patient.getStatus());

	        stmt.executeQuery();
	    }
	}
	
	/**
	 * adds nurse
	 * @param nurse
	 * @throws SQLException
	 */
	public void adminAddNurse(User nurse) throws SQLException {
		if (ActiveUser.getActiveUser().getRole() == UserRole.ADMIN) {
			
		}
	}
}
