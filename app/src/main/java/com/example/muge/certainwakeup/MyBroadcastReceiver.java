package com.example.muge.certainwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by caglacagla on 12.4.2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Alarm vakti",Toast.LENGTH_SHORT).show();
    }
}
