package invoice.utils;

import invoice.handlers.InvoiceHandler;
import invoice.models.Customer;
import invoice.models.Invoice;
import invoice.models.Item;

import java.time.LocalDate;
import java.util.*;

public class InputUtils
{

    private static Scanner scanner;

    public static void printHyphens(int repeatTime)
    {
        System.out.println("-".repeat(repeatTime));
    }

    // Scanner Creator and Destroyer

    public static void createScanner ()
    {
        if (scanner == null)
        {
            scanner = new Scanner(System.in);
        }
    }

    public static void destroyScanner ()
    {
        if (scanner != null)
        {
            scanner.close();
        }
    }

    // Show (Or) Print Methods

    public static void showItems (List<Item> items)
    {
        int noOfLines = 58;

        System.out.println("-".repeat(noOfLines));

        // Print header
        System.out.printf("| %-10s | %-10s | %-25s | %-15s | %15s |%n", "Serial No", "Item No", "Item Name", "Price", "Date");

        System.out.println("-".repeat(noOfLines));

        // Print item data
        for (int serialNo = 0; serialNo < items.size(); serialNo++)
        {
            Item item = items.get(serialNo);

            System.out.printf("| %-10d | %-10d | %-25s | %-15s | %-15s |%n",
                    serialNo + 1,
                    item.getItemNo(),
                    item.getItemName(),
                    String.format("Rs.%.2f", item.getPrice()),
                    item.getCreatedAt());
        }

        System.out.println("-".repeat(noOfLines));
    }

