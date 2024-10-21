package application.view.operations;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import application.DAL.PatientDAL;
import application.model.credentials.Patient;
import application.viewModel.operations.EditPatientAnchorPaneViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EditPatientAnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField cityTextField;

    @FXML
    private Button editBtn;

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
    private ComboBox<String> stateComboBox;
    
    @FXML
    private ListView<Patient> patientListView;
    
    @FXML
    private Button selectBtn;

    @FXML
    private TextField zipCodeTextField;
    
    private EditPatientAnchorPaneViewModel editPatientViewModel;
    

    @FXML
    void initialize() {
    	this.validateFXMLComponents();
    	this.setUpGenderComboBox();
    	this.setUpStateComboBox();
    	this.setUpPatientComboBox();
    	this.setUpEditBtn();
    	this.setUpSelectBtn();
    	this.bindToViewModel();
    }
    
    /**
     * Instantiates a new edits the patient anchor pane.
     */
    public EditPatientAnchorPane() {
    	this.editPatientViewModel = new EditPatientAnchorPaneViewModel();    }
    
	private void setUpGenderComboBox() {
		this.genderComboBox.getItems().add("Male");
		this.genderComboBox.getItems().add("Female");
	}
	
	private void setUpPatientComboBox() {
		try {
			this.patientListView.getItems().clear();
			List<Patient> patients = PatientDAL.getAllPatients();
			this.patientListView.getItems().addAll(patients);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setUpStateComboBox() {
		String[] stateAbbreviations = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
			"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
			"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA",
			"WV", "WI", "WY" };
		this.stateComboBox.getItems().addAll(stateAbbreviations);
	}

	private void setUpEditBtn() {
		this.editBtn.setOnAction(((event) -> {
			var patient = this.patientListView.getSelectionModel().getSelectedItem();
			if (patient != null) {
				var currentPatientId = patient.getId();
				this.validateTextFields();
				this.editPatientViewModel.updatePatient(currentPatientId);
				this.setUpPatientComboBox();
			}
			
		}));
	}
	
	private void setUpSelectBtn() {
		this.selectBtn.setOnAction(((event) -> {
			var patient = this.patientListView.getSelectionModel().getSelectedItem();
			if (patient != null) {
				this.patientFirstNameTextField.textProperty().set(patient.getFirstName());
				this.patientLastNameTextField.textProperty().set(patient.getLastName());
				this.genderComboBox.setValue(patient.getGender());
				this.patientDateOfBirthTextField.textProperty().set(patient.getDob());
				this.patientMobileNumberTextField.textProperty().set(patient.getPhone());
				this.patientStreetTextField.textProperty().set(patient.getAddress().getStreet());
				this.stateComboBox.setValue(patient.getAddress().getState());
				this.cityTextField.textProperty().setValue(patient.getAddress().getCity());
				this.zipCodeTextField.textProperty().setValue(patient.getAddress().getZipCode());
			}
		}));
	}
	
	private void validateTextFields() {
		var letterPattern = Pattern.compile("^[a-zA-Z]+$");
		var dateOfBirthPattern = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
		var emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		var mobilePattern = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
		var zipCodePattern = Pattern.compile("^\\d{5}$");

		var firstName = this.patientFirstNameTextField.textProperty().getValue();
		var lastName = this.patientLastNameTextField.textProperty().getValue();
		var gender = this.genderComboBox.getSelectionModel().getSelectedItem();
		var dateOfBirth = this.patientDateOfBirthTextField.textProperty().getValue();
		var email = this.patientEmailTextField.textProperty().getValue();
		var mobile = this.patientMobileNumberTextField.textProperty().getValue();
		var primaryAddress = this.patientAddressOneTextField.textProperty().getValue();
		var street = this.patientStreetTextField.textProperty().getValue();
		var state = this.stateComboBox.getSelectionModel().getSelectedItem();
		var city = this.cityTextField.textProperty().getValue();
		var zipCode = this.zipCodeTextField.textProperty().getValue();

		this.validateNameFields(letterPattern, firstName, lastName);
		this.validateComboBoxes(gender, state);
		this.validateDateOfBirth(dateOfBirthPattern, dateOfBirth);
		this.validateAddress(letterPattern, zipCodePattern, primaryAddress, street, city, zipCode);
		this.validateContactInfo(emailPattern, mobilePattern, email, mobile);
	}
	
	private void validateDateOfBirth(Pattern dateOfBirthPattern, String dateOfBirth) {
		if (dateOfBirth.isBlank() || !dateOfBirthPattern.matcher(dateOfBirth).matches()) {
			this.popUpError("Date of Birth is not entered correct, please follow this structure yyyy-MM-dd");
		}
	}

	private void validateContactInfo(Pattern emailPattern, Pattern mobilePattern, String email, String mobile) {
		if (email.isBlank() || !emailPattern.matcher(email).matches()) {
			this.popUpError("Email is invalid, please try again");
		} else if (mobile.isBlank() || !mobilePattern.matcher(mobile).matches()) {
			this.popUpError("Mobile Number is invalid, please follow this structure 123-456-7890");
		}
	}

	private void validateAddress(Pattern letterPattern, Pattern zipCodePattern, String primaryAddress, String street,
			String city, String zipCode) {
		if (primaryAddress.isBlank()) {
			this.popUpError("Primary Address is empty, please add an address");
		} else if (city.isBlank() || !letterPattern.matcher(city).matches()) {
			this.popUpError("City is invalid, please try again");
		} else if (zipCode.isBlank() || !zipCodePattern.matcher(zipCode).matches()) {
			this.popUpError("Zip Code is invalid, please enter a 5 digit number");
		} else if (street.isBlank()) {
			this.popUpError("Street is invalid, please try again");
		}
	}

	private void validateComboBoxes(String gender, String state) {
		if (gender == null) {
			this.popUpError("Gender was not selected, please select a gender");
		} else if (state == null) {
			this.popUpError("State was not selected, please select a state");
		}
	}

	private void validateNameFields(Pattern letterPattern, String firstName, String lastName) {
		if (firstName == null || firstName.isBlank() || !letterPattern.matcher(firstName).matches()) {
			this.popUpError("First Name is invalid, please try again");
		} else if (lastName == null || lastName.isBlank() || !letterPattern.matcher(lastName).matches()) {
			this.popUpError("Last Name is invalid, please try again");
		}
	}
	
	private void popUpError(String reasonForError) {
		var errorPopUp = new Alert(AlertType.ERROR);
		errorPopUp.setContentText(reasonForError);
		errorPopUp.showAndWait();
	}
	
	private void bindToViewModel() {
		this.cityTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getCityTextProperty());
		this.patientAddressOneTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientAddressOneTextProperty());
		this.patientAddressTwoTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientAddressTwoTextProperty());
		this.patientEmailTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientEmailTextProperty());
		this.patientFirstNameTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientFirstNameTextProperty());
		this.patientDateOfBirthTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientDateOfBirthTextProperty());
		this.patientLastNameTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientLastNameTextProperty());
		this.patientMobileNumberTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientMobileNumberTextProperty());
		this.patientStreetTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientStreetTextProperty());
		this.zipCodeTextField.textProperty()
				.bindBidirectional(this.editPatientViewModel.getPatientZipCodeTextProperty());
		this.genderComboBox.valueProperty()
				.bindBidirectional(this.editPatientViewModel.getSelectedGenderProperty());
		this.stateComboBox.valueProperty()
				.bindBidirectional(this.editPatientViewModel.getSelectedStateProperty());
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
		assert this.cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.cityTextField != null : "fx:id=\"cityTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.editBtn != null : "fx:id=\"editBtn\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.genderComboBox != null : "fx:id=\"genderComboBox\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientAddressOneTextField != null : "fx:id=\"patientAddressOneTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientAddressTwoTextField != null : "fx:id=\"patientAddressTwoTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientEmailTextField != null : "fx:id=\"patientEmailTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientFirstNameTextField != null : "fx:id=\"patientFirstNameTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientDateOfBirthTextField != null : "fx:id=\"patientFirstNameTextField2\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientLastNameTextField != null : "fx:id=\"patientLastNameTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientMobileNumberTextField != null : "fx:id=\"patientMobileNumberTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.patientStreetTextField != null : "fx:id=\"patientStreetTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.stateComboBox != null : "fx:id=\"stateComboBox\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
        assert this.zipCodeTextField != null : "fx:id=\"zipCodeTextField\" was not injected: check your FXML file 'EditPatientAnchorPane.fxml'.";
	}

}
