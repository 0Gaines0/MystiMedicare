package application.viewModel.operations;

import java.sql.SQLException;

import application.DAL.LabTestDAL;
import application.model.objects.LabTest;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class FinalDiagnosisAnchorPaneViewModel {
	
	private StringProperty finalDiagnosisTextProperty;
	private ListProperty<LabTest> testBeingListProperty;
	private LabTestDAL labTestDAL;

	/**
	 * Instantiates a new final diagnosis anchor pane view model.
	 */
	public FinalDiagnosisAnchorPaneViewModel() {
		this.finalDiagnosisTextProperty = new SimpleStringProperty();
		this.testBeingListProperty = new SimpleListProperty<LabTest>();
		this.labTestDAL = new LabTestDAL();
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
	
}
