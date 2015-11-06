package com.attribe.genericwaiterapp.models;

import java.io.Serializable;

/**
 * Created by Sabih Ahmed on 5/12/2015.
 */
public class Order implements Serializable{

    private String itemName, itemPrice, totalPrice;
    private int quantityValue, itemId, itemCategoryId;
    private Item item;


    public Order(String itemName, String itemPrice, int quantityValue, int itemId, int itemCategoryId, String totalPrice) {

       this.itemName = itemName ;
        this.itemPrice = itemPrice ;
        this.quantityValue = quantityValue ;
        this.itemId = itemId ;
        this.itemCategoryId = itemCategoryId ;
        totalPrice = this.totalPrice;

    }

    public Order (Item item){
        this.item = item;
    }

    public Order(Item item, int itemQuantity) {
        this.item=item;
        this.quantityValue=itemQuantity;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(int quantityValue) {
        this.quantityValue = quantityValue;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(int itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }
}
