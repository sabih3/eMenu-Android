package com.attribe.waiterapp.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attribe.waiterapp.Database.DatabaseHelper;
import com.attribe.waiterapp.Database.TABLE_CATEGORIES;
import com.attribe.waiterapp.R;
import com.attribe.waiterapp.interfaces.onDbCreate;
import com.attribe.waiterapp.models.Category;
import com.attribe.waiterapp.models.DeviceRegister;
import com.attribe.waiterapp.models.Item;
import com.attribe.waiterapp.network.PassCodeResponse;
import com.attribe.waiterapp.network.RestClient;

import com.attribe.waiterapp.utils.DevicePreferences;
import com.attribe.waiterapp.utils.ExceptionHanlder;
import com.google.gson.Gson;
import org.apache.http.util.ByteArrayBuffer;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabih Ahmed on 5/18/2015.
 */
public class SplashScreen extends Activity{

	private Handler postDelayedHandler;
	private DatabaseHelper databaseHelper;

	private TABLE_CATEGORIES table_categories;
	ArrayList<Integer> categoryIdList;
	private Gson gson;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHanlder(this));
		setContentView(R.layout.screen_splash);

		initContents();

		databaseHelper.clearCategoryTable();
		databaseHelper.clearMenuTable();
		databaseHelper.clearImagesTable();




		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}


		if(!DevicePreferences.getInstance().isDeviceRegistered()){
			showDeviceRegisterDialog();
		}
		else{
			showMenuScreen();
//			getCategories();
//			getItems();
		}


		//showDeviceRegisterDialog();
		//showPassCodeDialog();
		//showMenuScreen();


		/*
		  new Handler().postDelayed(new Runnable() {

		  @Override public void run() {

		  	showMenuScreen();


		  } },2000);
		*/

	}

	private void initContents() {
		gson = new Gson();
		databaseHelper = new DatabaseHelper(this);
		databaseHelper.getWritableDatabase();
		DevicePreferences.getInstance().init(SplashScreen.this);

	}

	private void showMenuScreen() {
		Intent intent = new Intent(SplashScreen.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void getItems() {
		int categoryIdListSize = categoryIdList.size();
		ArrayList<Item> itemList = new ArrayList<Item>();
		Item receivedItem;
		for (int i = 0; i < categoryIdListSize; i++) {

			try {
				itemList = RestClient.getAdapter().getItemSync(
						categoryIdList.get(i));

			}catch(RetrofitError error){
				itemList = RestClient.getAdapter().getItemSync(
						categoryIdList.get(i));

			}

			int itemListSize = itemList.size();

			for(int x = 0; x < itemListSize; x++){
				receivedItem = itemList.get(x);

				String receivedItemJson = gson.toJson(receivedItem);

				Item item = gson.fromJson(receivedItemJson,Item.class);


				databaseHelper.addItem(item);
				List<Item.Image> images = itemList.get(x).getImages();
				int imagesListSize = images.size();
				for (int z = 0; z < imagesListSize  ; z++) {

					byte[] itemImage = convertToBlob(images.get(z).getUrl());databaseHelper.addImages(item.getId(), itemImage,
							images.get(z).getCreated_at(), images.get(z).getUpdated_at());
				}
			}
				
			}
			
			
			
		Toast.makeText(SplashScreen.this, "Items added successfully into db",
				Toast.LENGTH_SHORT).show();


		showMenuScreen();

	}

	private void getCategories() {
		categoryIdList = new ArrayList<Integer>();
		ArrayList<Category> categoryArrayList;
		try {
			categoryArrayList = RestClient.getAdapter()
					.getCategoriesSync();
		}catch (RetrofitError retrofitError){
			categoryArrayList=RestClient.getAdapter().getCategoriesSync();
		}

		for (int i = 0; i < categoryArrayList.size(); i++) {
			Category receivedCategory = categoryArrayList.get(i);
			categoryIdList.add(categoryArrayList.get(i).getId());

			String receivedCategoryJson = gson.toJson(receivedCategory);


			Category category=gson.fromJson(receivedCategoryJson, Category.class);

			if(!(category.getImageUrl()== null)){
				byte[] imageBlob = convertToBlob(category.getImageUrl());


				category.setImageBlob(imageBlob);
				Log.d("GetCat",category.getName());
			}
			databaseHelper.addCategory(category);

		}
		Toast.makeText(SplashScreen.this,
				"Categories added successfully into db", Toast.LENGTH_SHORT)
				.show();


	}

	private byte[] convertToBlob(String imageURL) {

		try {
			URL imageUrl=new URL(imageURL);
			URLConnection urlConnection = imageUrl.openConnection();

			InputStream inputStream = urlConnection.getInputStream();
			BufferedInputStream bis=new BufferedInputStream(inputStream);

			ByteArrayBuffer baf=new ByteArrayBuffer(500);

			int current=0;
			while((current = bis.read())!=-1){
				baf.append((byte)current);
			}

			return baf.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/* TODO:Logic for handling first time setup */
	/*
	if(userRegistered){

		getCategories();
		getItems();

	}

	else{
		showSetUpScreen();
	}*/


	public void showPassCodeDialog(){

		Dialog dialog = new Dialog(this);

		dialog.setTitle(R.string.pass_code_title);
		dialog.setContentView(R.layout.dialog_pass_code);

		EditText passCodeText=(EditText)dialog.findViewById(R.id.dialog_pass_code_text);
		String passCode = passCodeText.getText().toString();
		Button verifyButton = (Button)dialog.findViewById(R.id.dialog_pass_code_button);
		verifyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				RestClient.getAdapter().verifyUser(passCode, new Callback<PassCodeResponse>() {


					@Override
					public void success(PassCodeResponse passCodeResponse, Response response) {

						DevicePreferences.getInstance().init(SplashScreen.this);

						DevicePreferences.getInstance().setClientKey(passCodeResponse.toString());

						showDeviceRegisterDialog();
					}

					@Override
					public void failure(RetrofitError retrofitError) {

					}
				});
			}
		});
		dialog.show();

	}

	private void showDeviceRegisterDialog() {
		Dialog dialog=new Dialog(SplashScreen.this);

		dialog.setTitle(R.string.title_device_registration);
		dialog.setContentView(R.layout.dialog_register_device);

		EditText deviceNameText=(EditText) dialog.findViewById(R.id.dialog_register_text);
		Button registerButton = (Button)dialog.findViewById(R.id.dialog_register_button);



		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				String deviceName = deviceNameText.getText().toString();
				String deviceId = DevicePreferences.getInstance().getDeviceId();

				DeviceRegister deviceRegister=new DeviceRegister(deviceId,deviceName);


				RestClient.getAdapter().registerDevice(deviceRegister, new Callback<DeviceRegister.Response>() {


					@Override
					public void success(DeviceRegister.Response response, Response response2) {

						DevicePreferences.getInstance().setDeviceRegistrationStatus(true);
						getCategories();
						getItems();
					}

					@Override
					public void failure(RetrofitError retrofitError) {

					}
				});
			}
		});


		dialog.show();

	}
}