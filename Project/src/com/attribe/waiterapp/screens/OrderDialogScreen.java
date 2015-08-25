package com.attribe.waiterapp.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.attribe.waiterapp.Database.DatabaseHelper;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.ImageAdapter;
import com.attribe.waiterapp.interfaces.OnItemAddedToOrder;
import com.attribe.waiterapp.interfaces.OnQuantityChangeListener;
import com.attribe.waiterapp.interfaces.QuantityPicker;
import com.attribe.waiterapp.models.Image;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.models.Order;
import com.attribe.waiterapp.utils.OrderContainer;
import com.attribe.waiterapp.utils.Constants;

import android.graphics.Bitmap;

import android.widget.ImageView;
import com.joooonho.SelectableRoundedImageView;
import com.pkmmte.circularimageview.CircularImageView;
//import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/14/2015.
 */
public class OrderDialogScreen extends Activity implements NumberPicker.OnValueChangeListener,QuantityPicker{

    private TextView textViewItemName,
            textViewTotalPrice,
            textViewItemPrice,
            textViewQuantity,
            textViewCategoryName;
    private NumberPicker pricePicker;
    private ImageView itemImage;
    private ImageView backButton;
    private TextView backCatButton;
//    private ImageView cheffImageView;
    private Item item;
    private Intent i;
    private int itemQuantity;
    private int position;
    private static CopyOnWriteArrayList<Order> orderList;
    private static OnQuantityChangeListener quantityChangeListener;
    private static OnItemAddedToOrder onItemAddedToOrder;
    private ListView galleryList;
    private ArrayList<Item> item_imageArrayList;
    private Button buttonIncrement;
    private Button buttonDecrement;

 //   CircularImageView circularImageView;
     // CircularImageView circularImageView ;
    SelectableRoundedImageView circularImageView ;

    public Context mContext;
    public File cacheDir;
    public String filePath;
    public File cacheFile;
    public Uri uri;
    public InputStream fileInputStream;

    private QuantityPicker quantityPicker;
    private int initialQuantity;
    private int newQuantity;

    public OrderDialogScreen(){

    }

    public void setOnItemAddedToOrderListener(OnItemAddedToOrder onItemAddedToOrderListener){
        this.onItemAddedToOrder = onItemAddedToOrderListener;
    }
    public void setQuantityChangeListener(OnQuantityChangeListener quantityChangeListener){
        this.quantityChangeListener = quantityChangeListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_full_screen);

        initContent();

