package com.attribe.waiterapp.adapters;

import android.app.Service;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.models.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Sabih Ahmed on 5/12/2015.
 */
public class CategoryAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Category> categoryArrayList;
    public CategoryAdapter(Context context , ArrayList<Category> categoryArrayList){
        this.context = context;
        this.categoryArrayList = categoryArrayList;

    }
    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {

        return categoryArrayList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater  layoutInflater= (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.list_item_category, null);

        TextView categoryName= (TextView) row.findViewById(R.id.list_item_category);
        ImageView imageView=(ImageView)row.findViewById(R.id.list_item_category_image);

        if(categoryArrayList.get(position).getImageBlob()==null){

            File cacheDir = context.getCacheDir();
            String filePath = categoryArrayList.get(position).getName()+categoryArrayList.get(position).getCreated_at();
            File cacheFile = new File(cacheDir, filePath);
            try {
                InputStream fileInputStream=new FileInputStream(cacheFile);

                imageView.setImageBitmap(BitmapFactory.decodeStream(fileInputStream));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


//            imageView.setImageBitmap(BitmapFactory.decodeByteArray(categoryArrayList.get(position).getImageBlob(),
//                    0,
//                    categoryArrayList.get(position).getImageBlob().length));
        }

        categoryName.setText(categoryArrayList.get(position).getName());


//        categoryName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                categoryName.setBackground(context.getResources().getDrawable(R.drawable.dialog_order_bg));
//                categoryName.setTextColor(context.getResources().getColor(R.color.maroon));
//            }
//        });
        return row;
    }
}
