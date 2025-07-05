package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Item;

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
        return Utils.getValidStringInput( GlobalConstants.NAME_REGEX, scanner, "Eg. Punam Saree", "Item Name", "Enter the Item Name: ");
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

        System.out.println("\nEnter the Item Selling Price ");

        return Utils.getValidDoubleInput( 0, scanner, "Selling Price", "Enter the Item Selling Price:");
    }

    public String getDescription (boolean isCreation) {
        String description = "";
        if (isCreation) {
            char yesOrNo = Utils.getValidOption( GlobalConstants.YES_NO_OPTIONS,
                    scanner, "Description", "Want to Write Description\nY -> Yes\nN -> No");

            if (yesOrNo == 'Y' || yesOrNo == 'y') {

                description = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Nice Saree", "Item Description", "Enter the Description:");
            }
        } else {

            description = Utils.getValidStringInput(GlobalConstants.NAME_REGEX, scanner, "Nice Saree", "Item Description", "Enter the Description:");
        }

        return description;
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
