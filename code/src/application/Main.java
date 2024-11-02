package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

import application.DAL.ConnectionString;

/**
 * main class
 * @author danielrivera jefferygaines
 */
public class Main extends Application {

	public static final String WINDOW_TITLE = "MystiMedicare";
	public static final String LOGIN_WINDOW = "view/LoginPage.fxml";
	public static final String NAVIGATION_PAGE = "../operations/NavigationPage.fxml";
	public static final String REGISTER_PATIENT_ANCHOR_PANE = "../operations/RegisterPatientAnchorPane.fxml";
	public static final String EDIT_PATIENT_ANCHOR_PANE = "../operations/EditPatientAnchorPane.fxml";
	public static final String SEARCH_PATIENT_ANCHOR_PANE = "../operations/SearchPatientAnchorPane.fxml";
	public static final String CREATE_APPOINTMENT_ANCHOR_PANE = "../operations/AppointmentAnchorPane.fxml";
	public static final String EDIT_APPOINTMENT_ANCHOR_PANE = "../operations/EditAppointmentAnchorPane.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {
			if (this.checkDatabaseConnection()) {
				System.out.println("Connected to the database successfully.");
			} else {
				System.out.println("Failed to connect to the database.");
			}

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

	private boolean checkDatabaseConnection() {
		try (Connection conn = DriverManager.getConnection(ConnectionString.CONNECTION_STRING)) {
			return conn != null;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
