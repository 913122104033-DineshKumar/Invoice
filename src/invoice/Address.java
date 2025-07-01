package invoice;

public class Address {
    private String country;
    private String state;
    private String city;
    private String street;
    private String pinCode;

    public Address (String country, String state, String city, String street, String pinCode) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.pinCode = pinCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s - %s",
                street, city, state, country, pinCode);
    }
}
