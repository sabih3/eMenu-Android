package com.attribe.waiterapp.screens;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import com.attribe.waiterapp.Database.Constants;
import com.attribe.waiterapp.Database.DatabaseHelper;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.controls.ImageAdapter;
import com.attribe.waiterapp.controls.Carousel;
import com.attribe.waiterapp.controls.CarouselAdapter;
import com.attribe.waiterapp.models.Category;

import java.util.ArrayList;

/**
 * Created by Sabih Ahmed on 07-Aug-15.
 */
public class CarouselScreen extends Activity {

    private CategoryScreen.OnCategorySelectListener mCallback;
    private ArrayList<Category> mCategoriesList;
    private DatabaseHelper mDatabaseHelper;
    private ArrayList<Category> categoryListCarousel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen_category_carousel);
        mDatabaseHelper = new DatabaseHelper(this);

        Carousel carousel = (Carousel)findViewById(R.id.carousel);

//        carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        carousel.setOnItemClickListener(new CarouselItemClickListener());


        TypedArray images = getResources().obtainTypedArray(R.array.entries);

        mCategoriesList = mDatabaseHelper.getAllCategories();
        categoryListCarousel =  new ArrayList<>(13);
        ArrayList<Category> repeatedCategories=new ArrayList<>();
        int size = mCategoriesList.size();

        for(int i = 0; i < size  ; i++){

            if(!mCategoriesList.get(i).getName().equalsIgnoreCase("Beverages")){
                mCategoriesList.get(i).setCarouselImage(images.getDrawable(i));
                categoryListCarousel.add(mCategoriesList.get(i));

            }

        }

        categoryListCarousel.add(mCategoriesList.get(2));
        categoryListCarousel.add(mCategoriesList.get(3));
//        categoryListCarousel.add(mCategoriesList.get(4));
//        categoryListCarousel.add(mCategoriesList.get(5));
//        categoryListCarousel.add(mCategoriesList.get(2));
//        categoryListCarousel.add(mCategoriesList.get(3));


        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageAdapter.SetImages(categoryListCarousel);


        carousel.setAdapter(imageAdapter);

    }



    private void showMenuScreen(int id) {
        Intent intent = new Intent(CarouselScreen.this, MainActivity.class);
        intent.putExtra(Constants.EXTRA_CATEGORY_ID, id);
        startActivity(intent);
        //TODO: UNCOMMENT BELOW FOR PRODUCTION BUILD
        //finish();
    }

    private class CarouselItemClickListener implements CarouselAdapter.OnItemClickListener {

        @Override
        public void onItemClick(CarouselAdapter<?> parent, View view, int position, long id) {

            categoryListCarousel.get(position).setSelected(true);
            showMenuScreen(categoryListCarousel.get(position).getId());

        }

    }
}