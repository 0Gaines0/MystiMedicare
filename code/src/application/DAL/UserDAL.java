package application.DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAL {

//	public List<User> getEmployeesByDepartment(int dno) throws SQLException{
//		
//		List<User> list = new ArrayList<User>();
//		String query = "select fname, bdate, dno from employee where dno = ?";
//		try ( Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING); 
//				PreparedStatement stmt = connection.prepareStatement(query)){ 
//		    
//			stmt.setInt(1, dno);
//	
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next() ) {
//				String name = rs.getString("fname");
//				Date bdate = rs.getDate("bdate");
//				int dnumber = rs.getInt("dno");
//				User emoloyee = new User(name, bdate, dnumber);
//			  	list.add(emoloyee);
//			}
//
//        } 
//		return list;
//	}
}
