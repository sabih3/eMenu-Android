package com.attribe.waiterapp.screens;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.waiterapp.Database.DatabaseHelper;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.CategoryItemAdapter;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.models.Order;
import com.attribe.waiterapp.network.RestClient;
import com.attribe.waiterapp.utils.Constants;
import com.attribe.waiterapp.utils.OrderContainer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/12/2015.
 */

public class CategoryItemScreen extends Fragment implements GridView.OnItemClickListener,FragmentManager.OnBackStackChangedListener{

    private ListView listView;
    private GridView gridView;
    Fragment detailFragment;
    private OrderFragment orderFragment;
    private ArrayList<Item> itemArrayList;
    private CheckBox gridItemCheckBox;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private CopyOnWriteArrayList<Order> orderList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catergory_items, container, false);
        gridView=(GridView)view.findViewById(R.id.item_grid);
        itemArrayList = new ArrayList<Item>();
        CategoryItemAdapter adapter = new CategoryItemAdapter(getActivity(), itemArrayList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);



       return view;
    }



    public void updateFragment(long id){


        DatabaseHelper mDatabaseHelper=new DatabaseHelper(getActivity());
        itemArrayList = mDatabaseHelper.getItemsWithImages(id);


        orderList = OrderContainer.getInstance().getOrderList();
        if(! orderList.isEmpty()){



            for(int i = 0 ; i<orderList.size(); i++){
                if(orderList.get(i).getItem().getCategory_id()== id){
                    for(int x = 0; x < itemArrayList.size() ; x++){

                        if(itemArrayList.get(x).getId() == orderList.get(i).getItem().getId()){
                            itemArrayList.get(x).setSelected(true);
                            continue;
                        }


                    }


                }


            }


        }


        CategoryItemAdapter adapter = new CategoryItemAdapter(getActivity(), itemArrayList);

        if(detailFragment!=null){
            if(detailFragment.getView()!=null){
                detailFragment.getView().setVisibility(View.GONE);
            }
        }

        Fragment itemFragment = getFragmentManager().findFragmentById(R.id.fragment_itemScreen);
        if(!itemFragment.isVisible()){

            itemFragment.getView().setVisibility(View.VISIBLE);
            gridView.setVisibility(View.VISIBLE);
        }

        if( orderFragment != null) {
            if (orderFragment.isVisible()) {
                orderFragment.getView().setVisibility(View.GONE);
            }
        }
        gridView.setAdapter(adapter);
        gridView.setVisibility(View.VISIBLE);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        CheckBox itemCheckBox= (CheckBox) view.findViewById(R.id.grid_item_check);
        itemArrayList.get(i).setSelected(true);
        itemCheckBox.setChecked(true);
        showOrderDialog(itemArrayList.get(i));

    }

    @Override
    public void onBackStackChanged() {


    }



    private void showOrderDialog(Item item) {
        Intent intent = new Intent(getActivity(),OrderDialogScreen.class);
        intent.putExtra(Constants.KEY_SERIALIZEABLE_ITEM_OBJECT, item);

        startActivity(intent);

    }

    public void showOrderFragment() {
        fragmentManager = getFragmentManager();


        gridView.setVisibility(View.GONE);


        orderFragment = OrderFragment.getInstance();


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, orderFragment);
        //fragmentTransaction.hide(CategoryItemScreen.this);
        fragmentTransaction.addToBackStack(CategoryItemScreen.class.getName());
        fragmentTransaction.commit();

    }
}