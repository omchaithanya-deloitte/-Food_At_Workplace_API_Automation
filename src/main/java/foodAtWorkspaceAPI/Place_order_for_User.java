package foodAtWorkspaceAPI;

import PojoClasses.Place_order;
import org.json.JSONObject;

public class Place_order_for_User {
    public static JSONObject sendRequest(String email, int amount, int tableNo){
        Place_order order = new Place_order();

        order.setEmail(email);
        order.setamount(amount);
        order.settableNo(tableNo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email",email);
        jsonObject.put("amount",amount);
        jsonObject.put("tableNo",tableNo);
        JSONObject data1=new JSONObject();
        data1.put("1",1);
        data1.put("2",2);
        jsonObject.put("data",data1);
        return jsonObject;
    }
}

