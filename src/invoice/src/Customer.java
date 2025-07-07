package invoice.src;

import invoice.utils.CustomerUtil;
import invoice.utils.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Customer {

    // Primary Details
    private static int totalCustomers = 0;
    private int cusNo;
    private char customerType;
    private final LocalDate createdAt;
    private String name;
    private String companyName;
    private String email;
    private String phone;

    // Office Address
    private Address address;

    // Shipping Address
    private Address shippingAddress;

    private static CustomerUtil customerUtil;

    public Customer (char customerType, LocalDate createdAt, String name, String companyName, String email, String phone, Address address, Address shippingAddress) {
        this.customerType = customerType;
        this.createdAt = createdAt;
        this.name = name;
        totalCustomers++;
        this.cusNo = totalCustomers;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.shippingAddress = shippingAddress;
    }

    public static Customer create (Set<String> emails, Scanner scanner) {
        System.out.println("\nYou can add Customer now");

        if (customerUtil == null)
        {
            customerUtil = new CustomerUtil(scanner);
        }

        String name = customerUtil.getNameInput();

        char customerType = customerUtil.getCustomerTypeInput();

        String companyName = "";

        if (customerType == 'B' || customerType == 'b')
        {
            companyName = customerUtil.getCompanyNameInput();
        }

        String email = customerUtil.getEmailInput(emails);
        emails.add(email);

        String phone = customerUtil.getPhoneInput();

        Address officeAddress = Address.create(scanner);

        Address shippingAddress = customerUtil.getShippingAddressInput(officeAddress);

        return new Customer (customerType, LocalDate.now(), name, companyName, email, phone, officeAddress, shippingAddress);
    }

    public void update (Set<String> emails, Scanner scanner) {
        int option = -1;

        do
        {
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
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    updateCustomerDetails(emails, scanner);
                    break;
                }

                case 2:
                {
                    this.address.update(scanner);
                    break;
                }

                case 3:
                {
                    this.shippingAddress.update(scanner);
                    break;
                }

                case 4:
                {
                    System.out.println("\nExiting the Customer Updation Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a valid input (1 - 4)");
                    break;
                }
            }
        } while (option != 4);

    }

    private void updateCustomerDetails (Set<String> emails, Scanner scanner) {
        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Updating Type");
            System.out.println("Option 2 -> Updating Name");
            System.out.println("Option 3 -> Updating Company Name (Applicable Businesses Only)");
            System.out.println("Option 4 -> Updating Email");
            System.out.println("Option 5 -> Updating Phone");
            System.out.println("Option 6 -> Exit");

            System.out.println("\nEnter the option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    char previousCustomerType = this.getCustomerType();

                    char updatedCustomerType = customerUtil.getCustomerTypeInput();

                    this.setCustomerType(updatedCustomerType);

                    System.out.println("\nCustomer's Type updated from " + getCustomerType(previousCustomerType) + " to " + this.getCustomerType());
                    break;
                }

                case 2:
                {
                    String previousName = this.name;

                    String updatedName = customerUtil.getNameInput();

                    this.setName(updatedName);

                    System.out.println("\nCustomer's Name updated from " + previousName + " to " + updatedName);
                    break;
                }

                case 3:
                {
                    String previousCompanyName = this.companyName;

                    if (this.customerType == 'I' || this.customerType == 'i') {
                        System.out.println("\nYou are not owning a Business");
                        if (companyName != null) {
                            this.setCompanyName("");
                        }
                        break;
                    }
                    String nCompanyName = customerUtil.getCompanyNameInput();

                    this.setCompanyName(nCompanyName);

                    System.out.println("\nCustomer's Company name updated from " + previousCompanyName + " to " + nCompanyName);
                    break;
                }

                case 4:
                {
                    String previousEmail = this.email;

                    String nEmail = customerUtil.getEmailInput(emails);
                    emails.remove(previousEmail);
                    emails.add(nEmail);

                    this.setEmail(nEmail);

                    System.out.println("\nCustomer's Email updated from " + previousEmail + " to " + nEmail);
                    break;
                }

                case 5:
                {
                    String previousPhone = this.phone;

                    String nPhone = customerUtil.getPhoneInput();

                    this.setEmail(nPhone);

                    System.out.println("\nCustomer's Phone Number updated from " + previousPhone + " to " + nPhone);
                    break;
                }

                case 6:
                {
                    System.out.println("\nExiting the Customer Details Updation Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a valid input (1 - 6)");
                    break;
                }
            }
        }while (option != 6);

    }

    public static Customer searchByName (String customerName, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(customerName)) {
                return customer;
            }
        }
        return null;
    }

    public static void sortCustomers (List<Customer> sortedCustomers) {
        customerUtil.sortingModule(sortedCustomers);
    }

    public void setCustomerType(char customerType) {
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

    public void setCusNo(int cusNo) {
        this.cusNo = cusNo;
    }

    public char getCustomerType() {

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void showCustomer() {
        Utils.printLines(60);

        // Print header
        System.out.printf("| %15s | %15s | %15s | %15s | %25s | %15s |\n",
                "Customer Number",
                "Name",
                "Customer Type",
                "Company Name",
                "Email",
                "Phone");

        Utils.printLines(60);

        // Print customer data
        System.out.printf("| %15s | %15s | %15s | %15s | %25s | %15s |\n",
                cusNo,
                name,
                getCustomerType(customerType),
                companyName == null ? "-" : companyName,
                email,
                phone);

        Utils.printLines(60);
    }

    private String getCustomerType (char customerType) {
        return switch (customerType) {
            case 'I', 'i' -> "Individual";
            default -> "Business";
        };
    }

}