        quantityPicker=this;

    }


    private void initContent() {
        i = getIntent();
        item = (Item) i.getSerializableExtra(Constants.KEY_SERIALIZEABLE_ITEM_OBJECT);
        position = i.getIntExtra(Constants.KEY_ITEM_POSITION, -1);
        itemImage = (ImageView)findViewById(R.id.dialog_order_image);



//        circularImageView = (CircularImageView)findViewById(R.id.circularCheffImage);
        circularImageView = (SelectableRoundedImageView)findViewById(R.id.circularCheffImage);
//        circularImageView.setCornerRadiiDP(4, 4, 0, 0);
//        circularImageView.setBorderWidthDP(4);
//        circularImageView.setBorderColor(Color.BLUE);
        circularImageView.setImageResource(R.drawable.gulzar1);
//        circularImageView.setImageResource(R.drawable.chef1);
//        circularImageView.setBackgroundColor(Color.TRANSPARENT);



        textViewItemName = (TextView)findViewById(R.id.dialog_order_itemName);
        textViewItemPrice = (TextView)findViewById(R.id.dialog_order_totalPrice);
        textViewTotalPrice = (TextView) findViewById(R.id.dialog_order_totalPrice);
        textViewQuantity = (TextView)findViewById(R.id.textViewQuantity);

        textViewCategoryName = (TextView)findViewById(R.id.dialog_order_categoryName);

        pricePicker=(NumberPicker)findViewById(R.id.dialog_order_numberPicker);
        pricePicker.setMinValue(1);
        pricePicker.setMaxValue(100);
        pricePicker.setOnValueChangedListener(this);

        buttonDecrement = (Button)findViewById(R.id.buttonDecrement);
        buttonIncrement=(Button)findViewById(R.id.buttonIncrement);

        buttonDecrement.setOnClickListener(new QuantityDecrementListener());
        buttonIncrement.setOnClickListener(new QuantityIncrementListener());

        //////Gallery of images [start]
        //vertical list of item images
        galleryList = (ListView)findViewById(R.id.imageGalleryViewList);
        item_imageArrayList = new ArrayList<Item>();
        DatabaseHelper mDatabaseHelper=new DatabaseHelper(this);

        //fetching items
        item_imageArrayList = mDatabaseHelper.getItemsWithImages(item.getCategory_id());

        galleryList.setAdapter(new ImageAdapter(this,item_imageArrayList));

        cacheDir = OrderDialogScreen.this.getCacheDir();
        galleryList.setOnItemClickListener(new GalleryClickListener());
        //////Gallery Of Images [end]

        backButton =(ImageView)findViewById(R.id.dialog_order_removeButton);
        backButton.setOnClickListener(new BackButtonListener());

        backCatButton = (TextView)findViewById(R.id.dialog_order_categoryName);
        backCatButton.setOnClickListener(new BackButtonListener());

        //////Initializing values
        initValues();

    }

    /**This method initializes & renders the necessary information
     * to the view such as item name, item price, item images, quantity etc
     *
     */
    private void initValues() {
        ////setting item name and Price
        //textViewCategoryName.setText(item.getCategory_id());
        textViewItemName.setText(item.getName());
        textViewItemPrice.setText(String.valueOf(item.getPrice()));

        if(item.getImageBlob() == null){
            itemImage.setImageURI(getImageUri(item));
            //itemImage.setImageBitmap(BitmapFactory.decodeByteArray(item.getImageBlob(),0,item.getImageBlob().length));
        }

        //////Iterate through order list to check if current item is already present in order list
        ////// if it is found, set the price and quantity according to that of order
        if(!OrderContainer.getInstance().getOrderList().isEmpty()){

            ////iterating through order list
            for(Order order: OrderContainer.getInstance().getOrderList() ){

                ////if current item is found in order list
                if(order.getItem().getId()== item.getId()){


                    ////set its price & quantity
                    pricePicker.setValue(order.getQuantityValue());
                    itemQuantity = order.getQuantityValue();
                    setQuantityView(itemQuantity);

                }
//                else{
//                    pricePicker.setValue(1);
//                    itemQuantity = 0;
//                }
            }
        }

        ////if not present in order, set the default price & quantity
        else{
            pricePicker.setValue(1);
            itemQuantity = 1;
            setQuantityView(itemQuantity);
        }

        ////set Total price View according to quantity
//        textViewTotalPrice.setText(getString(R.string.label_total) + "\t " +
//                String.valueOf(getPrice(itemQuantity, item.getPrice())));
        textViewTotalPrice.setText(String.valueOf(getPrice(itemQuantity, item.getPrice())));
    }

    private void setQuantityView(int itemQuantity) {
        textViewQuantity.setText(Integer.toString(itemQuantity)+" "+getString(R.string.items));
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        textViewTotalPrice.setText(String.valueOf(getPrice(newVal, item.getPrice())));
        itemQuantity = newVal;

    }

    @Override
    public void onQuantityValueChange(int oldVal, int newVal) {
        textViewTotalPrice.setText(String.valueOf(getPrice(newVal, item.getPrice())));
        itemQuantity = newVal;
        setQuantityView(itemQuantity);
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
                    if(quantityChangeListener != null){
                        quantityChangeListener.onQuantityChanged();
                    }
                    update = true;

                }

            }

            if(!update){
                onItemAddedToOrder.onItemAdded(position,itemQuantity);

            }

        }
        else{
              onItemAddedToOrder.onItemAdded(position,itemQuantity);
        }


        this.finish();

    }

    private Uri getImageUri(Item item){
        File cacheDir = this.getCacheDir();
        String filePath = item.getName()+item.getCreated_at();
        File imageFile = new File(cacheDir, filePath);
        Uri uri= Uri.fromFile(imageFile);

        return uri;
    }




    private class GalleryClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            filePath =  item_imageArrayList.get(position).getName()+ item_imageArrayList.get(position).getCreated_at();
            cacheFile = new File(cacheDir, filePath);
            uri = Uri.fromFile(cacheFile);

            try {
                // display the images selected
                fileInputStream = new FileInputStream(cacheFile);
                ImageView imageView = (ImageView) findViewById(R.id.dialog_order_image);

                imageView.setImageBitmap(BitmapFactory.decodeStream(fileInputStream));

            } catch (FileNotFoundException e) {
                //handle exception here
            }
        }
    }

    private class QuantityIncrementListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            initialQuantity=itemQuantity;
            itemQuantity ++;

            newQuantity = itemQuantity;

            quantityPicker.onQuantityValueChange(initialQuantity,newQuantity);

        }
    }

    private class QuantityDecrementListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {

            if(itemQuantity > 1 && itemQuantity!=0){
                initialQuantity = itemQuantity;

                itemQuantity--;
                newQuantity = itemQuantity;

                quantityPicker.onQuantityValueChange(initialQuantity,newQuantity);
            }


        }
    }

    private class BackButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
        }
    }
}