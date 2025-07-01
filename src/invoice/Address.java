package invoice;

import java.util.Scanner;

public class Address {

    // Constants
    private static final String NAME_REGEX = "[a-zA-Z\\s]+";
    private static final String STREET_REGEX = "[a-zA-Z0-9\\s,.'#-]+";
    private static final String PIN_CODE_REGEX = "\\d{6}";

    // Primary Details
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

    public static Address create () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Country: ");

        String country = "";
        country = Utils.getValidInput(country, NAME_REGEX, scanner, "Enter the Valid Country Name (Eg. India):");

        System.out.println("Enter the State: ");

        String state = "";
        state = Utils.getValidInput(state, NAME_REGEX, scanner, "Enter the Valid State Name (Eg. Tamil Nadu):");

        System.out.println("Enter the City: ");

        String city = "";
        city = Utils.getValidInput(city, NAME_REGEX, scanner, "Enter the Valid City Name (Eg. Madurai):");

        System.out.println("Enter the Street: ");

        String street = "";
        street = Utils.getValidInput(street, STREET_REGEX, scanner, "Enter the Valid Street Name (Eg. 14/22 dummy 1st street):");

        System.out.println("Enter the Pin Code: ");

        String pinCode = "";
        pinCode = Utils.getValidInput(pinCode, PIN_CODE_REGEX, scanner, "Enter the Valid Pin Code (Eg. 600001):");

        return new Address(country, state, city, street, pinCode);
    }

    public void update () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("Option 1 -> Updating Country");
            System.out.println("Option 2 -> Updating State");
            System.out.println("Option 3 -> Updating City");
            System.out.println("Option 4 -> Updating Street");
            System.out.println("Option 5 -> Updating Pin Code");
            System.out.println("Option 6 -> Exiting");

            System.out.println("Enter the Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Enter the Updated Country Name: ");

                    String nCountry = "";
                    nCountry = Utils.getValidInput(nCountry, NAME_REGEX, scanner, "Enter the Valid Country Name (Eg. India):");

                    this.setCountry(nCountry);

                    System.out.println("Updated Country");
                    break;
                case 2:
                    System.out.println("Enter the Updated State Name: ");

                    String nState = "";
                    nState = Utils.getValidInput(nState, NAME_REGEX, scanner, "Enter the Valid State Name (Eg. Tamil Nadu):");

                    this.setState(nState);

                    System.out.println("Updated State");
                    break;
                case 3:
                    System.out.println("Enter the Updated City Name: ");

                    String nCity = "";
                    nCity = Utils.getValidInput(nCity, NAME_REGEX, scanner, "Enter the Valid City Name (Eg. Madurai):");

                    this.setCity(nCity);

                    System.out.println("Updated City");
                    break;
                case 4:
                    System.out.println("Enter the Updated Street Name: ");

                    String nStreet = "";
                    nStreet = Utils.getValidInput(nStreet, STREET_REGEX, scanner, "Enter the Valid Street Name (Eg. 14/22 dummy 1st street):");

                    this.setStreet(nStreet);

                    System.out.println("Updated Street");
                    break;
                case 5:
                    System.out.println("Enter the Updated Pin Code: ");

                    String nPinCode = "";
                    nPinCode = Utils.getValidInput(nPinCode, PIN_CODE_REGEX, scanner, "Enter the Valid Pin Code (Eg. 600001):");

                    this.setPinCode(nPinCode);

                    System.out.println("Updated Pin Code");
                    break;
                case 6:
                    System.out.println("Successfully updated, exiting...");
                    isUpdating = false;
                    break;
                default:
                    break;
            }
        }
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
