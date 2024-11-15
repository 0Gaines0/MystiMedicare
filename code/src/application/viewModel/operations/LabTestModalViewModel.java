package application.viewModel.operations;

import java.sql.SQLException;

import application.DAL.LabTestDAL;
import application.model.objects.LabTest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class LabTestModalViewModel.
 * @author Jeffrey Gaines
 */
public class LabTestModalViewModel {

	/** The high value property. */
	private StringProperty highValueProperty;

	/** The low value property. */
	private StringProperty lowValueProperty;

	/** The test name. */
	private String testName;
	
	private LabTestDAL labTestDAL;

	/**
	 * Instantiates a new lab test modal view model.
	 */
	public LabTestModalViewModel() {
		this.labTestDAL = new LabTestDAL();
		this.setHighValueProperty(new SimpleStringProperty());
		this.lowValueProperty = new SimpleStringProperty();
		this.testName = "";
	}
	
	/**
	 * Submit test data.
	 *
	 * @return the lab test
	 */
	public LabTest submitTestData() {
		LabTest test = null;
		try {
			var name = this.getTestName();
			var lowValue = Double.parseDouble(this.getLowValueProperty().getValue());
			var highValue = Double.parseDouble(this.getHighValueProperty().getValue());
			test = this.labTestDAL.addLabTest(name, "mIU/L", lowValue, highValue);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return test;
	}

	/**
	 * Gets the high value property.
	 *
	 * @return the high value property
	 */
	public StringProperty getHighValueProperty() {
		return this.highValueProperty;
	}

	/**
	 * Sets the high value property.
	 *
	 * @param highValueProperty the new high value property
	 */
	public void setHighValueProperty(StringProperty highValueProperty) {
		this.highValueProperty = highValueProperty;
	}

	/**
	 * Gets the low value property.
	 *
	 * @return the low value property
	 */
	public StringProperty getLowValueProperty() {
		return this.lowValueProperty;
	}

	/**
	 * Sets the low value property.
	 *
	 * @param lowValueProperty the new low value property
	 */
	public void setLowValueProperty(StringProperty lowValueProperty) {
		this.lowValueProperty = lowValueProperty;
	}

	/**
	 * Gets the test name.
	 *
	 * @return the test name
	 */
	public String getTestName() {
		return this.testName;
	}

	/**
	 * Sets the test name.
	 *
	 * @param testName the new test name
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

}
