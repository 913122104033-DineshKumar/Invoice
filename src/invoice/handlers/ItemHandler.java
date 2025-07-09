package invoice.handlers;

import invoice.src.Item;
import invoice.utils.ItemUtil;

import java.util.List;

public class ItemHandler
{
    private ItemUtil itemUtil;

    public ItemHandler () {  }

    private boolean itemNameAlreadyExists (String name, List<Item> items)
    {
        for (Item item : items)
        {
            if (item.getItemName().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }

}
