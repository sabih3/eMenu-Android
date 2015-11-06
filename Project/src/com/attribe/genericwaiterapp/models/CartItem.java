package com.attribe.genericwaiterapp.models;

/**
 * Created by Sabih Ahmed on 5/22/2015.
 */
public class CartItem {

    Item item;
    int itemQuantity;

    public CartItem(Item item, int itemQuantity) {

        this.item = item;
        this.itemQuantity = itemQuantity;
    }
}
