package com.example.muge.certainwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by muge on 15.5.2016.
 */
public class SnoozeReceiver extends BroadcastReceiver {
    private static final String TAG = "SnoozeReceiver";

    Context context;
    int snoozeCounter,alarmId;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;

        Bundle extras = intent.getExtras();
        if (extras != null) {
            snoozeCounter = extras.getInt("snoozeCounter");
            alarmId = extras.getInt("alarm");

        }

        Intent newIntent = new Intent(context, ActiveAlarmActivity.class);
        newIntent.putExtra("snoozeCounter", snoozeCounter);
        newIntent.putExtra("isSnoozed",true);
        newIntent.putExtra("alarm",alarmId);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
        Toast.makeText(context,"snooze receiverîn içerisinde",Toast.LENGTH_LONG).show();
        Log.e(TAG, "ALARM RECEIVED!!!");
    }
}
