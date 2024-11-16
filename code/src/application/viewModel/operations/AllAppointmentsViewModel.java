package application.viewModel.operations;

import java.sql.SQLException;
import java.util.List;

import application.DAL.AppointmentDAL;
import application.model.credentials.Appointment;
import application.model.credentials.Patient;
import application.view.operations.SearchPatientAnchorPane;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class AllAppointmentsViewModel.
 * @author Jeffrey Gaines
 */
public class AllAppointmentsViewModel {

	private StringProperty informationProperty;
	private ObjectProperty<Patient> currentPatient;
	private ListProperty<Appointment> patientAppointments;
	private AppointmentDAL appointmentDAL;

	/**
	 * Instantiates a new all appointments view model.
	 */
	public AllAppointmentsViewModel() {
		this.informationProperty = new SimpleStringProperty();
		this.currentPatient = new SimpleObjectProperty<Patient>();
		this.patientAppointments = new SimpleListProperty<Appointment>();
		this.appointmentDAL = new AppointmentDAL();
	}
	
	/**
	 * gets visit info
	 * 
	 * @param visitId
	 */
	public void getVisitInformation(String visitId) {
		String information;
		try {
			information = this.appointmentDAL.getAppointmentInformation(visitId);
			if (information.length() == 0) {
			    this.informationProperty.setValue("The appointment hasn't been completed");
			} else {
			    this.informationProperty.setValue(information.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pull all patients current appointments.
	 */
	public void pullAllPatientsCurrentAppointments() {
		List<Appointment> appointments;
		try {
			appointments = this.appointmentDAL.getAllAppointmentsForPatient(SearchPatientAnchorPane.getSelectedPatient());
			this.patientAppointments.addAll(appointments);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the current patient.
	 *
	 * @return the current patient
	 */
	public ObjectProperty<Patient> getCurrentPatient() {
		return this.currentPatient;
	}

	/**
	 * Gets the patient appointments.
	 *
	 * @return the patient appointments
	 */
	public ListProperty<Appointment> getPatientAppointments() {
		return this.patientAppointments;
	}

	/**
	 * gets the information property
	 * 
	 * @return the information property
	 */
	public StringProperty getInformationProperty() {
		return this.informationProperty;
	}
}
