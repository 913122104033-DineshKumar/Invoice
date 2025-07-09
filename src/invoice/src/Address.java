package invoice.src;

import invoice.utils.InputUtils;

public class Address
{
    // Primary Details
    private String country;
    private String state;
    private String city;
    private String street;
    private String pinCode;
    private static final String NAME_REGEX = "[a-zA-Z\\s'-]+";
    private static final String STREET_REGEX = "^([a-zA-Z][a-zA-Z0-9-\\s,]{6,})|^([0-9]+[a-zA-Z0-9,\\/\\s.]{6,})";
    private static final String PIN_CODE_REGEX = "\\d{6}";

    public Address () {  }

    public Address (String country, String state, String city, String street, String pinCode)
    {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.pinCode = pinCode;
    }

    public static Address create () {
        String country = InputUtils.getValidStringInput(NAME_REGEX, "India", "Country", "Enter the Country (Optional):", false);

        String state = InputUtils.getValidStringInput(NAME_REGEX, "Tamil Nadu", "State", "Enter the State (Optional):", false);

        String city = InputUtils.getValidStringInput(NAME_REGEX,"Madurai", "City", "Enter the City (Optional):", false);

        String street = InputUtils.getValidStringInput(STREET_REGEX, "14/22, dummy 1st street", "Street", "Enter the Street (Optional):", false);

        String pinCode = InputUtils.getValidStringInput(PIN_CODE_REGEX, "600001", "Pin Code", "Enter the Pin Code (Optional):", false);

        return new Address(country, state, city, street, pinCode);
    }

    public void update (Address officeAddress) {
        int option = -1;

        addressUpdationMenu:
        {
            while (true) {

                if (officeAddress != null)
                {
                    char conformationOptionOption = InputUtils.getToggleInput(  'y',"Shipping Address Option", "Shipping Address\nDo you want Shipping Address, Same as Office Address (Or) Different from Office (y -> yes, any other key -> no)");

                    if (conformationOptionOption == 'y')
                    {
                        this.setCountry(officeAddress.getCountry());
                        this.setState(officeAddress.getState());
                        this.setCity(officeAddress.getCity());
                        this.setStreet(officeAddress.getStreet());
                        this.setPinCode(officeAddress.getPinCode());

                        System.out.println("\nShipping Address is copied from Office Address");

                        break addressUpdationMenu;
                    }
                }

                System.out.println("\nOption 1 -> Updating Country");
                System.out.println("Option 2 -> Updating State");
                System.out.println("Option 3 -> Updating City");
                System.out.println("Option 4 -> Updating Street");
                System.out.println("Option 5 -> Updating Pin Code");
                System.out.println("Option 6 -> Exiting");

                System.out.println("\nEnter the Option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option) {
                    case 1: {
                        String previousCountry = InputUtils.handleNullStrings(this.country);

                        String nCountry = InputUtils.getValidStringInput(NAME_REGEX, "India", "Country", "Enter the Country:", true);

                        if (previousCountry.equalsIgnoreCase(nCountry))
                        {
                            System.out.println("\nYou entered the same Country Name again, so no updation is done");
                            break;
                        }

                        this.setCountry(nCountry);

                        System.out.println("\nCustomer's Country updated from " + previousCountry + " to " + nCountry);
                        break;
                    }

                    case 2: {
                        String previousState = InputUtils.handleNullStrings(this.state);

                        String nState = InputUtils.getValidStringInput(NAME_REGEX, "Tamil Nadu", "State", "Enter the State:", true);

                        if (previousState.equalsIgnoreCase(nState))
                        {
                            System.out.println("\nYou entered the same State Name again, so no updation is done");
                            break;
                        }

                        this.setState(nState);
                        System.out.println("Customer's State updated from " + previousState + " to " + nState);
                        break;
                    }

                    case 3: {
                        String previousCity = InputUtils.handleNullStrings(this.city);

                        String nCity = InputUtils.getValidStringInput(NAME_REGEX, "Madurai", "City", "Enter the City:", true);

                        if (previousCity.equalsIgnoreCase(nCity))
                        {
                            System.out.println("\nYou entered the same City Name again, so no updation is done");
                            break;
                        }

                        this.setCity(nCity);

                        System.out.println("\nCustomer's City updated from " + previousCity + " to " + nCity);
                        break;
                    }

                    case 4: {
                        String previousStreet = InputUtils.handleNullStrings(this.street);

                        String nStreet = InputUtils.getValidStringInput(STREET_REGEX, "14/22, dummy 1st street", "Street", "Enter the Street:", true);

                        if (previousStreet.equalsIgnoreCase(nStreet))
                        {
                            System.out.println("\nYou entered the same Street Name again, so no updation is done");
                            break;
                        }

                        this.setStreet(nStreet);

                        System.out.println("\nCustomer's Street updated from " + previousStreet + " to " + nStreet);
                        break;
                    }

                    case 5: {
                        String previousPinCode = InputUtils.handleNullStrings(this.pinCode);

                        String nPinCode = InputUtils.getValidStringInput(PIN_CODE_REGEX, "600001", "Pin Code", "Enter the Pin Code", true);

                        if (previousPinCode.equalsIgnoreCase(nPinCode))
                        {
                            System.out.println("\nYou entered the same Pin Code Name again, so no updation is done");
                            break;
                        }

                        this.setPinCode(nPinCode);

                        System.out.println("\nCustomer's Pin Code updated from " + previousPinCode + " to " + nPinCode);
                        break;
                    }

                    case 6: {
                        System.out.println("\nExiting the Address Module...");
                        break addressUpdationMenu;
                    }

                    default: {
                        System.out.println("Enter a Valid Input (1 - 6)");
                        break;
                    }
                }
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
