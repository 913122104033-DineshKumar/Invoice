package invoice.src;

import invoice.utils.ItemUtil;
import invoice.utils.InputUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Item
{
    private static int totalItems = 1;

    // Primary Details
    private int itemNo;
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

    private static ItemUtil itemUtil;

    public Item () {
        this.createdAt = LocalDate.now();
    }

    public Item (char itemType, char itemUnit, LocalDate createdAt, String itemName, double price, String description, boolean isTaxable, double intraTaxRate, double interTaxRate)
    {
        this.itemType = itemType;
        this.itemUnit = itemUnit;
        this.createdAt = createdAt;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.itemNo = totalItems;
        totalItems++;
        this.isTaxable = isTaxable;
        this.intraTaxRate = intraTaxRate;
        this.interTaxRate = interTaxRate;
        Item.itemUtil = new ItemUtil();
    }

    public static Item create (List<Item> items)
    {
        if (itemUtil == null) {
            itemUtil = new ItemUtil();
        }

        System.out.println("\nYou can add Item now");

        // Item Type Input
        char itemType = itemUtil.getItemTypeInput();

        // Item Unit Input
        char itemUnit = itemUtil.getItemUnitInput();

        // Item Name Input
        String itemName = itemUtil.getItemNameInput(items);

        double price = itemUtil.getPriceInput();

        String nDescription = itemUtil.getDescription(true);

        double[] taxableDetails = itemUtil.getTaxableInput(true, false);

        return new Item (itemType, itemUnit, LocalDate.now(), itemName, price, nDescription, taxableDetails[0] == 1, taxableDetails[1], taxableDetails[2]);
    }

    public void update (List<Item> items)
    {
        int option = -1;

        itemUpdation:
        {
            while (true)
            {
                System.out.println("\nOption 1 -> Updating Item Details");
                System.out.println("Option 2 -> Updating Item Tax Details");
                System.out.println("Option 3 -> To exit the update");
                System.out.println("-".repeat(30));

                System.out.println("\nEnter the option: ");

                option = InputUtils.handleIntegerInputMisMatches(option, -1);


                switch (option)
                {
                    case 1:
                    {
                        updateItemDetails(items);
                        break;
                    }

                    case 2:
                    {
                        updateItemTaxes();
                        break;
                    }

                    case 3:
                    {
                        System.out.println("\nExiting the Item Updation Module...");
                        break itemUpdation;
                    }

                    default:
                    {
                        System.out.println("\nEnter a valid input (1 - 3)...");
                        break;
                    }
                }
            }
        }

    }

    // Items Primary Details
    private void updateItemDetails ( List<Item> items)
    {
        int option = -1;

        itemDetailsUpdation:
        {
            while (true)
            {
                System.out.println("\nOption 1 -> Updating Item Type");
                System.out.println("Option 2 -> Updating Item Name");
                System.out.println("Option 3 -> Updating Item Unit");
                System.out.println("Option 4 -> Updating Item Price");
                System.out.println("Option 5 -> Updating Item Description");
                System.out.println("Option 6 -> For exit");
                System.out.println("-".repeat(30));

                System.out.println("\nEnter the option: ");
                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option)
                {

                    case 1:
                    {
                        char previousItemType = this.itemType;

                        char nItemType = itemUtil.getItemTypeInput();

                        if (previousItemType == nItemType)
                        {
                            System.out.println("\nYou entered the same Item type again, so no updation is done");
                            break;
                        }

                        this.setItemType(nItemType);

                        System.out.println("\nItem's Type updated from " + getItemType(previousItemType) + " to " + getItemType(nItemType));
                        break;
                    }

                    case 2:
                    {
                        String previousName = InputUtils.handleNullStrings(this.itemName);

                        String updatedItemName = itemUtil.getItemNameInput(items);

                        if (previousName.equalsIgnoreCase(updatedItemName))
                        {
                            System.out.println("\nYou entered the same Item Name again, so no updation is done");
                            break;
                        }

                        this.setItemName(updatedItemName);

                        System.out.println("\nItem's Description updated from " + previousName + " to " + updatedItemName);

                        break;
                    }

                    case 3:
                    {
                        char previousItemUnit = this.itemUnit;

                        char nItemUnit = itemUtil.getItemUnitInput();

                        if (previousItemUnit == nItemUnit)
                        {
                            System.out.println("\nYou entered the same Item Unit again, so no updation is done");
                            break;
                        }

                        this.setItemUnit(nItemUnit);

                        System.out.println("\nItem's Unit updated from " + getItemUnit(previousItemUnit) + " to " + getItemUnit(nItemUnit));

                        break;
                    }

                    case 4:
                    {
                        double previousPrice = this.price;

                        double updatedPrice = itemUtil.getPriceInput();

                        if (previousPrice == updatedPrice)
                        {
                            System.out.println("\nYou entered the same Item price again, so no updation is done");
                            break;
                        }

                        this.setPrice(updatedPrice);

                        System.out.println("\nItem's Price updated from " + previousPrice + " to " + updatedPrice);
                        break;
                    }

                    case 5:
                    {
                        String previousDescription = InputUtils.handleNullStrings(this.description);

                        String nDescription = itemUtil.getDescription(false);

                        if (previousDescription.equalsIgnoreCase(nDescription))
                        {
                            System.out.println("\nYou entered the same Description again, so no updation is done");
                            break;
                        }

                        this.setDescription(nDescription);

                        System.out.println("\nItem's Description updated from " + previousDescription + " to " + nDescription);
                        break;
                    }

                    case 6:
                    {
                        System.out.println("\nExiting the Item Details Updation Module...");
                        break itemDetailsUpdation;
                    }

                    default:
                    {
                        System.out.println("\nEnter a valid input (1 - 6)");
                        break;
                    }
                }
            }
        }
    }

    // Item's Tax Details
    private void updateItemTaxes () {
        int option = -1;

        itemTaxUpdation:
        {
            while (true)
            {
                System.out.println("\nOption 1 -> Updating Taxable");
                System.out.println("Option 2 -> Updating Intra State Tax Rate");
                System.out.println("Option 3 -> Updating Inter State Tax Rate");
                System.out.println("Option 4 -> For exit");

                System.out.println("\nEnter Option: ");

                option = InputUtils.handleIntegerInputMisMatches(option, -1);

                switch (option)
                {
                    case 1:
                    {
                        double[] taxableDetails = itemUtil.getTaxableInput(false, this.isTaxable);

                        this.setIsTaxable(taxableDetails[0] == 1);
                        this.setIntraTaxRate(taxableDetails[1]);
                        this.setInterTaxRate(taxableDetails[2]);

                        System.out.println("\nTaxable has been updated to " + (this.isTaxable ? "true" : "false"));
                        break;
                    }

                    case 2:
                    {
                        if (!this.isTaxable)
                        {
                            System.out.println("\nThis item is not taxable...");
                            break;
                        }

                        double previousIntraTaxRate = this.intraTaxRate;

                        double nIntraTaxRate = InputUtils.getValidDoubleInput(0, "Intra Tax Rate", "Enter the Intra Tax Rate:");

                        if (previousIntraTaxRate == nIntraTaxRate)
                        {
                            System.out.println("\nYou entered the same Intra Tax Rate again, so no updation is done");
                            break;
                        }

                        this.setIntraTaxRate(nIntraTaxRate);

                        System.out.println("\nIntra State Tax Rate updated from " + previousIntraTaxRate + " to " + nIntraTaxRate);
                        break;
                    }

                    case 3:
                    {
                        if (!isTaxable)
                        {
                            System.out.println("\nThis item is not taxable...");
                            break;
                        }

                        double previousInterTaxRate = this.interTaxRate;

                        double nInterTaxRate = InputUtils.getValidDoubleInput(0, "Inter Tax Rate", "Enter the Inter Tax Rate:");

                        if (previousInterTaxRate == nInterTaxRate)
                        {
                            System.out.println("\nYou entered the same Inter Tax Rate again, so no updation is done");
                            break;
                        }

                        this.setInterTaxRate(nInterTaxRate);

                        System.out.println("\nInter State Tax Rate updated from " + previousInterTaxRate + " to " + nInterTaxRate);
                        break;
                    }

                    case 4:
                    {
                        System.out.println("\nExiting the Items Tax Details Module...");
                        break itemTaxUpdation;
                    }

                    default:
                    {
                        System.out.println("\nEnter a valid input (1 - 4)");
                        break;
                    }
                }
            }
        }

    }

    public static List<Item> searchByName(String itemName, List<Item> items)
    {
        List<Item> result = new ArrayList<>();

        for (Item item : items)
        {
            if (item.getItemName().toLowerCase().contains(itemName.toLowerCase()))
            {
                result.add(item);
            }
        }
        return result;
    }

    public static int searchByItemNo (int itemNo, List<Item> items)
    {
        for (int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);

            if (item.getItemNo() == itemNo)
            {
                return i;
            }
        }
        return -1;
    }

    public static void sortItems (List<Item> items)
    {
        itemUtil.sortingModule(items);
    }

    public static void setTotalItems (int totalItems) {

        Item.totalItems = totalItems;
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

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Boolean getTaxable() {
        return isTaxable;
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

    public String generateItemId ()
    {
        return "ITEM" + totalItems;
    }

    private String getItemType (char itemType)
    {
        return itemType == 'g' ? "Goods" : "Services";
    }

    private String getItemUnit (char itemUnit)
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
        InputUtils.printHyphens(147);

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

        InputUtils.printHyphens(147);

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

        InputUtils.printHyphens(147);
    }

}
