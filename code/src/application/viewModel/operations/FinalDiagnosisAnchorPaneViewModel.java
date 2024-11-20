package application.viewModel.operations;

import java.sql.SQLException;

import application.DAL.AppointmentDAL;
import application.DAL.LabTestDAL;
import application.model.objects.LabTest;
import application.view.operations.SelectAppointmentAnchorPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class FinalDiagnosisAnchorPaneViewModel {
	
	private StringProperty finalDiagnosisTextProperty;
	private StringProperty finalResultTextProperty;
	private ListProperty<LabTest> testBeingListProperty;
	private ObjectProperty<LabTest> selectedTestProperty;
	private LabTestDAL labTestDAL;
	private AppointmentDAL appointmentDAL;
	private BooleanProperty isAbnormalProperty;
	
	/**
	 * Instantiates a new final diagnosis anchor pane view model.
	 */
	public FinalDiagnosisAnchorPaneViewModel() {
		this.finalDiagnosisTextProperty = new SimpleStringProperty();
		this.testBeingListProperty = new SimpleListProperty<LabTest>();
		this.labTestDAL = new LabTestDAL();
		this.selectedTestProperty = new SimpleObjectProperty<LabTest>();
		this.isAbnormalProperty = new SimpleBooleanProperty();
		this.finalResultTextProperty = new SimpleStringProperty();
		this.appointmentDAL = new AppointmentDAL();
	}
	
	/**
	 * Insert final result.
	 *
	 * @return true, if successful
	 */
	public boolean insertFinalResultWithTest() {
		var visitId = RoutineCheckUpAnchorPaneViewModel.getCurrentVisitId();
		var labCode = this.getSelectedTestProperty().getValue().getLabCode();
		var finalResult = this.getFinalResultTextProperty().getValue();
		try {
			this.labTestDAL.insertFinalResultAndIsAbnormal(visitId, labCode, finalResult);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Finish appointment.
	 */
	public void finishAppointment() {
		var appointment = SelectAppointmentAnchorPane.getSelectedAppointment();
		try {
			this.appointmentDAL.setAppointmentToComplete(appointment.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert final diagnosis.
	 *
	 * @return true, if successful
	 */
	public boolean insertFinalDiagnosis() {
		var visitId = RoutineCheckUpAnchorPaneViewModel.getCurrentVisitId();
		var finalDiagnosis = this.getFinalDiagnosisTextProperty().getValue();
		try {
			this.labTestDAL.insertDiagnosisFromFinalPage(visitId, "", finalDiagnosis);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the final diagnosis text property.
	 *
	 * @return the final diagnosis text property
	 */
	public StringProperty getFinalDiagnosisTextProperty() {
		return this.finalDiagnosisTextProperty;
	}

	/**
	 * Gets the test being list property.
	 *
	 * @return the test being list property
	 */
	public ListProperty<LabTest> getTestBeingListProperty() {
		return this.testBeingListProperty;
	}
	
	/**
	 * Populate test list.
	 */
	public void populateTestList() {
		try {
			var visitId = RoutineCheckUpAnchorPaneViewModel.getCurrentVisitId();
			var tests = this.labTestDAL.getAllLabTestFromVisit(visitId);
			this.testBeingListProperty.setValue(FXCollections.observableArrayList(tests));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the selected test property.
	 *
	 * @return the selected test property
	 */
	public ObjectProperty<LabTest> getSelectedTestProperty() {
		return this.selectedTestProperty;
	}

	/**
	 * Gets the checks if is abnormal property.
	 *
	 * @return the checks if is abnormal property
	 */
	public BooleanProperty getIsAbnormalProperty() {
		return this.isAbnormalProperty;
	}

	/**
	 * Gets the final result text property.
	 *
	 * @return the final result text property
	 */
	public StringProperty getFinalResultTextProperty() {
		return this.finalResultTextProperty;
	}

	
}
