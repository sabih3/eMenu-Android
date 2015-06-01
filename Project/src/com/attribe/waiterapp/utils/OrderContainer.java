package com.attribe.waiterapp.utils;

import com.attribe.waiterapp.models.CartItem;
import com.attribe.waiterapp.models.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/22/2015.
 */
public class OrderContainer {


    private static OrderContainer orderContainer;
    public CopyOnWriteArrayList<Order> orderList;
    public ArrayList<Order> selectedOrderList;

    public static OrderContainer getInstance(){

        if(orderContainer == null){

            orderContainer = new OrderContainer();
        }

            return orderContainer;
    }

    private OrderContainer(){
        orderList = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<Order> getOrderList(){

        return orderList;
    }

    public void putOrder(Order order){
        this.orderList.add(order);

    }

    public void removeOrder(Order order){
        this.orderList.remove(order);

    }



    public void addOrderToSelectedList(Order order){

        this.selectedOrderList.add(order);
    }
}
