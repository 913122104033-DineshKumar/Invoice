package invoice.handlers;

import invoice.src.Address;
import invoice.utils.InputUtils;

public class AddressHandler 
{
    private final Address previousAddress;
    private final String customerId;
    private final String NAME_REGEX = "[a-zA-Z\\s'-]+";
    
    public AddressHandler (String customerId, Address previousAddress)
    {
        this.previousAddress = previousAddress;
        this.customerId = customerId;
    }

    private char getShippingAddressInput()
    {
        return InputUtils.getToggleInput( 'y', "Shipping Address Option", "Shipping Address\nDo you want Shipping Address, Same as Office Address (Or) Different from Office (y -> Same, any other key -> Different)");
    }
    
    public Address createOrEdit (boolean isShippingAddress)
    {
        Address address;
        String previousCountry = null;
        String previousState = null;
        String previousCity = null;
        String previousStreet = null;
        String previousPinCode = null;
        
        if (previousAddress != null)
        {
            previousCountry = previousAddress.getCountry();
            previousState = previousAddress.getState();
            previousCity = previousAddress.getCity();
            previousStreet = previousAddress.getStreet();
            previousPinCode = previousAddress.getPinCode();

            address = previousAddress;

            if (isShippingAddress) {
                char confirmationOption = getShippingAddressInput();

                if (confirmationOption == 'y') {
                    return address;
                }
            }
            
        } else {
            address = new Address();
        }
        
        address.setCountry(getCountry(previousCountry));
        address.setStreet(getState(previousState));
        address.setCity(getCity(previousCity));
        address.setStreet(getStreet(previousStreet));
        address.setPinCode(getPinCode(previousPinCode));

        return address;
    }

    private String getCountry (String previousCountry)
    {
        String country = InputUtils.getValidStringInput(NAME_REGEX, "India", "Country", "Enter the Country:", false).trim();

        if (previousCountry != null && !previousCountry.isEmpty() && previousCountry.equalsIgnoreCase(country))
        {
            System.out.println("\nYou entered the same Country Name again, so no updation is done");
        } else if (customerId != null)
        {
            System.out.println("\nCustomer's Country updated from " + previousCountry + " to " + country);
        }

        return country;
    }
    
    private String getState (String previousState)
    {
        String state = InputUtils.getValidStringInput(NAME_REGEX, "Tamil Nadu", "State", "Enter the State:", false).trim();

        if (previousState != null && !previousState.isEmpty() && previousState.equalsIgnoreCase(state))
        {
            System.out.println("\nYou entered the same State Name again, so no updation is done");
        } else if (customerId != null)
        {
            System.out.println("\nCustomer's State updated from " + previousState + " to " + state);
        }
        
        return state;
    }

    private String getCity (String previousCity)
    {
        String city = InputUtils.getValidStringInput(NAME_REGEX, "Madurai", "City", "Enter the City:", false).trim();

        if (previousCity != null && !previousCity.isEmpty() && previousCity.equalsIgnoreCase(city))
        {
            System.out.println("\nYou entered the same City Name again, so no updation is done");
        } else if (customerId != null)
        {
            System.out.println("\nCustomer's City updated from " + previousCity + " to " + city);
        }

        return city;
    }

    private String getStreet (String previousStreet)
    {
        final String STREET_REGEX = "^([a-zA-Z][a-zA-Z0-9-\\s,]{6,})|^([0-9]+[a-zA-Z0-9,\\/\\s.]{6,})";

        String street = InputUtils.getValidStringInput(STREET_REGEX, "14/22, dummy 1st street", "Street", "Enter the Street:", false).trim();

        if (previousStreet != null && !previousStreet.isEmpty() && previousStreet.equalsIgnoreCase(street))
        {
            System.out.println("\nYou entered the same Street Name again, so no updation is done");
        } else if (customerId != null)
        {
            System.out.println("\nCustomer's Street updated from " + previousStreet + " to " + street);
        }

        return street;
    }

    private String getPinCode (String previousPinCode)
    {
        final String PIN_CODE_REGEX = "\\d{6}";

        String pinCode = InputUtils.getValidStringInput(PIN_CODE_REGEX, "123456", "Pin Code", "Enter the Pin Code:", false).trim();

        if (previousPinCode != null && !previousPinCode.isEmpty() && previousPinCode.equalsIgnoreCase(pinCode))
        {
            System.out.println("\nYou entered the same Pin Code Name again, so no updation is done");
        } else if (customerId != null)
        {
            System.out.println("\nCustomer's Pin Code updated from " + previousPinCode + " to " + pinCode);
        }

        return pinCode;
    }
    
}
