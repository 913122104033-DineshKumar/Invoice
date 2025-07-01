import invoice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Enter Option 1 for Adding Customer: ");
            System.out.println("Enter Option 2 for Adding Item: ");
            System.out.println("Enter Option 3 for Creating Invoice: ");
            System.out.println("Enter Option 4 for Exit: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    Customer customer = Customer.createCustomer();
                    System.out.println(customer.toString());
                    customers.add(customer);
                    break;
                case 2:
                    Item item = Item.createItem();
                    items.add(item);
                    break;
                case 3:
                    Invoice invoice = Invoice.createInvoice(items, customers);
                    invoices.add(invoice);
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

    private static void showCustomers() {
    }

}