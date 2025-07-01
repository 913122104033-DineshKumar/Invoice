package invoice;

import java.util.Scanner;

public class Customer {
    // Primary Details
    private static int totalCustomers = 0;
    private final int cusNo;
    private final Utils.CustomerTypes customerType;
    private final String name;
    private String companyName;
    private String email;
    private String phone;

    // Office Address
    private Address address;

    // Shipping Address
    private Address shippingAddress;

    public Customer (Utils.CustomerTypes customerType, String name) {
        this.customerType = customerType;
        this.name = name;
        totalCustomers++;
        this.cusNo = totalCustomers;
    }

    public static Customer createCustomer () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Name: ");
        String name = scanner.nextLine();
        Utils.CustomerTypes customerType;
        String companyName = "";
        System.out.println("Individual -> 0, Business -> 1");
        int customerTypeOption = scanner.nextInt();
        scanner.nextLine();
        if (customerTypeOption == 0) {
            customerType = Utils.CustomerTypes.INDIVIDUAL;
        } else {
            customerType = Utils.CustomerTypes.BUSINESS;
            System.out.println("Enter the Company Name: ");
            companyName = scanner.nextLine();
        }
        Customer customer = new Customer(customerType, name);
        if (!companyName.isEmpty()) {
            customer.setCompanyName(companyName);
        }
        String email = "";
        while (email.isBlank()) {
            System.out.println("Enter the Email Address: ");
            email = scanner.nextLine();
        }
        customer.setEmail(email);
        String phone = "";
        while (phone.isEmpty()) {
            System.out.println("Enter the Phone Number: ");
            phone = scanner.nextLine();
        }
        customer.setPhone(phone);
        System.out.println("Enter the Country: ");
        String country = scanner.nextLine();
        System.out.println("Enter the State: ");
        String state = scanner.nextLine();
        System.out.println("Enter the City: ");
        String city = scanner.nextLine();
        System.out.println("Enter the Street: ");
        String street = scanner.nextLine();
        System.out.println("Enter the Pin Code: ");
        String pinCode = scanner.nextLine();
        Address address = new Address(country, state, city, street, pinCode);
        System.out.println("Address for shipping, Same -> 0, Different -> 1 ");
        customer.setAddress(address);
        int addressOption = scanner.nextInt();
        scanner.nextLine();
        if (addressOption == 0) {
            customer.setShippingAddress(address);
        } else {
            System.out.println("Enter the Shipping Country: ");
            String shippingCountry = scanner.nextLine();
            System.out.println("Enter the Shipping State: ");
            String shippingState = scanner.nextLine();
            System.out.println("Enter the Shipping City: ");
            String shippingCity = scanner.nextLine();
            System.out.println("Enter the Shipping Street: ");
            String shippingStreet = scanner.nextLine();
            System.out.println("Enter the Shipping Pin Code: ");
            String shippingPinCode = scanner.nextLine();
            Address shippingAddress = new Address(shippingCountry,shippingState, shippingCity, shippingStreet, shippingPinCode);
            customer.setShippingAddress(shippingAddress);
        }
        return customer;
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

    public Utils.CustomerTypes getCustomerType() {
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

        sb.append("\nTotal Customers: ").append(totalCustomers);

        return sb.toString();
    }

}
