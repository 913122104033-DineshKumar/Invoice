package invoice;

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
    public static void showItems (List<Item> items) {
        System.out.println("-".repeat(noOfLines));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Price");
        System.out.println("-".repeat(noOfLines));

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    (i + 1),
                    item.getItemName(),
                    item.getPrice());
        }
        System.out.println("-".repeat(noOfLines));
    }

    public static void showCustomers (List<Customer> customers) {
        System.out.println("-".repeat(noOfLines));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Customer No", "Customer Name", "Customer Email");
        System.out.println("-".repeat(noOfLines));

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("| %-10d | %-25s | %-15s |%n",
                    (i + 1),
                    customer.getName(),
                    customer.getEmail());
        }
        System.out.println("-".repeat(noOfLines));
    }

    public static void showInvoices (List<Invoice> invoices) {
        System.out.println("-".repeat(noOfLines));
        System.out.printf("| %-10s | %-25s | %-15s | %-15s |%n",
                "Invoice No", "Customer Name", "Status", "Total");
        System.out.println("-".repeat(noOfLines));

        for (int i = 0; i < invoices.size(); i++) {
            Invoice invoice = invoices.get(i);
            System.out.printf("| %-10d | %-25s | %-15s | %-15s |%n",
                    (i + 1),
                    invoice.getCustomer().getName(),
                    invoice.getStatus(),
                    invoice.getTotal());
        }

        System.out.println("-".repeat(noOfLines));
    }

    public static String showStatuses () {
        StringBuilder sb = new StringBuilder("Status \n");
        for (int i = 0; i < invoiceStatus.size(); i++) {
            sb.append(invoiceStatus.get(i)).append(" -> ").append(i + 1).append("\n");
        }
        return sb.toString().trim();
    }

    // Validations
    public static char getValidOption (char option, char option1, char option2, Scanner scanner, String errorMessage) {
        char inputOption = option;
        while (inputOption != option1 && inputOption != option2) {
            System.out.println(errorMessage);
            inputOption = scanner.nextLine().charAt(0);
        }
        System.out.println("Utils");
        return inputOption;
    }

    // Overloading the Valid function for String Types
    public static String getValidInput (String s, String regex, Scanner scanner, String errorMessage) {
        String ans = s;
        while (ans.isEmpty() || !ans.matches(regex)) {
            System.out.println(errorMessage);
            ans = scanner.nextLine();
        }
        return ans;
    }

    // Overloading the Valid function for Integer Types
    public static int getValidInput (int option, int option1, int option2, Scanner scanner, String errorMessage) {
        int inputOption = option;

        while (inputOption < option1 || inputOption > option2) {
            System.out.println(errorMessage);
            inputOption = scanner.nextInt();
        }

        return inputOption;
    }

    // Overloading the Valid function for Double Types
    public static double getValidInput (double option, double option1, double option2, Scanner scanner, String errorMessage) {
        double inputOption = option;

        while (inputOption < option1 || inputOption > option2) {
            System.out.println(errorMessage);
            inputOption = scanner.nextDouble();
        }

        return inputOption;
    }

    public static void printLines (int repeatTime) {
        System.out.println("-".repeat(repeatTime));
    }

}
