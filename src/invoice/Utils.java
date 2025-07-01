package invoice;

import java.util.*;

public class Utils {

    public static Map<Character, String> itemTypes;
    public static Map<Character, String> itemUnits;
    public static Map<Character, String> customerTypes;
    public static List<String> invoiceStatus;

    public Utils () {
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

    public static void showItems (List<Item> items) {
        System.out.println("-".repeat(20));
        System.out.print("|");
        System.out.print(" S.No |");
        System.out.print(" Item Name |");
        System.out.print(" Price ");
        System.out.println("|");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.print("|");
            System.out.print(" " + i + " |");
            System.out.print(" " + item.getItemName() + " |");
            System.out.print(" " + item.getPrice() + " ");
            System.out.println("|");
        }
        System.out.println("-".repeat(20));
    }

    public static void showCustomers (List<Customer> customers) {
        System.out.println("-".repeat(20));
        System.out.print("|");
        System.out.print(" S.No |");
        System.out.print(" Customer No |");
        System.out.println(" Customer Name |");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.print("|");
            System.out.print(" " + i + " |");
            System.out.print(" " + customer.getCusNo() + " |");
            System.out.println(" " + customer.getName() + " |");
        }
        System.out.println("-".repeat(20));
    }

    public static boolean optionValidation (char inputOption, char option1, char option2) {
        return inputOption != option1 && inputOption != option2;
    }

    public static String getValidInput (String s, String regex, Scanner scanner, String errorMessage) {
        String ans = s;
        while (ans.isEmpty() || !ans.matches(regex)) {
            System.out.println(errorMessage);
            ans = scanner.nextLine();
        }
        return ans;
    }

}
