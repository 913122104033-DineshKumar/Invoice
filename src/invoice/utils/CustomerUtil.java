package invoice.utils;

import invoice.src.Address;

import java.util.Scanner;
import java.util.Set;

public class CustomerUtil {

    private static final String NAME_REGEX = "[a-zA-Z\\s'-]{3,}";
    private static final String EMAIL_REGEX = "[a-zA-Z][a-zA-Z0-9._%+-]+@[a-z][a-zA-Z0-9-]+.[a-zA-Z]{2,}";
    private static final String PHONE_REGEX = "\\d{10}";
    private final Scanner scanner;

    public CustomerUtil (Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNameInput() {
        System.out.println("\nEnter the Name (Eg. Dinesh Kumar K K): ");

        String name = scanner.nextLine();

        name = Utils.getValidInput(name, NAME_REGEX, scanner, "Dinesh Kumar K K", "Customer Name Input");

        return name;
    }

    public String getCustomerTypeInput() {
        System.out.println("\nEnter the Customer Type (I -> Individual, B -> Business): ");

        char customerTypeOption = 'A';

        String[][] availableOptions = { {"B", "BUSINESS"}, {"I", "INDIVIDUAL"} };

        customerTypeOption = Utils.handleOptionStringOutOfBoundError(scanner, customerTypeOption, "Customer Type Option");

        customerTypeOption = Character.toUpperCase(customerTypeOption);

        customerTypeOption = Utils.getValidOption(customerTypeOption, 'I', 'B', scanner, "Customer Type Input", availableOptions);

        return customerTypeOption == 'B' ? "BUSINESS" : "INDIVIDUAL";
    }

    public String getCompanyNameInput() {
        System.out.println("\nEnter the Company Name (Eg. Zoho Corp): ");

        String companyName = scanner.nextLine();

        companyName = Utils.getValidInput(companyName, NAME_REGEX, scanner, "Zoho Corp", "Company Name");

        return companyName;
    }

    public String getEmailInput(Set<String> emails) {
        System.out.println("\nEnter the Email (Eg. abc@gmail.com): ");

        String email = scanner.nextLine();

        email = Utils.getValidInput(email, EMAIL_REGEX, scanner, "abc@gmail.com", "Customer Email");

        while (emails.contains(email) || !email.matches(EMAIL_REGEX)) {
            System.out.println("Email already exists, try with any email");
            email = scanner.nextLine();
        }

        return email;
    }

    public String getPhoneInput() {
        System.out.println("\nEnter the Phone Number (Eg. 1234567890): ");

        String phone = scanner.nextLine();

        phone = Utils.getValidInput(phone, PHONE_REGEX, scanner, "1234567890", "Customer Phone Number");

        return phone;
    }

    public Address getShippingAddressInput(Address address) {
        System.out.println("\nShipping Address\nSame as Office Address -> S\nDifferent from Office Address -> D ");

        String[][] availableOptions = { {"S", "SAME"}, {"D", "DIFFERENT"} };

        char addressOption = 'A';
        addressOption = Utils.handleOptionStringOutOfBoundError(scanner, addressOption, "Shipping Address Option");

        addressOption = Character.toUpperCase(addressOption);

        addressOption = Utils.getValidOption(addressOption, 'S', 'D',
                scanner, "Shipping Address Option", availableOptions);
        if (addressOption == 'S') {
            return address;
        }
        System.out.println("Shipping Address...");
        return Address.create();
    }

    public void printCustomerHeaders () {
        Utils.printLines(60);
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s |\n",
                "Item Number",
                "Item Type",
                "Item Unit",
                "Item Name",
                "Intra State Tax Rate",
                "Inter State Tax Rate",
                "Item Price",
                "Item Description");
        Utils.printLines(60);
    }

}
