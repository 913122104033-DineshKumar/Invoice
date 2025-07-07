package invoice.src;

import invoice.GlobalConstants;
import invoice.utils.Utils;

import java.util.Scanner;

public class Address {
    // Primary Details
    private String country;
    private String state;
    private String city;
    private String street;
    private String pinCode;
    private static final String STREET_REGEX = "^([a-zA-Z][a-zA-Z0-9-\\s,]{6,})|^([0-9]+[a-zA-Z0-9,\\/\\s.]{6,})";
    private static final String PIN_CODE_REGEX = "\\d{6}";

    public Address (String country, String state, String city, String street, String pinCode) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.pinCode = pinCode;
    }

    public static Address create (Scanner scanner) {
        String country = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "India", "Country", "Enter the Country:", false);

        String state = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Tamil Nadu", "State", "Enter the State:", false);

        String city = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner,"Madurai", "City", "Enter the City:", false);

        String street = Utils.getValidStringInput(STREET_REGEX, scanner, "14/22, dummy 1st street", "Street", "Enter the Street:", false);

        String pinCode = Utils.getValidStringInput(PIN_CODE_REGEX, scanner, "600001", "Pin Code", "Enter the Pin Code:", false);

        return new Address(country, state, city, street, pinCode);
    }

    public void update (Scanner scanner) {
        int option = -1;
        do
        {
            System.out.println("\nOption 1 -> Updating Country");
            System.out.println("Option 2 -> Updating State");
            System.out.println("Option 3 -> Updating City");
            System.out.println("Option 4 -> Updating Street");
            System.out.println("Option 5 -> Updating Pin Code");
            System.out.println("Option 6 -> Exiting");

            System.out.println("\nEnter the Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    String previousCountry = this.country;

                    String nCountry = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "India", "Country", "Enter the Country:", true);

                    this.setCountry(nCountry);

                    System.out.println("\nCustomer's Country updated from " + previousCountry + " to " + nCountry);
                    break;
                }

                case 2:
                {
                    String previousState = this.state;

                    String nState = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Tamil Nadu", "State", "Enter the State:",true);

                    this.setState(nState);
                    System.out.println("Customer's State updated from " + previousState + " to " + nState);
                    break;
                }

                case 3:
                {
                    String previousCity = this.city;

                    String nCity = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Madurai", "City", "Enter the City:", true);

                    this.setCity(nCity);

                    System.out.println("\nCustomer's City updated from " + previousCity + " to " + nCity);
                    break;
                }

                case 4:
                {
                    String previousStreet = this.street;

                    String nStreet = Utils.getValidStringInput(STREET_REGEX, scanner, "14/22, dummy 1st street", "Street", "Enter the Street:", true);

                    this.setStreet(nStreet);

                    System.out.println("\nCustomer's Street updated from " + previousStreet + " to " + nStreet);
                    break;
                }

                case 5:
                {
                    String previousPinCode = this.pinCode;

                    String nPinCode = Utils.getValidStringInput(PIN_CODE_REGEX, scanner, "600001", "Pin Code", "Enter the Pin Code", true);

                    this.setPinCode(nPinCode);

                    System.out.println("\nCustomer's Pin Code updated from " + previousPinCode + " to " + nPinCode);
                    break;
                }

                case 6:
                {
                    System.out.println("\nExiting the Address Module...");
                    break;
                }

                default:
                {
                    System.out.println("Enter a Valid Input (1 - 6)");
                    break;
                }
            }
        } while (option != 6);
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
