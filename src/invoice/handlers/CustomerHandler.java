package invoice.handlers;

import invoice.interfaces.ComparatorCallBack;
import invoice.models.Address;
import invoice.models.Customer;
import invoice.utils.CustomerUtil;
import invoice.utils.InputUtils;
import invoice.utils.SortingUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerHandler
{

    private final CustomerUtil customerUtil;

    public CustomerHandler () {
        this.customerUtil = new CustomerUtil();
    }

    public Customer create (List<Customer> customers)
    {
        System.out.println("\nYou can add Customer now");

        String name = customerUtil.getNameInput();

        char customerType = customerUtil.getCustomerTypeInput();

        String companyName = "";

        if (customerType != 'i')
        {
            companyName = customerUtil.getCompanyNameInput();
        }

        String email = customerUtil.getEmailInput(customers);

        String phone = customerUtil.getPhoneInput();

        Customer customer = new Customer(customerType, LocalDate.now(), name, companyName, email, phone);

        customer.setAddress(new AddressHandler(null, null).createOrEdit(false));

        customer.setShippingAddress(new AddressHandler(null, customer.getAddress()).createOrEdit(true));

        customer.setCustomerId(customer.generateCustomerId());

        return customer;
    }

    public void update (List<Customer> customers) {

        if (customers.isEmpty()) {
            System.out.println("\nThere is no Customers available to Update");
            return;
        }

        int customerNo = InputUtils.getCustomerNumber(customers);

        Customer customer = customers.get(customerNo);

        int option = -1;

        customerUpdateIem:
        {
            while (true)
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
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option)
                {
                    case 1:
                    {
                        updateCustomerDetails(customer, customers);
                        break;
                    }

                    case 2:
                    {
                        customer.setAddress(new AddressHandler(customer.getCustomerId(), customer.getAddress()).createOrEdit(false));

                        break;
                    }

                    case 3:
                    {
                        Address shippingAddress = customer.getShippingAddress() == null ? customer.getAddress() : customer.getShippingAddress();

                        customer.setShippingAddress(new AddressHandler(customer.getCustomerId(), shippingAddress).createOrEdit(true));

                        break;
                    }

                    case 4:
                    {
                        System.out.println("\nExiting the Customer Updation Module...");
                        break customerUpdateIem;
                    }

                    default:
                    {
                        System.out.println("\nEnter a valid input (1 - 4)");
                        break;
                    }
                }
            }
        }

        System.out.println("\nUpdated Customer Details");
        customer.showCustomer();

    }

    public void searchByName (List<Customer> customers)
    {
        final String NAME_REGEX = "[a-zA-Z\\s'-]+";

        String customerName = InputUtils.getValidStringInput(NAME_REGEX, "Dinesh Kumar K K", "Customer Name", "Enter the Customer Name", true);

        List<Customer> customersFound = new ArrayList<>();

        for (Customer customer : customers)
        {
            if (customer.getName().toLowerCase().contains(customerName.toLowerCase()))
            {
                customersFound.add(customer);
            }
        }

        if (!customersFound.isEmpty())
        {
            System.out.println("\n");
            InputUtils.showCustomers(customersFound);
        } else
        {
            System.out.println("\nNo customer found with this name");
        }
    }

    public void deleteCustomer (List<Customer> customers)
    {
        int deleteCustomerNo = InputUtils.getCustomerNumber(customers);

        Customer selectedCustomer = customers.get(deleteCustomerNo);

        if (InputUtils.hasSingleElement(customers)) {
            char confirmationOption = InputUtils.collectToggleChoice(  'y', "Delete Customer", "\nSince there is only one customer " + customers.get(deleteCustomerNo).getName() + "\nDo you still want to delete (y -> yes, any other key -> no)");

            if (confirmationOption == 'N' || confirmationOption == 'n') {
                System.out.println("\nCustomer is not deleted");
                return;
            }
        }

        System.out.println("\n" + selectedCustomer.getName() + " " + "is deleted successfully...");

        customers.remove(deleteCustomerNo);
    }

    public void sortingModule (List<Customer> customers)
    {
        SortingUtil sortingUtil = new SortingUtil();

        System.out.println("\nEnter the choice based in which you want to sort the Customers: ");

        int sortBy = -1;

        sortModule:
        {
            while (true) {
                System.out.println("\nOption 1 -> Sort by Customer Number");
                System.out.println("Option 2 -> Sort by Date");
                System.out.println("Option 3 -> Sort by Customer Name");
                System.out.println("Option 4 -> Exit the Sorting Module");

                System.out.println("\nEnter the Sort by Option: ");

                sortBy = InputUtils.handleIntegerInputMisMatches(sortBy, -1);

                int sortingOrder = -1;

                if (sortBy >= 1 && sortBy <= 3){
                    orderInput:
                    {
                        while (true) {
                            System.out.println("\nOption 1 -> Ascending Order");
                            System.out.println("Option 2 -> Descending Order");

                            System.out.println("\nEnter the Sorting Order Option: ");

                            sortingOrder = InputUtils.handleIntegerInputMisMatches(sortingOrder, -1);

                            switch (sortingOrder) {
                                case 1, 2: {
                                    break orderInput;
                                }
                                default: {
                                    System.out.println("\nEnter a valid input (1 - 2)");
                                    break;
                                }
                            }
                        }

                    }
                }

                int finalSortingOrder = sortingOrder;

                switch (sortBy) {
                    case 1: {

                        sortingUtil.mergeSort(0, customers.size() - 1, customers, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Customer c1 = (Customer) obj1;
                                Customer c2 = (Customer) obj2;
                                return finalSortingOrder == 1 ? InputUtils.compareIntegers(c1.getCusNo(), c2.getCusNo()) : InputUtils.compareIntegers(c2.getCusNo(), c1.getCusNo());
                            }
                        });

                        break;
                    }
                    case 2: {
                        sortingUtil.mergeSort(0, customers.size() - 1, customers, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Customer c1 = (Customer) obj1;
                                Customer c2 = (Customer) obj2;
                                return finalSortingOrder == 1 ? InputUtils.compareDates(c1.getCreatedAt(), c2.getCreatedAt()) : InputUtils.compareDates(c2.getCreatedAt(), c1.getCreatedAt());
                            }
                        });

                        break;
                    }
                    case 3: {
                        sortingUtil.mergeSort(0, customers.size() - 1, customers, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Customer c1 = (Customer) obj1;
                                Customer c2 = (Customer) obj2;
                                return finalSortingOrder == 1 ? InputUtils.compareStrings(c1.getName(), c2.getName()) : InputUtils.compareStrings(c2.getName(), c1.getName());
                            }
                        });

                        break;
                    }
                    case 4: {
                        System.out.println("\nExiting sorting module...");
                        break sortModule;
                    }
                    default: {
                        System.out.println("\nEnter a valid option (1 - 4)");
                        break;
                    }
                }

                InputUtils.showCustomers(customers);
            }
        }

    }

    private void updateCustomerDetails (Customer customer, List<Customer> customers) {
        int option = -1;

        customerPrimaryDetailsUpdationMenu:
        {
            while (true)
            {
                System.out.println("\nOption 1 -> Updating Type");
                System.out.println("Option 2 -> Updating Name");
                System.out.println("Option 3 -> Updating Company Name (Applicable Businesses Only)");
                System.out.println("Option 4 -> Updating Email");
                System.out.println("Option 5 -> Updating Phone");
                System.out.println("Option 6 -> Exit");

                System.out.println("\nEnter the option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option)
                {
                    case 1:
                    {
                        char previousCustomerType = customer.getCustomerType();
                        String previousCustomerTypeString = customer.getCustomerType(previousCustomerType);

                        char updatedCustomerType = customerUtil.getCustomerTypeInput();

                        if (previousCustomerType == updatedCustomerType)
                        {
                            System.out.println("\nYou entered the same Customer Type again, so no updation is done");
                            break;
                        }

                        customer.setCustomerType(updatedCustomerType);

                        System.out.println("\nCustomer's Type updated from " + previousCustomerTypeString + " to " + customer.getCustomerType(updatedCustomerType));
                        break;
                    }

                    case 2:
                    {
                        String previousName = InputUtils.handleNullStrings(customer.getName());

                        String updatedName = customerUtil.getNameInput();

                        if (previousName.equalsIgnoreCase(updatedName))
                        {
                            System.out.println("\nYou entered the same Customer Name again, so no updation is done");
                            break;
                        }

                        customer.setName(updatedName);

                        System.out.println("\nCustomer's Name updated from " + previousName + " to " + updatedName);
                        break;
                    }

                    case 3:
                    {
                        String previousCompanyName = InputUtils.handleNullStrings(customer.getCompanyName());

                        if (customer.getCustomerType() == 'i')
                        {
                            System.out.println("\nYou are not owning a Business");
                            if (customer.getCompanyName() != null)
                            {
                                customer.setCompanyName("");
                            }
                            break;
                        }
                        String nCompanyName = customerUtil.getCompanyNameInput();

                        if (previousCompanyName.equalsIgnoreCase(nCompanyName))
                        {
                            System.out.println("\nYou entered the same Company Name again, so no updation is done");
                            break;
                        }

                        customer.setCompanyName(nCompanyName);

                        System.out.println("\nCustomer's Company name updated from " + previousCompanyName + " to " + nCompanyName);
                        break;
                    }

                    case 4:
                    {
                        String previousEmail = customer.getEmail();

                        String nEmail = customerUtil.getEmailInput(customers);

                        customer.setEmail(nEmail);

                        System.out.println("\nCustomer's Email updated from " + previousEmail + " to " + nEmail);
                        break;
                    }

                    case 5:
                    {
                        String previousPhone = InputUtils.handleNullStrings(customer.getPhone());

                        String nPhone = customerUtil.getPhoneInput();

                        if (previousPhone.equals(nPhone))
                        {
                            System.out.println("\nYou entered the same Phone Number again, so no updation is done");
                            break;
                        }

                        customer.setPhone(nPhone);

                        System.out.println("\nCustomer's Phone Number updated from " + previousPhone + " to " + nPhone);
                        break;
                    }

                    case 6:
                    {
                        System.out.println("\nExiting the Customer Details Updation Module...");
                        break customerPrimaryDetailsUpdationMenu;
                    }

                    default:
                    {
                        System.out.println("\nEnter a valid input (1 - 6)");
                        break;
                    }
                }
            }
        }

    }

}
