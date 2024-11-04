package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.credentials.Patient;
import application.viewModel.operations.SearchPatientAnchorPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SearchPatientAnchorPane {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker dobPicker;

	@FXML
	private TextField fnameField;

	@FXML
	private TextField lnameField;

	@FXML
	private ListView<Patient> patientListview;

	@FXML
	private AnchorPane searchAnchorPane;

	@FXML
	private Button searchButton;

	@FXML
	private Button selectPatientButton;
	private static Patient selectedPatient;

	private SearchPatientAnchorPageViewModel viewModel;
	private EditAppointmentAnchorPane editApptCodeBehind;

	/**
	 * the search patient anchor pane
	 */
	public SearchPatientAnchorPane() {
		this.viewModel = new SearchPatientAnchorPageViewModel();
		this.editApptCodeBehind = new EditAppointmentAnchorPane();
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

	@FXML
	void initialize() {
		this.validateFXML();
		this.setupButtons();
		this.bindToViewModel();
	}

	private void setupButtons() {
		this.searchButton.setOnAction((event) -> {
			var result = this.validateAllFields();
			if (!result.isBlank()) {
				this.popUpError(result);
			} else {
				this.patientListview.getItems().setAll(this.viewModel.searchPatients());
				this.popUpConformation("Searching for patient");
			}

		});
		this.selectPatientButton.setOnAction((event) -> {
			if (this.patientListview.getSelectionModel().getSelectedItem() != null) {
				selectedPatient = this.patientListview.getSelectionModel().getSelectedItem();
				this.editApptCodeBehind.openAnchorPane((BorderPane) this.searchAnchorPane.getParent(),
						Main.EDIT_APPOINTMENT_ANCHOR_PANE);
			} else {
				this.popUpError("Patient must be selected");
			}

		});
	}

	private String validateAllFields() {
		var result = "";

		if (this.fnameField.textProperty().getValue() == null || this.fnameField.textProperty().getValue().isBlank()) {
			result += "First name must be inputted and valid" + System.lineSeparator();
		}
		if (this.lnameField.textProperty().getValue() == null || this.lnameField.textProperty().getValue().isBlank()) {
			result += "Last name must be inputted and valid" + System.lineSeparator();
		}
		if (this.dobPicker.getValue() == null) {
			result += "Date of Birth must be selected" + System.lineSeparator();
		}

		return result;
	}

	private void bindToViewModel() {
		this.fnameField.textProperty().bindBidirectional(this.viewModel.getPatientFirstNameTextProperty());
		this.lnameField.textProperty().bindBidirectional(this.viewModel.getPatientLastNameTextProperty());
		this.viewModel.getPatientDateOfBirthTextProperty().bindBidirectional(this.dobPicker.valueProperty());
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

	private void validateFXML() {
		assert this.dobPicker != null
				: "fx:id=\"dobPicker\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
		assert this.fnameField != null
				: "fx:id=\"fnameField\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
		assert this.lnameField != null
				: "fx:id=\"lnameField\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
		assert this.patientListview != null
				: "fx:id=\"patientListview\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
		assert this.searchAnchorPane != null
				: "fx:id=\"searchAnchorPane\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
		assert this.searchButton != null
				: "fx:id=\"searchButton\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
		assert this.selectPatientButton != null
				: "fx:id=\"selectPatientButton\" was not injected: check your FXML file 'SearchPatientAnchorPane.fxml'.";
	}

	/**
	 * Gets the selected patient.
	 *
	 * @return the selected patient
	 */
	public static Patient getSelectedPatient() {
		return selectedPatient;
	}

}
