package com.example.muge.certainwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by muge on 15.5.2016.
 */
public class AlarmReceiver extends BroadcastReceiver{

    private static final String TAG = "AlarmReceiver";

    Context context;
    int snoozeCounter;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "Alarm received!");
        this.context = context;
        //set bundle with snooze counter
        Bundle extras = intent.getExtras();
        if (extras != null) {
            snoozeCounter = extras.getInt("SNOOZE_COUNTER");
        }
        // and start new activity
        Intent newIntent = new Intent(context, ActiveAlarmActivity.class);
        newIntent.putExtra("SNOOZE_COUNTER", snoozeCounter);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}
