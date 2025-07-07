import invoice.GlobalConstants;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;
import invoice.utils.*;

import java.util.*;

public class Main
{

    public static void main(String[] args)
    {
        GlobalConstants.initialize();
        Scanner scanner = new Scanner(System.in);
        InvoiceUtil invoiceUtils = new InvoiceUtil(scanner);
        List<Customer> customers = new ArrayList<>();
        List<Customer> sortedCustomers = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Item> sortedItems = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();
        List<Invoice> sortedInvoices = new ArrayList<>();
        Set<String> customerEmails = new HashSet<>();

        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Item Module");
            System.out.println("Option 2 -> Customer Module");
            System.out.println("Option 3 ->  Invoice Module");
            System.out.println("Option 4 -> Quit");
            System.out.println("-".repeat(20));

            System.out.println("\nEnter the option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {

                case 1:
                {
                    System.out.println("\nEntering into Item Module...");
                    itemModule(items, sortedItems, scanner);

                    break;
                }

                case 2:
                {
                    System.out.println("\nEntering into Customer Module...");
                    customerModule(customers, sortedCustomers, customerEmails, scanner);

                    break;
                }

                case 3:
                {
                    System.out.println("\nEntering into Invoice Module...");
                    invoiceModule(items, sortedItems, customers, sortedCustomers, invoices, sortedInvoices, customerEmails, invoiceUtils, scanner);

                    break;
                }

                case 4:
                {
                    System.out.println("\nExiting the Invoice App...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a valid input (1 - 4)");

                    break;
                }
            }
        } while (option != 4);

