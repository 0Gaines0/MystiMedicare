package application.model.credentials;

import java.time.LocalDate;

/**
 * The Class Doctor.
 * @author Jeffrey Gaines
 */
public class Doctor {

	/** The last name. */
	private String lastName;
	
	/** The first name. */
	private String firstName;
	
	/** The date of birth. */
	private LocalDate dateOfBirth;
	
	/** The address id. */
	private String addressId;
	
	/** The phone number. */
	private String phoneNumber;
	
	/** The gender. */
	private String gender;
	
	/**
	 * Instantiates a new doctor with default values.
	 */
	public Doctor() {
		this.lastName = "";
		this.firstName = "";
		this.dateOfBirth = null;
		this.addressId = "";
		this.phoneNumber = "";
		this.gender = "";
	}
	
	/**
	 * Instantiates a new doctor with given parameters.
	 *
	 * @param fName the first name
	 * @param lName the last name
	 * @param date the date of birth
	 * @param addressId the address id
	 * @param phone the phone number
	 * @param gender the gender
	 * @throws IllegalArgumentException if any parameter is invalid
	 */
	public Doctor(String fName, String lName, LocalDate date, String addressId, String phone, String gender) {
		this.setFirstName(fName);
		this.setLastName(lName);
		this.setDateOfBirth(date);
		this.setAddressId(addressId);
		this.setPhoneNumber(phone);
		this.setGender(gender);
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.trim().isEmpty()) {
			throw new IllegalArgumentException("First name cannot be empty");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.trim().isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be empty");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Date of birth must be a valid past date");
		}
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Sets the address id.
	 *
	 * @param addressId the new address id
	 */
	public void setAddressId(String addressId) {
		if (addressId == null || addressId.trim().isEmpty()) {
			throw new IllegalArgumentException("Address ID cannot be empty");
		}
		this.addressId = addressId;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
			throw new IllegalArgumentException("Phone number must be a 10-digit number");
		}
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
			throw new IllegalArgumentException("Gender must be 'male' or 'female'");
		}
		this.gender = gender;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * Gets the address id.
	 *
	 * @return the address id
	 */
	public String getAddressId() {
		return this.addressId;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return this.gender;
	}
}
