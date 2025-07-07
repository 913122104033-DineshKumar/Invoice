package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;

import java.time.LocalDate;
import java.util.*;

public class Utils {

    public static void printLines (int repeatTime) {
        System.out.println("-".repeat(repeatTime));
    }

    public static void showItems(List<Item> items) {
        int noOfLines = 58;

        System.out.println("-".repeat(noOfLines));

        // Print header
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Price");

        System.out.println("-".repeat(noOfLines));

        // Print item data
        for (int serialNo = 0; serialNo <  items.size(); serialNo++) {
            Item item = items.get(serialNo);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    item.getItemNo(),
                    item.getItemName(),
                    String.format("Rs.%.2f", item.getPrice()));
        }

        System.out.println("-".repeat(noOfLines));
    }

    public static void showCustomers(List<Customer> customers) {
        int noOfLines = 58;
        System.out.println("-".repeat(noOfLines));

        // Print header
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Customer No", "Customer Name", "Customer Email");

        System.out.println("-".repeat(noOfLines));

        // Print customer data
        for (int serialNo = 0; serialNo < customers.size(); serialNo++) {
            Customer customer = customers.get(serialNo);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    customer.getCusNo(),
                    customer.getName(),
                    customer.getEmail());
        }

        System.out.println("-".repeat(noOfLines));
    }

    public static void showInvoices(List<Invoice> invoices) {
        int noOfLines = 85;

        System.out.println("-".repeat(noOfLines));

        // Print header
        System.out.printf("| %-10s | %-25s | %-15s | %-15s | %-15s |%n",
                "Invoice No", "Customer Name", "Total", "Due Amount", "Status");

        System.out.println("-".repeat(noOfLines));

        // Print invoice data
        for (int serialNo = 0; serialNo < invoices.size(); serialNo++) {
            Invoice invoice = invoices.get(serialNo);
            System.out.printf("| %-10d | %-25s | %-15s | %-15s | %-15s |%n",
                    invoice.getInvNo(),
                    invoice.getCustomer().getName(),
                    String.format("Rs.%.2f", invoice.getTotal()),
                    String.format("Rs.%.2f", invoice.getDueAmount()),
                    invoice.getStatus());
        }

        System.out.println("-".repeat(noOfLines));
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

        inputOption = Utils.handleCharacterInput(scanner, inputOption,fieldName);

        while (!options.contains(inputOption)) {
            System.out.println("\n" + ErrorUtils.optionError(fieldName, options));

            inputOption = Utils.handleCharacterInput(scanner, inputOption, fieldName);

        }

        return inputOption;
    }

    // Overloading the Valid function for String Types
    public static String getValidStringInput (String regex, Scanner scanner, String example, String fieldName, String prompt, boolean isMandatory) {
        String value = "";

        System.out.println("\n" + prompt.trim());

        value = scanner.nextLine().trim();

        while (isMandatory && !value.matches(regex)) {
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

        inputValue = Utils.handleDoubleInput(inputValue, lowerLimit -1, scanner);

        while (inputValue < lowerLimit)
        {
            System.out.println("\n" + ErrorUtils.negativeInputError(fieldName));

            inputValue = Utils.handleDoubleInput(inputValue, lowerLimit -1, scanner);
        }

        return inputValue;
    }

    public static double getValidDoubleRange (double lowerLimit,  double upperLimit, Scanner scanner, String fieldName, String prompt)
    {
        double inputValue = 0;

        System.out.println("\n" + prompt.trim());

        inputValue = Utils.handleDoubleInput(inputValue, lowerLimit -1, scanner);

        while (inputValue < lowerLimit || inputValue > upperLimit)
        {
            System.out.println("\n" + ErrorUtils.doubleInvalidAmountPaidError(fieldName, inputValue, lowerLimit, upperLimit));

            inputValue = Utils.handleDoubleInput(inputValue, lowerLimit -1, scanner);
        }

        return inputValue;
    }

    private static char handleCharacterInput (Scanner scanner, char option, String fieldName) {
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

    private static double handleDoubleInput(double value, double defaultValue, Scanner scanner) {
        double inputValue = value;
        try
        {
            inputValue = scanner.nextDouble();
        }
        catch (InputMismatchException i)
        {
            System.out.println("\nYou have entered an invalid input\nEg. 0, 0.23, 1.50");
            inputValue = defaultValue;
        }

        scanner.nextLine();

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
            System.out.println("\nProvided input is invalid\nEg. 1, 2, 3");
            inputValue = defaultValue;
        }

        scanner.nextLine();

        return inputValue;

    }

    public static<T> void reverse (List<T> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++, j--)
        {
            T currentData = list.get(i);
            list.set(i, list.get(j));
            list.set(j, currentData);
        }
    }

    public static int compareDates (LocalDate date1, LocalDate date2)
    {

        if (date1.getYear() < date2.getYear())
        {
            return 1;
        }
        else if (date1.getYear() > date2.getYear())
        {
            return -1;
        } else
        {
            if (date1.getMonthValue() < date2.getMonthValue())
            {
                return 1;
            }
            else if (date1.getMonthValue() > date2.getMonthValue())
            {
                return -1;
            } else
            {
                if (date1.getDayOfMonth() <= date2.getDayOfMonth())
                {
                    return 1;
                } else
                {
                    return -1;
                }
            }
        }
    }

    public static<T> boolean isOnlyOneData (List<T> list)
    {
        return list.size() == 1;
    }

    public static void showMessageOnlyOneDataIsThere (String module)
    {
        System.out.println("\nThere is only one " + module);
    }

}