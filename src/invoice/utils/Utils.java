package invoice.utils;

import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;

import java.util.*;

public class Utils {

    private static int noOfLines;
    public static Map<Character, String> itemTypes;
    public static Map<Character, String> itemUnits;
    public static Map<Character, String> customerTypes;
    public static List<String> invoiceStatus;

    // Initialization
    public static void initialize () {
        noOfLines = 75;
        itemTypes = Map.of(
                'G', "Goods",
                'S', "Services"
        );
        itemUnits = Map.of(
                'P', "Pieces",
                'M', "Meters",
                'B', "Box",
                'N', "Undefined"
        );
        customerTypes = Map.of(
                'B', "Business",
                'I', "Individual"
        );
        invoiceStatus = Arrays.asList("DRAFT", "SENT", "PARTIALLY_PAID", "CLOSED");
    }

    // Printing the Outputs
    public static void showItems (TreeMap<Integer, Item> items) {
        System.out.println("-".repeat(noOfLines));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Price");
        System.out.println("-".repeat(noOfLines));

        for (Integer itemNo : items.keySet()) {
            Item item = items.get(itemNo);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    (itemNo),
                    item.getItemName(),
                    item.getPrice());
        }
        System.out.println("-".repeat(noOfLines));
    }

    public static void showCustomers (TreeMap<Integer, Customer> customers) {
        System.out.println("-".repeat(noOfLines));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Customer No", "Customer Name", "Customer Email");
        System.out.println("-".repeat(noOfLines));

        for (Integer cusNo : customers.keySet()) {
            Customer customer = customers.get(cusNo);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    (cusNo),
                    customer.getName(),
                    customer.getEmail());
        }
        System.out.println("-".repeat(noOfLines));
    }

    public static void showInvoices (TreeMap<Integer, Invoice> invoices) {
        System.out.println("-".repeat(noOfLines));
        System.out.printf("| %-10s | %-25s | %-15s | %-15s |%n",
                "Invoice No", "Customer Name", "Status", "Total");
        System.out.println("-".repeat(noOfLines));

        for (Integer invNo : invoices.keySet()) {
            Invoice invoice = invoices.get(invNo);
            System.out.printf("| %-10d | %-25s | %-15s | %-15s |%n",
                    (invNo),
                    invoice.getCustomer().getName(),
                    invoice.getStatus(),
                    invoice.getTotal());
        }

        System.out.println("-".repeat(noOfLines));
    }

    public static String showStatuses () {
        StringBuilder sb = new StringBuilder("Status\n");
        for (int i = 0; i < invoiceStatus.size(); i++) {
            sb.append(invoiceStatus.get(i)).append(" -> ").append(i + 1).append("\n");
        }
        return sb.toString().trim();
    }

    // Validations
    public static char getValidOption (char option, char option1, char option2, Scanner scanner, String fieldName, String[][] availableOptions) {
        char inputOption = option;
        while (inputOption != option1 && inputOption != option2) {
            System.out.println("\n" + ErrorUtils.optionError(fieldName, availableOptions));

            inputOption = Utils.handleOptionStringOutOfBoundError(scanner, inputOption, fieldName);

            inputOption = Character.toUpperCase(inputOption);
        }
        return inputOption;
    }

    // Overloading the Valid function for String Types
    public static String getValidInput (String s, String regex, Scanner scanner, String example, String fieldName) {
        String ans = s;
        while (!ans.matches(regex)) {
            System.out.println("\n" + ErrorUtils.regexMatchFailedError(fieldName, example));
            ans = scanner.nextLine().trim();
        }
        return ans;
    }

    // Overloading the Valid function for Integer Types
    public static int getValidInput (int option, int lowerLimit, int upperLimit, Scanner scanner, String fieldName) {
        int inputOption = option;

        while (inputOption < lowerLimit || inputOption > upperLimit) {
            System.out.println("\n" + ErrorUtils.rangeOutOfBoundError(fieldName, inputOption, lowerLimit, upperLimit));

            inputOption = (int) Utils.handleIntegerInputMisMatches(inputOption, scanner);
        }

        return inputOption;
    }

    // Overloading the Valid function for Double Types
    public static double getValidInput (double option, double lowerLimit, double upperLimit, Scanner scanner, String fieldName) {
        double inputOption = option;

        while (inputOption < lowerLimit || inputOption > upperLimit) {
            System.out.println("\n" + ErrorUtils.rangeOutOfBoundError(fieldName, inputOption, lowerLimit, upperLimit));

            inputOption = Utils.handleIntegerInputMisMatches(inputOption, scanner);
        }

        return inputOption;
    }

    public static void printLines (int repeatTime) {
        System.out.println("-".repeat(repeatTime));
    }

    public static boolean redirectVerification (String module, Scanner scanner) {
        System.out.println("\nSince, there is no " + module + ", you have to create" + module + " .");

        System.out.println("\nWould you like to create the " + module + "\nY -> Yes\nN -> No");

        char reDirectToCustomerModule = 'A';

        reDirectToCustomerModule = Utils.handleOptionStringOutOfBoundError(scanner,reDirectToCustomerModule, "Redirection Option");
        reDirectToCustomerModule = Character.toUpperCase(reDirectToCustomerModule);

        String[][] availableOptions = { {"Y", "Yes"}, {"N", "No"} };

        reDirectToCustomerModule = Utils.getValidOption(reDirectToCustomerModule,'Y', 'N', scanner, "Redirection to Customer Creation", availableOptions);

        return reDirectToCustomerModule == 'Y';
    }

    public static String getAddressFieldInput(String regex, Scanner scanner, String example, String fieldName) {
        System.out.println("\nEnter the " + fieldName + " (Eg. " + example + "):");

        String name = scanner.nextLine().trim();

        name = Utils.getValidInput(name, regex, scanner, example, fieldName);

        return name;
    }

    public void printItemHeaders () {
        Utils.printLines(60);
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s |\n",
                "Item Number",
                "Item Type",
                "Item Unit",
                "Item Name",
                "Intra State Tax Rate",
                "Inter State Tax Rate",
                "Item Price",
                "Item Description");
        Utils.printLines(60);
    }

    public void printCustomerHeaders () {
        Utils.printLines(60);
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s |\n",
                "Item Number",
                "Item Type",
                "Item Unit",
                "Item Name",
                "Intra State Tax Rate",
                "Inter State Tax Rate",
                "Item Price",
                "Item Description");
        Utils.printLines(60);
    }

    public void printInvoiceHeaders () {
        Utils.printLines(60);
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s |\n",
                "Item Number",
                "Item Type",
                "Item Unit",
                "Item Name",
                "Intra State Tax Rate",
                "Inter State Tax Rate",
                "Item Price",
                "Item Description");
        Utils.printLines(60);
    }

    public static char handleOptionStringOutOfBoundError (Scanner scanner, char option, String fieldName) {
        char inputOption = option;
        try {
            inputOption = scanner.nextLine().trim().charAt(0);
        } catch (StringIndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("\nYou have pressed the Enter Key\nEnter the " + fieldName + " again");
            inputOption = '\0';
        }

        return inputOption;
    }

    public static double handleIntegerInputMisMatches (double s, Scanner scanner) {
        double input = s;
        try {
            input = scanner.nextDouble();
        } catch (InputMismatchException i) {
            System.out.println("\nYou have entered different Input type other than double (Or) Int.\nEnter data in Integer (Or) Double type");
            input = (int) 1e9;
        }

        scanner.nextLine();

        if (input == (int) 1e9) {
            input = 0;
        }

        return input;
    }



}
