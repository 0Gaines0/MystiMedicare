package application;

import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	public static final String WINDOW_TITLE = "MystiMedicare";
	public static final String LOGIN_WINDOW = "view/LoginPage.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {
			this.connectToDataBase();
			Parent parent = FXMLLoader.load(getClass().getResource(Main.LOGIN_WINDOW));
			Scene scene = new Scene(parent);
			primaryStage.setTitle(WINDOW_TITLE);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void connectToDataBase() {
		String connectionString = "jdbc:mysql://cs-dblab01.uwg.westga.edu:3306/cs3230f24b?user=yourusername&password=yourpassword";

		try (Connection con = DriverManager.getConnection(connectionString);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select lname,DNO from employee")) {

			System.out.println("name: \t\t     Dnumber");
			while (rs.next()) {
				String name = rs.getString(1);
				int number = rs.getInt(2);
				System.out.println(name + "\t\t" + number);
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
