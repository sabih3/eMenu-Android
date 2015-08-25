package com.attribe.waiterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OrderGalleryAdapter extends BaseAdapter {
	
	
	private Context context;
	private static LayoutInflater inflater = null;
	
	
	public OrderGalleryAdapter(Context context) {
		
		 this.context = context;
	      
	     inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
        
	}
	

	@Override
	public int getCount() {
		
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
