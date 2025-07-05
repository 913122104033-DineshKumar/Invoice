package invoice.src;

import invoice.utils.ItemUtil;
import invoice.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class Item {
    private static int totalItems = 0;

    // Primary Details
    private int itemNo;
    private char itemType;
    private char itemUnit;
    private String itemName;
    private Boolean isTaxable;
    private double price;
    private String description;

    // Tax Rates
    private double intraTaxRate;
    private double interTaxRate;

    private static ItemUtil itemUtil;

    public Item (char itemType, char itemUnit, String itemName, double price, String description, boolean isTaxable, double intraTaxRate, double interTaxRate) {
        this.itemType = itemType;
        this.itemUnit = itemUnit;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        totalItems++;
        this.itemNo = totalItems;
        this.isTaxable = isTaxable;
        this.intraTaxRate = intraTaxRate;
        this.interTaxRate = interTaxRate;
    }

    public static Item create (Scanner scanner) {
        System.out.println("\nYou can add Item now");

        if (itemUtil == null) {
            itemUtil = new ItemUtil(scanner);
        }

        // Item Type Input
        char itemType = itemUtil.getItemTypeInput();

        // Item Unit Input
        char itemUnit = itemUtil.getItemUnitInput();

        // Item Name Input
        String itemName = itemUtil.getItemNameInput();

        double price = itemUtil.getPriceInput();

        String nDescription = itemUtil.getDescription(true);

        double[] taxableDetails = itemUtil.getTaxableInput(true, false);

        return new Item(itemType, itemUnit, itemName, price, nDescription, taxableDetails[0] == 1, taxableDetails[1], taxableDetails[2]);
    }

    public void update (Scanner scanner) {
        while (true) {
            System.out.println("\nOption 1 -> Updating Item Details");
            System.out.println("Option 2 -> Updating Item Tax Details");
            System.out.println("Option 3 -> To exit the update");
            System.out.println("-".repeat(30));

            System.out.println("\nEnter the option: ");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 3) {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option) {
                case 1:
                    updateItemDetails(scanner);
                    break;

                case 2:
                    updateItemTaxes(scanner);
                    break;

                default:
                    System.out.println("\nEnter a valid input (1 - 3)...");
                    break;
            }
        }
    }

    // Items Primary Details
    private void updateItemDetails (Scanner scanner) {
        while (true) {
            System.out.println("\nOption 1 -> Updating Item Type");
            System.out.println("Option 2 -> Updating Item Name");
            System.out.println("Option 3 -> Updating Item Unit");
            System.out.println("Option 4 -> Updating Item Price");
            System.out.println("Option 5 -> Updating Item Description");
            System.out.println("Option 6 -> For exit");
            System.out.println("-".repeat(30));

            System.out.println("\nEnter the option: ");
            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 6) {
                System.out.println("\nExiting the Customer Module...");
                break;
            }

            switch (option) {

                case 1:
                    char previousItemType = this.itemType;

                    char nItemType = itemUtil.getItemTypeInput();

                    this.setItemType(nItemType);

                    System.out.println("\nItem's Type updated from " + getItemType(itemType) + " to " + nItemType );
                    break;

                case 2:
                    String previousName = this.itemName;

                    String updatedItemName = itemUtil.getItemNameInput();

                    this.setItemName(updatedItemName);

                    System.out.println("\nItem's Description updated from " + previousName + " to " + updatedItemName );

                    break;

                case 3:
                    char previousItemUnit = this.itemUnit;

                    char nItemUnit = itemUtil.getItemUnitInput();

                    this.setItemUnit(nItemUnit);

                    System.out.println("\nItem's Unit updated from " + getItemUnit(itemUnit) + " to " + nItemUnit );

                    break;

                case 4:
                    double previousPrice = this.price;

                    double updatedPrice = itemUtil.getPriceInput();

                    this.setPrice(updatedPrice);

                    System.out.println("\nItem's Price updated from " + previousPrice + " to " + updatedPrice );
                    break;

                case 5:
                    String previousDescription = this.description;

                    String nDescription = itemUtil.getDescription(false);

                    this.setDescription(nDescription);

                    System.out.println("\nItem's Description updated from " + previousDescription + " to " + nDescription );
                    break;

                default:
                    System.out.println("\nEnter a valid input (1 - 6)");
                    break;
            }
        }
    }

    // Item's Tax Details
    private void updateItemTaxes (Scanner scanner) {
        while (true) {
            System.out.println("\nOption 1 -> Updating Taxable");
            System.out.println("Option 2 -> Updating Intra State Tax Rate");
            System.out.println("Option 3 -> Updating Inter State Tax Rate");
            System.out.println("Option 4 -> For exit");

            System.out.println("Enter Option: ");

            int option = -1;
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            if (option == 4) {
                System.out.println("\nExiting the Items Tax Details Module...");
                break;
            }

            switch (option) {
                case 1:

                    double[] taxableDetails = itemUtil.getTaxableInput(false, this.isTaxable);

                    this.setIsTaxable(taxableDetails[0] == 1);
                    this.setIntraTaxRate(taxableDetails[1]);
                    this.setInterTaxRate(taxableDetails[2]);

                    System.out.println("\nTaxable has been updated to " + (this.isTaxable ? "true" : "false"));
                    break;

                case 2:
                    if (!this.isTaxable) {
                        System.out.println("\nThis item is not taxable...");
                        break;
                    }

                    double previousIntraTaxRate = this.intraTaxRate;

                    System.out.println("\n: ");

                    double nIntraTaxRate = Utils.getValidDoubleInput( 0, scanner, "Intra Tax Rate", "Enter the Intra Tax Rate:");

                    this.setIntraTaxRate(nIntraTaxRate);

                    System.out.println("\nIntra State Tax Rate updated from " + previousIntraTaxRate + " to " + nIntraTaxRate );
                    break;

                case 3:
                    if (!isTaxable) {
                        System.out.println("\nThis item is not taxable...");
                        break;
                    }

                    double previousInterTaxRate = this.interTaxRate;

                    double nInterTaxRate = Utils.getValidDoubleInput( 0,  scanner, "Inter Tax Rate", "Enter the Inter Tax Rate:");

                    this.setInterTaxRate(nInterTaxRate);

                    System.out.println("\nInter State Tax Rate updated from " + previousInterTaxRate + " to " + nInterTaxRate );
                    break;

                default:
                    System.out.println("\nEnter a valid input (1 - 4)");
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

    public static int getTotalItems () {
        return totalItems;
    }

    public char getItemType() {
        return itemType;
    }

    public void setItemType(char itemType) {
        this.itemType = itemType;
    }

    public char getItemUnit () {
        return itemUnit;
    }

    public void setItemUnit(char itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getIsTaxable() {
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

    public void setItemNo (int itemNo) {
        this.itemNo = itemNo;
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

    private String getItemType (char itemType) {
        return switch (itemType) {
            case 'G', 'g' -> "Goods";
            default -> "Services";
        };
    }

    private String getItemUnit (char itemUnit) {
        return switch(itemUnit) {
            case 'P', 'p' -> "Pieces";
            case 'M', 'm' -> "Meters";
            case 'B', 'b' -> "Box";
            default -> "None";
        };
    }

    public void showItem() {
        Utils.printLines(147);

        // Print header
        System.out.printf("| %15s | %20s | %15s | %15s | %20s | %20s | %15s | %25s |\n",
                "Item Number",
                "Item Name",
                "Item Type",
                "Item Unit",
                "Intra State Tax Rate",
                "Inter State Tax Rate",
                "Item Price",
                "Item Description");

        Utils.printLines(147);

        // Print item data
        System.out.printf("| %15s | %20s | %15s | %15s | %20s | %20s | %15s | %25s |\n",
                itemNo,
                itemName,
                getItemType(itemType),
                getItemUnit(itemUnit),
                String.format("%.2f%%", intraTaxRate),
                String.format("%.2f%%", interTaxRate),
                String.format("Rs.%.2f", price),
                description == null ? "No Description provided" : description);

        Utils.printLines(147);
    }

}
