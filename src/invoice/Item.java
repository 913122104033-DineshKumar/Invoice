package invoice;

import java.util.List;
import java.util.Scanner;

public class Item {
    private static int totalItems = 0;

    // Constant Regexes
    private static final String NAME_REGEX = "[a-zA-Z\\s]+";

    // Primary Details
    private final int itemNo;
    private String itemType;
    private String itemUnit;
    private String itemName;
    private boolean isTaxable;
    private double price;
    private String description;

    // Tax Rates
    private double intraTaxRate;
    private double interTaxRate;

    public Item (String itemType, String itemUnit, String itemName, boolean isTaxable, double price, String description, double intraTaxRate, double interTaxRate) {
        this.itemType = itemType;
        this.itemUnit = itemUnit;
        this.itemName = itemName;
        this.isTaxable = isTaxable;
        this.price = price;
        this.description = description;
        this.intraTaxRate = intraTaxRate;
        this.interTaxRate = interTaxRate;
        totalItems++;
        this.itemNo = totalItems;
    }

    public static Item create () {
        System.out.println("You can add Item now");
        Scanner scanner = new Scanner(System.in);
        String itemType;
        String itemUnit;
        boolean isTaxable = false;
        double intraTaxRate = -1;
        double interTaxRate = -1;

        // Item Type Input
        char itemTypeOption = 'A';
        itemTypeOption = Utils.getValidOption(itemTypeOption, 'G', 'S',
                scanner, "Item Type: Goods -> G, Service -> S");
        itemType = Utils.itemTypes.get(itemTypeOption);

        // Item Unit Input
        System.out.println("Item Unit: Pieces -> P, Meters -> M, Box -> B, Undefined -> N");
        char itemUnitOption = scanner.nextLine().charAt(0);
        while (itemUnitOption != 'P' && itemUnitOption != 'B' && itemUnitOption != 'M' && itemUnitOption != 'N') {
            System.out.println("Enter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N): ");
            itemUnitOption = scanner.nextLine().charAt(0);
        }
        itemUnit = Utils.itemUnits.get(itemUnitOption);

        // Item Name Input
        String itemName = "";
        itemName = Utils.getValidInput(itemName, NAME_REGEX, scanner, "Enter the Valid Item Name (Eg. Punam Saree)");

        char taxableOption = 'A';
        taxableOption = Utils.getValidOption(taxableOption, 'Y', 'N',
                scanner, "Is tax applied for this item, if Yes -> Y, No -> N");
        if (taxableOption == 'Y') {
            isTaxable = true;

            intraTaxRate = Utils.getValidInput(intraTaxRate, 0.1, 28, scanner, "Enter the Intra Tax Rate (0 - 28)% :");
            scanner.nextLine();

            interTaxRate = Utils.getValidInput(interTaxRate, 0.1, 28, scanner, "Enter the Inter Tax Rate (0 - 28)% :");
            scanner.nextLine();
        } else {
            intraTaxRate = 0;
            interTaxRate = 0;
        }

        double price = -1;
        price = Utils.getValidInput(price, 50, Integer.MAX_VALUE, scanner, "Enter the Selling price of the item (Can't be Negative):");
        scanner.nextLine();

        String nDescription = getDescription(scanner);

        return new Item(itemType, itemUnit, itemName, isTaxable,  price, nDescription, intraTaxRate, interTaxRate);
    }

