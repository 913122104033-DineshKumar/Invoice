package invoice.utils;

import invoice.models.Item;

import java.util.*;

public class ItemUtil{

    public ItemUtil () { }

    public char getItemTypeInput ()
    {
        return ValidationUtil.collectToggleChoice( 'g', "Item Type", "Enter the Item Type (g -> goods, any other key -> services)");
    }

    public Character getItemUnitInput ()
    { // Current Solution hard coding to constraints

        char itemUnit = '\0';
        do {
            System.out.println("\nEnter the Valid Item type (P, p -> Pieces, M, m -> meters, B, b -> Box, N, n -> None)");

            itemUnit = InputUtil.handleCharacterInput( "Item Type");

            itemUnit = Character.toLowerCase(itemUnit);

        } while (itemUnit != 'p' && itemUnit != 'b' && itemUnit != 'm' && itemUnit != 'n');

        return itemUnit;
    }

    public String getItemNameInput (List<Item> items)
    {
        final String ITEM_NAME_REGEX = "[a-zA-Z0-9\\s'-]+";

        String itemName = ValidationUtil.getValidStringInput(ITEM_NAME_REGEX,  "Eg. Punam Saree", "Item Name", "Enter the Item Name: ", true);

        while (true)
        {
            boolean exists = isItemNameExists(itemName, items);
            boolean invalid = !itemName.matches(ITEM_NAME_REGEX);

            if (!exists && !invalid)
            {
                break;
            }

            if (invalid) {
                System.out.println(ValidationUtil.regexMatchFailedError("Item Name", "Eg. Punam Saree"));
            } else {
                System.out.println("\nItem name already exists\nEnter the name again");
            }
            itemName = InputUtil.handleStringInputMisMatches(itemName, "" );
        }

        return itemName;
    }

    public double[] getTaxableInput (boolean isCreation, boolean previousIsTaxable)
    {
        int isTaxable = 0;
        double intraTaxRate, interTaxRate;
        char conformationOption;

        if (isCreation)
        {
            conformationOption = ValidationUtil.collectToggleChoice(
                     'y', "Tax Option","Is Taxable applied (y -> yes, any other key -> no)");
        } else
        {
            if (previousIsTaxable)
            {
                conformationOption = 'n';
            } else
            {
                conformationOption = 'y';
            }
        }


        if (conformationOption == 'y')
        {
            isTaxable = 1;

            intraTaxRate = ValidationUtil.getValidDoubleInput( 0,   "Intra Tax Rate", "Enter the Intra Tax Rate:");

            interTaxRate = ValidationUtil.getValidDoubleInput( 0,  "Inter Tax Rate", "Enter the Inter Tax Rate: ");
        } else {
            intraTaxRate = 0;
            interTaxRate = 0;
        }

        return new double[] { isTaxable, intraTaxRate, interTaxRate };
        
    }

    public double getPriceInput ()
    {
        return ValidationUtil.getValidDoubleInput( 0,  "Selling Price", "Enter the Item Selling Price:");
    }

    public String getDescription ()    {
        final String DESCRIPTION_REGEX = "[a-zA-Z0-9\\s'\\-#$%@!*&]+";

        String description = "";

        description = ValidationUtil.getValidStringInput(DESCRIPTION_REGEX,  "Nice Saree", "Item Description", "Enter the Description:", false);

        return description;
    }

    private boolean isItemNameExists (String itemName, List<Item> items)
    {
        for (Item item : items)
        {
            if (item.getItemName().equalsIgnoreCase(itemName))
            {
                return true;
            }
        }
        return false;
    }

}
