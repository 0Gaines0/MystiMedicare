package application.viewModel.operations;

import java.sql.SQLException;
import java.time.LocalDate;

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
	private StringProperty patientFirstNameTextProperty;
	private ObjectProperty<LocalDate> patientDateOfBirthProperty;
	private StringProperty patientLastNameTextProperty;
	private StringProperty patientMobileNumberTextProperty;
	private StringProperty patientStreetTextProperty;
	private StringProperty patientZipCodeTextProperty;


	/**
	 * Instantiates a new Edit Patient view model.
	 */
	public EditPatientAnchorPaneViewModel() {
		this.selectedGenderProperty = new SimpleObjectProperty<String>();
		this.selectedStateProperty = new SimpleObjectProperty<String>();
		this.cityTextProperty = new SimpleStringProperty();
		this.patientFirstNameTextProperty = new SimpleStringProperty();
		this.patientLastNameTextProperty = new SimpleStringProperty();
		this.patientMobileNumberTextProperty = new SimpleStringProperty();
		this.patientStreetTextProperty = new SimpleStringProperty();
		this.patientZipCodeTextProperty = new SimpleStringProperty();
		this.patientDateOfBirthProperty = new SimpleObjectProperty<LocalDate>();
	}

	
	/**
	 * Update patient.
	 *
	 * @param currentPatientId the current patient id
	 */
	public void updatePatient(String currentPatientId) {
		Patient currentPatientRow;
		try {
			currentPatientRow = PatientDAL.getPatientUsingId(currentPatientId);
			var address = this.handleUpdatingAndFindingAddress();
			this.handleUpdatingPatient(currentPatientId, address.getId(), currentPatientRow.getAddress().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void handleUpdatingPatient(String patientId, String addressId, String oldAddressId) {
		var lastName = this.getPatientLastNameTextProperty().get();
		var firstName = this.getPatientFirstNameTextProperty().get();
		var dob = this.getPatientDateOfBirthTextProperty().get().toString();
		var gender = this.getSelectedGenderProperty().get();
		var phone = this.getPatientMobileNumberTextProperty().get();

		try {
			PatientDAL.updatePatient(patientId, lastName, firstName, dob, gender, addressId, phone);
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
	 * 
	 * @return gender property
	 */
	public ObjectProperty<String> getSelectedGenderProperty() {
		return this.selectedGenderProperty;
	}

	/**
	 * gets selected state
	 * 
	 * @return selected state
	 */
	public ObjectProperty<String> getSelectedStateProperty() {
		return this.selectedStateProperty;
	}

	/**
	 * gets city
	 * 
	 * @return city
	 */
	public StringProperty getCityTextProperty() {
		return this.cityTextProperty;
	}


	/**
	 * gets first name
	 * 
	 * @return first name
	 */
	public StringProperty getPatientFirstNameTextProperty() {
		return this.patientFirstNameTextProperty;
	}

	/**
	 * gets dob
	 * 
	 * @return dob
	 */
	public ObjectProperty<LocalDate> getPatientDateOfBirthTextProperty() {
		return this.patientDateOfBirthProperty;
	}

	/**
	 * gets last name
	 * 
	 * @return last name
	 */
	public StringProperty getPatientLastNameTextProperty() {
		return this.patientLastNameTextProperty;
	}

	/**
	 * gets mobile number
	 * 
	 * @return mobile number
	 */
	public StringProperty getPatientMobileNumberTextProperty() {
		return this.patientMobileNumberTextProperty;
	}

	/**
	 * gets street property
	 * 
	 * @return street
	 */
	public StringProperty getPatientStreetTextProperty() {
		return this.patientStreetTextProperty;
	}

	/**
	 * gets zip code property
	 * 
	 * @return zip code
	 */
	public StringProperty getPatientZipCodeTextProperty() {
		return this.patientZipCodeTextProperty;
	}
}
