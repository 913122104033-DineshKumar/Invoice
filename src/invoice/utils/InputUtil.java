package invoice.utils;

import invoice.handlers.InvoiceHandler;
import invoice.models.Customer;
import invoice.models.Invoice;
import invoice.models.Item;

import java.time.LocalDate;
import java.util.*;

public class InputUtil
{

    private static Scanner scanner;

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
            scanner = null;
        }
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


    public static double handleDoubleInput(double value, double defaultValue) {
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
        catch (InputMismatchException i)
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

    // Input getters

    public static int getCustomerNumber (List<Customer> customers)
    {
        int cusNo = 0;

        if (InputUtil.hasSingleElement(customers))
        {
            InputUtil.showSingleItemMessage("Customer", customers.get(0).getName());
        }
        else
        {
            DisplayUtil.showCustomers(customers);

            cusNo = getSerialNumberInput( customers.size(), "Customer") - 1;
        }

        return cusNo;
    }

    public static int getItemNumber (List<Item> items )
    {
        int itemNo = 0;

        if (InputUtil.hasSingleElement(items))
        {
            InputUtil.showSingleItemMessage("Item", items.get(0).getItemName());
        }
        else
        {
            DisplayUtil.showItems(items);

            itemNo = getSerialNumberInput( items.size(), "Item ") - 1;
        }

        return itemNo;
    }

    public static int getInvoiceNumber (List<Invoice> invoices)
    {
        int invNo = 0;

        if (InputUtil.hasSingleElement(invoices))
        {
            InputUtil.showSingleItemMessage("Invoice for ", invoices.get(0).getCustomer().getName());
        }
        else
        {
            DisplayUtil.showInvoices(invoices);

            invNo = getSerialNumberInput( invoices.size(), "Invoice") - 1;
        }

        return invNo;
    }

    private static int getSerialNumberInput (int upperLimit, String fieldName)
    {
        return ValidationUtil.getValidRange( 1, upperLimit, fieldName.trim() + " Number", "Enter the " + fieldName.trim() + " Serial Number from the table: ");
    }

    public static int getIndexForItemId (String itemId, List<Item> items)
    {
        for (int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            if (item.getItemId().equalsIgnoreCase(itemId))
            {
                return i;
            }
        }
        return -1;
    }

    public static int getIndexForCustomerId (String itemId, List<Customer> customers)
    {
        for (int i = 0; i < customers.size(); i++)
        {
            Customer customer = customers.get(i);
            if (customer.getCustomerId().equalsIgnoreCase(itemId))
            {
                return i;
            }
        }
        return -1;
    }

    private static void showSingleItemMessage (String module, String name)
    {
        System.out.println("\nThere is only one " + module + ": " + name);
    }

}