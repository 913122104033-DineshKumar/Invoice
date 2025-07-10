package invoice.models;

import invoice.utils.DisplayUtil;
import invoice.utils.ItemUtil;
import invoice.utils.InputUtil;

import java.time.LocalDate;

public class Item
{
    private static int totalItems = 1;

    // Primary Details
    private String itemId;
    private final int itemNo;
    private char itemType;
    private char itemUnit;
    private final LocalDate createdAt;
    private String itemName;
    private Boolean isTaxable;
    private double price;
    private String description;

    // Tax Rates
    private double intraTaxRate;
    private double interTaxRate;

    public Item (char itemType, char itemUnit, LocalDate createdAt, String itemName, double price)
    {
        this.itemType = itemType;
        this.itemUnit = itemUnit;
        this.createdAt = createdAt;
        this.itemName = itemName;
        this.price = price;
        this.itemId = generateItemId();
        this.itemNo = totalItems;
        totalItems++;
    }

    public Item (char itemType, char itemUnit, LocalDate createdAt, String itemName, double price, String description, boolean isTaxable, double intraTaxRate, double interTaxRate)
    {
        this.itemType = itemType;
        this.itemUnit = itemUnit;
        this.createdAt = createdAt;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.isTaxable = isTaxable;
        this.intraTaxRate = intraTaxRate;
        this.interTaxRate = interTaxRate;
        this.itemId = generateItemId();
        this.itemNo = totalItems;
        totalItems++;
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

    public int getItemNo() {
        return itemNo;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String generateItemId ()
    {
        return "ITEM" + itemName.substring(0, Math.min(itemName.length(), 3)) + itemType + itemUnit;
    }

    public String getItemType (char itemType)
    {
        return itemType == 'g' ? "Goods" : "Services";
    }

    public String getItemUnit (char itemUnit)
    {
        return switch(itemUnit)
        {
            case 'p' -> "Pieces";
            case 'm' -> "Meters";
            case 'b' -> "Box";
            default -> "None";
        };
    }

    public void showItem()
    {
        DisplayUtil.printHyphens(147);

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

        DisplayUtil.printHyphens(147);

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

        DisplayUtil.printHyphens(147);
    }

}
