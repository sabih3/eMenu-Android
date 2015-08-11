package com.attribe.waiterapp.screens;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.attribe.waiterapp.Database.Constants;
import com.attribe.waiterapp.R;
import android.support.v4.app.FragmentActivity;
import com.attribe.waiterapp.utils.ExceptionHanlder;

public class MainActivity extends FragmentActivity implements CategoryScreen.OnCategorySelectListener,
        FragmentManager.OnBackStackChangedListener{

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
        CategoryItemScreen itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        if(itemFragment.getView().getVisibility() == View.VISIBLE){
            itemFragment.getView().setVisibility(View.GONE);
        }

        //TODO: REMOVE THIS FOR PRODUCTION BUILD
        this.finish();

    }

    @Override
    public void onCategorySelected(long id) {
        //Show Grid of items of required category
        CategoryItemScreen itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        itemFragment.updateFragment(id);


//        if(DevicePreferences.getInstance().isRtlLayout()){
//            CategoryItemScreen itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_rtl_itemScreen);
//            itemFragment.updateFragment(id);
//        }
//        else{
//            CategoryItemScreen itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
//            itemFragment.updateFragment(id);
//        }


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


        CategoryItemScreen itemScreen= (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        itemScreen.showOrderFragment();

//        if(DevicePreferences.getInstance().isRtlLayout()){
//            CategoryItemScreen itemScreen= (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_rtl_itemScreen);
//            itemScreen.showOrderFragment();
//        }
//        else{
//            CategoryItemScreen itemScreen= (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
//            itemScreen.showOrderFragment();
//        }


    }


}
