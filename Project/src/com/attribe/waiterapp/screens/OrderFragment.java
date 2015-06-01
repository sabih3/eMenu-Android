package com.attribe.waiterapp.screens;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.OrderAdapter;
import com.attribe.waiterapp.interfaces.OnItemRemoveListener;
import com.attribe.waiterapp.models.*;
import com.attribe.waiterapp.network.RestClient;
import com.attribe.waiterapp.utils.Constants;
import com.attribe.waiterapp.utils.OrderContainer;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/26/2015.
 */
public class OrderFragment extends Fragment implements GridView.OnItemClickListener,OnItemRemoveListener{

    public GridView ordergrid;
    private OrderAdapter orderAdapter;
    private CopyOnWriteArrayList<Order> orderList;
    private TextView totalText, totalPrice;
    private Button confirmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        initContents(view);
        return view;
    }

    private void initContents(View view) {

        long total = 0;
        totalText= (TextView) view.findViewById(R.id.fragment_order_textTotal);
        totalPrice = (TextView) view.findViewById(R.id.fragment_order_textTotalPrice);
        ordergrid = (GridView) view.findViewById(R.id.fragment_order_grid);
        confirmButton =(Button)view.findViewById(R.id.fragment_order_confirmButton);
        orderList = OrderContainer.getInstance().getOrderList();


        orderAdapter = new OrderAdapter(getActivity(),orderList);

        totalPrice.setText(Double.toString(computeTotalPrice()));

        ordergrid.setAdapter(orderAdapter);
        ordergrid.setOnItemClickListener(this);

        confirmButton.setOnClickListener(new ComfirmButtonClick());
        orderAdapter.setOnItemRemoveListener(this);


    }

    public static OrderFragment getInstance(){
        OrderFragment orderFragment = new OrderFragment();

        return orderFragment;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Order orderItem = (Order) adapterView.getItemAtPosition(i);
        showOrderDialog(orderItem.getItem());
    }


    private void showOrderDialog(Item item){

        Intent intent = new Intent(getActivity(),OrderDialogScreen.class);
        intent.putExtra(Constants.KEY_SERIALIZEABLE_ITEM_OBJECT, item);

        startActivity(intent);

    }

    @Override
    public void onItemRemoved() {
        //totalPrice.invalidate();
        totalPrice.setText(Double.toString(computeTotalPrice()));
    }

    private double computeTotalPrice() {
        double total = 0;
        for(Order order :OrderContainer.getInstance().getOrderList()){

            total += order.getItem().getPrice() * order.getQuantityValue();
        }

        return total;
    }

    public class ComfirmButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if(OrderContainer.getInstance().getOrderList().isEmpty()){
                Toast.makeText(getActivity(),"Please select items to order",Toast.LENGTH_SHORT).show();
            }
            else{

                showConfirmDialog();
            }
        }

        private void showConfirmDialog() {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.proceed_to_order))
                    .setMessage(getString(R.string.proceed_to_order_message))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            placeOrder(makeOrder(getOrderDetail(OrderContainer.getInstance().getOrderList()), getDeviceId(getActivity()),computeTotalPrice()));

                            Toast.makeText(getActivity(),"Order Placed",Toast.LENGTH_SHORT).show();
                            OrderContainer.getInstance().getOrderList().clear();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }

    private void placeOrder(OrderDetail orderDetail) {

        RestClient.getAdapter().placeOrder(orderDetail, new Callback<order_detail.Response>() {

            @Override
            public void success(order_detail.Response response, Response response2) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }

    private OrderDetail makeOrder(ArrayList<order_detail> orderDetail, String deviceId, double orderTotal) {

        OrderDetail deviceOrder=new OrderDetail(deviceId,orderDetail,orderTotal);

        return deviceOrder;
    }

    private static String getDeviceId(Context context){

        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        //String deviceId = "QWERTY111";

        return deviceId;
    }

    private ArrayList<order_detail> getOrderDetail(CopyOnWriteArrayList<Order> orderList){
        ArrayList<order_detail> order_detail = new ArrayList<>();

        for(int i =  0;i < orderList.size(); i++){


            int itemId = orderList.get(i).getItem().getId();
            int quantityValue = orderList.get(i).getQuantityValue();
            double item_total = orderList.get(i).getItem().getPrice()*orderList.get(i).getQuantityValue();

            order_detail order=new order_detail(itemId,quantityValue,item_total);
            order_detail.add(order);
        }

        return order_detail;
    }

}