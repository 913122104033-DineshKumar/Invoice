package invoice;

import java.util.Scanner;

public class ItemUtil {

    private static final String NAME_REGEX = "[a-zA-Z\\s]+";
    private final Scanner scanner;

    public ItemUtil (Scanner scanner) {
        this.scanner = scanner;
    }

    public String getItemTypeInput () {
        char itemTypeOption = 'A';
        itemTypeOption = Utils.getValidOption(itemTypeOption, 'G', 'S',
                scanner, "Item Type: Goods -> G, Service -> S");
        return itemTypeOption == 'G' ? "GOODS" : "SERVICES";
    }

    public String getItemUnitInput () {
        char itemUnitOption = scanner.nextLine().charAt(0);
        while (itemUnitOption != 'P' && itemUnitOption != 'B' && itemUnitOption != 'M' && itemUnitOption != 'N') {
            System.out.println("Enter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N): ");
            itemUnitOption = scanner.nextLine().charAt(0);
        }
        return switch (itemUnitOption) {
            case 'P' -> "PIECES";
            case 'B' -> "BOX";
            case 'M' -> "METERS";
            default -> "NONE";
        };
    }

    public String getItemNameInput () {
        String itemName = "";
        itemName = Utils.getValidInput(itemName, NAME_REGEX, scanner, "Enter the Valid Item Name (Eg. Punam Saree)");
        return itemName;
    }

    public double[] getTaxableInput () {
        double isTaxable = 0, intraTaxRate = -1, interTaxRate = -1;
        char taxableOption = 'A';
        taxableOption = Utils.getValidOption(taxableOption, 'Y', 'N',
                scanner, "Is tax applied for this item, if Yes -> Y, No -> N");
        if (taxableOption == 'Y') {
            isTaxable = 1;

            intraTaxRate = Utils.getValidInput(intraTaxRate, 0.1, 28, scanner, "Enter the Intra Tax Rate (0 - 28)% :");
            scanner.nextLine();

            interTaxRate = Utils.getValidInput(interTaxRate, 0.1, 28, scanner, "Enter the Inter Tax Rate (0 - 28)% :");
            scanner.nextLine();
        } else {
            intraTaxRate = 0;
            interTaxRate = 0;
        }
        return new double[]{ isTaxable, interTaxRate, intraTaxRate };
    }

    public double getPriceInput () {
        double price = -1;
        price = Utils.getValidInput(price, 50, Integer.MAX_VALUE, scanner, "Enter the Selling price of the item (Can't be Negative):");
        scanner.nextLine();
        return price;
    }

    public String getDescription (Scanner scanner) {
        String description = "";

        char descriptionOption = 'A';

        descriptionOption = Utils.getValidOption(descriptionOption, 'Y', 'N',
                scanner, "Would you like to enter the description for the item, if yes -> Y, No -> N");

        if (descriptionOption == 'Y') {
            description = Utils.getValidInput(description, NAME_REGEX, scanner, "Enter the description (Can't have digits in it):");
        }

        return description;
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

}
