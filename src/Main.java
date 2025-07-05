import invoice.GlobalConstants;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;
import invoice.utils.CustomerUtil;
import invoice.utils.InvoiceUtil;
import invoice.utils.ItemUtil;
import invoice.utils.Utils;

import java.time.LocalDate;
import java.util.*;

public class Main
{

    public static void main(String[] args)
    {
        GlobalConstants.initialize();
        Scanner scanner = new Scanner(System.in);
        InvoiceUtil invoiceUtils = new InvoiceUtil(scanner);
        List<Customer> customers = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();
        Set<String> customerEmails = new HashSet<>();
        while (true)
        {
            System.out.println("\nOption 1 -> Item Module");
            System.out.println("Option 2 -> Customer Module");
            System.out.println("Option 3 ->  Invoice Module");
            System.out.println("Option 4 -> Quit");
            System.out.println("-".repeat(20));

            System.out.println("\nEnter the option: ");
            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 4)
            {
                System.out.println("\nExiting the app...");
                break;
            }

            switch (option)
            {

                case 1:
                {
                    System.out.println("\nEntering into Item Module...");
                    itemModule(items, scanner);

                    break;
                }

                case 2:
                {
                    System.out.println("\nEntering into Customer Module...");
                    customerModule(customers, customerEmails, scanner);

                    break;
                }

                case 3:
                {
                    System.out.println("\nEntering into Invoice Module...");
                    invoiceModule(items, customers, invoices, customerEmails, invoiceUtils, scanner);

                    break;
                }

                default:
                {
                    System.out.println("\nEnter a valid input (1 - 4)");

                    break;
                }
            }
        }
        scanner.close();
    }

    private static void itemModule(List<Item> items, Scanner scanner) {
        while (true) {
            System.out.println("\nOption 1 -> Adding Item");
            System.out.println("Option 2 -> Updating Item");
            System.out.println("Option 3 -> Searching Item");
            System.out.println("Option 4 -> Deleting Item");
            System.out.println("Option 5 -> Exit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 5)
            {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option)
            {
                case 1:
                {
                    Item item = Item.create(scanner);
                    items.add(item);

                    ItemUtil.reArrangeItemList(items);

                    System.out.println("\n");
                    item.showItem();
                    break;
                }

                case 2:
                {
                    if (items.isEmpty())
                    {
                        System.out.println("\nThere is no Items available to Update");
                        break;
                    }

                    Utils.showItems(items);

                    int itemNo = Utils.getValidRange( 1, items.size(), scanner, "Item Number", "Enter the Item No from the table: ") - 1;

                    Item updatedItem = items.get(itemNo);
                    updatedItem.update(scanner);

                    System.out.println("\n");
                    updatedItem.showItem();
                    break;
                }

                case 3:
                {
                    if (items.isEmpty())
                    {
                        System.out.println("\nThere are no Items to search");
                        break;
                    }

                    String itemName = Utils.getValidStringInput( GlobalConstants.NAME_REGEX, scanner, "Punam Saree", "Item Name", "Enter Item Name (Eg. Punam Saree):");

                    Item itemFound = Item.search(itemName, items);

                    if (itemFound != null)
                    {
                        System.out.println("\n");
                        itemFound.showItem();
                    } else
                    {
                        System.out.println("\nItem not found");
                    }
                    break;
                }

                case 4:
                {
                    if (items.isEmpty())
                    {
                        System.out.println("\nThere is no items to delete");
                        break;
                    }

                    Utils.showItems(items);

                    int deleteItemNo = Utils.getValidRange( 1, items.size(), scanner, "Item Number",
                            "Enter the Item Number from the table:") - 1;

                    System.out.println("\n" + items.get(deleteItemNo).getItemName() + " is deleted successfully...");
                    items.remove(deleteItemNo);

                    ItemUtil.reArrangeItemList(items);

                    break;
                }

                default:
                {
                    System.out.println("\nEnter Valid Option (1 - 5)");
                    break;
                }
            }
        }
    }

    private static void customerModule(List<Customer> customers, Set<String> customerEmails, Scanner scanner)
    {
        while (true)
        {
            System.out.println("\nOption 1 -> Adding Customer");
            System.out.println("Option 2 -> Updating Customer");
            System.out.println("Option 3 -> Searching Customer");
            System.out.println("Option 4 -> Deleting Customer");
            System.out.println("Option 5 -> Exit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 5) {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option)
            {
                case 1:
                {
                    Customer customer = Customer.create(customerEmails, scanner);
                    customers.add(customer);

                    CustomerUtil.reArrangeCustomerList(customers);

                    System.out.println("\n");

                    customer.showCustomer();
                    break;
                }

                case 2:
                {
                    if (customers.isEmpty())
                    {
                        System.out.println("\nThere is no Customers available to Update");
                        break;
                    }

                    Utils.showCustomers(customers);

                    System.out.println("\nEnter the Customer Number: ");
                    int customerNo = 0;
                    customerNo = Utils.handleIntegerInputMisMatches(customerNo, 0, scanner);

                    customerNo = Utils.getValidRange(1, customers.size(), scanner, "Customer Number", "Enter the Customer Number from the table:") - 1;

                    Customer updatedCustomer = customers.get(customerNo);
                    updatedCustomer.update(customerEmails, scanner);

                    System.out.println("\n");
                    updatedCustomer.showCustomer();
                    break;
                }

                case 3:
                {
                    if (customers.isEmpty())
                    {
                        System.out.println("\nThere are no Customers to search");
                        break;
                    }

                    String customerName = Utils.getValidStringInput( GlobalConstants.NAME_REGEX, scanner, "Dinesh Kumar K K", "Customer Name", "Enter the Customer Name");

                    Customer customerFound = Customer.searchByName(customerName, customers);

                    if (customerFound != null)
                    {
                        System.out.println("\n");
                        customerFound.showCustomer();
                    } else
                    {
                        System.out.println("Customer not found");
                    }
                    break;
                }

                case 4:
                {
                    if (customers.isEmpty())
                    {
                        System.out.println("\nThere are no Customers to delete");
                        break;
                    }

                    Utils.showCustomers(customers);

                    System.out.println("\nEnter the Customer No from the table: ");

                    int deleteCustomerNo = 0;
                    deleteCustomerNo = Utils.handleIntegerInputMisMatches(deleteCustomerNo, 0, scanner);

                    deleteCustomerNo = Utils.getValidRange( 1, customers.size(), scanner, "Customer Number", "Enter the Customer No from the table:") - 1;

                    customerEmails.remove(customers.get(deleteCustomerNo).getEmail());

                    System.out.println("\n" + customers.get(deleteCustomerNo).getName() + " " + "is deleted successfully...");

                    customers.remove(deleteCustomerNo);

                    CustomerUtil.reArrangeCustomerList(customers);

                    break;
                }

                default:
                {
                    System.out.println("\nEnter Valid Option (1 - 5)");
                    break;
                }
            }
        }
    }

    private static void invoiceModule(List<Item> items, List<Customer> customers, List<Invoice> invoices, Set<String> customerEmails, InvoiceUtil invoiceUtils, Scanner scanner)
    {
        while (true)
        {
            System.out.println("\nOption 1 -> Adding Invoice");
            System.out.println("Option 2 -> Updating Invoice");
            System.out.println("Option 3 -> Searching Invoice");
            System.out.println("Option 4 -> Deleting Invoice");
            System.out.println("Option 5 -> Pay for the Invoices");
            System.out.println("Option 6 -> Exit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 7)
            {
                System.out.println("\nExiting the Invoice Module...");
                break;
            }

            switch (option)
            {
                case 1:
                {
                    Invoice invoice = create(items, customers, customerEmails, scanner, invoiceUtils);

                    if (invoice == null)
                    {
                        System.out.println("\nCan't Create Invoice, due to no Customers...");
                        break;
                    }

                    invoices.add(invoice);

                    invoiceUtils.reArrangeInvoiceList(invoices);

                    System.out.println("\n");
                    invoice.showInvoice();
                    break;
                }

                case 2:
                {
                    if (checkIsThereInvoices(invoices, "update"))
                    {
                        break;
                    }

                    Utils.showInvoices(invoices);

                    int invNo = Utils.getValidRange( 1, invoices.size(), scanner, "Invoice Number", "Enter the Invoice number from the table:") - 1;

                    Invoice updatedInvoice = invoices.get(invNo);

                    update(updatedInvoice, items, customers, scanner, invoiceUtils);

                    System.out.println();
                    updatedInvoice.showInvoice();

                    break;
                }

                case 3:
                {
                    if (checkIsThereInvoices(invoices, "search"))
                    {
                        break;
                    }

                    String customerName = new CustomerUtil(scanner).getNameInput();

                    List<Invoice> invoicesForCustomer = searchByCustomer(customerName, invoices, -0.1);

                    if (invoicesForCustomer.isEmpty())
                    {
                        System.out.println("\nNo Such Customer (Or) No Invoices for this Customer ");
                        break;
                    }

                    Utils.showInvoices(invoicesForCustomer);
                    break;
                }

                case 4:
                {
                    if (checkIsThereInvoices(invoices, "delete"))
                    {
                        break;
                    }

                    Utils.showInvoices(invoices);

                    int deleteInvoiceNo = Utils.getValidRange(1, invoices.size(), scanner, "Invoice Number", "Enter the Invoice No from the table:") - 1;

                    if (invoiceUtils.neglectWarning(invoices.get(deleteInvoiceNo), "delete"))
                    {
                        break;
                    }

                    System.out.println("Invoice of the " + invoices.get(deleteInvoiceNo).getCustomer().getName() + " with Inv No " + invoices.get(deleteInvoiceNo).getInvNo() + " is deleted");

                    invoices.remove(deleteInvoiceNo);

                    invoiceUtils.reArrangeInvoiceList(invoices);

                    break;
                }

                case 5:
                {
                    if (checkIsThereInvoices(invoices, "pay"))
                    {
                        break;
                    }

                    while (true)
                    {
                        System.out.println("\nEntered into payment module");

                        System.out.println("\nOption 1 -> Pay for Particular Invoice");
                        System.out.println("\nOption 2 -> Pay for Due Amount remaining for the particular Customer");

                        System.out.println("\nHow you want to pay: ");

                        int optionForPayment = -1;
                        optionForPayment = Utils.handleIntegerInputMisMatches(optionForPayment, -1, scanner);

                        if (optionForPayment == 1)
                        {
                            Utils.showInvoices(invoices);

                            int invNo = invoiceUtils.getSerialNumberInput(1, invoices.size(), "Invoice") - 1;

                            Invoice paymentInvoice = invoices.get(invNo);

                            payForParticularInvoice(invoiceUtils, paymentInvoice);

                            break;
                        } else if (optionForPayment == 2)
                        {
                            Utils.showCustomers(customers);

                            int cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer") - 1;

                            List<Invoice> customerInvoices = searchByCustomer(customers.get(cusNo).getName(), invoices, 0);

                            payingDueRemainingForCustomer(invoiceUtils, customerInvoices);

                            break;
                        } else
                        {
                            System.out.println("\nEnter a valid input (1 - 2): ");
                        }
                    }

                    break;
                }

                case 6:
                {
                    if (checkIsThereInvoices(invoices, "status"))
                    {
                        break;
                    }

                    Utils.showInvoices(invoices);

                    System.out.println("\nEnter the Invoice Number from the table: ");

                    int invNo = invoiceUtils.getSerialNumberInput(1, invoices.size(), "Invoice Number") - 1;

                    Invoice invoice = invoices.get(invNo);

                    String previousStatus = invoice.getStatus();

                    int status = Utils.getValidRange( 1, GlobalConstants.INVOICE_STATUS.size(), scanner, "Invoice Status", "Enter the Status No:\n" + Utils.showStatuses()) - 1;

                    invoice.setStatus(status);

                    System.out.println("\nInvoice's Status updated from " + previousStatus + " to " + invoice.getStatus());
                }

                default:
                {
                    System.out.println("\nEnter Valid Option (1 - 7)");
                    break;
                }
            }
        }
    }

    private static Invoice create (List<Item> items, List<Customer> customers, Set<String> customerEmails, Scanner scanner, InvoiceUtil invoiceUtils) {
        System.out.println("\nYou can add Invoice now...");

        boolean isAvailableCustomer = customers.isEmpty();

        int cusNo = 0;

        if (customers.isEmpty())
        {
            if (redirectVerification("Customers", scanner))
            {
                Customer customer = Customer.create(customerEmails, scanner);

                customers.add(customer);

                CustomerUtil.reArrangeCustomerList(customers);

                cusNo = customers.size() - 1;
            } else
            {
                return null;
            }
        }

        if (isAvailableCustomer)
        {
            System.out.println("\nCustomer has been created and now can create Invoice\n");
        } else
        {
            Utils.showCustomers(customers);

            cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer") - 1;
        }

        Customer cus = customers.get(cusNo);

        LocalDate date = LocalDate.now();

        int paymentTerm = invoiceUtils.getPaymentTermInput();

        Map<Item, Integer> itemTable = new TreeMap<>((item1, item2) -> Integer.compare(item1.getItemNo(), item2.getItemNo()));
        double subTotal = itemTableOperations(cus, null, items, itemTable, scanner, false);

        double discount = invoiceUtils.getDiscountInput(true);

        double invTotal = subTotal - ((subTotal / 100) * discount);

        double shippingCharges = invoiceUtils.getShippingCharges(true);

        invTotal += shippingCharges;

        double paymentReceived = invoiceUtils.haveReceivedPayment(invTotal);

        int status = -1;

        if (paymentReceived == invTotal)
        {
            status = 3; // CLOSED
        } else if (paymentReceived > 0)
        {
            status = 2; // PARTIALLY PAID
        } else
        {
            char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Invoice Sent", "Have you sent the invoice to customer?\nY -> Yes\nN -> No");

            if (yesOrNo == 'Y' || yesOrNo == 'y')
            {
                status = 1;
            } else
            {
                status = 0;
            }

        }

        return new Invoice(cus, date, paymentTerm, itemTable, subTotal, discount, shippingCharges, invTotal, invTotal - paymentReceived, status);
    }

    private static void update(Invoice invoice, List<Item> items, List<Customer> customers, Scanner scanner, InvoiceUtil invoiceUtils) {

        if (invoiceUtils.neglectWarning(invoice, "update"))
        {
            return;
        }

        while (true)
        {
            System.out.println("\nOption 1 -> (Item Table, Payment Term, Customer) Modifications");
            System.out.println("Option 2 -> (Discount, Shipping Charges, status) Modifications");
            System.out.println("Option 3 -> Exit the Update Module");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 3)
            {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option)
            {
                case 1:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    modifyInvoicePrimaryDetails(invoice, items, customers, invoiceUtils, scanner);
                    break;
                }

                case 2:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    modifyInvoiceOtherCharges(invoice, invoiceUtils, scanner);
                    break;
                }

                default:
                {
                    System.out.println("Enter a Valid input (1 - 3)");
                    break;
                }
            }
        }
    }

    private static void modifyInvoicePrimaryDetails(Invoice invoice, List<Item> items, List<Customer> customers, InvoiceUtil invoiceUtils, Scanner scanner) {
        while (true)
        {
            System.out.println("\nOption 1 -> Update Item Table");
            System.out.println("Option 2 -> Update Payment Term");
            System.out.println("Option 3 -> Update Customer");
            System.out.println("Option 4 -> Exit the Invoice Primary Details Modification Module");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 4)
            {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option)
            {
                case 1:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    Map<Item, Integer> previousItemTable = invoice.getItemTable();

                    Map<Item, Integer> currentItemTable = invoice.getItemTable();

                    double subTotal = itemTableOperations(invoice.getCustomer(), invoice, items, currentItemTable, scanner, true);

                    System.out.println("\nPrevious Item Table\n");
                    showItemDetails(previousItemTable);

                    System.out.println("\nUpdate Item Table\n");
                    showItemDetails(currentItemTable);

                    invoice.setItemTable(currentItemTable);
                    invoice.setSubTotal(subTotal);

                    break;
                }

                case 2:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    int previousPaymentTerm = invoice.getPaymentTerm();

                    int paymentTerm = invoiceUtils.getPaymentTermInput();

                    invoice.setPaymentTerm(paymentTerm);

                    System.out.println("\nInvoice's Payment Term updated from " + previousPaymentTerm + " to " + paymentTerm);
                    break;
                }

                case 3:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    String previousCustomerName = invoice.getCustomer().getName();

                    Utils.showCustomers(customers);

                    int cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer") - 1;

                    invoice.setCustomer(customers.get(cusNo));

                    System.out.println("\nInvoice's Customer updated from " + previousCustomerName + " to " + customers.get(cusNo).getName());

                    break;
                }

                default:
                {
                    System.out.println("\nEnter a Valid input (1 - 4)");
                    break;
                }
            }
        }
    }

    private static void modifyInvoiceOtherCharges(Invoice invoice, InvoiceUtil invoiceUtils, Scanner scanner)
    {
        while (true)
        {
            System.out.println("\nOption 1 -> Update Discount");
            System.out.println("Option 2 -> Update Shipping Charges");
            System.out.println("Option 3 -> Update Status");
            System.out.println("Option 4 -> Exit the Invoice Other Charges Module");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 4)
            {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option)
            {
                case 1:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }

                    double previousDiscount = invoice.getDiscount();

                    double nDiscount = invoiceUtils.getDiscountInput(false);

                    invoice.setDiscount(nDiscount);

                    invoice.setTotal(invoice.getSubTotal() - ((invoice.getSubTotal() / 100) * nDiscount) + invoice.getShippingCharges());

                    System.out.println("\nInvoice's Discount updated from " + previousDiscount + " to " + nDiscount);

                    break;
                }

                case 2:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }

                    double previousShippingCharges = invoice.getShippingCharges();

                    double shippingCharges = invoiceUtils.getShippingCharges(false);

                    invoice.setShippingCharges(shippingCharges);

                    invoice.setTotal(invoice.getTotal() - previousShippingCharges + shippingCharges);

                    System.out.println("\nInvoice's Shipping Charges updated from " + previousShippingCharges + " to " + shippingCharges);

                    break;
                }

                case 3:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    String previousStatus = invoice.getStatus();

                    int status = Utils.getValidRange( 1, GlobalConstants.INVOICE_STATUS.size(), scanner, "Invoice Status", "Enter the Status No:\n" + Utils.showStatuses()) - 1;

                    invoice.setStatus(status);

                    System.out.println("\nInvoice's Status updated from " + previousStatus + " to " + invoice.getStatus());

                    break;
                }

                default:
                {
                    System.out.println("\nEnter a Valid input (1 - 4)");
                    break;
                }
            }
        }
    }

    private static double itemTableOperations (Customer customer, Invoice invoice, List<Item> items, Map<Item, Integer> itemTable, Scanner scanner, boolean isUpdation)
    {
        while (true)
        {

            System.out.println("\nOption 1 -> Add Items to the list");
            if (!itemTable.isEmpty())
            {
                System.out.println("Option 2 -> Remove Items from the list");
            }
            else
            {
                System.out.println("Option 2 is disabled, No items added yet...");
            }
            System.out.println("Option 3 -> Quit");

            System.out.println("\nEnter the Option: ");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 3)
            {
                System.out.println("\nExiting the purchasing...");
                break;
            }

            switch (option)
            {

                case 1:
                {
                    int itemNo = 0;

                    boolean isItemListEmpty = items.isEmpty();

                    if (items.isEmpty())
                    {
                        if (redirectVerification("Items", scanner)) {
                            Item item = Item.create(scanner);

                            items.add(item);

                            ItemUtil.reArrangeItemList(items);

                            System.out.println();

                            itemNo = items.size() - 1;
                        } else
                        {
                            break;
                        }
                    }

                    if (isItemListEmpty)
                    {
                        System.out.println("\nItem has been created.\nNow you can add quantity for the item");
                    } else
                    {
                        Utils.showItems(items);

                        itemNo = Utils.getValidRange( 1, items.size(), scanner, "Item Number:",
                                "Enter the Item No from the table:") - 1;
                    }

                    int quantity = Utils.getValidRange( 1, 100, scanner, items.get(itemNo).getItemName() + "'s Quantity: ","Enter the quantity:");

                    itemTable.put(items.get(itemNo), itemTable.getOrDefault(items.get(itemNo), 0) + quantity);
                    break;
                }

                case 2:
                {
                    if (!itemTable.isEmpty())
                    {

                        showItemDetails(itemTable);

                        int removalItemNo = Utils.getValidRange( 1, items.size(), scanner, "Item Number:", "Enter the Item No from the Table:") - 1;

                        Item item = items.get(removalItemNo);

                        System.out.println("\nOption 1 -> Remove the Entire Item");
                        System.out.println("Option 2 -> Remove Partial Items");
                        System.out.println("Option 3 -> Quit");

                        int removeOption = Utils.getValidRange( 1, 3, scanner, "Item Remove Option", "Enter the Item Number for removing:");

                        if (removeOption == 1)
                        {

                            itemTable.remove(item);

                        } else if (removeOption == 2)
                        {
                            System.out.println("\nItem Quantity: " + itemTable.get(item));

                            System.out.println("\nEnter the removal Quantity less the above mentioned Quantity: ");

                            int removalQuantity = 0;
                            removalQuantity = Utils.handleIntegerInputMisMatches(removalQuantity, 0, scanner);

                            if (removalQuantity < 0)
                            {
                                removalQuantity *= -1;
                            }

                            while (removalQuantity > itemTable.get(item
                            ))
                            {
                                System.out.println("\nItem Quantity: " + itemTable.get(item));

                                System.out.println("\nEnter the appropriate quantity,  that should less than the actual quantity");

                                removalQuantity = Utils.handleIntegerInputMisMatches(removalQuantity, 0, scanner);

                                if (removalQuantity < 0)
                                {
                                    removalQuantity *= -1;
                                }
                            }

                            itemTable.put(item, itemTable.get(item) - removalQuantity);

                        } else
                        {
                            System.out.println("\nItem Table Updation");
                            break;
                        }
                    } else
                    {
                        System.out.println("\nNo items added yet");
                    }
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a valid option (1 - 3)");
                    break;
                }
            }
        }

        double subTotal = 0;

        boolean withinState = customer.getAddress().getState().equalsIgnoreCase("Tamil Nadu");

        for (Item cartItem : itemTable.keySet())
        {
            double cost = cartItem.getRate(itemTable.get(cartItem), withinState);
            subTotal += cost;
        }

        if (isUpdation && invoice != null)
        {
            double invTotal = subTotal - ((subTotal / 100) * invoice.getDiscount());

            invTotal += invoice.getShippingCharges();

            invoice.setTotal(invTotal);
        }

        return subTotal;

    }

    private static List<Invoice> searchByCustomer(String customerName, List<Invoice> invoices, double limit)
    {
        List<Invoice> invoicesOfCustomer = new ArrayList<>();
        for (Invoice invoice : invoices)
        {
            if (invoice.getCustomer().getName().equalsIgnoreCase(customerName) && invoice.getDueAmount() > limit)
            {
                invoicesOfCustomer.add(invoice);
            }
        }
        return invoicesOfCustomer;
    }

    private static void payForParticularInvoice(InvoiceUtil invoiceUtils, Invoice invoice)
    {
        System.out.println("\n Due Amount need to be paid: " + invoice.getDueAmount());

        double paidAmount = invoiceUtils.getPaymentInput(invoice.getDueAmount());

        System.out.println(paidAmount != 0 ? "\nAmount paid: " + paidAmount : "Amount is not paid (Or) Cancelled the Payment");

        invoice.setDueAmount(invoice.getDueAmount() - paidAmount);
    }

    private static void payingDueRemainingForCustomer (InvoiceUtil invoiceUtils, List<Invoice> customerInvoices)
    {
        double totalAmountToBePaid = 0;

        for (Invoice invoice : customerInvoices)
        {
            totalAmountToBePaid += invoice.getDueAmount();
        }

        double paidAmount = invoiceUtils.getPaymentInput(totalAmountToBePaid);

        Utils.showInvoices(customerInvoices);

        for (Invoice invoice : customerInvoices)
        {
            System.out.println("\nInvoice No: " + invoice.getInvNo() + " has due amount of " + invoice.getDueAmount());

            double amountPaidForThisInvoice = invoiceUtils.getPaymentInput(Math.min(invoice.getDueAmount(), paidAmount));

            invoice.setDueAmount(invoice.getDueAmount() - amountPaidForThisInvoice);

            paidAmount -= amountPaidForThisInvoice;

        }

    }

    private static boolean redirectVerification (String module, Scanner scanner)
    {
        System.out.println("\nSince, there is no " + module + ", you have to create" + module + " .");

        char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Redirection to Customer Creation", "Would you like to create the \" + module + \"\\nY -> Yes\\nN -> No");

        return yesOrNo == 'Y' || yesOrNo == 'y';
    }

    private static void showItemDetails(Map<Item, Integer> itemTable)
    {
        System.out.println("-".repeat(45));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Item Quantity");
        System.out.println("-".repeat(45));

        for (Item cartItem : itemTable.keySet())
        {
            System.out.printf("| %-10d | %-25s | %-15s |\n",
                    cartItem.getItemNo(),
                    cartItem.getItemName(),
                    itemTable.get(cartItem));
        }

        System.out.println("-".repeat(45));
    }

    private static boolean checkIsThereInvoices (List<Invoice> invoices, String module)
    {
        if (invoices.isEmpty())
        {
            System.out.println("\nThere is no invoices to " + module);
            return true;
        }
        return false;
    }

}