import invoice.*;

import java.time.LocalDate;
import java.util.*;

public class Main {

    private static final String NAME_REGEX = "[a-zA-Z\\s'-]+";
    private static Scanner scanner;
    private static List<Item> items;
    private static List<Customer> customers;
    private static Set<String> customerEmails;
    private static List<Invoice> invoices;
    private static InvoiceUtil invoiceUtils;

    public static void main(String[] args) {
        Utils.initialize();
        scanner = new Scanner(System.in);
        invoiceUtils = new InvoiceUtil(scanner);
        customers = new ArrayList<>();
        items = new ArrayList<>();
        invoices = new ArrayList<>();
        customerEmails = new HashSet<>();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Option 1 -> Item Module");
            System.out.println("Option 2 -> Customer Module");
            System.out.println("Option 3 ->  Invoice Module");
            System.out.println("Option 4 -> Quit");
            System.out.println("-".repeat(20));
            System.out.println("Enter the option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {

                case 1:
                    System.out.println("Entering into Item Module...");
                    itemModule();

                    break;

                case 2:
                    System.out.println("Entering into Customer Module...");
                    customerModule();

                    break;

                case 3:
                    System.out.println("Entering into Invoice Module...");
                    invoiceModule();

                    break;

                case 4:
                    System.out.println("Exiting the app...");
                    isRunning = false;

                    break;
                default:
                    System.out.println("Enter a valid input (1 - 4)");

                    break;
            }
        }
        scanner.close();
    }

    private static void itemModule() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Option 1 -> Adding Item");
            System.out.println("Option 2 -> Updating Item");
            System.out.println("Option 3 -> Searching Item");
            System.out.println("Option 4 -> Deleting Item");
            System.out.println("Option 5 -> Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    Item item = Item.create();
                    items.add(item);

                    System.out.println(item.toString());
                    break;

                case 2:
                    if (items.isEmpty()) {
                        System.out.println("There is no Items available to Update");
                        break;
                    }

                    Utils.showItems(items);

                    int rowNo = -1;
                    rowNo = Utils.getValidInput(rowNo, 1, items.size() + 1, scanner, "Enter a Valid Item Number (1 - " + items.size() + " )") - 1;

                    scanner.nextLine();

                    Item uItem = items.get(rowNo);
                    uItem.update();

                    System.out.println(uItem.toString());
                    break;

                case 3:
                    String itemName = "";

                    itemName = Utils.getValidInput(itemName, NAME_REGEX, scanner, "Enter a Valid Item Name (Punam Saree): ");

                    Item searchItem = Item.search(itemName, items);

                    if (searchItem != null) {
                        System.out.println(searchItem.toString());
                    } else {
                        System.out.println("Item not found");
                    }
                    break;

                case 4:
                    Utils.showItems(items);

                    int deleteIndexNo = -1;
                    deleteIndexNo = Utils.getValidInput(deleteIndexNo, 1, items.size() + 1, scanner, "Enter a Valid Item Number (1 - " + items.size() + " )") - 1;
                    scanner.nextLine();

                    System.out.println(items.get(deleteIndexNo).getItemName() + " is deleted successfully...");
                    items.remove(deleteIndexNo);
                    break;

                case 5:
                    System.out.println("Exiting the Item Module...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Enter Valid Option (1 - 5)");
                    break;
            }
        }
    }

    private static void customerModule() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Option 1 -> Adding Customer");
            System.out.println("Option 2 -> Updating Customer");
            System.out.println("Option 3 -> Searching Customer");
            System.out.println("Option 4 -> Deleting Customer");
            System.out.println("Option 5 -> Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    Customer customer = Customer.create(customerEmails);
                    customers.add(customer);

                    System.out.println(customer.toString());
                    break;

                case 2:
                    if (customers.isEmpty()) {
                        System.out.println("There is no Customers available to Update");
                        break;
                    }

                    Utils.showCustomers(customers);

                    int rowNo = -1;
                    rowNo = Utils.getValidInput(rowNo, 1, invoices.size() + 1, scanner, "Enter a Valid Customer Number (1 - " + customers.size() + " )") - 1;

                    scanner.nextLine();

                    Customer uCustomer = customers.get(rowNo);
                    uCustomer.update();

                    System.out.println(uCustomer.toString());
                    break;

                case 3:
                    String customerName = "";

                    customerName = Utils.getValidInput(customerName, NAME_REGEX, scanner, "Enter a Valid Customer Name (Dinesh Kumar K K): ");

                    Customer searchCustomer = Customer.searchByName(customerName, customers);

                    if (searchCustomer != null) {
                        System.out.println(searchCustomer.toString());
                    } else {
                        System.out.println("Customer not found");
                    }
                    break;

                case 4:
                    Utils.showCustomers(customers);

                    int deleteIndexNo = -1;
                    deleteIndexNo = Utils.getValidInput(deleteIndexNo, 1, customers.size() + 1, scanner, "Enter a Valid Customer Number (1 - " + customers.size() + " )") - 1;

                    scanner.nextLine();

                    customerEmails.remove(customers.get(deleteIndexNo).getEmail());
                    System.out.println(customers.get(deleteIndexNo).getName() + " " + "is deleted successfully...");

                    customers.remove(deleteIndexNo);

                    break;

                case 5:
                    System.out.println("Exiting the Customer Module...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Enter Valid Option (1 - 5)");
                    break;
            }
        }
    }

    private static void invoiceModule() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Option 1 -> Adding Invoice");
            System.out.println("Option 2 -> Updating Invoice");
            System.out.println("Option 3 -> Searching Invoice");
            System.out.println("Option 4 -> Deleting Invoice");
            System.out.println("Option 5 -> Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    Invoice invoice = create();

                    if (invoice == null) {
                        System.out.println("Can't Create Invoice, due to no Customers...");
                        break;
                    }

                    invoices.add(invoice);

                    System.out.println(invoice.toString());
                    break;

                case 2:
                    if (invoices.isEmpty()) {
                        System.out.println("There is no Invoices available to Update");
                        break;
                    }

                    Utils.showInvoices(invoices);

                    int rowNo = -1;
                    rowNo = Utils.getValidInput(rowNo, 1, invoices.size() + 1, scanner, "Enter a Valid Invoice Number (1 - " + invoices.size() + " )") - 1;
                    scanner.nextLine();

                    Invoice uInvoice = invoices.get(rowNo);

                    update(uInvoice);

                    break;

                case 3:
                    String customerName = "";

                    customerName = Utils.getValidInput(customerName, NAME_REGEX, scanner, "Enter a Valid Customer Name (Dinesh Kumar K K): ");

                    List<Invoice> invoicesForCustomer = searchByCustomer(customerName);

                    if (invoicesForCustomer.isEmpty()) {
                        System.out.println("No Such Customer (Or) No Invoices for this Customer ");
                        break;
                    }

                    Utils.showInvoices(invoicesForCustomer);
                    break;

                case 4:
                    Utils.showInvoices(invoices);

                    int deleteInvoiceNo = -1;

                    deleteInvoiceNo = Utils.getValidInput(deleteInvoiceNo, 1, invoices.size() + 1, scanner, "Enter a Valid Invoice Number (1 - " + invoices.size() + " )") - 1;

                    if (invoiceUtils.neglectWarning(invoices.get(deleteInvoiceNo), "delete")){
                        break;
                    }

                    invoices.remove(deleteInvoiceNo);
                    break;

                case 5:
                    System.out.println("Exiting the Invoice Module...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Enter Valid Option (1 - 5)");
                    break;
            }
        }
    }

    private static Invoice create() {
        Scanner scanner = new Scanner(System.in);

        if (customers.isEmpty()) {
            System.out.println("Since, there is no customers, you have to create Customers.");
            char reDirectToCustomerModule = 'A';
            reDirectToCustomerModule = Utils.getValidOption(reDirectToCustomerModule,'Y', 'N', scanner, "Do you like to Create a customer, \nYes -> Y\nNo -> N");

            if (reDirectToCustomerModule == 'Y') {
                customerModule();
            } else {
                return null;
            }
        }

        Utils.showCustomers(customers);

        int cusNo = invoiceUtils.getCustomerInput(1, customers.size() + 1);

        Customer cus = customers.get(cusNo);

        LocalDate date = LocalDate.now();

        int paymentTerm = invoiceUtils.getPaymentTermInput();

        Invoice invoice = new Invoice(cus, date, paymentTerm);

        Map<Item, Integer> itemTable = new HashMap<>();
        itemTableOperations(invoice, itemTable);
        invoice.setItemTable(itemTable);

        double subTotal = invoice.getSubTotal();

        double discount = invoiceUtils.getDiscountInput();
        invoice.setDiscount(discount);

        double invTotal = subTotal - ((subTotal / 100) * discount);

        double shippingCharges = invoiceUtils.getShippingCharges();
        System.out.println("Main");
        invoice.setShippingCharges(shippingCharges);

        invTotal += shippingCharges;

        invoice.setTotal(invTotal);

        return invoice;
    }

    private static void update (Invoice invoice) {
        boolean isUpdating = true;

        if (invoiceUtils.neglectWarning(invoice, "update")) {
            return;
        }

        while (isUpdating) {
            System.out.println("Option 1 -> (Item Table, Payment Term, Customer) Modifications");
            System.out.println("Option 2 -> (Discount, Shipping Charges, status) Modifications");
            System.out.println("Option 3 -> Exit the Update Module");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    modifyInvoicePrimaryDetails(invoice);
                    break;
                case 2:
                    modifyInvoiceOtherCharges(invoice);
                    break;
                case 3:
                    System.out.println("Exiting the Invoice modification module");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter a Valid input (1 - 3)");
                    break;
            }
        }
    }

    private static void modifyInvoicePrimaryDetails(Invoice invoice) {
        boolean isUpdating = true;
        while (isUpdating) {
            System.out.println("Option 1 -> Update Item Table");
            System.out.println("Option 2 -> Update Payment Term");
            System.out.println("Option 3 -> Update Customer");
            System.out.println("Option 4 -> Exit the Invoice Primary Details Modification Module");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    itemTableOperations(invoice, invoice.getItemTable());

                    break;
                case 2:
                    int paymentTerm = invoiceUtils.getPaymentTermInput();

                    invoice.setPaymentTerm(paymentTerm);

                    break;
                case 3:
                    Utils.showCustomers(customers);

                    int cusNo = invoiceUtils.getCustomerInput(1, customers.size());

                    invoice.setCustomer(customers.get(cusNo));

                    break;
                case 4:
                    System.out.println("Exiting the Invoice modification module");

                    isUpdating = false;

                    break;
                default:
                    System.out.println("Enter a Valid input (1 - 4)");
                    break;
            }
        }
    }

    private static void modifyInvoiceOtherCharges(Invoice invoice) {
        boolean isUpdating = true;
        while (isUpdating) {
            System.out.println("Option 1 -> Update Discount");
            System.out.println("Option 2 -> Update Shipping Charges");
            System.out.println("Option 3 -> Update Status");
            System.out.println("Option 4 -> Exit the Invoice Other Charges Module");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    double previousDiscount = invoice.getDiscount();

                    double nDiscount = invoiceUtils.getDiscountInput();

                    invoice.setDiscount(nDiscount);

                    invoice.setTotal(invoice.getTotal() + ((invoice.getSubTotal() / 100) * previousDiscount) - ((invoice.getSubTotal() / 100) * nDiscount));
                    break;
                case 2:

                    double previousShippingCharges = invoice.getShippingCharges();

                    double shippingCharges = invoiceUtils.getShippingCharges();

                    invoice.setShippingCharges(shippingCharges);

                    invoice.setTotal(invoice.getTotal() - previousShippingCharges + shippingCharges);

                    break;
                case 3:
                    int status = -1;

                    status = Utils.getValidInput(status, 1, Utils.invoiceStatus.size(), scanner, Utils.showStatuses()) - 1;

                    invoice.setStatus(status);

                    break;
                case 4:
                    System.out.println("Exiting the Invoice modification module");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter a Valid input (1 - 4)");
                    break;
            }
        }
    }

    private static void itemTableOperations (Invoice invoice, Map<Item, Integer> itemTable) {

        boolean isPurchasing = true;
        while (isPurchasing) {

            System.out.println("Option 1 -> Add Items to the list");
            if (!itemTable.isEmpty()) {
                System.out.println("Option 2 -> Remove Items from the list");
            } else {
                System.out.println("Option 2 is disabled, No items added yet...");
            }
            System.out.println("Option 3 -> Quit");
            int purchaseOption = scanner.nextInt();
            scanner.nextLine();

            switch (purchaseOption) {
                case 1:
                    if (items.isEmpty()) {
                        System.out.println("Since, there is no items, you have to create Items.");
                        char reDirectToItemsModule = 'A';
                        reDirectToItemsModule = Utils.getValidOption(reDirectToItemsModule,'Y', 'N', scanner, "Do you like to Create an item, \nYes -> Y\nNo -> N");

                        if (reDirectToItemsModule == 'Y') {
                            itemModule();
                        } else {
                            break;
                        }
                    }

                    Utils.showItems(items);

                    int itemNo = -1;

                    itemNo = Utils.getValidInput(itemNo, 1, items.size(), scanner, "Enter the Item No (Eg. 1 -  " + items.size() + "):") - 1;


                    int quantity = -1;

                    quantity = Utils.getValidInput(quantity, 1, (int) 1e9, scanner, "Enter the Quantity (No -ve Integers allowed): ");

                    scanner.nextLine();

                    itemTable.put(items.get(itemNo), itemTable.getOrDefault(items.get(itemNo), 0) + quantity);
                    break;
                case 2:
                    if (!itemTable.isEmpty()) {

                        showItemDetails(itemTable);

                        int removalItemNo = -1;
                        removalItemNo = Utils.getValidInput(removalItemNo, 1, itemTable.size(), scanner, "Select the Item No to remove (Eg. 1 - " + itemTable.size() + ")") - 1;

                        Item item = items.get(removalItemNo);

                        int removeOption = 0;
                        removeOption = Utils.getValidInput(removeOption, 1, 3, scanner, "Option 1 -> Remove the Entire items from the table\n Option 2 -> Remove partial items");

                        if (removeOption == 1) {
                            System.out.println("Enter the Item no, you want to remove: ");
                            itemTable.remove(item);
                        } else {
                            System.out.println("Enter the Item no, you want to partially remove: ");
                            int removalQuantity = itemTable.get(item) + 1;
                            while (removalQuantity > itemTable.get(item
                            )) {
                                System.out.println("Enter the appropriate quantity that you want remove");
                                removalQuantity = scanner.nextInt();
                            }
                            itemTable.put(item, itemTable.get(item) - removalQuantity);
                        }
                        scanner.nextLine();
                    } else {
                        System.out.println("No items added yet");
                    }
                    break;
                case 3:
                    isPurchasing = false;
                    System.out.println("Exiting the purchasing...");
                    break;
                default:
                    System.out.println("Enter a valid option (1 - 3)");
                    break;
            }
        }

        invoice.setItemTable(itemTable);

        double subTotal = 0;

        char stateOption = 'A';

        stateOption = Utils.getValidOption(stateOption, 'Y', 'N',
                scanner, "Is this transaction within the state, if Yes -> Y, No -> N");

        boolean withinState = stateOption == 'Y';

        for (Item cartItem : itemTable.keySet()) {
            double cost = cartItem.getRate(itemTable.get(cartItem), withinState);
            subTotal += cost;
        }

        invoice.setSubTotal(subTotal);
    }

    private static List<Invoice> searchByCustomer (String customerName) {
        List<Invoice> invoicesOfCustomer = new ArrayList<>();
        for (Invoice inv : invoices) {
            if (inv.getCustomer().getName().equalsIgnoreCase(customerName)) {
                invoicesOfCustomer.add(inv);
            }
        }
        return invoicesOfCustomer;
    }

    private static void showItemDetails (Map<Item, Integer> itemTable) {
        System.out.println("-".repeat(45));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Item Quantity");
        System.out.println("-".repeat(45));

        int count = 0;
        for (Item cartItem : itemTable.keySet()) {
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    ++count,
                    cartItem.getItemName(),
                    itemTable.get(cartItem));
        }

        System.out.println("-".repeat(45));
    }

}