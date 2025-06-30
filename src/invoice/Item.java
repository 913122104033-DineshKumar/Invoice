package invoice;

public class Item {
    // Primary Details
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
}
