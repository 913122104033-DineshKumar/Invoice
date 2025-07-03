package invoice.utils;

import java.util.Scanner;

public class ItemUtil {

    private static final String NAME_REGEX = "[a-zA-Z\\s]+";
    private final Scanner scanner;

    public ItemUtil (Scanner scanner) {
        this.scanner = scanner;
    }

    public String getItemTypeInput () {
        char itemTypeOption = 'A';

        System.out.println("\nEnter a Item Type (G,g  -> Goods, S, s -> Services):");

        String[][] availableOptions = { {"G", "GOODS"}, {"S", "SERVICES"} };

        itemTypeOption = Utils.handleOptionStringOutOfBoundError(scanner, itemTypeOption, "Item Type Option");

        itemTypeOption = Character.toUpperCase(itemTypeOption);

        itemTypeOption = Utils.getValidOption(itemTypeOption, 'G', 'S',
                scanner, "Item Type Option", availableOptions);
        return itemTypeOption == 'G' ? "GOODS" : "SERVICES";
    }

    public String getItemUnitInput () {
        System.out.println("\nEnter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N): ");
        char itemUnitOption = '\0';
        String[][] availableOptions = { { "P", "PIECES" }, { "B", "BOX" }, { "M", "METERS" }, { "N", "NONE" } };

        itemUnitOption = Utils.handleOptionStringOutOfBoundError(scanner, itemUnitOption, "Item Unit Option");

        itemUnitOption = Character.toUpperCase(itemUnitOption);

        while (itemUnitOption != 'P' && itemUnitOption != 'B' && itemUnitOption != 'M' && itemUnitOption != 'N' && itemUnitOption != 'p' && itemUnitOption != 'b' && itemUnitOption != 'm' && itemUnitOption != 'n') {
            System.out.println("\n" + ErrorUtils.optionError("Item Unit Option", availableOptions));
            itemUnitOption = Utils.handleOptionStringOutOfBoundError(scanner, itemUnitOption, "Item Unit Option");
            itemUnitOption = Character.toUpperCase(itemUnitOption);
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

        System.out.println("\nEnter the Item Name: ");
        itemName = scanner.nextLine();

        itemName = Utils.getValidInput(itemName, NAME_REGEX, scanner, "Eg. Punam Saree", "Item Name");
        return itemName;
    }

    public double[] getTaxableInput () {
        double isTaxable = 0, intraTaxRate = -1, interTaxRate = -1;
        char taxableOption = 'A';

        System.out.println("\nIs Taxable applied\nY -> Yes\nN -> No");
        taxableOption = Utils.handleOptionStringOutOfBoundError(scanner, taxableOption, "Taxable Option");

        taxableOption = Character.toUpperCase(taxableOption);

        String[][] availableOptions = { {"Y", "Yes"}, {"N", "No"} };

        taxableOption = Utils.getValidOption(taxableOption, 'Y', 'N',
                scanner, "Tax Option", availableOptions);
        if (taxableOption == 'Y') {
            isTaxable = 1;

            System.out.println("Enter the Intra Tax Rate: ");
            intraTaxRate = Utils.handleIntegerInputMisMatches(intraTaxRate, scanner);

            intraTaxRate = Utils.getValidInput(intraTaxRate, 0.1, 28, scanner, "Intra Tax Rate");

            System.out.println("Enter the Inter Tax Rate: ");
            interTaxRate = Utils.handleIntegerInputMisMatches(interTaxRate, scanner);

            interTaxRate = Utils.getValidInput(interTaxRate, 0.1, 28, scanner, "Inter Tax Rate");
        } else {
            intraTaxRate = 0;
            interTaxRate = 0;
        }
        return new double[]{ isTaxable, intraTaxRate, interTaxRate };
    }

    public double getPriceInput () {

        System.out.println("\nEnter the Item Selling Price: ");

        double price = -1;
        price = Utils.handleIntegerInputMisMatches(price, scanner);

        price = Utils.getValidInput(price, 0.1, Integer.MAX_VALUE, scanner, "Selling Price");

        return price;
    }

    public String getDescription (boolean isCreation) {
        String description = "";
        if (isCreation) {
            char descriptionOption = 'A';

            System.out.println("\nWant to Write Description\nY -> Yes\nN -> No");
            descriptionOption = Utils.handleOptionStringOutOfBoundError(scanner, descriptionOption, "Description Option");

            descriptionOption = Character.toUpperCase(descriptionOption);

            String[][] availableOptions = { {"Y", "Yes"}, {"N", "No"} };

            descriptionOption = Utils.getValidOption(descriptionOption, 'Y', 'N',
                    scanner, "Description", availableOptions);

            if (descriptionOption == 'Y') {

                System.out.println("\nEnter the Description: ");
                description = scanner.nextLine();

                description = Utils.getValidInput(description, NAME_REGEX, scanner, "Nice Saree", "Item Description");
            }
        } else {
            System.out.println("\nEnter the Description: ");
            description = scanner.nextLine();

            description = Utils.getValidInput(description, NAME_REGEX, scanner, "Nice Saree", "Item Description");
        }

        return description;
    }

}
