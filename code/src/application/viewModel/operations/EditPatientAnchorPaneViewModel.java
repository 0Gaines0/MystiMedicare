package application.viewModel.operations;

import java.sql.SQLException;
import application.DAL.AddressDAL;
import application.DAL.PatientDAL;
import application.model.credentials.Address;
import application.model.credentials.Patient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditPatientAnchorPaneViewModel {

	private ObjectProperty<String> selectedGenderProperty;
	private ObjectProperty<String> selectedStateProperty;
	private StringProperty cityTextProperty;
	private StringProperty patientAddressOneTextProperty;
	private StringProperty patientAddressTwoTextProperty;
	private StringProperty patientEmailTextProperty;
	private StringProperty patientFirstNameTextProperty;
	private StringProperty patientDateOfBirthTextProperty;
	private StringProperty patientLastNameTextProperty;
	private StringProperty patientMobileNumberTextProperty;
	private StringProperty patientStreetTextProperty;
	private StringProperty patientZipCodeTextProperty;

	private Patient currentPatient;

	/**
	 * Instantiates a new Edit Patient view model.
	 */
	public EditPatientAnchorPaneViewModel() {
		this.selectedGenderProperty = new SimpleObjectProperty<String>();
		this.selectedStateProperty = new SimpleObjectProperty<String>();
		this.cityTextProperty = new SimpleStringProperty();
		this.patientAddressOneTextProperty = new SimpleStringProperty();
		this.patientAddressTwoTextProperty = new SimpleStringProperty();
		this.patientEmailTextProperty = new SimpleStringProperty();
		this.patientFirstNameTextProperty = new SimpleStringProperty();
		this.patientLastNameTextProperty = new SimpleStringProperty();
		this.patientMobileNumberTextProperty = new SimpleStringProperty();
		this.patientStreetTextProperty = new SimpleStringProperty();
		this.patientZipCodeTextProperty = new SimpleStringProperty();
		this.patientDateOfBirthTextProperty = new SimpleStringProperty();
	}

	/**
	 * Loads the patient information into the view model for editing.
	 * @param patient
	 */
//	public void loadPatient(Patient patient) {
//		this.currentPatient = patient;
//		this.patientFirstNameTextProperty.set(patient.getFirstName());
//		this.patientLastNameTextProperty.set(patient.getLastName());
//		this.patientDateOfBirthTextProperty.set(patient.getDob());
//		this.selectedGenderProperty.set(patient.getGender());
//		this.patientMobileNumberTextProperty.set(patient.getPhone());
//		try {
//			var address = AddressDAL.getAddressById(patient.getAddress().toString());
//			this.patientStreetTextProperty.set(address.getStreet());
//			this.cityTextProperty.set(address.getCity());
//			this.selectedStateProperty.set(address.getState());
//			this.patientZipCodeTextProperty.set(address.getZipCode());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Updates the patient information in the database.
	 */
	public void updatePatient() {
		var address = this.handleUpdatingAndFindingAddress();
		this.handleUpdatingPatient(address.getId());
	}
	
	private void handleUpdatingPatient(String addressId) {
		var lastName = this.getPatientLastNameTextProperty().get();
		var firstName = this.getPatientFirstNameTextProperty().get();
		var dob = this.getPatientDateOfBirthTextProperty().get();
		var gender = this.getSelectedGenderProperty().get();
		var phone = this.getPatientMobileNumberTextProperty().get();

		try {
			this.currentPatient = PatientDAL.getPatient(firstName, lastName, dob, gender, addressId);
			PatientDAL.updatePatient(this.currentPatient.getId(), lastName, firstName, dob, gender, this.currentPatient.getAddress().getId(), addressId, phone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Address handleUpdatingAndFindingAddress() {
		var street = this.getPatientStreetTextProperty().get();
		var city = this.getCityTextProperty().get();
		var state = this.getSelectedStateProperty().get();
		var zipCode = this.getPatientZipCodeTextProperty().get();
		try {
			if (!AddressDAL.addressExists(street, city, state, zipCode)) {
				AddressDAL.addAddress(street, city, state, zipCode);
			}
			var address = AddressDAL.getAddress(street, city, state, zipCode);
			return address;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * gets gender property
	 * @return gender property
	 */
	public ObjectProperty<String> getSelectedGenderProperty() {
		return this.selectedGenderProperty;
	}

	/**
	 * gets selected state
	 * @return selected state
	 */
	public ObjectProperty<String> getSelectedStateProperty() {
		return this.selectedStateProperty;
	}

	/**
	 * gets city
	 * @return city
	 */
	public StringProperty getCityTextProperty() {
		return this.cityTextProperty;
	}

	/**
	 * gets address one
	 * @return address one
	 */
	public StringProperty getPatientAddressOneTextProperty() {
		return this.patientAddressOneTextProperty;
	}

	/**
	 * gets address two
	 * @return address two
	 */
	public StringProperty getPatientAddressTwoTextProperty() {
		return this.patientAddressTwoTextProperty;
	}

	/**
	 * gets email
	 * @return email
	 */
	public StringProperty getPatientEmailTextProperty() {
		return this.patientEmailTextProperty;
	}

	/**
	 * gets first name
	 * @return first name
	 */
	public StringProperty getPatientFirstNameTextProperty() {
		return this.patientFirstNameTextProperty;
	}

	/**
	 * gets dob
	 * @return dob 
	 */
	public StringProperty getPatientDateOfBirthTextProperty() {
		return this.patientDateOfBirthTextProperty;
	}

	/**
	 * gets last name
	 * @return last name
	 */
	public StringProperty getPatientLastNameTextProperty() {
		return this.patientLastNameTextProperty;
	}

	/**
	 * gets mobile number
	 * @return mobile number
	 */
	public StringProperty getPatientMobileNumberTextProperty() {
		return this.patientMobileNumberTextProperty;
	}

	/**
	 * gets street property
	 * @return street
	 */
	public StringProperty getPatientStreetTextProperty() {
		return this.patientStreetTextProperty;
	}

	/**
	 * gets zip code property
	 * @return zip code
	 */
	public StringProperty getPatientZipCodeTextProperty() {
		return this.patientZipCodeTextProperty;
	}
}
