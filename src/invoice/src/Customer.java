package invoice.src;

import invoice.utils.CustomerUtil;
import invoice.utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

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

    private static CustomerUtil customerUtil;

    public Customer (String customerType, String name) {
        this.customerType = customerType;
        this.name = name;
        totalCustomers++;
        this.cusNo = totalCustomers;
    }

    public static Customer create (Set<String> emails) {
        System.out.println("\nYou can add Customer now");

        Scanner scanner = new Scanner(System.in);

        if (customerUtil == null) {
            customerUtil = new CustomerUtil(scanner);
        }

        String name = customerUtil.getNameInput();

        String customerType = customerUtil.getCustomerTypeInput();

        Customer customer = new Customer(customerType, name);
        if (customerType.equals("BUSINESS")) {
            String companyName = customerUtil.getCompanyNameInput();
            customer.setCompanyName(companyName);
        }

        String email = customerUtil.getEmailInput(emails);
        emails.add(email);
        customer.setEmail(email);

        String phone = customerUtil.getPhoneInput();
        customer.setPhone(phone);

        Address officeAddress = Address.create();
        customer.setAddress(officeAddress);

        Address shippingAddress = customerUtil.getShippingAddressInput(officeAddress);
        customer.setShippingAddress(shippingAddress);

        return customer;
    }

    public void update (Set<String> emails) {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("\nOption 1 -> Updating the Customer's Primary Detail");

            // Office Address
            System.out.println("Option 2 -> Updating the Office Address");

            // Shipping Address
            System.out.println("Option 3 -> Updating the Shipping Address");

            // Exiting
            System.out.println("Option 4 -> For exit");

            System.out.println("-".repeat(30));

            // Option
            System.out.println("\nEnter the option: ");
            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    updateCustomerDetails(emails);
                    break;
                case 2:
                    this.address.update();
                    break;
                case 3:
                    this.shippingAddress.update();
                    break;
                case 4:
                    System.out.println("\n" + this.name + " is updated");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("\nEnter a valid input (1 - 4)");
                    break;
            }
        }
    }

    private void updateCustomerDetails (Set<String> emails) {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("\nOption 1 -> Updating Type");
            System.out.println("Option 2 -> Updating Name");
            System.out.println("Option 3 -> Updating Company Name (Applicable Businesses Only)");
            System.out.println("Option 4 -> Updating Email");
            System.out.println("Option 5 -> Updating Phone");
            System.out.println("Option 6 -> Exit");

            System.out.println("\nEnter the option: ");
            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    String previousCustomerType = this.customerType;

                    String updatedCustomerType = customerUtil.getCustomerTypeInput();

                    this.setCustomerType(updatedCustomerType);

                    System.out.println("\nCustomer's Type updated from " + previousCustomerType + " to " + updatedCustomerType );
                    break;
                case 2:
                    String previousName = this.name;

                    String updatedName = customerUtil.getNameInput();

                    this.setName(updatedName);

                    System.out.println("\nCustomer's Name updated from " + previousName + " to " + updatedName );
                    break;
                case 3:
                    String previousCompanyName = this.companyName;

                    if (this.customerType.equals("INDIVIDUAL")) {
                        System.out.println("\nYou are not owning a Business");
                        break;
                    }
                    String nCompanyName = customerUtil.getCompanyNameInput();

                    this.setCompanyName(nCompanyName);

                    System.out.println("\nCustomer's Company name updated from " + previousCompanyName + " to " + nCompanyName );
                    break;
                case 4:
                    String previousEmail = this.email;

                    String nEmail = customerUtil.getEmailInput(emails);
                    emails.remove(previousEmail);
                    emails.add(nEmail);

                    this.setEmail(nEmail);

                    System.out.println("\nCustomer's Email updated from " + previousEmail + " to " + nEmail );
                    break;
                case 5:
                    String previousPhone = this.phone;

                    String nPhone = customerUtil.getPhoneInput();

                    this.setEmail(nPhone);

                    System.out.println("\nCustomer's Phone Number updated from " + previousPhone + " to " + nPhone);
                    break;
                case 6:
                    System.out.println("\nCustomer's Primary Details is updated");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("\nEnter a valid input (1 - 6)");
                    break;
            }
        }
    }

    public static Customer searchByName (String customerName, TreeMap<Integer, Customer> customers) {
        for (Integer cusNo : customers.keySet()) {
            Customer customer = customers.get(cusNo);
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
