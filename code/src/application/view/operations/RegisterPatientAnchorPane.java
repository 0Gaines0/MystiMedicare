package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import application.model.credentials.ActiveUser;
import application.viewModel.operations.RegisterPatientAnchorPaneViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private DatePicker patientDateOfBirthPicker;

	@FXML
	private TextField patientFirstNameTextField;

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

	private RegisterPatientAnchorPaneViewModel registerPatientViewModel;

	@FXML
	void initialize() {
		this.validateFXMLComponents();
		this.setUpGenderComboBox();
		this.setUpStateComboBox();
		this.setUpRegisterBtn();
		this.bindToViewModel();

	}

	/**
	 * Instantiates a new register patient anchor pane.
	 */
	public RegisterPatientAnchorPane() {
		this.registerPatientViewModel = new RegisterPatientAnchorPaneViewModel();
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
	
	

	private void setUpGenderComboBox() {
		this.genderComboBox.getItems().add("Male");
		this.genderComboBox.getItems().add("Female");
	}

	private void setUpStateComboBox() {
		String[] stateAbbreviations = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
				"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
				"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA",
				"WV", "WI", "WY" };
		this.stateComboBox.getItems().addAll(stateAbbreviations);
	}

	private void setUpRegisterBtn() {
		this.registerBtn.setOnAction(((event) -> {
			if (this.validateTextFields()) {
				this.registerPatientViewModel.addPatient();
				this.popUpConformation("This patient was successfully added!");
			}
		}));
	}

	private boolean validateTextFields() {
		var letterPattern = Pattern.compile("^[a-zA-Z]+$");
		var dateOfBirthPattern = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
		var emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		var mobilePattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
		var zipCodePattern = Pattern.compile("^\\d{5}$");

		var firstName = this.patientFirstNameTextField.textProperty().getValue();
		var lastName = this.patientLastNameTextField.textProperty().getValue();
		var gender = this.genderComboBox.getSelectionModel().getSelectedItem();
		var dateOfBirth = this.patientDateOfBirthPicker.valueProperty().getValue();
		var mobile = this.patientMobileNumberTextField.textProperty().getValue();
		var street = this.patientStreetTextField.textProperty().getValue();
		var state = this.stateComboBox.getSelectionModel().getSelectedItem();
		var city = this.cityTextField.textProperty().getValue();
		var zipCode = this.zipCodeTextField.textProperty().getValue();

		var nameFields = this.validateNameFields(letterPattern, firstName, lastName);
		var comboFields = this.validateComboBoxes(gender, state);
		var dateOfBirthFields = this.validateDateOfBirth(dateOfBirthPattern, dateOfBirth);
		var addressFields = this.validateAddress(letterPattern, zipCodePattern, street, city, zipCode);
		var contactFields = this.validateContactInfo(emailPattern, mobilePattern, mobile);
		
		var validationResult = nameFields + comboFields + dateOfBirthFields + addressFields + contactFields;
		if (validationResult.isBlank()) {
			return true;
		}
		this.popUpError(validationResult);
		return false;
		
	}

	private String validateDateOfBirth(Pattern dateOfBirthPattern, LocalDate dateOfBirth) {
		var result = "";
		if (dateOfBirth == null) {
			result += "Date of Birth is not entered correct, please follow this structure yyyy-MM-dd" + System.lineSeparator();
		}
		return result;
	}

	private String validateContactInfo(Pattern emailPattern, Pattern mobilePattern, String mobile) {
		var result = "";
		if (mobile == null || mobile.isBlank() || !mobilePattern.matcher(mobile).matches()) {
			result += "Mobile Number is invalid, please follow this structure 123-456-7890" + System.lineSeparator();
		}
		return result;
	}

	private String validateAddress(Pattern letterPattern, Pattern zipCodePattern, String street, String city,
			String zipCode) {
		var result = "";
		if (city == null || city.isBlank() || !letterPattern.matcher(city).matches()) {
			result += "City is invalid, please try again";
		} 
		if (zipCode == null || zipCode.isBlank() || !zipCodePattern.matcher(zipCode).matches()) {
			result += "Zip Code is invalid, please enter a 5 digit number" + System.lineSeparator();
		} 
		if (street == null || street.isBlank()) {
			result += "Street is invalid, please try again" + System.lineSeparator();
		}
		return result;
	}

	private String validateComboBoxes(String gender, String state) {
		var result = "";
		if (gender == null) {
			result += "Gender was not selected, please select a gender" + System.lineSeparator();
		}
		if (state == null) {
			result += "State was not selected, please select a state" + System.lineSeparator();
		}
		return result;
	}

	private String validateNameFields(Pattern letterPattern, String firstName, String lastName) {
		var result = "";
		if (firstName == null || firstName.isBlank() || !letterPattern.matcher(firstName).matches()) {
			result += "First Name is invalid, please try again" + System.lineSeparator();
		} 
		if (lastName == null || lastName.isBlank() || !letterPattern.matcher(lastName).matches()) {
			result += "Last Name is invalid, please try again" + System.lineSeparator();
		}
		return result;
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

	private void bindToViewModel() {
		this.cityTextField.textProperty().bindBidirectional(this.registerPatientViewModel.getCityTextProperty());
		this.patientFirstNameTextField.textProperty()
				.bindBidirectional(this.registerPatientViewModel.getPatientFirstNameTextProperty());
		this.patientLastNameTextField.textProperty()
				.bindBidirectional(this.registerPatientViewModel.getPatientLastNameTextProperty());
		this.patientMobileNumberTextField.textProperty()
				.bindBidirectional(this.registerPatientViewModel.getPatientMoblieNumberTextProperty());
		this.patientStreetTextField.textProperty()
				.bindBidirectional(this.registerPatientViewModel.getPatientStreetTextProperty());
		this.zipCodeTextField.textProperty()
				.bindBidirectional(this.registerPatientViewModel.getPatientZipCodeTextProperty());
		this.registerPatientViewModel.getPatientDateOfBirthTextProperty().bindBidirectional(this.patientDateOfBirthPicker.valueProperty());
		this.genderComboBox.valueProperty()
				.bindBidirectional(this.registerPatientViewModel.getSelectedGenderProperty());
		this.stateComboBox.valueProperty().bindBidirectional(this.registerPatientViewModel.getSelectedStateProperty());
	}

	private void validateFXMLComponents() {
		assert this.cancelBtn != null
				: "fx:id=\"cancelBtn\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.cityTextField != null
				: "fx:id=\"cityTextField\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
		assert this.genderComboBox != null
				: "fx:id=\"genderComboBox\" was not injected: check your FXML file 'RegisterPatientAnchorPane.fxml'.";
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
