package PojoClasses;

public class Items {

    private int itemsId;
    private String category;
    private String image;
    private String name;
    private String details;
    private int duration;
    private int price;
    private boolean isAvailable;
    private boolean isVeg;

    public Items(){

    }

    public Items(int itemsId, String category, String image, String name, String details, int duration, int price, boolean isAvailable, boolean isVeg){

        this.itemsId = itemsId;
        this.category = category;
        this.image = image;
        this.name = name;
        this.details = details;
        this.duration = duration;
        this.price = price;
        this.isAvailable = isAvailable;
        this.isVeg = isVeg;

    }



    public int getItemsId() {
        return itemsId;
    }

    public void setItemsId(int itemsId) {
        this.itemsId = itemsId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getIsAvailable(){
        return isAvailable;
    }
    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public boolean getIsVeg(){
        return isVeg;
    }
    public void setIsVeg(boolean isVeg){
        this.isVeg = isVeg;
    }
}
