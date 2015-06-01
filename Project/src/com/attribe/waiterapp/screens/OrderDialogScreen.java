package com.attribe.waiterapp.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.CategoryAdapter;
import com.attribe.waiterapp.adapters.CategoryItemAdapter;
import com.attribe.waiterapp.models.CartItem;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.models.Order;
import com.attribe.waiterapp.utils.OrderContainer;
import com.attribe.waiterapp.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/14/2015.
 */
public class OrderDialogScreen extends Activity implements NumberPicker.OnValueChangeListener{
    private TextView textViewItemName, textViewTotalPrice, textViewItemPrice;
    private NumberPicker pricePicker;
    private ImageView itemImage;
    private Item item;
    private Intent i;
    private int itemQuantity;
    private static CopyOnWriteArrayList<Order> orderList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order_new);
        initContent();

    }

    private void initContent() {
        i = getIntent();
        item = (Item) i.getSerializableExtra(Constants.KEY_SERIALIZEABLE_ITEM_OBJECT);

        itemImage = (ImageView)findViewById(R.id.dialog_order_image);
        textViewItemName = (TextView)findViewById(R.id.dialog_order_itemName);
        textViewItemPrice = (TextView)findViewById(R.id.dialog_order_totalPrice);
        textViewTotalPrice = (TextView) findViewById(R.id.dialog_order_totalPrice);
        pricePicker=(NumberPicker)findViewById(R.id.dialog_order_numberPicker);
        pricePicker.setMinValue(1);
        pricePicker.setMaxValue(100);
        pricePicker.setOnValueChangedListener(this);

        initValues();

    }

    private void initValues() {
        textViewItemName.setText(item.getName());
        textViewItemPrice.setText(String.valueOf(item.getPrice()));
        if(item.getImageBlob() != null){
            itemImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getImageBlob(),0,item.getImageBlob().length));
        }


        if(!OrderContainer.getInstance().getOrderList().isEmpty()){

            for(Order order: OrderContainer.getInstance().getOrderList() ){

                if(order.getItem().getId()== item.getId()){

                    pricePicker.setValue(order.getQuantityValue());
                    itemQuantity = order.getQuantityValue();

                }
//                else{
//                    pricePicker.setValue(1);
//                    itemQuantity = 0;
//                }
            }
        }
        else{
            pricePicker.setValue(1);
            itemQuantity = 1;
        }

        textViewTotalPrice.setText(getString(R.string.label_total) + "\t " + String.valueOf(item.getPrice()));
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        textViewTotalPrice.setText(getString(R.string.label_total) + "\t " + String.valueOf(getPrice(newVal, item.getPrice())));
        itemQuantity = newVal;
    }

    private double getPrice(int newVal, double price) {
        return newVal*price;
    }

    public void orderNow(View view){

        /**
        String itemName = textViewItemName.getText().toString();
        String itemPrice = textViewItemPrice.getText().toString();
        int quantityValue = itemQuantity ;
        int itemId = item.getId();
        int itemCategoryId = item.getCategory_id();
        String totalPrice = textViewTotalPrice.getText().toString().replace("Total","").trim();

        //Order order = new Order(itemName, itemPrice,quantityValue,itemId,itemCategoryId,totalPrice);
        Order order= new Order(item,itemQuantity);
        OrderContainer.getInstance().orderList.add(order);
        **/
    }

    public void addCart(View view){
        Order order = new Order(item,itemQuantity);

        boolean update = false;

        if(! OrderContainer.getInstance().getOrderList() .isEmpty()){

            for(Order iterator : OrderContainer.getInstance().getOrderList() ){

                if(iterator.getItem().getId()== order.getItem().getId()){ // Item already exists in order


                    iterator.setQuantityValue(itemQuantity);            //intention of increasing quantity
                    update = true;
                }

            }

            if(!update){
                OrderContainer.getInstance().getOrderList().add(order);
            }

        }
        else{

            OrderContainer.getInstance().getOrderList().add(order);
        }







        this.finish();

    }


}