import com.sun.source.tree.Tree;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;
import invoice.utils.CustomerUtil;
import invoice.utils.InvoiceUtil;
import invoice.utils.Utils;

import java.time.LocalDate;
import java.util.*;

public class Main {

    private static final String NAME_REGEX = "[a-zA-Z\\s'-]+";
    private static Scanner scanner;
    private static TreeMap<Integer, Item> items;
    private static TreeMap<Integer, Customer> customers;
    private static Set<String> customerEmails;
    private static TreeMap<Integer, Invoice> invoices;
    private static InvoiceUtil invoiceUtils;

    public static void main(String[] args) {
        Utils.initialize();
        scanner = new Scanner(System.in);
        invoiceUtils = new InvoiceUtil(scanner);
        customers = new TreeMap<>();
        items = new TreeMap<>();
        invoices = new TreeMap<>();
        customerEmails = new HashSet<>();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nOption 1 -> Item Module");
            System.out.println("Option 2 -> Customer Module");
            System.out.println("Option 3 ->  Invoice Module");
            System.out.println("Option 4 -> Quit");
            System.out.println("-".repeat(20));

            System.out.println("\nEnter the option: ");
            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {

                case 1:
                    System.out.println("\nEntering into Item Module...");
                    itemModule();

                    break;

                case 2:
                    System.out.println("\nEntering into Customer Module...");
                    customerModule();

                    break;

                case 3:
                    System.out.println("\nEntering into Invoice Module...");
                    invoiceModule();

                    break;

                case 4:
                    System.out.println("\nExiting the app...");
                    isRunning = false;

                    break;
                default:
                    System.out.println("\nEnter a valid input (1 - 4)");

                    break;
            }
        }
        scanner.close();
    }

    private static void itemModule() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nOption 1 -> Adding Item");
            System.out.println("Option 2 -> Updating Item");
            System.out.println("Option 3 -> Searching Item");
            System.out.println("Option 4 -> Deleting Item");
            System.out.println("Option 5 -> Exit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    Item item = Item.create();
                    items.put(item.getItemNo(), item);

                    System.out.println("\n" + item.toString());
                    break;

                case 2:
                    if (items.isEmpty()) {
                        System.out.println("\nThere is no Items available to Update");
                        break;
                    }

                    Utils.showItems(items);

                    System.out.println("Enter the Item No from the table");
                    int itemNo = 0;
                    itemNo = (int) Utils.handleIntegerInputMisMatches(itemNo, scanner);

                    itemNo = Utils.getValidInput(itemNo, items.firstKey(), items.lastKey(), scanner, "Item Number");

                    Item uItem = items.get(itemNo);
                    uItem.update();

                    System.out.println(uItem.toString());
                    break;

                case 3:

                    if (items.isEmpty()) {
                        System.out.println("\nThere are no Items to search");
                        break;
                    }

                    System.out.println("\nEnter Item Name (Eg. Punam Saree): ");

                    String itemName = scanner.nextLine();

                    itemName = Utils.getValidInput(itemName, NAME_REGEX, scanner, "Punam Saree", "Item Name");

                    Item searchItem = Item.search(itemName, items);

                    if (searchItem != null) {
                        System.out.println(searchItem.toString());
                    } else {
                        System.out.println("\nItem not found");
                    }
                    break;

                case 4:

                    if (items.isEmpty()) {
                        System.out.println("\nThere is no items to delete");
                        break;
                    }

                    Utils.showItems(items);

                    System.out.println("\nEnter the Item Number from the table: ");
                    int deleteItemNo = 0;
                    deleteItemNo = (int) Utils.handleIntegerInputMisMatches(deleteItemNo, scanner);;

                    deleteItemNo = Utils.getValidInput(deleteItemNo, items.firstKey(), items.lastKey(), scanner, "Item Number");

                    System.out.println("\n" + items.get(deleteItemNo).getItemName() + " is deleted successfully...");
                    items.remove(deleteItemNo);
                    break;

                case 5:
                    System.out.println("\nExiting the Item Module...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("\nEnter Valid Option (1 - 5)");
                    break;
            }
        }
    }

    private static void customerModule() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nOption 1 -> Adding Customer");
            System.out.println("Option 2 -> Updating Customer");
            System.out.println("Option 3 -> Searching Customer");
            System.out.println("Option 4 -> Deleting Customer");
            System.out.println("Option 5 -> Exit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    Customer customer = Customer.create(customerEmails);
                    customers.put(customer.getCusNo(), customer);

                    System.out.println("\n" + customer.toString());
                    break;

                case 2:
                    if (customers.isEmpty()) {
                        System.out.println("\nThere is no Customers available to Update");
                        break;
                    }

                    Utils.showCustomers(customers);

                    System.out.println("\nEnter the Customer Number: ");
                    int customerNo = 0;
                    customerNo = (int) Utils.handleIntegerInputMisMatches(customerNo, scanner);

                    customerNo = Utils.getValidInput(customerNo, customers.firstKey(), customers.lastKey(), scanner, "Customer Number");

                    Customer uCustomer = customers.get(customerNo);
                    uCustomer.update(customerEmails);

                    System.out.println("\n" + uCustomer.toString());
                    break;

                case 3:

                    if (customers.isEmpty()) {
                        System.out.println("\nThere are no Customers to search");
                        break;
                    }

                    System.out.println("Enter the Customer Name: ");

                    String customerName = scanner.nextLine();

                    customerName = Utils.getValidInput(customerName, NAME_REGEX, scanner, "Dinesh Kumar K K", "Customer Name");

                    Customer searchCustomer = Customer.searchByName(customerName, customers);

                    if (searchCustomer != null) {
                        System.out.println("\n" + searchCustomer.toString());
                    } else {
                        System.out.println("Customer not found");
                    }
                    break;

                case 4:
                    if (customers.isEmpty()) {
                        System.out.println("\nThere are no Customers to delete");
                        break;
                    }

                    Utils.showCustomers(customers);

                    System.out.println("\nEnter the Customer No from the table: ");

                    int deleteIndexNo = 0;
                    deleteIndexNo = (int) Utils.handleIntegerInputMisMatches(deleteIndexNo, scanner);

                    deleteIndexNo = Utils.getValidInput(deleteIndexNo, customers.firstKey(), customers.lastKey(), scanner, "Customer Number");

                    customerEmails.remove(customers.get(deleteIndexNo).getEmail());

                    System.out.println("\n" + customers.get(deleteIndexNo).getName() + " " + "is deleted successfully...");

                    customers.remove(deleteIndexNo);

                    break;

                case 5:
                    System.out.println("\nExiting the Customer Module...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("\nEnter Valid Option (1 - 5)");
                    break;
            }
        }
    }

    private static void invoiceModule() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nOption 1 -> Adding Invoice");
            System.out.println("Option 2 -> Updating Invoice");
            System.out.println("Option 3 -> Searching Invoice");
            System.out.println("Option 4 -> Deleting Invoice");
            System.out.println("Option 5 -> Exit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    Invoice invoice = create();

                    if (invoice == null) {
                        System.out.println("\nCan't Create Invoice, due to no Customers...");
                        break;
                    }

                    invoices.put(invoice.getInvNo(),  invoice);

                    System.out.println("\n" + invoice.toString());
                    break;

                case 2:
                    if (invoices.isEmpty()) {
                        System.out.println("\nThere is no Invoices available to Update");
                        break;
                    }

                    Utils.showInvoices(invoices);

                    System.out.println("\nEnter the Invoice No from the table:");

                    int invNo = 0;
                    invNo = (int) Utils.handleIntegerInputMisMatches(invNo, scanner);

                    invNo = Utils.getValidInput(invNo, invoices.firstKey(), invoices.lastKey(), scanner, "Invoice Number");

                    Invoice uInvoice = invoices.get(invNo);

                    update(uInvoice);

                    break;

                case 3:

                    if (invoices.isEmpty()) {
                        System.out.println("\nThere is no Invoices to update");
                        break;
                    }

                    String customerName = new CustomerUtil(scanner).getNameInput();

                    TreeMap<Integer, Invoice> invoicesForCustomer = searchByCustomer(customerName);

                    if (invoicesForCustomer.isEmpty()) {
                        System.out.println("\nNo Such Customer (Or) No Invoices for this Customer ");
                        break;
                    }

                    Utils.showInvoices(invoicesForCustomer);
                    break;

                case 4:
                    if (invoices.isEmpty()) {
                        System.out.println("\nThere is no invoices to delete");
                        break;
                    }

                    Utils.showInvoices(invoices);

                    System.out.println("\nEnter the Invoice No from the table:");

                    int deleteInvoiceNo = 0;
                    deleteInvoiceNo = (int) Utils.handleIntegerInputMisMatches(deleteInvoiceNo, scanner);

                    deleteInvoiceNo = Utils.getValidInput(deleteInvoiceNo, invoices.firstKey(), invoices.lastKey(), scanner, "Invoice Number");

                    if (invoiceUtils.neglectWarning(invoices.get(deleteInvoiceNo), "delete")){
                        break;
                    }

                    invoices.remove(deleteInvoiceNo);
                    break;

                case 5:
                    System.out.println("\nExiting the Invoice Module...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("\nEnter Valid Option (1 - 5)");
                    break;
            }
        }
    }

    private static Invoice create() {
        System.out.println("\nYou can add Invoice now...");
        Scanner scanner = new Scanner(System.in);

        boolean isAvailableCustomer = customers.isEmpty();

        if (customers.isEmpty()) {
            if (Utils.redirectVerification("Customers", scanner)) {
                Customer customer = Customer.create(customerEmails);

                customers.put(customer.getCusNo(), customer);
            } else {
                return null;
            }
        }

        if (isAvailableCustomer) {
            System.out.println("\nCustomer has been created and now can create Invoice\n");
        }

        Utils.showCustomers(customers);

        int cusNo = invoiceUtils.getCustomerInput(customers.firstKey(), customers.lastKey());

        Customer cus = customers.get(cusNo);

        LocalDate date = LocalDate.now();

        int paymentTerm = invoiceUtils.getPaymentTermInput();

        Invoice invoice = new Invoice(cus, date, paymentTerm);

        Map<Item, Integer> itemTable = new TreeMap<>((item1, item2) -> Integer.compare(item1.getItemNo(), item2.getItemNo()));
        itemTableOperations(invoice, itemTable);
        invoice.setItemTable(itemTable);

        double subTotal = invoice.getSubTotal();

        double discount = invoiceUtils.getDiscountInput(true);
        invoice.setDiscount(discount);

        double invTotal = subTotal - ((subTotal / 100) * discount);

        double shippingCharges = invoiceUtils.getShippingCharges(true);
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
            System.out.println("\nOption 1 -> (Item Table, Payment Term, Customer) Modifications");
            System.out.println("Option 2 -> (Discount, Shipping Charges, status) Modifications");
            System.out.println("Option 3 -> Exit the Update Module");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
                    modifyInvoicePrimaryDetails(invoice);
                    break;
                case 2:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
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
            System.out.println("\nOption 1 -> Update Item Table");
            System.out.println("Option 2 -> Update Payment Term");
            System.out.println("Option 3 -> Update Customer");
            System.out.println("Option 4 -> Exit the Invoice Primary Details Modification Module");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
                    Map<Item, Integer> previousItemTable = invoice.getItemTable();

                    itemTableOperations(invoice, invoice.getItemTable());

                    System.out.println("\nPrevious Item Table\n");
                    showItemDetails(previousItemTable);

                    System.out.println("\nUpdate Item Table\n");
                    showItemDetails(invoice.getItemTable());

                    break;
                case 2:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
                    int previousPaymentTerm = invoice.getPaymentTerm();

                    int paymentTerm = invoiceUtils.getPaymentTermInput();

                    invoice.setPaymentTerm(paymentTerm);

                    System.out.println("\nInvoice's Payment Term updated from " + previousPaymentTerm + " to " + paymentTerm );
                    break;
                case 3:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
                    String previousCustomerName = invoice.getCustomer().getName();

                    Utils.showCustomers(customers);

                    int cusNo = invoiceUtils.getCustomerInput(customers.firstKey(), customers.lastKey());

                    invoice.setCustomer(customers.get(cusNo));

                    System.out.println("\nInvoice's Customer updated from " + previousCustomerName + " to " + customers.get(cusNo).getName() );

                    break;
                case 4:
                    System.out.println("\nExiting the Invoice modification module");

                    isUpdating = false;

                    break;
                default:
                    System.out.println("\nEnter a Valid input (1 - 4)");
                    break;
            }
        }
    }

    private static void modifyInvoiceOtherCharges(Invoice invoice) {
        boolean isUpdating = true;
        while (isUpdating) {
            System.out.println("\nOption 1 -> Update Discount");
            System.out.println("Option 2 -> Update Shipping Charges");
            System.out.println("Option 3 -> Update Status");
            System.out.println("Option 4 -> Exit the Invoice Other Charges Module");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
                    double previousDiscount = invoice.getDiscount();

                    double nDiscount = invoiceUtils.getDiscountInput(false);

                    invoice.setDiscount(nDiscount);

                    invoice.setTotal(invoice.getTotal() + ((invoice.getSubTotal() / 100) * previousDiscount) - ((invoice.getSubTotal() / 100) * nDiscount));

                    System.out.println("\nInvoice's Discount updated from " + previousDiscount + " to " + nDiscount );

                    break;
                case 2:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }

                    double previousShippingCharges = invoice.getShippingCharges();

                    double shippingCharges = invoiceUtils.getShippingCharges(false);

                    invoice.setShippingCharges(shippingCharges);

                    invoice.setTotal(invoice.getTotal() - previousShippingCharges + shippingCharges);

                    System.out.println("\nInvoice's Shipping Charges updated from " + previousShippingCharges + " to " + shippingCharges );

                    break;
                case 3:
                    if (invoiceUtils.neglectWarning(invoice, "update")) {
                        return;
                    }
                    String previousStatus = invoice.getStatus();

                    System.out.println("\nEnter the Status No:\n");
                    System.out.println(Utils.showStatuses());

                    int status = 0;
                    status = (int) Utils.handleIntegerInputMisMatches(status, scanner);

                    status = Utils.getValidInput(status, 1, Utils.invoiceStatus.size(), scanner, "Invoice Status") - 1;

                    invoice.setStatus(status);

                    System.out.println("\nInvoice's Status updated from " + previousStatus + " to " + invoice.getStatus() );

                    break;
                case 4:
                    System.out.println("\nExiting the Invoice modification module");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("\nEnter a Valid input (1 - 4)");
                    break;
            }
        }
    }

    private static void itemTableOperations (Invoice invoice, Map<Item, Integer> itemTable) {

        boolean isPurchasing = true;
        while (isPurchasing) {

            System.out.println("\nOption 1 -> Add Items to the list");
            if (!itemTable.isEmpty()) {
                System.out.println("Option 2 -> Remove Items from the list");
            } else {
                System.out.println("Option 2 is disabled, No items added yet...");
            }
            System.out.println("Option 3 -> Quit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    if (items.isEmpty()) {
                        if (Utils.redirectVerification("Items", scanner)) {
                            Item item = Item.create();

                            items.put(item.getItemNo(), item);
                        } else {
                            break;
                        }
                    }

                    Utils.showItems(items);

                    System.out.println("\nEnter the Item No from the table: ");

                    int itemNo = 0;
                    itemNo = (int) Utils.handleIntegerInputMisMatches(itemNo, scanner);

                    itemNo = Utils.getValidInput(itemNo, items.firstKey(), items.lastKey(), scanner, "Item Number:");


                    System.out.println("\nEnter the quantity: ");

                    int quantity = 0;
                    quantity = (int) Utils.handleIntegerInputMisMatches(quantity, scanner);

                    quantity = Utils.getValidInput(quantity, 1, 100, scanner, items.get(itemNo).getItemName() + "'s Quantity: ");

                    itemTable.put(items.get(itemNo), itemTable.getOrDefault(items.get(itemNo), 0) + quantity);
                    break;
                case 2:
                    if (!itemTable.isEmpty()) {

                        showItemDetails(itemTable);

                        System.out.println("\nEnter the Item No from the Table: ");

                        int removalItemNo = 0;
                        removalItemNo = (int) Utils.handleIntegerInputMisMatches(removalItemNo, scanner);

                        removalItemNo = Utils.getValidInput(removalItemNo, 1, itemTable.size(), scanner, "Item Number:");

                        Item item = items.get(removalItemNo);

                        System.out.println("\nOption 1 -> Remove the Entire Item");
                        System.out.println("Option 2 -> Remove Partial Items");
                        System.out.println("Option 3 -> Quit");

                        int removeOption = 0;
                        removeOption = (int) Utils.handleIntegerInputMisMatches(removeOption, scanner);

                        removeOption = Utils.getValidInput(removeOption, 1, 3, scanner, "Item Remove Option");

                        if (removeOption == 1) {

                            itemTable.remove(item);

                        } else if (removeOption == 2) {
                            System.out.println("\nItem Quantity: " + itemTable.get(item));

                            System.out.println("\nEnter the removal Quantity less the above mentioned Quantity: ");

                            int removalQuantity = 0;
                            removalQuantity = (int) Utils.handleIntegerInputMisMatches(removalQuantity, scanner);

                            while (removalQuantity > itemTable.get(item
                            )) {
                                System.out.println("\nItem Quantity: " + itemTable.get(item));

                                System.out.println("\nEnter the appropriate quantity,  that should less than the actual quantity");

                                removalQuantity = (int) Utils.handleIntegerInputMisMatches(removalQuantity, scanner);

                                if (removalQuantity < 0) {
                                    removalQuantity *= -1;
                                }
                            }

                            if (removalQuantity < 0) {
                                removalQuantity *= -1;
                            }

                            itemTable.put(item, itemTable.get(item) - removalQuantity);

                        } else {
                            System.out.println("\nItem Table Updation");
                            break;
                        }
                    } else {
                        System.out.println("\nNo items added yet");
                    }
                    break;
                case 3:
                    isPurchasing = false;
                    System.out.println("\nExiting the purchasing...");
                    break;
                default:
                    System.out.println("\nEnter a valid option (1 - 3)");
                    break;
            }
        }

        invoice.setItemTable(itemTable);

        double subTotal = 0;

        boolean withinState = invoice.getCustomer().getAddress().getState().equalsIgnoreCase("Tamil Nadu");

        for (Item cartItem : itemTable.keySet()) {
            double cost = cartItem.getRate(itemTable.get(cartItem), withinState);
            subTotal += cost;
        }

        invoice.setSubTotal(subTotal);
    }

    private static TreeMap<Integer, Invoice> searchByCustomer (String customerName) {
        TreeMap<Integer, Invoice> invoicesOfCustomer = new TreeMap<>();
        for (Integer invNo : invoices.keySet()) {
            Invoice inv = invoices.get(invNo);
            if (inv.getCustomer().getName().equalsIgnoreCase(customerName)) {
                invoicesOfCustomer.put(invNo, inv);
            }
        }
        return invoicesOfCustomer;
    }

    private static void showItemDetails (Map<Item, Integer> itemTable) {
        System.out.println("-".repeat(45));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Item Quantity");
        System.out.println("-".repeat(45));

        for (Item cartItem : itemTable.keySet()) {
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    cartItem.getItemNo(),
                    cartItem.getItemName(),
                    itemTable.get(cartItem));
        }

        System.out.println("-".repeat(45));
    }

}