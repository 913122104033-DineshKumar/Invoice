package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Address;
import invoice.src.Customer;

import java.time.LocalDate;
import java.util.*;

public class CustomerUtil
{
    private final Scanner scanner;
    private final Set<Character> CUSTOMER_TYPES;
    private final String EMAIL_REGEX;
    private final String PHONE_REGEX;

    public CustomerUtil(Scanner scanner)
    {
        this.scanner = scanner;
        this.CUSTOMER_TYPES = new HashSet<>();
        this.CUSTOMER_TYPES.addAll(Arrays.asList('I', 'B', 'i', 'b'));
        this.EMAIL_REGEX = "[a-zA-Z][a-zA-Z0-9._%+-]+@[a-z][a-zA-Z0-9-]+.[a-zA-Z]{2,}";
        this.PHONE_REGEX = "\\d{10}";
    }

    public String getNameInput()
    {
        return Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Dinesh Kumar K K", "Customer Name Input", "Enter the Name (Eg. Dinesh Kumar K K):", true);
    }

    public char getCustomerTypeInput()
    {
        return Utils.getValidOption(CUSTOMER_TYPES, scanner, "Customer Type Input", "Enter the Customer Type (I -> Individual, B -> Business):");
    }

    public String getCompanyNameInput()
    {
        return Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Zoho Corp", "Company Name", "Enter the Company Name (Eg. Zoho Corp):", true);
    }

    public String getEmailInput(Set<String> emails)
    {
        String email = Utils.getValidStringInput(EMAIL_REGEX, scanner, "abc@gmail.com", "Customer Email", "Enter the Email (Eg. abc@gmail.com):", true);

        while (emails.contains(email) || !email.matches(EMAIL_REGEX)) {
            System.out.println("Email already exists, try with any email");
            email = scanner.nextLine().trim();
        }

        return email;
    }

    public String getPhoneInput()
    {
        return Utils.getValidStringInput(PHONE_REGEX, scanner, "1234567890", "Customer Phone Number", "Enter the Phone Number (Eg. 1234567890):", true);
    }

    public Address getShippingAddressInput(Address address)
    {

        char yesOrNoOption = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Shipping Address Option", "Shipping Address\nDo you want Shipping Address, Same as Office Address (Or) Different from Office\nSame -> Y\nDifferent -> N");

        if (yesOrNoOption == 'Y' || yesOrNoOption == 'y') {
            return address;
        }
        System.out.println("Shipping Address...");
        return Address.create(scanner);
    }

    public void sortingModule (List<Customer> customers)
    {
        SortingUtil sortingUtil = new SortingUtil();

        System.out.println("\nEnter the choice based in which you want to sort the Items: ");

        int sortBy = -1;

        do
        {
            System.out.println("\nOption 1 -> Sort by Customer Number");
            System.out.println("\nOption 2 -> Sort by Customer Name");
            System.out.println("\nOption 3 -> Sort by Date");

            sortBy = Utils.handleIntegerInputMisMatches(sortBy, -1, scanner);

        } while (sortBy < 1 || sortBy > 3);

        int sortingOrder = -1;

        do
        {
            System.out.println("\nOption 1 -> Ascending Order");
            System.out.println("\nOption 2 -> Descending Order");

            sortingOrder = Utils.handleIntegerInputMisMatches(sortingOrder, -1, scanner);

        } while (sortingOrder < 1 || sortingOrder > 2);

        if (sortBy == 1)
        {
            List<Integer> helper = new ArrayList<>();

            for (Customer customer : customers)
            {
                helper.add(customer.getCusNo());
            }

            sortingUtil.mergeSort(0, customers.size() - 1, customers, helper);
        }
        else if (sortBy == 2)
        {
            List<String> helper = new ArrayList<>();

            for (Customer customer : customers)
            {
                helper.add(customer.getName());
            }

            sortingUtil.mergeSort(0, customers.size() - 1, customers, helper);
        } else
        {
            List<LocalDate> helper = new ArrayList<>();

            for (Customer customer : customers)
            {
                helper.add(customer.getCreatedAt());
            }

            sortingUtil.mergeSort(0, customers.size() - 1, customers, helper);
        }

        if (sortingOrder == 2)
        {
            Utils.reverse(customers);
        }

        Utils.showCustomers(customers);

    }

    public static void reArrangeCustomerList (List<Customer> customers) {

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);

            if (i + 1 != customer.getCusNo()) {
                customer.setCusNo(i + 1);
            }

        }
    }

}
