package com.attribe.waiterapp.screens;

import android.app.ActionBar;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.attribe.waiterapp.Database.Constants;
import com.attribe.waiterapp.Database.DatabaseHelper;
import com.attribe.waiterapp.R;
import android.support.v4.app.FragmentActivity;
import com.attribe.waiterapp.adapters.CategoryItemAdapter;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.models.Order;
import com.attribe.waiterapp.utils.ExceptionHanlder;
import com.attribe.waiterapp.utils.OrderContainer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends FragmentActivity implements CategoryScreen.OnCategorySelectListener,
        FragmentManager.OnBackStackChangedListener{

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private CategoryItemScreen dynamicFragment;
    private ArrayList<Item> itemArrayList;
    private CopyOnWriteArrayList<Order> orderList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting Action bar's color programmatically
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));
        //actionBar.setIcon(R.drawable.dm_logo_white);

        //Handling Exceptions
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHanlder(this));

        setContentView(R.layout.main);

        // First time when user comes from the carousel view, the code lands here for setting the required category
        CategoryScreen categoryScreen = new CategoryScreen();
        if(getIntent().getExtras().get(Constants.EXTRA_CATEGORY_ID)!=null){
            categoryScreen.setOnCategorySelecListener(this, (Integer) getIntent().getExtras().get(Constants.EXTRA_CATEGORY_ID));
        }





    }

    @Override
    public void onBackPressed() {
        //If Grid of Items is visible, hide that

        TODO://Removed Hardcoded fragment
        /**
        CategoryItemScreen itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        if(itemFragment.getView().getVisibility() == View.VISIBLE){
            itemFragment.getView().setVisibility(View.GONE);
        }**/

        //TODO: REMOVE THIS FOR PRODUCTION BUILD
        this.finish();

    }

    @Override
    public void onCategorySelected(long id) {

        Bundle args = new Bundle();
        args.putLong(Constants.EXTRA_CATEGORY_ID,id);
        CategoryItemScreen itemScreen = CategoryItemScreen.getInstance();
        itemScreen.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_items, itemScreen)
                .commit();


        //  CategoryItemScreen.getInstance().updateFragment(id);





        //Show Grid of items of required category

        //TODO:Removed HardCoded Fragment
        //Fragment callingFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);


        /**
        if(callingFragment instanceof CategoryItemScreen){


            CategoryItemScreen itemFragment=null;
            itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
            itemFragment.updateFragment(id);
        }

        else if(callingFragment instanceof OrderFragment){
            fragmentManager = getSupportFragmentManager();
            dynamicFragment = CategoryItemScreen.getInstance();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_items, dynamicFragment);
            fragmentTransaction.commit();

            if(getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen) instanceof OrderFragment){


                CategoryItemScreen categoryFragment;
                categoryFragment=(CategoryItemScreen)(dynamicFragment);
                categoryFragment.updateFragment(id);
            }**/


            /**
            //User in on order screen, and has tapped any of the category item
            OrderFragment itemFragment = null;
            itemFragment = (OrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
            //hiding the Order screen
            itemFragment.getView().findViewById(R.id.fragment_order_amountLayout).setVisibility(View.GONE);
            //fetching the items of desired category
            itemFragment.updateFragment(id);
            **/

       // }

    }



    @Override
    public void onBackStackChanged() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_cart:
                //openOrderScreen();
                showOrderFragment();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showOrderFragment() {

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen));
//        fragmentTransaction.add(R.id.fragment_container_items, OrderFragment.getInstance());
        fragmentTransaction.replace(R.id.fragment_container_items, OrderFragment.getInstance());
        fragmentTransaction.commit();

        /**
        CategoryItemScreen itemScreen= (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        itemScreen.showOrderFragment();
        **/


    }


}
