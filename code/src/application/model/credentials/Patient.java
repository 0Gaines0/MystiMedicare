package application.model.credentials;

/**
 * The Patient class represents a patient in the system.
 * 
 * @author danielrivera
 */
public class Patient {

	private String id;
	private String lastName;
	private String firstName;
	private String dob;
	private Address address;
	private String phone;
	private String status;
	private String gender;

	private static final String PARAM_MUST_NOT_BE_EMPTY_OR_NULL = "must not be empty or null";

	/**
	 * Instantiates a new patient.
	 */
	public Patient() {
		this.id = "";
		this.lastName = "";
		this.firstName = "";
		this.dob = "";
		this.address = null;
		this.phone = "";
		this.status = "active";
		this.gender = "";
	}

	/**
	 * Instantiates a new patient.
	 *
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param dob the dob
	 * @param address the address
	 * @param phone the phone
	 * @param status the status
	 * @param gender the gender
	 */
	public Patient(String lastName, String firstName, String dob, Address address, String phone,
			String status, String gender) {
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("lastName " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("firstName " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (dob == null || dob.isBlank()) {
			throw new IllegalArgumentException("dob " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (address == null) {
			throw new IllegalArgumentException("addressId " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (phone == null || phone.isBlank()) {
			throw new IllegalArgumentException("phone " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (status == null || status.isBlank()) {
			throw new IllegalArgumentException("status " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (gender == null || gender.isBlank()) {
			throw new IllegalArgumentException("gender " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		this.lastName = lastName;
		this.firstName = firstName;
		this.dob = dob;
		this.address = address;
		this.phone = phone;
		this.status = status;
		this.gender = gender;
	}

	
	/**
	 * Instantiates a new patient.
	 *
	 * @param id the id
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param dob the dob
	 * @param address the address
	 * @param phone the phone
	 * @param status the status
	 * @param gender the gender
	 */
	public Patient(String id, String lastName, String firstName, String dob, Address address, String phone,
			String status, String gender) {
		this(lastName, firstName, dob, address, phone, status, gender);
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("id " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}


		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.dob = dob;
		this.address = address;
		this.phone = phone;
		this.status = status;
		this.gender = gender;
	}

	/**
	 * Gets the patient id.
	 * 
	 * @return the patient id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the patient id.
	 * 
	 * @param id the new patient id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the last name of the patient.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name of the patient.
	 * 
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the first name of the patient.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name of the patient.
	 * 
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the date of birth of the patient.
	 * 
	 * @return the date of birth (dob)
	 */
	public String getDob() {
		return this.dob;
	}

	/**
	 * Sets the date of birth of the patient.
	 * 
	 * @param dob the new date of birth
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * Gets the address ID of the patient.
	 * 
	 * @return the address ID
	 */
	public Address getAddress() {
		return this.address;
	}

	
	/**
	 * Sets the address id.
	 *
	 * @param address the new address id
	 */
	public void setAddressId(Address address) {
		this.address = address;
	}

	/**
	 * Gets the phone number of the patient.
	 * 
	 * @return the phone number
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Sets the phone number of the patient.
	 * 
	 * @param phone the new phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the status of the patient.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Sets the status of the patient.
	 * 
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the gender of the patient.
	 * 
	 * @return the gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * Sets the gender of the patient.
	 * 
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
}
