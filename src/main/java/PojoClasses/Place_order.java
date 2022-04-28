package PojoClasses;

public class Place_order {
    private String email;
    private int amount;
    private int tableNo;
    public Place_order(String email, int amount, int tableNo) {
        this.email=email;
        this.amount=amount;
        this.tableNo=tableNo;
    }

    public Place_order() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int gettableNo() {
        return tableNo;
    }

    public void settableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public int getamount() {
        return amount;
    }

    public void setamount(int amount) {
        this.amount = amount;
    }

    }

