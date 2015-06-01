package com.attribe.waiterapp.screens;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_maroon));
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHanlder(this));

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.main);
        //getFragmentManager().addOnBackStackChangedListener(this);

    }

    @Override
    public void onBackPressed() {



    }

    @Override
    public void onCategorySelected(long id) {
        CategoryItemScreen itemFragment = (CategoryItemScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        itemFragment.updateFragment(id);
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

    }


}