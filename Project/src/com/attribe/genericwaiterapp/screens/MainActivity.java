package com.attribe.genericwaiterapp.screens;

import android.app.ActionBar;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.attribe.genericwaiterapp.Database.Constants;
import com.attribe.genericwaiterapp.R;
import android.support.v4.app.FragmentActivity;
import com.attribe.genericwaiterapp.models.Item;
import com.attribe.genericwaiterapp.models.Order;
import com.attribe.genericwaiterapp.utils.ExceptionHanlder;

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
                showOrderFragment();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showOrderFragment() {

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_items, OrderFragment.getInstance());
        fragmentTransaction.commit();

    }


}