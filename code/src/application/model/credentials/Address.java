package application.model.credentials;

/**
 * The Address class represents an address in the system.
 * 
 * @author Jeffrey Gaines
 */
public class Address {

	/** The street. */
	private String street;
	
	/** The city. */
	private String city;
	
	/** The state. */
	private String state;
	
	/** The zip code. */
	private String zipCode;
	
	/** The address one. */
	private String addressOne;
	
	/** The address two. */
	private String addressTwo;
	
	/** The id. */
	private String id;

	/** The Constant PARAM_MUST_NOT_BE_EMPTY_OR_NULL. */
	private static final String PARAM_MUST_NOT_BE_EMPTY_OR_NULL = "must not be empty or null";

	/**
	 * Instantiates a new Address with default values.
	 */
	public Address() {
		this.id = "";
		this.street = "";
		this.city = "";
		this.state = "";
		this.zipCode = "";
		this.addressOne = "";
		this.addressTwo = "";
	}

	
	/**
	 * Instantiates a new address.
	 *
	 * @param id the id
	 * @param street the street
	 * @param city the city
	 * @param state the state
	 * @param zipCode the zip code
	 */
	public Address(String id, String street, String city, String state, String zipCode) {
		if (street == null || street.isBlank()) {
			throw new IllegalArgumentException("street " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (city == null || city.isBlank()) {
			throw new IllegalArgumentException("city " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (state == null || state.isBlank()) {
			throw new IllegalArgumentException("state " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		if (zipCode == null || zipCode.isBlank()) {
			throw new IllegalArgumentException("zipCode " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
		}
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return this.zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the address one.
	 *
	 * @return the address one
	 */
	public String getAddressOne() {
		return this.addressOne;
	}

	/**
	 * Sets the address one.
	 *
	 * @param addressOne the new address one
	 */
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	/**
	 * Gets the address two.
	 *
	 * @return the address two
	 */
	public String getAddressTwo() {
		return this.addressTwo;
	}

	/**
	 * Sets the address two.
	 *
	 * @param addressTwo the new address two
	 */
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
