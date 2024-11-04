package application.viewModel.operations;

import application.model.credentials.ActiveUser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
	
	private StringProperty symptomsProperty;
	
	private StringProperty tempProperty;
	
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
	}
	
	/**
	 * Upload routine check up.
	 */
	public void uploadRoutineCheckUp() {
		var nurseID = ActiveUser.getActiveUser().getId();
		
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
