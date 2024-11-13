package application.viewModel.operations;

import java.sql.SQLException;

import application.DAL.DiagnosisDAL;
import application.DAL.VisitDAL;
import application.model.credentials.ActiveUser;
import application.view.operations.SelectAppointmentAnchorPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class RoutineCheckUpAnchorPaneViewModel.
 * @author Jeffrey Gaines
 */
public class RoutineCheckUpAnchorPaneViewModel {

	/** The systolic pressure property. */
	private StringProperty systolicPressureProperty;
	
	/** The pulse property. */
	private StringProperty pulseProperty;
	
	/** The height property. */
	private StringProperty heightProperty;
	
	/** The weight property. */
	private StringProperty weightProperty;
	
	/** The diastolic blood pressure property. */
	private StringProperty diastolicBloodPressureProperty;
	
	/** The symptoms property. */
	private StringProperty symptomsProperty;
	
	/** The temp property. */
	private StringProperty tempProperty;
	
	private StringProperty initalDiagnosisProperty;
	
	/** The visit DAL. */
	private VisitDAL visitDAL;
	
	private DiagnosisDAL diagnosisDAL;
	
	/**
	 * Instantiates a new routine check up anchor pane view model.
	 */
	public RoutineCheckUpAnchorPaneViewModel() {
		this.systolicPressureProperty = new SimpleStringProperty();
		this.pulseProperty = new SimpleStringProperty();
		this.heightProperty = new SimpleStringProperty();
		this.weightProperty = new SimpleStringProperty();
		this.diastolicBloodPressureProperty = new SimpleStringProperty();
		this.symptomsProperty = new SimpleStringProperty();
		this.tempProperty = new SimpleStringProperty();
		this.visitDAL = new VisitDAL();
		this.diagnosisDAL = new DiagnosisDAL();
		this.initalDiagnosisProperty = new SimpleStringProperty();
	}
	

	/**
	 * Upload routine check up.
	 *
	 * @return true, if successful
	 */
	public boolean uploadRoutineCheckUp() {
		var appointment = SelectAppointmentAnchorPane.getSelectedAppointment();
		var nurseID = ActiveUser.getActiveUser().getId();
		var doctorID = appointment.getDoctor().getId();
		var patientID = appointment.getPatient().getId();
		var date = appointment.getDateOfAppointment() + " " + appointment.getTimeOfAppointment();
		var sysBP = this.getSystolicPressureProperty().getValue();
		var diastoBP = this.getDiastolicBloodPressureProperty().getValue();
		var temp = this.getTempProperty().getValue();
		var pulse = this.getPulseProperty().getValue();
		var height = this.getHeightProperty().getValue();
		var weight = this.getWeightProperty().getValue();
		var symptoms = this.getSymptomsProperty().getValue();
		
		try {
			this.visitDAL.addRoutineCheckUpVisit(appointment.getId(), nurseID, doctorID, patientID, date, sysBP, diastoBP, temp, pulse, height, weight, symptoms);
			var initalDiagnosis = this.getInitalDiagnosis().get();
			if (!initalDiagnosis.isBlank()) {
				var visitId = this.visitDAL.getVisitId(appointment.getId());
				this.diagnosisDAL.insertADiagnosis(visitId, initalDiagnosis, null);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets the inital diagnosis.
	 *
	 * @return the inital diagnosis
	 */
	public StringProperty getInitalDiagnosis() {
		return this.initalDiagnosisProperty;
	}

	/**
	 * Gets the systolic pressure property.
	 *
	 * @return the systolic pressure property
	 */
	public StringProperty getSystolicPressureProperty() {
		return this.systolicPressureProperty;
	}

	/**
	 * Gets the pulse property.
	 *
	 * @return the pulse property
	 */
	public StringProperty getPulseProperty() {
		return this.pulseProperty;
	}

	/**
	 * Gets the height property.
	 *
	 * @return the height property
	 */
	public StringProperty getHeightProperty() {
		return this.heightProperty;
	}

	/**
	 * Gets the weight property.
	 *
	 * @return the weight property
	 */
	public StringProperty getWeightProperty() {
		return this.weightProperty;
	}

	/**
	 * Gets the diastolic blood pressure property.
	 *
	 * @return the diastolic blood pressure property
	 */
	public StringProperty getDiastolicBloodPressureProperty() {
		return this.diastolicBloodPressureProperty;
	}

	/**
	 * Gets the symptoms property.
	 *
	 * @return the symptoms property
	 */
	public StringProperty getSymptomsProperty() {
		return this.symptomsProperty;
	}

	/**
	 * Gets the temp property.
	 *
	 * @return the temp property
	 */
	public StringProperty getTempProperty() {
		return this.tempProperty;
	}

}