    public void update () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("Option 1 -> Updating Item Details");
            System.out.println("Option 2 -> Updating Item Tax Details");
            System.out.println("Option 3 -> To exit the update");
            System.out.println("-".repeat(30));
            System.out.println("Enter the option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    updateItemDetails();
                    break;
                case 2:
                    updateItemTaxes();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter a valid input (1 - 3)...");
                    break;
            }
        }
    }

    // Items Primary Details
    private void updateItemDetails () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("Option 1 -> Updating Item Type");
            System.out.println("Option 2 -> Updating Item Name");
            System.out.println("Option 3 -> Updating Item Unit");
            System.out.println("Option 4 -> Updating Item Price");
            System.out.println("Option 5 -> Updating Item Description");
            System.out.println("Option 6 -> For exit");
            System.out.println("-".repeat(30));
            System.out.println("Enter the option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:

                    char itemTypeOption = 'A';

                    itemTypeOption = Utils.getValidOption(itemTypeOption, 'G', 'S',
                            scanner, "Item Type: Goods -> G, Service -> S");

                    this.setItemType(Utils.itemTypes.get(itemTypeOption));

                    System.out.println("Updated Item Type");
                    break;
                case 2:
                    System.out.println("Enter the Updated Item Name: ");

                    String updatedItemName = scanner.nextLine();

                    updatedItemName = Utils.getValidInput(updatedItemName, NAME_REGEX, scanner, "Enter the Valid Item Name (Eg. Punam Saree)");

                    this.setItemName(updatedItemName);

                    System.out.println("Updated Item Name");
                    break;
                case 3:
                    char itemUnitOption = 'A';

                    while (itemUnitOption != 'P' && itemUnitOption != 'B' && itemUnitOption != 'M' && itemUnitOption != 'N') {
                        System.out.println("Enter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N): ");
                        itemUnitOption = scanner.nextLine().charAt(0);
                    }

                    this.setItemUnit(Utils.itemUnits.get(itemUnitOption));
                    break;
                case 4:
                    double updatedPrice = -1;

                    updatedPrice = Utils.getValidInput(updatedPrice, 50, Integer.MAX_VALUE, scanner, "Enter the Selling price of the item (Can't be Negative):");

                    scanner.nextLine();

                    this.setPrice(updatedPrice);

                    System.out.println("Updated Item Price");
                    break;
                case 5:
                    this.setDescription(getDescription(scanner));

                    System.out.println("Updated Item Description");
                    break;
                case 6:
                    System.out.println(this.itemName + " is updated");

                    isUpdating = false;

                    break;
                default:
                    System.out.println("Enter a valid input (1 - 6)");
                    break;
            }
        }
    }

    // Item's Tax Details
    private void updateItemTaxes () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("Option 1 -> Updating Taxable");
            System.out.println("Option 2 -> Updating Intra State Tax Rate");
            System.out.println("Option 3 -> Updating Inter State Tax Rate");
            System.out.println("Option 4 -> For exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:

                    char taxableOption = 'A';
                    taxableOption = Utils.getValidOption(taxableOption, 'Y', 'N', scanner, "Is tax applied for this item, if Yes -> Y, No -> N");

                    if (taxableOption == 'N') {
                        this.setIsTaxable(false);
                        this.setInterTaxRate(0);
                        this.setIntraTaxRate(0);
                    } else {
                        this.setIsTaxable(true);

                        double nIntraTaxRate = -1;
                        nIntraTaxRate = Utils.getValidInput(nIntraTaxRate, 0.1, 28, scanner, "Enter the Intra Tax Rate (0 - 28)% :");

                        this.setIntraTaxRate(nIntraTaxRate);

                        double nInterTaxRate = -1;
                        nInterTaxRate = Utils.getValidInput(nInterTaxRate, 0.1, 28, scanner, "Enter the Inter Tax Rate (0 - 28)% :");

                        this.setInterTaxRate(nInterTaxRate);

                        scanner.nextLine();
                    }
                    System.out.println("Updated Taxable");
                    break;
                case 2:
                    if (!isTaxable) {
                        System.out.println("This item is not taxable...");
                        break;
                    }

                    double nIntraTaxRate = -1;
                    nIntraTaxRate = Utils.getValidInput(nIntraTaxRate, 0.1, 28, scanner, "Enter the Intra Tax Rate (0 - 28)% :");
                    scanner.nextLine();

                    this.setIntraTaxRate(nIntraTaxRate);

                    System.out.println("Updated Intra State Tax Rate");
                    break;
                case 3:
                    if (!isTaxable) {
                        System.out.println("This item is not taxable...");
                        break;
                    }

                    double nInterTaxRate = -1;
                    nInterTaxRate = Utils.getValidInput(nInterTaxRate, 0.1, 28, scanner, "Enter the Inter Tax Rate (0 - 28)% :");
                    scanner.nextLine();

                    this.setInterTaxRate(nInterTaxRate);

                    System.out.println("Updated Inter State Tax Rate");
                    break;
                case 4:
                    System.out.println(this.itemName + "'s taxes are updated");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter a valid input (1 - 4)");
                    break;
            }
        }
    }

    public static Item search (String itemName, List<Item> items) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(boolean taxable) {
        isTaxable = taxable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getIntraTaxRate() {
        return intraTaxRate;
    }

    public void setIntraTaxRate(double intraTaxRate) {
        this.intraTaxRate = intraTaxRate;
    }

    public double getInterTaxRate() {
        return interTaxRate;
    }

    public void setInterTaxRate(double interTaxRate) {
        this.interTaxRate = interTaxRate;
    }

    public int getItemNo () {
        return itemNo;
    }

    public double getRate (int quantity, boolean withinState) {
        double baseRate = this.price;
        if (withinState) {
            baseRate += (this.price / 100) * this.intraTaxRate;
        } else {
            baseRate += (this.price / 100) * this.interTaxRate;
        }
        return baseRate * quantity;
    }

    private static String getDescription (Scanner scanner) {
        String descript = "";

        char descriptionOption = 'A';

        descriptionOption = Utils.getValidOption(descriptionOption, 'Y', 'N',
                scanner, "Would you like to enter the description for the item, if yes -> Y, No -> N");

        if (descriptionOption == 'Y') {
            descript = Utils.getValidInput(descript, NAME_REGEX, scanner, "Enter the description (Can't have digits in it):");
        }

        return descript;
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append("Item Details:\n");
        sb.append("================\n");
        sb.append("Item Number: ").append(itemNo).append("\n");
        sb.append("Item Name: ").append(itemName).append("\n");
        sb.append("Item Type: ").append(itemType).append("\n");
        sb.append("Item Unit: ").append(itemUnit).append("\n");

        if (isTaxable) {
            sb.append("Intra State Tax Rate: ").append(intraTaxRate).append("\n");
            sb.append("Inter State Tax Rate: ").append(interTaxRate).append("\n");
        }

        if (description != null && !description.trim().isEmpty()) {
            sb.append("Description: ").append(description).append("\n");
        }

        sb.append("Price: ").append(price).append("\n");

        return sb.toString();
    }

}
