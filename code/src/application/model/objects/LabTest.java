package application.model.objects;

/**
 * The Class LabTest.
 * 
 * @author Jeffrey Gaines
 */
public class LabTest {
	
	private String labCode;

	/** The name. */
	private String name;

	/** The unit measurement. */
	private String unitMeasurement = "mIU/L";

	/** The low value. */
	private double lowValue;

	/** The high value. */
	private double highValue;
	
	private boolean isAbnormal;
	
	/**
	 * Instantiates a new lab test.
	 *
	 * @param labCode the lab code
	 * @param name the name
	 * @param lowValue the low value
	 * @param highValue the high value
	 */
	public LabTest(String labCode, String name, double lowValue, double highValue) {
		this.labCode = labCode;
		this.setName(name);
		this.setLowValue(lowValue);
		this.setHighValue(highValue);
	}

	/**
	 * Instantiates a new lab test.
	 *
	 * @param name      the name
	 * @param lowValue  the low value
	 * @param highValue the high value
	 */
	public LabTest(String name, double lowValue, double highValue) {
		this.setName(name);
		this.setLowValue(lowValue);
		this.setHighValue(highValue);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty.");
		}
		this.name = name;
	}

	/**
	 * Gets the unit measurement.
	 *
	 * @return the unit measurement
	 */
	public String getUnitMeasurement() {
		return this.unitMeasurement;
	}

	/**
	 * Gets the low value.
	 *
	 * @return the low value
	 */
	public double getLowValue() {
		return this.lowValue;
	}

	/**
	 * Sets the low value.
	 *
	 * @param lowValue the new low value
	 */
	public void setLowValue(double lowValue) {
		if (lowValue < 0) {
			throw new IllegalArgumentException("Low value cannot be negative.");
		}
		this.lowValue = lowValue;
	}

	/**
	 * Gets the high value.
	 *
	 * @return the high value
	 */
	public double getHighValue() {
		return this.highValue;
	}

	/**
	 * Sets the high value.
	 *
	 * @param highValue the new high value
	 */
	public void setHighValue(double highValue) {
		if (highValue <= this.lowValue) {
			throw new IllegalArgumentException("High value must be greater than low value.");
		}
		this.highValue = highValue;
	}
	
	/**
	 * Gets the lab code.
	 *
	 * @return the lab code
	 */
	public String getLabCode() {
		return this.labCode;
	}
	
	/**
	 * Sets the lab code.
	 *
	 * @param labCode the new lab code
	 */
	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}
	
	/**
	 * Checks if is abnormal.
	 *
	 * @return true, if is abnormal
	 */
	public boolean isAbnormal() {
		return this.isAbnormal;
	}
	
	/**
	 * Sets the checks if is abnormal.
	 *
	 * @param isAbnormal the new checks if is abnormal
	 */
	public void setIsAbnormal(boolean isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	

	@Override
	public String toString() {
		return this.name + " Test";
	}
}
