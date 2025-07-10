package invoice.models;

import invoice.utils.DisplayUtil;
import invoice.utils.InputUtil;

import java.time.LocalDate;

public class Customer
{

    // Primary Details
    private static int totalCustomers = 1;
    private String customerId;
    private final int cusNo;
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

    public Customer (char customerType, LocalDate createdAt, String name, String companyName, String email, String phone)
    {
        this.customerType = customerType;
        this.createdAt = createdAt;
        this.name = name;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.customerId = generateCustomerId();
        this.cusNo = totalCustomers;
        totalCustomers++;
    }

    public Customer (char customerType, LocalDate createdAt, String name, String companyName, String email, String phone, Address address, Address shippingAddress)
    {
        this.customerType = customerType;
        this.createdAt = createdAt;
        this.name = name;
        this.cusNo = totalCustomers;
        totalCustomers++;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.shippingAddress = shippingAddress;
        this.customerId = generateCustomerId();
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getCustomerType(char customerType)
    {
        return customerType == 'i' ? "Individual" : "Business";
    }

    public String generateCustomerId ()
    {
        String emailSplit = this.email.split("@")[0].toUpperCase();
        return "CUS" + emailSplit + this.phone.substring(this.phone.length() - 2);
    }

    public void showCustomer()
    {
        DisplayUtil.printHyphens(60);

        System.out.printf("| %15s | %15s | %15s | %15s | %25s | %15s |\n",
                "Customer ID",
                "Name",
                "Customer Type",
                "Company Name",
                "Email",
                "Phone");

        DisplayUtil.printHyphens(60);

        System.out.printf("| %15s | %15s | %15s | %15s | %25s | %15s |\n",
                customerId,
                Character.toUpperCase(name.charAt(0)) + name.substring(1),
                getCustomerType(customerType),
                companyName == null ? "-" : companyName,
                email,
                phone);

        DisplayUtil.printHyphens(60);
    }

}

