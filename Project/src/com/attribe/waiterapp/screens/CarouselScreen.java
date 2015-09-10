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
import com.attribe.waiterapp.Database.Constants;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.controls.ImageAdapter;
import com.attribe.waiterapp.controls.Carousel;
import com.attribe.waiterapp.controls.CarouselAdapter;

/**
 * Created by Sabih Ahmed on 07-Aug-15.
 */
public class CarouselScreen extends Activity {

    CategoryScreen.OnCategorySelectListener callBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen_category_carousel);
        Carousel carousel = (Carousel)findViewById(R.id.carousel);

        carousel.setOnItemClickListener(new CarouselItemClickListener());

        TypedArray images = getResources().obtainTypedArray(R.array.entries);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageAdapter.SetImages(images,false);

        carousel.setAdapter(imageAdapter);
    }



    /**
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.screen_category_carousel,container,false);
        Carousel carousel = (Carousel)view.findViewById(R.id.carousel);

        carousel.setOnItemClickListener(new CarouselItemClickListener());

        return view;
    }
     **/

    /**
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callBack = (CategoryScreen.OnCategorySelectListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    +"must implement onCategorySelect Listener");
        }
    }**/

    private void showMenuScreen(int id) {
        Intent intent = new Intent(CarouselScreen.this, MainActivity.class);
        intent.putExtra(Constants.EXTRA_CATEGORY_ID, id);
        startActivity(intent);
        //TODO: UNCOMMENT BELOW FOR PRODUCTION BUILD
        //finish();
    }

    private class CarouselItemClickListener implements com.attribe.waiterapp.controls.CarouselAdapter.OnItemClickListener {

        @Override
        public void onItemClick(CarouselAdapter<?> parent, View view, int position, long id) {
            showMenuScreen(position);

        }
    }
}