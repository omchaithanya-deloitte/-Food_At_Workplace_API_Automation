package foodAtWorkspaceAPI;

import PojoClasses.Place_order;

public class Place_order_for_User {
    public static Place_order sendRequest(String email, int amount, int tableNo){
        Place_order order = new Place_order();
        order.setEmail(email);
        order.setamount(amount);
        order.settableNo(tableNo);
        return order;
    }
}
