package com.attribe.waiterapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.Settings;

/**
 * Created by Sabih Ahmed on 5/22/2015.
 */
public class DevicePreferences {

    private Context mContext;
    private static DevicePreferences mInstance;
    private SharedPreferences mPrefs;
    private static String DEVICE_REGISTRATION = "deviceRegistrationFlag" ;
    private static String DEVICE_VERIFICATION = "deviceVerificationFlag";

    public static DevicePreferences getInstance(){
        if(mInstance == null){

            mInstance =new DevicePreferences();
        }

        return mInstance;

    }
    private DevicePreferences(){

    }

    public void init(Context context){

        this.mContext = context;

        mPrefs= mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);
    }


    public void setClientKey(String clientSecret){

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putString(clientSecret,"clientSecret");

        editor.commit();

    }


    public String getClientKey(String key){
        //SharedPreferences prefs = mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);

        String clientSecret = mPrefs.getString(key, null);

        return clientSecret;

    }

    public void setDeviceRegistrationStatus(Boolean isRegistered){

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putBoolean(DEVICE_REGISTRATION, isRegistered);

        editor.commit();
    }

    public Boolean isDeviceRegistered(){

        boolean isRegistered;

        isRegistered = mPrefs.getBoolean(DEVICE_REGISTRATION, false);

        return isRegistered;

    }

    public void setDeviceVerificationStatus(boolean verificationStatus){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(DEVICE_VERIFICATION, verificationStatus);

        editor.commit();

    }

    public boolean isDeviceVerified(){
        boolean verificationFlag;

        verificationFlag = mPrefs.getBoolean(DEVICE_VERIFICATION,false);

        return verificationFlag;
    }



    public String getDeviceId(){

       String deviceId = Settings.Secure.getString(this.mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        //String deviceId = "QWERTY111";

        return deviceId;
    }



}
