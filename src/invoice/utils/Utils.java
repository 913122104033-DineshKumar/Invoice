package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;

import java.util.*;

public class Utils {

    public static void printLines (int repeatTime) {
        System.out.println("-".repeat(repeatTime));
    }

    public static void showItems(List<Item> items) {
        System.out.println("-".repeat(58));

        // Print header
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Price");

        System.out.println("-".repeat(58));

        // Print item data
        for (int serialNo = 0; serialNo <  items.size(); serialNo++) {
            Item item = items.get(serialNo);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    serialNo + 1,
                    item.getItemName(),
                    String.format("Rs.%.2f", item.getPrice()));
        }

        System.out.println("-".repeat(58));
    }

    public static void showCustomers(List<Customer> customers) {
        System.out.println("-".repeat(58));

        // Print header
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Customer No", "Customer Name", "Customer Email");

        System.out.println("-".repeat(58));

        // Print customer data
        for (int serialNo = 0; serialNo < customers.size(); serialNo++) {
            Customer customer = customers.get(serialNo);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    serialNo + 1,
                    customer.getName(),
                    customer.getEmail());
        }

        System.out.println("-".repeat(58));
    }

    public static void showInvoices(List<Invoice> invoices) {
        System.out.println("-".repeat(74));

        // Print header
        System.out.printf("| %-10s | %-25s | %-15s | %-15s |%n",
                "Invoice No", "Customer Name", "Status", "Total");

        System.out.println("-".repeat(74));

        // Print invoice data
        for (int serialNo = 0; serialNo < invoices.size(); serialNo++) {
            Invoice invoice = invoices.get(serialNo);
            System.out.printf("| %-10d | %-25s | %-15s | %-15s |%n",
                    serialNo + 1,
                    invoice.getCustomer().getName(),
                    invoice.getStatus(),
                    String.format("Rs.%.2f", invoice.getTotal()));
        }

        System.out.println("-".repeat(74));
    }

    public static String showStatuses () {
        StringBuilder sb = new StringBuilder("Status\n");
        for (int i = 0; i < GlobalConstants.INVOICE_STATUS.size(); i++) {
            sb.append(GlobalConstants.INVOICE_STATUS.get(i)).append(" -> ").append(i + 1).append("\n");
        }
        return sb.toString().trim();
    }

    // Validations
    public static char getValidOption (Set<Character> options, Scanner scanner, String fieldName, String prompt) {
        char inputOption = '\0';

        System.out.println("\n" + prompt.trim());

        while (!options.contains(inputOption)) {
            System.out.println("\n" + ErrorUtils.optionError(fieldName, options));

            inputOption = Utils.handleOptionStringOutOfBoundError(scanner, inputOption, fieldName);

        }

        return inputOption;
    }

    // Overloading the Valid function for String Types
    public static String getValidStringInput (String regex, Scanner scanner, String example, String fieldName, String prompt) {
        String value = "";

        System.out.println("\n" + prompt.trim());

        value = scanner.nextLine().trim();

        while (!value.matches(regex)) {
            System.out.println("\n" + ErrorUtils.regexMatchFailedError(fieldName, example));
            value = scanner.nextLine().trim();
        }
        return value;
    }

    // Overloading the Valid function for Integer Types
    public static int getValidRange(int lowerLimit, int upperLimit, Scanner scanner, String fieldName, String prompt) {
        int inputOption = 0;

        System.out.println("\n" + prompt.trim());

        inputOption = Utils.handleIntegerInputMisMatches(inputOption, lowerLimit - 1, scanner);

        while (inputOption < lowerLimit || inputOption > upperLimit) {
            System.out.println("\n" + ErrorUtils.integerRangeOutOfBoundError(fieldName, inputOption, lowerLimit, upperLimit));

            inputOption = Utils.handleIntegerInputMisMatches(inputOption, lowerLimit - 1, scanner);
        }

        return inputOption;
    }

    // Overloading the Valid function for Double Types
    public static double getValidDoubleInput(double lowerLimit, Scanner scanner, String fieldName, String prompt)
    {
        double inputValue = 0;

        System.out.println("\n" + prompt.trim());

        inputValue = Utils.handleDoubleInputMisMatches(inputValue, lowerLimit -1, scanner);

        while (inputValue < lowerLimit)
        {
            System.out.println("\n" + ErrorUtils.negativeInputError(fieldName));

            inputValue = Utils.handleDoubleInputMisMatches(inputValue, lowerLimit -1, scanner);
        }

        return inputValue;
    }

    public static char handleOptionStringOutOfBoundError (Scanner scanner, char option, String fieldName) {
        char inputOption = option;
        try
        {
            inputOption = scanner.nextLine().trim().charAt(0);
        }
        catch (StringIndexOutOfBoundsException | InputMismatchException e)
        {
            System.out.println("\nYou have pressed the Enter Key\nEnter the " + fieldName + " again");
            inputOption = '\0';
        }

        return inputOption;
    }

    public static double handleDoubleInputMisMatches (double value, double defaultValue, Scanner scanner) {
        double inputValue = value;
        try
        {
            inputValue = scanner.nextDouble();
        }
        catch (InputMismatchException i)
        {
            System.out.println("\nYou have entered different Input type other than double (Or) Int.\nEnter data in Integer (Or) Double type");
            inputValue = defaultValue;
        }

        return inputValue;
    }

    public static int handleIntegerInputMisMatches (int value, int defaultValue, Scanner scanner)
    {
        int inputValue = value;
        try
        {
            inputValue = scanner.nextInt();
        }
        catch (InputMismatchException | IndexOutOfBoundsException i)
        {
            System.out.println("\nProvided input is invalid\nEg. 1 - 100");
            inputValue = defaultValue;
        }

        return inputValue;

    }

}