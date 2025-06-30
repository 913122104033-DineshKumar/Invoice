package invoice;

public class Customer {
    // Primary Details
    private static int cusNo = 1;
    private Utils.CustomerTypes customerType;
    private String firstName;
    private String lastName;
    private String salutation;
    private String companyName;
    private String email;
    private String phone;

    // Office Address
    private String country;
    private String state;
    private String city;
    private String street;
    private String pinCode;

    // Shipping Address
    private String shippingCountry;
    private String shippingState;
    private String shippingCity;
    private String shippingStreet;
    private String shippingPinCode;

    // Create a customer with Type and firstName.
    public Customer (Utils.CustomerTypes customerType, String firstName, String lastName, String salutation, String companyName, String email, String phone, String country, String state, String city, String street, String pinCode, String shippingCountry, String shippingState, String shippingCity, String shippingStreet, String shippingPinCode) {
        this.customerType = customerType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salutation = salutation;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.pinCode = pinCode;
        this.shippingCountry = shippingCountry;
        this.shippingState = shippingState;
        this.shippingCity = shippingCity;
        this.shippingStreet = shippingStreet;
        this.shippingPinCode = shippingPinCode;
        cusNo++;
    }

    // For other uses, call this setter and getter for simplified Constructor.
    public void setCustomerType (Utils.CustomerTypes customerType) {
        this.customerType = customerType;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setSalutation (String salutation) {
        this.salutation = salutation;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public void setState (String state) {
        this.state = state;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public void setStreet (String street) {
        this.street = street;
    }

    public void setPinCode (String pinCode) {
        this.pinCode = pinCode;
    }

    public void setShippingCountry (String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public void setShippingState (String shippingState) {
        this.shippingState = shippingState;
    }

    public void setShippingCity (String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public void setShippingStreet (String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }

    public void setShippingPinCode (String shippingPinCode) {
        this.shippingPinCode = shippingPinCode;
    }

    public static int getCusNo() {
        return cusNo;
    }

    public Utils.CustomerTypes getCustomerType() {
        return customerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public String getShippingState() {
        return shippingState;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public String getShippingStreet() {
        return shippingStreet;
    }

    public String getShippingPinCode() {
        return shippingPinCode;
    }
}
