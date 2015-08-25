package com.attribe.waiterapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.attribe.waiterapp.R;
import com.attribe.waiterapp.adapters.CategoryItemAdapter.ViewHolder;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.models.ItemImage;

public class HAdapter extends BaseAdapter {
	
	
	    private Context context;
	    private ArrayList<Item> item_imageArrayList;
	    private static LayoutInflater inflater = null;

    public HAdapter(Context context , ArrayList<Item> itemImageList ) {
    	
    	this.context = context;
        this.item_imageArrayList = itemImageList;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

	@Override
	public int getCount() {
		
		 return item_imageArrayList.size();
	}

	@Override
	public Object getItem(int i ) {
		
		  return item_imageArrayList.get(i);
	}

	@Override
	public long getItemId(int i) {
		
		return i;
	}
	
	
public static class ViewHolder {
    	
    	
        public ImageView menu_itemImage;
      //  public int position;


    }

	@Override
	public View getView(int position , View view, ViewGroup viewGroup) {
		
		final ViewHolder viewHolder ;
		 if(view == null){

			 view  = inflater.inflate(R.layout.horizontal_list_image_new, null);
           
           viewHolder =new ViewHolder();
           
           viewHolder.menu_itemImage = (ImageView)view.findViewById(R.id.dialog_menu_image);
           
           
           

           view.setTag(viewHolder);

       }
		 
		 else{

	            viewHolder = (ViewHolder) view.getTag();
	        }
		
		 viewHolder.menu_itemImage.setTag(position); 
		
		return view;
	}
	
	
} 



