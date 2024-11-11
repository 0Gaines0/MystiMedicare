package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import application.DAL.AppointmentDAL;
import application.model.credentials.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SelectAppointmentAnchorPane {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
    private AnchorPane baseAnchorPane;

	@FXML
	private ListView<Appointment> appointmentListView;

	@FXML
	private Button selectAppointmentBtn;
	private AppointmentDAL appointmentDAL;
	
	private static Appointment selectedAppointment;
	private RoutineCheckUpAnchorPane routineCheckUpCodeBehind;

	@FXML
	void initialize() {
		this.validateFXMLComponets();
		this.populateAppointmentsListView();
		this.setUpSelectBtn();
	}

	/**
	 * Instantiates a new select appointment anchor pane.
	 */
	public SelectAppointmentAnchorPane() {
		this.routineCheckUpCodeBehind = new RoutineCheckUpAnchorPane();
		this.appointmentDAL = new AppointmentDAL();
		
	}

	private void setUpSelectBtn() {
		this.selectAppointmentBtn.setOnAction(((event) -> {
			if (this.appointmentListView.getSelectionModel().getSelectedItem() != null) {
				this.popUpConformation("Appointment selected!");
				selectedAppointment = this.appointmentListView.getSelectionModel().getSelectedItem();
				this.routineCheckUpCodeBehind.openAnchorPane((BorderPane) this.baseAnchorPane.getParent(), Main.ROUTINE_CHECKOUT_ANCHOR_PANE);
			} else {
				this.popUpError("Appointment must be selected");
			}
		}));
	}
	
	private void popUpError(String reasonForError) {
		var errorPopUp = new Alert(AlertType.ERROR);
		errorPopUp.setContentText(reasonForError);
		errorPopUp.showAndWait();
	}
	
	private void popUpConformation(String reasonForConfirm) {
		var errorPopUp = new Alert(AlertType.CONFIRMATION);
		errorPopUp.setContentText(reasonForConfirm);
		errorPopUp.showAndWait();
	}

	private void populateAppointmentsListView() {

		try {
			var todayDate = LocalDate.now();
			ObservableList<Appointment> obsList;
			obsList = FXCollections.observableArrayList(this.appointmentDAL.selectAppointmentsFromToday(todayDate));
			this.appointmentListView.setItems(obsList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open anchor pane.
	 *
	 * @param parent        the parent
	 * @param newAnchorPath the new anchor path
	 */
	public void openAnchorPane(BorderPane parent, String newAnchorPath) {
		try {
			AnchorPane currentAnchor = (AnchorPane) parent.getCenter();
			var loader = new FXMLLoader(getClass().getResource(newAnchorPath));
			AnchorPane newAnchor = loader.load();
			parent.setCenter(newAnchor);
			parent.getChildren().remove(currentAnchor);
		} catch (IOException error) {
			error.getMessage();
		}
	}

	private void validateFXMLComponets() {
		assert this.appointmentListView != null
				: "fx:id=\"appointmentListView\" was not injected: check your FXML file 'SelectAppointmentAnchorPane.fxml'.";
		assert this.selectAppointmentBtn != null
				: "fx:id=\"selectAppointmentBtn\" was not injected: check your FXML file 'SelectAppointmentAnchorPane.fxml'.";
	}

	/**
	 * Gets the selected appointment.
	 *
	 * @return the selected appointment
	 */
	public static Appointment getSelectedAppointment() {
		return selectedAppointment;
	}

}
