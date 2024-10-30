package application.viewModel.operations;

import java.sql.SQLException;
import java.time.LocalDate;

import application.DAL.AddressDAL;
import application.DAL.PatientDAL;
import application.model.credentials.Address;
import application.model.credentials.Patient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterPatientAnchorPaneViewModel {

	private ObjectProperty<String> selectedGenderProperty;
	private ObjectProperty<String> selectedStateProperty;
	private StringProperty cityTextProperty;
	private StringProperty patientAddressOneTextProperty;
	private StringProperty patientAddressTwoTextProperty;
	private StringProperty patientEmailTextProperty;
	private StringProperty patientFirstNameTextProperty;
	private ObjectProperty<LocalDate> patientDateOfBirthTextProperty;
	private StringProperty patientLastNameTextProperty;
	private StringProperty patientMoblieNumberTextProperty;
	private StringProperty patientStreetTextProperty;
	private StringProperty patientZipCodeTextProperty;

	/**
	 * Instantiates a new register patient anchor pane view model.
	 */
	public RegisterPatientAnchorPaneViewModel() {
		this.selectedGenderProperty = new SimpleObjectProperty<String>();
		this.selectedStateProperty = new SimpleObjectProperty<String>();
		this.cityTextProperty = new SimpleStringProperty();
		this.patientAddressOneTextProperty = new SimpleStringProperty();
		this.patientAddressTwoTextProperty = new SimpleStringProperty();
		this.patientEmailTextProperty = new SimpleStringProperty();
		this.patientFirstNameTextProperty = new SimpleStringProperty();
		this.patientLastNameTextProperty = new SimpleStringProperty();
		this.patientMoblieNumberTextProperty = new SimpleStringProperty();
		this.patientStreetTextProperty = new SimpleStringProperty();
		this.patientZipCodeTextProperty = new SimpleStringProperty();
		this.patientDateOfBirthTextProperty = new SimpleObjectProperty<LocalDate>();
	}

	/**
	 * Adds the patient.
	 */
	public void addPatient() {
		var address = this.handleAddingAndFindingAddress();
		this.handleAddingAndFindingPatient(address.getId());
		
	}
	
	private Patient handleAddingAndFindingPatient(String addressId) {
		var lastName = this.getPatientLastNameTextProperty().get();
		var firstName = this.getPatientFirstNameTextProperty().get();
		var dob = this.getPatientDateOfBirthTextProperty().get().toString();
		var gender = this.getSelectedGenderProperty().get();
		var phone = this.getPatientMoblieNumberTextProperty().get();
		var status = "active";
		
		try {
			if (!PatientDAL.patientExists(firstName, lastName, dob, gender, addressId)) {
				PatientDAL.registerPatient(lastName, firstName, dob, gender, addressId, phone, status);
			}
			var patient = PatientDAL.getPatient(firstName, lastName, dob, gender, addressId);
			return patient;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Address handleAddingAndFindingAddress() {
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
	 * Gets the selected gender property.
	 *
	 * @return the selected gender property
	 */
	public ObjectProperty<String> getSelectedGenderProperty() {
		return this.selectedGenderProperty;
	}

	/**
	 * Gets the selected state property.
	 *
	 * @return the selected state property
	 */
	public ObjectProperty<String> getSelectedStateProperty() {
		return this.selectedStateProperty;
	}

	/**
	 * Gets the city text property.
	 *
	 * @return the city text property
	 */
	public StringProperty getCityTextProperty() {
		return this.cityTextProperty;
	}

	/**
	 * Gets the patient address one text property.
	 *
	 * @return the patient address one text property
	 */
	public StringProperty getPatientAddressOneTextProperty() {
		return this.patientAddressOneTextProperty;
	}

	/**
	 * Gets the patient address two text property.
	 *
	 * @return the patient address two text property
	 */
	public StringProperty getPatientAddressTwoTextProperty() {
		return this.patientAddressTwoTextProperty;
	}

	/**
	 * Gets the patient email text property.
	 *
	 * @return the patient email text property
	 */
	public StringProperty getPatientEmailTextProperty() {
		return this.patientEmailTextProperty;
	}

	/**
	 * Gets the patient first name text property.
	 *
	 * @return the patient first name text property
	 */
	public StringProperty getPatientFirstNameTextProperty() {
		return this.patientFirstNameTextProperty;
	}

	/**
	 * Gets the patient date of birth text property.
	 *
	 * @return the patient date of birth text property
	 */
	public ObjectProperty<LocalDate> getPatientDateOfBirthTextProperty() {
		return this.patientDateOfBirthTextProperty;
	}

	/**
	 * Gets the patient last name text property.
	 *
	 * @return the patient last name text property
	 */
	public StringProperty getPatientLastNameTextProperty() {
		return this.patientLastNameTextProperty;
	}

	/**
	 * Gets the patient moblie number text property.
	 *
	 * @return the patient moblie number text property
	 */
	public StringProperty getPatientMoblieNumberTextProperty() {
		return this.patientMoblieNumberTextProperty;
	}

	/**
	 * Gets the patient street text property.
	 *
	 * @return the patient street text property
	 */
	public StringProperty getPatientStreetTextProperty() {
		return this.patientStreetTextProperty;
	}

	/**
	 * Gets the patient zip code text property.
	 *
	 * @return the patient zip code text property
	 */
	public StringProperty getPatientZipCodeTextProperty() {
		return this.patientZipCodeTextProperty;
	}

}
