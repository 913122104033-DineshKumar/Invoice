package invoice;

import java.util.Scanner;
import java.util.Set;

public class CustomerUtil {

    private static final String NAME_REGEX = "[a-zA-Z\\s'-]{3,}";
    private static final String EMAIL_REGEX = "[a-zA-Z][a-zA-Z0-9._%+-]+@[a-z][a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    private static final String PHONE_REGEX = "\\d{10}";
    private final Scanner scanner;

    public CustomerUtil (Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNameInput() {
        String name = "";

        name = Utils.getValidInput(name, NAME_REGEX, scanner, "Enter Valid Name (Eg. Dinesh Kumar K K)");

        return name;
    }

    public String getCustomerTypeInput() {
        char customerTypeOption = 'A';

        customerTypeOption = Utils.getValidOption(customerTypeOption, 'I', 'B', scanner, "Individual -> I, Business -> B");

        return Utils.customerTypes.get(customerTypeOption);
    }

    public String getCompanyNameInput() {
        String companyName = "";

        companyName = Utils.getValidInput(companyName, NAME_REGEX, scanner, "Enter Company Name (Eg. Zoho Corp)");

        return companyName;
    }

    public String getEmailInput(Set<String> emails) {
        String email = "";

        email = Utils.getValidInput(email, EMAIL_REGEX, scanner, "Enter Valid Email (Eg. abc@gmail.com)");

        while (emails.contains(email) || !email.matches(EMAIL_REGEX)) {
            System.out.println("Email already exists, try with any email");
            email = scanner.nextLine();
        }

        return email;
    }

    public String getPhoneInput() {
        String phone = "";

        phone = Utils.getValidInput(phone, PHONE_REGEX, scanner, "Enter the Valid Phone Number (Eg. 1234567890):");

        return phone;
    }

    public Address getShippingAddressInput(Address address) {
        char addressOption = 'A';
        addressOption = Utils.getValidOption(addressOption, 'S', 'D',
                scanner, "Same Location -> S, Different Location -> D");
        if (addressOption == 'S') {
            return address;
        }
        System.out.println("Shipping Address...");
        return Address.create();
    }

}
