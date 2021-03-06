package com.attribe.waiterapp.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.attribe.waiterapp.R;
import com.attribe.waiterapp.models.Item;


public class ImageAdapter extends BaseAdapter {
    	
	private int itemBackground;
	
	private Context mContext;
    private ArrayList<Item> item_imageArrayList;
    private LayoutInflater inflater = null;
    
	 private File cacheDir;
	 private String filePath;
	 private File cacheFile;
	 private Uri uri;
	 private InputStream fileInputStream;
	
	
	public ImageAdapter(Context context , ArrayList<Item> itemList){
	
        this.mContext=context;
        this.item_imageArrayList=itemList;

	}
	
	

	public int getCount() {
		return item_imageArrayList.size();
	}

	public Object getItem(int position) {
		return item_imageArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	
	
	@Override
	public View getView(int position , View view, ViewGroup viewGroup) {

		ViewHolder viewHolder ;

		if(view == null){

			LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view  = inflater.inflate(R.layout.new_order_image_screen_dialogue, null);

			viewHolder =new ViewHolder();
			viewHolder.imageViewItem = (ImageView) view.findViewById(R.id.iv_new_order_image);

			view.setTag(viewHolder);
		}

		else{

			viewHolder = (ViewHolder) view.getTag();
		}
		
		cacheDir = mContext.getCacheDir();
		filePath =  item_imageArrayList.get(position).getName()+ item_imageArrayList.get(position).getCreated_at();
		cacheFile = new File(cacheDir, filePath);
        uri = Uri.fromFile(cacheFile);
        
        try {
            fileInputStream = new FileInputStream(cacheFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

		viewHolder.imageViewItem.setImageBitmap(BitmapFactory.decodeStream(fileInputStream));

		return view;
	}


	private static class ViewHolder{

		ImageView imageViewItem ;
	}

	
}