    public static void showCustomers(List<Customer> customers)
    {
        int noOfHyphens = 58;
        System.out.println("-".repeat(noOfHyphens));

        // Print header
        System.out.printf("! %-10s | %15s | %-25s | %-15s | %-15s |%n", "Serial No", "Customer No", "Customer Name", "Customer Email", "Created At");

        System.out.println("-".repeat(noOfHyphens));

        // Print customer data
        for (int serialNo = 0; serialNo < customers.size(); serialNo++)
        {
            Customer customer = customers.get(serialNo);

            System.out.printf("| %-10d | %10s | %-25s | %-15s | %-15s |%n",
                    serialNo + 1,
                    customer.getCusNo(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getCreatedAt());
        }

        System.out.println("-".repeat(noOfHyphens));
    }

    public static void showInvoices(List<Invoice> invoices) {

        int noOfHyphens = 85;

        System.out.println("-".repeat(noOfHyphens));

        // Print header
        System.out.printf("| %-10s | %-10s | %-15s | %-25s | %-15s | %-15s | %-15s |%n", "Serial No", "Invoice No", "Date", "Customer Name", "Total", "Due Amount", "Status");

        System.out.println("-".repeat(noOfHyphens));

        // Print invoice data
        for (int serialNo = 0; serialNo < invoices.size(); serialNo++)
        {
            Invoice invoice = invoices.get(serialNo);

            System.out.printf("| %-10d | %-10d | %-15s | %-25s | %-15s | %-15s | %-15s |%n",
                    serialNo + 1,
                    invoice.getInvNo(),
                    invoice.getCreatedAt(),
                    invoice.getCustomer().getName(),
                    String.format("Rs.%.2f", invoice.getTotal()),
                    String.format("Rs.%.2f", invoice.getDueAmount()),
                    invoice.getStatus());
        }

        System.out.println("-".repeat(noOfHyphens));
    }

    public static String showStatuses ()
    {
        StringBuilder sb = new StringBuilder("Status\n");
        for (InvoiceHandler.Status s : InvoiceHandler.Status.values())
        {
            sb.append(s.ordinal() + 1).append(" -> ").append(s.name()).append("\n");
        }
        return sb.toString().trim();
    }

    // Validation Methods
    public static char collectToggleChoice(char key, String fieldName, String prompt)
    {
        char inputOption;
        do
        {
            System.out.println("\n" + prompt.trim() + "\n" + "Note: Function will consider any other than " + key + " as secondary Option");

            inputOption = InputUtils.handleCharacterInput(fieldName);


        } while (inputOption == '\0');

        return inputOption;

    }

    // Overloading the Valid function for String Types
    public static String getValidStringInput (String regex, String example, String fieldName, String prompt, boolean isMandatory)
    {
        String value = "";

        System.out.println("\n" + prompt.trim());

        value = InputUtils.handleStringInputMisMatches(value, "");
        while (!value.matches(regex))
        {
            if (!isMandatory && value.isEmpty())
            {
                break;
            }

            System.out.println("\n" + InputUtils.regexMatchFailedError(fieldName, example));
            value = InputUtils.handleStringInputMisMatches(value, "");
        }
        return value;
    }

    // Overloading the Valid function for Integer Types
    public static int getValidRange(int lowerLimit, int upperLimit, String fieldName, String prompt)
    {
        int inputOption = 0;

        System.out.println("\n" + prompt.trim());

        inputOption = InputUtils.handleIntegerInputMisMatches(inputOption, lowerLimit - 1);

        while (inputOption < lowerLimit || inputOption > upperLimit)
        {
            System.out.println("\n");

            if (inputOption < 0)
            {
                System.out.println("You have entered a negative number" + ".\n\n" + "The range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Enter the number from the above mentioned range for " + fieldName + ":");
            } else
            {
                System.out.println("You have entered an number that's not exist in the input range for "
                        + fieldName + ".\n\n" + "The range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Enter the number from the above mentioned range for " + fieldName + ":");
            }

            inputOption = InputUtils.handleIntegerInputMisMatches(inputOption, lowerLimit - 1);
        }

        return inputOption;
    }

    // Overloading the Valid function for Double Types
    public static double getValidDoubleInput(double lowerLimit, String fieldName, String prompt)
    {
        double inputValue = 0;

        System.out.println("\n" + prompt.trim());

        inputValue = InputUtils.handleDoubleInput(inputValue, lowerLimit -1);

        while (inputValue < lowerLimit)
        {
            System.out.println("\n" + "You have entered a negative number" + ".\n\n" + "Enter the number a non negative number for " + fieldName + ":");

            inputValue = InputUtils.handleDoubleInput(inputValue, lowerLimit -1);
        }

        if (inputValue == -0)
        {
            inputValue = 0;
        }

        return inputValue;
    }

    public static double getValidDoubleRange (double lowerLimit,  double upperLimit, String fieldName, String prompt)
    {
        double inputValue = 0;

        System.out.println("\n" + prompt.trim());

        inputValue = InputUtils.handleDoubleInput(inputValue, lowerLimit - 1);

        while (inputValue < lowerLimit || inputValue > upperLimit)
        {
            System.out.println("\n");

            if (inputValue < 0)
            {
                System.out.println("You have entered a negative number" + ".\n\n" + "The Amount range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Pay the amount from the above mentioned range for " + fieldName + ":");
            } else {
                System.out.println("You have entered an number exceeds the Amount to be paid for "
                    + fieldName + ".\n\n" + "The Amount range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Pay the amount from the above mentioned range for " + fieldName + ":");
            }

            inputValue = InputUtils.handleDoubleInput(inputValue, lowerLimit - 1);
        }

        return inputValue;
    }

    // Error Message for Improper regex
    public static String regexMatchFailedError (String fieldName, String example)
    {
        return "Your " + fieldName + " didn't match with the validation regex.\n\n" + "Example: " + example + "\n\nEnter the input again, refer the above one:";
    }

    // Handles the app from getting any Exception

    public static char handleCharacterInput (String fieldName) {
        char inputOption = '\0';
        try
        {
            inputOption = scanner.nextLine().charAt(0);

            if (Character.isDigit(inputOption))
            {
                System.out.println("\nYou have entered a digit, enter any alphabet key");
                return '\0';
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
            System.out.println("\nYou have pressed the Enter Key");
             return '\0';
        }

        return Character.toLowerCase(inputOption);
    }


    private static double handleDoubleInput(double value, double defaultValue) {
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

    public static int handleIntegerInputMisMatches (int value, int defaultValue)
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

    public static String handleStringInputMisMatches (String value, String defaultValue)
    {
        String inputValue = value;
        try {
            inputValue = scanner.nextLine().trim();
        } catch (NullPointerException | IndexOutOfBoundsException e)
        {
            System.out.println("\nProvided input is invalid\nEg. name");
            inputValue = defaultValue;
        }

        return inputValue;
    }

    public static String handleNullStrings (String s)
    {
        return s == null ? "NONE" : s;
    }

    // Internal Messages

    public static<T> boolean hasSingleElement (List<T> list)
    {
        return list.size() == 1;
    }

    private static void showSingleItemMessage (String module, String name)
    {
        System.out.println("\nThere is only one " + module + ": " + name);
    }

    // Input getters

    public static int getCustomerNumber (List<Customer> customers)
    {
        int cusNo = 0;

        if (InputUtils.hasSingleElement(customers))
        {
            InputUtils.showSingleItemMessage("Customer", customers.get(0).getName());
        }
        else
        {
            InputUtils.showCustomers(customers);

            cusNo = getSerialNumberInput( customers.size(), "Customer") - 1;
        }

        return cusNo;
    }

    public static int getItemNumber (List<Item> items )
    {
        int itemNo = 0;

        if (InputUtils.hasSingleElement(items))
        {
            InputUtils.showSingleItemMessage("Item", items.get(0).getItemName());
        }
        else
        {
            InputUtils.showItems(items);

            itemNo = getSerialNumberInput( items.size(), "Item ") - 1;
        }

        return itemNo;
    }

    public static int getInvoiceNumber (List<Invoice> invoices)
    {
        int invNo = 0;

        if (InputUtils.hasSingleElement(invoices))
        {
            InputUtils.showSingleItemMessage("Invoice for ", invoices.get(0).getCustomer().getName());
        }
        else
        {
            InputUtils.showInvoices(invoices);

            invNo = getSerialNumberInput( invoices.size(), "Invoice") - 1;
        }

        return invNo;
    }

    private static int getSerialNumberInput (int upperLimit, String fieldName)
    {
        return InputUtils.getValidRange( 1, upperLimit, fieldName+ " Number", "Enter the " + fieldName + " Serial Number from the table: ");
    }

    // Comparisons
    public static int compareIntegers(int value1, int value2)
    {

        return value1 <= value2 ? 1 : -1;

    }

    public static int compareDoubles (double value1, double value2)
    {
        return value1 <= value2 ? 1 : -1;
    }

    public static int compareStrings (String s1, String s2)
    {

        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int s1Index = 0, s2Index = 0;

        while (s1Index < s1.length() && s2Index < s2.length())
        {
            if (s1.charAt(s1Index) < s2.charAt(s2Index))
            {
                return 1;
            }
            else if (s1.charAt(s1Index) > s2.charAt(s2Index))
            {
                return -1;
            } else
            {
                s1Index++;
                s2Index++;
            }
        }

        if (s1Index == s1.length()) {
            return 1;
        }

        return -1;
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

}