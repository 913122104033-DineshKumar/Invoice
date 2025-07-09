import invoice.handlers.CustomerHandler;
import invoice.handlers.InvoiceHandler;
import invoice.handlers.ItemHandler;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;
import invoice.utils.*;

import java.util.*;

public class Main {

    static List<Customer> customers = new ArrayList<>();
    static List<Item> items = new ArrayList<>();
    static List<Invoice> invoices = new ArrayList<>();

    public static void initialize(List<Customer> cus, List<Item> itm, List<Invoice> inv) {
        customers.addAll(cus);
        items.addAll(itm);
        invoices.addAll(inv);
    }

    public static void main(String[] args) {
        InputUtils.createScanner();

        int option = -1;

        mainMenu:
        {
            while (true) {

                System.out.println("\nOption 1 -> Item Module");
                System.out.println("Option 2 -> Customer Module");
                System.out.println("Option 3 ->  Invoice Module");
                System.out.println("Option 4 -> Quit");

                System.out.println("-".repeat(20));

                System.out.println("\nEnter the option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);


                switch (option) {

                    case 1: {
                        System.out.println("\nEntering into Item Module...");
                        itemModule(items);
                        break;
                    }


                    case 2: {
                        System.out.println("\nEntering into Customer Module...");

                        customerModule(customers);

                        break;
                    }

                    case 3: {
                        System.out.println("\nEntering into Invoice Module...");
                        invoiceModule(items, customers, invoices);

                        break;
                    }

                    case 4: {
                        System.out.println("\nExiting the Invoice App...");
                        break mainMenu;
                    }

                    default: {
                        System.out.println("\nEnter a valid input (1 - 4)");

                        break;
                    }
                }

            }
        }

        InputUtils.destroyScanner();
    }

    private static void itemModule(List<Item> items){
        int option = -1;

        ItemHandler itemHandler = new ItemHandler();

        itemMenu:
        {
            while (true) {
                System.out.println("\nOption 1 -> Adding Item");
                System.out.println("Option 2 -> Updating Item");
                System.out.println("Option 3 -> Searching Item");
                System.out.println("Option 4 -> Deleting Item");
                System.out.println("Option 5 -> Sort Items");
                System.out.println("Option 6 -> Exit");

                System.out.println("\nEnter the Option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option) {
                    case 1: {
                        Item item = itemHandler.create(items);
                        items.add(item);

                        System.out.println("\n");
                        item.showItem();
                        break;
                    }

                    case 2: {
                        if (items.isEmpty()) {
                            System.out.println("\nThere is no Items available to Update");
                            break;
                        }

                        itemHandler.update(items);

                        break;
                    }

                    case 3: {
                        if (items.isEmpty()) {
                            System.out.println("\nThere are no Items to search");
                            break;
                        }

                        itemHandler.searchByName(items);

                        break;
                    }

                    case 4: {
                        if (items.isEmpty()) {
                            System.out.println("\nThere is no items to delete");
                            break;
                        }

                        itemHandler.deleteItem(items);

                        break;
                    }

                    case 5: {

                        if (items.size() < 2) {
                            System.out.println("\nThere is only one items (or) Zero items to sort");
                            break;
                        }

                        itemHandler.sortingModule(items);

                        break;
                    }

                    case 6: {
                        System.out.println("\nExiting the Item Module...");
                        break itemMenu;
                    }

                    default: {
                        System.out.println("\nEnter Valid Option (1 - 6)");
                        break;
                    }
                }
            }
        }
    }

    private static void customerModule(List<Customer> customers) {
        int option = -1;

        CustomerHandler customerHandler = new CustomerHandler();

        customerMenu:
        {
            while (true) {
                System.out.println("\nOption 1 -> Adding Customer");
                System.out.println("Option 2 -> Updating Customer");
                System.out.println("Option 3 -> Searching Customer");
                System.out.println("Option 4 -> Deleting Customer");
                System.out.println("Option 5 -> Sort the Customers");
                System.out.println("Option 6 -> Exit");

                System.out.println("\nEnter the Option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option) {
                    case 1: {
                        Customer customer = customerHandler.create(customers);
                        customers.add(customer);

                        System.out.println("\n");

                        customer.showCustomer();
                        break;
                    }

                    case 2:
                    {
                        customerHandler.update(customers);

                        break;
                    }

                    case 3: {
                        if (customers.isEmpty())
                        {
                            System.out.println("\nThere are no Customers to search");
                            break;
                        }

                        customerHandler.searchByName(customers);

                        break;
                    }

                    case 4:
                    {
                        if (customers.isEmpty())
                        {
                            System.out.println("\nThere are no Customers to delete");
                            break;
                        }
                        customerHandler.deleteCustomer(customers);

                        break;
                    }

                    case 5:
                    {

                        if (customers.size() < 2)
                        {
                            System.out.println("\nThere is only one customer (or) Zero customers to sort");
                            break;
                        }

                        customerHandler.sortingModule(customers);

                        break;
                    }

                    case 6:
                    {
                        System.out.println("\nExiting the Customer Module...");
                        break customerMenu;
                    }

                    default: {
                        System.out.println("\nEnter Valid Option (1 - 6)");
                        break;
                    }
                }
            }
        }
    }

    private static void invoiceModule(List<Item> items, List<Customer> customers, List<Invoice> invoices) {
        InvoiceHandler invoiceHandler = new InvoiceHandler();

        InvoiceUtil invoiceUtils = new InvoiceUtil();

        int option = -1;

        invoiceMenu:
        {
            while (true) {
                System.out.println("\nOption 1 -> Adding Invoice");
                System.out.println("Option 2 -> Updating Invoice");
                System.out.println("Option 3 -> Searching Invoice");
                System.out.println("Option 4 -> Deleting Invoice");
                System.out.println("Option 5 -> Pay for the Invoices");
                System.out.println("Option 6 -> Set Status for Invoice");
                System.out.println("Option 7 -> Sort the Invoices");
                System.out.println("Option 8 -> Exit");

                System.out.println("\nEnter the Option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option) {
                    case 1: {
                        Invoice invoice = invoiceHandler.create(items, customers);

                        if (invoice == null) {
                            System.out.println("\nCan't Create Invoice, due to no Customers (Or) no Items...");
                            break;
                        }

                        invoices.add(invoice);

                        System.out.println("\n");
                        invoice.showInvoice();
                        break;
                    }

                    case 2: {
                        if (checkIsThereInvoices(invoices, "update")) {
                            break;
                        }

                        int invNo = InputUtils.getInvoiceNumber(invoices);

                        Invoice updatedInvoice = invoices.get(invNo);

                        invoiceHandler.update(updatedInvoice, items, customers);

                        System.out.println();
                        updatedInvoice.showInvoice();

                        break;
                    }

                    case 3: {
                        if (checkIsThereInvoices(invoices, "search")) {
                            break;
                        }

                        String customerName = new CustomerUtil().getNameInput();

                        List<Invoice> invoicesForCustomer = invoiceHandler.searchByCustomer(customerName, invoices, -0.1);

                        if (invoicesForCustomer.isEmpty()) {
                            System.out.println("\nNo Such Customer (Or) No Invoices for this Customer ");
                            break;
                        }

                        InputUtils.showInvoices(invoicesForCustomer);
                        break;
                    }

                    case 4: {
                        if (checkIsThereInvoices(invoices, "delete")) {
                            break;
                        }

                        int deleteInvoiceNo = InputUtils.getInvoiceNumber(invoices);

                        Invoice selectedInvoice = invoices.get(deleteInvoiceNo);

                        if (invoiceUtils.showWarning(invoices.get(deleteInvoiceNo), "delete")) {
                            break;
                        }

                        System.out.println("Invoice of the " + selectedInvoice.getCustomer().getName() + " with Inv No " + selectedInvoice.getInvNo() + " is deleted");

                        invoices.remove(deleteInvoiceNo);

                        break;
                    }

                    case 5: {
                        if (checkIsThereInvoices(invoices, "pay")) {
                            break;
                        }

                        invoiceHandler.paymentModule(invoices, customers);

                        break;
                    }

                    case 6: {
                        if (checkIsThereInvoices(invoices, "status")) {
                            break;
                        }

                        int invNo = InputUtils.getInvoiceNumber(invoices);

                        Invoice invoice = invoices.get(invNo);

                        System.out.println("\nThe invoice you chosen\n");

                        invoice.showInvoice();

                        int previousStatusOrdinal = invoice.getStatus().ordinal();

                        int statusOrdinal;


                        // To get a proper Status Code
                        status:
                        while (true) {
                            statusOrdinal = InputUtils.getValidRange(1, 4, "Invoice Status", "Enter the Status No:\n" + InputUtils.showStatuses()) - 1;

                            if (previousStatusOrdinal == statusOrdinal) {
                                System.out.println("\nThis invoice is already " + switch (statusOrdinal) {
                                    case 0 -> InvoiceHandler.Status.DRAFT;
                                    case 1 -> InvoiceHandler.Status.SENT;
                                    case 2 -> InvoiceHandler.Status.PARTIALLY_PAID;
                                    default -> InvoiceHandler.Status.CLOSED;
                                });
                                continue status;
                            }
                            break;
                        }

                        // Demotion of the statuses
                        if (previousStatusOrdinal > statusOrdinal) {

                            if (statusOrdinal > InvoiceHandler.Status.SENT.ordinal())
                            {
                                char confirmationOption = InputUtils.getToggleInput(   'y',"Status Demotion", "You are trying to demote the Status" + "\nDo you want to continue this process, then if you had paid the amount for this invoice will be refunded and the due will increase by " + (invoice.getTotal() - invoice.getDueAmount()) + " (y -> yes, any other key -> no)");

                                if (confirmationOption == 'y')
                                {
                                    System.out.println("\nPaid amount " + (invoice.getTotal() - invoice.getDueAmount()) + " is refunded");

                                    invoice.setDueAmount(invoice.getTotal());

                                    invoice.setStatus(InvoiceHandler.Status.SENT);

                                    System.out.println("\nStatus is changed from " + InvoiceHandler.Status.values()[previousStatusOrdinal] + " to " + InvoiceHandler.Status.SENT);
                                } else
                                {
                                    System.out.println("\nStatus is not updated");
                                }
                            } else
                            {
                                char confirmationOption = InputUtils.getToggleInput(  'y',"Status Demotion", "Do you want the Status from SENT to DRAFT (y -> yes, any other key -> no)");

                                if (confirmationOption == 'y')
                                {
                                    System.out.println("\nStatus changed from SENT to DRAFT\n");

                                    invoice.setStatus(InvoiceHandler.Status.DRAFT);
                                }
                            }
                            break;
                        }

                        // Actual status Changing
                        if (statusOrdinal == InvoiceHandler.Status.SENT.ordinal())
                        {
                            invoice.setStatus(InvoiceHandler.Status.SENT);

                            System.out.println("\nINVOICE #" + invoice.getInvNo() + " Status updated from " + InvoiceHandler.Status.values()[previousStatusOrdinal] + " to " + invoice.getStatus());
                            break;
                        }

                        invoiceHandler.payInvoice(invoice);

                        break;
                    }

                    case 7: {

                        if (invoices.size() < 2) {
                            System.out.println("\nThere is only one invoice (or) Zero invoices to sort");
                            break;
                        }

                        invoiceHandler.sortingModule(invoices);

                        break;
                    }

                    case 8: {
                        System.out.println("\nExiting the Invoice Module...");
                        break invoiceMenu;
                    }

                    default: {
                        System.out.println("\nEnter Valid Option (1 - 8)");
                        break;
                    }
                }
            }
        }
    }

    private static boolean checkIsThereInvoices(List<Invoice> invoices, String module) {
        if (invoices.isEmpty()) {
            System.out.println("\nThere is no invoices to " + module);
            return true;
        }
        return false;
    }

    private static void updateInvoiceWithPaymentValidation(boolean hasPaymentProgressed, Invoice invoice, int targetStatus, boolean isClosingAttempt) {

    }

}