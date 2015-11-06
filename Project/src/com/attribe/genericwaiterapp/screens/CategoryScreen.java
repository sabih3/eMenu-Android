package com.attribe.genericwaiterapp.screens;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.genericwaiterapp.Database.DatabaseHelper;
import com.attribe.genericwaiterapp.R;
import com.attribe.genericwaiterapp.adapters.CategoryAdapter;
import com.attribe.genericwaiterapp.models.Category;
import com.attribe.genericwaiterapp.utils.ExceptionHanlder;

import java.util.ArrayList;

/**
 * Created by Sabih Ahmed on 5/12/2015.
 */

public class CategoryScreen extends Fragment implements AdapterView.OnItemClickListener{

    OnCategorySelectListener callBack;
    private CategoryAdapter listAdapter;
    private ArrayList<Category> categoryArrayList;
    private ListView listView;
    private int oldPosition = - 1;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_screen, container,false);
        listView=(ListView)view.findViewById(R.id.catrgoryScreen_list);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(listAdapter);

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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        if(oldPosition != position){
            if(oldPosition !=-1){
                categoryArrayList.get(oldPosition).setSelected(false);
            }

            categoryArrayList.get(position).setSelected(true);
            listAdapter.notifyDataSetChanged();
        }

        oldPosition=position;

        callBack.onCategorySelected(id);
    }


    public interface OnCategorySelectListener{

        public void onCategorySelected(long id);
    }
}