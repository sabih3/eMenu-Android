package com.attribe.waiterapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.models.Order;
import com.attribe.waiterapp.screens.OrderDialogScreen;
import com.attribe.waiterapp.utils.Constants;
import com.attribe.waiterapp.utils.OrderContainer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sabih Ahmed on 5/13/2015.
 */
public class CategoryItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> itemList;


    public CategoryItemAdapter(Context context, ArrayList<Item> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position,  View view, ViewGroup viewGroup) {
        ViewHolder viewHolder =new ViewHolder();

        if(view == null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  = inflater.inflate(R.layout.list_item_grid,null);
            viewHolder=createViewHolder(view);
            view.setTag(viewHolder);

        }

        else{

            viewHolder = (ViewHolder) view.getTag();
        }

        byte[] imagesBlob = itemList.get(position).getImageBlob();

        if(imagesBlob != null){

            viewHolder.itemImage.setImageBitmap(BitmapFactory.decodeByteArray(imagesBlob,0,
                    imagesBlob.length));


        }
        viewHolder.itemName.setText(itemList.get(position).getName());

        viewHolder.itemPrice.setText(String.valueOf(itemList.get(position).getPrice()));

        viewHolder.gridItemCheckBox.setBackgroundColor(context.getResources().getColor(R.color.white));
        viewHolder.gridItemCheckBox.setChecked(itemList.get(position).isSelected());

        if(itemList.get(position).isSelected()){
            viewHolder.listItemGridLayout.setBackground(context.getResources().getDrawable(R.drawable.grid_item_background_selected));
        }

        else{
            viewHolder.listItemGridLayout.setBackground(context.getResources().getDrawable(R.drawable.grid_item_background));
        }

        final View finalView = view;
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.gridItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                itemList.get(position).setSelected(compoundButton.isChecked());

                if (itemList.get(position).isSelected()) {

                    Order order = new Order(itemList.get(position), 1);
                    if(OrderContainer.getInstance().getOrderList().contains(order)){

                    }
                    finalViewHolder.listItemGridLayout.setBackground(context.getResources().getDrawable(R.drawable.grid_item_background_selected));

                    itemList.get(position).setSelected(true);
                    OrderContainer.getInstance().putOrder(order);


                } else {

                    itemList.get(position).setSelected(false);
                    CopyOnWriteArrayList<Order> orderList = OrderContainer.getInstance().getOrderList();



                    ArrayList<Order> toRemoveList=new ArrayList<Order>();
                    for (Order o : orderList) {

                        if (o.getItem().getId() == itemList.get(position).getId()) {
                            toRemoveList.add(o);

                        }
                    }

                    orderList.removeAll(toRemoveList);
                    finalViewHolder.listItemGridLayout.setBackground(context.getResources().getDrawable(R.drawable.grid_item_background));

                }

                finalViewHolder.gridItemCheckBox.setChecked(compoundButton.isChecked());

            }

        });


        return view;
    }

    private ViewHolder createViewHolder(View view) {
        ViewHolder holder=new ViewHolder();

        holder.itemName = (TextView) view.findViewById(R.id.grid_item_itemName);
        holder.itemPrice = (TextView)view.findViewById(R.id.grid_item_price);
        holder.gridItemCheckBox = (CheckBox) view.findViewById(R.id.grid_item_check);
        holder.listItemGridLayout = (LinearLayout) view.findViewById(R.id.list_item_grid_parent);
        holder.itemImage = (ImageView)view.findViewById(R.id.grid_item_image);
        return holder;
    }


    public static class ViewHolder {
        TextView itemName, itemPrice;
        CheckBox gridItemCheckBox;
        LinearLayout listItemGridLayout;
        ImageView itemImage;


    }

    private void showOrderDialog(Item item) {
        Intent intent = new Intent(context,OrderDialogScreen.class);
        intent.putExtra(Constants.KEY_SERIALIZEABLE_ITEM_OBJECT,item);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
