package com.attribe.waiterapp.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.OrderPageAdapter;
import com.attribe.waiterapp.interfaces.PrintOrder;
import com.attribe.waiterapp.utils.OrderContainer;

/**
 * Created by Sabih Ahmed on 21-Oct-15.
 */
public class OrderTakingScreen extends Activity {

    private ListView orderPageList;
    private ImageView printButton;
    private static PrintOrder printOrder;

    public OrderTakingScreen(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_taking);

        initView();
        setOrderList();
    }

    public void setOrderPrintInterface(PrintOrder printOrder){

        this.printOrder = printOrder;
    }
    private void setOrderList() {

        OrderPageAdapter adapter = new OrderPageAdapter(this, OrderContainer.getInstance().getOrderList());
        orderPageList.setAdapter(adapter);
    }

    private void initView() {

        orderPageList = (ListView)findViewById(R.id.order_page_list);
        printButton = (ImageView)findViewById(R.id.order_page_print);

        printButton.setOnClickListener(new PrintClickListener());
    }

    private class PrintClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            OrderTakingScreen.this.finish();
            printOrder.onOrderSentToPrint();
        }
    }
}