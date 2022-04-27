package foodAtWorkspaceAPI;

import PojoClasses.Items;

public class ItemsController {

    public static Items sendRequest(int itemsId, String category, String image, String name, String details, int duration, int price, boolean isAvailable, boolean isVeg){

        Items item = new Items();
        item.setItemsId(itemsId);
        item.setCategory(category);
        item.setImage(image);
        item.setName(name);
        item.setDetails(details);
        item.setDuration(duration);
        item.setIsAvailable(isAvailable);
        item.setIsVeg(isVeg);

        return item;
    }
}
