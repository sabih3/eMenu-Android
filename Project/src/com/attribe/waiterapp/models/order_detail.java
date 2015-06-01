package com.attribe.waiterapp.models;

/**
 * Created by Sabih Ahmed on 5/30/2015.
 */
public class order_detail {

    int menu_id;
    int quantity;
    double item_total;

    public order_detail(int menu_id, int quantity,double item_total) {
        this.menu_id = menu_id;
        this.quantity = quantity;
        this.item_total=item_total;

    }



    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItem_total() {
        return item_total;
    }

    public void setItem_total(long item_total) {
        this.item_total = item_total;
    }

    public class Response{
        String message;
        int status;
    }

}




