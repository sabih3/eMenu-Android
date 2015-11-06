package com.attribe.genericwaiterapp.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.attribe.genericwaiterapp.Database.DatabaseHelper;
import com.attribe.genericwaiterapp.R;
import com.attribe.genericwaiterapp.adapters.ImageAdapter;
import com.attribe.genericwaiterapp.adapters.SwipeImageAdapter;
import com.attribe.genericwaiterapp.interfaces.OnItemAddedToOrder;
import com.attribe.genericwaiterapp.interfaces.OnQuantityChangeListener;
import com.attribe.genericwaiterapp.interfaces.QuantityPicker;
import com.attribe.genericwaiterapp.models.Image;
import com.attribe.genericwaiterapp.models.Item;
import com.attribe.genericwaiterapp.models.Order;
import com.attribe.genericwaiterapp.utils.OrderContainer;
import com.attribe.genericwaiterapp.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/14/2015.
 */
public class OrderDialogScreen extends Activity implements QuantityPicker{

    private TextView textViewItemName,
            textViewTotalPrice,
            textViewItemPrice,
            textViewQuantity,
            textViewCategoryName;
    private NumberPicker pricePicker;
    private ImageView itemImage;
    private ImageView backButton;
    private TextView  backToMainScreen;
    private Item item;
    private Intent i;
    private int itemQuantity;
    private int position;
    private static CopyOnWriteArrayList<Order> orderList;
    private static OnQuantityChangeListener quantityChangeListener;
    private static OnItemAddedToOrder onItemAddedToOrder;
    private ListView galleryList;
    private List<Image> item_imageArrayList;
    private Button buttonIncrement;
    private Button buttonDecrement;

    public Context mContext;
    public File cacheDir;
    public String filePath;
    public File cacheFile;
    public Uri uri;
    public InputStream fileInputStream;
    public DatabaseHelper databaseHelper;
    private QuantityPicker quantityPicker;
    private int initialQuantity;
    private int newQuantity;
    private SwipeImageAdapter swipeImageAdapter;
    private ImageAdapter verticalGalleryAdapter;
    private ViewPager viewPager;

    private int oldSelectedPosition = -1;       // no item selected by default

    public OrderDialogScreen(){}

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
        databaseHelper = new DatabaseHelper(this);
        item = (Item) i.getSerializableExtra(Constants.KEY_SERIALIZEABLE_ITEM_OBJECT);
        position = i.getIntExtra(Constants.KEY_ITEM_POSITION, -1);

        textViewItemName = (TextView)findViewById(R.id.dialog_order_itemName);
        textViewItemPrice = (TextView)findViewById(R.id.dialog_order_totalPrice);
        textViewTotalPrice = (TextView) findViewById(R.id.dialog_order_totalPrice);
        textViewQuantity = (TextView)findViewById(R.id.textViewQuantity);
        textViewCategoryName = (TextView)findViewById(R.id.dialog_order_categoryName);

        pricePicker=(NumberPicker)findViewById(R.id.dialog_order_numberPicker);
        pricePicker.setMinValue(1);
        pricePicker.setMaxValue(100);

        buttonDecrement = (Button)findViewById(R.id.buttonDecrement);
        buttonIncrement=(Button)findViewById(R.id.buttonIncrement);

        buttonDecrement.setOnClickListener(new QuantityDecrementListener());
        buttonIncrement.setOnClickListener(new QuantityIncrementListener());

        //////Gallery of images [start]
        //vertical list of item images
        galleryList = (ListView)findViewById(R.id.imageGalleryViewList);
        item_imageArrayList = item.getImages();
        verticalGalleryAdapter=new ImageAdapter(this,item_imageArrayList,item.getName(),item.getCreated_at());

        galleryList.setAdapter(verticalGalleryAdapter);

        cacheDir = OrderDialogScreen.this.getCacheDir();
        galleryList.setOnItemClickListener(new GalleryClickListener());

        //////Gallery Of Images [end]

        backButton =(ImageView)findViewById(R.id.dialog_order_removeButton);
        backButton.setOnClickListener(new BackButtonListener());
        backToMainScreen = (TextView) findViewById(R.id.dialog_order_categoryName);
        backToMainScreen.setOnClickListener(new BackButtonListener());

        viewPager = (ViewPager) findViewById(R.id.pager);
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        swipeImageAdapter = new SwipeImageAdapter(this,item.getImages(),item.getName(),item.getCreated_at());
        viewPager.setAdapter(swipeImageAdapter);

        viewPager.setOnPageChangeListener(new SwipeImageListener());
        // displaying selected image first
        viewPager.setCurrentItem(position);
        //Initializing values
        initValues();

    }

    /**This method initializes & renders the necessary information
     * to the view such as item name, item price, item images, quantity etc
     *
     */
    private void initValues() {
        ////setting item name and Price
        textViewItemName.setText(item.getName());
        textViewItemPrice.setText(String.valueOf(item.getPrice()));

        textViewCategoryName.setText(databaseHelper.getCategoryName(item.getCategory_id()));
        textViewCategoryName.setOnClickListener(new BackNavigationListener());

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
            }
        }

        ////if not present in order, set the default price & quantity
        else{
            pricePicker.setValue(1);
            itemQuantity = 1;
            setQuantityView(itemQuantity);
        }

        ////set Total price View according to quantity
        textViewTotalPrice.setText(String.valueOf(getPrice(itemQuantity, item.getPrice())));
    }

    private void setQuantityView(int itemQuantity) {
        textViewQuantity.setText(Integer.toString(itemQuantity) + " " + getString(R.string.items));
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

        //Image file is saved in following pattern
        //filepath = ItemName+ImageFileCreationDateTime
        File cacheDir = this.getCacheDir();
        Uri uri = null;
        if(!item.getImages().isEmpty()){
            String filePath = item.getName()+item.getImages().get(0).getCreated_at(); // placing first image of item
            File imageFile = new File(cacheDir, filePath);
            uri= Uri.fromFile(imageFile);
        }
        return uri;
    }

    private void showImages(String filePath){

        cacheFile = new File(cacheDir, filePath);
        uri = Uri.fromFile(cacheFile);

        try {
              fileInputStream = new FileInputStream(cacheFile);

            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        itemImage.setImageBitmap(BitmapFactory.decodeStream(fileInputStream));
    }




    private class GalleryClickListener implements AdapterView.OnItemClickListener {

        View res;

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            viewPager.setCurrentItem(position, true);

           if(oldSelectedPosition != position) {
               if(oldSelectedPosition != -1){

                   item_imageArrayList.get(oldSelectedPosition).setSelected(false);
               }
               item_imageArrayList.get(position).setSelected(true);
               verticalGalleryAdapter.notifyDataSetChanged();
           }
              oldSelectedPosition=position;

            verticalGalleryAdapter.notifyDataSetChanged();
        }
    }


    private class SwipeImageListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {}

        @Override
        public void onPageSelected(int position) {

            if(oldSelectedPosition != position) {
                if(oldSelectedPosition != -1){

                    item_imageArrayList.get(oldSelectedPosition).setSelected(false);
                }
                item_imageArrayList.get(position).setSelected(true);
                verticalGalleryAdapter.notifyDataSetChanged();
            }
            oldSelectedPosition=position;

            verticalGalleryAdapter.notifyDataSetChanged();

        }

        @Override
        public void onPageScrollStateChanged(int i) {}

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

    private class BackNavigationListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            OrderDialogScreen.this.finish();
        }
    }
}