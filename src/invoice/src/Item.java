package invoice.src;

import invoice.utils.ItemUtil;
import invoice.utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

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

    private static ItemUtil itemUtil;

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
        System.out.println("\nYou can add Item now");
        Scanner scanner = new Scanner(System.in);

        if (itemUtil == null) {
            itemUtil = new ItemUtil(scanner);
        }

        // Item Type Input
        String itemType = itemUtil.getItemTypeInput();

        // Item Unit Input
        String itemUnit = itemUtil.getItemUnitInput();

        // Item Name Input
        String itemName = itemUtil.getItemNameInput();

        double[] taxList = itemUtil.getTaxableInput();

        boolean isTaxable = taxList[0] == 1;

        double intraTaxRate = taxList[1];
        double interTaxRate = taxList[2];

        double price = itemUtil.getPriceInput();

        String nDescription = itemUtil.getDescription(true);

        return new Item(itemType, itemUnit, itemName, isTaxable,  price, nDescription, intraTaxRate, interTaxRate);
    }

    public void update () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("\nOption 1 -> Updating Item Details");
            System.out.println("Option 2 -> Updating Item Tax Details");
            System.out.println("Option 3 -> To exit the update");
            System.out.println("-".repeat(30));

            System.out.println("\nEnter the option: ");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    updateItemDetails();
                    break;
                case 2:
                    updateItemTaxes();
                    break;
                case 3:
                    System.out.println("\nExiting...");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("\nEnter a valid input (1 - 3)...");
                    break;
            }
        }
    }

    // Items Primary Details
    private void updateItemDetails () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("\nOption 1 -> Updating Item Type");
            System.out.println("Option 2 -> Updating Item Name");
            System.out.println("Option 3 -> Updating Item Unit");
            System.out.println("Option 4 -> Updating Item Price");
            System.out.println("Option 5 -> Updating Item Description");
            System.out.println("Option 6 -> For exit");
            System.out.println("-".repeat(30));

            System.out.println("\nEnter the option: ");
            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:
                    String previousItemType = this.itemType;

                    String nItemType = itemUtil.getItemTypeInput();

                    this.setItemType(nItemType);

                    System.out.println("\nItem's Type updated from " + previousItemType + " to " + nItemType );
                    break;
                case 2:
                    String previousName = this.itemName;

                    String updatedItemName = itemUtil.getItemNameInput();

                    this.setItemName(updatedItemName);

                    System.out.println("\nItem's Description updated from " + previousName + " to " + updatedItemName );

                    break;
                case 3:
                    String previousItemUnit = this.itemUnit;

                    String nItemUnit = itemUtil.getItemUnitInput();

                    this.setItemUnit(nItemUnit);

                    System.out.println("\nItem's Unit updated from " + previousItemUnit + " to " + nItemUnit );

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
                case 6:
                    System.out.println("\n" + this.itemName + " is updated");

                    isUpdating = false;

                    break;
                default:
                    System.out.println("\nEnter a valid input (1 - 6)");
                    break;
            }
        }
    }

    // Item's Tax Details
    private void updateItemTaxes () {
        boolean isUpdating = true;
        Scanner scanner = new Scanner(System.in);
        while (isUpdating) {
            System.out.println("\nOption 1 -> Updating Taxable");
            System.out.println("Option 2 -> Updating Intra State Tax Rate");
            System.out.println("Option 3 -> Updating Inter State Tax Rate");
            System.out.println("Option 4 -> For exit");

            System.out.println("Enter Option: ");

            int option = -1;
            option = (int) Utils.handleIntegerInputMisMatches(option, scanner);

            switch (option) {
                case 1:

                    double[] taxList = itemUtil.getTaxableInput();

                    boolean nIsTaxable = taxList[0] == 1;

                    double intraTaxRate = taxList[1];
                    double interTaxRate = taxList[2];

                    this.setIsTaxable(nIsTaxable);
                    this.setIntraTaxRate(intraTaxRate);
                    this.setInterTaxRate(interTaxRate);

                    System.out.println("\nTaxable has been updated to " + (nIsTaxable ? "true" : "false"));
                    break;
                case 2:
                    if (!this.isTaxable) {
                        System.out.println("\nThis item is not taxable...");
                        break;
                    }

                    double previousIntraTaxRate = this.intraTaxRate;

                    System.out.println("\nEnter the Intra Tax Rate: ");

                    double nIntraTaxRate = -1;
                    nIntraTaxRate = Utils.handleIntegerInputMisMatches(nIntraTaxRate, scanner);

                    nIntraTaxRate = Utils.getValidInput(nIntraTaxRate, 0.1, 28, scanner, "Intra Tax Rate");

                    this.setIntraTaxRate(nIntraTaxRate);

                    System.out.println("\nIntra State Tax Rate updated from " + previousIntraTaxRate + " to " + nIntraTaxRate );
                    break;
                case 3:
                    if (!isTaxable) {
                        System.out.println("\nThis item is not taxable...");
                        break;
                    }

                    double previousInterTaxRate = this.interTaxRate;

                    System.out.println("\nEnter the Inter Tax Rate: ");
                    double nInterTaxRate = -1;
                    nInterTaxRate = Utils.handleIntegerInputMisMatches(nInterTaxRate, scanner);

                    nInterTaxRate = Utils.getValidInput(nInterTaxRate, 0.1, 28, scanner, "Inter Tax Rate");

                    this.setInterTaxRate(nInterTaxRate);

                    System.out.println("\nInter State Tax Rate updated from " + previousInterTaxRate + " to " + nInterTaxRate );
                    break;
                case 4:
                    System.out.println("\n" + this.itemName + "'s taxes are updated");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("\nEnter a valid input (1 - 4)");
                    break;
            }
        }
    }

    public static Item search (String itemName, TreeMap<Integer, Item> items) {
        for (Integer itemNo : items.keySet()) {
            Item item = items.get(itemNo);
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

        sb.append("\nItem Details:\n");
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
