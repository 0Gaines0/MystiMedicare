package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RegisterPatientAnchorPane {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button cancelBtn;

	@FXML
	private TextField cityTextField;

	@FXML
	private ComboBox<String> genderComboBox;

	@FXML
	private TextField patientAddressOneTextField;

	@FXML
	private TextField patientAddressTwoTextField;

	@FXML
	private TextField patientEmailTextField;

	@FXML
	private TextField patientFirstNameTextField;

	@FXML
	private TextField patientDateOfBirthTextField;

	@FXML
	private TextField patientLastNameTextField;

	@FXML
	private TextField patientMobileNumberTextField;

	@FXML
	private TextField patientStreetTextField;

	@FXML
	private Button registerBtn;

	@FXML
	private ComboBox<String> stateComboBox;

	@FXML
	private TextField zipCodeTextField;

	@FXML
	void initialize() {
		this.validateFXMLComponents();

	}

	/**
	 * Instantiates a new register patient anchor pane.
	 */
	public RegisterPatientAnchorPane() {

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
	
	private void validateFXMLComponents() {
		assert this.cancelBtn != null
				: "fx:id=\"cancelBtn\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.cityTextField != null
				: "fx:id=\"cityTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.genderComboBox != null
				: "fx:id=\"genderComboBox\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientAddressOneTextField != null
				: "fx:id=\"patientAddressOneTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientAddressTwoTextField != null
				: "fx:id=\"patientAddressTwoTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientEmailTextField != null
				: "fx:id=\"patientEmailTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientFirstNameTextField != null
				: "fx:id=\"patientFirstNameTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientDateOfBirthTextField != null
				: "fx:id=\"patientFirstNameTextField2\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientLastNameTextField != null
				: "fx:id=\"patientLastNameTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientMobileNumberTextField != null
				: "fx:id=\"patientMobileNumberTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.patientStreetTextField != null
				: "fx:id=\"patientStreetTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.registerBtn != null
				: "fx:id=\"registerBtn\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.stateComboBox != null
				: "fx:id=\"stateComboBox\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.zipCodeTextField != null
				: "fx:id=\"zipCodeTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
	}

}
