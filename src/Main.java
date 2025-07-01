import invoice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static List<Item> items;
    private static List<Customer> customers;
    private static List<Invoice> invoices;

    public static void main(String[] args) {
        Utils utils = new Utils();
        scanner = new Scanner(System.in);
        customers = new ArrayList<>();
        items = new ArrayList<>();
        invoices = new ArrayList<>();
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

    private static void itemModule () {
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
                    Utils.showItems(items);

                    System.out.println("Enter the Item No to update: ");
                    int rowNo = scanner.nextInt();
                    scanner.nextLine();

                    Item uItem = items.get(rowNo);
                    uItem.update();

                    System.out.println(uItem.toString());
                    break;

                case 3:
                    System.out.println("Enter the Item Name to search: ");
                    String itemName = scanner.nextLine();

                    Item searchItem = Item.search(itemName, items);

                    if (searchItem != null) {
                        System.out.println(searchItem.toString());
                    } else {
                        System.out.println("Item not found");
                    }
                    break;

                case 4:
                    Utils.showItems(items);

                    System.out.println("Enter Item No, you want to delete: ");
                    int indexNo = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println(items.get(indexNo).getItemName() + " is deleted successfully...");
                    items.remove(indexNo);
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

    private static void customerModule () {
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
                    Customer customer = Customer.create();
                    customers.add(customer);

                    System.out.println(customer.toString());
                    break;

                case 2:
                    Utils.showCustomers(customers);

                    System.out.println("Enter the Customer No to update: ");
                    int rowNo = scanner.nextInt();
                    scanner.nextLine();

                    Customer uCustomer = customers.get(rowNo);
                    uCustomer.update();

                    System.out.println(uCustomer.toString());
                    break;

                case 3:
                    System.out.println("Enter the Customer Name to search: ");
                    String customerName = scanner.nextLine();

                    Customer searchCustomer = Customer.search(customerName, customers);

                    if (searchCustomer != null) {
                        System.out.println(searchCustomer.toString());
                    } else {
                        System.out.println("Customer not found");
                    }
                    break;

                case 4:
                    Utils.showCustomers(customers);

                    System.out.println("Enter Customer No, you want to delete: ");
                    int indexNo = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println(customers.get(indexNo).getName() + " " + "is deleted successfully...");
                    customers.remove(indexNo);
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

    private static void invoiceModule () {
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
                    Invoice invoice = Invoice.create(items, customers);
                    invoices.add(invoice);

                    System.out.println(invoice.toString());
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
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

}