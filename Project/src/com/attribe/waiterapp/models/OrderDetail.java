package com.attribe.waiterapp.models;

import java.util.ArrayList;

/**
 * Created by Sabih Ahmed on 5/30/2015.
 */
public class OrderDetail {

    private String device_id;
    private double order_total;
    private ArrayList<order_detail> order_detail;

    public OrderDetail(String deviceId, ArrayList<order_detail> order_detail, double order_total) {
        this.device_id = deviceId;
        this.order_detail = order_detail;
        this.order_total=order_total;
    }




}


