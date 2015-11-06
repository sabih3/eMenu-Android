package com.attribe.genericwaiterapp.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.attribe.genericwaiterapp.screens.CarouselScreen;

/**
 * Created by Sabih Ahmed on 5/11/2015.
 */
public class BootReceiver  extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent=new Intent(context, CarouselScreen.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);

    }
}
