package invoice;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Customer {

    // Constants
    private static final String NAME_REGEX = "[a-zA-Z\\s'-]{3,}";
    private static final String EMAIL_REGEX = "[a-zA-Z][a-zA-Z0-9._%+-]+@[a-z][a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    private static final String PHONE_REGEX = "\\d{10}";

    // Primary Details
    private static int totalCustomers = 0;
    private final int cusNo;
    private String customerType;
    private String name;
    private String companyName;
    private String email;
    private String phone;

    // Office Address
    private Address address;

    // Shipping Address
    private Address shippingAddress;

    public Customer (String customerType, String name) {
        this.customerType = customerType;
        this.name = name;
        totalCustomers++;
        this.cusNo = totalCustomers;
    }

    public static Customer create (Set<String> emails) {

        System.out.println("You can add Customer now");

        Scanner scanner = new Scanner(System.in);

        String name = "";
        name = Utils.getValidInput(name, NAME_REGEX, scanner, "Enter Valid Name (Eg. Dinesh Kumar K K)");

        String customerType;

        char customerTypeOption = 'A';
        customerTypeOption = Utils.getValidOption(customerTypeOption, 'I', 'B', scanner, "Individual -> I, Business -> B");
        customerType = Utils.customerTypes.get(customerTypeOption);

        Customer customer = new Customer(customerType, name);
        if (customerType.equals(Utils.customerTypes.get('B'))) {
            String companyName = "";
            companyName = Utils.getValidInput(companyName, NAME_REGEX, scanner, "Enter Company Name (Eg. Zoho Corp)");
            customer.setCompanyName(companyName);
        }

        String email = "";
        email = Utils.getValidInput(email, EMAIL_REGEX, scanner, "Enter Valid Email (Eg. abc@gmail.com)");

        while (emails.contains(email) || !email.matches(EMAIL_REGEX)) {
            System.out.println("Email already exists, try with any email");
            email = scanner.nextLine();
        }

        emails.add(email);
        customer.setEmail(email);

        String phone = "";
        phone = Utils.getValidInput(phone, PHONE_REGEX, scanner, "Enter the Valid Phone Number (Eg. 1234567890):");
        customer.setPhone(phone);

        Address officeAddress = Address.create();
        customer.setAddress(officeAddress);

        char addressOption = 'A';
        addressOption = Utils.getValidOption(addressOption, 'S', 'D',
                scanner, "Same Location -> S, Different Location -> D");
        if (addressOption == 'S') {
            customer.setShippingAddress(officeAddress);
        } else {
            System.out.println("Shipping Address...");
            Address shippingAddress = Address.create();
            customer.setShippingAddress(shippingAddress);
        }

        return customer;
    }

    public void update () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("Option 1 -> Updating the Customer's Primary Detail");

            // Office Address
            System.out.println("Option 2 -> Updating the Office Address");

            // Shipping Address
            System.out.println("Option 3 -> Updating the Shipping Address");

            // Exiting
            System.out.println("Option 4 -> For exit");

            System.out.println("-".repeat(30));

            // Option
            System.out.println("Enter the option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    updateCustomerDetails();
                    break;
                case 2:
                    this.address.update();
                    break;
                case 3:
                    this.shippingAddress.update();
                    break;
                case 4:
                    System.out.println(this.name + " is updated");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter a valid input (1 - 4)");
                    break;
            }
        }
    }

    private void updateCustomerDetails () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("Option 1 -> Updating Type");
            System.out.println("Option 2 -> Updating Name");
            System.out.println("Option 3 -> Updating Company Name (Applicable Businesses Only)");
            System.out.println("Option 4 -> Updating Email");
            System.out.println("Option 5 -> Updating Phone");
            System.out.println("Option 6 -> Exit");

            System.out.println("Enter the option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    String updatedCustomerType;

                    char customerTypeOption = 'A';
                    customerTypeOption = Utils.getValidOption(customerTypeOption, 'I', 'B',
                            scanner, "Individual -> I, Business -> B");
                    updatedCustomerType = Utils.customerTypes.get(customerTypeOption);

                    this.setCustomerType(updatedCustomerType);

                    System.out.println("Updated Customer Type");
                    break;
                case 2:
                    String updatedName = "";
                    updatedName = Utils.getValidInput(updatedName, NAME_REGEX, scanner, "Enter Valid Name (Eg. Dinesh Kumar K K)");

                    this.setName(updatedName);

                    System.out.println("Updated Name");
                    break;
                case 3:
                    if (this.customerType.equals(Utils.customerTypes.get('I'))) {
                        System.out.println("You are not owning a Business");
                        break;
                    }
                    String nCompanyName = "";
                    nCompanyName = Utils.getValidInput(nCompanyName, NAME_REGEX, scanner, "Enter a Valid Company Name (Eg. Dinesh Textiles):");

                    this.setCompanyName(nCompanyName);

                    System.out.println("Updated Company Name");
                    break;
                case 4:
                    System.out.println("Enter the Updated Email: ");

                    String nEmail = "";
                    nEmail = Utils.getValidInput(nEmail, EMAIL_REGEX, scanner, "Enter Valid Email (Eg. abc@gmail.com)");

                    this.setEmail(nEmail);

                    System.out.println("Updated Email");
                    break;
                case 5:
                    System.out.println("Enter the Phone: ");

                    String nPhone = "";
                    nPhone = Utils.getValidInput(nPhone, PHONE_REGEX, scanner, "Enter the Valid Phone Number (Eg. 1234567890):");

                    this.setEmail(nPhone);

                    System.out.println("Updated Email");
                    break;
                case 6:
                    System.out.println("Customer Primary Details is updated");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter a valid input (1 - 6)");
                    break;
            }
        }
    }

    public static Customer searchByName (String customerName, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(customerName)) {
                return customer;
            }
        }
        return null;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setAddress (Address address) {
        this.address = address;
    }

    public void setShippingAddress (Address address) {
        this.shippingAddress = address;
    }

    public int getCusNo() {
        return cusNo;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getName() {
        return name;
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

    public Address getAddress () { return address; }

    public Address getShippingAddress () { return shippingAddress; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Customer Details:\n");
        sb.append("================\n");
        sb.append("Customer Number: ").append(cusNo).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Customer Type: ").append(customerType).append("\n");

        if (companyName != null && !companyName.trim().isEmpty()) {
            sb.append("Company: ").append(companyName).append("\n");
        }

        sb.append("Email: ").append(email != null ? email : "Not provided").append("\n");
        sb.append("Phone: ").append(phone != null ? phone : "Not provided").append("\n");

        // Office Address
        sb.append("\nOffice Address:\n");
        if (address != null) {
            sb.append(address.toString()).append("\n");
        } else {
            sb.append("Not provided\n");
        }

        // Shipping Address
        sb.append("\nShipping Address:\n");
        if (shippingAddress != null) {
            sb.append(shippingAddress.toString()).append("\n");
        } else {
            sb.append("Not provided\n");
        }

        return sb.toString();
    }

}
