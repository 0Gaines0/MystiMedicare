package application.viewModel.operations;

import java.time.LocalDate;
import java.util.List;

import application.DAL.PatientDAL;
import application.model.credentials.Patient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchPatientAnchorPageViewModel {

	private StringProperty patientFirstNameTextProperty;
	private StringProperty patientLastNameTextProperty;
	private ObjectProperty<LocalDate> patientDateOfBirthTextProperty;
	private PatientDAL patientDAL;
	
	/**
	 * the search patient anchor page view model 
	 */
	public SearchPatientAnchorPageViewModel() {
		this.patientFirstNameTextProperty = new SimpleStringProperty();
		this.patientLastNameTextProperty = new SimpleStringProperty();
		this.patientDateOfBirthTextProperty = new SimpleObjectProperty<LocalDate>();
		this.patientDAL = new PatientDAL();
	}
	
	/**
	 * searches patients
	 * 
	 * @return an arraylist of patients
	 */
	public List<Patient> searchPatients() {
		return this.patientDAL.handleSearch(this.getPatientFirstNameTextProperty().getValue(), 
									   this.getPatientLastNameTextProperty().getValue(), 
									   this.getPatientDateOfBirthTextProperty().getValue());
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

}
