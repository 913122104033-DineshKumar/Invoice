package invoice;

import java.util.Scanner;

public class Item {
    private static int totalItems = 0;
    // Primary Details
    private final int itemNo;
    private Utils.ItemTypes itemType;
    private String itemName;
    private boolean isTaxable;
    private double price;
    private String description;

    // Tax Rates
    private int intraTaxRate;
    private int interTaxRate;

    public Item (Utils.ItemTypes itemType, String itemName, boolean isTaxable, double price, String description, int intraTaxRate, int interTaxRate) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.isTaxable = isTaxable;
        this.price = price;
        this.description = description;
        this.intraTaxRate = intraTaxRate;
        this.interTaxRate = interTaxRate;
        totalItems++;
        this.itemNo = totalItems;
    }

    public static Item createItem () {
        Scanner scanner = new Scanner(System.in);
        Utils.ItemTypes itemType;
        double price;
        String itemName = "";
        boolean isTaxable = false;
        String description = "";
        int intraTaxRate = 0;
        int interTaxRate = 0;
        System.out.println("Item Type: Goods -> 0, Service -> 1");
        int itemTypeOption = scanner.nextInt();
        scanner.nextLine();
        if (itemTypeOption == 0) {
            itemType = Utils.ItemTypes.GOODS;
        } else {
            itemType = Utils.ItemTypes.SERVICES;
        }
        while (itemName.isEmpty()) {
            System.out.println("Enter the Item Name: ");
            itemName = scanner.nextLine();
        }
        System.out.println("Is tax applied for this item, if Yes -> 0, No -> 1");
        int taxableOption = scanner.nextInt();
        scanner.nextLine();
        if (taxableOption == 0) {
            isTaxable = true;
            System.out.println("Enter the Intra State tax: ");
            intraTaxRate = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter the Inter State tax: ");
            interTaxRate = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println("Enter the Selling price of the item: ");
        price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Would you like to enter the description for the item, if yes -> 0, No -> 1");
        int descriptionOption = scanner.nextInt();
        scanner.nextLine();
        if (descriptionOption == 0) {
            System.out.println("Enter the description: ");
            description = scanner.nextLine();
        }
        return new Item(itemType, itemName, isTaxable,  price, description, intraTaxRate, interTaxRate);
    }

    public Utils.ItemTypes getItemType() {
        return itemType;
    }

    public void setItemType(Utils.ItemTypes itemType) {
        this.itemType = itemType;
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

    public void setTaxable(boolean taxable) {
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

    public int getIntraTaxRate() {
        return intraTaxRate;
    }

    public void setIntraTaxRate(int intraTaxRate) {
        this.intraTaxRate = intraTaxRate;
    }

    public int getInterTaxRate() {
        return interTaxRate;
    }

    public void setInterTaxRate(int interTaxRate) {
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

}
