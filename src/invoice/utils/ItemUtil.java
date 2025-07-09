package invoice.utils;

import invoice.src.Item;

import java.util.*;

public class ItemUtil{

    public ItemUtil () { }

    public char getItemTypeInput ()
    {
        return InputUtils.getToggleInput( 'g', "Item Type", "Enter the Item Type (g -> goods, any other key -> services)");
    }

    public Character getItemUnitInput ()
    { // Current Solution hard coding to constraints

        char itemUnit = '\0';
        do {
            System.out.println("\nEnter the Valid Item type (P, p -> Pieces, M, m -> meters, B, b -> Box, N, n -> None");

            itemUnit = InputUtils.handleCharacterInput( "Item Type");

            itemUnit = Character.toLowerCase(itemUnit);

        } while (itemUnit != 'p' && itemUnit != 'b' && itemUnit != 'm' && itemUnit != 'n');

        return itemUnit;
    }

    public String getItemNameInput (List<Item> items)
    {
        final String ITEM_NAME_REGEX = "[a-zA-Z0-9\\s'-]+";

        String itemName = InputUtils.getValidStringInput(ITEM_NAME_REGEX,  "Eg. Punam Saree", "Item Name", "Enter the Item Name: ", true);

        List<String> itemNames = new ArrayList<>(); // Alternatively write checkExistsMethod

        for (Item item : items)
        {
            itemNames.add(item.getItemName().toLowerCase());
        }

        while (itemNames.contains(itemName) || !itemName.matches(ITEM_NAME_REGEX))
        {
            if (itemNames.contains(itemName.toLowerCase())) {
                System.out.println("\nItem name already exists\nEnter the name again");
            } else {
                System.out.println(InputUtils.regexMatchFailedError("Item Name", "Eg. Punam Saree"));
            }
            itemName = InputUtils.handleStringInputMisMatches(itemName, "" );
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
            conformationOption = InputUtils.getToggleInput(
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

            intraTaxRate = InputUtils.getValidDoubleInput( 0,   "Intra Tax Rate", "Enter the Intra Tax Rate:");

            interTaxRate = InputUtils.getValidDoubleInput( 0,  "Inter Tax Rate", "Enter the Inter Tax Rate: ");
        } else {
            intraTaxRate = 0;
            interTaxRate = 0;
        }

        return new double[] { isTaxable, intraTaxRate, interTaxRate };
        
    }

    public double getPriceInput ()
    {
        return InputUtils.getValidDoubleInput( 0,  "Selling Price", "Enter the Item Selling Price:");
    }

    public String getDescription (boolean isCreation)
    {
        final String DESCRIPTION_REGEX = "[a-zA-Z0-9\\s'-#$%@!*&]+";

        String description = "";

        if (isCreation)
        {
            description = InputUtils.getValidStringInput(DESCRIPTION_REGEX,  "Nice Saree", "Item Description", "Enter the Description:", false);
        } else
        {

            description = InputUtils.getValidStringInput(DESCRIPTION_REGEX,  "Nice Saree", "Item Description", "Enter the Description:", true);
        }

        return description;
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

                sortBy = InputUtils.handleIntegerInputMisMatches(sortBy, -1);

                int sortingOrder = -1;

                if (sortBy >= 1 && sortBy <= 3){
                    orderInput:
                    {
                        while (true) {
                            System.out.println("\nOption 1 -> Ascending Order");
                            System.out.println("Option 2 -> Descending Order");

                            System.out.println("\nEnter the Sorting Order Option: ");

                            sortingOrder = InputUtils.handleIntegerInputMisMatches(sortingOrder, -1);

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
                        Comparator<Item> numberComparator = (a, b) -> {
                            if (finalSortingOrder == 1)
                            {
                                return InputUtils.compareIntegers(a.getItemNo(), b.getItemNo());
                            }
                            return InputUtils.compareIntegers(b.getItemNo(), a.getItemNo());
                        };

                        sortingUtil.mergeSort(0, items.size() - 1, items, numberComparator);

                        break;
                    }
                    case 2: {
                        Comparator<Item> dateComparator = (a, b) -> {
                            if (finalSortingOrder == 1) {
                                return InputUtils.compareDates(a.getCreatedAt(), b.getCreatedAt());
                            }
                            return InputUtils.compareDates(b.getCreatedAt(), a.getCreatedAt());
                        };

                        sortingUtil.mergeSort(0, items.size() - 1, items, dateComparator);

                        break;
                    }
                    case 3: {
                        Comparator<Item> priceComparator = (a, b) -> {
                            if (finalSortingOrder == 1)
                            {
                                return InputUtils.compareDoubles(a.getPrice(), b.getPrice());
                            }
                            return InputUtils.compareDoubles(b.getPrice(), a.getPrice());
                        };

                        sortingUtil.mergeSort(0, items.size() - 1, items, priceComparator);

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

                InputUtils.showItems(items);

            }
        }

    }

}