        scanner.close();
    }

    private static void itemModule(List<Item> items, List<Item> sortedItems, Scanner scanner) {
        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Adding Item");
            System.out.println("Option 2 -> Updating Item");
            System.out.println("Option 3 -> Searching Item");
            System.out.println("Option 4 -> Deleting Item");
            System.out.println("Option 5 -> Sort Items");
            System.out.println("Option 6 -> Exit");

            System.out.println("\nEnter the Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    Item item = Item.create(scanner);
                    items.add(item);
                    sortedItems.add(item);

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

                    String itemName = Utils.getValidStringInput( GlobalConstants.NAME_REGEX, scanner, "Punam Saree", "Item Name", "Enter Item Name (Eg. Punam Saree):", true);

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

                case 5:
                {
                    Item.sortItems(sortedItems);
                }

                case 6:
                {
                    System.out.println("\nExiting the Item Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter Valid Option (1 - 6)");
                    break;
                }
            }
        } while (option != 6);
    }

    private static void customerModule(List<Customer> customers, List<Customer> sortedCustomers, Set<String> customerEmails, Scanner scanner)
    {
        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Adding Customer");
            System.out.println("Option 2 -> Updating Customer");
            System.out.println("Option 3 -> Searching Customer");
            System.out.println("Option 4 -> Deleting Customer");
            System.out.println("Option 5 -> Sort the Customers");
            System.out.println("Option 6 -> Exit");

            System.out.println("\nEnter the Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    Customer customer = Customer.create(customerEmails, scanner);
                    customers.add(customer);
                    sortedCustomers.add(customer);

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

                    String customerName = Utils.getValidStringInput( GlobalConstants.NAME_REGEX, scanner, "Dinesh Kumar K K", "Customer Name", "Enter the Customer Name", true);

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

                    int deleteCustomerNo = 0;

                    deleteCustomerNo = Utils.getValidRange( 1, customers.size(), scanner, "Customer Number", "Enter the Customer No from the table:") - 1;

                    customerEmails.remove(customers.get(deleteCustomerNo).getEmail());

                    System.out.println("\n" + customers.get(deleteCustomerNo).getName() + " " + "is deleted successfully...");

                    customers.remove(deleteCustomerNo);

                    CustomerUtil.reArrangeCustomerList(customers);

                    break;
                }

                case 5:
                {
                    Customer.sortCustomers(sortedCustomers);
                }

                case 6:
                {
                    System.out.println("\nExiting the Customer Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter Valid Option (1 - 6)");
                    break;
                }
            }
        } while (option != 6);
    }

    private static void invoiceModule (List<Item> items, List<Item> sortedItems, List<Customer> customers, List<Customer> sortedCustomers, List<Invoice> invoices, List<Invoice> sortedInvoices, Set<String> customerEmails, InvoiceUtil invoiceUtils, Scanner scanner)
    {
        InvoiceHandler invoiceHandler = new InvoiceHandler(scanner);

        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Adding Invoice");
            System.out.println("Option 2 -> Updating Invoice");
            System.out.println("Option 3 -> Searching Invoice");
            System.out.println("Option 4 -> Deleting Invoice");
            System.out.println("Option 5 -> Pay for the Invoices");
            System.out.println("Option 6 -> Set Status for Invoice");
            System.out.println("Option 7 -> Sort the Invoices");
            System.out.println("Option 8 -> Exit");

            System.out.println("\nEnter the Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    Invoice invoice = invoiceHandler.create(items,sortedItems, customers, sortedCustomers, customerEmails);

                    if (invoice == null)
                    {
                        System.out.println("\nCan't Create Invoice, due to no Customers...");
                        break;
                    }

                    invoices.add(invoice);
                    sortedInvoices.add(invoice);

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

                    int invNo = 0;

                    if (Utils.isOnlyOneData(invoices))
                    {
                        Utils.showMessageOnlyOneDataIsThere("Invoice");
                    }
                    else
                    {
                        Utils.showInvoices(invoices);

                        invNo = Utils.getValidRange(1, invoices.size(), scanner, "Invoice Number", "Enter the Invoice number from the table:") - 1;
                    }

                    Invoice updatedInvoice = invoices.get(invNo);

                    invoiceHandler.update(updatedInvoice, items, sortedItems, customers);

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

                    List<Invoice> invoicesForCustomer = invoiceHandler.searchByCustomer(customerName, invoices, -0.1);

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

                    int deleteInvoiceNo = 0;

                    if (Utils.isOnlyOneData(invoices))
                    {
                        Utils.showMessageOnlyOneDataIsThere("Invoice");
                    }
                    else {
                        Utils.showInvoices(invoices);

                        deleteInvoiceNo = Utils.getValidRange(1, invoices.size(), scanner, "Invoice Number", "Enter the Invoice No from the table:") - 1;
                    }

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

                    invoiceHandler.paymentModule(invoices, customers);

                    break;
                }

                case 6:
                {
                    if (checkIsThereInvoices(invoices, "status"))
                    {
                        break;
                    }

                    int invNo = 0;

                    if (Utils.isOnlyOneData(invoices))
                    {
                        Utils.showMessageOnlyOneDataIsThere("Invoice");
                    }
                    else {
                        Utils.showInvoices(invoices);

                        System.out.println("\nEnter the Invoice Number from the table: ");

                        invNo = invoiceUtils.getSerialNumberInput(1, invoices.size(), "Invoice Number") - 1;
                    }

                    Invoice invoice = invoices.get(invNo);

                    String previousStatus = invoice.getStatus();

                    int status = Utils.getValidRange( 1, GlobalConstants.INVOICE_STATUS.size(), scanner, "Invoice Status", "Enter the Status No:\n" + Utils.showStatuses()) - 1;

                    invoice.setStatus(status);

                    System.out.println("\nInvoice's Status updated from " + previousStatus + " to " + invoice.getStatus());

                    break;
                }

                case 7:
                {
                    invoiceHandler.sortingModule(sortedInvoices);

                    break;
                }

                case 8:
                {
                    System.out.println("\nExiting the Invoice Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter Valid Option (1 - 8)");
                    break;
                }
            }
        } while (option != 8);
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

    private static void reverse (List<Invoice> invoices) {
        for (int i = 0, j = invoices.size() - 1; i < j; i++, j--)
        {
            Invoice invoice = invoices.get(i);
            invoices.set(i, invoices.get(j));
            invoices.set(j, invoice);
        }
    }

}