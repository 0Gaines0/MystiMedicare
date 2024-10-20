package application.model.credentials;

/**
 * the nurse class
 * 
 * @author danielrivera
 */
public class Nurse extends User {

    private String lastName;
    private String firstName;
    private String dob;
    private String addressId;
    private String phone;
    private String gender;

    private static final String PARAM_MUST_NOT_BE_EMPTY_OR_NULL = "must not be empty or null";

    /**
     * Instantiates a new nurse.
     */
    public Nurse() {
        super();
        this.lastName = "";
        this.firstName = "";
        this.dob = "";
        this.addressId = "";
        this.phone = "";
        this.gender = "";
    }

    /**
     * Instantiates a new nurse.
     *
     * @param userId the user id
     * @param username the username
     * @param password the password
     * @param role the role
     * @param lastName the last name
     * @param firstName the first name
     * @param dob the date of birth
     * @param addressId the address ID
     * @param phone the phone number
     * @param gender the gender
     */
    public Nurse(String userId, String username, String password, UserRole role, 
                 String lastName, String firstName, String dob, String addressId, 
                 String phone, String gender) {
        super(userId, username, password, role);
        
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
        }
        if (dob == null || dob.isBlank()) {
            throw new IllegalArgumentException("dob " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
        }
        if (addressId == null || addressId.isBlank()) {
            throw new IllegalArgumentException("addressId " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("phone " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
        }
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("gender " + PARAM_MUST_NOT_BE_EMPTY_OR_NULL);
        }
        
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.addressId = addressId;
        this.phone = phone;
        this.gender = gender;
    }

    /**
     * gets last name
     * @return last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * sets last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets first name
     * @return first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * sets first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets DOB
     * @return Dob
     */
    public String getDob() {
        return this.dob;
    }

    /**
     * set dob
     * @param dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * gets addressid
     * @return address id
     */
    public String getAddressId() {
        return this.addressId;
    }

    /**
     * sets addressid 
     * @param addressId
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * gets phone
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * sets phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * gets gender
     * @return gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * sets gender
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}

   