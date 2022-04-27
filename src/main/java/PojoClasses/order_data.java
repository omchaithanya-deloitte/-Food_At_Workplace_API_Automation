package PojoClasses;

public class order_data {
    private String item_id1;
    private String item_id2;

    public order_data(String item_id1, String item_id2) {
        this.item_id1=item_id1;
        this.item_id2=item_id2;
    }

    public order_data() {

    }


    public String getitem_id1() {
        return item_id1;
    }

    public void setitem_id1(String item_id1) {
        this.item_id1 = item_id1;
    }

    public String  getitem_id2() {
        return item_id2;
    }

    public void setitem_id2(String item_id2) {
        this.item_id2 = item_id2;
    }
}
