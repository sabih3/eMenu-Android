package com.attribe.waiterapp.network;

import com.attribe.waiterapp.models.*;
import com.attribe.waiterapp.utils.Constants;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/13/2015.
 */
public interface ApiInterface {

    public static String URL_GET_CATEGORIES ="/categories";
    public static String URL_GET_ITEMS = "/menus";
    public static String URL_PLACE_ORDER = "/orders";

    @GET(URL_GET_CATEGORIES)
    void getCategories(Callback<ArrayList<Category>> callback);

    @GET(URL_GET_ITEMS)
    void getItems(@Query("category_id") long categoryId ,
                  Callback<ArrayList<Item>> callback);
    
    
    @GET(URL_GET_CATEGORIES)
    ArrayList<Category> getCategoriesSync();
    
    @GET(URL_GET_ITEMS)
    ArrayList<Item> getItemSync(@Query("category_id") long categoryId);

    @POST(URL_PLACE_ORDER)
    void placeOrder(@Body OrderDetail orderObject,Callback<order_detail.Response> response);

    @POST("")
    void verifyUser(String passCode,Callback<PassCodeResponse> response);

    @POST("/devices")
    void registerDevice(@Body DeviceRegister deviceRegister,Callback<DeviceRegister.Response> response);

}
