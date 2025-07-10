package invoice.utils;

import invoice.models.Customer;
import invoice.models.Invoice;
import invoice.models.Item;

import java.util.List;

// Dedicated for displaying all the classes

public class DisplayUtil
{
    // Hyphens for decorating the headings
    public static void printHyphens(int repeatTime)
    {
        System.out.println("-".repeat(repeatTime));
    }

    public static void showItems (List<Item> items)
    {
        int noOfLines = 58;

        System.out.println("-".repeat(noOfLines));

        // Print header
        System.out.printf("| %-10s | %-10s | %-10s | %-25s | %-15s | %15s |%n",
                "Serial No", "Item Id", "Item No", "Item Name", "Price", "Date");

        System.out.println("-".repeat(noOfLines));

        // Print item data
        for (int serialNo = 0; serialNo < items.size(); serialNo++)
        {
            Item item = items.get(serialNo);

            System.out.printf("| %-10d | %-10s | %-10d | %-25s | %-15s | %-15s |%n",
                    serialNo + 1,
                    item.getItemId(),
                    item.getItemNo(),
                    item.getItemName(),
                    String.format("Rs.%.2f", item.getPrice()),
                    item.getCreatedAt());
        }

        System.out.println("-".repeat(noOfLines));
    }

    // Showing the Customer List
    public static void showCustomers(List<Customer> customers)
    {
        int noOfHyphens = 58;
        System.out.println("-".repeat(noOfHyphens));

        // Print header
        System.out.printf("| %-10s | %-10s |  %15s | %-25s | %-15s | %-15s |%n", "Serial No", "Customer Id", "Customer No", "Customer Name", "Customer Email", "Created At");

        System.out.println("-".repeat(noOfHyphens));

        // Print customer data
        for (int serialNo = 0; serialNo < customers.size(); serialNo++)
        {
            Customer customer = customers.get(serialNo);

            System.out.printf("| %-10d | %10s | %10s | %-25s | %-15s | %-15s |%n",
                    serialNo + 1,
                    customer.getCustomerId(),
                    customer.getCusNo(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getCreatedAt());
        }

        System.out.println("-".repeat(noOfHyphens));
    }

    // Showing the Invoice List
    public static void showInvoices(List<Invoice> invoices) {

        int noOfHyphens = 85;

        System.out.println("-".repeat(noOfHyphens));

        // Print header
        System.out.printf("| %-10s | %10s | %-10s | %-15s | %-25s | %-15s | %-15s | %-15s |%n", "Serial No", "Invoice Id", "Invoice No", "Date", "Customer Name", "Total", "Due Amount", "Status");

        System.out.println("-".repeat(noOfHyphens));

        // Print invoice data
        for (int serialNo = 0; serialNo < invoices.size(); serialNo++)
        {
            Invoice invoice = invoices.get(serialNo);

            System.out.printf("| %-10d | %10s | %-10d | %-15s | %-25s | %-15s | %-15s | %-15s |%n",
                    serialNo + 1,
                    invoice.getInvoiceId(),
                    invoice.getInvNo(),
                    invoice.getCreatedAt(),
                    invoice.getCustomer().getName(),
                    String.format("Rs.%.2f", invoice.getTotal()),
                    String.format("Rs.%.2f", invoice.getDueAmount()),
                    invoice.getStatus());
        }

        System.out.println("-".repeat(noOfHyphens));
    }

}
