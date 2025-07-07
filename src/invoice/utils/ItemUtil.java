package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Item;

import java.time.LocalDate;
import java.util.*;

public class ItemUtil
{
    private final Scanner scanner;
    private final Set<Character> ITEM_TYPES;
    private final Set<Character> ITEM_UNITS;

    public ItemUtil (Scanner scanner)
    {
        this.scanner = scanner;
        this.ITEM_TYPES = new HashSet<>();
        this.ITEM_TYPES.addAll(Arrays.asList('G', 'S', 'g', 's'));
        this.ITEM_UNITS = new HashSet<>();
        this.ITEM_UNITS.addAll(Arrays.asList('P', 'M', 'B', 'N', 'p', 'm', 'b', 'n'));
    }

    public Character getItemTypeInput ()
    {
        return Utils.getValidOption( ITEM_TYPES,
                scanner, "Item Type Option", "Enter a Item Type (G,g  -> Goods, S, s -> Services):");
    }

    public Character getItemUnitInput ()
    {
        return Utils.getValidOption(ITEM_UNITS, scanner, "Item Unit Option", "Enter a Valid input (Pieces -> P, Meters -> M, Box -> B, Undefined -> N):");
    }

    public String getItemNameInput ()
    {
        return Utils.getValidStringInput( GlobalConstants.NAME_REGEX, scanner, "Eg. Punam Saree", "Item Name", "Enter the Item Name: ", true);
    }

    public double[] getTaxableInput (boolean isCreation, boolean previousIsTaxable)
    {
        int isTaxable = 0;
        double intraTaxRate, interTaxRate;
        char yesOrNo;

        if (isCreation)
        {
            yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS,
                    scanner, "Tax Option","Is Taxable applied\nY -> Yes\nN -> No");
        } else
        {
            if (previousIsTaxable) {
                yesOrNo = 'N';
            } else {
                yesOrNo = 'Y';
            }
        }


        if (yesOrNo == 'Y' || yesOrNo == 'y') {
            isTaxable = 1;

            intraTaxRate = Utils.getValidDoubleInput( 0,  scanner, "Intra Tax Rate", "Enter the Intra Tax Rate:");

            interTaxRate = Utils.getValidDoubleInput( 0, scanner, "Inter Tax Rate", "Enter the Inter Tax Rate: ");
        } else {
            intraTaxRate = 0;
            interTaxRate = 0;
        }

        return new double[] { isTaxable, intraTaxRate, interTaxRate };
        
    }

    public double getPriceInput () {
        return Utils.getValidDoubleInput( 0, scanner, "Selling Price", "Enter the Item Selling Price:");
    }

    public String getDescription (boolean isCreation) {
        String description = "";
        if (isCreation) {
            char yesOrNo = Utils.getValidOption( GlobalConstants.YES_NO_OPTIONS,
                    scanner, "Description", "Want to Write Description\nY -> Yes\nN -> No");

            if (yesOrNo == 'Y' || yesOrNo == 'y') {

                description = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Nice Saree", "Item Description", "Enter the Description:", true);
            }
        } else {

            description = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Nice Saree", "Item Description", "Enter the Description:", true);
        }

        return description;
    }

    public void sortingModule (List<Item> items)
    {
        SortingUtil sortingUtil = new SortingUtil();

        System.out.println("\nEnter the choice based in which you want to sort the Items: ");

        int sortBy = -1;

        do
        {
            System.out.println("\nOption 1 -> Sort by Item Number");
            System.out.println("\nOption 2 -> Sort by Item Price");
            System.out.println("\nOption 3 -> Sort by Date");

            sortBy = Utils.handleIntegerInputMisMatches(sortBy, -1, scanner);

        } while (sortBy < 1 || sortBy > 3);

        int sortingOrder = -1;

        do
        {
            System.out.println("\nOption 1 -> Ascending Order");
            System.out.println("\nOption 2 -> Descending Order");

            sortingOrder = Utils.handleIntegerInputMisMatches(sortingOrder, -1, scanner);

        } while (sortingOrder < 1 || sortingOrder > 2);

        if (sortBy == 1)
        {
            List<Integer> helper = new ArrayList<>();

            for (Item item : items)
            {
                helper.add(item.getItemNo());
            }

            sortingUtil.mergeSort(0, items.size() - 1, items, helper);
        }
        else if (sortBy == 2)
        {
            List<Double> helper = new ArrayList<>();

            for (Item item : items)
            {
                helper.add(item.getPrice());
            }

            sortingUtil.mergeSort(0, items.size() - 1, items, helper);
        } else
        {
            List<LocalDate> helper = new ArrayList<>();

            for (Item item : items)
            {
                helper.add(item.getCreatedAt());
            }

            sortingUtil.mergeSort(0, items.size() - 1, items, helper);
        }

        if (sortingOrder == 2)
        {
            Utils.reverse(items);
        }

        Utils.showItems(items);

    }

    public static void reArrangeItemList (List<Item> items) {

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (i + 1 != item.getItemNo()) {
                item.setItemNo(i + 1);
            }
        }
    }

}
