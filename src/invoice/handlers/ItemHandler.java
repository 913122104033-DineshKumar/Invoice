package invoice.handlers;

import invoice.interfaces.ComparatorCallBack;
import invoice.models.Item;
import invoice.utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemHandler
{
    private final ItemUtil itemUtil;

    public ItemHandler () { 
        this.itemUtil = new ItemUtil();
    }

    public Item create (List<Item> items)
    {
        System.out.println("\nYou can add Item now");

        // Item Type Input
        char itemType = itemUtil.getItemTypeInput();

        // Item Unit Input
        char itemUnit = itemUtil.getItemUnitInput();

        // Item Name Input
        String itemName = itemUtil.getItemNameInput(items);

        double price = itemUtil.getPriceInput();
        
        Item item = new Item(itemType, itemUnit, LocalDate.now(), itemName, price);

        String nDescription = itemUtil.getDescription();
        
        item.setDescription(nDescription);

        double[] taxableDetails = itemUtil.getTaxableInput(true, false);
        
        item.setIsTaxable(taxableDetails[0] == 1);
        item.setIntraTaxRate(taxableDetails[1]);
        item.setInterTaxRate(taxableDetails[2]);

        item.setItemId(item.generateItemId());

        return item;
    }

    public void update (List<Item> items)
    {
        int itemNo = InputUtil.getItemNumber(items);

        Item item = items.get(itemNo);

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

                option = InputUtil.handleIntegerInputMisMatches(option, -1);


                switch (option)
                {
                    case 1:
                    {
                        updateItemDetails(item, items);
                        break;
                    }

                    case 2:
                    {
                        updateItemTaxes(item);
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

        System.out.println("\nUpdated Item Details\n");
        item.showItem();

    }

    private void updateItemDetails (Item item, List<Item> items)
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
                option = InputUtil.handleIntegerInputMisMatches(option, -1);

                switch (option)
                {

                    case 1:
                    {
                        char previousItemType = item.getItemType();

                        char nItemType = itemUtil.getItemTypeInput();

                        if (previousItemType == nItemType)
                        {
                            System.out.println("\nYou entered the same Item type again, so no updation is done");
                            break;
                        }

                        item.setItemType(nItemType);

                        System.out.println("\nItem's Type updated from " + item.getItemType(previousItemType) + " to " + item.getItemType(nItemType));
                        break;
                    }

                    case 2:
                    {
                        String previousName = InputUtil.handleNullStrings(item.getItemName());

                        String updatedItemName = itemUtil.getItemNameInput(items);

                        if (previousName.equalsIgnoreCase(updatedItemName))
                        {
                            System.out.println("\nYou entered the same Item Name again, so no updation is done");
                            break;
                        }

                        item.setItemName(updatedItemName);

                        System.out.println("\nItem's Name updated from " + previousName + " to " + updatedItemName);

                        break;
                    }

                    case 3:
                    {
                        char previousItemUnit = item.getItemUnit();

                        char nItemUnit = itemUtil.getItemUnitInput();

                        if (previousItemUnit == nItemUnit)
                        {
                            System.out.println("\nYou entered the same Item Unit again, so no updation is done");
                            break;
                        }

                        item.setItemUnit(nItemUnit);

                        System.out.println("\nItem's Unit updated from " + item.getItemUnit(previousItemUnit) + " to " + item.getItemUnit(nItemUnit));

                        break;
                    }

                    case 4:
                    {
                        double previousPrice = item.getPrice();

                        double updatedPrice = itemUtil.getPriceInput();

                        if (previousPrice == updatedPrice)
                        {
                            System.out.println("\nYou entered the same Item price again, so no updation is done");
                            break;
                        }

                        item.setPrice(updatedPrice);

                        System.out.println("\nItem's Price updated from " + previousPrice + " to " + updatedPrice);
                        break;
                    }

                    case 5:
                    {
                        String previousDescription = InputUtil.handleNullStrings(item.getDescription());

                        String nDescription = itemUtil.getDescription();

                        if (previousDescription.equalsIgnoreCase(nDescription))
                        {
                            System.out.println("\nYou entered the same Description again, so no updation is done");
                            break;
                        }

                        item.setDescription(nDescription);

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

    private void updateItemTaxes (Item item) {
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

                option = InputUtil.handleIntegerInputMisMatches(option, -1);

                switch (option)
                {
                    case 1:
                    {
                        double[] taxableDetails = itemUtil.getTaxableInput(false, item.getIsTaxable());

                        item.setIsTaxable(taxableDetails[0] == 1);
                        item.setIntraTaxRate(taxableDetails[1]);
                        item.setInterTaxRate(taxableDetails[2]);

                        System.out.println("\nTaxable has been updated to " + (item.getIsTaxable() ? "true" : "false"));
                        break;
                    }

                    case 2:
                    {
                        if (!item.getIsTaxable())
                        {
                            System.out.println("\nItem is not taxable...");
                            break;
                        }

                        double previousIntraTaxRate = item.getIntraTaxRate();

                        double nIntraTaxRate = ValidationUtil.getValidDoubleInput(0, "Intra Tax Rate", "Enter the Intra Tax Rate:");

                        if (previousIntraTaxRate == nIntraTaxRate)
                        {
                            System.out.println("\nYou entered the same Intra Tax Rate again, so no updation is done");
                            break;
                        }

                        item.setIntraTaxRate(nIntraTaxRate);

                        System.out.println("\nIntra State Tax Rate updated from " + previousIntraTaxRate + " to " + nIntraTaxRate);
                        break;
                    }

                    case 3:
                    {
                        if (!item.getIsTaxable())
                        {
                            System.out.println("\nItem is not taxable...");
                            break;
                        }

                        double previousInterTaxRate = item.getInterTaxRate();

                        double nInterTaxRate = ValidationUtil.getValidDoubleInput(0, "Inter Tax Rate", "Enter the Inter Tax Rate:");

                        if (previousInterTaxRate == nInterTaxRate)
                        {
                            System.out.println("\nYou entered the same Inter Tax Rate again, so no updation is done");
                            break;
                        }

                        item.setInterTaxRate(nInterTaxRate);

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

    public void searchByName(List<Item> items)
    {
        final String NAME_REGEX = "[a-zA-Z0-9\\s'-]+";

        String itemName = ValidationUtil.getValidStringInput(NAME_REGEX, "Punam Saree", "Item Name", "Enter Item Name (Eg. Punam Saree):", true);

        List<Item> itemsFound = new ArrayList<>();

        for (Item item : items)
        {
            if (item.getItemName().toLowerCase().contains(itemName.toLowerCase()))
            {
                itemsFound.add(item);
            }
        }
        if (!itemsFound.isEmpty())
        {
            System.out.println("\n");
            DisplayUtil.showItems(itemsFound);
        } else
        {
            System.out.println("\nNo item found with this name");
        }
    }

    public int searchByItemNo (int itemNo, List<Item> items)
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

    public void deleteItem (List<Item> items)
    {
        int deleteItemNo = InputUtil.getItemNumber(items);

        Item selectedItem = items.get(deleteItemNo);

        if (InputUtil.hasSingleElement(items)) {
            char confirmationOption = ValidationUtil.collectToggleChoice( 'y',"Delete Customer", "\nSince there is only one item " + selectedItem.getItemName() + "\nDo you still want to delete (y -> yes, any other key -> no)");

            if (confirmationOption == 'N' || confirmationOption == 'n') {
                System.out.println("\nItem is not deleted");
                return;
            }
        }

        System.out.println("\n" + selectedItem.getItemName() + " is deleted successfully...");

        items.remove(deleteItemNo);
    }

    public void sortingModule (List<Item> items)
    {
        SortingUtil sortingUtil = new SortingUtil();

        System.out.println("\nEnter the choice based in which you want to sort the Items: ");

        int sortBy = -1;

        sortModule:
        {
            while (true) {
                System.out.println("\nOption 1 -> Sort by Item Number");
                System.out.println("Option 2 -> Sort by Date");
                System.out.println("Option 3 -> Sort by Item Price");
                System.out.println("Option 4 -> Exit the Sorting Module");

                System.out.println("\nEnter the Sort by Option: ");

                sortBy = InputUtil.handleIntegerInputMisMatches(sortBy, -1);

                int sortingOrder = -1;

                if (sortBy >= 1 && sortBy <= 3){
                    orderInput:
                    {
                        while (true) {
                            System.out.println("\nOption 1 -> Ascending Order");
                            System.out.println("Option 2 -> Descending Order");

                            System.out.println("\nEnter the Sorting Order Option: ");

                            sortingOrder = InputUtil.handleIntegerInputMisMatches(sortingOrder, -1);

                            switch (sortingOrder) {
                                case 1, 2: {
                                    break orderInput;
                                }
                                default: {
                                    System.out.println("\nEnter a valid input (1 - 2)");
                                    break;
                                }
                            }
                        }

                    }
                }

                int finalSortingOrder = sortingOrder;

                switch (sortBy) {
                    case 1: {

                        sortingUtil.mergeSort(0, items.size() - 1, items, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Item it1 = (Item) obj1;
                                Item it2 = (Item) obj2;
                                return finalSortingOrder == 1 ? ComparisonUtil.compareIntegers(it1.getItemNo(), it2.getItemNo()) : ComparisonUtil.compareIntegers(it2.getItemNo(), it1.getItemNo());
                            }
                        });

                        break;
                    }
                    case 2: {

                        sortingUtil.mergeSort(0, items.size() - 1, items, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Item it1 = (Item) obj1;
                                Item it2 = (Item) obj2;
                                return finalSortingOrder == 1 ? ComparisonUtil.compareDates(it1.getCreatedAt(), it2.getCreatedAt()) : ComparisonUtil.compareDates(it2.getCreatedAt(), it1.getCreatedAt());
                            }
                        });

                        break;
                    }
                    case 3: {

                        sortingUtil.mergeSort(0, items.size() - 1, items, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Item it1 = (Item) obj1;
                                Item it2 = (Item) obj2;
                                return finalSortingOrder == 1 ? ComparisonUtil.compareDoubles(it1.getPrice(), it2.getPrice()) : ComparisonUtil.compareDoubles(it2.getPrice(), it1.getPrice());
                            }
                        });

                        break;
                    }
                    case 4: {
                        System.out.println("\nExiting sorting module...");
                        break sortModule;
                    }
                    default: {
                        System.out.println("\nEnter a valid option (1 - 4)");
                        break;
                    }
                }

                DisplayUtil.showItems(items);

            }
        }

    }
}
