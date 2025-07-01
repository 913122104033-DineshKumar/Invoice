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
        double price;
        boolean isTaxable = false;
        String description = "";
        double intraTaxRate = 0;
        double interTaxRate = 0;

        System.out.println("Item Type: Goods -> G, Service -> S");
        char itemTypeOption = scanner.nextLine().charAt(0);
        while (Utils.optionValidation(itemTypeOption, 'G', 'S')) {
            System.out.println("Provide a proper type (G or S): ");
            itemTypeOption = scanner.nextLine().charAt(0);
        }
        itemType = Utils.itemUnits.get(itemTypeOption);

        System.out.println("Item Unit: Pieces -> P, Meters -> M, Box -> B, Undefined -> N");
        char itemUnitOption = scanner.nextLine().charAt(0);
        while (Utils.optionValidation(itemUnitOption, 'P', 'M') && Utils.optionValidation(itemUnitOption, 'B', 'N')) {
            System.out.println("Enter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N): ");
            itemUnitOption = scanner.nextLine().charAt(0);
        }
        itemUnit = Utils.itemUnits.get(itemUnitOption);

        String itemName = "";
        itemName = Utils.getValidInput(itemName, NAME_REGEX, scanner, "Enter the Valid Item Name (Eg. Punam Saree)");

        System.out.println("Is tax applied for this item, if Yes -> Y, No -> N");
        char taxableOption = scanner.nextLine().charAt(0);
        while (Utils.optionValidation(taxableOption, 'Y', 'N')) {
            taxableOption = scanner.nextLine().charAt(0);
        }
        if (taxableOption == 'Y') {
            isTaxable = true;
            System.out.println("Enter the Intra State tax: ");
            intraTaxRate = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter the Inter State tax: ");
            interTaxRate = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.println("Enter the Selling price of the item: ");
        price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Would you like to enter the description for the item, if yes -> Y, No -> N");
        char descriptionOption = scanner.nextLine().charAt(0);
        while (Utils.optionValidation(descriptionOption, 'Y', 'N')) {
            descriptionOption = scanner.nextLine().charAt(0);
        }
        if (descriptionOption == 'Y') {
            System.out.println("Enter the description: ");
            description = scanner.nextLine();
        }

        return new Item(itemType, itemUnit, itemName, isTaxable,  price, description, intraTaxRate, interTaxRate);
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
                    System.out.println("Item Type: Goods -> G, Service -> S");

                    char itemTypeOption = scanner.nextLine().charAt(0);

                    while (Utils.optionValidation(itemTypeOption, 'G', 'S')) {
                        System.out.println("Provide a proper type (G or S): ");
                        itemTypeOption = scanner.nextLine().charAt(0);
                    }

                    if (itemTypeOption == 'G') {
                        itemType = "Goods";
                    } else {
                        itemType = "Services";
                    }

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
                    char itemUnitOption = scanner.nextLine().charAt(0);

                    while (Utils.optionValidation(itemUnitOption, 'P', 'M') || Utils.optionValidation(itemUnitOption, 'B', 'N')) {
                        System.out.println("Enter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N): ");
                        itemUnitOption = scanner.nextLine().charAt(0);
                    }

                    String uItemUnit = Utils.itemUnits.get(itemUnitOption);
                    this.setItemUnit(uItemUnit);
                    break;
                case 4:
                    System.out.println("Enter the Updated Item Price: ");

                    double updatedPrice = scanner.nextDouble();
                    scanner.nextLine();

                    this.setPrice(updatedPrice);

                    System.out.println("Updated Item Price");
                    break;
                case 5:
                    System.out.println("Enter the Item Description: ");

                    String nDescription = scanner.nextLine();

                    if (!nDescription.trim().isEmpty()) {
                        this.setDescription(nDescription);
                    }

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
                    System.out.println("Is tax applied for this item, if Yes -> Y, No -> N");

                    char taxableOption = scanner.nextLine().charAt(0);
                    while (Utils.optionValidation(taxableOption, 'Y', 'N')) {
                        taxableOption = scanner.nextLine().charAt(0);
                    }

                    if (taxableOption == 'N') {
                        this.setIsTaxable(false);
                        this.setInterTaxRate(0);
                        this.setIntraTaxRate(0);
                    } else {
                        this.setIsTaxable(true);

                        System.out.println("Enter the Intra State Tax Rate(%): ");

                        double nIntraTaxRate = scanner.nextDouble();
                        this.setIntraTaxRate(nIntraTaxRate);

                        System.out.println("Enter the Inter State Tax Rate(%): ");

                        double nInterTaxRate = scanner.nextDouble();
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

                    System.out.println("Enter Intra State Tax Rate(%): ");

                    double nIntraStateTaxRate = scanner.nextDouble();
                    scanner.nextLine();

                    this.setIntraTaxRate(nIntraStateTaxRate);

                    System.out.println("Updated Intra State Tax Rate");
                    break;
                case 3:
                    if (!isTaxable) {
                        System.out.println("This item is not taxable...");
                        break;
                    }

                    System.out.println("Enter Inter State Tax Rate(%): ");

                    double nInterStateTaxRate = scanner.nextDouble();
                    scanner.nextLine();

                    this.setInterTaxRate(nInterStateTaxRate);

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

    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append("Item Details:\n");
        sb.append("================\n");
        sb.append("Item Number: ").append(itemNo).append("\n");
        sb.append("Item Name: ").append(itemName).append("\n");
        sb.append("Item Type: ").append(itemType).append("\n");

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
