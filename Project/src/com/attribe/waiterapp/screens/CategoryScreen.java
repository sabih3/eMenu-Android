package com.attribe.waiterapp.screens;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.attribe.waiterapp.Database.DatabaseHelper;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.CategoryAdapter;
import com.attribe.waiterapp.models.Category;
import com.attribe.waiterapp.network.RestClient;
import com.attribe.waiterapp.utils.DevicePreferences;
import com.attribe.waiterapp.utils.ExceptionHanlder;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

/**
 * Created by Sabih Ahmed on 5/12/2015.
 */

public class CategoryScreen extends ListFragment  {

    OnCategorySelectListener callBack;
    private CategoryAdapter listAdapter;
    private ArrayList<Category> categoryArrayList;

    public CategoryScreen(){

    }

    public void setOnCategorySelecListener(OnCategorySelectListener callBack, int id){

        this.callBack=callBack;
        callBack.onCategorySelected(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHanlder(getActivity()));
        categoryArrayList = new ArrayList<>();

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(getActivity());
        categoryArrayList= mDatabaseHelper.getAllCategories();



        listAdapter = new CategoryAdapter(getActivity(), categoryArrayList);

        setListAdapter(listAdapter);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_screen, container,false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callBack = (OnCategorySelectListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    +"must implement onCategorySelect Listener");
        }
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {

        long itemIdAtPosition = listView.getItemIdAtPosition(position);

        categoryArrayList.get(position).setSelected(true);
        listAdapter.notifyDataSetChanged();
        callBack.onCategorySelected(itemIdAtPosition);
    }



    public interface OnCategorySelectListener{

        public void onCategorySelected(long id);
    }
}