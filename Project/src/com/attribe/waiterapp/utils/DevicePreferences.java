package com.attribe.waiterapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

/**
 * Created by Sabih Ahmed on 5/22/2015.
 */
public class DevicePreferences {

    private Context mContext;

    public DevicePreferences(Context context){
        mContext = context;
    }

    public void setClientKey(String clientSecret){
        SharedPreferences prefs = mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(clientSecret,"clientSecret");

        editor.commit();

    }


    public String getClientKey(String key){
        SharedPreferences prefs = mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);

        String clientSecret = prefs.getString(key, null);

        return clientSecret;

    }
}
